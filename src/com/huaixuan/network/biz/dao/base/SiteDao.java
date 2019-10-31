package com.huaixuan.network.biz.dao.base;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.base.Site;



/**
 * @author Mr_Yang   2016-6-22 下午04:02:01
 **/

public  interface SiteDao
{
	public List<Site> getSiteByMap(Map<String,String> searMap);
	
	public int getSiteByMapCount(Map<String,String> searMap);
	
	public Site getSiteByIdSite(String idSite);
	
	public Integer getMaxIdSiteByType(String type);
	
	public void insertSite(Site site);
    //修改站点名
	public void updateIdSite(Site site);
	
}
 
