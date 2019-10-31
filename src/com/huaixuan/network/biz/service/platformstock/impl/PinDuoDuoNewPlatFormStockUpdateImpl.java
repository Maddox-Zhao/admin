 package com.huaixuan.network.biz.service.platformstock.impl;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.PinDuoDuoNewPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.PinDuoDuoPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("PinDuoDuoNewPlatFormStockUpdate")
public class PinDuoDuoNewPlatFormStockUpdateImpl implements PinDuoDuoNewPlatFormStockUpdate {
	private static final String client_id = "2a8e6de22645486a9df6b58abfb41401";
	private static final String grant_type = "refresh_token";
	private static final String client_secret = "bd966ea61aba6dd01757d0e772909621e83ff1cb";
	private static final String code = "f36267014c1e499ab6edb298ec9a999f640d6c1c";
	private static final String redirect_uri = "http://admin.hkshangshang.com/index.html";
	private static final String access_token_url = "http://open-api.pinduoduo.com/oauth/token";


	private static final String date_type = "JSON";
	public static String accesstoken = "";
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;


	@Autowired
	private AutoSyncDao autoSyncDao;
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	private static final String refresh_token = "91fb144d4659440a9518b3d38d10ce1c917f9e61";

    protected Log log = LogFactory.getLog(this.getClass());
	public void updateSku2Location() {
		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);
		TreeMap<String, String> map = new TreeMap<String, String>();
		String type = "pdd.goods.list.get";
		map.put("type", type);
		map.put("client_id", client_id);
		map.put("access_token", accesstoken);
		map.put("timestamp", timestamp);
		map.put("date_type", date_type);
        int errorCnt =1;
		int page = 1;
		int page_size = 30;
		for (;;) {
			map.put("is_onsale", "1");
			map.remove("sign");
			map.put("page", page + "");
			map.put("page_size", page_size + "");
			String sign = getsign(map);
			map.put("sign", sign);
			String response = HttpRequest.sendPost("http://gw-api.pinduoduo.com/api/router", map);
//System.out.println(response);
			try {
				JSONObject jsonObj = new JSONObject(response);
				String goods_list_get_response_str = jsonObj.getString("goods_list_get_response");
				JSONObject goods_list_get_response = new JSONObject(goods_list_get_response_str);
				int totalNum = goods_list_get_response.getInt("total_count");
				int totalPageSize = (int) Math.floor(totalNum / page_size) + 1;

				JSONArray items = goods_list_get_response.getJSONArray("goods_list");

				List<PlatformSku2LocationSku> pddskuList = new ArrayList<PlatformSku2LocationSku>();
				for (int i = 0; i < items.length(); i++) {
					JSONObject item = new JSONObject(items.get(i).toString());
					JSONArray skus = item.getJSONArray("sku_list");
					for (int index = 0; index < skus.length(); index++) {
						JSONObject sku = new JSONObject(skus.get(index).toString());
						String pddsku = sku.getString("sku_id");
						String ourSku = sku.getString("outer_id");
						if (ourSku != null) {
							ourSku = ourSku.trim();
						}
						if ((StringUtil.isNotBlank(pddsku)) && (StringUtil.isNotBlank(ourSku))) {
							PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
							pslsku.setOurSku(ourSku);
							pslsku.setPlatformField("pddnew_sku");
							pslsku.setPlatformSku(pddsku);
							pslsku.setType("sh");
							pddskuList.add(pslsku);
						}
					}
				}
				Map pddSkuMap = new HashMap();
				pddSkuMap.put("list", pddskuList);
				pddSkuMap.put("platformField", "pddnew_sku");
				if (pddskuList.size() > 0) {
					this.platformStockUpdateDao.batchUpdatePlatformSku2Location(pddSkuMap);
				}
				if (page >= totalPageSize) {
					break;
				}
				page++;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("pddnew syn sku error " + errorCnt);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}
		}
	}

	public boolean updatePinDuoduoStock(String pddSku, String ourSku,int quantity, String type) {
		if ((StringUtil.isBlank(pddSku)) && (StringUtil.isBlank(ourSku))) {
			return true;
		}
		Map<String, String> searchMap = new HashMap<String, String>();
		if (StringUtil.isBlank(ourSku)) {
			searchMap.put("type", type);
			searchMap.put("pddnewSku", pddSku);
			List<StockUpdate> list = this.platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if (list.size() > 0) {
				ourSku = ((StockUpdate) list.get(0)).getSku();
			}
		} else if (StringUtil.isBlank(pddSku)) {
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "pddnew_sku");
			pddSku = this.platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if (StringUtil.isBlank(pddSku)) {
			return true;
		}
		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);
		TreeMap<String, String> map = new TreeMap<String, String>();
		String pddtype = "pdd.goods.sku.stock.update";
		map.put("type", pddtype);
		map.put("client_id", client_id);
		map.put("access_token", accesstoken);
		map.put("timestamp", timestamp);
		map.put("date_type", date_type);

		map.put("sku_id", pddSku);
		map.put("quantity", quantity + "");
		String sign = getsign(map);
		map.put("sign", sign);
		String response = HttpRequest.sendPost("http://gw-api.pinduoduo.com/api/router", map);

		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", pddSku);
		logMap.put("name", "pddnew");
		logMap.put("stock", quantity + "");
		logMap.put("location", type);
		logMap.put("type", "success");
		boolean flag = false;
		try {
			JSONObject jsonObject = new JSONObject(response);
			String Key = jsonObject.getString("sku_stock_update_response");
			JSONObject keys = new JSONObject(Key);
			String success = keys.getString("is_success");
			flag = true;
		} catch (Exception e) {
			logMap.put("type", "error");
			logMap.put("error", response);		
			log.error(e.getMessage(), e);
		}
		this.autoSyncDao.addUpdateLog(logMap);
		return flag;
	}

	private String getsign(Map<String, String> map) {
		String sign = "";
		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		Iterator<Map.Entry<String, String>> it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = (Map.Entry) it.next();
			sign = sign + (String) en.getKey() + (String) en.getValue();
		}
		sign = client_secret + sign + client_secret;
		sign = HttpRequest.string2MD5(sign).toUpperCase();
		return sign;
	}

	public void updateAllStock() {
		HashMap<String, String> serchMap = new HashMap<String, String>();
		serchMap.put("type", "sh");
		serchMap.put("skuisnotnull", "pddnew_sku");
		List<StockUpdate> list = this.platformStockUpdateDao.selectStockUpdateByMap(serchMap);
		for (StockUpdate s : list) {
			int num = 0;
			//String oursku = s.getSku(); // 得到我们的sku
			num = s.getNowStockNum() - s.getOrderStockNum();

			/*Map<String, String> hkmap = new HashMap<String, String>();
			hkmap.put("type", "hk");
			hkmap.put("sku", oursku);
			List<StockUpdate> hklist = platformStockUpdateDao.searchStockUpdate(hkmap);
			int hkNum = 0;
			for (StockUpdate hk : hklist) {
				int cnt = hk.getNowStockNum() - hk.getOrderStockNum();
				if (cnt < 0)
					cnt = 0;
				hkNum += cnt;
			}
			num += hkNum;*/
			if (num < 0) num = 0;
			updatePinDuoduoStock(s.getPddnewSku(), s.getSku(), num, s.getType());
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public int atuoSyncOrder() {
		String times = String.valueOf(System.currentTimeMillis());
		String timestamp = times.substring(0, times.length() - 3);
		TreeMap<String, String> map = new TreeMap<String, String>();
		String type = "pdd.order.number.list.get";
		map.put("type", type);
		map.put("client_id", client_id);
		map.put("access_token", accesstoken);
		map.put("timestamp", timestamp);
		map.put("date_type", date_type);

		Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		map.put("order_status", "1");
		map.remove("sign");
		String sign = getsign(map);
		map.put("sign", sign);
		String response = HttpRequest.sendPost("http://gw-api.pinduoduo.com/api/router", map);
//		System.out.println(response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject order_sn_list_get_response_str = new JSONObject(jsonObject.getString("order_sn_list_get_response"));
			JSONArray orderList = order_sn_list_get_response_str.getJSONArray("order_sn_list");
//			int total_count = order_sn_list_get_response_str.getInt("total_count");
			for (int index = 0; index < orderList.length(); index++) {
				JSONObject orderInfo = new JSONObject(orderList.get(index).toString());
				String orderId = orderInfo.getString("order_sn");
				list.add(orderId);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		TreeMap<String, String> maps;
		JSONObject order_info;
		String msg;
		for (int i = 0; i < list.size(); i++) {
			String id = (String) list.get(i);
			String time = String.valueOf(System.currentTimeMillis());
			String timestamps = time.substring(0, time.length() - 3);
			maps = new TreeMap<String, String>();
			String types = "pdd.order.information.get";
			maps.put("type", types);
			maps.put("client_id", client_id);
			maps.put("access_token", accesstoken);
			maps.put("timestamp", timestamps);
			maps.put("date_type", date_type);
			maps.put("order_sn", id);
			maps.remove("sign");
			String signs = getsign(maps);
			maps.put("sign", signs);
			String responses = HttpRequest.sendPost("http://gw-api.pinduoduo.com/api/router", maps);
			try {
				JSONObject jsonObjects = new JSONObject(responses);
				JSONObject order_info_get_response = new JSONObject(jsonObjects.getString("order_info_get_response"));
				order_info = order_info_get_response.getJSONObject("order_info");
				String order_sn = order_info.getString("order_sn");//订单编号
				String created_time = order_info.getString("created_time");//下单时间
				String pay_time = order_info.getString("pay_time");//支付时间
				String receiver_name = order_info.getString("receiver_name");//收件人姓名
				String receiver_phone = order_info.getString("receiver_phone");//收件人电话
				String country = order_info.getString("country");//收件地址国家
				String province = order_info.getString("province");//省
				String city = order_info.getString("city");//市
				String town = order_info.getString("town");//区
				String address = order_info.getString("address");//详细地址
				String town_id = order_info.getString("town_id");//区县邮编
				String discount_amount = order_info.getString("discount_amount");//优惠金额
				String pay_amount = order_info.getString("pay_amount");//支付金额
				String postage = order_info.getString("postage");//邮费
				JSONArray item_list = order_info.getJSONArray("item_list");
				for (int j = 0; j < item_list.length(); j++) {
					JSONObject good = new JSONObject(item_list.get(j).toString());
					String pddsku = good.getString("sku_id");//平台SKU
					String outer_id = good.getString("outer_id");//我们的SKU
					String goods_count = good.getString("goods_count");//商品数量
					String goods_name = good.getString("goods_name");//商品名称
					String goods_spec = good.getString("goods_spec");//商品规格
					String goods_price = good.getString("goods_price");//商品售价
					msg = pddsku + "_" + goods_count;
					PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
					platformorderdetails.setIdorder(order_sn);
					List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
						platformorderdetails.setIdorder(order_sn);
						platformorderdetails.setPalcedTime(created_time);
						platformorderdetails.setPayTime(pay_time);
						platformorderdetails.setName(receiver_name);
						platformorderdetails.setMobile(receiver_phone);
						platformorderdetails.setCountry(country);
						platformorderdetails.setProvince(province);
						platformorderdetails.setCity(city);
						platformorderdetails.setDistrict(town);
						platformorderdetails.setStreetAddress(address);
						platformorderdetails.setZipCode(town_id);
						platformorderdetails.setDiscountPrice(discount_amount);
						platformorderdetails.setPayprice(pay_amount);
						platformorderdetails.setFreight(postage);
						platformorderdetails.setSkuId(pddsku);
						platformorderdetails.setMerchantSkuId(outer_id);
						platformorderdetails.setQuantity(goods_count);
						platformorderdetails.setProductname(goods_name);
						platformorderdetails.setSize(goods_spec);
						platformorderdetails.setPrice(goods_price);
						platformorderdetails.setPtype("pddNew");
						if ((liststatus == null || liststatus.size() == 0)) {
							platformOrderDetailsDao.insertOrder(platformorderdetails);

						} else if (liststatus != null) {
							List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
							if (listst == null || listst.size() == 0) {
								platformOrderDetailsDao.insertOrder(platformorderdetails);
							}
						}
					if (orderMap.get(order_sn) == null) {
						List<String> value = new ArrayList<String>();
						value.add(msg);
						orderMap.put(order_sn, value);
					} else {
						((List) orderMap.get(order_sn)).add(msg);
					}
				}
			} catch (Exception localJSONException1) {
				log.error(localJSONException1.getMessage());
			}
		}
		int updateNumber = 0;
		if (orderMap.size() > 0) {
			list.add("-1");
			List<PlatFormOrderRecord> orderList = this.platformStockUpdateDao.getOrdersByOrders(list);
			Map<String, String> orderMapRealy = new HashMap<String, String>();
			for (PlatFormOrderRecord k : orderList) {
				orderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Map.Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Map.Entry<String, List<String>>> it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<String, List<String>> entry = it.next();
				String orderId = (String) entry.getKey();
				List<String> value = entry.getValue();
				boolean updateSuccess = false;
				String[] arr;
				if (orderMapRealy.get(orderId) == null) {
					for (String v : value) {
						arr = v.split("_");
						String pddsku = arr[0];
						int num = Integer.valueOf(arr[1]).intValue();
						Map<String, String> searchMap = new HashMap<String, String>();
						searchMap.put("pddnewSku", pddsku);
						List<StockUpdate> stockUpdateList = this.platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						for (StockUpdate su : stockUpdateList) {
							Map<String, String> searchMaps = new HashMap<String, String>();
							searchMaps.put("sku", su.getSku());
							searchMaps.put("type", "hk");
							boolean updatesh = true;
							List<StockUpdate> stockUpdateLists = this.platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
							if (stockUpdateLists != null && stockUpdateLists.size() > 0) {
								int s = su.getNowStockNum() - su.getOrderStockNum();
								if (s <= 0) {
									for (StockUpdate ss : stockUpdateLists) {
										ss.setPddOrderStock(num+ ss.getPddOrderStock()); //新拼多多的订单数量都放入pdd_order_stock，不放pddnew_order_stock,目的是方便开单时减订单数
										ss.setLastOrderTime("yes");
										this.platformStockUpdateDao.updateStockByNotNull(ss); 
										updateSuccess = true;
										updatesh = false;
										updateNumber++;
									}
								}
							}
							if (updatesh) {
								su.setPddOrderStock(num + su.getPddOrderStock());
								su.setLastOrderTime("yes");
								this.platformStockUpdateDao.updateStockByNotNull(su);
								updateSuccess = true;
								updateNumber++;
							}
						}
					}
				}
				if (updateSuccess) {
					PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
					oderRecord.setOrderId(orderId);
					oderRecord.setIdPlartform(8);
					oderRecord.setIdStatus(1);
					this.platformStockUpdateDao.insertOrder(oderRecord);
				}
			}
		}
		return updateNumber;
	}

//	 @PostConstruct
	public void startDealPinduoduoRefresh() {
	
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
//					try { 
//						Thread.sleep(10000);
//						System.out.println("100000");
//					} catch (InterruptedException e) {
//
//						e.printStackTrace();
//						log.error(e.getMessage(), e);
//					}
					PinDuoDuoNewPlatFormStockUpdateImpl pddnew = new PinDuoDuoNewPlatFormStockUpdateImpl();
					pddnew.pinduoduoRefresh();
					try { 
						Thread.sleep(1000 * 60 * 60 * 1);
					} catch (InterruptedException e) {

						e.printStackTrace();
						log.error(e.getMessage(), e);
					}
				}

			}
		});

	
		thread.start();
	}

	public String pinduoduoRefresh() {
		Map<String, Object> mapt = new HashMap();

		mapt.put("client_id", client_id);
		mapt.put("client_secret", client_secret);
		mapt.put("grant_type", grant_type);

		mapt.put("refresh_token", refresh_token);

		String resp = HttpRequest.sendPostWeimob(access_token_url, mapt);
		try {
			JSONObject json = new JSONObject(resp);

//			System.out.println(json);

			accesstoken = json.getString("access_token");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return accesstoken;
	}
//	// 获取token的 
//	private static final String grant_type_token ="authorization_code";
//	public static void main(String[] args) {
//		Map<String, Object> mapt = new HashMap();
//
//		mapt.put("client_id", client_id);
//		mapt.put("client_secret", client_secret);
//		mapt.put("grant_type", grant_type_token);
//		mapt.put("code", code);
//		mapt.put("redirect_uri", redirect_uri);
//
//		String resp = HttpRequest.sendPostWeimob(access_token_url, mapt);
//		System.out.println(resp);
//	}
	
	//刷新token的
//	public static void main(String[] args) {
//		Map<String, Object> mapt = new HashMap();
//
//		mapt.put("client_id", client_id);
//		mapt.put("client_secret", client_secret);
//		mapt.put("grant_type", grant_type);
//
//		mapt.put("refresh_token", refresh_token);
//
//		String resp = HttpRequest.sendPostWeimob(access_token_url, mapt);
//		System.out.println(resp);
//	}
	
//	public static void main(String[] args) {
//		PinDuoDuoPlatFormStockUpdateImpl pin = new PinDuoDuoPlatFormStockUpdateImpl();
//		pin.atuoSyncOrder();
//	}
}
