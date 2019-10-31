package com.huaixuan.network.web.action.hx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.huaixuan.network.biz.service.admin.LoginLogoService;

import com.huaixuan.network.common.util.miniui.MiniUiUtil;


@Controller
@RequestMapping("/loginLogoInformation")
public class ShowLoginLogoAction {
	
	@Autowired
	private LoginLogoService loginService;
	
	//页面跳转
	@RequestMapping("/toshowLoginLog")
	public String toSearchStock(Model model,HttpServletRequest request)
	{
		return "/hx/showLoginLogoInformation";
	}
	
	
	
	//查看用户登录记录
	@RequestMapping("/showLoginLogo")
	public @ResponseBody Object list(HttpServletRequest request){
		
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		return loginService.queryLoginLogoList(searchMap);
			
	}

}
