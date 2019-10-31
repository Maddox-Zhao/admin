/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-28
 */
package com.huaixuan.network.biz.dao.account.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.account.AccountDao;
import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.query.QueryPage;
import com.hundsun.itrans.domain.base.AccountLogDO;

/**
 * @version $Id: AccountDaoiBatis.java,v 0.1 2011-3-28 上午09:53:45  Exp
 *          $
 */
@Service("accountDao")
public class AccountDaoiBatis implements AccountDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public List<Account> getAccountCountByCreate(String dateStart,
			String dateEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("datestart", dateStart + " 00:00:00");
		map.put("dateEnd", dateStart + " 23:59:59");
		return (List<Account>) this.sqlMapClientTemplate.queryForList(
				"getAccountCountByCreate", map);
	}

	@Override
	public Long getUserSeq() {
		return (Long) this.sqlMapClientTemplate.queryForObject("getUsersSeq");
	}

	@Override
	public Long getPayMentOffLineSeq() {
		return (Long) this.sqlMapClientTemplate
				.queryForObject("getPayMentOffLineSeq");
	}

	@Override
	public Integer getAccountCount() {
		return (Integer) this.sqlMapClientTemplate
				.queryForObject("getAccountCount");
	}

	@Override
	public Integer getCompayAccountCount(String accountNo) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"getCompayAccountCount", accountNo);
	}

	@Override
	public Integer getSubAccountCount(Map map) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"getSubAccountCount", map);
	}

	@Override
	public List<Account> getAccountPage(int currentPage, int pageSize,
			int totalCount) {
		Account acc = new Account();
		QueryPage queryPage = new QueryPage(acc);
		Map pramas = queryPage.getParameters();

		/* 当前页 */
		queryPage.setCurrentPage(currentPage);
		/* 每页总数 */
		queryPage.setPageSize(pageSize);
		queryPage.setTotalItem(totalCount);
		pramas.put("startRow", queryPage.getPageFristItem());
		pramas.put("endRow", queryPage.getPageLastItem());

		List<Account> list = this.sqlMapClientTemplate.queryForList(
				"getAccounts", pramas);
		return list;
	}

	@Override
	public void addAccount(Account account) {
		this.sqlMapClientTemplate.insert("addAccount", account);
	}

	@Override
	public void editAccount(Account account) {
		this.sqlMapClientTemplate.update("editAccount", account);
	}

	@Override
	public void removeAccount(Long accountId) {
		this.sqlMapClientTemplate.delete("removeAccount", accountId);
	}

	@Override
	public Account getAccount(Long accountId) {
		return (Account) this.sqlMapClientTemplate.queryForObject("getAccount",
				accountId);
	}

	@Override
	public List<Account> getAccounts() {
		return this.sqlMapClientTemplate.queryForList("getAccounts", null);
	}

	@Override
	public List<Account> getAccountsByCondition(
			Map<String, String> searchCondition) {
		List<Account> list = this.sqlMapClientTemplate.queryForList(
				"account-searchAccountByCondition-query", searchCondition);
		return list;
	}

	@Override
	public Integer getAccountsByConditionCount(Map map) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"account-searchAccountByCondition-count", map);
	}

	@Override
	public List<Account> getAccountLogsByAccountNo(Map<String, String> searchMap) {
		List<Account> list = this.sqlMapClientTemplate.queryForList(
				"accountLog-searchLogByAccountNo-query", searchMap);
		return list;
	}

	@Override
	public Integer getAccountLogsByAccountNoCount(Map map) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"accountLog-searchLogByAccountNo-count", map);
	}

	@Override
	public List<Account> getAccountLogsByUserId(Map<String, String> searchMap) {
		List<Account> list = this.sqlMapClientTemplate.queryForList(
				"accountLog-searchLogByAccountNo-query", searchMap);
		return list;
	}

	public Integer getAccountLogsByUserIdCount(Map map) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"accountLog-searchLogByAccountNo-count", map);
	}

	@Override
	public List<Account> getBalanceLogsListByAccountNo(
			Map<String, String> searchMap) {
		List<Account> list = this.sqlMapClientTemplate.queryForList(
				"freezeAdminBalanceLog-searchLogByAccountNo-count", searchMap);
		return list;
	}

	@Override
	public Integer getBalanceLogsListByAccountNoCount(Map map) {
		return (Integer) this.sqlMapClientTemplate.queryForObject(
				"accountLog-searchLogByAccountNo-count", map);
	}

	@Override
	public void doProcessAccount(Account account) {
		this.sqlMapClientTemplate.update("account-processAccount-update",
				account);
	}

	@Override
	public Account getAccountByUidAndSubtype(long uid, String subType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", uid);
		param.put("accountSubType", subType);
		return (Account) this.sqlMapClientTemplate.queryForObject(
				"getAccountByUidAndType", param);
	}

	@Override
	public Account getAccountByAccountNo(String AccountNo) {
		return (Account) this.sqlMapClientTemplate.queryForObject(
				"account-getAccountByAccountNo-query", AccountNo);
	}

	@Override
	public long sumTotleFreezeAmountByType(String accountNo, String freezeReason) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accountNo", accountNo);
		param.put("actionType", "F");
		param.put("actionReason", freezeReason);
		Long totalAmout = (Long) this.sqlMapClientTemplate.queryForObject(
				"sumAccountFreezeBalance", param);
		if (totalAmout != null) {
			return totalAmout.longValue();
		}
		return 0;
	}

	@Override
	public long sumTotleUnFreezeAmountByType(String accountNo,
			String freezeReason) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accountNo", accountNo);
		param.put("actionType", "W");
		param.put("actionReason", freezeReason);
		Long totalAmout = (Long) this.sqlMapClientTemplate.queryForObject(
				"sumAccountFreezeBalance", param);
		if (totalAmout != null) {
			return totalAmout.longValue();
		}
		return 0;
	}

	@Override
	public List<AccountLogDO> getByTransDate(Map<String, String> searchMap) {

		List<AccountLogDO> temp = this.sqlMapClientTemplate.queryForList(
				"getBalanceChangeReportByDate", searchMap);
		List<AccountLogDO> list = (List<AccountLogDO>) temp;
		return temp;
	}

	@Override
	public List<AccountLogDO> getByOutOrderNo(String outOrderNo) {
		Map map = new HashMap();
		map.put("transOutOrderNo", outOrderNo);
		return (List<AccountLogDO>) this.sqlMapClientTemplate.queryForList(
				"getBalanceChangeReportByOutOrderNo", map);
	}

}
