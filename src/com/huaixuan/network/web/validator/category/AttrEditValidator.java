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
            errors.rejectValue("attrName", "", "�������Ʋ���Ϊ��");
        }
        
        if (StringUtil.isNotEmpty(attribute.getAttrName()) && attribute.getAttrName().length() > 20) {
            errors.rejectValue("attrName", "", "�������Ƴ��Ȳ��ܳ���20���ַ�");
        }else{
            Attribute attributeTemp = attributeManager.getAttributesByName(attribute.getAttrName());
            if(attributeTemp != null && (attributeTemp.getId() != attribute.getId())){
            	errors.rejectValue("attrName", "", "���������Ѿ�����");
            }
        }
        if (StringUtil.isNotEmpty(attribute.getAttrDesc())&& attribute.getAttrDesc().length() > 250) {
            errors.rejectValue("attrDesc", "", "�����������Ȳ��ܳ���250���ַ�");
        }
        if (StringUtil.isNotEmpty(attribute.getAttrValues()) && attribute.getAttrValues().length() > 10000) {
            errors.rejectValue("attrValues", "", "��ѡֵ���Ȳ��ܳ���10000���ַ�");
        }
        //��������Ϊ��
        if (StringUtil.isEmpty(attribute.getInputType())) {
            errors.rejectValue("inputType", "", "¼�뷽ʽ����Ϊ��");
        } else {
            //��������Ϊcheckbox ���� radio,����ֵ�б�Ϊ�հ�
            if (attribute.getInputType().equalsIgnoreCase(Constants.InputStyle.checkBOx.toString())
                || attribute.getInputType().equalsIgnoreCase(Constants.InputStyle.radio.toString())) {
                if (StringUtil.isEmpty(attribute.getAttrValues())) {
                    errors.rejectValue("attrValues", "", "��ѡֵ����ȷ");
                }
            }
        }
	}
}
