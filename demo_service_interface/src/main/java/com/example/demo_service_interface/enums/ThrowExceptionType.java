package com.example.demo_service_interface.enums;

import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;

public enum  ThrowExceptionType implements IntEnumConstant {
    THROW_EXCEPTION(1,"抛出一个自定义的异常"),
    NOT_THROW(0,"不抛异常");

    private String name;
    private int value;


    ThrowExceptionType(int value, String name) {
       this.name=name;
       this.value=value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Deprecated
    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Deprecated
    @Override
    public void setValue(Integer value) {
        this.value=value;
    }

    public static ThrowExceptionType valueOf(int exception) throws ServiceException {
        return EnumUtils.toEnum(exception,ThrowExceptionType.class);
    }
}
