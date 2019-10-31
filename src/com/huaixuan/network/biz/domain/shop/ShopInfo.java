package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ShopInfo implements Serializable {
	/* @property: */
	private long id;
	/* @property: */
	private String shopName;
	/* @property: */
	private String shopTitle;
	/* @property: */
	private String shopLogo;
	/* @property: */
	private String faviconLogo;
	/* @property: */
	private String shopDesc;
	/* @property: */
	private long userId;
	/* @property: */
	private String qq;
	/* @property: */
	private String ww;
	/* @property: */
	private String ym;
	/* @property: */
	private String msn;
	/* @property: */
	private String email;
	/* @property: */
	private String phone;
	/* @property: */
	private String serviceTel;
	/* @property: */
	private String mobile;
	/* @property: */
	private String receiveAccount;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private long addressId;

	private String address;

	private String province;
	/* @property: */
	private String city;
	/* @property: */
	private String district;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	/* Default constructor - creates a new instance with no values set. */
	public ShopInfo() {
	}

	public void setId(long obj) {
		this.id = obj;
	}

	public long getId() {
		return this.id;
	}

	public void setShopName(String obj) {
		this.shopName = obj;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopTitle(String obj) {
		this.shopTitle = obj;
	}

	public String getShopTitle() {
		return this.shopTitle;
	}

	public void setShopLogo(String obj) {
		this.shopLogo = obj;
	}

	public String getShopLogo() {
		return this.shopLogo;
	}

	public void setShopDesc(String obj) {
		this.shopDesc = obj;
	}

	public String getShopDesc() {
		return this.shopDesc;
	}

	public void setUserId(long obj) {
		this.userId = obj;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setQq(String obj) {
		this.qq = obj;
	}

	public String getQq() {
		return this.qq;
	}

	public void setWw(String obj) {
		this.ww = obj;
	}

	public String getWw() {
		return this.ww;
	}

	public void setYm(String obj) {
		this.ym = obj;
	}

	public String getYm() {
		return this.ym;
	}

	public void setMsn(String obj) {
		this.msn = obj;
	}

	public String getMsn() {
		return this.msn;
	}

	public void setEmail(String obj) {
		this.email = obj;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPhone(String obj) {
		this.phone = obj;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setMobile(String obj) {
		this.mobile = obj;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setReceiveAccount(String obj) {
		this.receiveAccount = obj;
	}

	public String getReceiveAccount() {
		return this.receiveAccount;
	}

	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* {@inheritDoc} */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ShopInfo other = (ShopInfo) obj;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		if (shopTitle == null) {
			if (other.shopTitle != null)
				return false;
		} else if (!shopTitle.equals(other.shopTitle))
			return false;
		if (shopDesc == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!shopDesc.equals(other.shopDesc))
			return false;
		if (gmtModify == null) {
			if (other.gmtModify != null)
				return false;
		} else if (!gmtModify.equals(other.gmtModify))
			return false;
		if (shopLogo == null) {
			if (other.shopLogo != null)
				return false;
		} else if (!shopLogo.equals(other.shopLogo))
			return false;
		if (qq == null) {
			if (other.qq != null)
				return false;
		} else if (!qq.equals(other.qq))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (ww == null) {
			if (other.ww != null)
				return false;
		} else if (!ww.equals(other.ww))
			return false;
		if (ym == null) {
			if (other.ym != null)
				return false;
		} else if (!ym.equals(other.ym))
			return false;
		if (msn == null) {
			if (other.msn != null)
				return false;
		} else if (!msn.equals(other.msn))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (receiveAccount == null) {
			if (other.receiveAccount != null)
				return false;
		} else if (!receiveAccount.equals(other.receiveAccount))
			return false;

		return true;
	}

	/* {@inheritDoc} */
	public int hashCode() {
		final int prime = 30;
		int result = 1;
		result = prime * result
				+ ((shopName == null) ? 0 : shopName.hashCode());
		result = prime * result
				+ ((shopTitle == null) ? 0 : shopTitle.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result
				+ ((shopLogo == null) ? 0 : shopLogo.hashCode());
		result = prime * result + ((qq == null) ? 0 : qq.hashCode());
		result = prime * result + ((ww == null) ? 0 : ww.hashCode());
		result = prime * result + ((ym == null) ? 0 : ym.hashCode());
		result = prime * result + ((msn == null) ? 0 : msn.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result
				+ ((receiveAccount == null) ? 0 : receiveAccount.hashCode());
		return result;
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("shopName", this.shopName)
				.append("shopTitle", this.shopTitle)
				.append("shopLogo", this.shopLogo)
				.append("shopDesc", this.shopDesc)
				.append("userId", this.userId).append("qq", this.qq)
				.append("ww", this.ww).append("ym", this.ym)
				.append("msn", this.msn).append("email", this.email)
				.append("phone", this.phone).append("mobile", this.mobile)
				.append("receiveAccount", this.receiveAccount)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify);
		return sb.toString();
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public String getFaviconLogo() {
		return faviconLogo;
	}

	public void setFaviconLogo(String faviconLogo) {
		this.faviconLogo = faviconLogo;
	}

}
