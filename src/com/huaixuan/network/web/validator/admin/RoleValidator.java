package com.huaixuan.network.web.validator.admin;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.admin.Role;
import com.hundsun.network.melody.common.util.StringUtil;

public class RoleValidator extends ValangValidator{
	
	public void validate(Object obj, Errors errors) {
		Role role = (Role) obj;
		
        if(StringUtil.isBlank(role.getName()) || StringUtil.isEmpty(role.getName())){
        	errors.rejectValue("name", "", "请填写职务名称");
        }else{
        	if(role.getName().length() > 40 ){
        		errors.rejectValue("name", "", "职务名称长度不能超过40个字符");
        	}
        }
        /* 添加职务的时候不添加部门
        if(StringUtil.isBlank(role.getDepCode()) || StringUtil.isEmpty(role.getDepCode()))
        {
        	errors.rejectValue("depCode", "", "部门不能为空");
        }
        */
        if(StringUtil.isNotBlank(role.getDisplay()) && StringUtil.isNotEmpty(role.getDisplay())){
        	if(role.getDisplay().length() > 200 ){
        		errors.rejectValue("display", "", "角色描述不能超过200个字符");
        	}
        }
	}

}
