package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.LoginLogo;

public interface LoginLogoDao {
	//添加用户的每次登录（包括 用户名,登录时间,ip）	,把需要的信息封装在login对象中
	public void insertlogin(LoginLogo login);
	
	//查看用户登录的记录
	public List<LoginLogo> loginLogoList(Map<String,String> searchMap);
	
	//查找用户登录记录的总条数 为分页做准备
	public int getLoginLogoListCount(Map<String, String> searchMap);
}
