package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.DaiGouDao;
import com.huaixuan.network.biz.domain.hx.DaiGou;

/**
 * 2012-03-08 17:09
 * @author Mr_Yang
 *
 */
@Service("daiGouDao")
public class DaiGouDaoiBatis implements DaiGouDao
{

	@Autowired
	private SqlMapClientTemplate sqlMap; 
	
	@Override
	public Long addAcquisition(DaiGou daigou)
	{
		return (Long)sqlMap.insert("insertDaigou",daigou);
	}

	@Override
	public void deleteDaiGouById(Long id)
	{
		sqlMap.delete("deleteDaigouById",id);
	}

	@Override
	public DaiGou getDaiGouById(Long id)
	{
		return (DaiGou)sqlMap.queryForObject("selectDaigouById",id);
	}

	@Override
	public List<DaiGou> getDaiGouViewByConditionWithPage(Map parMap)
	{
		return sqlMap.queryForList("getDaiGouViewByConditionWithPage",parMap);
	}

	@Override
	public Integer getDaiGouViewByConditionWithPageCount(Map parMap)
	{
		return (Integer)sqlMap.queryForObject("getDaiGouViewByConditionWithPageCount",parMap);
	}

	@Override
	public void updateDaiGouByNotNull(DaiGou daigou)
	{
		sqlMap.update("updateDaigouByNotNull",daigou);
	}

	@Override
	public Integer daiGouCodeIsEixst(String daigouCode)
	{
		return (Integer)sqlMap.queryForObject("daiGouCodeIsExist",daigouCode);
	}

	@Override
	public DaiGou getDaiGouByDaiGouCode(String daigouCode)
	{
		return  (DaiGou)sqlMap.queryForObject("selectDaigouByDaigouCode",daigouCode);
	}

}
