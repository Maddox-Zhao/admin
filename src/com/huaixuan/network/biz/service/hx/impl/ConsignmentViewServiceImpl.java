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
		// 拿到操作者ID
		long operatorId =  -1L;
		try
		{
			operatorId = Long.parseLong(adminAgent.getUsername());
		}
		catch(Exception e)
		{
			operatorId = -1L;
		}
		consignmentView.setOperatorId(operatorId + ""); // 设置操作者ID

		Integer customerId = customerDao.selectOneCustomerId(customer); // 查询客户ID

		if (customerId == null) // 判断客户是否存在，不存在添加一个新的用户
		{
			consignmentView.setCustomerId(customerDao.addOneCustomer(customer)); // 添加新用户
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
			c.setStatus("未取");
		}
		else if (c.getStatus().equals("part_paid"))
		{
			c.setStatus("以取部分");
		}
		else
		{
			c.setStatus("以结清");
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

	// 判断寄卖编号是否存在
	@Override
	public boolean conCodeIsEixsts(String conCode)
	{
		if (consignmentViewDao.selectOneConsignmentViewByConCode(conCode)
				.intValue() == 0)
			return true;
		return false;
	}

	// 上传图片
	@Override
	@SuppressWarnings("unchecked")
	public String uploadImage(MultipartFile file, String type, String id)
	{
		// 设定可以上传的文件类型
		List<String> fileType = new ArrayList<String>();
		fileType.add("jpg");
		fileType.add("jpeg");
		fileType.add("bmp");
		fileType.add("gif");

		// 得到文件全路径
		String fileName = file.getOriginalFilename();

		// 得到后缀名
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		if (fileName.equals(""))
		{
			return "必须选择文件";
		}
		else if (!fileType.contains(ext))
		{
			return "文件必须为jpg,jpeg,bmp,gif中的一种";
		}
		else if (file.getSize() > 200 * 1204)
		{
			return "文件必须小于200K";
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
				return "抱歉,服务器出现故障";
			}
			return "上传成功";
		}
	}

}
