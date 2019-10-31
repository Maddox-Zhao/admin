package com.huaixuan.network.biz.service.active;


/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-27 下午05:10:43
 * 发送邮件
 */

public interface ActiveSendMailService
{

	public void sendMail();;
	
	
	/**
	 * 针对同一客户对同一区域同款产品有不同价格
	 */
	public void notSamePrice();
	
	
	/**
	 * 代销添加的产品
	 */
	public void sendDLMail();
	
	/**
	 * 特惠添加的产品
	 */
	public void sendHKMail();
	
}
