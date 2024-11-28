### 数据对象

* 展示层=服务器的controller层+用户侧交互界面。

* 业务逻辑层：处理业务规则、逻辑和流程，完成数据验证、计算、数据转换。

* 数据访问层：数据查询和持久化。

* VO：用于展示层，VO对DTO数据进行解释封装，适配不同交互界面数据需求。

* DTO：数据传输对象，用于展示层与服务层之间的数据传输对象，功能对外需要剪切BO数据，能够完整的表达一个业务模块的输出。

* BO：业务对象，把某类业务逻辑封装为一个对象，是该业务对应多种PO的组合，功能对内。

* PO/DO/Entity：持久化对象，对应于关系型数据库的数据结构。

<img src=".\assets\image-20240122212844960.png" alt="image-20240122212844960" style="zoom:50%;" />

### 泛型

* `List<? extends SomeClass>`和` List<? super SomeClass>` 都被擦除为`List<SomeClass>`。
* `List<? extends A> a`：`a` 是一个生产者，可以生产 `A` 或其子类的对象。可以确保从 `a` 读取的每个对象至少是 `A` 类型的，所以使用`A`读取操作是类型安全的（向上转型）。因为无法确定 `a` 的确切类型，如果允许写入一个特定类型的对象，可能会造成向下转型，不安全。
* `List<? super B> b`：`b` 是一个消费者，可以消费 `B` 或其任何子类的对象。可以安全地向 `b` 写入 `B` 的实例或其任何子类的实例，因为 `b` 被声明为可以容纳 `B` 或其父类的任何类型（向上转型）。从 `b` 读取数据时，由于 `b` 可能指向 `List<B>` 或 `List<B>` 的任何父类，因此无法确定读取的对象的确切类型，可能会造成向下转型，不安全，除非将它们视为 `Object` 类型。

### HBase

HBase是一个面向列的可扩展的数据库，适用于非结构化和半结构化数据，可以快速扩展节点，使用Hadoop分布式文件系统（HDFS）来存储数据，查询语言相对简单，以低成本存储海量的数据，支持高并发随机写和实时查询。

![image-20240315211428481](C:\Users\053799\Desktop\阅读\assets\image-20240315211428481.png)

* 列（Column）都得归属到列族（Column Family）中，用列修饰符（Column Qualifier）来标识每个列。行与行之间的列组成不需要相同，一个列族下可以任意添加列。

* 数据写到HBase的时会被记录一个时间戳，读取时按照时间戳读最新的记录。
* HBase本质上其实就是Key-Value的数据库：Key=RowKey(行键)+ColumnFamily（列族）+Column Qualifier（列修饰符）+TimeStamp（时间戳），而Value=实际上的值。

<img src="C:\Users\053799\Desktop\阅读\assets\image-20240315212057276.png" alt="image-20240315212057276" style="zoom:60%;" />

* HRegionServer负责将HFile映射到HDFS，一个HRegionServer只负责一部分数据， 依据RowKey做横向切分表，HRegion里边会有多个Store，每个Store就是一个列族的数据（列存储结构）。

* 单点查询：如果数据分布均匀，利用好RowKey为字典序排序特性，通过匹配每个HREGION的最大和最小RowKey，匹配待查找的HREGION；如果数据分布不均匀，对RowKey哈希定位查找的HREGION。

* 范围查询：RowKey为字典序排序，定位目标HREGION。

  ### 负载指标

* 每秒处理请求数（Transactions Per Second），80%的访问集中在20%的时间

$$
TPS=\frac{N_{用户总数}R_{日活比例}{N_{操作发生次数}}*0.8}{24*60*60*0.2}\\
$$



* 每秒处理事务数（Queries Per Second）

$$
QPS=TPS*N_{操作需要请求数}
$$

* 系统同时处理中请求数

$$
并发数=QPS*T_{操作耗时}\\
$$

* 网络文件传输带宽

$$
带宽=N_{总数}R_{日活比例}R_{同时在线比例}R_{操作同时运行比例}N_{操作文件数}N_{操作文件大小}\\
$$

* 每日新增存储量

