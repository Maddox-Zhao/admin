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
 * @version $Id: SupplierService.java,v 0.1 2011-3-3 ����10:40:33 shengyong Exp $
 */
public interface SupplierService {

    /**
     * ��ӹ�Ӧ��
     * 
     * @param supplier
     */
    public void addSupplier(Supplier supplier);

    /**
     * ��Ӧ�̲�ѯ
     * 
     * @param supplier
     * @param page
     * @return
     */
    //    public List<Supplier> searchSupplierList(Supplier supplier, Page page);
    public QueryPage searchSupplierListWithPage(Supplier supplier, int currPage, int pageSize);

    /**
     * id����id��ù�Ӧ��Supplier
     * 
     * @param id
     * @return
     */
    public Supplier selectSupplierById(Long id);

    /**
     * ����supplier����
     * 
     * @param supplier
     */
    public void updateSupplier(Supplier supplier);

    /**
     * ����supplier״̬
     * @param supplier
     */
    public void updateSupplierStatus(Supplier supplier);

    /**
     * �õ����й�Ӧ��
     * 
     * @return
     * @throws SQLException
     */
    List<Supplier> getSupplier();

    /**
     * ͨ�����ƻ�ù�Ӧ��
     * 
     * @param name
     * @return
     */
    public Supplier selectSupplierByName(String name);

}
