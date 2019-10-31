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
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockNumber;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.KaolaPlatHaiTaoformStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;

@Service("KaolaPlatHaiTaoformStockUpdate")
public class KaolaPlatHaiTaoformStockUpdateImpl implements KaolaPlatHaiTaoformStockUpdate{
	
	
//	private static final String HT_TEST_APP_KEY = "edb6c3b9ac4847e7584c38e2b630b14f";
	
	private static final String HT_APP_KEY = "8a82a4405650a89b464f40251acad897";
	
//	private static final String HT__TEST_APP_SECRET = "8200ee92ec22fcae76e2f00bc5c79247188e0593";
	
	private static final String HT_APP_SECRET = "89b66dc770eacde9a704edb6884d44a8df889bd6b815a59ebd5910d46d4d1547";
	
//	private static final String HT_TEST_ACCESS_TOKEN = "ae60da8c-a8f9-4571-bcb0-d500e5c040cc";
	
	private static final String HT_ACCESS_TOKEN = "090b6c27-9171-47a0-8036-84d547356607";
	
//	private static final String BASE_URL = "http://openapi-test.kaola.com/router";
	
	private static final String BASE_URL = "https://openapi.kaola.com/router";
	
	private static Map<String,List<String>> HTRepSkuMap = new HashMap<String, List<String>>();//记录有重复的SKU
	
	private static final String HT_KAOLA_SKU_FILE_NAME= "kaola_HT_sku.txt";
	
	private static final String path = "d:/stock_log/";//记录库存的日志文件夹
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;

	@Autowired
	private  AutoSyncDao autoSyncDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	@Override
	public void updateSku2Location() {
		
		platformStockUpdateDao.updateStockUpdateSku2Null("kaolaHT_on_sale_status");
		MiniUiUtil.writeText2File(HT_KAOLA_SKU_FILE_NAME, "", "cx");
		//5-在售  6-下架  先更新下架 在更新在售  防止下架的产品和在售的sku重复  已在售的sku为准
		updateSku2LocationByType("hk","6");
		updateSku2LocationByType("hk","5");
		
		HTRepSkuMap = MiniUiUtil.readRpeatSku(HT_KAOLA_SKU_FILE_NAME);
	}
	
