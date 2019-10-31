package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 用于出库统计
 * 
 * @author chenhang 2010/01/11
 * 
 */
public class OutDepAnalysis extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String dateStart;// 查询的起始时间

	private String dateEnd;// 查询的结束时间

	private Date gmtOutDep;// 出库单完成时间 即出库时间

	private Long outDepSum;// 该时间段的出库单总数

	private Long goodsNum;// 该时间段出的总发货数量

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

	public Long getOutDepSum() {
		return outDepSum;
	}

	public Date getGmtOutDep() {
		return gmtOutDep;
	}

	public void setGmtOutDep(Date gmtOutDep) {
		this.gmtOutDep = gmtOutDep;
	}

	public void setOutDepSum(Long outDepSum) {
		this.outDepSum = outDepSum;
	}

	public Long getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}

}
