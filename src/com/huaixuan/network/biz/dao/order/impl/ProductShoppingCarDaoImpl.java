package com.huaixuan.network.biz.dao.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.order.ProductShoppingCarDao;
import com.huaixuan.network.biz.domain.order.ProductShoppingCar;



/**
 * @author Mr_Yang   2016-6-1 下午02:43:13
 **/
@Repository("productShoppingCarDao")
public class ProductShoppingCarDaoImpl implements ProductShoppingCarDao
{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public void add2ShoppingCar(ProductShoppingCar productShoppingCar)
	{
		int cnt = getShoppingCarCnt(productShoppingCar);
		if(cnt == 0)
		{
			sqlMapClient.insert("addShoppingCar",productShoppingCar);
		}
	}

	@Override
	public void removeAllShopingCar(String userName)
	{
		sqlMapClient.delete("deleteAllShoppingCar",userName);
	}

	@Override
	public void removeShoppingCar(ProductShoppingCar productShoppingCar)
	{
		sqlMapClient.delete("deleteShoppingCar",productShoppingCar);
	}

	@Override
	public int getShoppingCarCnt(ProductShoppingCar productShoppingCar)
	{
		Object o = sqlMapClient.queryForObject("getShoppingCar",productShoppingCar);
		if(o != null) return (Integer)o;
		return 0;
	}
	@Override
	public int updateShoppingCarSalePrice(List<ProductShoppingCar> list)
	{
		int cnt = sqlMapClient.update("updateShoppingCarSalePrice",list);
		return cnt;
	}

}
 
