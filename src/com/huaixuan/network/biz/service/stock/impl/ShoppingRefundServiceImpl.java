/**
 * @Title: ShoppingRefundServiceImpl.java
 * @Package com.huaixuan.network.biz.service.stock.impl
 * @Description: TODO
 * @author 
 * @date 2011-3-4 下午07:54:32
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.stock.ShoppingRefundDao;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundSearch;
import com.huaixuan.network.biz.domain.stock.query.RefundDetailSearchQuery;
import com.huaixuan.network.biz.domain.stock.query.ShoppingRefundSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.stock.ShoppingRefundService;

/**
 * @ClassName: ShoppingRefundServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:54:32
 *
 */
@Service("shoppingRefundService")
public class ShoppingRefundServiceImpl implements
		ShoppingRefundService {

	@Autowired
	ShoppingRefundDao shoppingRefundDao;

	public long addShoppingRefund(ShoppingRefund shoppingRefund)throws Exception {
//		log.info("ShoppingRefundManagerImpl.addShoppingRefund method");
		try {
			return this.shoppingRefundDao.addShoppingRefund(shoppingRefund);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return -1;
	}

	/* @model: */
	public void editShoppingRefund(ShoppingRefund shoppingRefund)throws Exception {
//		log.info("ShoppingRefundManagerImpl.editShoppingRefund method");
		try {
			this.shoppingRefundDao.editShoppingRefund(shoppingRefund);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void removeShoppingRefund(Long shoppingRefundId)throws Exception {
//		log.info("ShoppingRefundManagerImpl.removeShoppingRefund method");
		try {
			this.shoppingRefundDao.removeShoppingRefund(shoppingRefundId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	public ShoppingRefund getShoppingRefund(Long shoppingRefundId) throws Exception{
//		log.info("ShoppingRefundManagerImpl.getShoppingRefund method");
		try {
			return this.shoppingRefundDao.getShoppingRefund(shoppingRefundId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public ShoppingRefund getShoppingRefunds(Map<String, String> paramMap)throws Exception {
//		log.info("ShoppingRefundManagerImpl.getShoppingRefunds method");
		try {
			return this.shoppingRefundDao.getShoppingRefunds(paramMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public int getShoppingCountByParameterMap(Map parMap)throws Exception {
		return this.shoppingRefundDao.getCountByParameterMap(parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getShoppingRefundListByParameterMap (
			ShoppingRefundSearchQuery shoppingRefundSearchQuery, int currPage, int pageSize) throws Exception{
//		try {
////			return this.shoppingRefundDao.getShoppingRefundListByParameterMap(
////					parameterMap, page);
//		} catch (Exception e) {
////			log.error(e.getMessage());
//		}
//		return null;

		QueryPage queryPage = new QueryPage(shoppingRefundSearchQuery);
		Map pramas = queryPage.getParameters();
		pramas.put("depfirstIds", shoppingRefundSearchQuery.getDepfirstIds());

		int count = shoppingRefundDao
				.getCountByParameterMap(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			// List<ShoppingList> shoppingList =
			// shoppingListDao.getShoppingListByParameterMap(pramas);

			List<ShoppingRefund> shoppingRefunds = shoppingRefundDao
					.getShoppingRefundListByParameterMap(pramas);

			if (shoppingRefunds != null
					&& shoppingRefunds.size() > 0) {
				queryPage.setItems(shoppingRefunds);
			}
		}
		return queryPage;
	}

	public List<ShoppingRefundSearch> getShoppingRefundNum(Long shoppingRefundId) throws Exception {
		try {
			return this.shoppingRefundDao
					.getShoppingRefundNem(shoppingRefundId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public List<ShoppingRefundDSearch> getShoppingRefundDNum(
			Map<String, String> parMap) throws Exception{
		try {
			return this.shoppingRefundDao.getByParameterMap(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingRefundManager#
	 * getShoppingRefundCountDetailSearchByParameterMap(java.util.Map)
	 */
	public int getShoppingRefundCountDetailSearchByParameterMap(
			Map<String, String> parMap) throws Exception{
		try {
			return shoppingRefundDao
					.getShoppingRefundCountDetailSearchByParameterMap(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return 0;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingRefundManager#
	 * getShoppingRefundDetailSearchRusultByParameterMap(java.util.Map,
	 * com.hundsun.bible.Page)
	 */
	@SuppressWarnings("unchecked")
	public QueryPage getShoppingRefundDetailSearchRusultByParameterMap(
			RefundDetailSearchQuery refundDetailSearchQuery, int currPage, int pageSize) throws Exception{
//		log.info("ShoppingRefundManagerImpl.getShoppingRefundDetailSearchRusultByParameterMap method");
//		try {
////			return this.shoppingRefundDao
////					.getShoppingRefundDetailSearchRusultByParameterMap(
////							parameterMap, page);
//		} catch (Exception e) {
////			log.error(e.getMessage());
//		}
//		return null;

		QueryPage queryPage = new QueryPage(refundDetailSearchQuery);
		Map pramas = queryPage.getParameters();
		if(refundDetailSearchQuery.getDepfirstIds() != null && refundDetailSearchQuery.getDepfirstIds().size()>0)
		{
			pramas.put("depfirstIds", refundDetailSearchQuery.getDepfirstIds());
		}

		int count = shoppingRefundDao
				.getShoppingRefundCountDetailSearchByParameterMap(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			List<ShoppingRefundDetailSearch> shoppingRefundDetailSearchs = shoppingRefundDao
					.getShoppingRefundDetailSearchRusultByParameterMap(pramas);

			if (shoppingRefundDetailSearchs != null
					&& shoppingRefundDetailSearchs.size() > 0) {
				queryPage.setItems(shoppingRefundDetailSearchs);
			}
		}
		return queryPage;


	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingRefundManager#
	 * getShoppingRefundCountGatherSearchByParameterMap(java.util.Map)
	 */
	public int getShoppingRefundCountGatherSearchByParameterMap(
			Map<String, String> parMap) throws Exception{
		try {
			return shoppingRefundDao
					.getShoppingRefundCountGatherSearchByParameterMap(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return 0;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingRefundManager#
	 * getShoppingRefundGatherSearchRusultByParameterMap(java.util.Map,
	 * com.hundsun.bible.Page)
	 */
	@SuppressWarnings("unchecked")
	public QueryPage getShoppingRefundGatherSearchRusultByParameterMap(
			RefundDetailSearchQuery refundDetailSearchQuery, int currPage, int pageSize) throws Exception{
//		log.info("ShoppingRefundManagerImpl.getShoppingRefundGatherSearchRusultByParameterMap method");
//		try {
////			return this.shoppingRefundDao
////					.getShoppingRefundGatherSearchRusultByParameterMap(
////							parameterMap, page);
//		} catch (Exception e) {
////			log.error(e.getMessage());
//		}
//		return null;

		QueryPage queryPage = new QueryPage(refundDetailSearchQuery);
		Map pramas = queryPage.getParameters();

		if(refundDetailSearchQuery.getDepfirstIds() != null && refundDetailSearchQuery.getDepfirstIds().size()>0)
		{
			pramas.put("depfirstIds", refundDetailSearchQuery.getDepfirstIds());
		}

		int count = shoppingRefundDao
				.getShoppingRefundCountGatherSearchByParameterMap(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			List<ShoppingRefundGatherSearch> shoppingRefundGatherSearchs = shoppingRefundDao
					.getShoppingRefundGatherSearchRusultByParameterMap(pramas);

			if (shoppingRefundGatherSearchs != null
					&& shoppingRefundGatherSearchs.size() > 0) {
				queryPage.setItems(shoppingRefundGatherSearchs);
			}
		}
		return queryPage;

	}

	public List<ShoppingRefundDetail> getBatchNumByRelationNum(
			String relationNum) throws Exception{
//		log.info("ShoppingRefundManagerImpl.getBatchNumByRelationNum method");
		try {
			return this.shoppingRefundDao.getBatchNumByRelationNum(relationNum);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

}
