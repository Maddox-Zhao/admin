package com.huaixuan.network.biz.service.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.query.QueryPage;

/**
 *2012-6-12 上午11:08:54
 *Mr_Yang
 */
public interface AccountDetailService
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
	
	
	public  QueryPage getAccountDetailByConditionWithPage(AccountDetail accountDetail, int currPage, int pageSize);
	
	
	public List<Admin> getAdminByDepCode(String depCode);
	
	
}
