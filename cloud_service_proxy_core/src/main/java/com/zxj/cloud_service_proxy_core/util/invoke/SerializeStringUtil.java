package com.zxj.cloud_service_proxy_core.util.invoke;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SerializeStringUtil {

    public static final String CHARTSET_FILE="ISO-8859-1";

    public static byte[] serialize(Object obj) throws IOException {
        if(obj==null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(os);
        hessianOutput.writeObject(obj);
        byte[] byteArray= os.toByteArray();
        try{
            if(hessianOutput!=null)hessianOutput.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(os!=null)os.close();
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
            if(hi!=null)hi.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(is!=null)is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }


    public static String serializeToByteString(Object obj)  {
        if(obj==null) return null;
        try {
            byte[] bytes= serialize(obj);
            String  result = new String(bytes,CHARTSET_FILE);
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Object deserializeByteStringToObject(String byteString) {
        if(byteString==null) return null;
        try {
            byte[] bytes=byteString.getBytes(CHARTSET_FILE);
            Object object=deserialize(bytes);
            return object;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserializeByteStringToClass(String byteString) {
        T t= (T) deserializeByteStringToObject(byteString);
        return t;
    }



//    public static void main(String[] args){
//        Pageable pageable=new PageRequest(1,20);
//        UserVO userVO=new UserVO();
//        userVO.setId(123454);
//      String bytes=serializeToByteString(userVO);
//      UserVO userVO1=(UserVO)deserializeByteStringToObject(bytes);
//      System.out.println(userVO1.getId());
//    }
}
