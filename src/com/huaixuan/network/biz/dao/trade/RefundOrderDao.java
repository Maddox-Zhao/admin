package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.RefundOrder;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RefundOrderDao {
	void addRefundOrder(RefundOrder refundOrder);

	void editRefundOrder(RefundOrder refundOrder);

	void removeRefundOrder(Long refundOrderId);

	RefundOrder getRefundOrder(Long refundOrderId);

	void addRefundOrders(List<RefundOrder> RefundOrder);

	List<RefundOrder> getRefundOrders();

	// 根究refundId 删除所有的RefundOrder
	void removeRefundOrderByRefund(String refundId);

	// 根究refundId 返回所有的RefundOrder
	List<RefundOrder> getRefundOrderByRefundId(String refundId);

}
