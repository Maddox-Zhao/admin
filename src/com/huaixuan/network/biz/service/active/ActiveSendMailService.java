package com.huaixuan.network.biz.service.active;


/**
 * @author Mr_Yang
 * @version ����ʱ�䣺2012-3-27 ����05:10:43
 * �����ʼ�
 */

public interface ActiveSendMailService
{

	public void sendMail();;
	
	
	/**
	 * ���ͬһ�ͻ���ͬһ����ͬ���Ʒ�в�ͬ�۸�
	 */
	public void notSamePrice();
	
	
	/**
	 * ������ӵĲ�Ʒ
	 */
	public void sendDLMail();
	
	/**
	 * �ػ���ӵĲ�Ʒ
	 */
	public void sendHKMail();
	
}
