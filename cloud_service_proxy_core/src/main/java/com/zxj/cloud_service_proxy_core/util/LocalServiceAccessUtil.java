package com.zxj.cloud_service_proxy_core.util;

import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.LocalServiceProxyUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeStringUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.springframework.context.ApplicationContext;

import java.io.InputStream;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class LocalServiceAccessUtil {



    public static byte[] access(ApplicationContext applicationContext, InputStream inputStream, Logger logger) throws Throwable {
        byte[] bytes = null;
        try {
            bytes = SerializeStringUtil.input2byte(inputStream);
        } finally {
            //TODO clear stream
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
        if (bytes == null){throw new ServiceRuntimeException("input2byte fail! bytes=null!");}
        return access(applicationContext,bytes,logger);
    }


    public static byte[] access(ApplicationContext applicationContext, byte[] bytes, Logger logger) throws Throwable {
        if (bytes == null){throw new ServiceRuntimeException("bytes can not be null!");}
        logger.info("bytesLength:" + bytes.length);
        ServiceDTO serviceDTO = (ServiceDTO) SerializeStringUtil.deserialize(bytes);
        if (serviceDTO == null){throw new ServiceRuntimeException("deserialize fail! serviceDTO=null!");}
        Object[] params = serviceDTO.getParams();
        Class[] paramTypes = serviceDTO.getParamsTypes();
        String method = serviceDTO.getMethod();
        String service = serviceDTO.getService();

        long startTime = System.currentTimeMillis();
        logger.info("method:" + method);
        logger.info("service:" + service);

        Object serviceResult = LocalServiceProxyUtil.invoke(params, method, service, paramTypes, applicationContext);
        byte[] result = null;
        if (serviceResult != null) result = SerializeStringUtil.serialize(serviceResult);
        long endTime = System.currentTimeMillis();
        String invokeInfo = createInvokeInfo(paramTypes, service, method, startTime, endTime);
        logger.info(invokeInfo);
        return result;
    }

    private static String createInvokeInfo(Class[] paramTypes, String service, String method, long startTime, long endTime) {
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder("End invoke=");
            stringBuilder.append(service).append(".").append(method).append("()=").append(endTime - startTime).append("ms");
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    public static interface Logger {
        public void info(String info);
    }
}
