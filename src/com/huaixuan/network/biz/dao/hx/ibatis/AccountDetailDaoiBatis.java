package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.huaixuan.network.biz.dao.hx.AccountDetailDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.AccountDetail;

@Component("accountDetailDao")
public class AccountDetailDaoiBatis implements AccountDetailDao
{

	@Autowired
	private SqlMapClientTemplate sqlMap;
	
	@Override
	public void addAccount(AccountDetail accountDetail)
	{
		sqlMap.insert("insertAccountDetail",accountDetail);
	}

	@Override
	public AccountDetail getAccountDetailByAccountDetailId(
			Long accountDetailId)
	{
		return (AccountDetail)sqlMap.queryForObject("selectAccountDetailById",accountDetailId);
	}

	@Override
	public List<AccountDetail> getAccountDetailByAccountId(Long accountId)
	{
		return sqlMap.queryForList("getAccountDetailByAccountId",accountId);
	}

	@Override
	public List<AccountDetail> getAccountDetailByConditionWithPage(Map parMap)
	{
		return sqlMap.queryForList("getAccountDetailByConditionWithPage",parMap);
	}

	@Override
	public Integer getAccountDetailByConditionWithPageCount(Map parMap)
	{
		return (Integer)sqlMap.queryForObject("getAccountDetailByConditionWithPageCount",parMap);
	}

	@Override
	public List<Admin> getAdminByDepCode(Map parMap)
	{
		return sqlMap.queryForList("getAdminByDepCode",parMap);
	}
}
