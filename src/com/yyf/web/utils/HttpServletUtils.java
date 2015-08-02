package com.yyf.web.utils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServletUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpServletUtils.class);
	/**
	 * 获取HttpServletRequest的参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> geRequestParams(HttpServletRequest request){
		Map<String, Object> context = new HashMap<String, Object>(); //参数Map
		Map<String, String[]> parameterMap = request.getParameterMap();
		for(Map.Entry<String, String[]> entry : parameterMap.entrySet()){
			context.put(entry.getKey(), entry.getValue()[0]);
			if(entry.getValue().length>1){
				logger.warn("参数数据大于1：{}", new Object[]{entry.getValue()});
			}
		}
		return context;
	}
	
}
