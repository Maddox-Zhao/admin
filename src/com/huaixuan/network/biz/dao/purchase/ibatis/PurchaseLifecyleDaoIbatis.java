package com.huaixuan.network.biz.dao.purchase.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.purchase.PurchaseLifecyleDao;
import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;



/**
 * @author Mr_Yang   2016-7-12 上午11:38:28
 **/

@Repository("PurchaseLifecyleDao")
public class PurchaseLifecyleDaoIbatis implements PurchaseLifecyleDao
{
	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	@Override
	public void updatePurchaseLifecyleByNotNull(Purchaselifecycle pruPurchaselifecycle)
	{
		sqlMap.update("updatePurchaselifecycleByNotNull",pruPurchaselifecycle);
		
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		this.sqlMap.update("updatePurchase", purchase);
		
	}

	@Override
	public int getidPurchase(int idPurchaseLifeCycle) {
		  Object o = this.sqlMap.queryForObject("getidPurchase", idPurchaseLifeCycle);
		  if(o != null) return (Integer)o;
		  return -1;
	}

}
 
