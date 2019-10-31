package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.RefundApplyOrderDao;
import com.huaixuan.network.biz.domain.trade.RefundApplyOrder;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
@Service("refundApplyOrderDao")
public class RefundApplyOrderDaoiBatis implements RefundApplyOrderDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public void addRefundApplyOrder(RefundApplyOrder refundApplyOrder) {
		sqlMapClientTemplate.insert("addRefundApplyOrder", refundApplyOrder);
	}

	public void editRefundApplyOrder(RefundApplyOrder refundApplyOrder) {
		sqlMapClientTemplate.update("editRefundApplyOrder", refundApplyOrder);
	}

	public void removeRefundApplyOrder(Long refundApplyOrderId) {
		sqlMapClientTemplate.delete("removeRefundApplyOrder", refundApplyOrderId);
	}

	public RefundApplyOrder getRefundApplyOrder(Long refundApplyOrderId) {
		return (RefundApplyOrder) sqlMapClientTemplate.queryForObject("getRefundApplyOrder", refundApplyOrderId);
	}

	public List<RefundApplyOrder> getRefundApplyOrders() {
		return sqlMapClientTemplate.queryForList("getRefundApplyOrders", null);
	}

	public List<RefundApplyOrder> getRefundApplyOrdersByApplyId(Long applyId) {
		return sqlMapClientTemplate.queryForList("getRefundApplyOrdersByApplyId", applyId);
	}

}
