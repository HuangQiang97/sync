[TOC]

## SEATA

### 概述

* `Seata`中有三大模块，分别是`TC`(事务协调器):维护全局事务的运行状态，负责协调并驱动全局事务的提交或回滚；`TM`(事务管理器)：控制全局事务的边界，负责开启一个全局事务，并最终发起全局提交或全局回滚的决议;`RM`(资源管理器)：控制分支事务，负责分支注册、状态汇报，并接收事务协调器的指令，驱动本地事务的提交和回滚。其中`TM`和`RM`是作为`Seata`的客户端与业务系统集成在一起，`TC`作为`Seata`的服务端独立部署。

<img src="./assets/TB19qmhOrY1gK0jSZTEXXXDQVXa-1330-924.png" alt="seata-mod" style="zoom: 40%;" />

* 执行分布式事务时：->`TM`向`TC`请求发起（Begin）、提交（Commit）、回滚（Rollback）全局事务；->`TM`把代表全局事务的`XID`绑定到分支事务上；->`RM`向`TC`注册，把分支事务关联到`XID`代表的全局事务中；->`RM`把分支事务的执行结果上报给`TC`；->`TM`结束分布式事务，事务一阶段结束，`TM`通知`TC`提交/回滚分布式事务；->`TC`汇总事务信息，决定分布式事务是提交还是回滚；->`TC`通知所有`RM`提交/回滚资源，事务二阶段结束；

### AT

#### 概述

* AT属于无侵入的分布式事务解决方案，为两阶段提交的变种。

    <img src="./assets/TB1NTuzOBr0gK0jSZFnXXbRRXXa-1330-924.png" alt="at-mod" style="zoom:40%;" />

* 在一阶段，`Seata`通过代理数据源，拦截解析业务SQL语义，找到业务SQL要更新的业务数据，在业务数据被更新前，将其保存成`beforeimage`，然后执行业务SQL更新业务数据，在业务数据更新之后，将其保存成`afterimage`，把业务数据在更新前后的数据镜像组织成回滚日志。

    先申请本地数据库写锁，成功后向`TC`申请关于这条记录的全局行锁，成功后将业务SQL和`undolog`写入同一个事务中，提交到数据库中，保证业务SQL必定存在相应的回滚日志，保证一阶段操作的原子性，最后释放本地数据库锁，对分支事务状态向`TC`上报执行成功。如果获取全局锁失败，重试失败则回滚本地事务，并向`TC`汇报本地事务执行失败。

![图片3.png](./assets/11-b62ee2dfd6ab6e9094d2b51451b41cdd.png)<img src="./assets/webp.webp" alt="img" style="zoom:50%;" />

* 二阶段如果提交，立即释放相关记录的全局锁。因为业务SQL在一阶段已经提交至数据库，所以把提交请求放入一个异步任务的队列中，马上返回提交成功的结果给`TC`。异步队列中的提交请求真正执行时，只是删除相应`UNDOLOG`和行锁，可以快速完成。

![图片4.png](./assets/12-fb7571a44266fa4692599f2907e93125.png)

* 二阶段回滚，需要回滚一阶段已经执行的业务SQL，还原业务数据。`RM`通过`XID`找到对应的`undolog`回滚日志。首先校验脏写，对比数据库当前业务数据和`afterimage`，如果两份数据完全一致就说明没有脏写，可以还原业务数据，生成并执行回滚的语句；如果不一致就说明有脏写，需要额外处理。最后用`beforeimage`还原业务数据，并删除`undolog`日志，释放全局锁。

![图片5.png](./assets/13-67b42e8743563de3117194847e4119de.png)



#### 隔离级别

* 读取：由于一阶段`RM`自动提交本地事务的原因，默认隔离级别为`ReadUncommitted`，即可以读取到其他分布式事务未提交数据，本地事务隔离级别由本地DB决定。如果希望隔离级别为`ReadCommitted`，需要使用`SELECT...FORUPDATE`语句进行当前读，同时代理方法增加`@GlobalLock`注解。此时会先申请本地锁再申请全局锁。如果获取全局锁失败，则释放本地锁并重试，直到持有该全局锁的分布式事务提交，当前查询获得全局锁，读取到其他分布式事务已提交数据。

#### 性能

`XA`方案事务性资源的锁都要保持到Phase2完成才释放。`AT`将锁分为了本地锁和全局锁，本地锁由本地事务管理，在分支事务一阶段结束时释放，全局锁由`TC`管理，在二阶段全局提交时，全局锁立即释放，二阶段回滚时，全局锁被持有至分支的二阶段结束。`AT`剥离了分布式事务方案对数据库在协议支持上的要求，避免XA协议需要同步协调导致资源锁定时间过长的问题。

