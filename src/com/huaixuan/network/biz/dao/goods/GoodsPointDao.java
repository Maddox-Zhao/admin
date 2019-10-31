package com.huaixuan.network.biz.dao.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsPoint;

public interface GoodsPointDao {

 	void addGoodsPoint(GoodsPoint goodsPoint) throws Exception;

 	void editGoodsPoint(GoodsPoint goodsPoint) throws Exception;

 	void removeGoodsPoint(Long goodsPointId) throws Exception;

 	GoodsPoint getGoodsPoint(Long goodsPointId) throws Exception;

 	List <GoodsPoint> getGoodsPoints() throws Exception;

	List<GoodsPoint> getHotSalePoints(String dateString);

	List<GoodsPoint> getHighPopularPoint(String dateString);

    /**
     * 取得商品某天的排行分数 
     * @param goodsId
     * @param pointDate "yyyy-MM-dd"
     * @return
     */
    GoodsPoint getGPByGoodsIdAndPointDate(Long goodsId,String pointDate);
 }
