package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.RefundOrder;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RefundOrderManager {
	/* @interface model: RefundOrder */
	public void addRefundOrder(RefundOrder refundOrder);

	/* @interface model: RefundOrder */
	public void editRefundOrder(RefundOrder refundOrder);

	/* @interface model: RefundOrder */
	public void removeRefundOrder(Long refundOrderId);

	/* @interface model: RefundOrder,RefundOrder */
	public RefundOrder getRefundOrder(Long refundOrderId);

	/* @interface model: RefundOrder,RefundOrder */
	public List<RefundOrder> getRefundOrders();

	// 根据refundId 删除所有的RefundOrder
	public void removeRefundOrderByRefund(String refundId);

	// 根据refundId 返回所有的RefundOrder
	List<RefundOrder> getRefundOrderByRefundId(String refundId);

	// 根据tid回所有的RefundOrder
}