<img src="./assets/webp-1724587041953-49.webp" alt="img" style="zoom:57%;" /><img src="./assets/webp-1724587050361-52.png" alt="img" style="zoom:60%;" />

#### 启动配置

* `seata`启动依赖自动配置类。在`io.seata-spring-boot-starter`下的`spring.factories`中包含以下启动配置类

    ```properties
    # Auto Configure
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
    io.seata.spring.boot.autoconfigure.SeataAutoConfiguration,\
    io.seata.spring.boot.autoconfigure.SeataDataSourceAutoConfiguration,\
    ```

    用于配置全局事务扫描器`GlobalTransactionScanner`用于`TM, MC`实例化数据源代理`SeataAutoDataSourceProxyCreator`用于拦截业务操作，生成`boforeImage, afterImage`，保证分布式事务的持久化和可回滚特性。

##### TM初始化

* 全局事务扫描器`GlobalTransactionScanner`实现了`InitializingBean`接口，将有`spring`自动调用`afterPropertiesSet`完成`initClient`调用，从而完成`RM, TM`初始化，初始化的参数来自`application.yml`。

```java
public class GlobalTransactionScanner implements InitializingBean, 
    private void initClient() {
        //初始化 TM
        TMClient.init(applicationId, txServiceGroup, accessKey, secretKey);
        //初始化 RM
        RMClient.init(applicationId, txServiceGroup);
        registerSpringShutdownHook();
    }
    public void afterPropertiesSet() {
        // cas保证每个节点RM，TM只会初始化一次
        if (initialized.compareAndSet(false, true)) {
            initClient();
        }
    }
}
```

* `TM`的作用是开启以及提交全局事务，`TMClient`初始化主要完成:创建连接池；创建并启动客户端`Netty`；创建并启动用于`RPC`交互的线程池。

    `TM`的实例由`TmNettyRemotingClient#getInstance`获得。获取时使用双检查模式，保证每个节点只存在一个`TM`。内部持有一个线程池，用于处理与`TC, RM`之间的`RPC`通信。

    初始化`TM`时，根据service_group得到cluster_name，从注册中心(例如`Nacos`)获得`TC`地址，建立与`TC`之间的连接，并注册自身，`TC`地址通过注册中心(例如`Nacos`)获得，此时同样使用双检查，避免重复连接。

```java
public final class TmNettyRemotingClient extends AbstractNettyRemotingClient {    
	public static TmNettyRemotingClient getInstance() {
   		 // 双检查模式, 保证单例
        if (instance == null) {
            synchronized (TmNettyRemotingClient.class) {
                if (instance == null) {
                    NettyClientConfig nettyClientConfig = new NettyClientConfig();
                    // 线程池用于处理与TC， RM之间的`RPC`通信
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                            nettyClientConfig.getClientWorkerThreads(), nettyClientConfig.getClientWorkerThreads(),
                            KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                            new NamedThreadFactory(nettyClientConfig.getTmDispatchThreadPrefix(),
                                    nettyClientConfig.getClientWorkerThreads()),
                            RejectedPolicies.runsOldestTaskPolicy());
                    instance = new TmNettyRemotingClient(nettyClientConfig, null, messageExecutor);
                }
            }
        }
        return instance;
    }
    private void initConnection() {
        // 与TC之间建立连接，`TC`地址通过注册中心获得， 前置双检查保证连接唯一
        getClientChannelManager().reconnect(transactionServiceGroup);
    }
}
```

##### RM初始化

* `RM`控制分支事务，负责分支注册、状态汇报，并接收`TC`的指令，驱动本地事务的提交和回滚。

    ```java
    public class RMClient {
    
        /**
         * Init.
         *
         * @param applicationId           the application id
         * @param transactionServiceGroup the transaction service group
         */
        public static void init(String applicationId, String transactionServiceGroup) {
            RmNettyRemotingClient rmNettyRemotingClient = RmNettyRemotingClient.getInstance(applicationId, transactionServiceGroup);
            rmNettyRemotingClient.setResourceManager(DefaultResourceManager.get());
            rmNettyRemotingClient.setTransactionMessageHandler(DefaultRMHandler.get());
            rmNettyRemotingClient.init();
        }
    ```

    

    `RM`的初始化和`TM`类似，实例对象通过`RmNettyRemotingClient#getInstance`获得。获取时使用双检查模式，保证每个节点只存在一个`RM`。同时内部持有一个线程池，用于处理与`TC, TM`之间的`RPC`通信。

