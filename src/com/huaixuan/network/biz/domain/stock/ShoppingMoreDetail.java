package com.huaixuan.network.biz.domain.stock;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

 /**
  * ??????????(bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class ShoppingMoreDetail implements Serializable {

	 private static final long serialVersionUID = 1L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private long shoppingId;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private long goodsInstanceId;
	 /* @property: */
	 private String units;
	 /* @property: */
	 private long amount;
	 /* @property: */
	 private double unitPrice;
	 /* @property: */
	 private double dueFee;
	 /* @property: */
	 private double factFee;
	 /* @property: */
	 private long rejectNum;
	 /* @property: */
	 private long missingNum;
	 /* @property: */
	 private long receiveNum;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;

	 /* @property: 产品实例编码*/
	 private String goodsInstanceCode;
	 /* @property: ????????????*/
	 private String supplierCode;
	 /* @property: ?????????*/
	 private String instanceName;
	 /* @property: ??????*/
	 private String catCode;
	 /* @property: ???????*/
	 private String attrs;

	 private String supplierName;

	 /* Default constructor - creates a new instance with no values set. */
	 public ShoppingMoreDetail(){}


	 /**
	 * @return goodsInstanceCode
	 */
	public String getGoodsInstanceCode() {
		return goodsInstanceCode;
	}


	/**
	 * @param goodsInstanceCode 要设置的 goodsInstanceCode
	 */
	public void setGoodsInstanceCode(String goodsInstanceCode) {
		this.goodsInstanceCode = goodsInstanceCode;
	}


	/* @model:???? */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model:??? */
	 public long getId(){
		 return this.id;
	 }
	 /* @model:???? */
	 public void setShoppingId(long obj){
		 this.shoppingId = obj;
	 }

	 /* @model:??? */
	 public long getShoppingId(){
		 return this.shoppingId;
	 }
	 /* @model:???? */
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model:??? */
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model:???? */
	 public void setGoodsInstanceId(long obj){
		 this.goodsInstanceId = obj;
	 }

	 /* @model:??? */
	 public long getGoodsInstanceId(){
		 return this.goodsInstanceId;
	 }
	 /* @model:???? */
	 public void setUnits(String obj){
		 this.units = obj;
	 }

	 /* @model:??? */
	 public String getUnits(){
		 return this.units;
	 }
	 /* @model:???? */
	 public void setAmount(long obj){
		 this.amount = obj;
	 }

	 /* @model:??? */
	 public long getAmount(){
		 return this.amount;
	 }
	 /* @model:???? */
	 public void setUnitPrice(double obj){
		 this.unitPrice = obj;
	 }

	 /* @model:??? */
	 public double getUnitPrice(){
		 return this.unitPrice;
	 }
	 /* @model:???? */
	 public void setDueFee(double obj){
		 this.dueFee = obj;
	 }

	 /* @model:??? */
	 public double getDueFee(){
		 return this.dueFee;
	 }
	 /* @model:???? */
	 public void setFactFee(double obj){
		 this.factFee = obj;
	 }

	 /* @model:??? */
	 public double getFactFee(){
		 return this.factFee;
	 }
	 /* @model:???? */
	 public void setRejectNum(long obj){
		 this.rejectNum = obj;
	 }

	 /* @model:??? */
	 public long getRejectNum(){
		 return this.rejectNum;
	 }
	 /* @model:???? */
	 public void setMissingNum(long obj){
		 this.missingNum = obj;
	 }

	 /* @model:??? */
	 public long getMissingNum(){
		 return this.missingNum;
	 }
	 /* @model:???? */
	 public void setReceiveNum(long obj){
		 this.receiveNum = obj;
	 }

	 /* @model:??? */
	 public long getReceiveNum(){
		 return this.receiveNum;
	 }
	 /* @model:???? */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:??? */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:???? */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:??? */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }

	/**
	 * @return attrs
	 */
	public String getAttrs() {
		return attrs;
	}
	/**
	 * @param attrs ?????? attrs
	 */
	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}
	/**
	 * @return catCode
	 */
	public String getCatCode() {
		return catCode;
	}
	/**
	 * @param catCode ?????? catCode
	 */
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	/**
	 * @return instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}
	/**
	 * @param instanceName ?????? instanceName
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	/**
	 * @return supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}
	/**
	 * @param supplierCode ?????? supplierCode
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/**
	 * @return supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName ?????? supplierName
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof ShoppingMoreDetail)) {
			 return false;
		 }
		 final ShoppingMoreDetail shoppingdetail = (ShoppingMoreDetail) o;
		 return this.hashCode() == shoppingdetail.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 final int prime = 31;
	        int result = 1;
	        result = prime * result + ((units == null) ? 0 : units.hashCode());
	        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
	        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());

	     return result;
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("shoppingId", this.shoppingId)
			 .append("goodsId", this.goodsId)
			 .append("goodsInstanceId", this.goodsInstanceId)
			 .append("units", this.units)
			 .append("amount", this.amount)
			 .append("unitPrice", this.unitPrice)
			 .append("dueFee", this.dueFee)
			 .append("factFee", this.factFee)
			 .append("rejectNum", this.rejectNum)
			 .append("missingNum", this.missingNum)
			 .append("receiveNum", this.receiveNum)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }

 }
