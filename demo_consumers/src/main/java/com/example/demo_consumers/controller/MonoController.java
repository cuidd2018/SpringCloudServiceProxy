package com.example.demo_consumers.controller;

import com.alibaba.fastjson.JSON;
import com.zxj.cloud_service_proxy_core.react.mono.ServiceProxyMono;
import com.example.demo_service_interface.enums.ThrowExceptionType;
import com.example.demo_service_interface.page.Page;
import com.example.demo_service_interface.page.PageRequest;
import com.example.demo_service_interface.service.DemoService;
import com.example.demo_service_interface.vo.DemoVO;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class MonoController {

    @Resource
    private DemoService demoService;
    /**
     * 测试远程调用
     * @return
     */
    @ResponseBody
    @GetMapping("/mono")
    public Mono<String> index(){
        return ServiceProxyMono.createMono(() -> demoService.sayHello())
                ;
    }

    /**
     * 测试远程调用使用 复杂参数对象
     * @param exception 1 抛出异常测试，0 不抛出
     * @return
     * @throws ServiceException
     */
    @ResponseBody
    @RequestMapping("/mono/page")
    public Mono hello(@RequestParam(value = "exception",defaultValue = "0")Integer exception,
                      @RequestParam(value = "page",defaultValue = "1")Integer page,
                      @RequestParam(value = "size",defaultValue = "20")Integer size
    ) throws ServiceException {
        return ServiceProxyMono.createMono(() -> {
              PageRequest pageRequest = PageRequest.create(page, size);
              ThrowExceptionType throwExceptionType = ThrowExceptionType.valueOf(exception);
              Page<DemoVO> demoVOPage = demoService.invokeObject(pageRequest, throwExceptionType);
              String json = JSON.toJSONString(demoVOPage);
              return json;
          });
    }
}
