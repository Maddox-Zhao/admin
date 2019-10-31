package com.huaixuan.network.biz.service.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.hx.Department;

public interface DepartmentService
{
	public List<Department> selectAllDepartment();
	
	public Department selectDepartMentByName(String name);
	
	public Department selectOneDepartmentById(int id);
	
	public Department selectDepartmentParent(String parentCode);
	
	public void updateDepartment(Department department);
	
	public void addDepartment(Department department);
	
	public void deleteDepartment(String depcode);
	
	public void updateDepartmentStaffNum(String depCode);
	
	
}
