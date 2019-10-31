package com.huaixuan.network.biz.service.user;

import java.util.Map;

import com.huaixuan.network.biz.domain.user.MailTask;
import com.huaixuan.network.biz.query.QueryPage;

public interface MailtaskManager {

    /* @interface model: 新增邮件 */
    void addMailTask(MailTask mailTask);
    
    /* 每次取100条邮件进行发送 */
    QueryPage getMailTaskList(Map parMap, int currentPage, int pageSize);
    
    /* 更新邮件发送状态 */
    void updateMailTask(MailTask mailTask);
}
