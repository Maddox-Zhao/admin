package com.huaixuan.network.biz.domain.storage;

import com.huaixuan.network.biz.domain.BaseObject;

public class OutDetailBaseInfo extends BaseObject {

    private static final long serialVersionUID = 1489003404379338809L;

    // ��Ʒ���
    private String            code;
    // ��Ʒʵ��ID
    private Long              goodsInstanceId;
    // ��Ʒ����
    private String            instanceName;
    // ����
    private Long              outNum;
    // ��Ӧ��ID
    private Long              supplierId;
    // ��Ӧ������
    private String            supplierName;
    // ��������
    private String            relationNum;

    private Long              outDetailId;
    private Long              outDepositoryId;
    private Long              goodsId;
    /*
     * �ɱ���
     */
    private Double            unitPrice;

    private String            depFirstId;

    private String            depFirstName;

    private String            storType;                               //����

    /**
     * @return the relationNum
     */
    public String getRelationNum() {
        return relationNum;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    /**
     * @param relationNum the relationNum to set
     */
    public void setRelationNum(String relationNum) {
        this.relationNum = relationNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getOutDetailId() {
        return outDetailId;
    }

    public void setOutDetailId(Long outDetailId) {
        this.outDetailId = outDetailId;
    }

    public Long getOutDepositoryId() {
        return outDepositoryId;
    }

    public void setOutDepositoryId(Long outDepositoryId) {
        this.outDepositoryId = outDepositoryId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getOutNum() {
        return outNum;
    }

    public void setOutNum(Long outNum) {
        this.outNum = outNum;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(String depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

}
