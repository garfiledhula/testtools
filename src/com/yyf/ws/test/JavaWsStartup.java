package com.yyf.ws.test;

import java.net.URI;
import javax.xml.ws.Endpoint;

public class JavaWsStartup {
	private static String URL_DEMO = "http://localhost:8888/ws/wsForTest";
	public static void main(String[] args) {
		System.out.println("准备发布一个Webservice："+URL_DEMO); 
		Endpoint.publish(URL_DEMO, new WSForTest());
		System.out.println("已发布完一个Webservice："+URL_DEMO);
		openBrowser(URL_DEMO+"?wsdl");
	}
	
	
	private static void openBrowser(String url){
		try {
			Class<?> desktopClass = Class.forName("java.awt.Desktop");
			boolean isSupported = (Boolean)desktopClass.getMethod("isDesktopSupported").invoke(null, new Object[0]);
			if(isSupported){
				Object desktop = desktopClass.getMethod("getDesktop").invoke(null, new Object[0]);
				desktopClass.getMethod("browse", URI.class).invoke(desktop, new URI(url));
			}
		} catch (Exception e) {
			System.out.println("打开浏览器失败");
			e.printStackTrace();
		}
	}
}
