/**
 *
 */
package com.huaixuan.network.biz.domain.account.query;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author chenhang 2011-3-30
 */
public class SearchConditionQuery extends BaseObject {

	private String accountNo;

	private String account;

	private String createTimeStart;

	private String createTimeEnd;

	private String accountBalance;

	private String enabledStatus;

	private String accountTypeSearch;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getEnabledStatus() {
		return enabledStatus;
	}

	public void setEnabledStatus(String enabledStatus) {
		this.enabledStatus = enabledStatus;
	}

	public String getAccountTypeSearch() {
		return accountTypeSearch;
	}

	public void setAccountTypeSearch(String accountTypeSearch) {
		this.accountTypeSearch = accountTypeSearch;
	}

}
