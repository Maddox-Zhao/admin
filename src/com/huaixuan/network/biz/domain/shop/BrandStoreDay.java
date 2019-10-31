package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class BrandStoreDay extends BaseObject implements Serializable {
	/* @property: */
	private long id;
	/* @property: */
	private Long idBrand;
	/* �ܿ��  */
	private double allStore;
	/* Ʒ�ƿ��  */
	private double brandStore;
	/* ռ����  */
	private double brandStoreRate;
	/* ����  */
	private Date date;
	/* �����۳ɱ�  */
	private double daySaleCost;
	/* �����۳ɱ�hkd  */
	private double daySaleCostHkd;
	
	private double dayPreCost;
	
	private double dayOrderPreCost;
	/* ��ת����  */
	private double dayRate;
	/* �����۳ɱ�  */
	private double weekSale;
	/* ��ת����  */
	private double weekRate;
	/* �������۳ɱ�  */
	private double halfMonthSale;
	/* ����ת����  */
	private double halfMonthSaleRate;
	/* �����۳ɱ�  */
	private double monthSale;
	/* ��ת����  */
	private double monthSaleRate;
	/* �����۶�  */
	private double daySaleAmount;
	/* �����۶�  */
	private double weekSaleAmount;
	/* �������۶�  */
	private double halfMsAmount;
	/* �����۶�  */
	private double monthSaleAmount;
	/* ��ë  */
	private double dayMaori;
	/* ����ë��  */
	private double hmMaoriRate;
	/* ��ë��  */
	private double monthMaoriRate;
	
	private String  dateEnd;
	 
	private String  dateStart;
	
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Long getIdBrand() {
		return idBrand;
	}
	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}
	public double getAllStore() {
		return allStore;
	}
	public void setAllStore(double allStore) {
		this.allStore = allStore;
	}
	public double getBrandStore() {
		return brandStore;
	}
	public void setBrandStore(double brandStore) {
		this.brandStore = brandStore;
	}
	public double getBrandStoreRate() {
		return brandStoreRate;
	}
	public void setBrandStoreRate(double brandStoreRate) {
		this.brandStoreRate = brandStoreRate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getDaySaleCost() {
		return daySaleCost;
	}
	public void setDaySaleCost(double daySaleCost) {
		this.daySaleCost = daySaleCost;
	}
	public double getDayRate() {
		return dayRate;
	}
	public void setDayRate(double dayRate) {
		this.dayRate = dayRate;
	}
	public double getWeekSale() {
		return weekSale;
	}
	public void setWeekSale(double weekSale) {
		this.weekSale = weekSale;
	}
	public double getWeekRate() {
		return weekRate;
	}
	public void setWeekRate(double weekRate) {
		this.weekRate = weekRate;
	}
	public double getHalfMonthSale() {
		return halfMonthSale;
	}
	public void setHalfMonthSale(double halfMonthSale) {
		this.halfMonthSale = halfMonthSale;
	}
	public double getHalfMonthSaleRate() {
		return halfMonthSaleRate;
	}
	public void setHalfMonthSaleRate(double halfMonthSaleRate) {
		this.halfMonthSaleRate = halfMonthSaleRate;
	}
	public double getMonthSale() {
		return monthSale;
	}
	public void setMonthSale(double monthSale) {
		this.monthSale = monthSale;
	}
	public double getMonthSaleRate() {
		return monthSaleRate;
	}
	public void setMonthSaleRate(double monthSaleRate) {
		this.monthSaleRate = monthSaleRate;
	}
	public double getDaySaleAmount() {
		return daySaleAmount;
	}
	public void setDaySaleAmount(double daySaleAmount) {
		this.daySaleAmount = daySaleAmount;
	}
	public double getWeekSaleAmount() {
		return weekSaleAmount;
	}
	public void setWeekSaleAmount(double weekSaleAmount) {
		this.weekSaleAmount = weekSaleAmount;
	}
	public double getHalfMsAmount() {
		return halfMsAmount;
	}
	public void setHalfMsAmount(double halfMsAmount) {
		this.halfMsAmount = halfMsAmount;
	}
	public double getMonthSaleAmount() {
		return monthSaleAmount;
	}
	public void setMonthSaleAmount(double monthSaleAmount) {
		this.monthSaleAmount = monthSaleAmount;
	}
	public double getDayMaori() {
		return dayMaori;
	}
	public void setDayMaori(double dayMaori) {
		this.dayMaori = dayMaori;
	}
	public double getHmMaoriRate() {
		return hmMaoriRate;
	}
	public void setHmMaoriRate(double hmMaoriRate) {
		this.hmMaoriRate = hmMaoriRate;
	}
	public double getMonthMaoriRate() {
		return monthMaoriRate;
	}
	public void setMonthMaoriRate(double monthMaoriRate) {
		this.monthMaoriRate = monthMaoriRate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(allStore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(brandStore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(brandStoreRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		temp = Double.doubleToLongBits(dayMaori);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dayRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(daySaleAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(daySaleCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(halfMonthSale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(halfMonthSaleRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(halfMsAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hmMaoriRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (idBrand ^ (idBrand >>> 32));
		temp = Double.doubleToLongBits(monthMaoriRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthSale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthSaleAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthSaleRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weekRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weekSale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weekSaleAmount);
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
		BrandStoreDay other = (BrandStoreDay) obj;
		if (Double.doubleToLongBits(allStore) != Double
				.doubleToLongBits(other.allStore))
			return false;
		if (Double.doubleToLongBits(brandStore) != Double
				.doubleToLongBits(other.brandStore))
			return false;
		if (Double.doubleToLongBits(brandStoreRate) != Double
				.doubleToLongBits(other.brandStoreRate))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(dayMaori) != Double
				.doubleToLongBits(other.dayMaori))
			return false;
		if (Double.doubleToLongBits(dayRate) != Double
				.doubleToLongBits(other.dayRate))
			return false;
		if (Double.doubleToLongBits(daySaleAmount) != Double
				.doubleToLongBits(other.daySaleAmount))
			return false;
		if (Double.doubleToLongBits(daySaleCost) != Double
				.doubleToLongBits(other.daySaleCost))
			return false;
		if (Double.doubleToLongBits(halfMonthSale) != Double
				.doubleToLongBits(other.halfMonthSale))
			return false;
		if (Double.doubleToLongBits(halfMonthSaleRate) != Double
				.doubleToLongBits(other.halfMonthSaleRate))
			return false;
		if (Double.doubleToLongBits(halfMsAmount) != Double
				.doubleToLongBits(other.halfMsAmount))
			return false;
		if (Double.doubleToLongBits(hmMaoriRate) != Double
				.doubleToLongBits(other.hmMaoriRate))
			return false;
		if (id != other.id)
			return false;
		if (idBrand != other.idBrand)
			return false;
		if (Double.doubleToLongBits(monthMaoriRate) != Double
				.doubleToLongBits(other.monthMaoriRate))
			return false;
		if (Double.doubleToLongBits(monthSale) != Double
				.doubleToLongBits(other.monthSale))
			return false;
		if (Double.doubleToLongBits(monthSaleAmount) != Double
				.doubleToLongBits(other.monthSaleAmount))
			return false;
		if (Double.doubleToLongBits(monthSaleRate) != Double
				.doubleToLongBits(other.monthSaleRate))
			return false;
		if (Double.doubleToLongBits(weekRate) != Double
				.doubleToLongBits(other.weekRate))
			return false;
		if (Double.doubleToLongBits(weekSale) != Double
				.doubleToLongBits(other.weekSale))
			return false;
		if (Double.doubleToLongBits(weekSaleAmount) != Double
				.doubleToLongBits(other.weekSaleAmount))
			return false;
		return true;
	}
	public double getDaySaleCostHkd() {
		return daySaleCostHkd;
	}
	public void setDaySaleCostHkd(double daySaleCostHkd) {
		this.daySaleCostHkd = daySaleCostHkd;
	}
	public double getDayPreCost() {
		return dayPreCost;
	}
	public void setDayPreCost(double dayPreCost) {
		this.dayPreCost = dayPreCost;
	}
	public double getDayOrderPreCost() {
		return dayOrderPreCost;
	}
	public void setDayOrderPreCost(double dayOrderPreCost) {
		this.dayOrderPreCost = dayOrderPreCost;
	}
	
	

}