$$
每日新增存储=N_{用户总数}R_{日活比例}N_{操作文件数}N_{操作文件大小}\\
$$

### DelayQueue

##### 1，初始化

```java
private final transient ReentrantLock lock = new ReentrantLock();
private final PriorityQueue<E> q = new PriorityQueue<E>();
private Thread leader;
private final Condition available = lock.newCondition();
```

* ` lock = new ReentrantLock()`保证多线程操作队列的安全性。

* `q = new PriorityQueue<E>()`按照到期时间从小到大对元素排序。

* `Thread leader`等待对队首元素操作的leader线程，该线程将被定时休眠后唤醒，最先获取值返回。

* `available = lock.newCondition()`与Leader/Followers模式配合，Followers在此等待，当leader离任时某个Follower被唤醒成为新的leader，非公平获取，线程获取元素的返回顺序和调用顺序不一定一致。。

  保证只有一个leader线程会被定时唤醒，其余Followers线程在`available `上无限休眠，减少不必要的竞争。

* 非公平获取，线程获取元素的返回顺序和调用顺序不一定一致。

  ##### 2，加入队列

```java
public boolean offer(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        q.offer(e);
        if (q.peek() == e) {
            leader = null;
            available.signal();
        }
        return true;
    } finally {
        lock.unlock();
    }
}
```

* `available.signal()`：如果`e`加入队列后自己为队首元素，证明`e`的到期时间早于向前队首元素$\hat{e}$，而`leader`线程的休眠时间设定为$\hat{e}$的到期时间，所以需要提前唤醒等待中的线程(`leader`和`flowers`线程)，让他们尝试获取`e`或者将自己的休眠时间更新为`e`的到期，保证`e`被及时消费。

  举例：->当前队首元素$\hat{e}$过期时间10s，leader线程休眠10s

     	->1s后过期时间5s的新元素`e`加入队列，成为队首元素
     	
     	->如果不手动唤醒线程，元素`e`只能在9s被主动唤醒的`leader`线程获取，此时`e`已经过期4s
     	
     	->如果手动唤醒等待线程，被唤醒的线程更新自己的定时休眠时间为5s，`e`将能在5s后被及时消	      费。

* `leader = null`：`leader`是对队首元素操作的线程，处于定时休眠状态，具有更高的获取数据优先级。当`leader`和`flower`线程休眠时，由于二者处于同一个等待队列和柱塞队列，无法保证`available.signal()`唤醒的是`leader`线程，如果被唤醒的是`flower`线程且队首元素`e`未到期，该线程发现存在`leader`线程后将进入永久休眠（`take`方法的第15行），之后只能等待`leader`线程休眠时间到后主动唤醒获取队首元素，但由于`leader`线程休眠时间是按照$\hat{e}$设定的，长于`e`的过期时间，将导致`e`不能及时被消费。

  举例：->前队首元素$\hat{e}$过期时间10s，leader线程休眠10s

  ​	   ->1s后过期时间5s的新元素`e`加入队列，成为队首元素，唤醒一个等待线程
  
  ​	   ->如果不将leader标识置空，且被唤醒的线程是之前的flower线程，唤醒线程将再次进入永久休眠，元素`e`只		能在9s被主动唤醒的`leader`线程获取，此时`e`已经过期4s
  
     	->如果将leader标识置空，被唤醒的线程将成为新的leader线程，并且更新自己的定时休眠时间为5s，`e`将能		在5s后被及时消费。

* 公平性：由于手动调用了`available.signal()`，被唤醒线程不一定是`leader`线程，所以是非公平，方法返回顺序不一定和调用顺序一致，

##### 3，获取元素

```java
 // 柱塞方式获得元素
public E take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        for (;;) {
            E first = q.peek();
            if (first == null)
                available.await();
            else {
                long delay = first.getDelay(NANOSECONDS);
                if (delay <= 0L)
                    return q.poll();
                first = null; // don't retain ref while waiting
                if (leader != null)
                    available.await();
                else {
                    Thread thisThread = Thread.currentThread();
                    leader = thisThread;
                    try {
                        available.awaitNanos(delay);
                    } finally {
                        if (leader == thisThread)
                            leader = null;
                    }
                }
            }
        }
    } finally {
        if (leader == null && q.peek() != null)
            available.signal();
        lock.unlock();
    }
}

```

