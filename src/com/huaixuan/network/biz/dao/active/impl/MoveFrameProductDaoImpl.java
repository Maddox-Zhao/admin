package com.huaixuan.network.biz.dao.active.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.active.MoveFrameProductDao;
import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeGoods;
import com.huaixuan.network.biz.domain.active.MoveframeInstance;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;

@Repository("moveFrameProductDao")
public class MoveFrameProductDaoImpl implements MoveFrameProductDao
{

	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<MoveframeProduct> getMoveFrameProductsByPorductIdAndMfId(Map parMap)
	{
		return sqlMap.queryForList("getMoveFrameProductsByPorductIdAndMfId",parMap);
	}

	@Override
	public List<MoveframeGoods> getMoveFrameGoodsByGoodsIdAndMfId(Map parMap)
	{
		return sqlMap.queryForList("getMoveFrameGoodsByGoodsIdAndMoveFramId",parMap);
	}

	@Override
	public List<MoveframeInstance> getMoveFrameInstanceByGoodsIdAndInstanceIdAndMfId(
			Map parMap)
	{
		return sqlMap.queryForList("getMoveFrameInstanceByGoodsIdAndInstanceIdAndMfId",parMap);
	}

	@Override
	public void updateMoveFrameGoods(MoveframeGoods moveframeGoods)
	{
		sqlMap.update("updateMoveFrameGoodsByMoveId",moveframeGoods);
	}

	@Override
	public void updateMoveFrameInstance(MoveframeInstance moveframeInstance)
	{
		sqlMap.update("updateMoveFrameInstanceById",moveframeInstance);
	}

	@Override
	public void insertMoveFrameLog(MoveFrameLog moveFrameLog)
	{
		sqlMap.insert("insertMoveFrameLog",moveFrameLog);
	}

	@Override
	public List<MoveframeInstance> getMoveFrameInstanceByInstanceId(Long instanceId)
	{
		return sqlMap.queryForList("getMoveFrameInstanceByInstanceId",instanceId);
	}

	@Override
	public List<MoveframeProduct> getMoveFrameProductsByPorductId(Long productId)
	{
		return sqlMap.queryForList("getMoveFrameProductsByPorductId",productId);
	}

}
