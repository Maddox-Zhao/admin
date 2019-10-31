package com.huaixuan.network.web.action.hx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.service.hx.DepartmentService;

/**
 * 2012-02-28 16:18
 * @author Mr_Yang
 *
 */
@Controller
@RequestMapping("/department")
public class DepartmentAction
{

	@Autowired
	private DepartmentService departmentService;


	@RequestMapping("/list")
	public String selectAllDepartment(Model model)
	{
		List<Department> list = departmentService.selectAllDepartment();
		model.addAttribute("list", list);
		return "hx/departmentViewList";
	}

	@RequestMapping("/addInput")
	public String addInput(Model model)
	{
		Department department = new Department();
		model.addAttribute("department", department);
		List<Department> list = departmentService.selectAllDepartment();
		model.addAttribute("list", list);
		return "hx/saveDepartmentInput";
	}


	@RequestMapping("/add")
	public String addDepartment(
			@ModelAttribute("department") Department department, Model model)
	{
		departmentService.addDepartment(department);
		List<Department> list = departmentService.selectAllDepartment();
		model.addAttribute("list", list);
		model.addAttribute("message", "addsuccess");
		return "hx/departmentViewList";
	}

	//����ҳ��
	@RequestMapping("/detail")
	public String getOneDetail(int id, Model model)
	{
		Department department = departmentService.selectOneDepartmentById(id);
		String roleIds = department.getRoleIds();
		String roleNames = department.getRoleNames();
		Map<String, String> roles = null;
		if (roleIds != null && roleNames != null) //��ְ��id�������ó�������ҳ����ѭ����ʾ��
		{
			String[] roleId = roleIds.split(";");
			String[] roleName = roleNames.split(";");
		    roles = new HashMap<String, String>();
			for (int i = 0; i < roleId.length; i++)
			{
				roles.put(roleId[i], roleName[i]);
			}
		}
		model.addAttribute("department", department);
		model.addAttribute("roles", roles);
		return "hx/departmentDetail";
	}

	/**
	 * ���²�����Ϣ
	 * @param department
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	public String updateDepartment(
			@ModelAttribute("department") Department department, Model model)
	{
		//������������õģ�ֻҪ�޸�ʱ�䲻Ϊ�վ��У���ibatis������sysdate()�������
		department.setGmtModify("�޸�ʱ��"); 
		departmentService.updateDepartment(department);
		model.addAttribute("message", "updatesuccess");
		return getOneDetail(department.getId(), model);
	}
	

	//ɾ��һ������
	@RequestMapping("/delete")
	public String deleteDepartment(int id,String depcode,Model model)
	{
		departmentService.deleteDepartment(depcode);
		model.addAttribute("message", "deletesuccess");
		return selectAllDepartment(model);
	}
	
}
