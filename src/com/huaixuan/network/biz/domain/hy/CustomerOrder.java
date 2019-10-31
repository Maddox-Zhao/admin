package com.huaixuan.network.biz.domain.hy;

 import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * 
  * 20111027  songfy
  * @version 3.2.0
  */
 public class CustomerOrder extends BaseObject implements Serializable {
 	
	private static final long serialVersionUID = -3188643482907269803L;
	/* @property: */
	 private Long idOrder;
	 /* @property: */
	 private Date date;
	 
	 private Long idCustomer;
	 
	 private int idChannel;
     
     private double subTotal;
     
     private int idCurrency;
     
     private Long operator;
     
     private Long operator2;
     
     private int idPayment;
     
     private double amountCard;
     
     private double amountCash;
     
     private int status;
	 
	 private String  remark;
	 
	 private Long idSite;

	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdChannel() {
		return idChannel;
	}

	public void setIdChannel(int idChannel) {
		this.idChannel = idChannel;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public int getIdCurrency() {
		return idCurrency;
	}

	public void setIdCurrency(int idCurrency) {
		this.idCurrency = idCurrency;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getOperator2() {
		return operator2;
	}

	public void setOperator2(Long operator2) {
		this.operator2 = operator2;
	}

	public int getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	public double getAmountCard() {
		return amountCard;
	}

	public void setAmountCard(double amountCard) {
		this.amountCard = amountCard;
	}

	public double getAmountCash() {
		return amountCash;
	}

	public void setAmountCash(double amountCash) {
		this.amountCash = amountCash;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getIdSite() {
		return idSite;
	}

	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountCard);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(amountCash);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idChannel;
		result = prime * result + idCurrency;
		result = prime * result
				+ ((idCustomer == null) ? 0 : idCustomer.hashCode());
		result = prime * result + ((idOrder == null) ? 0 : idOrder.hashCode());
		result = prime * result + idPayment;
		result = prime * result + ((idSite == null) ? 0 : idSite.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result
				+ ((operator2 == null) ? 0 : operator2.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + status;
		temp = Double.doubleToLongBits(subTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOrder other = (CustomerOrder) obj;
		if (Double.doubleToLongBits(amountCard) != Double
				.doubleToLongBits(other.amountCard))
			return false;
		if (Double.doubleToLongBits(amountCash) != Double
				.doubleToLongBits(other.amountCash))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idChannel != other.idChannel)
			return false;
		if (idCurrency != other.idCurrency)
			return false;
		if (idCustomer == null) {
			if (other.idCustomer != null)
				return false;
		} else if (!idCustomer.equals(other.idCustomer))
			return false;
		if (idOrder == null) {
			if (other.idOrder != null)
				return false;
		} else if (!idOrder.equals(other.idOrder))
			return false;
		if (idPayment != other.idPayment)
			return false;
		if (idSite == null) {
			if (other.idSite != null)
				return false;
		} else if (!idSite.equals(other.idSite))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (operator2 == null) {
			if (other.operator2 != null)
				return false;
		} else if (!operator2.equals(other.operator2))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(subTotal) != Double
				.doubleToLongBits(other.subTotal))
			return false;
		return true;
	}
	 
	 
	 
 }
