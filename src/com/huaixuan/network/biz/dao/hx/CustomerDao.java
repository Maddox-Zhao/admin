package com.huaixuan.network.biz.dao.hx;

import com.huaixuan.network.biz.domain.hx.Customer;

public interface CustomerDao
{
	public Integer addOneCustomer(Customer customer);
	
	public Integer selectOneCustomerId(Customer customer);
	
	/**
	 * �Զ�ˢ�»���״̬
	 */
	public void updateActiveStatus();
}
