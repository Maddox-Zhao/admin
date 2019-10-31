package com.huaixuan.network.web.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.AdminLog;
import com.huaixuan.network.biz.domain.admin.AdminRole;
import com.huaixuan.network.biz.domain.admin.AdminTeam;
import com.huaixuan.network.biz.domain.admin.Employee;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.domain.hx.Department;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.enums.EnumAdminLog;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminLogManager;
import com.huaixuan.network.biz.service.admin.AdminRoleService;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.admin.EmpolyeeService;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.admin.RoleService;
import com.huaixuan.network.biz.service.base.MiniuiBaseService;
import com.huaixuan.network.biz.service.hx.DepartmentService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value ="/admin")
public class AdminAction extends BaseAction {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRoleService adminRoleService;
    
	@Autowired
	private EmpolyeeService empolyeeService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourcesManager resourcesManager;

    @Autowired
    private Validator   adminModifyValidator;

    @Autowired
    private Validator   adminInsertValidator;

    @Autowired
    private DepositoryFirstManager depositoryFirstManager;

    @Autowired
    private Validator adminModifyPasswordValidator;
    
    @Autowired
    private AdminLogManager adminLogManager;
    
    @Autowired
    private Validator adminModifySelfValidator;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private MiniuiBaseService miniuiBaseService;


	@AdminAccess({EnumAdminPermission.A_ADMINISTRATORS_VIEW_USER})
    @RequestMapping("/showUserList")
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
    	
