package com.huaixuan.network.web.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hx.DepartmentService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
@Controller
@RequestMapping(value = "admin")
public class AdminActionTest extends BaseAction {
	@Autowired
    private DepartmentService departmentService;
	@AdminAccess({EnumAdminPermission.A_ADMINISTRATORS_VIEW_USER})
    @RequestMapping("/showUserListTest")
    public String adminlist(@ModelAttribute("admin") Admin admin, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
		
		if(StringUtil.isNotBlank(admin.getDepCodeName())){
			System.out.println(admin.getDepCodeName());
			
			Department dept = departmentService.selectDepartMentByName(admin.getDepCodeName());
			
			String code = dept.getDepCode();
			
			admin.setDepCode(code);
		}
    	QueryPage page = adminService.getAdminListByConditionWithPage(admin, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	
    	List<Department> list = departmentService.selectAllDepartment();
    	model.addAttribute("list", list);
    	
    	return "/admin/showUserListTest";
    }
	
}
