package com.huaixuan.network.biz.service.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.query.QueryPage;

public interface RoleService {

	/**
	 * 获取全部的有用角色
	 * @return
	 */
	public List<Role> getAllRole(String depCode);
	
	/**
	 * 根据管理员id获取全部管理员角色
	 * @param adminId
	 * @return
	 */
	public List<Role> getRoleListByAdminId(Long adminId);

	/**
	 * 分页查询获取roleList
	 * 
	 * @param roleList
	 * @return
	 */
	public QueryPage getRoleListByConditionWithPage(Role role, int currPage, int pageSize);
	
	/**
	 * 新增角色
	 * @param role
	 */
	public Long insertRole(Role role);
	
	/**
	 * 根据id获取角色
	 * @param id
	 * @return
	 */
	public Role getRoleId(Long id);
	
	/**
	 * 更新角色
	 * @param role
	 */
	public void updateRole(Role role);
	
	public List<Department> getDepartmentNameByDepcode(String depCode);
	
	
	
	/**
	 * 客户端角色
	 * 
	 */
	public List<Role> getClientRoles();
	
	
	public Role getClientRoleById(Long id);
	
	
	public void updateClientRoleById(Role role);
	
	
	public void addClientRole(Role role);
}
