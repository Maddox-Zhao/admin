package com.huaixuan.network.web.validator.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.hundsun.network.melody.common.util.StringUtil;

public class InstanceEditValidator extends ValangValidator{

    @Autowired
    private GoodsInstanceManager goodsInstanceManager;
	
	public void validate(Object obj, Errors errors) {
		GoodsInstance instance = (GoodsInstance) obj;
		
        if(StringUtil.isBlank(instance.getInstanceName()) || StringUtil.isEmpty(instance.getInstanceName())){
        	errors.rejectValue("instanceName", "", "请填写产品名称");
        }else{
        	if(instance.getInstanceName().length() > 100){
        		errors.rejectValue("instanceName", "", "产品名称长度不能超过100个字符");
        	}
        }
        
        if(StringUtil.isBlank(instance.getCode()) || StringUtil.isEmpty(instance.getCode())){
        	errors.rejectValue("code", "", "请填写产品编码");
        }else{
        	if(instance.getCode().length() != 9){
        		errors.rejectValue("code", "", "产品编码为9个字符");
        	}else{
        		GoodsInstance temp = goodsInstanceManager.getInstance(instance.getId());
        		if(!temp.getCode().equals(instance.getCode())){
                	if (!goodsInstanceManager.checkCode(instance)) {
                		errors.rejectValue("code", "", "产品编码已存在");
                    }
        		}
        	}
        }
        
        if(instance.getSellPrice() != null){
        	if(instance.getSellPrice().doubleValue() < 0 || instance.getSellPrice().doubleValue() > 1000000){
        		errors.rejectValue("sellPrice", "", "销售价格应为0到1000000之间");
        	}
        }
        
        if(instance.getSalesProPrice() != null){
        	if(instance.getSalesProPrice().doubleValue() < 0 || instance.getSalesProPrice().doubleValue() > 1000000){
        		errors.rejectValue("salesProPrice", "", "促销价格应为0到1000000之间");
        	}
        }
        
        if(instance.getMarketPrice() != null){
        	if(instance.getMarketPrice().doubleValue() < 0 || instance.getMarketPrice().doubleValue() > 1000000){
        		errors.rejectValue("marketPrice", "", "市场指导价格应为0到1000000之间");
        	}
        }
        
        if(instance.getRemark() != null && instance.getRemark().length() > 200){
        	errors.rejectValue("remark", "", "备注长度不能超过200个字符");
        }
        
        
        if(instance.getMinNum()!= null){
        	if(instance.getMinNum().intValue() < 1){
        		errors.rejectValue("minNum", "", "最小库存不能小于1");
        	}
        }
        
        if(instance.getMaxNum()!= null){
        	if(instance.getMaxNum().intValue() < 1){
        		errors.rejectValue("maxNum", "", "最大库存不能小于1");
        	}
        }
        
	}
}
