package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.config.ProxyCoreConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 序列化工具
 */
public class SerializeUtil {

    public static byte[] serialize(Object obj) throws IOException {
        if (obj == null) return null;
        return ProxyCoreConfig.getSingleton().getEncoder().encoder(obj);
    }

    public static Object deserialize(byte[] by,Class clazz) throws IOException {
        if (by == null) return null;
        return ProxyCoreConfig.getSingleton().getDecoder().decoder(by,clazz);
    }

    public static InputStream byte2Input(byte[] buf) {
        if (buf == null) return null;
        return new ByteArrayInputStream(buf);
    }

    public static byte[] input2byte(InputStream inStream) throws IOException {
        if (inStream == null) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            inStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.flush();
            } catch (Exception e) {
            }
            try {
                byteArrayOutputStream.close();
            } catch (Exception e) {
            }
            return bytes;
        } finally {
            try {
                byteArrayOutputStream.flush();
            } catch (Exception e) {
            }
            try {
                byteArrayOutputStream.close();
            } catch (Exception e) {
            }
        }
    }
}
