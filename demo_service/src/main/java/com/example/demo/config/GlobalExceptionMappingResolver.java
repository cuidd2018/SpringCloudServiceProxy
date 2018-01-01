package com.example.demo.config;


import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一异常处理
 *
 * @author zhuxiujie
 */
@Configuration
public class GlobalExceptionMappingResolver implements HandlerExceptionResolver {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private StringBuffer jsonStrBuffer;
	/**
	 * 判断ajax
	 *
	 * @param request
	 * @return
	 */
	protected boolean isAjaxRequest(HttpServletRequest request) {
		return (request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1));
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = null;
		logger.error("全局错误",ex);
		try {
			writer = response.getWriter();
			jsonStrBuffer = new StringBuffer();
			if (ex instanceof BaseExceptionInterface) {
				Integer errCode = ((BaseExceptionInterface) ex).getErrCode();
				String errMsg = ((BaseExceptionInterface) ex).getErrMsg();
				jsonStrBuffer.append("errCode="+errCode+",errMsg="+errMsg);
				writer.write(SerializeStringUtil.serializeToByteString(ex));
			} else {
				jsonStrBuffer.append(ex.toString());
				BaseExceptionInterface baseExceptionInterface=ex instanceof RuntimeException? new ServiceException(ex):new ServiceRuntimeException(ex);
				writer.write(SerializeStringUtil.serializeToByteString(baseExceptionInterface));
			}
			writer.flush();
			jsonStrBuffer=null;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return new ModelAndView();
	}

}