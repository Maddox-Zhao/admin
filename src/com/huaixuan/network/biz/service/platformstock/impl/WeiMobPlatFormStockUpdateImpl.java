package com.huaixuan.network.biz.service.platformstock.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

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
import com.huaixuan.network.biz.domain.platformstock.WeimobEntity;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.WeiMobPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;

@Service("WeiMobPlatFormStockUpdate")
public class WeiMobPlatFormStockUpdateImpl implements WeiMobPlatFormStockUpdate {

	private static final String FullInfoGet_url = "https://dopen.weimob.com/api/1_0/wangpu/Spu/FullInfoGet?"; // 获取商品信息和规格信息

	private static final String Inventory_url = "https://dopen.weimob.com/api/1_0/wangpu/Inventory/Update?"; // 获取库存的接口

	// 获取订单接口
	private static final String GetHighly_url = "https://dopen.weimob.com/api/1_0/wangpu/Order/GetHighly?";

	// 获取订单详情接口
	private static final String FullInfoGetHighly_url = "https://dopen.weimob.com/api/1_0/wangpu/Order/FullInfoGetHighly?";

	public static String accesstoken = "";
	protected Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	@Autowired
	private AutoSyncDao autoSyncDao;

	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;

	// 更新微盟的sku到本地
	@Override
	public void updateSku2Location() {

		updateSku2LocationByAccesstoken();

	}

