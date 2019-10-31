package com.huaixuan.network.biz.dao.autosyn.ibatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.AutoSynDate;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("autoSyncDaoImpl")
public class AutoSyncDaoImpl implements AutoSyncDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	/*
	 * 通过SKU获取该instanceid
	 * 
	 * @param skus eg:'123456987978','555565656565'
	 * 
	 * @return
	 */
	public List<String> getInstancesBySkus(String skus) {
		return sqlMapClient.queryForList("getInstanceidsBySkus", skus);
	}

	@Override
	/*
	 * 通过instanceid 获取库存 instancesid eg:1231,456,798
	 */
	public List<AutoSynDate> getStockByInstance(String instancesid) {
		return sqlMapClient.queryForList("getStockByInstances", instancesid);
	}

	@Override
	public List<AutoSynDate> getHkStockBySkus(String skus) {
		return sqlMapClient.queryForList("getHkStockySkus", skus);
	}

	@Override
	public List<AutoSynDate> getShStockBySkus(String skus) {
		return sqlMapClient.queryForList("getShStockySkus", skus);
	}
	
	public String getSupplkierSkuByInfo(Map map)
	{
		Object obj =  sqlMapClient.queryForObject("getSupplierSkuByinfo",map);
		if(null == obj) return "";
		return obj.toString();
	}
	
	
	
	
	public int updateShangPinSku(Map map)
	{
		return sqlMapClient.update("updateShangpSku",map);
	}
	
	
	
	public int getSupplierStockNum(String type,String sku)
	{
		Map<String,String> map = new HashMap<String, String>();
		if("".equals(sku) || null == sku) return 0;
		map.put("shangPinSku", sku);
		if("hk".equals(type))
		{
			map.put("field", "sku_shangpin_hk");
			map.put("location", "(q.idLocation = '102'  OR q.idLocation = '103')");
			
		}
		else
		{
			map.put("field", "sku_shangpin_sh");
			map.put("location", "(q.idLocation = '101'  OR q.idLocation = '202')");
		}
		Object obj = sqlMapClient.queryForObject("getSupplierStockByShangPinSku",map);
		if(null == obj) return 0;
		else return (Integer)obj;
		
	}

	@Override
	public int getCountByShangPinSku(String type, String shangPinSku) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("sku", shangPinSku);
		 if("hk".equals(type))
		 {
			 map.put("field", "sku_shangpin_hk");
		 }
		 else
		 {
			 map.put("field", "sku_shangpin_sh");
		 }
		return (Integer)sqlMapClient.queryForObject("getCountByShangPinSku",map);
	}

	/**
	 * 更新走秀SKU到本地
	 */
	@Override
	public int updateZouXiuSku2Location(String sku,String type, String material,
			String color, String size) {
		Map<String,String> map = new HashMap<String, String>();
		if("".equals(sku) || null == sku) return 0;
		if("".equals(type) || null == type) return 0;
		map.put("sku_zouxiu_hk", sku);
		map.put("type", type);
		map.put("material", material);
		map.put("color", color);
		map.put("size", size);
		
		return sqlMapClient.update("updateZouXiuSku2Location",map);
	}

	@Override
	public List<AutoSynDate> getZouXiuLocationStock(String skus) {
		
		return sqlMapClient.queryForList("getZouXiuHkStockySkus", skus);
	}

	@Override
	public int updateShangPinSku2Null(Map map)
	{
		return sqlMapClient.update("updateShangpSku2Null",map);
	}

	
	
	
	
	
	/*旺店通*/
	
	@Override
	public void updateWangDianTongSku2Location(List<Goods> list)
	{
			SqlMapClient  client = sqlMapClient.getSqlMapClient();
		  	try
			{
		  		client.startTransaction();
		  		client.startBatch();
		  		Map map = new HashMap<String, String>();
		  		for(Goods g : list)
		  		{
		  			map.put("sku", g.getTaobaoSkuProp());
		  			map.put("type", g.getType());
		  			map.put("material", g.getMaterial());
		  			map.put("color", g.getColor());
		  			map.put("size", g.getSize());
		  			client.update("updateWangDianTongSku2Location", map);
		  		}
		  		client.executeBatch();
		  		client.commitTransaction();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					client.endTransaction();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		  	
	}

	@Override
	public AutoSynDate getWangDianTongStockBySku(String sku)
	{
		Object o = sqlMapClient.queryForObject("getWangDianTongStock",sku);
		if(o == null) return null;
		else return (AutoSynDate)o;
	}

	@Override
	public String getHuoHaoByWangDianTongSku(String sku)
	{
		Object o = sqlMapClient.queryForObject("getHuoHaoByWdtSku",sku);
		if(null != o) return o.toString();
		return "";
	}

	@Override
	public List<String> getWangDianTongSkus()
	{
		return sqlMapClient.queryForList("getWangDianTongSkus");
	}

	@Override
	public void clearPlatformSku2Null(String field)
	{
		sqlMapClient.update("clearPlatformSku2Null",field);
	}

	@Override
	public List<StockData> searchStockBySiteList(List<Long> siteList)
	{
		if(siteList == null || siteList.size() == 0) return new ArrayList<StockData>();
		return sqlMapClient.queryForList("searchStockBySites",siteList);
	}

	@Override
	public String getPlatformSkuByOurSku(Map<String, String> map)
	{
		if(map == null) return "";
		if(map.get("sku") == null || map.get("field") == null) return "";
		Object obj = sqlMapClient.queryForObject("getPlatformSkuByOurSku",map);
		if(obj == null) return "";
		return obj.toString();
	}

	@Override
	public String getHuoHaoByOurSku(Map<String, String> map)
	{
		if(map == null) return "";
		if(map.get("sku") == null) return "";
		Object o = sqlMapClient.queryForObject("getHuoHaoByOurSku",map);
		if(null != o) return o.toString();
		return "";
	}

	
	/**
	 * 根据SKU和站点ID获取对应的可售库存
	 * @param siteList
	 * @return
	 */
	@Override
	public int searchStockBySiteAndSku(List<Long> idSiteList, String sku)
	{
		if(idSiteList == null || idSiteList.size() == 0) return 0;
		String idSite = "";
		for(int i = 0; i < idSiteList.size();i++)
		{
			idSite =  idSite + "'" + idSiteList.get(i) + "'";
			if(i < idSiteList.size()-1)
			{
				idSite += ",";
			}
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("sku", sku);
		map.put("idSite", idSite);
		Object o = sqlMapClient.queryForObject("searchStockBySitesAndSku",map);
		if(o == null) return 0;
		StockData sd = (StockData)o;
		return sd.getNum();
		
	}

	@Override
	public int getStockByOurSku(Map<String, String> map)
	{
		Object o = sqlMapClient.queryForObject("getSupplierStockByOurSku",map);
		if(o != null) return (Integer)o;
		return 0;
	}

	
	@Override
	public void updateKaoLaSku2Location(List<LogData> list,String type)
	{
			if("sh".equals(type))
			{
				 sqlMapClient.update("updateKaoLaSku2LocationForSh", list);
			}
			else
			{
				sqlMapClient.update("updateKaoLaSku2LocationForHk", list);
			}
	}
	
	
	public void updateKaoLaKey2Location(List<LogData> list,String type)
	{
		if("sh".equals(type))
		{
			 sqlMapClient.update("updateKaoLaKey2LocationForSh", list);
		}
		else
		{
			sqlMapClient.update("updateKaoLaKey2LocationForHk", list);
		}
	}

	@Override
	public List<PlatformData> selectNeedUpdateStockProduct(
			Map<String, String> map)
	{
		return sqlMapClient.queryForList("selectNeedUpdateStockProduct",map);
	}

	@Override
	public void updateNeedUpdateStockProduct(Map<String,String> map)
	{
		sqlMapClient.update("updateNeedUpdateStockProduct",map);
		
	}

	@Override
	public String getKaoLaKeyByIdProduct(String idProduct, String type)
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("kaolakey", "kaola_key_sh");
		map.put("idProduct", idProduct);
		if("hk".equals(type))
		{
			map.put("kaolakey", "kaola_key_hk");
		}
		Object o = sqlMapClient.queryForObject("getKaoLaKeyByIdProduct",map);
		if(o == null) return "";
		return o.toString();
	}

	@Override
	public void addUpdateLog(Map<String, String> map)
	{
		sqlMapClient.insert("addUpdatePlatformStockLog",map);
	}
 


}
