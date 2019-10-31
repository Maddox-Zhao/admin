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
		daigouProDao.addDaiGouPro(daigouPro); //��Ӳ�Ʒ
		
		//���¶����ܽ���״̬
		DaiGou daigou = daigouDao.getDaiGouByDaiGouCode(daigouPro.getDaigouCode());
		daigou.setAmount(daigou.getAmount().add(daigouPro.getAmount()));
		daigou.setPaidAmount(daigou.getPaidAmount().add(daigouPro.getPaidAmount()));
		daigou.setStatus(getStatus(daigou.getAmount(), daigou.getPaidAmount()));
		daigou.setNum(daigou.getNum() + daigouPro.getNum());
		daigouDao.updateDaiGouByNotNull(daigou);
		
		
		/*
		//�������֧����ʽΪ�ֽ�����Ҫ���ֽ���¼���β���
		if(daigou.getPayment().equals(2L)) //1.���� 2.�ֽ� 
		{
			//���ֽ���������
			Admin admin = adminDao.getAdminById(adminAgent.getId());
			if(StringUtil.isNotEmpty(admin.getDepCode()))
			{
				Map parMap = new HashMap();
				parMap.put("accountDepcode", admin.getDepCode());
				List<Account> accounts = accountDao.getAccountByDepCode(parMap); //���ݵ�¼�ʻ��Ĳ��ű�ŵõ��ò��ŵ��ֽ��˻�
				if(accounts.size() > 0)
				{
					AccountDetail accountDetail = new AccountDetail();
					accountDetail.setAccountId(accounts.get(0).getId()); //�����ֽ��˻���������ֽ���������ID
					long nowMoney  = daigouPro.getPaidAmount().longValue();
					accountDetail.setRmbAccount(nowMoney); //���õ�ǰ���׶�
					accountDetail.setType(0L); //0.����  1.֧��
					Long operationId = -1L;
					try
					{
						operationId = Long.parseLong(admin.getUserName());
					}
					catch (Exception e)
					{
					}
					accountDetail.setOperationId(operationId);//���õ�ǰ������					
					accountDetail.setOperationType("����");
					accountDetail.setHrefId(daigou.getId());
					accountDetailDao.addAccount(accountDetail);//�����ֽ�������¼
					
					//�����ֽ������
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
		
		//���������ܼۺ��Ѹ��������
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
			//���ֽ���������
			Admin admin = adminDao.getAdminById(adminAgent.getId());
			if(StringUtil.isNotEmpty(admin.getDepCode()))
			{
				Map parMap = new HashMap();
				parMap.put("accountDepcode", admin.getDepCode());
				List<Account> accounts = accountDao.getAccountByDepCode(parMap); //���ݵ�¼�ʻ��Ĳ��ű�ŵõ��ò��ŵ��ֽ��˻�
				if(accounts.size() > 0)
				{
					AccountDetail accountDetail = new AccountDetail();
					accountDetail.setAccountId(accounts.get(0).getId()); //�����ֽ��˻���������ֽ���������ID
					
					accountDetail.setRmbAccount(Math.round(money)); //���õ�ǰ���׶�
					accountDetail.setType(0L); //0.����  1.֧��
					Long operationId = -1L;
					try
					{
						operationId = Long.parseLong(admin.getUserName());
					}
					catch (Exception e)
					{
					}
					accountDetail.setOperationId(operationId);//���õ�ǰ������					
					accountDetail.setOperationType("����");
					accountDetail.setHrefId(daigou.getId());
					accountDetailDao.addAccount(accountDetail);//�����ֽ�������¼
					
					//�����ֽ������
					accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() + Math.round(money));
					accountDao.updateAccountByNotNull(accounts.get(0));
					
				}
							
			}
			
		}
		
	}


}
