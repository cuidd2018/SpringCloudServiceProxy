package com.example.demo_service_interface.service;

import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.bean.page.Page;
import com.zxj.cloud_service_proxy_core.bean.page.PageRequest;
import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface DemoService {

    String sayHello();

    Page<DemoVO> invokeObject(PageRequest pageRequest, ThrowExceptionType throwExceptionType ,
                              List<ServiceProxyErrorCode> serviceProxyErrorCodes,
                              Map<String,List<ServiceProxyErrorCode>> serviceProxyErrorCodeMap//嵌套Map复杂类型

    ) throws ServiceException;

    String uploadFile(byte[] bytes,String name);

    List<String> list();
}
