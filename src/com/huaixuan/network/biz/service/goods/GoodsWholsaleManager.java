package com.huaixuan.network.biz.service.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsWholsale;

public interface GoodsWholsaleManager {

	public void addGoodsWholsale(GoodsWholsale goodsWholsale);
	
	/**
	 * ������Ʒ����ȡ�����ȼ�
	 * @param goodsId
	 * @return
	 */
	public List<GoodsWholsale> getGoodsWholsalelistByGoodsId(Long goodsId);
	
	/**
	 * ������Ʒ�����ȼ�idɾ���õȼ�
	 * @param id
	 */
	public void removeGoodswholesaleById(Long id);
}
