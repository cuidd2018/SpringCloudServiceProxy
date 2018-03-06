package com.zxj.cloud_service_proxy_core.util.invoke;

import org.nustaq.serialization.FSTConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 序列化工具
 */
public class SerializeUtil {

	private  static FSTConfiguration fstConfiguration = FSTConfiguration.createDefaultConfiguration();

	public static byte[] serialize(Object obj) throws IOException {
		if (obj == null) return null;
		return fstConfiguration.asByteArray(obj);
	}
	
	public static Object deserialize(byte[] by) throws IOException {
		if (by == null) return null;
        return fstConfiguration.asObject(by);
	}
	
	public static final InputStream byte2Input(byte[] buf) {
	    if(buf==null)return null;
		return new ByteArrayInputStream(buf);
	}
	
	public static final byte[] input2byte(InputStream inStream) throws IOException {
	    if(inStream==null)return null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, len);
			}
			inStream.close();
			return byteArrayOutputStream.toByteArray();
		} finally {
			try{byteArrayOutputStream.flush();}catch (Exception e){}
			try{byteArrayOutputStream.close();}catch (Exception e){}
		}
	}

}
