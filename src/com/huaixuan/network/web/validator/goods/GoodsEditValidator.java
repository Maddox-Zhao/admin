package com.huaixuan.network.web.validator.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.hundsun.network.melody.common.util.StringUtil;

public class GoodsEditValidator extends ValangValidator {

	
	@Autowired
	private GoodsManager goodsManager;
	
	public void validate(Object obj, Errors errors) {
		Goods goods = (Goods) obj;
		
        if(StringUtil.isBlank(goods.getTitle()) || StringUtil.isEmpty(goods.getTitle())){
        	errors.rejectValue("title", "", "����д��Ʒ����");
        }else{
        	if(goods.getTitle().length() > 120){
        		errors.rejectValue("title", "", "��Ʒ���Ƴ��Ȳ��ܳ���120���ַ�");
        	}
        }
        
        if(StringUtil.isBlank(goods.getGoodsSn()) || StringUtil.isEmpty(goods.getGoodsSn())){
        	errors.rejectValue("goodsSn", "", "����д��Ʒ����");
        }else{
//        	if(goods.getGoodsSn().length() != 7){
//        		errors.rejectValue("goodsSn", "", "��Ʒ���볤��Ϊ7���ַ�");
//        	}else{
        		Goods goodsTemp = goodsManager.getGoodsByCode(goods.getGoodsSn());
        		if(goodsTemp != null && !goodsTemp.getId().equals(goods.getId())){
        			errors.rejectValue("goodsSn", "", "��Ʒ�����Ѿ�����");
        		}
//        	}
        }
        
        if(goods.getGoodsPrice() == null){
        	errors.rejectValue("goodsPrice", "", "����д��Ʒ�۸�");
        }else{
        	if(goods.getGoodsPrice().doubleValue() > 1000000){
        		errors.rejectValue("goodsPrice", "", "��Ʒ�۸��ܴ���1000000Ԫ");
        	}else if(goods.getGoodsPrice().doubleValue() < 0){
        		errors.rejectValue("goodsPrice", "", "��Ʒ�۸���С��0Ԫ");
        	}
        }
        
        if(goods.getGoodsWeight() != null){
        	if(goods.getGoodsWeight().doubleValue() > 1000000){
        		errors.rejectValue("goodsWeight", "", "��Ʒ�������ܴ���1000000KG");
        	}else if(goods.getGoodsWeight().doubleValue() < 0){
        		errors.rejectValue("goodsWeight", "", "��Ʒ��������С��0KG");
        	}
        }
        
        if(goods.getMarketPrice() != null){
        	if(goods.getMarketPrice().doubleValue() > 1000000){
        		errors.rejectValue("marketPrice", "", "�г��۲��ܴ���1000000Ԫ");
        	}else if(goods.getMarketPrice().doubleValue() < 0){
        		errors.rejectValue("marketPrice", "", "�г��۲���С��0Ԫ");
        	}
        }
        
        if(goods.getSalesProPrice() != null){
        	if(goods.getMarketPrice().doubleValue() > 1000000){
        		errors.rejectValue("salesProPrice", "", "�����۲��ܴ���1000000Ԫ");
        	}else if(goods.getMarketPrice().doubleValue() < 0){
        		errors.rejectValue("salesProPrice", "", "�����۲���С��0Ԫ");
        	}
        }
        
        if(goods.getIsAgent().equals("y")){
        	if(goods.getAgentPrice().doubleValue() > 1000000){
        		errors.rejectValue("agentPrice", "", "�����۲��ܴ���1000000Ԫ");
        	}else if(goods.getAgentPrice().doubleValue() < 0){
        		errors.rejectValue("agentPrice", "", "�����۲���С��0Ԫ");
        	}
        }
        
        if(StringUtil.isBlank(goods.getGoodsUnit()) || StringUtil.isEmpty(goods.getGoodsUnit())){
        	errors.rejectValue("goodsUnit", "", "��ѡ����Ʒ��λ");
        }
        
        if(StringUtil.isBlank(goods.getGoodsDesc()) || StringUtil.isEmpty(goods.getGoodsDesc())){
        	errors.rejectValue("goodsDesc", "", "����д��Ʒ����");
        }else{
        	if(goods.getGoodsDesc().length() > 40000){
        		errors.rejectValue("goodsDesc", "", "��Ʒ�������Ȳ��ܳ���40000���ַ�");
        	}else if(goods.getGoodsDesc().length() < 2){
        		errors.rejectValue("goodsDesc", "", "��Ʒ�������Ȳ���С��2���ַ�");
        	}
        }
	}
	
}
