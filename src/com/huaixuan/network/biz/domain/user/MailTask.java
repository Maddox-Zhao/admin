package com.huaixuan.network.biz.domain.user;

 import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

 public class MailTask extends BaseObject implements Serializable {
     private long id;

     private long userId;
     
     private String context;
     
     private String isSend;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the isSend
     */
    public String getIsSend() {
        return isSend;
    }

    /**
     * @param isSend the isSend to set
     */
    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }
 }
