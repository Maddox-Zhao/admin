package com.huaixuan.network.biz.domain.stock;

 import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* @ClassName: ShoppingRefundDetail
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-23 上午10:39:36
 */
public class ShoppingRefundDetail implements Serializable {

	private static final long serialVersionUID = 1L;
		/* @property: */
	 private Long id;
	 /* @property: */
	 private Long goodsId;
	 /* @property: */
	 private Long goodsInstanceId;
	 /* @property: */
	 private Long locId;
	 /* @property: */
	 private Long shopRefId;
	 /* @property: */
	 private Long shoppingId;

	 private Long shoppingDetailId;

	 /* @property: */
	 private String goodsCode;
	 /* @property: */
	 private Double refPrice;
	 /* @property: */
	 private Long refNum;

	 /* @property: */
	 private Double dueFee;
	 /* @property: */
	 private Double factFee;
	 /* @property: */
	 private String reason;
	 /* @property: */
	 private String remark;

	 private String units;

	 private Long damagedNum;

	 private String code;//产品编码

	 private String instanceName;//产品名称

	 private Long amount;//采购数量

	 private Double unitPrice;//单价

	 private String catCode;//
	 private String attrDesc;
	 private String attrs;

	 private String batchNum;//入库批次�

	 private String shoppingNum;//采购订单�

	 /* Default constructor - creates a new instance with no values set. */
//	 public ShoppingRefundDetail(){}
//
//
//	/*{@inheritDoc}*/
//	 public boolean equals(Object o) {
//		 if (this == o) {
//			 return true;
//		 }
//		 if (!(o instanceof ShoppingRefundDetail)) {
//			 return false;
//		 }
//		 final ShoppingRefundDetail shoppingrefunddetail = (ShoppingRefundDetail) o;
//		 return this.hashCode() == shoppingrefunddetail.hashCode();
//	 }
//	/*{@inheritDoc}*/
//	 public int hashCode() {
//		 return this.hashCode();
//	 }
//	/*{@inheritDoc}*/
//	 public String toString() {
//		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
//			 .append("id", this.id)
//			 .append("goodsId", this.goodsId)
//			 .append("goodsInstanceId", this.goodsInstanceId)
//			 .append("locId", this.locId)
//			 .append("shopRefId", this.shopRefId)
//			 .append("shoppingDetailId", this.shoppingDetailId)
//			 .append("goodsCode", this.goodsCode)
//			 .append("refPrice", this.refPrice)
//			 .append("refNum", this.refNum)
//			 .append("dueFee", this.dueFee)
//			 .append("factFee", this.factFee)
//			 .append("reason", this.reason)
//			 .append("remark", this.remark)
//			 .append("damagedNum",this.damagedNum)
//			 .append("units",this.units);
//		 return sb.toString();
//	 }


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


	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}


	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}


	public Long getLocId() {
		return locId;
	}


	public void setLocId(Long locId) {
		this.locId = locId;
	}


	public Long getShopRefId() {
		return shopRefId;
	}


	public void setShopRefId(Long shopRefId) {
		this.shopRefId = shopRefId;
	}


	public Long getShoppingId() {
		return shoppingId;
	}


	public void setShoppingId(Long shoppingId) {
		this.shoppingId = shoppingId;
	}


	public Long getShoppingDetailId() {
		return shoppingDetailId;
	}


	public void setShoppingDetailId(Long shoppingDetailId) {
		this.shoppingDetailId = shoppingDetailId;
	}


	public String getGoodsCode() {
		return goodsCode;
	}


	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}


	public Double getRefPrice() {
		return refPrice;
	}


	public void setRefPrice(Double refPrice) {
		this.refPrice = refPrice;
	}


	public Long getRefNum() {
		if(null==refNum)
			return new Long(0);
		return refNum;
	}


	public void setRefNum(Long refNum) {
		this.refNum = refNum;
	}


	public Double getDueFee() {
		return dueFee;
	}


	public void setDueFee(Double dueFee) {
		this.dueFee = dueFee;
	}


	public Double getFactFee() {
		return factFee;
	}


	public void setFactFee(Double factFee) {
		this.factFee = factFee;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getUnits() {
		return units;
	}


	public void setUnits(String units) {
		this.units = units;
	}


	public Long getDamagedNum() {
		if(null==damagedNum)
			return new Long(0);
		return damagedNum;
	}


	public void setDamagedNum(Long damagedNum) {
		this.damagedNum = damagedNum;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getInstanceName() {
		return instanceName;
	}


	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public Double getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getCatCode() {
		return catCode;
	}


	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}


	public String getAttrDesc() {
		return attrDesc;
	}


	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}


	public String getAttrs() {
		return attrs;
	}


	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}


	public String getBatchNum() {
		return batchNum;
	}


	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getShoppingNum() {
		return shoppingNum;
	}

	public void setShoppingNum(String shoppingNum) {
		this.shoppingNum = shoppingNum;
	}



 }
