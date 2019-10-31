package com.huaixuan.network.biz.service.hx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.dao.hx.AccountDetailDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.AccountDetailService;

/**
 *2012-6-12 上午11:10:54
 *Mr_Yang
 */
@Service("accountDetail")
public class AccountDetailServiceImpl implements AccountDetailService
{

	@Autowired
	private AccountDetailDao accountDetailDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public void addAccount(AccountDetail accountDetail)
	{
		Account account = accountDao.getAccountByAccountId(accountDetail.getAccountId());
		if(account == null)
			return;
		if(accountDetail.getAccountType().equals(1)) //欧元账户
		{
			accountDetail.setEuroAccount(Long.valueOf(accountDetail.getTradePrice()));
			
			if(new Long(0).equals(accountDetail.getType()))//收入
			{
				Integer nowPrice = account.getEuBalance().intValue()+accountDetail.getTradePrice();
				account.setEuBalance(Long.valueOf(nowPrice));
			}
			else //支出
			{
				Integer nowPrice = account.getEuBalance().intValue()-accountDetail.getTradePrice();
				account.setEuBalance(Long.valueOf(nowPrice));
			}
		}
		else if(accountDetail.getAccountType().equals(2)) //港元账户
		{
			accountDetail.setHkAccount(Long.valueOf(accountDetail.getTradePrice()));
			
			if(new Long(0).equals(accountDetail.getType()))//收入
			{
				Integer nowPrice = account.getHkBalance().intValue()+accountDetail.getTradePrice();
				account.setHkBalance(Long.valueOf(nowPrice));
			}
			else //支出
			{
				Integer nowPrice = account.getHkBalance().intValue()-accountDetail.getTradePrice();
				account.setHkBalance(Long.valueOf(nowPrice));
			}
		}
		else if(accountDetail.getAccountType().equals(3))//人民币
		{
			accountDetail.setRmbAccount(Long.valueOf(accountDetail.getTradePrice()));
			if(new Long(0).equals(accountDetail.getType()))//收入
			{
				Integer nowPrice = account.getRmbBalance().intValue()+accountDetail.getTradePrice();
				account.setRmbBalance(Long.valueOf(nowPrice));
			}
			else //支出
			{
				Integer nowPrice = account.getRmbBalance().intValue()-accountDetail.getTradePrice();
				account.setRmbBalance(Long.valueOf(nowPrice));
			}
		}
		else if(accountDetail.getAccountType().equals(4)) //美元账户
		{
			accountDetail.setDollarAccount(Long.valueOf(accountDetail.getTradePrice()));
			if(new Long(0).equals(accountDetail.getType()))//收入
			{
				Integer nowPrice = account.getDollarBalance().intValue()+accountDetail.getTradePrice();
				account.setDollarBalance(Long.valueOf(nowPrice));
			}
			else //支出
			{
				Integer nowPrice = account.getDollarBalance().intValue()-accountDetail.getTradePrice();
				account.setDollarBalance(Long.valueOf(nowPrice));
			}
		}
		accountDetailDao.addAccount(accountDetail); //添加
		accountDao.updateAccountByNotNull(account); //更新账户余额
	}

	@Override
	public AccountDetail getAccountDetailByAccountDetailId(Long accountDetailId)
	{
		return accountDetailDao.getAccountDetailByAccountDetailId(accountDetailId);
	}

	@Override
	public List<AccountDetail> getAccountDetailByAccountId(Long accountId)
	{
		return accountDetailDao.getAccountDetailByAccountId(accountId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getAccountDetailByConditionWithPage(
			AccountDetail accountDetail, int currPage, int pageSize)
	{
		QueryPage queryPage = new QueryPage(accountDetail);
		Map parMap = queryPage.getParameters();
		Integer count = accountDetailDao.getAccountDetailByConditionWithPageCount(parMap);
		if(count != null && count.intValue() > 0)
		{
			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			List<AccountDetail> list = accountDetailDao.getAccountDetailByConditionWithPage(parMap);
			if(list != null && list.size() > 0)
			{
				queryPage.setItems(list);
			}
		}
		return queryPage;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Admin> getAdminByDepCode(String depCode)
	{
		Map parMap = new HashMap();
		parMap.put("depCode", depCode);
		return accountDetailDao.getAdminByDepCode(parMap);
		
	}

}
