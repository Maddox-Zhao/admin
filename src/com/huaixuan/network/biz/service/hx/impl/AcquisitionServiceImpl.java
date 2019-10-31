package com.huaixuan.network.biz.service.hx.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.AcquisitionDao;
import com.huaixuan.network.biz.dao.hx.CustomerDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Acquisition;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.AcquisitionService;


/**
 * 2012-03-02 11:51
 * @author Mr_Yang
 *
 */
@Service("acquisitionService")
public class AcquisitionServiceImpl implements AcquisitionService
{
	@Autowired
	private AcquisitionDao acquisitionDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public void addAcquisitionView(Acquisition acquisition, Customer customer,
			AdminAgent adminAgent)
	{
		// 拿到操作者ID
		long operatorId = adminAgent.getId();
		acquisition.setOperatorId(operatorId); // 设置操作者ID

		Integer customerId = customerDao.selectOneCustomerId(customer); // 查询客户ID

		if (customerId == null) // 判断客户是否存在，不存在添加一个新的用户
		{
			acquisition.setCustomerId(customerDao.addOneCustomer(customer)); // 添加新用户
		}
		else
		{
			acquisition.setCustomerId(customerId);
		}
		 acquisitionDao.addAcquisitionView(acquisition);
	}

	@Override
	public void deleteAcquisitionView(Acquisition acquisition)
	{
		acquisitionDao.delteAcquisitionView(acquisition);
	}

	@Override
	public void deleteAcquisitionViewById(Long id)
	{
		acquisitionDao.deleteAcquisitionViewById(id);
	}

	@Override
	public Acquisition getAcquisitionById(Long id)
	{
		Acquisition acquisition =  acquisitionDao.getAcquisitionById(id);
		if (acquisition.getStatus().equals("no_pay"))
		{
			acquisition.setStatus("未取钱");
		}
		else if (acquisition.getStatus().equals("part_paid"))
		{
			acquisition.setStatus("已取部分");
		}
		else
		{
			acquisition.setStatus("已结清");
		}
		return acquisition;
	}

	/**
	 * 分页查询
	 */
	@Override
	@SuppressWarnings("unchecked")
	public QueryPage getAcquisitionViewByConditionWithPage(Acquisition acquisition, int currPage, int pageSize)
	{	
		QueryPage queryPage = new QueryPage(acquisition);
		Map parMap = queryPage.getParameters();
		Integer count = acquisitionDao.getConsignmentViewByConditionWithPageCount(parMap);
		if(count != null && count.intValue() >0)
		{
			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			List<Acquisition> list = acquisitionDao.getAcquisitionViewByConditionWithPage(parMap);
			if(list != null && list.size() >0)
			{
				queryPage.setItems(list);
			}
		}
		return queryPage;
	}

	@Override
	public Acquisition selectAcquisitionViewByAcpCode(String acpCode)
	{
		return acquisitionDao.selectAcquisitionViewByAcpCode(acpCode);
	}

	
	@Override
	public void updateAcquisitionViewByNotNull(Acquisition acquisition)
	{
		acquisitionDao.updateAcquisitionViewByNotNull(acquisition);
	}

	
	@Override
	public void updateAcquisitionViewByPrimaryKey(Acquisition acquisition)
	{
		acquisitionDao.updateAcquisitionViewByPrimaryKey(acquisition);
	}

	@Override
	public boolean theAcpCodeIsExist(String acpCode)
	{
		Integer count = acquisitionDao.theAcpCodeIsEixst(acpCode);
		if(count.intValue() > 0)
			return true;		
		return false;
	}



}
