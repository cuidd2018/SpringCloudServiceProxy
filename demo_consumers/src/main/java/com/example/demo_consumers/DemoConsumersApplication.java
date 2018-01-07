package com.example.demo_consumers;

import com.alibaba.fastjson.JSON;
import com.example.demo_service_interface.page.Page;
import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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
	 * @param exception 1 抛出异常测试，0 不抛出
	 * @return
	 * @throws ServiceException
	 */
	@ResponseBody
	@RequestMapping("/invokeObject")
	public String hello(@RequestParam(value = "exception",defaultValue = "0")int exception) throws ServiceException {
		DemoVO demoVO=new DemoVO();
		Page<DemoVO> demoVOPage= demoService.invokeObject(demoVO,exception==1?true:false);
		return JSON.toJSONString(demoVOPage);
	}

	/**
	 * 测试远程上传文件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(@RequestParam(value = "file")MultipartFile file) throws ServiceException, IOException {
		String result= demoService.uploadFile(file.getBytes(),file.getOriginalFilename());
		return result;
	}
}
