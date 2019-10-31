package com.huaixuan.network.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumClientPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.RoleAuthorityService;
import com.huaixuan.network.biz.service.admin.RoleService;
import com.huaixuan.network.biz.service.hx.DepartmentService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
@Controller
@RequestMapping(value ="/role")
public class RoleAction extends BaseAction {

	@Autowired
	private RoleService roleService;

    @Autowired
    private Validator   roleValidator;

    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private RoleAuthorityService roleAuthorityService;

	@AdminAccess({EnumAdminPermission.A_ROLE_VIEW_USER})
    @RequestMapping("/roleList")
    public String rolelist(@ModelAttribute("role") Role role, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	QueryPage page = roleService.getRoleListByConditionWithPage(role, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	return "/admin/roleList";
    }

	@AdminAccess({EnumAdminPermission.A_ROLE_ADD_USER})
    @RequestMapping(value = "/addRole", method = RequestMethod.GET)
    public String add_role(@ModelAttribute("role") Role role, @RequestParam("type")String type,Model model) {
		List<Department> list = departmentService.selectAllDepartment();
		model.addAttribute("list", list);
		model.addAttribute("type", type);
    	return "/admin/add_role";
    }
	
	@AdminAccess({EnumAdminPermission.A_ROLE_MODIFY_USER})
    @RequestMapping(value = "/insertRole", method = RequestMethod.POST)
    public String add_role(@ModelAttribute("role") Role role, BindingResult result, Model model){
    	roleValidator.validate(role, result);
    	if(result.hasErrors()){
    		return "/admin/add_role";
    	}
    	if(role.getType().equals("client"))// 往client端的角色表插入数据
    	{
    		roleService.addClientRole(role);
    		return "redirect:/role/clientList.html";
    	}
    	else //更新web端的角色
    	{
	    	Long roleId = roleService.insertRole(role);
	    	Role sqlRole = roleService.getRoleId(roleId); //查出插入数据库的职务id和职务名称，用于更新部门表
			Department department = departmentService.selectDepartmentParent(role.getDepCode());
			
			String roleIds = department.getRoleIds();
			String roleNames = department.getRoleNames();
			
			//把之前部门表的职务id和职务名称更新
			if(!StringUtil.isBlank(roleNames) && !StringUtil.isEmpty(roleNames))
			{
				roleIds = roleIds + ";" + sqlRole.getId();
				roleNames = roleNames +  ";" + sqlRole.getName();
			}
			else
			{
				roleIds = sqlRole.getId()+"";
				roleNames =  sqlRole.getName();
			}
			department.setRoleIds(roleIds);
			department.setRoleNames(roleNames);
			departmentService.updateDepartment(department); //更新
			 model.addAttribute("message", "insertrolesuccess");
			 return "redirect:/role/roleList.html";
	    }      
    }

    @AdminAccess({EnumAdminPermission.A_ROLE_MODIFY_USER})
    @RequestMapping(value = "/editRole", method = RequestMethod.GET)
    public String editRole(@RequestParam("id") String id,@RequestParam("type")String type, Model model){
        if (StringUtils.isBlank(id) || StringUtil.isEmpty(id)) {
            return "/admin/admin_error";
        }
        Role role = null;
        if("client".equals(type))
        {
        	role = roleService.getClientRoleById(Long.parseLong(id));
        }
        else
        {
        	role = roleService.getRoleId(Long.parseLong(id));
        }
       
        role.setType(type);
        model.addAttribute("role", role);
        return "/admin/editRole";
    }


    @RequestMapping(value = "/modifyRole", method = RequestMethod.POST)
    public String modifyRole(@ModelAttribute("role") Role role, BindingResult result, Model model){
    	roleValidator.validate(role, result);
    	if(result.hasErrors()){
    		return "/admin/editRole";
    	}
    	//更新C客户端角色
    	if(role.getType().equals("client"))
    	{
    		roleService.updateClientRoleById(role);
    		model.addAttribute("message", "modifyrolesuccess");
    		return "redirect:/role/clientList.html";
    	}
    	else //WEB端角色
    	{
	    	roleService.updateRole(role);
	        model.addAttribute("message", "modifyrolesuccess");
	        return "redirect:/role/roleList.html";
    	}
    }

    @RequestMapping(value = "/roleAuthorityList", method = RequestMethod.GET)
    public String setPermission(@RequestParam("id") String id, Model model) {
        if (StringUtil.isBlank(id) || StringUtil.isEmpty(id)) {
        	model.addAttribute("message", "角色不存在");
            return "/admin/admin_error";
        }
        JSONArray data = roleAuthorityService.getSystemFunctionJson(Long.parseLong(id));
        model.addAttribute("data", data.toString().replace("\\", ""));
        model.addAttribute("count", EnumAdminPermission.values().length);
        model.addAttribute("roleId", id);
        return "/admin/roleAuthorityList";
    }
    
    
    
    @RequestMapping(value = "/clientRoleAuthorityList", method = RequestMethod.GET)
    public String setClientPermission(@RequestParam("id") String id, Model model) {
        if (StringUtil.isBlank(id) || StringUtil.isEmpty(id)) {
        	model.addAttribute("message", "角色不存在");
            return "/admin/admin_error";
        }
        JSONArray data = roleAuthorityService.getClientFunctionJson(Long.parseLong(id));
        model.addAttribute("data", data.toString().replace("\\", ""));
        model.addAttribute("count", EnumClientPermission.values().length);
        model.addAttribute("roleId", id);
        return "/admin/clientRoleAuthorityList";
    }

    @RequestMapping(value = "/modifyRoleAuthority", method = RequestMethod.POST)
    public String setPermission(@RequestParam("ids") String ids, @RequestParam("roleId") String roleId, Model model) {
        if (StringUtil.isBlank(roleId) || StringUtil.isEmpty(roleId)) {
        	model.addAttribute("message", "角色不存在");
            return "/admin/admin_error";
        }
        List<Long> list = new ArrayList<Long>();
        if(StringUtil.isNotEmpty(ids) && StringUtil.isNotBlank(ids)){
            String[] permissionIds = ids.split(",");
            for (int i = 0; i < permissionIds.length; i++) {
                if (StringUtil.isNotBlank(permissionIds[i])&&StringUtil.isNotEmpty(permissionIds[i])) {
                    list.add(Long.parseLong(permissionIds[i]));
                }
            }
        }
        roleAuthorityService.addRolePermission(list, Long.parseLong(roleId));
        model.addAttribute("message", "modifypersuccess");
        return "redirect:/role/roleList.html";
    }
    
    //修改C客户端权限
    @RequestMapping(value = "/modifyClientRoleAuthority", method = RequestMethod.POST)
    public String setClientPermission(@RequestParam("ids") String ids, @RequestParam("roleId") String roleId, Model model) {
        if (StringUtil.isBlank(roleId) || StringUtil.isEmpty(roleId)) {
        	model.addAttribute("message", "职务不存在");
            return "/admin/admin_error";
        }
        List<String> list = new ArrayList<String>();
        if(StringUtil.isNotEmpty(ids) && StringUtil.isNotBlank(ids)){
            String[] permissionIds = ids.split(",");
            for (int i = 0; i < permissionIds.length; i++) {
                if (StringUtil.isNotBlank(permissionIds[i])&&StringUtil.isNotEmpty(permissionIds[i])) {
                    list.add(permissionIds[i]);
                }
            }
        }
        roleAuthorityService.addClientRolePermission(list, Long.parseLong(roleId));
        model.addAttribute("message", "modifypersuccess");
        return "redirect:/role/clientList.html";
    }
    
    
    @RequestMapping("/getDepatment")
    public @ResponseBody List<Department> getDepartmentNameByDepcode(String depCode,Model model)
    {
    	return  roleService.getDepartmentNameByDepcode(depCode);
    }
    
    /**
     * 客户端角色列表
     * @param model
     * @return
     */
    @RequestMapping("/clientList")
    public String getClientRoels(Model model)
    {
    	List<Role> clientRoles = roleService.getClientRoles();
    	model.addAttribute("clientRoles", clientRoles);
    	return "/admin/clientRoleList";
    }
    

}
