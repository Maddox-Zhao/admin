package com.huaixuan.network.biz.domain.stock;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

 /**
  * 閫璐ф槑缁嗘煡璇㈠璞(bibleUtil auto code generation)
  * @version 3.2.0
  */
/**
 * 退货明细查询对象(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class ShoppingRefundDetailSearch implements Serializable {

	private static final long serialVersionUID = -5058723490361404002L;
	 /* @property: 退货单号*/
	 private String refundNum;
	 /* @property: 供货商ID*/
	 private long supplierId;
	 /* @property: 供货商名称*/
	 private String supplierName;
	 /* @property: 产品编码*/
	 private String goodsCode;
	 /* @property: 产品名称*/
	 private String instanceName;
	 /* @property: 退货时间*/
	 private Date refTime;
	 /* @property: 类目*/
	 private String catCode;
	 /* @property: 属性*/
	 private String attrs;
	 /* @property: 单位*/
	 private String units;
	 /* @property: 退货单价*/
	 private double refPrice;
	 /* @property: 退货数量*/
	 private long refNum;
	 /* @property: 应收金额*/
	 private double dueFee;
	 /* @property: 实收金额*/
	 private double factFee;

	 private String storType;

	 private Long  depFirstId;


	/**
	 * @return refTime
	 */
	public Date getRefTime() {
		return refTime;
	}
	/**
	 * @param refTime 要设置的 refTime
	 */
	public void setRefTime(Date refTime) {
		this.refTime = refTime;
	}
	/**
	 * @return attrs
	 */
	public String getAttrs() {
		return attrs;
	}
	/**
	 * @param attrs 要设置的 attrs
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
	 * @param catCode 要设置的 catCode
	 */
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	/**
	 * @return dueFee
	 */
	public double getDueFee() {
		return dueFee;
	}
	/**
	 * @param dueFee 要设置的 dueFee
	 */
	public void setDueFee(double dueFee) {
		this.dueFee = dueFee;
	}
	/**
	 * @return factFee
	 */
	public double getFactFee() {
		return factFee;
	}
	/**
	 * @param factFee 要设置的 factFee
	 */
	public void setFactFee(double factFee) {
		this.factFee = factFee;
	}
	/**
	 * @return goodsCode
	 */
	public String getGoodsCode() {
		return goodsCode;
	}
	/**
	 * @param goodsCode 要设置的 goodsCode
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	/**
	 * @return instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}
	/**
	 * @param instanceName 要设置的 instanceName
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	/**
	 * @return refNum
	 */
	public long getRefNum() {
		return refNum;
	}
	/**
	 * @param refNum 要设置的 refNum
	 */
	public void setRefNum(long refNum) {
		this.refNum = refNum;
	}
	/**
	 * @return refPrice
	 */
	public double getRefPrice() {
		return refPrice;
	}
	/**
	 * @param refPrice 要设置的 refPrice
	 */
	public void setRefPrice(double refPrice) {
		this.refPrice = refPrice;
	}
	/**
	 * @return refundNum
	 */
	public String getRefundNum() {
		return refundNum;
	}
	/**
	 * @param refundNum 要设置的 refundNum
	 */
	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}
	/**
	 * @return supplierId
	 */
	public long getSupplierId() {
		return supplierId;
	}
	/**
	 * @param supplierId 要设置的 supplierId
	 */
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * @return supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName 要设置的 supplierName
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return units
	 */
	public String getUnits() {
		return units;
	}
	/**
	 * @param units 要设置的 units
	 */
	public void setUnits(String units) {
		this.units = units;
	}
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof ShoppingRefundDetailSearch)) {
			 return false;
		 }
		 final ShoppingRefundDetailSearch shoppingdetail = (ShoppingRefundDetailSearch) o;
		 return this.hashCode() == shoppingdetail.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("refundNum", this.refundNum)
			 .append("supplierId", this.supplierId)
			 .append("supplierName", this.supplierName)
			 .append("goodsCode", this.goodsCode)
			 .append("instanceName", this.instanceName)
			 .append("catCode", this.catCode)
			 .append("attrs", this.attrs)
			 .append("units", this.units)
			 .append("refPrice", this.refPrice)
			 .append("refNum", this.refNum)
			 .append("dueFee", this.dueFee)
			 .append("factFee", this.factFee);
		 return sb.toString();
	 }
   public String getStorType() {
       return storType;
   }
   public void setStorType(String storType) {
       this.storType = storType;
   }
   public Long getDepFirstId() {
       return depFirstId;
   }
   public void setDepFirstId(Long depFirstId) {
       this.depFirstId = depFirstId;
   }

}
