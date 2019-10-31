package com.huaixuan.network.biz.dao.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.StockReserve;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateHistory;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-5-10 下午05:12:39
 **/

@Repository("platformStockUpdateDao")
public class PlatformStockUpdateDaoImpl implements PlatformStockUpdateDao
{
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public void updateStockUpdateSku2Null(String field)
	{
		if(StringUtil.isNotBlank(field))
		{
			sqlMapClient.update("updateStockSkuToNull",field);
		}
	}
	@Override
	public void batchUpdatePlatformSku2Location(Map map)
	{
		if(map.size() > 0)
			sqlMapClient.update("updatePlatformSku2Location",map);
	}

	@Override
	public List<StockUpdate> selectStockUpdateByMap(Map<String, String> searchMap)
	{
		return  sqlMapClient.queryForList("selectStockUpdateByMap",searchMap);
	}
	
	@Override
	public StockUpdateHistory selectProductLastSteepStatus(Map<String, String> searchMap)
	{
		 Object o = sqlMapClient.queryForObject("selectProductLastSteepStatus",searchMap);
		 if(o != null) return (StockUpdateHistory)o;
		 return new StockUpdateHistory();
	}
	

	@Override
	public String getPlatformSkuByOurSku(Map<String, String> searchMap)
	{
		Object o = sqlMapClient.queryForObject("getPlatformSkuByOurSkuAndType",searchMap);
		if(o != null) return o.toString();
		return "";
	}

	@Override
	public void updateBatchNowStock(Map<String, String> map)
	{
		sqlMapClient.update("updateStockBatchBySkuAndType",map);
	}
	
	@Override
	public void updateLastUpdateStockBySkuAndTypeForOne(StockUpdate su)
	{
		sqlMapClient.update("updateStockBySkuAndTypeForOne",su);
	}

	@Override
	public void updateStock2ZeroByType(String type)
	{
		sqlMapClient.update("updateStockB2ZeroByType",type);
	}
	
	public void updatePlatformOrderStock2ZeroByType(Map<String,String> updateMap)
	{
		sqlMapClient.update("updatePlatformOrderStock2ZeroByType",updateMap);
	}
	
	@Override
	public void syncOrderStock()
	{
		sqlMapClient.update("syncOrderStock");
	}

	@Override
	public void updateStockByNotNull(StockUpdate su)
	{
		sqlMapClient.update("updateStockByNotNull",su);
	}

	@Override
	public Long getHistoryMaxId()
	{
		Object o = sqlMapClient.queryForObject("selectHistoryMaxId");
		if(null == o)
		{
			o = sqlMapClient.queryForObject("selectInsertHistoryMaxId");
			insertHistoryMaxId(Long.valueOf(o.toString()));
		}
		return Long.valueOf(o.toString());
	}
	
	@Override
	public void updateHistoryMaxId(Long maxId)
	{
		sqlMapClient.update("updateHistoryMaxId",maxId);
	}
	@Override
	public void insertHistoryMaxId(Long maxId)
	{
		sqlMapClient.insert("insertHistoryMaxId",maxId);
	}

	@Override
	public List<StockUpdateHistory> selectHistoryByHistoryMaxid(Long maxHistoryId)
	{
		 return (List<StockUpdateHistory>)sqlMapClient.queryForList("selectHistoryByHistoryMaxid",maxHistoryId);
	}

	@Override
	public Long insertStockUpdate(StockUpdate stockUpdate)
	{
		Object o = sqlMapClient.insert("insertStockUpdate",stockUpdate);
		if(o != null)return (Long)o;
		return -1L;
	}

	@Override
	public List<PlatFormOrderRecord> getOrdersByOrders(List<String> list)
	{
		return sqlMapClient.queryForList("selectKaolaOrderByOrders",list); 	
	}

	@Override
	public void insertOrder(PlatFormOrderRecord kaolaOrder)
	{
		 sqlMapClient.insert("insertKaoLaOrder",kaolaOrder);
		
	}
	@Override
	public List<StockUpdate> searchStockUpdate(Map<String, String> searchMap)
	{
		return  sqlMapClient.queryForList("selectStockUpdateByMap",searchMap);
	}
	@Override
	public int searchStockUpdateCnt(Map<String, String> searchMap)
	{
		Object o = sqlMapClient.queryForObject("selectStockUpdateByMapCount",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}
	@Override
	public List<StockUpdateLog> selectStockUpdateLogByMap(
			Map<String, String> searchMap)
	{
		return sqlMapClient.queryForList("selectStockUpdateLogByMap",searchMap); 	
	}
	
	
	
	
	@Override
	public void updateOrder(PlatFormOrderRecord Order) {
		 sqlMapClient.update("updateOrder", Order);
		
	}
	@Override
	public PlatFormOrderRecord select(PlatFormOrderRecord Order) {
		 
		return (PlatFormOrderRecord) sqlMapClient.queryForObject("searchOrders", Order);
	}
	@Override
	public PlatFormOrderRecord selectderRecord(
			PlatFormOrderRecord platformorderdetails) {           
		return (PlatFormOrderRecord) sqlMapClient.queryForObject("searchRecords", platformorderdetails);
	}
	@Override
	public void insertxhsOrder(PlatFormOrderRecord Order) {
		sqlMapClient.insert("insertxhsOrder",Order);
		
	}
	@Override
	public StockUpdate selectstock(StockUpdate stockupdate) {
		return  (StockUpdate) sqlMapClient.queryForObject("stockUpdateResult", stockupdate);
		
	}
	@Override
	public void updateStockUpdate(Map<String, String> searchMap) {
		sqlMapClient.update("updatestockOrder", searchMap);
		
	}
	@Override
	public void insertStockReserve(StockReserve stockreserve) {
		sqlMapClient.insert("insertStockReserve", stockreserve);
		
	}
	/*
	 * 
	 */
	@Override
	public List<StockUpdateLog> selectStockUpdateLogByMapAndError(Map<String, String> searchMap) {
		return sqlMapClient.queryForList("selectStockUpdateLogByMapAndError",searchMap); 
	}
	/* (non-Javadoc)
	 * 更新单个库存，通过sku和type
	 */
	@Override
	public void updateNowStockBySku(StockUpdate su) {
		sqlMapClient.update("updateNowStockBySku",su);
		
	}

 

}
 
