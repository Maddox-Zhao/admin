package com.huaixuan.network.biz.dao.purchase;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.purchase.PurchaseProduct;

public interface PurchaseProudctDao {
	
	public List<PurchaseProduct> getPurchaseproductList(Map<String,String> searchMap);
	
	public int getPurchaseProduct(Map<String,String> searchMap);
	
	void updatePurchaseProduct(PurchaseProduct purchaseProduct);
	
	Long addPurchaseProduct(PurchaseProduct purchaseProduct);
	
	public String getPurchaseProductTotlPrice(Map<String,String> searchMap);
	
	public String getPurchaseProducttotalNumber(Map<String,String> searchMap);
	
	Long deletePurchaseProduct(PurchaseProduct purchaseProduct);

}
