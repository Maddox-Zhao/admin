package com.huaixuan.network.biz.service.platformstock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.HigoPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.common.util.json.JSONArray;

import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-10-20 上午11:40:56
 * 同步higo平台的库存
 **/

@Service("higoPlatFormStockUpdate")
public class HigoPlatFormStockUpdateImpl implements HigoPlatFormStockUpdate
{

	
	private static final String APP_KEY = "7537159006";
	private static final String APP_SECRET = "w7surzf9jcignep2z7vthnif13xuzc";
	private static final String ACCESS_TOKEN = "50ac55de0e208afe9765c9671f697a54f62981a6";
	private static final String API_URL = "http://open.lehe.com/oauth2/api?";
	private static final String Higo_SKU_List = "higo_sku.txt";
	protected Log log = LogFactory.getLog(this.getClass());
	private static final String V = "1.0";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Map<String, List<String>> repSkuMap = new HashMap<String, List<String>>();
	
	@Autowired
	private StockUpdateService stockService;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	
  
//	private HigoPlatFormStockUpdateImpl() {
//		repSkuMap = MiniUiUtil.readRpeatSku(Higo_SKU_List);
//	}

	@Override
	public int atuoSyncOrder()
	{
		String method = "higo.order.search";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("method", method);//调用方法
		map.put("app_key", APP_KEY);
		map.put("session", ACCESS_TOKEN);
		map.put("v", V);
		map.put("sign_method", "md5");
		//map.put("order_status", "1002");//订单正常
		
	 
		//应用参数
		Date now_24 = new Date(new Date().getTime() - 2*24*60*60*1000); //2天内的订单
		String startTimeStr = sdf.format(now_24);
		String endTimeStr = sdf.format(new Date());
		int pageNO = 1;
		int pageSize = 20;
		map.put("timestamp", endTimeStr);//调用时间
		map.put("ctime_start", startTimeStr); //订单起始日期
		map.put("ctime_end", endTimeStr); //订单结束日期
		
		 Map<String,List<String>> orderMap = new HashMap<String, List<String>>(); //保存订单信息和对应的sku
		 List<String> list = new ArrayList<String>(); //当前查询出来的订单id
		while(true)
		{
			map.put("page_curr", pageNO+"");
			map.remove("sign");
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(API_URL, map);
			 try
			{
				JSONObject jsonObject = new JSONObject(response);
				JSONObject higo_order_search = new JSONObject(jsonObject.getString("higo_order_search"));
				int totalNum = higo_order_search.getInt("total_num");
				if(totalNum == 0) break;
				int totalPageSize = (totalNum/pageSize)+1; //总页数
				//当前页为最后一页 退出
				if(pageNO > totalPageSize)break;
				pageNO++;
				 JSONArray order_data = higo_order_search.getJSONArray("order_data");
				 
				
				 for(int index = 0; index < order_data.length(); index++)
				 {
					 JSONObject orderInfo = new JSONObject(order_data.get(index).toString());
 
					 String orderId = new JSONObject(orderInfo.getString("order_info")).getString("order_id");
					 String order_state = new JSONObject(orderInfo.getString("order_info")).getString("order_state");
					 if(!"1002".equals(order_state)) continue;//不是商家备货状态的不处理
					 list.add(orderId); 
					 JSONArray goodsInfo = orderInfo.getJSONArray("goods_info");
					 for(int i = 0; i < goodsInfo.length(); i ++)
					 {
						 JSONObject  good = new JSONObject(goodsInfo.get(i).toString());
						 String higoSku = good.getString("sku_id");
						 int goodsNum = good.getInt("goods_quantity");
						 String msg = higoSku+"_" +goodsNum; //sku_num
						 if(orderMap.get(orderId) == null)
						 {
							 List<String> value = new ArrayList<String>();
							 value.add(msg);
							 orderMap.put(orderId, value);
						 }
						 else
						 {
							 orderMap.get(orderId).add(msg);
						 }
						  
					 }
					 
				 }
				 
				
				
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
		
		int updateNumber = 0;
		//如果有数据
		if(orderMap.size() > 0)
		{
			list.add("-1");//避免没有数据  报错
			List<PlatFormOrderRecord>  orderList = platformStockUpdateDao.getOrdersByOrders(list);
			//用作对比 如果有记录则说明已处理
			Map<String,String> orderMapRealy = new HashMap<String, String>();
			for(PlatFormOrderRecord k : orderList)
			{
				orderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Entry<String,List<String>>> it = set.iterator();
			
			while(it.hasNext())
			{
				Entry<String,List<String>> entry = it.next();
				String orderId  = entry.getKey();
				List<String> value = entry.getValue();//higosku_number
				boolean updateSuccess = false;
				if(orderMapRealy.get(orderId) == null) //没有保存该orderid 没处理  增加orderid数 并插入表
				{
						for(String v : value)
						{
							String[] arr = v.split("_");
							String higoSku = arr[0];
							int num = Integer.valueOf(arr[1]);
							Map<String,String> searchMap = new HashMap<String, String>();
							searchMap.put("type", "sh");
							searchMap.put("higoSku", higoSku);
							List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
							for(StockUpdate su : stockUpdateList)
							{
								Map<String,String> searchMaps = new HashMap<String, String>();
								searchMaps.put("sku", su.getSku());
								searchMaps.put("type", "hk");
								boolean updatesh = true;
								List<StockUpdate>  stockUpdateLists = platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
								if(stockUpdateLists != null && stockUpdateLists.size() > 0){
									int  s = su.getNowStockNum()-su.getOrderStockNum();
									if(s <=0 ){
											for(StockUpdate ss : stockUpdateLists){
												ss.setHigoOrderStock(num + ss.getHigoOrderStock());
												ss.setLastOrderTime("yes");
												platformStockUpdateDao.updateStockByNotNull(ss);
												updateSuccess = true;
												updatesh = false;
												updateNumber++;
											}
										}
									}
								if(updatesh){
								su.setHigoOrderStock(num + su.getHigoOrderStock());
								su.setLastOrderTime("yes");
								platformStockUpdateDao.updateStockByNotNull(su);
								updateSuccess = true;
								updateNumber++;
								}
							}
							
						}
					
				}
				if(updateSuccess)
				{
					//记录该orderid
					PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
					oderRecord.setOrderId(orderId);
					oderRecord.setIdPlartform(6);//higo
					oderRecord.setIdStatus(1);//已支付的订单
					platformStockUpdateDao.insertOrder(oderRecord);
					
				}
			}
		}
		
		
		return updateNumber;
	}

	@Override
	public void updateAllStock()
	{
		Map<String,String> searchMapHK = new HashMap<String, String>();
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", "sh");
		searchMap.put("skuisnotnull", "higo_sku");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate s : list)
		{
			int num = s.getNowStockNum() - s.getOrderStockNum();
			Map<String,String> map = new HashMap<String, String>();
			//查询香港库存
			map.put("type", "hk");
			map.put("sku", s.getSku());
			List<StockUpdate> hkList =  platformStockUpdateDao.selectStockUpdateByMap(map);
			int hkNum = 0;
			for(StockUpdate hks : hkList){
				int cnt = hks.getNowStockNum() - hks.getOrderStockNum();
				if(cnt < 0 ) cnt = 0;
				hkNum += cnt;
			}
			num += hkNum;
			if(num < 0) continue;
			updateHigOStock(s.getHigoSku(), s.getSku(), num, s.getType());
			//更新一次休息1秒
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				log.error(e.getMessage(), e);
			}
		}

	}

	@Override
	public boolean updateHigOStock(String higoSku, String ourSku, int num,String type)
	{
		if(StringUtil.isBlank(higoSku) && StringUtil.isBlank(ourSku)) return true;
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("higoSku", higoSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(higoSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "higo_sku");
			higoSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(higoSku)) return true;
		String method = "higo.product.update_stock";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("method", method);//调用方法
		map.put("app_key", APP_KEY);
		map.put("session", ACCESS_TOKEN);
		map.put("v", V);
		map.put("sign_method", "md5");
		
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", higoSku);
		logMap.put("name", "higo");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		boolean flag = false;

		if(repSkuMap.get(higoSku) != null){
			List<String> higoSkuLists = repSkuMap.get(ourSku);
			for (String higoSkus : higoSkuLists) {
				String nowTime = sdf.format(new Date());
				map.put("timestamp", nowTime);
				map.put("sku_id", higoSkus);
				map.put("stock", num+"");
				map.put("type", "1");
				String sign = getSign(map);
				map.put("sign", sign);
//				logMap.put("psku", higoSkus);
				//发送POST请求 获取数据
				String response = HttpRequest.sendPost(API_URL, map);
				try
				{
					
					JSONObject jsonObject = new JSONObject(response);
					jsonObject.get("higo_product_update_stock"); //能获取到higo_product_update_stock说明更新成功
					flag = true;
				}
				catch (Exception e)
				{
					logMap.put("type", "error");
					logMap.put("error",response);
					log.error(e.getMessage(), e);
				}
				//记录日志
				autoSyncDao.addUpdateLog(logMap);
			}
		}else{
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("sku_id", higoSku);
			map.put("stock", num+"");
			map.put("type", "1");
			String sign = getSign(map);
			map.put("sign", sign);
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(API_URL, map);
			try
			{
				
				JSONObject jsonObject = new JSONObject(response);
				jsonObject.get("higo_product_update_stock"); //能获取到higo_product_update_stock说明更新成功
				flag = true;
			}
			catch (Exception e)
			{
				logMap.put("type", "error");
				logMap.put("error",response);
				log.error(e.getMessage(), e);
			}
			//记录日志
			autoSyncDao.addUpdateLog(logMap);
		}
		return flag;
	}
	private String getSign(Map<String,String> map)
	{
		String sign = "";
		Set<Entry<String, String>> entrySet  =  map.entrySet();
		Iterator<Entry<String,String>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String,String> en = it.next();
			sign += en.getKey() + en.getValue();
		}
		sign = APP_SECRET + sign + APP_SECRET;

		sign = HttpRequest.string2MD5(sign).toUpperCase();
		return sign;
	}
	
	

