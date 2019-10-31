package com.huaixuan.network.biz.domain.storage.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ����ѯ��
 * @version 3.2.0
 */
public class StorageQuery extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -1546602608530274027L;
	//��Ʒ����
    private String             instanceName;
    // ��Ӧ������
    private String             supplierName;
    //��Ʒ����
    private String             code;
    //����
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
    //�Ƿ��ϼ�
    private String             goodStatus;

    //����
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
