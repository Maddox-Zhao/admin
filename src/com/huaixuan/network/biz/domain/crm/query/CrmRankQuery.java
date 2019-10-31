package com.huaixuan.network.biz.domain.crm.query;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * CRM排行检索条件
 * @version 3.2.0
 */
public class CrmRankQuery extends BaseObject {

    private static final long serialVersionUID = 1334239122704678794L;

    private String            customerName;                           //客户名称
    private String            gmtOptTimeStart;                        //统计开始时间
    private String            gmtOptTimeEnd;                          //统计结束时间

    private String            productName;                            //产品名称
    private String            productCode;                            //产品编码

    private String            saleName;                               //销售人员名称

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
