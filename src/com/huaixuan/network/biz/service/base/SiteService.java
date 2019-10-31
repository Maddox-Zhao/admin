package com.huaixuan.network.biz.service.base;

import java.util.Map;

import com.huaixuan.network.biz.domain.base.Site;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2016-6-22 下午04:06:58
 **/

public interface SiteService
{
	public MiniUiGrid getSiteByMap(Map<String,String> searMap);
	
	public Integer getMaxIdSiteByType(String type);
	
	public Site getSiteByIdSite(String idSite);
	
	public void insertSite(Site site);
	
	//修改站点名和地址
	
	public void updateIdSiteName(Site site);
}
 
