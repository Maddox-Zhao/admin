package com.huaixuan.network.biz.service.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.query.QueryPage;

public interface AccountService
{
	/**
	 * ����ֽ��˻�
	 */
	public void addAccount(Account account);
	
	public QueryPage getAccountViewByConditionWithPage(Account account,int currPage,int pageSize);
	
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
	 * ͨ�����ű�ŵõ��ò����µ������ֽ��˻�
	 * @param accountDepcode
	 * @return
	 */
	public List<Account> getAccountByDepcode(String accountDepcode);
	
	/**
	 * ͨ���ֽ��˻�ID�õ����ֽ��˻�
	 * @param accountId
	 * @return
	 */
	public Account getAccountByAccountId(Long accountId);
	
}
