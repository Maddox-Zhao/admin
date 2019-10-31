package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.LoginLogoDao;
import com.huaixuan.network.biz.domain.admin.LoginLogo;
@Repository("LoginLogoDao")
public class LoginLogoDaoiBatis implements LoginLogoDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	//添加用户的每次登录（包括 用户名,登录时间,ip）	
	@Override
	public void insertlogin(LoginLogo login) {
		this.sqlMapClient.insert("addLoginLog", login);

	}
	//查找用户登录记录的信息
	@Override
	public List<LoginLogo> loginLogoList(Map<String, String> searchMap) {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("showloginLogoInformation", searchMap);
	}
	//查找用户登录记录的总条数
	@Override
	public int getLoginLogoListCount(Map<String, String> searchMap) {
		Object obj = sqlMapClient.queryForObject("getLoginLogoLsitCount", searchMap);
		if(obj == null) 
		return 0;
		return (Integer)obj;
	}

}
