package com.zxj.cloud_service_proxy_core.util.invoke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * 本地服务代理调用工具
 * @author zhuxiujie
 * @since 2017/12/17
 */
public class LocalServiceProxyUtil {

    private static Logger logger= LoggerFactory.getLogger(LocalServiceProxyUtil.class);

    public static Object invoke(List<Object> paramsList,String method,String service,Class[] parsmTypes,ApplicationContext applicationContext) throws Throwable {
        Class serviceClass = Class.forName(service);
        Object serviceBean = getServiceBean(serviceClass,applicationContext);
        Method targetMethod = getMethod(serviceClass, method, parsmTypes);
        Class[] paramTypes = targetMethod.getParameterTypes();
        try {
            Object serviceResult = BeanUtils.innvoke(serviceBean, method, paramTypes, paramsList.toArray());
            return serviceResult;
        }catch (InvocationTargetException ex){
            logger.error("本地代理服务调用失败",ex.getCause());
            throw  ex.getTargetException();
        }
    }

    private static Method getMethod(Class cla, String methodName, Class[] parsmTypes) throws NoSuchMethodException {
       Method method = cla.getMethod(methodName,parsmTypes);
       return method;
    }

    private static Object getServiceBean(Class serviceClass, ApplicationContext applicationContext) {
       return applicationContext.getBean(serviceClass);
    }

}
