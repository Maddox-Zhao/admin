/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.biz.dao.supplier;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.supplier.Supplier;

/**
 * @author shengyong
 * @version $Id: SupplierDao.java,v 0.1 2011-3-3 ����10:49:23 shengyong Exp $
 */
public interface SupplierDao {

    void insert(Supplier record);

    //List<Supplier> searchSupplierList(Supplier supplier, Page page);

    //    int searchSupplierCount(Supplier supplier);

    List<Supplier> searchSupplierList(Map parMap);

    int searchSupplierCount(Map parMap);

    Supplier selectSupplierById(Long id);

    /**
     * �༭��Ӧ����Ϣ
     * 
     * @param supplier
     */
    void updateSupplier(Supplier supplier);

    /**
     * �������ι�Ӧ��
     * 
     * @param supplier
     */
    void updateSupplierStatus(Supplier supplier);

    // �õ����еĹ�Ӧ��
    List<Supplier> getSupplier() throws SQLException;

    Supplier selectSupplierByName(String name);

}
