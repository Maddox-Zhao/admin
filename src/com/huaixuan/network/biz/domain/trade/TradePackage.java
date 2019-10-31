package com.huaixuan.network.biz.domain.trade;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class TradePackage extends BaseObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	 private Long id;
	 /* @property: */
	 private String tid;
	 /* @property: */
	 private String mergeTid;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 private Double actualInventory;

	 private String receiver;

	 private Long   expressId;

	 private Double   goodsWeight;

	 public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Double getActualInventory() {
		return actualInventory;
	}
	public void setActualInventory(Double actualInventory) {
		this.actualInventory = actualInventory;
	}
	public TradePackage(){}
	 /* @model: */
	 public void setId(Long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public Long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setTid(String obj){
		 this.tid = obj;
	 }

	public Double getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	/* @model: */
	 public String getTid(){
		 return this.tid;
	 }
	 /* @model:¨² */
	 public void setMergeTid(String obj){
		 this.mergeTid = obj;
	 }

	 /* @model: */
	 public String getMergeTid(){
		 return this.mergeTid;
	 }
	 /* @model: */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model: */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 public Long getExpressId() {
		return expressId;
	}
	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}
	/* @model: */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model: */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
 }
