package com.huaixuan.network.web.action.wap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.web.action.interceptor.AdminAccess;



/**
 * @author Mr_Yang   2015-12-16 下午03:22:31
 **/

@Controller
@RequestMapping("/m")
public class WapAction
{
		@RequestMapping("/login")
		public String toLogin()
		{
			return "/web/web_login";
		}
		
		@RequestMapping("/index")
		@AdminAccess
		public String toIndex()
		{
			return "/web/web_index";
		}
}
 
