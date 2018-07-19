package com.zxj.cloud_service_proxy_core.vo;

import com.zxj.cloud_service_proxy_core.constant.Constant;

import java.io.Serializable;

/**
 * Created by zhuxiujie on 2017/5/11.
 */
public class ConstantVO<T> implements Serializable,Constant<T> {
    String name;
    T value;

    public void setValue(T value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
