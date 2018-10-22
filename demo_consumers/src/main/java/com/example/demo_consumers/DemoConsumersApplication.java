package com.example.demo_consumers;

import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.bean.page.Page;
import com.zxj.cloud_service_proxy_core.bean.page.PageRequest;
import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.util.convert.ConvertUtil;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableWebFlux
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
	@RequestMapping("/")
	public String index(){
		List<ThrowExceptionType> list=new ArrayList<>();
		list.add(ThrowExceptionType.table.THROW_EXCEPTION);
		return demoService.sayHello(list);
	}

	/**
	 * 测试远程调用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hello")
	public String helloTest(){
		List<ThrowExceptionType> list=new ArrayList<>();
		list.add(ThrowExceptionType.table.THROW_EXCEPTION);
		return demoService.sayHello(list);
	}


	/**
	 * 测试远程调用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public List<String> list(){
		return demoService.list();
	}

	/**
	 * 返回 数据库分页
	 * 测试远程调用使用 复杂参数对象
	 * @param exception 1 抛出异常测试，0 不抛出
	 * @return
	 * @throws ServiceException
	 */
	@ResponseBody
	@RequestMapping("/page")
	public Page<DemoVO> hello(@RequestParam(value = "exception",defaultValue = "0",required = false)int exception,
						@RequestParam(value = "page",defaultValue = "1",required = false)Integer page,
						@RequestParam(value = "size",defaultValue = "20",required = false)Integer size
						) throws ServiceException {
		PageRequest pageRequest=PageRequest.create(page,size);
		ThrowExceptionType throwExceptionType=ThrowExceptionType.valueOf(exception);

		List<ServiceProxyErrorCode> serviceProxyErrorCodeList=new ArrayList<>();
		serviceProxyErrorCodeList.add(ServiceProxyErrorCode.table.ERROR);
		Map<String,List<ServiceProxyErrorCode>> stringServiceProxyErrorCodeMap=new HashMap<>();
		stringServiceProxyErrorCodeMap.put("error",serviceProxyErrorCodeList);

		Page<DemoVO> demoVOPage= demoService.invokeObject(pageRequest,throwExceptionType,EnumUtils.constantVO(throwExceptionType),serviceProxyErrorCodeList,stringServiceProxyErrorCodeMap);

		try {
			System.out.println(ConvertUtil.getEncoder().encoder(demoVOPage.getContent().get(0).getTestVOMap().get("test")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return demoVOPage;
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
