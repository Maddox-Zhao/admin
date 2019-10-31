package com.huaixuan.network.biz.dao.reserved;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface HxStockOrderDao {
	//查询最大条数
    public int searchStockOrderOne(Map<String,String> searchmap);
    
    //根据条件查询
	//public List<PlatFormOrderDetails> searchStockOrder(Map<String,String> searchmap);
	
	//查询hx_stock_update_platform_orderdetails的总条数
	public int searchorDerdetails(Map<String,String> searchmap);
		
	//根据条件查询hx_stock_update_platform_orderdetails
	public List<PlatFormOrderDetails> searchDetailsOrder(Map<String,String> searchmap);
	
	
	
	//查询hx_stock_update_platform_orderdetails的总条数（无条件单独显示）
	public int selectDerdetails(Map<String,String> searchmap);
	
	//根据条件查询hx_stock_update_platform_orderdetails
	//用于详情的全部导出
	public List<PlatFormOrderDetails> selectDetailsOrder(Map<String,String> searchmap);
	
	
	
	
	//根据条件查询 //查询PlatFormOrderRecord的全部首页面导出用
		public List<PlatFormOrderRecord> searchStockOrderDc(Map<String,String> searchmap);
}
