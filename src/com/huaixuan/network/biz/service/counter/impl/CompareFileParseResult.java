package com.huaixuan.network.biz.service.counter.impl;

import java.util.ArrayList;
import java.util.List;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.enums.EnumCompareFailReason;

/**
 * 对账明细导入结果
 * @author guoyj
 * @version $Id: CompareFileParseResult.java,v 0.1 2010-6-7 下午02:46:46 guoyj Exp $
 */
public class CompareFileParseResult {
    /**
     * 对账明细导入成功数
     */
    private int                         successCount    = 0;
    /**
     * 对账明细导入失败数
     */
    private int                         failCount       = 0;
    /**
     * 对账明细待导入列表,待对账记录
     */
    private List<BankCompareItem>       bankCompareList = new ArrayList<BankCompareItem>();
    /**
     * 失败原因列表
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
