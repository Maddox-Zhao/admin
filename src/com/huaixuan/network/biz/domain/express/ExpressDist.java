package com.huaixuan.network.biz.domain.express;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * �bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class ExpressDist extends BaseObject implements Cloneable {

	private static final long serialVersionUID = 555304194480854099L;
	protected Log log = LogFactory.getLog(this.getClass());
	/* @property: */
	private Long id;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;
	/* @property: */
	private String regionCodeStart;
	/* @property: */
	private String regionCodeEnd;
	/* @property: */
	private Long expressId;

	private String expressName;
	private String regionCodeStartName;
	private String regionCodeEndName;
	private String regionCodeStartProvinceName;
	private String regionCodeStartCityName;
	private String regionCodeEndProvinceName;
	private String regionCodeEndCityName;

	/* @property: */
	private String spendTime;
	/* @property: */
	private Double weightFirst;
	/* @property: */
	private Double weightFirstMoney;
	/* @property: */
	private Double weightNext;
	/* @property: */
	private Double weightNextMoney;
	/* @property: */
	private String payment;
	/* @property: */
	private String memo;
	/* @property: */
	private String status;

	private String expressDesc;
	private Double weightMoney;

	private String youJiPao = "";// �Ƿ��ʼ���

	private String regionCodeProvinceStart;
	private String regionCodeCityStart;
	private String regionCodeDistinctStart;
	private String regionCodeProvinceEnd;
	private String regionCodeCityEnd;
	private String regionCodeDistinctEnd;

	private String spendTimeFrom;
	private String spendTimeTo;

	public String getYouJiPao() {
		return youJiPao;
	}

	public void setYouJiPao(String youJiPao) {
		this.youJiPao = youJiPao;
	}

	public Double getWeightMoney() {
		return weightMoney;
	}

	public void setWeightMoney(Double weightMoney) {
		this.weightMoney = weightMoney;
	}

	/* Default constructor - creates a new instance with no values set. */
	public ExpressDist() {
	}

	public String getRegionCodeStartProvinceName() {
		return regionCodeStartProvinceName;
	}

	public void setRegionCodeStartProvinceName(String regionCodeStartProvinceName) {
		this.regionCodeStartProvinceName = regionCodeStartProvinceName;
	}

	public String getRegionCodeStartCityName() {
		return regionCodeStartCityName;
	}

	public void setRegionCodeStartCityName(String regionCodeStartCityName) {
		this.regionCodeStartCityName = regionCodeStartCityName;
	}

	public String getRegionCodeEndProvinceName() {
		return regionCodeEndProvinceName;
	}

	public void setRegionCodeEndProvinceName(String regionCodeEndProvinceName) {
		this.regionCodeEndProvinceName = regionCodeEndProvinceName;
	}

	public String getRegionCodeEndCityName() {
		return regionCodeEndCityName;
	}

	public void setRegionCodeEndCityName(String regionCodeEndCityName) {
		this.regionCodeEndCityName = regionCodeEndCityName;
	}

	public String getRegionCodeStartName() {
		return regionCodeStartName;
	}

	public void setRegionCodeStartName(String regionCodeStartName) {
		this.regionCodeStartName = regionCodeStartName;
	}

	public String getRegionCodeEndName() {
		return regionCodeEndName;
	}

	public void setRegionCodeEndName(String regionCodeEndName) {
		this.regionCodeEndName = regionCodeEndName;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	/* @model:� */
	public void setId(Long obj) {
		this.id = obj;
	}

	/* @model:� */
	public Long getId() {
		return this.id;
	}

	/* @model:� */
	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	/* @model:� */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	/* @model:� */
	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	/* @model:� */
	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* @model:� */
	public void setRegionCodeStart(String obj) {
		this.regionCodeStart = obj;
	}

	/* @model:� */
	public String getRegionCodeStart() {
		return this.regionCodeStart;
	}

	/* @model:� */
	public void setRegionCodeEnd(String obj) {
		this.regionCodeEnd = obj;
	}

	/* @model:� */
	public String getRegionCodeEnd() {
		return this.regionCodeEnd;
	}

	/* @model:� */
	public void setExpressId(Long obj) {
		this.expressId = obj;
	}

	/* @model:� */
	public Long getExpressId() {
		return this.expressId;
	}

	/* @model:� */
	public void setSpendTime(String obj) {
		this.spendTime = obj;
	}

	/* @model:� */
	public String getSpendTime() {
		return this.spendTime;
	}

	/* @model:� */
	public void setWeightFirst(Double obj) {
		this.weightFirst = obj;
	}

	/* @model:� */
	public Double getWeightFirst() {
		return this.weightFirst;
	}

	/* @model:� */
	public void setWeightFirstMoney(Double obj) {
		this.weightFirstMoney = obj;
	}

	/* @model:� */
	public Double getWeightFirstMoney() {
		return this.weightFirstMoney;
	}

	/* @model:� */
	public void setWeightNext(Double obj) {
		this.weightNext = obj;
	}

	/* @model:� */
	public Double getWeightNext() {
		return this.weightNext;
	}

	/* @model:� */
	public void setWeightNextMoney(Double obj) {
		this.weightNextMoney = obj;
	}

	/* @model:� */
	public Double getWeightNextMoney() {
		return this.weightNextMoney;
	}

	/* @model:� */
	public void setPayment(String obj) {
		this.payment = obj;
	}

	/* @model:� */
	public String getPayment() {
		return this.payment;
	}

	/* @model:� */
	public void setMemo(String obj) {
		this.memo = obj;
	}

	/* @model:� */
	public String getMemo() {
		return this.memo;
	}

	/* @model:� */
	public void setStatus(String obj) {
		this.status = obj;
	}

	/* @model:� */
	public String getStatus() {
		return this.status;
	}

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ExpressDist)) {
			return false;
		}
		final ExpressDist expressdist = (ExpressDist) o;
		return this.hashCode() == expressdist.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		return this.hashCode();
	}

	public String getExpressDesc() {
		return expressDesc;
	}

	public void setExpressDesc(String expressDesc) {
		this.expressDesc = expressDesc;
	}

	/* {@inheritDoc} */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public ExpressDist clone() {
		ExpressDist o = null;
		try {
			o = (ExpressDist) super.clone();
		} catch (CloneNotSupportedException e) {
			log.error(e.getMessage());
		}
		return o;
	}

	public String getRegionCodeProvinceStart() {
		return regionCodeProvinceStart;
	}

	public void setRegionCodeProvinceStart(String regionCodeProvinceStart) {
		this.regionCodeProvinceStart = regionCodeProvinceStart;
	}

	public String getRegionCodeCityStart() {
		return regionCodeCityStart;
	}

	public void setRegionCodeCityStart(String regionCodeCityStart) {
		this.regionCodeCityStart = regionCodeCityStart;
	}

	public String getRegionCodeDistinctStart() {
		return regionCodeDistinctStart;
	}

	public void setRegionCodeDistinctStart(String regionCodeDistinctStart) {
		this.regionCodeDistinctStart = regionCodeDistinctStart;
	}

	public String getRegionCodeProvinceEnd() {
		return regionCodeProvinceEnd;
	}

	public void setRegionCodeProvinceEnd(String regionCodeProvinceEnd) {
		this.regionCodeProvinceEnd = regionCodeProvinceEnd;
	}

	public String getRegionCodeCityEnd() {
		return regionCodeCityEnd;
	}

	public void setRegionCodeCityEnd(String regionCodeCityEnd) {
		this.regionCodeCityEnd = regionCodeCityEnd;
	}

	public String getRegionCodeDistinctEnd() {
		return regionCodeDistinctEnd;
	}

	public void setRegionCodeDistinctEnd(String regionCodeDistinctEnd) {
		this.regionCodeDistinctEnd = regionCodeDistinctEnd;
	}

	public String getSpendTimeFrom() {
		return spendTimeFrom;
	}

	public void setSpendTimeFrom(String spendTimeFrom) {
		this.spendTimeFrom = spendTimeFrom;
	}

	public String getSpendTimeTo() {
		return spendTimeTo;
	}

	public void setSpendTimeTo(String spendTimeTo) {
		this.spendTimeTo = spendTimeTo;
	}

}
