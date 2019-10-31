package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class WholesaleApplyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;

    private Date              gmtCreate;

    private Date              gmtModify;

    private Long              wholesaleApplyId;

    private Long              goodsInstanceId;

    private Long              applyNumber;

    private String            code;

    private String            instanceName;

    private String            catCode;

    private String            attrs;

    /* Default constructor - creates a new instance with no values set. */
    public WholesaleApplyDetail() {
    }

    public void setId(Long obj) {
        this.id = obj;
    }

    public Long getId() {
        return this.id;
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

    public void setWholesaleApplyId(Long obj) {
        this.wholesaleApplyId = obj;
    }

    public Long getWholesaleApplyId() {
        return this.wholesaleApplyId;
    }

    public void setGoodsInstanceId(Long obj) {
        this.goodsInstanceId = obj;
    }

    public Long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    public void setApplyNumber(Long obj) {
        this.applyNumber = obj;
    }

    public Long getApplyNumber() {
        return this.applyNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WholesaleApplyDetail)) {
            return false;
        }
        final WholesaleApplyDetail wholesaleapplydetail = (WholesaleApplyDetail) o;
        return this.hashCode() == wholesaleapplydetail.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify)
            .append("wholesaleApplyId", this.wholesaleApplyId).append("goodsInstanceId",
                this.goodsInstanceId).append("applyNumber", this.applyNumber);
        return sb.toString();
    }
}
