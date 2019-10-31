package com.huaixuan.network.biz.service.goods.impl;

 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsAttrDao;
import com.huaixuan.network.biz.domain.goods.GoodsAttr;
import com.huaixuan.network.biz.service.goods.GoodsAttrManager;

@Service("goodsAttrManager")
 public class GoodsAttrManagerImpl implements GoodsAttrManager {

	protected Log  log = LogFactory.getLog(this.getClass());
	
 	/* @model: */
 	public GoodsAttrDao goodsAttrDao;

 	/* @model: */
 	public void addGoodsAttr(GoodsAttr goodsAttrDao) {
 		log.info("GoodsAttrManagerImpl.addGoodsAttr method");
 		try {
 			 this.goodsAttrDao.addGoodsAttr(goodsAttrDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editGoodsAttr(GoodsAttr goodsAttr) {
 		log.info("GoodsAttrManagerImpl.editGoodsAttr method");
 		try {
 			this.goodsAttrDao.editGoodsAttr(goodsAttr);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removeGoodsAttr(Long goodsAttrId) {
 		log.info("GoodsAttrManagerImpl.removeGoodsAttr method");
 		try {
 			this.goodsAttrDao.removeGoodsAttr(goodsAttrId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public GoodsAttr getGoodsAttr(Long goodsAttrId) {
 		log.info("GoodsAttrManagerImpl.getGoodsAttr method");
 		try {
 			return this.goodsAttrDao.getGoodsAttr(goodsAttrId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	/* @model: */
 	public List<GoodsAttr> getGoodsAttrs() {
 		log.info("GoodsAttrManagerImpl.getGoodsAttrs method");
 		try {
 			return this.goodsAttrDao.getGoodsAttrs();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

	public GoodsAttr getGoodsAttrByGoodsIdAndAttrId(Long goodsId,
			Long goodsAttrId) {
		  if (null == goodsId || null==goodsAttrId) {
	            return null;
	        }
	        Map parameterMap = new HashMap();
	        parameterMap.put("goodsId", goodsId);
	        parameterMap.put("goodsAttrId", goodsAttrId);
	        GoodsAttr goodsAttr = goodsAttrDao
	            .getGoodsAttrByGoodsIdAndAttrId(parameterMap);
	        if (null == goodsAttr) {
	            return null;
	        }
	        return goodsAttr;
	}
	
	/**
	 * 根据物品id返回List<GoodsAttr>
	 * @param goodsId
	 * @return
	 * @throws Exception
	 * @see com.hundsun.bible.facade.goods.GoodsAttrManager#getGoodsAttrByGoodSId(java.lang.Long)
	 */
	public List<GoodsAttr> getGoodsAttrByGoodSId(Long goodsId) throws Exception{
	    if (null == goodsId) {
            return null;
        }
	    List<GoodsAttr> goodsAttrList = goodsAttrDao.getGoodsAttrByGoodSId(goodsId);
        if (null == goodsAttrList) {
            return null;
        }
        return goodsAttrList;
	}

	public GoodsAttr getGoodsAttrByMap(Map parMap) {
		return goodsAttrDao.getGoodsAttrByMap(parMap);
	}
 }
