package com.huaixuan.network.biz.dao.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsWholsale;

public interface GoodsWholsaleDao {

	void addGoodsWholsale(GoodsWholsale goodsWholsale);

	List<GoodsWholsale> getGoodsWholsalelistByGoodsId(Long goodsId);

	void removeGoodswholesaleById(Long id);
}
