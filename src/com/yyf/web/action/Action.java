package com.yyf.web.action;

import java.util.Map;

public interface Action {
	
	public Map<String, Object> execute(Map<String, Object> context);
	
}
