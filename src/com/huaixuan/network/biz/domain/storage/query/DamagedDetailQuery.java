/**
 *
 */
package com.huaixuan.network.biz.domain.storage.query;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author chenhang 2011-3-28
 */
public class DamagedDetailQuery extends BaseObject {
	private long id;
	/* @property: */
	private long goodsId;
	/* @property: */
	private long goodsInstanceId;
	/* @property: */
	private long damagedId;
	/* @property: */
	private String goodsName;
	/* @property: */
	private String supplierId;
	/* @property: */
	private String unit;
	/* @property: */
	private long amount;
	/* @property: */
	private double unitCost;
	/* @property: */
	private double costCount;
	/* @property: */
	private String remark;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private long locId;
	/* @property: */
	private String reason;
	/* @property: Åú´Î */
	private String batchNum;

	public String getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(String depFirstId) {
		this.depFirstId = depFirstId;
	}

	/* @property: */
	private String damagedTimeStart;
	/* @property: */
	private String damagedTimeEnd;
	private String depFirstId;
	private String goodsCode;

	public long getId() {
		return id;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public long getDamagedId() {
		return damagedId;
	}

	public void setDamagedId(long damagedId) {
		this.damagedId = damagedId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public double getCostCount() {
		return costCount;
	}

	public void setCostCount(double costCount) {
		this.costCount = costCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public long getLocId() {
		return locId;
	}

	public void setLocId(long locId) {
		this.locId = locId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getDamagedTimeStart() {
		return damagedTimeStart;
	}

	public void setDamagedTimeStart(String damagedTimeStart) {
		this.damagedTimeStart = damagedTimeStart;
	}

	public String getDamagedTimeEnd() {
		return damagedTimeEnd;
	}

	public void setDamagedTimeEnd(String damagedTimeEnd) {
		this.damagedTimeEnd = damagedTimeEnd;
	}

}
