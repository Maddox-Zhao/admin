package com.huaixuan.network.biz.dao.hx;

import com.huaixuan.network.biz.domain.hx.Customer;

public interface CustomerDao
{
	public Integer addOneCustomer(Customer customer);
	
	public Integer selectOneCustomerId(Customer customer);
	
	/**
	 * 自动刷新活动框的状态
	 */
	public void updateActiveStatus();
}
