/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.biz.service.supplier;

import java.util.List;

import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @author shengyong
 * @version $Id: SupplierService.java,v 0.1 2011-3-3 上午10:40:33 shengyong Exp $
 */
public interface SupplierService {

    /**
     * 添加供应商
     * 
     * @param supplier
     */
    public void addSupplier(Supplier supplier);

    /**
     * 供应商查询
     * 
     * @param supplier
     * @param page
     * @return
     */
    //    public List<Supplier> searchSupplierList(Supplier supplier, Page page);
    public QueryPage searchSupplierListWithPage(Supplier supplier, int currPage, int pageSize);

    /**
     * id根据id获得供应商Supplier
     * 
     * @param id
     * @return
     */
    public Supplier selectSupplierById(Long id);

    /**
     * 更新supplier对象
     * 
     * @param supplier
     */
    public void updateSupplier(Supplier supplier);

    /**
     * 更新supplier状态
     * @param supplier
     */
    public void updateSupplierStatus(Supplier supplier);

    /**
     * 得到所有供应商
     * 
     * @return
     * @throws SQLException
     */
    List<Supplier> getSupplier();

    /**
     * 通过名称获得供应商
     * 
     * @param name
     * @return
     */
    public Supplier selectSupplierByName(String name);

}
