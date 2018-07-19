package com.zxj.cloud_service_proxy_core.util.convert;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

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

    public static void main(String[] args) throws IOException {

        Type type = new TypeToken<Map<String,ServiceDTO>>() {}.getType();


        ServiceDTO serviceDTO=new ServiceDTO();
        serviceDTO.setService("dsaf");

        Map<String,ServiceDTO> serviceDTOS=new HashedMap();
        serviceDTOS.put("f",serviceDTO);


       String json=  gson.toJson(serviceDTOS);
        Map<String,ServiceDTO> b= gson.fromJson(json,Map.class);

        String typejs=JSON.toJSONString(type);
        System.out.println(typejs);

        Type type1=serviceDTOS.getClass();

        b=gson.fromJson(json, type1);
        System.out.println(JSON.toJSONString(b));


    }


}
