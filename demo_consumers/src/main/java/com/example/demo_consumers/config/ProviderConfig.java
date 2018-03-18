package com.example.demo_consumers.config;


import com.example.demo_service_interface.service.DemoService;
import com.zxj.cloud_service_proxy_core.util.invoke.DefaultRestTempleteProvider;
import com.example.demo_service_interface.config.RemoteMicroServiceName;
import com.zxj.cloud_service_proxy_core.util.invoke.RemoteServiceProxyFactory;
import com.zxj.cloud_service_proxy_core.config.RestTempletConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 远程服务代理Bean 配置
 * 为什么使用RemoteServiceProxyFactory.newInstance 而不用自定义annotation来标注，然后系统来自动发现并注册？
 * annotation的局限是，它只能用来标注你可以修改的类，而很多序列化中引用的类很可能是你没法做修改的（比如第三方库或者JDK系统类或者其他项目的类）。另外，添加annotation毕竟稍微的“污染”了一下代码，使应用代码对框架增加了一点点的依赖性。
 * @author zhuxiujie
 * @since 2017/12/7
 */
@Configuration
public class ProviderConfig {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return DefaultRestTempleteProvider.restTemplate(new RestTempletConfig());
    }


    @Resource
    private RestTemplate restTemplate;


    @Bean
    public DemoService demoService() {
        DemoService demoService = RemoteServiceProxyFactory.newInstance(restTemplate, RemoteMicroServiceName.SERVICE_EVEYY_THING, DemoService.class);
        return demoService;
    }
}
