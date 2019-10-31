/**
 * @Title: ShoppingListServiceImpl.java
 * @Package com.huaixuan.network.biz.service.stock.impl
 * @Description: TODO
 * @author 
 * @date 2011-3-1 下午02:47:41
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.stock.ShoppingDetailDao;
import com.huaixuan.network.biz.dao.stock.ShoppingListDao;
import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.dao.trade.WholesaleApplyDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailGb;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.V_SupplierShoppingList;
import com.huaixuan.network.biz.domain.stock.query.ShoppingListQuery;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.trade.WholesaleApply;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumStockStatus;
import com.huaixuan.network.biz.enums.EnumWholesaleStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.stock.ShoppingDetailGbService;
import com.huaixuan.network.biz.service.stock.ShoppingListService;
import com.huaixuan.network.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @ClassName: ShoppingListServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:47:41
 *
 */
@Service("shoppingListSerivice")
public class ShoppingListServiceImpl implements ShoppingListService {

	@Autowired
	ShoppingListDao shoppingListDao;

	@Autowired
	WholesaleApplyDao wholesaleApplyDao;

	@Autowired
	ShoppingDetailDao shoppingDetailDao;

	@Autowired
	GoodsInstanceDao goodsInstanceDao;

	@Autowired
	InDepositoryDao inDepositoryDao;

