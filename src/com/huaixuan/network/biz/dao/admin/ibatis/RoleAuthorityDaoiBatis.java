package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.RoleAuthorityDao;
import com.huaixuan.network.biz.domain.admin.ClientRoleAuthority;
import com.huaixuan.network.biz.domain.admin.RoleAuthority;

@Repository("roleAuthorityDao")
public class RoleAuthorityDaoiBatis implements RoleAuthorityDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public List<RoleAuthority> getPermissionByRoleId(Long roleId) {
		return sqlMapClient.queryForList("RoleAuthorityDao.getPermissionByRoleId", roleId);
	}

	@Override
	public void removeRolePermission(Long roleId) {
		sqlMapClient.delete("RoleAuthorityDao.removeRolePermission", roleId);
	}

	@Override
	public void insertRolePermission(RoleAuthority roleAuthority) {
		sqlMapClient.insert("RoleAuthorityDao.insertRolePermission", roleAuthority);
	}

	@Override
	public List<ClientRoleAuthority> getClientPermissionByRoleId(Long roleId)
	{
		return sqlMapClient.queryForList("RoleAuthorityDao.getClientPermissionByRoleId",roleId);
	}

	@Override
	public void insertClientRolePermission(
			ClientRoleAuthority clientRoleAuthority)
	{
		sqlMapClient.insert("RoleAuthorityDao.insertClientRolePermission",clientRoleAuthority);
	}

	@Override
	public void removeClientRolePermission(Long roleId)
	{
		sqlMapClient.delete("RoleAuthorityDao.removeClientRolePermission",roleId);
	}

}
