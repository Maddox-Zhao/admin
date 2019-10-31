package com.huaixuan.network.biz.dao.active;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeGoods;
import com.huaixuan.network.biz.domain.active.MoveframeInstance;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;

public interface MoveFrameProductDao
{
	/**
	 * 通过ProductId得到对应的活动Product信息
	 * @param productId
	 * @return
	 */
	public List<MoveframeProduct> getMoveFrameProductsByPorductIdAndMfId(Map parMap);
	
	public List<MoveframeProduct> getMoveFrameProductsByPorductId(Long productId);
	
	
	/**
	 * 通过goodsId和活动框id得到MoveframeGoods信息
	 * @param parMap
	 * @return
	 */
	public List<MoveframeGoods> getMoveFrameGoodsByGoodsIdAndMfId(Map parMap);
	
	/**
	 * 通过GoodsId和instanceId和活动框ID得到对应的MoveframeInstance产品信息
	 * @param parMap
	 * @return
	 */
	public List<MoveframeInstance> getMoveFrameInstanceByGoodsIdAndInstanceIdAndMfId(Map parMap);
	
	
	/**
	 * 通过instanceId得到MoveframeInstance
	 * @param instanceId
	 * @return
	 */
	public List<MoveframeInstance> getMoveFrameInstanceByInstanceId(Long instanceId);
	
	
	public void updateMoveFrameGoods(MoveframeGoods moveframeGoods);
	
	
	public void updateMoveFrameInstance(MoveframeInstance moveframeInstance);
	
	/**
	 * 产品售出后往MoveFrame_Log表里面插入一条数据，用于发邮件
	 * @param moveFrameLog
	 */
	public void insertMoveFrameLog(MoveFrameLog moveFrameLog);
	
	
}
