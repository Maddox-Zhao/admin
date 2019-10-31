package com.huaixuan.network.biz.service.active;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.goods.SendMailInfo;
import com.huaixuan.network.biz.service.user.MailEngine;

public class SendMailThread implements Runnable
{	

	private final Map<String, List<SendMailInfo>> sendMap;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private MailEngine mailEngine;
	
	private String title;
	
	private String templetName;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public SendMailThread(Map<String, List<SendMailInfo>> sendMap,MailEngine mailEngine,String title,String templtName)
	{
		this.sendMap = sendMap;
		this.mailEngine = mailEngine;
		this.templetName = templtName;
		this.title = title;
	}
	
	@Override
	public void run()
	{
		// 发送邮件
		Set<Entry<String, List<SendMailInfo>>> set = sendMap.entrySet();
		Iterator<Entry<String, List<SendMailInfo>>> ite = set.iterator();
		String date = simpleDateFormat.format(new Date());
		while (ite.hasNext())
		{
			String email = ite.next().getKey();
			if(email == null || !isEmail(email))
				continue;
			List<SendMailInfo> list = sendMap.get(email);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("list", list);
			 try
			{
				 synchronized(mailEngine)
				 {
//					 mailEngine.sendMessage("328195773@qq.com", date+title, templetName, map);
					 mailEngine.sendMessage(email, date+title, templetName, map);
					 Thread.sleep(15000);
					 
				 }
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.info("send to " + email + " failed");
				log.error(e);
			}
		}
	}
	
	
	/**
	 * 
	 * @param email 待验证的Email
	 * @return  
	 */
	private boolean isEmail(String email)
	{
		 String regex ="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, email);
	}

	/**
	* @param regex
	*            正则表达式字符串
	* @param str
	*            要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	private  boolean match(String regex, String str)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}
