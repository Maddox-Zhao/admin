/**
 * created since 2009-8-5
 */
package com.huaixuan.network.biz.domain.statistics;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;

import com.huaixuan.network.biz.domain.BaseObject;



/**
 * @author shengyong
 * @version $Id: GoodsAnalysis.java,v 0.1 2009-8-5 下午03:09:37 shengyong Exp $
 */
public class GoodsAnalysis extends BaseObject implements Serializable/**,Comparable<GoodsAnalysis>*/{

	private static final long serialVersionUID = -7831736543515671464L;

	private Long goodsId;

	private String goodsSn;

	private String goodsName;

	private String catCode;

	private String catName;

	private String attrValue;

	private String unit;

	private Map<String, String> properties;

	private Long saleSum;

	private double inPrice;

	private double outPrice;

	private double profit;

	private double profitPer;

	private Long refundSum;

	private double refundPrice;

	private Long refundCustomer;

	private double price;

	private Long   storageNum;

	private String brandName;

	private Long availableNum;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getInPrice() {
		return inPrice;
	}

	public void setInPrice(double inPrice) {
		this.inPrice = inPrice;
	}

	public double getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(double outPrice) {
		this.outPrice = outPrice;
	}

	public String getProfit() {
		String pf = "0.00";
		profit = this.outPrice - this.inPrice;
		DecimalFormat df = new DecimalFormat("0.00");
		pf = df.format(profit);
		return pf;
	}

	public String getProfitPer() {
		String pf = "0.00";
		if (this.inPrice != 0) {
			profitPer = (this.profit * 100) / this.outPrice;
			DecimalFormat df = new DecimalFormat("0.00");
			pf = df.format(profitPer);
		}
		return pf;
	}

	public Long getSaleSum() {
		return saleSum;
	}

	public void setSaleSum(Long saleSum) {
		this.saleSum = saleSum;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getRefundCustomer() {
		return refundCustomer;
	}

	public void setRefundCustomer(Long refundCustomer) {
		this.refundCustomer = refundCustomer;
	}

	public double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Long getRefundSum() {
		return refundSum;
	}

	public void setRefundSum(Long refundSum) {
		this.refundSum = refundSum;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getStorageNum() {
		return storageNum;
	}

	public void setStorageNum(Long storageNum) {
		this.storageNum = storageNum;
	}

	public Long getAvailableNum() {
		return availableNum;
	}

	public void setAvailableNum(Long availableNum) {
		this.availableNum = availableNum;
	}

//	  shlin
//    public int compareTo(GoodsAnalysis  goodsAnalysis){
//        Long saleSum = goodsAnalysis.getSaleSum();
//        int value=1;
//        if (saleSum !=null && this.getSaleSum() != null){
//            if(this.getSaleSum() > saleSum){
//                value = -1;
//            }else if(this.getSaleSum() < saleSum){
//            	value = 1;
//            }else{
//            	value = 0;
//            }
//        }
//        return value;
//    }
}
