package com.example.demo.controller;


import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.LocalServiceAccessUtil;
import com.example.demo_service_interface.config.RemoteMicroServiceName;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rx.Single;
import rx.schedulers.Schedulers;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 调用服务层核心控制器，请勿修改此处代码！！！
 */
@Controller
public class ServiceInvokeCoreController {

    private static Logger logger = LoggerFactory.getLogger(ServiceInvokeCoreController.class);

    @Resource
    private ApplicationContext applicationContext;

    private static LocalServiceAccessUtil.Logger controllerLogger = info -> logger.info(info);

    private static ExecutorService executor = Executors.newFixedThreadPool(4);


    /**
     * 使用RXJava的类似观察者模式的机制处理异步任务
     * @param request
     * @return
     */
    @RequestMapping("/" + RemoteMicroServiceName.SERVICE_EVEYY_THING)
    public Single<byte[]> responseWithObservable(ServletRequest request) {

        byte[] bytes = null;
        InputStream inputStream=null;
        try {
            inputStream=request.getInputStream();
            bytes = SerializeUtil.input2byte(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO clear stream
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
        if (bytes == null) {
            throw new ServiceRuntimeException("input2byte fail! bytes=null!");
        }

        byte[] finalBytes = bytes;
        Single<byte[]> observable = Single.create((Single.OnSubscribe<byte[]>) singleSubscriber -> {
            byte[] result = null;
            try {
                result = LocalServiceAccessUtil.access(applicationContext, finalBytes, controllerLogger);
                singleSubscriber.onSuccess(result);
            }catch (Exception e){
                logger.error("invokeError",e);
            } catch (Throwable throwable) {
                logger.error("invokeError",throwable);
            }
        }).subscribeOn(Schedulers.from(executor));
        return observable;
    }
}
