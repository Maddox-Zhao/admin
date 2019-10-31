package com.huaixuan.network.biz.domain.stock;

 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* @ClassName: ShoppingRefund
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-23 上午10:39:24
 */
public class ShoppingRefund implements Serializable {

	private static final long serialVersionUID = 5640447932222203288L;
	/* @property: */
	 private long id;
	 /* @property: */
	 private String refNum;
	 /* @property: */
	 private long supplierId;
	 private String supplierName;
	 /* @property: */
	 private String type;
	 /* @property: */
	 private Date refTime;
	 /* @property: */
	 private String status;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private String creater;
	 /* Default constructor - creates a new instance with no values set. */
	 private String shoppingNum;

	 private String dealer;

	 private Long   depFirstId;

	 public ShoppingRefund(){}
	 public void setId(long obj){
		 this.id = obj;
	 }

	 public long getId(){
		 return this.id;
	 }
	 public void setRefNum(String obj){
		 this.refNum = obj;
	 }

	 public String getRefNum(){
		 return this.refNum;
	 }
	 public void setSupplierId(long obj){
		 this.supplierId = obj;
	 }

	 public long getSupplierId(){
		 return this.supplierId;
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
	 public void setType(String obj){
		 this.type = obj;
	 }

	 public String getType(){
		 return this.type;
	 }
	 public void setRefTime(Date obj){
		 this.refTime = obj;
	 }

	 public Date getRefTime(){
		 return this.refTime;
	 }
	 public void setStatus(String obj){
		 this.status = obj;
	 }

	 public String getStatus(){
		 return this.status;
	 }
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 public void setCreater(String obj){
		 this.creater = obj;
	 }

	 public String getCreater(){
		 return this.creater;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof ShoppingRefund)) {
			 return false;
		 }
		 final ShoppingRefund shoppingrefund = (ShoppingRefund) o;
		 return this.hashCode() == shoppingrefund.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("refNum", this.refNum)
			 .append("supplierId", this.supplierId)
			 .append("type", this.type)
			 .append("refTime", this.refTime)
			 .append("status", this.status)
			 .append("gmtCreate", this.gmtCreate)
			 .append("creater", this.creater);
		 return sb.toString();
	 }
	public String getShoppingNum() {
		return shoppingNum;
	}
	public void setShoppingNum(String shoppingNum) {
		this.shoppingNum = shoppingNum;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
    public Long getDepFirstId() {
        return depFirstId;
    }
    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

 }
