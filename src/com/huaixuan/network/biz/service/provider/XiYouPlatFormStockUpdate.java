/**
 * 
 */
package com.huaixuan.network.biz.service.provider;

import java.util.Map;

import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 * @author TT
 * 
 */
public interface XiYouPlatFormStockUpdate {
	
	
	//获取西有在库的商品id列表
	
	public void getProducts();
	
	//获取商品详细信息,可获取一个 或多个商品的详情
	public void getDetailProducts();
	
	
	public void getProductCostChange();
	
	
	//获取一个商品或多个商品库存
	public void getXiYouStock();
	
	
	//获取时间段内库存变化的商品库存
	public void getChangeStock();
	
	public void getChangeStockTwo();
	
	public void getChangeStockThree(String startTime,String endTime); //获取一段时间内银泰变化的库存
	
	
	
	public void insertProvideToStockUpdate();
	
	
	//减hx_stock_update下单平台的订单数为0减now_stock和order_stock字段,减商品表provide_goods_xiyou的库存,推送订单给供应商
	   
	
	public void pushOrderToPlat();
	
	
	
	public void selectProvideOrderDetail();
	
   
	/*
	 * 银泰平台sku更新到本地
	 * 
    */
	public void updateSku2Location();
	
	
	/**
	 * 同步所有库存
	 */
	 public void updateAllStock();


	/**
	 * 当平台库存发生变化时，执行
	 * @param yinTaiSku
	 * @param ourSku
	 * @param quantity
	 * @param type
	 * @return
	 */
	 public boolean updateYinTaiStock(String oursku,String weimobsku,int quantity,String type);
	 
	 
	 
	 
     /*
	 * 同步订单
	 */
	
	public int atuoSyncOrder();

	/**
	 * @param prodIds
	 */
	String getXiYouStock(String prodIds);
	
	public MiniUiGrid providerSaleInfo(Map<String,String> searchMap);
	
	
}
