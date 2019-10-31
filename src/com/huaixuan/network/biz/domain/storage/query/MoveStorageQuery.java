package com.huaixuan.network.biz.domain.storage.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * �ƿ�����ѯ��
 * @version 3.2.0
 */
public class MoveStorageQuery extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -1546602608530274027L;

	private String 				id;
	//��Ʒ����
    private String             instanceName;
    //�������ͣ��ƿ⣬��裩
    private String             type;
    //��Ʒ����
    private String             code;
    //�ֿ�����
    private String             storType;
    //һ����Ŀ����
    private String             catCode;
    //������Ŀ����
    private String             twoCatCode;
    //һ���ֿ�ID
    private String             depfirstId;
    //�ֿ�ID
    private String             depId;
    //��λID
    private String             locId;
    //�ƿ���赥��
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
