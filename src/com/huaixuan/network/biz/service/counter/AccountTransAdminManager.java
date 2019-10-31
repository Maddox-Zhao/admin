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
 * �����̨����ӿ�
 *
 * @author guoyj
 * @version $Id: BankPayOnlineAdminManager.java,v 0.1 2010-6-7 ����2:33:47 guoyj
 *          Exp $
 */
public interface AccountTransAdminManager {

    /**
     * ����������ˮIDȡ�øñ�������ˮ������Ϣ
     *
     * @param accountLogId
     * @return
     */
    public List<TransLogDO> getTransLogByAccountLogId(long accountLogId);

    /**
     * ���������������
     *
     * @param transLog
     * @return
     */
    public String doAccountTransAjust(TransLogDO transLog, Map parMap);

    /**
     * �����������
     *
     * @param transLog
     * @return
     */
    public String doAccountTransAjustApp(TransLogDO transLog);

    /**
     * ���������ʲ���
     *
     * @param accTransReq
     * @return
     */
    public String doAddAccTrans(AccountTransReq accTransReq, Map<String, String> parMap);

    /**
     * �����������������
     *
     * @param transApp
     * @return
     */
    public String doTransApp(TransApp transApp);

    /**
     * ͳ������������
     *
     * @param parMap
     * @return
     */
    public int getManagerTransAppCount(Map parMap);

    /**
     * �������б�
     *
     * @param parMap
     * @param page
     * @return
     */
    // public List<TransApp> getManagerTransApp(Map parMap, Page page);
    QueryPage getManagerTransAppQuery(Map parMap, int currPage, int pageSize);

    /**
     * ��ȡ����������Ϣ
     *
     * @param parMap
     * @return
     */
    public TransApp getTransAppInfo(Map parMap);

    /**
     * ͳ�������������
     *
     * @param parMap
     * @return
     */
    public int getManagerTransLogAppCount(Map parMap);

    /**
     * ��������б�
     *
     * @param parMap
     * @param page
     * @return
     */
    // public List<TransLogApp> getManagerTransLogApp(Map parMap, Page page);
	/**
	 * ��������б�
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	public QueryPage getManagerTransLogApp(Map parMap, int currPage,
			int pageSize);

    /**
     * ���������Ϣ
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
