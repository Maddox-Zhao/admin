package com.huaixuan.network.biz.dao.hy;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hy.ProductView;


public interface ProductViewDao {
	
	/**
	 * @return
	 */
	public Integer getProductViewByConditionWithPageCount(Map parMap);
	
	/**
	 * @return
	 */
	public List<ProductView> getProductViewByConditionWithPage(Map parMap);
	
	
}
