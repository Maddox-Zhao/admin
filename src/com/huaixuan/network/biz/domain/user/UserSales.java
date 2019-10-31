package com.huaixuan.network.biz.domain.user;

import java.io.Serializable;
import java.util.Date;

public class UserSales implements Serializable {

    private long   id;

    private long   userId;

    private long   adminId;

    private Date   optTime;

    private long   tradeNum;

    private double salesAmount;

    private double refundAmount;

    private String account;

    private String area;

    private String phone;

    private String userType;

    private String shopTYRank;

    private String shopPYRank;

    private String saleName;

    private long   rankNum;

    public long getRankNum() {
        return rankNum;
    }

    public void setRankNum(long rankNum) {
        this.rankNum = rankNum;
    }

    public String getShopTYRank() {
        return shopTYRank;
    }

    public void setShopTYRank(String shopTYRank) {
        this.shopTYRank = shopTYRank;
    }

    public String getShopPYRank() {
        return shopPYRank;
    }

    public void setShopPYRank(String shopPYRank) {
        this.shopPYRank = shopPYRank;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public long getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(long tradeNum) {
        this.tradeNum = tradeNum;
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

}
