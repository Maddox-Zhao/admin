package com.huaixuan.network.biz.service.storage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.stock.ShoppingRefundDao;
import com.huaixuan.network.biz.dao.stock.ShoppingRefundDetailDao;
import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.dao.storage.StorageRefundApplyDao;
import com.huaixuan.network.biz.dao.supplier.SupplierDao;
import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumShoppingRefund;
import com.huaixuan.network.biz.enums.EnumShoppingRefundStatus;
import com.huaixuan.network.biz.enums.EnumStorageRefundApply;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Service("storageManager")
public class StorageManagerImpl implements StorageManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	StorageDao storageDao;
	@Autowired
	DepositoryDao depositoryDao;
	@Autowired
	DepLocationDao depLocationDao;
	@Autowired
	DepLocationManager depLocationManager;
	@Autowired
	CategoryManager categoryManager;
	@Autowired
	DepositoryFirstDao depositoryFirstDao;
	@Autowired
	SupplierDao supplierDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	CodeManager codeManager;
	@Autowired
	GoodsInstanceDao goodsInstanceDao;
	@Autowired
	AvailableStockDao availableStockDao;
	@Autowired
	ShoppingRefundDao shoppingRefundDao;
	@Autowired
	ShoppingRefundDetailDao shoppingRefundDetailDao;
	@Autowired
	OutDepositoryDao outDepositoryDao;
	@Autowired
	OutDetailDao outDetailDao;
	@Autowired
	ProdRelationOutDao prodRelationOutDao;
	@Autowired
	StorageRefundApplyDao storageRefundApplyDao;

	// private CategoryManager categoryManager;
	//
	// private ShoppingRefundDao shoppingRefundDao;
	// private ShoppingRefundDetailDao shoppingRefundDetailDao;
	// private OutDepositoryDao outDepositoryDao;
	// private OutDetailDao outDetailDao;
	// private ProdRelationOutDao prodRelationOutDao;
	// private GoodsInstanceManager goodsInstanceManager;
	// private SupplierDao supplierDao;
	@Autowired
	DepositoryFirstManager depositoryFirstManager;
	// private OrderManager orderManager;
	// private AvailableStockDao availableStockDao;
	// private GoodsInstanceDao goodsInstanceDao;
	// private StorageRefundApplyDao storageRefundApplyDao;
	//
	/* @model: */
	// public void addStorage(Storage storageDao) {
	// log.info("StorageManagerImpl.addStorage method");
	// try {
	// this.storageDao.addStorage(storageDao);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public void editStorage(Storage storage) {
	// log.info("StorageManagerImpl.editStorage method");
	// try {
	// this.storageDao.editStorage(storage);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public void removeStorage(Long storageId) {
	// log.info("StorageManagerImpl.removeStorage method");
	// try {
	// this.storageDao.removeStorage(storageId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	 public List<Storage> getStorageByIds(Map<String, Object> condition) {
	 return this.storageDao.getStorageByIds(condition);
	 }

	public Storage getStorage(Long storageId) {
		log.info("StorageManagerImpl.getStorage method");
		try {
			return this.storageDao.getStorage(storageId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/* @model: */
	// public List<Storage> getStorages() {
	// log.info("StorageManagerImpl.getStorages method");
	// try {
	// return this.storageDao.getStorages();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	@SuppressWarnings("unchecked")
	public QueryPage getStoragesByCondition(StorageQuery storageQuery,
			int currentPage, int pageSize, boolean isGroup, boolean isPage) {
		log.info("StorageManagerImpl.getStoragesByCondition method");
		QueryPage queryPage =null;
		try {
			if (isGroup) {
				queryPage = storageDao.getStoragesByCondition(storageQuery,
						currentPage, pageSize, isPage);// 分组查询库存集合
			} else {
				queryPage = storageDao.getStorageListByMap(storageQuery,
						currentPage, pageSize, isPage);
			}
			List<Storage> storagelist = (List<Storage>) queryPage.getItems();
			if (storagelist != null) {
				for (Storage tmp : storagelist) {
					// 设置一级仓库名称
					if (tmp.getDepFirstId() != null) {
						DepositoryFirst depositoryFirst = depositoryFirstDao
								.getDepositoryById(tmp.getDepFirstId());
						if (depositoryFirst != null) {
							tmp.setDepFirstName(depositoryFirst
									.getDepFirstName());
						}
					}
					// 设置仓库名称和库位名称
					if (tmp.getLocId() != null) {
						DepLocation depLocation = depLocationDao
								.getLocationsById(tmp.getLocId());
						Depository depository = depLocationManager
								.getDepositoryByLocationId(tmp.getLocId());
						if (depLocation != null) {
							tmp.setDepositoryName(depLocation.getDepName());
							tmp.setDepLocationName(depLocation.getLocName());
						}
						if (depository != null) {
							tmp.setDepType(depository.getType());
						}
					}
					// 设置供应商名称
					if (tmp.getSupplierId() != null) {
						Supplier supplier = supplierDao.selectSupplierById(tmp
								.getSupplierId());
						if (supplier != null) {
							tmp.setSupplierName(supplier.getName());
						}
					}
				}
			}

			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// public List<Storage> getStoragesWithMove(Map<String, String> condition,
	// Page page) {
	// log.info("StorageManagerImpl.getStoragesWithMove method");
	// try {
	// List<Storage> storagelist = storageDao.getStoragesWithMove(condition,
	// page);
	// for (Storage tmp : storagelist) {
	// //设置一级仓库名称
	// if (tmp.getDepFirstId() != null) {
	// DepositoryFirst depositoryFirst =
	// depositoryFirstManager.getDepositoryById(tmp
	// .getDepFirstId());
	// if (depositoryFirst != null) {
	// tmp.setDepFirstName(depositoryFirst.getDepFirstName());
	// tmp.setDepfirstType(depositoryFirst.getType());
	// }
	// }
	// //设置仓库名称和库位名称
	// if (tmp.getLocId() != null) {
	// DepLocation depLocation = depLocationDao.getLocationsById(tmp
	// .getLocId());
	// Depository depository = depLocationManager.getDepositoryByLocationId(tmp
	// .getLocId());
	// if (depLocation != null) {
	// tmp.setDepositoryName(depLocation.getDepName());
	// tmp.setDepLocationName(depLocation.getLocName());
	// }
	// if (depository != null) {
	// tmp.setDepType(depository.getType());
	// }
	// }
	// //设置供应商名称
	// if (tmp.getSupplierId() != null) {
	// Supplier supplier = supplierDao.selectSupplierById(tmp.getSupplierId());
	// if (supplier != null) {
	// tmp.setSupplierName(supplier.getName());
	// }
	// }
	// }
	// return storagelist;
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }


	@SuppressWarnings("unchecked")
	public int getStoragesCountWithMove(MoveStorageQuery moveStorageQuery) {
		QueryPage queryPage = new QueryPage(moveStorageQuery);
		Map condition = queryPage.getParameters();
		return this.storageDao.getStoragesCountWithMove(condition);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getStoragesWithMove(MoveStorageQuery moveStorageQuery,
			int currentPage, int pageSize) {
		log.info("StorageManagerImpl.getStoragesWithMove method");
		try {
			QueryPage queryPage = storageDao.getStoragesWithMove(moveStorageQuery, currentPage, pageSize);
			List<Storage> storagelist = (List<Storage>) queryPage.getItems();
			for (Storage tmp : storagelist) {
				// 设置一级仓库名称
				if (tmp.getDepFirstId() != null) {
					DepositoryFirst depositoryFirst = depositoryFirstDao
							.getDepositoryById(tmp.getDepFirstId());
					if (depositoryFirst != null) {
						tmp.setDepFirstName(depositoryFirst.getDepFirstName());
						tmp.setDepfirstType(depositoryFirst.getType());
					}
				}
				// 设置仓库名称和库位名称
				if (tmp.getLocId() != null) {
					DepLocation depLocation = depLocationDao
							.getLocationsById(tmp.getLocId());
					Depository depository = depLocationManager
							.getDepositoryByLocationId(tmp.getLocId());
					if (depLocation != null) {
						tmp.setDepositoryName(depLocation.getDepName());
						tmp.setDepLocationName(depLocation.getLocName());
					}
					if (depository != null) {
						tmp.setDepType(depository.getType());
					}
				}
				// 设置供应商名称
				if (tmp.getSupplierId() != null) {
					Supplier supplier = supplierDao.selectSupplierById(tmp
							.getSupplierId());
					if (supplier != null) {
						tmp.setSupplierName(supplier.getName());
					}
				}
			}
			queryPage.setItems(storagelist);
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// public int editStorageExistNum(Storage storage) {
	// log.info("StorageManagerImpl.editStorageExistNum method");
	// try {
	// return this.storageDao.editStorageExistNum(storage);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }
	//
	// public int sumStorageByParameterMap(Map parameterMap) {
	// try {
	// return this.storageDao.sumStorageByParameterMap(parameterMap);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }
	//
	 public Storage sumStorageResultForCheck(Map parameterMap) {
	 try {
	 return this.storageDao.sumStorageResultForCheck(parameterMap);
	 } catch (Exception e) {
	 log.error(e.getMessage());
	 }
	 return null;
	 }

	/*
	 * （非 Javadoc）
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.StorageManager#updateStorageStoNumByMap(
	 * java.util.Map)
	 */
	@Override
	public int updateStorageStoNumByMap(Map parameterMap) {
		log.info("StorageManagerImpl.updateStorageStoNumByMap method");
		try {
			return this.storageDao.updateStorageStoNumByMap(parameterMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.StorageManager#getStorageByMap(java.util
	 * .Map)
	 */
	@Override
	public Storage getStorageByMap(Map parameterMap) {
		log.info("StorageManagerImpl.getStorageByMap method");
		try {
			return this.storageDao.getStorageByMap(parameterMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<Storage> getStorageListsByMap(Map parameterMap) {
		log.info("StorageManagerImpl.getStorageListsByMap method");
		return this.storageDao.getStorageListsByMap(parameterMap);
	}


	 public List<Long> getGoodsInstanceIdsByLocId(Long locId) {
	 log.info("StorageManagerImpl.getGoodsInstanceIdsByLocId method");
	 try {
	 return this.storageDao.getGoodsInstanceIdsByLocId(locId);
	 } catch (Exception e) {
	 log.error(e.getMessage());
	 }
	 return null;
	 }

	public List<OutDepositoryStorage> listOutDetailForDisByDetailId(
			Long outDetailId) {
		return this.storageDao.listOutDetailForDisByDetailId(outDetailId);
	}

	public List<Storage> sumStorageCostByDepid(Long depId) {
		log.info("StorageManagerImpl.sumStorageCostByDepid method");
		try {
			if (depId == null) {
				return null;
			}
			Depository d = depositoryDao.getDepository(depId);
			List<Storage> storages = this.storageDao
					.sumStorageCostByDepid(depId);
			for (Storage s : storages) {
				if (d != null) {
					s.setDepositoryName(d.getName());
				}
				s.setDepId(depId);
				s.setCatName(categoryManager.getCatFullNameByCatcodeSimple(
						s.getCatCode(), ">"));
			}
			return storages;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	//
	// public List<Storage> getDataForStorageOnce() {
	// log.info("StorageManagerImpl.getDataForStorageOnce method");
	// try {
	// return storageDao.getDataForStorageOnce();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }
	//
	// public Storage getStorageByGoodsInstanceId(Long goodsInstanceId) {
	// log.info("StorageManagerImpl.getStorageByGoodsInstanceId method");
	// try {
	// return storageDao.getStorageByGoodsInstanceId(goodsInstanceId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	@SuppressWarnings("unchecked")
	public QueryPage searchZeroStock(StorageQuery storageQuery,
			int currentPage, int pageSize, boolean isPage) {
		log.info("StorageManagerImpl.searchZeroStock method");
		QueryPage queryPage = new QueryPage(storageQuery);
		Map condition = queryPage.getParameters();
		try {
			queryPage = storageDao.searchZeroStock(condition, currentPage,
					pageSize, isPage);
			List<Storage> zeroList = (List<Storage>) queryPage.getItems();
			if (zeroList != null) {
				for (Storage temp : zeroList) {
					Order order = orderDao.getSalesSumByGoodsInstanceId(temp
							.getGoodsInstanceId());
					temp.setSalesSum(order.getSalesSum());
				}
			}
			queryPage.setItems(zeroList);
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// /* (non-Javadoc)
	// * @see
	// com.hundsun.bible.facade.ios.StorageManager#getZeroStorageByParameterMap(java.util.Map)
	// */
	public List<Storage> getZeroStorageByParameterMap(Map<String, String> parMap) {
		log.info("StorageManagerImpl.getZeroStorageByParameterMap method");
		try {
			return storageDao.getZeroStorageByParameterMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Storage> getStorageWithTrade(Long goodsInstanceId,
			boolean showAllStorageNum) {
		log.info("StorageManagerImpl.getStorageWithTrade method");
		List<Storage> storages = storageDao.getStorageWithTrade(
				goodsInstanceId, showAllStorageNum);
		for (Storage s : storages) {
			Depository d = depositoryDao.getDepository(s.getDepId());
			if (d != null) {
				s.setDepositoryName(d.getName());
			}
		}
		return storages;
	}

	public QueryPage getStoragesWithAll(Storage storage, int currPage, int pageSize) {
		log.info("StorageManagerImpl.getStoragesWithAll method");
		try {
			 QueryPage queryPage = storageDao.getStoragesWithAll(
					storage, currPage, pageSize);
			 List<Storage> storagelist = (List<Storage>) queryPage.getItems();
			for (Storage tmp : storagelist) {
				// 设置一级仓库名称
				if (tmp.getDepFirstId() != null) {
					DepositoryFirst depositoryFirst = depositoryFirstManager
							.getDepositoryById(tmp.getDepFirstId());
					if (depositoryFirst != null) {
						tmp.setDepFirstName(depositoryFirst.getDepFirstName());
						tmp.setDepfirstType(depositoryFirst.getType());
					}
				}
				// 设置仓库名称和库位名称
				if (tmp.getLocId() != null) {
					DepLocation depLocation = depLocationDao
							.getLocationsById(tmp.getLocId());
					Depository depository = depLocationManager
							.getDepositoryByLocationId(tmp.getLocId());
					if (depLocation != null) {
						tmp.setDepositoryName(depLocation.getDepName());
						tmp.setDepLocationName(depLocation.getLocName());
					}
					if (depository != null) {
						tmp.setDepType(depository.getType());
					}
				}
				// 设置供应商名称
				if (tmp.getSupplierId() != null) {
					Supplier supplier = supplierDao.selectSupplierById(tmp
							.getSupplierId());
					if (supplier != null) {
						tmp.setSupplierName(supplier.getName());
					}
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public QueryPage getStoragesWithMoveAllTwo(Storage storage, int currPage, int pageSize) {
		log.info("StorageManagerImpl.getStoragesWithMoveAllTwo method");
		try {
			QueryPage queryPage = storageDao.getStoragesWithAll(storage, currPage, pageSize);
			List<Storage> storagelist = (List<Storage>) queryPage.getItems();
			for (Storage tmp : storagelist) {
				// 设置一级仓库名称
				if (tmp.getDepFirstId() != null) {
					DepositoryFirst depositoryFirst = depositoryFirstManager
							.getDepositoryById(tmp.getDepFirstId());
					if (depositoryFirst != null) {
						tmp.setDepFirstName(depositoryFirst.getDepFirstName());
						tmp.setDepfirstType(depositoryFirst.getType());
					}
				}
				// 设置仓库名称和库位名称
				if (tmp.getLocId() != null) {
					DepLocation depLocation = depLocationDao
							.getLocationsById(tmp.getLocId());
					Depository depository = depLocationManager
							.getDepositoryByLocationId(tmp.getLocId());
					if (depLocation != null) {
						tmp.setDepositoryName(depLocation.getDepName());
						tmp.setDepLocationName(depLocation.getLocName());
					}
					if (depository != null) {
						tmp.setDepType(depository.getType());
					}
				}
				// 设置供应商名称
				if (tmp.getSupplierId() != null) {
					Supplier supplier = supplierDao.selectSupplierById(tmp
							.getSupplierId());
					if (supplier != null) {
						tmp.setSupplierName(supplier.getName());
					}
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

//	public List<Storage> getRefundStoragesByMap(Map<String, String> parMap,
//			Page page) {
//		log.info("StorageManagerImpl.getRefundStoragesByMap method");
//		try {
//			return storageDao.getRefundStoragesByMap(parMap, page);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//		return null;
//	}
	//
	// /* (non-Javadoc)
	// * @see
	// com.hundsun.bible.facade.ios.StorageManager#getRefundStoragesCountByMap(java.util.Map)
	// */
	// public int getRefundStoragesCountByMap(Map<String, String> parMap) {
	// log.info("StorageManagerImpl.getRefundStoragesCountByMap method");
	// try {
	// return storageDao.getRefundStoragesCountByMap(parMap);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }
	//
	// public List<Storage> getStockAgeByCondition(Map<String, String>
	// condition, Page page) {
	// log.info("StorageManagerImpl.getStockAgeByCondition method");
	// try {
	// if (condition.get("orderBy") != null &&
	// condition.get("orderBy").equals("goodsCodeAsc")) {
	// condition.put("orderBy", "order by gi.code asc");
	// } else if (condition.get("orderBy") != null
	// && condition.get("orderBy").equals("goodsCodeDesc")) {
	// condition.put("orderBy", "order by gi.code desc");
	// } else if (condition.get("orderBy") != null
	// && condition.get("orderBy").equals("supplierIdAsc")) {
	// condition.put("orderBy", "order by st.supplier_id asc");
	// } else if (condition.get("orderBy") != null
	// && condition.get("orderBy").equals("supplierIdDesc")) {
	// condition.put("orderBy", "order by st.supplier_id desc");
	// } else if (condition.get("orderBy") != null
	// && condition.get("orderBy").equals("storageNumSumAsc")) {
	// condition.put("orderBy", "order by sum(st.storage_num) asc");
	// } else if (condition.get("orderBy") != null
	// && condition.get("orderBy").equals("storageNumSumDesc")) {
	// condition.put("orderBy", "order by sum(st.storage_num) desc");
	// }
	// if (StringUtil.isNotBlank(condition.get("stockAge"))) {
	// if (condition.get("stockAge").equals("fifteen")) {
	// condition.put("ageDateStart", DateUtil.getDiffDate(new Date(), -15));
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// } else if (condition.get("stockAge").equals("twenty")) {
	// condition.put("ageDateStart", DateUtil.getDiffDate(new Date(), -20));
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// } else if (condition.get("stockAge").equals("onemonth")) {
	// condition.put("ageDateStart", DateUtil.getDiffDate(new Date(), -30));
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// } else if (condition.get("stockAge").equals("twomonth")) {
	// condition.put("ageDateStart", DateUtil.getDiffMon(new Date(), -2));
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// } else if (condition.get("stockAge").equals("threemonth")) {
	// condition.put("ageDateStart", DateUtil.getDiffMon(new Date(), -3));
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// } else if (condition.get("stockAge").equals("halfyear")) {
	// condition.put("ageDateStart", DateUtil.getDiffMon(new Date(), -6));
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// } else if (condition.get("stockAge").equals("allday")) {
	// condition.put("ageDateStart", DateUtil.getDiffMon(new Date(), -120));
	// //10年前
	// condition.put("ageDateEnd", DateUtil.getDateToString(new Date()));
	// }
	// }
	// List<Storage> storagelist = storageDao.getStockAgeByCondition(condition,
	// page);
	// for (Storage tmp : storagelist) {
	// //设置仓库名称和库位名称
	// if (tmp.getLocId() != null) {
	// DepLocation depLocation = depLocationDao.getLocationsById(tmp
	// .getLocId());
	// Depository depository = depLocationManager.getDepositoryByLocationId(tmp
	// .getLocId());
	// if (depLocation != null) {
	// tmp.setDepositoryName(depLocation.getDepName());
	// tmp.setDepLocationName(depLocation.getLocName());
	// }
	// if (depository != null) {
	// tmp.setDepType(depository.getType());
	// }
	// }
	// //设置供应商名称
	// if (tmp.getSupplierId() != null) {
	// Supplier supplier = supplierDao.selectSupplierById(tmp.getSupplierId());
	// if (supplier != null) {
	// tmp.setSupplierName(supplier.getName());
	// }
	// }
	// }
	// return storagelist;
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	@SuppressWarnings("unchecked")
	public Storage getStorageAmountByCondition(StorageQuery storageQuery) {
		log.info("StorageManagerImpl.getStorageAmountByCondition method");
		QueryPage queryPage = new QueryPage(storageQuery);
		Map condition = queryPage.getParameters();
		if(storageQuery.getDepfirstIds()!= null && storageQuery.getDepfirstIds().size() > 0){
			condition.put("depfirstIds", storageQuery.getDepfirstIds());
		}
//		try {
//			if (StringUtil.isNotBlank(condition.get("stockAge"))) {
//				if (condition.get("stockAge").equals("fifteen")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffDate(new Date(), -15));
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				} else if (condition.get("stockAge").equals("twenty")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffDate(new Date(), -20));
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				} else if (condition.get("stockAge").equals("onemonth")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffDate(new Date(), -30));
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				} else if (condition.get("stockAge").equals("twomonth")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffMon(new Date(), -2));
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				} else if (condition.get("stockAge").equals("threemonth")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffMon(new Date(), -3));
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				} else if (condition.get("stockAge").equals("halfyear")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffMon(new Date(), -6));
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				} else if (condition.get("stockAge").equals("allday")) {
//					condition.put("ageDateStart",
//							DateUtil.getDiffMon(new Date(), -120)); // 10年前
//					condition.put("ageDateEnd",
//							DateUtil.getDateToString(new Date()));
//				}
//			}
			return storageDao.getStorageAmountByCondition(condition);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//		return null;
	}

	// /**
	// * 获取成本价
	// * @param Long id
	// * @return
	// */
	// public Double getStoragePrice(Long id) {
	// log.info("StorageManagerImpl.getStoragePrice method");
	// try {
	// return storageDao.getStoragePriceById(id);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	// -- modify by wangkun 2010-09-25 修改可用库存查询 --//
	public Long sumStorageByGoodsInstanceId(long goodsInstanceId,
			Long depFirstId, String type) {
		try {
			if ("exist".equals(type)) {
				return storageDao.sumExistNumByGoodsInstanceId(goodsInstanceId,
						depFirstId);
			} else if ("storage".equals(type)) {
				return storageDao.sumStorageByGoodsInstanceId(goodsInstanceId,
						depFirstId);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public int getStorageNumBySend(Map parameterMap) {
		return storageDao.getStorageNumBySend(parameterMap);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hundsun.bible.facade.ios.StorageManager#refundApplyStorages(java. util.List)
	 */
	@Transactional
	public Long refundApplyStorages(
			List<StorageRefundApply> storageRefundApplyList,
			String disposeUserName) {
		Long refundId = new Long(0);
		Date nowDate = new Date();
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cutuiCode = codeManager.buildCodeByDateAndType(
				CodeManager.KUTUI_CODE, 6, "KT");

		if (storageRefundApplyList != null && storageRefundApplyList.size() > 0) {
			try {
				// Date gmtOutDep = df.parse(df.format(nowDate));

				OutDetail outDetail = null;
				ProdRelationOut prodRelationOutInfo = null;
				Long detailId = null;

				ShoppingRefundDetail shoppingRefundDetail;
				int i = 0;
				int j = 0;
				Long outId = null;
				for (StorageRefundApply temp : storageRefundApplyList) {
					if (temp.getRefundNum() == 0 || temp.getRefundNum() < 0) {
						i++;
						continue;
					}
					Storage storage = storageDao
							.getStorage(temp.getStorageId());
					GoodsInstance gi = goodsInstanceDao.getInstance(storage
							.getGoodsInstanceId());
					if (storage.getStorageNum() < temp.getRefundNum()) {
						throw new Exception(gi.getInstanceName()
								+ "实际库存小于出库数量，不允许操作！");
					}

					AvailableStock availableStockTmp = availableStockDao
							.getAvailableStock(storage.getGoodsInstanceId(),
									storage.getDepFirstId());
					if (availableStockTmp != null) {
						if (availableStockTmp.getGoodsNumber().longValue() < temp
								.getRefundNum()) {
							throw new Exception(gi.getInstanceName()
									+ "可用库存小于出库数量，不允许操作！");
						}
					}

					if (temp.getRefundNum() > 0) {
						if (j == 0) {
							// 退货基本信息
							ShoppingRefund shoppingRefund = new ShoppingRefund();
							shoppingRefund.setRefNum(cutuiCode);
							shoppingRefund.setSupplierId(storage
									.getSupplierId());
							shoppingRefund
									.setType(EnumShoppingRefund.REFUND_STORAGE
											.getKey());// 库存退货
							shoppingRefund.setRefTime(nowDate);
							shoppingRefund.setGmtCreate(nowDate);
							shoppingRefund.setCreater(disposeUserName);
							shoppingRefund
									.setStatus(EnumShoppingRefundStatus.STOCK_NEW
											.getKey());
							shoppingRefund.setDepFirstId(storage
									.getDepFirstId());
							refundId = shoppingRefundDao
									.addShoppingRefund(shoppingRefund);
						}
						// 退货商品信息
						shoppingRefundDetail = new ShoppingRefundDetail();
						shoppingRefundDetail.setGoodsInstanceId(gi.getId());
						shoppingRefundDetail.setCode(gi.getCode());
						shoppingRefundDetail.setGoodsId(gi.getGoodsId());
						shoppingRefundDetail.setShopRefId(refundId);
						shoppingRefundDetail.setShoppingId(Long.parseLong("0"));
						shoppingRefundDetail.setRefPrice(temp.getRefPrice());
						shoppingRefundDetail.setReason(temp.getReason());
						shoppingRefundDetail.setRefNum(temp.getRefundNum());
						shoppingRefundDetail.setDueFee(temp.getDueFee());
						shoppingRefundDetail.setFactFee(temp.getFactFee());
						shoppingRefundDetail.setUnits(gi.getGoodsUnit());
						shoppingRefundDetail.setRemark(temp.getMemo());
						shoppingRefundDetail.setLocId(temp.getLocId());
						if (temp.getRefundNum() > 0) {
							shoppingRefundDetailDao
									.addShoppingRefundDetail(shoppingRefundDetail);
							if (j == 0) {
								// 生成出库单基本信息
								OutDepository out = new OutDepository();
								out.setBillNum(codeManager
										.buildCodeByDateAndType(
												CodeManager.CHUKU_CODE, 6, "CK"));
								out.setType(EnumOutDepository.OUT_STORAGE_REFUND
										.getKey());
								out.setRelationNum(cutuiCode);
								out.setCreater(disposeUserName);
								out.setStatus(EnumOutStatus.OUT_NEW.getKey());
								out.setGmtCreate(nowDate);
								// out.setGmtOutDep(gmtOutDep);
								out.setDepFirstId(storage.getDepFirstId());
								out.setIsWholesale(storage.getIsWholesale());// 添加是否批发标签
																				// modify
																				// by
																				// fanyj
																				// 2010-11-02
								outId = outDepositoryDao.addOutDepository(out);
							}

							// 生成出库单
							outDetail = new OutDetail();
							outDetail.setGoodsInstanceId(gi.getId());
							outDetail.setGoodsId(gi.getGoodsId());
							outDetail.setOutDepositoryId(outId);
							outDetail.setOutNum(temp.getRefundNum());
							outDetail.setUnitPrice(temp.getRefPrice());
							outDetail.setDueFee(temp.getDueFee());
							outDetail.setFactFee(temp.getFactFee());
							outDetail.setStatus(EnumOutDetailStatus.OUT_NEW
									.getKey());
							outDetail.setDepFirstId(storage.getDepFirstId());
							outDetail.setStorType(storage.getStorType());
							detailId = outDetailDao.addOutDetail(outDetail);

							// 添加到商品出库关联表中
							prodRelationOutInfo = new ProdRelationOut();
							prodRelationOutInfo.setOutDepId(outId);
							prodRelationOutInfo.setGoodsInstanceId(gi.getId());
							prodRelationOutInfo.setAmount(temp.getRefundNum());
							prodRelationOutInfo.setGoodsId(gi.getGoodsId());
							prodRelationOutInfo.setOutDetailId(detailId);
							if (storage != null) {
								prodRelationOutInfo.setStorageId(storage
										.getId());
							}
							prodRelationOutInfo.setLocId(temp.getLocId());
							prodRelationOutInfo.setGmtCreate(nowDate);
							prodRelationOutInfo.setGmtModify(nowDate);
							prodRelationOutInfo.setSelfCost(temp.getRefPrice());
							prodRelationOutInfo.setIsWholesale(storage
									.getIsWholesale());
							prodRelationOutDao
									.addProdRelationOut(prodRelationOutInfo);

							/*
							 * 功能改造：申请库存退货审核通过后出库单保持新建状态（by fanyj 2010-10-15）
							 *
							 * // 修改库存数量 storageDao.updateStorageStorageNumById(0 - temp.getRefundNum(),
							 * storage.getId()); //如果一级仓库id为批发一级仓库的时候,不减可用库存 zhangwy DepositoryFirst depositoryFirst =
							 * depositoryFirstManager .getDepositoryById(storage.getDepFirstId());
							 * if(depositoryFirst!=null && (!depositoryFirst.getType().equals("w"))){ // 修改产品表和商品表中的库存数量
							 * goodsInstanceManager.updateAmountForTwo (gi.getId(),gi.getGoodsId(), Long.valueOf(0 -
							 * temp.getRefundNum()), storage.getDepFirstId(),true); }
							 *
							 * //加入全部一级仓库的剩余库存 long leftDepNum = 0; if (outDetail.getDepFirstId() != null) { leftDepNum
							 * = this.storageDao.sumStorageByGoodsInstanceId(outDetail .getGoodsInstanceId(),
							 * outDetail.getDepFirstId()); } //加入剩余库存 zhangwy long leftNum =
							 * this.storageDao.sumStorageByGoodsInstanceId (outDetail.getGoodsInstanceId(), null);
							 * outDetail.setLeftNum(leftNum); outDetail.setLeftDepNum(leftDepNum); if (EnumStorType
							 * .STOR_TYPE_V.getKey().equals(storage. getStorType())) {
							 * outDetail.setOutVirtualNum(temp.getRefundNum()); } outDetailDao.editOutDetail(outDetail);
							 */

							// 成功后修改申请表状态
							StorageRefundApply storageRefundApply = this.storageRefundApplyDao
									.getStorageRefundApplyById(temp.getId());
							storageRefundApply
									.setStatus(EnumStorageRefundApply.SUCCESS
											.getKey());
							storageRefundApply
									.setDisposeUserName(disposeUserName);
							this.storageRefundApplyDao
									.updateStorageRefundApply(storageRefundApply);
						}
						j++;
						i++;
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		return refundId;
	}



	@Override
	public List<Storage> getRefundStoragesByMap(Map<String, String> parMap) {
		return storageDao.getRefundStoragesByMap(parMap);
	}

	@Override
	public List<Storage> getStoragesByCondition(Map parMap) {
		List<Storage> storagelist = new ArrayList<Storage>();
		storagelist = storageDao.getStoragesByCondition(parMap);
		if (storagelist != null) {
			for (Storage tmp : storagelist) {
				// 设置一级仓库名称
				if (tmp.getDepFirstId() != null) {
					DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(tmp.getDepFirstId());
					if (depositoryFirst != null) {
						tmp.setDepFirstName(depositoryFirst.getDepFirstName());
					}
				}
				// 设置仓库名称和库位名称
				if (tmp.getLocId() != null) {
					DepLocation depLocation = depLocationDao.getLocationsById(tmp.getLocId());
					Depository depository = depLocationManager.getDepositoryByLocationId(tmp.getLocId());
					if (depLocation != null) {
						tmp.setDepositoryName(depLocation.getDepName());
						tmp.setDepLocationName(depLocation.getLocName());
					}
					if (depository != null) {
						tmp.setDepType(depository.getType());
					}
				}
				// 设置供应商名称
				if (tmp.getSupplierId() != null) {
					Supplier supplier = supplierDao.selectSupplierById(tmp.getSupplierId());
					if (supplier != null) {
						tmp.setSupplierName(supplier.getName());
					}
				}
			}
		}
		return storagelist;
	}

	public QueryPage getStoragesWithMoveTwo(MoveStorageQuery moveStorageQuery, int currPage, int pageSize) {
        log.info("StorageManagerImpl.getStoragesWithMove method");
        try {
            QueryPage queryPage = storageDao.getStoragesWithMove(moveStorageQuery, currPage, pageSize);
            List<Storage> storagelist = (List<Storage>) queryPage.getItems();
            for (Storage tmp : storagelist) {
                //设置一级仓库名称
                if (tmp.getDepFirstId() != null) {
                    DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(tmp
                        .getDepFirstId());
                    if (depositoryFirst != null) {
                        tmp.setDepFirstName(depositoryFirst.getDepFirstName());
                        tmp.setDepfirstType(depositoryFirst.getType());
                    }
                }
                //设置仓库名称和库位名称
                if (tmp.getLocId() != null) {
                    DepLocation depLocation = depLocationManager.getDepLocationByLocationId(tmp
                        .getLocId());
                    Depository depository = depLocationManager.getDepositoryByLocationId(tmp
                        .getLocId());
                    if (depLocation != null) {
                        tmp.setDepositoryName(depLocation.getDepName());
                        tmp.setDepLocationName(depLocation.getLocName());
                    }
                    if (depository != null) {
                        tmp.setDepType(depository.getType());
                    }
                }
                //设置供应商名称
                if (tmp.getSupplierId() != null) {
                    Supplier supplier = supplierDao.selectSupplierById(tmp.getSupplierId());
                    if (supplier != null) {
                        tmp.setSupplierName(supplier.getName());
                    }
                }
            }
            return queryPage;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

	@Override
	public long getSumGoodsNumberByGoodsId(Long goodsId) {
		log.info("StorageManagerImpl.getSumGoodsNumberByGoodsId method");
		try {
			return availableStockDao.getSumGoodsNumberByGoodsId(goodsId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Long getLeftStorageNumWithLoc(Map parMap) {
		return storageDao.getLeftStorageNumWithLoc(parMap);
	}

}
