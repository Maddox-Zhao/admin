
package com.huaixuan.network.biz.service.platformstock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.XiaohongshuPlatFormStockupdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;

@Service("xiaohongshuPlatFormStockupdate")
public class XiaohongshuPlatFormStockupdateImpl implements
		XiaohongshuPlatFormStockupdate {

	@Autowired
	private AutoSyncDao autoSyncDao;
    
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	//正式环境
	 private static final String APPKEY_HK = "db42f0b603";
	 private static final String SECRET_HK =
	 "d636b02a1560210b619e55418162b7c8";

	 private static final String APPKEY_SH = "64dc37f480";
	 private static final String SECRET_SH =
	 "5505de51de92aa36990046f0f80984f5";

	 
	 //测试环境
//	private static final String APPKEY_HK = "496eb3b839";
//	private static final String SECRET_HK = "bc9faff227955139f01eb5010745adbd";
//
//	private static final String APPKEY_SH = "0121690f05";
//	private static final String SECRET_SH = "a0fe677b8b08ce193021cf6f66efe49c";

	//private static final String url = "http://flssandbox.xiaohongshu.com/ark/open_api/v1/items?";
	
	private static final String url = "https://ark.xiaohongshu.com/ark/open_api/v1/items?";

	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;

	@Override
	public void updateSku2Location() {
		platformStockUpdateDao.updateStockUpdateSku2Null("xhs_on_sale_status");
		//true-上架状态,false-下架状态
		updateSku2LocationBytype(APPKEY_SH, SECRET_SH,true);  //最多只能获取到10000条（国内）,国内总条数不到10000，无需补充
		updateSku2LocationBytype(APPKEY_SH, SECRET_SH,false); //下架状态（国内）
		
		updateSku2LocationBytype(APPKEY_HK,SECRET_HK,true);    //最多只能获取到10000条 （海外）上架状态
		updateSku2LocationBytype(APPKEY_HK,SECRET_HK,false);    //下架状态，下架状态的条数没有超过10000条，不用补充（海外）
		
		updateSku2LocationBytypeTime(APPKEY_HK,SECRET_HK,true); //对只能获取到10000条的补充（海外）
	}

	public void updateSku2LocationBytype(String appKey, String secret,boolean buyable) {
		int pageno = 1;
		int pagesize = 50;
		boolean b = buyable;
		String s = String.valueOf(b);
		String method = "/ark/open_api/v1/items?";
		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);
		TreeMap<String, String> map = new TreeMap<String, String>();
		int errorCnt = 0;
		while (true) {		
			map.put("status", "2");
			map.put("page_no", pageno + "");
			map.put("page_size", pagesize + "");
			map.put("buyable", s);
			String sign = getsign(map, secret, method, timestamp, appKey);

			String response = HttpRequest.sendGet(url, map, sign, timestamp,appKey);
//			System.out.println(response);
			try {
				JSONObject jsonObj = new JSONObject(response);
				String data = jsonObj.getString("data");
				if(!data.equals("")||!data.equals("null")){
				JSONObject obj = new JSONObject(data);
				int total = obj.getInt("total");
				int totalPageSize = (int) Math.floor((total / pagesize)) + 1;
				JSONArray items = obj.getJSONArray("hits");
				List<PlatformSku2LocationSku> pddskuList = new ArrayList<PlatformSku2LocationSku>();
				for (int i = 0; i < items.length(); i++) {
					JSONObject item = new JSONObject(items.get(i).toString());
					String pro = item.getString("item");
					JSONObject p = new JSONObject(pro);
					String ourSku = p.getString("barcode");
					String xhsSku = p.getString("skucode");
					String buyab = p.getString("buyable");
					String itemid = p.getString("id");
					if (StringUtil.isNotBlank(ourSku)) {
						ourSku = ourSku.trim();
					}
					if (StringUtil.isNotBlank(buyab)) {
						buyab = buyab.trim();
					}
					String status = "";
					if(buyab.equals("true")){
						status ="1";
					}else{
						status ="0";
						
					}
					if (StringUtil.isNotBlank(xhsSku)
							&& StringUtil.isNotBlank(ourSku) && StringUtil.isNotBlank(status)) {
						PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
						pslsku.setOurSku(ourSku);
						pslsku.setPlatformField("xhs_sku");
						pslsku.setPlatformSku(xhsSku);
						pslsku.setItemid(itemid);
						pslsku.setPlatformstatus("xhs_on_sale_status");
						pslsku.setStatus(status);
						if (appKey == APPKEY_SH) {
							pslsku.setType("sh");
						} else {
							pslsku.setType("hk");
						}

						pddskuList.add(pslsku);
					}
				}
				Map xhsSkuMap = new HashMap();
				xhsSkuMap.put("list", pddskuList);
				xhsSkuMap.put("platformField", "xhs_sku");
				xhsSkuMap.put("xhsitem_id", "true");
				xhsSkuMap.put("platformstatus", "xhs_on_sale_status");
				if (pddskuList.size() > 0) {
					platformStockUpdateDao.batchUpdatePlatformSku2Location(xhsSkuMap);
				}
				if (pageno >= totalPageSize)
					break;
				pageno++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("xiaohongshu syn sku error " + errorCnt);
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("xiaohongshu syn sku error " + errorCnt);
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}
		}
	}
	
	
	//获取系统32天内修改的商品的sku
	public void updateSku2LocationBytypeTime(String appKey, String secret,boolean buyable) {
		int pageno = 1;
		int pagesize = 50;
		boolean b = buyable;
		String s = String.valueOf(b);
		String method = "/ark/open_api/v1/items?";
		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);
		TreeMap<String, String> map = new TreeMap<String, String>();
		Long curren = System.currentTimeMillis();
		curren = curren-24*24*60*60*1000-8*24*60*60*1000; 
		String bf = String.valueOf(curren);
		String beforetime = bf.substring(0, bf.length() - 3);
		int errorCnt = 0;
		while (true) {	
			
			map.put("status", "2");
			map.put("page_no", pageno + "");
			map.put("page_size", pagesize + "");
			map.put("buyable", s);
			map.put("update_time_from", beforetime);
			map.put("update_time_to", timestamp);
			
			String sign = getsignTime(map, secret, method, timestamp, appKey, beforetime);
			String response = HttpRequest.sendGet(url, map, sign, timestamp,appKey);
//			System.out.println(response);
			try {
				JSONObject jsonObj = new JSONObject(response);
				String data = jsonObj.getString("data");
				if(!data.equals("")||!data.equals("null")){
				JSONObject obj = new JSONObject(data);
				int total = obj.getInt("total");
				int totalPageSize = (int) Math.floor((total / pagesize)) + 1;
				JSONArray items = obj.getJSONArray("hits");
				List<PlatformSku2LocationSku> pddskuList = new ArrayList<PlatformSku2LocationSku>();
				for (int i = 0; i < items.length(); i++) {
					JSONObject item = new JSONObject(items.get(i).toString());
					String pro = item.getString("item");
					JSONObject p = new JSONObject(pro);
					String ourSku = p.getString("barcode");
					String xhsSku = p.getString("skucode");
					String buyab = p.getString("buyable");
					String itemid = p.getString("id");
					if (StringUtil.isNotBlank(ourSku)) {
						ourSku = ourSku.trim();
					}
					if (StringUtil.isNotBlank(buyab)) {
						buyab = buyab.trim();
					}
					String status = "";
					if(buyab.equals("true")){
						status ="1";
					}else{
						status ="0";
						
					}
					if (StringUtil.isNotBlank(xhsSku)
							&& StringUtil.isNotBlank(ourSku) && StringUtil.isNotBlank(status)) {
						PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
						pslsku.setOurSku(ourSku);
						pslsku.setPlatformField("xhs_sku");
						pslsku.setPlatformSku(xhsSku);
						pslsku.setItemid(itemid);
						pslsku.setPlatformstatus("xhs_on_sale_status");
						pslsku.setStatus(status);
						if (appKey == APPKEY_SH) {
							pslsku.setType("sh");
						} else {
							pslsku.setType("hk");
						}

						pddskuList.add(pslsku);
					}
				}
				Map xhsSkuMap = new HashMap();
				xhsSkuMap.put("list", pddskuList);
				xhsSkuMap.put("platformField", "xhs_sku");
				xhsSkuMap.put("xhsitem_id", "true");
				xhsSkuMap.put("platformstatus", "xhs_on_sale_status");
				if (pddskuList.size() > 0) {
					platformStockUpdateDao.batchUpdatePlatformSku2Location(xhsSkuMap);
				}
				if (pageno >= totalPageSize)
					break;
				pageno++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("xiaohongshu syn sku error " + errorCnt);
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("xiaohongshu syn sku error " + errorCnt);
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}
		}
	}
	
	
	
	
	
	
	
	

	@Override
	public void updateAllStock() {
		updateCanSaleProductByType("hk",APPKEY_HK,SECRET_HK);

		updateCanSaleProductByType("sh", APPKEY_SH, SECRET_SH);
	}

	private void updateCanSaleProductByType(String type, String appKey,
			String secret) {
//		String selecturl = "http://flssandbox.xiaohongshu.com";
		Map<String, String> searchMap = new HashMap<String, String>();
		TreeMap<String, String> map = new TreeMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("skuisnotnull", "xhs_sku");
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		if(list.size() > 0){
		for (StockUpdate s : list) {
				String ourSku = s.getSku();
				int num = s.getNowStockNum() - s.getOrderStockNum();
				if (num < 0)
					num = 0;
				String times = String.valueOf(System.currentTimeMillis());
				String timestamp = times.substring(0, times.length() - 3);
				String itemid = s.getXhsitemId();
				if (StringUtil.isNotBlank(itemid)) {
					String method = "/ark/open_api/v0/items/" + itemid
							+ "/stock";
					String sign = getsign(map, secret, method, timestamp,
							appKey);
					String selecturl = "https://ark.xiaohongshu.com";
					selecturl += method;
					String response = HttpRequest.sendGet(selecturl, map, sign,
							timestamp, appKey);
					try {
						JSONObject jsonObject = new JSONObject(response);
						String qty = jsonObject.getString("data");
						if(!qty.equals("null")){
						int number = Integer.parseInt(qty);
						if (number > num || number < num) {
							updateCanSaleProduct(s.getXhsSku(),ourSku, num, type);
						}
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(500);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
		}
	}
	}

	private static String getsign(Map<String, String> map, String secret,
			String method, String timestamp, String appkey) {
		String sign = "";
		Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			sign += en.getKey() + "=" + en.getValue();
			if (it.hasNext())
				sign += "&";
		}
		if (sign == "") {
			sign = method + "?" + "app-key" + "=" + appkey + "&timestamp" + "="
					+ timestamp + secret;
		} else {
			sign = method + "app-key" + "=" + appkey + "&" + sign
					+ "&timestamp" + "=" + timestamp + secret;
		}
		sign = HttpRequest.string2MD5(sign);
		return sign;
	}
	
	
	
	private static String getsignTime(Map<String, String> map, String secret,
			String method, String timestamp, String appkey,String beforetime) {
		String sign = "";
		Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if(en.getKey().equals("update_time_from") ||en.getKey().equals("update_time_to")){
				continue;
			}
			sign += en.getKey() + "=" + en.getValue();
			if (it.hasNext())
				sign += "&";
		}
		if (sign == "") {
			sign = method + "?" + "app-key" + "=" + appkey + "&timestamp" + "="
					+ timestamp + secret;
		} else {
			sign = method + "app-key" + "=" + appkey + "&" + sign
					+ "timestamp" + "=" + timestamp +"&update_time_from="+beforetime+"&update_time_to="+timestamp+ secret;
		}
		sign = HttpRequest.string2MD5(sign);
		return sign;
	}
	
	

	@Override
	public boolean updateCanSaleProduct(String xhsSku,String ourSku, int quantity, String type) {
//		String selecturl = "http://flssandbox.xiaohongshu.com";
		if(StringUtil.isBlank(xhsSku) && StringUtil.isBlank(ourSku)) return true;
		Map<String, String> searchMaps = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMaps.put("type", type);
			searchMaps.put("xhsSku", xhsSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(xhsSku))
		{
			searchMaps.put("sku", ourSku);
			searchMaps.put("type", type);
			searchMaps.put("field", "xhs_sku");
			xhsSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMaps);
		}
		if(StringUtil.isBlank(xhsSku)) return true;
		
		String selecturl = "https://ark.xiaohongshu.com";
		TreeMap<String, String> map = new TreeMap<String, String>();
		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);
		String appKey = "";
		String secret = "";

		if (type.equals("sh")) {
			appKey = APPKEY_SH;
			secret = SECRET_SH;
		} else if (type.equals("hk")) {
			appKey = APPKEY_HK;
			secret = SECRET_HK;
		}

		String method = "/ark/open_api/v0/inventories/" + ourSku;
		String sign = getsign(map, secret, method, timestamp, appKey);
		selecturl += method;
		String response = HttpRequest.doPut(selecturl, map, sign, timestamp,
				appKey, quantity);
		
		Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", xhsSku);
			logMap.put("name", "xhs");
			logMap.put("stock", quantity + "");
			logMap.put("location", type);
			logMap.put("type", "success");
			boolean flag = false;
		try {
			JSONObject jsonObject = new JSONObject(response);
			String success = jsonObject.getString("success");
			flag = true;
		} catch (Exception e) {
			logMap.put("type", "error");
			logMap.put("error",response);
			log.error(e.getMessage(), e);
		}
		autoSyncDao.addUpdateLog(logMap);
		return flag;
	}

	@Override
	public int atuoSyncOrder(String type) {
		String appKey = "";
		String secret = "";
		String sku = "";
		int q = 0;
		int updateNumber = 0;
		String createtime = "";
		String palcedTime = "";
//		String selecturl = "http://flssandbox.xiaohongshu.com";
		String selecturl = "https://ark.xiaohongshu.com";
		String method = "/ark/open_api/v0/packages/latest_packages?";

		Long curren = System.currentTimeMillis();
		curren -= 29 * 60 * 1000;
		String bf = String.valueOf(curren);
		String beforetime = bf.substring(0, bf.length() - 3);// 系统当前时间的半小时之前（时间戳）

		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);// 系统当前时间（时间戳）
		TreeMap<String, String> map = new TreeMap<String, String>();

		map.put("order_time_from", beforetime);
		map.put("order_time_to", timestamp);

		if (type == "sh") {
			appKey = APPKEY_SH;
			secret = SECRET_SH;
		} else if (type == "hk") {
			appKey = APPKEY_HK;
			secret = SECRET_HK;
		}
		String sign = getsign(map, secret, method, timestamp, appKey);
		selecturl += method;
		String response = HttpRequest.sendGet(selecturl, map, sign, timestamp,
				appKey);
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject data = new JSONObject(jsonObject.getString("data"));
			if (data != null) {
				JSONArray packagelist = data.getJSONArray("packages");
				for (int index = 0; index < packagelist.length(); index++) {
					JSONObject orderInfo = new JSONObject(packagelist.get(index).toString());
					String 	orderId = orderInfo.getString("package_id");
					String  status = orderInfo.getString("status");

					     // String orderId="P496824419724460131";
					    // String status="canceled";

					Long ordertime = orderInfo.getLong("time");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String ordertimes = format.format(ordertime * 1000L);// 订单创建时间

					JSONArray itemlist = orderInfo.getJSONArray("item_list");
					for (int inde = 0; inde < itemlist.length(); inde++) {
						JSONObject item = new JSONObject(itemlist.get(inde).toString());
						sku = item.getString("skucode");
						String t = item.getString("qty");
						if(t.equals("null")){
							q++;
						}else{
							q = Integer.parseInt(t);
						}
					}
					if (status.equals("confirmed")) {
						List<String> list = new ArrayList<String>();
						Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
						list.add(orderId);
						for (int i = 0; i < list.size(); i++) {
//							String selecturls = "http://flssandbox.xiaohongshu.com";
							String selecturls = "https://ark.xiaohongshu.com";
							String id = list.get(i);
							String methodss = "/ark/open_api/v0/packages/" + id;
							String time = String.valueOf(System.currentTimeMillis());
							String timestamps = time.substring(0,time.length() - 3);
							TreeMap<String, String> maps = new TreeMap<String, String>();
							String signs = getsign(maps, secret, methodss,timestamps, appKey);
							selecturls += methodss;
							String responses = HttpRequest.sendGet(selecturls,maps, signs, timestamps, appKey);
							try {
								JSONObject jsonObjects = new JSONObject(responses);
								JSONObject datas = new JSONObject(jsonObjects.getString("data"));
								if (datas != null) {
									Long usertime = datas.getLong("time");
									Long confirmtime = datas.getLong("confirm_time");
									palcedTime = format.format(usertime * 1000L);// 客户下单时间
									createtime = format.format(confirmtime * 1000L);// 订单确认时间
									
									Long paytime  = datas.getLong("pay_time");
									String payTime = format.format(paytime * 1000L);//用户支付时间
									String receivername = datas.getString("receiver_name");// 收件人姓名
									String receiverphone = datas.getString("receiver_phone");// 收件人电话
									String receiveraddress = datas.getString("receiver_address");// 收件人详细地址
									String province = datas.getString("province");// 省
									String citye = datas.getString("city");// 市
									String district = datas.getString("district");// 区
									String payamount = datas.getString("pay_amount");// 订单总价
//									String payprice = datas.getString("pay_price");// 实际支付价格
									JSONArray itemlists = datas.getJSONArray("item_list");
									for (int j = 0; j < itemlists.length(); j++) {
										JSONObject good = new JSONObject(itemlists.get(j).toString());
										String merchantSkuId = good.getString("barcode");// 我们的SKU
										String productname = good.getString("item_name");// 商品名称
//										if(productname!=null && productname.length()>45){
//											productname = productname.substring(0, 45);
//										}
//										String size = good.getString("specification");// 商品规格   //這個字段被小紅書去掉了
										int quantity = Integer.parseInt(good.getString("qty"));// 商品数量
										String price = good.getString("price");//商品售价
										
										//获取商品属性，保存数据到订单详情表
										PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
										platformorderdetails.setIdorder(orderId);
										List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
										platformorderdetails.setIdorder(orderId);
										platformorderdetails.setPalcedTime(palcedTime);
										platformorderdetails.setPayTime(payTime);
										platformorderdetails.setName(receivername);
										platformorderdetails.setMobile(receiverphone);
										platformorderdetails.setProvince(province);
										platformorderdetails.setCity(citye);
										platformorderdetails.setDistrict(district);
										platformorderdetails.setStreetAddress(receiveraddress);
										platformorderdetails.setTotalPrice(payamount);
//										platformorderdetails.setPayprice(payprice);
										platformorderdetails.setSkuId(sku);
										platformorderdetails.setMerchantSkuId(merchantSkuId);
										platformorderdetails.setQuantity(quantity+"");
										platformorderdetails.setProductname(productname);
										platformorderdetails.setSize("无");
										platformorderdetails.setPrice(price);
										platformorderdetails.setPtype("xhs");
										if ((liststatus == null || liststatus.size() == 0)) {
											platformOrderDetailsDao.insertOrder(platformorderdetails);

										} else if (liststatus != null) {
											List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
											if (listst == null || listst.size() == 0) {
												platformOrderDetailsDao.insertOrder(platformorderdetails);
											}
										}
										String msg = merchantSkuId + "_"+ quantity;
										
										if (orderMap.get(orderId) == null) {
											List<String> value = new ArrayList<String>();
											value.add(msg);
											orderMap.put(orderId, value);
										} else {
											orderMap.get(orderId).add(msg);
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								log.error(e.getMessage(), e);
							}

						}
						if (orderMap.size() > 0) {
							list.add("-1");
							List<PlatFormOrderRecord> orderList = platformStockUpdateDao.getOrdersByOrders(list);
							Map<String, String> orderMapRealy = new HashMap<String, String>();
							for (PlatFormOrderRecord k : orderList) {
								orderMapRealy.put(k.getOrderId(), "yes");
								orderMapRealy.put(k.getStatus(), "yes");
							}
							Set<Entry<String, List<String>>> set = orderMap.entrySet();
							Iterator<Entry<String, List<String>>> it = set.iterator();

							while (it.hasNext()) {
								Entry<String, List<String>> entry = it.next();
								String orderIds = entry.getKey();
								List<String> value = entry.getValue();
								boolean updateSuccess = false;
								boolean update = false;
								if (orderMapRealy.get(orderId) == null) {
									for (String v : value) {
										String[] arr = v.split("_");
										String xhssku = arr[0];
										int num = Integer.valueOf(arr[1]);
										Map<String, String> searchMap = new HashMap<String, String>();
										searchMap.put("xhsSku", xhssku);
										searchMap.put("type", type);
										List<StockUpdate> stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
										for (StockUpdate su : stockUpdateList) {
											su.setXhsOrderStock(num+ su.getXhsOrderStock());
											su.setLastOrderTime("yes");
											platformStockUpdateDao.updateStockByNotNull(su);
											updateSuccess = true;
											updateNumber++;
										}
									}
								}
								if (updateSuccess) {
									PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
									oderRecord.setOrderId(orderIds);
									oderRecord.setIdPlartform(10);// 小红书
									oderRecord.setIdStatus(1);
									oderRecord.setOrderTime(palcedTime);
									oderRecord.setStatus(status);
									platformStockUpdateDao.insertxhsOrder(oderRecord);
								}
								if (status.equals("confirmed")) {
									PlatFormOrderRecord oderRecords = new PlatFormOrderRecord();
									oderRecords.setOrderId(orderIds);
									PlatFormOrderRecord liststatus = platformStockUpdateDao.selectderRecord(oderRecords);
									if(liststatus != null){
									String sta = liststatus.getStatus();
									if (sta.equals("unconfirmed")) {
										PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
										oderRecord.setOrderId(orderIds);
										oderRecord.setIdPlartform(10);// 小红书
										oderRecord.setIdStatus(1);
										oderRecord.setOrderTime(createtime);
										oderRecord.setStatus(status);
										platformStockUpdateDao.updateOrder(oderRecord);
									}
									}
								} 
							}
						}
					} else if (status.equals("unconfirmed")) {
						PlatFormOrderRecord oderRecords = new PlatFormOrderRecord();
						oderRecords.setOrderId(orderId);
						PlatFormOrderRecord liststatus = platformStockUpdateDao.selectderRecord(oderRecords);
						if(liststatus == null){
						Map<String, String> searchMap = new HashMap<String, String>();
						searchMap.put("xhsSku", sku);
						searchMap.put("type", type);
						List<StockUpdate> stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						for (StockUpdate su : stockUpdateList) {
							su.setXhsOrderStock(q + su.getXhsOrderStock());
							su.setLastOrderTime("yes");
							platformStockUpdateDao.updateStockByNotNull(su);

							PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
							oderRecord.setOrderId(orderId);
							oderRecord.setIdPlartform(10);// 小红书
							oderRecord.setIdStatus(3);
							oderRecord.setOrderTime(ordertimes);
							oderRecord.setStatus(status);
							platformStockUpdateDao.insertxhsOrder(oderRecord);
							updateNumber++;
						}
						}
					} else if (status.equals("canceled")) {
						PlatFormOrderRecord oderRecords = new PlatFormOrderRecord();
						oderRecords.setOrderId(orderId);
						PlatFormOrderRecord liststatus = platformStockUpdateDao.selectderRecord(oderRecords);
						if(liststatus != null){

						if (liststatus.getStatus().equals("unconfirmed")) {


							String stat = status;
							oderRecords.setOrderId(orderId);
							oderRecords.setIdPlartform(10);
							oderRecords.setIdStatus(2);
							oderRecords.setOrderTime(ordertimes);
							oderRecords.setStatus(stat);
							platformStockUpdateDao.updateOrder(oderRecords);
 
							Map<String, String> searchMap = new HashMap<String, String>();
							searchMap.put("xhsSku", sku);
							searchMap.put("type", type);
							List<StockUpdate> stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
							for (StockUpdate su : stockUpdateList) {
								su.setXhsOrderStock(su.getXhsOrderStock() - q);
								su.setLastOrderTime("yes");
								platformStockUpdateDao.updateStockByNotNull(su);
								updateNumber--;
							}
						}
					}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			System.out.println("請求失敗,小红书,订单接口");
		}
		return updateNumber;
	}
	/*public static void main(String[] args) {
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Long time= new Long(1496975692);
		String d = format.format(time * 1000L);
		
		System.out.println(d);
		
		String lo = "1496975692000";
	     long times=Long.valueOf(lo);  
	     System.out.println(times);
	     System.out.println(format.format(new Date(times)));  
	}*/
	
	
}
