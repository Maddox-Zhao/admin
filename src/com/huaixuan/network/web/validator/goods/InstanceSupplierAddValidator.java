package com.huaixuan.network.web.validator.goods;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.hundsun.network.melody.common.util.StringUtil;

public class InstanceSupplierAddValidator extends ValangValidator{

	public void validate(Object obj, Errors errors) {
		
		GoodsInstanceSupplier goodsInstanceSupplier = (GoodsInstanceSupplier) obj;
		
		if(StringUtil.isBlank(goodsInstanceSupplier.getSupplierName())|| StringUtil.isEmpty(goodsInstanceSupplier.getSupplierName())){
			errors.rejectValue("supplierName", "", "��ѡ��Ӧ��");
		}
		
		if(goodsInstanceSupplier.getSupplierCode()!=null){
			if(goodsInstanceSupplier.getSupplierCode().length() > 30){
				errors.rejectValue("supplierCode", "", "��Ʒ��Ӧ�̱��볤�Ȳ��ܳ���30���ַ�");
			}
		}

		if(goodsInstanceSupplier.getConsultPrice() != null){
			if(goodsInstanceSupplier.getConsultPrice().doubleValue() < 0 || goodsInstanceSupplier.getConsultPrice().doubleValue() > 1000000){
				errors.rejectValue("consultPrice", "", "�����۸�Ӧ��0��1000000֮��");
			}
		}
	}
}
