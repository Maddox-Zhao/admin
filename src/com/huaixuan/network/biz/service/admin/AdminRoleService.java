package com.huaixuan.network.biz.service.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.AdminRole;

public interface AdminRoleService {

	/**
	 * ���ݹ���Աid��ȡ��ɫ�б�
	 * @param adminId
	 * @return
	 */
	public List<AdminRole> getRoleByAdminId(Long adminId);
	
	/**
	 * ����Ա������ɫ
	 * @param roleIds
	 * @param adminId
	 */
	public void addAdminRole(List<String> roleIds, Long adminId);
}
