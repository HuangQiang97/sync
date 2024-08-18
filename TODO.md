

### 注册

`org.springframework.cloud.client.discovery.EnableDiscoveryClient`，

@EnableDiscoveryClient->@Import(AutoServiceRegistrationConfiguration)->@EnableConfigurationProperties(AutoServiceRegistrationProperties.class)

NacosServiceRegistryAutoConfiguration->AbstractAutoServiceRegistration->onApplicationEvent->register->NacosServiceRegistry.register->NacosNamingService.registerInstance->

Spring Cloud Alibaba Nacos Discovery 遵循了 Spring Cloud Common 标准，实现了 AutoServiceRegistration、ServiceRegistry、Registration 这三个接口。

在 Spring Cloud 应用的启动阶段，监听了 WebServerInitializedEvent 事件，当 Web 容器初始化完成后，即收到 WebServerInitializedEvent 事件后，会触发注册的动作，调用 ServiceRegistry 的 register 方法，将服务注册到 Nacos Server。
### 心跳

### 订阅

### 负载均衡

`org.springframework.cloud.client.loadbalancer.LoadBalanced`、`org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient`、

`org. springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer`

### 降级、熔断、限流

https://www.cnblogs.com/luozhiyun/p/11537947.html

https://myblackboxrecorder.com/sentinel-reading-4/

https://cloud.tencent.com/developer/article/1855600

```
@GetMapping("/myEndpoint")
@SentinelResource(value = "myEndpointResource", blockHandler = "handleLimit")
public String myEndpoint() {
    return "This is my endpoint";
}

// 限流后的处理方法
public String handleLimit(BlockException ex) {
    return "Request has been limited";
}

@Component
public class SentinelRulesConfiguration {
    /**
     * You can configure sentinel rules by referring.
     * https://sca.aliyun.com/docs/2023/user-guide/sentinel/advanced-guide/#%E6%9B%B4%E5%A4%9A%E9%85%8D%E7%BD%AE%E9%A1%B9
     */
    @PostConstruct
    public void init() {

        // 配置限流规则
        FlowRule rule = new FlowRule();
        rule.setResource("myEndpointResource");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1); // QPS 限制为 5
        FlowRuleManager.loadRules(Collections.singletonList(rule));

    }
}

```

### 远程调用链路

`Nacos`

### 参数推送

`com.alibaba.nacos.api.config.ConfigService`、`com.alibaba.nacos.api.config.listener.Listener`、`org.springframework.cloud.config.server.EnableConfigServer`

### 分布式事务

`seata`

### 任务调度

shedlock分布式抢锁模式