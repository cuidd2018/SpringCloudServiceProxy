package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.config.ProxyCoreConfig;
import org.springframework.beans.BeanUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 序列化工具
 */
public class SerializeUtil {

    public static String serialize(Object obj) throws IOException {
        if (obj == null) return null;
        return ProxyCoreConfig.getSingleton().getEncoder().encoder(obj);
    }

    public static Object deserialize(String by,Class<?> clazz) throws IOException {
        if (by == null) return null;
        return ProxyCoreConfig.getSingleton().getDecoder().decoder(by,clazz);
    }


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        //com.zxj.fast_io_core.util.BeanUtils.setProperty();

        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }
}
