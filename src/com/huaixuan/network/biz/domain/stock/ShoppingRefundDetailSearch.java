package com.huaixuan.network.biz.domain.stock;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

 /**
  * �货明细查询对�(bibleUtil auto code generation)
  * @version 3.2.0
  */
/**
 * �˻���ϸ��ѯ����(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class ShoppingRefundDetailSearch implements Serializable {

	private static final long serialVersionUID = -5058723490361404002L;
	 /* @property: �˻�����*/
	 private String refundNum;
	 /* @property: ������ID*/
	 private long supplierId;
	 /* @property: ����������*/
	 private String supplierName;
	 /* @property: ��Ʒ����*/
	 private String goodsCode;
	 /* @property: ��Ʒ����*/
	 private String instanceName;
	 /* @property: �˻�ʱ��*/
	 private Date refTime;
	 /* @property: ��Ŀ*/
	 private String catCode;
	 /* @property: ����*/
	 private String attrs;
	 /* @property: ��λ*/
	 private String units;
	 /* @property: �˻�����*/
	 private double refPrice;
	 /* @property: �˻�����*/
	 private long refNum;
	 /* @property: Ӧ�ս��*/
	 private double dueFee;
	 /* @property: ʵ�ս��*/
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
	 * @param refTime Ҫ���õ� refTime
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
	 * @param attrs Ҫ���õ� attrs
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
	 * @param catCode Ҫ���õ� catCode
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
	 * @param dueFee Ҫ���õ� dueFee
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
	 * @param factFee Ҫ���õ� factFee
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
	 * @param goodsCode Ҫ���õ� goodsCode
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
	 * @param instanceName Ҫ���õ� instanceName
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
	 * @param refNum Ҫ���õ� refNum
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
	 * @param refPrice Ҫ���õ� refPrice
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
	 * @param refundNum Ҫ���õ� refundNum
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
	 * @param supplierId Ҫ���õ� supplierId
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
	 * @param supplierName Ҫ���õ� supplierName
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
	 * @param units Ҫ���õ� units
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
