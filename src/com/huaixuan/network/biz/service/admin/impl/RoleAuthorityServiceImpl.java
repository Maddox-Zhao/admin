package com.huaixuan.network.biz.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.RoleAuthorityDao;
import com.huaixuan.network.biz.domain.admin.ClientRoleAuthority;
import com.huaixuan.network.biz.domain.admin.RoleAuthority;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumClientPermission;
import com.huaixuan.network.biz.service.admin.RoleAuthorityService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;

@Service("roleAuthorityService")
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	
	protected Log  logger = LogFactory.getLog(this.getClass());

	@Override
	public List<RoleAuthority> getPermissionByRoleId(Long roleId) {
		return roleAuthorityDao.getPermissionByRoleId(roleId);
	}

	@Override
	public JSONArray getSystemFunctionJson(Long roleId) {
        List<RoleAuthority> rolePermissions = new ArrayList<RoleAuthority>();
        boolean hasFunc = false;
        if (roleId != null) {
            rolePermissions = roleAuthorityDao.getPermissionByRoleId(roleId);
        }
        if (rolePermissions.size() > 0) {
            hasFunc = true;
        }
        List<EnumAdminPermission> permissions = EnumAdminPermission.toList();
        Map<EnumAdminPermission, List<EnumAdminPermission>> allPermissionMap = new HashMap<EnumAdminPermission, List<EnumAdminPermission>>();
        List<EnumAdminPermission> parents = EnumAdminPermission.getParents();
        for (EnumAdminPermission parent : parents) {
            List<EnumAdminPermission> children = new ArrayList<EnumAdminPermission>();
            for (EnumAdminPermission permission : permissions) {
                if (permission.getParent() != null && permission.getParent().equals(parent)
                    && !children.contains(permission)) {
                    children.add(permission);
                }
            }
            allPermissionMap.put(parent, children);
        }
        JSONArray data = new JSONArray();
        setData(parents, data, hasFunc, rolePermissions, allPermissionMap);
        //System.out.println(data);
        return data;
	}
	
	
	
	/**
	 * �õ����û����е�Ȩ�޺����п�ѡ��Ȩ��
	 */
	@Override
	public JSONArray getClientFunctionJson(Long roleId)
	{
		List<ClientRoleAuthority> rolePermissions = new ArrayList<ClientRoleAuthority>();
        boolean hasFunc = false;
        if (roleId != null) {
            rolePermissions = roleAuthorityDao.getClientPermissionByRoleId(roleId);
        }
        if (rolePermissions.size() > 0) {
            hasFunc = true;
        }
        List<EnumClientPermission> permissions = EnumClientPermission.toList();
        Map<EnumClientPermission, List<EnumClientPermission>> allPermissionMap = new HashMap<EnumClientPermission, List<EnumClientPermission>>();
        List<EnumClientPermission> parents = EnumClientPermission.getParents();
        for (EnumClientPermission parent : parents) {
            List<EnumClientPermission> children = new ArrayList<EnumClientPermission>();
            for (EnumClientPermission permission : permissions) {
                if (permission.getParent() != null && permission.getParent().equals(parent) && !children.contains(permission)) {
                    children.add(permission);
                }
            }
            allPermissionMap.put(parent, children);
        }
        JSONArray data = new JSONArray();
        setDataForClient(parents, data, hasFunc, rolePermissions, allPermissionMap);
        //System.out.println(data.toString());
        return data;
	}
	
    /**
     * �ݹ��������״JSON����
     * 
     * @param permissions  ����Ȩ��
     * @param data �ӽڵ�Ȩ��JSON���� 
     * @param hasFunc ��ǰ��ɫ�Ƿ�����Ȩ��
     * @param roleFuncs ��ǰ��ɫ���е�Ȩ���б�
     * @param allPermissionMap 
     * @throws JSONException 
     * @throws JSONException 
     */
    private void setData(List<EnumAdminPermission> parents, JSONArray jsonArray, boolean hasFunc,
                         List<RoleAuthority> rolePermissions,
                         Map<EnumAdminPermission, List<EnumAdminPermission>> allPermissionMap) {
        try {
            for (EnumAdminPermission permission : parents) {
                JSONObject parent = new JSONObject();
                JSONArray jsonArray2 = new JSONArray();
                parent.accumulate("id", permission.getId());
                parent.accumulate("text", permission.getName());
                parent.accumulate("value", permission.getId());
                parent.accumulate("showcheck", true);
                if (permission.getParent() == null) {
                    parent.accumulate("complete", false);
                } else {
                    parent.accumulate("complete", true);
                }
                if (hasFunc) {
                    for (RoleAuthority rf : rolePermissions) {
                        if (rf.getAuthorityId().equals(Long.parseLong(permission.getId()))) {
                            parent.accumulate("checkstate", true);
                            break;
                        }
                    }
                } else {
                    parent.accumulate("checkstate", false);
                }
                parent.accumulate("isexpand", true);
                List<EnumAdminPermission> list = allPermissionMap.get(permission);
                if (list == null || list.size() == 0) {
                    parent.accumulate("hasChildren", false);
                    parent.accumulate("ChildNodes", null);
                    jsonArray.put(parent);
                    continue;
                } else {
                    setData(list, jsonArray2, hasFunc, rolePermissions, allPermissionMap);
                }
                parent.accumulate("hasChildren", true);
                parent.accumulate("ChildNodes", jsonArray2);
                jsonArray.put(parent);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    
    
    /**
     * �ݹ��������״JSON���� -- C�ͻ���
     * 
     * @param permissions ����Ȩ��
     * @param data �ӽڵ�Ȩ��JSON����
     * @param hasFunc ��ǰ��ɫ�Ƿ�����Ȩ��
     * @param roleFuncs ��ǰ��ɫ���е�Ȩ���б�
     * @param allPermissionMap 
     * @throws JSONException 
     * @throws JSONException 
     */
    private void setDataForClient(List<EnumClientPermission> parents, JSONArray jsonArray, boolean hasFunc,
                         List<ClientRoleAuthority> rolePermissions,
                         Map<EnumClientPermission, List<EnumClientPermission>> allPermissionMap) {
        try {
            for (EnumClientPermission permission : parents) {
                JSONObject parent = new JSONObject();
                JSONArray jsonArray2 = new JSONArray();
                parent.accumulate("id", permission.getId());
                parent.accumulate("text", permission.getName());
                parent.accumulate("value", permission.getId());
                parent.accumulate("showcheck", true);
                if (permission.getParent() == null) {
                    parent.accumulate("complete", false);
                } else {
                    parent.accumulate("complete", true);
                }
                if (hasFunc) {
                    for (ClientRoleAuthority rf : rolePermissions) {
                        if (rf.getAuthorityId().equals(permission.getId())) {
                            parent.accumulate("checkstate", true);
                            break;
                        }
                    }
                } else {
                    parent.accumulate("checkstate", false);
                }
                parent.accumulate("isexpand", true);
                List<EnumClientPermission> list = allPermissionMap.get(permission);
                if (list == null || list.size() == 0) {
                    parent.accumulate("hasChildren", false);
                    parent.accumulate("ChildNodes", null);
                    jsonArray.put(parent);
                    continue;
                } else {
                	setDataForClient(list, jsonArray2, hasFunc, rolePermissions, allPermissionMap);
                }
                parent.accumulate("hasChildren", true);
                parent.accumulate("ChildNodes", jsonArray2);
                jsonArray.put(parent);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

	@Override
	public void addRolePermission(List<Long> permissionIds, Long roleId) {
		roleAuthorityDao.removeRolePermission(roleId);
        for (Long permissionId : permissionIds) {
        	RoleAuthority roleAuthority = new RoleAuthority();
        	roleAuthority.setAuthorityId(permissionId);
        	roleAuthority.setRoleId(roleId);
        	roleAuthorityDao.insertRolePermission(roleAuthority);
        }
	}

	
	
	@Override
	public void addClientRolePermission(List<String> permissionIds, Long roleId)
	{
		roleAuthorityDao.removeClientRolePermission(roleId);
        for (String permissionId : permissionIds) {
        	ClientRoleAuthority roleAuthority = new ClientRoleAuthority();
        	roleAuthority.setAuthorityId(permissionId);
        	roleAuthority.setRoleId(roleId);
        	roleAuthorityDao.insertClientRolePermission(roleAuthority);
        }
	}

	

}
