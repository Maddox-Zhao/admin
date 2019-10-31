package com.huaixuan.network.biz.dao.hx;

import java.util.List;

public interface AccountDetail
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
	
}
