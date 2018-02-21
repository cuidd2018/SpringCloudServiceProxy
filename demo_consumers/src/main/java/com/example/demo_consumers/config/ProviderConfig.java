package com.example.demo_consumers.config;


import com.example.demo_service_interface.service.DemoService;
import com.zxj.cloud_service_proxy_core.util.invoke.DefaultRestTempleteProvider;
import com.example.demo_service_interface.config.RemoteMicroServiceName;
import com.zxj.cloud_service_proxy_core.util.invoke.RemoteServiceProxyFactory;
import com.zxj.cloud_service_proxy_core.util.invoke.config.RestTempletConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 远程服务代理Bean 配置
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
