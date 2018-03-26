package com.example.demo_consumers.config;

import com.alibaba.fastjson.JSON;
import com.zxj.cloud_service_proxy_core.exception.BaseExceptionInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
		try {
			writer = response.getWriter();
			jsonStrBuffer = new StringBuffer();
			if (ex instanceof BaseExceptionInterface) {
				Integer errCode = ((BaseExceptionInterface) ex).getErrCode();
				String errMsg = ((BaseExceptionInterface) ex).getErrMsg();
				Map<String,Object> map=new HashMap<>();
				map.put("errorCode",errCode);
				map.put("errMsg",errMsg);
				jsonStrBuffer.append(JSON.toJSON(map));
			} else {
				jsonStrBuffer.append(ex.toString());
			}
			writer.write(jsonStrBuffer.toString());
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