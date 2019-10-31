package com.huaixuan.network.biz.service.storage.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.storage.DamagedDetailDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.domain.storage.Damaged;
import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailGb;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.ProdRelationOutGb;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.enums.EnumDamagedStatus;
import com.huaixuan.network.biz.enums.EnumFinanceStatus;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DamagedDetailManager;
import com.huaixuan.network.biz.service.storage.DamagedManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailGbManager;
import com.huaixuan.network.biz.service.storage.OutDetailManager;
import com.huaixuan.network.biz.service.storage.ProdRelationOutGbManager;
import com.huaixuan.network.biz.service.storage.StorageManager;

/**
 *
 * @version 3.2.0
 */
@Repository("damagedDetailManager")
public class DamagedDetailManagerImpl implements DamagedDetailManager {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public DamagedDetailDao damagedDetailDao;
	@Autowired
	public ProdRelationOutDao prodRelationOutDao;
	@Autowired
	public DamagedManager damagedManager;
	@Autowired
	public OutDepositoryManager outDepositoryManager;
	@Autowired
	public StorageManager storageManager;
	@Autowired
	public OutDetailManager outDetailManager;
	@Autowired
	public GoodsInstanceManager goodsInstanceManager;
	@Autowired
	private StorageDao storageDao;
	@Autowired
	private OutDetailDao outDetailDao;
	@Autowired
	private DepositoryService depositoryService;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;
	@Autowired
	public GoodsDao goodsDao;
	@Autowired
	public OutDetailGbManager outDetailGbManager;
	@Autowired
	public ProdRelationOutGbManager prodRelationOutGbManager;
	