	// 更新sku到本地
	public void updateSku2LocationByAccesstoken() {

		TreeMap<String, String> map = new TreeMap<String, String>();
		int page_no = 1;

		if (accesstoken == "") {
			accesstoken = weiMobRefreshAndAccess();
		}

		String accesstokenkk = "accesstoken=" + accesstoken;
//		System.out.println(accesstokenkk+"----->accesstokenkk");
		// 页码
		while (true) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				int page_size = 20;
				map.put("is_onsale", "1"); // 上下架状态(下架=0，上架=1，所有=2)
				map.put("page_size", page_size + ""); // 分页大小
				map.put("page_no", page_no + "");
				String response = HttpRequest.ofsendPost(FullInfoGet_url+ accesstokenkk, mapper.writeValueAsString(map));
//				System.out.println("response----->"+response);
				JSONObject jsons = new JSONObject(response);
				
				String data = jsons.getString("data");
				JSONObject jsondata = new JSONObject(data);
				int row_count = jsondata.getInt("row_count"); // 商品总数
				JSONArray dataArrays = jsondata.getJSONArray("page_data"); // 数据
				int pageTotal = (int) Math.floor((row_count / page_size)) + 1; // 计算得到的总页数

				for (int i = 0; i < dataArrays.length(); i++) {
					JSONObject dataArray = new JSONObject(dataArrays.get(i)
							.toString());
					JSONArray skus = dataArray.getJSONArray("skus"); // 货品信息

					List<PlatformSku2LocationSku> weimobList = new ArrayList<PlatformSku2LocationSku>();
					for (int j = 0; j < skus.length(); j++) {
						JSONObject jsonSkus = new JSONObject(skus.get(j)
								.toString());
						String spu_code = jsonSkus.getString("spu_code"); // 平台sku
						String sku_code = jsonSkus.getString("sku_code"); // 我们的sku
						if (StringUtil.isNotBlank(sku_code)) {

							sku_code = sku_code.trim();
						}

						if (StringUtil.isNotBlank(sku_code)
								&& StringUtil.isNotBlank(spu_code)) {
							PlatformSku2LocationSku pss = new PlatformSku2LocationSku();
							pss.setOurSku(sku_code); // 我们的sku
							pss.setPlatformField("weimob_sku");
							pss.setPlatformSku(spu_code); // 平台sku
							pss.setType("sh");
							weimobList.add(pss);
						}

					}

					Map weiMobSkuMap = new HashMap();
					weiMobSkuMap.put("list", weimobList);
					weiMobSkuMap.put("platformField", "weimob_sku");

					if (weimobList.size() > 0) {
						platformStockUpdateDao.batchUpdatePlatformSku2Location(weiMobSkuMap);
					}

				}
				if (page_no >= pageTotal)
					break;
				page_no++;

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}

		}

	}

	@Override
	public void updateAllStock() {
		Map<String, String> searchmap = new HashMap<String, String>();
		searchmap.put("type", "sh");
		searchmap.put("skuisnotnull", "weimob_sku");
		List<StockUpdate> list = platformStockUpdateDao.searchStockUpdate(searchmap);
		for (StockUpdate s : list) {
			int num = 0;
			String oursku = s.getSku(); // 得到我们的sku
			num = s.getNowStockNum() - s.getOrderStockNum();

			Map<String, String> hkmap = new HashMap<String, String>();
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
			num += hkNum;
			if (num < 0)
				continue;
			updateWeimobStock(oursku, s.getWeimobsku(), num, s.getType());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public boolean updateWeimobStock(String oursku, String weimobsku,
			int quantity, String type) {
		if (StringUtil.isBlank(weimobsku) && StringUtil.isBlank(oursku)) {
			return true;
		}
		boolean flag = true;
		Map<String, String> searchMap = new HashMap<String, String>();
		if (StringUtil.isBlank(oursku)) {
			searchMap.put("type", type);
			searchMap.put("weimobsku", weimobsku);
			List<StockUpdate> list = platformStockUpdateDao
					.selectStockUpdateByMap(searchMap);
			if (list.size() > 0) {
				oursku = list.get(0).getSku();
			}
		} else if (StringUtil.isBlank(weimobsku)) {
			searchMap.put("sku", oursku);
			searchMap.put("type", type);
			searchMap.put("field", "weimob_sku");
			weimobsku = platformStockUpdateDao
					.getPlatformSkuByOurSku(searchMap);
		}
		if (StringUtil.isNotBlank(oursku)) {
			searchMap.put("type", type);
			searchMap.put("weimobsku", weimobsku);
			searchMap.put("sku", oursku);
			List<StockUpdate> list = platformStockUpdateDao
					.selectStockUpdateByMap(searchMap);
			if (list.size() > 0) {
				oursku = list.get(0).getSku();
				weimobsku = list.get(0).getWeimobsku();
			}
		}
		if (StringUtil.isBlank(weimobsku)) {
			return true;
		}
		List<WeimobEntity> wetList = new ArrayList<WeimobEntity>();
		WeimobEntity web = new WeimobEntity();
		web.setSku_code(oursku);
		web.setInventory(quantity);
		wetList.add(web);
		Map<String, Object> map = new HashMap<String, Object>();
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(wetList);
		map.put("spu_code", weimobsku);
		map.put("inventory", quantity + "");
		map.put("sku_list", json);
		map.put("type", "TOTAL");
		if (accesstoken == "") {
			accesstoken = weiMobRefreshAndAccess();
		}
		String accesstokenkk = "accesstoken=" + accesstoken;

		String response = "";

		Map<String, String> logMap = new HashMap<String, String>();

		JSONObject jns;
		try {
			response = HttpRequest.sendPostWeimob(
					Inventory_url + accesstokenkk, map);

			logMap.put("sku", oursku);
			logMap.put("psku", weimobsku);
			logMap.put("name", "weimob");
			logMap.put("stock", quantity + "");
			logMap.put("location", type);
			logMap.put("type", "success");

			jns = new JSONObject(response);
			String code = jns.getString("code");
			JSONObject codeAndMess = new JSONObject(code);
			String errcode = codeAndMess.getString("errcode");

			if ("0".equals(errcode)) {
				// 更新成功
				flag = true;

			} else {
				String errmsg = jns.getString("errmsg");
				logMap.put("type", "error");
				logMap.put("error", errmsg);
			}

		} catch (Exception e) {
			logMap.put("type", "error");
			logMap.put("error", response);
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		autoSyncDao.addUpdateLog(logMap);
		return false;
	}

	// 同步微盟的订单
	@Override
	public int atuoSyncOrder() {
		int updateNumber = 0;

		if (accesstoken == "") {
			weiMobRefreshAndAccess();
		}

		if (accesstoken != "") {
			String accesstokenken = "accesstoken=" + accesstoken;
			Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
			List<String> list = new ArrayList<String>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_status", "1"); // 订单状态(1交易中,2交易成功,3交易关闭，空值代表所有)
			map.put("pay_status", "0"); // 订单支付状态(0待支付，1已支付，空值代表所有)
			map.put("delivery_status", null); // 物流状态(0待发货，1卖家发货,2买家收货，空值代表所有)
			map.put("page_size", 50); // 分页条数(取值范围【1，200】)
			map.put("page_no", 1); // 分页页码(从1开始)

			String response = "";
			String createtime = "";
			try {
				response = HttpRequest.sendPostWeimob(GetHighly_url
						+ accesstokenken, map);
//				System.out.println(response);
				JSONObject json = new JSONObject(response);
				String data = json.getString("data");
				if (!data.equals("null")) {
					JSONObject datajson = new JSONObject(data);
					String pagedada = datajson.getString("page_data");
					JSONArray datarray = new JSONArray(pagedada);
					for (int i = 0; i < datarray.length(); i++) {
						JSONObject jsObject = new JSONObject(datarray.get(i).toString());
						String order_no = jsObject.getString("order_no"); // 从订单列表中获取订单id
						if (order_no != "") {
							Map<String, Object> maps = new HashMap<String, Object>();
							maps.put("order_no", order_no); // 用订单id获取订单详情
							String responses = HttpRequest.sendPostWeimob(FullInfoGetHighly_url + accesstokenken,maps);
							JSONObject jsonBec = new JSONObject(responses);
							String jsoda = jsonBec.getString("data");
							JSONObject dataObj = new JSONObject(jsoda);
							String details = dataObj.getString("order_details");
							JSONArray orderArray = new JSONArray(details);
							for (int j = 0; j < orderArray.length(); j++) {
								JSONObject ob = new JSONObject(orderArray.get(j).toString());
								String spucode = ob.getString("spu_code"); // 平台的sku
								String skucode = ob.getString("sku_code"); // 我们的sku
								String qty = ob.getString("qty"); // 此sku商品的数量
								String skuname = ob.getString("sku_name"); // 商品的名字
								String saleprice = ob.getString("sale_price"); // 售价
								String price = ob.getString("price"); // 成交价格
								String sku_description = ob.getString("sku_description"); // 商品描述

								String rersion = dataObj.getString("receiver_region"); // 得到收件人地址的信息
								JSONObject jsonRersion = new JSONObject(rersion);
								String province = jsonRersion.getString("province"); // 省
								String city = jsonRersion.getString("city"); // 市
								String district = jsonRersion.getString("district"); // 区
								String town = jsonRersion.getString("town"); // 街道
								String address = jsonRersion.getString("address"); // 路
								String postalcode = jsonRersion.getString("postalcode"); // 邮编

								String receviername = dataObj.getString("receiver_name");// 收件人姓名
								String receviertel = dataObj.getString("receiver_tel"); // 收件人号码
								createtime = dataObj.getString("create_time"); // 订单创建时间
								String deliveryAmount = dataObj.getString("delivery_amount"); // 运费

								PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
								platformorderdetails.setIdorder(order_no);
								List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);

								platformorderdetails.setIdorder(order_no); // 订单号
								platformorderdetails.setPalcedTime(createtime); // 下单时间
								platformorderdetails.setName(receviername); // 收件人姓名
								platformorderdetails.setMobile(receviertel); // 收件人电话
								platformorderdetails.setProvince(province); // 省份
								platformorderdetails.setCity(city); // 城市
								platformorderdetails.setDistrict(district); // 区
								platformorderdetails.setStreetAddress(town + address);// 街道地址
								platformorderdetails.setZipCode(postalcode); // 邮政编码
								platformorderdetails.setTotalPrice(saleprice); // 订单总价
								platformorderdetails.setDiscountPrice(price); // 优惠总价
								platformorderdetails.setFreight(deliveryAmount); // 运费
								platformorderdetails.setSkuId(spucode); // 平台SKU
								platformorderdetails.setMerchantSkuId(skucode); // 我们的SKU
								platformorderdetails.setQuantity(qty); // 商品数量
								platformorderdetails.setProductname(skuname); // 商品名称
								platformorderdetails.setSize(sku_description); // 商品尺寸
								platformorderdetails.setPtype("weimob"); // 平台订单
								// 如果没有这个订单详情insert一个订单详情，一个订单有多个多个商品，有多个商品详情，当已有订单详情且订单详情的sku不等于现在的sku添加一个新的商品详情
								if ((liststatus == null || liststatus.size() == 0)) {
									platformOrderDetailsDao.insertOrder(platformorderdetails);

								} else if (liststatus != null) {
									List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
									if (listst == null || listst.size() == 0) {
										platformOrderDetailsDao.insertOrder(platformorderdetails);
									}
								}

								list.add(order_no); // 将订单号放进list
								String msg = skucode + "_" + qty; // 我们的sku和购买的商品数量

								if (orderMap.get(order_no) == null) {
									List<String> value = new ArrayList<String>();
									value.add(msg);
									orderMap.put(order_no, value);

								} else {
									orderMap.get(order_no).add(msg);
								}
							}
						}
					}
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}

			if (orderMap.size() > 0) {
				list.add("-1"); // 避免没有数据 报错

				// 用放进list中的订单号查询订单表
				List<PlatFormOrderRecord> orderList = platformStockUpdateDao.getOrdersByOrders(list);
				Map<String, String> orderMapRealy = new HashMap<String, String>();
				// 第一次执行，某个订单号在PlatFormOrderRecord表中，还没有插入,orderList中没有东西，会跳过，不执行for循环中的东西
				for (PlatFormOrderRecord k : orderList) {
					orderMapRealy.put(k.getOrderId(), "yes");
				}

				Set<Entry<String, List<String>>> set = orderMap.entrySet();
				Iterator<Entry<String, List<String>>> it = set.iterator();
				while (it.hasNext()) {
					Entry<String, List<String>> entry = it.next();
					String orderId = entry.getKey();
					List<String> value = entry.getValue();

					boolean updateSuccess = false;

					if (orderMapRealy.get(orderId) == null) {
						for (String v : value) {
							String[] arr = v.split("_");
							String sku = arr[0];
							int num = Integer.valueOf(arr[1]);
							Map<String, String> searchMap = new HashMap<String, String>();
							searchMap.put("sku", sku);
							searchMap.put("type", "sh");
							List<StockUpdate> stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);

							for (StockUpdate su : stockUpdateList) {
								Map<String, String> searchMaps = new HashMap<String, String>();
								searchMaps.put("sku", sku);
								searchMaps.put("type", "hk");
								boolean updatesh = true;
								List<StockUpdate> stockUpdateLists = platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
								if (stockUpdateLists != null && stockUpdateLists.size() > 0) {
									int s = su.getNowStockNum()- su.getOrderStockNum();
									if (s <= 0) {
										for (StockUpdate ss : stockUpdateLists) {
											ss.setWeiMobOrderStock(num+ ss.getWeiMobOrderStock());
											ss.setLastOrderTime("yes");
											platformStockUpdateDao.updateStockByNotNull(ss);
											updateSuccess = true;
											updatesh = false;
											updateNumber++;
										}
									}
								}
								if (updatesh) {
									su.setWeiMobOrderStock(num + su.getWeiMobOrderStock());
									su.setLastOrderTime("yes");
									platformStockUpdateDao.updateStockByNotNull(su);
									updateSuccess = true;
									updateNumber++;
								}
							}
						}
					}
					if (updateSuccess) {
						PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
						oderRecord.setOrderId(orderId);
						oderRecord.setIdPlartform(12);// 微盟
						oderRecord.setIdStatus(1); // 已支付的订单
						oderRecord.setOrderTime(createtime);
						platformStockUpdateDao.insertOrder(oderRecord);
					}
				}

			}
		}

		return updateNumber;

	}

	/*
	 * refresh_token有效期是7天，在有效期时间内，刷新，可得到新的token，此token可用两小时(在两小时范围内，或范围外，皆可刷新，
	 * 得到token) refresh_token每次刷新，可增加两小时，
	 */

	private static final String url = "https://dopen.weimob.com/fuwu/b/oauth2/token?";
	private static final String grant_type = "refresh_token";
	private static final String client_id = "C2549FC94205C075429BD78FC1C7716C";
	private static final String client_secret = "CA92DFB45B9EC0C216A92683F65096BC";
	private static final String refresh_token = "9815eb58-100c-416b-9d9a-bdc3984be62d";

	public String weiMobRefreshAndAccess() {

		TreeMap<String, String> mapt = new TreeMap<String, String>();

		mapt.put("grant_type", grant_type);
		mapt.put("client_id", client_id);
		mapt.put("client_secret", client_secret);
		mapt.put("refresh_token", refresh_token);

		String resp = HttpRequest.sendPost(url, mapt);
		try {
			JSONObject json = new JSONObject(resp);
			accesstoken = json.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return accesstoken;
	}

	// 每小时启动一次线程获取新的accesstoken

//	 @PostConstruct
	public void startDealWeiMob() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					WeiMobPlatFormStockUpdateImpl we = new WeiMobPlatFormStockUpdateImpl();
					we.weiMobRefreshAndAccess();
					try {
						Thread.sleep(1000 * 60 * 60 * 1);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
						e.printStackTrace();
					}
				}

			}
		});

		thread.start();
	}

}
