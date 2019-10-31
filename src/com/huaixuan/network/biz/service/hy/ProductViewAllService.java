package com.huaixuan.network.biz.service.hy;

import java.util.List;

import com.huaixuan.network.biz.domain.hy.ProductViewAll;
import com.huaixuan.network.biz.domain.shop.BrandStoreDay;
import com.huaixuan.network.biz.query.QueryPage;

public interface ProductViewAllService {

	/**
	 * @param 
	 * @return
	 */
	public QueryPage getProductViewAllByConditionWithPage(ProductViewAll productViewAll, int currPage, int pageSize);
	
	public List<ProductViewAll> getProductViewAllSumByConditionWithPage(ProductViewAll productViewAll);
	
	public QueryPage getBrandStoreDayConditionWithPage(BrandStoreDay brandStoreDay,int currPage, int pageSize);
	
	public QueryPage getProductHistoryByConditionWithPage(ProductViewAll productViewAll, int currPage, int pageSize);
	
	public List<ProductViewAll> productViewCostSumValue(ProductViewAll productViewAll);
	
	public void addProductHistory();
	
}
