/**
 * @Title: FinanceDepositoryQuery.java
 * @Package com.huaixuan.network.biz.domain.storage.query
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-17 ионГ10:30:48
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.storage.query;

/**
 * @ClassName: FinanceDepositoryQuery
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-17 ионГ10:30:48
 *
 */
public class FinanceDepositoryQuery {

	private String billNum;

	private String startTime;

	private String endTime;

	private String financeStatus;

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}



}
