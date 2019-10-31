package com.huaixuan.network.biz.domain.express;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class Express extends BaseObject implements Serializable {

	private static final long serialVersionUID = 2745311035992578803L;
	/* @property: */
	private Long id;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private String expressName;
	// 快递公司代码
	private String expressCode;
	/* @property: */
	private String expressDesc;
	/* @property: */
	private String expressUrl;
	/* @property: */
	private String status;

	private String account; // 用户名

	private String tid; // 订单号

	private Double shippingAmount; // 运费金额

	private String depFirstName; // 一级仓库名称

	private Date gmtOutDep; // 出库时间

	private Long expressId;

	private Double actualInventory; // 实际运费

	private String interfaceExpressCode;//淘宝同步物流

	public Double getActualInventory() {
		return actualInventory;
	}

	public void setActualInventory(Double actualInventory) {
		this.actualInventory = actualInventory;
	}

	public String getInterfaceExpressCode() {
		return interfaceExpressCode;
	}

	public void setInterfaceExpressCode(String interfaceExpressCode) {
		this.interfaceExpressCode = interfaceExpressCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getDepFirstName() {
		return depFirstName;
	}

	public void setDepFirstName(String depFirstName) {
		this.depFirstName = depFirstName;
	}

	public Date getGmtOutDep() {
		return gmtOutDep;
	}

	public void setGmtOutDep(Date gmtOutDep) {
		this.gmtOutDep = gmtOutDep;
	}

	/* Default constructor - creates a new instance with no values set. */
	public Express() {
	}

	/* @model: */
	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	/* @model: */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	/* @model: */
	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	/* @model: */
	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* @model: */
	public void setExpressName(String obj) {
		this.expressName = obj;
	}

	/* @model: */
	public String getExpressName() {
		return this.expressName;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	/* @model: */
	public void setExpressDesc(String obj) {
		this.expressDesc = obj;
	}

	/* @model: */
	public String getExpressDesc() {
		return this.expressDesc;
	}

	/* @model: */
	public void setExpressUrl(String obj) {
		this.expressUrl = obj;
	}

	/* @model: */
	public String getExpressUrl() {
		return this.expressUrl;
	}

	/* @model: */
	public void setStatus(String obj) {
		this.status = obj;
	}

	/* @model: */
	public String getStatus() {
		return this.status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(Double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Express)) {
			return false;
		}
		final Express express = (Express) o;
		return this.hashCode() == express.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		return this.hashCode();
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify)
				.append("expressName", this.expressName).append("expressCode", this.expressCode)
				.append("expressDesc", this.expressDesc).append("expressUrl", this.expressUrl)
				.append("status", this.status);
		return sb.toString();
	}

}
