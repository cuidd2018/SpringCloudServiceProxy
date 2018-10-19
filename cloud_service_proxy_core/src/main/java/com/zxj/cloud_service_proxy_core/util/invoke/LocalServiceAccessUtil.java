package com.zxj.cloud_service_proxy_core.util.invoke;

import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceRspDTO;
import org.springframework.context.ApplicationContext;

import com.zxj.cloud_service_proxy_core.config.ProxyCoreConfig;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceReqDTO;
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
            throw new ServiceRuntimeException("lost arg! arg=null!");
        }
        Mono<String> mono = Mono.create(tringMonoSink -> {
            String result = null;
            long startTime = System.currentTimeMillis();
            ServiceReqDTO serviceDTO = null;
            ServiceRspDTO serviceRspDTO = new ServiceRspDTO();
            try {
                serviceDTO = LocalServiceAccessUtil.deserialize(arg);
                serviceRspDTO.setSuccess(1);
                result = LocalServiceAccessUtil.access(applicationContext, serviceDTO, serviceRspDTO, logger, startTime);
                tringMonoSink.success(result);
            } catch (Exception e) {
                logger.error("全局错误", e);
                StringBuffer jsonStrBuffer = new StringBuffer();
                try {
                    Object ex = ExceptionCheckOutUtil.checkOut(e, jsonStrBuffer);
                    serviceRspDTO.setSuccess(0);
                    tringMonoSink.success(LocalServiceAccessUtil.buildByteResult(serviceRspDTO, null, ex));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Throwable throwable) {
                logger.info("invokeError" + throwable.toString());
                logger.error("全局错误", new Exception(throwable));
                StringBuffer jsonStrBuffer = new StringBuffer();
                try {
                    Object ex = ExceptionCheckOutUtil.checkOut(new Exception(throwable), jsonStrBuffer);
                    serviceRspDTO.setSuccess(0);
                    tringMonoSink.success(LocalServiceAccessUtil.buildByteResult(serviceRspDTO, null, ex));
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
    public static String access(ApplicationContext applicationContext, ServiceReqDTO serviceDTO, ServiceRspDTO serviceRspDTO, Logger logger, long startTime) throws Throwable {
        if (serviceDTO == null) {
            throw new ServiceRuntimeException("deserialize fail! serviceDTO=null!");
        }
        String service = serviceDTO.getService();
        Class serviceClass = LocalServiceProxyUtil.getClassFromService(service);
        String method = serviceDTO.getMethod();

        Class[] paramTypes = getClassTypes(serviceClass, method);

        Object[] params = createObjectArg(serviceDTO.getArgs(), methodParamTypeFilter(serviceClass.getMethod(method, paramTypes)), logger);

        logger.info("method:" + method);
        logger.info("service:" + service);
        Object serviceResult = LocalServiceProxyUtil.invoke(params, method, service, paramTypes, applicationContext);
        String result = buildByteResult(serviceRspDTO, serviceResult, null);
        long endTime = System.currentTimeMillis();
        String invokeInfo = createInvokeInfo(service, method, startTime, endTime);
        logger.info(invokeInfo);
        return result;
    }

    private static Class[] getClassTypes(Class cla, String method) {
        if (cla == null || method == null) return null;
        Method[] methods = cla.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(method)) {
                return methods[i].getParameterTypes();
            }
        }
        return new Class[0];
    }

    /**
     * find class from className
     *
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private static Class filterClass(String className) throws ClassNotFoundException {
        if (className.equals("int")) {
            return int.class;
        } else if (className.equals("boolean")) {
            return boolean.class;
        } else if (className.equals("byte")) {
            return byte.class;
        } else if (className.equals("char")) {
            return char.class;
        } else if (className.equals("short")) {
            return short.class;
        } else if (className.equals("float")) {
            return float.class;
        } else if (className.equals("long")) {
            return long.class;
        } else if (className.equals("double")) {
            return double.class;
        } else {
            return Class.forName(className);
        }
    }

    private static Type[] methodParamTypeFilter(Method method) {
        AnnotatedType[] annotationTypes = method.getAnnotatedParameterTypes();
        Type[] types = new Type[annotationTypes.length];
        int i = 0;
        for (AnnotatedType annotationType : annotationTypes) {
            types[i] = annotationType.getType();
            i++;
        }
        return types;
    }


    private static Object[] createObjectArg(String[] params, Type[] paramTypes, Logger logger) {
        if (params == null || paramTypes == null || params.length == 0 || paramTypes.length == 0)
            return null;
        Object[] objects = new Object[paramTypes.length];
        for (int i = 0; i < params.length; i++) {
            try {
                objects[i] = ProxyCoreConfig.getSingleton().getDecoder().decoder(params[i], paramTypes[i]);
            } catch (Exception e) {
                logger.error("全局错误", e);
                e.printStackTrace();
            }
        }
        return objects;
    }

    private static String buildByteResult(ServiceRspDTO serviceRspDTO, Object serviceResult, Object error) throws IOException {
        if (serviceResult != null) {
            serviceRspDTO.setResult(ProxyCoreConfig.getSingleton().getEncoder().encoder(serviceResult));
        }
        if (error != null) {
            serviceRspDTO.setError(ProxyCoreConfig.getSingleton().getEncoder().encoder(error));
        }
        String result = SerializeUtil.serialize(serviceRspDTO);
        return result;
    }

    public static ServiceReqDTO deserialize(String row) throws Throwable {
        if (row == null) {
            throw new ServiceRuntimeException("rowString can not be null!");
        }
        ServiceReqDTO serviceDTO = (ServiceReqDTO) SerializeUtil.deserialize(row, ServiceReqDTO.class);
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
