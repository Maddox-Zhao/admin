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
 * @author Mr_Yang �չ������Ĳ�Ʒ����
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
		acquisitionProDao.addAcquisitionPro(acquisitionPro);//���չ�������Ӳ�Ʒ
		Acquisition acquisition =  acquisitionDao.selectAcquisitionViewByAcpCode(acquisitionPro.getAcqCode()); //ͨ����Ž���Ʒ������Ķ����õ�
		acquisition.setAmount(acquisition.getAmount().add(acquisitionPro.getAmount())); //���ö������ܼ�Ǯ 
		acquisition.setPaidAmount(acquisition.getPaidAmount().add(acquisitionPro.getPaidAmount())); //���ö�������ȡ��Ǯ
		acquisition.setStatus(getStatus(acquisition.getAmount(), acquisition.getPaidAmount())); //������״̬
		acquisitionDao.updateAcquisitionViewByNotNull(acquisition); //���¶����ļ�Ǯ��״̬
		
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
				long nowMoney  = acquisitionPro.getPaidAmount().longValue();
				accountDetail.setRmbAccount(nowMoney); //���õ�ǰ���׶�
				accountDetail.setType(1L); //0.����  1.֧��
				Long operationId = -1L;
				try
				{
					operationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
				}
				accountDetail.setOperationId(operationId);//���õ�ǰ������
				accountDetail.setOperationType("�չ�");
				accountDetail.setHrefId(acquisitionPro.getAcqId());
				accountDetailDao.addAccount(accountDetail);//�����ֽ�������¼
				
				//�����ֽ������
				accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() - nowMoney);
				accountDao.updateAccountByNotNull(accounts.get(0));
				
			}
						
		}
		
	}

	//ɾ���ö����Ĳ�Ʒ
	@Override
	public void deleteAcquisitionProById(Long id)
	{
		AcquisitionPro acquisitionPro = acquisitionProDao.getOneAcquisitionById(id); //�õ���id�Ĳ�Ʒ��Ϣ
		acquisitionProDao.deleteAcquisitionProById(id); //ɾ��
		BigDecimal amount = acquisitionPro.getAmount();
		BigDecimal paidAmount = acquisitionPro.getPaidAmount();
		Acquisition acquisition = acquisitionDao.selectAcquisitionViewByAcpCode(acquisitionPro.getAcqCode());
		//���ٶ������ܼ�Ǯ����ȡ���
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
	 * ȡ��
	 */
	@Override
	@Transactional
	public void draw(Long id, Long acqId, AdminAgent adminAgent, Double money)
	{
		//���¸ò�Ʒ��ȡ����
		AcquisitionPro acquisitionPro = acquisitionProDao.getOneAcquisitionById(id);
		BigDecimal paidAmount = acquisitionPro.getPaidAmount();
		paidAmount = paidAmount.add(new BigDecimal(money)); //�����Ѿ�ȡ�ߵĽ��
		acquisitionPro.setPaidAmount(paidAmount);
	
		acquisitionProDao.updateAcquisitionProByNotNull(acquisitionPro);//������ȡ���
		
		//���¶����Ѹ�����״̬
		Acquisition acquisition = acquisitionDao.getAcquisitionById(acqId);
		BigDecimal amount = acquisition.getAmount();
		paidAmount = acquisition.getPaidAmount();
		String status = getStatus(amount, paidAmount);
		acquisition.setStatus(status);//���õ�ǰ״̬
		paidAmount = paidAmount.add(new BigDecimal(money));
		acquisition.setPaidAmount(paidAmount); //�����Ѹ����
		acquisitionDao.updateAcquisitionViewByNotNull(acquisition); //����
		
		if("2".equals(adminAgent.getPassword())) //1.�ֽ� 2 ����ת�� ����ת�˵Ļ��Ͳ���Ҫ���ֽ�����������
			return;
		
		//���ֽ���������
		Admin admin = adminDao.getAdminById(adminAgent.getId());
		if(StringUtil.isNotEmpty(admin.getDepCode()))
		{
			Map parMap = new HashMap();
			parMap.put("accountDepcode", admin.getDepCode());
			List<Account> accounts = accountDao.getAccountByDepCode(parMap); //���ݵ�¼�ʻ��Ĳ��ű�ŵõ��ò��ŵ��ֽ��˻�
			if(accounts != null && accounts.size() > 0)
			{
				AccountDetail accountDetail = new AccountDetail();
				accountDetail.setAccountId(accounts.get(0).getId()); //�����ֽ��˻���������ֽ���������ID
				accountDetail.setRmbAccount(money.longValue()); //���õ�ǰ���׶�
				accountDetail.setType(1L); //0.����  1.֧��
				Long operationId = -1L;
				try
				{
					operationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
				}
				accountDetail.setOperationId(operationId);//���õ�ǰ������
				accountDetail.setOperationType("�չ�");
				accountDetail.setHrefId(acquisitionPro.getAcqId());
				accountDetailDao.addAccount(accountDetail);//�����ֽ�������¼
				
				//�����ֽ������
				accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() - money.longValue());
				accountDao.updateAccountByNotNull(accounts.get(0));
				
			}
						
		}
	}

	private void addAccountDetail(AdminAgent adminAgent,AcquisitionPro acquisitionPro)
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
				long nowMoney  = acquisitionPro.getPaidAmount().longValue();
				accountDetail.setRmbAccount(nowMoney); //���õ�ǰ���׶�
				accountDetail.setType(1L); //0.����  1.֧��
				Long operationId = -1L;
				try
				{
					operationId = Long.parseLong(admin.getUserName());
				}
				catch (Exception e)
				{
				}
				accountDetail.setOperationId(operationId);//���õ�ǰ������
				accountDetail.setOperationType("�չ�");
				accountDetail.setHrefId(acquisitionPro.getAcqId());
				accountDetailDao.addAccount(accountDetail);//�����ֽ�������¼
				
				//�����ֽ������
				accounts.get(0).setRmbBalance(accounts.get(0).getRmbBalance() - nowMoney);
				accountDao.updateAccountByNotNull(accounts.get(0));
				
			}
						
		}
	}
}
