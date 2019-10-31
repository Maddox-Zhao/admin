package com.huaixuan.network.biz.service.platformstock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.OFashionPlatFormStockUpdate;
import com.huaixuan.network.common.util.Md5Util;
import com.huaixuan.network.common.util.json.JSONArray;
import com.hundsun.common.lang.StringUtil;


@Service("OFashionPlatFormStockUpdate")
public class OFashionPlatFormStockUpdateImpl  implements OFashionPlatFormStockUpdate{
	
	private static final String TextUrl = "http://tmoses.ofashion.com.cn:8020/api";
	
	private static final String textapp_key = "test_20170627184423412";
	
	private static final String textsecret = "42c017dc5701f4225e306aa806f40234";
	
	
	
	private static final String Url = "https://open.ofashion.com.cn/api"; 
	
	private static final String app_key = "184720170712443264";
	
	private static final String secret = "e0b1d42566f20c8c9c542e34b9cd6381";
	
	private static final String v = "1.0";
	
	private static final String format = "json";
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected Log log = LogFactory.getLog(this.getClass());
	@Override
	public void updateSku2Location() {
		int errorCnt = 1;
		int start = 0;
		int count = 50;
		while (true) {
		int product_status = 1;
		String product_id = "";
		String type = "sh";
		String times = String.valueOf(System.currentTimeMillis());
		
		String time = times.substring(0, times.length() - 3);
		long l = Long.parseLong(time);
		String timestamp = String.valueOf(l);
//		System.out.println("当前时间戳"+timestamp);
//		String tt= sdf.format(l*1000L);
//		System.out.println(tt+"----->当前时间");
		Map<String, Object> map = new HashMap<String, Object>();		
		Map<String, Object> data = new HashMap<String, Object>();
		map.put("app_key", app_key);
		map.put("v", v);
		map.put("method", "ofashion.goods.getList");
		map.put("format", format);
		map.put("timestamp", timestamp);
		data.put("product_status", product_status);
		data.put("start", start);
		data.put("count", count);
		
		map.put("data", data);
		String sign = getsign(map, secret);
		map.put("sign", sign);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String response = HttpRequest.ofsendPost(Url, mapper.writeValueAsString(map));
//			System.out.println(response);
			com.huaixuan.network.common.util.json.JSONObject jsonObj = new  com.huaixuan.network.common.util.json.JSONObject(response);
//			if(jsonObj.getString("ret_code").equals("200")){
				
			
			String datas = jsonObj.getString("data");
			com.huaixuan.network.common.util.json.JSONObject jsonobject = new com.huaixuan.network.common.util.json.JSONObject(datas);
			int total = jsonobject.getInt("total");//商品总数
			int totalPageSize = (int) Math.floor((total/count))+1;
			JSONArray products = jsonobject.getJSONArray("list");
//			System.out.println(products.length());
			
			for (int i = 0; i < products.length(); i++) {
				com.huaixuan.network.common.util.json.JSONObject item = new com.huaixuan.network.common.util.json.JSONObject(products.get(i).toString());
				product_id = item.getString("product_id");
				String product_code = item.getString("product_code");//我们自己的商品编码（并不是SKU）
				if(!product_id.equals("")){
					String timese = String.valueOf(System.currentTimeMillis());
					String timess = timese.substring(0, timese.length() - 3);
					long ls = Long.parseLong(timess);
					String timestamps = String.valueOf(ls);
//					System.out.println("当前时间戳"+timestamps);
					Map<String, Object> maps = new HashMap<String, Object>();		
					Map<String, Object> business = new HashMap<String, Object>();
					maps.put("app_key", app_key);
					maps.put("v", v);
					maps.put("method", "ofashion.sku.get");
					maps.put("format", format);
					maps.put("timestamp", timestamps);
					business.put("product_id", product_id);
					maps.put("data", business);
					String signs = getsign(maps, secret);
					maps.put("sign", signs);
					ObjectMapper mappers = new ObjectMapper();
					String responses = HttpRequest.ofsendPost(Url, mappers.writeValueAsString(maps));
//					System.out.println(responses);
					com.huaixuan.network.common.util.json.JSONObject jsonboject = new com.huaixuan.network.common.util.json.JSONObject(responses);
					String ret_code = jsonboject.getString("ret_code");
					if(ret_code.equals("200")){
					String datalist = jsonboject.getString("data");
					com.huaixuan.network.common.util.json.JSONObject jsondata = new com.huaixuan.network.common.util.json.JSONObject(datalist);
					JSONArray sku_info = jsondata.getJSONArray("sku_info");
					if(sku_info.length()>0){
					List<PlatformSku2LocationSku> ofashionskuList = new ArrayList<PlatformSku2LocationSku>();
					for (int j = 0; j < sku_info.length(); j++) {
						com.huaixuan.network.common.util.json.JSONObject items = new com.huaixuan.network.common.util.json.JSONObject(sku_info.get(j).toString());
						String ofashionSku = items.getString("sku_id");
						String ourSku = items.getString("sku_code");
						if(ourSku != null){
							ourSku = ourSku.trim();
						}
						if(StringUtil.isNotBlank(ofashionSku) && StringUtil.isNotBlank(ourSku)){
							PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
							pslsku.setOurSku(ourSku);
							pslsku.setPlatformField("ofashion_sku");
							pslsku.setPlatformSku(ofashionSku);
							pslsku.setType(type);
							pslsku.setProduct_id(product_id);
							ofashionskuList.add(pslsku);
						}
					}
				
					Map ofashionSkuMap = new HashMap();
					ofashionSkuMap.put("list", ofashionskuList);
					ofashionSkuMap.put("platformField", "ofashion_sku");
					ofashionSkuMap.put("product_id", "true");
					if(ofashionskuList.size() > 0){
						platformStockUpdateDao.batchUpdatePlatformSku2Location(ofashionSkuMap);
					}
				}
					}
			}
			}
			if(start >= total)break;
			start+=50;
//			}else{
//				break;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			System.out.println("siku syn sku error " + start+" 页");
			if(errorCnt++ > 5) break; //错误5次 跳出
		}
		}
	}

	@Override
	public boolean updateOFashionStock(String ofSku, String ourSku,
			int quantity, String type) {
		String product_id = "";
		if(StringUtil.isBlank(ofSku) && StringUtil.isBlank(ourSku)){
			return true;
		}
		boolean  flag = true;
		Map<String, String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku)){
			searchMap.put("type", type);
			searchMap.put("ofashionsku", ofSku);
			List<StockUpdate> list= platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0){
				ourSku = list.get(0).getSku();
				product_id = list.get(0).getProduct_id();
			}
		}else if(StringUtil.isBlank(ofSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "ofashion_sku");
			ofSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isNotBlank(ourSku)){
			searchMap.put("type", type);
			searchMap.put("ofashionsku", ofSku);
			List<StockUpdate> list= platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0){
				ourSku = list.get(0).getSku();
				product_id = list.get(0).getProduct_id();
			}
		}
		if(StringUtil.isBlank(ofSku) || StringUtil.isBlank(product_id)){
			return true;
			}
	
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> business = new HashMap<String, Object>();
		String timese = String.valueOf(System.currentTimeMillis());
		String timess = timese.substring(0, timese.length() - 3);
		long ls = Long.parseLong(timess);
		String timestamps = String.valueOf(ls);
