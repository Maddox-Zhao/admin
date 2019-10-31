package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;

public class TradeSalesCount  implements Serializable{
	
	private static final long serialVersionUID = 3637717302149640516L;
	private Double             salesCount;                                     //�����û����۽��
	private Double             tradeSum;                                       //�����ܽ��
	private Double             refundSum;                                      //�˿��ܽ��
	private Long               userId;                                         //��ԱID
	
	public Double getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(Double salesCount) {
		this.salesCount = salesCount;
	}
	public Double getTradeSum() {
		return tradeSum;
	}
	public void setTradeSum(Double tradeSum) {
		this.tradeSum = tradeSum;
	}
	public Double getRefundSum() {
		return refundSum;
	}
	public void setRefundSum(Double refundSum) {
		this.refundSum = refundSum;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
