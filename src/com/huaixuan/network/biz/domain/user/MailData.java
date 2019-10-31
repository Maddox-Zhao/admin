package com.huaixuan.network.biz.domain.user;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送邮件数据封装类
 * @author chenyan 2009/12/09
 */
public class MailData implements Serializable{

    private static final long serialVersionUID = -6531466114519038681L;

    //邮件模板
    private String mailId;
    //收件人
    private String toAddress;
    //邮件标题
    private String subject;
    
    //邮件中的数据
    @SuppressWarnings("unchecked")
    private Map data;
    
    public String getMailId() {
        return mailId;
    }
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    @SuppressWarnings("unchecked")
    public Map getData() {
        return data;
    }
    @SuppressWarnings("unchecked")
    public void setData(Map data) {
        this.data = data;
    }
}
