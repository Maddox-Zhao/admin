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

import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceSalesDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceSupplierDao;
import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSales;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSalesManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSupplierManager;

@Service("goodsInstanceSalesManager")
public class GoodsInstanceSalesManagerImpl implements GoodsInstanceSalesManager {

    protected final Log      log = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsInstanceSalesDao goodsInstanceSalesDao;
    @Autowired
    private GoodsInstanceDao goodsInstanceDao;
    
    @Override
    public void addGoodsInstanceSales(GoodsInstanceSales goodsInstanceSales) {
        log.info("GoodsInstanceSalesManagerImpl.addGoodsInstanceSales method");
        try {
            this.goodsInstanceSalesDao.addGoodsInstanceSales(goodsInstanceSales);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    @Override
    public double getRefundAmountInfo(Map parMap) {
        log.info("GoodsInstanceSalesManagerImpl.getRefundAmountInfo method");
        try {
            return this.goodsInstanceSalesDao.getRefundAmountInfo(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public double getSalesAmountInfo(Map parMap) {
        log.info("GoodsInstanceSalesManagerImpl.getSalesAmountInfo method");
        try {
            return this.goodsInstanceSalesDao.getSalesAmountInfo(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public int getProductByParameterMap(Map parMap) {
        log.info("GoodsInstanceSalesManagerImpl.getProductByParameterMap method");
        try {
            return this.goodsInstanceSalesDao.getProductByParameterMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0; 
    }

    @Override
    public QueryPage getProductListsByParameterMap(CrmRankQuery crmProductRankQuery,int currentPage, int pageSize) {
        log.info("GoodsInstanceSalesManagerImpl.getProductListsByParameterMap method");
        try {
            QueryPage queryPage = this.goodsInstanceSalesDao.getProductListsByParameterMap(crmProductRankQuery, currentPage, pageSize);
            if(queryPage != null && queryPage.getItems() != null && queryPage.getItems().size()>0){
              for(GoodsInstanceSales  temp : (List<GoodsInstanceSales>)queryPage.getItems()){
                  GoodsInstance goodsInstance = goodsInstanceDao.getInstance(temp.getGoodsInstanceId());
                  temp.setName(goodsInstance.getInstanceName());
                  if(goodsInstance.getAttrDesc()!=null){
                      temp.setDesc(goodsInstance.getAttrDesc());
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
    public void deleteProductByParameterMap(Map parMap) {
        log.info("GoodsInstanceSalesManagerImpl.deleteProductByParameterMap method");
        try {
            this.goodsInstanceSalesDao.deleteProductByParameterMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public int getSearchDayByMap(Map parMap) {
        log.info("GoodsInstanceSalesManagerImpl.getSearchDayByMap method");
        try {
            return this.goodsInstanceSalesDao.getSearchDayByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}