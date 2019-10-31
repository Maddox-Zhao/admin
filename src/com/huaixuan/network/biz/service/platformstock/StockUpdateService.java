package com.huaixuan.network.biz.service.platformstock;

import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockReserve;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;




/**
 * @author Mr_Yang   2016-5-13 上午11:37:50
 **/

public interface StockUpdateService
{
	/**
	 * 同步现在可售库存到hx_stock_update表
	 */
	public boolean syncNowStock();
	

 
	
	/**
	 * 获取更新库存状态 如果为true 表示可以更新  false 表示正在同步所有库存
	 * @return
	 */
	public String getsyncNowStockStatus();
	
	
	/**
	 *  设置库存更新状态 如果为true 表示可以更新  false 表示正在同步所有库存
	 * @param flag
	 */
	public void setCanUpdateStockStatus(String flag);
	 
	/**
	 * 更新订单数量为0<br/>
	 * 先更新订单数为0<br/>
	 * 在同步总订单库存数 <br/>
	 * field 必须  更新为0的字段
	 */
	public void updatePlatformOrderStock2Zero(String field);
	
	
	/**
	 * 自动同步库存到各个平台（每分钟执行一次）
	 * 1.先处理历史表数据  设置有过变动的产品库存 
	 * 2.查询需要同步库存的数据(上一次更新的库存数和当前可售数不一样)
	 */
  public void autoSyncLocationStock();
  
  /**
	 * 自动同步库存到各个平台（每分钟执行一次）
	 * 1.先处理历史表数据  设置有过变动的产品库存 
	 * 2.查询需要同步库存的数据(上一次更新的库存数和当前可售数不一样)
	 */
  public void autoSyncPlatformStock();
  
  
 
  /**
   * 自动同步订单数量到本地
   */
  public void  autoSyncOrderStock();
  
  
  /**
   * 通过idProduct更新该sku的可售库存
   * @param idPorudct
   */
  public void updateStockUpdateProductNum(String idProduct,String sku);
  
  
  /**
   * 查询当前库存
   * @param searchMap
   * @return
   */
  public MiniUiGrid searchStockUpdate(Map<String,String> searchMap);
  
 
  
  /**
   * 更新当前sku库存-针对平台
   * @param stockUpdate
   */
  public void updateStockUpdate(StockUpdate stockUpdate);
  
  
  /**
   * 查询库存更新日志
   * @param searchMap
   * @return
   */
  public MiniUiGrid searchStockUpdateLog(Map<String,String> searchMap);

  
  public StockUpdate selectstock(StockUpdate stockupdate);

  
  public void updateStockUpdateorder(Map<String,String> searchMap);
  
  public void insertStockReserve(StockReserve stockreserve);
}
 
