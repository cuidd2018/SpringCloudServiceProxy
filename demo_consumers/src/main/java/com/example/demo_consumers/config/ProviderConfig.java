package com.example.demo_consumers.config;


import com.example.demo_service_interface.service.DemoService;
import com.zxj.cloud_service_proxy_core.util.invoke.InvokeRemoteServiceURL;
import com.zxj.cloud_service_proxy_core.util.invoke.RemoteServiceProxyFactory;
import com.zxj.cloud_service_proxy_core.util.invoke.RestTempleteProvider;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 远程服务代理Bean 配置
 * @author zhuxiujie
 * @since 2017/12/7
 */
@Configuration
public class ProviderConfig {

    private static final int TIME_OUT=30*1000;

    @Resource
    private RestTempleteProvider restTempleteProvider;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory=new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(TIME_OUT);
        clientHttpRequestFactory.setConnectTimeout(TIME_OUT);
        return new RestTemplate(clientHttpRequestFactory);
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

                @Override
                public String service() {
                    return InvokeRemoteServiceURL.SERVICE_EVEYY_THING;
                }
            };
        }
        return restTempleteProvider;
    }


    @Bean
    public DemoService demoService() {
        DemoService demoService = RemoteServiceProxyFactory.newInstance(restTempleteProvider, InvokeRemoteServiceURL.SERVICE_EVEYY_THING, DemoService.class);
        return demoService;
    }
}
