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
        HessianOutput ho = new HessianOutput(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    public static Object deserialize(byte[] by) throws IOException{
        if(by==null) throw new NullPointerException();
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        return hi.readObject();
    }


    public static String serializeToByteString(Object obj)  {
        if(obj==null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HessianOutput ho = new HessianOutput(os);
            ho.writeObject(obj);
            String  result = new String(os.toByteArray(),CHARTSET_FILE);
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
