package com.example.demo_service_interface.service;

import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;

public interface DemoService {
    String sayHello();

    DemoVO invokeObject(DemoVO arg,boolean isThrowException) throws ServiceException;

    String uploadFile(byte[] bytes);
}
