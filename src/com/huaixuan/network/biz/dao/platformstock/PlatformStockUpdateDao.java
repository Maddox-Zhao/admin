package com.huaixuan.network.biz.dao.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.StockReserve;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateHistory;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;

/**
 * @author Mr_Yang 2016-5-10 下午05:11:12
 * 
 **/

public interface PlatformStockUpdateDao {
	/**
	 * 1 更新平台sku为NULL 在重新批量更新下来 防止一些平台已删除的sku存在
	 * 
	 * @param field
	 */
	public void updateStockUpdateSku2Null(String field);

	/**
	 * 2 批量更新平台sku和本地sku对应关系
	 */
	public void batchUpdatePlatformSku2Location(Map map);

	/**
	 * 
	 * @param searchMap
	 *            需要参数searchCanSale以及type <br/>
	 * @return
	 */
	public List<StockUpdate> selectStockUpdateByMap(
			Map<String, String> searchMap);

	/**
	 * 查询历史表上一步操作 产品状态
	 * 
	 * @param searchMap
	 *            idproduct 以及 idhistory 参数必须
	 * @return
	 */
	public StockUpdateHistory selectProductLastSteepStatus(
			Map<String, String> searchMap);

	public void updateStockByNotNull(StockUpdate su);

	/**
	 * 更新库存到平台成功后 通过sku和type设置上次更新库存数
	 * 
	 * @param su
	 */
	public void updateLastUpdateStockBySkuAndTypeForOne(StockUpdate su);

	/**
	 * 获取平台sku
	 * 
	 * @param searchMap
	 *            field字段 sku type 必须
	 * @return
	 */
	public String getPlatformSkuByOurSku(Map<String, String> searchMap);

	/**
	 * 批量更新库存
	 * 
	 * @param map
	 *            库存 type sku 必须
	 */
	public void updateBatchNowStock(Map<String, String> map);

	/**
	 * 更新订单数为0
	 * 
	 * @param updateMap
	 *            field 和 type 必须
	 */
	public void updatePlatformOrderStock2ZeroByType(
			Map<String, String> updateMap);

	/**
	 * 同步订单库存总数(总数量=寺库+尚品+珍品+考拉+1号店)
	 */
	public void syncOrderStock();

	/**
	 * 设置库存为0
	 * 
	 * @param type
	 */
	public void updateStock2ZeroByType(String type);

	/** 同步库存 **/
	public Long getHistoryMaxId();

	/**
	 * 处理完更新最大history
	 * 
	 * @param maxId
	 */
	public void updateHistoryMaxId(Long maxId);

	/**
	 * 插入新的maxid
	 * 
	 * @param maxId
	 */
	public void insertHistoryMaxId(Long maxId);

	/**
	 * 获取未处理的数据
	 * 
	 * @param manxHistoryId
	 * @return
	 */
	public List<StockUpdateHistory> selectHistoryByHistoryMaxid(
			Long maxHistoryId);

	/**
	 * 为新入库产品 插入库存表
	 * 
	 * @param stockUpdate
	 * @return
	 */
	public Long insertStockUpdate(StockUpdate stockUpdate);

	/**
	 * 用于判断是否已经处理过该order
	 * 
	 * @param list
	 * @return
	 */
	public List<PlatFormOrderRecord> getOrdersByOrders(List<String> list);

	// 保存已处理的平台订单
	public void insertOrder(PlatFormOrderRecord Order);
	
	//保存小红书时间差平台订单
	public void insertxhsOrder(PlatFormOrderRecord Order);
	// 修改订单状态
	public void updateOrder(PlatFormOrderRecord Order);

	//
	public PlatFormOrderRecord select(PlatFormOrderRecord Order);

	// 查询当前库存总数
	public int searchStockUpdateCnt(Map<String, String> searchMap);

	// 库存
	public List<StockUpdate> searchStockUpdate(Map<String, String> searchMap);

	/*
	 * 查询库存同步日志
	 */
	public List<StockUpdateLog> selectStockUpdateLogByMap(
			Map<String, String> searchMap);

	public PlatFormOrderRecord selectderRecord(PlatFormOrderRecord oderRecord);
	
	public StockUpdate selectstock(StockUpdate stockupdate);
	
	
	public void updateStockUpdate(Map<String,String> searchMap);
	
	public void insertStockReserve(StockReserve stockreserve);
	
	
	public List<StockUpdateLog> selectStockUpdateLogByMapAndError(
			Map<String, String> searchMap);

	/**
	 * @param su
	 */
	public void updateNowStockBySku(StockUpdate su);

}
