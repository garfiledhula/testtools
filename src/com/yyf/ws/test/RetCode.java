package com.yyf.ws.test;

class RetCode{
	private String type;
	private String code;
	private String message;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public RetCode(String type, String code, String message) {
		super();
		this.type = type;
		this.code = code;
		this.message = message;
	}
	public RetCode() {
		super();
	}
	
}