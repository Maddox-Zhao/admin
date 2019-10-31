package com.huaixuan.network.biz.service.hx.impl;

import com.huaixuan.network.biz.domain.hx.Customer;

public interface CustomerService
{
	public Integer getOneCustomerId(Customer customer);
	
	public Integer addOneCustomer(Customer customer);
}
