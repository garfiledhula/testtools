package com.yyf.java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionAccessibleTest {
	
	public static void main(String[] args) throws Exception {
		Class<?> clazz = Class.forName("com.yyf.java.A");
		
		Field field = clazz.getDeclaredField("i");
		//Field field = clazz.getField("i"); // java.lang.NoSuchFieldException
		System.out.println(field);
		
		Method methodGet = clazz.getMethod("getI", new Class[]{});
		//Method methodGet = clazz.getDeclaredMethod("getI", new Class[]{});
		System.out.println(methodGet);
		
		Method methodSet = clazz.getDeclaredMethod("setI", new Class[]{int.class});
		//Method methodSet = clazz.getMethod("setI", new Class[]{int.class}); // java.lang.NoSuchMethodException
		System.out.println(methodSet);
		
		Object instance = clazz.newInstance();
		Object retValue = methodGet.invoke(instance, new Object[]{});
		System.out.println(retValue);
		
		methodSet.setAccessible(true); //无则： java.lang.IllegalAccessException
		methodSet.invoke(instance, new Object[]{100});
		Object retValue2 = methodGet.invoke(instance, null);
		System.out.println(retValue2);
		
	}
}


class A{
	private int i;
	
	public int getI(){
		return i;
	}
	
	private void setI(int num){
		this.i = num;
	}
}