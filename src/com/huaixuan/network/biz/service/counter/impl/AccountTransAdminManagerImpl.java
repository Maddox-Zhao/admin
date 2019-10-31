package com.huaixuan.network.biz.service.counter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.AccountTransDao;
import com.huaixuan.network.biz.domain.TransLogApp;
import com.huaixuan.network.biz.domain.counter.TransApp;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.counter.AccountTransAdminManager;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;
import com.hundsun.itrans.biz.manager.AccountTransManager;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.domain.base.TransLogDO;

/**
 * 帐务台服务实现
 *
 * @author guoyj
 * @version $Id: BankPayOnlineAdminManagerImpl.java,v 0.1 2010-6-7 下午14:30:11
 *          guoyj Exp $
 */
@Service("accountTransAdminManager")
public class AccountTransAdminManagerImpl implements AccountTransAdminManager {

    protected final Log         log = LogFactory.getLog(getClass());
    @Autowired
    private AccountTransDao     accountTransDao;
    @Autowired
    private AccountTransManager accountTransManager;

    /**
     * 根据账务流水ID取得该笔事务流水账务信息
     *
     * @param accountLogId
     * @return
     * @see com.hundsun.bible.facade.counter.BankPayOnlineAdminManager#getTransLogByAccountLogId(long)
     */
    @Override
    public List<TransLogDO> getTransLogByAccountLogId(long accountLogId) {
        List<TransLogDO> transLogs = new ArrayList<TransLogDO>();
        TransLogDO formerTransLog = accountTransDao.getTransLogByAccountLogId(accountLogId);
        if (formerTransLog != null) {
            transLogs.add(formerTransLog);
            if (formerTransLog.getFlagCancel() != null) {
                TransLogDO destTransLog = accountTransDao.getTransLogById(formerTransLog
                    .getRelatedTransId());
                transLogs.add(destTransLog);
            }
        }
        return transLogs;
    }

    /**
     * 进行账务充正操作
     *
     * @param transLog
     * @return
     * @see com.hundsun.bible.facade.counter.BankPayOnlineAdminManager#doAccountTransAjust(long)
     */
    @Override
    public String doAccountTransAjust(TransLogDO transLog, Map parMap) {
        String message = AccountTransResult.TXN_RESULT_SUCCESS.getMessage();

        if (((String) parMap.get("status")).equals("fail")) {
            accountTransDao.updateTransLogAppInfo(parMap);
        } else if (((String) parMap.get("status")).equals("success")) {
            AccountTransReq req = new AccountTransReq();
            req.setTransLogId(transLog.getId());
            req.setMemo(transLog.getTransMemo());
            req.setOperator(transLog.getTransOperator());
            req.setSubTransCode(EnumSubTransCode.TXCODE_FINACE_WRITEOFF);
            AccountTransResult result = accountTransManager.execute(req);

            if (result.getMessage().equals(AccountTransResult.TXN_RESULT_SUCCESS.getMessage())) {
                accountTransDao.updateTransLogAppInfo(parMap);
            }

            message = result.getMessage();
        }
        return message;
    }

    /**
     * 账务冲正申请
     */
    @Override
    public String doAccountTransAjustApp(TransLogDO transLog) {
        Map parMap = new HashMap();
        parMap.put("transLogId", transLog.getId());
        parMap.put("statusList", new String[] { "new", "success" });
        TransLogApp transLogApp = accountTransDao.getTransLogAppInfo(parMap);
        if (transLogApp != null) {
            return "counter.app.exist";
        }

        transLogApp = new TransLogApp();
        transLogApp.setStatus("new");
        transLogApp.setTransLogId(transLog.getId());
        transLogApp.setTransMemo(transLog.getTransMemo());

        long transLogAppId = accountTransDao.addTransLogApp(transLogApp);
        return transLogAppId > 0 ? "success" : "fail";
    }

