package com.huaixuan.network.biz.domain.storage.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 移库外借查询类
 * @version 3.2.0
 */
public class MoveStorageQuery extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -1546602608530274027L;

	private String 				id;
	//商品名称
    private String             instanceName;
    //操作类型（移库，外借）
    private String             type;
    //商品编码
    private String             code;
    //仓库类型
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
    //移库外借单号
    private String             moveCode;

    private String             gmtCreateStart;
    private String             gmtCreateEnd;

    private String             conditionOne;
    private String             conditionTwo;

    private Object[] 		   storageids;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object[] getStorageids() {
		return storageids;
	}
	public void setStorageids(Object[] storageids) {
		this.storageids = storageids;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(String gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}
	public String getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(String gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
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
	public String getMoveCode() {
		return moveCode;
	}
	public void setMoveCode(String moveCode) {
		this.moveCode = moveCode;
	}

}
