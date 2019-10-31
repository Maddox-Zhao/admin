package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * �����Զ����(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class ActivityUsers implements Serializable {
	/* @property: */
	private long id;
	/* @property: */
	private long usersId;
	/* @property: */
	private long actId;
	/* @property: */
	private String actType;
	/* @property: */
	private String tradeId;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;

	/* Default constructor - creates a new instance with no values set. */
	public ActivityUsers() {
	}

	/* @model:���� */
	public void setId(long obj) {
		this.id = obj;
	}

	/* @model:��ȡ */
	public long getId() {
		return this.id;
	}

	/* @model:���� */
	public void setUsersId(long obj) {
		this.usersId = obj;
	}

	/* @model:��ȡ */
	public long getUsersId() {
		return this.usersId;
	}

	/* @model:���� */
	public void setActId(long obj) {
		this.actId = obj;
	}

	/* @model:��ȡ */
	public long getActId() {
		return this.actId;
	}

	/* @model:���� */
	public void setActType(String obj) {
		this.actType = obj;
	}

	/* @model:��ȡ */
	public String getActType() {
		return this.actType;
	}

	/**
	 * @return the tradeId
	 */
	public String getTradeId() {
		return tradeId;
	}

	/**
	 * @param tradeId
	 *            the tradeId to set
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	/* @model:���� */
	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	/* @model:��ȡ */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	/* @model:���� */
	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	/* @model:��ȡ */
	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ActivityUsers)) {
			return false;
		}
		final ActivityUsers activityusers = (ActivityUsers) o;
		return this.hashCode() == activityusers.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		return this.hashCode();
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("usersId", this.usersId).append("actId", this.actId)
				.append("actType", this.actType)
				.append("tradeId", this.tradeId)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify);
		return sb.toString();
	}

}
