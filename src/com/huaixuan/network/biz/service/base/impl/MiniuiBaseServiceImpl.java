package com.huaixuan.network.biz.service.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.base.MiniUiBaseDataDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.service.base.MiniuiBaseService;



/**
 * @author Mr_Yang   2015-11-23 ����05:00:30
 **/

@Service("miniuiBaseService")
public class MiniuiBaseServiceImpl implements MiniuiBaseService
{

	
	@Autowired
	private MiniUiBaseDataDao miniUiBaseDataDao;
	
	@Override
	public List<MiniUiBase> getSiteByType(String type)
	{
		return  miniUiBaseDataDao.querySiteByType(type);	
	}
	
	
	@Override
	public List<MiniUiBase> getAllSellChannel()
	{
		return miniUiBaseDataDao.querySellChannel();
	}
	 


	@Override
	public List<MiniUiBase> getAllCurrency()
	{ 
		List<MiniUiBase> list = miniUiBaseDataDao.queryAllCurrency();
		return list;
	}


	@Override
	public List<MiniUiBase> getAllPayment()
	{
		List<MiniUiBase> list = miniUiBaseDataDao.queryAllPayMent();
		return list;
	}

	@Override
	public List<MiniUiBase> getSellChannelByAccount(AdminAgent adminAgent)
	{
		List<MiniUiBase> list = new ArrayList<MiniUiBase>();
		if(adminAgent == null || adminAgent.getUsername() == null) return list;
		list = miniUiBaseDataDao.querySellChannelByAccount(adminAgent.getUsername());
		return list;
	}


	@Override
	public List<MiniUiBase> getSiteByAccount(AdminAgent adminAgent)
	{
		List<MiniUiBase> list = new ArrayList<MiniUiBase>();
		if(adminAgent == null || adminAgent.getUsername() == null) return list;
		list = miniUiBaseDataDao.querySiteByAccount(adminAgent.getUsername());
		return list;
	}


	@Override
	public List<MiniUiBase> querySupplier(Map<String, String> map)
	{
		return miniUiBaseDataDao.querySupplier(map);
	}


	@Override
	public List<MiniUiBase> getProvince() {
		List<MiniUiBase> list = miniUiBaseDataDao.queryProvince();
		return list;
	}


	@Override
	public List<MiniUiBase> getSiteWhereMenDian(String type) {
		return  miniUiBaseDataDao.getSiteWhereMenDian(type);
	}


	@Override
	public List<MiniUiBase> getSiteByName(String name) {
		
		return miniUiBaseDataDao.getSiteByName(name);
	}



}
 
