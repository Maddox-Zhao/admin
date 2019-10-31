package com.huaixuan.network.web.action.evcard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;



/**
 * @author Mr_Yang   2016-12-27 下午02:00:34
 **/

public class Evcard
{
	public static String IMG_USED_PATH = "e:/evcard/used/";
	
	public static String IMG_NOT_USED_PATH = "e:/evcard/not_used/";
	
	public static String LOGIN_URL = "http://139.196.248.167:8080/evcard-mas/evcardapp?service=login";
	
	public static String UPDATEIMG_URL = "http://139.196.248.167:8080/evcard-mas/EiModifyImageServlet";
	
	public static String ADDRESS = "上海市宝山区韶山路245弄36号1101(右边水表箱)";
	
	public static SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static  RuoKuai ruoKuai = new RuoKuai();
	
	public static ShengHua shengHua = new ShengHua();
	
	public static String uidUrl = "";
	public static String name = "";
	public static String sfzNum = "";
	public static String wangwangID = "";
	
	//修改图片
	public static boolean updateImg(EvcardDomain evcardDomain)
	{
		RequestDomain requestDomain = new RequestDomain();
		 
		requestDomain.setUserAgent("EVCARD/4 CFNetwork/808.0.2 Darwin/16.0.0");
		requestDomain.setRefere("");
		requestDomain.setUrl(UPDATEIMG_URL);
		requestDomain.setContentType("multipart/form-data;boundary=YY");
		requestDomain.setAccept("text/html");
		requestDomain.setAccepLanguage("zh-cn");
		requestDomain.setAcceptEncoding("gzip, deflate");
		requestDomain.setMethod("post");
		requestDomain.setConnection("keep-alive");
		requestDomain.getOtherRquestMap().put("TableKey", evcardDomain.getSfz());
		requestDomain.getOtherRquestMap().put("token", evcardDomain.getToken());
		requestDomain.getOtherRquestMap().put("FileType","0B");
		String fullPath = IMG_USED_PATH + evcardDomain.getDisPlayName() + " " + evcardDomain.getSfz() + ".jpg";
		File f = new File(fullPath);
		if(!f.exists())
		{
			System.out.println(fullPath + " 不存在");
			return false;
		}
		ResponseDomain rd = HttpRequestUtil.sendPost2UploadFile(requestDomain,fullPath); 
		String result = rd.getBody();
		try
		{
			JSONObject json = new JSONObject(result);
			if(json.getString("msg").contains("成功"))
			{
				return true;
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			System.out.println(rd.getBody());
		}
		return false;
	}
	
	//登录
	public static EvcardDomain login(String userName,String password,String ieme)
	{
		if(!StringUtil.isNotBlank(ieme)) ieme = "B2B5696E-4DC0-4747-A8A3-AEDB6DD5FBC9";
		String postData = "{\"password\":\"" + password + "\",\"loginName\":\"" + userName + "\",\"imei\":\"" +  ieme + "\",\"channelId\":\"5326295453519604110\",\"version\":\"2.2.2\",\"token\":\"\",\"appType\":\"1\",\"loginOrigin\":\"0\"}";		
		EvcardDomain evcardDomain = new EvcardDomain();
		RequestDomain requestDomain = new RequestDomain();
		requestDomain.setUserAgent("evcard/2.2.2 (iPhone; iOS 10.0.2; Scale/2.00)");
		requestDomain.setRefere("");
		requestDomain.setUrl(LOGIN_URL);
		requestDomain.setAccept("*/*");
		requestDomain.setContentType("application/json");
		requestDomain.setAccepLanguage("zh-Hans;q=1");
		requestDomain.setAcceptEncoding("gzip, deflate");
		requestDomain.setMethod("post");
		requestDomain.setPostData(postData);
		ResponseDomain rd = HttpRequestUtil.sendPostRequest(requestDomain,""); 
		String result = rd.getBody();
		 
		try
		{
			JSONObject json = new JSONObject(result);
			String token = json.getString("token");
			String authId = json.getString("authId");
			String displayName = json.getString("displayName");
			String msg = json.getString("message");
			evcardDomain.setUserName(userName);
			evcardDomain.setPassword(password);
			evcardDomain.setToken(token);
			evcardDomain.setSfz(authId);
			evcardDomain.setDisPlayName(displayName);
			evcardDomain.setMsg(msg);
			System.out.println(userName + " 登录成功");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			System.out.println(rd.getBody());
		}
		return evcardDomain;
	}
	
	
	//注册
	public static EvcardDomain regist()
	{
		if(!StringUtil.isNotBlank(uidUrl))
		{
			System.out.println("注册url不能为空");
			return null;
		}
		else if(!StringUtil.isNotBlank(name))
		{
			System.out.println("NAME不能为空");
			return null;
		}
		else if(!StringUtil.isNotBlank(sfzNum))
		{
			System.out.println("身份证号码不能为空");
			return null;
		}
		else if(!StringUtil.isNotBlank(wangwangID))
		{
			System.out.println("旺旺不能为空");
			return null;
		}
		
		ruoKuai.typeid = 3060;//6位英数混合
		shengHua.itemId = "86979"; //evard
		shengHua.showLog = true;
		EvcardDomain evcardDomain = new EvcardDomain();
		String qqUserAgnt = "Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; X900 Build/CBXCNOP5500912251S) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.4 TBS/025489 Mobile Safari/533.1 V1_AND_SQ_6.0.0_300_YYB_D QQ/6.0.0.2605 NetType/WIFI WebP/0.3.0 Pixel/1440";
		RequestDomain requestDomain = new RequestDomain();
		requestDomain.setUserAgent(qqUserAgnt);
		requestDomain.setRefere(uidUrl);
		requestDomain.setUrl(uidUrl);
		requestDomain.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		requestDomain.setContentType("text/html");
		requestDomain.setAccepLanguage("	zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		requestDomain.setAcceptEncoding("gzip, deflate");
		requestDomain.setMethod("get");
		ResponseDomain rd = HttpRequestUtil.sendGetRequest(requestDomain); 
		
		String cookies = rd.getCookies();
	
		Map<String,String> cookiesMap = StringUtil.string2Map(cookies);
		
		String uid = uidUrl.split("uid=")[1];
		String uidUrl = "http://www.evcardchina.com/registration/mobile/step1?uid=" + uid;
		requestDomain.setRefere(uidUrl);
		requestDomain.setUrl(uidUrl);
		requestDomain.setMethod("get");
		rd = HttpRequestUtil.sendGetRequest(requestDomain); 
		
	    
		
		String xsrfToken = cookiesMap.get("XSRF-TOKEN");
    	try
		{
			xsrfToken = java.net.URLDecoder.decode(xsrfToken,   "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		//验证手机号码是否存在
        String phoneNumber = "";
        while(true)
        {
        	phoneNumber = shengHua.getPhoneNumber("1", "上海;");
        	if(phoneNumber.contains("170"))
        	{
        		shengHua.add2Black(phoneNumber);
        		continue;
        	}
        	String check = "http://www.evcardchina.com/api/proxy/check_unique";
        	String postData = "{\"type\":1,\"dataValue\":\"" + phoneNumber +"\"}";
        	RequestDomain postRq = new RequestDomain();
        	postRq.setMethod("post");
        	postRq.setCookies(cookies);
        	postRq.setUrl(check);
        	postRq.setAccept("application/json, text/plain");
        	postRq.setAcceptEncoding("gzip, deflate");
        	postRq.setAccepLanguage("zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        	postRq.getOtherRquestMap().put("X-XSRF-TOKEN",xsrfToken);
        	postRq.getOtherRquestMap().put("X-Requested-With","XMLHttpRequest");
        	postRq.setPostData(postData);
        	postRq.setContentType("application/json;charset=utf-8");
        	postRq.setRefere(uidUrl);
        	postRq.setUserAgent(qqUserAgnt);
        	ResponseDomain postRd = HttpRequestUtil.sendPostRequest(postRq,"");
        	try
			{
				JSONObject json = new JSONObject(postRd.getBody());
				String result = json.getString("result");
				if("0".equals(result))
				{
					break;
				}
				else
				{
					shengHua.add2Black(phoneNumber);
				}
				
			}
			catch (JSONException e)
			{
 
				e.printStackTrace();
			}
        }
     
        System.out.println("开始获取验证码....");
        String imgCode = "";
        while(true)
        {
	        String sysTime = System.currentTimeMillis()+"";
	        String imgUrl = "http://www.evcardchina.com/captcha?i="+sysTime;
			requestDomain.setRefere(uidUrl);
			requestDomain.setUrl(imgUrl);
			requestDomain.setCookies(cookies);
			requestDomain.setMethod("get");
			HttpRequestUtil.saveImage2Location(requestDomain,"e:/evcard/code",uid + "_code.png");
				
			String filepath = "e:/evcard/code/" + uid + "_code.png";
	        imgCode = ruoKuai.getVerCodeByFile(filepath);
	        /*
	       System.out.print("请输入验证码:");  
		   BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
		   try
		   {
			   imgCode = strin.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			*/
	       String sendSmsUrl = "http://www.evcardchina.com/api/sms/send_sms/"+phoneNumber+"?captcha=" + imgCode;
	       requestDomain.setUrl(sendSmsUrl);
	       requestDomain.getOtherRquestMap().put("X-XSRF-TOKEN",xsrfToken);
	       requestDomain.getOtherRquestMap().put("X-Requested-With","XMLHttpRequest");
	       requestDomain.setAccept("application/json, text/plain");
	       requestDomain.setCookies(cookies);
	       ResponseDomain smsRd = HttpRequestUtil.sendGetRequest(requestDomain);
	       if(smsRd.getCode() == 200)
	       {
	    	   break;
	       }
	       else
	       {
	    	   System.out.println("验证码错误或者号码有误");
	       }
       }
        System.out.println("验证码正确,验证短信....");
        String smsStr = shengHua.getMsg(phoneNumber);
        int start = smsStr.indexOf("：");
		int end = smsStr.indexOf("。");
		String smsCode = smsStr.subSequence(start+1, end).toString().trim();
 
        //2分钟内没有获取到验证 重新获取号码
        if("".equals(smsCode))
        {
        	shengHua.add2Black(phoneNumber);//
        	regist();
        }
        
       String checkSmsUrl  = "http://www.evcardchina.com/api/sms/check_key/"+phoneNumber+"?phone_key="+smsCode;
       requestDomain.setUrl(checkSmsUrl);
       ResponseDomain checkSmsRd = HttpRequestUtil.sendGetRequest(requestDomain);
       if(checkSmsRd.getBody().contains("正确"))
       {
    	   System.out.println("验证码正确,开始提交...");
       }
       else
       {
    	   System.out.println("短信验证码错误,提交验证码为:" + smsCode);
    	   return null;
       }
      
      // String dirImg = "/system/tmp/17162/1482832465-554fffb0.jpg";
       
       String submitUrl = "http://www.evcardchina.com/api/proxy/insert_user";
       RequestDomain submitRd = new RequestDomain();
       submitRd.setUrl(submitUrl);
       submitRd.setMethod("post");
       submitRd.setAccepLanguage("zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
       submitRd.setAccept("application/json, text/plain");
       submitRd.setAcceptEncoding("gzip, deflate");
       submitRd.setContentType("application/json;charset=utf-8");
       submitRd.setRefere(uidUrl);
       submitRd.getOtherRquestMap().put("X-XSRF-TOKEN",xsrfToken);
       submitRd.getOtherRquestMap().put("X-Requested-With","XMLHttpRequest");
       submitRd.setCookies(cookies);
       submitRd.setUserAgent(qqUserAgnt);
       //String userName = "顾大锋";
       //String sfz = "320681197909141014";
       String postData = "{\"mobilePhone\":\"" + phoneNumber + "\",\"phone_key\":\"" + smsCode +"\",\"phone_captcha_key\":\"" + imgCode + "\",\"password\":\"jieshuai!\",\"password_confirm\":\"jieshuai!\",\"address\":\""+ADDRESS+"\",\"userName\":\"" + name + "\",\"authId\":\""+ sfzNum +"\",\"drivingLicenseImgUrl\":\"/system/tmp/17162/1482826838-d0c4e6a3.jpg\",\"uid\":\""+uid +"\"}";
       System.out.println(postData);
       submitRd.setPostData(postData);
       ResponseDomain submitR = HttpRequestUtil.sendPostRequest(submitRd,"");
       if(submitR.getCode() == 200)
       {
    	   System.out.println("注册成功,开始修改图片");
    	   String date = sdf.format(new Date());
    	   String fileContent = name + " " + sfzNum + " " + phoneNumber + " jieshuai!" + " " + date + " " + wangwangID + " 请等待管理员审核！" +  "\r\n";
           FileUtil.path = "e:/evcard/";
           FileUtil.zhuijiaWenJian("user.txt", fileContent);
           evcardDomain = login(phoneNumber, "jieshuai!", "");
           boolean b = updateImg(evcardDomain);
           if(b)
           {
        	   System.out.println("修改图片成功!");
           }
           else
           {
        	   System.out.println("修改图片失败 " + phoneNumber);
           }
       }
       else
       {
    	   System.out.println(submitR.getResposeLog());
    	   System.out.println("注册失败");
       }
      
       
       return evcardDomain;
	}
	
	
	//获取当前用户
	public static List<String> getUsers()
	{
		List<String> list = new ArrayList<String>();
		File f = new File("e:/evcard/user.txt");
		if(!f.exists()) return list;
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String line = "";
			while((line = br.readLine()) != null)
			{
				if(StringUtil.isNotBlank(line))
				{
					 list.add(line);
				}
			}
		}
		catch (Exception e)
		{
			 
			e.printStackTrace();
		}
		finally
		{
			
			try
			{
					if(fr != null) fr.close();
					if(br != null) br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	//检查是否审核通过
	public static void checkShenHe()
	{
		List<String> users = getUsers();
		String resultStr = "";
		for(String user : users)
		{
			String[] userArr = user.split(" ");
			String tmpUser = "";
			String status = userArr[userArr.length-1];
			for(int i = 0; i < userArr.length; i++)
			{
				if(i <= userArr.length-2)
					tmpUser += userArr[i] + " ";
			}
			String msg = "审核通过";
			if(!status.contains("审核通过"))
			{
				String phone = userArr[2];
				EvcardDomain ed = login(phone, "jieshuai!", "");
				msg = ed.getMsg();
				msg = msg.trim();
			}
			
			if("".equals(msg))
			{
				msg = "审核通过";
			}
			else if(msg.contains("会员卡"))
			{
				msg = "审核通过";
			}
			resultStr = resultStr + tmpUser + msg + "\r\n"; 
		}
		if(StringUtil.isNotBlank(resultStr))
		{
			FileUtil.path =  "e:/evcard/";
			FileUtil.chongxieWenJian("user.txt", resultStr);
		}
		System.out.println("检测完成!");
	}
	
	
	public static void main(String[] args) throws Exception
	{
		//Evcard.uidUrl = "http://www.evcardchina.com/register/mobile?uid=b7c05da893d341949e3624d2675cb41bb5ed958174a54c7c9a4afcb4f8b8085b";
		Evcard.uidUrl = "";
		Evcard.name = "";
		Evcard.sfzNum = "";
		Evcard.wangwangID = "";
		List<String> list = getUsers();
		//Evcard.ADDRESS = "上海市奉贤区钱桥社区长丰路71号(熟食店)";
		Evcard.ADDRESS = "上海市宝山区韶山路245弄36号1101-右边水表箱"+list.size();
		
		//regist();
		
		
		checkShenHe();

		 /*
		EvcardDomain ed = login("15546113673", "jieshuai!", "");
		System.out.println(ed.getMsg());
		boolean success = updateImg(ed);
		System.out.println(success);
		*/
	 
		
		
		

	
	}
}
 
