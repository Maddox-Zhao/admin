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
        	errors.rejectValue("title", "", "请填写商品名称");
        }else{
        	if(goods.getTitle().length() > 120){
        		errors.rejectValue("title", "", "商品名称长度不能超过120个字符");
        	}
        }
        
        if(StringUtil.isBlank(goods.getGoodsSn()) || StringUtil.isEmpty(goods.getGoodsSn())){
        	errors.rejectValue("goodsSn", "", "请填写商品编码");
        }else{
//        	if(goods.getGoodsSn().length() != 7){
//        		errors.rejectValue("goodsSn", "", "商品编码长度为7个字符");
//        	}else{
        		Goods goodsTemp = goodsManager.getGoodsByCode(goods.getGoodsSn());
        		if(goodsTemp != null && !goodsTemp.getId().equals(goods.getId())){
        			errors.rejectValue("goodsSn", "", "商品编码已经存在");
        		}
//        	}
        }
        
        if(goods.getGoodsPrice() == null){
        	errors.rejectValue("goodsPrice", "", "请填写商品价格");
        }else{
        	if(goods.getGoodsPrice().doubleValue() > 1000000){
        		errors.rejectValue("goodsPrice", "", "商品价格不能大于1000000元");
        	}else if(goods.getGoodsPrice().doubleValue() < 0){
        		errors.rejectValue("goodsPrice", "", "商品价格不能小于0元");
        	}
        }
        
        if(goods.getGoodsWeight() != null){
        	if(goods.getGoodsWeight().doubleValue() > 1000000){
        		errors.rejectValue("goodsWeight", "", "商品重量不能大于1000000KG");
        	}else if(goods.getGoodsWeight().doubleValue() < 0){
        		errors.rejectValue("goodsWeight", "", "商品重量不能小于0KG");
        	}
        }
        
        if(goods.getMarketPrice() != null){
        	if(goods.getMarketPrice().doubleValue() > 1000000){
        		errors.rejectValue("marketPrice", "", "市场价不能大于1000000元");
        	}else if(goods.getMarketPrice().doubleValue() < 0){
        		errors.rejectValue("marketPrice", "", "市场价不能小于0元");
        	}
        }
        
        if(goods.getSalesProPrice() != null){
        	if(goods.getMarketPrice().doubleValue() > 1000000){
        		errors.rejectValue("salesProPrice", "", "促销价不能大于1000000元");
        	}else if(goods.getMarketPrice().doubleValue() < 0){
        		errors.rejectValue("salesProPrice", "", "促销价不能小于0元");
        	}
        }
        
        if(goods.getIsAgent().equals("y")){
        	if(goods.getAgentPrice().doubleValue() > 1000000){
        		errors.rejectValue("agentPrice", "", "代销价不能大于1000000元");
        	}else if(goods.getAgentPrice().doubleValue() < 0){
        		errors.rejectValue("agentPrice", "", "代销价不能小于0元");
        	}
        }
        
        if(StringUtil.isBlank(goods.getGoodsUnit()) || StringUtil.isEmpty(goods.getGoodsUnit())){
        	errors.rejectValue("goodsUnit", "", "请选择商品单位");
        }
        
        if(StringUtil.isBlank(goods.getGoodsDesc()) || StringUtil.isEmpty(goods.getGoodsDesc())){
        	errors.rejectValue("goodsDesc", "", "请填写商品描述");
        }else{
        	if(goods.getGoodsDesc().length() > 40000){
        		errors.rejectValue("goodsDesc", "", "商品描述长度不能超过40000个字符");
        	}else if(goods.getGoodsDesc().length() < 2){
        		errors.rejectValue("goodsDesc", "", "商品描述长度不能小于2个字符");
        	}
        }
	}
	
}
