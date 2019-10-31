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
 *����ƾ֤�Ĳ�Ʒ����
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
		
		//�õ������ܶ�
		Integer orderAmout = consignmentViewService.selectAmount(consignmentPro.getConId());
		orderAmout = orderAmout +  consignmentPro.getPrice(); //���
		Map parMap = new HashMap();
		parMap.put("id", consignmentPro.getConId());
		parMap.put("amount", orderAmout);
		consignmetViewPro.updateAmount(parMap); //���¼���ƾ֤�����ܶ�
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteConsignmentPro(String id,String conId)
	{
		Integer proAmount = consignmentProDao.selectConsignmentProAmount(id); //�õ��ò�Ʒ���
		consignmentProDao.deleteConsignmentPro(id); //ɾ�������еĲ�Ʒ
		Integer orderAmount = consignmentViewService.selectAmount(conId); //�õ����ڸò�Ʒ�������ܶ�
		if(orderAmount!=null && proAmount!=null)
		{
			Map parMap = new HashMap();
			parMap.put("id", conId);
			parMap.put("amount", orderAmount - proAmount); //�Ѷ����ܶ���ٺ� ���¶����ܶ�
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
		Integer proAmount = consignmentProDao.selectConsignmentProAmount(consignmentPro.getProId()+""); //�õ���Ʒ���ݿ��еļ۸�
		Integer c = consignmentPro.getPrice() - proAmount;  //���ݿ�������������
		Integer orderAmount = consignmentViewService.selectAmount(consignmentPro.getConId()); //�õ������ܶ�
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
		
		Integer proAmount = consignmentProDao.selectConsignmentProAmount(id);//�ò�Ʒ�ļ�Ǯ
		Integer proPaidAmount  = consignmentProDao.selectConsignmentProPaidAmount(id); //�ò�Ʒ(����)��ȡ�ߵĽ��
		
		if(paidMoney + proPaidAmount <= proAmount) //�������Ľ�����֮ǰȡ�ߵĽ��С�ڵ��ڲ�Ʒ��Ǯ
		{
			Map parMap = new HashMap();
			parMap.put("id", id);
			parMap.put("paidMoney", proPaidAmount + paidMoney);
			consignmentProDao.updatePaidAmount(parMap); //���²�Ʒ��ȡ���

			Integer sql_orderAmount = consignmentViewService.selectAmount(conId); //�õ������ܶ�
			Integer sql_orderPaidAmount = consignmentViewService.seletePaidAmount(conId); //�õ�������ȡ���
			
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
			consignmentViewService.updatePaidAmount(orderMap);  //���¶����ܶ�
			
			if("2".equals(adminAgent.getPassword())) //1.�ֽ� 2 ����ת�� ����ת�˵Ļ��Ͳ���Ҫ���ֽ�����������
				return;
			Admin admin = adminDao.getAdminById(adminAgent.getId()); //ͨ����¼ID�õ���ǰ�û���Ϣ
			if(admin.getDepCode() != null)
				parMap.put("accountDepCode", admin.getDepCode());
			
			List<Account>  list = accountDao.getAccountByDepCode(parMap); //��ǰ�û�����Ӧ���ֽ��˻�
			
			if(list.size() > 0)
			{
				//��װ�ֽ����Ϣ
				AccountDetail accountDetail = new AccountDetail();
				accountDetail.setGmtCreate(new Date());
				accountDetail.setGmtModify(new Date());
				accountDetail.setOperationType("����");
				accountDetail.setHrefId(Long.valueOf(conId));
				accountDetail.setType(1L);//֧��
				
				Long opreationId = -1L;
				try
				{
					opreationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
					
				}
				accountDetail.setOperationId(opreationId); //��¼�û���
				Long accountId = list.get(0).getId();//�ֽ�������Ӧ��ID,get(0)--һ�����Ŷ�Ӧһ���ֽ��
				accountDetail.setAccountId(accountId);
				accountDetail.setRmbAccount(Long.parseLong(paidMoney+""));
				accountDetailDao.addAccount(accountDetail);//�ֽ��ʻ���ϸ��
				
				
				//�����ֽ��
				Long rmb = list.get(0).getRmbBalance();
				list.get(0).setRmbBalance((rmb==null?0:rmb) - accountDetail.getRmbAccount());//�˻����
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
			//���¶����ܶ��״̬
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
