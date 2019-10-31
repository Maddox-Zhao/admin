package com.huaixuan.network.biz.service.storage.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.dao.stock.ShoppingListDao;
import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationInDao;
import com.huaixuan.network.biz.dao.storage.StorageCheckDetailDao;
import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInDetailStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.InDetailManager;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 *
 * @version 3.2.0
 */
@Service("inDetailManager")
public class InDetailManagerImpl implements InDetailManager {
	Log log = LogFactory.getLog(this.getClass());
/* @model: */
	@Autowired
	InDetailDao inDetailDao;
	@Autowired
	CatAttrRelDao catAttrRelDao;
	@Autowired
	ProdRelationInDao prodRelationInDao;
	@Autowired
	StorageCheckDetailDao storageCheckDetailDao;
	@Autowired
	ShoppingListDao shoppingListDao;
	@Autowired
	InDepositoryDao inDepositoryDao;
	@Autowired
	RefundDao refundDao;
/* @model: */
	public long addInDetail(InDetail inDetailDao) {
		log.info("InDetailManagerImpl.addInDetail method");
		try {
			return this.inDetailDao.addInDetail(inDetailDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}
/* @model: */
	public void editInDetail(InDetail inDetail) {
		log.info("InDetailManagerImpl.editInDetail method");
		try {
			this.inDetailDao.editInDetail(inDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeInDetail(Long inDetailId) {
		log.info("InDetailManagerImpl.removeInDetail method");
		try {
			this.inDetailDao.removeInDetail(inDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public InDetail getInDetail(Long inDetailId) {
		log.info("InDetailManagerImpl.getInDetail method");
		try {
			return this.inDetailDao.getInDetail(inDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
/* @model: */
	public List<InDetail> getInDetails() {
		log.info("InDetailManagerImpl.getInDetails method");
		try {
			return this.inDetailDao.getInDetails();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<InDetailGoods> getInDetailGoodsLists(Long id, String type, String relationNum) {
		log.info("InDetailManagerImpl.getInDetailGoodsLists method");
		try {
			List<InDetailGoods> inDetailGoodsLists = this.inDetailDao.getInDetailGoodsLists(id);
			if (inDetailGoodsLists != null && inDetailGoodsLists.size() > 0) {
				for (InDetailGoods inDetailGoodsInfo : inDetailGoodsLists) {
					Long supplierId = 0L;
					// ȡ�ù�Ӧ��ID
					if (EnumInDepository.IN_SHOPPING.getKey().equals(type)) {
						supplierId = shoppingListDao.getSupplierIdByShoppingNum(relationNum);
					} else if (EnumInDepository.IN_CHECK_MORE.getKey().equals(type)) {
						// �ڸ���ʱ�����
					} else if (EnumInDepository.IN_SALES_CHANGE.getKey().equals(type)
							|| EnumInDepository.IN_SALES_REFUND.getKey().equals(type)) {
						// �ڸ���ʱ�����
					}
					// ���ù�Ӧ��ID
					inDetailGoodsInfo.setSupplierId(supplierId);
				}
			}
			return inDetailGoodsLists;

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<GoodsForLocation> getLocationForGoods(Long id, Long depFirstId) {
		log.info("InDetailManagerImpl.getLocationForGoods method");
		try {
			return this.inDetailDao.getLocationForGoods(id, depFirstId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<GoodsForLocation> getLocationForGoodsNoMatch(Long id, Long depFirstId) {
		log.info("InDetailManagerImpl.getLocationForGoodsNoMatch method");
		try {
			return this.inDetailDao.getLocationForGoodsNoMatch(id, depFirstId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// �Գ����е�Exceptionʵ������ع�(ע����ֱ��@Transactional����ֻ��RuntimeException���лع�)
	@Transactional
	public void updateInDetailRelationIn(InDetail inDetailInfo, Map mapForUpdate, InDetailBaseInfo inDetailBaseInfo) {
		log.info("InDetailManagerImpl.updateInDetailRelationIn method");
		String type = (String) mapForUpdate.get("type");
		String[] locationNumArray = (String[]) mapForUpdate.get("locationNumArray");
		String[] disCountArray = (String[]) mapForUpdate.get("disCountArray");
		String[] supplierIdForUpdateArray = (String[]) mapForUpdate.get("supplierIdForUpdateArray");
		String[] batchNumForUpdateArray = (String[]) mapForUpdate.get("batchNumForUpdateArray");
		String[] selfCostForUpdateArray = (String[]) mapForUpdate.get("selfCostForUpdateArray");
		String[] storTypeForUpdateArray = (String[]) mapForUpdate.get("storTypeForUpdateArray");

		String storType = inDetailBaseInfo.getStorType();
		if (StringUtil.isNotBlank(type)
				&& (EnumInDepository.IN_SALES_CHANGE.getKey().equals(type) || EnumInDepository.IN_SALES_REFUND.getKey()
						.equals(type)) && storTypeForUpdateArray != null && storTypeForUpdateArray.length > 0) {
			// �����˻���ʱ�򣬿�����ʹ�ԭ���ⵥ��ȡ
			storType = storTypeForUpdateArray[0];
		}

		// �������������״̬Ϊ�����(finish),����һ���ֿ�Ϳ������
		inDetailDao.updateStatusById(EnumInDetailStatus.IN_FINISHED.getKey(), inDetailInfo.getId(),
				inDetailBaseInfo.getDepFirstId(), storType);

		ProdRelationIn prodRelationIn = new ProdRelationIn();
		prodRelationIn.setDepFirstId(inDetailBaseInfo.getDepFirstId());
		prodRelationIn.setStorType(inDetailBaseInfo.getStorType());
		prodRelationIn.setInDepId(inDetailInfo.getInDepositoryId());
		prodRelationIn.setGoodsInstanceId(inDetailInfo.getGoodsInstanceId());
		prodRelationIn.setGoodsId(inDetailInfo.getGoodsId());
		prodRelationIn.setInDetailId(inDetailInfo.getId());
		prodRelationIn.setGmtCreate(new Date());
		prodRelationIn.setGmtModify(new Date());
		// ���óɱ���(�����˻���ʱ��ɱ��������´ӿ�����ȡ��)
		prodRelationIn.setSelfCost(inDetailInfo.getUnitPrice());
		// ���Σ��ɹ����ʱ��ֱ�Ӵ����������ȡ��
		InDepository inDepositoryInfoTemp = inDepositoryDao.getInDepository(inDetailInfo.getInDepositoryId());
		if (StringUtil.isNotBlank(type) && EnumInDepository.IN_SHOPPING.getKey().equals(type)) {
			// �ɹ����ʱ����������κ͹�Ӧ��ID,�Ҹ���tid�Ͳɹ�����
			ShoppingList shoppingListInfo = this.shoppingListDao.getSupplierIdAndGmtCreateForUpdate(inDetailInfo
					.getInDepositoryId());

			if (shoppingListInfo != null) {
				prodRelationIn.setSupplierId(shoppingListInfo.getSupplierId());
			}

			if (inDepositoryInfoTemp != null) {
				prodRelationIn.setBatchNum(inDepositoryInfoTemp.getBatchNum());
			}
		}

		// added by chenyan 2010/06/10 start �����������ɹ���
		if (inDepositoryInfoTemp != null) {
			// ����tid�����ڶ�Ӧ�˲ɹ�����Ʒֻ�ܳ���Ķ���
			prodRelationIn.setTid(inDepositoryInfoTemp.getTid());
			// ���òɹ�������
			prodRelationIn.setIsWholesale(inDepositoryInfoTemp.getIsWholesale());
		}
		// added by chenyan 2010/06/10 end

		// ��ɾ��
		prodRelationInDao.removeProdRelationInForAdd(prodRelationIn);

		// �����Ʒ����λ����������
		for (int i = 0; i < disCountArray.length; i++) {
			if (StringUtil.isNotBlank(disCountArray[i]) && StringUtil.isNumeric(disCountArray[i])
					&& new Long(disCountArray[i]) > 0 && StringUtil.isNotBlank(locationNumArray[i])) {
				prodRelationIn.setAmount(new Long(disCountArray[i]));
				prodRelationIn.setLocId(new Long(locationNumArray[i]));
				// �����˻�����ʱ��������κ͹�Ӧ��ID,�������
				if (StringUtil.isNotBlank(type)
						&& (EnumInDepository.IN_SALES_CHANGE.getKey().equals(type) || EnumInDepository.IN_SALES_REFUND
								.getKey().equals(type))) {
					prodRelationIn.setSupplierId(new Long(supplierIdForUpdateArray[i]));
					prodRelationIn.setBatchNum(batchNumForUpdateArray[i]);
					prodRelationIn.setSelfCost(new Double(selfCostForUpdateArray[i]));
					prodRelationIn.setStorType(storTypeForUpdateArray[i]);
				}
				// �����
				prodRelationInDao.addProdRelationIn(prodRelationIn);
			}
		}
	}

	public List<InDetailGoods> listInDetailNotFinish(Long inDepId, String status) {
		log.info("InDetailManagerImpl.listInDetailNotFinish method");
		try {
			return inDetailDao.listInDetailNotFinish(inDepId, status);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public InDetailBaseInfo getCheckInDetailBaseInfo(Long inDetailId) {
		log.info("InDetailManagerImpl.getCheckInDetailBaseInfo method");
		try {
			return inDetailDao.getCheckInDetailBaseInfo(inDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public InDetailBaseInfo getShoppingOrSalesInDetailBaseInfo(Long inDetailId, String type) {
		log.info("InDetailManagerImpl.getShoppingOrSalesInDetailBaseInfo method");
		try {
			return inDetailDao.getShoppingOrSalesInDetailBaseInfo(inDetailId, type);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<GoodsForLocation> getSalesLocationForGoods(Long id) {
		log.info("InDetailManagerImpl.getSalesLocationForGoods method");
		try {
			InDetail ind = inDetailDao.getInDetail(id);
			if (ind == null) {
				return null;
			}
			InDepository inDepository = inDepositoryDao.getInDepository(ind.getInDepositoryId());
			if (inDepository == null) {
				return null;
			}
			String refundId = inDepository.getRelationNum();
			Refund re = refundDao.getRefundByRefundId(refundId);
			if (re != null) {
				if (StringUtil.isNotBlank(re.getRelRefundId())) {
					return inDetailDao.getSalesLocationForGoodsChange(id);
				}
			}
			return inDetailDao.getSalesLocationForGoods(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<GoodsForLocation> listInDetailForDisByDetailId(Long inDetailId) {
		log.info("InDetailManagerImpl.listInDetailForDisByDetailId method");
		try {
			return inDetailDao.listInDetailForDisByDetailId(inDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public int getGoodsLocationCountByInDetailId(Long inDetailId) {
		log.info("InDetailManagerImpl.getGoodsLocationCountByInDetailId method");
		try {
			return inDetailDao.getGoodsLocationCountByInDetailId(inDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public QueryPage gatherInDepositoryLists(GatherQuery gatherQuery, int currPage, int pageSize, boolean isPage){
		log.info("InDetailManagerImpl.gatherInDepositoryLists method");
		QueryPage queryPage = new QueryPage(gatherQuery);
        Map parMap = queryPage.getParameters();
        parMap.put("depfirstIds", gatherQuery.getDepfirstIds());
		try {
			return inDetailDao.gatherInDepositoryLists(parMap, currPage, pageSize, isPage);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hundsun.bible.facade.ios.InDetailManager#gatherInDepositoryListsCount(java.util.Map)
	 */
	public int gatherInDepositoryListsCount(Map<String, String> parMap) {
		log.info("InDetailManagerImpl.gatherInDepositoryListsCount method");
		try {
			return inDetailDao.gatherInDepositoryListsCount(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public GatherInDepository gatherFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)

	{
		log.info("InDetailManagerImpl.gatherFinanceInDepositoryCount method");
		try {
			return inDetailDao.gatherFinanceInDepositoryCount(financeDepositoryQuery);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	 public QueryPage gatherFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize) {
	 log.info("InDetailManagerImpl.gatherFinanceInDepositoryLists method");
	 try {
	 return inDetailDao.gatherFinanceInDepositoryLists(financeDepositoryQuery, currPage, pageSize);
	 } catch (Exception e) {
	 log.error(e.getMessage());
	 }
	 return null;
	 }

	public GatherInDepository estimateFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery) {
		log.info("InDetailManagerImpl.estimateFinanceInDepositoryCount method");
		try {
			return inDetailDao.estimateFinanceInDepositoryCount(financeDepositoryQuery);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	 public QueryPage estimateFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize) {
	 log.info("InDetailManagerImpl.estimateFinanceInDepositoryLists method");
	 try {
	 return inDetailDao.estimateFinanceInDepositoryLists(financeDepositoryQuery, currPage, pageSize);
	 } catch (Exception e) {
	 log.error(e.getMessage());
	 }
	 return null;
	 }

	public InDetailBaseInfo getInfoByInDetailIdForShoppingList(Long inDetailId) {
		log.info("InDetailManagerImpl.getInfoByInDetailIdForShoppingList method");
		try {
			return inDetailDao.getInfoByInDetailIdForShoppingList(inDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<GoodsForLocation> getLocationForDefect(Long id, Long locId) {
		log.info("InDetailManagerImpl.getLocationForDefect method");
		try {
			return inDetailDao.getLocationForDefect(id, locId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
