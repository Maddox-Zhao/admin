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
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.OfMiChengPaltFormStockupdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;

@Service("OfMiChengPaltFormStockupdate")
public class OfMiChengPaltFormStockupdateImpl implements OfMiChengPaltFormStockupdate{
	
	private static final String TextUrl = "http://tsupplyapi.ofashion.com.cn:90/boot";
	
	private static final String url 	= "https://opensupplierapi.ofashion.com.cn/boot";
	
	private static final String app_key = "201807170344469100";
	
	private static final String secret 	= "556f4445fc08a781fa02d54777af62ac";
	
	private static final String textapp_key = "201806270000000001";
	
	private static final String textsecret = "950c88c7a2a70743115aa2886ef996b7";
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	private static final String v = "1.0";
	
	private static final String format = "json";
	
	private static  String OFMC_SKU_FILE_NAME= "ofashionMiCheng_sku.txt";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	


     protected Log log = LogFactory.getLog(this.getClass()); 

	@Override
	public void updateSku2Location() {
		MiniUiUtil.writeText2File(OFMC_SKU_FILE_NAME, "", "cx");
		int errorCnt = 1;
		int start = 0;
		int count = 50;
		int total = 0;
		while (true) {
			String times = String.valueOf(System.currentTimeMillis());
			String time = times.substring(0, times.length() - 3);
			long l = Long.parseLong(time);
			String timestamp = String.valueOf(l);
			Map<String, Object> map = new HashMap<String, Object>();		
			Map<String, Object> data = new HashMap<String, Object>();
			map.put("app_key", app_key);
			map.put("v", v);
			map.put("method", "ofashion.product.getList");
			map.put("format", format);
			map.put("timestamp", timestamp);
			data.put("start", start);
			data.put("count", count);
			map.put("data", data);
			String sign = getsign(map, secret);
			map.put("sign", sign);
			ObjectMapper mapper = new ObjectMapper();
			try {
				String response = HttpRequest.ofsendPost(url, mapper.writeValueAsString(map));
				JSONObject jsonobject = new JSONObject(response);
				String code = jsonobject.getString("code");
				String msg = jsonobject.getString("msg");
				if(code.equals("0") && msg.equals("success")){
					String datas = jsonobject.getString("data");
					JSONObject jsonobjects = new JSONObject(datas);
					total = jsonobjects.getInt("total");//商品总数
					int totalPageSize = (int) Math.floor((total/count))+1;
					JSONArray products = jsonobjects.getJSONArray("product_list");
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < products.length(); i++) {
						JSONObject item = new JSONObject(products.get(i).toString());
						String show_status = item.getString("show_status");
							String bar_code = item.getString("bar_code");
							if(!bar_code.equals("")){
							Map<String, Object> maps = new HashMap<String, Object>();		
							Map<String, Object> datae = new HashMap<String, Object>();
							String timess = String.valueOf(System.currentTimeMillis());
							String timese = times.substring(0, timess.length() - 3);
							long ls = Long.parseLong(timese);
							String timestamps = String.valueOf(ls);
							
							maps.put("app_key", app_key);
							maps.put("v", v);
							maps.put("method", "ofashion.product.getDetail");
							maps.put("format", format);
							maps.put("timestamp", timestamps);
							datae.put("bar_code", bar_code);
							maps.put("data", datae);
							String signs = getsign(maps, secret);
							maps.put("sign", signs);
							ObjectMapper mappes = new ObjectMapper();
							try {
								String responses = HttpRequest.ofsendPost(url, mappes.writeValueAsString(maps));
								JSONObject jsonobjectse = new JSONObject(responses);
								String datase = jsonobjectse.getString("data");
								JSONObject jsonobjectses = new JSONObject(datase);
								JSONArray productse = jsonobjectses.getJSONArray("product_sku");
								for (int j = 0; j < productse.length(); j++) {
									JSONObject iteme = new JSONObject(productse.get(j).toString());
									String sku_code = iteme.getString("sku_code");
									if(!sku_code.equals("")||sku_code != null){
										sku_code.trim();
										String oursku="";
										if(sku_code.indexOf("-")>0){
											oursku= sku_code.split("-")[1];
		                				 }else{
		                					 oursku=sku_code;
		                				 }
										sb.append(oursku);
										sb.append(",");
										sb.append("\n");
									}
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
							
							}	
					}
					MiniUiUtil.writeText2File(OFMC_SKU_FILE_NAME, sb.toString(), "zj");
				}
				if(start >= total)break;
				start+=50;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("siku syn sku error " + start+" 页");
				if(errorCnt++ > 5) break; //错误5次 跳出
			}
		}
	}

	@Override
	public boolean updateOFashionStock(String ourSku,
			int quantity, String type) {
		if("hk".equals(type)){
		boolean  flag = true;
		List<String> skulist = MiniUiUtil.readOfanshSku(OFMC_SKU_FILE_NAME);
		for (int i = 0; i < skulist.size(); i++) {
			String ourskus = skulist.get(i);
			if(ourSku.equals(ourskus)){
				String timese = String.valueOf(System.currentTimeMillis());
				String timess = timese.substring(0, timese.length() - 3);
				long ls = Long.parseLong(timess);
				String timestamps = String.valueOf(ls);
				Map<String, Object> maps = new HashMap<String, Object>();
				Map<String, Object> business = new HashMap<String, Object>();
				maps.put("app_key", app_key);
				maps.put("v", v);
				maps.put("method", "ofashion.product.editSkuStock");
				maps.put("format", format);
				maps.put("timestamp", timestamps);
				
				business.put("sku_code", ourSku);
				business.put("sku_stock", quantity);
				
				maps.put("data", business);
				String signs = getsign(maps, secret);
				maps.put("sign", signs);
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> logMap = new HashMap<String, String>();
				String response= "";

				try {
					response = HttpRequest.ofsendPost(url, mapper.writeValueAsString(maps));
					JSONObject jsonobject = new JSONObject(response);
					logMap.put("sku", ourSku);
					logMap.put("psku", "ourSku");
					logMap.put("name", "ofashionmicheng");
					logMap.put("stock", quantity+"");
					logMap.put("location", type);
					logMap.put("type", "success");
					String code = jsonobject.getString("code");
					if("0".equals(code)){
						flag = true;
					}else{
						 String codeMsg = jsonobject.getString("msg");
						 logMap.put("type", "error");
						 logMap.put("error",codeMsg);
					}
					
				} catch (Exception e) {
					logMap.put("type", "error");
					logMap.put("error",response);
					log.error(e.getMessage(), e);
				}
				autoSyncDao.addUpdateLog(logMap);
			}
			
		}
		}
		return false;
		
	}

	@Override
	public void updateAllStock() {
		
		List<String> skulist = MiniUiUtil.readOfanshSku(OFMC_SKU_FILE_NAME);
		for (int i = 0; i < skulist.size(); i++) {
			
			String oursku = skulist.get(i);
			Map<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("type", "hk");
			searchMap.put("sku", oursku);
			List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			for(StockUpdate s : list)
			{
				int num = s.getNowStockNum() - s.getOrderStockNum();
				Map<String,String> map = new HashMap<String, String>();
				if(num < 0) continue;
				updateOFashionStock(s.getSku(), num, s.getType());
			}
		}
			//更新一次休息1秒                         
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				log.error(e.getMessage(), e);
			}
		}


