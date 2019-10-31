package com.huaixuan.network.biz.service.hx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService
{

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public void addAccount(Account account)
	{
		accountDao.addAccount(account);
	}

	@Override
	public void deleteAccountById(Long id)
	{
		accountDao.deleteAccountById(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getAccountViewByConditionWithPage(Account account,
			int currPage, int pageSize)
	{
		QueryPage queryPage = new QueryPage(account);
		Map parMap = queryPage.getParameters();
		Integer count = accountDao.getAccountByConditionWithPageCount(parMap);
		
		if(count != null && count.intValue() >0)
		{
			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			List<Account> list = accountDao.getAccountByConditionWithPage(parMap);
			if(list != null && list.size() >0)
			{
				queryPage.setItems(list);
			}
		}
		return queryPage;
	}

	@Override
	public void updateAccountByNotNull(Account account)
	{
		accountDao.updateAccountByNotNull(account);
	}

	@Override
	public List<Account> getAccountByDepcode(String accountDepcode)
	{
		Map parMap = new HashMap();
		parMap.put("accountDepCode", accountDepcode);
		return accountDao.getAccountByDepCode(parMap);
	}

	@Override
	public Account getAccountByAccountId(Long accountId)
	{
		return accountDao.getAccountByAccountId(accountId);
	}

}
