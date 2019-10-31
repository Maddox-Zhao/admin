package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class PayRecord extends BaseObject implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5736093879925298289L;
    /* @property: */
    private long              id;
    /* @property: */
    private String            payPlatform;
    /* @property: */
    private String            tid;
    /* @property: */
    private Date              tradeTime;
    /* @property: */
    private double            payAmount;
    /* @property: */
    private String            payId;
    /* @property: */
    private String            payDest;
    /* @property: */
    private String            buyAccountNu;
    /* @property: */
    private String            buyer;
    /* @property: */
    private String            sellerAccountNo;
    /* @property: */
    private String            seller;
    /* @property: */
    private String            batchNo;
    /* @property: */
    private String            voucherNo;
    /* @property: */
    private String            payStatus;
    /* @property: */
    private Date              gmtPlatformPaytime;
    /* @property: */
    private String            platformPayDemo;
    /* @property: */
    private String            platformPayType;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;

    private double            codAmount;                              //货到付款金额

    private String            account;                                //用户名

    private double            payAmountSum;

    private double            payCodSum;                              //货到付款统计

    private String            financeStatus;                          //财务状态

    private Date              payTime;                                //付款时间

    private Double            agentSellAmount;

    private double            shippingAmount;

    private String            type;
    
    private String            outBillNum;                             //出库单号

    
    /**
     * @return the outBillNum
     */
    public String getOutBillNum() {
        return outBillNum;
    }

    /**
     * @param outBillNum the outBillNum to set
     */
    public void setOutBillNum(String outBillNum) {
        this.outBillNum = outBillNum;
    }

    /**
     * @return the payTime
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime the payTime to set
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * @return the financeStatus
     */
    public String getFinanceStatus() {
        return financeStatus;
    }

    /**
     * @param financeStatus the financeStatus to set
     */
    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

    /**
     * @return the payCodSum
     */
    public double getPayCodSum() {
        return payCodSum;
    }

    /**
     * @param payCodSum the payCodSum to set
     */
    public void setPayCodSum(double payCodSum) {
        this.payCodSum = payCodSum;
    }

    /**
     * @return the codAmount
     */
    public double getCodAmount() {
        return codAmount;
    }

    /**
     * @param codAmount the codAmount to set
     */
    public void setCodAmount(double codAmount) {
        this.codAmount = codAmount;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the payAmountSum
     */
    public double getPayAmountSum() {
        return payAmountSum;
    }

    /**
     * @param payAmountSum the payAmountSum to set
     */
    public void setPayAmountSum(double payAmountSum) {
        this.payAmountSum = payAmountSum;
    }

    /* Default constructor - creates a new instance with no values set. */
    public PayRecord() {
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
    public void setPayPlatform(String obj) {
        this.payPlatform = obj;
    }

    /* @model: */
    public String getPayPlatform() {
        return this.payPlatform;
    }

    /* @model: */
    public void setTid(String obj) {
        this.tid = obj;
    }

    /* @model: */
    public String getTid() {
        return this.tid;
    }

    /* @model: */
    public void setTradeTime(Date obj) {
        this.tradeTime = obj;
    }

    /* @model: */
    public Date getTradeTime() {
        return this.tradeTime;
    }

    /* @model: */
    public void setPayAmount(double obj) {
        this.payAmount = obj;
    }

    /* @model: */
    public double getPayAmount() {
        return this.payAmount;
    }

    /* @model: */
    public void setPayId(String obj) {
        this.payId = obj;
    }

    /* @model: */
    public String getPayId() {
        return this.payId;
    }

    /* @model: */
    public void setPayDest(String obj) {
        this.payDest = obj;
    }

    /* @model: */
    public String getPayDest() {
        return this.payDest;
    }

    /* @model: */
    public void setBuyAccountNu(String obj) {
        this.buyAccountNu = obj;
    }

    /* @model: */
    public String getBuyAccountNu() {
        return this.buyAccountNu;
    }

    /* @model: */
    public void setBuyer(String obj) {
        this.buyer = obj;
    }

    /* @model: */
    public String getBuyer() {
        return this.buyer;
    }

    /* @model: */
    public void setSellerAccountNo(String obj) {
        this.sellerAccountNo = obj;
    }

    /* @model: */
    public String getSellerAccountNo() {
        return this.sellerAccountNo;
    }

    /* @model: */
    public void setSeller(String obj) {
        this.seller = obj;
    }

    /* @model: */
    public String getSeller() {
        return this.seller;
    }

    /* @model: */
    public void setBatchNo(String obj) {
        this.batchNo = obj;
    }

    /* @model: */
    public String getBatchNo() {
        return this.batchNo;
    }

    /* @model: */
    public void setVoucherNo(String obj) {
        this.voucherNo = obj;
    }

    /* @model: */
    public String getVoucherNo() {
        return this.voucherNo;
    }

    /* @model: */
    public void setPayStatus(String obj) {
        this.payStatus = obj;
    }

    /* @model: */
    public String getPayStatus() {
        return this.payStatus;
    }

    /* @model: */
    public void setGmtPlatformPaytime(Date obj) {
        this.gmtPlatformPaytime = obj;
    }

    /* @model: */
    public Date getGmtPlatformPaytime() {
        return this.gmtPlatformPaytime;
    }

    /* @model: */
    public void setPlatformPayDemo(String obj) {
        this.platformPayDemo = obj;
    }

    /* @model: */
    public String getPlatformPayDemo() {
        return this.platformPayDemo;
    }

    /* @model: */
    public void setPlatformPayType(String obj) {
        this.platformPayType = obj;
    }

    /* @model: */
    public String getPlatformPayType() {
        return this.platformPayType;
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
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model: */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    public Double getAgentSellAmount() {
        return agentSellAmount;
    }

    public void setAgentSellAmount(Double agentSellAmount) {
        this.agentSellAmount = agentSellAmount;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
