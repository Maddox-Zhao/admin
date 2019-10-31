package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.DaiGouProDao;
import com.huaixuan.network.biz.domain.hx.DaiGouPro;

/**
 * 2012-03-09 13:35
 * ´ú¹º²úÆ·
 * @author Mr_Yang
 *
 */
@Repository("daigouProDao")
public class DaiGouProiBatis implements DaiGouProDao
{
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public void addDaiGouPro(DaiGouPro daigouPro)
	{
		sqlMap.insert("insertDaigouPro",daigouPro);
	}

	@Override
	public void deleteDaiGouProById(Long id)
	{
		sqlMap.delete("deleteDaigouProById",id);
	}

	@Override
	public DaiGouPro getDaiGouProById(Long id)
	{
		return (DaiGouPro)sqlMap.queryForObject("selectDaigouProById",id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DaiGouPro> getDaiGouProListByDaiGouCode(String daigouCode)
	{
		return (List<DaiGouPro>)sqlMap.queryForList("selectDaiGouProListByDaiGouCode",daigouCode);
	}

	@Override
	public void updatDaiGouProByNotNull(DaiGouPro daigouPro)
	{
		sqlMap.update("updateDaigouProByNotNull",daigouPro);
	}

}
