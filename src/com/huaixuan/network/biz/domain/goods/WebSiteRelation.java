package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

public class WebSiteRelation {
    private     Long    id;
    private     String      status;//状态
    private     String      fromUser;//发信人
    private     String      toUser;//收信人
    private     Long        webSiteId;//站内信ID
    private     Date        gmtCreate;
    private     Date        gmtModify;
    private     String      title;//标题
    private     String      content;//内容
    private     String      type;//站内信类型(system,private)


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
