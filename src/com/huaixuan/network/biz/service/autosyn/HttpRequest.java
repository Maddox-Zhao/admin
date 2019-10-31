package com.huaixuan.network.biz.service.autosyn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;


import org.apache.commons.httpclient.HttpClient;

import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;


public class HttpRequest
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/*
	 * params参数 key=value&key1=value1
	 */
	public static String sendPost(String url, Map<String, String> paramsMap)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		try
		{
			URL readUrl = new URL(url);
			
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
//			System.out.println(params);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	
	public static String sendPostYinTai(String url, Map<String, String> map,String ourSku,int quantity)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		String urlNameString = "";
		String paramsM="";
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = map.get(key);
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		
        	if(params == ""){
        		urlNameString = url;
        	}else{
        		urlNameString = url + params;
        	}
		
		paramsM="sku_code"+"="+ourSku+"&"+"stock"+"="+quantity;
		try
		{
			URL readUrl = new URL(urlNameString);
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(paramsM);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	
	
	}
	
	
	
	
	
	public static String sendPostWeimob(String url, Map<String, Object> paramsMap)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		try
		{
			URL readUrl = new URL(url);
			
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			JSONObject json=new JSONObject(paramsMap);
			
			out.print(json);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	
	/*
	 * params参数 key=value&key1=value1  去掉 密码项
	 */
	public static String sendPostNoSecret(String url, Map<String, String> paramsMap)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			if("secret".equals(key)) continue;
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		try
		{
			URL readUrl = new URL(url);
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
//        System.out.println(url+params);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println(sdf.format(new Date()) + "sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr)
	{
		MessageDigest md5 = null; 
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++)
		{
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
//	MD5中文加密不编译中文（解决加密字符中存在中文字符和其他语言加密结果不一致）
//	public static String string2MD5(String inStr)
//	{
//		String result = "";
//		MessageDigest md5 = null;
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//			// 这句是关键
//			md5.update(inStr.getBytes("UTF-8"));
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		byte b[] = md5.digest();
//		int i;
//		StringBuffer buf = new StringBuffer("");
//		for (int offset = 0; offset < b.length; offset++) {
//			i = b[offset];
//			if (i < 0)
//				i += 256;
//			if (i < 16)
//				buf.append("0");
//			buf.append(Integer.toHexString(i));
//		}
//		result = buf.toString();
// 
//		return result;
//
//
//	}
	//根据原数组分割成大小一样的数组
	public static List<List<String>>  createList(List<String> targe,int size) {
		List<List<String>> listArr = new ArrayList<List<String>>();
		//获取被拆分的数组个数
		int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
		for(int i=0;i<arrSize;i++) {
			List<String>	sub = new ArrayList<String>();
			//把指定索引数据放入到list中
			for(int j=i*size;j<=size*(i+1)-1;j++) {
				if(j<=targe.size()-1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(sub);
		}
		return listArr;
	}
	
	
	public static String replaceSpecileStr(String oldString)
	{
		String resultStr = oldString;
		String[] arr = new String[]{"•","【","】","\\u"};
		for(int i = 0;i < arr.length;i++)
		{
			resultStr = resultStr.replace(arr[i], "");
		}
		return resultStr;
	}
	
	//记录更新库存日志
	public static void recodeUpdateLog(LogData logData)
	{
		if(logData == null) return;
		String fileName = logData.getFileName();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fileName =  sf.format(new Date()) + "_" + fileName + "_update_stock.log";
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		path = "d:/log";
		File f = new File(path);
		if(!f.exists()){f.mkdirs();}
		path = path + Constants.FILE_SEP  + fileName;
		//String str = sf1.format(new Date()) + " " + logData.getHuohao() + " psku:" + logData.getPlatformSku() + " sku:" + logData.getSku()+
		//				" "+ logData.getBeforNum() + "->" + logData.getNowNum() + "\n";
		String str = sf1.format(new Date()) + " " + logData.getType() +   " sku:" + logData.getSku()+  " psku:" + logData.getPlatformSku()  + " h: " + logData.getHuohao() + " " + logData.getNowNum();
		if(StringUtil.isNotBlank(logData.getError()))
		{
			str += " " + logData.getError();
		}
		str += "\n";
		RandomAccessFile accessFile = null;
		try
		{
			accessFile = new RandomAccessFile(path,"rw");
			long fileLength = accessFile.length();
			accessFile.seek(fileLength);
			accessFile.write(str.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
				if(accessFile != null)
				{
					try
					{
						accessFile.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
		}
		
		
		 
		
	}
	
	
	//记录更新库存日志
	public static void fileAddContext(String fileName,String context)
	{
		String path = "d:\\log\\"+fileName;
		File f = new File(path);
		RandomAccessFile accessFile = null;
		
		try
		{
			if(!f.exists()){f.createNewFile();}
			context += "\n";
			accessFile = new RandomAccessFile(path,"rw");
			long fileLength = accessFile.length();
			accessFile.seek(fileLength);
			accessFile.write(context.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
				if(accessFile != null)
				{
					try
					{
						accessFile.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
		}	
	}
	public static String sendGet(String url, Map<String, String> paramsMap, String sign,String timestamp,String APPKEY_SH) {
        String result = "";
        BufferedReader in = null;
        String params = "";
        String urlNameString;
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			params += key+"="+val;
			if(it.hasNext())
				params+="&";
		}
        try {
        	if(params == ""){
        		urlNameString = url;
        	}else{
        		urlNameString = url + params;
        	}

            
          URL realUrl = new URL(urlNameString);
//            Client client = ClientBuilder.newClient();
//            Response response = (Response) client.target(urlNameString).request(MediaType.APPLICATION_JSON_TYPE)
//            .header("app-key", APPKEY_SH)
//            .header("timestamp", timestamp)
//            .header("sign", sign).get();
            // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
             //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("sign",sign);
            connection.setRequestProperty("timestamp",timestamp);
            connection.setRequestProperty("app-key",APPKEY_SH);
             //建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                System.out.println("发送请求,在finally出现异常！" + e2);
            }
        }
        return result;
    }
	
	public static String doPut(String url, Map<String, String> paramsMap, String sign,String timestamp,String APPKEY_SH,int quantity){
		String result = "";
		BufferedReader in = null;
		String params = "";
        String urlNameString;
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			params += key+"="+val;
			if(it.hasNext())
				params+="&";
		}
		if(params == ""){
    		urlNameString = url;
    	}else{
    		urlNameString = url + params;
    	}
		try {
			URL postURL = new URL(urlNameString);
			HttpClient htpClient = new HttpClient();
			HttpURLConnection httpURLConnection = (HttpURLConnection) postURL.openConnection();
			httpURLConnection.setDoOutput(true);                 
			httpURLConnection.setDoInput(true); 
			httpURLConnection.setRequestMethod("PUT"); 
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true); 
			httpURLConnection.setRequestProperty("Content-Type",  "application/json;charset=UTF-8");//json格式上传的模式
			httpURLConnection.setRequestProperty("sign",sign);
			httpURLConnection.setRequestProperty("timestamp",timestamp);
			httpURLConnection.setRequestProperty("app-key",APPKEY_SH);
			JSONObject obj = new JSONObject();
			try
			{
				obj.put("qty",quantity);
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8"));
			out.println(obj);
			out.close();
           // 获取所有响应头字段
           Map<String, List<String>> map = httpURLConnection.getHeaderFields();
           // 遍历所有的响应头字段
           for (String key : map.keySet()) {
               System.out.println(key + "--->" + map.get(key));
           }
           //将字节转为字符流，在包装在bufferReader中，这样可提高速度,在将字符返回
             in = new BufferedReader(new InputStreamReader(httpURLConnection  
                    .getInputStream()));  
            String inputLine; 
            while ((inputLine = in.readLine()) != null) {  
            	result += inputLine;  
            }
            in.close(); 
            httpURLConnection.disconnect();
		} catch (Exception e) {
            System.out.println("发送put请求出现异常！" + e);
            e.printStackTrace();
        }
		return result;
	}
	
	
	
	public static String sendGetM(String url, Map<String, String> paramsMap) {
        String result = "";
        BufferedReader in = null;
        String params = "";
        String urlNameString;
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			params += key+"="+val;
			if(it.hasNext())
				params+="&";
		}
        try {
        	if(params == ""){
        		urlNameString = url;
        	}else{
        		urlNameString = url + params;
        	}
  
          URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
             //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
             //建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

	public static String ofsendPost(String url,String paramsMap)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		try
		{
			URL readUrl = new URL(url);
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数

			out.print(paramsMap);
			
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	
	
	
	
	
	
	public static String sendGetMJson(String url, Map<String, String> paramsMap) {
        String result = "";
        BufferedReader in = null;
        String params = "";
        String urlNameString;
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			params += key+"="+val;
			if(it.hasNext())
				params+="&";
		}
        try {
        	if(params == ""){
        		urlNameString = url;
        	}else{
        		urlNameString = url + params;
        	}
//  System.out.println(urlNameString);
          URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
             //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
             //建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	
	
	public static String sendPostXiYou(String url, Map<String, String> map,String paramsM)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		String urlNameString = "";
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = map.get(key);
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		
        	if(params == ""){
        		urlNameString = url;
        	}else{
        		urlNameString = url + params;
        	}
		
//        	System.out.println(urlNameString);
		try
		{
			URL readUrl = new URL(urlNameString);
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(paramsM);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	
	
	}
	
	
	
	public static String sendPostOrderXiYou(String url, Map<String, String> map,Map<String, Object> mapJson)
	{
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		String urlNameString = "";
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = map.get(key);
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		
        	if(params == ""){
        		urlNameString = url;
        	}else{
        		urlNameString = url + params;
        	}
		
//        	System.out.println(urlNameString);
		try
		{
			URL readUrl = new URL(urlNameString);
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			
			// 发送请求参数
			JSONObject json=new JSONObject(mapJson);
			String order_json = "order_json="+json.toString();
//			System.out.println(order_json);
			out.print(order_json);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	
	
	}
	
	public static String sendGetMLH(String url, Map<String, String> paramsMap) {
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		try
		{
			URL readUrl = new URL(url);
			
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
//			System.out.println(params);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
    }
	
	
	
	
	//if("secret".equals(key)) continue;
	//paramsMap.remove("secret");
	//paramsMap.put("app_key", "xmW6rKJpRyo%3D");
	//URLEncoder.encode("电","utf8")
	public static String sendPostOrderYs(String url, Map<String, String> paramsMap){
		StringBuilder result = new StringBuilder();
		PrintWriter out = null;
		BufferedReader in = null;
		String params = "";
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String val = paramsMap.get(key);
			if("secret".equals(key)) continue;
			params += key + "=" + val;
			if(it.hasNext())
				params+="&";
		}
		try
		{
			URL readUrl = new URL(url);
			URLConnection conn = readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
//			URLDecoder.decode(params,"UTF-8");
			out.print(params);
//        System.out.println(url+params);
			// flush输出流的缓冲
			out.flush();
			
			
		
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result.append(line);
			}

		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println(sdf.format(new Date()) + "sendpost error  url:" + url );
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	
	
}