	// **
	// * @return the storageDao
	// */
	// /*
	// * public StorageDao getStorageDao() { return storageDao; }
	// *//**
	// * @param storageDao
	// * the storageDao to set
	// */
	// /*
	// * public void setStorageDao(StorageDao storageDao) { this.storageDao =
	// * storageDao; }
	// *//**
	// * @return the outDetailDao
	// */
	// /*
	// * public OutDetailDao getOutDetailDao() { return outDetailDao; }
	// *//**
	// * @param outDetailDao
	// * the outDetailDao to set
	// */
	// /*
	// * public void setOutDetailDao(OutDetailDao outDetailDao) {
	// * this.outDetailDao = outDetailDao; }
	// *//**
	// * @return damagedManager
	// */
	// /*
	// * public DamagedManager getDamagedManager() { return damagedManager; }
	// *//**
	// * @param damagedManager
	// * 要设置的 damagedManager
	// */
	// /*
	// * public void setDamagedManager(DamagedManager damagedManager) {
	// * this.damagedManager = damagedManager; }
	// *//**
	// * @return prodRelationOutDao
	// */
	// /*
	// * public ProdRelationOutDao getProdRelationOutDao() { return
	// * prodRelationOutDao; }
	// *//**
	// * @param prodRelationOutDao
	// * 要设置的 prodRelationOutDao
	// */
	// /*
	// * public void setProdRelationOutDao(ProdRelationOutDao
	// prodRelationOutDao)
	// * { this.prodRelationOutDao = prodRelationOutDao; }
	// *//**
	// * @return goodsInstanceManager
	// */
	// /*
	// * public GoodsInstanceManager getGoodsInstanceManager() { return
	// * goodsInstanceManager; }
	// *//**
	// * @param goodsInstanceManager
	// * 要设置的 goodsInstanceManager
	// */
	// /*
	// * public void setGoodsInstanceManager(GoodsInstanceManager
	// * goodsInstanceManager) { this.goodsInstanceManager =
	// goodsInstanceManager;
	// * }
	// *//**
	// * @return outDepositoryManager
	// */
	// /*
	// * public OutDepositoryManager getOutDepositoryManager() { return
	// * outDepositoryManager; }
	// *//**
	// * @param outDepositoryManager
	// * 要设置的 outDepositoryManager
	// */
	// /*
	// * public void setOutDepositoryManager(OutDepositoryManager
	// * outDepositoryManager) { this.outDepositoryManager =
	// outDepositoryManager;
	// * }
	// *//**
	// * @return outDetailManager
	// */
	// /*
	// * public OutDetailManager getOutDetailManager() { return
	// outDetailManager;
	// * }
	// *//**
	// * @param outDetailManager
	// * 要设置的 outDetailManager
	// */
	// /*
	// * public void setOutDetailManager(OutDetailManager outDetailManager) {
	// * this.outDetailManager = outDetailManager; }
	// *//**
	// * @return storageManager
	// */
	// /*
	// * public StorageManager getStorageManager() { return storageManager; }
	// *//**
	// * @param storageManager
	// * 要设置的 storageManager
	// */
	// /*
	// * public void setStorageManager(StorageManager storageManager) {
	// * this.storageManager = storageManager; }
	// *
	// * public void setDamagedDetailDao(DamagedDetailDao damagedDetailDao) {
	// * this.damagedDetailDao = damagedDetailDao; }
	// *
	// * public DamagedDetailDao getDamagedDetailDao() { return
	// * this.damagedDetailDao; }
	// *
	@Override
	public long addDamagedDetail(DamagedDetail damagedDetailDao) {
		log.info("DamagedDetailManagerImpl.addDamagedDetail method");
		try {
			return this.damagedDetailDao.addDamagedDetail(damagedDetailDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public void editDamagedDetail(DamagedDetail damagedDetail) {
		log.info("DamagedDetailManagerImpl.editDamagedDetail method");
		try {
			this.damagedDetailDao.editDamagedDetail(damagedDetail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeDamagedDetail(Long damagedDetailId) {
		log.info("DamagedDetailManagerImpl.removeDamagedDetail method");
		try {
			this.damagedDetailDao.removeDamagedDetail(damagedDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	// DamagedDetailView getDamagedDetail(Long damagedDetailId) {
	// log.info("DamagedDetailManagerImpl.getDamagedDetail method"); try {
	// return this.damagedDetailDao.getDamagedDetail(damagedDetailId); } catch
	// (Exception e) { log.error(e.getMessage()); } return null; }

	@Override
	public DamagedDetail getDamagedDetailById(Long damagedDetailId) {
		log.info("DamagedDetailManagerImpl.getDamagedDetailById method");
		try {
			return this.damagedDetailDao.getDamagedDetailById(damagedDetailId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	// *
	// * （非 Javadoc）
	// *
	// * @see
	// *
	// com.hundsun.bible.facade.ios.DamagedDetailManager#getCountByParameterMap
	// * (java.util.Map)
	// *
	// * public int getDamagedDetailsCountByParameterMap(Map<String, String>
	// * parMap) { log.info(
	// *
	// "DamagedDetailManagerImpl.getDamagedDetailsCountByParameterMap method");
	// * try { return
	// * damagedDetailDao.getDamagedDetailsCountByParameterMap(parMap); } catch
	// * (Exception e) { log.error(e.getMessage()); } return 0; }
	// *
	// * （非 Javadoc）
	// *
	// * @see
	// *
	// com.hundsun.bible.facade.ios.DamagedDetailManager#getListsByParameterMap
	// * (java.util.Map, com.hundsun.bible.Page)
	// *
	@Override
	public QueryPage getDamagedDetailsByParameterMap(
			Map parameterMap, int currPage, int pageSize) {
		log.info("DamagedDetailManagerImpl.getDamagedDetailsByParameterMap method");
		try {
			QueryPage queryPage = new QueryPage("da");

			int count = damagedDetailDao
					.getDamagedDetailsCountByParameterMap(parameterMap);
			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				parameterMap.put("startRow",queryPage.getPageFristItem());
				parameterMap.put("endRow",queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<DamagedDetailView> damagedDetailView = damagedDetailDao
						.getDamagedDetailsByParameterMap(parameterMap);
				if (damagedDetailView != null && damagedDetailView.size() > 0) {
					queryPage.setItems(damagedDetailView);
				}
			}
			return queryPage;
		} catch (Exception e) {
			// log.error(e.getMessage());
			// throw new ManagerException(e);
			return null;
		}
	}

	@Override
	public List<DamagedDetailView> getDamagedDetails(Map parameterMap) {
		log.info("DamagedDetailManagerImpl.getDamagedDetails method");
		try {
			return this.damagedDetailDao
					.getDamagedDetailsByParameterMap(parameterMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<DamagedDetail> getGroupCountListByMap(Map parameterMap) {
		log.info("DamagedDetailManagerImpl.getGroupCountListByMap method");
		try {
			return this.damagedDetailDao.getGroupCountListByMap(parameterMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int getCountByParameterMap(Map<String, String> parMap) {
		log.info("DamagedDetailManagerImpl.getCountByParameterMap method");
		try {
			return this.damagedDetailDao.getCountByParameterMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see
	 * com.hundsun.bible.facade.ios.DamagedDetailManager#finishedDamaged(java
	 * .util.Map)
	 */
	@Transactional
	@SuppressWarnings({ "unchecked" })
	@Override
	public Boolean finishedDamaged(Map parMap) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date gmtOutDep = df.parse(df.format(new Date()));
		// 出库单编号
		String billNum = (String) parMap.get("billNum");
		// 用户登录名
		String userName = (String) parMap.get("userName");
		// 报残单明细集合
		List<DamagedDetailView> damagedDetailViews = (List<DamagedDetailView>) parMap
				.get("damagedDetailViews");
		// 报残单对象
		Damaged damaged = (Damaged) parMap.get("damaged");

		damaged.setStatus(EnumDamagedStatus.DAM_FINISHED.getKey());
		// 编辑订单状态
		damagedManager.editDamaged(damaged);

		Depository dep = depositoryService.getDepository(damaged.getDepId());

		// 获取一级仓库
		DepositoryFirst depositoryFirst = depositoryFirstManager
				.getDepositoryById(dep.getDepFirstId());

		// 生成出库单基本信息
		OutDepository out = new OutDepository();
		out.setBillNum(billNum);
		out.setType(EnumOutDepository.OUT_DAMAGED.getKey());
		out.setRelationNum(damaged.getDamagedCode());
		out.setCreater(userName);
		out.setStatus(EnumOutStatus.OUT_FINISHED.getKey());
		out.setGmtCreate(new Date());
		out.setGmtOutDep(gmtOutDep);
		out.setFinanceStatus(EnumFinanceStatus.NO_SURE.getKey());
		if (dep != null) {
			out.setDepFirstId(dep.getDepFirstId());
		}
		Long outId = outDepositoryManager.addOutDepository(out);
		Map<String, String> map = new HashMap<String, String>();
		OutDetail outDetail = null;
		ProdRelationOut prodRelationOutInfo = null;
		Long detailId = null;
		for (DamagedDetailView obj : damagedDetailViews) {
			map.put("goodsInstanceId", String.valueOf(obj.getGoodsInstanceId()));
			map.put("supplierId", String.valueOf(obj.getSupplierId()));
			map.put("locId", String.valueOf(obj.getLocId()));
			map.put("batchNum", obj.getBatchNum());
			map.put("storageNum", String.valueOf(0 - obj.getAmount()));

			Storage storage = storageManager.getStorageByMap(map);
			if (storage == null) {
				return Boolean.FALSE;
			}
			//商品开票id
			Long billId = goodsDao.getBillId(obj.getGoodsId());
			// 生成出库单
			outDetail = new OutDetail();
			outDetail.setGoodsInstanceId(obj.getGoodsInstanceId());
			outDetail.setGoodsId(obj.getGoodsId());
			outDetail.setOutDepositoryId(outId);
			outDetail.setOutNum(obj.getAmount());
			outDetail.setUnitPrice(obj.getUnitCost());
			outDetail.setDueFee(obj.getUnitCost() * obj.getAmount());
			outDetail.setFactFee(obj.getUnitCost() * obj.getAmount());
			outDetail.setStatus(EnumOutDetailStatus.OUT_FINISHED.getKey());
			outDetail.setDepFirstId(out.getDepFirstId());
			if (EnumStorType.STOR_TYPE_V.getKey().equals(storage.getStorType())) {
				outDetail.setOutVirtualNum(obj.getAmount());
			}
			outDetail.setStorType(storage.getStorType());

			detailId = outDetailManager.addOutDetail(outDetail);

			// 添加到商品出库关联表中
			prodRelationOutInfo = new ProdRelationOut();
			prodRelationOutInfo.setOutDepId(outId);
			prodRelationOutInfo.setGoodsInstanceId(obj.getGoodsInstanceId());
			prodRelationOutInfo.setAmount(obj.getAmount());
			prodRelationOutInfo.setGoodsId(obj.getGoodsId());
			prodRelationOutInfo.setOutDetailId(detailId);

			prodRelationOutInfo.setStorageId(storage.getId());

			prodRelationOutInfo.setLocId(obj.getLocId());
			prodRelationOutInfo.setGmtCreate(new Date());
			prodRelationOutInfo.setGmtModify(new Date());
			prodRelationOutInfo.setSelfCost(obj.getUnitCost());

			long prodOutId = prodRelationOutDao.addProdRelationOut(prodRelationOutInfo);
			
            //开票公司出库分配表加入 zhangwy
			if(billId != null){
				ProdRelationOutGb prodRelationOutGb = new ProdRelationOutGb();
				prodRelationOutGb.setRelationId(prodOutId);
				prodRelationOutGb.setOutDepId(outId);
				prodRelationOutGb.setGoodsInstanceId(obj.getGoodsInstanceId());
				prodRelationOutGb.setAmount(obj.getAmount());
				prodRelationOutGb.setGoodsId(obj.getGoodsId());
				prodRelationOutGb.setOutDetailId(detailId);
				prodRelationOutGb.setStorageId(storage.getId());
				prodRelationOutGb.setLocId(obj.getLocId());
				prodRelationOutGb.setGmtCreate(new Date());
				prodRelationOutGb.setGmtModify(new Date());
				prodRelationOutGb.setSelfCost(obj.getUnitCost());
				prodRelationOutGb.setBillId(billId);
				prodRelationOutGbManager.addProdRelationOutGb(prodRelationOutGb);
			}

			// 修改库存数量
			storageManager.updateStorageStoNumByMap(map);

			// zhangwy 一级仓库类型限制
			if (depositoryFirst != null
					&& (!depositoryFirst.getType().equals("w"))) {
				// 修改产品表和商品表中的库存数量
				goodsInstanceManager.updateAmountForTwo(
						Long.valueOf(obj.getGoodsInstanceId()),
						Long.valueOf(obj.getGoodsId()),
						Long.valueOf(0 - obj.getAmount()), out.getDepFirstId(),
						true);
			}

			outDetail.setStorType(storage.getStorType());
			long leftNum = this.storageDao.sumStorageByGoodsInstanceId(
					outDetail.getGoodsInstanceId(), null);
			outDetail.setLeftNum(leftNum);

			long leftDepNum = this.storageDao.sumStorageByGoodsInstanceId(
					outDetail.getGoodsInstanceId(), out.getDepFirstId());
			outDetail.setLeftDepNum(leftDepNum);

			outDetailManager.editOutDetail(outDetail);
			
			//开票公司出库单详情表加入 zhangwy
			if(billId!=null){
				OutDetailGb outDetailGb = new OutDetailGb();
				outDetailGb.setRelationId(detailId);
				outDetailGb.setGoodsInstanceId(obj.getGoodsInstanceId());
				outDetailGb.setGoodsId(obj.getGoodsId());
				outDetailGb.setOutDepositoryId(outId);
				outDetailGb.setOutNum(obj.getAmount());
				outDetailGb.setUnitPrice(obj.getUnitCost());
				outDetailGb.setDueFee(obj.getUnitCost() * obj.getAmount());
				outDetailGb.setFactFee(obj.getUnitCost() * obj.getAmount());
				outDetailGb.setStatus(EnumOutDetailStatus.OUT_FINISHED.getKey());
				outDetailGb.setLeftNum(outDetail.getLeftNum());
				outDetailGb.setRelationNum(outDetail.getRelationNum());
				outDetailGb.setDepFirstId(out.getDepFirstId());
				outDetailGb.setStorType(storage.getStorType());
				outDetailGb.setOutVirtualNum(outDetail.getOutVirtualNum());
				outDetailGb.setLeftDepNum(outDetail.getLeftDepNum());
				outDetailGb.setBillId(billId);
				outDetailGbManager.addOutDetailGb(outDetailGb);
			}
		}
		return Boolean.TRUE;
	}

	// * public void setDepositoryManager(DepositoryManager depositoryManager) {
	// * this.depositoryManager = depositoryManager; }

}
