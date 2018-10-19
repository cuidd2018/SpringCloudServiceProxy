package com.zxj.cloud_service_proxy_core.util.invoke.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 服务远程调用数据传输对象
 * @author zhuxiujie
 * @since 2017/12/17
 */
public class ServiceReqDTO implements Serializable {

    private static final long serialVersionUID = 3650809103104022467L;

    private String[] args;//服务参数
    private String method;//方法
    private String service;//服务名称

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
