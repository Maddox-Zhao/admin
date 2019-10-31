/**
 * created since 2010-6-7
 */
package com.huaixuan.network.biz.service.counter.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.BankCompareItemDao;
import com.huaixuan.network.biz.dao.counter.InputFileDao;
import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;
import com.huaixuan.network.biz.service.counter.ParseCompareFileTrans;

/**
 * @author guoyj
 * @version $Id: AbstractParseCompareFile.java,v 0.1 2010-6-7 下午2:41:31 guoyj Exp $
 */
@Service("baseParseCompareFileTrans")
public abstract class AbstractParseCompareFileTrans implements ParseCompareFileTrans {

    protected final Log           log = LogFactory.getLog(getClass());

    @Autowired
    public BankCompareItemDao     bankCompareItemDao;    //���ж�������dao

    @Autowired
    public InputFileDao inputFileDao;    //����dao


    public void setBankCompareItemDao(BankCompareItemDao bankCompareItemDao) {
		this.bankCompareItemDao = bankCompareItemDao;
	}

	public void setInputFileDao(InputFileDao inputFileDao) {
		this.inputFileDao = inputFileDao;
	}

	/**
     * ���������ļ����������ж�Ҫʵ�ִ˷���
     * @param req
     * @return
     */
    public CounterOutPintMessage doParseFile(CounterCompareReq req) {
        return getParseFile(req);
    }

    /**
     * ����������Ҫʵ�ֵĽ����ļ��ķ���
     * @param req
     * @return
     */
    protected abstract CounterOutPintMessage getParseFile(CounterCompareReq req);

    /**
     * ������Ӷ����ļ���¼
     * @param counterType  �����ļ�����
     * @param itemListu    Ҫ��ӵ�listu
     * @return
     */
    protected boolean addBatchCompareItem(String operateType, List itemListu) {
        if (operateType.equals("C")) //�������������

            bankCompareItemDao.addBeankCompareItemList(itemListu);

      

        return true;
    }

}
