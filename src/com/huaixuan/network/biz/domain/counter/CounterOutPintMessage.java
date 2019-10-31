/**
 * created since 2009-8-3
 */
package com.huaixuan.network.biz.domain.counter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 对账文件导入输出参数bean
 * @author guoyj
 * @version $Id: CounterOutPintMessage.java,v 0.1 2010-6-7 上午09:55:11 guoyj Exp $
 */
public class CounterOutPintMessage {

    private String batchId;             //批次号

    private String accountFileName;     //文件名

    private String blankName;           //银行 

    private String importSuccessCount;  //导入成功条数

    private String importFailCount;     //导入失败条数

    private List<String>   failDescriptionListu=new ArrayList<String>(); //导入失败明细（订单号，错误原因）

    public CounterOutPintMessage() {
        super();
    }

    public CounterOutPintMessage(String batchId, String accountFileName, String blankName, String importSuccessCount, String importFailCount, List<String> failDescriptionListu) {
        super();
        this.batchId = batchId;
        this.accountFileName = accountFileName;
        this.blankName = blankName;
        this.importSuccessCount = importSuccessCount;
        this.importFailCount = importFailCount;
        this.failDescriptionListu = failDescriptionListu;
    }

    public String getAccountFileName() {
        return accountFileName;
    }

    public void setAccountFileName(String accountFileName) {
        this.accountFileName = accountFileName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBlankName() {
        return blankName;
    }

    public void setBlankName(String blankName) {
        this.blankName = blankName;
    }

    public List<String> getFailDescriptionListu() {
        return failDescriptionListu;
    }

    public void setFailDescriptionListu(List<String> failDescriptionListu) {
        this.failDescriptionListu = failDescriptionListu;
    }

    public String getImportFailCount() {
        return importFailCount;
    }

    public void setImportFailCount(String importFailCount) {
        this.importFailCount = importFailCount;
    }

    public String getImportSuccessCount() {
        return importSuccessCount;
    }

    public void setImportSuccessCount(String importSuccessCount) {
        this.importSuccessCount = importSuccessCount;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-39169353, 557828055).appendSuper(super.hashCode()).append(
            this.accountFileName).append(this.blankName).append(this.failDescriptionListu).append(
            this.importFailCount).append(this.importSuccessCount).append(this.batchId).toHashCode();
    }

 

}
