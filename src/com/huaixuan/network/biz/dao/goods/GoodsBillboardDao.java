package com.huaixuan.network.biz.dao.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.GoodsBillboard;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;

 /**
  * �����Զ����bibleUtil auto code generation)
  * @version 3.2.0
  */
 public interface GoodsBillboardDao {
 	/* @interface model: ���һ��GoodsBillboard��¼ */
 	void addGoodsBillboard(GoodsBillboard goodsBillboard) throws Exception;

 	/* @interface model: ����һ��GoodsBillboard��¼ */
 	void editGoodsBillboard(GoodsBillboard goodsBillboard) throws Exception;

 	/* @interface model: ɾ��һ��GoodsBillboard��¼ */
 	void removeGoodsBillboard(Long goodsBillboardId) throws Exception;

 	/* @interface model: ��ѯһ��GoodsBillboard�������GoodsBillboard���� */
 	GoodsBillboard getGoodsBillboard(Long goodsBillboardId) throws Exception;

 	/* @interface model: ��ѯ����GoodsBillboard�������GoodsBillboard����ļ���*/
 	List <GoodsBillboard> getGoodsBillboards() throws Exception;

 	//获取排行商品
 	List<GoodsBillboard> getRankGoodsBy(String cat,String type,Integer num,Integer numAll) throws Exception;

	boolean updateBilllboard(GoodsPoint gp,long points,int type);

	void createBillboard(GoodsPoint gp,long points,int type);
 }
