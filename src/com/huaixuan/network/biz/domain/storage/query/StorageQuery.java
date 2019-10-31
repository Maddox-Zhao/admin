package com.huaixuan.network.biz.domain.storage.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 库存查询类
 * @version 3.2.0
 */
public class StorageQuery extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -1546602608530274027L;
	//商品名称
    private String             instanceName;
    // 供应商名称
    private String             supplierName;
    //商品编码
    private String             code;
    //类型
    private String             storType;
    //一级类目编码
    private String             catCode;
    //二级类目编码
    private String             twoCatCode;
    //一级仓库ID
    private String             depfirstId;
    //仓库ID
    private String             depId;
    //库位ID
    private String             locId;
    //是否上架
    private String             goodStatus;

    //排序
    private String             orderBy;

    private String             conditionOne;
    private String             conditionTwo;

    private List<Long>         depfirstIds;

    private String             type;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List getDepfirstIds() {
		return depfirstIds;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStorType() {
		return storType;
	}
	public void setStorType(String storType) {
		this.storType = storType;
	}
	public String getCatCode() {
		return catCode;
	}
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	public String getTwoCatCode() {
		return twoCatCode;
	}
	public void setTwoCatCode(String twoCatCode) {
		this.twoCatCode = twoCatCode;
	}

	public String getDepfirstId() {
		return depfirstId;
	}
	public void setDepfirstId(String depfirstId) {
		this.depfirstId = depfirstId;
	}
	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getConditionOne() {
		return conditionOne;
	}
	public void setConditionOne(String conditionOne) {
		this.conditionOne = conditionOne;
	}
	public String getConditionTwo() {
		return conditionTwo;
	}
	public void setConditionTwo(String conditionTwo) {
		this.conditionTwo = conditionTwo;
	}
	public String getGoodStatus() {
		return goodStatus;
	}
	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

}
