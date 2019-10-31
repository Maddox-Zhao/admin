package com.huaixuan.network.web.validator.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.hundsun.network.melody.common.util.StringUtil;
import com.tenpay.util.MD5Util;

public class AdminModifyPasswordValidator extends ValangValidator{

	@Autowired
	private AdminService adminService;

    @Autowired
    private PasswordEncoder     passwordEncoder;

	public void validate(Object obj, Errors errors) {
		Admin admin = (Admin) obj;

        if(StringUtil.isBlank(admin.getCurrentlypwd()) || StringUtil.isEmpty(admin.getCurrentlypwd())){
        	errors.rejectValue("currentlypwd", "", "请填写当前密码");
        }else{
        	if(admin.getCurrentlypwd().length() > 16 || admin.getCurrentlypwd().length() < 6){
        		errors.rejectValue("currentlypwd", "", "当前密码长度为6到16个字符");
        	}else{
        		Admin adminCurrent = adminService.getAdminById(admin.getId());
//        		String currentlypwdDigst = passwordEncoder.encodePassword(admin.getCurrentlypwd(), null);
        		String currentlypwdDigst = MD5Util.MD5Encode(admin.getCurrentlypwd(), null);
            	if(!(adminCurrent.getPassword().equals(currentlypwdDigst))){
            		errors.rejectValue("currentlypwd", "", "当前密码不正确");
            	}
        	}
        }

        if(StringUtil.isBlank(admin.getPassword()) || StringUtil.isEmpty(admin.getPassword())){
        	errors.rejectValue("password", "", "请填写新密码");
        }else{
        	if(admin.getPassword().length() > 16 || admin.getPassword().length() < 6){
        		errors.rejectValue("password", "", "新密码长度为6到16个字符");
        	}
        }

        if(StringUtil.isBlank(admin.getConfirmPassword()) || StringUtil.isEmpty(admin.getConfirmPassword())){
        	errors.rejectValue("confirmPassword", "", "请填写密码确认");
        }else{
        	if(admin.getConfirmPassword().length() > 16 || admin.getConfirmPassword().length() < 6){
        		errors.rejectValue("confirmPassword", "", "密码确认长度为6到16个字符");
        	}
        }

        if(StringUtil.isNotBlank(admin.getPassword()) && StringUtil.isNotBlank(admin.getConfirmPassword())){
        	if(!(admin.getPassword().equals(admin.getConfirmPassword()))){
        		errors.rejectValue("confirmPassword", "", "两次密码输入不一致");
        	}
        }

	}

}