* `leader = null`：24行leader线程定时唤醒后即完成一届任期，如果自己还是leader将自动卸任进入下一轮leader的竞选，以便其他线程有机会成为新的领导线程并被唤醒。
* 能否自动连任`leader`，修改进入无限期休眠条件为当前`leaer`不是自己、定时唤醒后不卸任自动连任、返回前再卸任：

```java
public E take() throws InterruptedException {
    //...
    try {
        //...
		// 修改进入无限期休眠条件
        if (leader != null&&leader != Thread.currentThread())
            available.await();
        else {
            Thread thisThread = Thread.currentThread();
            leader = thisThread;
            try {
                available.awaitNanos(delay);
            } finally {
                // 不卸任leader，自动连任
                // if (leader == thisThread)
                //     leader = null;
            }
        }
    } finally {
        // 返回前卸任leader
        leader=null;
        if (q.peek() != null)
            available.signal();
        lock.unlock();
    }
}
```



上述

```java
int n = 10; // 时间片个数
AtomicInt[] a = new AtomicInt[n]; // 计数数组
int deltaT = 1; // 时间片长度
int count = 0; // 窗口中请求数
int lastTime = startTime / deltaT; // 最近一个请求到达的时间下标
int maxCount = 1024; // 最大请求数

boolean canPass() {
    // 新来请求
    int currTime = now / deltaT;
    // 清除过期时间片
    for (int i = lastTime; i < currTime && lastTime % n != currTime % n; ++i) {
        int index = i % n;
        count -= a[index];
        a[index] = 0;
    }
    lastTime = currTime; // 更新最近一次请求的时间
    // 更新计数
    int index = currTime % n;
    count += 1;
    a[index] += 1;
    // 能否通过判断
    if (count >= maxCount) {
        return false;
    } else {
        return true;
    }
}

```

### CompletableFuture

* 通过`CompletableFuture.supplyAsync(Supplier, Executor)`方法启动任务，并返回`CompletableFuture`用于后续链路处理。
* 任务提交后，主线程立即返回，任务默认交由`ForkJoinPool`线程池执行，也可以手动传入线程池。
* 获得上一步的`CompletableFuture`后开启新的操作，分为同步（由上一步线程执行）或者异步（线程池执行），异步版本支持自定义线程池。
* `thenApply(Function)` 和 `thenApplyAsync(Function,Executor )`接收上一个阶段的结果，同步或者异步地应用一个`Function`并返回结果。适用于需要转换结果而不需要进一步的异步操作时。
* `thenAccept(Consumer)` 和 `thenAcceptAsync(Consumer,Executor )`：接收上一个阶段的结果，同步或者异步地执行一个`Consumer`操作，但不返回结果。适合于在异步流程的最后执行副作用操作，而不用关心返回值。
* `thenCompose(Function)` 和 `thenComposeAsync(Function,Executor )`：接收上一个阶段的结果，同步或者异步地应用一个函数，该函数返回一个新的`CompletionStage`。适用于以上一步操作的输出为输入，开启新的异步任务，实现异步操作的流水线。
* `thenCombine(CompletionStage, BiFunction)` 和 `thenCombineAsync(CompletionStage, BiFunction, Executor)`：与另一个`CompletionStage`并发或者异步并发执行，两者结果都完成后，应用提供的`BiFunction`并返回`CompletableFuture`结果。`thenCombine`下`BiFunction`由两任务中后一个完成的任务的线程执行，适合在不需要额外并发开销且确信组合操作不会阻塞或非常快速完成的情况下使用；`thenCombineAsync` 下则有线程池执行，适用于组合函数可能是计算密集型或可能需要更长时间执行时。
* `thenRun(Runnable )` 和 `thenRunAsync(Runnable, Executor)`：在上一个阶段完成后，同步活异步地执行一个`Runnable`操作，不接收上一个阶段的结果，也不返回结果。适用于副作用操作。

