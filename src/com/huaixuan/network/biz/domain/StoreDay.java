package com.huaixuan.network.biz.domain;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class StoreDay extends BaseObject implements Serializable {
    
	private static final long serialVersionUID = -4132151804771787587L;
	/* @property: */
    private long               id;
    /* @property: */
    private double             storeAmount;

    private double             storeRmbAmount;
    /* @property: */
    private double 			   storeEuAmount; 
    /* @property: */
    private double             storeHkAmount;
    /* @property: */
    private int                storeNumber;
    
    private double             hkStoreAmount;
    /* @property: */
    private double             hkStoreRmbAmount;
    /* @property: */
    private double 			   hkStoreEuAmount; 
    /* @property: */
    private double             hkStoreHkAmount;
    /* @property: */
    private int                hkStoreNnumber;
    /* @property: */
    private String             datemonthday;
    /* @property: */
    private Date             gmtCreate;
    /* @property: */
    private Date             gmtModify;
    
    private double 			   sellAmount; 
    /* @property: */
    private double             hkSellAmount;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getStoreAmount() {
		return storeAmount;
	}
	public void setStoreAmount(double storeAmount) {
		this.storeAmount = storeAmount;
	}
	public double getStoreRmbAmount() {
		return storeRmbAmount;
	}
	public void setStoreRmbAmount(double storeRmbAmount) {
		this.storeRmbAmount = storeRmbAmount;
	}
	public double getStoreEuAmount() {
		return storeEuAmount;
	}
	public void setStoreEuAmount(double storeEuAmount) {
		this.storeEuAmount = storeEuAmount;
	}
	public double getStoreHkAmount() {
		return storeHkAmount;
	}
	public void setStoreHkAmount(double storeHkAmount) {
		this.storeHkAmount = storeHkAmount;
	}
	public int getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}
	public double getHkStoreAmount() {
		return hkStoreAmount;
	}
	public void setHkStoreAmount(double hkStoreAmount) {
		this.hkStoreAmount = hkStoreAmount;
	}
	public double getHkStoreRmbAmount() {
		return hkStoreRmbAmount;
	}
	public void setHkStoreRmbAmount(double hkStoreRmbAmount) {
		this.hkStoreRmbAmount = hkStoreRmbAmount;
	}
	public double getHkStoreEuAmount() {
		return hkStoreEuAmount;
	}
	public void setHkStoreEuAmount(double hkStoreEuAmount) {
		this.hkStoreEuAmount = hkStoreEuAmount;
	}
	public double getHkStoreHkAmount() {
		return hkStoreHkAmount;
	}
	public void setHkStoreHkAmount(double hkStoreHkAmount) {
		this.hkStoreHkAmount = hkStoreHkAmount;
	}
	public int getHkStoreNnumber() {
		return hkStoreNnumber;
	}
	public void setHkStoreNnumber(int hkStoreNnumber) {
		this.hkStoreNnumber = hkStoreNnumber;
	}
	public String getDatemonthday() {
		return datemonthday;
	}
	public void setDatemonthday(String datemonthday) {
		this.datemonthday = datemonthday;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datemonthday == null) ? 0 : datemonthday.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		long temp;
		temp = Double.doubleToLongBits(hkStoreAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hkStoreEuAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hkStoreHkAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + hkStoreNnumber;
		temp = Double.doubleToLongBits(hkStoreRmbAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		temp = Double.doubleToLongBits(storeAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(storeEuAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(storeHkAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + storeNumber;
		temp = Double.doubleToLongBits(storeRmbAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreDay other = (StoreDay) obj;
		if (datemonthday == null) {
			if (other.datemonthday != null)
				return false;
		} else if (!datemonthday.equals(other.datemonthday))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModify == null) {
			if (other.gmtModify != null)
				return false;
		} else if (!gmtModify.equals(other.gmtModify))
			return false;
		if (Double.doubleToLongBits(hkStoreAmount) != Double
				.doubleToLongBits(other.hkStoreAmount))
			return false;
		if (Double.doubleToLongBits(hkStoreEuAmount) != Double
				.doubleToLongBits(other.hkStoreEuAmount))
			return false;
		if (Double.doubleToLongBits(hkStoreHkAmount) != Double
				.doubleToLongBits(other.hkStoreHkAmount))
			return false;
		if (hkStoreNnumber != other.hkStoreNnumber)
			return false;
		if (Double.doubleToLongBits(hkStoreRmbAmount) != Double
				.doubleToLongBits(other.hkStoreRmbAmount))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(storeAmount) != Double
				.doubleToLongBits(other.storeAmount))
			return false;
		if (Double.doubleToLongBits(storeEuAmount) != Double
				.doubleToLongBits(other.storeEuAmount))
			return false;
		if (Double.doubleToLongBits(storeHkAmount) != Double
				.doubleToLongBits(other.storeHkAmount))
			return false;
		if (storeNumber != other.storeNumber)
			return false;
		if (Double.doubleToLongBits(storeRmbAmount) != Double
				.doubleToLongBits(other.storeRmbAmount))
			return false;
		return true;
	}
	public double getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(double sellAmount) {
		this.sellAmount = sellAmount;
	}
	public double getHkSellAmount() {
		return hkSellAmount;
	}
	public void setHkSellAmount(double hkSellAmount) {
		this.hkSellAmount = hkSellAmount;
	}

    

}
