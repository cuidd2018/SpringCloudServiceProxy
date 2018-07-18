package com.zxj.cloud_service_proxy_core.util.invoke;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zxj.cloud_service_proxy_core.util.convert.ConvertUtil;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.zxj.cloud_service_proxy_core.config.ProxyCoreConfig;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import com.zxj.fast_io_core.util.ExtendBeanBuildUtil;

import reactor.core.publisher.Mono;

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
     * @param logger
     * @return
     * @throws Throwable
     */
    public static Mono<String> asyncMonoAccess(ApplicationContext applicationContext, String arg, final Logger logger) throws Throwable {
        if (arg == null) {
            throw new ServiceRuntimeException("fail! arg=null!");
        }
        Mono<String> mono = Mono.create(tringMonoSink -> {
            String result = null;
            long startTime = System.currentTimeMillis();
            ServiceDTO serviceDTO=null;
            try {
                serviceDTO=LocalServiceAccessUtil.deserialize(arg);
                serviceDTO.setSuccess(1);
                result = LocalServiceAccessUtil.access(applicationContext, serviceDTO, logger,startTime);
                tringMonoSink.success(result);
            } catch (Exception e) {
                logger.error("全局错误", e);
                StringBuffer jsonStrBuffer = new StringBuffer();
                try {
                    Object ex = ExceptionCheckOutUtil.checkOut(e, jsonStrBuffer);
                    serviceDTO.setSuccess(0);
                    tringMonoSink.success(LocalServiceAccessUtil.buildByteResult(serviceDTO,ex,startTime,logger));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Throwable throwable) {
                logger.info("invokeError" + throwable.toString());
                logger.error("全局错误", new Exception(throwable));
                StringBuffer jsonStrBuffer = new StringBuffer();
                try {
                    Object ex = ExceptionCheckOutUtil.checkOut(new Exception(throwable), jsonStrBuffer);
                    serviceDTO.setSuccess(0);
                    tringMonoSink.success(LocalServiceAccessUtil.buildByteResult(serviceDTO,ex,startTime,logger));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mono = mono.subscribeOn(reactor.core.scheduler.Schedulers.fromExecutor(ProxyCoreConfig.getSingleton().getExecutorService()));
        return mono;
    }



    /**
     * 同步访问
     *
     * @param applicationContext
     * @param serviceDTO
     * @param logger
     * @return
     * @throws Throwable
     */
    public static String access(ApplicationContext applicationContext, ServiceDTO serviceDTO, Logger logger, long startTime) throws Throwable {
        if (serviceDTO == null) {
            throw new ServiceRuntimeException("deserialize fail! serviceDTO=null!");
        }
        String[] params = serviceDTO.getParams();
        Class[] paramTypes = serviceDTO.getParamsTypes();
        String method = serviceDTO.getMethod();
        String service = serviceDTO.getService();

        Object[] objects = createObjectArg(params,paramTypes);

        logger.info("method:" + method);
        logger.info("service:" + service);

        Object serviceResult = LocalServiceProxyUtil.invoke(objects, method, service, paramTypes, applicationContext);
        return buildByteResult(serviceDTO,serviceResult,startTime,logger);
    }

    private static Object[] createObjectArg(String[] params,Class[] paramTypes ) {
		if (params == null || paramTypes == null || params.length == 0 || paramTypes.length == 0)
			return null;
		Object[] objects = new Object[paramTypes.length];
		for (int i = 0; i < params.length; i++) {
            try {
                objects[i] = ConvertUtil.getDecoder().decoder(params[i], paramTypes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return objects;
    }

    private static String buildByteResult(ServiceDTO serviceDTO, Object serviceResult, long startTime, Logger logger) throws IOException {
        ServiceDTO serviceResultDTO=new ServiceDTO();
        serviceResultDTO=ExtendBeanBuildUtil.buildChild(serviceDTO,ServiceDTO.class);
        serviceResultDTO.setResult(JSON.toJSONString(serviceResult));
        String result = null;
        if (serviceResult != null) result = SerializeUtil.serialize(serviceResultDTO);
        long endTime = System.currentTimeMillis();
        String invokeInfo = createInvokeInfo(serviceResultDTO.getService(), serviceDTO.getMethod(), startTime, endTime);
        logger.info(invokeInfo);
        return result;
    }

    public static ServiceDTO deserialize(String row) throws Throwable {
        if (row == null) {
            throw new ServiceRuntimeException("rowString can not be null!");
        }
        ServiceDTO serviceDTO = (ServiceDTO)SerializeUtil.deserialize(row,ServiceDTO.class);
        if (serviceDTO == null) {
            throw new ServiceRuntimeException("deserialize fail! serviceDTO=null!");
        }
       return serviceDTO;
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
