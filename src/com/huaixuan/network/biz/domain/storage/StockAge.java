package com.huaixuan.network.biz.domain.storage;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;

public class StockAge extends BaseObject implements Serializable {

    /**
     * 
     */

    protected Log             log              = LogFactory.getLog(this.getClass());
    private static final long serialVersionUID = 1943494040441667584L;

    private long              id;

    private String            code;

    private Long              goodsInstanceId;

    private long              depositoryId;

    private long              supplierId;

    private long               storageNum;

    private double            storagePrice;

    private int               halfMonth;

    private int               oneMonth;

    private int               oneHalfMonth;

    private int               twoMonth;

    private int               twohalfMonth;

    private int               threeMonth;

    private int               aboveMonth;

    private Date              gmtCreate;

    private Date              gmtModify;

    private Long              locId;

    private String            storType;

    private String            countDate;

    private String            instanceName;

    private String            catCode;

    private String            attrs;

    private String            goodsUnit;

    private String            depositoryName;

    private String            locName;

    private String            supplierName;

    private Integer           afterOnemonth;

    private Integer           onemonthThreemonty;

    private Integer           threemontySixmonty;

    private Integer           sixmontyTwelvemonth;

    private Integer           aboveTwelvemonth;

    public Integer getAfterOnemonth() {
        return afterOnemonth;
    }

    public void setAfterOnemonth(Integer afterOnemonth) {
        this.afterOnemonth = afterOnemonth;
    }

    public Integer getOnemonthThreemonty() {
        return onemonthThreemonty;
    }

    public void setOnemonthThreemonty(Integer onemonthThreemonty) {
        this.onemonthThreemonty = onemonthThreemonty;
    }

    public Integer getThreemontySixmonty() {
        return threemontySixmonty;
    }

    public void setThreemontySixmonty(Integer threemontySixmonty) {
        this.threemontySixmonty = threemontySixmonty;
    }

    public Integer getSixmontyTwelvemonth() {
        return sixmontyTwelvemonth;
    }

    public void setSixmontyTwelvemonth(Integer sixmontyTwelvemonth) {
        this.sixmontyTwelvemonth = sixmontyTwelvemonth;
    }

    public Integer getAboveTwelvemonth() {
        return aboveTwelvemonth;
    }

    public void setAboveTwelvemonth(Integer aboveTwelvemonth) {
        this.aboveTwelvemonth = aboveTwelvemonth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStorageNum() {
		return storageNum;
	}

	public void setStorageNum(long storageNum) {
		this.storageNum = storageNum;
	}

	public int getHalfMonth() {
        return halfMonth;
    }

    public void setHalfMonth(int halfMonth) {
        this.halfMonth = halfMonth;
    }

    public int getOneHalfMonth() {
        return oneHalfMonth;
    }

    public void setOneHalfMonth(int oneHalfMonth) {
        this.oneHalfMonth = oneHalfMonth;
    }

    public int getTwoMonth() {
        return twoMonth;
    }

    public void setTwoMonth(int twoMonth) {
        this.twoMonth = twoMonth;
    }

    public int getThreeMonth() {
        return threeMonth;
    }

    public void setThreeMonth(int threeMonth) {
        this.threeMonth = threeMonth;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public long getDepositoryId() {
        return depositoryId;
    }

    public void setDepositoryId(long depositoryId) {
        this.depositoryId = depositoryId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public double getStoragePrice() {
        return storagePrice;
    }

    public void setStoragePrice(double storagePrice) {
        this.storagePrice = storagePrice;
    }

    public int getOneMonth() {
        return oneMonth;
    }

    public void setOneMonth(int oneMonth) {
        this.oneMonth = oneMonth;
    }

    public int getAboveMonth() {
        return aboveMonth;
    }

    public void setAboveMonth(int aboveMonth) {
        this.aboveMonth = aboveMonth;
    }

    public double getAverageStoragePrice() {
        return this.storagePrice / this.storageNum;
    }

    public int getTwohalfMonth() {
        return twohalfMonth;
    }

    public void setTwohalfMonth(int twohalfMonth) {
        this.twohalfMonth = twohalfMonth;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
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

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}