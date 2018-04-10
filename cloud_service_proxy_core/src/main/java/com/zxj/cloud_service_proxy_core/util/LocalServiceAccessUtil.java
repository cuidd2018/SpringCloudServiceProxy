package com.zxj.cloud_service_proxy_core.util;

import com.zxj.cloud_service_proxy_core.config.ProxyCoreConfig;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.ExceptionCheckOutUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.LocalServiceProxyUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.springframework.context.ApplicationContext;
import rx.Single;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class LocalServiceAccessUtil {


    /**
     * 异步访问
     * 使用RxJava 实现的异步访问
     *
     * @param applicationContext
     * @param inputStream
     * @param logger
     * @return
     * @throws Throwable
     */
    public static Single<byte[]> asyncAccess(ApplicationContext applicationContext, InputStream inputStream, final Logger logger) throws Throwable {
        byte[] bytes = null;
        try {
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
        return asyncAccess(applicationContext, bytes, logger);
    }

    /**
     * 异步访问
     * 使用RxJava 实现的异步访问
     *
     * @param applicationContext
     * @param finalBytes
     * @param logger
     * @return
     * @throws Throwable
     */
    public static Single<byte[]> asyncAccess(ApplicationContext applicationContext, byte[] finalBytes, final Logger logger) throws Throwable {
        Single<byte[]> observable = Single.create((Single.OnSubscribe<byte[]>) singleSubscriber -> {
            byte[] result = null;
            try {
                result = LocalServiceAccessUtil.access(applicationContext, finalBytes, logger);
                singleSubscriber.onSuccess(result);
            } catch (Exception e) {
                logger.error("全局错误", e);
                StringBuffer jsonStrBuffer = new StringBuffer();
                try {
                    byte[] bytes = ExceptionCheckOutUtil.checkOut(e, jsonStrBuffer);
                    singleSubscriber.onSuccess(bytes);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Throwable throwable) {
                logger.info("invokeError" + throwable.toString());
                logger.error("全局错误", new Exception(throwable));
                StringBuffer jsonStrBuffer = new StringBuffer();
                try {
                    byte[] bytes = ExceptionCheckOutUtil.checkOut(new Exception(throwable), jsonStrBuffer);
                    singleSubscriber.onSuccess(bytes);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.from(ProxyCoreConfig.getSingleton().getExecutorService()));
        return observable;
    }


    /**
     * 同步访问
     *
     * @param applicationContext
     * @param bytes
     * @param logger
     * @return
     * @throws Throwable
     */
    public static byte[] access(ApplicationContext applicationContext, byte[] bytes, Logger logger) throws Throwable {
        if (bytes == null) {
            throw new ServiceRuntimeException("bytes can not be null!");
        }
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

    /**
     * 创建服务访问日志
     *
     * @param service
     * @param method
     * @param startTime
     * @param endTime
     * @return
     */
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

    /**
     * 自定义日志接口
     */
    public interface Logger {
        void info(String info);

        void error(String error);

        void error(String info, Exception e);
    }
}
