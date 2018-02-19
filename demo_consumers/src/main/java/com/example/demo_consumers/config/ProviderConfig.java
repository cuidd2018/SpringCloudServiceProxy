package com.example.demo_consumers.config;


import com.example.demo_service_interface.service.DemoService;
import com.zxj.cloud_service_proxy_core.util.invoke.DefaultRestTempleteProvider;
import com.zxj.cloud_service_proxy_core.util.invoke.RemoteMicroServiceName;
import com.zxj.cloud_service_proxy_core.util.invoke.RemoteServiceProxyFactory;
import com.zxj.cloud_service_proxy_core.util.invoke.RestTempleteProvider;
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

    @Resource
    private RestTempleteProvider restTempleteProvider;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return DefaultRestTempleteProvider.restTemplate(new RestTempletConfig());
    }

    @Bean
    public RestTempleteProvider restTempleteProvider(){
        if(restTempleteProvider ==null) {
            restTempleteProvider = new RestTempleteProvider() {
                @Resource
                private RestTemplate restTemplate;

                @Override
                public Object getRestTemplete() {
                    return restTemplate;
                }
            };
        }
        return restTempleteProvider;
    }


    @Bean
    public DemoService demoService() {
        DemoService demoService = RemoteServiceProxyFactory.newInstance(restTempleteProvider, RemoteMicroServiceName.SERVICE_EVEYY_THING, DemoService.class);
        return demoService;
    }
}
