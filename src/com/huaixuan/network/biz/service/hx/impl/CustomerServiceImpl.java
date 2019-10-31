package com.huaixuan.network.biz.service.hx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.domain.hx.Customer;

/**
 * 2012-02-17 16:18
 * @author Mr_Yang
 *
 */

@Service("customerService")
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Integer addOneCustomer(Customer customer)
	{
		return customerDao.addOneCustomer(customer);
	}

	@Override
	public Integer getOneCustomerId(Customer customer)
	{
		return customerDao.selectOneCustomerId(customer);
	}

}
