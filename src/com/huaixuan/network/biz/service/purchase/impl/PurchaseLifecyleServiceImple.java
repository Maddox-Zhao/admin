package com.huaixuan.network.biz.service.purchase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.purchase.PurchaseLifecyleDao;
import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;
import com.huaixuan.network.biz.service.purchase.PurchaseLifecyleService;



/**
 * @author Mr_Yang   2016-7-12 上午11:42:15
 **/
@Service("purchaseLifecyleService")
public class PurchaseLifecyleServiceImple implements PurchaseLifecyleService
{
	@Autowired
	private PurchaseLifecyleDao purchaseLifecyleDao;
	
	@Override
	public void updatePurchaseLifecyleByNotNull(
			Purchaselifecycle purchaselifecycle)
	{
		purchaseLifecyleDao.updatePurchaseLifecyleByNotNull(purchaselifecycle);
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		this.purchaseLifecyleDao.updatePurchase(purchase);
		
	}

	@Override
	public int getidPurchase(int idPurchaseLifeCycle) {
		return	this.purchaseLifecyleDao.getidPurchase(idPurchaseLifeCycle);
	}

}
 
