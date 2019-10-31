package com.huaixuan.network.biz.service.user.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.MailtaskDao;
import com.huaixuan.network.biz.domain.user.MailTask;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.user.MailtaskManager;

@Service("mailtaskManager")
public class MailtaskManagerImpl implements MailtaskManager{

    @Autowired
    private MailtaskDao mailtaskDao;
    
    protected Log  log = LogFactory.getLog(this.getClass());
    
    @Override
    public void addMailTask(MailTask mailTask) {
        log.info("MailTaskManagerImpl.addMailTask method");
        try {
             mailtaskDao.addMailTask(mailTask);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    @Override
    public QueryPage getMailTaskList(Map parMap, int currentPage, int pageSize) {
        log.info("MailTaskManagerImpl.getMailTaskList method");
        try{
            return mailtaskDao.getMailTaskList(parMap,currentPage,pageSize);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
    @Override
    public void updateMailTask(MailTask mailTask) {
        log.info("MailTaskManagerImpl.updateMailTask method");
        try{
            mailtaskDao.updateMailTask(mailTask);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
