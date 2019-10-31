package com.huaixuan.network.biz.domain.storage;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;

/**
 * ������ⵥ������ʾ
 * @author chenyan
 *
 */
public class InDetailGoods extends BaseObject {

	private static final long serialVersionUID = 1397476841110592641L;
	//��ⵥ��ϸ���ID
	private Long id;
	//����
	private Long amount;
	//����
	private Double unitPrice;
	//��Ʒ����
	private String code;
	//��Ŀ����
	private String catCode;
	//����
	private String attrs;
	//��ƷID
	private Long goodsId;
	//��Ʒʵ��ID
	private Long goodsInstanceId;
	//ʵ������
	private String instanceName;
	//��λ
	private String goodsUnit;
	//����״̬
	private String status;
	//��Ӧ��ID
	private Long supplierId;
	//����
	private String attributeName;
	//��Ŀ����
	private String catName;
	//ʣ����
	private long leftNum;
	//һ���ֿ�ʣ����
	private long leftDepNum;
	//һ���ֿ�ID
    private Long depFirstId;
    //�ֿ�ID
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
