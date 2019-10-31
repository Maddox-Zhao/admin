package com.huaixuan.network.biz.dao.admin;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;

public interface RoleDao {
	/**
	 * 获取全部角色
	 * @return
	 */
	List<Role> getAllRole(String depCode);
	
	/**
	 * 根据管理员id获取全部管理员角色
	 * @param adminId
	 * @return
	 */
	List<Role> getRoleListByAdminId(Long adminId);
	
	/**
	 * 分页获取角色数量
	 * 
	 * @param parMap
	 * @return
	 */
	public int getRoleListByConditionWithPageCount(Map parMap);

	/**
	 * 分页获取角色列表
	 * 
	 * @param parMap
	 * @return
	 */
	public List<Role> getRoleListByConditionWithPage(Map parMap);
	
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
	
	/**
	 * 添加时通过编号查找部门名称，使用二级联动
	 * @param depCode
	 * @return
	 */
	public List<Department> getDepartmentNameByDepcode(String depCode);
	
	
	
	public List<Role> getClientRoles();
	
	
	public Role getClientRoleById(Long id);
	
	public void updateClientRoleById(Role role);
	
	public void addClientRole(Role role);
}
