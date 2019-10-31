package com.huaixuan.network.biz.dao.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.ibatis.EmployeeDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.Employee;
@Repository("emplyeeDao")
public class EmployeeDaoImpl implements EmployeeDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	@Override
	public void addEmp(Employee emp) {     
			sqlMapClientTemplate.insert("emplyeeDao.insertEmp", emp);		
	}

}
