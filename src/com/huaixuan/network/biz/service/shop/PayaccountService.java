package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Payaccount;
import com.huaixuan.network.biz.domain.shop.Proxyagency;

public interface PayaccountService {

	public List<Payaccount> getBankbyBankName();
	
	public List<Proxyagency>getCompany();
}
