package com.huaixuan.network.biz.dao.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.hx.Department;

public interface DepartmentDao
{
	public List<Department> selectAllDepartment();
	
	public Department selectOneDepartByName(String name);
	
	public Department selectOneDeprtmentById(int id);
	
	public Department selectDepartmentParent(String parentCode);
	
	public void updateDepartment(Department department);
	
	public void addDepartment(Department department);
	
	public void deleteDepartment(String depcode);
	
	public void updateDepartmentStaffNum(String depCode);
}
