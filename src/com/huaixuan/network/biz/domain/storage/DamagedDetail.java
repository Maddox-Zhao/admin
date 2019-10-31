package com.huaixuan.network.biz.domain.storage;

 import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
 public class DamagedDetail extends BaseObject {
 		 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private long goodsId;
	 /* @property: */
	 private long goodsInstanceId;
	 /* @property: */
	 private long damagedId;
	 /* @property: */
	 private String goodsName;
	 /* @property: */
	 private String supplierId;
	 /* @property: */
	 private String unit;
	 /* @property: */
	 private long amount;
	 /* @property: */
	 private double unitCost;
	 /* @property: */
	 private double costCount;
	 /* @property: */
	 private String remark;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* @property: */
	 private long locId;
	 /* @property: */
	 private String reason;
	 /* @property: 批次 */
	 private String batchNum;

	 /* Default constructor - creates a new instance with no values set. */
	 public DamagedDetail(){}

	/**
	 * @return batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum 要设置的 batchNum
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	/* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setGoodsId(long obj){
		 this.goodsId = obj;
	 }

	 /* @model: */
	 public long getGoodsId(){
		 return this.goodsId;
	 }
	 /* @model: */
	 public void setGoodsInstanceId(long obj){
		 this.goodsInstanceId = obj;
	 }

	 /* @model: */
	 public long getGoodsInstanceId(){
		 return this.goodsInstanceId;
	 }
	 /* @model: */
	 public void setDamagedId(long obj){
		 this.damagedId = obj;
	 }

	 /* @model: */
	 public long getDamagedId(){
		 return this.damagedId;
	 }
	 /* @model: */
	 public void setGoodsName(String obj){
		 this.goodsName = obj;
	 }

	 /* @model: */
	 public String getGoodsName(){
		 return this.goodsName;
	 }
	 /* @model: */
	 public void setSupplierId(String obj){
		 this.supplierId = obj;
	 }

	 /* @model: */
	 public String getSupplierId(){
		 return this.supplierId;
	 }
	 /* @model: */
	 public void setUnit(String obj){
		 this.unit = obj;
	 }

	 /* @model: */
	 public String getUnit(){
		 return this.unit;
	 }

	 /**
	 * @return amount
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * @param amount 要设置的 amount
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

	/* @model: */
	 public void setUnitCost(double obj){
		 this.unitCost = obj;
	 }

	 /* @model: */
	 public double getUnitCost(){
		 return this.unitCost;
	 }
	 /* @model: */
	 public void setCostCount(double obj){
		 this.costCount = obj;
	 }

	 /* @model: */
	 public double getCostCount(){
		 return this.costCount;
	 }
	 /* @model: */
	 public void setRemark(String obj){
		 this.remark = obj;
	 }

	 /* @model: */
	 public String getRemark(){
		 return this.remark;
	 }
	 /* @model: */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model: */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model: */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model: */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	 /* @model: */
	 public void setLocId(long obj){
		 this.locId = obj;
	 }

	 /* @model: */
	 public long getLocId(){
		 return this.locId;
	 }
	 /* @model: */
	 public void setReason(String obj){
		 this.reason = obj;
	 }

	 /* @model: */
	 public String getReason(){
		 return this.reason;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof DamagedDetail)) {
			 return false;
		 }
		 final DamagedDetail damageddetail = (DamagedDetail) o;
		 return this.hashCode() == damageddetail.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("goodsId", this.goodsId)
			 .append("goodsInstanceId", this.goodsInstanceId)
			 .append("damagedId", this.damagedId)
			 .append("goodsName", this.goodsName)
			 .append("supplierId", this.supplierId)
			 .append("unit", this.unit)
			 .append("amount", this.amount)
			 .append("unitCost", this.unitCost)
			 .append("costCount", this.costCount)
			 .append("remark", this.remark)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify)
			 .append("locId", this.locId)
			 .append("reason", this.reason);
		 return sb.toString();
	 }
 }
