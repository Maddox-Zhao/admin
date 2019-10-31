package com.huaixuan.network.biz.dao.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.AdminRole;

public interface AdminRoleDao {
	List<AdminRole> getRoleByAdminId(Long adminId);
	
	void deleteAdminRoleByAdminId(Long AdminRoleList);
	
	void insertAdminRoleBatch(List<AdminRole> AdminRoleList);

}
