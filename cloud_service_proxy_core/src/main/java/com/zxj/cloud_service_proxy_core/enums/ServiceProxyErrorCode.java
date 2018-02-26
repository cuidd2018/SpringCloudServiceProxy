package com.zxj.cloud_service_proxy_core.enums;


import com.zxj.cloud_service_proxy_core.variable.IntEnumVariable;

/**
 * 汇集所有的服务异常，可增加，不要修改！
 * Created by zhuxiujie
 */
public enum ServiceProxyErrorCode implements IntEnumVariable {
    ERROR(-1, "错误:");

    Integer code = null;
    String info = null;

    ServiceProxyErrorCode(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    @Override
    public String getName() {
        return info;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
