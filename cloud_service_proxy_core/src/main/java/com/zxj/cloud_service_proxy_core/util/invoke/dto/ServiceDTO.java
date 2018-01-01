package com.zxj.cloud_service_proxy_core.util.invoke.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 服务远程调用数据传输对象
 * @author zhuxiujie
 * @since 2017/12/17
 */
public class ServiceDTO implements Serializable {

    private static final long serialVersionUID = 3650809103104022467L;

    private List<Object> params;
    private String method;
    private String service;
    private Class[] paramsTypes;

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
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

    public void setParamsTypes(Class[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public Class[] getParamsTypes() {
        return paramsTypes;
    }
}
