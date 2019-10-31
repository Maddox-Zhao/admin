package com.huaixuan.network.biz.service.purchase;

import java.util.Map;


import com.huaixuan.network.biz.domain.purchase.PurchaseProduct;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface PurchaseProductService {

	public MiniUiGrid getPurchaseProductList(Map<String, String> searchMap);
	
	public MiniUiGrid getPurchaseProduct(Map<String, String> searchMap);
	
	public void updatePurchaseProductf(PurchaseProduct purchaseProduct);
	
	public void addAddPurchaseProduct(PurchaseProduct purchaseProduct);
	
	public String getPurchaseProductTotlPrice(Map<String,String> searchMap);
	
	public String getPurchaseProducttotalNumber(Map<String,String> searchaMap);
	
	public void deletePurchaseProduct(PurchaseProduct  purchaseProduct);
	
}
