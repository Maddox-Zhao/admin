package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.query.QueryPage;

public interface RefundManager {
	// /* @interface model: Refund */
	// public void addRefund(Refund refund);
	//
	// /* @interface model: Refund */
	// public void editRefund(Refund refund);
	//
	// /* @interface model: Refund */
	// public void removeRefund(Long refundId);

	/* @interface model: Refund,Refund */
	public Refund getRefund(Long refundId);

	public Refund getRefundByOrder(String tid);

	// /* @interface model: Refund,Refund */
	// public List<Refund> getRefunds();
	//
	// void updateRefundStatusAndNote(Refund refund);
	//
	// public List<Order> getGoodsListByRefundId(String refundId);

	String updateRefundStatusByRefund(Refund refund, String agreeTag);

	// public int getRefundCountByParameterMap(Map<String, String> parMap);

	List<Refund> getRefundByParameterMap(Map<String, Object> parameterMap);

	QueryPage getRefundByParameterMap(Map<String, Object> parameterMap, int currPage, int pageSize);

	public Refund getRefundByRefundId(String refundId);

	// public int getRefundCountByStatus(Map parMap);
	//
	// public List<Refund> getRefundByStatus(Map parameterMap, Page page);
	//
	// /**
	// * 判断交易状态为完成，已经产生积分，退货状态为完成，没有减去积分的退货记录
	// *
	// * @param stauts
	// * @return
	// */
	// List<Refund> getRefundByTradeStatus(String stauts, String isRefund, Long userId);

	void updateMessageForRefundByTradeId(String refundId, String message);
}
