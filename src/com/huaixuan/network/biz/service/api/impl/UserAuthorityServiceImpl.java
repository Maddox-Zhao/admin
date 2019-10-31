package com.huaixuan.network.biz.service.api.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.domain.api.ResponseData;
import com.huaixuan.network.biz.domain.api.UserAccessTimes;
import com.huaixuan.network.biz.enums.EnumApiResponseCode;
import com.huaixuan.network.biz.service.api.UserAuthorityService;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;



/**
 * @author Mr_Yang   2015-12-3 下午02:11:23
 **/
@Service("userAuthorityService")
public class UserAuthorityServiceImpl implements UserAuthorityService
{
	//用户存于文本文件 不存数据库
	private final static String USERFILEPATH   = "d:/api_users.txt";
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//记录用户每分钟访问次数
	private Map<String,UserAccessTimes> userAccessTimesMap = new HashMap<String, UserAccessTimes>();
	
	private static final String CHARSET = "UTF-8"; //请求参数已UTF-8格式编码
	
	private static final int ACCESS_TIMES_PER_MIN = 30;//允许每分钟访问30次
	
	/**
	 * 加载用户
	 */
	private Map<String,String> loadUserMap()
	{
		Map<String,String> usersMap = new HashMap<String, String>();
		try
		{
			File f = new File(USERFILEPATH);
			if(!f.exists())
			{
				f.createNewFile();
			}
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while((line = br.readLine()) != null)
			{
				String[] arr = line.split(",");
				usersMap.put(arr[0], arr[1]);
			}
			fr.close();
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return usersMap;
	}

	@Override
	public ResponseData validataUser(HttpServletRequest request)
	{
		
		Map<String,String> existsUsersap = loadUserMap();//加载用户
		ResponseData responseData = new ResponseData();//返回数据
		
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request); //讲请求参数转为map
		String userKey = requestMap.get("app_key");
		String requestStr = requestMap.get("request");
		String timestamp = requestMap.get("timestamp");
		String sign = requestMap.get("sign");
		long currDateTime = System.currentTimeMillis(); //当前访问时间
		
		//时间范围内不可查
//      try {
//					
//	   	 	Date date=new Date();
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String time=format.format(date);  //系统当前时间
//			String startTime = time.substring(0, 10)+" 08:00:00";            //系统可查询开始时间
//			String endTime = time.substring(0, 10)+" 22:00:00";               //系统可查询借宿时间
//			
//			Long longStartTime = format.parse(startTime).getTime();  //系统当前时间的时间戳
//			Long longTime = format.parse(time).getTime();            //系统可查询开始时间戳
//			Long longEndTime = format.parse(endTime).getTime();      //系统可查询结束时间戳
//			
//	             
//		    if(longTime>longStartTime && longTime<longEndTime){
//		    	responseData.setResponseMsg(EnumApiResponseCode.TIME_BUSY.getValue());
//		    	responseData.setResponseCode(EnumApiResponseCode.TIME_BUSY.getKey());
//		    	return responseData;
//		    }
//		} catch (Exception e) {
//			responseData.setResponseMsg(EnumApiResponseCode.TIME_BUSY.getValue());
//	    	responseData.setResponseCode(EnumApiResponseCode.TIME_BUSY.getKey());
//			return responseData;
//		}

		////验证参数是否都有app_key request timestamp sign 都必须要  
		if(userKey == null || requestStr == null || timestamp == null || sign == null)
		{
			responseData.setResponseCode(EnumApiResponseCode.REQUEST_ERROR.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.REQUEST_ERROR.getValue());
			return responseData;
		}
		//参数都存在 在依次验证是否正确
		
		//没有此用户
		if(existsUsersap.get(userKey) == null)
		{	
			responseData.setResponseCode(EnumApiResponseCode.NO_USER.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.NO_USER.getValue());
			return responseData;
		}
		//验证json格式是否正确
		try
		{
			new JSONObject(requestStr);
		}
		catch (JSONException e1)
		{
			//请求中的json错误
			responseData.setResponseCode(EnumApiResponseCode.JSON_ERROR.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.JSON_ERROR.getValue());
			return responseData;
		}
		
		//验证时间戳 eg:2015-03-09 11:45:00
		try
		{
			Date requestDate = sf.parse(timestamp);
			
			long requestDateTime = requestDate.getTime();
			
			long min = (currDateTime-requestDateTime) / (1000 * 60);
			if(min > 5)
			{
				//请求时间和服务器时间超过5分钟  防止重复提交
				responseData.setResponseCode(EnumApiResponseCode.TIME_OVER_FIVE_MIN.getKey());
				responseData.setResponseMsg(EnumApiResponseCode.TIME_OVER_FIVE_MIN.getValue());
				return responseData;
			}
			
		}
		catch (ParseException e)
		{
			//时间转换错误
			responseData.setResponseCode(EnumApiResponseCode.TIME_ERROR.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.TIME_ERROR.getValue());
			return responseData;
		}
		
		//不用MD5 签名认证
		/*
		try
		{
			
			String md5_sign = "app_key="+URLDecoder.decode(userKey,CHARSET)+"&"
									+"request="+URLDecoder.decode(requestStr,CHARSET)+"&"
									+"timestamp="+URLDecoder.decode(timestamp,CHARSET)
									+"_"+existsUsersap.get(userKey);
			String serveSign = HttpRequest.string2MD5(md5_sign);
			if(!serveSign.equalsIgnoreCase(sign))
			{
				//签名错误
				responseData.setResponseCode(EnumApiResponseCode.SIGN_ERROR.getKey());
				responseData.setResponseMsg(EnumApiResponseCode.SIGN_ERROR.getValue());
				return responseData;
			}
			
		}
		catch (UnsupportedEncodingException e)
		{
			//签名错误
			responseData.setResponseCode(EnumApiResponseCode.SIGN_ERROR.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.SIGN_ERROR.getValue());
			return responseData;
		}
		*/
		
		
		
		//验证成功  记录每分钟访问次数
		UserAccessTimes u = userAccessTimesMap.get(userKey);
		if(u == null)
		{
			u = new UserAccessTimes();
			//第一次访问
			u.setLastAccessDate( new Date(currDateTime));
			u.setTimesMin(1);
			u.setUserKey(userKey);
		}
		else
		{
			Date lastAcceTime = u.getLastAccessDate();//上次访问时间
			int timesPer = u.getTimesMin(); //每分钟访问次数
			long sec =  (currDateTime - lastAcceTime.getTime()) / 1000; //距离上一次访问时间多少秒
			if(sec > 60)
			{
				u.setLastAccessDate( new Date(currDateTime));
				u.setTimesMin(1);//超过了1分钟,重新更新为第一次访问
			}
			else
			{
				timesPer++; 
				if(timesPer > ACCESS_TIMES_PER_MIN)
				{
					//签名错误
					responseData.setResponseCode(EnumApiResponseCode.OVER_PER_MIN.getKey());
					responseData.setResponseMsg(EnumApiResponseCode.OVER_PER_MIN.getValue());
					return responseData;
				}
				u.setTimesMin(timesPer);//每分钟访问次数(不会超过ACCESS_TIMES_PER_MIN)
			}
			
		}
		userAccessTimesMap.put(userKey, u);

		return responseData;
	}
	
	
	@Override
	public ResponseData validataUserJsonArray(HttpServletRequest request)
	{
			ResponseData responseData = new ResponseData();//返回数据
//			String timestamp = request.getParameter("timestamp");
//			String edition = request.getParameter("v");
//			if(edition == null || timestamp == null)
//			{
//				responseData.setResponseCode(EnumApiResponseCode.REQUEST_ERROR.getKey());
//				responseData.setResponseMsg(EnumApiResponseCode.REQUEST_ERROR.getValue());
//				return responseData;
//			}
		return responseData;
	} 
}
 
