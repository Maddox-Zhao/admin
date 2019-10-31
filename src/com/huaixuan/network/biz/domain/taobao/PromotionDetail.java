package com.huaixuan.network.biz.domain.taobao;

public class PromotionDetail{

	//
	private static final long serialVersionUID = 7964963017855436490L;

	/** 交易或子订单ID */
	private Long id;

	/**
	 * 优惠名称
	 */
	private String promotionName;

	/**
	 * 优惠金额（免运费、限时打折时�,单位：元
	 */
	private String discountFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
}
