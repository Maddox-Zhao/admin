/**
 * created since 2009-8-3
 */
package com.huaixuan.network.biz.domain.counter;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * �õ���������˲��� 
 * @author guoyj
 * @version $Id: CounterCompareReq.java,v 0.1 2010-6-7 ����09:58:40 guoyj Exp $
 */
public class CounterCompareReq {

    private String compareAccountType; //������������

    private String compareAccountFile; //�����ļ�����
    
    private String compareReNameAccountFile;  //�����ļ��ϴ�����ļ�����

    private String dealOperator;      //���˴�����Ա

    public CounterCompareReq() {
        super();
    }

    public CounterCompareReq(String compareAccountType, String compareAccountFile, String compareReNameAccountFile, String dealOperator) {
        super();
        this.compareAccountType = compareAccountType;
        this.compareAccountFile = compareAccountFile;
        this.compareReNameAccountFile = compareReNameAccountFile;
        this.dealOperator = dealOperator;
    }

    public String getCompareAccountFile() {
        return compareAccountFile;
    }

    public void setCompareAccountFile(String compareAccountFile) {
        this.compareAccountFile = compareAccountFile;
    }

    public String getCompareAccountType() {
        return compareAccountType;
    }

    public void setCompareAccountType(String compareAccountType) {
        this.compareAccountType = compareAccountType;
    }

    public String getCompareReNameAccountFile() {
        return compareReNameAccountFile;
    }

    public void setCompareReNameAccountFile(String compareReNameAccountFile) {
        this.compareReNameAccountFile = compareReNameAccountFile;
    }

    public String getDealOperator() {
        return dealOperator;
    }

    public void setDealOperator(String dealOperator) {
        this.dealOperator = dealOperator;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1232577495, -1326852021).appendSuper(super.hashCode()).append(
            this.compareReNameAccountFile).append(this.compareAccountFile).append(
            this.compareAccountType).append(this.dealOperator).toHashCode();
    }

  
}
