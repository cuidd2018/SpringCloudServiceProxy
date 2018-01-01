package com.example.demo.controller;


import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class IndexController {

    @ResponseBody
    @RequestMapping(value = "/",produces="text/plain;charset=UTF-8")
    public String index() throws ServiceException {
        return "microservice is running!";
    }

}
