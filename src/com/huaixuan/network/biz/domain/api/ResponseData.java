package com.huaixuan.network.biz.domain.api;



/**
 * @author Mr_Yang   2015-12-3 下午01:49:41
 * API 返回数据。
 **/

public class ResponseData
{
	private int responseCode = 0;
	private String responseMsg = "ok";
	private int code = 200; 
	private Object response;
	public int getResponseCode()
	{
		return responseCode;
	}
	public void setResponseCode(int responseCode)
	{
		this.responseCode = responseCode;
	}
	public String getResponseMsg()
	{
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg)
	{
		this.responseMsg = responseMsg;
	}
	public Object getResponse()
	{
		return response;
	}
	public void setResponse(Object response)
	{
		this.response = response;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
	
}
 
