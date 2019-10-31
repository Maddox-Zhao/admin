package com.huaixuan.network.biz.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.ibatis.EmployeeDao;
import com.huaixuan.network.biz.domain.admin.Employee;
import com.huaixuan.network.biz.service.admin.EmpolyeeService;
@Service("employeeService")
public class EmployeeServiceImpl implements EmpolyeeService {

	
	 @Autowired
	 EmployeeDao employeeDao;
	@Override
	public void insertEmp(Employee emp) {
		
		employeeDao.addEmp(emp);
	}

}
