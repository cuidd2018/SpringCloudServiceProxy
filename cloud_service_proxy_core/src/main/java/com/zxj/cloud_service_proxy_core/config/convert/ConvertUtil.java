package com.zxj.cloud_service_proxy_core.config.convert;


import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;
import com.zxj.cloud_service_proxy_core.util.invoke.dto.ServiceDTO;
import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

public class ConvertUtil {


    private static Decoder decoder;
    private static Encoder encoder;
    private static FSTConfiguration fstConfiguration = FSTConfiguration.createDefaultConfiguration();


    public static Decoder getDecoder() {
        if(decoder==null)decoder= (bytes, clazz) -> fstConfiguration.asObject(bytes);
        return decoder;
    }

    public static Encoder getEncoder() {
       if(encoder==null){
           encoder= object -> fstConfiguration.asByteArray(object);
       }
        return encoder;
    }

    public static void main(String[] args) throws IOException {

        ServiceDTO serviceDTO=new ServiceDTO();
        serviceDTO.setService("adsfsadfas");
        byte[] bytes= getEncoder().encoder(serviceDTO);
        ServiceDTO b= (ServiceDTO) getDecoder().decoder(bytes,ServiceDTO.class);

        System.out.println(b.getService());
    }
}
