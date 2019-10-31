package com.huaixuan.network.biz.dao.base.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.base.SiteDao;
import com.huaixuan.network.biz.domain.base.Site;



/**
 * @author Mr_Yang   2016-6-22 下午04:03:11
 **/

@Repository("siteDao")
public class SiteDaoIbatis implements SiteDao
{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public List<Site> getSiteByMap(Map<String, String> searMap)
	{
		return sqlMapClient.queryForList("selectSitesByPage",searMap);
	}
	
	@Override
	public Site getSiteByIdSite(String idSite)
	{
		Object o = sqlMapClient.queryForObject("selectSiteByIdSite",idSite);
		if(o != null) return (Site)o;
		return  null;
	}

	@Override
	public int getSiteByMapCount(Map<String, String> searMap)
	{
		Object o = sqlMapClient.queryForObject("selectSitesByPageCount", searMap);
		if(o != null) return (Integer)o;
		return 0;
	}

	@Override
	public Integer getMaxIdSiteByType(String type)
	{
		return (Integer)sqlMapClient.queryForObject("getMaxIdSiteByType",type);
	}

	@Override
	public void insertSite(Site site)
	{
		 
		sqlMapClient.insert("insertSite", site);
	}
	
	
	//修改站点名和地址
	@Override
	public void updateIdSite(Site site){
		
		sqlMapClient.update("updateSite",site);
	}
	
}
 
