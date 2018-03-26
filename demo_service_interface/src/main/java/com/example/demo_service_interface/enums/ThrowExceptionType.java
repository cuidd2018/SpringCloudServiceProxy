package com.example.demo_service_interface.enums;

import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;

public enum  ThrowExceptionType implements IntEnumConstant {
    THROW_EXCEPTION(1,"抛出异常"),
    NOT_THROW(0,"不抛异常");


    private String name;
    private int value;
    ThrowExceptionType(int value, String name) {
       setName(name);
       setValue(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Constant<Integer> setName(String name) {
        this.name=name;
        return this;
    }

    @Override
    public Constant<Integer> setValue(Integer value) {
        this.value=value;
        return this;
    }

    public static ThrowExceptionType valueOf(int exception) throws ServiceException {
        return EnumUtils.enumValueOf(exception,ThrowExceptionType.class);
    }
}
