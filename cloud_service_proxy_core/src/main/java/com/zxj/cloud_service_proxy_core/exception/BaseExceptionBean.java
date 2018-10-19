package com.zxj.cloud_service_proxy_core.exception;

/**
 * Created by zhuxiujie
 */
public class BaseExceptionBean implements BaseExceptionInterface {

    private Integer errCode;

    private String errMsg;

    private Integer runtime;

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public Integer getRuntime() {
        return runtime;
    }

    @Override
    public Integer getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }
}
