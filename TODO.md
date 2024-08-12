1，服务：
* 注册：`org.springframework.cloud.client.discovery.EnableDiscoveryClient`，
  Spring Cloud Alibaba Nacos Discovery 遵循了 Spring Cloud Common 标准，实现了 AutoServiceRegistration、ServiceRegistry、Registration 这三个接口。

在 Spring Cloud 应用的启动阶段，监听了 WebServerInitializedEvent 事件，当 Web 容器初始化完成后，即收到 WebServerInitializedEvent 事件后，会触发注册的动作，调用 ServiceRegistry 的 register 方法，将服务注册到 Nacos Server。
* 心跳：
2，客户端：订阅、
* 负载均衡：`org.springframework.cloud.client.loadbalancer.LoadBalanced`、`org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient`、
  `org. springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer`
* 降级、熔断、限流
3，远程调用链路：`Nacos`
4，参数推送：`com.alibaba.nacos.api.config.ConfigService`、`com.alibaba.nacos.api.config.listener.Listener`、`org.springframework.cloud.config.server.EnableConfigServer`