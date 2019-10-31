package com.huaixuan.network.biz.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.huaixuan.network.biz.dao.admin.LoginLogoDao;
import com.huaixuan.network.biz.domain.admin.LoginLogo; 
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.LoginLogoService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
@Service("LoginLogoService")
public class LoginLogoServiceImpl implements LoginLogoService {
	
	@Autowired
	private LoginLogoDao loginLogo;
	@Override
	public void addLogin(LoginLogo login) {
		// TODO Auto-generated method stub
		this.loginLogo.insertlogin(login);
	}
	@Override
	public MiniUiGrid queryLoginLogoList(Map<String, String> searchMap) {
		// TODO Auto-generated method stub
		QueryPage queryPage = new QueryPage(new Object());
		int count = loginLogo.getLoginLogoListCount(searchMap);
		MiniUiGrid grid = new MiniUiGrid();
		grid.setTotal(count);
		if(count>0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理.
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageSize()+"");
			List<LoginLogo> list = loginLogo.loginLogoList(searchMap);
			if(list!=null && list.size()>0){
				grid.setData(list);
			}
		}
	
		return grid;
	}

}
