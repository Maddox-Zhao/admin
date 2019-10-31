/**
 * 
 */
package com.huaixuan.network.biz.service.platformstock.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;

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
import com.huaixuan.network.biz.service.platformstock.YinTaiPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author TT
 * 
 */
@Service("YinTaiPlatFormStockUpdateImpl")
public class YinTaiPlatFormStockUpdateImpl implements YinTaiPlatFormStockUpdate {

	private static final String app_key = "0137";

	private static final String app_secret = "429040604ed541f0e65fcf34cacf190d";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;

	@Autowired
	private AutoSyncDao autoSyncDao;

	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;



   protected Log log = LogFactory.getLog(this.getClass());
	/*
	 * 银泰的sku更新到本地
	 */
	@Override
	public void updateSku2Location() {
		String url = "http://ch.shoplinq.cn/api/v0/sku?";
		// String type = "sh";

		String type = "hk";
		Map<String, String> map = new HashMap<String, String>();
		String timesmap = String.valueOf(System.currentTimeMillis()).toString()
				.substring(0, 10);
		String method = "GET";

		// app_key=0000&app_secret=AAAA&method=POST&ts=1513906235
		// 先通过sha1加密，在通过MD5加密，得到sign
		String sign = getSignSha1AndMd5(app_key, app_secret, method, timesmap);
		// System.out.println(sign);
		// 应用参数

		int pageno = 1;
		int pagesize = 50;
		while (true) {
			// 系统参数
			map.put("ts", timesmap);
			map.put("app_key", app_key);
			map.put("method", "GET");
			map.put("sign", sign);

			// 应用参数
			map.put("page_no", pageno + "");
			map.put("page_size", pagesize + "");
			String response = HttpRequest.sendGetM(url, map);
			// System.out.println(response+"response的值");

			try {
				JSONObject jsonObj = new JSONObject(response);
				String status = jsonObj.getString("status");
				if (status.equals("200")) {
					String data = jsonObj.getString("data");
					JSONObject jsonObject = new JSONObject(data);
					int total = jsonObject.getInt("total_num");
					int totalPage = (int) Math.floor((total / pagesize)) + 1;
					JSONArray skuInfo = jsonObject.getJSONArray("sku_info");
					List<PlatformSku2LocationSku> yinTaiskuList = new ArrayList<PlatformSku2LocationSku>();
					if (skuInfo != null) {
						for (int i = 0; i < skuInfo.length(); i++) {
							JSONObject skufo = new JSONObject(skuInfo.get(i)
									.toString());
							String ourSku = skufo.getString("sku_code"); // 我们的sku
							String yinTaiSku = skufo.getString("out_sku_code");// 银泰sku
							if (ourSku != null) {
								ourSku = ourSku.trim();
							}
							if (StringUtil.isNotBlank(ourSku)
									&& StringUtil.isNotBlank(yinTaiSku)) {

								PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
								pslsku.setOurSku(ourSku);
								pslsku.setPlatformField("yinTai_sku");
								pslsku.setPlatformSku(yinTaiSku);
								pslsku.setType(type);
								yinTaiskuList.add(pslsku);
							}
						}
					}

					Map yinTaiSkuMap = new HashMap();
					yinTaiSkuMap.put("list", yinTaiskuList);
					yinTaiSkuMap.put("platformField", "yinTai_sku");

					if (yinTaiskuList.size() > 0) {
						platformStockUpdateDao
								.batchUpdatePlatformSku2Location(yinTaiSkuMap);
					}

					if (pageno >= totalPage) {
						break;
					} else {
						pageno++;
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();

			}

		}

	}

	// 更新银泰平台上的stock
	public void updateAllStock() {
		Map<String, String> searchMap = new HashMap<String, String>();
		// searchMap.put("type","sh");
		searchMap.put("type", "hk");
		searchMap.put("skuisnotnull", "yinTai_sku");
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			for (StockUpdate s : list) {
				if (s.getYinTaiSku() != null) {
					int num = s.getNowStockNum() - s.getOrderStockNum();
					if (num < 0) num = 0;
					updateYinTaiStock(s.getSku(), s.getYinTaiSku(), num, s.getType());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
					}
				}
			}

		
	}

	@Override
	public boolean updateYinTaiStock(String ourSku, String yinTaiSku,
			int quantity, String type) {
		String url = "http://ch.shoplinq.cn/api/v0/stock?";

		if (StringUtil.isBlank(ourSku) && StringUtil.isBlank(yinTaiSku)) {
			return true;
		}

		boolean flag = true;
		Map<String, String> searchMap = new HashMap<String, String>();
		if (StringUtil.isBlank(ourSku)) {
			searchMap.put("type", type);
			searchMap.put("yinTaiSku", yinTaiSku);
			List<StockUpdate> list = platformStockUpdateDao
					.selectStockUpdateByMap(searchMap);
			if (list.size() > 0) {
				ourSku = list.get(0).getSku();
			}
		} else if (StringUtil.isBlank(yinTaiSku)) {
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "yinTai_sku");
			yinTaiSku = platformStockUpdateDao
					.getPlatformSkuByOurSku(searchMap);
		}
		if (StringUtil.isBlank(yinTaiSku)) {
			return true;
		}

		Map<String, String> map = new HashMap<String, String>();
		String timesmap = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10);

