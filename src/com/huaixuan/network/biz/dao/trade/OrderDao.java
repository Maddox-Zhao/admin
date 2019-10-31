package com.huaixuan.network.biz.dao.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.query.OrderListQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;

public interface OrderDao {
	/* @interface model: Order */
	void addOrder(Order order);

	/* @interface model: Order */
	void editOrder(Order order);

	//
	// /* @interface model: Order */
	 void removeOrder(Long orderId) ;

	/* @interface model: Order,Order */
	Order getOrder(Long orderId);

	//
	// /* @interface model: Order,Order */
	// List<Order> getOrders() ;
	//
	 /**
	 * 批量添加orderList集
	 * @param orderList
	 */
	 void addOrders(List<Order> orderList);
	//
	// /**
	// * 批量更新orderList集
	// * @param orderList
	// */
	// void editOrders(List<Order> orderList);
	//
	// /**
	// * 批量删除orderList集
	// * @param orderList
	// */
	// void removeOrders(List<Order> orderList);

	/**
	 * 查询符合参数集ParameterMap要求的Order结果集
	 *
	 * @param parameterMap
	 *            参数集
	 * @return 符合参数集ParameterMap要求的Order结果集
	 */
	@SuppressWarnings("unchecked")
	List<Order> getOrdersByParameterMap(Map parameterMap);

	QueryPage getOrdersByParameterMapQuery(OrderListQuery query, int currPage, int pageSize);

	/**
	 *
	 * @Title: getDepNameByMap
	 * @Description: TODO
	 * @param @param parameterMap
	 * @author chenhang 2010/12/04
	 * @param @return
	 * @return List<String>
	 * @throws
	 */
	List<String> getDepNameByMap(Map parameterMap);

	int getOrdersByParameterMapCount(OrderListQuery query);

	//
	// /**
	// * 查询某个商品的所有order列表
	// * @param goodsId
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// List<Order> getOrdersByGoodsId(Long goodsId);

	/**
	 * 根据交易号得到该交易的非套餐商品
	 *
	 * @param tId
	 * @return
	 */
	List<Order> getOrdersNotInPackage(String tId);

	// List<Order> getOrdersListByRefundId(Map<String, Object> pramas);

	public List<Order> getOrdersByTid(String tId);

	//
	public List<Order> getOrdersByDate(Map<String, String> parMap);

	//
	// /**
	// * 根据ID更新商品价格
	// * @param goodsPrice Double
	// * @param id Long
	// * @author chenyan 2009/09/14
	// */
	// void updateGoodsPriceById(Double goodsPrice, Long id);
	//
	int getOrdersCountByDate(Map<String, String> parMap);

	 /**
	 * 根据商品Id获取销售数量
	 * @param goodsInstanceId
	 * @return
	 * @author zhangwy
	 */
	 Order getSalesSumByGoodsInstanceId(Long goodsInstanceId);
	 
	 /**
	  * 通过idProduct和orderId删除关联表中的数据
	  * @param parMap
	  */
	 void deleteOrderByIdProduct(Map parMap);
	 
	 /**
	  * 通过OrderId删除关联的Product
	  * @param orderId
	  */
	 void deleteOrderByOrderId(Long orderId);
}
