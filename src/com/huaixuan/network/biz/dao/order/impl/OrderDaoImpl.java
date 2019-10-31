package com.huaixuan.network.biz.dao.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.order.OrderDao;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.product.Product;

@Repository("clientOrderDao")
public class OrderDaoImpl implements OrderDao{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	@Override
	public int getOrderListCount(Map<String, String> searchMap) {
		Object obj = sqlMapClient.queryForObject("getOrderListCount", searchMap);
		if(obj == null) 
		return 0;
		return (Integer)obj;
	}
	@Override
	public int getOrderListCountExport(Map<String, String> searchMap) {
		Object obj = sqlMapClient.queryForObject("getOrderListCountExport", searchMap);
		if(obj == null) 
		return 0;
		return (Integer)obj;
	}
  
	@Override
	public List<Orderdetails> getorderListExport(Map<String, String> searchMap) {
		return sqlMapClient.queryForList("getOrderbyidorderExport", searchMap);
	}
	
	
	@Override
	public String getOrderListTotalPrice(Map<String, String> searchMap)
	{
		Object obj = sqlMapClient.queryForObject("getOrderListTotalPrice", searchMap);
		if(obj == null) 
		return "0,0";
		return obj.toString();
	}
	
	@Override
	public List<Orderdetails> getorderList(Map<String, String> searchMap) {
		return sqlMapClient.queryForList("getOrderbyidorder", searchMap);
		
	}

	@Override
	public Orderdetails getorderlistByid(String idorder) {
		return (Orderdetails) this.sqlMapClient.queryForObject("orderbyidorder", idorder);
	}




	@Override
	public Integer insertIntoCustomerOrder(Map<String, String> map)
	{
		Object o =  sqlMapClient.insert("insertIntoCustomerOrder",map);
		if(o != null) return (Integer)o;
		return null;
	}


	@Override
	public List<Product> getProductListByidorder(Map<String, String> searchMap) {
		return sqlMapClient.queryForList("productByidorder",searchMap);
	}

	@Override
	public void updateCustomerOrder(Orderdetails orderdetails)
	{
		sqlMapClient.update("updateCustomerOrder",orderdetails);
	}

	@Override
	public boolean canSearchAllOrder(String userName)
	{
		Integer cnt = (Integer)sqlMapClient.queryForObject("canSearchAllOrderOrCustomer",userName);
		if(cnt > 0) return true;
		return false;
	}
	
	



}
