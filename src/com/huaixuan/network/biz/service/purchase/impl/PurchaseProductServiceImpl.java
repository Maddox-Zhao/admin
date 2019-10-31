package com.huaixuan.network.biz.service.purchase.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.purchase.PurchaseProudctDao;
import com.huaixuan.network.biz.domain.purchase.PurchaseProduct;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.purchase.PurchaseProductService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

@Service("PurchaseProductService")
public class PurchaseProductServiceImpl implements PurchaseProductService{
	
	@Autowired
	private PurchaseProudctDao purchaseproduct;
	protected Log        log = LogFactory.getLog(this.getClass());
	
	@Override
	public MiniUiGrid getPurchaseProductList(Map<String, String> searchMap) {
		MiniUiGrid gird = new MiniUiGrid();
		List<PurchaseProduct> list = purchaseproduct.getPurchaseproductList(searchMap);
		if(list != null&& list.size() >0){
			gird.setData(list);
			gird.setTotal(list.size());
		}
		return gird;
		
	}

	@Override
	public MiniUiGrid getPurchaseProduct(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = purchaseproduct.getPurchaseProduct(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		if(count > 0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count);
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow"))){
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<PurchaseProduct> list = purchaseproduct.getPurchaseproductList(searchMap);
			if(list != null&& list.size() > 0){
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public void updatePurchaseProductf(PurchaseProduct purchaseProduct) {
		this.purchaseproduct.updatePurchaseProduct(purchaseProduct);
		
	}

	@Override
	public void addAddPurchaseProduct(PurchaseProduct purchaseProduct) {
		log.info("PurchaseProductServiceImpl.addAddPurchaseProduct method");
		try {
			this.purchaseproduct.addPurchaseProduct(purchaseProduct);
		} catch (Exception e) {
		}

	}

	@Override
	public String getPurchaseProductTotlPrice(Map<String, String> searchMap) {
		return purchaseproduct.getPurchaseProductTotlPrice(searchMap);
	}

	@Override
	public String getPurchaseProducttotalNumber(Map<String, String> searchaMap) {
		return purchaseproduct.getPurchaseProducttotalNumber(searchaMap);
	}

	@Override
	public void deletePurchaseProduct(PurchaseProduct purchaseProduct) {
		log.info("PurchaseProductServiceImpl.deletePurchaseProduct method");
		try {
			this.purchaseproduct.deletePurchaseProduct(purchaseProduct);
		} catch (Exception e) {
		}
	}


	
}
