package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;

public class ShoppingRefundDSearch implements Serializable {

	private static final long serialVersionUID = -678041172267388627L;

	private String shoppingNum;

	private String id;

	private String goods_id;

	private String amount;

	private String unit_price;

	private String goods_instance_id;

	private String code;

	private String way_num;

	private String ref_num;

	private String ref_price;

	private String due_fee;

	private String fact_fee;

	private String reason;

	public String getShoppingNum() {
		return shoppingNum;
	}

	public void setShoppingNum(String shoppingNum) {
		this.shoppingNum = shoppingNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
	}

	public String getGoods_instance_id() {
		return goods_instance_id;
	}

	public void setGoods_instance_id(String goods_instance_id) {
		this.goods_instance_id = goods_instance_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWay_num() {
		return way_num;
	}

	public void setWay_num(String way_num) {
		this.way_num = way_num;
	}

	public String getDue_fee() {
		return due_fee;
	}

	public void setDue_fee(String due_fee) {
		this.due_fee = due_fee;
	}

	public String getRef_num() {
		return ref_num;
	}

	public void setRef_num(String ref_num) {
		this.ref_num = ref_num;
	}

	public String getRef_price() {
		return ref_price;
	}

	public void setRef_price(String ref_price) {
		this.ref_price = ref_price;
	}

	public String getFact_fee() {
		return fact_fee;
	}

	public void setFact_fee(String fact_fee) {
		this.fact_fee = fact_fee;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
