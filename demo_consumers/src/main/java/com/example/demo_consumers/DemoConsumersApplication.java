package com.example.demo_consumers;

import com.alibaba.fastjson.JSON;
import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class DemoConsumersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoConsumersApplication.class, args);
	}

	@Resource
	private DemoService demoService;


	/**
	 * 测试远程调用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hello")
	public String helloTest(){
		return demoService.sayHello();
	}

	/**
	 * 测试远程调用使用 复杂参数对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/invokeObject")
	public String hello(){
		DemoVO demoVO=new DemoVO();
		DemoVO newDemoVO= demoService.invokeObject(demoVO);
		return JSON.toJSONString(newDemoVO);
	}
}
