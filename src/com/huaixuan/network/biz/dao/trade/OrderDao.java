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
	 * �������orderList��
	 * @param orderList
	 */
	 void addOrders(List<Order> orderList);
	//
	// /**
	// * ��������orderList��
	// * @param orderList
	// */
	// void editOrders(List<Order> orderList);
	//
	// /**
	// * ����ɾ��orderList��
	// * @param orderList
	// */
	// void removeOrders(List<Order> orderList);

	/**
	 * ��ѯ���ϲ�����ParameterMapҪ���Order�����
	 *
	 * @param parameterMap
	 *            ������
	 * @return ���ϲ�����ParameterMapҪ���Order�����
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
	// * ��ѯĳ����Ʒ������order�б�
	// * @param goodsId
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// List<Order> getOrdersByGoodsId(Long goodsId);

	/**
	 * ���ݽ��׺ŵõ��ý��׵ķ��ײ���Ʒ
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
	// * ����ID������Ʒ�۸�
	// * @param goodsPrice Double
	// * @param id Long
	// * @author chenyan 2009/09/14
	// */
	// void updateGoodsPriceById(Double goodsPrice, Long id);
	//
	int getOrdersCountByDate(Map<String, String> parMap);

	 /**
	 * ������ƷId��ȡ��������
	 * @param goodsInstanceId
	 * @return
	 * @author zhangwy
	 */
	 Order getSalesSumByGoodsInstanceId(Long goodsInstanceId);
	 
	 /**
	  * ͨ��idProduct��orderIdɾ���������е�����
	  * @param parMap
	  */
	 void deleteOrderByIdProduct(Map parMap);
	 
	 /**
	  * ͨ��OrderIdɾ��������Product
	  * @param orderId
	  */
	 void deleteOrderByOrderId(Long orderId);
}