```java
public final class RmNettyRemotingClient extends AbstractNettyRemotingClient {    
public static RmNettyRemotingClient getInstance() {
        // 双检查模式，保证每个节点只存在一个`RM`
        if (instance == null) {
            synchronized (RmNettyRemotingClient.class) {
                if (instance == null) {
                    NettyClientConfig nettyClientConfig = new NettyClientConfig();
                    // 一个线程池，用于处理与`TC, TM`之间的`RPC`通信
                    final ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(
                        nettyClientConfig.getClientWorkerThreads(), nettyClientConfig.getClientWorkerThreads(),
                        KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                        new NamedThreadFactory(nettyClientConfig.getRmDispatchThreadPrefix(),
                            nettyClientConfig.getClientWorkerThreads()), new ThreadPoolExecutor.CallerRunsPolicy());
                    instance = new RmNettyRemotingClient(nettyClientConfig, null, messageExecutor);
                }
            }
        }
        return instance;
    }
}
```



### TCC

#### 概述

* TCC属于两阶段提交的改进，同时属于有侵入的分布式事务方案。需要在`RM`处手动实现`Try,Comform,Cancel`，并且由业务层面实现`Cancel`保证可回滚以及回滚数据的持久化。`TM`在第一阶段询问所有`RM`是否成功，如果所有资源均准备成功，则在第二阶段执行所有资源的`Comform`操作，否则在第二阶段执行所有资源的`Cancel`操作。由于`Try`同步调用，其结果会影响到二阶段决策，因此TCC分布式事务解决方案适用于执行时间确定且较短的业务。
* 在TCC方案中，`RM`（资源管理器）需要提供准备、提交和回滚3个操作；

<img src="./assets/TB1m59pOEY1gK0jSZFCXXcwqXXa-1330-924.png" alt="tcc-mod" style="zoom:40%;" />

* 一阶段：`TRY`检查资源是否充足，并进行资源冻结，此时资源总数不变，被冻结资源将不能挪用。此时其他并发事务可以继续消费未被冻结资源，不阻塞其余事务。
* 二阶段：使用一阶段预冻结资源完成业务流程，执行成功后变更资源总数和被冻结资源，执行失败后释放冻结资源。由于只使用本事务一阶段冻结资源，其余事务冻结资源不会对当前事务的第二阶段执行产生影响。

![图片7.png](./assets/16-e07ff8c531571c0ef2887d9dc8b4c83f.png)

#### 容错性

* 允许空回滚：在`TRY`接口因为丢包时没有收到，事务管理器会触发回滚，这时会触发`Cancel`接口，这时`Cancel`执行时发现没有对应的事务`XID`或主键时，需要返回回滚成功。

<img src="./assets/empty_rollback-e06a0b70a6ab6a896c0bb67ace87b751.png" alt="image.png" style="zoom:33%;" />

* 防悬挂控制：`TRY`由于网络拥堵而超时，`Cancel`比`TRY`接口先执行，成功空回滚，此时如果`TRY`请求到达，则不应该执行。通过在`RM`处保留已执行回滚事务`XID`，`TRY`接口先检查事务`XID`是否回滚，如果已回滚则不执行`TRY`的业务操作。

<img src="./assets/susp-3a5ed6577950c1034caca1d822a7aa80.png" alt="image.png" style="zoom:33%;" />

* 幂等性：一次请求和重复的多次请求对系统资源的影响是一致的，在`RM`处保留`XID`对应事务`Try,Comform,Cancel`执行情况，用事务`XID`判重。

### saga

* Saga模式属于有侵入是分布式事务方案。各个阶段无直接耦合，参与者可以采用事务驱动异步执行高吞吐，通过逆向操作完成回滚。每个阶段完成后提交本地事务，无全局锁，长流程情况下可以保证性能。由于正向阶段无全局锁，且未进行进行资源预留，其他事务可以在分布式事务未完成时，读取到已完成阶段修改的数据，不能保证隔离性

<img src="./assets/image-20240825162517159.png" alt="image-20240825162517159" style="zoom:60%;" />

### XA

* XA属于无侵入式两阶段提交变种，通过分布式事务提交前一直持有锁的方式，保障从任意视角对数据的访问有效隔离，满足全局数据一致性，性能很差。