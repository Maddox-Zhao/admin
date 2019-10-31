package com.huaixuan.network.biz.dao.user;

import java.util.Map;

import com.huaixuan.network.biz.domain.user.MailTask;
import com.huaixuan.network.biz.query.QueryPage;

public interface MailtaskDao {
    void addMailTask(MailTask mailTask) throws Exception;
    
    QueryPage getMailTaskList(Map parMap, int currentPage, int pageSize);
    
    void updateMailTask(MailTask mailTask);
}