	@Override
	public void updateSku2Location()
	{
//		stockService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
//		platformStockUpdateDao.updateStockUpdateSku2Null("higo_sku");//更新平台sku为NULL 
		MiniUiUtil.writeText2File(Higo_SKU_List, "", "cx");
		StringBuffer sb = new StringBuffer();
		
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统级参数
		String method = "higo.product.search";		
		map.put("method", method);//调用方法
		//系统需要参数
		map.put("app_key", APP_KEY);
		map.put("session", ACCESS_TOKEN);
		map.put("v", V);
		map.put("sign_method", "md5");
		
		int pageNO = 1;
		int pageSize = 50;
		int errorCnt = 0;
		while(true)
		{
			//应用级参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("goods_status", "1"); //产品状态 未删除 
			map.put("page", pageNO+""); //当前页
			
			map.put("page_size", pageSize+""); //每页条数
			map.remove("sign");//先移除
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(API_URL, map);
			try
			{
				JSONObject jsonObj = new JSONObject(response);
				String higo_product_search_str = jsonObj.getString("higo_product_search");
				JSONObject higo_product_search = new JSONObject(higo_product_search_str);
				int totalNum = higo_product_search.getInt("total");
				int totalPageSize = (totalNum/pageSize)+1; //总页数
				JSONArray  items = higo_product_search.getJSONArray("items");
				
				List<PlatformSku2LocationSku> higoSkuList = new ArrayList<PlatformSku2LocationSku>();
				for(int i = 0; i < items.length();i++)
				{
					
					JSONObject item = new JSONObject(items.get(i).toString());
					
					JSONArray  skus =  item.getJSONArray("skus");
					
					//JSONArray  express_path =  item.getJSONArray("express_path");
					//if(express_path.length() <= 0) continue;
					//一个商品对应下面的所有发货地暂时都一样 后期higo可能会改 到时候再说
					//JSONObject expressObj =  new JSONObject(express_path.get(0).toString());
					//String type = expressObj.getString("store_type");//1:国内货仓;2:海外 3国内保税
					//if(StringUtil.isBlank(type)) continue;
					//if(type.equals("1") || type.equals("3")) type = "sh";
					//else if(type.equals("2")) type = "hk";
					//if(!type.equals("sh") && !type.equals("hk")) continue;
					
					for(int index = 0; index < skus.length();index++)
					{
						JSONObject sku = new JSONObject(skus.get(index).toString());
						String higoSku = sku.getString("sku_id");
						String ourSku = sku.getString("art_no");
						if(ourSku != null) ourSku = ourSku.trim();
						
						//如果sku都存在 且我们sku正确
						if(StringUtil.isNotBlank(higoSku) && StringUtil.isNotBlank(ourSku))
						{
							//同步sku到本地,  批量更新
							PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
							pslSku.setOurSku(ourSku);
							pslSku.setPlatformField("higo_sku");
							pslSku.setPlatformSku(higoSku);
							pslSku.setType("sh"); 
							//pslSku.setType(type); 
							higoSkuList.add(pslSku);
							
							sb.append(ourSku);
							sb.append(",");
							sb.append(higoSku+"");
							sb.append("\n");
							
						}
					}
					
				 
				}
				
				Map higoSkuMap = new HashMap();
				higoSkuMap.put("list", higoSkuList);
				higoSkuMap.put("platformField", "higo_sku");
				
				if(higoSkuList.size() > 0)
				{
					//更新higo sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(higoSkuMap);
				}
				//当前页为最后一页 退出
				if(pageNO > totalPageSize)break;
				pageNO++;
				
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
				log.error(e.getMessage(), e);
				errorCnt++;
				//如果错误10次以上退出
				if(errorCnt > 10)
				{
					break;
				}
				
				System.out.println("higo syn sku error " + errorCnt);
			}
		}
		MiniUiUtil.writeText2File(Higo_SKU_List, sb.toString(), "zj");
		repSkuMap = MiniUiUtil.readRpeatSku(Higo_SKU_List);
//		stockService.setCanUpdateStockStatus("true"); //解除-暂时不让其他地方做更新
	}

//	public void mr2(){
//		Map<String,String> param = new HashMap<String,String>();
//	    param.put("grant_type", "authorization_code");
//	    param.put("code", "953342909d4f18c50651e482a4bb531be82916fe");
//	    param.put("client_id", "7537159006");
//	    param.put("client_secret", "w7surzf9jcignep2z7vthnif13xuzc");
//	    param.put("redirect_uri", "http://140.207.52.210/api/product/higo.html");
//	    String response = HttpRequest.sendPost("http://open.lehe.com/oauth2/token", param);
//	    System.out.println(response);
//	}
//	public static void main(String[] args)
//	{		
//		
//		HigoPlatFormStockUpdateImpl ho = new HigoPlatFormStockUpdateImpl();
//		ho.mr2();
//	}
}
 
