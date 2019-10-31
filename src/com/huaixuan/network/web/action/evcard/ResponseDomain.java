package com.huaixuan.network.web.action.evcard;

import java.util.HashMap;
import java.util.Map;



/**
 * @author Mr_Yang   2016-11-24 上午11:29:22
 * 返回参数model
 **/

public class ResponseDomain
{
	private int code = -1;
	private String body;
	private String contentType;
	private String cookies;
	private String resposeLog = "";//记录请求和响应信息
	private Map<String,String> cookiesMap = new HashMap<String, String>();
	public int getCode()
	{
		return code;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	 
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public String getCookies()
	{
		return cookies;
	}
	public void setCookies(String cookies)
	{
		this.cookies = cookies;
	}
	public String getResposeLog()
	{
		return resposeLog;
	}
	public void setResposeLog(String resposeLog)
	{
		this.resposeLog = resposeLog;
	}
	public Map<String, String> getCookiesMap()
	{
		return cookiesMap;
	}
	public void setCookiesMap(Map<String, String> cookiesMap)
	{
		this.cookiesMap = cookiesMap;
	}
	
	
	
	
	
	
	
}	
 
