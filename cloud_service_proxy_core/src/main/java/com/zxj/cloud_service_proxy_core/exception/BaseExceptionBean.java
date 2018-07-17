package com.zxj.cloud_service_proxy_core.exception;

/**
 * Created by zhuxiujie
 */
public class BaseExceptionBean implements BaseExceptionInterface {

    private Integer errCode;

    private String errMsg;

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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
