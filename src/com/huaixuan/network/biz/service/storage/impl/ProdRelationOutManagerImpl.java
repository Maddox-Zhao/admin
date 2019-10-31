package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationOutDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.service.storage.ProdRelationOutManager;

/**
 * 
 * @version 3.2.0
 */
@Service("prodRelationOutManager")
public class ProdRelationOutManagerImpl implements ProdRelationOutManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ProdRelationOutDao prodRelationOutDao;
/* @model: */
	public void addProdRelationOut(ProdRelationOut prodRelationOutDao) {
		log.info("ProdRelationOutManagerImpl.addProdRelationOut method");
		try {
			this.prodRelationOutDao.addProdRelationOut(prodRelationOutDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void editProdRelationOut(ProdRelationOut prodRelationOut) {
		log.info("ProdRelationOutManagerImpl.editProdRelationOut method");
		try {
			this.prodRelationOutDao.editProdRelationOut(prodRelationOut);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeProdRelationOut(Long prodRelationOutId) {
		log.info("ProdRelationOutManagerImpl.removeProdRelationOut method");
		try {
			this.prodRelationOutDao.removeProdRelationOut(prodRelationOutId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public ProdRelationOut getProdRelationOut(Long prodRelationOutId) {
		log.info("ProdRelationOutManagerImpl.getProdRelationOut method");
		try {
			return this.prodRelationOutDao.getProdRelationOut(prodRelationOutId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
/* @model: */
	public List<ProdRelationOut> getProdRelationOuts() {
		log.info("ProdRelationOutManagerImpl.getProdRelationOuts method");
		try {
			return this.prodRelationOutDao.getProdRelationOuts();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * £¨·Ç Javadoc£©
	 * 
	 * @see com.hundsun.bible.facade.ios.ProdRelationOutManager#getPrintList(java.lang.Long)
	 */
	public List<ProdRelationOut> getPrintList(Long outDepId) {
		log.info("ProdRelationOutManagerImpl.getPrintList method");
		try {
			return this.prodRelationOutDao.getPrintList(outDepId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Long getDistributedAmount(Map map) {
		log.info("ProdRelationOutManagerImpl.getDistributedAmount method");
		try {
			return this.prodRelationOutDao.getDistributedAmount(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
