package com.yyf.ws.test;

public class ReqObj{
	private ReqHeader reqHeader;
	private String reqBodyStr;
	
	public ReqHeader getReqHeader() {
		return reqHeader;
	}
	
	public void setReqHeader(ReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}
	
	public String getReqBodyStr() {
		return reqBodyStr;
	}

	public void setReqBodyStr(String reqBodyStr) {
		this.reqBodyStr = reqBodyStr;
	}
}
