package com.huaixuan.network.biz.domain.user;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserPoints {
    private static final long serialVersionUID = -4554018611867689671L;

    //主键ID
    private long              id;

    //会员ID
    private long              userId;

    //会员昵称
    private String            userNick;

    //邮箱
    private String            email;

    //固定电话
    private String            home_phone;

    //手机
    private String            mobile_phone;

    //可用积分
    private int               usablePoints;

    //下限积分
    private int               minPoints;

    //上限积分
    private int               maxPoints;

    //冻结积分
    private int               freezePoints;

    //创建时间
    private Date              gmtCreate;

    //修改时间
    private Date              gmtModify;

    //操作类型：下单(order)冻结、支付(pay)使用积分,冻结积分减少、
    //取消订单(cancelOrder)解冻、交易成功(transaction)发放积分
    private String            type;

    //本次操作点数
    private int               points;

    /* Default constructor - creates a new instance with no values set. */
    public UserPoints() {
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
    public void setUsablePoints(int obj) {
        this.usablePoints = obj;
    }

    /* @model: */
    public int getUsablePoints() {
        return this.usablePoints;
    }

    /* @model: */
    public void setFreezePoints(int obj) {
        this.freezePoints = obj;
    }

    /* @model: */
    public int getFreezePoints() {
        return this.freezePoints;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPoints)) {
            return false;
        }
        final UserPoints userpoints = (UserPoints) o;
        return this.hashCode() == userpoints.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("userId", this.userId).append("userNick", this.userNick).append(
            "usablePoints", this.usablePoints).append("freezePoints", this.freezePoints).append(
            "gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify);
        return sb.toString();
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHome_phone() {
		return home_phone;
	}

	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}
}