	@Override
	public int atuoSyncOrder() {
		Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		String purchase_status = "1";
		int start = 0;
		int count = 50;
		String createtime = "";
		
		String times = String.valueOf(System.currentTimeMillis());
		String time = times.substring(0, times.length() - 3);
		long l = Long.parseLong(time);
		String timestamp = String.valueOf(l);
		
		Date now_48 = new Date(new Date().getTime() - 48*60*60*1000); //2天内的订单
		String start_time = sdf.format(now_48);
		String over_time = sdf.format(new Date());
		
		Map<String, Object> map = new HashMap<String, Object>();		
		Map<String, Object> data = new HashMap<String, Object>();
		
		map.put("app_key", app_key);
		map.put("v", v);
		map.put("method", "ofashion.purchase.getList");
		map.put("format", format);
		map.put("timestamp", timestamp);
		
		data.put("purchase_status", purchase_status);
		data.put("start_time", start_time);
		data.put("over_time", over_time);
		data.put("start", start);
		data.put("count", count);
		
		map.put("data", data);
		
		String sign = getsign(map, secret);
		map.put("sign", sign);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String response = HttpRequest.ofsendPost(url, mapper.writeValueAsString(map));

//			System.out.println(response);

			JSONObject jsonobject = new JSONObject(response);
			String ret_code = jsonobject.getString("code");
			String msg = jsonobject.getString("msg");
			
			JSONObject datas = new JSONObject(jsonobject.getString("data"));
			if(ret_code.equals("0")&& msg.equals("success")){
				JSONArray itemlists = datas.getJSONArray("purchase_list");
				if(itemlists.length() > 0){
					for (int j = 0; j < itemlists.length(); j++) {
						JSONObject good = new JSONObject(itemlists.get(j).toString());
						String purchase_no = good.getString("purchase_no");
						if(purchase_no != ""){
							String now = String.valueOf(System.currentTimeMillis());
							String timese = now.substring(0, now.length() - 3);
							long le = Long.parseLong(timese);
							String timestamps = String.valueOf(le);
							
							Map<String, Object> maps = new HashMap<String, Object>();		
							Map<String, Object> datalist = new HashMap<String, Object>();
							
							maps.put("app_key", app_key);
							maps.put("v", v);
							maps.put("method", "ofashion.purchase.getDetail");
							maps.put("format", format);
							maps.put("timestamp", timestamps);
							
							datalist.put("purchase_no", purchase_no);
							maps.put("data", datalist);
							
							String signs = getsign(maps, secret);
							maps.put("sign", signs);
							ObjectMapper mappers = new ObjectMapper();
							
							String responses = HttpRequest.ofsendPost(url, mappers.writeValueAsString(maps));
//							System.out.println(responses);

							JSONObject jsonobjects = new JSONObject(responses);
							String code = jsonobjects.getString("code");
							String msgs = jsonobjects.getString("msg");
							JSONObject datase = new JSONObject(jsonobjects.getString("data"));
							String purchaseno = datase.getString("purchase_no");//采购单编号
							createtime = datase.getString("create_time");//采购单创建时间
//							String skuid = datase.getString("sku_code");//我们的SKU
							String recipeints_address = datase.getString("recipeints_address");//收货地址
							if(code.equals("0")&& msgs.equals("success")){
								JSONArray goods_info = datase.getJSONArray("goods_info");
								String name  = "Ofashion迷橙采购";
								for (int i = 0; i < goods_info.length(); i++) {
									JSONObject goods = new JSONObject(goods_info.get(j).toString());
									String sku_code = goods.getString("sku_code");//我们的SKU
									String goods_num  = goods.getString("goods_num");//商品数量
									String goods_name = goods.getString("goods_name");//商品名称
									String color = goods.getString("color");//商品颜色
									String spec = goods.getString("spec");//商品规格
									String goods_price = goods.getString("goods_price");//商品价格
									String pay_price = goods.getString("pay_price");//支付金额
									PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
									platformorderdetails.setIdorder(purchase_no);
									List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
										platformorderdetails.setIdorder(purchaseno);
										platformorderdetails.setPalcedTime(createtime);
										platformorderdetails.setName(name);
										platformorderdetails.setStreetAddress(recipeints_address);
										platformorderdetails.setMerchantSkuId(sku_code);
										platformorderdetails.setQuantity(goods_num);
										platformorderdetails.setProductname(goods_name);
										platformorderdetails.setSize(color);
										platformorderdetails.setSize(spec);
										platformorderdetails.setPrice(goods_price);
										platformorderdetails.setPayprice(pay_price);
										platformorderdetails.setPtype("OfashionMicheng");
										if ((liststatus == null || liststatus.size() == 0)) {
											platformOrderDetailsDao.insertOrder(platformorderdetails);

										} else if (liststatus != null) {
											List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
											if (listst == null || listst.size() == 0) {
												platformOrderDetailsDao.insertOrder(platformorderdetails);
											}
										}
									list.add(purchase_no);
									String msgse = sku_code + "_"+ goods_num;
									if (orderMap.get(purchase_no) == null) {
										List<String> value = new ArrayList<String>();
										value.add(msgse);
										orderMap.put(purchase_no, value);
									} else {
										orderMap.get(purchase_no).add(msgse);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		int updateNumber = 0;
		if(orderMap.size() > 0){
			list.add("-1");
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
						searchMap.put("type", "hk");
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
										ss.setOfashionMcOrderStock(num + ss.getOfashionMcOrderStock());
										ss.setLastOrderTime("yes");
										platformStockUpdateDao.updateStockByNotNull(ss);
										updateSuccess = true;
										updatesh = false;
										updateNumber++;
									}
								}
							}
							if(updatesh){
								su.setOfashionMcOrderStock(num + su.getOfashionMcOrderStock());
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
					oderRecord.setIdPlartform(15);//Ofashion迷橙B2B
					oderRecord.setIdStatus(1);    //已支付的订单
					oderRecord.setOrderTime(createtime);
					platformStockUpdateDao.insertOrder(oderRecord);
				}
			}
		}
		return updateNumber;
	}
	
	private static String getsign(Map<String, Object> params, String appSecret){
		String first = assemble(params);
		return strtoupper(md5(strtoupper(md5(first)) + appSecret));
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
	return prestr;
}
	private static String md5(String str){
		return HttpRequest.string2MD5(str);
		}
	private static String strtoupper(String str){
		return str.toUpperCase();
		}
}
