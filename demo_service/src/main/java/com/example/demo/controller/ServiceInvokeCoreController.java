package com.example.demo.controller;

import com.zxj.cloud_service_proxy_core.util.invoke.LocalServiceAccessUtil;
import com.example.demo_service_interface.config.RemoteMicroServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 调用服务层核心控制器，请勿修改此处代码！！！
 */
@RestController
public class ServiceInvokeCoreController {

    private static Logger logger = LoggerFactory.getLogger(ServiceInvokeCoreController.class);


    private static LocalServiceAccessUtil.Logger controllerLogger = new LocalServiceAccessUtil.Logger() {
        @Override
        public void info(String info) {
            logger.info(info);
        }

        @Override
        public void error(String error) {
            logger.error(error);
        }

        @Override
        public void error(String info, Exception e) {
            logger.error(info, e);
        }
    };

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 使用Mono的类似观察者模式的机制处理异步任务
     * @param arg
     * @return
     * @throws Throwable
     */
    @ResponseBody
    @RequestMapping("/" + RemoteMicroServiceName.SERVICE_EVEYY_THING)
    Mono<String> invoke(@RequestParam("arg") String arg,HttpServletResponse response) throws Throwable {
        response.setStatus(200);
        return LocalServiceAccessUtil.asyncMonoAccess(applicationContext,arg,controllerLogger);
    }
}
