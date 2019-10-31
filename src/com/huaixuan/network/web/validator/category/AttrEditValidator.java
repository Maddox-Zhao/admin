package com.huaixuan.network.web.validator.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.hundsun.network.melody.common.util.StringUtil;

public class AttrEditValidator extends ValangValidator{

	
    @Autowired
    private AttributeManager attributeManager;
	
	public void validate(Object obj, Errors errors) {
		Attribute attribute = (Attribute) obj;
		
        if (StringUtil.isEmpty(attribute.getAttrName())) {
            errors.rejectValue("attrName", "", "属性名称不能为空");
        }
        
        if (StringUtil.isNotEmpty(attribute.getAttrName()) && attribute.getAttrName().length() > 20) {
            errors.rejectValue("attrName", "", "属性名称长度不能超过20个字符");
        }else{
            Attribute attributeTemp = attributeManager.getAttributesByName(attribute.getAttrName());
            if(attributeTemp != null && (attributeTemp.getId() != attribute.getId())){
            	errors.rejectValue("attrName", "", "属性名称已经存在");
            }
        }
        if (StringUtil.isNotEmpty(attribute.getAttrDesc())&& attribute.getAttrDesc().length() > 250) {
            errors.rejectValue("attrDesc", "", "属性描述长度不能超过250个字符");
        }
        if (StringUtil.isNotEmpty(attribute.getAttrValues()) && attribute.getAttrValues().length() > 10000) {
            errors.rejectValue("attrValues", "", "可选值长度不能超过10000个字符");
        }
        //输入类型为空
        if (StringUtil.isEmpty(attribute.getInputType())) {
            errors.rejectValue("inputType", "", "录入方式不能为空");
        } else {
            //输入类型为checkbox 或者 radio,但是值列表为空白
            if (attribute.getInputType().equalsIgnoreCase(Constants.InputStyle.checkBOx.toString())
                || attribute.getInputType().equalsIgnoreCase(Constants.InputStyle.radio.toString())) {
                if (StringUtil.isEmpty(attribute.getAttrValues())) {
                    errors.rejectValue("attrValues", "", "可选值不正确");
                }
            }
        }
	}
}
