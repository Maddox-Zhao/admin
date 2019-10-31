package com.huaixuan.network.biz.domain.crm.query;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * CRM���м�������
 * @version 3.2.0
 */
public class CrmRankQuery extends BaseObject {

    private static final long serialVersionUID = 1334239122704678794L;

    private String            customerName;                           //�ͻ�����
    private String            gmtOptTimeStart;                        //ͳ�ƿ�ʼʱ��
    private String            gmtOptTimeEnd;                          //ͳ�ƽ���ʱ��

    private String            productName;                            //��Ʒ����
    private String            productCode;                            //��Ʒ����

    private String            saleName;                               //������Ա����

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGmtOptTimeStart() {
        return gmtOptTimeStart;
    }

    public void setGmtOptTimeStart(String gmtOptTimeStart) {
        this.gmtOptTimeStart = gmtOptTimeStart;
    }

    public String getGmtOptTimeEnd() {
        return gmtOptTimeEnd;
    }

    public void setGmtOptTimeEnd(String gmtOptTimeEnd) {
        this.gmtOptTimeEnd = gmtOptTimeEnd;
    }
}
