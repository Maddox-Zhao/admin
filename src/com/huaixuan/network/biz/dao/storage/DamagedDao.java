/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Damaged;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @author shengyong
 * @version $Id: DamagedDao.java,v 0.1 2011-3-3 上午11:27:01 shengyong Exp $
 */
public interface DamagedDao {
	/* @interface model: Damaged */
	long addDamaged(Damaged damaged) throws Exception;

	/* @interface model: Damaged */
	void editDamaged(Damaged damaged) throws Exception;

	/* @interface model: Damaged */
	void removeDamaged(Long damagedId) throws Exception;

	/* @interface model: Damaged,Damaged */
	Damaged getDamaged(Map parMap) throws Exception;

	/* @interface model: Damaged,Damaged */
	List<Damaged> getDamageds() throws Exception;

	/*
	 * 根据查询条件得到报残单记录集合
	 */
	List<Damaged> getDamagedListsByParameterMap(Map parameterMap)
			throws Exception;

	/*
	 * 根据查询条件得到报残单记录数量
	 */
	int getDamagedCountByParameterMap(Map<String, String> parMap)
			throws Exception;

	/* 根据供货商ID得到实际到货时间小于等于预期到货时间的个数 */
	int getArreveCountBySupplierId(Long suplierId) throws Exception;

	/* 根据供货商ID得到所有采购单数量 */
	int getCountBySupplierId(Long suplierId) throws Exception;

}
