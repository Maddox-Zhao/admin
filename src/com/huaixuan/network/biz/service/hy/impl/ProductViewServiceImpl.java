package com.huaixuan.network.biz.service.hy.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hy.ProductViewDao;
import com.huaixuan.network.biz.domain.hy.ProductView;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hy.ProductViewService;

@Service("productViewService")
public class ProductViewServiceImpl implements ProductViewService {

	@Autowired
	private ProductViewDao productViewDao;

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getProductViewByConditionWithPage(ProductView productView, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(productView);
		Map pramas = queryPage.getParameters();

		Integer count = productViewDao.getProductViewByConditionWithPageCount(pramas);

		if (count !=null && count.intValue() > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<ProductView> productViewList = productViewDao.getProductViewByConditionWithPage(pramas);
			if (productViewList != null && productViewList.size() > 0) {
				queryPage.setItems(productViewList);
			}
		}
		return queryPage;
	}

}
