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
 * @version $Id: AbstractParseCompareFile.java,v 0.1 2010-6-7 涓嬪崍2:41:31 guoyj Exp $
 */
@Service("baseParseCompareFileTrans")
public abstract class AbstractParseCompareFileTrans implements ParseCompareFileTrans {

    protected final Log           log = LogFactory.getLog(getClass());

    @Autowired
    public BankCompareItemDao     bankCompareItemDao;    //银行对账数据dao

    @Autowired
    public InputFileDao inputFileDao;    //对账dao


    public void setBankCompareItemDao(BankCompareItemDao bankCompareItemDao) {
		this.bankCompareItemDao = bankCompareItemDao;
	}

	public void setInputFileDao(InputFileDao inputFileDao) {
		this.inputFileDao = inputFileDao;
	}

	/**
     * 解析对账文件，所有银行都要实现此方法
     * @param req
     * @return
     */
    public CounterOutPintMessage doParseFile(CounterCompareReq req) {
        return getParseFile(req);
    }

    /**
     * 各银行真正要实现的解析文件的方法
     * @param req
     * @return
     */
    protected abstract CounterOutPintMessage getParseFile(CounterCompareReq req);

    /**
     * 批量添加对账文件记录
     * @param counterType  对账文件类型
     * @param itemListu    要添加的listu
     * @return
     */
    protected boolean addBatchCompareItem(String operateType, List itemListu) {
        if (operateType.equals("C")) //如果是网银对账

            bankCompareItemDao.addBeankCompareItemList(itemListu);

      

        return true;
    }

}
