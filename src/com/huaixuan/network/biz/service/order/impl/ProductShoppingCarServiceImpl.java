package com.huaixuan.network.biz.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.order.ProductShoppingCarDao;
import com.huaixuan.network.biz.domain.order.ProductShoppingCar;
import com.huaixuan.network.biz.service.order.ProductShoppingCarService;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-6-1 下午02:33:00
 **/

@Service("productShoppingCarService")
public class ProductShoppingCarServiceImpl implements ProductShoppingCarService
{
	
	@Autowired
	private ProductShoppingCarDao productShoppingCarDao;
	
	@Override
	public void add2ShoppingCar(ProductShoppingCar productShoppingCar)
	{
		productShoppingCarDao.add2ShoppingCar(productShoppingCar);
	}

	@Override
	public void removeAllShopingCar(String userName)
	{
		productShoppingCarDao.removeAllShopingCar(userName);

	}

	@Override
	public void removeShoppingCar(ProductShoppingCar productShoppingCar)
	{
		productShoppingCarDao.removeShoppingCar(productShoppingCar);

	}

	@Override
	public int getShoppingCarCnt(ProductShoppingCar productShoppingCar)
	{
		return productShoppingCarDao.getShoppingCarCnt(productShoppingCar);
	}
	
	/**
	 * str 格式：idProduct:salePrice;idProduct:salePrice;idProduct:salePrice;
	 */
	@Override
	public int updateShoppingCarSalePrice(String str,String userName)
	{
		if(StringUtil.isBlank(str) || StringUtil.isEmpty(str))
		{
			return 0;
		}
		String[] arr = str.split(";");
		List<ProductShoppingCar> list = new ArrayList<ProductShoppingCar>();
		for(String sp : arr)
		{
			String[] arrResult = sp.split(":");
			String idProduct = arrResult[0];
			String salePriceStr = arrResult[1];
			double salePirce = 0;
			if(StringUtil.isBlank(salePriceStr) || StringUtil.isEmpty(salePriceStr)) continue;
			try
			{
				salePirce = Double.valueOf(salePriceStr);
			}
			catch (Exception e)
			{
				continue;
			}
			ProductShoppingCar p= new ProductShoppingCar();
			p.setIdProduct(idProduct);
			p.setSalePrice(salePirce);
			p.setUserName(userName);
			list.add(p);
		}
		return productShoppingCarDao.updateShoppingCarSalePrice(list);
	}

}
 
