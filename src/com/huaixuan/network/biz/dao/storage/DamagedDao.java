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
 * @version $Id: DamagedDao.java,v 0.1 2011-3-3 ����11:27:01 shengyong Exp $
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
	 * ���ݲ�ѯ�����õ����е���¼����
	 */
	List<Damaged> getDamagedListsByParameterMap(Map parameterMap)
			throws Exception;

	/*
	 * ���ݲ�ѯ�����õ����е���¼����
	 */
	int getDamagedCountByParameterMap(Map<String, String> parMap)
			throws Exception;

	/* ���ݹ�����ID�õ�ʵ�ʵ���ʱ��С�ڵ���Ԥ�ڵ���ʱ��ĸ��� */
	int getArreveCountBySupplierId(Long suplierId) throws Exception;

	/* ���ݹ�����ID�õ����вɹ������� */
	int getCountBySupplierId(Long suplierId) throws Exception;

}
