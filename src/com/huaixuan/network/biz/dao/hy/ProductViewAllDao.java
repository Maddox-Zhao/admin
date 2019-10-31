package com.huaixuan.network.biz.dao.hy;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hy.ProductViewAll;


public interface ProductViewAllDao {
	
	/**
	 * @return
	 */
	public Integer getProductViewAllByConditionWithPageCount(Map parMap);
	
	public Integer getProductHistoryByConditionWithPageCount(Map parMap);
	
	/**
	 * @return
	 */
	public List<ProductViewAll> getProductViewAllByConditionWithPage(Map parMap);
	public List<ProductViewAll> getProductHistoryByConditionWithPage(Map parMap);
	
	
	
	public List<ProductViewAll> getProductViewAllSumByConditionWithPage(Map parMap);
	
	public List<ProductViewAll> productViewCostSumValue(Map parMap);
	
	public List<ProductViewAll> productViewToday() throws Exception;
	
	public Integer addProductHistory(ProductViewAll productViewAll) throws Exception;
	
}
