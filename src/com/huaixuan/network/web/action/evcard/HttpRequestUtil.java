package com.huaixuan.network.web.action.evcard;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author Mr_Yang 2016-11-23 上午11:53:35 发送post以及get请求 获取返回cookies等信息
 **/

public class HttpRequestUtil  
{
	public static String sendPostRquest(String url,String postData)
	{
		RequestDomain requestDomain = new RequestDomain();
		requestDomain.setMethod("post");
		requestDomain.setUrl(url);
		requestDomain.setPostData(postData);
		ResponseDomain rd = sendPostRequest(requestDomain,"");
		return rd.getBody();
	}
	
	public static String sendGetRquest(String url)
	{
		RequestDomain requestDomain = new RequestDomain();
		requestDomain.setMethod("get");
		requestDomain.setUrl(url);
		requestDomain.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		requestDomain.setAccept("*/*");
		requestDomain.setConnection("Keep-Alive");
		requestDomain.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		ResponseDomain rd = sendGetRequest(requestDomain);
		if(rd.getCode() == 400)
		{
			return getURLContent(url);
		}
		return rd.getBody();
	}
	
	public static ResponseDomain sendPostRequest(RequestDomain requestDomain,String charset)
	{
		StringBuffer buffer = new StringBuffer();
		DataOutputStream  out = null; // write 发送post
		BufferedReader in = null;// read 获取返回值
		ResponseDomain responseDomain = new ResponseDomain();
		HttpURLConnection connection = null;
		try
		{
			// 如果是get 需要在url后面加请求参数
			if (!"post".equalsIgnoreCase(requestDomain.getMethod()))
			{
				responseDomain.setCode(-1);
				responseDomain.setBody("只允许post提交");
				responseDomain.setResposeLog("只允许post提交");
				return responseDomain;
			    
			}
			String requestUrl = requestDomain.getUrl();
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// post需要加Context-length
			connection.setRequestMethod("POST");
			if (requestDomain.getPostData() != null)
				connection.setRequestProperty("Content-Length",  requestDomain.getPostData().length()+"");
			else 
				connection.setRequestProperty("Content-Length", "0");
			// 设置通用的请求属性
			connection.setRequestProperty("Host", getHost(requestDomain.getUrl()));
			connection.setRequestProperty("Accept", requestDomain.getAccept());
			connection.setRequestProperty("Accept-Encoding", requestDomain.getAcceptEncoding());
			connection.setRequestProperty("Accept-Language", requestDomain.getAccepLanguage());
			connection.setRequestProperty("Content-Type", requestDomain.getContentType());
			connection.setRequestProperty("Connection", requestDomain.getConnection());
			connection.setRequestProperty("User-Agent", requestDomain.getUserAgent());
			connection.setRequestProperty("Host", getHost(requestUrl));
			connection.setRequestProperty("Referer", requestDomain.getRefere());
			connection.setRequestProperty("Cookie", requestDomain.getCookies());
			
			Map<String,String> otherRequestMap = requestDomain.getOtherRquestMap();
			Set<Entry<String,String>> en = otherRequestMap.entrySet();
			Iterator<Entry<String,String>> it = en.iterator();
			while(it.hasNext())
			{
				Entry<String,String> next = it.next();
				String key = next.getKey();
				String value = next.getValue();
				connection.setRequestProperty(key, value);
			}
			
			
			
			if(requestDomain.getShowRquestLog())
			{
				 showRequestLog(connection, requestDomain);
			}
			setRequestLog(connection, responseDomain, requestDomain);
			
			
			connection.setInstanceFollowRedirects(false);
			connection.connect();
			// 获取URLConnection对象对应的输出流
			out = new DataOutputStream(connection.getOutputStream());
			// 发送请求参数
			if(StringUtil.isNotBlank(charset))
				out.write(requestDomain.getPostData().getBytes(charset));
			else
				out.write(requestDomain.getPostData().getBytes());
			// flush输出流的缓冲
			out.flush();
			
			
			// 定义BufferedReader输入流来读取URL的响应
			if(StringUtil.isNotBlank(charset))
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
			else
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			//获取成功
			if(connection.getResponseCode() == 200)
			{
				String line;
				while ((line = in.readLine()) != null)
				{
					buffer.append(line);
				}
			}
			else if(connection.getResponseCode() == 302)
			{
				buffer.append(connection.getHeaderField("Location"));
			}
			
			responseDomain.setCode(connection.getResponseCode());
			responseDomain.setBody(buffer.toString());
			responseDomain.setContentType(connection.getHeaderField("Content-Type"));
			//获取cookies
			 String key = null;
			 String cookies = "";
			 for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) 
			 {
			     String value = connection.getHeaderField(i);
				 if("Set-Cookie".equals(key))
				 {
					 cookies += value+";";
				 }
			}
			 responseDomain.setCookies(cookies);
			//显示返回数据日志
			if(requestDomain.getShowResponseLog())
			{
				showResponseLog(connection);
			}
			
			setResponseLog(connection, responseDomain, requestDomain);
			
			connection.disconnect();
		}
		catch (Exception e)
		{
			try
			{
				responseDomain.setCode(connection.getResponseCode());
			}
			catch (IOException e1)
			{
				//e1.printStackTrace();
			}
			responseDomain.setBody(e.getMessage());
			//e.printStackTrace();
		}
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (Exception ex)
			{
				responseDomain.setCode(500);
				responseDomain.setBody(ex.getMessage());
			}
		}
		//System.out.println(buffer.toString());
		return responseDomain;
	}
	
	
	
	/**
	 * 上传附件
	 * @param requestDomain
	 * @return
	 */
	public static ResponseDomain sendPost2UploadFile(RequestDomain requestDomain,String fullPath)
	{
		StringBuffer buffer = new StringBuffer();
		DataOutputStream  out = null; // write 发送post
		BufferedReader in = null;// read 获取返回值
		ResponseDomain responseDomain = new ResponseDomain();
		try
		{
			// 如果是get 需要在url后面加请求参数
			if (!"post".equalsIgnoreCase(requestDomain.getMethod()))
			{
				responseDomain.setCode(-1);
				responseDomain.setBody("只允许post提交");
				responseDomain.setResposeLog("只允许post提交");
				return responseDomain;
			    
			}
			
			File file = new File(fullPath);
			FileInputStream fis = new FileInputStream(file);  
			String Enter = "\r\n";  
			String boundary = requestDomain.getContentType().split("=")[1]; //Could be any string  
			String requestUrl = requestDomain.getUrl();
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// post需要加Context-length
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length", file.length()+"");
	 
			// 设置通用的请求属性
			connection.setRequestProperty("Host", getHost(requestDomain.getUrl()));
			connection.setRequestProperty("Accept", requestDomain.getAccept());
			connection.setRequestProperty("Accept-Encoding", requestDomain.getAcceptEncoding());
			connection.setRequestProperty("Accept-Language", requestDomain.getAccepLanguage());
			connection.setRequestProperty("Content-Type", requestDomain.getContentType());
			connection.setRequestProperty("Connection", requestDomain.getConnection());
			connection.setRequestProperty("User-Agent", requestDomain.getUserAgent());
			connection.setRequestProperty("Host", getHost(requestUrl));
			connection.setRequestProperty("Referer", requestDomain.getRefere());
			connection.setRequestProperty("Cookie", requestDomain.getCookies());
			connection.setRequestProperty("Content-Length",  file.length()+"");
			
			Map<String,String> otherRequestMap = requestDomain.getOtherRquestMap();
			Set<Entry<String,String>> en = otherRequestMap.entrySet();
			Iterator<Entry<String,String>> it = en.iterator();
			while(it.hasNext())
			{
				Entry<String,String> next = it.next();
				String key = next.getKey();
				String value = next.getValue();
				connection.setRequestProperty(key, value);
			}
			
			
			
			if(requestDomain.getShowRquestLog())
			{
				 showRequestLog(connection, requestDomain);
			}
			setRequestLog(connection, responseDomain, requestDomain);
			
			
			connection.setInstanceFollowRedirects(false);
			connection.connect();
			// 获取URLConnection对象对应的输出流
			out = new DataOutputStream(connection.getOutputStream());
			
			
		
			//part 1  
            String part1 =  "--" + boundary + Enter  
            			+ "Content-Disposition: form-data;name=\"file\";filename=\"img.jpeg\"" + Enter  
                        + "Content-Type: image/jpeg" + Enter  + Enter;
            
            String part2 =  Enter + Enter + "--" + boundary + "--";  
            byte[] fileBytes = new byte[fis.available()];  
            fis.read(fileBytes);
            
            
			// 发送请求参数
			out.writeBytes(part1);
			out.write(fileBytes);
			out.writeBytes(part2);
			// flush输出流的缓冲
			out.flush();
 
			
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			
			//获取成功
			if(connection.getResponseCode() == 200)
			{
				String line;
				while ((line = in.readLine()) != null)
				{
					buffer.append(line);
				}
			}
			else if(connection.getResponseCode() == 302)
			{
				buffer.append(connection.getHeaderField("Location"));
			}
			
			responseDomain.setCode(connection.getResponseCode());
			responseDomain.setBody(buffer.toString());
			responseDomain.setContentType(connection.getHeaderField("Content-Type"));
			//获取cookies
			 String key = null;
			 String cookies = "";
			 for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) 
			 {
			     String value = connection.getHeaderField(i);
				 if("Set-Cookie".equals(key))
				 {
					 cookies += value+";";
				 }
			}
			 responseDomain.setCookies(cookies);
			//显示返回数据日志
			if(requestDomain.getShowResponseLog())
			{
				showResponseLog(connection);
			}
			
			setResponseLog(connection, responseDomain, requestDomain);
			
			connection.disconnect();
		}
		catch (Exception e)
		{
			responseDomain.setCode(-1);
			responseDomain.setBody(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (Exception ex)
			{
				responseDomain.setCode(-1);
				responseDomain.setBody(ex.getMessage());
			}
		}
		//System.out.println(buffer.toString());
		return responseDomain;
	}
	
	
	
	
	
	/**
     * 发送get请求
     * @param urlStr
     * @return
     */
    public static String getURLContent(String urlStr) {             
        /** 网络的url地址 */      
     URL url = null;            
        /** http连接 */  
     HttpURLConnection httpConn = null;          
         /**//** 输入流 */ 
     BufferedReader in = null; 
     StringBuffer sb = new StringBuffer(); 
     try{   
      url = new URL(urlStr);   
      in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") ); 
      String str = null;  
      while((str = in.readLine()) != null) {  
       sb.append( str );   
             }   
         } catch (Exception ex) { 
           
         } finally{  
          try{           
           if(in!=null) {
            in.close();   
                 }   
             }catch(IOException ex) {    
             }   
         }   
         String result =sb.toString();    
         return result;  
         }  
	
	public static ResponseDomain sendGetRequest(RequestDomain requestDomain)
	{
		StringBuffer buffer = new StringBuffer();
		BufferedReader in = null;// read 获取返回值
		ResponseDomain responseDomain = new ResponseDomain();
		try
		{
			// 如果是get 需要在url后面加请求参数
			if (!"get".equalsIgnoreCase(requestDomain.getMethod()))
			{
				responseDomain.setCode(-1);
				responseDomain.setBody("只允许get提交");
				return responseDomain;
			    
			}
			String requestUrl = requestDomain.getUrl();
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
 
			// 设置通用的请求属性
			 connection.setRequestProperty("Host", "api.shjmpt.com:9002");
			connection.setRequestProperty("Accept", requestDomain.getAccept());
			connection.setRequestProperty("Accept-Encoding", requestDomain.getAcceptEncoding());
			connection.setRequestProperty("Accept-Language", requestDomain.getAccepLanguage());
			connection.setRequestProperty("Content-Type", requestDomain.getContentType());
			connection.setRequestProperty("Connection", requestDomain.getConnection());
			connection.setRequestProperty("User-Agent", requestDomain.getUserAgent());
			connection.setRequestProperty("Host", getHost(requestUrl));
			connection.setRequestProperty("Referer", requestDomain.getRefere());
			connection.setRequestProperty("Cookie", requestDomain.getCookies());
			
			Map<String,String> otherRequestMap = requestDomain.getOtherRquestMap();
			Set<Entry<String,String>> en = otherRequestMap.entrySet();
			Iterator<Entry<String,String>> it = en.iterator();
			while(it.hasNext())
			{
				Entry<String,String> next = it.next();
				String key = next.getKey();
				String value = next.getValue();
				connection.setRequestProperty(key, value);
			}
			
			//不自动转跳
			connection.setInstanceFollowRedirects(false);
			
			
			if(requestDomain.getShowRquestLog())
			{
				 showRequestLog(connection, requestDomain);
			}
			setRequestLog(connection, responseDomain, requestDomain);
			
			
		 
			//定义BufferedReader输入流来读取URL的响应
			connection.connect();
			
			
			//获取成功
			if(connection.getResponseCode() == 200)
			{
				in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				String line;
				while ((line = in.readLine()) != null)
				{
					buffer.append(line);
				}
				
			}
			else if(connection.getResponseCode() == 302)
			{
				buffer.append(connection.getHeaderField("Location"));
			}
			
			responseDomain.setCode(connection.getResponseCode());
			responseDomain.setBody(buffer.toString());
			responseDomain.setContentType(connection.getHeaderField("Content-Type"));
			//获取cookies
			 String key = null;
			 String cookies = "";
			 for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) 
			 {
			     String value = connection.getHeaderField(i);
				 if("Set-Cookie".equals(key))
				 {
					 cookies += value+";";
					 
					 
				 }
			}
			 responseDomain.setCookies(cookies);
			//显示返回数据日志
			if(requestDomain.getShowResponseLog())
			{
				showResponseLog(connection);
			}
			
			//设置请求和响应信息
			setResponseLog(connection, responseDomain, requestDomain);
			
			connection.disconnect();
		}
		catch (Exception e)
		{
			responseDomain.setCode(-1);
			responseDomain.setBody(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			}
			catch (Exception ex)
			{
				responseDomain.setCode(-1);
				responseDomain.setBody(ex.getMessage());
			}
		}
		//System.out.println(buffer.toString());
		return responseDomain;
	}
	
	
	
	
	/**
	 * get请求图片并保存到本地
	 * @param requestDomain
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static ResponseDomain saveImage2Location(RequestDomain requestDomain,String path,String fileName)
	{
		InputStream in = null;// read 获取返回值
		OutputStream os = null;//写图片
		ResponseDomain responseDomain = new ResponseDomain();
		try
		{
			// 如果是get 需要在url后面加请求参数
			if (!"get".equalsIgnoreCase(requestDomain.getMethod()))
			{
				responseDomain.setCode(-1);
				responseDomain.setBody("只允许get提交");
				return responseDomain;
			    
			}
			String requestUrl = requestDomain.getUrl();
			//requestUrl = urlEncode(requestDomain.getUrl(),"UTF-8");
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
 
			// 设置通用的请求属性
			connection.setRequestProperty("Host", getHost(requestDomain.getUrl()));
			connection.setRequestProperty("Accept", requestDomain.getAccept());
			connection.setRequestProperty("Accept-Encoding", requestDomain.getAcceptEncoding());
			connection.setRequestProperty("Accept-Language", requestDomain.getAccepLanguage());
			connection.setRequestProperty("Content-Type", requestDomain.getContentType());
			connection.setRequestProperty("Connection", requestDomain.getConnection());
			connection.setRequestProperty("User-Agent", requestDomain.getUserAgent());
			connection.setRequestProperty("Host", getHost(requestUrl));
			connection.setRequestProperty("Referer", requestDomain.getRefere());
			connection.setRequestProperty("Cookie", requestDomain.getCookies());
			
			Map<String,String> otherRequestMap = requestDomain.getOtherRquestMap();
			Set<Entry<String,String>> en = otherRequestMap.entrySet();
			Iterator<Entry<String,String>> it = en.iterator();
			while(it.hasNext())
			{
				Entry<String,String> next = it.next();
				String key = next.getKey();
				String value = next.getValue();
				connection.setRequestProperty(key, value);
			}
			
			//不允许转跳
			connection.setInstanceFollowRedirects(false);
			
			if(requestDomain.getShowRquestLog())
			{
				 showRequestLog(connection, requestDomain);
			}
			setRequestLog(connection, responseDomain, requestDomain);
			
			
			connection.connect();
 
			// 定义BufferedReader输入流来读取URL的响应
			in =  new BufferedInputStream(connection.getInputStream());
			File dir = new File(path);
			if(!dir.exists()) dir.mkdirs();
			String absPath = path + "/" + fileName;
			os = new  BufferedOutputStream(new FileOutputStream(new File(absPath)));  
			
			//获取成功
			if(connection.getResponseCode() == 200)
			{
				int len = 0;  
                byte[] buffer = new byte[1024];  
                //将输入流写入到我们定义好的文件中  
                while ((len = in.read(buffer)) != -1) {  
                    os.write(buffer, 0, len);  
                }  
                //将缓冲刷入文件  
                os.flush();  
			}
			
			responseDomain.setCode(connection.getResponseCode());
			responseDomain.setBody("图片写入成功,路径： " + absPath);
			responseDomain.setContentType(connection.getHeaderField("Content-Type"));
			//获取cookies
			 String key = null;
			 String cookies = "";
			 for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) 
			 {
			     String value = connection.getHeaderField(i);
				 if("Set-Cookie".equals(key))
				 {
					 cookies += value+";";
				 }
			}
			 responseDomain.setCookies(cookies);
			//显示返回数据日志
			if(requestDomain.getShowResponseLog())
			{
				 showResponseLog(connection);
			}
			
			//设置请求和响应信息
			setResponseLog(connection, responseDomain, requestDomain);
			
			connection.disconnect();
		}
		catch (Exception e)
		{
			responseDomain.setCode(-1);
			responseDomain.setBody(e.getMessage());
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
				if (os != null)
				{
					os.close();
				}
			}
			catch (Exception ex)
			{
				responseDomain.setCode(-1);
				responseDomain.setBody(ex.getMessage());
			}
		}
		return responseDomain;
	}
	
	
	
	/**
	 * get请求图片字节数组
	 * @param requestDomain
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static byte[] getImage2ByteArray(RequestDomain requestDomain)
	{
		InputStream in = null;// read 获取返回值
		ByteArrayOutputStream os = null;//写图片
		byte[] resultByte = new byte[0];
		try
		{
			// 如果是get 需要在url后面加请求参数
			if (!"get".equalsIgnoreCase(requestDomain.getMethod()))
			{
				return resultByte;
			    
			}
			String requestUrl = requestDomain.getUrl();
			//requestUrl = urlEncode(requestDomain.getUrl(),"UTF-8");
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
 
			// 设置通用的请求属性
			connection.setRequestProperty("Host", getHost(requestDomain.getUrl()));
			connection.setRequestProperty("Accept", requestDomain.getAccept());
			connection.setRequestProperty("Accept-Encoding", requestDomain.getAcceptEncoding());
			connection.setRequestProperty("Accept-Language", requestDomain.getAccepLanguage());
			connection.setRequestProperty("Content-Type", requestDomain.getContentType());
			connection.setRequestProperty("Connection", requestDomain.getConnection());
			connection.setRequestProperty("User-Agent", requestDomain.getUserAgent());
			connection.setRequestProperty("Host", getHost(requestUrl));
			connection.setRequestProperty("Referer", requestDomain.getRefere());
			connection.setRequestProperty("Cookie", requestDomain.getCookies());
			
			Map<String,String> otherRequestMap = requestDomain.getOtherRquestMap();
			Set<Entry<String,String>> en = otherRequestMap.entrySet();
			Iterator<Entry<String,String>> it = en.iterator();
			while(it.hasNext())
			{
				Entry<String,String> next = it.next();
				String key = next.getKey();
				String value = next.getValue();
				connection.setRequestProperty(key, value);
			}
			
			//不允许转跳
			connection.setInstanceFollowRedirects(false);
			connection.connect();
 
			// 定义BufferedReader输入流来读取URL的响应
			in =  new BufferedInputStream(connection.getInputStream());
			os  = new ByteArrayOutputStream();
			
			//获取成功
			if(connection.getResponseCode() == 200)
			{
				int len = 0;  
                byte[] buffer = new byte[1024];  
                //将输入流写入到我们定义好的文件中  
                while ((len = in.read(buffer)) != -1) {  
                    os.write(buffer, 0, len);  
                }  
                //将缓冲刷入文件  
                os.flush();  
			}
			resultByte = os.toByteArray();
			connection.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
				if (os != null)
				{
					os.close();
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return resultByte;
	}
	
	
	
	//显示request日志
	private static void showRequestLog(HttpURLConnection connection,RequestDomain requestDomain) throws IOException
	{
		System.out.println("=============================");  
        System.out.println("Contents of get request");  
		System.out.println(requestDomain.getMethod() + "  url: " + requestDomain.getUrl());
		Map<String, List<String>> map = connection.getRequestProperties();
		Set<Entry<String,List<String>>> requestMapEn = map.entrySet();
		Iterator<Entry<String,List<String>>> requestMapIt = requestMapEn.iterator();
		while(requestMapIt.hasNext())
		{
			Entry<String,List<String>> next = requestMapIt.next();
			String key = next.getKey();
			List<String> value = next.getValue();
			System.out.print(key + ": ");
			for(int i = 0; i < value.size();i ++)
			{
				System.out.print(value.get(i) +  " " );
			}
			System.out.println();
		}
		System.out.println();
		if("post".equals(requestDomain.getMethod()))
		{
			System.out.println("Content-Length: " + requestDomain.getPostData().length());
			System.out.println(requestDomain.getPostData());
		}
	}
	
	//显示response日志
	private static void showResponseLog(HttpURLConnection connection) throws IOException
	{
		 String key=null;
		 System.out.println("=============================");  
         System.out.println("Contents of  response");  
		 System.out.println("response:");
		 System.out.println("post url :" + connection.getURL() );
		 System.out.println("response code :" +connection.getResponseCode());
		 System.out.println("response msg :" +connection.getResponseMessage());
		 for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) 
		 {
		     String field = connection.getHeaderField(i);
			 System.out.println(key + " " + field);
		}
	}
	
	
	
	
	//设置返回日志
	private static void setRequestLog(HttpURLConnection connection,ResponseDomain responseDomain,RequestDomain requestDomain) throws IOException
	{
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("=============================\r\n");  
		sb.append("Contents of "+ requestDomain.getMethod()+" request\r\n");  
		sb.append(requestDomain.getMethod() + " url: " + requestDomain.getUrl()+"\r\n");
		Map<String, List<String>> map = connection.getRequestProperties();
		Set<Entry<String,List<String>>> requestMapEn = map.entrySet();
		Iterator<Entry<String,List<String>>> requestMapIt = requestMapEn.iterator();
		while(requestMapIt.hasNext())
		{
			Entry<String,List<String>> next = requestMapIt.next();
			String key = next.getKey();
			List<String> value = next.getValue();
			sb.append(key + ": ");
			for(int i = 0; i < value.size();i ++)
			{
				sb.append(value.get(i) +  " " );
			}
			sb.append("\r\n");
		}
		if("post".equals(requestDomain.getMethod()))
		{
			sb.append("\r\n");
			sb.append("Content-Length: " + requestDomain.getPostData().length() + "\r\n");
			sb.append(requestDomain.getPostData());
		}		
		sb.append("\r\n");
		responseDomain.setResposeLog(sb.toString());
		 
	}
	
	
	
	private static void setResponseLog(HttpURLConnection connection,ResponseDomain responseDomain,RequestDomain requestDomain) throws IOException
	{
		
		 StringBuffer sb = new StringBuffer();
		 String key=null;
		 sb.append("=============================\r\n");  
		 sb.append("Contents of  response\r\n");  
		 sb.append(requestDomain.getMethod() + " url :" + connection.getURL() + "\r\n");
		 sb.append("response msg :" +connection.getResponseMessage() + "\r\n");
		 sb.append("response code :" +connection.getResponseCode() + "\r\n");
		 if(connection.getResponseCode() == 302)
		 {
			 String location = connection.getHeaderField("Location");  
			 sb.append("rederect url  :" + location + "\r\n");
		 }
		 for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) 
		 {
		     String field = connection.getHeaderField(i);
		     sb.append(key + " " + field + "\r\n");
		}
		 sb.append("\r\n");
		 sb.append(responseDomain.getBody()+"\r\n");
		 responseDomain.setResposeLog(responseDomain.getResposeLog() + sb.toString());
		 
	}
	
	/**
	 * 获取主机
	 * @param url
	 * @return
	 */
	public static String getHost(String url){
	  if(url==null||url.trim().equals("")){
	   return "";
	  }
	  String host = "";
	  Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
	  Matcher matcher = p.matcher(url);  
	  if(matcher.find()){
	   host = matcher.group();  
	  }
	  return host;
	 }

	/**
	 * 對中文進行编码
	 * 
	 * @param source
	 * @param encode
	 * @return
	 */
	public static String urlEncode(String source, String encode)
	{
		String result = source;
		try
		{
			result = java.net.URLEncoder.encode(source, encode);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "";
		}
		return result;
	}
	
	
 
}
