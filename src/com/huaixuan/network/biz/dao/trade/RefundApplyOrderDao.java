package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.RefundApplyOrder;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RefundApplyOrderDao {
	void addRefundApplyOrder(RefundApplyOrder refundApplyOrder);

	void editRefundApplyOrder(RefundApplyOrder refundApplyOrder);

	void removeRefundApplyOrder(Long refundApplyOrderId);

	RefundApplyOrder getRefundApplyOrder(Long refundApplyOrderId);

	List<RefundApplyOrder> getRefundApplyOrders();

	List<RefundApplyOrder> getRefundApplyOrdersByApplyId(Long applyId);
}