	private int updateSku2LocationByType(String type,String productStatus)
	{
		String method = "kaola.item.batch.status.get";
		int pageNO = 1;	
		int pageSize = 20;
		int totalNum = 0;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		if("hk".equals(type))
		{
			map.put("secret", HT_APP_SECRET);
			map.put("app_key", HT_APP_KEY);
			map.put("access_token", HT_ACCESS_TOKEN);
		}
		int totalCnt = 0;
		int errorCnt = 1;
		StringBuilder sb = new StringBuilder(); //记录sku对应关系
		while (true) {
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("item_edit_status", productStatus); //产品状态 
			map.put("page_no", pageNO+""); //当前页
			map.put("page_size", pageSize+""); //每页条数
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			pageNO++;
			String response = HttpRequest.sendPost(BASE_URL, map);
//			System.out.println(response);
			try {
				List<PlatformSku2LocationSku> kaolaHTSkuList = new ArrayList<PlatformSku2LocationSku>();
				List<PlatformSku2LocationSku> kaolaHTKeyList = new ArrayList<PlatformSku2LocationSku>();
				JSONObject jsonObj = new JSONObject(response);
				JSONObject itemsResponse = new JSONObject(jsonObj.get("kaola_item_batch_status_get_response").toString());
				totalNum = itemsResponse.getInt("total_count");
				if(0 == totalNum) break; //没有数据 中断
				int totalPageSize = (totalNum/pageSize)+1; //总页数
				JSONArray jsonArray = itemsResponse.getJSONArray("item_edit_list");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					String kaolaHTKey = productObj.getString("key");//商品key 一个商品对应多个sku
					JSONObject item = new JSONObject(productObj.getString("raw_item_edit"));
					String status ="";
					String itemStaus = item.getString("item_status");
					if(itemStaus.equals("ON_SALE")){
						status ="1";
					}else{
						status="0";
					}
					JSONArray skuArray = productObj.getJSONArray("sku_list");
					for (int index = 0; index < skuArray.length(); index++) {
						JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
						
						String kaolaHTSku = skuObj.getString("key"); //考拉海淘sku
						String supplySku = new JSONObject(skuObj.getString("raw_sku")).getString("bar_code"); //供货商SKU
						if(StringUtil.isBlank(supplySku) || StringUtil.isBlank(kaolaHTSku)) continue;
						supplySku = supplySku.trim();
						String[] supplySkuArr = supplySku.split("-");
						if(supplySkuArr.length == 2)
						{
							supplySku = supplySkuArr[1].trim();
						}
						else
						{
							supplySku = supplySkuArr[0].trim();
						}
						//同步sku到本地,单个更新使用, 批量更新
						PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
						pslSku.setOurSku(supplySku);
						pslSku.setPlatformField("kaolaHT_sku");
						pslSku.setPlatformSku(kaolaHTSku);
						pslSku.setType(type); 
						kaolaHTSkuList.add(pslSku);
						
						sb.append(supplySku);
						sb.append(",");
						sb.append(kaolaHTSku+"");
						sb.append("\n");
						
						//同步考拉key到本地 一个key对应多个sku 用于更新库时查询考拉当前库存(查询考拉库存必须要考拉key  没有用考拉sku查询的接口)
						PlatformSku2LocationSku pslKey = new PlatformSku2LocationSku();
						pslKey.setOurSku(supplySku);
						pslKey.setPlatformField("kaolaHT_key");
						pslKey.setPlatformstatus("kaolaHT_on_sale_status");
						pslKey.setStatus(status);
						pslKey.setPlatformSku(kaolaHTKey);
						pslKey.setType(type); 
						kaolaHTKeyList.add(pslKey);
						
						totalCnt++;
					}
				}
				Map kaolaHTSkuMap = new HashMap();
				kaolaHTSkuMap.put("list", kaolaHTSkuList);
				kaolaHTSkuMap.put("platformField", "kaolaHT_sku");
				
				Map kaolaHTKeyMap = new HashMap();
				kaolaHTKeyMap.put("list", kaolaHTKeyList);
				kaolaHTKeyMap.put("platformField", "kaolaHT_key");
				kaolaHTKeyMap.put("platformstatus", "kaolaHT_on_sale_status");
				
				if(kaolaHTSkuList.size() > 0)
				{
					//更新考拉sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(kaolaHTSkuMap);
				}
				
				if(kaolaHTKeyList.size() > 0)
				{
					//更新考拉key到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(kaolaHTKeyMap);
				}
				//当前页为最后一页 中断
				if(pageNO > totalPageSize)break;
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("klht syn sku error " + pageNO);
				if(errorCnt++ > 5)break; //错误5次挑出
			}
		}
		if("hk".equals(type))
			MiniUiUtil.writeText2File(HT_KAOLA_SKU_FILE_NAME, sb.toString(), "zj");
		return totalCnt;
	}
	
	@Override
	public boolean updateKaoLahtStocku(String kaolahtSku, String ourSku, int num,
			String type) {
		if(StringUtil.isBlank(kaolahtSku) && StringUtil.isBlank(ourSku)) return true;
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("kaolahtsku", kaolahtSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}else if(StringUtil.isBlank(kaolahtSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "kaolaHT_sku");
			kaolahtSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(kaolahtSku)) return true;
		String method = "kaola.item.sku.stock.update";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("method", method);//调用方法
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HT_APP_SECRET);
			map.put("app_key", HT_APP_KEY);
			map.put("access_token", HT_ACCESS_TOKEN);
		}
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", kaolahtSku);
		logMap.put("name", "kaohtla");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		List<String> kaolaSkusList = null;
		if("hk".equals(type))
		{
			kaolaSkusList = HTRepSkuMap.get(ourSku);
		}
		if(kaolaSkusList != null)
		{
			boolean flag = false;
			for(String kaolahtSku1 : kaolaSkusList)
			{
				//应用参数
				String nowTime = sdf.format(new Date());
				map.put("timestamp", nowTime);//调用时间
				map.put("key", kaolahtSku1); //考拉sku
				map.put("stock", num+""); //库存
				String sign = getSign(map);
				map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
				//发送POST请求 获取数据
				String response = HttpRequest.sendPost(BASE_URL, map);
				logMap.put("psku", kaolahtSku1);
				try {
					
					JSONObject jsonObject = new JSONObject(response);
					String result = new JSONObject(jsonObject.getString("kaola_item_sku_stock_update_response")).getString("result");
					if("1".equals(result))
					{
						flag = true;
						logMap.put("type", "success");
					}
					else
					{
						logMap.put("type", "error");
					    logMap.put("error",response);
					}
					
				} catch (Exception e) {
					 log.error(e.getMessage(), e);
					 logMap.put("type", "error");
					 logMap.put("error",response);
				}
				//记录日志
				autoSyncDao.addUpdateLog(logMap);
			}
			return flag;
		}
		else
		{
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("key", kaolahtSku); //考拉sku
			map.put("stock", num+""); //库存
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			logMap.put("psku", kaolahtSku);
			String response = HttpRequest.sendPost(BASE_URL, map);
			boolean flag = true;
			try {
				JSONObject jsonObject = new JSONObject(response);
				String result = new JSONObject(jsonObject.getString("kaola_item_sku_stock_update_response")).getString("result");
				if("1".equals(result))
				{
					logMap.put("type", "success");
					flag = true;
				}
				else
				{
					logMap.put("type", "error");
				    logMap.put("error",response);
				    flag = true;
				}
			} catch (Exception e) {
				 logMap.put("type", "error");
				 logMap.put("error",response);
				 log.error(e.getMessage(), e);
			}
			//记录日志
			autoSyncDao.addUpdateLog(logMap);
			return flag;
		}
	}

	@Override
	public int atuoSyncOrder(String type) {
		Date now_24 = new Date(new Date().getTime() - 3*24*60*60*1000); //3天内的订单
		String startTimeStr = sdf.format(now_24);
		String endTimeStr = sdf.format(new Date());
		String method = "kaola.order.search";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("method", method);//调用方法	
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HT_APP_SECRET);
			map.put("app_key", HT_APP_KEY);
			map.put("access_token", HT_ACCESS_TOKEN);
		}
		//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("order_status", "1"); //已付款
			map.put("page_size", "100"); //
			map.put("start_time", startTimeStr); //
			map.put("end_time", endTimeStr); //
			map.put("date_type", "1"); //支付时间
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			String response = HttpRequest.sendPost(BASE_URL, map);
			Map<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("type", type);
			int updateNumber = 0;
			Map<String,String> orderMap = new HashMap<String, String>(); //保存订单信息和对应的sku
			List<String> list = new ArrayList<String>(); //当前查询出来的订单id
			try {
				JSONObject objList = new JSONObject(response);
				JSONObject orderObj = new JSONObject(objList.getString("kaola_order_search_response"));
				JSONArray orderList = orderObj.getJSONArray("orders");
				for(int i = 0; i < orderList.length();i++)
				{
					JSONObject skuList = new JSONObject(orderList.get(i).toString());
					String orderId = skuList.getString("order_id");//订单号
					PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
					platformorderdetails.setIdorder(orderId);
					List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
					String receiver_name = skuList.getString("receiver_name");//收件人姓名
					String receiver_phone = skuList.getString("receiver_phone");//收件人手机
					String receiver_province_name = skuList.getString("receiver_province_name");//省
					String receiver_city_name = skuList.getString("receiver_city_name");//市
					String receiver_district_name = skuList.getString("receiver_district_name");//区
					String receiver_address_detail = skuList.getString("receiver_address_detail");//详细地址
					String receiver_post_code = skuList.getString("receiver_post_code");//邮政编码
					String pay_success_time = skuList.getString("pay_success_time");//支付时间
					String order_real_price = skuList.getString("order_real_price");//订单实际价格
					String order_origin_price = skuList.getString("order_origin_price");//订单原始价格
					String express_fee = skuList.getString("express_fee");//邮费
					String order_time  = skuList.getString("order_time");//订单下单时间
					JSONArray orderSkus = skuList.getJSONArray("order_skus");
					for (int  j= 0; j < orderSkus.length();j++) {
						JSONObject goods = new JSONObject(orderSkus.get(j).toString());
						String skuId = goods.getString("sku_key");//平台SKU
						String product_name = goods.getString("product_name");//商品名称
						String origin_price = goods.getString("origin_price");//原始单价
						String count = goods.getString("count");//商品数量
						String ourSku = goods.getString("barcode");//我们的SKU
						if(!ourSku.equals("")){
							ourSku = ourSku.substring(ourSku.indexOf("-")+1);
						}
						platformorderdetails.setIdorder(orderId);
						platformorderdetails.setQuantity(count);
						platformorderdetails.setSkuId(skuId);
						platformorderdetails.setName(receiver_name);
						platformorderdetails.setMobile(receiver_phone);
						platformorderdetails.setProvince(receiver_province_name);
						platformorderdetails.setCity(receiver_city_name);
						platformorderdetails.setDistrict(receiver_district_name);
						platformorderdetails.setStreetAddress(receiver_address_detail);
						platformorderdetails.setZipCode(receiver_post_code);
						platformorderdetails.setPayTime(pay_success_time);
						platformorderdetails.setPayprice(order_real_price);
						platformorderdetails.setTotalPrice(order_origin_price);
						platformorderdetails.setFreight(express_fee);
						platformorderdetails.setPalcedTime(order_time);
						platformorderdetails.setProductname(product_name);
						platformorderdetails.setPrice(origin_price);
						platformorderdetails.setQuantity(count);
						platformorderdetails.setMerchantSkuId(ourSku);
						platformorderdetails.setPtype("kaolaht");
						if ((liststatus == null || liststatus.size() == 0)) {
							platformOrderDetailsDao.insertOrder(platformorderdetails);
						}else if (liststatus != null) {
							List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
							if (listst == null || listst.size() == 0) {
								platformOrderDetailsDao.insertOrder(platformorderdetails);
							}
						}
					}
					list.add(orderId); 
					orderMap.put(orderId, orderSkus.toString());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			//如果有数据
			if(orderMap.size() > 0)
			{
				list.add("-1");//避免没有数据  报错
				List<PlatFormOrderRecord>  kaolahtOrderList = platformStockUpdateDao.getOrdersByOrders(list);
				//用作对比 如果有记录则说明已处理
				Map<String,String> kaolahtOrderMapRealy = new HashMap<String, String>();
				for(PlatFormOrderRecord k : kaolahtOrderList)
				{
					kaolahtOrderMapRealy.put(k.getOrderId(), "yes");
				}
				Set<Entry<String, String>> set = orderMap.entrySet();
				Iterator<Entry<String,String>> it = set.iterator();
				while(it.hasNext())
				{
					Entry<String,String> entry = it.next();
					String orderId  = entry.getKey();
					String value = entry.getValue();
					if(kaolahtOrderMapRealy.get(orderId) == null) //没有保存该orderid 没处理  增加orderid数 并插入表
					{
						try {
							boolean updateSucces = false;
							JSONArray  orderSkus = new JSONArray(value);
							for(int index = 0; index < orderSkus.length(); index++)
							{
								JSONObject skuObj = new JSONObject(orderSkus.get(index).toString());
								String ourSku = skuObj.getString("barcode");
								if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
								ourSku = ourSku.trim();
								String[] supplySkuArr = ourSku.split("-");
								if(supplySkuArr.length == 2)
								{
									ourSku = supplySkuArr[1];
								}
								else
								{
									ourSku = supplySkuArr[0];
								}
								int num = skuObj.getInt("count");
								searchMap.put("sku", ourSku);
								searchMap.put("type", type);
								if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
								List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
								for(StockUpdate su : stockUpdateList)
								{
									su.setKaolahtOrderStock(num + su.getKaolahtOrderStock());
									su.setLastOrderTime("yes");
									platformStockUpdateDao.updateStockByNotNull(su);
									updateNumber++;
									updateSucces = true;
								}
							}
							if(updateSucces)
							{
								//记录该orderid
								PlatFormOrderRecord kaolahtOrder = new PlatFormOrderRecord();
								kaolahtOrder.setOrderId(orderId);
								kaolahtOrder.setIdPlartform(18);
								kaolahtOrder.setIdStatus(1);
								platformStockUpdateDao.insertOrder(kaolahtOrder);
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage(), e);
							System.out.println("請求失敗,考拉海淘");
						}
					}
				}
				
			}
		return updateNumber;
	}

	@Override
	public StockNumber getKaolahtStock(String kaolahtSku, String kaolahtKey,
			String type) {
		StockNumber sn = null;
		if(StringUtil.isBlank(kaolahtSku)) return sn; 
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("kaolahtsku", kaolahtSku);
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		if(list.size() > 0)
		{
			kaolahtKey= list.get(0).getKaolaKey();
		}
		if(StringUtil.isBlank(kaolahtKey)) return sn; 
		String method = "kaola.item.get";
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HT_APP_SECRET);
			map.put("app_key", HT_APP_KEY);
			map.put("access_token", HT_ACCESS_TOKEN);
			
		}
		//应用参数
				String nowTime = sdf.format(new Date());
				map.put("timestamp", nowTime);//调用时间
				map.put("key", kaolahtKey); //
				String sign = getSign(map);
				map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
				//发送POST请求 获取数据
				String response = HttpRequest.sendPost(BASE_URL, map);
				try {
					JSONObject jsonObj = new JSONObject(response);
					JSONObject productObj = new JSONObject(jsonObj.getString("kaola_item_get_response"));
					JSONArray skuArray = productObj.getJSONArray("sku_list");
					for(int index = 0; index < skuArray.length();index++)
					{
						JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
						String kaolahtSkuServer = skuObj.getString("key"); //考拉海淘sku
						if(kaolahtSku.equals(kaolahtSkuServer)) //获取该sku当前的库存
						{
							JSONObject obj = new JSONObject(skuObj.getString("raw_sku"));
							int kaolaCanSaleNum = obj.getInt("stock_can_sale");
							int kaolaFreezeSaleNum = obj.getInt("stock_freeze");
							sn = new StockNumber();
							sn.setPlatformSku(kaolahtSku);
							sn.setCanSaleNum(kaolaCanSaleNum);
							sn.setFreezeNum(kaolaFreezeSaleNum);
							return sn;
							
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
		return sn;
	}

	@Override
	public int updateWaitFoOnSaleSku2Location() {
		int cnt = updateSku2LocationByType("hk","4");
		//更新待上架库存
		updateAllStockByType("hk","4");
		return cnt;
	}

	@Override
	public void updateAllStock() {
		updateAllStockByType("hk","6");  //6, 下架,
		updateAllStockByType("hk","5"); //5是在售
		
	}
	private void updateAllStockByType(String type,String productStatus)
	{
		String method = "kaola.item.batch.status.get";
		int pageNO = 1;	
		int pageSize = 20;
		int totalNum = 0;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HT_APP_SECRET);
			map.put("app_key", HT_APP_KEY);
			map.put("access_token", HT_ACCESS_TOKEN);	 
		}
		Map<String,StockUpdate> canSaleStockMap = new HashMap<String, StockUpdate>(); //key为我们sku  value为当前sku可售库存
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("searchCanSale", "yes");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate su : list)
		{
			if(StringUtil.isNotBlank(su.getKaolahtSku()))
			{
				canSaleStockMap.put(su.getSku(), su);
			}
		}
		while(true)
		{
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("item_edit_status", productStatus); //产品状态 
			map.put("page_no", pageNO+""); //当前页
			map.put("page_size", pageSize+""); //每页条数
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			pageNO++;
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(BASE_URL, map);
			try {
				JSONObject jsonObj = new JSONObject(response);
				JSONObject itemsResponse = new JSONObject(jsonObj.get("kaola_item_batch_status_get_response").toString());
				totalNum = itemsResponse.getInt("total_count");
				if(0 == totalNum) break; //没有数据 中断
				int totalPageSize = (totalNum/pageSize)+1; //总页数
				JSONArray jsonArray = itemsResponse.getJSONArray("item_edit_list");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					JSONArray skuArray = productObj.getJSONArray("sku_list");
					for(int index = 0; index < skuArray.length();index++)
					{
						JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
						String kaolahtSku = skuObj.getString("key"); //考拉海淘sku
						String supplySku = new JSONObject(skuObj.getString("raw_sku")).getString("bar_code"); //供货商SKU
						if(StringUtil.isBlank(supplySku)) continue;
						supplySku = supplySku.trim();
						String[] supplySkuArr = supplySku.split("-");
						if(supplySkuArr.length == 2)
						{
							supplySku = supplySkuArr[1];
						}
						else 
						{
							supplySku = supplySkuArr[0];
						}
						StockUpdate su = canSaleStockMap.get(supplySku); //当前库存
						int nowNum = 0;
						if(su != null){nowNum = su.getNowStockNum()-su.getOrderStockNum();}
						if(nowNum < 0 ) nowNum = 0;
						boolean flag = updateKaoLahtStocku(kaolahtSku, supplySku, nowNum, type); //更新成功
						//更新成功 设置上次更新到平台库存数,防止单个更新的在做更新
						if(flag)
						{
							StockUpdate stockUpdate = new StockUpdate();
							stockUpdate.setType(type);
							stockUpdate.setSku(supplySku);
							stockUpdate.setLastUpdateStockNum(nowNum);
							//platformStockUpdateDao.updateLastUpdateStockBySkuAndTypeForOne(stockUpdate); 暂时不做更新防止其他平台更新不到
						}
					}
				}
				//当前页为最后一页 中断
				if(pageNO > totalPageSize)break;
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if(pageNO > 500)break;
			}
		}
		System.out.println(sdf.format(new Date()) + " kaohtla " + type + " " + productStatus);
	}
	
	
	
	
	/**
	 * 签名规则 见考拉文档
	 * @param map
	 * @return
	 */
	private String getSign(Map<String,String> map)
	{
		String sign = "";
		String needMD5Str = map.get("secret");
		Set<Entry<String, String>> entrySet  =  map.entrySet();
		Iterator<Entry<String,String>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String,String> en = it.next();
			if(en.getKey().equals("secret") || en.getKey().equals("sign")) continue; //密码和签名项不加入签名算法
				needMD5Str += en.getKey() + en.getValue();
		}
		needMD5Str+= map.get("secret");
		sign = HttpRequest.string2MD5(needMD5Str).toUpperCase();
		return sign;
	}

}
