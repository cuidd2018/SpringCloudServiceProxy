package com.example.demo.service;

import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl  implements DemoService{

    @Override
    public String sayHello() {
        return "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<   hello word!";
    }

    @Override
    public DemoVO invokeObject(DemoVO arg ,boolean isThrowException) throws ServiceException {
        arg.setName("demo");
        if(isThrowException)throw new ServiceException("错误");
        return arg;
    }
}
