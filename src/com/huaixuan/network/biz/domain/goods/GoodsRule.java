package com.huaixuan.network.biz.domain.goods;

import com.huaixuan.network.biz.domain.BaseObject;

public class GoodsRule extends BaseObject implements Cloneable {
	private Long id;

	private Long goodsId;

	private Long returnId;

	private String status;
	
	private String ruleName;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

}
