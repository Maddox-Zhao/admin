package com.huaixuan.network.biz.service.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsWholsale;

public interface GoodsWholsaleManager {

	public void addGoodsWholsale(GoodsWholsale goodsWholsale);
	
	/**
	 * 根据商品编码取批发等级
	 * @param goodsId
	 * @return
	 */
	public List<GoodsWholsale> getGoodsWholsalelistByGoodsId(Long goodsId);
	
	/**
	 * 根据商品批发等级id删除该等级
	 * @param id
	 */
	public void removeGoodswholesaleById(Long id);
}
