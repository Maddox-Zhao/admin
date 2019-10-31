package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.query.OrderListQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface OrderManager {
	// /* @interface model: ���һ��Order��¼ */
	// public void addOrder(Order order);

	/* @interface model: ����һ��Order��¼ */
	public void editOrder(Order order);

	
	 /* @interface model: ɾ��һ��Order��¼ */
	 public void removeOrder(Long orderId);

	/* @interface model: ��ѯһ��Order�����,����Order���� */
	public Order getOrder(Long orderId);

	//
	// /* @interface model: ��ѯ����Order�����,����Order����ļ��� */
	// public List<Order> getOrders();
	//
	 /**
	 * �������orderList��
	 *
	 * @param orderList
	 */
	 public void addOrders(List<Order> orderList);
	//
	// /**
	// * ��������orderList��
	// *
	// * @param orderList
	// */
	// public void editOrders(List<Order> orderList);
	//
	// /**
	// * ����ɾ��orderList��
	// *
	// * @param orderList
	// */
	// public void removeOrders(List<Order> orderList);
	//
	// /**
	// * ����ɾ��orderList����ͨ��orderList��Id����ɾ����
	// *
	// * @param orderList
	// */
	// public void removeOrdersByIdList(List<Long> orderIdList);
	//
	// /**
	// * ����ɾ��orderList����ͨ�����״���tId��ɾ����
	// *
	// * @param orderList
	// */
	// public void removeOrdersByUserId(String tId);

	/**
	 * ��ѯ���ϲ�����ParameterMapҪ���Order�����
	 * 
	 * @param parameterMap
	 *            ������
	 * @return ���ϲ�����ParameterMapҪ���Order�����
	 */
	public List<Order> getOrdersByParameterMap(Map parameterMap);

	public QueryPage getOrdersByParameterMapQuery(OrderListQuery query, int currPage, int pageSize);

	/**
	 * 
	 * @Title: getDepNameByMap ȡ�ó��ⵥ����Ʒ�����Ĳֿ���
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
	// * ��ѯĳ����Ʒ������order�б�
	// *
	// * @param goodsId
	// * @return
	// */
	// public List<Order> getOrdersByGoodsId(Long goodsId);

	/**
	 * ���ݽ��׺ŵõ��ý��׵ķ��ײ���Ʒ
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
	 * ������ƷId��ȡ��������
	 *
	 * @param goodsInstanceId
	 * @return
	 * @author zhangwy
	 */
	 public Order getSalesSumByGoodsInstanceId(Long goodsInstanceId);
	 
	 
	 /**
	  * ͨ��idProduct��orderIdɾ���������е�����
	  * @param parMap
	  */
	 public void deleteOrderByIdProduct(Long idProduct,Long orderId);
	 
	 /**
	  * ͨ��OrderIdɾ��������Product
	  * @param orderId
	  */
	 public void deleteOrderByOrderId(Long orderId);
}
