package com.huaixuan.network.biz.domain.base.account;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class BaseAccount extends BaseObject implements Serializable {

    private static final long serialVersionUID = 7635477290170289612L;

    /* @property: */
    private String            accountNo;

    /* @property:ID */
    private Long              userId;

    /* @property:1:,2:,3:,6:§Þ,8: */
    private Long              accountType;

    /* @property:01:02:,03:04:,05: */
    private String            accountSubType;

    /* @property: */
    private Long              balance;

    /* @property: */
    private Long              freezeAmount;

    /* @property:(T-,B-,C-,R-) */
    private String            enabledStatus;

    /* @property: */
    private Date              gmtCreate;

    /* @property: */
    private Date              gmtModified;

    /* @property: */
    private String            creator;

    /* @property: */
    private String            modifier;

    /* @property: */
    private String            memo;

    private String            checkSign;

    private String            account;

    /* Default constructor - creates a new instance with no values set. */
    public BaseAccount() {
    }

    /* @model: */
    public void setAccountNo(String obj) {
        this.accountNo = obj;
    }

    /* @model: */
    public String getAccountNo() {
        return this.accountNo;
    }

    /* @model:ID */
    public void setUserId(Long obj) {
        this.userId = obj;
    }

    /* @model:ID */
    public Long getUserId() {
        return this.userId;
    }

    /* @model:1:,2:,3:,6:§Þ,8: */
    public void setAccountType(Long obj) {
        this.accountType = obj;
    }

    /* @model:1:,2:,3:,6:§Þ,8: */
    public Long getAccountType() {
        return this.accountType;
    }

    /* @model:01:02:,03:04:,05: */
    public void setAccountSubType(String obj) {
        this.accountSubType = obj;
    }

    /* @model:01:02:,03:04:,05: */
    public String getAccountSubType() {
        return this.accountSubType;
    }

    /* @model: */
    public void setBalance(Long obj) {
        this.balance = obj;
    }

    /* @model: */
    public Long getBalance() {
        return this.balance;
    }

    /* @model: */
    public void setFreezeAmount(Long obj) {
        this.freezeAmount = obj;
    }

    /* @model: */
    public Long getFreezeAmount() {
        return this.freezeAmount;
    }

    /* @model:(T-,B-,C-,R-) */
    public void setEnabledStatus(String obj) {
        this.enabledStatus = obj;
    }

    /* @model:(T-,B-,C-,R-) */
    public String getEnabledStatus() {
        return this.enabledStatus;
    }

    /* @model: */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model: */
    public void setGmtModified(Date obj) {
        this.gmtModified = obj;
    }

    /* @model: */
    public Date getGmtModified() {
        return this.gmtModified;
    }

    /* @model: */
    public void setCreator(String obj) {
        this.creator = obj;
    }

    /* @model: */
    public String getCreator() {
        return this.creator;
    }

    /* @model: */
    public void setModifier(String obj) {
        this.modifier = obj;
    }

    /* @model: */
    public String getModifier() {
        return this.modifier;
    }

    /* @model: */
    public void setMemo(String obj) {
        this.memo = obj;
    }

    /* @model: */
    public String getMemo() {
        return this.memo;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseAccount)) {
            return false;
        }
        final BaseAccount account = (BaseAccount) o;
        return this.hashCode() == account.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append(
            "accountNo", this.accountNo).append("userId", this.userId).append("accountType",
            this.accountType).append("accountSubType", this.accountSubType).append("balance",
            this.balance).append("freezeAmount", this.freezeAmount).append("enabledStatus",
            this.enabledStatus).append("gmtCreate", this.gmtCreate).append("gmtModified",
            this.gmtModified).append("creator", this.creator).append("modifier", this.modifier)
            .append("memo", this.memo).append("checkSign", this.checkSign).append("account",
                this.account);
        return sb.toString();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(2070986541, 1759949175).appendSuper(super.hashCode()).append(
            this.modifier).append(this.gmtModified).append(this.balance).append(this.memo).append(
            this.gmtCreate).append(this.freezeAmount).append(this.userId).append(this.accountType)
            .append(this.accountNo).append(this.accountSubType).append(this.enabledStatus).append(
                this.creator).toHashCode();
    }

    public String getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
