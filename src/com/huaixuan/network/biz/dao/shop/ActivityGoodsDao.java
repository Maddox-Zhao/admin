package com.huaixuan.network.biz.dao.shop;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.ActivityGoods;

 /**
  * �����Զ����(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public interface ActivityGoodsDao  {
 	/* @interface model: ���һ��ActivityGoods��¼ */
 	void addActivityGoods(ActivityGoods activityGoods) throws Exception;

 	/* @interface model: ����һ��ActivityGoods��¼ */
 	void editActivityGoods(ActivityGoods activityGoods) throws Exception;

 	void editActivityGoodsByMap(Map parMap) throws Exception;

 	/* @interface model: ɾ��һ��ActivityGoods��¼ */
 	void removeActivityGoods(Long activityGoodsId) throws Exception;

 	/* @interface model: ��ѯһ��ActivityGoods���,����ActivityGoods���� */
 	ActivityGoods getActivityGoods(Long activityGoodsId) throws Exception;

 	/* @interface model: ��ѯ����ActivityGoods���,����ActivityGoods����ļ��� */
 	List <ActivityGoods> getActivityGoodssByMap(Map parMap) throws Exception;


 	void removeByActivityId(Long activityId) throws Exception;

 	List <ActivityGoods> getByActivityId(Long activityId) throws Exception;

 	List <ActivityGoods> getCategoryByActivityId(Long activityId) throws Exception;


 }
