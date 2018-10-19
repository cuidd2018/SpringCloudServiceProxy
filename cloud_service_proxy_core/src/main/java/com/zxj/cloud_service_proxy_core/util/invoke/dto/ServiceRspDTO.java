package com.zxj.cloud_service_proxy_core.util.invoke.dto;

import java.io.Serializable;

/**
 * 服务远程调用数据传输对象
 *
 * @author zhuxiujie
 * @since 2017/12/17
 */
public class ServiceRspDTO implements Serializable {
    private static final long serialVersionUID = -4245432082917548131L;

    private Integer success;
    private String result;//结果类型
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
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