//		System.out.println("当前时间戳"+timestamps);
		Map<String, Object> maps = new HashMap<String, Object>();		
		Map<String, Object> data = new HashMap<String, Object>();
		maps.put("app_key", app_key);
		maps.put("v", v);
		maps.put("method", "ofashion.sku.stock.update");
		maps.put("format", format);
		maps.put("timestamp", timestamps);
		
		business.put("product_id", product_id);
		business.put("sku_code", ourSku);
		business.put("stock", quantity);
		
		maps.put("data", business);
		String signs = getsign(maps, secret);
		maps.put("sign", signs);
		ObjectMapper mappers = new ObjectMapper();
		Map<String, String> logMap = new HashMap<String, String>();
		String responses = "";
		try {
			responses = HttpRequest.ofsendPost(Url, mappers.writeValueAsString(maps));
			logMap.put("sku", ourSku);
			logMap.put("psku", ofSku);
			logMap.put("name", "ofashion");
			logMap.put("stock", quantity+"");
			logMap.put("location", type);
			logMap.put("type", "success");
			com.huaixuan.network.common.util.json.JSONObject jsonObject = new com.huaixuan.network.common.util.json.JSONObject(responses);
			if("200".equals(jsonObject.getString("ret_code")))
			{
				//更新成功
				flag = true;
			}else{
				 String codeMsg = jsonObject.getString("msg");
				 logMap.put("type", "error");
				 logMap.put("error",codeMsg);
			}
		} catch (Exception e) {
			logMap.put("type", "error");
			logMap.put("error",responses);
			log.error(e.getMessage(), e);
		}
		
		autoSyncDao.addUpdateLog(logMap);
		return false;
	}

	@Override
	public void updateAllStock() {
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", "sh");
		searchMap.put("skuisnotnull", "ofashion_sku");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate s : list)
		{
			int num = s.getNowStockNum() - s.getOrderStockNum();
			Map<String,String> map = new HashMap<String, String>();
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
			updateOFashionStock(s.getOfashionsku(), s.getSku(), num, s.getType());
			//更新一次休息5秒
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				log.error(e.getMessage(), e);
			}
		}
		
	}

	@Override
	public int atuoSyncOrder() {
		Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		int trade_status = 2;//待付款
		int start = 0;
		int count = 50;
		String createtime = "";
		String times = String.valueOf(System.currentTimeMillis());
		String time = times.substring(0, times.length() - 3);
		long l = Long.parseLong(time);
		String timestamp = String.valueOf(l);
		
		Date now_48 = new Date(new Date().getTime() - 48*60*60*1000); //2天内的订单
		String start_time = sdf.format(now_48);
		String end_time = sdf.format(new Date());
		Map<String, Object> map = new HashMap<String, Object>();		
		Map<String, Object> data = new HashMap<String, Object>();
		map.put("app_key", app_key);
		map.put("v", v);
		map.put("method", "ofashion.order.getList");
		map.put("format", format);
		map.put("timestamp", timestamp);
		
		data.put("trade_status", trade_status);
		data.put("start_time", start_time);
		data.put("end_time", end_time);
		data.put("start", start);
		data.put("count", count);
		
		map.put("data", data);
		String sign = getsign(map, secret);
		map.put("sign", sign);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String response = HttpRequest.ofsendPost(Url, mapper.writeValueAsString(map));
//			System.out.println("ofashion"+response);
			com.huaixuan.network.common.util.json.JSONObject jsonObject = new com.huaixuan.network.common.util.json.JSONObject(response);
			com.huaixuan.network.common.util.json.JSONObject datae = new com.huaixuan.network.common.util.json.JSONObject(jsonObject.getString("data"));
			JSONArray itemlists = datae.getJSONArray("list");
			if(itemlists.length() > 0){
			for (int j = 0; j < itemlists.length(); j++) {
				com.huaixuan.network.common.util.json.JSONObject good = new com.huaixuan.network.common.util.json.JSONObject(itemlists.get(j).toString());
				String tradeno = good.getString("trade_no");
				if(tradeno != ""){
					String now = String.valueOf(System.currentTimeMillis());
					String timese = now.substring(0, now.length() - 3);
					long le = Long.parseLong(timese);
					String timestamps = String.valueOf(le);
					
					Map<String, Object> maps = new HashMap<String, Object>();		
					Map<String, Object> datalist = new HashMap<String, Object>();
					maps.put("app_key", app_key);
					maps.put("v", v);
					maps.put("method", "ofashion.order.getDetail");
					maps.put("format", format);
					maps.put("timestamp", timestamps);
					
					datalist.put("trade_no", tradeno);
					
					maps.put("data", datalist);
					String signs = getsign(maps, secret);
					maps.put("sign", signs);
					ObjectMapper mappers = new ObjectMapper();
					String responses = HttpRequest.ofsendPost(Url, mappers.writeValueAsString(maps));
//					System.out.println(responses);
					com.huaixuan.network.common.util.json.JSONObject jsonObjects = new com.huaixuan.network.common.util.json.JSONObject(responses);
					com.huaixuan.network.common.util.json.JSONObject dataes = new com.huaixuan.network.common.util.json.JSONObject(jsonObjects.getString("data"));
					createtime = dataes.getString("create_time");//下单时间
					String pay_time = dataes.getString("pay_time");//支付时间
					if(pay_time.equals("")){
						pay_time = "00-00-00 00:00:00";
					}
					String name = dataes.getString("consignee");//收件人
					String phone = dataes.getString("mobilephone");//收件人电话
					String address = dataes.getString("address");//收件人地址
					String code = dataes .getString("zip_code");//邮编
					String totalPrice = dataes.getString("trade_price");//订单总金额
					String pay_price = dataes.getString("pay_price");//实付金额
					String discountPrice = dataes.getString("coupon_price");//优惠金额
					String freight = dataes.getString("shipping_rate");//邮费
					String merchantSkuId = dataes.getString("sku_code");//我们的SKU
					String skuId = dataes.getString("product_id");//平台SKU
					String quantity = dataes.getString("product_num");//商品数量
					
					PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
					platformorderdetails.setIdorder(tradeno);
					List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
					platformorderdetails.setIdorder(tradeno);
					platformorderdetails.setPalcedTime(createtime);
					platformorderdetails.setPayTime(pay_time);
					platformorderdetails.setName(name);
					platformorderdetails.setMobile(phone);
					platformorderdetails.setStreetAddress(address);
					platformorderdetails.setZipCode(code);
					platformorderdetails.setTotalPrice(totalPrice);
					platformorderdetails.setPayprice(pay_price);
					platformorderdetails.setDiscountPrice(discountPrice);
					platformorderdetails.setFreight(freight);
					platformorderdetails.setMerchantSkuId(merchantSkuId);
					platformorderdetails.setSkuId(skuId);
					platformorderdetails.setQuantity(quantity);
					platformorderdetails.setPtype("Ofashion");
					if ((liststatus == null || liststatus.size() == 0)) {
						platformOrderDetailsDao.insertOrder(platformorderdetails);

					} else if (liststatus != null) {
						List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
						if (listst == null || listst.size() == 0) {
							platformOrderDetailsDao.insertOrder(platformorderdetails);
						}
					}
					list.add(tradeno);
					String msg = merchantSkuId + "_"+ quantity;
					if (orderMap.get(tradeno) == null) {
						List<String> value = new ArrayList<String>();
						value.add(msg);
						orderMap.put(tradeno, value);
					} else {
						orderMap.get(tradeno).add(msg);
					}
					
				}
			}
		}
		} catch (Exception e) {
//			e.printStackTrace();
			log.error(e.getMessage(), e);
			System.out.println("ofashion获取订单失败");
		}
		int updateNumber = 0;
		if(orderMap.size() > 0){
			list.add("-1");//避免没有数据  报错
			List<PlatFormOrderRecord>  orderList = platformStockUpdateDao.getOrdersByOrders(list);
			Map<String,String> orderMapRealy = new HashMap<String, String>();
			for(PlatFormOrderRecord k : orderList){
				orderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Entry<String,List<String>>> it = set.iterator();
			while(it.hasNext()){
				Entry<String,List<String>> entry = it.next();
				String orderId  = entry.getKey();
				List<String> value = entry.getValue();
				boolean updateSuccess = false;
				if(orderMapRealy.get(orderId) == null){
					for(String v : value){
						String[] arr = v.split("_");
						String sku = arr[0];
						int num = Integer.valueOf(arr[1]);
						Map<String,String> searchMap = new HashMap<String, String>();
						searchMap.put("sku", sku);
						searchMap.put("type", "sh");
						List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						for(StockUpdate su : stockUpdateList){
							Map<String,String> searchMaps = new HashMap<String, String>();
							searchMaps.put("sku", sku);
							searchMaps.put("type", "hk");
							boolean updatesh = true;
							List<StockUpdate>  stockUpdateLists = platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
							if(stockUpdateLists != null && stockUpdateLists.size() > 0){
								int  s = su.getNowStockNum()-su.getOrderStockNum();
								if(s <=0 ){
										for(StockUpdate ss : stockUpdateLists){
											ss.setOfashionOrderStock(num + ss.getOfashionOrderStock());
											ss.setLastOrderTime("yes");
											platformStockUpdateDao.updateStockByNotNull(ss);
											updateSuccess = true;
											updatesh = false;
											updateNumber++;
										}
									}
								}
							if(updatesh){
							su.setOfashionOrderStock(num + su.getOfashionOrderStock());
							su.setLastOrderTime("yes");
							platformStockUpdateDao.updateStockByNotNull(su);
							updateSuccess = true;
							updateNumber++;
							}
						}
					}
				}
				if(updateSuccess){
					PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
					oderRecord.setOrderId(orderId);
					oderRecord.setIdPlartform(11);//Ofashion
					oderRecord.setIdStatus(1);    //已支付的订单
					oderRecord.setOrderTime(createtime);
					platformStockUpdateDao.insertOrder(oderRecord);
				}
			}
		}
		return updateNumber;
	}
	
		private static String assemble(Object _params){
			if (!(_params instanceof Map)){
				return _params.toString();
			}
		Map<String, Object> params = (Map<String, Object>) _params;
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		
		for (int i = 0; i < keys.size(); i++){
			String key = keys.get(i);
			prestr += key + assemble(params.get(key));
			
		}
//		System.out.println("即将进行MD5加密的参数"+prestr);
		return prestr;
	}
		private static String getsign(Map<String, Object> params, String appSecret){
		String first = assemble(params);
		return strtoupper(md5(strtoupper(md5(first)) + appSecret));
		}
		
		
		private static String md5(String str){
		return HttpRequest.string2MD5(str);
		}
		private static String strtoupper(String str){
		return str.toUpperCase();
		}
}
