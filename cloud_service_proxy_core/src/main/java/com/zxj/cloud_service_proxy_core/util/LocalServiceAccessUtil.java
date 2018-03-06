package com.zxj.cloud_service_proxy_core.util;

import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.LocalServiceProxyUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.springframework.context.ApplicationContext;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class LocalServiceAccessUtil {



    public static Single<byte[]> access(ExecutorService executor, ApplicationContext applicationContext, byte[] finalBytes, final Logger logger) throws Throwable {
        Single<byte[]> observable = Single.create((Single.OnSubscribe<byte[]>) singleSubscriber -> {
            byte[] result = null;
            try {
                result = LocalServiceAccessUtil.access(applicationContext, finalBytes, logger);
                singleSubscriber.onSuccess(result);
            }catch (Exception e){
                logger.info("invokeError"+e.toString());
            } catch (Throwable throwable) {
                logger.info("invokeError"+throwable.toString());
            }
        }).subscribeOn(Schedulers.from(executor));
        return observable;
    }


    public static byte[] access(ApplicationContext applicationContext, byte[] bytes, Logger logger) throws Throwable {
        if (bytes == null) {
            throw new ServiceRuntimeException("bytes can not be null!");
        }
        logger.info("bytesLength:" + bytes.length);
        ServiceDTO serviceDTO = (ServiceDTO) SerializeUtil.deserialize(bytes);
        if (serviceDTO == null) {
            throw new ServiceRuntimeException("deserialize fail! serviceDTO=null!");
        }
        Object[] params = serviceDTO.getParams();
        Class[] paramTypes = serviceDTO.getParamsTypes();
        String method = serviceDTO.getMethod();
        String service = serviceDTO.getService();

        long startTime = System.currentTimeMillis();
        logger.info("method:" + method);
        logger.info("service:" + service);

        Object serviceResult = LocalServiceProxyUtil.invoke(params, method, service, paramTypes, applicationContext);
        byte[] result = null;
        if (serviceResult != null) result = SerializeUtil.serialize(serviceResult);
        long endTime = System.currentTimeMillis();
        String invokeInfo = createInvokeInfo(service, method, startTime, endTime);
        logger.info(invokeInfo);
        return result;
    }

    private static String createInvokeInfo(String service, String method, long startTime, long endTime) {
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder("End invoke=");
            stringBuilder.append(service).append(".").append(method).append("()=").append(endTime - startTime).append("ms");
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    public interface Logger {
        void info(String info);
    }
}
