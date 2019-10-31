package com.huaixuan.network.biz.service.purchase;


import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;



/**
 * @author Mr_Yang   2016-7-12 上午11:41:41
 **/

public interface PurchaseLifecyleService
{
	public void updatePurchaseLifecyleByNotNull(Purchaselifecycle purchaselifecycle);
	
	public void updatePurchase(Purchase purchase); 
	
	public int getidPurchase(int idPurchaseLifeCycle);
}
 
