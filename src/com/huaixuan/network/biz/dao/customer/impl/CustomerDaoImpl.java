package com.huaixuan.network.biz.dao.customer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.customer.CustomerDao;
import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.order.Orderdetails;



/**
 * @author Mr_Yang   2015-12-8 上午11:57:20
 **/

@Repository
public class CustomerDaoImpl implements CustomerDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public List<Customer> searchCustomer(Map<String, String> searchMap)
	{
		return sqlMapClientTemplate.queryForList("searchCustomer",searchMap);
	}

	@Override
	public int searchCustomerCount(Map<String, String> searchMap)
	{
		Object o = sqlMapClientTemplate.queryForObject("searchCustomerCount",searchMap);
		if(o != null) return (Integer)o;
		return 0;
	}

	@Override
	public Customer getCustomerById(Long idCustomer)
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("idCustomer", idCustomer+"");
		Object o =sqlMapClientTemplate.queryForObject("searchCustomer",map);
		if(o == null) return null;
		return (Customer)o;
	}

	@Override
	public Long inserCustomer(Customer customer)
	{
		 Object o = sqlMapClientTemplate.insert("insertCustomer",customer);
		 return Long.valueOf(o.toString());
		
	}

	@Override
	public void updateCustomer(Customer customer)
	{
		 sqlMapClientTemplate.update("updateCustomer",customer);
		
	}

	@Override
	public List<Orderdetails> getOrderByidCustomer(Map<String, String> map) {
		return sqlMapClientTemplate.queryForList("searchOrderByidCustomer",map);
	}
}
 
