package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.AccountDetail;

public interface AccountDetailDao
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
	
	
	
	public Integer getAccountDetailByConditionWithPageCount(Map parMap);

	public List<AccountDetail> getAccountDetailByConditionWithPage(Map parMap);
	
	
	public List<Admin> getAdminByDepCode(Map parMap);
	
}
