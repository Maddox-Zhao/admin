package com.huaixuan.network.biz.dao.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.ClientRoleAuthority;
import com.huaixuan.network.biz.domain.admin.RoleAuthority;

public interface RoleAuthorityDao {
	/**
	 * ���ݽ�ɫid��ȡȨ��
	 * @param roleId
	 * @return
	 */
	List<RoleAuthority> getPermissionByRoleId(Long roleId);
	
	/**
	 * ɾ����ɫȫ��Ȩ��
	 * @param roleId
	 */
	void removeRolePermission(Long roleId);
	
	
	
	
	/**
	 * ������ɫȨ�޹���
	 * @param roleAuthority
	 */
	void insertRolePermission(RoleAuthority roleAuthority);
	
	
	
	/**
	 * ���ݽ�ɫid��ȡC�ͻ���Ȩ��
	 * @param roleId
	 * @return
	 */
	List<ClientRoleAuthority> getClientPermissionByRoleId(Long roleId);
	
	
	/**
	 * ɾ��C�ͻ��˽�ɫȫ��Ȩ��
	 * @param roleId
	 */
	void removeClientRolePermission(Long roleId);
	
	/**
	 * ����C�ͻ��˽�ɫȨ�޹���
	 * @param roleAuthority
	 */
	void insertClientRolePermission(ClientRoleAuthority clientRoleAuthority);
	
	
	
}
