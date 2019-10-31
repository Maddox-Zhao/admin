package com.huaixuan.network.biz.query;

import java.util.Date;
import java.util.List;

public class TradeListQuery extends WholesaleQuery {
    private Long         agentManagerId;
    private String       expressCode;
    private Long         id;
    private Long         buyUserId;
    private String       isWholesale;
    private String       tid;
    private String       type;
    private String       finishTime;
    private Date         gmtModify;
    private Date         gmtCreate;
    private String       receiver;
    private String       country;
    private String       province;
    private String       city;
    private String       district;
    private String       address;
    private String       zipcode;
    private String       mobile;
    private Integer      userPoint;
    private Integer      sendPoint;
    private String       notStatus;
    private List<Long>   depfirstIds;
    private String       opp;
    private String       stockoutStatus;
    private String       buyNick;
    private String       gmtCreateStart;
    private String       gmtCreateEnd;
    private String       status;
    private String       interTid;
    private Byte         tradeType;
    private Long         depFirstId;
    private String       wholesaleStatus;
    private String       isPurchased;
    private String       expressPayment;
    private String       payStatus;
    private String       invoice;
    private String       payStartTime;
    private String       payEndTime;
    private Integer      startRow;
    private Integer      endRow;
    private String       isReminders;
    private String       orderType;
    private String       send;
    private String       platformTimeStart;
    private String       platformTimeEnd;
    private String       payPlatform;
    private String       buyer;
    private String       batchNo;
    private String       finance;
    /** ¿ªÆ±¹«Ë¾ÐòºÅ */
    private Long         rorder;
    private List<String> tradeTypes;

    public List<String> getTradeTypes() {
        return tradeTypes;
    }

    public void setTradeTypes(List<String> tradeTypes) {
        this.tradeTypes = tradeTypes;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getPlatformTimeStart() {
        return platformTimeStart;
    }

    public void setPlatformTimeStart(String platformTimeStart) {
        this.platformTimeStart = platformTimeStart;
    }

    public String getPlatformTimeEnd() {
        return platformTimeEnd;
    }

    public void setPlatformTimeEnd(String platformTimeEnd) {
        this.platformTimeEnd = platformTimeEnd;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBuyNick() {
        return buyNick;
    }

    public void setBuyNick(String buyNick) {
        this.buyNick = buyNick;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInterTid() {
        return interTid;
    }

    public void setInterTid(String interTid) {
        this.interTid = interTid;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getWholesaleStatus() {
        return wholesaleStatus;
    }

    public void setWholesaleStatus(String wholesaleStatus) {
        this.wholesaleStatus = wholesaleStatus;
    }

    public String getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(String isPurchased) {
        this.isPurchased = isPurchased;
    }

    public String getExpressPayment() {
        return expressPayment;
    }

    public void setExpressPayment(String expressPayment) {
        this.expressPayment = expressPayment;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getPayStartTime() {
        return payStartTime;
    }

    public void setPayStartTime(String payStartTime) {
        this.payStartTime = payStartTime;
    }

    public String getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(String payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Byte getTradeType() {
        return tradeType;
    }

    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Long getAgentManagerId() {
        return agentManagerId;
    }

    public void setAgentManagerId(Long agentManagerId) {
        this.agentManagerId = agentManagerId;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Long buyUserId) {
        this.buyUserId = buyUserId;
    }

    public String getIsWholesale() {
        return isWholesale;
    }

    public void setIsWholesale(String isWholesale) {
        this.isWholesale = isWholesale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    public Integer getSendPoint() {
        return sendPoint;
    }

    public void setSendPoint(Integer sendPoint) {
        this.sendPoint = sendPoint;
    }

    public String getNotStatus() {
        return notStatus;
    }

    public void setNotStatus(String notStatus) {
        this.notStatus = notStatus;
    }

    public List<Long> getDepfirstIds() {
        return depfirstIds;
    }

    public void setDepfirstIds(List<Long> depfirstIds) {
        this.depfirstIds = depfirstIds;
    }

    public String getOpp() {
        return opp;
    }

    public void setOpp(String opp) {
        this.opp = opp;
    }

    public String getStockoutStatus() {
        return stockoutStatus;
    }

    public void setStockoutStatus(String stockoutStatus) {
        this.stockoutStatus = stockoutStatus;
    }

    public String getIsReminders() {
        return isReminders;
    }

    public void setIsReminders(String isReminders) {
        this.isReminders = isReminders;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public Long getRorder() {
        return rorder;
    }

    public void setRorder(Long rorder) {
        this.rorder = rorder;
    }

}
