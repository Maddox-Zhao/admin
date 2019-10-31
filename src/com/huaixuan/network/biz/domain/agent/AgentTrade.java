package com.huaixuan.network.biz.domain.agent;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;
import com.hundsun.network.melody.common.util.StringUtil;

public class AgentTrade extends BaseObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2898396584941370396L;

    private Long              id;

    private Date              gmtCreate;

    private Date              gmtModify;

    private Long              userId;

    private String            tid;

    private Long              orderId;

    private String            catCode;

    private Double            price;

    private Long              amount;

    private Double            amt;

    private Double            refundPrice;

    private Long              refundAmount;

    private String            refundType;

    private Long              goodsInstanceId;

    private Long              goodsId;

    private String            isReceive;

    private Date              gmtTradeFinish;

    private String            goodsPhoto;

    private String            goodsName;

    private String            goodsAgentstatus;

    private Double            goodsAmount;

    private Integer           goodsNumber;

    private Integer           refundNumber;

    private String            goodsSn;

    private String            status;

    private String            goodsStatus;

    private String            userNick;

    private Long              agentManagerId;                          //拓展人员ID

    private Long              tradeId;

    private String            agentManagerName;                        //拓展人员名称

    private double            factMoney;                               // 实际交易总金额

    private Long              returnId;                                // 返点规则ID

    private String            ruleName;                                //规则名称

    private String            goodCode;                                //商品编码

    private String            code;                                    //产品编码

    private String            sortType;

    private String            goodName;                                //商品名称

    private String            gmtCreateStart;
    private String            gmtCreateEnd;

    public void setCatCode(String catCode) {
        if (StringUtil.isNotBlank(catCode)) {
            String[] c = catCode.split("\\.");
            if (c.length > 0) {
                this.catCode = c[0];
                return;
            }
        }
        this.catCode = catCode;
    }

    public long getTotalAmount() {
        return amount - refundAmount;
    }

    public double getTotalPrice() {
        return price * amount - refundPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(String isReceive) {
        this.isReceive = isReceive;
    }

    public Date getGmtTradeFinish() {
        return gmtTradeFinish;
    }

    public void setGmtTradeFinish(Date gmtTradeFinish) {
        this.gmtTradeFinish = gmtTradeFinish;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsAgentstatus() {
        return goodsAgentstatus;
    }

    public void setGoodsAgentstatus(String goodsAgentstatus) {
        this.goodsAgentstatus = goodsAgentstatus;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getRefundNumber() {
        return refundNumber;
    }

    public void setRefundNumber(Integer refundNumber) {
        this.refundNumber = refundNumber;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public Long getAgentManagerId() {
        return agentManagerId;
    }

    public void setAgentManagerId(Long agentManagerId) {
        this.agentManagerId = agentManagerId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getAgentManagerName() {
        return agentManagerName;
    }

    public void setAgentManagerName(String agentManagerName) {
        this.agentManagerName = agentManagerName;
    }

    public double getFactMoney() {
        return factMoney;
    }

    public void setFactMoney(double factMoney) {
        this.factMoney = factMoney;
    }

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
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

    public String getCatCode() {
        return catCode;
    }

}
