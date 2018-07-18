package com.example.demo_service_interface.enums;

import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;

public class ThrowExceptionType implements IntEnumConstant {
    public static final ThrowExceptionType THROW_EXCEPTION=new ThrowExceptionType(1,"抛出一个自定义的异常");
    public static final ThrowExceptionType NOT_THROW = new ThrowExceptionType(0,"不抛异常");

    private String name;
    private int value;

    ThrowExceptionType(){}

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
    public Constant<Integer> setName(String name) {
        this.name=name;
        return this;
    }

    @Deprecated
    @Override
    public Constant<Integer> setValue(Integer value) {
        this.value=value;
        return this;
    }

    public static ThrowExceptionType valueOf(int exception) throws ServiceException {
        return EnumUtils.toEnum(exception,ThrowExceptionType.class);
    }
}
