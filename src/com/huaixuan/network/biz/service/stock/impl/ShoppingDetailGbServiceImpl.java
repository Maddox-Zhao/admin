package com.huaixuan.network.biz.service.stock.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.stock.ShoppingDetailGbDao;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailGb;
import com.huaixuan.network.biz.service.stock.ShoppingDetailGbService;

@Service("shoppingDetailGbService")
public class ShoppingDetailGbServiceImpl implements ShoppingDetailGbService {

    @Autowired
    private ShoppingDetailGbDao shoppingDetailGbDao;

	@Override
	public void addShoppingDetailGb(ShoppingDetailGb shoppingDetailGb) {
		shoppingDetailGbDao.addShoppingDetailGb(shoppingDetailGb);
	}
    
}
