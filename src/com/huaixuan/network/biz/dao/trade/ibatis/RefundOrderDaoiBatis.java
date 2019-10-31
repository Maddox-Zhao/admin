package com.huaixuan.network.biz.dao.trade.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.RefundOrderDao;
import com.huaixuan.network.biz.domain.trade.RefundOrder;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @version 3.2.0
 */
@Repository("refundOrderDao")
public class RefundOrderDaoiBatis implements RefundOrderDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
/* @model: */
	public void addRefundOrder(RefundOrder refundOrder) {
		sqlMapClientTemplate.insert("addRefundOrder", refundOrder);
	}
/* @model: */
	public void editRefundOrder(RefundOrder refundOrder) {
		sqlMapClientTemplate.update("editRefundOrder", refundOrder);
	}
/* @model: */
	public void removeRefundOrder(Long refundOrderId) {
		sqlMapClientTemplate.delete("removeRefundOrder", refundOrderId);
	}
/* @model: */
	public RefundOrder getRefundOrder(Long refundOrderId) {
		return (RefundOrder) sqlMapClientTemplate.queryForObject("getRefundOrder", refundOrderId);
	}
/* @model: */
	public List<RefundOrder> getRefundOrders() {
		return sqlMapClientTemplate.queryForList("getRefundOrders", null);
	}

	public void removeRefundOrderByRefund(String refundId) {
		sqlMapClientTemplate.delete("removeRefundOrderByRefund", refundId);
	}

	public List<RefundOrder> getRefundOrderByRefundId(String refundId) {
		return sqlMapClientTemplate.queryForList("getRefundOrderByRefundId", refundId);
	}

	public void addRefundOrders(List<RefundOrder> RefundOrder) {
		this.batch(RefundOrder, "add");
	}

	/**
	 * 批量操作，主要是添加，更新，删除
	 * 
	 * @param list
	 *            要操作的对象集
	 * @param executeType
	 *            添加（add），修改（edit），删除（remove）
	 */
	@SuppressWarnings("unused")
	private void batch(final List<RefundOrder> list, final String executeType) {
		sqlMapClientTemplate.execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				if (executeType.equals("add")) {
					for (int i = 0; i < list.size(); i++) {
						RefundOrder o = list.get(i);
						executor.insert("addRefundOrder", o);
					}
				}
				// if (executeType.equals("edit"))
				// for (int i = 0; i < list.size(); i++) {
				// OutDetail o = list.get(i);
				// executor.update("editOutDetail", o);
				// }
				// if (executeType.equals("remove"))
				// for (int i = 0; i < list.size(); i++) {
				// Long o = list.get(i).getId();
				// executor.delete("removeOutDetail", o);
				// }
				executor.executeBatch();
				return null;
			}
		});
		return;
	}
}
