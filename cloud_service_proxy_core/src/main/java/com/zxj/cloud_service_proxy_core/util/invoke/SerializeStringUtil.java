package com.zxj.cloud_service_proxy_core.util.invoke;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 序列化工具
 */
public class SerializeStringUtil {

	private  static Kryo kryo = new Kryo();

	public static byte[] serialize(Object obj) throws IOException {
		if (obj == null) return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		Output output = null;
		byte[] byteArray = null;
		
		try {
			kryo.register(obj.getClass(), new JavaSerializer());
			byteArrayOutputStream = new ByteArrayOutputStream();
			output = new Output(byteArrayOutputStream);
			kryo.writeObject(output, obj);
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
				output.flush();
			} catch (Exception e) {
			}
			try {
				output.close();
			} catch (Exception e) {
			}
		}
		
		return byteArray;
	}
	
	public static Object deserialize(byte[] by) throws IOException {
		if (by == null) return null;
        Object object = null;
        ByteArrayInputStream byteArrayInputStream=null;
		Input input=null;
        try {
			kryo.register(Object.class, new JavaSerializer());
            byteArrayInputStream = new ByteArrayInputStream(by);
			input=new Input(byteArrayInputStream);
			object=kryo.readObject(input,Object.class);
        }finally {
            try {
                byteArrayInputStream.close();
            } catch (Exception e) {
            }
            try {
                input.close();
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
