package com.huaixuan.network.biz.service.counter.impl;

import java.util.ArrayList;
import java.util.List;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.enums.EnumCompareFailReason;

/**
 * ������ϸ������
 * @author guoyj
 * @version $Id: CompareFileParseResult.java,v 0.1 2010-6-7 ����02:46:46 guoyj Exp $
 */
public class CompareFileParseResult {
    /**
     * ������ϸ����ɹ���
     */
    private int                         successCount    = 0;
    /**
     * ������ϸ����ʧ����
     */
    private int                         failCount       = 0;
    /**
     * ������ϸ�������б�,�����˼�¼
     */
    private List<BankCompareItem>       bankCompareList = new ArrayList<BankCompareItem>();
    /**
     * ʧ��ԭ���б�
     */
    private List<EnumCompareFailReason> failReasonList  = new ArrayList<EnumCompareFailReason>();

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public List<BankCompareItem> getBankCompareList() {
        return bankCompareList;
    }

    public void setBankCompareList(List<BankCompareItem> bankCompareList) {
        this.bankCompareList = bankCompareList;
    }

    public List<EnumCompareFailReason> getFailReasonList() {
        return failReasonList;
    }

    public void setFailReasonList(List<EnumCompareFailReason> failReasonList) {
        this.failReasonList = failReasonList;
    }
}
