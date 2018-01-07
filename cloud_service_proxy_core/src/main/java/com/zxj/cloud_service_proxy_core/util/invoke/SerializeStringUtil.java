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
        if (obj == null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(os);
        hessianOutput.writeObject(obj);
        byte[] byteArray = os.toByteArray();
        try {
            if (hessianOutput != null) {
                hessianOutput.close();
                hessianOutput = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static Object deserialize(byte[] by) throws IOException {
        if (by == null) throw new NullPointerException();
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        Object object = hi.readObject();
        try {
            if (hi != null) {
                hi.close();
                hi = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        try {
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
        } catch (Exception e) {
            return null;
        } finally {
            swapStream.close();
        }
    }

}
