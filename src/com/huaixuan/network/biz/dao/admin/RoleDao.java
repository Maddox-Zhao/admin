package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;

public interface RoleDao {
	/**
	 * ��ȡȫ����ɫ
	 * @return
	 */
	List<Role> getAllRole(String depCode);
	
	/**
	 * ���ݹ���Աid��ȡȫ������Ա��ɫ
	 * @param adminId
	 * @return
	 */
	List<Role> getRoleListByAdminId(Long adminId);
	
	/**
	 * ��ҳ��ȡ��ɫ����
	 * 
	 * @param parMap
	 * @return
	 */
	public int getRoleListByConditionWithPageCount(Map parMap);

	/**
	 * ��ҳ��ȡ��ɫ�б�
	 * 
	 * @param parMap
	 * @return
	 */
	public List<Role> getRoleListByConditionWithPage(Map parMap);
	
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
	
	/**
	 * ���ʱͨ����Ų��Ҳ������ƣ�ʹ�ö�������
	 * @param depCode
	 * @return
	 */
	public List<Department> getDepartmentNameByDepcode(String depCode);
	
	
	
	public List<Role> getClientRoles();
	
	
	public Role getClientRoleById(Long id);
	
	public void updateClientRoleById(Role role);
	
	public void addClientRole(Role role);
}
