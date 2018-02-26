package com.zxj.cloud_service_proxy_core.vo;

import java.io.Serializable;

/**
 * Created by zhuxiujie on 2017/5/11.
 */
public class ConstantVO implements Serializable{
    String name;
    Object value;

    public void setValue(Object value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
