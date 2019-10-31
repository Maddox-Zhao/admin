package com.huaixuan.network.biz.domain.storage;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * �bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class StorageCheckList extends BaseObject implements Serializable {
    private static final long serialVersionUID = -1676018619285749272L;

    /* @property: */
    private long              id;

    /* @property: */
    private long              goodsId;

    /* @property: */
    private long              goodsInstanceId;

    private String            goodsInstanceName;

    private String            code;

    private String            attribute;

    /* @property: */
    private long              depId;

    /* @property: */
    private long              locId;

    private String            depName;

    /* @property: */
    private String            locName;

    /* @property: */
    private Long              storNum;

    /* @property: */
    private Long              checkNum;

    /* @property: */
    private String            status;

    /* @property: */
    private long              checkId;

    /* @property: */
    private Date              gmtCreate;

    /* @property: */
    private Date              gmtModify;

    private double            price;

    /* Default constructor - creates a new instance with no values set. */
    public StorageCheckList() {
    }

    /* @model:�*/
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model:�*/
    public long getId() {
        return this.id;
    }

    /* @model:�*/
    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    /* @model:�*/
    public long getGoodsId() {
        return this.goodsId;
    }

    /* @model:�*/
    public void setGoodsInstanceId(long obj) {
        this.goodsInstanceId = obj;
    }

    /* @model:�*/
    public long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    /* @model:�*/
    public void setDepId(long obj) {
        this.depId = obj;
    }

    /* @model:�*/
    public long getDepId() {
        return this.depId;
    }

    /* @model:�*/
    public void setLocId(long obj) {
        this.locId = obj;
    }

    /* @model:�*/
    public long getLocId() {
        return this.locId;
    }

    public Long getStorNum() {
        return storNum;
    }

    public void setStorNum(Long storNum) {
        this.storNum = storNum;
    }

    /* @model:�*/
    public void setStatus(String obj) {
        this.status = obj;
    }

    /* @model:�*/
    public String getStatus() {
        return this.status;
    }

    /* @model:�*/
    public void setCheckId(long obj) {
        this.checkId = obj;
    }

    /* @model:�*/
    public long getCheckId() {
        return this.checkId;
    }

    /* @model:�*/
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:�*/
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:�*/
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model:�*/
    public Date getGmtModify() {
        return this.gmtModify;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getGoodsInstanceName() {
		return goodsInstanceName;
	}

	public void setGoodsInstanceName(String goodsInstanceName) {
		this.goodsInstanceName = goodsInstanceName;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProfitNum() {
        if (this.storNum != null && this.checkNum != null) {
            return new Long(this.checkNum.longValue() - this.storNum.longValue());
        }
        return null;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
