package com.yyf.web;

import java.util.Map;

public interface Action {
	
	public Map<String, Object> execute(Map<String, Object> context);
	
}
