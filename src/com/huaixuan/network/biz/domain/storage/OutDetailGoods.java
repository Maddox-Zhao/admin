package com.huaixuan.network.biz.domain.storage;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;

/**
 * 用于出库单详情显示
 * @author chenyan
 *
 */
public class OutDetailGoods extends BaseObject {

    private static final long serialVersionUID = 6671813292360926278L;

    //出库单详细表的ID
    private Long              id;
    //数量
    private Long              outNum;
    //单价
    private Double            unitPrice;
    //商品编码
    private String            code;
    //类目代码
    private String            catCode;
    //属性
    private String            attrs;
    //商品ID
    private Long              goodsId;
    //商品实例ID
    private Long              goodsInstanceId;
    //实例名称
    private String            instanceName;
    //单位
    private String            goodsUnit;
    //分配状态
    private String            status;
    //供应商ID
    private Long              supplierId;
    //属性
    private String            attributeName;
    //类目名称
    private String            catName;
    //剩余库存
    private long              leftNum;
    // 关联单号
    private String            relationNum;

    private Long              depFirstId;

    private String            depFirstName;

    private String            storType;

    private long              leftDepNum;

    private long              outVirtualNum;                          //暂估出库数量

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
