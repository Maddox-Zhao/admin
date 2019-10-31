package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

public class WebSiteRelation {
    private     Long    id;
    private     String      status;//״̬
    private     String      fromUser;//������
    private     String      toUser;//������
    private     Long        webSiteId;//վ����ID
    private     Date        gmtCreate;
    private     Date        gmtModify;
    private     String      title;//����
    private     String      content;//����
    private     String      type;//վ��������(system,private)


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFromUser() {
        return fromUser;
    }
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
    public String getToUser() {
        return toUser;
    }
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
    public Long getWebSiteId() {
        return webSiteId;
    }
    public void setWebSiteId(Long webSiteId) {
        this.webSiteId = webSiteId;
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
