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
        if(obj==null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(os);
        hessianOutput.writeObject(obj);
        byte[] byteArray= os.toByteArray();
        try{
            if(hessianOutput!=null){
                hessianOutput.close();
                hessianOutput=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(os!=null){
                os.close();
                os=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return byteArray;
    }

    public static Object deserialize(byte[] by) throws IOException{
        if(by==null) throw new NullPointerException();
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        Object object= hi.readObject();
        try {
            if(hi!=null){
                hi.close();
                hi=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(is!=null){
                is.close();
                is=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }



}
