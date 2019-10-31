package com.huaixuan.network.biz.dao.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.SumGoods;

public interface SumGoodsDao {

    void addSumGoods(SumGoods sumGoods);

    void updateSumGoods(SumGoods sumGoods);

    List<SumGoods> getSumGoodslist();
}
