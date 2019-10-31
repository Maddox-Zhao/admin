package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.RoleDao;
import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;

@Repository("roleDao")
public class RoleDaoiBatis implements RoleDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public List<Role> getAllRole(String depCode) {
		return sqlMapClient.queryForList("RoleDao.getAllRole",depCode);
	}

	@Override
	public List<Role> getRoleListByAdminId(Long adminId) {
		return sqlMapClient.queryForList("RoleDao.getRoleListByAdminId", adminId);
	}

	@Override
	public int getRoleListByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("RoleDao.getRoleListByConditionWithPageCount", parMap);
	}

	@Override
	public List<Role> getRoleListByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("RoleDao.getRoleListByConditionWithPage", parMap);
	}

	@Override
	public Long insertRole(Role role) {
		return (Long)sqlMapClient.insert("RoleDao.insertRole", role);
	}

	@Override
	public Role getRoleId(Long id) {
		return (Role)sqlMapClient.queryForObject("RoleDao.getRoleId", id);
	}

	@Override
	public void updateRole(Role role) {
		sqlMapClient.update("RoleDao.updateRole", role);
	}

	@Override
	public List<Department> getDepartmentNameByDepcode(String depCode)
	{
		return sqlMapClient.queryForList("getDepartmentNameByDepcode", depCode);
	}

	@Override
	public void addClientRole(Role role)
	{
		sqlMapClient.insert("addClientRole",role);
	}

	@Override
	public Role getClientRoleById(Long id)
	{
		return (Role)sqlMapClient.queryForObject("getClientRoleById",id);
	}

	@Override
	public List<Role> getClientRoles()
	{
		return sqlMapClient.queryForList("getClientRoles");
	}

	@Override
	public void updateClientRoleById(Role role)
	{
		 sqlMapClient.update("updateClientRole",role);
	}

}
