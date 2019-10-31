package com.huaixuan.network.biz.service.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.query.QueryPage;

public interface RoleService {

	/**
	 * ��ȡȫ�������ý�ɫ
	 * @return
	 */
	public List<Role> getAllRole(String depCode);
	
	/**
	 * ���ݹ���Աid��ȡȫ������Ա��ɫ
	 * @param adminId
	 * @return
	 */
	public List<Role> getRoleListByAdminId(Long adminId);

	/**
	 * ��ҳ��ѯ��ȡroleList
	 * 
	 * @param roleList
	 * @return
	 */
	public QueryPage getRoleListByConditionWithPage(Role role, int currPage, int pageSize);
	
	/**
	 * ������ɫ
	 * @param role
	 */
	public Long insertRole(Role role);
	
	/**
	 * ����id��ȡ��ɫ
	 * @param id
	 * @return
	 */
	public Role getRoleId(Long id);
	
	/**
	 * ���½�ɫ
	 * @param role
	 */
	public void updateRole(Role role);
	
	public List<Department> getDepartmentNameByDepcode(String depCode);
	
	
	
	/**
	 * �ͻ��˽�ɫ
	 * 
	 */
	public List<Role> getClientRoles();
	
	
	public Role getClientRoleById(Long id);
	
	
	public void updateClientRoleById(Role role);
	
	
	public void addClientRole(Role role);
}
