/**
 * created since 2009-8-8
 */
package com.huaixuan.network.biz.domain.counter;

import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 文件批次查询封装vo
 * @author guoyj
 * @version $Id: InputFileSearchBean.java,v 0.1 2010-6-7 上午11:01:29 guoyj Exp $
 */
public class InputFileSearchBean {

    private String       datestart;             //开始时间

    private String       dateend;               //结束时间

    private String       operateType;           //操作类型

    private String       bankType;              //银行类型

    private String       operator;              //订单处理人员

    private String       batchId;               //导入文件批次号

    private int          compareSuccessCount;   //对账报表，2边统计成功的条数

    private int          compareNotSuccessCount; //对账报表，2边统计不成功和金额不等的条数

    private String       createDate;            //导入文件创建时间

    private List<String> failMessageList;       //意外数据恢复输出消息
    
    private int waiteDealCount;//等待处理条数
    
    private int sumNotEqualCount;//金额不等条数

    public InputFileSearchBean(String datestart, String dateend, String operateType,
                               String bankType, String operator, String batchId,
                               int compareSuccessCount, int compareNotSuccessCount,
                               String createDate, List<String> failMessageList) {
        super();
        this.datestart = datestart;
        this.dateend = dateend;
        this.operateType = operateType;
        this.bankType = bankType;
        this.operator = operator;
        this.batchId = batchId;
        this.compareSuccessCount = compareSuccessCount;
        this.compareNotSuccessCount = compareNotSuccessCount;
        this.createDate = createDate;
        this.failMessageList = failMessageList;
    }

    public InputFileSearchBean() {
        super();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getCompareNotSuccessCount() {
        return compareNotSuccessCount;
    }

    public void setCompareNotSuccessCount(int compareNotSuccessCount) {
        this.compareNotSuccessCount = compareNotSuccessCount;
    }

    public int getCompareSuccessCount() {
        return compareSuccessCount;
    }

    public void setCompareSuccessCount(int compareSuccessCount) {
        this.compareSuccessCount = compareSuccessCount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public List<String> getFailMessageList() {
        return failMessageList;
    }

    public void setFailMessageList(List<String> failMessageList) {
        this.failMessageList = failMessageList;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-508425303, -1235126127).appendSuper(super.hashCode()).append(
            this.dateend).append(this.datestart).append(this.failMessageList).append(
            this.operateType).append(this.compareSuccessCount).append(this.bankType).append(
            this.compareNotSuccessCount).append(this.batchId).append(this.createDate).append(
            this.operator).toHashCode();
    }

	public int getWaiteDealCount() {
		return waiteDealCount;
	}

	public void setWaiteDealCount(int waiteDealCount) {
		this.waiteDealCount = waiteDealCount;
	}

	public int getSumNotEqualCount() {
		return sumNotEqualCount;
	}

	public void setSumNotEqualCount(int sumNotEqualCount) {
		this.sumNotEqualCount = sumNotEqualCount;
	}

}
