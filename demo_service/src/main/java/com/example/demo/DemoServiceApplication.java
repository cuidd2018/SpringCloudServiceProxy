package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo_service_interface.page.Page;
import com.example.demo_service_interface.page.PageRequest;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DemoServiceApplication {

	public static void main(String[] args) throws ServiceException {
		//SpringApplication.run(DemoServiceApplication.class, args);


		PageRequest page= PageRequest.create(1,20);

		var str=JSON.toJSONString(page);

		PageRequest request=JSON.parseObject(str,PageRequest.class);
		System.out.println(request);
	}
}
