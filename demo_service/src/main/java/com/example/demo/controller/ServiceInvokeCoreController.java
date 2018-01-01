package com.example.demo.controller;


import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.InvokeRemoteServiceURL;
import com.zxj.cloud_service_proxy_core.util.invoke.LocalServiceProxyUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeStringUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * 调用服务层核心控制器，请勿修改此处代码！！！
 */
@Controller
public class ServiceInvokeCoreController {

    private static Logger logger = LoggerFactory.getLogger(ServiceInvokeCoreController.class);

    @Resource
    private ApplicationContext applicationContext;


    @ResponseBody
    @RequestMapping("/" + InvokeRemoteServiceURL.SERVICE_EVEYY_THING)
    public String everything(ServletRequest request, ServletResponse response) throws Throwable {
        String dto = request.getParameter("dto");
        if (dto == null) throw new ServiceRuntimeException("dto 参数异常！dto=" + dto);
        ServiceDTO serviceDTO = SerializeStringUtil.deserializeByteStringToClass(dto);

        List<Object> paramsList = serviceDTO.getParams();
        Class[] paramTypes = serviceDTO.getParamsTypes();
        String method = serviceDTO.getMethod();
        String service = serviceDTO.getService();

        long startTime = System.currentTimeMillis();
        logger.info("method:" + method);
        logger.info("service:" + service);
        Object serviceResult = LocalServiceProxyUtil.invoke(paramsList, method, service, paramTypes, applicationContext);
        String result = SerializeStringUtil.serializeToByteString(serviceResult);
        long endTime = System.currentTimeMillis();

        String invokeInfo = createInvokeInfo(paramTypes, service, method, startTime, endTime);
        logger.info(invokeInfo);
        return result;
    }

    private String createInvokeInfo(Class[] paramTypes, String service, String method, long startTime, long endTime) {
        StringBuilder stringBuilder = new StringBuilder("End invoke=");
        stringBuilder.append(service).append(".").append(method).append("(");
        if (paramTypes != null) {
            int size = 0;
            for (Class paramType : paramTypes) {
                size++;
                stringBuilder.append(paramType.getSimpleName());
                if (size != paramTypes.length && size != 0) {
                    stringBuilder.append(",");
                }
            }
        }
        stringBuilder.append(")=").append(endTime - startTime).append("ms");
        return stringBuilder.toString();
    }


}
