package com.huaixuan.network.web.action.pic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.service.pic.AutoCreateImageService;



/**
 * @author Mr_Yang   2015-8-19 обнГ05:52:32
 **/

@Controller
public class AutoCreateImageAction
{
	@Autowired
	private AutoCreateImageService autoCreateImageService;
	
	
	@RequestMapping("/timetask/autoCreateImage")
	public void autoUpdate()
	{
		autoCreateImageService.autoUpdateImage2DB();
	}
 

}
 
