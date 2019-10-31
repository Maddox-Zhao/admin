package com.huaixuan.network.biz.service.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.AdminRole;

public interface AdminRoleService {

	/**
	 * 根据管理员id获取角色列表
	 * @param adminId
	 * @return
	 */
	public List<AdminRole> getRoleByAdminId(Long adminId);
	
	/**
	 * 管理员关联角色
	 * @param roleIds
	 * @param adminId
	 */
	public void addAdminRole(List<String> roleIds, Long adminId);
}
