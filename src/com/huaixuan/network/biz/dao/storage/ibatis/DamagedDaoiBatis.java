/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-3
 */
package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.DamagedDao;
import com.huaixuan.network.biz.domain.storage.Damaged;

/**
 * @author 
 * @version $Id: DamagedDaoiBatis.java,v 0.1 2011-3-3 上午11:28:23  Exp $
 */
@Repository("damagedDao")
public class DamagedDaoiBatis implements DamagedDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
/* @model: */
	public long addDamaged(Damaged damaged) throws Exception {
		return (Long) this.sqlMapClient.insert("addDamaged", damaged);
	}

	@Override
	public List<Damaged> getDamagedListsByParameterMap(Map parameterMap) {
		return this.sqlMapClient.queryForList("getDamagedListsByParameterMap",
				parameterMap);
	}
/* @model: */
	public void editDamaged(Damaged damaged) throws Exception {
		this.sqlMapClient.update("editDamaged", damaged);
	}
/* @model: */
	public void removeDamaged(Long damagedId) throws Exception {
		this.sqlMapClient.delete("removeDamaged", damagedId);
	}
/* @model: */
	public Damaged getDamaged(Map parMap) throws Exception {
		return (Damaged) this.sqlMapClient.queryForObject("getDamaged", parMap);
	}
/* @model: */
	public List<Damaged> getDamageds() throws Exception {
		return this.sqlMapClient.queryForList("getDamageds", null);
	}

	/*
	 * （非 Javadoc
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDao#getDamagedCountByParameterMap(java
	 * .util.Map)
	 */
	public int getDamagedCountByParameterMap(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject(
				"getDamagedCountByParameterMap", parMap);
	}

	/*
	 * （非 Javadoc
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDao#getArreveCountBySupplierId(java.
	 * lang.Long)
	 */
	public int getArreveCountBySupplierId(Long suplierId) throws Exception {
		Integer i = (Integer) this.sqlMapClient.queryForObject(
				"getArreveCountBySupplierId", suplierId);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	/*
	 * （非 Javadoc
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDao#getCountBySupplierId(java.lang.Long)
	 */
	public int getCountBySupplierId(Long suplierId) throws Exception {
		Integer i = (Integer) this.sqlMapClient.queryForObject(
				"getArreveCountBySupplierId", suplierId);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

}
