package com.zxj.cloud_service_proxy_core.util.invoke;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 序列化工具
 */
public class SerializeStringUtil {
	
	public static byte[] serialize(Object obj) throws IOException {
		if (obj == null) return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		HessianOutput hessianOutput = null;
		byte[] byteArray = null;
		
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			hessianOutput = new HessianOutput(byteArrayOutputStream);
			hessianOutput.writeObject(obj);
			byteArray = byteArrayOutputStream.toByteArray();
		} finally {
			try {
				byteArrayOutputStream.flush();
			} catch (Exception e) {
			}
			try {
				byteArrayOutputStream.close();
			} catch (Exception e) {
			}
			try {
				hessianOutput.flush();
			} catch (Exception e) {
			}
			try {
				hessianOutput.close();
			} catch (Exception e) {
			}
		}
		
		return byteArray;
	}
	
	public static Object deserialize(byte[] by) throws IOException {
		if (by == null) return null;
        Object object = null;
        ByteArrayInputStream byteArrayInputStream=null;
        HessianInput hessianInput=null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(by);
            hessianInput = new HessianInput(byteArrayInputStream);
            object = hessianInput.readObject();
        }finally {
            try {
                byteArrayInputStream.close();
            } catch (Exception e) {
            }
            try {
                hessianInput.close();
            } catch (Exception e) {
            }
        }
		return object;
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
