package com.huaixuan.network.biz.service.hx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AdminDao;
import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.dao.hx.AccountDetailDao;
import com.huaixuan.network.biz.dao.hx.ConsignmentProDao;
import com.huaixuan.network.biz.dao.hx.ConsignmentViewDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.domain.hx.ConsignmentPro;
import com.huaixuan.network.biz.domain.hx.ConsignmentView;
import com.huaixuan.network.biz.service.hx.ConsignmentProService;
import com.huaixuan.network.biz.service.hx.ConsignmentViewService;

/**
 * 2012-02-16 10:52
 * @author Mr_Yang
 *寄卖凭证的产品操作
 */
@Service("consignmentProService")
public class ConsignmentProServiceImpl implements ConsignmentProService
{
	
	@Autowired
	private ConsignmentProDao consignmentProDao;
	
	@Autowired
	private AccountDetailDao accountDetailDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ConsignmentViewDao consignmentViewDao;
	
	
	@Autowired
	private ConsignmentViewService consignmentViewService;
	
	
	@Autowired
	private ConsignmentViewDao consignmetViewPro;
	
	@Override
	@SuppressWarnings("unchecked")
	public void addConsignmentPro(ConsignmentPro consignmentPro)
	{
		consignmentProDao.addConsignmentPro(consignmentPro);
		
		//得到订单总额
		Integer orderAmout = consignmentViewService.selectAmount(consignmentPro.getConId());
		orderAmout = orderAmout +  consignmentPro.getPrice(); //相加
		Map parMap = new HashMap();
		parMap.put("id", consignmentPro.getConId());
		parMap.put("amount", orderAmout);
		consignmetViewPro.updateAmount(parMap); //更新寄卖凭证订单总额
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteConsignmentPro(String id,String conId)
	{
		Integer proAmount = consignmentProDao.selectConsignmentProAmount(id); //得到该产品金额
		consignmentProDao.deleteConsignmentPro(id); //删除订单中的产品
		Integer orderAmount = consignmentViewService.selectAmount(conId); //得到属于该产品订单的总额
		if(orderAmount!=null && proAmount!=null)
		{
			Map parMap = new HashMap();
			parMap.put("id", conId);
			parMap.put("amount", orderAmount - proAmount); //把订单总额减少后 更新订单总额
			consignmentViewService.updateAmount(parMap);
		}
	}

	@Override
	public List<ConsignmentPro> getConsignmentPro(String conId)
	{
		return consignmentProDao.getConsignmentById(conId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateConsignmentPro(ConsignmentPro consignmentPro)
	{
		Integer proAmount = consignmentProDao.selectConsignmentProAmount(consignmentPro.getProId()+""); //得到产品数据库中的价格
		Integer c = consignmentPro.getPrice() - proAmount;  //数据库和输入的相差多少
		Integer orderAmount = consignmentViewService.selectAmount(consignmentPro.getConId()); //得到订单总额
		Map parMap = new HashMap();
		parMap.put("id",consignmentPro.getConId());
		parMap.put("amount", orderAmount + c);
		consignmentViewService.updateAmount(parMap);
		consignmentProDao.updateConsignmentPro(consignmentPro);
	}

	@Override
	public ConsignmentPro getOneConsignmentPro(String id)
	{
		return consignmentProDao.getOntConsignmentPro(id);
	}


	@Override
	public Integer selectPaidAmount(String id)
	{
		return consignmentProDao.selectConsignmentProAmount(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updatePaidAmount(String id,Integer paidMoney,String conId,AdminAgent adminAgent)
	{
		
		Integer proAmount = consignmentProDao.selectConsignmentProAmount(id);//该产品的价钱
		Integer proPaidAmount  = consignmentProDao.selectConsignmentProPaidAmount(id); //该产品(已售)已取走的金额
		
		if(paidMoney + proPaidAmount <= proAmount) //如果输入的金额加上之前取走的金额小于等于产品价钱
		{
			Map parMap = new HashMap();
			parMap.put("id", id);
			parMap.put("paidMoney", proPaidAmount + paidMoney);
			consignmentProDao.updatePaidAmount(parMap); //更新产品已取金额

			Integer sql_orderAmount = consignmentViewService.selectAmount(conId); //得到订单总额
			Integer sql_orderPaidAmount = consignmentViewService.seletePaidAmount(conId); //得到订单已取金额
			
			sql_orderPaidAmount =  sql_orderPaidAmount + paidMoney;
			Map orderMap = new HashMap();
			
			orderMap.put("id", conId);
			orderMap.put("paidAmount", sql_orderPaidAmount);

			if(sql_orderPaidAmount.intValue() == sql_orderAmount.intValue())
			{
				orderMap.put("status", "paid");
			}
			else if(sql_orderPaidAmount.intValue() < sql_orderAmount.intValue())
			{
				orderMap.put("status", "part_paid");
			}
			else
				orderMap.put("status", "no_pay");
			consignmentViewService.updatePaidAmount(orderMap);  //更新订单总额
			
			if("2".equals(adminAgent.getPassword())) //1.现金。 2 银行转账 银行转账的话就不需要往现金表插入数据了
				return;
			Admin admin = adminDao.getAdminById(adminAgent.getId()); //通过登录ID得到当前用户信息
			if(admin.getDepCode() != null)
				parMap.put("accountDepCode", admin.getDepCode());
			
			List<Account>  list = accountDao.getAccountByDepCode(parMap); //当前用户所对应的现金账户
			
			if(list.size() > 0)
			{
				//组装现金表信息
				AccountDetail accountDetail = new AccountDetail();
				accountDetail.setGmtCreate(new Date());
				accountDetail.setGmtModify(new Date());
				accountDetail.setOperationType("寄卖");
				accountDetail.setHrefId(Long.valueOf(conId));
				accountDetail.setType(1L);//支出
				
				Long opreationId = -1L;
				try
				{
					opreationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
					
				}
				accountDetail.setOperationId(opreationId); //登录用户名
				Long accountId = list.get(0).getId();//现金表主表对应的ID,get(0)--一个部门对应一个现金表
				accountDetail.setAccountId(accountId);
				accountDetail.setRmbAccount(Long.parseLong(paidMoney+""));
				accountDetailDao.addAccount(accountDetail);//现金帐户明细表
				
				
				//更新现金表
				Long rmb = list.get(0).getRmbBalance();
				list.get(0).setRmbBalance((rmb==null?0:rmb) - accountDetail.getRmbAccount());//账户余额
				accountDao.updateAccountByNotNull(list.get(0));
				
			}
			
			
			
			
		}
		
	}

	@Override
	public void updateProStatusById(Long id,String type)
	{
		String status = "sold";
		Map parMap = new HashMap();
		if("0".equals(type))
		{
			//更新订单总额和状态
			status = "back";
			ConsignmentPro p = consignmentProDao.getOntConsignmentPro(id+"");
			ConsignmentView consignmentView = consignmentViewDao.getOneConsignment(p.getConId());
			consignmentView.setAmount(consignmentView.getAmount() - p.getPrice());
			parMap.put("id", p.getConId());
			parMap.put("amount", consignmentView.getAmount());
			consignmentViewDao.updateAmount(parMap);
			
			if(consignmentView.getAmount() == consignmentView.getPaidAmount())
			{
				consignmentView.setStatus("paid");
				consignmentViewDao.updateConsignmentView(consignmentView);
			}
		}
		if("1".equals(type))
			status = "sold";
		
		parMap.put("status", status);
		parMap.put("id", id);
		consignmentProDao.updateProStatusById(parMap);
	}


	
}
