package com.huaixuan.network.biz.service.hx.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.dao.hx.DaiGouDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.domain.hx.DaiGou;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.DaiGouService;

/**
 * 2012-03-08 17:09
 * @author Mr_Yang
 *
 */
@Service("daiGouService")
public class DaiGouServiceImpl implements DaiGouService
{
	@Autowired
	private DaiGouDao daiGouDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public void addDaiGou(DaiGou daigou, Customer customer,
			AdminAgent adminAgent)
	{
		// 拿到操作者ID
		long operatorId = adminAgent.getId();
		daigou.setOperatorId(operatorId); // 设置操作者ID

		Integer customerId = customerDao.selectOneCustomerId(customer); // 查询客户ID

		if (customerId == null) // 判断客户是否存在，不存在添加一个新的用户
		{
			daigou.setCustomerId(Long.valueOf(customerDao.addOneCustomer(customer))); // 添加新用户
		}
		else
		{
			daigou.setCustomerId(Long.valueOf(customerId));
		}
		daiGouDao.addAcquisition(daigou);
	}

	@Override
	public boolean daiGouCodeIsExist(String daigouCode)
	{
		if(daiGouDao.daiGouCodeIsEixst(daigouCode).intValue() > 0)
			return true;
		return false;
	}

	@Override
	public void deleteDaiGouById(Long id)
	{
		daiGouDao.deleteDaiGouById(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getDaiGouByConditionWithPage(DaiGou daigou,
			int currPage, int pageSize)
	{
		QueryPage queryPage = new QueryPage(daigou);
		Map parMap = queryPage.getParameters();
		Integer count = daiGouDao.getDaiGouViewByConditionWithPageCount(parMap);
		if(count != null && count.intValue() >0)
		{
			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			List<DaiGou> list = daiGouDao.getDaiGouViewByConditionWithPage(parMap);
			if(list != null && list.size() >0)
			{
				queryPage.setItems(list);
			}
		}
		return queryPage;
	}

	@Override
	public DaiGou getDaiGouById(Long id)
	{
		DaiGou daiGou = daiGouDao.getDaiGouById(id);
		if (daiGou.getStatus().equals("no_pay"))
		{
			daiGou.setStatus("未取钱");
		}
		else if (daiGou.getStatus().equals("part_paid"))
		{
			daiGou.setStatus("已取部分");
		}
		else
		{
			daiGou.setStatus("已结清");
		}
		return daiGou;
	}

	@Override
	public DaiGou selectDaigouByDaigouCode(String daigouCode)
	{
		return daiGouDao.getDaiGouByDaiGouCode(daigouCode);
	}

	@Override
	public void updateDaiGouByNotNull(DaiGou daigou)
	{
		daiGouDao.updateDaiGouByNotNull(daigou);
	}

}
