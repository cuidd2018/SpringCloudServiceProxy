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

    private Integer success;
    private String[] params;//服务参数
    private String method;//方法
    private String service;//服务名称
    private String[] paramsTypes;//参数类型
    private String result;//结果类型

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
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

    public void setParamsTypes(String[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public String[] getParamsTypes() {
        return paramsTypes;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getSuccess() {
        return success;
    }
}
