package com.huaixuan.network.biz.dao.express;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.query.QueryPage;

 /**
  * @version 3.2.0
  */
 public interface ExpressDistDao {
 	/* @interface model: 锟斤拷锟揭伙拷锟紼xpressDist锟斤拷录 */
 	void addExpressDist(ExpressDist expressDist) throws Exception;

 	/* @interface model: 锟斤拷锟斤拷一锟斤拷ExpressDist锟斤拷录 */
 	void editExpressDist(ExpressDist expressDist) throws Exception;

 	/* @interface model: 删锟斤拷一锟斤拷ExpressDist锟斤拷录 */
 	void removeExpressDist(Long expressDistId) throws Exception;

 	ExpressDist getExpressDist(Long expressDistId) throws Exception;

 	List <ExpressDist> getExpressDists() throws Exception;

 	/**
 	 * 取得物流范围信息
 	 * @param expressDistId
 	 * @return ExpressDist
 	 * @author chenyan 2009/08/07
 	 */
 	ExpressDist getExpressDistById(Long expressDistId);

 	/**
 	 * 根据物流ID，起始地和目的地唯一确定一条记录，进行删除操作
 	 * @param expressId Long
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @return int
 	 * @author chenyan 2009/08/10
 	 */
 	int removeExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd);

 	/**
 	 * 检索物流范围总数
 	 * @param map Map
 	 * @return int
 	 * @author chenyan 2009/08/11
 	 */
 	int getExpressDistCountByCond(Map map);

 	/**
 	 * 检索物流范围信息
 	 * @param map Map
 	 * @param page Page
 	 * @return QueryPage
 	 * @author chenyan 2009/08/11
 	 */
 	QueryPage getExpressDistByCond(Map parMap, int currentPage, final int pageSize);

 	/**
 	 * 检索出用于下订单时候的物流信息列表
 	 * @return List
 	 * @author chenyan 2009/08/20
 	 */
 	List<ExpressDist> listExpressDistForTrade();

 	/**
 	 * 根据物流ID，起始地和目的地和支付方式取得记录条数
 	 * @param expressId Long
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @param payment String
 	 * @return int
 	 * @author chenyan 2009/08/27
 	 */
 	int getExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd, String payment);

 	/**
 	 * 根据目的地和支付方式取得记录条数
 	 * @param regionCodeEnd String
 	 * @param payment String
 	 * @return int
 	 * @author chenyan 2009/09/04
 	 */
 	int getCountByRegionCodeEnd(String regionCodeEnd, String payment);

 	/**
 	 * 根据目的地和支付方式取得记录信息
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @param payment String
 	  @param expressId Long
 	 * @return List
 	 * @author chenyan 2009/09/04 modified by chenyan 2009/09/07 modified by chenyan 2011/04/11
 	 */
 	List<ExpressDist> listExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd, String payment, Long expressId);

 	/**
 	 * 取得待更新的数据是否地址重复
 	 * @param regionCodeStart String
 	 * @param regionCodeEnd String
 	 * @param expressId Long
 	 * @param id Long
 	 * @param payment String
 	 * @return int
 	 * @author chenyan 2009/09/08
 	 */
 	int getCountByFourForUpdate(String regionCodeStart, String regionCodeEnd, Long expressId, Long id, String payment);

 	/**
 	 * 根据物流ID，起始地，目的地，支付方式进行更新物流范围
 	 * @param expressDist ExpressDist
 	 * @return int
 	 * @author chenyan 2009/11/10
 	 */
 	int editExpressDistByFourCond(ExpressDist expressDist);

 	/**
 	 * 合并订单的时候重新找原有订单的物流
 	 * @param regionCodeStart
 	 * @param regionCodeEnd
 	 * @param payment
 	 * @param expressId
 	 * @return
 	 * @author zhangwy
 	 */
 	ExpressDist getExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd, String payment, Long expressId);

 	/**
 	 * 批量更新物流范围状态
 	 * @param ids
 	 * @param status
 	 * @return
 	 */
 	public int bathUpdateStatus(List<Long> expressDistIds,String status);
 }
