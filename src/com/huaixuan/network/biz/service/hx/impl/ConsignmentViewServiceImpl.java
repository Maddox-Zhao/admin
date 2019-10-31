package com.huaixuan.network.biz.service.hx.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.hx.ConsignmentViewDao;
import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.ConsignmentView;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.ConsignmentViewService;

@Service("consignmentViewService")
public class ConsignmentViewServiceImpl implements ConsignmentViewService
{

	@Autowired
	private ConsignmentViewDao consignmentViewDao;

	@Autowired
	private CustomerDao customerDao;

	private @Value("${file.upload.dir}")
	String path;

	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getConsignmentViewByConditionWithPage(
			ConsignmentView consignmentview, int currPage, int pageSize)
	{
		QueryPage queryPage = new QueryPage(consignmentview);

		Map parMap = queryPage.getParameters();

		Integer count = consignmentViewDao
				.getConsignmentViewByConditionWithPageCount(parMap);

		if (count != null && count.intValue() > 0)
		{
			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());

			List<ConsignmentView> list = consignmentViewDao.getConsignmentViewByConditionWithPage(parMap);

			if (list != null && list.size() > 0)
			{
				queryPage.setItems(list);
			}
		}

		return queryPage;
	}

	@Override
	public Integer addConsignmentView(ConsignmentView consignmentView,
			Customer customer,AdminAgent adminAgent)
	{
		// �õ�������ID
		long operatorId =  -1L;
		try
		{
			operatorId = Long.parseLong(adminAgent.getUsername());
		}
		catch(Exception e)
		{
			operatorId = -1L;
		}
		consignmentView.setOperatorId(operatorId + ""); // ���ò�����ID

		Integer customerId = customerDao.selectOneCustomerId(customer); // ��ѯ�ͻ�ID

		if (customerId == null) // �жϿͻ��Ƿ���ڣ����������һ���µ��û�
		{
			consignmentView.setCustomerId(customerDao.addOneCustomer(customer)); // ������û�
		}
		else
		{
			consignmentView.setCustomerId(customerId);
		}

		return consignmentViewDao.addConsignmentView(consignmentView);
	}

	@Override
	public void updateConsignmentView(ConsignmentView consignmentView)
	{
		consignmentViewDao.updateConsignmentView(consignmentView);
	}

	@Override
	public ConsignmentView getOneConsignmentView(String id)
	{
		ConsignmentView c = consignmentViewDao.getOneConsignment(id);
		if (c.getStatus().equals("no_pay"))
		{
			c.setStatus("δȡ");
		}
		else if (c.getStatus().equals("part_paid"))
		{
			c.setStatus("��ȡ����");
		}
		else
		{
			c.setStatus("�Խ���");
		}
		return c;
	}

	@Override
	public void deleteOneConsignmentView(String id)
	{
		consignmentViewDao.deleteConsignmentViewById(id);
	}

	@Override
	public Integer selectAmount(String id)
	{
		return consignmentViewDao.selectAmount(id);
	}

	@Override
	public Integer seletePaidAmount(String id)
	{
		return consignmentViewDao.selectPaidAmount(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateAmount(Map parMap)
	{
		consignmentViewDao.updateAmount(parMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updatePaidAmount(Map parMap)
	{
		consignmentViewDao.updatePaidAmount(parMap);
	}

	// �жϼ�������Ƿ����
	@Override
	public boolean conCodeIsEixsts(String conCode)
	{
		if (consignmentViewDao.selectOneConsignmentViewByConCode(conCode)
				.intValue() == 0)
			return true;
		return false;
	}

	// �ϴ�ͼƬ
	@Override
	@SuppressWarnings("unchecked")
	public String uploadImage(MultipartFile file, String type, String id)
	{
		// �趨�����ϴ����ļ�����
		List<String> fileType = new ArrayList<String>();
		fileType.add("jpg");
		fileType.add("jpeg");
		fileType.add("bmp");
		fileType.add("gif");

		// �õ��ļ�ȫ·��
		String fileName = file.getOriginalFilename();

		// �õ���׺��
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		if (fileName.equals(""))
		{
			return "����ѡ���ļ�";
		}
		else if (!fileType.contains(ext))
		{
			return "�ļ�����Ϊjpg,jpeg,bmp,gif�е�һ��";
		}
		else if (file.getSize() > 200 * 1204)
		{
			return "�ļ�����С��200K";
		}
		else
		{
			Date date = new Date();
			String fileUpName = date.getTime() + "." + ext;
			File f = new File(path + "/img/", fileUpName);
			try
			{
				file.transferTo(f);
				Map parMap = new HashMap();

				if (type.equals("idCard"))
				{
					parMap.put("id", id);
					parMap.put("idcardImage", "/img/" + fileUpName);
					consignmentViewDao.uploadIdcardImage(parMap);
				}
				else if (type.equals("conCard"))
				{
					parMap.put("id", id);
					parMap.put("conImage", "/img/" + fileUpName);
					consignmentViewDao.uploadConImage(parMap);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "��Ǹ,���������ֹ���";
			}
			return "�ϴ��ɹ�";
		}
	}

}
