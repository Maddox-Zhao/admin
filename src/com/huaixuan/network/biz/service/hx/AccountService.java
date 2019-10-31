package com.huaixuan.network.biz.service.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.query.QueryPage;

public interface AccountService
{
	/**
	 * 添加现金账户
	 */
	public void addAccount(Account account);
	
	public QueryPage getAccountViewByConditionWithPage(Account account,int currPage,int pageSize);
	
	/**
	 * 更新现金账户
	 * @param account
	 */
	public void updateAccountByNotNull(Account account);
	
	/**
	 * 通过现金账户ID删除现金账户
	 */
	public void deleteAccountById(Long id);
	
	/**
	 * 通过部门编号得到该部门下的所有现金账户
	 * @param accountDepcode
	 * @return
	 */
	public List<Account> getAccountByDepcode(String accountDepcode);
	
	/**
	 * 通过现金账户ID得到该现金账户
	 * @param accountId
	 * @return
	 */
	public Account getAccountByAccountId(Long accountId);
	
}
