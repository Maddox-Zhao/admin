package com.huaixuan.network.biz.service.user;

import java.util.Map;

import com.huaixuan.network.biz.domain.user.MailTask;
import com.huaixuan.network.biz.query.QueryPage;

public interface MailtaskManager {

    /* @interface model: �����ʼ� */
    void addMailTask(MailTask mailTask);
    
    /* ÿ��ȡ100���ʼ����з��� */
    QueryPage getMailTaskList(Map parMap, int currentPage, int pageSize);
    
    /* �����ʼ�����״̬ */
    void updateMailTask(MailTask mailTask);
}
