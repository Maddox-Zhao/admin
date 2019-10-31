package com.huaixuan.network.biz.service.admin;

import java.util.Map;

import com.huaixuan.network.biz.domain.admin.LoginLogo;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface LoginLogoService {
	//添加用户的每次登录（包括 用户名,登录时间,ip）	
	public void addLogin(LoginLogo login);
	
	//查找登录用户的记录
	public MiniUiGrid queryLoginLogoList (Map<String,String> searchMap);
}
