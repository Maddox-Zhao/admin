package com.huaixuan.network.biz.domain.user;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserPointsWatertable {

    //主键
    private long              id;

    //积分点数
    private int               points;

    //外部编号
    private String            outerId;

    //外部编号类型： 如订单编号，活动编号
    private String            outerType;

    //该积分的会员ID
    private long              userId;

    //昵称
    private String            userNick;

    //积分流水类型 acquire获取积分 use 使用积分 freeze冻结积分 unfreeze 解冻积分 expire过期积分
    private String            type;

    //创建时间
    private Date              gmtCreate;

    //修改时间
    private Date              gmtModify;

    //查询日期起
    private Date              startDate;

    //查询日期止
    private Date              endDate;

    /* Default constructor - creates a new instance with no values set. */
    public UserPointsWatertable() {
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
    public void setPoints(int obj) {
        this.points = obj;
    }

    /* @model: */
    public int getPoints() {
        return this.points;
    }

    /* @model: */
    public void setOuterId(String obj) {
        this.outerId = obj;
    }

    /* @model: */
    public String getOuterId() {
        return this.outerId;
    }

    /* @model: */
    public void setOuterType(String obj) {
        this.outerType = obj;
    }

    /* @model: */
    public String getOuterType() {
        return this.outerType;
    }

    /* @model: */
    public void setUserId(long obj) {
        this.userId = obj;
    }

    /* @model: */
    public long getUserId() {
        return this.userId;
    }

    /* @model: */
    public void setUserNick(String obj) {
        this.userNick = obj;
    }

    /* @model: */
    public String getUserNick() {
        return this.userNick;
    }

    /* @model: */
    public void setType(String obj) {
        this.type = obj;
    }

    /* @model: */
    public String getType() {
        return this.type;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPointsWatertable)) {
            return false;
        }
        final UserPointsWatertable userpointswatertable = (UserPointsWatertable) o;
        return this.hashCode() == userpointswatertable.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("points", this.points).append("outerId", this.outerId).append(
            "outerType", this.outerType).append("userId", this.userId).append("userNick",
            this.userNick).append("type", this.type).append("gmtCreate", this.gmtCreate).append(
            "gmtModify", this.gmtModify);
        return sb.toString();
    }
}
