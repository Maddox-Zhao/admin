package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsWholsaleDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsWholsale;
import com.huaixuan.network.biz.service.goods.GoodsRuleManager;
import com.huaixuan.network.biz.service.goods.GoodsWholsaleManager;

@Service("goodsWholsaleManager")
public class GoodsWholsaleManagerImpl implements GoodsWholsaleManager {
	
	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
    private GoodsWholsaleDao      goodsWholsaleDao;
	
	@Autowired
    private GoodsDao      goodsDao;

	public void addGoodsWholsale(GoodsWholsale goodsWholsale) {
        log.info("GoodsWholsaleManagerImpl.addGoodsWholsale method");
        this.goodsWholsaleDao.addGoodsWholsale(goodsWholsale);
	}

	public List<GoodsWholsale> getGoodsWholsalelistByGoodsId(Long goodsId) {
		log.info("GoodsWholsaleManagerImpl.getGoodsWholsalelistByGoodsId method");
		try{
			Goods goods = goodsDao.getGoods(goodsId);
			List<GoodsWholsale> list = goodsWholsaleDao.getGoodsWholsalelistByGoodsId(goodsId);
			for(GoodsWholsale tmp:list){
				tmp.setGoodsUnit(goods.getGoodsUnit());
			}
			return list;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return null;
	}

	public void removeGoodswholesaleById(Long id) {
		log.info("GoodsWholsaleManagerImpl.removeGoodswholesaleById method");
		this.goodsWholsaleDao.removeGoodswholesaleById(id);
	}
}
