package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.DepartmentDao;
import com.huaixuan.network.biz.domain.hx.Department;

@Repository("departmentDao")
public class DepartmentDaoiBatis implements DepartmentDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapclient;
	
	@Override
	public void addDepartment(Department department)
	{
		sqlMapclient.insert("addDepartment",department);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Department> selectAllDepartment()
	{
		return sqlMapclient.queryForList("selectallDepartment");
	}

	
	@Override
	public Department selectOneDepartByName(String name) {
		
		return (Department) sqlMapclient.queryForObject("selectByName",name);
	}
	
	
	
	
	@Override
	public Department selectDepartmentParent(String parentCode)
	{
		return (Department)sqlMapclient.queryForObject("selectDepartmentParent", parentCode);
	}

	@Override
	public Department selectOneDeprtmentById(int id)
	{
		return (Department)sqlMapclient.queryForObject("selectOneDepartmentById",id);
	}

	@Override
	public void updateDepartment(Department department)
	{
		sqlMapclient.update("updateDepartment",department);
	}

	@Override
	public void deleteDepartment(String depcode)
	{
		sqlMapclient.delete("deleteDepartment",depcode);
	}

	@Override
	public void updateDepartmentStaffNum(String depCode)
	{
		sqlMapclient.update("updateDepartmentStaffNum",depCode);
	}

	

}
