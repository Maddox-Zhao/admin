package com.huaixuan.network.web.validator.category;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.hundsun.network.melody.common.util.StringUtil;

public class CategoryAddValidator extends ValangValidator{
	
    @Autowired
    private CategoryManager categoryManager;
	
	public void validate(Object obj, Errors errors) {
		Category category = (Category) obj;
		
        String parttern = "^(0|[1-9][0-9]*)\\.[0-9]+$";//浮点数
        Pattern patternNum = Pattern.compile(parttern);
		
		if(StringUtil.isEmpty(category.getCatName())){
			errors.rejectValue("catName", "", "类目名称不能为空");
		}else{
			if(category.getCatName().length() > 60){
				errors.rejectValue("catName", "", "类目名称长度不能超过60个字符");
			}else{
				try {
					Category categoryDb = new Category();
					categoryDb.setCatName(category.getCatName());
					categoryDb.setParentCode(category.getParentCode());
					categoryDb.setIsShow(1);
					Category categoryTemp = categoryManager.getCateInfoByCatName(category);
					if(categoryTemp != null){
						errors.rejectValue("catName", "", "类目名称已经存在");
					}
				} catch (Exception e) {
					errors.rejectValue("catName", "", "类目名称验证失败");
				}
			}
		}
		
		if(category.getCommonAgentAgio() == null || category.getCommonAgentAgio() == 0){
			errors.rejectValue("commonAgentAgio", "", "代销折扣不能为空或为0");
		}else{
			Matcher matcher1 = patternNum.matcher(category.getCommonAgentAgio().toString());
			if(!matcher1.matches()){
				errors.rejectValue("commonAgentAgio", "", "请输入正确的代销折扣");
			}
		}
		
		if(null == category.getPointProportional()){
			errors.rejectValue("pointProportional", "", "请输入积分比例");
		}else{
			Matcher matcher = patternNum.matcher(category.getPointProportional().toString());
			if (!matcher.matches()) {
				errors.rejectValue("pointProportional", "", "请输入正确的积分比例");
			}
		}
		
		if(StringUtil.isNotEmpty(category.getCatDesc())){
			if (category.getCatDesc().length() > 250) {
				errors.rejectValue("catDesc", "", "类目介绍长度不能超过250个字符");
			}
		}
		
	}
}
