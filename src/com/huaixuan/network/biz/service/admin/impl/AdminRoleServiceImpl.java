package com.huaixuan.network.biz.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AdminRoleDao;
import com.huaixuan.network.biz.domain.admin.AdminRole;
import com.huaixuan.network.biz.service.admin.AdminRoleService;

@Service("adminRoleService")
public class AdminRoleServiceImpl implements AdminRoleService {

	@Autowired
	private AdminRoleDao adminRoleDao;

	@Override
	public List<AdminRole> getRoleByAdminId(Long adminId) {
		return adminRoleDao.getRoleByAdminId(adminId);
	}

	@Override
	public void addAdminRole(List<String> roleIds, Long adminId) {
		//先删除原有的所有角色
		adminRoleDao.deleteAdminRoleByAdminId(adminId);
		if(roleIds != null && roleIds.size() > 0){
			List<AdminRole> newAdminRoleList = new ArrayList<AdminRole>();
			for(String roleId : roleIds){
				AdminRole adminRole = new AdminRole();
				adminRole.setAdminId(adminId);
				adminRole.setRoleId(Long.parseLong(roleId));
				newAdminRoleList.add(adminRole);
			}
			
			adminRoleDao.insertAdminRoleBatch(newAdminRoleList);	
		}
	}

}
