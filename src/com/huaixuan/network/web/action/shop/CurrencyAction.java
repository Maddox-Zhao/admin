package com.huaixuan.network.web.action.shop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.huaixuan.network.biz.service.shop.CurrencyService;

@Controller
public class CurrencyAction {
	
	private CurrencyService currencyService;
	
	@RequestMapping("/currency/getListByCurrenctname")
	public @ResponseBody Object getCurrenctByname(HttpServletRequest request){
		
		return currencyService.getCurrenctByname();
	}
	
}
