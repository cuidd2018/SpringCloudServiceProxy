package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.fast_io_core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 本地服务代理调用工具
 *
 * @author zhuxiujie
 * @since 2017/12/17
 */
public class LocalServiceProxyUtil {

    private static Logger logger = LoggerFactory.getLogger(LocalServiceProxyUtil.class);

    private static Map<String,Class> classMap=new HashMap<>();

    public static Object invoke(Object[] params, String method, String service, Class[] parsmTypes, ApplicationContext applicationContext) throws Throwable {
        Class serviceClass = getClassFromService(service);
        Object serviceBean = getServiceBean(serviceClass, applicationContext);
        return invoke(params, method, service, parsmTypes, serviceBean);
    }


    public static Object invoke(Object[] params, String method, String service, Class[] parsmTypes, Object serviceBean) throws Throwable {
        Class serviceClass = getClassFromService(service);
        Method targetMethod = getMethod(serviceClass, method, parsmTypes);
        Class[] paramTypes = targetMethod.getParameterTypes();
        try {
            Object serviceResult = BeanUtils.invoke(serviceBean, method, paramTypes, params);
            return serviceResult;
        } catch (InvocationTargetException ex) {
            logger.error("本地代理服务调用失败", ex.getCause());
            throw ex.getTargetException();
        }
    }

    public static Class getClassFromService(String service) throws ClassNotFoundException {
        Class serviceClass=classMap.get(service);
        if (serviceClass==null){
            serviceClass=Class.forName(service);
            classMap.put(service,serviceClass);
        }
        return serviceClass;
    }

    private static Method getMethod(Class cla, String methodName, Class[] parsmTypes) throws NoSuchMethodException {
        Method method = cla.getMethod(methodName, parsmTypes);
        return method;
    }

    private static Object getServiceBean(Class serviceClass, ApplicationContext applicationContext) {
        return applicationContext.getBean(serviceClass);
    }

}
