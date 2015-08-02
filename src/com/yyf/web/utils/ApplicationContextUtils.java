package com.yyf.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static <T> T getBean(String beanName, Class<T> requiredType){
		return applicationContext.getBean(beanName, requiredType);
	}
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	public static boolean containsBean(String beanName){
		return applicationContext.containsBean(beanName);
	}	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationContextUtils.applicationContext = applicationContext;
	}
}
