package com.huaixuan.network.biz.domain.shop;

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
public class FriendLink extends BaseObject implements Serializable {
	/* @property: */
	private long id;
	/* @property: */
	private String linkName;
	/* @property: */
	private String linkUrl;
	/* @property: */
	private String linkLogo;

	private String linkTemp;
	/* @property: */
	private int sort;
	/* @property: */
	private String sortstr;

	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private long shopId;

	/* Default constructor - creates a new instance with no values set. */
	public FriendLink() {
	}

	/* @model: */
	public void setId(long obj) {
		this.id = obj;
	}

	/* @model: */
	public long getId() {
		return this.id;
	}

	/* @model: */
	public void setLinkName(String obj) {
		this.linkName = obj;
	}

	/* @model: */
	public String getLinkName() {
		return this.linkName;
	}

	/* @model: */
	public void setLinkUrl(String obj) {
		this.linkUrl = obj;
	}

	/* @model: */
	public String getLinkUrl() {
		return this.linkUrl;
	}

	/* @model: */
	public void setLinkLogo(String obj) {
		this.linkLogo = obj;
	}

	/* @model: */
	public String getLinkLogo() {
		return this.linkLogo;
	}

	/* @model: */
	public void setSort(int obj) {
		this.sort = obj;
	}

	/* @model: */
	public int getSort() {
		return this.sort;
	}

	public String getLinkTemp() {
		return linkTemp;
	}

	public void setLinkTemp(String linkTemp) {
		this.linkTemp = linkTemp;
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
	public void setShopId(long obj) {
		this.shopId = obj;
	}

	/* @model: */
	public long getShopId() {
		return this.shopId;
	}

	/* {@inheritDoc} */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FriendLink other = (FriendLink) obj;
		if (linkName == null) {
			if (other.linkName != null)
				return false;
		} else if (!linkName.equals(other.linkName))
			return false;
		if (linkUrl == null) {
			if (other.linkUrl != null)
				return false;
		} else if (!linkUrl.equals(other.linkUrl))
			return false;
		if (linkLogo == null) {
			if (other.linkLogo != null)
				return false;
		} else if (!linkLogo.equals(other.linkLogo))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModify == null) {
			if (other.gmtModify != null)
				return false;
		} else if (!gmtModify.equals(other.gmtModify))
			return false;

		return true;
	}

	/* {@inheritDoc} */
	public int hashCode() {
		final int prime = 32;
		int result = 1;
		result = prime * result
				+ ((linkName == null) ? 0 : linkName.hashCode());
		result = prime * result + ((linkUrl == null) ? 0 : linkUrl.hashCode());
		result = prime * result
				+ ((linkLogo == null) ? 0 : linkLogo.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		return result;
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("linkName", this.linkName)
				.append("linkUrl", this.linkUrl)
				.append("linkLogo", this.linkLogo).append("sort", this.sort)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify)
				.append("shopId", this.shopId);
		return sb.toString();
	}

	public String getSortstr() {
		return sortstr;
	}

	public void setSortstr(String sortstr) {
		this.sortstr = sortstr;
	}

}
