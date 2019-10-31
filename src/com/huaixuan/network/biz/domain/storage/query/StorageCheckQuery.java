package com.huaixuan.network.biz.domain.storage.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ø‚¥Ê≈Ãµ„≤È—Ø¿‡
 * @version 3.2.0
 */
public class StorageCheckQuery extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /* @property: */
    private String            locId;
    private String            locName;
    private String            depId;
    private String            depName;
    private String            depfirstId;
    private String            dateStart;
    private String            dateEnd;
    private String            goodsInstanceId;
    private String            goodsInstanceName;
    private String            status;
    private String            goodsInstanceCode;
    private String            checkId;
    private String            storType;


	public String getStorType() {
		return storType;
	}
	public void setStorType(String storType) {
		this.storType = storType;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDepfirstId() {
		return depfirstId;
	}
	public void setDepfirstId(String depfirstId) {
		this.depfirstId = depfirstId;
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
	public String getGoodsInstanceId() {
		return goodsInstanceId;
	}
	public void setGoodsInstanceId(String goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}
	public String getGoodsInstanceName() {
		return goodsInstanceName;
	}
	public void setGoodsInstanceName(String goodsInstanceName) {
		this.goodsInstanceName = goodsInstanceName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGoodsInstanceCode() {
		return goodsInstanceCode;
	}
	public void setGoodsInstanceCode(String goodsInstanceCode) {
		this.goodsInstanceCode = goodsInstanceCode;
	}
}
