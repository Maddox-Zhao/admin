package com.huaixuan.network.biz.dao.purchase;

import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;



/**
 * @author Mr_Yang   2016-7-12 上午11:37:29
 **/

public interface PurchaseLifecyleDao
{
	
	public void updatePurchaseLifecyleByNotNull(Purchaselifecycle pruPurchaselifecycle);
	
	public void updatePurchase(Purchase purchase);
	
	public int getidPurchase(int idPurchaseLifeCycle);
}
 
