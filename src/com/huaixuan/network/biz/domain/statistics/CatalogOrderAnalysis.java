/**
 * @Title: CatalogOrderAnalysis.java
 * @Package com.hundsun.bible.domain.model.ios
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2010-11-8 下午01:39:21
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.statistics;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;



/**
 * @ClassName: CatalogOrderAnalysis
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2010-11-8 下午01:39:21
 *
 */
public class CatalogOrderAnalysis extends BaseObject implements Serializable{

	private static final long serialVersionUID = 3592152308018467178L;

	private String catCode;//类目编码

	private String catName;//类目名称

	private Long customerNum;//客户�

	private Long orderNum;//订单�

	private Double orderAmount;//订单总金�

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

	public Long getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(Long customerNum) {
		this.customerNum = customerNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}



}