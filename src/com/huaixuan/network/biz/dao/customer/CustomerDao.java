package com.huaixuan.network.biz.dao.customer;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.order.Orderdetails;



/**
 * @author Mr_Yang   2015-12-8 上午11:45:55
 * 客户管理
 **/

public interface CustomerDao
{

	public List<Customer> searchCustomer(Map<String,String> searchMap);
	
	public int searchCustomerCount(Map<String,String> searchMap);
	
	public Customer getCustomerById(Long idCustomer);
	
	public List<Orderdetails> getOrderByidCustomer(Map<String, String> map);
	
	public Long inserCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);
	
	
}
 
