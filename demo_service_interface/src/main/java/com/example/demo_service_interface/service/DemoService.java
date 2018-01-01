package com.example.demo_service_interface.service;

import com.example.demo_service_interface.vo.DemoVO;

public interface DemoService {
    String sayHello();

    DemoVO invokeObject(DemoVO arg);
}
