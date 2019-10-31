package com.huaixuan.network.biz.dao.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.ClientRoleAuthority;
import com.huaixuan.network.biz.domain.admin.RoleAuthority;

public interface RoleAuthorityDao {
	/**
	 * 根据角色id获取权限
	 * @param roleId
	 * @return
	 */
	List<RoleAuthority> getPermissionByRoleId(Long roleId);
	
	/**
	 * 删除角色全部权限
	 * @param roleId
	 */
	void removeRolePermission(Long roleId);
	
	
	
	
	/**
	 * 新增角色权限关联
	 * @param roleAuthority
	 */
	void insertRolePermission(RoleAuthority roleAuthority);
	
	
	
	/**
	 * 根据角色id获取C客户端权限
	 * @param roleId
	 * @return
	 */
	List<ClientRoleAuthority> getClientPermissionByRoleId(Long roleId);
	
	
	/**
	 * 删除C客户端角色全部权限
	 * @param roleId
	 */
	void removeClientRolePermission(Long roleId);
	
	/**
	 * 新增C客户端角色权限关联
	 * @param roleAuthority
	 */
	void insertClientRolePermission(ClientRoleAuthority clientRoleAuthority);
	
	
	
}
