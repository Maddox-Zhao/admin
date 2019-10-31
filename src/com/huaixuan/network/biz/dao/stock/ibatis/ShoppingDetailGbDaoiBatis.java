package com.huaixuan.network.biz.dao.stock.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.stock.ShoppingDetailGbDao;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailGb;

@Repository("shoppingDetailGbDao")
public class ShoppingDetailGbDaoiBatis implements ShoppingDetailGbDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addShoppingDetailGb(ShoppingDetailGb shoppingDetailGb) {
		sqlMapClient.insert("addShoppingDetailGb", shoppingDetailGb);
	}

}
