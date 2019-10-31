package com.huaixuan.network.biz.service.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsGallery;


 public interface GoodsGalleryManager {
	 	/* @interface model: GoodsGallery */
	 	public void addGoodsGallery(GoodsGallery goodsGallery);

	 	/* @interface model: GoodsGallery */
	 	public void editGoodsGallery(GoodsGallery goodsGallery);

	 	/* @interface model: GoodsGallery */
	 	public void removeGoodsGallery(Long goodsGalleryId);

	 	/* @interface model: GoodsGallery,GoodsGallery */
	 	public GoodsGallery getGoodsGallery(Long goodsGalleryId);

	 	/* @interface model: GoodsGallery,GoodsGallery */
	 	public List<GoodsGallery> getGoodsGallerys();
	 	/**
	 	 * ������Ʒid��ѯ����Ʒ������ͼƬ
	 	 * @param goodsId
	 	 * @return
	 	 */
	 	public List<GoodsGallery> getGoodsGallerysByGoodsId(Long goodsId);


	 	/* @interface model: ����ID���� */
	 	void goodsGalleryUp(Long goodsGalleryId)throws Exception;

	 	/* @interface model: ����ID�½� */
	 	void goodsGalleryDown(Long goodsGalleryId)throws Exception;
 }
