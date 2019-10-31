package com.huaixuan.network.biz.service.autosyn.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.huaixuan.network.biz.service.autosyn.AuToSyncZhenPingManager;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;



/**
 * @author Mr_Yang   2015-11-17 下午03:09:30 弃用
 **/

public class AuToSyncZhenPingManagerImpl implements AuToSyncZhenPingManager
{
	private static final String SHUSERNAME = ""; //国内账号
	private static final String SHPASSWORD = ""; //国内密码
	
	private static final String HKUSERNAME = "";//海外账号
	private static final String HKPASSWORD = "";//海外密码
	
	private static String SHTOKEN = "";//国内token
	private static String HKTOKEN = "";//海外token
	
	private static final String GET_TOKEN_URL = ""; //获取token的URL
	private static final String GET_GOODSINFO_URL = ""; //获取产品信息URL
	private static final String GET_GOODSINFO_DETAIL_URL = "";//获取产品详情URL

	private SimpleDateFormat sfm = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public void autoUpdateSku2Location()
	{
		

	}
	
	//更新珍品海外SKU到本地
	private void updateHKSku2Location()
	{
			
	}

	//获取token sh-国内  hk-海外
	//获取方法见文档
	public void setToken(String type)
	{
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("api_method", "getSecretKey");
		if("hk".equals(type))
		{
			map.put("provider", HKUSERNAME);
			map.put("sign", HKPASSWORD);
		}
		else
		{
			map.put("provider", SHUSERNAME);
			map.put("sign", HKPASSWORD);
		}
		map.put("timestamp", sfm.format(new Date()));
		map.put("version", "2.0");
		map.put("zp_param_json", "{}");
		String result =  HttpRequest.sendPost(GET_TOKEN_URL, map);
		System.out.println(result);
	}
	
	//获取签名 sh-国内  hk-海外  paramJson请求的json数据
	//获取格式见文档
	public String getSign(String type,String paramJson)
	{
		try
		{
			paramJson = URLEncoder.encode(paramJson,"UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		//没有获取TOKEN 先设置TOKEN值 并且缓存(static字段)
		if("".equals(HKTOKEN))
		{
			setToken(type);
		}
		TreeMap<String, String> map = new TreeMap<String, String>();//key按自然顺序排
		if("hk".equals(type))
		{
			map.put("provider", HKUSERNAME);
		}
		else
		{
			map.put("provider", SHUSERNAME);
		}
		map.put("timestamp", sfm.format(new Date()));
		map.put("version", "2.0");
		map.put("zp_param_json", paramJson);

		String params = "";
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
		
		//加上token
		if("hk".equals(type))
		{
			 params += HKTOKEN;
		}
		else
		{
			params += SHTOKEN;
		}
		
		//进行MD5加密
		return HttpRequest.string2MD5(params);
		
	}
	
	@Override
	public void autoUpdateZhenPinStock()
	{
		// TODO Auto-generated method stub

	}

	
	public static void main(String[] args)
	{
		new AuToSyncZhenPingManagerImpl().setToken("hk");
	}
}
 
