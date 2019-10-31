package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.domain.hx.Account;

@Component("accounDao")
public class AccountDaoIbatis implements AccountDao
{
	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	@Override
	public void addAccount(Account account)
	{
		sqlMap.insert("insertAccount",account);
	}

	@Override
	public void deleteAccountById(Long id)
	{
		sqlMap.delete("deleteAccountById",id);
	}

	@Override
	public void updateAccountByNotNull(Account account)
	{
		sqlMap.update("updateAccountByNotNull",account);
	}
	
	
	@Override
	public List<Account> getAccountByConditionWithPage(Map parMap)
	{
		return sqlMap.queryForList("getAccountByConditionWithPage",parMap);
	}

	@Override
	public Integer getAccountByConditionWithPageCount(Map parMap)
	{
		return (Integer)sqlMap.queryForObject("getAccountByConditionWithPageCount",parMap);
	}

	@Override
	public List<Account> getAccountByDepCode(Map accountDepcode)
	{
		return sqlMap.queryForList("getHKAccountByDepcode",accountDepcode);
	}

	@Override
	public Account getAccountByAccountId(Long accountId)
	{
		return (Account)sqlMap.queryForObject("selectAccountById",accountId);
	}

}
