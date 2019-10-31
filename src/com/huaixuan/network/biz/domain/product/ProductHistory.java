package com.huaixuan.network.biz.domain.product;

import java.util.Date;



/**
 * @author Mr_Yang   2016-1-19 下午03:24:47
 * 产品历史记录
 **/

public class ProductHistory
{
		private String idProduct; 
		private Integer idOperation; //操作ID
		private String operation; //操作动作
		private Date date; 
		private String idOperator;//操作人ID
		private String operatorName; //操作人姓名
		private Integer idCurStation; //当前站点ID
		private String siteName; //当前站点名称
		private Integer idStatus; //当前产品状态ID
		private String status; //当前产品状态
		private Integer idSupply;//供货商id
		private String supply;//供货商名称
		private Integer idCustomer;//客户ID
		private String customerName;//客户姓名
		
		
		
		public String getIdProduct()
		{
			return idProduct;
		}
		public void setIdProduct(String idProduct)
		{
			this.idProduct = idProduct;
		}
		public Integer getIdOperation()
		{
			return idOperation;
		}
		public void setIdOperation(Integer idOperation)
		{
			this.idOperation = idOperation;
		}
		public String getOperation()
		{
			return operation;
		}
		public void setOperation(String operation)
		{
			this.operation = operation;
		}
		public Date getDate()
		{
			return date;
		}
		public void setDate(Date date)
		{
			this.date = date;
		}
		public String getIdOperator()
		{
			return idOperator;
		}
		public void setIdOperator(String idOperator)
		{
			this.idOperator = idOperator;
		}
		public String getOperatorName()
		{
			return operatorName;
		}
		public void setOperatorName(String operatorName)
		{
			this.operatorName = operatorName;
		}
		public Integer getIdCurStation()
		{
			return idCurStation;
		}
		public void setIdCurStation(Integer idCurStation)
		{
			this.idCurStation = idCurStation;
		}
		public String getSiteName()
		{
			return siteName;
		}
		public void setSiteName(String siteName)
		{
			this.siteName = siteName;
		}
		public Integer getIdStatus()
		{
			return idStatus;
		}
		public void setIdStatus(Integer idStatus)
		{
			this.idStatus = idStatus;
		}
		public String getStatus()
		{
			return status;
		}
		public void setStatus(String status)
		{
			this.status = status;
		}
		public Integer getIdSupply()
		{
			return idSupply;
		}
		public void setIdSupply(Integer idSupply)
		{
			this.idSupply = idSupply;
		}
		public String getSupply()
		{
			return supply;
		}
		public void setSupply(String supply)
		{
			this.supply = supply;
		}
		public Integer getIdCustomer()
		{
			return idCustomer;
		}
		public void setIdCustomer(Integer idCustomer)
		{
			this.idCustomer = idCustomer;
		}
		public String getCustomerName()
		{
			return customerName;
		}
		public void setCustomerName(String customerName)
		{
			this.customerName = customerName;
		}
		
		
		
		
		
}
 
