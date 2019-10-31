package com.huaixuan.network.biz.service.storage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationInDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGb;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;
import com.huaixuan.network.biz.domain.storage.ProdRelationInGb;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.InDepositoryManager;
import com.huaixuan.network.biz.service.storage.InDetailGbManager;
import com.huaixuan.network.biz.service.storage.ProdRelationInGbManager;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("inDepositoryManager")
public class InDepositoryManagerImpl implements InDepositoryManager {
	/* @model: */

	@Autowired
	public InDepositoryDao inDepositoryDao;
	@Autowired
	DepositoryFirstManager depositoryFirstManager;

	// public GoodsInstanceDao goodsInstanceDao;
	@Autowired
	public StorageDao storageDao;
	@Autowired
	public ProdRelationInDao prodRelationInDao;
	@Autowired
	public DepLocationDao depLocationDao;

	@Autowired
	public GoodsInstanceManager goodsInstanceManager;
	@Autowired
	public RefundDao refundDao;
	@Autowired
	public InDetailDao inDetailDao;
	@Autowired
	public GoodsDao goodsDao;
	// public DepositoryFirstManager depositoryFirstManager;
	@Autowired
	public DepositoryService depositoryService;
	
	@Autowired
	public ProdRelationInGbManager prodRelationInGbManager;
	
	@Autowired
	public InDetailGbManager inDetailGbManager;

	//
	/* @model: */
	// public long addInDepository(InDepository inDepositoryDao) {
	// log.info("InDepositoryManagerImpl.addInDepository method");
	// try {
	// return this.inDepositoryDao.addInDepository(inDepositoryDao);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }
	//
	 /* @model: */
	 public void editInDepository(InDepository inDepository) {
	 try {
	 this.inDepositoryDao.editInDepository(inDepository);
	 } catch (Exception e) {
	 }
	 }
	//
	/* @model: */
	// public void removeInDepository(Long inDepositoryId) {
	// log.info("InDepositoryManagerImpl.removeInDepository method");
	// try {
	// this.inDepositoryDao.removeInDepository(inDepositoryId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }

