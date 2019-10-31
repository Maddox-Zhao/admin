package com.huaixuan.network.biz.dao.purchase.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.purchase.BaseDataDao;
import com.huaixuan.network.biz.domain.purchase.BaseData;

/**
 *2012-7-5 ����04:00:23
 *Mr_Yang
 */
@Repository("baseDataDao")
public class BaseDataDaoiBatis implements BaseDataDao
{

	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	@Override
	public BaseData getBaseDataByMap(Map parMap)
	{
		return (BaseData)sqlMap.queryForObject("getBaseDataByMap",parMap);
	}

	@Override
	public List<BaseData> getBaseDataTmpByMap(Map parMap)
	{
		return sqlMap.queryForList("getBaseDataTmpByMap",parMap);
	}

	@Override
	public Integer getBaseDataTmpByMapCount(Map parMap)
	{
		return (Integer)sqlMap.queryForObject("getBaseDataTmpByMapCount",parMap);
	}

	@Override
	public Map getNotSameEUPrice(Long goodsId)
	{
		return sqlMap.queryForMap("getNotSamePriceByGoodsId", goodsId, "euPrice");
	}

	@Override
	public Map getNotSameHKPrice(Long goodsId)
	{
		return  sqlMap.queryForMap("getNotSamePriceByGoodsId", goodsId, "hkPrice");
	}

	@Override
	public List<BaseData> getInStorageInfoByMap(Map parMap)
	{
		return sqlMap.queryForList("getInStorageInfo",parMap);
	}

	@Override
	public Integer getInStorageInfoByMapCount(Map parMap)
	{
		return (Integer)sqlMap.queryForObject("getInStorageInfoCount",parMap);
	}

}
