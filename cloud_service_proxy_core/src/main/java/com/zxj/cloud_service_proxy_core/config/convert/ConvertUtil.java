package com.zxj.cloud_service_proxy_core.config.convert;


import com.alibaba.fastjson.JSON;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;
import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

public class ConvertUtil {


    private static Decoder decoder;
    private static Encoder encoder;
    private static FSTConfiguration fstConfiguration = FSTConfiguration.createDefaultConfiguration();


    public static Decoder getDecoder() {
        if(decoder==null)decoder= (row, clazz) -> JSON.parseObject(row,clazz);
        return decoder;
    }

    public static Encoder getEncoder() {
       if(encoder==null)encoder= object -> JSON.toJSONString(object);
        return encoder;
    }

    public static void main(String[] args) throws IOException {

//        ServiceDTO serviceDTO=new ServiceDTO();
//        serviceDTO.setService("adsfsadfas");
//        byte[] bytes= getEncoder().encoder(serviceDTO);
//        ServiceDTO b= (ServiceDTO) getDecoder().decoder(bytes,ServiceDTO.class);
//
//        System.out.println(b.getService());
    }
}
