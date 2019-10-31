package com.huaixuan.network.biz.service.order;

import com.huaixuan.network.biz.domain.order.ProductShoppingCar;



/**
 * @author Mr_Yang   2016-6-1 下午02:28:21
 **/

public interface ProductShoppingCarService
{
	/**
	 * 添加到购物车
	 * @param idProduct
	 */
	public void add2ShoppingCar(ProductShoppingCar productShoppingCar);
	
	public void removeShoppingCar(ProductShoppingCar productShoppingCar);
	
	public void removeAllShopingCar(String userName);
	
	public int getShoppingCarCnt(ProductShoppingCar productShoppingCar);
	
	/**
	 *  保存售价  防止购物车关闭后 再次填写售价
	 * @param list
	 * @return
	 */
	public int updateShoppingCarSalePrice(String str,String userName);
}
 
