package com.example.demo.service;

import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl  implements DemoService{

    @Override
    public String sayHello() {
        return "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   hello word!";
    }

    @Override
    public DemoVO invokeObject(DemoVO arg) {
        arg.setName("demo");
        return arg;
    }
}
