package com.huaixuan.network.biz.domain.api;

import java.util.Date;



/**
 * @author Mr_Yang   2015-12-3 下午02:51:21
 * 记录用户访问次数
 **/

public class UserAccessTimes
{
	private String userKey;//用户
	private int timesMin; //每分钟访问的次数
	private Date lastAccessDate; //上次访问时间
	public String getUserKey()
	{
		return userKey;
	}
	public void setUserKey(String userKey)
	{
		this.userKey = userKey;
	}
	public int getTimesMin()
	{
		return timesMin;
	}
	public void setTimesMin(int timesMin)
	{
		this.timesMin = timesMin;
	}
	public Date getLastAccessDate()
	{
		return lastAccessDate;
	}
	public void setLastAccessDate(Date lastAccessDate)
	{
		this.lastAccessDate = lastAccessDate;
	}
	
	
	
	
}
 
