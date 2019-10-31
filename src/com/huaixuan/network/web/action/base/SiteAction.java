package com.huaixuan.network.web.action.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.bean.conf.loader.annotation.handler.ConditionRef;

import com.huaixuan.network.biz.domain.base.Site;
import com.huaixuan.network.biz.service.base.SiteService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-6-22 下午04:18:29
 **/

@Controller
@RequestMapping("/site")
public class SiteAction
{
	@Autowired
	private SiteService siteService;
	
	
	@RequestMapping("/list")
	public @ResponseBody MiniUiGrid searchSiteByMap(HttpServletRequest request)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		return siteService.getSiteByMap(searchMap);
	}
	
	
	@RequestMapping("/toList")
	public String toSearchSiteByMap(HttpServletRequest request)
	{
		return  "/base/siteList";
	}
	
	@RequestMapping("/toAddSite")
	public String toAddSite(HttpServletRequest request)
	{
		return  "/base/addSite";
	}
	
	//获取idSite
	@RequestMapping("/getMaxIdSite")
	public @ResponseBody Object getMaxIdSite(HttpServletRequest request)
	{
		 return siteService.getMaxIdSiteByType(request.getParameter("type"));
	}
	
	@RequestMapping("/addSite")
	public @ResponseBody Object insertSite(HttpServletRequest request)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		Site site = (Site)MiniUiUtil.Map2Object(requestMap, Site.class);
		siteService.insertSite(site);
		return "ok"; 
	}
	
	//修改站点名
	
	@RequestMapping("/editSite")
	
	public @ResponseBody Object updateSite(HttpServletRequest request,HttpServletResponse respon){
		
		String idSiteAndName = request.getParameter("idSiteAndName");
		
		if(StringUtil.isBlank("idSiteAndName") || StringUtil.isEmpty(idSiteAndName)){
			  return 0;
		}
		
		String[] str = idSiteAndName.split(";");
		for(String s:str){
			String[] st = idSiteAndName.split(":");
			
				String idSitet = st[0];
			    String name = st[1];
			    if(StringUtil.isBlank(idSitet) || StringUtil.isEmpty(idSitet))continue;
			    Long idSite=null;
			    try {
					idSite = Long.valueOf(idSitet);
				} catch (Exception e) {
					continue;
				}
			    
			    Site site = new Site();
			    site.setName(name);
			    site.setIdSite(idSite);
			  
			    siteService.updateIdSiteName(site);
			
		}
		
		return "ok";
	}
	
	//修改站点地址
     @RequestMapping("/editAddress")
	public @ResponseBody Object editAddress(HttpServletRequest request,HttpServletResponse respon){
		
		String idSiteAndName = request.getParameter("idSiteAndName");
		
		if(StringUtil.isBlank("idSiteAndName") || StringUtil.isEmpty(idSiteAndName)){
			  return 0;
		}
		
		String[] str = idSiteAndName.split(";");
		for(String s:str){
			String[] st = idSiteAndName.split(":");
			
				String idSitet = st[0];
			   String address = st[1];
			    if(StringUtil.isBlank(idSitet) || StringUtil.isEmpty(idSitet))continue;
			    Long idSite=null;
			    try {
					idSite = Long.valueOf(idSitet);
				} catch (Exception e) {
					continue;
				}
			    if(StringUtil.isBlank(address) || StringUtil.isEmpty(address)){
			    	 address="";
			    }
			    Site site = new Site();
			   
			    site.setIdSite(idSite);
			    site.setAddress(address);
			    siteService.updateIdSiteName(site);
			
		}
		
		return "ok";
	}
	
	
	
}
 
