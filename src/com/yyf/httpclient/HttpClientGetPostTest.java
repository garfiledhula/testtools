package com.yyf.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.yyf.tools.SizeOf;

public class HttpClientGetPostTest {
	
	public static void main(String[] args) throws IOException {
		//System.out.println(post("http://localhost:8080/test/helloWord.json", "aabbccddeeffgg"));
		//System.out.println(get("http://www.baidu.com/"));
		HttpPost httppost = new HttpPost("http://www.baidu.com/");
		SizeOf.deepSizeOf(httppost);
	}
	
	
	@SuppressWarnings("deprecation")
	public static String post(String url, String content){
		String body = null;
		DefaultHttpClient httpClient = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			
			//设置post编码  
			/*httpClient.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8); 
			httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
			httpClient.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
			httppost.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);*/
			
			//设置参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("data", content));
			httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			
			//httppost.setHeader("content-type", "application/json"); //如果是json，servlet获取不到参数
			//httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			HttpResponse httpresponse = httpClient.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {  
                entity.consumeContent();  
            }
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();  
        } catch (ClientProtocolException e) {
        	e.printStackTrace();  
        } catch (ParseException e) {
        	e.printStackTrace();  
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		} finally{
			if(httpClient!=null)
				httpClient.close();
		}
		return body;
	}
	
	
	@SuppressWarnings("deprecation")
	public static String get(String url){
		String body = null;
		DefaultHttpClient httpClient = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			 //配置请求的超时设置  
	        RequestConfig requestConfig = RequestConfig.custom()
	        		.setConnectionRequestTimeout(50)
	        		.setConnectTimeout(50)
	        		.setSocketTimeout(50).build();
	        httpget.setConfig(requestConfig);  
			HttpResponse httpresponse = httpClient.execute(httpget);
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {  
                entity.consumeContent();  
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();  
        } catch (ClientProtocolException e) {
        	e.printStackTrace();  
        } catch (ParseException e) {
        	e.printStackTrace();  
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		} finally{
			if(httpClient!=null)
				httpClient.close();
		}
		return body;
	}
	
	
}
