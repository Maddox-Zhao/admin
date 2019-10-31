package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ��������������������(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class CatAttrRel extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
    private long   id;
    /* @property: */
    private String showName;
    /* @property: */
    private String catCode;
    /* @property: */
    private String attrCode;
    /* @property: */
    private int    sortOrder;
    /* @property: */
    private int    enable;
    /* @property: */
    private Date   gmtCreate;
    /* @property: */
    private Date   gmtModify;

    private String attrName;

    private String attrValue;

    private String categoryName;

    private int    parentTag;

    public int getParentTag() {
        return parentTag;
    }

    public void setParentTag(int parentTag) {
        this.parentTag = parentTag;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    /* Default constructor - creates a new instance with no values set. */
    public CatAttrRel() {
    }

    /* @model:�������� */
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model:������ */
    public long getId() {
        return this.id;
    }

    /* @model:�������� */
    public void setShowName(String obj) {
        this.showName = obj;
    }

    /* @model:������ */
    public String getShowName() {
        return this.showName;
    }

    /* @model:�������� */
    public void setCatCode(String obj) {
        this.catCode = obj;
    }

    /* @model:������ */
    public String getCatCode() {
        return this.catCode;
    }

    /* @model:�������� */
    public void setAttrCode(String obj) {
        this.attrCode = obj;
    }

    /* @model:������ */
    public String getAttrCode() {
        return this.attrCode;
    }

    /* @model:�������� */
    public void setSortOrder(int obj) {
        this.sortOrder = obj;
    }

    /* @model:������ */
    public int getSortOrder() {
        return this.sortOrder;
    }

    /* @model:�������� */
    public void setEnable(int obj) {
        this.enable = obj;
    }

    /* @model:������ */
    public int getEnable() {
        return this.enable;
    }

    /* @model:�������� */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:������ */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:�������� */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model:������ */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatAttrRel)) {
            return false;
        }
        final CatAttrRel catattrrel = (CatAttrRel) o;
        return this.hashCode() == catattrrel.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("showName", this.showName).append("catCode", this.catCode).append(
            "attrCode", this.attrCode).append("sortOrder", this.sortOrder).append("enable",
            this.enable).append("gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify);
        return sb.toString();
    }

}
