package com.huaixuan.network.web.validator.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.hundsun.network.melody.common.util.StringUtil;

public class AdminModifyValidator extends ValangValidator{

	@Autowired
	private AdminService adminService;
	
	public void validate(Object obj, Errors errors) {
		Admin admin = (Admin) obj;
		
		
        if(StringUtil.isBlank(admin.getUserName()) || StringUtil.isEmpty(admin.getUserName())){
        	errors.rejectValue("userName", "", "请填写用户名");
        }else{
        	if(admin.getUserName().getBytes().length > 20 || admin.getUserName().getBytes().length < 4){
        		errors.rejectValue("userName", "", "用户名长度为4到20个字符");
        	}else{
        		Admin adminCurrent = adminService.getAdminById(admin.getId());
            	if(!(adminCurrent.getUserName().equals(admin.getUserName()))){
                	if(!(adminService.checkUserName(admin.getUserName()))){
                		errors.rejectValue("userName", "", "用户名已经被注册");
                	}	
            	}
        	}
        }
        if(StringUtil.isBlank(admin.getDepCode()) || StringUtil.isEmpty(admin.getDepCode()))
        {
        	errors.rejectValue("depCode", "", "部门必须选择");
        }
        
        if(StringUtil.isBlank(admin.getEmail()) || StringUtil.isEmpty(admin.getEmail())){
        	errors.rejectValue("email", "", "请填写电子邮箱");
        }else{
			if(admin.getEmail().length() > 60){
				errors.rejectValue("email", "", "电子邮箱长度不能超过60个字符");
			}
    		String regex = "^\\w+[[\\.-]?\\w*]*@\\w+([\\.-]\\w+)*(\\.\\w{2,100})+$";
    		if(!admin.getEmail().matches(regex)){
    			errors.rejectValue("email", "", "请正确填写电子邮箱");
    		}
        	Admin adminCurrent = adminService.getAdminById(admin.getId());
        	if(!(adminCurrent.getEmail().equals(admin.getEmail()))){
            	if(!(adminService.checkUserEmail(admin.getEmail()))){
            		errors.rejectValue("email", "", "电子邮箱已经被注册");
            	}	
        	}
        }
        
		if(StringUtil.isNotBlank(admin.getTel()) && StringUtil.isNotEmpty(admin.getTel())){
			if(admin.getTel().length() > 20){
				errors.rejectValue("tel", "", "电话长度不能超过20个字符");
			}
			String regex = "^0[0-9]{2,3}-[0-9]{5,9}(-[0-9]{1,5})?$";
    		if(!admin.getTel().matches(regex)){
    			errors.rejectValue("tel", "", "请正确填写电话,格式为区号-电话号码-分机(可选)");
    		}
		}
		
		if(StringUtil.isNotBlank(admin.getMobile()) && StringUtil.isNotEmpty(admin.getMobile())){
			if(admin.getMobile().length() > 20){
				errors.rejectValue("mobile", "", "手机号码长度不能超过20个字符");
			}
			String regex = "(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+(\\d){8})";
    		if(!admin.getMobile().matches(regex)){
    			errors.rejectValue("mobile", "", "请正确填写手机");
    		}
		}
        
        if(StringUtil.isBlank(admin.getTel()) && StringUtil.isBlank(admin.getMobile())){
        	errors.rejectValue("mobile", "", "手机号码和电话号码必填一个");
        }
        
		if(StringUtil.isNotBlank(admin.getName()) && StringUtil.isNotEmpty(admin.getName())){
			if(admin.getName().length() > 40){
				errors.rejectValue("name", "", "真实姓名长度不能超过40个字符");
			}
		}
        
	}
}