    	return "/admin/showUserList";
    }
	
	
	@AdminAccess({EnumAdminPermission.A_ADMINISTRATORS_VIEW_USER})
    @RequestMapping("/showUserList2Json")
    @ResponseBody
    public Object showUserList2Json(HttpServletRequest request) {
		String depCode = request.getParameter("depCode");
		Admin admin = new Admin();
		admin.setDepCode(depCode);
    	QueryPage page = adminService.getAdminListByConditionWithPage(admin, 1,100);
    	List<Admin> list = (List<Admin>)page.getItems();
    	return list;
    }


    /**
     * �����û�
     *
     * @return
     */
    @RequestMapping(value = "/freezeUser", method = RequestMethod.GET)
    public String doFreezeAdmin(@RequestParam("id") String id, Model model) {
    	if(StringUtil.isBlank(id)||StringUtil.isEmpty(id)){
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
    	}
    	adminService.updateFreezeAdmin(Long.valueOf(id));
    	model.addAttribute("message", "freezesuccess");
        return "redirect:/admin/showUserList.html";
    }

    /**
     * �ⶳ�û�
     *
     * @return
     */
    @RequestMapping(value = "/thawUser", method = RequestMethod.GET)
    public String doReleaseAdmin(@RequestParam("id") String id, Model model) {
    	if(StringUtil.isBlank(id)||StringUtil.isEmpty(id)){
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
    	}
    	adminService.updateReleaseAdmin(Long.valueOf(id));
    	model.addAttribute("message", "releasesuccess");
        return "redirect:/admin/showUserList.html";
    }

    /**
     * ����Ա��ɫѡ��
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/userRoleList", method = RequestMethod.GET)
    public String setRole(@RequestParam("id") String id, Model model) {
        if (StringUtil.isBlank(id) || StringUtil.isEmpty(id)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
        List<AdminRole> adminRoles = adminRoleService.getRoleByAdminId(Long.parseLong(id));
        List<Role> list = roleService.getAllRole(id);
        model.addAttribute("roles", list);
        model.addAttribute("adminRoles", adminRoles);
        model.addAttribute("adminId", id);
        return "/admin/userRoleList";
    }
    
    
    /**
     * ����Ա��ɫѡ��
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/userClientRoleList",method = RequestMethod.GET)
    public String setClientRole(@RequestParam("id") String id , Model model) {
        if (StringUtil.isBlank(id) || StringUtil.isEmpty(id)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
        Admin admin = adminService.getAdminById(Long.parseLong(id));
        List<Role> list = roleService.getClientRoles();
        model.addAttribute("roles", list);
        model.addAttribute("admin", admin);
        return "/admin/userClientRoleList";
    }

    /**
     * ����Ա��ɫ����
     * @param ids
     * @param opid
     * @param teid
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyUserRole", method = RequestMethod.POST)
    public String setRole(@RequestParam("ids") String ids, @RequestParam("adminId") String adminId, Model model) {
        if (StringUtil.isBlank(adminId) || StringUtil.isEmpty(adminId)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
        List<String> list = new ArrayList<String>();
        if(StringUtil.isNotEmpty(ids)||StringUtil.isNotBlank(ids)){
            String[] roleIds = ids.split(",");
            for (int i = 0; i < roleIds.length; i++) {
                if(StringUtil.isNotBlank(roleIds[i]) && StringUtil.isNotEmpty(roleIds[i])){
                	list.add(roleIds[i]);
                }
            }
        }
        adminRoleService.addAdminRole(list, Long.parseLong(adminId));
        model.addAttribute("message", "setrolesuccess");
        return "redirect:/admin/showUserList.html";
    }
    
    
    
    /**
     * ����Ա��ɫ����
     * @param ids
     * @param opid
     * @param teid
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyClientUserRole", method = RequestMethod.POST)
    public String setClientRole(@RequestParam("id") String id, @RequestParam("adminId") String adminId, Model model) {
        if (StringUtil.isBlank(adminId) || StringUtil.isEmpty(adminId)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
        if(StringUtil.isBlank(adminId) || StringUtil.isEmpty(adminId))
        {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
       Admin admin = adminService.getAdminById(Long.parseLong(adminId));
       admin.setPassword(null);//����������
       if(id != null)
    	   admin.setFunctionRole(Long.parseLong(id));
       else
    	   admin.setFunctionRole(-1L);
       adminService.updateAdmin(admin); //���¸��û�ְλ
       model.addAttribute("message", "setrolesuccess");
       return "redirect:/admin/showUserList.html";
    }

    /**
     * ��ʾ�û��ɹ�������Ϣ
     * @return
     */
    @RequestMapping(value = "/showUserShoppingTeamList", method = RequestMethod.GET)
    public String doShowUserShoppingTeamList(@RequestParam("id") String id, Model model) {
        if (StringUtils.isBlank(id) || StringUtil.isEmpty(id)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
        model.addAttribute("adminId", id);

        Admin admin = adminService.getAdminById(Long.parseLong(id));

        model.addAttribute("admin", admin);

        // ��ȡ�û�������Ϣ
        Map parMap = new HashMap();
        parMap.put("adminId", id);

        List<AdminTeam> adminTeamList = adminService.getAdminTeamListByMap(parMap);

        if (adminTeamList != null && adminTeamList.size() > 0) {
            String teamValue = "";
            for (AdminTeam adminTeam : adminTeamList) {
                teamValue += adminTeam.getTeamName() + ",";
            }
            model.addAttribute("teamValue", teamValue);
        }
        // ��ȡ��̨���з�����Ϣ
        List<Resources> shoppingTeamResourcesList = resourcesManager.getResourcesByType(TYPE_SHOPPING_TEAM);
        model.addAttribute("shoppingTeamResourcesList", shoppingTeamResourcesList);

        // ��ȡ�û���ɫ
        List<Role> userRoleList = roleService.getRoleListByAdminId(Long.valueOf(id));
        List<Long> userRoleIds = getUserRoleIds(userRoleList);
        model.addAttribute("userRoleIds", userRoleIds);
        // ��ȡ��̨���н�ɫ����
        List<Role> allRoleList = roleService.getAllRole(id);
        model.addAttribute("allRoleList", allRoleList);
        return "/admin/userShoppingTeamList";
    }

    private List<Long> getUserRoleIds(List<Role> userRoleList) {
        if (userRoleList == null) {
            return null;
        }
        List<Long> userRoleIds = new ArrayList<Long>();
        for (Role role : userRoleList) {
            userRoleIds.add(role.getId());
        }
        return userRoleIds;
    }

    /**
     * �޸��û��ɹ�������Ϣ
     *
     * @return
     */
    @RequestMapping(value = "/modifyUserShoppingTeam", method = RequestMethod.POST)
    public String doModifyUserShoppingTeam(Model model,HttpServletRequest request) {
        String id = request.getParameter("id");
        
        if (StringUtils.isBlank(id) || StringUtil.isEmpty(id)) {
            return "/admin/admin_error";
        }
        
        String[] teamValues = request.getParameterValues("teamValues");

        Admin admin = adminService.getAdminById(Long.parseLong(id));
        if (admin != null) {
        	adminService.updateAdminTeam(Long.valueOf(id), admin.getUserName(), teamValues,TYPE_SHOPPING_TEAM);
        }
        model.addAttribute("message", "setteamsuccess");
        return "redirect:/admin/showUserList.html";
    }

    /**
     * ��ʾ�༭�û�ҳ��
     *
     * @return
     */
	@AdminAccess({EnumAdminPermission.A_ADMINISTRATORS_MODIFY_USER})
    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String doShowEditUserPage(@RequestParam("id") String id, Model model) {
        if (StringUtils.isBlank(id) || StringUtil.isEmpty(id)) {
            return "/admin/admin_error";
        }
        Admin admin = adminService.getAdminById(Long.valueOf(id));
        model.addAttribute("admin", admin);
        List<Department> list = departmentService.selectAllDepartment();
        List<MiniUiBase> siteList = miniuiBaseService.getSiteByType("1,4");
        
    	Admin admin1 = new Admin();
		admin1.setDepCode(admin.getDepCode());
    	QueryPage page = adminService.getAdminListByConditionWithPage(admin1, 1,100);
    	List<Admin> higerList = (List<Admin>)page.getItems();
        model.addAttribute("higerList", higerList);
        model.addAttribute("siteList", siteList);
        model.addAttribute("list", list);
        return "/admin/editUser";
    }

    /**
     * �޸��û�
     *
     * @return
     */
    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public String doModifyUser(@ModelAttribute("admin") Admin admin, BindingResult result, Model model){
    	adminModifyValidator.validate(admin, result);
    	if(result.hasErrors()){
    		return "/admin/editUser";
    	}
    	
    	adminService.updateAdmin(admin);
        model.addAttribute("message", "modifysuccess");
        return "redirect:/admin/showUserList.html";
    }

    /**�޸�һ���ֿ�Ȩ��
     * @return
     */
    @RequestMapping(value = "/depFirstList", method = RequestMethod.GET)
    public String depFirstList(@RequestParam("id") String id, Model model) {
        if (StringUtil.isBlank(id) || StringUtil.isEmpty(id)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }

        Admin admin = adminService.getAdminById(Long.parseLong(id));
        model.addAttribute("admin", admin);
        List<DepositoryFirst> depFirstList = depositoryFirstManager.getDepositoryFirstListByIds(null);
        model.addAttribute("depFirstList", depFirstList);
        Map<String, String> selectMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(admin.getDepFirstId())) {
            StringTokenizer st = new StringTokenizer(admin.getDepFirstId(), ",");
            while (st.hasMoreTokens()) {
                selectMap.put(st.nextToken(), admin.getId().toString());
            }
        }
        model.addAttribute("selectMap", selectMap);
        return "/admin/depFirstList";
    }

    /**
     * һ���ֿⱣ��
     * @param ids
     * @param adminId
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveDepFirstToUser", method = RequestMethod.POST)
    public String saveDepFirstToUser(HttpServletRequest request, Model model) {
    	String adminId = request.getParameter("adminId");
        if (StringUtil.isBlank(adminId) || StringUtil.isEmpty(adminId)) {
        	model.addAttribute("message", "����Ա������");
            return "/admin/admin_error";
        }
        
        String[] depFirstIds = request.getParameterValues("depFirstIds");

        Admin admin = adminService.getAdminById(Long.parseLong(adminId));

        StringBuffer sf = new StringBuffer();
        if (null != depFirstIds) {
            for (String dep : depFirstIds) {
                    if (sf.length() == 0) {
                        sf.append(dep);
                    } else {
                        if (sf.toString().endsWith(",")) {
                            sf.append(dep);
                        } else {
                            sf.append(",");
                            sf.append(dep);
                        }
                    }
            }
        }
        if (sf.length() == 0) {
            admin.setDepFirstId("");
        } else {
            admin.setDepFirstId(sf.toString());
        }

        adminService.updateAdminDepfirst(admin);

        model.addAttribute("message", "setdepfirstsuccess");
        return "redirect:/admin/showUserList.html";
    }

    /**
     * ��ӹ���Աҳ��
     * @param admin
     * @param model
     * @return
     */
	@AdminAccess({EnumAdminPermission.A_ADMINISTRATORS_ADD_USER})
    @RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
    public String add_admin(@ModelAttribute("admin") Admin admin, Model model) {
		List<Department> list =departmentService.selectAllDepartment();
	    List<MiniUiBase> siteList = miniuiBaseService.getSiteByType("1,4");
	    model.addAttribute("siteList", siteList);
		model.addAttribute("list", list);
    	return "/admin/add_admin";
    }
	
	@AdminAccess({EnumAdminPermission.A_ADMINISTRATORS_ADD_USER})
    @RequestMapping(value = "/addAdminForMini", method = RequestMethod.GET)
    public String addAdminForMini(HttpServletRequest request,Model model) {
    	return "/admin/add_admin_mini";
    }
	
	
	  @RequestMapping(value = "/insertAdminForMini")
	   public @ResponseBody Object insertAdminForMini(HttpServletRequest request,Model model){
		  	Map<String,String> requestMap  = MiniUiUtil.getParameterMap(request);
		  	MiniUiUtil.trimAndConvSpeSqlStr(requestMap, false, true, true);
		  	Admin admin = (Admin)MiniUiUtil.Map2Object(requestMap, Admin.class);
		  	Admin adminSearch = new Admin();
		  	adminSearch.setUserName(admin.getUserName());
		  	if(null != adminService.getAdminByCondition(adminSearch))
		  	{
		  		return "exists";
		  	}
		  	if(!admin.getPassword().equals(admin.getConfirmPassword()))
		  	{
		  		return "passwrod_error";
		  	}
		  	if(admin.getDepCode() == null){
		  		admin.setDepCode("-1");
		  	}
		  	if(admin.getEmail() == null || admin.getEmail().length() <= 0){
		  		admin.setEmail("admin@163.com");
		  	}
		  	if(admin.getMobile() == null || admin.getMobile().length() <=0){
		  		admin.setMobile("13000000000");
		  	}
	    	adminService.insertAdmin(admin);
	        return "ok";
		   
	    }
	  
	  @RequestMapping("/getUserNameByIdSite")
	  public @ResponseBody Object getUserName(HttpServletRequest request)
	  {
		  String idSite = request.getParameter("idSite");
		  return adminService.getUserNameByIdSite(idSite);
	  }

    /**
     * ��ӹ���Ա
     * @param admin
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertAdmin", method = RequestMethod.POST)
    public String insertAdmin(@ModelAttribute("admin") Admin admin, BindingResult result, Model model){
    	adminInsertValidator.validate(admin, result);
    	if(result.hasErrors()){
    		List<Department> list =departmentService.selectAllDepartment();
    		model.addAttribute("list", list);
    		List<MiniUiBase> siteList = miniuiBaseService.getSiteByType("1,4");
    	    model.addAttribute("siteList", siteList);
    		return "/admin/add_admin";
    	}
    	adminService.insertAdmin(admin);
    	String idEmployee = admin.getUserName();  //用戶名
    	String name = admin.getName();     //用戶真實姓名
    	
    	if(StringUtil.isBlank(name)){
    		name="null";
    	}
    	long idSite = admin.getSite();     //用戶站點
    	String phone = admin.getMobile();   //電話號碼
    	
    	Employee emp = new Employee();
	    	emp.setIdEmployee(Integer.parseInt(idEmployee));
	    	emp.setName(name);
	    	emp.setIdSite(idSite);   
	    	emp.setPhone(phone);
	    	try {
	    		empolyeeService.insertEmp(emp);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	
    	//departmentService.updateDepartmentStaffNum(admin.getDepCode()); //���²��ŵ�����
        model.addAttribute("message", "insertadminsuccess");
        return "redirect:/admin/showUserList.html";
    }

    /**
     * �޸ĵ�ǰ����Ա��Ϣ
     * @return
     */
    @RequestMapping(value = "/editCurUser", method = RequestMethod.GET)
    public String editCurUser(Model model, AdminAgent adminAgent){
    	if(adminAgent == null){
    		model.addAttribute("message", "error");
    		return "/admin/admin_error";
    	}
        Admin admin = adminService.getAdminById(adminAgent.getId());
		Admin admin1 = new Admin();
		admin1.setDepCode(admin.getDepCode());
    	QueryPage page = adminService.getAdminListByConditionWithPage(admin, 1,100);
    	List<Admin> higerList = (List<Admin>)page.getItems();
        model.addAttribute("admin", admin);
        model.addAttribute("higerList", higerList);
    	return "/admin/editCurUser";
    }

    /**
     * �޸ĵ�ǰ����Ա��Ϣ
     *
     * @return
     */
    @RequestMapping(value = "/modifyCurUser", method = RequestMethod.POST)
    public String modifyCurUser(@ModelAttribute("admin") Admin admin, BindingResult result, Model model){
    	adminModifySelfValidator.validate(admin, result);
    	if(result.hasErrors()){
    		return "/admin/editCurUser";
    	}

    	adminService.updateCurAdmin(admin);
        model.addAttribute("message", "modifysuccess");
        return "redirect:/admin/editCurUser.html";
    }

    /**
     * �޸ĵ�ǰ����Ա����
     * @return
     */
    
   
    @RequestMapping(value = "/editPassword", method = RequestMethod.GET)
    public String editPassword(Model model, AdminAgent adminAgent){
    	if(adminAgent == null){
    		model.addAttribute("message", "����Ա������");
    		return "/admin/admin_error";
    	}
    	
         
    	Admin admin = adminService.getAdminById(adminAgent.getId());	        
        admin.setCurrentlypwd(admin.getPassword());
        admin.setPassword(null);
        model.addAttribute("admin", admin);
    	return "/admin/editPassword";
    }

    /**
     * �޸ĵ�ǰ����Ա����
     *
     * @return
     */
    @RequestMapping(value = "/modifyUserPassword", method = RequestMethod.POST)
    public String modifyUserPassword(@ModelAttribute("admin") Admin admin, BindingResult result, Model model){
    	adminModifyPasswordValidator.validate(admin, result);
    	if(result.hasErrors()){
    		return "/admin/editPassword";
    	}

    	adminService.updateCurPasswordAdmin(admin);
        model.addAttribute("message", "modifysuccess");
        return "redirect:/admin/editPassword.html";
    }
    
  
	@AdminAccess({EnumAdminPermission.A_LOG_VIEW_USER})
    @RequestMapping("/logSearch")
    public String adminlist(@ModelAttribute("adminlog") AdminLog adminlog, Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
        
        Date now = new Date();
        Timestamp nowTs = new Timestamp(now.getTime());
        Date before30 = DateUtil.getDate(now, -30);
        Timestamp beforeTs = new Timestamp(before30.getTime());
        
        if(StringUtils.isBlank(adminlog.getGmtCreateStart())){
        	adminlog.setGmtCreateStart(DateUtil.getTimestampToString(beforeTs));
        }
        
        if(StringUtils.isBlank(adminlog.getGmtCreateEnd())){
        	adminlog.setGmtCreateEnd(DateUtil.getTimestampToString(nowTs));
        }
        
    	QueryPage page = adminLogManager.getAdminLogListByConditionWithPage(adminlog, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	Map<String, String> adminLogMap = EnumAdminLog.toMap();
    	
        model.addAttribute("adminLogMap", adminLogMap);
    	return "/admin/adminlogList";
    }
	
	@RequestMapping("/getAdmin2Json")
	public @ResponseBody Object queryAdminByParam(HttpServletRequest request)
	{
		String currPage = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		if(StringUtil.isBlank(currPage)) currPage = "1";
		if(StringUtil.isBlank(pageSize)) pageSize = "50";
		
		Admin admin = new Admin();
		admin.setUserName(request.getParameter("userName"));
		QueryPage queryPage = adminService.getAdminListByConditionWithPage(admin, Integer.valueOf(currPage), Integer.valueOf(pageSize));
		int total = queryPage.getTotalItem();
		List list = queryPage.getItems();
		MiniUiGrid gird = new MiniUiGrid();
		gird.setData(list);
		gird.setTotal(total);
		return gird;
	}
	
	
	@RequestMapping("/toGgetAdmin2Json")
	public   String toGgetAdmin2Json(HttpServletRequest request)
	{
		 return "/customer/adminSearch";
	}

}
