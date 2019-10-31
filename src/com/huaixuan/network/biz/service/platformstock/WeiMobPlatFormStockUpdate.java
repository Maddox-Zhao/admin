package com.huaixuan.network.biz.service.platformstock;

import java.util.Map;

import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;

public interface WeiMobPlatFormStockUpdate {
 
	/*
	 * 微盟平台sku更新到本地
	 * 
    */
	public void updateSku2Location();
	
	
	/*每两分钟更新平台库存所有的平台，只有库存发生变化的商品才会更新
	 * 微盟不分国内和海外(hk和sh都更)
	 * 
	 */
	public boolean updateWeimobStock(String oursku,String weimobsku,int quantity,String type);
	/*
	 * 全量更新库存微盟(指定的某个平台)平台，无论库存变化没有变化都会更新。
	 * 同步所有库存(无论是hk的，还是sh都更新到平台)
	 */
	public void updateAllStock();
	
	
	/*
	 * 同步订单
	 */
	
	public int atuoSyncOrder();
	
	
}
