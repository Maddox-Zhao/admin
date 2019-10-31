package com.huaixuan.network.web.action.hx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.service.active.ActiveSendMailService;

/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-22 上午11:37:23
 * 
 */

@Controller
public class ActiveAction
{
	@Autowired
	private CustomerDao customerDao;
	
	
	@Autowired
	private ActiveSendMailService activeSendMailService;
	
	@RequestMapping("/timetask/upactivestatus")
	public String updateStatus()
	{
		customerDao.updateActiveStatus();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * 创建的活动、修改的活动、价钱改变的产品
	 * @return
	 */
	@RequestMapping("/timetask/sendMail")
	public String sendMail()
	{
		activeSendMailService.sendMail();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * 代销添加的产品
	 * @return
	 */
	@RequestMapping("/timetask/sendDLMail")
	public String sendDLMail()
	{
		activeSendMailService.sendDLMail();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * 特惠添加的产品
	 * @return
	 */
	@RequestMapping("/timetask/sendHKMail")
	public String sendHKMail()
	{
		activeSendMailService.sendHKMail();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * 同一客户不同价的产品
	 * @return
	 */
	@RequestMapping("/timetask/sendMailForNotSamePrice")
	public String sendMailToNotSamePrice()
	{
		activeSendMailService.notSamePrice();
		return "hx/updateActiveSuccess";
	}
	
	
}
