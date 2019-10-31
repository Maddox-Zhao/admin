package com.huaixuan.network.biz.domain.hy;

 import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * 
  * 20111027  songfy
  * @version 3.2.0
  */
 public class HistoryView extends BaseObject implements Serializable {
 		 
	private static final long serialVersionUID = 5628842868060960093L;
		/* @property: */
	 private long idHistory;
	 /* @property: */
	 private Long idproduct;
	 
	 private int idOperation;
	 
	 private String date;
	 
	 private Long idOperator;
	 
	 private int idCurStation;
	 
	 private int idStatus;
	 
	 private Long idCustomer;
	 /* @property: */
	 private String operation;
	 
	 private String operator;
	 
	 private String site;
	
	 private String status;
	
	 private String supply;
	 
     private Long idSupply;
     
     private String customer; 
     
     //后台查询开始日期
     private String dateStart;
     
     //后台查询结束日期
     private String dateEnd;
     
     
    
	 /* Default constructor - creates a new instance with no values set. */
	 public HistoryView(){}
	 /* @model: */


	public long getIdHistory() {
		return idHistory;
	}


	public void setIdHistory(long idHistory) {
		this.idHistory = idHistory;
	}


	public Long getIdproduct() {
		return idproduct;
	}


	public void setIdproduct(Long idproduct) {
		this.idproduct = idproduct;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getSupply() {
		return supply;
	}


	public void setSupply(String supply) {
		this.supply = supply;
	}


	public Long getIdSupply() {
		return idSupply;
	}


	public void setIdSupply(Long idSupply) {
		this.idSupply = idSupply;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (idHistory ^ (idHistory >>> 32));
		result = prime * result
				+ ((idSupply == null) ? 0 : idSupply.hashCode());
		result = prime * result
				+ ((idproduct == null) ? 0 : idproduct.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((supply == null) ? 0 : supply.hashCode());
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
		HistoryView other = (HistoryView) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idHistory != other.idHistory)
			return false;
		if (idSupply == null) {
			if (other.idSupply != null)
				return false;
		} else if (!idSupply.equals(other.idSupply))
			return false;
		if (idproduct == null) {
			if (other.idproduct != null)
				return false;
		} else if (!idproduct.equals(other.idproduct))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (supply == null) {
			if (other.supply != null)
				return false;
		} else if (!supply.equals(other.supply))
			return false;
		return true;
	}


	public String getDateStart() {
		return dateStart;
	}


	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}


	public String getDateEnd() {
		return dateEnd;
	}


	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}


	public int getIdOperation() {
		return idOperation;
	}


	public void setIdOperation(int idOperation) {
		this.idOperation = idOperation;
	}


	public Long getIdOperator() {
		return idOperator;
	}


	public void setIdOperator(Long idOperator) {
		this.idOperator = idOperator;
	}


	public int getIdCurStation() {
		return idCurStation;
	}


	public void setIdCurStation(int idCurStation) {
		this.idCurStation = idCurStation;
	}


	public int getIdStatus() {
		return idStatus;
	}


	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}


	public Long getIdCustomer() {
		return idCustomer;
	}


	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

 }
