package com.huaixuan.network.biz.dao.purchase.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.purchase.PurchaseOrderDao;
import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.PurchaseOrder;
import com.huaixuan.network.biz.domain.purchase.PurchaseProductYangJie;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;



/**
 * @author Mr_Yang   2015-12-30 上午11:34:14
 **/

@Repository("purchaseOrderDao")
public class PurchaseOrderDaoIbatis implements PurchaseOrderDao
{
	
	@Autowired
	private SqlMapClientTemplate sqlClientTemplate;
	
	@Override
	public List<Purchaselifecycle> queryPurchaselifecycle(Purchaselifecycle purchaselifecycle)
	{
		return sqlClientTemplate.queryForList("queryPurchaselifecycle",purchaselifecycle);
	}

	@Override
	public Purchase getPurchaseOrderById(Long idPurchase)
	{
		 Object o = sqlClientTemplate.queryForObject("selectPurchaseById",idPurchase);
		 if(null != o) return (Purchase)o;
		return null;
	}

	@Override
	public Long insertPurchase(Purchase purchase)
	{
		Object o = sqlClientTemplate.insert("insertPurchase",purchase);
		if(o != null) return (Long)o;
		return -1L;
	}

	@Override
	public List<PurchaseOrder> queryPurchaseOrderByMap(Map<String, String> searchMap)
	{
		 
		return  sqlClientTemplate.queryForList("queryPurchaseOrder",searchMap);
	}

	@Override
	public int queryPurchaseOrderCntByMap(Map<String, String> searchMap)
	{
		 
		Object o = sqlClientTemplate.queryForObject("queryPurchaseOrderCnt",searchMap);
		if(o != null)
		{
			return (Integer)o;
		}
		return 0;
	}

	@Override
	public List<PurchaseProductYangJie> getPurchasePurchaseProductByIdPurchaseLifecycle(Map<String,String> searchMap)
	{ 
		return sqlClientTemplate.queryForList("queryPurchaseProduct",searchMap);
	}

}
 
