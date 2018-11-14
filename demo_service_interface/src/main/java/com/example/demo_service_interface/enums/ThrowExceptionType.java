package com.example.demo_service_interface.enums;

import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.util.enums.EnableScanEnumTable;
import com.zxj.cloud_service_proxy_core.util.enums.EnumUtils;

public class ThrowExceptionType implements IntEnumConstant {

    public static class ThrowExceptionTypeTable {
        public ThrowExceptionType THROW_EXCEPTION = new ThrowExceptionType(1, "抛出一个自定义的异常");
        public ThrowExceptionType NOT_THROW = new ThrowExceptionType(0, "不抛异常");
    }

    @EnableScanEnumTable
    public static ThrowExceptionTypeTable table = new ThrowExceptionTypeTable();

    private String name;
    private int value;

    public ThrowExceptionType() {
    }

    public  ThrowExceptionType(int value, String name) {
        this.name = name;
        this.value = value;
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
        this.name = name;
    }

    @Deprecated
    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    public static ThrowExceptionType valueOf(int exception) throws ServiceException {
        return EnumUtils.toEnum(exception, ThrowExceptionType.class);
    }
}
