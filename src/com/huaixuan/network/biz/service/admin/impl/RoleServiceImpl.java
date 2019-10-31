package com.huaixuan.network.biz.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.RoleDao;
import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getAllRole(String depCode) {
		return roleDao.getAllRole(depCode);
	}

	@Override
	public List<Role> getRoleListByAdminId(Long adminId) {
		return roleDao.getRoleListByAdminId(adminId);
	}

	@Override
	public QueryPage getRoleListByConditionWithPage(Role role, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(role);
		Map pramas = queryPage.getParameters();

		int count = roleDao.getRoleListByConditionWithPageCount(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<Role> roleList = roleDao.getRoleListByConditionWithPage(pramas);
			if (roleList != null && roleList.size() > 0) {
				queryPage.setItems(roleList);
			}
		}
		return queryPage;
	}

	@Override
	public Long insertRole(Role role) {
        if (role.getStatus() == null) {
            role.setStatus(0);
        }
		return roleDao.insertRole(role);
	}

	@Override
	public Role getRoleId(Long id) {
		return roleDao.getRoleId(id);
	}

	@Override
	public void updateRole(Role role) {
        if (role.getStatus() == null) {
            role.setStatus(0);
        }
		roleDao.updateRole(role);
	}

	@Override
	public List<Department> getDepartmentNameByDepcode(String depCode)
	{
		return roleDao.getDepartmentNameByDepcode(depCode);
	}

	@Override
	public void addClientRole(Role role)
	{
		roleDao.addClientRole(role);
	}

	@Override
	public Role getClientRoleById(Long id)
	{
		return roleDao.getClientRoleById(id);
	}

	@Override
	public List<Role> getClientRoles()
	{
		return roleDao.getClientRoles();
	}

	@Override
	public void updateClientRoleById(Role role)
	{
		roleDao.updateClientRoleById(role);
	}

}
