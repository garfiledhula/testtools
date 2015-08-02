package com.yyf.ws.test;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class WSForTest {
	
	@WebMethod
	public RetObj callws(@WebParam(name="reqObj") ReqObj reqObj){
		String message = reqObj.getReqBodyStr();
		System.out.println(message);
		System.out.println(reqObj.getReqHeader().getSequence());
		
		RetObj ret = new RetObj();
		ret.setRetBodyStr("返回:"+message);
		ret.setRetCode(new RetCode("S", "AAAAAA", "交易成功 "));
		return ret;
	}
	
	@WebMethod
	public String testCallWs2(@WebParam(name="requestStr") String requestStr){
		return requestStr;
	}
}

