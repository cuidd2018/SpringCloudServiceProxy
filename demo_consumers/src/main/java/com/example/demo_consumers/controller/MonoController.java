package com.example.demo_consumers.controller;

import com.zxj.cloud_service_proxy_core.react.mono.ServiceProxyMono;
import com.example.demo_service_interface.service.DemoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
public class MonoController {

    @Resource
    private DemoService demoService;
    /**
     * 测试远程调用
     * @return
     */
    @ResponseBody
    @GetMapping("/mono")
    public Mono<String> index(){
        return ServiceProxyMono.createMono(() -> demoService.sayHello())
                ;
    }
}
