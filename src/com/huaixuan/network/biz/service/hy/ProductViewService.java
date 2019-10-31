package com.huaixuan.network.biz.service.hy;

import com.huaixuan.network.biz.domain.hy.ProductView;
import com.huaixuan.network.biz.query.QueryPage;

public interface ProductViewService {

	/**
	 * @param 
	 * @return
	 */
	public QueryPage getProductViewByConditionWithPage(ProductView productView, int currPage, int pageSize);
	
	
	
}
