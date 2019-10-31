/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.biz.dao.supplier.ibatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.supplier.SupplierDao;
import com.huaixuan.network.biz.domain.supplier.Supplier;

/**
 * @author shengyong
 * @version $Id: SupplierDaoiBatis.java,v 0.1 2011-3-3 上午10:50:30 shengyong Exp $
 */
@Repository("supplierDao")
public class SupplierDaoiBatis implements SupplierDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    /**
     * 获得所有的供应商
     */
    public List<Supplier> getSupplier() throws SQLException {
        List<Supplier> supplierListu = new ArrayList<Supplier>();
        supplierListu = sqlMapClient.queryForList("getSupplier");
        return supplierListu;

    }

    public void insert(Supplier record) {
        this.sqlMapClient.insert("add_supplier", record);
    }

    public List<Supplier> searchSupplierList(Map parMap) {
        return this.sqlMapClient.queryForList("searchSupplierList", parMap);
    }

    public int searchSupplierCount(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("searchSupplierCount", parMap);
    }

    public Supplier selectSupplierByName(String name) {
        return (Supplier) this.sqlMapClient.queryForObject("selectSupplierByName", name);
    }

    public Supplier selectSupplierById(Long id) {
        return (Supplier) this.sqlMapClient.queryForObject("getSupplierById", id);
    }

    public void updateSupplier(Supplier supplier) {
        this.sqlMapClient.update("editSupplier", supplier);
    }

    public void updateSupplierStatus(Supplier supplier) {
        this.sqlMapClient.update("editSupplierStatus", supplier);
    }

}