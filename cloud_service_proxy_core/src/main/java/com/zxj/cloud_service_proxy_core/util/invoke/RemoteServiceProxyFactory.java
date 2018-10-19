package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.config.ProxyCoreConfig;
import com.zxj.cloud_service_proxy_core.exception.BaseExceptionBean;
import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceReqDTO;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceRspDTO;
import com.zxj.fast_io_core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
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
    private static final Map<String, WebClient> webClientMap = new HashMap<>();

    public static <T> T newInstance(LoadBalancerClient loadBalancerClient, String remoteServiceMethod, Class<T> tClass) {
        Class[] interfaces = new Class[]{tClass};
        remoteServiceMethodMap.put(tClass.getName(), remoteServiceMethod);
        return (T) Proxy.newProxyInstance(RemoteServiceProxyFactory.class.getClassLoader(), interfaces,
                new RemoteServiceProxyFactory().setLoadBalancerClient(loadBalancerClient));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        String serviceName = (String) BeanUtils.getProperty(BeanUtils.getProperty(method, "clazz"), "name");
        ServiceReqDTO serviceDTO = new ServiceReqDTO();
        serviceDTO.setMethod(methodName);
        serviceDTO.setService(serviceName);
        serviceDTO.setArgs(toJsonArray(args));
        String jsons = SerializeUtil.serialize(serviceDTO);
        if (loadBalancerClient == null)
            throw new ServiceException("WebClient can not be null!");
        try {
            logger.info("request service:" + serviceName + "." + methodName);
            String remoteServiceMethod = getRemoteServiceMethod(serviceName);
            ServiceInstance serviceInstance = loadBalancerClient.choose(remoteServiceMethod);
            if (serviceInstance == null) throw new ServiceRuntimeException("no service instance ailviliable!");
            String remoteUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + remoteServiceMethod;
            WebClient webClient = getFromMap(remoteUrl);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("arg", jsons);
            Mono<String> monoBytes = webClient.post().body(BodyInserters.fromObject(formData)).retrieve().bodyToMono(String.class);
            String bytesResult = monoBytes.block();
            if (bytesResult == null)
                return null;
            ServiceRspDTO serviceRspDTO = (ServiceRspDTO) SerializeUtil.deserialize(bytesResult, ServiceRspDTO.class);
            if (serviceRspDTO.getSuccess() == 1) {
                String jsonResult = serviceRspDTO.getResult();
                Object result = ProxyCoreConfig.getSingleton().getDecoder().decoder(jsonResult, method.getGenericReturnType());
                return result;
            } else {
                String jsonError = serviceRspDTO.getError();
                Object error = ProxyCoreConfig.getSingleton().getDecoder().decoder(jsonError, BaseExceptionBean.class);
                if (error instanceof BaseExceptionInterface) {
                    ServiceException s;
                    if (error instanceof ServiceException) {
                        s = (ServiceException) error;
                        throw new ServiceRuntimeException(s.getErrCode(), s.getErrMsg());
                    } else if (error instanceof ServiceRuntimeException) {
                        throw (ServiceRuntimeException) error;
                    } else if (error instanceof BaseExceptionBean) {
                        ServiceRuntimeException serviceRuntimeException = new ServiceRuntimeException(((BaseExceptionBean) error).getErrCode(), ((BaseExceptionBean) error).getErrMsg());
                        throw serviceRuntimeException;
                    } else {
                        throw new ServiceRuntimeException(error);
                    }
                } else {
                    throw new ServiceRuntimeException(jsonError);
                }
            }
        } catch (Exception e) {
            logger.error("远程代理服务" + serviceName + "调用失败", e);
            throw e;
        }
    }

    private String[] toParamTypeJson(Class[] args) {
        if (args == null || args.length == 0) return null;
        String[] strings = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                strings[i] = args[i].getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strings;
    }

    private String[] toJsonArray(Object[] args) {
        if (args == null || args.length == 0) return null;
        String[] strings = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                strings[i] = ProxyCoreConfig.getSingleton().getEncoder().encoder(args[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }

    private WebClient getFromMap(String remoteUrl) {
        WebClient webClient = webClientMap.get(remoteUrl);
        if (webClient == null) {
            webClient = WebClient.create(remoteUrl);
            webClientMap.put(remoteUrl, webClient);
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