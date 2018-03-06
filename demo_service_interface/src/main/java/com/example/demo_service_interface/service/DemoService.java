package com.example.demo_service_interface.service;

import com.example.demo_service_interface.page.Page;
import com.example.demo_service_interface.page.PageRequest;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;

public interface DemoService {
    String sayHello();

    Page<DemoVO> invokeObject(PageRequest pageRequest, boolean isThrowException) throws ServiceException;

    String uploadFile(byte[] bytes,String name);
}
