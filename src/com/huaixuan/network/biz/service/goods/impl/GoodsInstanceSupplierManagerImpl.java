/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-4
 */
package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsInstanceSupplierDao;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSupplierManager;

/**
 * @author 
 * @version $Id: GoodsInstanceSupplierManagerImpl.java,v 0.1 2011-3-4 ÉÏÎç11:32:41  Exp $
 */
@Service("goodsInstanceSupplierManager")
public class GoodsInstanceSupplierManagerImpl implements GoodsInstanceSupplierManager {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	GoodsInstanceSupplierDao goodsInstanceSupplierDao;

	@Autowired
	CategoryManager categoryManager;

	/* @model: */
	public void addGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplierDao) {
		log.info("GoodsInstanceSupplierManagerImpl.addGoodsInstanceSupplier method");
		try {
			this.goodsInstanceSupplierDao.addGoodsInstanceSupplier(iossGoodsInstanceSupplierDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void editGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier) {
		log.info("GoodsInstanceSupplierManagerImpl.editGoodsInstanceSupplier method");
		try {
			this.goodsInstanceSupplierDao.editGoodsInstanceSupplier(iossGoodsInstanceSupplier);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void removeGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId) {
		log.info("GoodsInstanceSupplierManagerImpl.removeGoodsInstanceSupplier method");
		try {
			this.goodsInstanceSupplierDao.removeGoodsInstanceSupplier(iossGoodsInstanceSupplierId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/*
	 */
	public GoodsInstanceSupplier getGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId) {
		log.info("GoodsInstanceSupplierManagerImpl.getGoodsInstanceSupplier method");
		try {
			return this.goodsInstanceSupplierDao.getGoodsInstanceSupplier(iossGoodsInstanceSupplierId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 */
	public List<GoodsInstanceSupplier> getGoodsInstanceSuppliers() {
		log.info("GoodsInstanceSupplierManagerImpl.getGoodsInstanceSuppliers method");
		try {
			return this.goodsInstanceSupplierDao.getGoodsInstanceSuppliers();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<GoodsInstanceSupplier> getGoodsInstanceSuppliersByParameterMap(Map parameterMap, int currPage,
			int pageSize) {
		List<GoodsInstanceSupplier> goodsInsSuppliers = null;
		try {
			QueryPage query = this.goodsInstanceSupplierDao.getGoodsInstanceSuppliersByParameterMap(parameterMap,
					currPage, pageSize);
			goodsInsSuppliers = (List<GoodsInstanceSupplier>) query.getItems();
			for (GoodsInstanceSupplier gis : goodsInsSuppliers) {

				gis.setCatCode(categoryManager.getCatFullNameByCatcodeSimple(gis.getCatCode(), ">"));

			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return goodsInsSuppliers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryPage getGoodsInstanceSuppliersByQuery(Map parameterMap, int currPage, int pageSize) {
		QueryPage query = goodsInstanceSupplierDao.getGoodsInstanceSuppliersByParameterMap(parameterMap, currPage,
				pageSize);
		if (query.getItems() != null) {
			for (GoodsInstanceSupplier gis : (List<GoodsInstanceSupplier>) query.getItems()) {
				gis.setCatCode(categoryManager.getCatFullNameByCatcodeSimple(gis.getCatCode(), ">"));
			}
		}
		return query;
	}

	public int getGoodsInstanceSuppliersCountByParameterMap(Map parameterMap) {
		try {
			return this.goodsInstanceSupplierDao.getGoodsInstanceSuppliersCountByParameterMap(parameterMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public List<GoodsInstanceSupplier> findGoodsInstanceSuppliers(Long goodsInstanceId) {
		return this.goodsInstanceSupplierDao.findGoodsInstanceSuppliers(goodsInstanceId);
	}

	public void createInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier) throws Exception {
		List<GoodsInstanceSupplier> suppliers = findGoodsInstanceSuppliers(iossGoodsInstanceSupplier
				.getGoodsInstanceId());
		if (suppliers != null && !suppliers.isEmpty()) {
			for (GoodsInstanceSupplier gis : suppliers) {
				if (gis.getSupplierId().equals(iossGoodsInstanceSupplier.getSupplierId())
						&& gis.getGoodsInstanceId().equals(iossGoodsInstanceSupplier.getGoodsInstanceId())) {
					gis.setConsultPrice(iossGoodsInstanceSupplier.getConsultPrice());
					gis.setSupplierCode(iossGoodsInstanceSupplier.getSupplierCode());
					editGoodsInstanceSupplier(gis);
					return;
				}
			}
		}
		this.goodsInstanceSupplierDao.addGoodsInstanceSupplier(iossGoodsInstanceSupplier);
	}
}