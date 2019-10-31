package com.huaixuan.network.biz.service.reserved;

import java.util.Map;

import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface HxStockOrderService {
     
	public MiniUiGrid searchStockOrder(Map<String,String> searchmap);
	
	//查询两个表显示到页面，用于特别导出
	public MiniUiGrid searchDertailsOrder(Map<String,String> searchmap);
	
	//用于导出功能的查询
	public MiniUiGrid searchStockOrderList(Map<String,String> searchmap);
	
	//查询hx_stock_update_platform_orderdetails全部信息
	//也用于全部订单详情的导出
	public MiniUiGrid selectDertails(Map<String,String> searchmap);


	
}
