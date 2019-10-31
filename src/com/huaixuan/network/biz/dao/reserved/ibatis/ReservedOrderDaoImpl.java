package com.huaixuan.network.biz.dao.reserved.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.reserved.ReservedOrderDao;
import com.huaixuan.network.biz.domain.reserved.ReservedOrder;
import com.huaixuan.network.biz.domain.reserved.ReservedOrderProduct;



/**
 * @author Mr_Yang   2016-9-6 下午03:49:05
 **/

@Repository("reservedOrderDao")
public class ReservedOrderDaoImpl implements ReservedOrderDao
{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public Long insertReservedOrder(ReservedOrder reservedOrder)
	{
		Object o = sqlMapClient.insert("insertReservedOrder",reservedOrder);
		if(o != null) return Long.valueOf(o.toString());
		return null;
	}

	@Override
	public void insertReservedOrderProduct(ReservedOrderProduct reservedOrderProduct)
	{
		sqlMapClient.insert("insertReservedOrderProduct",reservedOrderProduct);
	}

	@Override
	public List<ReservedOrder> searchReservedList(Map<String, String> searchMap)
	{
		return  sqlMapClient.queryForList("searchReservedOrderList",searchMap);
	}

	@Override
	public int searchReservedListCnt(Map<String, String> searchMap)
	{
		Object o = sqlMapClient.queryForObject("searchReservedOrderListCnt",searchMap);
		if(null == o) return 0;
		return (Integer)o;
	}

	@Override
	public String searchReservedListPrice(Map<String, String> searchMap)
	{
		Object o = sqlMapClient.queryForObject("searchReservedOrderListPrice",searchMap);
		if(null != o) return o.toString();
		return "0,0";
	}

	@Override
	public List<ReservedOrderProduct> searchReservedOrderProduct(Map<String, String> searchMap)
	{
		return  sqlMapClient.queryForList("searchReservedOrderProduct",searchMap);
	}

	@Override
	public void updateReservedProudctByNotNull(ReservedOrderProduct reservedOrderProduct)
	{
		sqlMapClient.update("updateReservedProductByNotNull",reservedOrderProduct);
	}

	@Override
	public void updateReservedOrderByNotNull(ReservedOrder reservedOrder)
	{
		sqlMapClient.update("updateReservedOrderByNotNull",reservedOrder);
	}
	
	 

}
 
