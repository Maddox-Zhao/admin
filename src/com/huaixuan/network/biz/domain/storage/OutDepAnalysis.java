package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ���ڳ���ͳ��
 * 
 * @author chenhang 2010/01/11
 * 
 */
public class OutDepAnalysis extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String dateStart;// ��ѯ����ʼʱ��

	private String dateEnd;// ��ѯ�Ľ���ʱ��

	private Date gmtOutDep;// ���ⵥ���ʱ�� ������ʱ��

	private Long outDepSum;// ��ʱ��εĳ��ⵥ����

	private Long goodsNum;// ��ʱ��γ����ܷ�������

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
