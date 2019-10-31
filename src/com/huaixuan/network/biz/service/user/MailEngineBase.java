package com.huaixuan.network.biz.service.user;

import java.util.Map;

import javax.mail.MessagingException;

import com.huaixuan.network.biz.domain.user.MailData;

public interface MailEngineBase extends Runnable {

    public void setMailData(MailData mailData);

    /**
     * �����ʼ���
     * 
     * @param mailId �ʼ�ID(������email.xml�е�id)
     * @param toAddress �ռ��˵�ַ
     * @param data �ʼ��е�����
     */
    public void sendMessage(String recipients, String subject, String templateName, Map model)
                                                                                              throws MessagingException;
//    public MailEngine getMailEngine();
}
