package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 远程服务代理调用工具
 *
 * @author zhuxiujie
 * @since 2017/12/6
 */
public class RemoteServiceProxyFactory implements InvocationHandler {

    public static final Map<String, String> feignMethodMap = new HashMap<>();
    private static final Class<? extends byte[]> byteArrayClassType = getByteArrayClassType();
    private static Logger logger = LoggerFactory.getLogger(RemoteServiceProxyFactory.class);
    private static HttpHeaders httpHeaders = getHeaders();
    private RestTemplate restTemplate = null;

    private static Class<? extends byte[]> getByteArrayClassType() {
        byte[] bytes = "".getBytes();
        return bytes.getClass();
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return httpHeaders;
    }

    public static <T> T newInstance(RestTemplate restTemplate, String remoteServiceMethod, Class<T> tClass) {
        Class[] interfaces = new Class[]{tClass};
        feignMethodMap.put(tClass.getName(), remoteServiceMethod);
        return (T) Proxy.newProxyInstance(RemoteServiceProxyFactory.class.getClassLoader(), interfaces,
                new RemoteServiceProxyFactory().setRestTemplate(restTemplate));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class[] paramsTypes = method.getParameterTypes();
        String serviceName = (String) BeanUtils.getProperty(BeanUtils.getProperty(method, "clazz"), "name");
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setMethod(methodName);
        serviceDTO.setService(serviceName);
        serviceDTO.setParams(args);
        serviceDTO.setParamsTypes(paramsTypes);
        byte[] bytes = SerializeUtil.serialize(serviceDTO);
        if (restTemplate == null)
            throw new ServiceException("restTemplate can not be null!");
        try {
            logger.info("request service:" + serviceName + "." + methodName);
            logger.info("bytesLength:" + bytes.length);
            HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, httpHeaders);
            String remoteServiceName = getRemoteServiceName(serviceName);
            String remoteUrl = "http://" + remoteServiceName + "/" + remoteServiceName;
            ResponseEntity<? extends byte[]> response = restTemplate.exchange(remoteUrl, HttpMethod.PUT, httpEntity, byteArrayClassType);
            byte[] bytesResult = response.getBody();
            if (bytesResult == null)
                return null;
            Object result = SerializeUtil.deserialize(bytesResult);
            if (result instanceof BaseExceptionInterface) {
                ServiceException s;
                if (result instanceof ServiceException) {
                    s = (ServiceException) result;
                    throw new ServiceRuntimeException(s.getErrCode(), s.getErrMsg());
                } else if (result instanceof ServiceRuntimeException) {
                    throw (ServiceRuntimeException) result;
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("远程代理服务" + serviceName + "调用失败", e);
            throw e;
        }
    }

    private String getRemoteServiceName(String service) {
        String method = feignMethodMap.get(service);
        return method;
    }

    public RemoteServiceProxyFactory setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }
}