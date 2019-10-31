package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.PayaccountDao;
import com.huaixuan.network.biz.domain.shop.Payaccount;
import com.huaixuan.network.biz.domain.shop.Proxyagency;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.service.shop.PayaccountService;

@Service("PayaccountService")
public class PayaccountServiceImpl implements PayaccountService{
	
	@Autowired
	public PayaccountDao payaccountDao;
	
	@Override
	public List<Payaccount> getBankbyBankName() {
		return payaccountDao.getBankbyBankName();
	}

	@Override
	public List<Proxyagency> getCompany() {
		return payaccountDao.getCompany();
	}

}
