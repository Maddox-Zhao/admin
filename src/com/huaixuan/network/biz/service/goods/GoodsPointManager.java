package com.huaixuan.network.biz.service.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsPoint;

public interface GoodsPointManager {

 	public void addGoodsPoint(GoodsPoint goodsPoint);

 	public void editGoodsPoint(GoodsPoint goodsPoint);

 	public void removeGoodsPoint(Long goodsPointId);

 	public GoodsPoint getGoodsPoint(Long goodsPointId);

 	public List<GoodsPoint> getGoodsPoints();

    /**
     * ȡ����Ʒĳ������з��� 
     * @param goodsId
     * @param pointDate "yyyy-MM-dd"
     * @return
     */
    public GoodsPoint getGPByGoodsIdAndPointDate(Long goodsId,String pointDate);
 }
