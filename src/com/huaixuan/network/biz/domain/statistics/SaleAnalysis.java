/**
 * created since 2009-7-21
 */
package com.huaixuan.network.biz.domain.statistics;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.huaixuan.network.biz.domain.BaseObject;



/**
 * @author shengyong
 * @version $Id: SaleAnalysis.java,v 0.1 2009-7-21 ����02:22:57 shengyong Exp $
 */
public class SaleAnalysis extends BaseObject implements Serializable,Comparable<SaleAnalysis>{

	private static final long serialVersionUID = 3592152308018467178L;

	private String buyNick;

	private Long goodsId;

	private String goodsSn;

	private String tid;

	// ��Ʒ���
	private String title;

	// ��Ŀ
	private String catId;

	private String catName;

	// ��λ
	private String unit;

	// ����
	private String attrs;

	// ������
	private Integer saleNum;

	// ���۳ɱ�
	private double goodsInPrice;

	// ���۽��

	private double salePrice;

	// ë��
	private double saleProfit;

	// ë����
	private double saleProfitPer;
	
	private String catCode;//类目编码
	
	private String code;//产品编码

	private String instanceName;//产品名称

	private String goodsUnit;//商品单位
	
	private String depositoryName;//仓库名称
	
	private Long storageNumSum;//总库�

    private Double averagePrice; //成本均价

    private Double storageCost; //库存成本
    
    private Double goodsPrice;//商城价格
    
	private Long locId; //库位Id
	
    //�级仓库ID
    private Long               depFirstId;
    //�级仓库名�
    private String             depFirstName;
    //类型
    private String             storType;
    
    private String             dosearch;
    
    private String             dateStart;
    
    private String             dateEnd;
    
    private String             actionKind;

	public String getActionKind() {
		return actionKind;
	}

	public void setActionKind(String actionKind) {
		this.actionKind = actionKind;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDosearch() {
		return dosearch;
	}

	public void setDosearch(String dosearch) {
		this.dosearch = dosearch;
	}

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

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

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public double getGoodsInPrice() {
		return goodsInPrice;
	}

	public void setGoodsInPrice(double goodsInPrice) {
		this.goodsInPrice = goodsInPrice;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public String getSaleProfit() {
		String pf = "0.00";
		saleProfit = this.salePrice - this.goodsInPrice;
		DecimalFormat df = new DecimalFormat("0.00");
		pf = df.format(saleProfit);
		return pf;
	}

	public String getSaleProfitPer() {
		String pf = "0.00";
		if (this.goodsInPrice != 0) {
			saleProfitPer = ((this.salePrice - this.goodsInPrice) * 100)
					/ this.salePrice;
			DecimalFormat df = new DecimalFormat("0.00");
			pf = df.format(saleProfitPer);
		}
		return pf;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getBuyNick() {
		return buyNick;
	}

	public void setBuyNick(String buyNick) {
		this.buyNick = buyNick;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getDepositoryName() {
		return depositoryName;
	}

	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}

	public Long getStorageNumSum() {
		return storageNumSum;
	}

	public void setStorageNumSum(Long storageNumSum) {
		this.storageNumSum = storageNumSum;
	}

	public Double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public Double getStorageCost() {
		return storageCost;
	}

	public void setStorageCost(Double storageCost) {
		this.storageCost = storageCost;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
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

	public int compareTo(SaleAnalysis o) {
		return this.buyNick.compareTo(o.getBuyNick());
	}

}
