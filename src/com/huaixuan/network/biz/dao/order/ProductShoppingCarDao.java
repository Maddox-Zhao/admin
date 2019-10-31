package com.huaixuan.network.biz.dao.order;

import java.util.List;

import com.huaixuan.network.biz.domain.order.ProductShoppingCar;



/**
 * @author Mr_Yang   2016-6-1 下午02:42:28
 **/

public interface  ProductShoppingCarDao
{
		public void add2ShoppingCar(ProductShoppingCar productShoppingCar);
	
		public void removeShoppingCar(ProductShoppingCar productShoppingCar);
	
		public void removeAllShopingCar(String userName);
		
		public int getShoppingCarCnt(ProductShoppingCar productShoppingCar);
		
		
		/**
		 *  保存售价  防止购物车关闭后 再次填写售价
		 * @param list
		 * @return
		 */
		public int updateShoppingCarSalePrice(List<ProductShoppingCar> list);
}
 


