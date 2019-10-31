package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.Account;

public interface AccountDao
{
	/**
	 * ����ֽ��˻�
	 */
	public void addAccount(Account account);
	
	
	/**
	 * �����ֽ��˻�
	 * @param account
	 */
	public void updateAccountByNotNull(Account account);
	
	/**
	 * ͨ���ֽ��˻�IDɾ���ֽ��˻�
	 */
	public void deleteAccountById(Long id);
	
	/**
	 * ͨ�����ŵõ���Ӧ���ֽ��˻�
	 * @param accountDepcode
	 * @return
	 */
	public List<Account> getAccountByDepCode(Map accountDepcode);
	
	/**
	 * ͨ���ֽ��˻�ID�õ����ֽ��˻�
	 * @param accountId
	 * @return
	 */
	public Account getAccountByAccountId(Long accountId);
	
	
	public List<Account> getAccountByConditionWithPage(Map parMap);
	
	
	public Integer getAccountByConditionWithPageCount(Map parMap);
	
}
