package com.huaixuan.network.biz.domain.storage;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;

/**
 * ���ڳ��ⵥ������ʾ
 * @author chenyan
 *
 */
public class OutDetailGoods extends BaseObject {

    private static final long serialVersionUID = 6671813292360926278L;

    //���ⵥ��ϸ���ID
    private Long              id;
    //����
    private Long              outNum;
    //����
    private Double            unitPrice;
    //��Ʒ����
    private String            code;
    //��Ŀ����
    private String            catCode;
    //����
    private String            attrs;
    //��ƷID
    private Long              goodsId;
    //��Ʒʵ��ID
    private Long              goodsInstanceId;
    //ʵ������
    private String            instanceName;
    //��λ
    private String            goodsUnit;
    //����״̬
    private String            status;
    //��Ӧ��ID
    private Long              supplierId;
    //����
    private String            attributeName;
    //��Ŀ����
    private String            catName;
    //ʣ����
    private long              leftNum;
    // ��������
    private String            relationNum;

    private Long              depFirstId;

    private String            depFirstName;

    private String            storType;

    private long              leftDepNum;

    private long              outVirtualNum;                          //�ݹ���������

    public long getLeftDepNum() {
        return leftDepNum;
    }

    public void setLeftDepNum(long leftDepNum) {
        this.leftDepNum = leftDepNum;
    }

    /**
     * @return the relationNum
     */
    public String getRelationNum() {
        return relationNum;
    }

    /**
     * @param relationNum the relationNum to set
     */
    public void setRelationNum(String relationNum) {
        this.relationNum = relationNum;
    }

    /**
     * @return the leftNum
     */
    public long getLeftNum() {
        return leftNum;
    }

    /**
     * @param leftNum the leftNum to set
     */
    public void setLeftNum(long leftNum) {
        this.leftNum = leftNum;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public Long getOutNum() {
        return outNum;
    }

    public void setOutNum(Long outNum) {
        this.outNum = outNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<AttributeDTO> getAttributeDtoList() {
        return attributeDtoList;
    }

    public void setAttributeDtoList(List<AttributeDTO> attributeDtoList) {
        this.attributeDtoList = attributeDtoList;
    }

    private List<AttributeDTO> attributeDtoList;

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public long getOutVirtualNum() {
        return outVirtualNum;
    }

    public void setOutVirtualNum(long outVirtualNum) {
        this.outVirtualNum = outVirtualNum;
    }

}
