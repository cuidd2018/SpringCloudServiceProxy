package com.zxj.cloud_service_proxy_core.exception;

import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.constant.Constant;

/**
 * 普通业务异常
 *
 * @author zhuxiujie
 * @since 2016年8月11日 上午10:21:19
 */
public class ServiceException extends Exception implements BaseExceptionInterface {

    private static final long serialVersionUID = -6166718557157998012L;

    private Integer errCode;

    private String errMsg;

    private Integer runtime = 0;

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    @Override
    public Integer getRuntime() {
        return runtime;
    }

    public ServiceException(IntEnumConstant serviceErrorCode) {
        this(serviceErrorCode.getValue(), serviceErrorCode.getName());
    }

    public ServiceException(IntEnumConstant serviceErrorCode, String errMsg) {
        this(serviceErrorCode.getValue(), serviceErrorCode.getName() + errMsg);
    }

    public ServiceException(BaseExceptionInterface e) {
        this(e.getErrCode(), e.getErrMsg());
    }

    public ServiceException(String info) {
        this(ServiceProxyErrorCode.table.ERROR.getValue(), info);
    }

    public ServiceException(Object... infos) {
        this(getErrorCode(infos[0]), getInfos(infos));
    }

    public ServiceException(IntEnumConstant serviceErrorCode, Object... info) {
        this(serviceErrorCode, getInfos(info));
    }


    public ServiceException(Throwable e) {
        this(getErrorCode(e), getExceptionInfo(e));
    }

    public ServiceException(Throwable e, String extraInfo) {
        this(getErrorCode(e), extraInfo + getExceptionInfo(e));
    }

    public ServiceException(Exception e) {
        this(getErrorCode(e), getExceptionInfo(e));
    }

    public ServiceException(Exception e, String extraInfo) {
        this(getErrorCode(e), extraInfo + getExceptionInfo(e));
    }


    public ServiceException(String extraInfo, Throwable e) {
        this(getErrorCode(e), extraInfo + getExceptionInfo(e));
    }

    private static String getExceptionInfo(Object e) {
        if (e != null && e instanceof BaseExceptionInterface) {
            return ((BaseExceptionInterface) e).getErrMsg();
        } else if (e != null && e instanceof Constant) {
            return ((Constant) e).getName();
        } else {
            return e.toString();
        }
    }

    private static Integer getErrorCode(Object e) {
        if (e != null && e instanceof BaseExceptionInterface) {
            return ((BaseExceptionInterface) e).getErrCode();
        } else if (e != null && e instanceof IntEnumConstant) {
            return ((IntEnumConstant) e).getValue();
        } else {
            return ServiceProxyErrorCode.table.ERROR.getValue();
        }
    }


    private static String getInfos(Object[] info) {
        if (info != null && info.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object object : info) {
                if(object !=null) {
                    stringBuilder.append(getExceptionInfo(object));
                }
            }
            return stringBuilder.toString();
        }
        return "错误";
    }


    public ServiceException(Integer errCode, String errMsg) {
        super(String.format("errCode: %d, errMsg: %s", errCode, errMsg));
        this.setErrCode(errCode);
        this.setErrMsg(errMsg);
        this.setRuntime(0);
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