package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.CurrencyDao;
import com.huaixuan.network.biz.domain.shop.Currency;
import com.huaixuan.network.biz.service.shop.CurrencyService;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService{
	@Autowired
	public CurrencyDao currencyDao;
	@Override
	public List<Currency> getCurrenctByname() {
		
		return currencyDao.getCurrenctByname();
	}

}
