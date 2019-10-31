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

	//详情页面
	@RequestMapping("/detail")
	public String getOneDetail(int id, Model model)
	{
		Department department = departmentService.selectOneDepartmentById(id);
		String roleIds = department.getRoleIds();
		String roleNames = department.getRoleNames();
		Map<String, String> roles = null;
		if (roleIds != null && roleNames != null) //把职务id和名称拿出来，在页面上循环显示用
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
	 * 更新部门信息
	 * @param department
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	public String updateDepartment(
			@ModelAttribute("department") Department department, Model model)
	{
		//这里是随便设置的，只要修改时间不为空就行，在ibatis里面用sysdate()来插入的
		department.setGmtModify("修改时间"); 
		departmentService.updateDepartment(department);
		model.addAttribute("message", "updatesuccess");
		return getOneDetail(department.getId(), model);
	}
	

	//删除一个部门
	@RequestMapping("/delete")
	public String deleteDepartment(int id,String depcode,Model model)
	{
		departmentService.deleteDepartment(depcode);
		model.addAttribute("message", "deletesuccess");
		return selectAllDepartment(model);
	}
	
}
