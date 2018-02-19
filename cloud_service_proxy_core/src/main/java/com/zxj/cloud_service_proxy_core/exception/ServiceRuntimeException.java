package com.zxj.cloud_service_proxy_core.exception;


import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.variable.IntEnumVariable;

/**
 * 运行时异常，请注意抛出位置，会触发事务回滚！！！！！！！！！
 * Created by ce on 2017/4/14.
 */
public class ServiceRuntimeException extends RuntimeException implements BaseExceptionInterface{


    private static final long serialVersionUID = -6509426443798876274L;

    private Integer				errCode;

    private String				errMsg;

    public ServiceRuntimeException(IntEnumVariable serviceErrorCode){
        this(serviceErrorCode.getValue(),serviceErrorCode.getName());
    }

    public ServiceRuntimeException(IntEnumVariable serviceErrorCode, String errMsg){
        this(serviceErrorCode.getValue(),serviceErrorCode.getName()+errMsg);
    }

    public ServiceRuntimeException(String errMsg) {
        this(ServiceProxyErrorCode.ERROR.getValue(), errMsg);
        this.setErrCode(ServiceProxyErrorCode.ERROR.getValue());
    }

    public ServiceRuntimeException(Integer errCode, String errMsg) {
        super(String.format("errCode: %d, errMsg: %s", errCode, errMsg));
        this.setErrCode(errCode);
        this.setErrMsg(errMsg);
    }

    public ServiceRuntimeException(Object... info) {
        this(ServiceProxyErrorCode.ERROR.getValue(), getInfos(info));
    }

    public ServiceRuntimeException(IntEnumVariable serviceErrorCode, Object... info) {
        this(serviceErrorCode, getInfos(info));
    }

    private static String getInfos(Object[] info) {
        if(info!=null&&info.length!=0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object _info:info){
                stringBuilder.append(_info);
            }
            return stringBuilder.toString();
        }
        return "错误";
    }


    @Override
    public Integer getErrCode() {
        return errCode;
    }
    @Override
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
