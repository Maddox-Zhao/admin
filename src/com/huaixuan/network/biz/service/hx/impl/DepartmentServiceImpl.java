package com.huaixuan.network.biz.service.hx.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hx.DepartmentDao;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.service.hx.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService
{

	@Autowired
	private DepartmentDao departmentDao;
	
	@Override
	public void addDepartment(Department department)
	{
		//ͨ���ű�ŵõ����ڵ����Ϣ
		Department parentDepartment =  departmentDao.selectDepartmentParent(department.getParentCode());
		setDepCode(parentDepartment,department);
		department.setParentCode(parentDepartment.getDepCode());
		if(parentDepartment.getDepCode().equals("-1")) 
			department.setNote("һ������");
		//����ӵĲ���û��Ա��
		department.setStaffNum(0);
		//����ӵĲ���û���¼�����
		department.setChildNums(0);
		//���һ������
		departmentDao.addDepartment(department);

		//�޸��ϼ����ŵ��¼���������
		updateDepartmentChildNum(parentDepartment);
	}

	private void updateDepartmentChildNum(Department parentDepartment)
	{
		parentDepartment.setChildNums(parentDepartment.getChildNums() + 1);
		updateDepartment(parentDepartment);
	}

	//��ɲ��ű�ţ������ж����¼���ϵ
	private void setDepCode(Department parentDepartment, Department department)
	{
		StringBuilder depCode = new StringBuilder();
		int childNum = parentDepartment.getChildNums();
		if(!parentDepartment.getDepCode().equalsIgnoreCase("-1"))
		{
			depCode.append(parentDepartment.getDepCode());
			depCode.append(".");
		}
		if(childNum <= 9)
		{
			depCode.append("00");
			depCode.append(String.valueOf(childNum+1));
		}
		if(childNum > 9 && childNum <= 99)
		{
			depCode.append("0");
			depCode.append(String.valueOf(childNum + 1));
		}
		if(childNum > 99)
		{
			depCode.append(String.valueOf(childNum));
		}
		department.setDepCode(depCode.toString());
	}

	/**
	 * ��ѯ���еĲ���
	 */
	@Override
	public List<Department> selectAllDepartment()
	{
		return departmentDao.selectAllDepartment();
	}

	
	//用于页面查询部门
	@Override
	public Department selectDepartMentByName(String name) {
		
		return departmentDao.selectOneDepartByName(name);
	}
	
	
	
	/**
	 * ͨ���Ų�ѯ�ò���
	 */
	@Override
	public Department selectDepartmentParent(String parentCode)
	{
		return  departmentDao.selectDepartmentParent(parentCode);
	}

	/**
	 * ͨ��ID��ѯ�ò���
	 */
	@Override
	public Department selectOneDepartmentById(int id)
	{
		return  departmentDao.selectOneDeprtmentById(id);
	}

	/**
	 * ���¸ò���
	 */
	@Override
	public void updateDepartment(Department department)
	{
		departmentDao.updateDepartment(department);
	}

	/**
	 * ɾ��ò���
	 */
	@Override
	public void deleteDepartment(String depcode)
	{
		departmentDao.deleteDepartment(depcode);
	}

	/**
	 * �����Ա����ʱ����¸ò��ŵ�����
	 */
	@Override
	public void updateDepartmentStaffNum(String depCode)
	{
		List<String> list = new ArrayList<String>();
		split(depCode,list);
		StringBuffer s = new StringBuffer();
		for(String code : list)
		{
			s.append(code);
			s.append(",");
		}
		String temp = s.toString();
		departmentDao.updateDepartmentStaffNum(temp.substring(0,temp.lastIndexOf(",")));
	}
	
	private   void split(String s,List<String> list)
	{
		if(".".contains(s))
		{
			String t = s.substring(0,s.lastIndexOf("."));
			list.add(t);
			split(t,list);
		}
		else
		{
			list.add(s);
		}
	}

	
}
