package com.huaixuan.network.biz.service.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.query.QueryPage;

/**
 *2012-6-12 ����11:08:54
 *Mr_Yang
 */
public interface AccountDetailService
{

	/**
	 * �õ����ֽ���µ����н���
	 * @param accountId
	 * @return
	 */
	public List<AccountDetail> getAccountDetailByAccountId(Long accountId);
	
	
	/**
	 * ���
	 * @param account
	 */
	public void addAccount(AccountDetail accountDetail);
	
	
	/**
	 * ͨ���ֽ��˻���ϸ��ID�õ�����������Ϣ
	 * @param accountDetailId
	 * @return
	 */
	public AccountDetail getAccountDetailByAccountDetailId(Long accountDetailId);
	
	
	public  QueryPage getAccountDetailByConditionWithPage(AccountDetail accountDetail, int currPage, int pageSize);
	
	
	public List<Admin> getAdminByDepCode(String depCode);
	
	
}
