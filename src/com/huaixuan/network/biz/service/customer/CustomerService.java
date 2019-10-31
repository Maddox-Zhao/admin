package com.huaixuan.network.biz.service.customer;

import java.util.Map;

import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2015-12-8 下午12:10:37
 **/

public interface CustomerService
{
	public MiniUiGrid searchCustomer(Map<String,String> searchMap);
 
	
	public Customer getCustomerById(Long idCustomer);

	/**
	 * 
	 * @param customer 
	 * @return 1-ok  2-客户存在
	 */
	public int inserCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);
	
	public MiniUiGrid getOrderByidCustomer(Map<String, String> map);
}
 
