package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.AdminDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminTeam;

@Repository("adminDao")
public class AdminDaoiBatis implements AdminDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Admin getAdminByCondition(Admin admin) {
		return (Admin) sqlMapClientTemplate.queryForObject("AdminDao.getAdminByCondition", admin);
	}

	@Override
	public int getAdminListByConditionWithPageCount(Map parMap) {
		return (Integer) sqlMapClientTemplate.queryForObject("AdminDao.getAdminListByConditionWithPageCount", parMap);
	}
	
	@Override
	public String getUserNameByIdSite(String idSite)
	{
		Object o = sqlMapClientTemplate.queryForObject("AdminDao.getAdminUserName", idSite);
		if(o == null) return "";
		else return o.toString();
	}

	@Override
	public List<Admin> getAdminListByConditionWithPage(Map parMap) {
		return sqlMapClientTemplate.queryForList("AdminDao.getAdminListByConditionWithPage", parMap);
	}

	@Override
	public void updateAdminStatus(Admin admin) {
		sqlMapClientTemplate.update("AdminDao.updateAdminStatus", admin);
	}

	@Override
	public Admin getAdminById(Long adminId) {
		return (Admin) sqlMapClientTemplate.queryForObject("AdminDao.getAdminById", adminId);
	}

	@Override
	public List<AdminTeam> getAdminTeamListByMap(Map parMap) {
		return sqlMapClientTemplate.queryForList("AdminDao.getAdminTeamListByMap", parMap);
	}

	@Override
	public void deleteTeamByAdminId(Long adminId) {
		sqlMapClientTemplate.delete("AdminDao.deleteTeamByAdminId", adminId);
	}

	@Override
	public void insertAdminTeam(AdminTeam adminTeam) {
		sqlMapClientTemplate.insert("AdminDao.insertAdminTeam", adminTeam);
	}

	@Override
	public Integer getAdminCount(Admin admin) {
		return (Integer) sqlMapClientTemplate.queryForObject("AdminDao.getAdminCount", admin);
	}

	@Override
	public void updateAdmin(Admin admin) {
		sqlMapClientTemplate.update("AdminDao.updateAdmin", admin);
	}

	@Override
	public void updateAdminDepfirst(Admin admin) {
		sqlMapClientTemplate.update("AdminDao.updateAdminDepfirst", admin);
	}

	@Override
	public void insertAdmin(Admin admin) {
		sqlMapClientTemplate.insert("AdminDao.insertAdmin", admin);
	}

	@Override
	public void updateCurPasswordAdmin(Admin admin) {
		sqlMapClientTemplate.update("AdminDao.updateCurPasswordAdmin", admin);
	}

	@Override
	public List<Admin> getAdminUserList() {
		return (List<Admin>) sqlMapClientTemplate.queryForList("AdminDao.getAllAdminUserList");
	}

	@Override
	public List<AdminTeam> getListBySameTeam(String userName) {
		return sqlMapClientTemplate.queryForList("AdminDao.getListBySameTeam", userName);
	}
}
