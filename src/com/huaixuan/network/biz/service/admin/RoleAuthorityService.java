package com.huaixuan.network.biz.service.admin;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.RoleAuthority;
import com.huaixuan.network.biz.domain.hx.Dxrecord;
import com.huaixuan.network.common.util.json.JSONArray;

public interface RoleAuthorityService {
	
	/**
	 * ��ݽ�ɫid��ȡȨ��
	 * @param roleId
	 * @return
	 */
	public List<RoleAuthority> getPermissionByRoleId(Long roleId);
	
    /**
     * ���JSON������ʽ��ϵͳ�������
     * @return
     */
    public JSONArray getSystemFunctionJson(Long roleId);
    
    /**
     * ���ý�ɫȨ��
     * @param permissionIds
     * @param roleId
     */
    public void addRolePermission(List<Long> permissionIds, Long roleId);
    
    
    /**
     * ���JSON������ʽ��ϵͳ�������
     * @return
     */
    public JSONArray getClientFunctionJson(Long roleId);
    
    
    /**
     * ����C�ͻ��˽�ɫȨ��
     * @param permissionIds
     * @param roleId
     */
    public void addClientRolePermission(List<String> permissionIds, Long roleId);
    

}
