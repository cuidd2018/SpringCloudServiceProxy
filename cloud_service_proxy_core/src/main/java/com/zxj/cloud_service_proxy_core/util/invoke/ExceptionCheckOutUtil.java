package com.zxj.cloud_service_proxy_core.util.invoke;

import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;

import java.io.IOException;

/**
 * @author zhuxiujie
 * @since 2018/2/19
 */

public class ExceptionCheckOutUtil {

    /**
     * 检出异常转换为byte[]
     * @param ex
     * @return
     * @throws IOException
     */
    public static Object checkOut(Exception ex,StringBuffer stringBuffer) throws IOException {
         Object result=null;
        if (ex instanceof BaseExceptionInterface) {
            Integer errCode = ((BaseExceptionInterface) ex).getErrCode();
            String errMsg = ((BaseExceptionInterface) ex).getErrMsg();
            if(stringBuffer!=null)stringBuffer.append("errCode="+errCode+",errMsg="+errMsg);
            result=ex;
        } else {
            if(stringBuffer!=null)stringBuffer.append(ex.toString());
            BaseExceptionInterface baseExceptionInterface=ex instanceof RuntimeException? new ServiceException(ex):new ServiceRuntimeException(ex);
            result=baseExceptionInterface;
        }
        return result;
    }


}
