package com.huaixuan.network.biz.dao.storage;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.ProdRelationOut;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public interface ProdRelationOutDao  {
 	/* @interface model: ProdRelationOut */
 	long addProdRelationOut(ProdRelationOut prodRelationOut) throws Exception;

 	/* @interface model: ProdRelationOut */
 	void editProdRelationOut(ProdRelationOut prodRelationOut) throws Exception;

 	/* @interface model: ProdRelationOut */
 	void removeProdRelationOut(Long prodRelationOutId) throws Exception;

 	/* @interface model: ProdRelationOut,ProdRelationOut */
 	ProdRelationOut getProdRelationOut(Long prodRelationOutId) throws Exception;

 	/* @interface model: ProdRelationOut,ProdRelationOut */
 	List <ProdRelationOut> getProdRelationOuts() throws Exception;

 	/**
 	 * 得到打印出库单结果集合
 	 * @param outDepId
 	 * @return
 	 */
 	List<ProdRelationOut> getPrintList(Long outDepId) throws Exception;

 	/**
 	 * 刪除数据用于重新添加
 	 * @param outDepId Long
 	 * @param goodsInstanceId Long
 	 * @param outDetailId Long
 	 * @param goodsId Long
 	 * @author chenyan 2009/07/29
 	 */
 	void removeProdRelationOutForAdd(Long outDepId, Long goodsInstanceId, Long outDetailId, Long goodsId);

 	/**
 	 * 取得数量和库存表ID
 	 * @param outDepId Long
 	 * @param goodsInstanceId Long
 	 * @param goodsId Long
 	 * @param outDetailId Long
 	 * @return List
 	 * @author chenyan 2009/07/30
 	 */
 	List<ProdRelationOut> getAmountAndStorageId(Long outDepId, Long goodsInstanceId, Long goodsId, Long outDetailId);

 	/**
 	 * 取得已分配过的产品数量
 	 * @param map Map
 	 * @return Long
 	 * @author chenyan 2009/08/02
 	 */
 	Long getDistributedAmount(Map map);
 }
