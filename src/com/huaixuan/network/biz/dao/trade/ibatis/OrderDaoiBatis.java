package com.huaixuan.network.biz.dao.trade.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.query.OrderListQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.ibatis.sqlmap.client.SqlMapExecutor;

@Service("orderDao")
public class OrderDaoiBatis implements OrderDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	/* @model: */
	public void addOrder(Order order) {
		sqlMapClientTemplate.insert("addOrder", order);
	}

	/* @model: */
	public void editOrder(Order order) {
		sqlMapClientTemplate.update("editOrder", order);
	}

	//
	/* @model: */
	 public void removeOrder(Long orderId) {
	 sqlMapClientTemplate.delete("removeOrder", orderId);
	 }

	/* @model: */
	public Order getOrder(Long orderId) {
		return (Order) sqlMapClientTemplate.queryForObject("getOrder", orderId);
	}

	//
	/* @model: */
	// public List<Order> getOrders() {
	// return sqlMapClientTemplate.queryForList("getOrders", null);
	// }
	//
	 /**
	 * 批量添加orderList集
	 *
	 * @param orderList
	 */
	 public void addOrders(final List<Order> orderList) {
	 sqlMapClientTemplate.execute(new SqlMapClientCallback() {
         public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
             executor.startBatch();
                 for (int i = 0; i < orderList.size(); i++) {
                     Order o = orderList.get(i);
                     executor.insert("addOrder", o);
                 }
             executor.executeBatch();
             return null;
         }
     });
     return;
	 }
	//
	// /**
	// * 批量更新orderList集
	// *
	// * @param orderList
	// */
	// public void editOrders(List<Order> orderList) {
	// this.batch(orderList, "edit");
	// }
	//
	// /**
	// * 批量删除orderList集
	// *
	// * @param orderList
	// */
	// public void removeOrders(List<Order> orderList) {
	// this.batch(orderList, "remove");
	// }

	@Override
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByParameterMap(Map parameterMap) {
		return sqlMapClientTemplate.queryForList("getOrdersByParameterMap", parameterMap);
	}

	public QueryPage getOrdersByParameterMapQuery(OrderListQuery query, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(query);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getOrdersByParameterMapCount", query);
		queryPage.setTotalItem(count);

		if (count > 0) {
			query.setStartRow(queryPage.getPageFristItem());
			query.setEndRow(queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			queryPage.setItems(sqlMapClientTemplate.queryForList("getOrdersByParameterMapQuery", query));
		}

		return queryPage;
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
	 * @return
	 * @see com.hundsun.bible.dao.OrderDao#getDepNameByOutDepId
	 */
	public List<String> getDepNameByMap(Map parameterMap) {
		return sqlMapClientTemplate.queryForList("getDepNameByMap", parameterMap);
	}

	public int getOrdersByParameterMapCount(OrderListQuery query) {
		Integer i = (Integer) sqlMapClientTemplate.queryForObject("getOrdersByParameterMapCount", query);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	//
	// /**
	// * 批量操作，主要是添加，更新，删除
	// *
	// * @param list
	// * 要操作的对象集
	// * @param executeType
	// * 添加（add），修改（edit），删除（remove）
	// */
	// @SuppressWarnings("unused")
	// private void batch(final List<Order> list, final String executeType) {
	// getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
	// public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	// executor.startBatch();
	// if (executeType.equals("add"))
	// for (int i = 0; i < list.size(); i++) {
	// Order o = list.get(i);
	// executor.insert("addOrder", o);
	// }
	// if (executeType.equals("edit"))
	// for (int i = 0; i < list.size(); i++) {
	// Order o = list.get(i);
	// executor.update("editOrder", o);
	// }
	// if (executeType.equals("remove"))
	// for (int i = 0; i < list.size(); i++) {
	// Long o = list.get(i).getId();
	// executor.delete("removeOrder", o);
	// }
	// executor.executeBatch();
	// return null;
	// }
	// });
	// return;
	// }
	//
	// /**
	// * 查询某个商品的所有order列表
	// *
	// * @param goodsId
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// public List<Order> getOrdersByGoodsId(Long goodsId) {
	// return sqlMapClientTemplate.queryForList("getOrdersByGoodsId", goodsId);
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersNotInPackage(String tId) {
		return sqlMapClientTemplate.queryForList("getOrdersNotInPackage", tId);
	}

	/**
	 * 根据TID得到相应的orders
	 *
	 * @param tId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrdersByTid(String tId) {
		return sqlMapClientTemplate.queryForList("getOrdersListByTid", tId);
	}

	//
	public List<Order> getOrdersByDate(Map<String, String> parMap) {
		return sqlMapClientTemplate.queryForList("getOrdersByDate", parMap);
	}

	//
	public int getOrdersCountByDate(Map<String, String> parMap) {
		return (Integer) sqlMapClientTemplate.queryForObject("getOrdersCountByDate", parMap);
	}
	//
	// public List<Order> getOrdersListByRefundId(Map<String, Object> pramas) {
	// return sqlMapClientTemplate.queryForList("getOrdersListByRefundId", pramas);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public void updateGoodsPriceById(Double goodsPrice, Long id) {
	// Map map = new HashMap();
	// map.put("goodsPrice", goodsPrice);
	// map.put("id", id);
	// sqlMapClientTemplate.update("updateGoodsPriceById", map);
	// }

	 public Order getSalesSumByGoodsInstanceId(Long goodsInstanceId) {
	 return (Order) sqlMapClientTemplate.queryForObject("getSalesSumByGoodsInstanceId", goodsInstanceId);
	 }

	@Override
	public void deleteOrderByIdProduct(Map parMap)
	{
		sqlMapClientTemplate.delete("deleteOrderByIdProduct",parMap);
	}

	@Override
	public void deleteOrderByOrderId(Long orderId)
	{
		sqlMapClientTemplate.delete("deleteOrderByOrderId",orderId);
	}
}
