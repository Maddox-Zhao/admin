package com.huaixuan.network.biz.service.hx.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AdminDao;
import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.dao.hx.AccountDetailDao;
import com.huaixuan.network.biz.dao.hx.DaiGouDao;
import com.huaixuan.network.biz.dao.hx.DaiGouProDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.domain.hx.DaiGou;
import com.huaixuan.network.biz.domain.hx.DaiGouPro;
import com.huaixuan.network.biz.service.hx.DaiGouProService;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * 2012-03-09 14:05
 * @author Mr_Yang
 *
 */
@Service("daigouProService")
public class DaiGouProServiceImpl implements DaiGouProService
{
	
	@Autowired
	private DaiGouProDao daigouProDao;
	
	@Autowired
	private DaiGouDao daigouDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AccountDetailDao accountDetailDao;

	@Override
	public void addDaiGouPro(DaiGouPro daigouPro,AdminAgent adminAgent)
	{
		daigouProDao.addDaiGouPro(daigouPro); //添加产品
		
		//更新订单总金额和状态
		DaiGou daigou = daigouDao.getDaiGouByDaiGouCode(daigouPro.getDaigouCode());
		daigou.setAmount(daigou.getAmount().add(daigouPro.getAmount()));
		daigou.setPaidAmount(daigou.getPaidAmount().add(daigouPro.getPaidAmount()));
		daigou.setStatus(getStatus(daigou.getAmount(), daigou.getPaidAmount()));
		daigou.setNum(daigou.getNum() + daigouPro.getNum());
		daigouDao.updateDaiGouByNotNull(daigou);
		
		
		/*
		//如果订单支付方式为现金，则需要在现金表记录本次操作
		if(daigou.getPayment().equals(2L)) //1.银联 2.现金 
		{
			//往现金表插入数据
			Admin admin = adminDao.getAdminById(adminAgent.getId());
			if(StringUtil.isNotEmpty(admin.getDepCode()))
			{
				Map parMap = new HashMap();
				parMap.put("accountDepcode", admin.getDepCode());
				List<Account> accounts = accountDao.getAccountByDepCode(parMap); //根据登录帐户的部门编号得到该部门的现金账户
				if(accounts.size() > 0)
				{
					AccountDetail accountDetail = new AccountDetail();
					accountDetail.setAccountId(accounts.get(0).getId()); //设置现金账户详情表与现金表相关联的ID
					long nowMoney  = daigouPro.getPaidAmount().longValue();
					accountDetail.setRmbAccount(nowMoney); //设置当前交易额
					accountDetail.setType(0L); //0.收入  1.支出
					Long operationId = -1L;
					try
					{
						operationId = Long.parseLong(admin.getUserName());
					}
					catch (Exception e)
					{
					}
					accountDetail.setOperationId(operationId);//设置当前操作人					
					accountDetail.setOperationType("代购");
					accountDetail.setHrefId(daigou.getId());
					accountDetailDao.addAccount(accountDetail);//增加现金详情表记录
					
					//更新现金主余额
					accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() + nowMoney);
					accountDao.updateAccountByNotNull(accounts.get(0));
					
				}
							
			}
		}
		*/
		
	}

	@Override
	public void deleteDaiGouProById(Long id)
	{
		DaiGouPro daigouPro = daigouProDao.getDaiGouProById(id);
		daigouProDao.deleteDaiGouProById(id);
		
		//将订单的总价和已付定金减少
		DaiGou daigou =daigouDao.getDaiGouByDaiGouCode(daigouPro.getDaigouCode());
		daigou.setPaidAmount(daigou.getPaidAmount().subtract(daigouPro.getPaidAmount()));
		daigou.setAmount(daigou.getAmount().subtract(daigouPro.getAmount()));
		daigou.setStatus(getStatus(daigou.getAmount(), daigou.getPaidAmount()));
		daigouDao.updateDaiGouByNotNull(daigou);
	}

	@Override
	public List<DaiGouPro> getDaiGouProByDaiGouCode(String daigouCode)
	{
		return daigouProDao.getDaiGouProListByDaiGouCode(daigouCode);
	}

	@Override
	public void updateDaiGouProByNotNull(DaiGouPro daigouPro)
	{
		daigouProDao.updatDaiGouProByNotNull(daigouPro);
	}
	
	private String getStatus(BigDecimal amount,BigDecimal paidAmoun)
	{
		if(amount.compareTo(paidAmoun) == 0)
		{
			return "paid";
		}
		if(paidAmoun.intValue() == 0)
		{
			return "no_pay";
		}
		else
		{
			return "part_paid";
		}
	}

	@Override
	public DaiGouPro getDaiGouProById(Long id)
	{
		return (DaiGouPro)daigouProDao.getDaiGouProById(id);
	}

	@Override
	public void draw(DaiGouPro daiGouPro, AdminAgent adminAgent, String type,
			Double money)
	{
		DaiGou daigou = daigouDao.getDaiGouById(daiGouPro.getDaigouId());
		daigou.setPaidAmount(daigou.getPaidAmount().add(new BigDecimal(money)));
		daigouDao.updateDaiGouByNotNull(daigou);
		
		daiGouPro.setPaidAmount(daiGouPro.getPaidAmount().add(new BigDecimal(money)));
		daigouProDao.updatDaiGouProByNotNull(daiGouPro);
		
		if("1".equals(type))
		{
			//往现金表插入数据
			Admin admin = adminDao.getAdminById(adminAgent.getId());
			if(StringUtil.isNotEmpty(admin.getDepCode()))
			{
				Map parMap = new HashMap();
				parMap.put("accountDepcode", admin.getDepCode());
				List<Account> accounts = accountDao.getAccountByDepCode(parMap); //根据登录帐户的部门编号得到该部门的现金账户
				if(accounts.size() > 0)
				{
					AccountDetail accountDetail = new AccountDetail();
					accountDetail.setAccountId(accounts.get(0).getId()); //设置现金账户详情表与现金表相关联的ID
					
					accountDetail.setRmbAccount(Math.round(money)); //设置当前交易额
					accountDetail.setType(0L); //0.收入  1.支出
					Long operationId = -1L;
					try
					{
						operationId = Long.parseLong(admin.getUserName());
					}
					catch (Exception e)
					{
					}
					accountDetail.setOperationId(operationId);//设置当前操作人					
					accountDetail.setOperationType("代购");
					accountDetail.setHrefId(daigou.getId());
					accountDetailDao.addAccount(accountDetail);//增加现金详情表记录
					
					//更新现金主余额
					accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() + Math.round(money));
					accountDao.updateAccountByNotNull(accounts.get(0));
					
				}
							
			}
			
		}
		
	}


}
