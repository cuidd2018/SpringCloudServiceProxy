package com.zxj.cloud_service_proxy_core.exception;


import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.variable.IntEnumVariable;

/**
 * 普通业务异常
 * 
 * @author zhuxiujie
 * @since 2016年8月11日 上午10:21:19
 */
public class ServiceException extends Exception implements BaseExceptionInterface{

	private static final long serialVersionUID = -6166718557157998012L;

	private Integer				errCode;

	private String				errMsg;


	public ServiceException(IntEnumVariable intEnumVariable){
		this(intEnumVariable.getValue(),intEnumVariable.getName());
	}
	public ServiceException(IntEnumVariable serviceErrorCode, String errMsg){
		this(serviceErrorCode.getValue(),serviceErrorCode.getName()+errMsg);
	}

	public ServiceException(String info) {
		this(ServiceProxyErrorCode.ERROR.getValue(), info);
	}

	public ServiceException(Object... info) {
		this(ServiceProxyErrorCode.ERROR.getValue(), getInfos(info));
	}

	public ServiceException(IntEnumVariable intEnumVariable, Object... info) {
		this(intEnumVariable, getInfos(info));
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

	public ServiceException(Integer errCode, String errMsg) {
		super(String.format("errCode: %d, errMsg: %s", errCode, errMsg));
		this.setErrCode(errCode);
		this.setErrMsg(errMsg);
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
