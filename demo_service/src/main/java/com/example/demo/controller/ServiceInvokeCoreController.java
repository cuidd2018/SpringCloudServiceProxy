package com.example.demo.controller;


import com.zxj.cloud_service_proxy_core.util.invoke.InvokeRemoteServiceURL;
import com.zxj.cloud_service_proxy_core.util.invoke.LocalServiceProxyUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeStringUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 调用服务层核心控制器，请勿修改此处代码！！！
 */
@Controller
public class ServiceInvokeCoreController {

    private static Logger logger = LoggerFactory.getLogger(ServiceInvokeCoreController.class);

    @Resource
    private ApplicationContext applicationContext;


    @RequestMapping("/" + InvokeRemoteServiceURL.SERVICE_EVEYY_THING)
    public void everything(ServletRequest request, ServletResponse response) throws Throwable {
        InputStream inputStream = null;
        byte[] bytes = null;
        try {
            inputStream = request.getInputStream();
            bytes = SerializeStringUtil.input2byte(inputStream);
        } finally {
            //TODO clear stream
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
        if (bytes != null) logger.info("bytesLength:" + bytes.length);
        ServiceDTO serviceDTO = (ServiceDTO) SerializeStringUtil.deserialize(bytes);

        Object[] params = serviceDTO.getParams();
        Class[] paramTypes = serviceDTO.getParamsTypes();
        String method = serviceDTO.getMethod();
        String service = serviceDTO.getService();

        long startTime = System.currentTimeMillis();
        logger.info("method:" + method);
        logger.info("service:" + service);

        Object serviceResult = LocalServiceProxyUtil.invoke(params, method, service, paramTypes, applicationContext);
        byte[] result=null;
        if (serviceResult != null) result = SerializeStringUtil.serialize(serviceResult);
        long endTime = System.currentTimeMillis();
        String invokeInfo = createInvokeInfo(paramTypes, service, method, startTime, endTime);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            if(result!=null)outputStream.write(result);
        }finally {
            try{outputStream.flush();}catch (Exception e){}
            try{outputStream.close();}catch (Exception e){}
        }
        logger.info(invokeInfo);
    }

    private String createInvokeInfo(Class[] paramTypes, String service, String method, long startTime, long endTime) {
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder("End invoke=");
            stringBuilder.append(service).append(".").append(method).append("()=").append(endTime - startTime).append("ms");
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }


}
