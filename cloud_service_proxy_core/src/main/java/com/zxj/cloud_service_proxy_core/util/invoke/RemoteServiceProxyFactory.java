package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import com.zxj.fast_io_core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public static final Map<String, String> remoteServiceMethodMap = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(RemoteServiceProxyFactory.class);
    private LoadBalancerClient loadBalancerClient = null;
    private static final Map<String,WebClient> webClientMap=new HashMap<>();

    public static <T> T newInstance(LoadBalancerClient loadBalancerClient, String remoteServiceMethod, Class<T> tClass) {
        Class[] interfaces = new Class[]{tClass};
        remoteServiceMethodMap.put(tClass.getName(), remoteServiceMethod);
        return (T) Proxy.newProxyInstance(RemoteServiceProxyFactory.class.getClassLoader(), interfaces,
                new RemoteServiceProxyFactory().setLoadBalancerClient(loadBalancerClient));
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
        if (loadBalancerClient == null)
            throw new ServiceException("WebClient can not be null!");
        try {
            logger.info("request service:" + serviceName + "." + methodName);
            String remoteServiceMethod = getRemoteServiceMethod(serviceName);
            ServiceInstance serviceInstance = loadBalancerClient.choose(remoteServiceMethod);
            String remoteUrl = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort() + "/" + remoteServiceMethod;
            WebClient webClient=getFromMap(remoteUrl);
            Mono<byte[]> monoBytes=webClient.post().body(BodyInserters.fromObject(bytes)).retrieve().bodyToMono(byte[].class);
            byte[] bytesResult=monoBytes.block();

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

    private WebClient getFromMap(String remoteUrl) {
        WebClient webClient= webClientMap.get(remoteUrl);
        if(webClient==null){
            webClient=WebClient.create(remoteUrl);
            webClientMap.put(remoteUrl,webClient);
        }
        return webClient;
    }

    private String getRemoteServiceMethod(String service) {
        String method = remoteServiceMethodMap.get(service);
        return method;
    }

    public RemoteServiceProxyFactory setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
        return this;
    }
}