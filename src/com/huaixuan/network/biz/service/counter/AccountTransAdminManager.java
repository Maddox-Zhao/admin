package com.huaixuan.network.biz.service.counter;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.TransLogApp;
import com.huaixuan.network.biz.domain.counter.TransApp;
import com.huaixuan.network.biz.query.QueryPage;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.domain.base.TransLogDO;

;

/**
 * 帐务后台服务接口
 *
 * @author guoyj
 * @version $Id: BankPayOnlineAdminManager.java,v 0.1 2010-6-7 下午2:33:47 guoyj
 *          Exp $
 */
public interface AccountTransAdminManager {

    /**
     * 根据账务流水ID取得该笔事务流水账务信息
     *
     * @param accountLogId
     * @return
     */
    public List<TransLogDO> getTransLogByAccountLogId(long accountLogId);

    /**
     * 进行账务充正操作
     *
     * @param transLog
     * @return
     */
    public String doAccountTransAjust(TransLogDO transLog, Map parMap);

    /**
     * 账务冲正申请
     *
     * @param transLog
     * @return
     */
    public String doAccountTransAjustApp(TransLogDO transLog);

    /**
     * 进行帐务补帐操作
     *
     * @param accTransReq
     * @return
     */
    public String doAddAccTrans(AccountTransReq accTransReq, Map<String, String> parMap);

    /**
     * 进行帐务补帐申请操作
     *
     * @param transApp
     * @return
     */
    public String doTransApp(TransApp transApp);

    /**
     * 统计账务补帐数量
     *
     * @param parMap
     * @return
     */
    public int getManagerTransAppCount(Map parMap);

    /**
     * 账务补帐列表
     *
     * @param parMap
     * @param page
     * @return
     */
    // public List<TransApp> getManagerTransApp(Map parMap, Page page);
    QueryPage getManagerTransAppQuery(Map parMap, int currPage, int pageSize);

    /**
     * 获取单个补帐信息
     *
     * @param parMap
     * @return
     */
    public TransApp getTransAppInfo(Map parMap);

    /**
     * 统计账务冲正数量
     *
     * @param parMap
     * @return
     */
    public int getManagerTransLogAppCount(Map parMap);

    /**
     * 账务冲正列表
     *
     * @param parMap
     * @param page
     * @return
     */
    // public List<TransLogApp> getManagerTransLogApp(Map parMap, Page page);
	/**
	 * 账务冲正列表
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	public QueryPage getManagerTransLogApp(Map parMap, int currPage,
			int pageSize);

    /**
     * 账务冲正信息
     *
     * @param parMap
     * @return
     */
    public TransLogApp getTransLogAppInfo(Map parMap);

    /**
     *
     * @param accountLogId
     * @return
     */
    public TransLogDO getTransLogByLogId(long transLogId);

}
