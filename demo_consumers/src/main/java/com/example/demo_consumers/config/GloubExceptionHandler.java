package com.example.demo_consumers.config;

import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.util.convert.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


/**
 * 全局异常渲染器
 * @author zhuxiujie
 */
@ControllerAdvice
public class GloubExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String handler(Exception ex){
        try {
            StringBuffer jsonStrBuffer = new StringBuffer();
            if (ex instanceof BaseExceptionInterface) {
                Integer errCode = ((BaseExceptionInterface) ex).getErrCode();
                String errMsg = ((BaseExceptionInterface) ex).getErrMsg();
                Map<String,Object> map=new HashMap<>();
                map.put("errorCode",errCode);
                map.put("errMsg",errMsg);
                jsonStrBuffer.append(ConvertUtil.getEncoder().encoder(map));
            } else {
                jsonStrBuffer.append(ex.toString());
            }
            return jsonStrBuffer.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }
}
