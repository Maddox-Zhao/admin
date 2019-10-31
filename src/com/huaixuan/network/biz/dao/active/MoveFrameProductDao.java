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
	 * ͨ��ProductId�õ���Ӧ�ĻProduct��Ϣ
	 * @param productId
	 * @return
	 */
	public List<MoveframeProduct> getMoveFrameProductsByPorductIdAndMfId(Map parMap);
	
	public List<MoveframeProduct> getMoveFrameProductsByPorductId(Long productId);
	
	
	/**
	 * ͨ��goodsId�ͻ��id�õ�MoveframeGoods��Ϣ
	 * @param parMap
	 * @return
	 */
	public List<MoveframeGoods> getMoveFrameGoodsByGoodsIdAndMfId(Map parMap);
	
	/**
	 * ͨ��GoodsId��instanceId�ͻ��ID�õ���Ӧ��MoveframeInstance��Ʒ��Ϣ
	 * @param parMap
	 * @return
	 */
	public List<MoveframeInstance> getMoveFrameInstanceByGoodsIdAndInstanceIdAndMfId(Map parMap);
	
	
	/**
	 * ͨ��instanceId�õ�MoveframeInstance
	 * @param instanceId
	 * @return
	 */
	public List<MoveframeInstance> getMoveFrameInstanceByInstanceId(Long instanceId);
	
	
	public void updateMoveFrameGoods(MoveframeGoods moveframeGoods);
	
	
	public void updateMoveFrameInstance(MoveframeInstance moveframeInstance);
	
	/**
	 * ��Ʒ�۳�����MoveFrame_Log���������һ�����ݣ����ڷ��ʼ�
	 * @param moveFrameLog
	 */
	public void insertMoveFrameLog(MoveFrameLog moveFrameLog);
	
	
}
