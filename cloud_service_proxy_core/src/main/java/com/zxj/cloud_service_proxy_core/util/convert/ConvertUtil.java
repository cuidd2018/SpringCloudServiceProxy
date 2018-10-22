package com.zxj.cloud_service_proxy_core.util.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;

public class ConvertUtil {

    private static Gson gson = createGson();

    private static Gson createGson() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        return gson;
    }

    private static Decoder decoder;
    private static Encoder encoder;


    public static Decoder getDecoder() {
        if (decoder == null) decoder = (row, clazz) -> gson.fromJson(row, clazz);
        return decoder;
    }

    public static Encoder getEncoder() {
        if (encoder == null) encoder = object -> gson.toJson(object);
        return encoder;
    }

}
