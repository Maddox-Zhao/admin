package com.huaixuan.network.biz.domain.storage;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;

/**
 * 用于入库单详情显示
 * @author chenyan
 *
 */
public class InDetailGoods extends BaseObject {

	private static final long serialVersionUID = 1397476841110592641L;
	//入库单详细表的ID
	private Long id;
	//数量
	private Long amount;
	//单价
	private Double unitPrice;
	//商品编码
	private String code;
	//类目代码
	private String catCode;
	//属性
	private String attrs;
	//商品ID
	private Long goodsId;
	//商品实例ID
	private Long goodsInstanceId;
	//实例名称
	private String instanceName;
	//单位
	private String goodsUnit;
	//分配状态
	private String status;
	//供应商ID
	private Long supplierId;
	//属性
	private String attributeName;
	//类目名称
	private String catName;
	//剩余库存
	private long leftNum;
	//一级仓库剩余库存
	private long leftDepNum;
	//一级仓库ID
    private Long depFirstId;
    //仓库ID
    private Long storId;



	public Long getStorId() {
		return storId;
	}
	public void setStorId(Long storId) {
		this.storId = storId;
	}
	public Long getDepFirstId() {
		return depFirstId;
	}
	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}
	public long getLeftDepNum() {
		return leftDepNum;
	}
	public void setLeftDepNum(long leftDepNum) {
		this.leftDepNum = leftDepNum;
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
    public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	private List<AttributeDTO> attributeDtoList;

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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
}
