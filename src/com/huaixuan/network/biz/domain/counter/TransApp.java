/**
 *
 */
package com.huaixuan.network.biz.domain.counter;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.enums.EnumTransAppStatus;
import com.hundsun.itrans.biz.domain.Enum.EnumInstitution;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;

/**
 * @author shlin@hundsun.com
 *
 */
public class TransApp implements Serializable {

    private static final long serialVersionUID = -3790278932299005464L;
    protected Log             log              = LogFactory.getLog(this.getClass());
    private long              id;
    private String            inAccountNo;
    private String            outAccountNo;
    private double            amount;
    private String            outBizNo;
    private String            innerBizNo;
    private String            transDate;
    private String            outDate;
    private String            bankType;
    private String            memo;
    private String            status;
    private String            subTransCode;
    private Date              gmtCreate;
    private Date              gmtModify;

    public String getSubTransCodeName() {
        return EnumSubTransCode.getByCode(subTransCode).getDescription();
    }

    public String getStatusName() {
        return (String) (EnumTransAppStatus.toMap().get(this.status));
    }

    public String getBankTypeName() {
        return EnumInstitution.getByName(this.bankType).getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInAccountNo() {
        return inAccountNo;
    }

    public void setInAccountNo(String inAccountNo) {
        this.inAccountNo = inAccountNo;
    }

    public String getOutAccountNo() {
        return outAccountNo;
    }

    public void setOutAccountNo(String outAccountNo) {
        this.outAccountNo = outAccountNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getInnerBizNo() {
        return innerBizNo;
    }

    public void setInnerBizNo(String innerBizNo) {
        this.innerBizNo = innerBizNo;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubTransCode() {
        return subTransCode;
    }

    public void setSubTransCode(String subTransCode) {
        this.subTransCode = subTransCode;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}
