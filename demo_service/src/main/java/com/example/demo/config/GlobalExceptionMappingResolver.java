package com.example.demo.config;


import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import com.zxj.cloud_service_proxy_core.exception.ServiceException;
import com.zxj.cloud_service_proxy_core.exception.ServiceRuntimeException;
import com.zxj.cloud_service_proxy_core.util.invoke.ExceptionCheckOutUtil;
import com.zxj.cloud_service_proxy_core.util.invoke.SerializeStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
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
		logger.error("全局错误",ex);
		ServletOutputStream out=null;
		try {
			out = response.getOutputStream();
			jsonStrBuffer = new StringBuffer();
			byte[] bytes=ExceptionCheckOutUtil.checkOut(ex,jsonStrBuffer);
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ModelAndView();
	}

}