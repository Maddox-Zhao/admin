/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.biz.service.supplier.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.stock.ShoppingDetailDao;
import com.huaixuan.network.biz.dao.storage.DamagedDao;
import com.huaixuan.network.biz.dao.supplier.SupplierDao;
import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.supplier.SupplierService;

/**
 * @author shengyong
 * @version $Id: SupplierServiceImpl.java,v 0.1 2011-3-3 上午10:44:14 shengyong Exp $
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    SupplierDao         supplierDao;

    @Autowired
    DamagedDao          damagedDao;

    @Autowired
    ShoppingDetailDao   shoppingDetailDao;

    public void addSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new NullPointerException("supplier can't be null.");
        }
        supplierDao.insert(supplier);
    }

    //    public List<Supplier> searchSupplierList(Supplier supplier, Page page) {
    //        return supplierDao.searchSupplierList(supplier, page);
    //    }

    //    public int searchSupplierCount(Supplier supplier) {
    //        return supplierDao.searchSupplierCount(supplier);
    //    }

    public Supplier selectSupplierById(Long id) {
        Supplier s = supplierDao.selectSupplierById(id);
        if (s == null) {
            return s;
        }
        try {
            /* 根据供货商ID得到实际到货时间小于等于预期到货时间的个数 */
            int goodsmin = damagedDao.getArreveCountBySupplierId(s.getId());
            int goodssum = damagedDao.getCountBySupplierId(s.getId());
            double arrivePer = 0.00;
            if (goodssum > 0) {
                arrivePer = (goodsmin * 100) / goodssum;
            }
            s.setArrivePer(arrivePer);

            /* 根据供应商ID统计退货数量 */
            ShoppingDetail sd = shoppingDetailDao.getGatherNumBySupplierId(s.getId());
            s.setReturnPer(sd.getReturnPer());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return s;
    }

    public void updateSupplier(Supplier supplier) {
        supplierDao.updateSupplier(supplier);
    }

    public void updateSupplierStatus(Supplier supplier) {
        supplierDao.updateSupplierStatus(supplier);
    }

    /**
     * 得到所有供应商
     *
     * @return
     * @throws SQLException
     */
    public List<Supplier> getSupplier() {

        try {
            return supplierDao.getSupplier();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    public Supplier selectSupplierByName(String name) {

        return supplierDao.selectSupplierByName(name);

    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage searchSupplierListWithPage(Supplier supplier, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(supplier);
        Map pramas = queryPage.getParameters();

        int count = supplierDao.searchSupplierCount(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<Supplier> supplierList = supplierDao.searchSupplierList(pramas);
            if (supplierList != null && supplierList.size() > 0) {
                queryPage.setItems(supplierList);
            }
        }
        return queryPage;
    }

}
