package com.huaixuan.network.biz.domain.storage;

import com.huaixuan.network.biz.domain.BaseObject;

public class InDetailBaseInfo extends BaseObject {

    private static final long serialVersionUID = 7473330490960540398L;
    //��Ʒ���
    private String            code;
    //��Ʒʵ��ID
    private Long              goodsInstanceId;
    //��Ʒ����
    private String            instanceName;
    //����
    private Long              amount;
    //��Ӧ��ID
    private Long              supplierId;
    //��Ӧ������
    private String            supplierName;
    //ԭ��λID
    private Long              oriLocationId;
    //ԭ��λ����
    private String            oriLocationName;
    //����
    private String            batchNum;

    private Long              inDepId;
    private Long              inDetailId;
    private Long              goodsId;

    //һ���ֿ�����
    private String            depFirstName;
    //һ���ֿ�ID
    private Long              depFirstId;
    //�������
    private String            storType;

    private String            type;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getInDepId() {
        return inDepId;
    }

    public void setInDepId(Long inDepId) {
        this.inDepId = inDepId;
    }

    public Long getInDetailId() {
        return inDetailId;
    }

    public void setInDetailId(Long inDetailId) {
        this.inDetailId = inDetailId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public Long getOriLocationId() {
        return oriLocationId;
    }

    public void setOriLocationId(Long oriLocationId) {
        this.oriLocationId = oriLocationId;
    }

    public String getOriLocationName() {
        return oriLocationName;
    }

    public void setOriLocationName(String oriLocationName) {
        this.oriLocationName = oriLocationName;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

}
