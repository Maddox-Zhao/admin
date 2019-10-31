/**
 * created since 2010-6-7
 */
package com.huaixuan.network.biz.domain.base.counter;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author guoyj
 * @version $Id: BaseBankCompareItem.java,v 0.1 2010-6-7 ÏÂÎç13:41:12 guoyj Exp $
 */
public class BaseBankCompareItem extends BaseObject implements Serializable {

    private static final long serialVersionUID = 845713339115180429L;

    private Long              id;
    /* @property:¦ÊEP_INPUT_FILE.BATCH_ID */
    private Long              batchId;
    /* @property: */
    private String            payDate;
    /* @property: */
    private String            bankType;
    /* @property:§Ø */
    private String            bankBillNo;
    /* @property: */
    private Long              payAmount;
    /* @property: */
    private String            bankSerialNo;
    /* @property: */
    private Date              gmtCreate;

    /* Default constructor - creates a new instance with no values set. */
    public BaseBankCompareItem() {
    }

    /* @model:seq,pk */
    public void setId(Long obj) {
        this.id = obj;
    }

    /* @model:seq,pk */
    public Long getId() {
        return this.id;
    }

    /* @model:¦ÊEP_INPUT_FILE.BATCH_ID */
    public void setBatchId(Long obj) {
        this.batchId = obj;
    }

    /* @model:¦ÊEP_INPUT_FILE.BATCH_ID */
    public Long getBatchId() {
        return this.batchId;
    }

    /* @model: */
    public void setPayDate(String obj) {
        this.payDate = obj;
    }

    /* @model: */
    public String getPayDate() {
        return this.payDate;
    }

    /* @model: */
    public void setBankType(String obj) {
        this.bankType = obj;
    }

    /* @model: */
    public String getBankType() {
        return this.bankType;
    }

    /* @model:§Ø */
    public void setBankBillNo(String obj) {
        this.bankBillNo = obj;
    }

    /* @model:§Ø */
    public String getBankBillNo() {
        return this.bankBillNo;
    }

    /* @model:¡Â */
    public void setPayAmount(Long obj) {
        this.payAmount = obj;
    }

    /* @model: */
    public Long getPayAmount() {
        return this.payAmount;
    }

    /* @model: */
    public void setBankSerialNo(String obj) {
        this.bankSerialNo = obj;
    }

    /* @model: */
    public String getBankSerialNo() {
        return this.bankSerialNo;
    }

    /* @model: */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseBankCompareItem)) {
            return false;
        }
        final BaseBankCompareItem epbankcompareitem = (BaseBankCompareItem) o;
        return this.hashCode() == epbankcompareitem.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("batchId", this.batchId).append("payDate", this.payDate).append(
            "bankType", this.bankType).append("bankBillNo", this.bankBillNo).append("payAmount",
            this.payAmount).append("bankSerialNo", this.bankSerialNo).append("gmtCreate",
            this.gmtCreate);
        return sb.toString();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1520776441, -615687995).appendSuper(super.hashCode()).append(
            this.payAmount).append(this.id).append(this.gmtCreate).append(this.bankType).append(
            this.bankSerialNo).append(this.bankBillNo).append(this.payDate).append(this.batchId)
            .toHashCode();
    }

}
