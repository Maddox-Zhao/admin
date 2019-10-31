package com.huaixuan.network.web.action.evcard;

import java.util.HashMap;
import java.util.Map;



/**
 * @author Mr_Yang   2016-11-23 上午11:48:55
 * 请求参数 model
 **/

public class RequestDomain
{

	private String method = "get"; //发送请求的方法 get  post  put delete等
	private String url;
	private String acceptEncoding = "deflate;" ;
	private String accepLanguage = "zh-cn";
	private String contentType = "application/x-www-form-urlencoded;charset=UTF-8";
	private String accept = "*/*";
	private String origin;
	private String contentLenght = "0";
	private String connection = "keep-alive;";
	private String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0_2 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) Mobile/14A456;";
	private String refere = "";
	private String cookies = "";
	private String postData =""; //请求数据 {aa:ff,cc:dd} or  aa=ff&cc=dd
	
	private boolean useProxy = false;//是否使用代理
	private String proxyHost;
	private String proxyPort;
	private Map<String,String> otherRquestMap = new HashMap<String, String>();//其他参数
	
	private boolean showRquestLog = false; //是否打印请求信息
	private boolean showResponseLog = false; //是否打印响应信息
	public String getMethod()
	{
		return method;
	}
	public void setMethod(String method)
	{
		this.method = method;
	}
	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	} 
	 
	public String getAcceptEncoding()
	{
		return acceptEncoding;
	}
	public void setAcceptEncoding(String acceptEncoding)
	{
		this.acceptEncoding = acceptEncoding;
	}
	public String getAccepLanguage()
	{
		return accepLanguage;
	}
	public void setAccepLanguage(String accepLanguage)
	{
		this.accepLanguage = accepLanguage;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public String getAccept()
	{
		return accept;
	}
	public void setAccept(String accept)
	{
		this.accept = accept;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getContentLenght()
	{
		return contentLenght;
	}
	public void setContentLenght(String contentLenght)
	{
		this.contentLenght = contentLenght;
	}
	public String getConnection()
	{
		return connection;
	}
	public void setConnection(String connection)
	{
		this.connection = connection;
	}
	public String getUserAgent()
	{
		return userAgent;
	}
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}
	public String getRefere()
	{
		return refere;
	}
	public void setRefere(String refere)
	{
		this.refere = refere;
	}
	public String getCookies()
	{
		return cookies;
	}
	public void setCookies(String cookies)
	{
		this.cookies = cookies;
	}
	 
	public String getPostData() {
		return postData;
	}
	public void setPostData(String postData) {
		this.postData = postData;
	}
	public boolean isUseProxy()
	{
		return useProxy;
	}
	public void setUseProxy(boolean useProxy)
	{
		this.useProxy = useProxy;
	}
	public String getProxyHost()
	{
		return proxyHost;
	}
	public void setProxyHost(String proxyHost)
	{
		this.proxyHost = proxyHost;
	}
	public String getProxyPort()
	{
		return proxyPort;
	}
	public void setProxyPort(String proxyPort)
	{
		this.proxyPort = proxyPort;
	}
	public Map<String, String> getOtherRquestMap()
	{
		return otherRquestMap;
	}
	public void setOtherRquestMap(Map<String, String> otherRquestMap)
	{
		this.otherRquestMap = otherRquestMap;
	}
	public boolean getShowRquestLog()
	{
		return showRquestLog;
	}
	public void setShowRquestLog(boolean showRquestLog)
	{
		this.showRquestLog = showRquestLog;
	}
	public boolean getShowResponseLog()
	{
		return showResponseLog;
	}
	public void setShowResponseLog(boolean showResponseLog)
	{
		this.showResponseLog = showResponseLog;
	}
	
	
		
	
}
 
