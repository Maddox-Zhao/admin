package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationInDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;
import com.huaixuan.network.biz.service.storage.ProdRelationInManager;

/**
 * 
 * @version 3.2.0
 */
@Service("prodRelationInManagerImpl")
public class ProdRelationInManagerImpl implements ProdRelationInManager {
	Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ProdRelationInDao prodRelationInDao;
/* @model: */
	public void addProdRelationIn(ProdRelationIn prodRelationInDao) {
		log.info("ProdRelationInManagerImpl.addProdRelationIn method");
		try {
			this.prodRelationInDao.addProdRelationIn(prodRelationInDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void editProdRelationIn(ProdRelationIn prodRelationIn) {
		log.info("ProdRelationInManagerImpl.editProdRelationIn method");
		try {
			this.prodRelationInDao.editProdRelationIn(prodRelationIn);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeProdRelationIn(Long prodRelationInId) {
		log.info("ProdRelationInManagerImpl.removeProdRelationIn method");
		try {
			this.prodRelationInDao.removeProdRelationIn(prodRelationInId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public ProdRelationIn getProdRelationIn(Long prodRelationInId) {
		log.info("ProdRelationInManagerImpl.getProdRelationIn method");
		try {
			return this.prodRelationInDao.getProdRelationIn(prodRelationInId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
/* @model: */
	public List<ProdRelationIn> getProdRelationIns() {
		log.info("ProdRelationInManagerImpl.getProdRelationIns method");
		try {
			return this.prodRelationInDao.getProdRelationIns();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * £¨·Ç Javadoc£©
	 * 
	 * @see com.hundsun.bible.facade.ios.ProdRelationInManager#getPrintList()
	 */
	public List<ProdRelationIn> getPrintList(Long inDepId) {
		log.info("ProdRelationInManagerImpl.getPrintList method");
		try {
			return this.prodRelationInDao.getPrintList(inDepId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<ProdRelationIn> getLocIdAndAmountForStorage(ProdRelationIn prodRelationIn) {
		log.info("ProdRelationInManagerImpl.getLocIdAndAmountForStorage method");
		try {
			return this.prodRelationInDao.getLocIdAndAmountForStorage(prodRelationIn);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProdRelationIn> getSalesDistributedAmount(Map map) {
		log.info("ProdRelationInManagerImpl.getSalesDistributedAmount method");
		try {
			return this.prodRelationInDao.getSalesDistributedAmount(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Long getShoppingAndCheckDistributedAmount(Map map) {
		log.info("ProdRelationInManagerImpl.getShoppingAndCheckDistributedAmount method");
		try {
			return this.prodRelationInDao.getShoppingAndCheckDistributedAmount(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
