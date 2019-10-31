package com.huaixuan.network.biz.service.goods;

import com.huaixuan.network.biz.domain.goods.SumGoods;

public interface SumGoodsManager {

    void addSumGoods(Long lisingGoodsNumber,Long newLisingGoodsNum);

    void updateSumGoods(Long lisingGoodsNumber,Long newLisingGoodsNum);

    SumGoods getSumGoods();
}
