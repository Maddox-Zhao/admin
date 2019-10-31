package com.huaixuan.network.web.action.base;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.service.base.MiniuiBaseService;



/**
 * @author Mr_Yang   2015-11-23 ����05:05:21
 **/

@Controller
public class MiniuiBaseAction
{
	@Autowired
	private MiniuiBaseService miniuiBaseService;
	
	
	@RequestMapping("/site/getSiteByType")
	public @ResponseBody Object getSites(HttpServletRequest request,AdminAgent adminAgent)
	{
		String type = request.getParameter("type");
		String name = adminAgent.getUsername();
		if(name.equals("30001")){
			if(null == type || "".equals(type)) type = "1";
			return miniuiBaseService.getSiteWhereMenDian(type);
		}
		if(null == type || "".equals(type)) type = "1";
		return miniuiBaseService.getSiteByType(type);
	}
	
	
	@RequestMapping("/sell/getAllSellChannel")
	public @ResponseBody Object getAllSellChannel(HttpServletRequest request)
	{
		return miniuiBaseService.getAllSellChannel();
	}
	
	
	@RequestMapping("/sell/getAllCurrency")
	public @ResponseBody Object getAllCurrency(HttpServletRequest request)
	{
		return miniuiBaseService.getAllCurrency();
	}
	
	
	@RequestMapping("/sell/getAllPayment")
	public @ResponseBody Object getAllPayment(HttpServletRequest request)
	{
		return miniuiBaseService.getAllPayment();
	}
	
	@RequestMapping("/sell/getChannel")
	public @ResponseBody Object getSellChannel(HttpServletRequest request,AdminAgent adminAgent)
	{
		return miniuiBaseService.getSellChannelByAccount(adminAgent);
	}
	
	
	@RequestMapping("/supply/getAllSupply")
	public @ResponseBody Object getAllSupply(HttpServletRequest request,AdminAgent adminAgent)
	{
		return miniuiBaseService.querySupplier(new HashMap<String, String>());
	}
	
	@RequestMapping("/sell/getProvince")
	public @ResponseBody Object getProvince(HttpServletRequest request){
		return miniuiBaseService.getProvince();
	}
}
 
