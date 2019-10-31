package com.huaixuan.network.biz.dao.purchase;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.PurchaseOrder;
import com.huaixuan.network.biz.domain.purchase.PurchaseProductYangJie;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;



/**
 * @author Mr_Yang   2015-12-30 上午11:33:10
 **/

public interface PurchaseOrderDao
{
	/**
	 * 查询Purchaselifecycle
	 * @param purchaselifecycle
	 * @return
	 */
	public List<Purchaselifecycle> queryPurchaselifecycle(Purchaselifecycle purchaselifecycle);
	
	
	/**
	 * 根据id获取采购订单
	 * @param idPurchase
	 * @return
	 */
	public Purchase getPurchaseOrderById(Long idPurchase);
	
	
	/**
	 * 添加采购订单
	 * @param purchase
	 * @return
	 */
	public Long insertPurchase(Purchase purchase);
	
	
	/**
	 * 查询采购订单
	 * @param searchMap
	 */
	public List<PurchaseOrder> queryPurchaseOrderByMap(Map<String,String> searchMap);
	
	public int queryPurchaseOrderCntByMap(Map<String,String> searchMap);
	
	
	/**
	 * 获取采购产品(通过采购订单)
	 * @param idPurchaseLifecycle
	 * @return
	 */
	public List<PurchaseProductYangJie> getPurchasePurchaseProductByIdPurchaseLifecycle(Map<String,String> searchMap);
	
}
 
