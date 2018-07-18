package com.zxj.cloud_service_proxy_core.util.convert;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;

import java.io.IOException;

public class ConvertUtil {


    private static Decoder decoder;
    private static Encoder encoder;

    public static Decoder getDecoder() {
        if(decoder==null)decoder= (row, clazz) -> JSON.parseObject(row,clazz,Feature.SupportArrayToBean);
        return decoder;
    }

    public static Encoder getEncoder() {
       if(encoder==null)encoder= object -> JSON.toJSONString(object);
        return encoder;
    }

    public static void main(String[] args) throws IOException {

        ServiceProxyErrorCode serviceDTO=ServiceProxyErrorCode.ERROR;

       String json=  getEncoder().encoder(serviceDTO);
        ServiceProxyErrorCode b= (ServiceProxyErrorCode) getDecoder().decoder(json,ServiceProxyErrorCode.class);

        System.out.println(b.getValue());
    }
}
