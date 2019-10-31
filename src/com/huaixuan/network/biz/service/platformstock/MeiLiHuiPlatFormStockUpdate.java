package com.huaixuan.network.biz.service.platformstock;

import java.util.Map;

public interface MeiLiHuiPlatFormStockUpdate {

	/**
	 * 更新魅力惠SKU到本地
	 * 魅力惠商品接口未开放，使用EXCEL手动更新已上架SKU，自动更新SKU弃用，开放后再进行开发。
	 */
	public void updateSku2Location();
	
	/**
	 * 设置魅力惠和本地SKU对应关系——从文件设置
	 * @param keyMap key 为寺库sku value为我们sku
	 * @param type
	 */
	public void updateSku2LocationByFileMlh(Map<String,String> keyMap,String type);
	
	/**
	 * 更新魅力惠库存
	 */
	public boolean updateCanSaleProduct(String MlhSku,String ourSku,int quantity,String type);
	/**
	 * 同步所有可售 待售产品库存
	 */
	public void updateAllStock();
}
