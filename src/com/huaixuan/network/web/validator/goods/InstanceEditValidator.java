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
        	errors.rejectValue("instanceName", "", "����д��Ʒ����");
        }else{
        	if(instance.getInstanceName().length() > 100){
        		errors.rejectValue("instanceName", "", "��Ʒ���Ƴ��Ȳ��ܳ���100���ַ�");
        	}
        }
        
        if(StringUtil.isBlank(instance.getCode()) || StringUtil.isEmpty(instance.getCode())){
        	errors.rejectValue("code", "", "����д��Ʒ����");
        }else{
        	if(instance.getCode().length() != 9){
        		errors.rejectValue("code", "", "��Ʒ����Ϊ9���ַ�");
        	}else{
        		GoodsInstance temp = goodsInstanceManager.getInstance(instance.getId());
        		if(!temp.getCode().equals(instance.getCode())){
                	if (!goodsInstanceManager.checkCode(instance)) {
                		errors.rejectValue("code", "", "��Ʒ�����Ѵ���");
                    }
        		}
        	}
        }
        
        if(instance.getSellPrice() != null){
        	if(instance.getSellPrice().doubleValue() < 0 || instance.getSellPrice().doubleValue() > 1000000){
        		errors.rejectValue("sellPrice", "", "���ۼ۸�ӦΪ0��1000000֮��");
        	}
        }
        
        if(instance.getSalesProPrice() != null){
        	if(instance.getSalesProPrice().doubleValue() < 0 || instance.getSalesProPrice().doubleValue() > 1000000){
        		errors.rejectValue("salesProPrice", "", "�����۸�ӦΪ0��1000000֮��");
        	}
        }
        
        if(instance.getMarketPrice() != null){
        	if(instance.getMarketPrice().doubleValue() < 0 || instance.getMarketPrice().doubleValue() > 1000000){
        		errors.rejectValue("marketPrice", "", "�г�ָ���۸�ӦΪ0��1000000֮��");
        	}
        }
        
        if(instance.getRemark() != null && instance.getRemark().length() > 200){
        	errors.rejectValue("remark", "", "��ע���Ȳ��ܳ���200���ַ�");
        }
        
        
        if(instance.getMinNum()!= null){
        	if(instance.getMinNum().intValue() < 1){
        		errors.rejectValue("minNum", "", "��С��治��С��1");
        	}
        }
        
        if(instance.getMaxNum()!= null){
        	if(instance.getMaxNum().intValue() < 1){
        		errors.rejectValue("maxNum", "", "����治��С��1");
        	}
        }
        
	}
}
