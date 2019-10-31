package com.huaixuan.network.biz.service.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.query.OrderListQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.trade.OrderManager;

@Service("orderManager")
public class OrderManagerImpl implements OrderManager {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private GoodsInstanceDao goodsInstanceDao;

	// /* @interface model: ���һ��Order��¼ */
	// public void addOrder(Order orderDao) {
	// log.info("OrderManagerImpl.addOrder method");
	// try {
	// this.orderDao.addOrder(orderDao);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }

	/* @interface model: ����һ��Order��¼ */
	public void editOrder(Order order) {
		orderDao.editOrder(order);
	}

	
	 /* @interface model: ɾ��һ��Order��¼ */
	 public void removeOrder(Long orderId) {

	 try {
	 this.orderDao.removeOrder(orderId);
	 } catch (Exception e) {
	 }
	 }

	/* @interface model: ��ѯһ��Order�����,����Order���� */
	public Order getOrder(Long orderId) {
		return this.orderDao.getOrder(orderId);
	}

	//
	// /* @interface model: ��ѯ����Order�����,����Order����ļ��� */
	// public List<Order> getOrders() {
	// log.info("OrderManagerImpl.getOrders method");
	// try {
	// return this.orderDao.getOrders();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }
	//
	 /**
	 * �������orderList��
	 *
	 * @param orderList
	 */
	 public void addOrders(List<Order> orderList) {
	 if (null == orderList || orderList.size() < 1) {
	 return;
	 }
	 this.orderDao.addOrders(orderList);
	 }
	//
	// /**
	// * ��������orderList��
	// *
	// * @param orderList
	// */
	// public void editOrders(List<Order> orderList) {
	// if (null == orderList || orderList.size() < 1) {
	// return;
	// }
	// this.orderDao.editOrders(orderList);
	// }
	//
	// /**
	// * ����ɾ��orderList��
	// *
	// * @param orderList
	// */
	// public void removeOrders(List<Order> orderList) {
	// if (null == orderList || orderList.size() < 1) {
	// return;
	// }
	// this.orderDao.removeOrders(orderList);
	// }
	//
	// /**
	// * ����ɾ��orderList����ͨ��orderList��Id����ɾ����
	// *
	// * @param orderList
	// */
	// public void removeOrdersByIdList(List<Long> orderIdList) {
	// if (null == orderIdList || orderIdList.size() < 1) {
	// return;
	// }
	// List<Order> orderList = new ArrayList<Order>();
	// for (Long orderId : orderIdList) {
	// Order orderTmp = new Order();
	// orderTmp.setId(orderId);
	// orderList.add(orderTmp);
	// }
	// this.orderDao.removeOrders(orderList);
	// }
	//
	// /**
	// * ����ɾ��orderList����ͨ�����״���tId��ɾ����
	// *
	// * @param orderList
	// */
	// public void removeOrdersByUserId(String tId) {
	// if (StringUtil.isEmpty(tId)) {
	// return;
	// }
	// Map parameterMap = new HashMap();
	// parameterMap.put("tId", tId);
	// List<Order> orderList = orderDao.getOrdersByParameterMap(parameterMap);
	// this.orderDao.removeOrders(orderList);
	// }

	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByParameterMap(Map parameterMap) {
		return orderDao.getOrdersByParameterMap(parameterMap);
	}

	public QueryPage getOrdersByParameterMapQuery(OrderListQuery query, int currPage, int pageSize) {
		return orderDao.getOrdersByParameterMapQuery(query, currPage, pageSize);
	}

	/**
	 * 
	 * <p>
	 * Title: getDepNameByMap
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param parameterMap
	 * @author chenhang 2010/12/04
	 * @return
	 * @see com.hundsun.bible.facade.trade.OrderManager#getDepNameByOutDepId
	 */
	public List<String> getDepNameByMap(Map parameterMap) {
		return orderDao.getDepNameByMap(parameterMap);
	}

	public int getOrdersByParameterMapCount(OrderListQuery query) {
		return orderDao.getOrdersByParameterMapCount(query);
	}

	//
	// /**
	// * ��ѯĳ����Ʒ������order�б�
	// *
	// * @param goodsId
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// public List<Order> getOrdersByGoodsId(Long goodsId) {
	// return orderDao.getOrdersByGoodsId(goodsId);
	// }

	@Override
	public List<Order> getOrdersNotInPackage(String tId) {
		List<Order> templist = orderDao.getOrdersNotInPackage(tId);
		if (templist != null && templist.size() > 0) {
			for (Order o : templist) {
				Goods goods = goodsDao.getGoods(o.getGoodsId());
				if (goods != null) {
					o.setImgSmall(goods.getImgSmall());
					// ǰ̨������Ʒ�����ʾ shenzh
					o.setGoodsSn(goods.getGoodsSn());
				}
				GoodsInstance goodsInstances = this.goodsInstanceDao.getInstance(o.getGoodsInstanceId());
				if (goodsInstances != null) {
					o.setGoodsInstanceCode(goodsInstances.getCode());
				}
			}
		}
		return templist;
	}

	public List<Order> getOrdersByTid(String tid) {
		return orderDao.getOrdersByTid(tid);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getOrdersByDate(StockDetailSearchQuery stockDetailSearchQuery, int currPage, int pageSize) {
		// return orderDao.getOrdersByDate(parMap, page);

		QueryPage queryPage = new QueryPage(stockDetailSearchQuery);
		Map pramas = queryPage.getParameters();

		int count = orderDao.getOrdersCountByDate(pramas);

		if (count > 0) {

			/* ��ǰҳ */
			queryPage.setCurrentPage(currPage);
			/* ÿҳ���� */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* ��ҳ��ѯ����Ա��¼ */
			List<Order> orderList = orderDao.getOrdersByDate(pramas);
			if (orderList != null && orderList.size() > 0) {
				queryPage.setItems(orderList);
			}
		}
		return queryPage;
	}
	//
	// public int getOrdersCountByDate(Map<String, String> parMap) {
	// return (Integer) orderDao.getOrdersCountByDate(parMap);
	// }

	 public Order getSalesSumByGoodsInstanceId(Long goodsInstanceId) {
	 return orderDao.getSalesSumByGoodsInstanceId(goodsInstanceId);
	 }


	@Override
	public void deleteOrderByIdProduct(Long idProduct, Long orderId)
	{
		Map parMap = new HashMap();
		parMap.put("productId", idProduct);
		parMap.put("orderId", orderId);
		orderDao.deleteOrderByIdProduct(parMap);
	}


	@Override
	public void deleteOrderByOrderId(Long orderId)
	{
		orderDao.deleteOrderByOrderId(orderId);
	}
}
