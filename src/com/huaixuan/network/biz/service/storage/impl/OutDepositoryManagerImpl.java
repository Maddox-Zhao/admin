
package com.huaixuan.network.biz.service.storage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.stock.ShoppingRefundDao;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.dao.storage.OutDepAnalysisDao;
import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.dao.trade.TradePackageDao;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepAnalysis;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailGb;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.ProdRelationOutGb;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumIsWholesale;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumShoppingRefundStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailGbManager;
import com.huaixuan.network.biz.service.storage.ProdRelationOutGbManager;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("outDepositoryManager")
public class OutDepositoryManagerImpl implements OutDepositoryManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	OutDepositoryDao outDepositoryDao;
	@Autowired
	DepositoryFirstDao depositoryFirstDao;
	@Autowired
	TradeDao tradeDao;

	// public GoodsInstanceDao goodsInstanceDao;
	@Autowired
	private ProdRelationOutDao prodRelationOutDao;
	@Autowired
	private StorageDao storageDao;
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	@Autowired
	private RefundDao refundDao;
	@Autowired
	private ShoppingRefundDao shoppingRefundDao;
	@Autowired
	private OutDetailDao outDetailDao;
	@Autowired
	private DepLocationDao depLocationDao;
	@Autowired
	private DepositoryDao depositoryDao;
	@Autowired
	private TradePackageDao tradePackageDao;
	@Autowired
	public OutDepAnalysisDao outDepAnalysisDao;
	@Autowired
	public GoodsDao goodsDao;
	@Autowired
	public OutDetailGbManager outDetailGbManager;
	@Autowired
	public ProdRelationOutGbManager prodRelationOutGbManager;

	@Override
	public Long addOutDepository(OutDepository outDepositoryDao) {
		log.info("OutDepositoryManagerImpl.addOutDepository method");
		try {
			return this.outDepositoryDao.addOutDepository(outDepositoryDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void editOutDepository(OutDepository outDepository) {
		// log.info("OutDepositoryManagerImpl.editOutDepository method");
		try {
			this.outDepositoryDao.editOutDepository(outDepository);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
	}

	//
	/* @model: */
	// public void removeOutDepository(Long outDepositoryId) {
	// log.info("OutDepositoryManagerImpl.removeOutDepository method");
	// try {
	// this.outDepositoryDao.removeOutDepository(outDepositoryId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }

	/* @model: */
	public OutDepository getOutDepository(Long outDepositoryId) {
		log.info("OutDepositoryManagerImpl.getOutDepository method");
		OutDepository out = outDepositoryDao.getOutDepository(outDepositoryId);
		if (out != null) {
			out.setTradePackageList(tradePackageDao
					.getTradePackagesByMergeTid(out.getRelationNum()));
		}
		return out;
	}

	//
	/* @model: */
	// public List<OutDepository> getOutDepositorys() {
	// log.info("OutDepositoryManagerImpl.getOutDepositorys method");
	// try {
	// return this.outDepositoryDao.getOutDepositorys();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	public int getOutDepositoryListsCount(Map<String, String> parMap) {
		log.info("OutDepositoryManagerImpl.getOutDepositoryListsCount method");
		try {
			return this.outDepositoryDao.getOutDepositoryListsCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public QueryPage getOutDepositoryLists(Map<String, String> parMap,
			int currentPage, int pageSize, boolean isPage) {
		log.info("OutDepositoryManagerImpl.getOutDepositoryLists method");
		try {
			QueryPage queryPage = this.outDepositoryDao.getOutDepositoryLists(
					parMap, currentPage, pageSize, isPage);
			List<OutDepository> resultList = (List<OutDepository>) queryPage
					.getItems();
			for (OutDepository out : resultList) {
				out.setTradePackageList(tradePackageDao
						.getTradePackagesByMergeTid(out.getRelationNum()));
			}
			queryPage.setItems(resultList);
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public QueryPage getActualInventoryLists(Map<String, String> parMap,
			int currentPage, int pageSize, boolean isPage) {
		log.info("OutDepositoryManagerImpl.getActualInventoryLists method");
		try {
			QueryPage queryPage = this.outDepositoryDao.getActualInventoryLists(
					parMap, currentPage, pageSize, isPage);
			List<OutDepository> resultList = (List<OutDepository>) queryPage
					.getItems();
			for (OutDepository out : resultList) {
				out.setTradePackageList(tradePackageDao
						.getTradePackagesByMergeTid(out.getRelationNum()));
			}
			queryPage.setItems(resultList);
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	// 对程序中的Exception实现事务回滚(注：若直接@Transactional，则只对RuntimeException进行回滚)
	@Transactional
	public Boolean removeStorageOpt(Map map) throws Exception {
		log.info("OutDepositoryManagerImpl.removeStorageOpt method");
		// 出库表ID
		Long outDepId = (Long) map.get("outDepId");
		// 出库时间 //出入库时间改为点击完成的时间，不能更改 zhangwy 2010/11/23
		// Date gmtOutDep = (Date) map.get("gmtOutDep");
		Date gmtOutDep = new Date();
		// 出库单类型
		String type = (String) map.get("outDepType");
		// 取得关联单据号
		String relationNum = (String) map.get("relationNum");
		List<String> relationNumList = (List<String>) map
				.get("relationNumList");
		// 出库单详情信息
		List<OutDetailGoods> outDetailGoodsList = (List<OutDetailGoods>) map
				.get("outDetailGoodsLists");
		// 取得登录人
		String modifier = (String) map.get("modifier");
		// 判断是否是批发出库
		String isWholesale = (String) map.get("isWholesale");
		// 订单TID
		String tid = (String) map.get("tid");

		if (outDepId == null || outDepId <= 0 || outDetailGoodsList == null
				|| outDetailGoodsList.size() <= 0) {
			return Boolean.FALSE;
		}
		if (StringUtil.isNotBlank(type)
				&& EnumOutDepository.OUT_SALES.getKey().equals(type)) {
			// 更新交易表的状态(销售订单的类型才更新)
			Map tradeMap = new HashMap();
			tradeMap.put("status",
					EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey());
			tradeMap.put("relationNumList", relationNumList);// 销售单号集合
			this.tradeDao.updateTradeByRefund(tradeMap);
		} else if (StringUtil.isNotBlank(type)
				&& EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(type)) {
			// 更新退货表的状态(销售退货的类型才更新)
			Map refundMap = new HashMap();
			refundMap.put("status",
					EnumRefundStatus.Goods_Confirm_Success.getKey());
			refundMap.put("refundId", relationNum);
			refundMap.put("isGoodsRecevied", "1");
			this.refundDao.updateRefundStatusByRefId(refundMap);
		} else if (StringUtil.isNotBlank(type)
				&& EnumOutDepository.OUT_STORAGE_REFUND.getKey().equals(type)) {
			// 更新退货表的状态(库存退货的类型才更新) modify by fanyj 2010-11-2
			ShoppingRefund shoppingRefund = new ShoppingRefund();
			shoppingRefund.setStatus(EnumShoppingRefundStatus.STOCK_FINISHED
					.getKey());
			shoppingRefund.setRefNum(relationNum);
			shoppingRefundDao.editShoppingRefundStatus(shoppingRefund);
		}
		// 1.更新出库单表的状态为已完成
		outDepositoryDao.updateOutDepositoryStatusById(outDepId,
				EnumOutStatus.OUT_FINISHED.getKey(), gmtOutDep, modifier);
		// 操作库存表
		for (OutDetailGoods outDetailGoodsInfo : outDetailGoodsList) {
			//商品开票id
			Long billId = goodsDao.getBillId(outDetailGoodsInfo.getGoodsId());
			long virtualNum = 0;
			// 取得关联表的数量和库存表ID
			List<ProdRelationOut> prodRelationOutList = prodRelationOutDao
					.getAmountAndStorageId(outDepId,
							outDetailGoodsInfo.getGoodsInstanceId(),
							outDetailGoodsInfo.getGoodsId(),
							outDetailGoodsInfo.getId());
			if (prodRelationOutList != null && prodRelationOutList.size() > 0) {
				for (ProdRelationOut prodRelationOut : prodRelationOutList) {

					if (prodRelationOut != null) {

						this.storageDao.updateStorageStorageNumById(
								0 - prodRelationOut.getAmount(),
								prodRelationOut.getStorageId());
						// 判断是否可以更新
						Storage storageTemp = storageDao
								.getStorage(prodRelationOut.getStorageId());
						if (storageTemp == null
								|| storageTemp.getStorageNum() < 0) {
							// 实际库存为负，不正确，需回滚。
							throw new Exception("可用库存不够出库。StorageId:"
									+ prodRelationOut.getStorageId() + ".数量:"
									+ prodRelationOut.getAmount());
						}

						if (StringUtil.isBlank(isWholesale)
								|| !EnumIsWholesale.IS_WHOLESALE_W.getKey()
										.equals(isWholesale)) {
							// 不为批发出库的时候才更新可用库存
							DepLocation depLocation = depLocationDao
									.getLocationsById(prodRelationOut
											.getLocId());
							// 一级仓库类型验证 zhangwy
							DepositoryFirst depositoryFirst = depositoryFirstDao
									.getDepositoryById(storageTemp
											.getDepFirstId());

							// 次品库不减可用库存
							if (depLocation != null
									&& (!depositoryFirst.getType().equals("w"))) {
								Depository depository = depositoryDao
										.getDepository(depLocation.getDepId());
								if (!EnumDepositoryType.DEFECT_DEP.getKey()
										.equals(depository.getType())) {
									if (StringUtil.isBlank(type)
											|| (!EnumOutDepository.OUT_SALES
													.getKey().equals(type)
													&& !EnumOutDepository.OUT_SALES_CHANGE
															.getKey().equals(
																	type) && !EnumOutDepository.OUT_SHOPPING
													.getKey().equals(type))) {
										// 更新商品实例表和商品表的库存数量(销售订单或者销售换货的类型不更新)
										goodsInstanceManager
												.updateAmountForTwo(
														outDetailGoodsInfo
																.getGoodsInstanceId(),
														outDetailGoodsInfo
																.getGoodsId(),
														0 - prodRelationOut
																.getAmount(),
														outDetailGoodsInfo
																.getDepFirstId(),
														true);
									}
								}
							}
						}
						outDetailDao.updateOutDetailStorTypeById(
								prodRelationOut.getOutDetailId(),
								storageTemp.getStorType());
						if (EnumStorType.STOR_TYPE_V.getKey().equals(
								storageTemp.getStorType())) {
							virtualNum = virtualNum
									+ prodRelationOut.getAmount();
						}
                        //开票公司出库分配表加入 zhangwy
						if(billId != null){
							ProdRelationOutGb prodRelationOutGb = new ProdRelationOutGb();
							prodRelationOutGb.setRelationId(prodRelationOut.getId());
							prodRelationOutGb.setOutDepId(prodRelationOut.getOutDepId());
							prodRelationOutGb.setGoodsInstanceId(prodRelationOut.getGoodsInstanceId());
							prodRelationOutGb.setAmount(prodRelationOut.getAmount());
							prodRelationOutGb.setGoodsId(prodRelationOut.getGoodsId());
							prodRelationOutGb.setOutDetailId(prodRelationOut.getOutDetailId());
							prodRelationOutGb.setStorageId(prodRelationOut.getStorageId());
							prodRelationOutGb.setLocId(prodRelationOut.getLocId());
							prodRelationOutGb.setSelfCost(prodRelationOut.getSelfCost());
							prodRelationOutGb.setIsWholesale(prodRelationOut.getIsWholesale());
							prodRelationOutGb.setTid(prodRelationOut.getTid());
							prodRelationOutGb.setBillId(billId);
							prodRelationOutGbManager.addProdRelationOutGb(prodRelationOutGb);
						}
					} else {
						throw new Exception("商品出库失败。outDetailGoodsList:"
								+ outDetailGoodsList);
					}
				}
				outDetailGoodsInfo.setOutVirtualNum(virtualNum);
				// 加入全部一级仓库的剩余库存
				long leftDepNum = this.storageDao.sumStorageByGoodsInstanceId(
						outDetailGoodsInfo.getGoodsInstanceId(),
						outDetailGoodsInfo.getDepFirstId());
				outDetailGoodsInfo.setLeftDepNum(leftDepNum);
				// 加入剩余库存 zhangwy
				long leftNum = this.storageDao.sumStorageByGoodsInstanceId(
						outDetailGoodsInfo.getGoodsInstanceId(), null);

				outDetailGoodsInfo.setLeftNum(leftNum);
				outDetailDao.editOutOutDetailGoods(outDetailGoodsInfo);
				//开票公司出库单详情表加入 zhangwy
				if(billId!=null){
					OutDetail outDetail = outDetailDao.getOutDetail(outDetailGoodsInfo.getId());
					OutDetailGb outDetailGb = new OutDetailGb();
					outDetailGb.setRelationId(outDetail.getId());
					outDetailGb.setGoodsInstanceId(outDetail.getGoodsInstanceId());
					outDetailGb.setGoodsId(outDetail.getGoodsId());
					outDetailGb.setOutDepositoryId(outDetail.getOutDepositoryId());
					outDetailGb.setOutNum(outDetail.getOutNum());
					outDetailGb.setUnitPrice(outDetail.getUnitPrice());
					outDetailGb.setDueFee(outDetail.getDueFee());
					outDetailGb.setFactFee(outDetail.getFactFee());
					outDetailGb.setStatus(outDetail.getStatus());
					outDetailGb.setLeftNum(outDetail.getLeftNum());
					outDetailGb.setRelationNum(outDetail.getRelationNum());
					outDetailGb.setDepFirstId(outDetail.getDepFirstId());
					outDetailGb.setStorType(outDetail.getStorType());
					outDetailGb.setOutVirtualNum(outDetail.getOutVirtualNum());
					outDetailGb.setLeftDepNum(outDetail.getLeftDepNum());
					outDetailGb.setBillId(billId);
					outDetailGbManager.addOutDetailGb(outDetailGb);
				}
			} else {
				throw new Exception("商品出库失败。outDetailGoodsList:"
						+ outDetailGoodsList);
			}
		}
		return Boolean.TRUE;
	}

	// public String getOutDepositoryStatusByDetailId(Long outDetailId) {
	// log.info("InDepositoryManagerImpl.getOutDepositoryStatusByDetailId method");
	// try {
	// return
	// this.outDepositoryDao.getOutDepositoryStatusByDetailId(outDetailId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return "";
	// }
	//
	// /**
	// * <p>
	// * Title: updateActualWeightById
	// * </p>
	// * <p>
	// * Description: 鏍规嵁ID鏇存柊瀹為檯閲嶉噺
	// * </p>
	/*
	 * @param actualWeight
	 *
	 * @param id
	 *
	 * @return
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.OutDepositoryManager#updateActualWeightById
	 * (java.lang.Double, java.lang.String)
	 */
	public int updateActualWeightById(Double actualWeight, String id) {
		log.info("InDepositoryManagerImpl.updateActualWeightById method");
		try {
			return this.outDepositoryDao.updateActualWeightById(actualWeight,
					id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/**
	 * <p>
	 * Title: updateCastWeightById
	 * </p>
	 * <p>
	 * Description: 鏍规嵁ID鏇存柊璁℃姏閲嶉噺
	 * </p>
	 *
	 * @param castWeight
	 * @param id
	 * @return
	 * @see com.hundsun.bible.facade.ios.OutDepositoryManager#updateCastWeightById(java.lang.Double,
	 *      java.lang.String)
	 */
	public int updateCastWeightById(Double castWeight, String id) {
		log.info("InDepositoryManagerImpl.updateCastWeightById method");
		try {
			return this.outDepositoryDao.updateCastWeightById(castWeight, id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/**
	 *
	 * @Title: updateActualInventoryById
	 * @Description: TODO
	 * @param @param actualInventory
	 * @param @param outDepId
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int updateActualInventoryById(Double actualInventory, String reNum) {
		log.info("InDepositoryManagerImpl.updateActualInventoryById method");
		try {
			return this.tradeDao.updateActualInventoryById(actualInventory,
					reNum);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/**
	 *
	 * @Title: updateActualInventoryById
	 * @Description: TODO
	 * @param @param actualInventory
	 * @param @param reNum
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int updateActualInventoryByIdRe(Double actualInventory, String reNum) {
		log.info("InDepositoryManagerImpl.updateActualInventoryById method");
		try {
			return this.tradeDao.updateActualInventoryByIdRe(actualInventory,
					reNum);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public int updateExpressCodeById(String expressCode, Long outDepId) {
		return this.outDepositoryDao.updateExpressCodeById(expressCode,
				outDepId);
	}

	@SuppressWarnings("unchecked")
	public List<OutDepository> getOutDepositorysWithDetail(Map parmap) {
		log.info("InDepositoryManagerImpl.getOutDepositorysWithDetail method");
		try {
			List<OutDepository> outdetailList = outDepositoryDao
					.getOutDepositorysWithDetail(parmap);
			for (OutDepository tmp : outdetailList) {
				// 设置关联单号集合
				tmp.setTradePackageList(tradePackageDao
						.getTradePackagesByMergeTid(tmp.getRelationNum()));
				// 设置一级仓库名称
				if (tmp.getDepFirstId() != null) {
					DepositoryFirst depositoryFirst = depositoryFirstDao
							.getDepositoryById(tmp.getDepFirstId());
					if (depositoryFirst != null) {
						tmp.setDepFirstName(depositoryFirst.getDepFirstName());
					}
				}
			}
			return outdetailList;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// public int getOutDepositorysWithDetailCount(Map parmap) {
	// return this.outDepositoryDao.getOutDepositorysWithDetailCount(parmap);
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.OutDepositoryManager#gatherOutDepositoryLists
	 * (java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public QueryPage gatherOutDepositoryLists(GatherQuery gatherQuery,
			int currPage, int pageSize, boolean isPage) {
		log.info("InDepositoryManagerImpl.gatherOutDepositoryLists method");
		QueryPage queryPage = new QueryPage(gatherQuery);
		Map parMap = queryPage.getParameters();
		parMap.put("depfirstIds", gatherQuery.getDepfirstIds());
		try {
			return this.outDepositoryDao.gatherOutDepositoryLists(parMap,
					currPage, pageSize, isPage);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.hundsun.bible.facade.ios.OutDepositoryManager#gatherOutDepositoryListsCount(java.util.Map)
	// */
	// public int gatherOutDepositoryListsCount(Map<String, String> parMap) {
	// try {
	// return this.outDepositoryDao.gatherOutDepositoryListsCount(parMap);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }

	public OutDepository getOutDepositoryByTid(String tid) {
		log.info("OutDepositoryManagerImpl.getOutDepositoryByTid method");
		return outDepositoryDao.getOutDepositoryByTid(tid);
	}

	public OutDepository getOutDepositoryByDetailId(Long detailId) {
		log.info("OutDepositoryManagerImpl.getOutDepositoryByDetailId method");
		return this.outDepositoryDao.getOutDepositoryByDetailId(detailId);
	}

	//
	// public List<OutDepository> getOutDepositoryByExpressCode(String
	// expressCode) {
	// try {
	// return this.outDepositoryDao.getOutDepositoryByExpressCode(expressCode);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	/**
	 * @Title: updateIsOutDepositoryPrintedById
	 * @Description: 鏇存柊isOutDepositoryPrinted瀛楁
	 * @param ids
	 * @return int
	 * @throws Exception
	 */
	public int updateIsOutDepositoryPrintedById(String[] ids) {
		log.info("InDepositoryManagerImpl.updateIsOutDepositoryPrintedById method");
		try {
			if (ids != null && ids.length > 0) {
				return this.outDepositoryDao.updateIsOutDepositoryPrintedById(ids);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/**
	 * @Title: updateIsExpressPrintedById
	 * @Description: 鏇存柊isExpressPrinted瀛楁
	 * @param ids
	 * @return int
	 * @throws Exception
	 */
	public int updateIsExpressPrintedById(String[] ids) {
		log.info("InDepositoryManagerImpl.updateIsExpressPrintedById method");
		try {
			if (ids != null && ids.length > 0) {
				return this.outDepositoryDao.updateIsExpressPrintedById(ids);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public int getOutDepAnalysisCount(Map parMap) {
		try {
			return outDepAnalysisDao.getOutDepAnalysisCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public QueryPage getOutDepAnalysis(Map<String, String> outDepParam,
			int currPage, int pageSize, boolean isPage) {
		QueryPage out1 = outDepAnalysisDao.getOutDepAnalysis(outDepParam,
				currPage, pageSize, isPage);
		List<OutDepAnalysis> out2 = outDepAnalysisDao
				.getOutDepAnalysisTradeCount(outDepParam, currPage, pageSize,
						isPage);
		if(out1.getItems()!=null && out1.getItems().size()>0){
			for (int i = 0; i < out1.getItems().size(); i++) {
				OutDepAnalysis o = (OutDepAnalysis) out1.getItems().get(i);
				o.setOutDepSum(out2.get(i).getOutDepSum());
			}
		}
		return out1;
	}
	
    public int updateHandleAdminIdByUser(Long handleAdminId, Long outDepositoryId) {
        log.info("OutDepositoryManagerImpl.updateHandleAdminIdByUser method");
        try {
            return this.outDepositoryDao.updateHandleAdminIdByUser(handleAdminId, outDepositoryId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
