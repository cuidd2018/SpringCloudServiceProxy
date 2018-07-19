package com.zxj.cloud_service_proxy_core.util.convert;

import com.google.gson.Gson;
import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;

public class ConvertUtil {

    private  static Gson gson=new Gson();

    private static Decoder decoder;
    private static Encoder encoder;



    public static Decoder getDecoder() {
        if(decoder==null)decoder= (row, clazz) -> gson.fromJson(row, clazz);
        return decoder;
    }

    public static Encoder getEncoder() {
       if(encoder==null)encoder= object -> gson.toJson(object);
        return encoder;
    }

   public static void main(String[] args) throws Exception {
//
//        Type type = new TypeToken<Map<String,ServiceDTO>>() {}.getType();
//
//
//        ServiceDTO serviceDTO=new ServiceDTO();
//        serviceDTO.setService("dsaf");
//
//        Map<String,ServiceDTO> serviceDTOS=new HashedMap();
//        serviceDTOS.put("f",serviceDTO);
//
//
//       String json=  gson.toJson(serviceDTOS);
//        Map<String,ServiceDTO> b= gson.fromJson(json,Map.class);
//
//        String typejs=JSON.toJSONString(type);
//        System.out.println(typejs);
//
//        Type type1=serviceDTOS.getClass();
//
//        b=gson.fromJson(json, type1);
//        System.out.println(JSON.toJSONString(b));
        ServiceProxyErrorCode serviceProxyErrorCode=  EnumUtils.toEnum(-1,ServiceProxyErrorCode.class);
        System.out.println(getEncoder().encoder(serviceProxyErrorCode));
    }


}
