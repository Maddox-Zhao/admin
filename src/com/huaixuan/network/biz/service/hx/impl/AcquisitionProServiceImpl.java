package com.huaixuan.network.biz.service.hx.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.admin.AdminDao;
import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.dao.hx.AccountDetailDao;
import com.huaixuan.network.biz.dao.hx.AcquisitionDao;
import com.huaixuan.network.biz.dao.hx.AcquisitionProDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.domain.hx.Acquisition;
import com.huaixuan.network.biz.domain.hx.AcquisitionPro;
import com.huaixuan.network.biz.service.hx.AcquisitionProService;
import com.hundsun.common.lang.StringUtil;

/**
 * 2012-03-02 17:54
 * 
 * @author Mr_Yang 收购订单的产品操作
 */
@Service("acquisitionProService")
public class AcquisitionProServiceImpl implements AcquisitionProService
{
	@Autowired
	private AcquisitionDao acquisitionDao;

	@Autowired
	private AcquisitionProDao acquisitionProDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AccountDetailDao accountDetailDao;
	
	@Autowired
	private AdminDao adminDao;
	

	@Override
	public void addAcquisitionPro(AcquisitionPro acquisitionPro,AdminAgent adminAgent)
	{
		acquisitionProDao.addAcquisitionPro(acquisitionPro);//向收购订单添加产品
		Acquisition acquisition =  acquisitionDao.selectAcquisitionViewByAcpCode(acquisitionPro.getAcqCode()); //通过编号将产品相关联的订单得到
		acquisition.setAmount(acquisition.getAmount().add(acquisitionPro.getAmount())); //设置订单的总价钱 
		acquisition.setPaidAmount(acquisition.getPaidAmount().add(acquisitionPro.getPaidAmount())); //设置订单的已取价钱
		acquisition.setStatus(getStatus(acquisition.getAmount(), acquisition.getPaidAmount())); //设置其状态
		acquisitionDao.updateAcquisitionViewByNotNull(acquisition); //更新订单的价钱和状态
		
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
				long nowMoney  = acquisitionPro.getPaidAmount().longValue();
				accountDetail.setRmbAccount(nowMoney); //设置当前交易额
				accountDetail.setType(1L); //0.收入  1.支出
				Long operationId = -1L;
				try
				{
					operationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
				}
				accountDetail.setOperationId(operationId);//设置当前操作人
				accountDetail.setOperationType("收购");
				accountDetail.setHrefId(acquisitionPro.getAcqId());
				accountDetailDao.addAccount(accountDetail);//增加现金详情表记录
				
				//更新现金主余额
				accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() - nowMoney);
				accountDao.updateAccountByNotNull(accounts.get(0));
				
			}
						
		}
		
	}

	//删除该订单的产品
	@Override
	public void deleteAcquisitionProById(Long id)
	{
		AcquisitionPro acquisitionPro = acquisitionProDao.getOneAcquisitionById(id); //得到该id的产品信息
		acquisitionProDao.deleteAcquisitionProById(id); //删除
		BigDecimal amount = acquisitionPro.getAmount();
		BigDecimal paidAmount = acquisitionPro.getPaidAmount();
		Acquisition acquisition = acquisitionDao.selectAcquisitionViewByAcpCode(acquisitionPro.getAcqCode());
		//减少订单的总价钱和已取金额
		acquisition.setAmount(acquisition.getAmount().subtract(amount));  
		acquisition.setPaidAmount(acquisition.getPaidAmount().subtract(paidAmount));
		acquisition.setStatus(getStatus(amount, paidAmount));
		acquisitionDao.updateAcquisitionViewByNotNull(acquisition);
		
	}

	@Override
	public List<AcquisitionPro> getAcquisitionByAcqCode(String acqCode)
	{
		return acquisitionProDao.getAcquisitionByAcqCode(acqCode);
	}

	@Override
	public AcquisitionPro getOneAcquisitionById(Long id)
	{
		return acquisitionProDao.getOneAcquisitionById(id);
	}

	@Override
	public void updateAcquisitionProByNotNull(AcquisitionPro acquisitionPro)
	{
		acquisitionProDao.updateAcquisitionProByNotNull(acquisitionPro);
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

	/**
	 * 取款
	 */
	@Override
	@Transactional
	public void draw(Long id, Long acqId, AdminAgent adminAgent, Double money)
	{
		//更新该产品的取款金额
		AcquisitionPro acquisitionPro = acquisitionProDao.getOneAcquisitionById(id);
		BigDecimal paidAmount = acquisitionPro.getPaidAmount();
		paidAmount = paidAmount.add(new BigDecimal(money)); //现在已经取走的金额
		acquisitionPro.setPaidAmount(paidAmount);
	
		acquisitionProDao.updateAcquisitionProByNotNull(acquisitionPro);//更新已取金额
		
		//更新订单已付金额和状态
		Acquisition acquisition = acquisitionDao.getAcquisitionById(acqId);
		BigDecimal amount = acquisition.getAmount();
		paidAmount = acquisition.getPaidAmount();
		String status = getStatus(amount, paidAmount);
		acquisition.setStatus(status);//设置当前状态
		paidAmount = paidAmount.add(new BigDecimal(money));
		acquisition.setPaidAmount(paidAmount); //设置已付金额
		acquisitionDao.updateAcquisitionViewByNotNull(acquisition); //更新
		
		if("2".equals(adminAgent.getPassword())) //1.现金。 2 银行转账 银行转账的话就不需要往现金表插入数据了
			return;
		
		//往现金表插入数据
		Admin admin = adminDao.getAdminById(adminAgent.getId());
		if(StringUtil.isNotEmpty(admin.getDepCode()))
		{
			Map parMap = new HashMap();
			parMap.put("accountDepcode", admin.getDepCode());
			List<Account> accounts = accountDao.getAccountByDepCode(parMap); //根据登录帐户的部门编号得到该部门的现金账户
			if(accounts != null && accounts.size() > 0)
			{
				AccountDetail accountDetail = new AccountDetail();
				accountDetail.setAccountId(accounts.get(0).getId()); //设置现金账户详情表与现金表相关联的ID
				accountDetail.setRmbAccount(money.longValue()); //设置当前交易额
				accountDetail.setType(1L); //0.收入  1.支出
				Long operationId = -1L;
				try
				{
					operationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
				}
				accountDetail.setOperationId(operationId);//设置当前操作人
				accountDetail.setOperationType("收购");
				accountDetail.setHrefId(acquisitionPro.getAcqId());
				accountDetailDao.addAccount(accountDetail);//增加现金详情表记录
				
				//更新现金主余额
				accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() - money.longValue());
				accountDao.updateAccountByNotNull(accounts.get(0));
				
			}
						
		}
	}

	private void addAccountDetail(AdminAgent adminAgent,AcquisitionPro acquisitionPro)
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
				long nowMoney  = acquisitionPro.getPaidAmount().longValue();
				accountDetail.setRmbAccount(nowMoney); //设置当前交易额
				accountDetail.setType(1L); //0.收入  1.支出
				Long operationId = -1L;
				try
				{
					operationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
				}
				accountDetail.setOperationId(operationId);//设置当前操作人
				accountDetail.setOperationType("收购");
				accountDetail.setHrefId(acquisitionPro.getAcqId());
				accountDetailDao.addAccount(accountDetail);//增加现金详情表记录
				
				//更新现金主余额
				accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() - nowMoney);
				accountDao.updateAccountByNotNull(accounts.get(0));
				
			}
						
		}
	}
}
