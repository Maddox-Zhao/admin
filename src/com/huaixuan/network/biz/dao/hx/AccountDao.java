package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.Account;

public interface AccountDao
{
	/**
	 * 添加现金账户
	 */
	public void addAccount(Account account);
	
	
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
	 * 通过部门得到对应的现金账户
	 * @param accountDepcode
	 * @return
	 */
	public List<Account> getAccountByDepCode(Map accountDepcode);
	
	/**
	 * 通过现金账户ID得到该现金账户
	 * @param accountId
	 * @return
	 */
	public Account getAccountByAccountId(Long accountId);
	
	
	public List<Account> getAccountByConditionWithPage(Map parMap);
	
	
	public Integer getAccountByConditionWithPageCount(Map parMap);
	
}
