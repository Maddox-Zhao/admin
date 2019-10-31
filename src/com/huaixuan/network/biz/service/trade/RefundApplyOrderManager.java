package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.RefundApplyOrder;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RefundApplyOrderManager {
	public void addRefundApplyOrder(RefundApplyOrder refundApplyOrder);

	public void editRefundApplyOrder(RefundApplyOrder refundApplyOrder);

	public void removeRefundApplyOrder(Long refundApplyOrderId);

	public RefundApplyOrder getRefundApplyOrder(Long refundApplyOrderId);

	public List<RefundApplyOrder> getRefundApplyOrders();
}
