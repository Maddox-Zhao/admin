package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.query.OrderListQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface OrderManager {
	// /* @interface model: 添加一条Order记录 */
	// public void addOrder(Order order);

	/* @interface model: 更新一条Order记录 */
	public void editOrder(Order order);

	
	 /* @interface model: 删除一条Order记录 */
	 public void removeOrder(Long orderId);

	/* @interface model: 查询一个Order结果集,返回Order对象 */
	public Order getOrder(Long orderId);

	//
	// /* @interface model: 查询所有Order结果集,返回Order对象的集合 */
	// public List<Order> getOrders();
	//
	 /**
	 * 批量添加orderList集
	 *
	 * @param orderList
	 */
	 public void addOrders(List<Order> orderList);
	//
	// /**
	// * 批量更新orderList集
	// *
	// * @param orderList
	// */
	// public void editOrders(List<Order> orderList);
	//
	// /**
	// * 批量删除orderList集
	// *
	// * @param orderList
	// */
	// public void removeOrders(List<Order> orderList);
	//
	// /**
	// * 批量删除orderList集，通过orderList的Id集来删除。
	// *
	// * @param orderList
	// */
	// public void removeOrdersByIdList(List<Long> orderIdList);
	//
	// /**
	// * 批量删除orderList集，通过交易代码tId来删除。
	// *
	// * @param orderList
	// */
	// public void removeOrdersByUserId(String tId);

	/**
	 * 查询符合参数集ParameterMap要求的Order结果集
	 * 
	 * @param parameterMap
	 *            参数集
	 * @return 符合参数集ParameterMap要求的Order结果集
	 */
	public List<Order> getOrdersByParameterMap(Map parameterMap);

	public QueryPage getOrdersByParameterMapQuery(OrderListQuery query, int currPage, int pageSize);

	/**
	 * 
	 * @Title: getDepNameByMap 取得出库单中商品所出的仓库名
	 * @Description: TODO
	 * @param @param parameterMap
	 * @param @return
	 * @author chenhang 2010/12/04
	 * @return List<String>
	 * @throws
	 */
	public List<String> getDepNameByMap(Map parameterMap);

	public int getOrdersByParameterMapCount(OrderListQuery query);

	//
	// /**
	// * 查询某个商品的所有order列表
	// *
	// * @param goodsId
	// * @return
	// */
	// public List<Order> getOrdersByGoodsId(Long goodsId);

	/**
	 * 根据交易号得到该交易的非套餐商品
	 * 
	 * @param tId
	 * @return
	 */
	public List<Order> getOrdersNotInPackage(String tId);

	public List<Order> getOrdersByTid(String tId);

	public QueryPage getOrdersByDate(StockDetailSearchQuery stockDetailSearchQuery, int currPage, int pageSize);
	//
	// public int getOrdersCountByDate(Map<String, String> parMap);

	 /**
	 * 根据商品Id获取销售数量
	 *
	 * @param goodsInstanceId
	 * @return
	 * @author zhangwy
	 */
	 public Order getSalesSumByGoodsInstanceId(Long goodsInstanceId);
	 
	 
	 /**
	  * 通过idProduct和orderId删除关联表中的数据
	  * @param parMap
	  */
	 public void deleteOrderByIdProduct(Long idProduct,Long orderId);
	 
	 /**
	  * 通过OrderId删除关联的Product
	  * @param orderId
	  */
	 public void deleteOrderByOrderId(Long orderId);
}
