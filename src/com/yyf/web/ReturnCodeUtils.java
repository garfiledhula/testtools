package com.yyf.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReturnCodeUtils {
	
	private static final String RETURN_KEY = "returnCode";
	private static final String RETURN_CODE = "code";
	private static final String RETURN_TYPE = "type";
	private static final String RETURN_MESSAGE = "message";
	
	public static void putSucessReturnCode(Map<String,Object> context){
		putReturnCode(context, "S", "000000", "交易成功");
	}
	
	public static void putReturnCode(Map<String,Object> context, String type, String code, String message){
		if(!context.containsKey(RETURN_KEY)){
			Map<String,Object> returnCodeMap = new HashMap<String, Object>();
			returnCodeMap.put(RETURN_TYPE, type);
			returnCodeMap.put(RETURN_CODE, code);
			returnCodeMap.put(RETURN_MESSAGE, message);
			context.put(RETURN_KEY, returnCodeMap);
		}else{
			Map<String,Object> returnCodeMap = (Map<String,Object>) context.get(RETURN_KEY);
			Set<String> keySet = returnCodeMap.keySet();
			if(keySet.size()!=3 || !keySet.contains(RETURN_TYPE) || !keySet.contains(RETURN_CODE) || !keySet.contains(RETURN_MESSAGE))
				throw new RuntimeException("returnCode不合法");
		}
	}
	
	public static Map<String, Object> getReturnCode(String type, String code, String message){
		Map<String, Object> outputResult = new HashMap<String, Object>();
		ReturnCodeUtils.putReturnCode(outputResult, "E", "ER0000", message);
		return outputResult;
	}
	
	
	
}