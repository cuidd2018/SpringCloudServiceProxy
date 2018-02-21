package com.zxj.cloud_service_proxy_core.util.invoke;

import org.nustaq.serialization.FSTConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 序列化工具
 */
public class SerializeStringUtil {

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
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = inStream.read(buff, 0, 100)) > 0) {
				byteArrayOutputStream.write(buff, 0, rc);
			}
			byte[] in2b = byteArrayOutputStream.toByteArray();
			return in2b;
		} finally {
			try{byteArrayOutputStream.flush();}catch (Exception e){}
			try{byteArrayOutputStream.close();}catch (Exception e){}
		}
	}

}
