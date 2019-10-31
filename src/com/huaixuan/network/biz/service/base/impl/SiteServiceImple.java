package com.huaixuan.network.biz.service.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.base.SiteDao;
import com.huaixuan.network.biz.domain.base.Site;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.base.SiteService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2016-6-22 下午04:07:41
 **/

@Service("siteService")
public class SiteServiceImple implements SiteService
{

	@Autowired
	private SiteDao siteDao;
	
	@Override
	public MiniUiGrid getSiteByMap(Map<String, String> searchMap)
	{
		QueryPage queryPage = new QueryPage(new Object());
		int count = siteDao.getSiteByMapCount(searchMap);
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理

			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageSize()+"");
			List<Site> list = siteDao.getSiteByMap(searchMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public Integer getMaxIdSiteByType(String type)
	{
		 
		return siteDao.getMaxIdSiteByType(type);
	}
	
	public void insertSite(Site site)
	{
		siteDao.insertSite(site);
	}

	@Override
	public Site getSiteByIdSite(String idSite)
	{
		return siteDao.getSiteByIdSite(idSite);
	}

	//修改站点和地址
	@Override
	public void updateIdSiteName(Site site){
		
		 siteDao.updateIdSite(site);
	}
}
 
