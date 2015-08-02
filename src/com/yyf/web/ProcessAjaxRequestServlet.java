package com.yyf.web;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.UrlPathHelper;
import com.alibaba.fastjson.JSON;
import com.yyf.web.action.Action;
import com.yyf.web.utils.ApplicationContextUtils;
import com.yyf.web.utils.HttpServletUtils;
import com.yyf.web.utils.ReturnCodeUtils;

/**
 * 不适用Spring的MVC，直接用Servlet进行解析
 * @author yyf
 *
 */
@SuppressWarnings("serial")
public class ProcessAjaxRequestServlet extends HttpServlet {
	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
	private static final Logger logger = LoggerFactory.getLogger(ProcessAjaxRequestServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			String responseStr = processHttpRequest(request, response);
			response.getWriter().write(responseStr);
		} catch (IOException e) {
			logger.error("doGet异常", e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			String responseStr = processHttpRequest(request, response);
			response.getWriter().write(responseStr);
		} catch (IOException e) {
			logger.error("doPost异常", e);
		}
	}
	
	private String processHttpRequest(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		try {
			Map<String, Object> requestParams = HttpServletUtils.geRequestParams(request);
			logger.info("输入参数：{}", requestParams);
			Map<String, Object> responseResult = this.doProcessHttpRequest(request, response, requestParams);
			logger.info("输出结果：{}", responseResult);
			return JSON.toJSONString(responseResult);			
		} catch (Exception e) {
			logger.error("JSON序列化异常", e);
			Map<String, Object> jsonErrorMap = ReturnCodeUtils.getReturnCode("E", "ER0000", e.getMessage());
			logger.info("输出结果：{}", jsonErrorMap);
			return JSON.toJSONString(jsonErrorMap);
		}
	}
	
	private Map<String, Object> doProcessHttpRequest(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> context){
		String actionName = this.getActionName(request);
		if(StringUtils.isEmpty(actionName)){
			return ReturnCodeUtils.getReturnCode("E", "ER0000", "找不到对应的方法名称");
		}
		Action processAction = this.getAction(actionName);
		if(processAction==null){
			return ReturnCodeUtils.getReturnCode("E", "ER0000", "找不到对应的处理器");
		}
		try {
			Map<String, Object> outputResult = processAction.execute(context);
			ReturnCodeUtils.putSucessReturnCode(outputResult);
			return outputResult;
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnCodeUtils.getReturnCode("E", "ER0000", e.getMessage());
		}
	}
	
	/*private String processHttpRequest(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		String returnStr = "";
		String actionName = this.getActionName(request);
		if(StringUtils.isEmpty(actionName)){
			return JSON.toJSONString(ReturnCodeUtils.getReturnCode("E", "ER0000", "找不到对应的方法名称"));
		}
		Controller processController = this.getController(actionName);
		if(processController==null){
			return JSON.toJSONString(ReturnCodeUtils.getReturnCode("E", "ER0000", "找不到对应的处理器"));
		}
		try {
			ModelAndView mv = processController.handleRequest(request, response);
			Map<String, Object> outputResult = mv.getModelMap();
			ReturnCodeUtils.putSucessReturnCode(outputResult);
			returnStr = JSON.toJSONString(outputResult);
		} catch (Exception e) {
			e.printStackTrace();
			returnStr = JSON.toJSONString(ReturnCodeUtils.getReturnCode("E", "ER0000", e.getMessage()));
		}
		return returnStr;
	}*/
	
	
	private String getActionName(HttpServletRequest request){
		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		int end = lookupPath.lastIndexOf(".");
		int start = lookupPath.lastIndexOf("/");
		if(start==-1 || end==-1 || start>=end-1){
			return null;
		}
		return lookupPath.substring(start+1, end);
	}
	
	public Action getAction(String name){
		try {
			if(ApplicationContextUtils.containsBean(name))
				return ApplicationContextUtils.getBean(name, Action.class);
			else
				System.out.println("No action named '"+name+"' is defined");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
