package com.huaixuan.network.biz.dao.hx;

import java.util.List;

public interface AccountDetail
{
	/**
	 * 得到该现金表下的所有交易
	 * @param accountId
	 * @return
	 */
	public List<AccountDetail> getAccountDetailByAccountId(Long accountId);
	
	
	/**
	 * 添加
	 * @param account
	 */
	public void addAccount(AccountDetail accountDetail);
	
	
	/**
	 * 通过现金账户明细的ID得到该条交易信息
	 * @param accountDetailId
	 * @return
	 */
	public AccountDetail getAccountDetailByAccountDetailId(Long accountDetailId);
	
}
