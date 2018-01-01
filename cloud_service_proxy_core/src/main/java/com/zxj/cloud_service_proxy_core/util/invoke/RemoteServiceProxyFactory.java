package com.zxj.cloud_service_proxy_core.util.invoke;


import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程服务代理调用工具
 * @author zhuxiujie
 * @since 2017/12/6
 */
public class RemoteServiceProxyFactory implements InvocationHandler {

    private static Logger logger= LoggerFactory.getLogger(RemoteServiceProxyFactory.class);

    private RestTempleteProvider restTempleteProvider;

    public static final Map<String, String> feignMethodMap = new HashMap<>();


    public static <T> T newInstance(RestTempleteProvider provider, String remoteServiceMethod, Class<T> tClass) {
        Class[] interfaces = new Class[]{tClass};
        feignMethodMap.put(tClass.getName(), remoteServiceMethod);
        return (T) Proxy.newProxyInstance(RemoteServiceProxyFactory.class.getClassLoader(),
                interfaces, new RemoteServiceProxyFactory().setRestTempleteProvider(provider));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        List<Object> list = new ArrayList<>();
        Class[] paramsTypes = method.getParameterTypes();
        if (paramsTypes != null && paramsTypes.length != 0 && args != null) {
            for (Object o : args) {
                list.add(o);
            }
        }
        String _service = (String) BeanUtils.getProperty(BeanUtils.getProperty(method, "clazz"), "name");
        ServiceDTO feignDTO = new ServiceDTO();
        feignDTO.setMethod(methodName);
        feignDTO.setService(_service);
        feignDTO.setParams(list);
        feignDTO.setParamsTypes(paramsTypes);
        String dtoByteString = SerializeStringUtil.serializeToByteString(feignDTO);

        if(restTempleteProvider==null)throw new ServiceException("restTempleteProvider can not be null!");
        if(restTempleteProvider.getRestTemplete()==null)throw new ServiceException("can not restTempleteProvider.getRestTemplete()="+_service);
        try {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add("dto",dtoByteString);
            String object=((RestTemplate)restTempleteProvider.getRestTemplete()).postForObject("http://"+restTempleteProvider.service()+"/"+ getRemoteServiceMethod(_service),requestEntity,String.class);
            Object result = SerializeStringUtil.deserializeByteStringToClass(object);
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
            logger.error("远程代理服务"+_service+"调用失败",e);
           throw e;
        }
    }

    private String getRemoteServiceMethod(String service) {
        String method = feignMethodMap.get(service);
        return method;
    }

    public RemoteServiceProxyFactory setRestTempleteProvider(RestTempleteProvider restTempleteProvider) {
        this.restTempleteProvider = restTempleteProvider;
        return this;
    }
}