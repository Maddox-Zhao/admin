package com.huaixuan.network.biz.dao.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.TransLogApp;
import com.huaixuan.network.biz.domain.counter.TransApp;
import com.hundsun.itrans.domain.base.TransLogDO;

/**
 * @version 3.2.0
 */
public interface AccountTransDao {

    /**
     * 根据账务流水ID取得该笔事务流水账务信息
     * @param accountLogId
     * @return
     */
    public TransLogDO getTransLogByAccountLogId(long accountLogId);

    /**
     * 根据transLogId取得transLog
     * @param transLogId
     * @return
     */
    public TransLogDO getTransLogById(long transLogId);

    /**
     * 进行帐务补帐申请操作
     * @return
     */
    public Long addTransApp(TransApp transApp);

    /**
     * 统计账务补帐数量
     * @param parMap
     * @return
     */
    public int getManagerTransAppCount(Map parMap);

    /**
     * 账务补帐列表
     * @param parMap
     * @param page
     * @return
     */
    public List<TransApp> getManagerTransApp(Map parMap);

    /**
     * 获取单个补帐信息
     * @param parMap
     * @return
     */
    public TransApp getTransAppInfo(Map parMap);

    /**
     * 更新状
     * @param parMap
     * @return
     */
    public int updateTransAppInfo(Map parMap);

    public TransLogApp getTransLogAppInfo(Map parMap);

    public long addTransLogApp(TransLogApp transLogApp);

    /**
     * 统计账务冲正数量
     * @param parMap
     * @return
     */
    public int getManagerTransLogAppCount(Map parMap);

    /**
     * 账务冲正列表
     * @param parMap
     * @param page
     * @return
     */
    public List<TransLogApp> getManagerTransLogApp(Map parMap);

    /**
     * 更新状
     * @param parMap
     * @return
     */
    public int updateTransLogAppInfo(Map parMap);

}
