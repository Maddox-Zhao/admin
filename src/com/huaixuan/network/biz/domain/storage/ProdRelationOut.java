package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class ProdRelationOut extends BaseObject {

	/**
     *
     */
	private static final long serialVersionUID = -393806541002188617L;
	/* @property: */
	private long id;
	/* @property: */
	private long outDepId;
	/* @property: */
	private long goodsInstanceId;
	/* @property: */
	private long amount;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private long goodsId;
	/* @property: */
	private long outDetailId;
	/* @property: */
	private long storageId;
	/* @property: */
	private long locId;

	private String locName; // 库位名称
	private String depName; // 仓库名称
	private String instanceCode; // 商品编码
	private String instanceName; // 商品名称
	private String attrs; // 属性
	private String goodsUnit; // 单位
	private double unitPrice; // 单价
	private double money; // 金额
	/*
	 * 成本价
	 */
	private Double selfCost;

	private String isWholesale;
	private String tid;

	public Double getSelfCost() {
		return selfCost;
	}

	public void setSelfCost(Double selfCost) {
		this.selfCost = selfCost;
	}

	/* Default constructor - creates a new instance with no values set. */
	public ProdRelationOut() {
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	/**
	 * @return depName
	 */
	public String getDepName() {
		return depName;
	}

	/**
	 * @param depName
	 *            要设置的 depName
	 */
	public void setDepName(String depName) {
		this.depName = depName;
	}

	/**
	 * @return goodsUnit
	 */
	public String getGoodsUnit() {
		return goodsUnit;
	}

	/**
	 * @param goodsUnit
	 *            要设置的 goodsUnit
	 */
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	/**
	 * @return instanceCode
	 */
	public String getInstanceCode() {
		return instanceCode;
	}

	/**
	 * @param instanceCode
	 *            要设置的 instanceCode
	 */
	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	/**
	 * @return instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName
	 *            要设置的 instanceName
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return locName
	 */
	public String getLocName() {
		return locName;
	}

	/**
	 * @param locName
	 *            要设置的 locName
	 */
	public void setLocName(String locName) {
		this.locName = locName;
	}

	/**
	 * @return money
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            要设置的 money
	 */
	public void setMoney(double money) {
		this.money = money;
	}

	/**
	 * @return unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            要设置的 unitPrice
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
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
	public void setOutDepId(long obj) {
		this.outDepId = obj;
	}

	/* @model: */
	public long getOutDepId() {
		return this.outDepId;
	}

	/* @model: */
	public void setGoodsInstanceId(long obj) {
		this.goodsInstanceId = obj;
	}

	/* @model: */
	public long getGoodsInstanceId() {
		return this.goodsInstanceId;
	}

	/* @model: */
	public void setAmount(long obj) {
		this.amount = obj;
	}

	/* @model: */
	public long getAmount() {
		return this.amount;
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
	public void setGoodsId(long obj) {
		this.goodsId = obj;
	}

	/* @model: */
	public long getGoodsId() {
		return this.goodsId;
	}

	/* @model: */
	public void setOutDetailId(long obj) {
		this.outDetailId = obj;
	}

	/* @model: */
	public long getOutDetailId() {
		return this.outDetailId;
	}

	/* @model: */
	public void setStorageId(long obj) {
		this.storageId = obj;
	}

	/* @model: */
	public long getStorageId() {
		return this.storageId;
	}

	/* @model: */
	public void setLocId(long obj) {
		this.locId = obj;
	}

	/* @model: */
	public long getLocId() {
		return this.locId;
	}

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ProdRelationOut)) {
			return false;
		}
		final ProdRelationOut prodrelationout = (ProdRelationOut) o;
		return this.hashCode() == prodrelationout.hashCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result + ((attrs == null) ? 0 : attrs.hashCode());
		result = prime * result + ((depName == null) ? 0 : depName.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result + (int) (goodsId ^ (goodsId >>> 32));
		result = prime * result + (int) (goodsInstanceId ^ (goodsInstanceId >>> 32));
		result = prime * result + ((goodsUnit == null) ? 0 : goodsUnit.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((instanceCode == null) ? 0 : instanceCode.hashCode());
		result = prime * result + ((instanceName == null) ? 0 : instanceName.hashCode());
		result = prime * result + ((isWholesale == null) ? 0 : isWholesale.hashCode());
		result = prime * result + (int) (locId ^ (locId >>> 32));
		result = prime * result + ((locName == null) ? 0 : locName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (outDepId ^ (outDepId >>> 32));
		result = prime * result + (int) (outDetailId ^ (outDetailId >>> 32));
		result = prime * result + ((selfCost == null) ? 0 : selfCost.hashCode());
		result = prime * result + (int) (storageId ^ (storageId >>> 32));
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
		temp = Double.doubleToLongBits(unitPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("outDepId", this.outDepId).append("goodsInstanceId", this.goodsInstanceId)
				.append("amount", this.amount).append("gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify)
				.append("goodsId", this.goodsId).append("outDetailId", this.outDetailId)
				.append("storageId", this.storageId).append("locId", this.locId);
		return sb.toString();
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}
