package com.huaixuan.network.web.action.evcard;






/**
 * @author Mr_Yang   2016-11-7 上午10:42:32
 **/

public class ShengHua
{
	public  static String USER_NAME = "xt_yangjie";
	public static String USER_PASSWORD = "jieshuai!";
	public static String API_URL = "http://api.shjmpt.com:9002/pubApi";
	public static String token = "0J2i5RofNU3hUV7VCOIvAZRrwhDnEp21";
	public  String itemId = "112"; //友宝
	
	public boolean showLog = false;	
	public ShengHua(String itemId)
	{
		this.itemId = itemId;
		setToken();
	}
	public ShengHua()
	{
		setToken();
	}

	private  void setToken()
	{
		String sendUrl = API_URL + "/uLogin?uName=" + USER_NAME+ "&pWord=" + USER_PASSWORD;
		String resultStr = HttpRequestUtil.getURLContent(sendUrl);
		if(resultStr.contains("False") == false)
		{
			String[] arr = resultStr.split("&");
			token = arr[0];
		}
		else
		{
			System.out.println(resultStr);
		}
	}
	
	/**
	 * 
	 * @param phoneType 运营商 [不填为 0] 0 [随机] 1 [移动] 2 [联通] 3 [电信]
	 * @param area 	区域 [不填则 随机]
	 * @param phoneNumber 手机号
	 * @return
	 */
	public String getPhoneNumber(String phoneType,String area,String phoneNumber)
	{
		String sendUrl = API_URL + "/GetPhone?ItemId="+itemId+"&token=" + token ;
		if(!"".equals(phoneNumber))
		{
			sendUrl = sendUrl + "&Phone=" + phoneNumber;
		}

		if(StringUtil.isNotBlank(phoneType))
		{
			sendUrl = sendUrl + "&PhoneType" + phoneType;
		}
		if(StringUtil.isNotBlank(area))
		{
			sendUrl = sendUrl + "&Area" + area;
		}
		String resultStr =  HttpRequestUtil.getURLContent(sendUrl);
		if(resultStr.contains("Session") == true)
		{
			setToken();
			return getPhoneNumber(phoneType,area,phoneNumber);
		}
		else if(resultStr.contains("False") == true)
		{
			System.out.println(resultStr);
			return "";
		}
		return resultStr.replace(";", "");
	}
	
	public String getPhoneNumber(String phoneType,String area)
	{
		return  getPhoneNumber(phoneType, area,"");
	}
	
	
	
	/**
	 * 获取验证码
	 * @param phoneNumber
	 * @return
	 */
	public String getMsg(String phoneNumber)
	{
		String sendUrl = API_URL + "/GMessage?ItemId="+itemId+"&token=" + token + "&Phone="+phoneNumber;
		int cnt = 0;
		while(true)
		{
			String resultStr = HttpRequestUtil.getURLContent(sendUrl);
			if(resultStr.contains("Session") == true)
			{
				setToken();
				return getMsg(phoneNumber);
			}
			else if(resultStr.contains("请5秒后再试试") == true)
			{
				if(showLog)
				{
					System.out.println(resultStr);
				}
				cnt ++ ;
				 try
				{
					Thread.sleep(5*1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			else if(resultStr.contains("False") == true)
			{
				return "";
			}
			else
			{
				
				String[] arr = resultStr.split("&");
				//MSG&46060&13083973971&【HIGO】721457（您的HIGO验证码，十分钟内有效）
				String msg = arr[3];
				return msg;
			}
			//最多20次
			if(cnt >= 10)
			{
				if(showLog)
					System.out.println("号码: " + phoneNumber + " 获取了20次还没验证码,跳过,换一下个号码");
				return "";
			}
			
		}
	}
	
	
	public void releasePhoneNumber(String phoneNumber)
	{
		
		String sendUrl = API_URL + "/ReleasePhone?" + "token=" + token + "&phoneList="+phoneNumber+"-"+itemId;
		String resultStr = HttpRequestUtil.getURLContent(sendUrl);
		if("ok".equalsIgnoreCase(resultStr))
		{
			if(showLog)
			System.out.println("释放号码:" + phoneNumber + " 成功");
		}
		else
		{
			if(showLog)
			System.out.println("释放号码:" + phoneNumber + " 失败");
		}
	}
	
	
	public void releaseAllPhoneNumber()
	{
		
		String sendUrl = API_URL + "/ReleaseAllPhone?" + "token=" + token;
		String resultStr = HttpRequestUtil.getURLContent(sendUrl);
		if("ok".equalsIgnoreCase(resultStr))
		{
			if(showLog)
			System.out.println("释放号码所有号码成功");
		}
		else
		{
			if(showLog)
			System.out.println("释放号码释放失败:" + resultStr);
		}
	}
	public void add2Black(String phoneNumber)
	{
		String sendUrl = API_URL + "/AddBlack?" + "token=" + token + "&phoneList="+itemId+"-"+phoneNumber;
		String resultStr = HttpRequestUtil.sendGetRquest(sendUrl);
		if("ok".equalsIgnoreCase(resultStr))
		{
			if(showLog)
			System.out.println("号码: " + phoneNumber + " 加入黑名单成功");
		}
		else
		{
			if(showLog)
			System.out.println("号码: " + phoneNumber + " 加入黑名单失败");
		}
		
	}
	
	
	
	public static void main(String[] args)
	{
 
		ShengHua sh = new ShengHua();
		sh.showLog = true;
		sh.releaseAllPhoneNumber();
		//获取号码
		String phoneNumber = sh.getPhoneNumber("", "","17060714340");
		if(StringUtil.isNotBlank(phoneNumber))
		{
			System.out.println("成功获取号码："  + phoneNumber + "\r\n 开始获取短信....");
			System.out.println(sh.getMsg(phoneNumber));
		}
		else
		{
			System.out.println("没有该号码");
		}
		
	 
 
	  
		
	}
	
 
}
 
