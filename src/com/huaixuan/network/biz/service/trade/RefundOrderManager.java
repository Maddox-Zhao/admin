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

	// ����refundId ɾ�����е�RefundOrder
	public void removeRefundOrderByRefund(String refundId);

	// ����refundId �������е�RefundOrder
	List<RefundOrder> getRefundOrderByRefundId(String refundId);

	// ����tid�����е�RefundOrder
}
