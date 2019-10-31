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
public class SaleAnalysisTwo extends BaseObject implements Serializable,Comparable<SaleAnalysisTwo>{

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

    private String            catCode;                                 //��Ŀ����

    private String            code;                                    //��Ʒ����

    private String            instanceName;                            //��Ʒ����

    private String            goodsUnit;                               //��Ʒ��λ

    private String            depositoryName;                          //�ֿ�����

    private Long              storageNumSum;                           //�ܿ��

    private Double            averagePrice;                           //�ɱ�����

    private Double            storageCost;                            //���ɱ�

    private Double            goodsPrice;                              //�̳Ǽ۸�

    private Long              locId;                                  //��λId

    private String            locName;
    
    //һ���ֿ�ID
    private Long              depFirstId;
    //һ���ֿ�����
    private String            depFirstName;
    //����
    private String            storType;

    private Long   			  goodsNumber;

    private double			   storageLasts;

    private Long              depId;
    
    private String            actionKind;
    
    private String            dateStart;
    
    private String            dateEnd;
    
    private String            actionType;
    
    public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
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

	public String getActionKind() {
		return actionKind;
	}

	public void setActionKind(String actionKind) {
		this.actionKind = actionKind;
	}

	public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
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

	public int compareTo(SaleAnalysisTwo o) {
		return this.goodsId.compareTo(o.getGoodsId());
	}

	public double getStorageLasts() {
		return storageLasts;
	}

	public void setStorageLasts(double storageLasts) {
		this.storageLasts = storageLasts;
	}

	public Long getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Long goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

}
