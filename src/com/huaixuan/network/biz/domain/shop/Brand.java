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
public class Brand extends BaseObject implements Serializable {
	/* @property: */
	private long id;
	/* @property: */
	private String brandName;
	/* @property: */
	private String brandLogo;
	private String brandTemp;
	/* @property: */
	private String brandDesc;
	/* @property: */
	private String link;
	/* @property: */
	private int sort;
	/* @property: */
	private int isShow;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private long shopId;
	/* @property: */
	private int status;
	/* ishow为显示 */
	public static final int IsShow_yes = 1;
	/* ishow为不显示 */
	public static final int IsShow_no = 0;

	/* Default constructor - creates a new instance with no values set. */
	public Brand() {
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
	public void setBrandName(String obj) {
		this.brandName = obj;
	}

	/* @model: */
	public String getBrandName() {
		return this.brandName;
	}

	/* @model: */
	public void setBrandLogo(String obj) {
		this.brandLogo = obj;
	}

	/* @model: */
	public String getBrandLogo() {
		return this.brandLogo;
	}

	/* @model: */
	public void setBrandDesc(String obj) {
		this.brandDesc = obj;
	}

	/* @model: */
	public String getBrandDesc() {
		return this.brandDesc;
	}

	/* @model: */
	public void setLink(String obj) {
		this.link = obj;
	}

	/* @model: */
	public String getLink() {
		return this.link;
	}

	public String getBrandTemp() {
		return brandTemp;
	}

	public void setBrandTemp(String brandTemp) {
		this.brandTemp = brandTemp;
	}

	/* @model: */
	public void setSort(int obj) {
		this.sort = obj;
	}

	/* @model: */
	public int getSort() {
		return this.sort;
	}

	/* @model: */
	public void setIsShow(int obj) {
		this.isShow = obj;
	}

	/* @model: */
	public int getIsShow() {
		return this.isShow;
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
		final Brand other = (Brand) obj;
		if (brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!brandName.equals(other.brandName))
			return false;
		if (brandLogo == null) {
			if (other.brandLogo != null)
				return false;
		} else if (!brandLogo.equals(other.brandLogo))
			return false;
		if (brandDesc == null) {
			if (other.brandDesc != null)
				return false;
		} else if (!brandDesc.equals(other.brandDesc))
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
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;

		return true;
	}

	/* {@inheritDoc} */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((brandName == null) ? 0 : brandName.hashCode());
		result = prime * result
				+ ((brandLogo == null) ? 0 : brandLogo.hashCode());
		result = prime * result
				+ ((brandDesc == null) ? 0 : brandDesc.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return result;
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("brandName", this.brandName)
				.append("brandLogo", this.brandLogo)
				.append("brandDesc", this.brandDesc).append("link", this.link)
				.append("sort", this.sort).append("isShow", this.isShow)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify)
				.append("shopId", this.shopId);
		return sb.toString();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
