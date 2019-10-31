package com.huaixuan.network.biz.domain.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserInfo {
	
    protected Log             log              = LogFactory.getLog(this.getClass());
    /* @property: */
    private long              id;
    /* @property: */
    private long              userId;
    /* @property: */
    private long              address_id;
    /* @property: */
    private String            userName;
    /* @property: */
    private Date              birthday;
    /* @property: */
    private String            birthday_str;
    /* @property: */
    private String            qq;
    /* @property: */
    private String            msn;
    /* @property: */
    private String            wangwang;
    /* @property: */
    private String            officePhone;
    /* @property: */
    private String            homePhone;
    /* @property: */
    private String            mobilePhone;
    /* @property: */
    private String            question;
    /* @property: */
    private String            answer;

    private Long              tradeAddressId;
    private String            province;
    private String            city;
    private String            howKnow;

    public String getHowKnow() {
        return howKnow;
    }

    public void setHowKnow(String howKnow) {
        this.howKnow = howKnow;
    }

    /* Default constructor - creates a new instance with no values set. */
    public UserInfo() {
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
    public void setUserName(String obj) {
        this.userName = obj;
    }

    /* @model: */
    public String getUserName() {
        return this.userName;
    }

    /* @model: */
    public void setBirthday(Date obj) {
        this.birthday = obj;
    }

    /* @model: */
    public Date getBirthday() {
        return this.birthday;
    }

    /* @model: */
    public void setQq(String obj) {
        this.qq = obj;
    }

    /* @model: */
    public String getQq() {
        return this.qq;
    }

    /* @model: */
    public void setMsn(String obj) {
        this.msn = obj;
    }

    /* @model: */
    public String getMsn() {
        return this.msn;
    }

    /* @model: */
    public void setWangwang(String obj) {
        this.wangwang = obj;
    }

    /* @model: */
    public String getWangwang() {
        return this.wangwang;
    }

    /* @model: */
    public void setOfficePhone(String obj) {
        this.officePhone = obj;
    }

    /* @model: */
    public String getOfficePhone() {
        return this.officePhone;
    }

    /* @model: */
    public void setHomePhone(String obj) {
        this.homePhone = obj;
    }

    /* @model: */
    public String getHomePhone() {
        return this.homePhone;
    }

    /* @model: */
    public void setMobilePhone(String obj) {
        this.mobilePhone = obj;
    }

    /* @model: */
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    /* @model: */
    public void setQuestion(String obj) {
        this.question = obj;
    }

    public Long getTradeAddressId() {
        return tradeAddressId;
    }

    public void setTradeAddressId(Long tradeAddressId) {
        this.tradeAddressId = tradeAddressId;
    }

    /* @model: */
    public String getQuestion() {
        return this.question;
    }

    /* @model: */
    public void setAnswer(String obj) {
        this.answer = obj;
    }

    /* @model: */
    public String getAnswer() {
        return this.answer;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        final UserInfo userinfo = (UserInfo) o;
        return this.hashCode() == userinfo.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("userId", this.userId).append("userName", this.userName).append(
            "birthday", this.birthday).append("qq", this.qq).append("msn", this.msn).append(
            "wangwang", this.wangwang).append("officePhone", this.officePhone).append("homePhone",
            this.homePhone).append("mobilePhone", this.mobilePhone).append("question",
            this.question).append("answer", this.answer);
        return sb.toString();
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
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

    public String getBirthday_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        if (getBirthday() != null) {
            return df.format(getBirthday());
        } else {
            return null;
        }
    }

    public void setBirthday_str(String birthday_str) {
        this.birthday_str = birthday_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        Date ts = null;
        if (StringUtils.isNotBlank(birthday_str)) {
            try {
                ts = df.parse(birthday_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.birthday = ts;

    }
}
