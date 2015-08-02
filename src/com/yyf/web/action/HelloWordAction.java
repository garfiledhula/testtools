package com.yyf.web.action;

import java.util.HashMap;
import java.util.Map;

public class HelloWordAction implements Action{

	@Override
	public Map<String, Object> execute(Map<String, Object> context) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("message", "HelloWord");
		return retMap;
	}

}