		// 系统参数
		String method = "POST";

		String sign = getSignSha1AndMd5(app_key, app_secret, method, timesmap);
		map.put("ts", timesmap);
		map.put("app_key", app_key);
		map.put("method", "POST");
		map.put("sign", sign);
		map.remove("app_secret");
		// 应用参数
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("sku_code", ourSku);
		paramsMap.put("stock", quantity + "");
		String response = HttpRequest.sendPostYinTai(url, map, ourSku, quantity);
//		 System.out.println(response+"    ===>>response的值");

		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", yinTaiSku);
		logMap.put("name", "yinTai");
		logMap.put("stock", quantity + "");
		logMap.put("location", type);
		logMap.put("type", "success");
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(response);
			
			String status = jsonObject.getString("status");
			if (status.equals("200")) {
				flag = true;
			} else if (status.equals("300")) {
				logMap.put("type", "error");
				logMap.put("error", decode2(response));
			} else {
				logMap.put("type", "error");
				logMap.put("error", response);
			}

		} catch (JSONException e) {
			logMap.put("type", "error");
			logMap.put("error", response);
			log.error(e.getMessage(), e);
		}
		autoSyncDao.addUpdateLog(logMap);

		return flag;
	}

	@Override
	public int atuoSyncOrder() {
		String url = "http://ch.shoplinq.cn/api/v0/order?";
		int updateNumber = 0;
		int pageNO = 1;
		int count = 50;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long curren = System.currentTimeMillis();
		curren -= 29 * 60 * 1000;

		String times = String.valueOf(System.currentTimeMillis());
		String timesmap = times.substring(0, times.length() - 3);// 系统当前时间（时间戳）

		String beforetime = format.format(curren);
		String endTime = format.format(new Date());
		// System.out.println(beforetime);
		// System.out.println(endTime);

		// {102001874187736458=[8650058354173_1],
		// 101966228144291645=[8650062173271_1, 8650058354173_2]}
		Map<String, List<String>> orderMap = new HashMap<String, List<String>>(); // 保存订单信息和对应的sku
		List<String> list = new ArrayList<String>(); // 当前查询出来的订单id
		Map<String, String> map = new HashMap<String, String>();
		// 系统参数

		String method = "GET";
		String sign = getSignSha1AndMd5(app_key, app_secret, method, timesmap);
		map.put("sign", sign);
		map.put("ts", timesmap);
		map.put("app_key", app_key);
		map.put("method", "GET");
		map.put("sign", sign);

		// 应用参数
		map.put("start_time", beforetime);
		map.put("end_time", endTime);
		map.put("page_no", pageNO + "");
		map.put("page_size", count + "");
		// map.put("env", "test");
		String response = HttpRequest.sendGetM(url, map);
		// System.out.println(response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			String status = jsonObject.getString("status");
			if (status.equals("200")) {
				JSONArray ordersArray = jsonObject.getJSONArray("data");
				// System.out.println(ordersArray.length());
				if (ordersArray != null && ordersArray.length() > 0) {
					for (int i = 0; i < ordersArray.length(); i++) {
						JSONObject order = new JSONObject(
								ordersArray.getString(i));
						String orderId = order.getString("tid"); // 订单号
						String ourSkuId = order.getString("sku_code"); // 商品sku_code
						String num = order.getInt("num") + ""; // 数量
						String description = order.getString("properties");
						// //描述 “颜色分类:蓝;尺码:均码”

						PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
						platformorderdetails.setIdorder(orderId); // 订单号
						List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao
								.selectList(platformorderdetails);
						
						platformorderdetails.setIdorder(orderId);
						platformorderdetails.setQuantity(num); // 商品数量
						platformorderdetails.setSize(description); // 商品尺寸
						platformorderdetails.setMerchantSkuId(ourSkuId); // 我们的SKU
						platformorderdetails.setPtype("yinTai"); // 平台订单

						// 查询订单详情中是否有这个订单号
						if (liststatus == null || liststatus.size() == 0) {
							platformOrderDetailsDao.insertOrder(platformorderdetails);
						} else if (liststatus != null) {
							List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
							if (listst == null || listst.size() == 0) {
								platformOrderDetailsDao.insertOrder(platformorderdetails);
							}
						}
						list.add(orderId); // 将订单号放进list
						String msg = ourSkuId + "_" + num; // 我们的sku和购买的商品数量

						if (orderMap.get(orderId) == null) {
							List<String> value = new ArrayList<String>();
							value.add(msg);
							orderMap.put(orderId, value);
						} else {
							orderMap.get(orderId).add(msg);
						}
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (orderMap.size() > 0) {
			list.add("-1"); // 避免没有数据 报错

			// 用放进list中的订单号查询订单表
			List<PlatFormOrderRecord> orderList = platformStockUpdateDao
					.getOrdersByOrders(list);
			Map<String, String> orderMapRealy = new HashMap<String, String>();
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
						// searchMap.put("type", "sh");
						searchMap.put("type", "hk");
						List<StockUpdate> stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						for (StockUpdate su : stockUpdateList) {
							su.setYinTaiOrderStock(num + su.getYinTaiOrderStock());
							su.setLastOrderTime("yes");
							platformStockUpdateDao.updateStockByNotNull(su);
							updateSuccess = true;
							updateNumber++;
						}
					}
				}
				if (updateSuccess) {
					PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
					oderRecord.setOrderId(orderId);
					oderRecord.setIdPlartform(13);// 银泰
					oderRecord.setIdStatus(1); // 已支付的订单
					platformStockUpdateDao.insertOrder(oderRecord);
				}
			}
		}
		return updateNumber;

	}

	// 先SHA1加密，在MD5加密
	private String getSignSha1AndMd5(String appkey, String secret,
			String method, String timestamp) {
		String sign = "";
		String params = "";

		params = "app_key" + "=" + app_key + "&" + "app_secret" + "="
				+ app_secret + "&" + "method" + "=" + method + "&ts" + "="
				+ timestamp;
		// System.out.println(params);

		String sha1 = sha1(params);
		// System.out.println(sha1+"    ===》》40个============sha1");

		String MD5 = md5(sha1);
		// System.out.println(MD5+"   ===》》32个+++++++++++++=MD5");
		sign = MD5;
		return sign;

	}

	private String sha1(String s) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA1");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString
						.append(String.format("%02x", 0xFF & messageDigest[i]));// !!小于16的的数字格式化
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String md5(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(txt.getBytes("UTF-8")); // 注意这里的编码
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest()) {
				buf.append(String.format("%02x", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	// 用于Unicode转换
	public static String decode2(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '\\' && chars[i + 1] == 'u') {
				char cc = 0;
				for (int j = 0; j < 4; j++) {
					char ch = Character.toLowerCase(chars[i + 2 + j]);
					if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
						cc |= (Character.digit(ch, 16) << (3 - j) * 4);
					} else {
						cc = 0;
						break;
					}
				}
				if (cc > 0) {
					i += 5;
					sb.append(cc);
					continue;
				}
			}
			sb.append(c);
		}
		return sb.toString();

	}
	/*
	 * public static void main(String[] args) {
	 * 
	 * YinTaiPlatFormStockUpdateImpl yt = new YinTaiPlatFormStockUpdateImpl();
	 * 
	 * int i = yt.atuoSyncOrder(); System.out.println(i); }
	 */
}
