package com.huaixuan.network.web.action.hx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.service.active.ActiveSendMailService;

/**
 * @author Mr_Yang
 * @version ����ʱ�䣺2012-3-22 ����11:37:23
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
	 * �����Ļ���޸ĵĻ����Ǯ�ı�Ĳ�Ʒ
	 * @return
	 */
	@RequestMapping("/timetask/sendMail")
	public String sendMail()
	{
		activeSendMailService.sendMail();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * ������ӵĲ�Ʒ
	 * @return
	 */
	@RequestMapping("/timetask/sendDLMail")
	public String sendDLMail()
	{
		activeSendMailService.sendDLMail();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * �ػ���ӵĲ�Ʒ
	 * @return
	 */
	@RequestMapping("/timetask/sendHKMail")
	public String sendHKMail()
	{
		activeSendMailService.sendHKMail();
		return "hx/updateActiveSuccess";
	}
	
	/**
	 * ͬһ�ͻ���ͬ�۵Ĳ�Ʒ
	 * @return
	 */
	@RequestMapping("/timetask/sendMailForNotSamePrice")
	public String sendMailToNotSamePrice()
	{
		activeSendMailService.notSamePrice();
		return "hx/updateActiveSuccess";
	}
	
	
}
