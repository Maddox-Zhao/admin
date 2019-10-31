package com.huaixuan.network.biz.domain.crm;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 维护记录表
 * @author chenyan  2010/08/04
 *
 */
public class ConnectRecord extends BaseObject implements Serializable {

    private static final long serialVersionUID = -6749662505779052636L;

    private Long              id;
    private Date              gmtCreate;
    private Date              gmtModify;
    private Long              userId;
    private String            type;
    private Date              gmtTime;
    private String            content;
    private String            status;
    private String            adminUser;

    private String            gmtConnectTimeStr;
    private String            connectContent;
    private String            gmtOrderTimeStr;
    private String            orderContent;

    private String            gmtTimeStart;
    private String            gmtTimeEnd;

    private String            userName;

    //是否允许修改(y:允许;n:不允许)
    private String            allowModify;

    public String getAllowModify() {
        return allowModify;
    }

    public void setAllowModify(String allowModify) {
        this.allowModify = allowModify;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGmtTimeStart() {
        return gmtTimeStart;
    }

    public void setGmtTimeStart(String gmtTimeStart) {
        this.gmtTimeStart = gmtTimeStart;
    }

    public String getGmtTimeEnd() {
        return gmtTimeEnd;
    }

    public void setGmtTimeEnd(String gmtTimeEnd) {
        this.gmtTimeEnd = gmtTimeEnd;
    }

    public String getGmtConnectTimeStr() {
        return gmtConnectTimeStr;
    }

    public void setGmtConnectTimeStr(String gmtConnectTimeStr) {
        this.gmtConnectTimeStr = gmtConnectTimeStr;
    }

    public String getConnectContent() {
        return connectContent;
    }

    public void setConnectContent(String connectContent) {
        this.connectContent = connectContent;
    }

    public String getGmtOrderTimeStr() {
        return gmtOrderTimeStr;
    }

    public void setGmtOrderTimeStr(String gmtOrderTimeStr) {
        this.gmtOrderTimeStr = gmtOrderTimeStr;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getGmtTime() {
        return gmtTime;
    }

    public void setGmtTime(Date gmtTime) {
        this.gmtTime = gmtTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }
}
