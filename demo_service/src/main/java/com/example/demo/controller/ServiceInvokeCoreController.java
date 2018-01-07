package com.example.demo.controller;


import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public byte[] everything(ServletRequest request, ServletResponse response,@RequestParam("dto") byte[] dto) throws Throwable {
        //String dto = request.getParameter("dto");
        if (dto == null) throw new ServiceRuntimeException("dto 参数异常！dto=" + dto);
        ServiceDTO serviceDTO = (ServiceDTO) SerializeStringUtil.deserialize(dto);

        List<Object> paramsList = serviceDTO.getParams();
        Class[] paramTypes = serviceDTO.getParamsTypes();
        String method = serviceDTO.getMethod();
        String service = serviceDTO.getService();

        long startTime = System.currentTimeMillis();
        logger.info("method:" + method);
        logger.info("service:" + service);

        Object serviceResult = LocalServiceProxyUtil.invoke(paramsList, method, service, paramTypes, applicationContext);
        byte[] result = SerializeStringUtil.serialize(serviceResult);
        long endTime = System.currentTimeMillis();

        String invokeInfo = createInvokeInfo(paramTypes, service, method, startTime, endTime);
        logger.info(invokeInfo);
        return result;
    }

    private String createInvokeInfo(Class[] paramTypes, String service, String method, long startTime, long endTime) {
        StringBuilder stringBuilder=null;
        try {
            stringBuilder = new StringBuilder("End invoke=");
            stringBuilder.append(service).append(".").append(method).append("(");
            if (paramTypes != null && paramTypes.length != 0) {
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
        }catch (Exception e){
            return "";
        }
        return stringBuilder.toString();
    }


}