	/* @model: */
	public InDepository getInDepository(Long inDepositoryId) {
		return this.inDepositoryDao.getInDepository(inDepositoryId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hundsun.bible.facade.ios.InDepositoryManager#getInDepository(java.util.Map)
	 */
	public InDepository getInDepository(Map map) throws Exception {
		// log.info("InDepositoryManagerImpl.getInDepositorys method");
		try {
			List<InDepository> inDepList = inDepositoryDao.getInDepository(map);
			if (inDepList != null && inDepList.size() > 0) {
				return inDepList.get(0);
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	//
	/* @model: */
	// public List<InDepository> getInDepositorys() {
	// log.info("InDepositoryManagerImpl.getInDepositorys method");
	// try {
	// return this.inDepositoryDao.getInDepositorys();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }
	//
	// public int getInDepositoryListsCount(Map<String, String> parMap) {
	// log.info("InDepositoryManagerImpl.getInDepositoryListsCount method");
	// try {
	// return this.inDepositoryDao.getInDepositoryListsCount(parMap);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }

	public QueryPage getInDepositoryLists(InDepositoryQuery query, int currPage, int pageSize, boolean isPage) {
		return this.inDepositoryDao.getInDepositoryLists(query, currPage, pageSize, isPage);
	}

	//
	// public int updateInDepositoryStatusById(Long id, String status, Date gmtInDep) {
	// log.info("InDepositoryManagerImpl.updateInDepositoryStatusById method");
	// try {
	// return this.inDepositoryDao.updateInDepositoryStatusById(id, status, gmtInDep);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }

	@SuppressWarnings("unchecked")
	// �Գ����е�Exceptionʵ������ع�(ע����ֱ��@Transactional����ֻ��RuntimeException���лع�)
	@Transactional
	public Boolean addStorageOpt(Map<String, Object> map) throws Exception {
		// ����ID
		Long inDepId = (Long) map.get("inDepId");
		// ���ʱ�� ����ҳ��ָ����ֻ���ǵ���ɵ�ʱ�� zhangwy 2010/11/23
		// Date gmtInDep = (Date) map.get("gmtInDep");
		Date gmtInDep = new Date();
		// ��ⵥ������Ϣ
		List<InDetailGoods> InDetailGoodsList = (List<InDetailGoods>) map.get("inDetailGoodsLists");
		// ��ⵥ��Ϣ
		InDepository inDepositoryInfo = (InDepository) map.get("inDepositoryDispaly");

		if (inDepId == null || inDepId <= 0 || InDetailGoodsList == null || InDetailGoodsList.size() <= 0
				|| inDepositoryInfo == null) {
			return Boolean.FALSE;
		}

		// 1.������ⵥ���״̬Ϊ�����
		inDepositoryDao.updateInDepositoryStatusById(inDepId, EnumInStatus.IN_FINISHED.getKey(), gmtInDep);
		// δ���̵��е�δ��ɾ���Ŀ�λ
		List<DepLocation> depLocationEnabledList = depLocationDao.listUseabledLocInfo(inDepositoryInfo.getDepFirstId());
		// ��������
		if (StringUtil.isNotBlank(inDepositoryInfo.getType())
				&& inDepositoryInfo.getType().equals(EnumInDepository.IN_SHOPPING.getKey())) { // �ɹ��������
			for (InDetailGoods inDetailGoodsInfo : InDetailGoodsList) {

				// һ���ֿ������ж� zhangwy
				DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(inDepositoryInfo
						.getDepFirstId());
                //��Ʒ��ƱbillId
				Long billId = goodsDao.getBillId(inDetailGoodsInfo.getGoodsId());
				// ȡ�ÿ�λId������
				ProdRelationIn prodRelationInInfo = new ProdRelationIn();
				prodRelationInInfo.setInDepId(inDepId);
				prodRelationInInfo.setGoodsId(inDetailGoodsInfo.getGoodsId());
				prodRelationInInfo.setGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId());
				prodRelationInInfo.setInDetailId(inDetailGoodsInfo.getId());
				List<ProdRelationIn> prodRelationInList = prodRelationInDao
						.getLocIdAndAmountForStorage(prodRelationInInfo);
				if (prodRelationInList != null && prodRelationInList.size() > 0) {
					for (ProdRelationIn prodRelationInForStorage : prodRelationInList) {
						Storage storageInfo = new Storage();
						storageInfo.setGoodsId(inDetailGoodsInfo.getGoodsId());
						storageInfo.setGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId());
						storageInfo.setPrice(inDetailGoodsInfo.getUnitPrice());
						// ���Σ�ֱ�Ӵ����������ȡ��
						InDepository inDepositoryInfoTemp = this.getInDepository(inDepId);
						if (inDepositoryInfoTemp != null) {
							storageInfo.setBatchNum(inDepositoryInfoTemp.getBatchNum());
							storageInfo.setTid(inDepositoryInfoTemp.getTid());
							storageInfo.setIsWholesale(inDepositoryInfoTemp.getIsWholesale());
						}
						storageInfo.setGmtCreate(new Date());
						storageInfo.setGmtModify(new Date());
						storageInfo.setSupplierId(inDetailGoodsInfo.getSupplierId());

						if (depLocationEnabledList != null && depLocationEnabledList.size() > 0) {
							Boolean updateAllowedFlag = Boolean.FALSE;
							for (DepLocation depLocationInfoTemp : depLocationEnabledList) {
								if (depLocationInfoTemp != null) {
									if (depLocationInfoTemp.getId().equals(prodRelationInForStorage.getLocId())) {
										updateAllowedFlag = Boolean.TRUE;
										break;
									}
								}
							}
							if (!updateAllowedFlag) {
								throw new Exception("�޿��ÿ�λ");
							}
						} else {
							throw new Exception("�޿��ÿ�λ");
						}
						storageInfo.setLocId(prodRelationInForStorage.getLocId());
						storageInfo.setStorageNum(prodRelationInForStorage.getAmount());
						storageInfo.setDepFirstId(prodRelationInForStorage.getDepFirstId());
						storageInfo.setStorType(prodRelationInForStorage.getStorType());
						// 3.�޸Ŀ�������
						storageDao.addStorage(storageInfo);

						// ������Ʒʵ�������Ʒ��Ŀ������,��������������1.һ���ֿ����������;2.�ֿ������Ǵ�Ʒ���; ���ӿ��ÿ��
						if (depositoryFirst != null && (!depositoryFirst.getType().equals("w"))) {
							DepLocation depLocation = depLocationDao.getLocationsById(prodRelationInForStorage
									.getLocId());
							Depository depository = depositoryService.getDepository(depLocation.getDepId());
							if (depository != null
									&& (!EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType()))) {
								goodsInstanceManager.updateAmountForTwo(prodRelationInForStorage.getGoodsInstanceId(),
										prodRelationInForStorage.getGoodsId(), prodRelationInForStorage.getAmount(),
										prodRelationInForStorage.getDepFirstId(), true);
							}
						}
						
						//��Ʊ��˾���������� zhangwy
						if(billId!=null){
							ProdRelationInGb prodRelationInGb = changeProdInToGb(prodRelationInForStorage,billId);
							prodRelationInGbManager.addProdRelationInGb(prodRelationInGb);
						}
					}
				}
				// ����ȫ��һ���ֿ��ʣ����
				long leftDepNum = this.storageDao.sumStorageByGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId(),
						inDetailGoodsInfo.getDepFirstId());
				inDetailGoodsInfo.setLeftDepNum(leftDepNum);
				// ����ʣ���� zhangwy
				long leftNum = this.storageDao
						.sumStorageByGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId(), null);
				inDetailGoodsInfo.setLeftNum(leftNum);
				inDetailDao.editInDetailGoodsInfo(inDetailGoodsInfo);
				//��Ʊ��˾��ⵥ�������� zhangwy
				if(billId!=null){
					InDetailGb inDetailGb = changeInDetailToGb(inDetailGoodsInfo,billId);
					inDetailGbManager.addInDetailGb(inDetailGb);
				}
			}
		} else if (StringUtil.isNotBlank(inDepositoryInfo.getType())
				&& inDepositoryInfo.getType().equals(EnumInDepository.IN_CHECK_MORE.getKey())) { // �̵���������
			for (InDetailGoods inDetailGoodsInfo : InDetailGoodsList) {
                //��Ʒ��ƱbillId
				Long billId = goodsDao.getBillId(inDetailGoodsInfo.getGoodsId());
				if (inDetailGoodsInfo.getStorId() != null) {
					Storage storage = this.storageDao.getStorage(inDetailGoodsInfo.getStorId());
					if (storage != null) {
						DepLocation depLocation = depLocationDao.getLocationsById(storage.getLocId());
						// �����һ���ֿ����͵����ƣ�����һ���ֿⲻ�ӿ�� zhangwy
						DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(storage
								.getDepFirstId());
						if (depLocation != null && (!depositoryFirst.getType().equals("w"))) {
							Depository depository = depositoryService.getDepository(depLocation.getDepId());
							if (!EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType())) {
								// 2.������Ʒʵ�������Ʒ��Ŀ������(�̵��������)
								goodsInstanceManager.updateAmountForTwo(inDetailGoodsInfo.getGoodsInstanceId(),
										inDetailGoodsInfo.getGoodsId(), inDetailGoodsInfo.getAmount(),
										inDepositoryInfo.getDepFirstId(), true);
							}
						}
					}
				}
				Storage storageInfo = new Storage();
				storageInfo.setGoodsId(inDetailGoodsInfo.getGoodsId());
				storageInfo.setGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId());
				storageInfo.setPrice(inDetailGoodsInfo.getUnitPrice());
				// ȡ�����κ͹�Ӧ��ID
				InDetailBaseInfo inDetailBaseInfoTemp = inDetailDao.getCheckInDetailBaseInfo(inDetailGoodsInfo.getId());
				if (inDetailBaseInfoTemp != null) {
					storageInfo.setSupplierId(inDetailBaseInfoTemp.getSupplierId());
					storageInfo.setBatchNum(inDetailBaseInfoTemp.getBatchNum());
				}

				storageInfo.setGmtCreate(new Date());
				storageInfo.setGmtModify(new Date());

				// ȡ�ÿ�λId������
				ProdRelationIn prodRelationInInfo = new ProdRelationIn();
				prodRelationInInfo.setInDepId(inDepId);
				prodRelationInInfo.setGoodsId(inDetailGoodsInfo.getGoodsId());
				prodRelationInInfo.setGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId());
				prodRelationInInfo.setInDetailId(inDetailGoodsInfo.getId());
				List<ProdRelationIn> prodRelationInList = prodRelationInDao
						.getLocIdAndAmountForStorage(prodRelationInInfo);
				if (prodRelationInList != null && prodRelationInList.size() > 0) {
					for (ProdRelationIn prodRelationInForStorage : prodRelationInList) {
						if (depLocationEnabledList != null && depLocationEnabledList.size() > 0) {
							Boolean updateAllowedFlag = Boolean.FALSE;
							for (DepLocation depLocationInfoTemp : depLocationEnabledList) {
								if (depLocationInfoTemp != null) {
									if (depLocationInfoTemp.getId().equals(prodRelationInForStorage.getLocId())) {
										updateAllowedFlag = Boolean.TRUE;
										break;
									}
								}
							}
							if (!updateAllowedFlag) {
								throw new Exception("�޿��ÿ�λ");
							}
						} else {
							throw new Exception("�޿��ÿ�λ");
						}
						storageInfo.setLocId(prodRelationInForStorage.getLocId());
						storageInfo.setStorageNum(prodRelationInForStorage.getAmount());
						storageInfo.setDepFirstId(prodRelationInForStorage.getDepFirstId());
						storageInfo.setStorType(prodRelationInForStorage.getStorType());
						storageInfo.setTid(prodRelationInForStorage.getTid());
						storageInfo.setIsWholesale(prodRelationInForStorage.getIsWholesale());
						// 3.�޸Ŀ�������
						if (storageDao.editStorageExistNum(storageInfo) <= 0) {
							// �������ݵ�����
							storageDao.addStorage(storageInfo);
						}
						
						//��Ʊ��˾���������� zhangwy
						if(billId!=null){
							ProdRelationInGb prodRelationInGb = changeProdInToGb(prodRelationInForStorage,billId);
							prodRelationInGbManager.addProdRelationInGb(prodRelationInGb);
						}
					}
				}
				// ����ȫ��һ���ֿ��ʣ����
				long leftDepNum = this.storageDao.sumStorageByGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId(),
						inDetailGoodsInfo.getDepFirstId());
				inDetailGoodsInfo.setLeftDepNum(leftDepNum);
				// ����ʣ���� zhangwy
				long leftNum = this.storageDao
						.sumStorageByGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId(), null);
				inDetailGoodsInfo.setLeftNum(leftNum);
				inDetailDao.editInDetailGoodsInfo(inDetailGoodsInfo);
				//��Ʊ��˾��ⵥ�������� zhangwy
				if(billId!=null){
					InDetailGb inDetailGb = changeInDetailToGb(inDetailGoodsInfo,billId);
					inDetailGbManager.addInDetailGb(inDetailGb);
				}
			}
		} else if (StringUtil.isNotBlank(inDepositoryInfo.getType())
				&& (inDepositoryInfo.getType().equals(EnumInDepository.IN_SALES_CHANGE.getKey()) || inDepositoryInfo
						.getType().equals(EnumInDepository.IN_SALES_REFUND.getKey()))) { // �����˻��������
			// �����˻����״̬
			Map refundMap = new HashMap();
			refundMap.put("status", EnumRefundStatus.Goods_In_Depository.getKey());
			refundMap.put("refundId", inDepositoryInfo.getRelationNum());
			refundMap.put("isGoodsRecevied", "1");
			refundDao.updateRefundStatusByRefId(refundMap);
			for (InDetailGoods inDetailGoodsInfo : InDetailGoodsList) {
				// һ���ֿ������ж� zhangwy
				DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(inDepositoryInfo
						.getDepFirstId());

                //��Ʒ��ƱbillId
				Long billId = goodsDao.getBillId(inDetailGoodsInfo.getGoodsId());
				// ������Ʒ����������
				goodsDao.updateSaleNumberById(inDetailGoodsInfo.getGoodsId(), 0 - inDetailGoodsInfo.getAmount());

				// ȡ�ÿ�λId������
				ProdRelationIn prodRelationInInfo = new ProdRelationIn();
				prodRelationInInfo.setInDepId(inDepId);
				prodRelationInInfo.setGoodsId(inDetailGoodsInfo.getGoodsId());
				prodRelationInInfo.setGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId());
				prodRelationInInfo.setInDetailId(inDetailGoodsInfo.getId());
				List<ProdRelationIn> prodRelationInList = prodRelationInDao
						.getLocIdAndAmountForStorage(prodRelationInInfo);

				if (prodRelationInList != null && prodRelationInList.size() > 0) {
					for (ProdRelationIn prodRelationInForStorage : prodRelationInList) {
						Storage storageInfo = new Storage();
						storageInfo.setGoodsId(inDetailGoodsInfo.getGoodsId());
						storageInfo.setGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId());

						storageInfo.setGmtCreate(new Date());
						storageInfo.setGmtModify(new Date());

						if (depLocationEnabledList != null && depLocationEnabledList.size() > 0) {
							Boolean updateAllowedFlag = Boolean.FALSE;
							for (DepLocation depLocationInfoTemp : depLocationEnabledList) {
								if (depLocationInfoTemp != null) {
									if (depLocationInfoTemp.getId().equals(prodRelationInForStorage.getLocId())) {
										updateAllowedFlag = Boolean.TRUE;
										break;
									}
								}
							}
							if (!updateAllowedFlag) {
								throw new Exception("�޿��ÿ�λ");
							}
						} else {
							throw new Exception("�޿��ÿ�λ");
						}
						storageInfo.setLocId(prodRelationInForStorage.getLocId());
						storageInfo.setStorageNum(prodRelationInForStorage.getAmount());
						storageInfo.setBatchNum(prodRelationInForStorage.getBatchNum());
						storageInfo.setSupplierId(prodRelationInForStorage.getSupplierId());
						storageInfo.setPrice(prodRelationInForStorage.getSelfCost());
						storageInfo.setDepFirstId(prodRelationInForStorage.getDepFirstId());
						storageInfo.setStorType(prodRelationInForStorage.getStorType());
						storageInfo.setTid(prodRelationInForStorage.getTid());
						storageInfo.setIsWholesale(prodRelationInForStorage.getIsWholesale());
						// 3.�޸Ŀ�������
						if (storageDao.editStorageExistNum(storageInfo) <= 0) {
							// �������ݵ�����
							storageDao.addStorage(storageInfo);
						}

						// ������Ʒʵ�������Ʒ��Ŀ������,��������������1.һ���ֿ����������;2.�ֿ������Ǵ�Ʒ���; ���ӿ��ÿ��
						if (depositoryFirst != null && (!depositoryFirst.getType().equals("w"))) {
							DepLocation depLocation = depLocationDao.getLocationsById(prodRelationInForStorage
									.getLocId());
							Depository depository = depositoryService.getDepository(depLocation.getDepId());
							if (depository != null
									&& (!EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType()))) {
								goodsInstanceManager.updateAmountForTwo(prodRelationInForStorage.getGoodsInstanceId(),
										prodRelationInForStorage.getGoodsId(), prodRelationInForStorage.getAmount(),
										prodRelationInForStorage.getDepFirstId(), true);
							}
						}
						
						//��Ʊ��˾���������� zhangwy
						if(billId!=null){
							ProdRelationInGb prodRelationInGb = changeProdInToGb(prodRelationInForStorage,billId);
							prodRelationInGbManager.addProdRelationInGb(prodRelationInGb);
						}
					}
				}
				// ����ȫ��һ���ֿ��ʣ����
				long leftDepNum = this.storageDao.sumStorageByGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId(),
						inDetailGoodsInfo.getDepFirstId());
				inDetailGoodsInfo.setLeftDepNum(leftDepNum);
				// ����ʣ���� zhangwy
				long leftNum = this.storageDao
						.sumStorageByGoodsInstanceId(inDetailGoodsInfo.getGoodsInstanceId(), null);
				inDetailGoodsInfo.setLeftNum(leftNum);
				inDetailDao.editInDetailGoodsInfo(inDetailGoodsInfo);
				//��Ʊ��˾��ⵥ�������� zhangwy
				if(billId!=null){
					InDetailGb inDetailGb = changeInDetailToGb(inDetailGoodsInfo,billId);
					inDetailGbManager.addInDetailGb(inDetailGb);
				}
			}
		}
		return Boolean.TRUE;
	}

	public int getUnFinishedInDepByRelNum(String relationNum) {
		return this.inDepositoryDao.getUnFinishedInDepByRelNum(relationNum);
	}

	// public int getFinishedInDepByRelNum(String relationNum) {
	// log.info("InDepositoryManagerImpl.getFinishedInDepByRelNum method");
	// try {
	// return this.inDepositoryDao.getFinishedInDepByRelNum(relationNum);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }

	public String getInDepositoryStatusByDetailId(Long inDetailId) {
		return this.inDepositoryDao.getInDepositoryStatusByDetailId(inDetailId);
	}

	public List<InDepository> getInDepositorysWithDetail(Map parmap) {
		List<InDepository> indetailList = inDepositoryDao.getInDepositorysWithDetail(parmap);
		for (InDepository tmp : indetailList) {
			// ����һ���ֿ�����
			if (tmp.getDepFirstId() != null) {
				DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(tmp.getDepFirstId());
				if (depositoryFirst != null) {
					tmp.setDepFirstName(depositoryFirst.getDepFirstName());
				}
			}
		}
		return indetailList;
	}

	// public int getInDepositorysWithDetailCount(Map parmap) {
	// return this.inDepositoryDao.getInDepositorysWithDetailCount(parmap);
	// }
	//
	// public List<String> getAlltypes() {
	// return this.inDepositoryDao.getAlltypes();
	// }

	public Map getSupplierNameById(List<String> inDepositoryIds) {
		return this.inDepositoryDao.getSupplierNameById(inDepositoryIds);
	}
	
	
	public ProdRelationInGb changeProdInToGb(ProdRelationIn prodRelationIn, Long billId){
		ProdRelationInGb prodRelationInGb = new ProdRelationInGb();
		prodRelationInGb.setRelationId(prodRelationIn.getId());
		prodRelationInGb.setInDepId(prodRelationIn.getInDepId());
		prodRelationInGb.setGoodsInstanceId(prodRelationIn.getGoodsInstanceId());
		prodRelationInGb.setAmount(prodRelationIn.getAmount());
		prodRelationInGb.setGoodsId(prodRelationIn.getGoodsId());
		prodRelationInGb.setInDetailId(prodRelationIn.getInDetailId());
		prodRelationInGb.setLocId(prodRelationIn.getLocId());
		prodRelationInGb.setSupplierId(prodRelationIn.getSupplierId());
		prodRelationInGb.setBatchNum(prodRelationIn.getBatchNum());
		prodRelationInGb.setSelfCost(prodRelationIn.getSelfCost());
		prodRelationInGb.setDepFirstId(prodRelationIn.getDepFirstId());
		prodRelationInGb.setStorType(prodRelationIn.getStorType());
		prodRelationInGb.setIsWholesale(prodRelationIn.getIsWholesale());
		prodRelationInGb.setTid(prodRelationIn.getTid());
		prodRelationInGb.setBillId(billId);
		return prodRelationInGb;
	}
	
	public InDetailGb changeInDetailToGb(InDetailGoods inDetailGoods, Long billId) throws Exception{
		InDetail indetail = inDetailDao.getInDetail(inDetailGoods.getId());
		InDetailGb inDetailGb = new InDetailGb();
		inDetailGb.setRelationId(indetail.getId());
		inDetailGb.setGoodsInstanceId(indetail.getGoodsInstanceId());
		inDetailGb.setGoodsId(indetail.getGoodsId());
		inDetailGb.setInDepositoryId(indetail.getInDepositoryId());
		inDetailGb.setAmount(indetail.getAmount());
		inDetailGb.setUnitPrice(indetail.getUnitPrice());
		inDetailGb.setDueFee(indetail.getDueFee());
		inDetailGb.setFactFee(indetail.getFactFee());
		inDetailGb.setStatus(indetail.getStatus());
		inDetailGb.setStorId(indetail.getStorId());
		inDetailGb.setLeftNum(indetail.getLeftNum());
		inDetailGb.setStorType(indetail.getStorType());
		inDetailGb.setDepFirstId(indetail.getDepFirstId());
		inDetailGb.setLeftDepNum(indetail.getLeftDepNum());
		inDetailGb.setBillId(billId);
		return inDetailGb;
	}

}
