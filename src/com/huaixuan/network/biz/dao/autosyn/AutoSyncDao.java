package com.huaixuan.network.biz.dao.autosyn;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.autosyn.AutoSynDate;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.goods.Goods;


/**
 * 自动同步 尚品 走秀等网站需要查询的数据
 * @author YangJie  2015年8月7日 15:40:42
 *
 */
public interface AutoSyncDao 
{
	
		/**
		 * 通过SKU获取该instanceid
		 * @param skus   eg:'123456987978','555565656565'
		 * @return
		 */
		public List<String> getInstancesBySkus(String skus);
		
		
		
		public List<AutoSynDate> getStockByInstance(String instancesid);
		
		
		/**
		 * 通过sku获取上海库存
		 * @param skus
		 * @return
		 */
		public List<AutoSynDate> getShStockBySkus(String skus);
		
		/**
		 * 通过sku获取香港库存
		 * @param skus
		 * @return
		 */
		public List<AutoSynDate> getHkStockBySkus(String skus);
		
		
		/**
		 * 通过型号材质颜色大小查询sku
		 * @param map
		 * @return
		 */
		public String getSupplkierSkuByInfo(Map map);
		
		
		/**
		 * 更新尚品SKU到本地
		 */
		public int updateShangPinSku(Map map);
		
		/**
		 * 更新尚品SKU为空
		 */
		public int updateShangPinSku2Null(Map map);
		
		
		/**
		 * 通过尚品sku获取库存
		 * @param map
		 */
		public int getSupplierStockNum(String type,String sku);
		
		
		
		/**
		 * 通过尚品sku查询是否有更新尚品sku到本地
		 * @param map
		 */
		public int getCountByShangPinSku(String type,String shangPinSku);
		
		
		
		/**
		 * 更新走秀SKU到本地
		 */
		public int updateZouXiuSku2Location(String sku,String type,String material,String color,String size);
		
		
		
		/**
		 * 通过走秀SKU获取本地库存
		 */
		public List<AutoSynDate> getZouXiuLocationStock(String skus);
		
		
		
		
		
		
		//旺店通
		
		/**
		 * 更新旺店通sku到本地来 List为需要更新的对象
		 */
		public void updateWangDianTongSku2Location(List<Goods> list);
		
		
		
		/**
		 * 获取旺店通已有sku的库存
		 * @return
		 */
		public AutoSynDate getWangDianTongStockBySku(String sku);
		
		
		/**
		 * 通过旺店通sku查询货号
		 * @param sku
		 * @return
		 */
		public String getHuoHaoByWangDianTongSku(String sku);
		
		
		
		/**
		 * 获取旺店通已有的sku
		 * @return
		 */
		public List<String> getWangDianTongSkus();
		
		
		/**
		 * 清空对应平台sku为空
		 * @param field
		 */
		public void clearPlatformSku2Null(String field);
		
		
		
		/**
		 * 根据站点ID查询该站点可售库存
		 * @param siteList
		 * @return
		 */
		public List<StockData> searchStockBySiteList(List<Long> siteList);
		
		
	 
		/**
		 * 根据我们的sku获取平台的sku
		 * @param map 需要两个字段 filed-需要获取的sku字段名  和  sku-我们的sku
		 * @return
		 */
		public String getPlatformSkuByOurSku(Map<String,String> map);
		
		/**
		 * 根据我们的sku获取货号  型号 材质 颜色 大小
		 * @param map 需要sku字段
		 * @return
		 */
		public String getHuoHaoByOurSku(Map<String,String> map);
		
		
		
		/**
		 * 根据SKU和站点ID获取对应的可售库存
		 * @param siteList
		 * @return
		 */
		public int searchStockBySiteAndSku(List<Long> idSiteList,String sku);
		 
		
		/**
		 * 根据我们的sku获取对应的可售库存  
		 * @param map  必须包含我们的sku和location  站点
		 * @return
		 */
		public int getStockByOurSku(Map<String,String> map);
		
		
		/**
		 * 更新考拉sku到本地
		 * 
		 * @param list
		 * @param type sh-上海  hk-香港
		 */
		public void updateKaoLaSku2Location(List<LogData> list,String type);
		
		
		
		/**
		 * 更新考拉key到本地
		 * 
		 * @param list
		 * @param type sh-上海  hk-香港
		 */
		public void updateKaoLaKey2Location(List<LogData> list,String type);
		
		/**
		 * 
		 * @param map  map 包含idProduct 和 查询的字段名 kaola_key_sh or kaola_key_hk 
		 * @return 
		 */
		public String getKaoLaKeyByIdProduct(String idProduct,String type);
		
		
		
		/**
		 * 自动同步平台库存
		 */
		
		
		/**
		 * 查询需要痛苦的数据
		 */
		public List<PlatformData> selectNeedUpdateStockProduct(Map<String,String> map);
		
		/**
		 * 更新处理过的数据
		 * @param id
		 */
		public void updateNeedUpdateStockProduct(Map<String,String> map);
		
		
		
		/**
		 * 记录更新日志
		 * @param map
		 */
		public void addUpdateLog(Map<String,String> map);
		
}
