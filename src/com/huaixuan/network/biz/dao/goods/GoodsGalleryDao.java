package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsGallery;


public interface GoodsGalleryDao {
    /* @interface model: GoodsGallery */
    void addGoodsGallery(GoodsGallery goodsGallery) throws Exception;

    /* @interface model: GoodsGallery */
    void editGoodsGallery(GoodsGallery goodsGallery) throws Exception;

    /* @interface model: GoodsGallery */
    void removeGoodsGallery(Long goodsGalleryId) throws Exception;

    /* @interface model: GoodsGallery,GoodsGallery */
    GoodsGallery getGoodsGallery(Long goodsGalleryId) throws Exception;



    /* @interface model: GoodsGallery,GoodsGallery */
    List<GoodsGallery> getGoodsGallerys() throws Exception;

    /**
     * ����parameterMap������ѯGoodsGallery�����
     * @param parameterMap
     * @return
     */
    @SuppressWarnings("unchecked")
    List<GoodsGallery> getGoodsGallerysByParameterMap(Map parameterMap);

    /**
     * ��ѯ��ǰgoodsId������sortֵ
     * @param goodsId
     * @return
     */
    int getMaxSortNumByGoodsId(Long goodsId);

    /**
     * ɾ��ʱ��������
     * @param goodsGallery
     * @throws Exception
     */
    void updateGoodsGallerysSortByGoodsId(GoodsGallery goodsGallery)throws Exception;

 	void goodsGallerysUpSamll(Long goodsId,int sort)throws Exception;

 	void goodsGallerysUpBig(Long goodsGalleryId)throws Exception;

 	void goodsGallerysDownSamll(Long goodsId,int sort)throws Exception;

 	void goodsGallerysDownBig(Long goodsGalleryId)throws Exception;
}
