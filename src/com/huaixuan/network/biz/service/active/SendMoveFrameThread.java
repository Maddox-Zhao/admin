package com.huaixuan.network.biz.service.active;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

import com.huaixuan.network.biz.service.user.MailEngine;

public class SendMoveFrameThread implements Runnable
{
private final Map<String, List<String>> sendMap;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private MailEngine mailEngine;
	
	private String title;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public SendMoveFrameThread(Map<String, List<String>> sendMap,MailEngine mailEngine,String title)
	{
		this.sendMap = sendMap;
		this.mailEngine = mailEngine;
		this.title = title;
	}
	
	@Override
	public void run()
	{
		// 发送邮件
		Set<Entry<String, List<String>>> set = sendMap.entrySet();
		Iterator<Entry<String, List<String>>> it = set.iterator();
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		String date = simpleDateFormat.format(new Date());
		while (it.hasNext())
		{
			String email = it.next().getKey();
//			simpleMailMessage.setTo("328195773@qq.com");
			simpleMailMessage.setTo(email);
			simpleMailMessage.setSubject(date + title);
			
			List<String> list = sendMap.get(email);
			StringBuffer st = new StringBuffer();
			for (String s : list)
			{
				st.append(s);
			}
			simpleMailMessage.setText(st.toString());
			
			synchronized (mailEngine)
			{
				mailEngine.send(simpleMailMessage);
				try
				{
					Thread.sleep(15000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
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
