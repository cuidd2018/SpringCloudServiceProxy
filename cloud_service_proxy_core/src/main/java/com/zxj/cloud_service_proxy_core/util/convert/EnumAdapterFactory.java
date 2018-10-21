package com.zxj.cloud_service_proxy_core.util.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.constant.StringEnumConstant;

import java.lang.reflect.Type;

public  class EnumAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (rawType.isEnum()) {
            Type[] types = rawType.getGenericInterfaces();
            for (Type item : types) {
                if (item == IntEnumConstant.class || item == StringEnumConstant.class) {
                    return new EnumTypeAdapter<T>();
                }
            }
        }
        return null;
    }
}