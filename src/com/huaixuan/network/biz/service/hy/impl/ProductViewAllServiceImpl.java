package com.huaixuan.network.biz.service.hy.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hy.ProductViewAllDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.hy.ProductViewAll;
import com.huaixuan.network.biz.domain.shop.BrandStoreDay;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hy.ProductViewAllService;
import com.huaixuan.network.common.util.DateUtil;

@Service("productViewAllService")
public class ProductViewAllServiceImpl implements ProductViewAllService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private ProductViewAllDao productViewAllDao;
	@Autowired
	private BrandDao brandDao;

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getProductViewAllByConditionWithPage(ProductViewAll productViewAll, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(productViewAll);
		Map pramas = queryPage.getParametersQuery();

		Integer count = productViewAllDao.getProductViewAllByConditionWithPageCount(pramas);

		if (count !=null && count.intValue() > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<ProductViewAll> productViewAllList = productViewAllDao.getProductViewAllByConditionWithPage(pramas);
			if (productViewAllList != null && productViewAllList.size() > 0) {
				queryPage.setItems(productViewAllList);
			}
		}
		return queryPage;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getProductHistoryByConditionWithPage(ProductViewAll productViewAll, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(productViewAll);
		Map pramas = queryPage.getParametersQuery();

		Integer count = productViewAllDao.getProductHistoryByConditionWithPageCount(pramas);

		if (count !=null && count.intValue() > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<ProductViewAll> productViewAllList = productViewAllDao.getProductHistoryByConditionWithPage(pramas);
			if (productViewAllList != null && productViewAllList.size() > 0) {
				queryPage.setItems(productViewAllList);
			}
		}
		return queryPage;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getBrandStoreDayConditionWithPage(BrandStoreDay brandStoreDay, int currPage, int pageSize) {
		log.info("BrandManagerImpl.editBrand method");
		try {
			QueryPage queryPage = new QueryPage(brandStoreDay);
			Map pramas = queryPage.getParametersQuery();
	
			Integer count = brandDao.getBrandStoreDayConditionWithPageCount(pramas);
	
			if (count !=null && count.intValue() > 0) {
	
				queryPage.setCurrentPage(currPage);
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);
	
				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
	
				List<ProductViewAll> productViewAllList = brandDao.getBrandStoreDayConditionWithPage(pramas);
				if (productViewAllList != null && productViewAllList.size() > 0) {
					queryPage.setItems(productViewAllList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> getProductViewAllSumByConditionWithPage(
			ProductViewAll productViewAll) {
		QueryPage queryPage = new QueryPage(productViewAll);
		Map pramas = queryPage.getParametersQuery();

		List<ProductViewAll> productViewAllList = productViewAllDao.getProductViewAllSumByConditionWithPage(pramas);
		
		
		return productViewAllList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> productViewCostSumValue(
			ProductViewAll productViewAll) {
		QueryPage queryPage = new QueryPage(productViewAll);
		Map pramas = queryPage.getParametersQuery();

		List<ProductViewAll> productViewAllList = productViewAllDao.productViewCostSumValue(pramas);
		
		
		return productViewAllList;
	}
	
	public void addProductHistory() {
		log.info("ProductViewAllServiceImpl.addProductHistory method");
		try {
			Date da = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            String date = df.format(da);
            String date = "2013-10-28";
            List<ProductViewAll>  productViewAlllist = productViewAllDao.productViewToday();
			for(ProductViewAll productViewAll :productViewAlllist ){
				productViewAll.setDate(df.parse(date));
				productViewAllDao.addProductHistory(productViewAll);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
}
