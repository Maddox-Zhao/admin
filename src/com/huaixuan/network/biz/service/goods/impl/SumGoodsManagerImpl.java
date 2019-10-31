package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.SumGoodsDao;
import com.huaixuan.network.biz.domain.goods.SumGoods;
import com.huaixuan.network.biz.service.goods.SumGoodsManager;

@Service("sumGoodsManager")
public class SumGoodsManagerImpl implements SumGoodsManager {

    @Autowired
    private SumGoodsDao sumGoodsDao;

    @Override
    public void addSumGoods(Long lisingGoodsNumber, Long newLisingGoodsNum) {
        SumGoods sumGoods = new SumGoods();
        sumGoods.setLisingGoodsNumber(lisingGoodsNumber);
        sumGoods.setNewLisingGoodsNum(newLisingGoodsNum);
        sumGoodsDao.addSumGoods(sumGoods);
    }

    @Override
    public void updateSumGoods(Long lisingGoodsNumber, Long newLisingGoodsNum) {
        SumGoods sumGoods = new SumGoods();
        sumGoods.setLisingGoodsNumber(lisingGoodsNumber);
        sumGoods.setNewLisingGoodsNum(newLisingGoodsNum);
        sumGoodsDao.updateSumGoods(sumGoods);
    }

    @Override
    public SumGoods getSumGoods() {
        List<SumGoods> list = sumGoodsDao.getSumGoodslist();
        if (list.size() > 0) {
            SumGoods sumGoods = list.get(0);
            return sumGoods;
        }
        return null;
    }
}
