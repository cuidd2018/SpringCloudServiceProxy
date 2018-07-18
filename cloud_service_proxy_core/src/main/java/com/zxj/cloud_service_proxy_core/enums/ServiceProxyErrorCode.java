package com.zxj.cloud_service_proxy_core.enums;
import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;

/**
 * 汇集所有的服务异常，可增加，不要修改！
 * Created by zhuxiujie
 */
public class ServiceProxyErrorCode implements IntEnumConstant {
   public static ServiceProxyErrorCode ERROR = new ServiceProxyErrorCode(-1, "错误:");

    Integer value = null;
    String name = null;

    ServiceProxyErrorCode(){ }

    ServiceProxyErrorCode(Integer code, String info) {
        this.value = code;
        this.name = info;
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
    public void setName(String name) {
        this.name=name;
    }

    @Deprecated
    public void setValue(Integer value) {
        this.value=value;
    }
}
