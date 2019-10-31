package com.huaixuan.network.biz.service.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsBillboard;

 /**
  * �����Զ����bibleUtil auto code generation)
  * @version 3.2.0
  */
 public interface GoodsBillboardManager {
 	/* @interface model: ���һ��GoodsBillboard��¼ */
 	public void addGoodsBillboard(GoodsBillboard goodsBillboard);

 	/* @interface model: ����һ��GoodsBillboard��¼ */
 	public void editGoodsBillboard(GoodsBillboard goodsBillboard);

 	/* @interface model: ɾ��һ��GoodsBillboard��¼ */
 	public void removeGoodsBillboard(Long goodsBillboardId);

 	/* @interface model: ��ѯһ��GoodsBillboard�������GoodsBillboard���� */
 	public GoodsBillboard getGoodsBillboard(Long goodsBillboardId);

 	/* @interface model: ��ѯ����GoodsBillboard�������GoodsBillboard����ļ���*/
 	public List<GoodsBillboard> getGoodsBillboards();

 	public void buildHotSalePoints();

 	public void buildHighPopularPoints();
 }
