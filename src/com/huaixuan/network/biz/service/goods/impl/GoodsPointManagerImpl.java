package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsPointDao;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;
import com.huaixuan.network.biz.service.goods.GoodsPointManager;

@Service("goodsPointManager")
public class GoodsPointManagerImpl  implements GoodsPointManager {

    @Autowired
    private GoodsPointDao goodsPointDao;

    protected Log  log = LogFactory.getLog(this.getClass());
    
    @Override
    public void addGoodsPoint(GoodsPoint goodsPointDao) {
        log.info("GoodsPointManagerImpl.addGoodsPoint method");
        try {
             this.goodsPointDao.addGoodsPoint(goodsPointDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editGoodsPoint(GoodsPoint goodsPoint) {
        log.info("GoodsPointManagerImpl.editGoodsPoint method");
        try {
            this.goodsPointDao.editGoodsPoint(goodsPoint);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void removeGoodsPoint(Long goodsPointId) {
        log.info("GoodsPointManagerImpl.removeGoodsPoint method");
        try {
            this.goodsPointDao.removeGoodsPoint(goodsPointId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public GoodsPoint getGoodsPoint(Long goodsPointId) {
        log.info("GoodsPointManagerImpl.getGoodsPoint method");
        try {
            return this.goodsPointDao.getGoodsPoint(goodsPointId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GoodsPoint> getGoodsPoints() {
        log.info("GoodsPointManagerImpl.getGoodsPoints method");
        try {
            return this.goodsPointDao.getGoodsPoints();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     *  取得商品某天的排行分数
     * @param goodsId
     * @param pointDate "yyyy-MM-dd"
     * @return
     * @see com.hundsun.bible.facade.goods.GoodsPointManager#getGPByGoodsIdAndPointDate(java.lang.Long, java.util.Date)
     */
    @Override
    public GoodsPoint getGPByGoodsIdAndPointDate(Long goodsId,String pointDate) {
        log.info("GoodsPointManagerImpl.getGoodsPoint method");
        try {
            return this.goodsPointDao.getGPByGoodsIdAndPointDate(goodsId, pointDate);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
