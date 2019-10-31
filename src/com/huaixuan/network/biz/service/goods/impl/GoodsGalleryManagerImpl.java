package com.huaixuan.network.biz.service.goods.impl;

 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsGalleryDao;
import com.huaixuan.network.biz.domain.goods.GoodsGallery;
import com.huaixuan.network.biz.service.goods.GoodsGalleryManager;


@Service("goodsGalleryManager")
 public class GoodsGalleryManagerImpl implements GoodsGalleryManager {

	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
 	GoodsGalleryDao goodsGalleryDao;

 	/* @model: */
 	public void addGoodsGallery(GoodsGallery goodsGalleryDao) {
 		log.info("GoodsGalleryManagerImpl.addGoodsGallery method");
 		try {
 			 int maxSort = this.goodsGalleryDao.getMaxSortNumByGoodsId(goodsGalleryDao.getGoodsId());
 			 goodsGalleryDao.setSort(maxSort+1);//插入时设置排序
 			 this.goodsGalleryDao.addGoodsGallery(goodsGalleryDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editGoodsGallery(GoodsGallery goodsGallery) {
 		log.info("GoodsGalleryManagerImpl.editGoodsGallery method");
 		try {
 			this.goodsGalleryDao.editGoodsGallery(goodsGallery);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removeGoodsGallery(Long goodsGalleryId) {
 		log.info("GoodsGalleryManagerImpl.removeGoodsGallery method");
 		try {
 			GoodsGallery goodsGallery = this.goodsGalleryDao.getGoodsGallery(goodsGalleryId);
 			if(goodsGallery!=null){
 				this.goodsGalleryDao.updateGoodsGallerysSortByGoodsId(goodsGallery);
 			}
 			this.goodsGalleryDao.removeGoodsGallery(goodsGalleryId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public GoodsGallery getGoodsGallery(Long goodsGalleryId) {
 		log.info("GoodsGalleryManagerImpl.getGoodsGallery method");
 		try {
 			return this.goodsGalleryDao.getGoodsGallery(goodsGalleryId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	/* @model: */
 	public List<GoodsGallery> getGoodsGallerys() {
 		log.info("GoodsGalleryManagerImpl.getGoodsGallerys method");
 		try {
 			return this.goodsGalleryDao.getGoodsGallerys();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}
 	/**
     * 根据商品id查询该商品的所有图片
     * @param goodsId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<GoodsGallery> getGoodsGallerysByGoodsId(Long goodsId){
        Map parameterMap = new HashMap();
        parameterMap.put("goodsId", goodsId);
        return this.goodsGalleryDao.getGoodsGallerysByParameterMap(parameterMap);
    }

	public void goodsGalleryDown(Long goodsGalleryId) throws Exception {
		log.info("GoodsGalleryManagerImpl.goodsGalleryDown method");

		GoodsGallery goodsGallery = this.goodsGalleryDao.getGoodsGallery(goodsGalleryId);
		int sort = goodsGallery.getSort();
		this.goodsGalleryDao.goodsGallerysDownSamll(goodsGallery.getGoodsId(), sort+1);
		this.goodsGalleryDao.goodsGallerysDownBig(goodsGalleryId);
	}

	public void goodsGalleryUp(Long goodsGalleryId) throws Exception {
		log.info("GoodsGalleryManagerImpl.goodsGalleryUp method");

		GoodsGallery goodsGallery = this.goodsGalleryDao.getGoodsGallery(goodsGalleryId);
		int sort = goodsGallery.getSort();
		this.goodsGalleryDao.goodsGallerysUpSamll(goodsGallery.getGoodsId(), sort-1);
		this.goodsGalleryDao.goodsGallerysUpBig(goodsGalleryId);

	}
 }
