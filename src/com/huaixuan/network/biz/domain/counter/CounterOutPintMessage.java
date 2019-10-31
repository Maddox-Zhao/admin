/**
 * created since 2009-8-3
 */
package com.huaixuan.network.biz.domain.counter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * �����ļ������������bean
 * @author guoyj
 * @version $Id: CounterOutPintMessage.java,v 0.1 2010-6-7 ����09:55:11 guoyj Exp $
 */
public class CounterOutPintMessage {

    private String batchId;             //���κ�

    private String accountFileName;     //�ļ���

    private String blankName;           //���� 

    private String importSuccessCount;  //����ɹ�����

    private String importFailCount;     //����ʧ������

    private List<String>   failDescriptionListu=new ArrayList<String>(); //����ʧ����ϸ�������ţ�����ԭ��

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