	// public ShoppingListDao shoppingListDao;
	//
	// public ShoppingDetailDao shoppingDetailDao;
	//
	// // public SupplierDao supplierDao;
	//
	// public CatAttrRelDao catAttrRelDao;
	//
	// // public InDepositoryDao inDepositoryDao;
	//
	@Autowired
	InDetailDao inDetailDao;
	//
	// public GoodsInstanceDao goodsInstanceDao;
	//
	@Autowired
	CodeManager codeManager;
	//
	// // public WholesaleApplyDao wholesaleApplyDao;
	// //
	//
	@Autowired
	ShoppingDetailGbService shoppingDetailGbService;
	@Autowired
	GoodsDao goodsDao;
	//
	@Transactional
	public long addShoppingList(ShoppingList shoppingList) {
		// log.info("ShoppingListManagerImpl.addShoppingList method");
		long result = 0;
		try {
			result = this.shoppingListDao.addShoppingList(shoppingList);
			// 如果是批发采购订单，则修改批发申请单状态
			if (StringUtil.isNotBlank(shoppingList.getTid())) {
				WholesaleApply wholesaleApply = wholesaleApplyDao
						.getWholesaleApplyByTid(shoppingList.getTid());
				if (wholesaleApply != null
						&& EnumWholesaleStatus.WHOLESALE_STATUS_NEW.getKey()
								.equals(wholesaleApply.getStatus())) {
					wholesaleApply
							.setStatus(EnumWholesaleStatus.WHOLESALE_STATUS_HANDLING
									.getKey());
					wholesaleApplyDao.editWholesaleApply(wholesaleApply);
				}
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return result;
	}


	 /* @model: */
	 @Transactional(rollbackFor=java.lang.Exception.class)
	 public void editShoppingList(ShoppingList shoppingList, String oldStatus, String actionType) {
		// log.info("ShoppingListManagerImpl.editShoppingList method");
		try {
			this.shoppingListDao.editShoppingList(shoppingList);
			// 如果是批发采购，做完成操作时需要修改批发采购申请单状态为完成状态
			if (StringUtil.isNotBlank(shoppingList.getTid())
					&& EnumStockStatus.STOCK_FINISHED.getKey().equals(
							shoppingList.getStatus())) {
				WholesaleApply wholesaleApply = wholesaleApplyDao
						.getWholesaleApplyByTid(shoppingList.getTid());
				if (wholesaleApply != null
						&& EnumWholesaleStatus.WHOLESALE_STATUS_HANDLING
								.getKey().equals(wholesaleApply.getStatus())) {
					wholesaleApply
							.setStatus(EnumWholesaleStatus.WHOLESALE_STATUS_FINISH
									.getKey());
					wholesaleApplyDao.editWholesaleApply(wholesaleApply);
				}
			}
			// 只还原等待验收状态采购订单的在途库存
			if (shoppingList != null
					&& StringUtil.isNotBlank(oldStatus)
					&& EnumStockStatus.STOCK_WAIT_CHECK.getKey().equals(
							oldStatus)) {
				List<ShoppingDetail> shoppingDetails = shoppingDetailDao
						.getShopDetailsByShopListId(shoppingList.getId());
				// 采购数量大于零的产品修改在途库存
				if (shoppingDetails.size() > 0) {
					for (ShoppingDetail detail : shoppingDetails) {
						if (detail.getAmount() > 0 && detail.getGoodsId() > 0) {
							goodsInstanceDao.updateWayNumById(
									detail.getGoodsInstanceId(),
									-detail.getAmount());
						}
					}
				}
			}
			
			if(actionType.equals("finished") && shoppingList != null){
				List<ShoppingDetail> shoppingDetails = shoppingDetailDao.getShopDetailsByShopListId(shoppingList.getId());
				if (shoppingDetails.size() > 0) {
					for (ShoppingDetail detail : shoppingDetails) {
						Long billId = goodsDao.getBillId(detail.getGoodsId());
						if(billId !=null){
							ShoppingDetailGb shoppingDetailGb = new ShoppingDetailGb();
							shoppingDetailGb.setRelationId(detail.getId());
							shoppingDetailGb.setShoppingId(detail.getShoppingId());
							shoppingDetailGb.setGoodsId(detail.getGoodsId());
							shoppingDetailGb.setGoodsInstanceId(detail.getGoodsInstanceId());
							shoppingDetailGb.setUnits(detail.getUnits());
							shoppingDetailGb.setAmount(detail.getAmount());
							shoppingDetailGb.setUnitPrice(detail.getUnitPrice());
							shoppingDetailGb.setDueFee(detail.getDueFee());
							shoppingDetailGb.setFactFee(detail.getFactFee());
							shoppingDetailGb.setRejectNum(detail.getRejectNum());
							shoppingDetailGb.setMissingNum(detail.getMissingNum());
							shoppingDetailGb.setReceiveNum(detail.getReceiveNum());
							shoppingDetailGb.setBillId(billId);
							shoppingDetailGbService.addShoppingDetailGb(shoppingDetailGb);
						}
					}
				}
			}

		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

	public void editShoppingListAllInfo(ShoppingList shoppingList) {
		// log.info("ShoppingListManagerImpl.editShoppingListAllInfo method");
		try {
			this.shoppingListDao.editShoppingListAllInfo(shoppingList);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}
	//
	/* @model: */
	// public void removeShoppingList(Long shoppingListId) {
	// // log.info("ShoppingListManagerImpl.removeShoppingList method");
	// try {
	// this.shoppingListDao.removeShoppingList(shoppingListId);
	// } catch (Exception e) {
	// // log.error(e.getMessage());
	// }
	// }
	//
	public ShoppingList getShoppingList(Long shoppingListId) {
		// log.info("ShoppingListManagerImpl.getShoppingList method");
		try {
			return this.shoppingListDao.getShoppingList(shoppingListId);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	//
	/*
	 * （非 Javadoc）
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.ShoppingListManager#getShoppingListByShoppingNum
	 * (java.lang.String)
	 */
	public ShoppingList getShoppingListByShoppingNum(String shoppingNum) {
		// log.info("ShoppingListManagerImpl.getShoppingListByShoppingNum method");
		try {
			return shoppingListDao.getShoppingListByShoppingNum(shoppingNum);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	//
	/*
	 * （非 Javadoc＄1??7
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.ShoppingListManager#getCountByShoppingNum
	 * (java.lang.String)
	 */
	public int getCountByShoppingNum(String shoppingNum) {
		try {
			return shoppingListDao.getCountByShoppingNum(shoppingNum);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return 0;
	}

	//
	// /*
	// * ??????????????????????????????????1??7
	// * @param parMap
	// * @return
	// */
	// public int getShoppingCountByParameterMap(Map<String, String>
	// parMap)throws Exception {
	// return shoppingListDao.getCountByParameterMap(parMap);
	// }
	//
	@SuppressWarnings("unchecked")
	public QueryPage getShoppingListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception {

		QueryPage queryPage = new QueryPage(shoppingListQuery);
		Map pramas = queryPage.getParameters();
		pramas.put("sameTeamUsers", shoppingListQuery.getSameTeamUsers());
		int count = shoppingListDao.getCountByParameterMap(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			List<ShoppingList> shoppingList = shoppingListDao
					.getShoppingListByParameterMap(pramas);
			if (shoppingList != null && shoppingList.size() > 0) {
				queryPage.setItems(shoppingList);
			}
		}
		return queryPage;
	}

	//
	// public int getDueSearchCountByParameterMap(Map<String, String> parMap) {
	// //
	// log.info("ShoppingListManagerImpl.getDueSearchCountByParameterMap method");
	// try {
	// return shoppingListDao.getDueSearchCountByParameterMap(parMap);
	// } catch (Exception e) {
	// // log.error(e.getMessage());
	// }
	// return 0;
	// }
	//
	@SuppressWarnings("unchecked")
	public QueryPage getDueSearchListsByParameterMap(ShoppingListQuery shoppingListQuery
			 , int currPage, int pageSize)throws Exception {
		//
//		log.info("ShoppingListManagerImpl.getDueSearchListsByParameterMap method");

			QueryPage queryPage = new QueryPage(shoppingListQuery);
			Map pramas = queryPage.getParameters();

			int count = shoppingListDao
					.getDueSearchCountByParameterMap(pramas);

			List<ShoppingList> shoppingLists = null;
			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());

				/* 分页查询操作员记录 */

				shoppingLists = shoppingListDao
						.getDueSearchListsByParameterMap(pramas);
			}


			if (shoppingLists != null) {
				for (ShoppingList tmp : shoppingLists) {
					InDepository indepositoryTemp = null;
					try{
						indepositoryTemp = inDepositoryDao
							.getInDepByRelNum(tmp.getShoppingNum());
					}catch(Exception e)
					{}
					if (indepositoryTemp != null) {
						tmp.setBillNum(indepositoryTemp.getBillNum());
						tmp.setIndepId(indepositoryTemp.getId());
					}
				}
			}

			if (shoppingLists != null
					&& shoppingLists.size() > 0) {
				queryPage.setItems(shoppingLists);
			}
			return queryPage;
	}
	//
	/*
	 * （非 Javadoc＄1??7
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingListManager#
	 * getShoppingCountDetailSearchByParameterMap(java.util.Map)
	 */
	public int getShoppingCountDetailSearchByParameterMap(
			Map<String, String> parMap) {
		return shoppingListDao
				.getShoppingCountDetailSearchByParameterMap(parMap);
	}

	//
	// /* （非 Javadoc＄1??7
	// * @see
	// com.hundsun.bible.facade.ios.ShoppingListManager#shoppingDetailSearchRusultByParameterMap(java.util.Map,
	// com.hundsun.bible.Page)
	// */
	@SuppressWarnings("unchecked")
	public QueryPage getShoppingDetailSearchRusult(
			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
			int pageSize) throws Exception {
		// log.info("ShoppingListManagerImpl.shoppingDetailSearchRusultByParameterMap method");

		QueryPage queryPage = new QueryPage(stockDetailSearchQuery);
		Map pramas = queryPage.getParameters();

		int count = shoppingListDao
				.getShoppingCountDetailSearchByParameterMap(pramas);

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

			List<ShoppingDetailSearch> shoppingDetailSearchs = shoppingListDao
					.getShoppingDetailSearchRusultByParameterMap(pramas);

			if (shoppingDetailSearchs != null
					&& shoppingDetailSearchs.size() > 0) {
				queryPage.setItems(shoppingDetailSearchs);
			}
		}
		return queryPage;

	}

	/*
	 * （非 Javadoc＄1??7
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingListManager#
	 * getShoppingCountGatherSearchByParameterMap(java.util.Map)
	 */
//	public QueryPage getShoppingCountGatherSearchByParameterMap(
//			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
//			int pageSize) throws Exception {
//		// log.info("ShoppingListManagerImpl.getShoppingCountGatherSearchByParameterMap method");
//		// try {
//		// return shoppingListDao
//		// .getShoppingCountGatherSearchByParameterMap(parMap);
//		// } catch (Exception e) {
//		// // log.error(e.getMessage());
//		// }
//		// return 0;
//
//		QueryPage queryPage = new QueryPage(stockDetailSearchQuery);
//		Map<String, String> pramas = queryPage.getParameters();
//
//		int count = shoppingListDao
//				.getShoppingCountGatherSearchByParameterMap(pramas);
//
//		if (count > 0) {
//
//			/* 当前页 */
//			queryPage.setCurrentPage(currPage);
//			/* 每页总数 */
//			queryPage.setPageSize(pageSize);
//			queryPage.setTotalItem(count);
//
//			pramas.put("startRow", String.valueOf(queryPage.getPageFristItem()));
//			pramas.put("endRow", String.valueOf(queryPage.getPageLastItem()));
//
//			/* 分页查询操作员记录 */
//			// List<ShoppingList> shoppingList =
//			// shoppingListDao.getShoppingListByParameterMap(pramas);
//
//			List<ShoppingGatherSearch> shoppingGatherSearchs = shoppingListDao
//					.getShoppingGatherSearchRusultByParameterMap(pramas);
//
//			if (shoppingGatherSearchs != null
//					&& shoppingGatherSearchs.size() > 0) {
//				queryPage.setItems(shoppingGatherSearchs);
//			}
//		}
//		return queryPage;
//	}


	 /* （非 Javadoc＄1??7
	 * @see
	 com.hundsun.bible.facade.ios.ShoppingListManager#getShoppingGatherSearchRusultByParameterMap(java.util.Map,
	 com.hundsun.bible.Page)
	 */
	 @SuppressWarnings("unchecked")
	public QueryPage getShoppingGatherSearchRusult(StockDetailSearchQuery stockDetailSearchQuery,
			 int currPage, int pageSize)throws Exception
	 {
//	 //
//	 log.info("ShoppingListManagerImpl.getShoppingGatherSearchRusultByParameterMap method");
//	 try {
//	 return
//	 this.shoppingListDao.getShoppingGatherSearchRusultByParameterMap(parameterMap,
//	 page);
//	 } catch (Exception e) {
//	 // log.error(e.getMessage());
//	 }
//	 return null;

		 QueryPage queryPage = new QueryPage(stockDetailSearchQuery);
			Map pramas = queryPage.getParameters();

			int count = shoppingListDao
					.getShoppingCountGatherSearchByParameterMap(pramas);

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

				List<ShoppingGatherSearch> shoppingGatherSearchs = shoppingListDao
						.getShoppingGatherSearchRusultByParameterMap(pramas);

				if (shoppingGatherSearchs != null
						&& shoppingGatherSearchs.size() > 0) {
					queryPage.setItems(shoppingGatherSearchs);
				}
			}
			return queryPage;
	 }

	/**
	 * 根据查询条件，查出对应的供货商对应的记录
	 *
	 * @param v_SearchShoppingList
	 *            对应的查询vo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public QueryPage getSearchShoppingLists(
			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
			int pageSize) throws Exception {

		// try {
		// return shoppingListDao.getSearchShoppingLists(v_SearchShoppingList,
		// page);
		// } catch (Exception e) {
		// // log.error(e.getMessage());
		// return null;
		// }

		QueryPage queryPage = new QueryPage(stockDetailSearchQuery);
		Map pramas = queryPage.getParameters();

		int count = shoppingListDao.getCountSupplierShoppingLists(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			List<V_SupplierShoppingList> vSupplierShoppingList = shoppingListDao
					.getSearchShoppingLists(pramas);

			if (vSupplierShoppingList != null
					&& vSupplierShoppingList.size() > 0) {
				queryPage.setItems(vSupplierShoppingList);
			}
		}
		return queryPage;
	}

	//
	// /**
	// * 根据查询条件，查出对应的供货商对应的记录总条敄1??7
	// *
	// * @param v_SearchShoppingList
	// * @return
	// * @throws Exception
	// */
	// public int getCountSupplierShoppingLists(
	// V_SearchShoppingList v_SearchShoppingList)
	// {
	//
	// try
	// {
	// return shoppingListDao
	// .getCountSupplierShoppingLists(v_SearchShoppingList);
	// }
	// catch (Exception e)
	// {
	// // log.error(e.getMessage());
	// return 0;
	// }
	// }
	//
	@Transactional
	@SuppressWarnings("unchecked")
	public Boolean checkShoppingList(Map parMap) throws Exception {
		// 出库单编号
		String billNum = (String) parMap.get("billNum");
		// 用户登录名
		String userName = (String) parMap.get("userName");
		// 不包含验收数量为零的采购单商品集合
		List<ShoppingDetail> shopDetailList = (List<ShoppingDetail>) parMap
				.get("shopDetailList");
		// 所有采购单商品集合
		List<ShoppingDetail> shoppingDetails = (List<ShoppingDetail>) parMap
				.get("shoppingDetails");
		// 采购单对象
		ShoppingList shoppingList = (ShoppingList) parMap.get("shoppingList");
		// 批次
		String batchNum = DateUtil.convertDateToBatch(new Date()).toString()
				+ codeManager.buildCode(CodeManager.PICI_CODE, 4, "");
		try {
			if (shopDetailList.size() > 0) {
				// 生成入库单
				InDepository inDepository = new InDepository();
				inDepository.setBillNum(billNum);
				inDepository.setType(EnumInDepository.IN_SHOPPING.getKey());
				inDepository.setRelationNum(shoppingList.getShoppingNum());
				inDepository.setCreater(userName);
				inDepository.setGmtCreate(new Date());
				inDepository.setGmtModify(new Date());
				inDepository.setStatus(EnumInStatus.IN_NEW.getKey());
				inDepository.setBatchNum(batchNum);
				inDepository.setDepFirstId(shoppingList.getDepFirstId());
				inDepository.setIsWholesale(shoppingList.getIsWholesale());
				if (StringUtil.isNotBlank(shoppingList.getTid())) {
					inDepository.setTid(shoppingList.getTid());
					inDepository.setIsWholesale("w");
				}
				long inId = inDepositoryDao.addInDepository(inDepository);
				InDetail inDetail = null;
				// 生成入库单商品信息
				for (ShoppingDetail shopDetail : shopDetailList) {
					inDetail = new InDetail();
					inDetail.setGoodsInstanceId(shopDetail.getGoodsInstanceId());
					inDetail.setGoodsId(shopDetail.getGoodsId());
					inDetail.setInDepositoryId(inId);
					inDetail.setAmount(shopDetail.getReceiveNum());
					inDetail.setUnitPrice(shopDetail.getUnitPrice());
					inDetail.setDueFee(shopDetail.getDueFee());
					inDetail.setFactFee(shopDetail.getFactFee());
					inDetail.setGmtCreate(new Date());
					inDetail.setGmtModify(new Date());
					inDetail.setDepFirstId(shoppingList.getDepFirstId());
					inDetailDao.addInDetail(inDetail);
				}
			}
			// 入库后减少在途库存数量
			for (ShoppingDetail shopDetail : shoppingDetails) {
				goodsInstanceDao.updateWayNumById(
						shopDetail.getGoodsInstanceId(),
						-(shopDetail.getAmount()));
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public List<ShoppingDetailSearch> getShoppingDetailStorageNum(
			Map<String, String> paramMap) throws Exception {
		try {
			return shoppingListDao.getShoppingDetailStorageNum(paramMap);
		} catch (Exception e) {
			// log.error(e.getMessage());
			return null;
		}
	}
	//
	// public int getDueEstimateSearchCountByParameterMap(
	// Map<String, String> parMap) {
	// //
	// log.info("ShoppingListManagerImpl.getDueEstimateSearchCountByParameterMap method");
	// try{
	// return shoppingListDao.getDueEstimateSearchCountByParameterMap(parMap);
	// }catch(Exception e){
	// // log.error(e.getMessage());
	// }
	// return 0;
	// }

	@SuppressWarnings("unchecked")
	public QueryPage getDueEstimateSearchListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception{
		//
//		log.info("ShoppingListManagerImpl.getDueEstimateSearchListsByParameterMap method");
		try {
//			return shoppingListDao.getDueEstimateSearchListsByParameterMap(
//					parameterMap, page);

			QueryPage queryPage = new QueryPage(shoppingListQuery);
			Map pramas = queryPage.getParameters();

			int count = shoppingListDao.getDueEstimateSearchCountByParameterMap(pramas);

			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<ShoppingList> shoppingLists = shoppingListDao
						.getDueEstimateSearchListsByParameterMap(pramas);

				if (shoppingLists != null
						&& shoppingLists.size() > 0) {
					queryPage.setItems(shoppingLists);
				}
			}
			return queryPage;

		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public List<V_SupplierShoppingList> getSupplierShoppingExportList(
			StockDetailSearchQuery stockDetailSearchQuery) {
		try {
			return shoppingListDao
					.getSupplierShoppingExportList(stockDetailSearchQuery);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

}
