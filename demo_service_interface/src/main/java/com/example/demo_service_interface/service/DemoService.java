package com.example.demo_service_interface.service;

import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.example.demo_service_interface.page.Page;
import com.example.demo_service_interface.page.PageRequest;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;

import java.util.List;

public interface DemoService {

    String sayHello();

    Page<DemoVO> invokeObject(PageRequest pageRequest, ThrowExceptionType throwExceptionType) throws ServiceException;

    String uploadFile(byte[] bytes,String name);

    List<String> list();
}