    /**
     * 进行帐务补帐操作
     *
     * @param accTransReq
     * @return
     * @see com.hundsun.bible.facade.counter.BankPayOnlineAdminManager#doAddAccTrans(com.hundsun.bible.domain.model.acctrans.AccountTransReq)
     */
    @Override
    public String doAddAccTrans(AccountTransReq accTransReq, Map<String, String> parMap) {
        String message = AccountTransResult.TXN_RESULT_SUCCESS.getMessage();
        if (((String) parMap.get("status")).equals("fail")) {
            accountTransDao.updateTransAppInfo(parMap);
        } else if (((String) parMap.get("status")).equals("success")) {
            AccountTransResult result = accountTransManager.execute(accTransReq);
            if (result.getMessage().equals(AccountTransResult.TXN_RESULT_SUCCESS.getMessage())) {
                accountTransDao.updateTransAppInfo(parMap);
            }
            message = result.getMessage();
        }
        return message;
    }

    @Override
    public String doTransApp(TransApp transApp) {
        Long id = this.accountTransDao.addTransApp(transApp);
        String message = id > 0 ? "counter.app.success" : "counter.app.fail";
        return message;
    }

    @Override
    public int getManagerTransAppCount(Map parMap) {
        log.info("AccountTransAdminManagerImpl.getManagerTransAppCount method");
        try {
            return this.accountTransDao.getManagerTransAppCount(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    // public List<TransApp> getManagerTransApp(Map parMap, Page page) {
    // log.info("AccountTransAdminManagerImpl.getManagerTransApp method");
    // try {
    // return this.accountTransDao.getManagerTransApp(parMap, page);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return null;
    // }
    @Override
    public TransApp getTransAppInfo(Map parMap) {
        log.info("AccountTransAdminManagerImpl.getTransAppInfo method");
        return this.accountTransDao.getTransAppInfo(parMap);
    }

    @Override
    public int getManagerTransLogAppCount(Map parMap) {
        log.info("AccountTransAdminManagerImpl.getManagerTransLogAppCount method");
        try {
            return this.accountTransDao.getManagerTransLogAppCount(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    // public List<TransLogApp> getManagerTransLogApp(Map parMap, Page page) {
    // log.info("AccountTransAdminManagerImpl.getManagerTransLogApp method");
    // try {
    // return this.accountTransDao.getManagerTransLogApp(parMap, page);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return null;
    // }
    @Override
    public TransLogApp getTransLogAppInfo(Map parMap) {
        TransLogApp transLogApp = this.accountTransDao.getTransLogAppInfo(parMap);
        return transLogApp;
    }

    @Override
    public TransLogDO getTransLogByLogId(long transLogId) {

        return this.accountTransDao.getTransLogById(transLogId);
    }

    @Override
    public QueryPage getManagerTransAppQuery(Map parMap, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);

        int count = this.accountTransDao.getManagerTransAppCount(parMap);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());

            List<TransApp> transAppList = this.accountTransDao.getManagerTransApp(parMap);
            if (transAppList != null && transAppList.size() > 0) {
                queryPage.setItems(transAppList);
            }
        }

        return queryPage;
    }
    
    @Override
    public QueryPage getManagerTransLogApp(Map parMap, int currPage,
            int pageSize) {
        log.info("AccountTransAdminManagerImpl.getManagerTransLogApp method");
        try {
            TransLogApp transLogApp = new TransLogApp();
            QueryPage queryPage = new QueryPage(transLogApp);

            int count = accountTransDao.getManagerTransLogAppCount(parMap);

            if (count > 0) {

                /* 当前页 */
                queryPage.setCurrentPage(currPage);
                /* 每页总数 */
                queryPage.setPageSize(pageSize);
                queryPage.setTotalItem(count);

                parMap.put("startRow",queryPage.getPageFristItem());
                parMap.put("endRow",queryPage.getPageLastItem());

                /* 分页查询操作员记录 */
                List<TransLogApp> transLogAppList = accountTransDao
                        .getManagerTransLogApp(parMap);
                if (transLogAppList != null && transLogAppList.size() > 0) {
                    queryPage.setItems(transLogAppList);
                }
            }
            return queryPage;
        } catch (Exception e) {
            // log.error(e.getMessage());
            // throw new ManagerException(e);
            return null;
        }
    }

}
