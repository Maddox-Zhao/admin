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
     * ����������ˮIDȡ�øñ�������ˮ������Ϣ
     * @param accountLogId
     * @return
     */
    public TransLogDO getTransLogByAccountLogId(long accountLogId);

    /**
     * ����transLogIdȡ��transLog
     * @param transLogId
     * @return
     */
    public TransLogDO getTransLogById(long transLogId);

    /**
     * �����������������
     * @return
     */
    public Long addTransApp(TransApp transApp);

    /**
     * ͳ������������
     * @param parMap
     * @return
     */
    public int getManagerTransAppCount(Map parMap);

    /**
     * �������б�
     * @param parMap
     * @param page
     * @return
     */
    public List<TransApp> getManagerTransApp(Map parMap);

    /**
     * ��ȡ����������Ϣ
     * @param parMap
     * @return
     */
    public TransApp getTransAppInfo(Map parMap);

    /**
     * ����״
     * @param parMap
     * @return
     */
    public int updateTransAppInfo(Map parMap);

    public TransLogApp getTransLogAppInfo(Map parMap);

    public long addTransLogApp(TransLogApp transLogApp);

    /**
     * ͳ�������������
     * @param parMap
     * @return
     */
    public int getManagerTransLogAppCount(Map parMap);

    /**
     * ��������б�
     * @param parMap
     * @param page
     * @return
     */
    public List<TransLogApp> getManagerTransLogApp(Map parMap);

    /**
     * ����״
     * @param parMap
     * @return
     */
    public int updateTransLogAppInfo(Map parMap);

}
