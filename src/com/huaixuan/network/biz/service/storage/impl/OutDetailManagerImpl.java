package com.huaixuan.network.biz.service.storage.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailManager;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 *
 * @version 3.2.0
 */
@Service("outDetailManager")
public class OutDetailManagerImpl implements OutDetailManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	OutDetailDao outDetailDao;

	@Autowired
	StorageDao storageDao;

	@Autowired
	ProdRelationOutDao prodRelationOutDao;

	@Autowired
	OutDepositoryManager outDepositoryManager;


	public Long addOutDetail(OutDetail outDetailDao) {
		log.info("OutDetailManagerImpl.addOutDetail method");
		try {
			return this.outDetailDao.addOutDetail(outDetailDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public void editOutDetail(OutDetail outDetail) {
		log.info("OutDetailManagerImpl.editOutDetail method");
		try {
			this.outDetailDao.editOutDetail(outDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void removeOutDetail(Long outDetailId) {
		log.info("OutDetailManagerImpl.removeOutDetail method");
		try {
			this.outDetailDao.removeOutDetail(outDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public OutDetail getOutDetail(Long outDetailId) {
		log.info("OutDetailManagerImpl.getOutDetail method");
		try {
			return this.outDetailDao.getOutDetail(outDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<OutDetail> getOutDetails() {
		log.info("OutDetailManagerImpl.getOutDetails method");
		try {
			return this.outDetailDao.getOutDetails();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<OutDetailGoods> getOutDetailGoodsLists(Long id) {
		log.info("OutDetailManagerImpl.getOutDetailGoodsLists method");
		try {
			return this.outDetailDao.getOutDetailGoodsLists(id);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public OutDetailBaseInfo getOutDetailBaseInfo(Long outDetailId, String type) {
		log.info("OutDetailManagerImpl.getOutDetailBaseInfo method");
		try {
			if (StringUtil.isNotBlank(type) && EnumOutDepository.OUT_SHOPPING.getKey().equals(type)) {
				return this.outDetailDao.getOutDetailShoppingBaseInfo(outDetailId, type);
			} else {
				return this.outDetailDao.getOutDetailSalesChangeBaseInfo(outDetailId);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<OutDepositoryStorage> getOutStorageList(Map mapSearch) {
		log.info("OutDetailManagerImpl.getOutStorageList method");
		try {
			return this.storageDao.getOutStorageList(mapSearch);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// 对程序中的Exception实现事务回滚(注：若直接@Transactional，则只对RuntimeException进行回滚)
	@Transactional
	public Boolean outDepositoryOpt(Map map, String type) {
		log.info("OutDetailManagerImpl.outDepositoryOpt method");
		try {
			String[] storageIdArray = (String[]) map.get("storageIdArray");
			String[] disCountArray = (String[]) map.get("disCountArray");
			String[] locIdArray = (String[]) map.get("locIdArray");
			OutDetailBaseInfo outDetailBaseInfo = (OutDetailBaseInfo) map.get("outDetailBaseInfo");
			String isWholesale = (String) map.get("isWholesale");
			String tid = (String) map.get("tid");

			// 先删除原有数据
			prodRelationOutDao.removeProdRelationOutForAdd(outDetailBaseInfo.getOutDepositoryId(),
					outDetailBaseInfo.getGoodsInstanceId(), outDetailBaseInfo.getOutDetailId(),
					outDetailBaseInfo.getGoodsId());

			for (int i = 0; i < disCountArray.length; i++) {
				if (StringUtil.isNotBlank(disCountArray[i]) && StringUtil.isNumeric(disCountArray[i])
						&& new Long(disCountArray[i]) > 0) {
					ProdRelationOut prodRelationOutInfo = new ProdRelationOut();
					prodRelationOutInfo.setOutDepId(outDetailBaseInfo.getOutDepositoryId());
					prodRelationOutInfo.setGoodsInstanceId(outDetailBaseInfo.getGoodsInstanceId());
					prodRelationOutInfo.setAmount(new Long(disCountArray[i]));
					prodRelationOutInfo.setGoodsId(outDetailBaseInfo.getGoodsId());
					prodRelationOutInfo.setOutDetailId(outDetailBaseInfo.getOutDetailId());
					prodRelationOutInfo.setStorageId(new Long(storageIdArray[i]));
					prodRelationOutInfo.setLocId(new Long(locIdArray[i]));
					Storage storageInfo = storageDao.getStorage(new Long(storageIdArray[i]));
					if (storageInfo != null) {
						outDetailDao.updateOutDetailStatusById(outDetailBaseInfo.getOutDetailId(),
								storageInfo.getStorType());
					}
					// 设置成本价
					if (StringUtil.isNotBlank(type)
							&& (type.equals(EnumOutDepository.OUT_SALES.getKey()) || type
									.equals(EnumOutDepository.OUT_SALES_CHANGE.getKey()))) {
						// 销售订单和销售换货情况需从库存表中取的价格

						if (storageInfo != null) {
							prodRelationOutInfo.setSelfCost(storageInfo.getPrice());

						}
					} else {
						prodRelationOutInfo.setSelfCost(outDetailBaseInfo.getUnitPrice());
					}
					prodRelationOutInfo.setGmtCreate(new Date());
					prodRelationOutInfo.setGmtModify(new Date());

					// 设置批发类型和tid
					prodRelationOutInfo.setTid(tid);
					prodRelationOutInfo.setIsWholesale(isWholesale);

					// 添加到商品出库关联表中
					prodRelationOutDao.addProdRelationOut(prodRelationOutInfo);
					// 更新出库详单状态
					outDetailDao.updateOutDetailStatusById(outDetailBaseInfo.getOutDetailId(),
							EnumOutDetailStatus.OUT_FINISHED.getKey());
				}
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return Boolean.FALSE;
	}

	// added by chenhang 2010/11/04 started
	public Boolean outDepositoryOptAuto(Map map, String type) {
		log.info("OutDetailManagerImpl.outDepositoryOpt method");
		try {
			List<String> storageIdList = (List<String>) map.get("storageIdList");
			List<String> disCountList = (List<String>) map.get("disCountList");
			List<String> locIdList = (List<String>) map.get("locIdList");
			OutDetailBaseInfo outDetailBaseInfo = (OutDetailBaseInfo) map.get("outDetailBaseInfo");
			String isWholesale = (String) map.get("isWholesale");
			String tid = (String) map.get("tid");

			// 先删除原有数据
			prodRelationOutDao.removeProdRelationOutForAdd(outDetailBaseInfo.getOutDepositoryId(),
					outDetailBaseInfo.getGoodsInstanceId(), outDetailBaseInfo.getOutDetailId(),
					outDetailBaseInfo.getGoodsId());

			for (int i = 0; i < disCountList.size(); i++) {
				if (StringUtil.isNotBlank(disCountList.get(i)) && StringUtil.isNumeric(disCountList.get(i))
						&& new Long(disCountList.get(i)) > 0) {
					ProdRelationOut prodRelationOutInfo = new ProdRelationOut();
					prodRelationOutInfo.setOutDepId(outDetailBaseInfo.getOutDepositoryId());
					prodRelationOutInfo.setGoodsInstanceId(outDetailBaseInfo.getGoodsInstanceId());
					prodRelationOutInfo.setAmount(new Long(disCountList.get(i)));
					prodRelationOutInfo.setGoodsId(outDetailBaseInfo.getGoodsId());
					prodRelationOutInfo.setOutDetailId(outDetailBaseInfo.getOutDetailId());
					prodRelationOutInfo.setStorageId(new Long(storageIdList.get(i)));
					prodRelationOutInfo.setLocId(new Long(locIdList.get(i)));
					Storage storageInfo = storageDao.getStorage(new Long(storageIdList.get(i)));
					if (storageInfo != null) {
						outDetailDao.updateOutDetailStatusById(outDetailBaseInfo.getOutDetailId(),
								storageInfo.getStorType());
					}
					// 设置成本价
					if (StringUtil.isNotBlank(type)
							&& (type.equals(EnumOutDepository.OUT_SALES.getKey()) || type
									.equals(EnumOutDepository.OUT_SALES_CHANGE.getKey()))) {
						// 销售订单和销售换货情况需从库存表中取的价格

						if (storageInfo != null) {
							prodRelationOutInfo.setSelfCost(storageInfo.getPrice());

						}
					} else {
						prodRelationOutInfo.setSelfCost(outDetailBaseInfo.getUnitPrice());
					}
					prodRelationOutInfo.setGmtCreate(new Date());
					prodRelationOutInfo.setGmtModify(new Date());

					// 设置批发类型和tid
					prodRelationOutInfo.setTid(tid);
					prodRelationOutInfo.setIsWholesale(isWholesale);

					// 添加到商品出库关联表中
					prodRelationOutDao.addProdRelationOut(prodRelationOutInfo);
					// 更新出库详单状态
					outDetailDao.updateOutDetailStatusById(outDetailBaseInfo.getOutDetailId(),
							EnumOutDetailStatus.OUT_FINISHED.getKey());
				}
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return Boolean.FALSE;
	}

	// added by chenhang 2010/11/04 ended
	public List<OutDetailGoods> listOutDetailNotFinish(Long outDepId, String status) {
		log.info("OutDetailManagerImpl.listOutDetailNotFinish method");
		try {
			return outDetailDao.listOutDetailNotFinish(outDepId, status);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public GatherOutDepository gatherFinanceOutDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery) {
		log.info("OutDetailManagerImpl.gatherFinanceOutDepositoryCount method");
		try {
			return outDetailDao.gatherFinanceOutDepositoryCount(financeDepositoryQuery);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	 public QueryPage gatherFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize) {
	 log.info("OutDetailManagerImpl.gatherFinanceOutDepositoryLists method");
	 try {
	 return outDetailDao.gatherFinanceOutDepositoryLists(financeDepositoryQuery, currPage ,pageSize);
	 } catch (Exception e) {
	 log.error(e.getMessage());
	 }
	 return null;
	 }

	public GatherOutDepository estimateFinanceOutDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery) {
		log.info("OutDetailManagerImpl.estimateFinanceOutDepositoryCount method");
		try {
			return outDetailDao.estimateFinanceOutDepositoryCount(financeDepositoryQuery);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	 public QueryPage estimateFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize) {
	 log.info("OutDetailManagerImpl.estimateFinanceOutDepositoryLists method");
	 try {
	 return outDetailDao.estimateFinanceOutDepositoryLists(financeDepositoryQuery, currPage,pageSize);
	 } catch (Exception e) {
	 log.error(e.getMessage());
	 }
	 return null;
	 }

	public List<OutDetail> getOutDetailListByOutDepositoryId(Long outDepositoryId) {
		log.info("OutDetailManagerImpl.getOutDetailListByOutDepositoryId method");
		try {
			return outDetailDao.getOutDetailListByOutDepositoryId(outDepositoryId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public OutDepositoryManager getOutDepositoryManager() {
		return outDepositoryManager;
	}

	public void setOutDepositoryManager(OutDepositoryManager outDepositoryManager) {
		this.outDepositoryManager = outDepositoryManager;
	}

}
