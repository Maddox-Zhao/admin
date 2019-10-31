package com.huaixuan.network.biz.dao.hx.ibatis;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.domain.hx.Customer;


@Repository("customerDao")
public class CustomerDaoiBatis implements CustomerDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public Integer addOneCustomer(Customer customer)
	{
		return (Integer)sqlMapClient.insert("addOneCustomer", customer);
	}

	@Override
	public Integer selectOneCustomerId(Customer customer)
	{
		return (Integer)sqlMapClient.queryForObject("getOneCustomerId",customer);
	}

	@Override
	public void updateActiveStatus()
	{
		sqlMapClient.update("updateActiveStatusToWait");
		sqlMapClient.update("updateActiveStatusToOpen");
		sqlMapClient.update("updateActiveStatusToClose");
	}

}
