package com.huaixuan.network.biz.service.user;

import java.util.Map;

import javax.mail.MessagingException;

import com.huaixuan.network.biz.domain.user.MailData;

public interface MailEngineBase extends Runnable {

    public void setMailData(MailData mailData);

    /**
     * 发送邮件。
     * 
     * @param mailId 邮件ID(配置在email.xml中的id)
     * @param toAddress 收件人地址
     * @param data 邮件中的数据
     */
    public void sendMessage(String recipients, String subject, String templateName, Map model)
                                                                                              throws MessagingException;
//    public MailEngine getMailEngine();
}
