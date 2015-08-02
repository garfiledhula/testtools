package com.yyf.ws.test;

class ReqHeader{
	private String domain;
	private String systemId;
	private String sequence;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public ReqHeader(String domain, String systemId, String sequence) {
		super();
		this.domain = domain;
		this.systemId = systemId;
		this.sequence = sequence;
	}
	public ReqHeader() {
		super();
	}
}