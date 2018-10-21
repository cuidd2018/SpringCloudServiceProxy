package com.zxj.cloud_service_proxy_core.util.convert;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.constant.StringEnumConstant;

import java.io.IOException;
import java.lang.reflect.Method;

public class EnumTypeAdapter<T> extends TypeAdapter<T> {
    @Override
    public T read(JsonReader in) throws IOException {
        if(in.peek() == JsonToken.NULL){
            in.nextNull();
            return null;
        }
        return null;
    }

    public Object read(JsonReader in, TypeToken<T> type) throws IOException{
        boolean isEnum = type.getRawType().isEnum();
        if(isEnum){
            int value = in.nextInt();
            try {
                Method valuesMethod = type.getRawType().getMethod("values", null);
                IntEnumConstant[] enumArr = (IntEnumConstant[])valuesMethod.invoke(type.getClass(), null);
                for (IntEnumConstant iEnum : enumArr) {
                    if(iEnum.getValue() == value){
                        System.out.println( "value is=====>"+value);
                        return iEnum;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return null;
    }


    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if(value == null){
            out.nullValue();
            return;
        }
        if(value instanceof IntEnumConstant){
            out.jsonValue("{\"name\":\""+((IntEnumConstant)value).getName()+"\",\"value\":\""+((IntEnumConstant)value).getValue()+"\"}");
        }
        if(value instanceof StringEnumConstant){
            out.jsonValue("{\"name\":\""+((StringEnumConstant)value).getName()+"\",\"value\":\""+((StringEnumConstant)value).getValue()+"\"}");
        }
    }
}