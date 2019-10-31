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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.service.autosyn.Result;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;



 


/**
 * @author Mr_Yang   2017-1-12 下午02:35:15
 **/

@Controller
public class EvcardAction
{
	public static String IMG_USED_PATH = "e:/evcard/used/";
	
	public static String IMG_NOT_USED_PATH = "e:/evcard/not_used/";
	
	public static String LOGIN_URL = "http://139.196.248.167:8080/evcard-mas/evcardapp?service=login";
	
	public static String UPDATEIMG_URL = "http://139.196.248.167:8080/evcard-mas/EiModifyImageServlet";
	
	public static String ADDRESS = "上海市宝山区韶山路245弄36号1101-右边水表箱";
	public  ShengHua shengHua = new ShengHua();
	public  RuoKuai ruoKuai = new RuoKuai();
	public SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
	@RequestMapping("/timetask/evcard/toDeal")
	public String toDeal()
	{
		return "/hx/evcard";
	}
	
	
	@RequestMapping("/timetask/evcard/users")
	public String toCheckShenHe(Model model,HttpServletRequest request)
	{
		String sh = request.getParameter("sh");
		if("yes".equals(sh))
		{
			checkShenHe();
		}
		List<String> users = getUsers();
		model.addAttribute("list", users);
		
		return "/hx/users";
	}
	
	
	//检查是否审核通过
	public  void checkShenHe()
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
		 
	}
	
	
	@RequestMapping("/timetask/evcard/regist")
	@ResponseBody
	public Object regist(HttpServletRequest requst)
	{
		String name = requst.getParameter("userName");
		String sfz = requst.getParameter("sfz");
		String url = requst.getParameter("url");
		String wangwangID = requst.getParameter("wangwangID");
		if(StringUtil.isNotBlank(name)) name= name.trim();
		if(StringUtil.isNotBlank(sfz)) sfz= sfz.trim();
		if(StringUtil.isNotBlank(url)) url= url.trim();
		if(StringUtil.isNotBlank(wangwangID)) wangwangID= wangwangID.trim();
		
		/*
		 EvcardDomain evcardDomain = login("15555210264", "jieshuai!", "");
         boolean b = updateImg(evcardDomain);
         if(b)
         {
      	    System.out.println("图片修改成功");
         }
         else
         {
        	  System.out.println("图片修改失败");
         }
         return null;
         */
		return regist(url,name,sfz,wangwangID);
	}
	
	
	public  Result regist(String url,String name,String sfzNum,String wangwangID)
	{
 
		Result resultBack = new Result();
		String fullPath = IMG_USED_PATH + name + " " + sfzNum + ".jpg";
		File f = new File(fullPath);
		if(!f.exists())
		{
			resultBack.setMsg("照片不存在");
			resultBack.setOK(false);
			return resultBack;
		}
		if(!StringUtil.isNotBlank(url))
		{
			resultBack.setMsg("url不能为空");
			resultBack.setOK(false);
			return resultBack;
		}
		else if(!StringUtil.isNotBlank(name))
		{
			resultBack.setMsg("姓名不能为空");
			resultBack.setOK(false);
			return resultBack;
		}
		else if(!StringUtil.isNotBlank(sfzNum))
		{
			resultBack.setMsg("身份证号码不能为空");
			resultBack.setOK(false);
			return resultBack;
		}
		else if(!StringUtil.isNotBlank(wangwangID))
		{
			resultBack.setMsg("旺旺不能为空");
			resultBack.setOK(false);
			return resultBack;
		}
		
		String backStr  ="";
		ruoKuai.typeid = 3060;//6位英数混合
		shengHua.itemId = "86979"; //evard
		
		String qqUserAgnt = "Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; X900 Build/CBXCNOP5500912251S) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.4 TBS/025489 Mobile Safari/533.1 V1_AND_SQ_6.0.0_300_YYB_D QQ/6.0.0.2605 NetType/WIFI WebP/0.3.0 Pixel/1440";
		RequestDomain requestDomain = new RequestDomain();
		requestDomain.setUserAgent(qqUserAgnt);
		requestDomain.setRefere(url);
		requestDomain.setUrl(url);
		requestDomain.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		requestDomain.setContentType("text/html");
		requestDomain.setAccepLanguage("	zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		requestDomain.setAcceptEncoding("gzip, deflate");
		requestDomain.setMethod("get");
		ResponseDomain rd = HttpRequestUtil.sendGetRequest(requestDomain); 
		
		String cookies = rd.getCookies();
	
		Map<String,String> cookiesMap = StringUtil.string2Map(cookies);
		
		String uid = url.split("uid=")[1];
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
        	ResponseDomain postRd = HttpRequestUtil.sendPostRequest(postRq,"UTF-8");
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
     
        
        String imgCode = "";
        int cnt = 1;
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
	    	   //System.out.println("验证码错误或者号码有误");
	    	   cnt++;
	    	   if(cnt >= 10)
	    	   {
	    		   resultBack.setMsg("验证错误10次");
	    		   resultBack.setOK(false);
	    		   return resultBack;
	    	   }
	       }
       }
        
        //2分钟内没有获取到验证 重新获取号码
        String smsStr = shengHua.getMsg(phoneNumber);
        if("".equals(smsStr))
        {
           shengHua.add2Black(phoneNumber);
           resultBack.setMsg("手机号码2分钟内没有获取到验证码");
 		   resultBack.setOK(false);
 		   return resultBack;
        }
        int start = smsStr.indexOf("：");
		int end = smsStr.indexOf("。");
		String smsCode = smsStr.subSequence(start+1, end).toString().trim();
 
     
        
       String checkSmsUrl  = "http://www.evcardchina.com/api/sms/check_key/"+phoneNumber+"?phone_key="+smsCode;
       requestDomain.setUrl(checkSmsUrl);
       ResponseDomain checkSmsRd = HttpRequestUtil.sendGetRequest(requestDomain);
       if(checkSmsRd.getBody().contains("正确"))
       {
    	  //System.out.println("验证码正确,开始提交...");
       }
       else
       {
    	   resultBack.setMsg("手机短信验证码错误");
 		   resultBack.setOK(false);
 		   return resultBack;
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
       List<String> list = getUsers();
       String address = ADDRESS + "-" + list.size();
       String postData = "{\"mobilePhone\":\"" + phoneNumber + "\",\"phone_key\":\"" + smsCode +"\",\"phone_captcha_key\":\"" + imgCode + "\",\"password\":\"jieshuai!\",\"password_confirm\":\"jieshuai!\",\"address\":\""+address+"\",\"userName\":\"" + name + "\",\"authId\":\""+ sfzNum +"\",\"drivingLicenseImgUrl\":\"/system/tmp/17162/1482826838-d0c4e6a3.jpg\",\"uid\":\""+uid +"\"}";
       submitRd.setPostData(postData);
       ResponseDomain submitR = HttpRequestUtil.sendPostRequest(submitRd,"UTF-8");
       if(submitR.getCode() == 442 || submitR.getCode() == 500)
       {
    	   resultBack.setMsg("注册失败,该身份证已注册");
 		   resultBack.setOK(false);
 		   return resultBack;
       }
       else if(submitR.getCode() == 200)
       {
    	   backStr = "注册成功";
    	   String date = sdf.format(new Date());
    	   String fileContent = name + " " + sfzNum + " " + phoneNumber + " jieshuai!" + " " + date + " " + wangwangID + " 请等待管理员审核！" +  "\r\n";
           FileUtil.path = "e:/evcard/";
           FileUtil.zhuijiaWenJian("user.txt", fileContent);
           EvcardDomain evcardDomain = login(phoneNumber, "jieshuai!", "");
           boolean b = updateImg(evcardDomain);
           if(b)
           {
        	   backStr += ",修改图片成功";
           }
           else
           {
        	   backStr += ",修改图片失败";
           }
       }
       else
       {
    	   resultBack.setMsg("注册失败,未知错误 错误码" +  submitR.getCode());
 		   resultBack.setOK(false);
 		   return resultBack;
       }
       
       resultBack.setMsg(backStr);
	   resultBack.setOK(true);
	   return resultBack;
       
	}
	
	
	
	
	//登录
	public  EvcardDomain login(String userName,String password,String ieme)
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
		ResponseDomain rd = HttpRequestUtil.sendPostRequest(requestDomain,"UTF-8"); 
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
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return evcardDomain;
	}
	

	

	//修改图片
	public  boolean updateImg(EvcardDomain evcardDomain)
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
			//System.out.println(fullPath + " 不存在");
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
}
 
