package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.AccountDetail;

public interface AccountDetailDao
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
	
	
	
	public Integer getAccountDetailByConditionWithPageCount(Map parMap);

	public List<AccountDetail> getAccountDetailByConditionWithPage(Map parMap);
	
	
	public List<Admin> getAdminByDepCode(Map parMap);
	
}
