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
		
        String parttern = "^(0|[1-9][0-9]*)\\.[0-9]+$";//������
        Pattern patternNum = Pattern.compile(parttern);
		
		if(StringUtil.isEmpty(category.getCatName())){
			errors.rejectValue("catName", "", "��Ŀ���Ʋ���Ϊ��");
		}else{
			if(category.getCatName().length() > 60){
				errors.rejectValue("catName", "", "��Ŀ���Ƴ��Ȳ��ܳ���60���ַ�");
			}else{
				try {
					Category categoryDb = new Category();
					categoryDb.setCatName(category.getCatName());
					categoryDb.setParentCode(category.getParentCode());
					categoryDb.setIsShow(1);
					Category categoryTemp = categoryManager.getCateInfoByCatName(category);
					if(categoryTemp != null){
						errors.rejectValue("catName", "", "��Ŀ�����Ѿ�����");
					}
				} catch (Exception e) {
					errors.rejectValue("catName", "", "��Ŀ������֤ʧ��");
				}
			}
		}
		
		if(category.getCommonAgentAgio() == null || category.getCommonAgentAgio() == 0){
			errors.rejectValue("commonAgentAgio", "", "�����ۿ۲���Ϊ�ջ�Ϊ0");
		}else{
			Matcher matcher1 = patternNum.matcher(category.getCommonAgentAgio().toString());
			if(!matcher1.matches()){
				errors.rejectValue("commonAgentAgio", "", "��������ȷ�Ĵ����ۿ�");
			}
		}
		
		if(null == category.getPointProportional()){
			errors.rejectValue("pointProportional", "", "��������ֱ���");
		}else{
			Matcher matcher = patternNum.matcher(category.getPointProportional().toString());
			if (!matcher.matches()) {
				errors.rejectValue("pointProportional", "", "��������ȷ�Ļ��ֱ���");
			}
		}
		
		if(StringUtil.isNotEmpty(category.getCatDesc())){
			if (category.getCatDesc().length() > 250) {
				errors.rejectValue("catDesc", "", "��Ŀ���ܳ��Ȳ��ܳ���250���ַ�");
			}
		}
		
	}
}
