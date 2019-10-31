package com.huaixuan.network.biz.service.purchase;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.PurchaseProductYangJie;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2015-12-30 上午11:37:14
 **/

public interface PurchaseOrderService
{
	public List<Purchaselifecycle> queryPurchaselifecycle(Purchaselifecycle purchaselifecycle);
	
	
	/**
	 * 根据id获取采购订单
	 * @param idPurchase
	 * @return
	 */
	public Purchase getPurchaseOrderById(Long idPurchase);
	
	
	/**
	 * 
	 * @param searchMap
	 * @return
	 */
	public MiniUiGrid queryPurchaseOrder(Map<String,String> searchMap);
	
	
	/**
	 * 采购产品入库 
	 * @param requestMap  idLastOperator  idPurchaseLifecycle
	 * @return
	 */
	public  int purchaseProductInStock(Map<String,String> requestMap);
	
	
	/**
	 * 查询采购产品 
	 * @param  
	 * @return
	 */
	public  List<PurchaseProductYangJie> getPurchaseProduct(Map<String,String> searchMap);
	
	
 
}
 
