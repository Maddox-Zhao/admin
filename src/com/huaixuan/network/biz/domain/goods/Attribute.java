package com.huaixuan.network.biz.domain.goods;

import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.huaixuan.network.biz.domain.BaseObject;


public class Attribute extends BaseObject {
	/* @property: */
	private long id;
	/* @property: */
	private String attrName;
	/* @property: */
	private String attrDesc;
	/* @property: */
	private String attrCode;
	/* @property: */
	private long groupId;
	/* @property: */
	private String inputType;
	/* @property: */
	private String attrValues;
	private int status;
	/* @property: */
	private int isIndex;
	/* @property: */
	private int isGuide;
	/* @property: */
	private int isNeed;

	private int isBuyerChoose;

	/* 是否商品实例属性 */
	private int isInstance;

	private Map attrValueMap;

	public Map getAttrValueMap() {
		return attrValueMap;
	}

	public void setAttrValueMap(Map attrValueMap) {
		this.attrValueMap = attrValueMap;
	}

	/* Default constructor - creates a new instance with no values set. */
	public Attribute() {
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
	public void setAttrName(String obj) {
		this.attrName = obj;
	}

	/* @model: */
	public String getAttrName() {
		return this.attrName;
	}

	/* @model: */
	public void setAttrDesc(String obj) {
		this.attrDesc = obj;
	}

	/* @model: */
	public String getAttrDesc() {
		return this.attrDesc;
	}

	/* @model: */
	public void setAttrCode(String obj) {
		this.attrCode = obj;
	}

	/* @model: */
	public String getAttrCode() {
		return this.attrCode;
	}

	/* @model: */
	public void setGroupId(long obj) {
		this.groupId = obj;
	}

	/* @model: */
	public long getGroupId() {
		return this.groupId;
	}

	/* @model: */
	public void setInputType(String obj) {
		this.inputType = obj;
	}

	/* @model: */
	public String getInputType() {
		return this.inputType;
	}

	/* @model: */
	public void setAttrValues(String obj) {
		this.attrValues = obj;
	}

	/* @model: */
	public String getAttrValues() {
		return this.attrValues;
	}

	/* @model: */
	public void setIsIndex(int obj) {
		this.isIndex = obj;
	}

	/* @model: */
	public int getIsIndex() {
		return this.isIndex;
	}

	/* @model: */
	public void setIsGuide(int obj) {
		this.isGuide = obj;
	}

	/* @model: */
	public int getIsGuide() {
		return this.isGuide;
	}

	/* @model: */
	public void setIsNeed(int obj) {
		this.isNeed = obj;
	}

	/* @model: */
	public int getIsNeed() {
		return this.isNeed;
	}

	/* {@inheritDoc} */

	/* {@inheritDoc} */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsBuyerChoose() {
		return isBuyerChoose;
	}

	public void setIsBuyerChoose(int isBuyerChoose) {
		this.isBuyerChoose = isBuyerChoose;
	}

	public int getIsInstance() {
		return isInstance;
	}

	public void setIsInstance(int isInstance) {
		this.isInstance = isInstance;
	}
	
	public boolean isInstance(){
		return this.isInstance == 1;
	}

}
