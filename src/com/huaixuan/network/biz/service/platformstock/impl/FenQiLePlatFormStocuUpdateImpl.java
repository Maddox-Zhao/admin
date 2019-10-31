package com.huaixuan.network.biz.service.platformstock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
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
import com.huaixuan.network.biz.service.platformstock.FenQiLePlatFormStocuUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.network.melody.common.util.StringUtil;



/**
 * @author Mr_Yang   2016-11-29 上午11:19:37
 **/

@Service("fenQiLePlatFormStocuUpdate")
public class FenQiLePlatFormStocuUpdateImpl implements FenQiLePlatFormStocuUpdate
{


	private static final String SH_APP_KEY = "PAI201611300000019";
	private static final String SH_APP_SECRET = "375e562e44a85fddd68888c157d3c606";
	private static final String BASE_URL = "http://pop.api.fenqile.com/router/rest.json?";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String FQL_SKU_FILE_NAME= "fql_sku.txt";
	private Map<String,List<String>> repSkuMap = new HashMap<String, List<String>>(); //记录有重复sku的产品
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private  AutoSyncDao autoSyncDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	


    protected Log log = LogFactory.getLog(this.getClass());
	
	
	public FenQiLePlatFormStocuUpdateImpl()
	{
		repSkuMap = MiniUiUtil.readRpeatSku(FQL_SKU_FILE_NAME);
	}
	@Override
	public int atuoSyncOrder()
	{

		TreeMap<String, String> map = new TreeMap<String, String>();
		String method = "fenqile.trades.list.get";
		map.put("method", method);//调用方法
		map.put("status", "12");// 已下单 未发货
		String nowTime = sdf.format(new Date());
		
		Date now24 = new Date(new Date().getTime() - 2*24*60*60*1000);
		String now_24 = sdf.format(now24);
		
		map.put("begin_time", now_24);
		map.put("end_time", nowTime);
		
		map.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0,10));//调用时间
		//系统需要参数
		map.put("partner_id", SH_APP_KEY);
		map.put("format", "json");
		map.put("v", "1.1");
		map.put("page_size", "100");
		map.put("page", "1");
		map.put("fields", "order_id,create_time,wait_ship_time,product_name,price,amount,buyer_name,buyer_mobile,receiver,status,sku_id,goods_id,sku_num,spec");
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		String response = HttpRequest.sendPost(BASE_URL, map);
		Map<String,List<String>> orderMap = new HashMap<String,List<String>>(); //保存订单信息和对应的sku
		List<String> list = new ArrayList<String>(); //当前查询出来的订单id
		try
		{
			JSONObject objList = new JSONObject(response);
			JSONObject orderObj = new JSONObject(objList.getString("trades_list_get_response"));
			JSONArray orderList = orderObj.getJSONArray("trades");
			for(int i = 0; i < orderList.length();i++)
			{
				JSONObject skuObj = new JSONObject(orderList.get(i).toString());
				String orderId = skuObj.getString("order_id");//订单号
				String create_time = skuObj.getString("create_time");//下单时间
				String wait_ship_time = skuObj.getString("wait_ship_time");//支付时间
				String product_name = skuObj.getString("product_name");//商品名称
				String price = skuObj.getString("price");//商品价格
				String amount = skuObj.getString("amount");//订单价格
				String buyer_name = skuObj.getString("buyer_name");//收件人姓名
				String buyer_mobile = skuObj.getString("buyer_mobile");//收件l人手机
				String fqlSku = skuObj.getString("sku_id");//分期乐SKU
				String ourSku = skuObj.getString("goods_id");//我们的SKU
				String sku_num = skuObj.getString("sku_num");//商品数量
				String spec = skuObj.getString("spec");//商品规格
				String province= "";
				String city = "";
				String county="";
				String addresse ="";
				JSONObject receiver = skuObj.getJSONObject("receiver");
				JSONObject receiver_address = receiver.getJSONObject("receiver_address");
				province = receiver_address.getString("province");//省
				city = receiver_address.getString("city");//市
				county = receiver_address.getString("county");//区
				addresse = receiver_address.getString("address");//详细地址

					PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
					platformorderdetails.setIdorder(orderId);
					List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
						platformorderdetails.setIdorder(orderId);
						platformorderdetails.setPalcedTime(create_time);
						platformorderdetails.setPayTime(wait_ship_time);
						platformorderdetails.setName(buyer_name);
						platformorderdetails.setMobile(buyer_mobile);
						platformorderdetails.setProvince(province);
						platformorderdetails.setCity(city);
						platformorderdetails.setCountry(county);
						platformorderdetails.setStreetAddress(addresse);
						platformorderdetails.setTotalPrice(amount);
						platformorderdetails.setSkuId(fqlSku);
						platformorderdetails.setMerchantSkuId(ourSku);
						platformorderdetails.setQuantity(sku_num);
						platformorderdetails.setProductname(product_name);
						platformorderdetails.setSize(spec);
						platformorderdetails.setPrice(price);
						platformorderdetails.setPtype("fql");
						if ((liststatus == null || liststatus.size() == 0)) {
							platformOrderDetailsDao.insertOrder(platformorderdetails);

						} else if (liststatus != null) {
							List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
							if (listst == null || listst.size() == 0) {
								platformOrderDetailsDao.insertOrder(platformorderdetails);
							}
						}
				list.add(orderId);
				String str  = fqlSku + ";"+ourSku + ";" + sku_num;
				if(orderMap.get(orderId) == null)
				{
					List<String> skuList = new ArrayList<String>(); //当前产品List
					skuList.add(str);
					orderMap.put(orderId, skuList);
				}
				else
				{
					orderMap.get(orderId).add(str);
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
		int updateNumber = 0;
		//如果有数据
		if(orderMap.size() > 0)
		{
			list.add("-1");//避免没有数据  报错
			List<PlatFormOrderRecord>  kaolaOrderList = platformStockUpdateDao.getOrdersByOrders(list);
			//用作对比 如果有记录则说明已处理
			Map<String,String> fqlOrderMapRealy = new HashMap<String, String>();
			for(PlatFormOrderRecord k : kaolaOrderList)
			{
				fqlOrderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Entry<String,List<String>>> it = set.iterator();
			while(it.hasNext())
			{
				Entry<String,List<String>> entry = it.next();
				String orderId  = entry.getKey();
				List<String> value = entry.getValue();
				if(fqlOrderMapRealy.get(orderId) == null) //没有保存该orderid 没处理  增加orderid数 并插入表
				{
					boolean updateSucces = false;
			 
					for(int i =0 ; i < value.size(); i++)
					{
						String str = value.get(i);
						if(StringUtil.isNotBlank(str))
						{
							String[] arr = str.split(";"); 
							if(arr.length == 3)
							{
								String fqlSku = arr[0].trim();
								String ourSku = arr[1].trim();
								Integer number = Integer.valueOf(arr[2].trim());
								Map<String,String> searchMap = new HashMap<String, String>();
								searchMap.put("sku", ourSku);
								searchMap.put("type", "sh");
								if(StringUtil.isBlank(ourSku) || StringUtil.isBlank(fqlSku)) continue;
								List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
								for(StockUpdate su : stockUpdateList)
								{
									Map<String,String> searchMaps = new HashMap<String, String>();
									searchMaps.put("sku", ourSku);
									searchMaps.put("type", "hk");
									boolean updatesh = true;
									List<StockUpdate>  stockUpdateLists = platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
									if(stockUpdateLists != null && stockUpdateLists.size() > 0){
										int  s = su.getNowStockNum()-su.getOrderStockNum();
										if(s <=0 ){
												for(StockUpdate ss : stockUpdateLists){
													ss.setFqlOrderStock(number + ss.getFqlOrderStock());
													ss.setLastOrderTime("yes");
													platformStockUpdateDao.updateStockByNotNull(ss);
													updateSucces = true;
													updatesh = false;
													updateNumber++;
												}
											}
										}
									if(updatesh){
									su.setFqlOrderStock(number + su.getFqlOrderStock());
									su.setLastOrderTime("yes");
									platformStockUpdateDao.updateStockByNotNull(su);
									updateNumber++;
									updateSucces = true;
									}
								}
								
							}
						}
					}
					if(updateSucces)
					{
						//记录该orderid
						PlatFormOrderRecord kaolaOrder = new PlatFormOrderRecord();
						kaolaOrder.setOrderId(orderId);
						kaolaOrder.setIdPlartform(7);//id-7  分期乐
						kaolaOrder.setIdStatus(1);
						platformStockUpdateDao.insertOrder(kaolaOrder);
						

						Integer number = 0;
						String ourSku = "";
						for(int i =0 ; i < value.size(); i++)
						{
							String str = value.get(i);
							if(StringUtil.isNotBlank(str))
							{
								String[] arr = str.split(";"); 
								if(arr.length == 3)
								{
									String fqlSku = arr[0].trim();
									ourSku = arr[1].trim();
									number = Integer.valueOf(arr[2].trim());
									
								}
							}
						}
					}
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
		searchMap.put("skuisnotnull", "fql_sku");
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
			for(StockUpdate hks : hkList)
			{
				int cnt = hks.getNowStockNum() - hks.getOrderStockNum();
				if(cnt < 0 ) cnt = 0;
				hkNum += cnt;
			}
			num += hkNum;
			if(num < 0) continue;
			updateFenQiLeStock(s.getFqlSku(), s.getSku(), num, s.getType());
			//更新一次休息0.5秒
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
	public boolean updateFenQiLeStock(String fqlSku, String ourSku, int num,String type)
	{
		if(StringUtil.isBlank(fqlSku) && StringUtil.isBlank(ourSku)) return true;
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("fqlSku", fqlSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(fqlSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "fql_sku");
			fqlSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		
		boolean flag = false;
		if(repSkuMap.get(ourSku) != null)
		{
			List<String> fqlSkusList = repSkuMap.get(ourSku);
			for(String felSku : fqlSkusList)
			{
				flag = updateFqlStock(felSku,ourSku,num,type);
			}
		}
		else
		{
			flag = updateFqlStock(fqlSku,ourSku,num,type);
		}
		
		return flag;
		
		
	}
	
	private boolean updateFqlStock(String fqlSku,String ourSku,int num,String type)
	{
		if(StringUtil.isBlank(fqlSku)) return true;
		String method = "fenqile.sku.update.quantity";
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		map.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0,10));//调用时间
		//系统需要参数
		map.put("partner_id", SH_APP_KEY);
		map.put("format", "json");
		map.put("v", "1.1");
		map.put("sku_id", fqlSku);
		map.put("type", "1"); //全量更新
		map.put("quantity", num+"");
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		String response = HttpRequest.sendPost(BASE_URL, map);
		 
		
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", fqlSku);
		logMap.put("name", "fql");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		boolean flag = false;
		try
		{
			JSONObject json  = new JSONObject(response);
			json.get("sku_update_quantity_response");
			flag = true;
		}
		catch (JSONException e)
		{
			logMap.put("type", "error");
		    logMap.put("error",response);
		    log.error(e.getMessage(), e);
		}
		
		//记录日志
		autoSyncDao.addUpdateLog(logMap);
		
		return flag;
	}

	@Override
	public void updateSku2Location()
	{
		//清空文件
		MiniUiUtil.writeText2File(FQL_SKU_FILE_NAME, "", "cx");
		
		updateSku2LocationByStatus("13");//已下架
		updateSku2LocationByStatus("10");//已上架
		updateSku2LocationByStatus("0");//即将上架
		
		repSkuMap = MiniUiUtil.readRpeatSku(FQL_SKU_FILE_NAME);
 

	}
	
	private void updateSku2LocationByStatus(String status)
	{
		String method = "fenqile.sku.list.get";
		int pageNO = 1;	
		int pageSize = 40;
		int errorCnt = 1;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		//系统需要参数
		map.put("partner_id", SH_APP_KEY);
		map.put("format", "json");
		map.put("v", "1.1");
		map.put("status", status);
		StringBuilder sb = new StringBuilder(); //记录sku对应关系
		while(true)
		{
			map.remove("sign");
			//应用参数
			Date nowDate = new Date();
			String endTime = sdf.format(nowDate);
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(nowDate);//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_YEAR, -90);  //设置为前3月
			Date start_time = calendar.getTime();   //得到前3月的时间

			map.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0,10));//调用时间
			map.put("begin_time", sdf.format(start_time));  
			map.put("end_time", endTime); 
			map.put("page_size", pageSize+""); //每页条数
			map.put("page", pageNO+""); //当前页
		

			map.put("fields",  "sku_id,product_id,product_name,goods_id"); 
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			pageNO++;
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(BASE_URL, map);
//			System.out.println(response);
			try
			{
				List<PlatformSku2LocationSku> fqlSkuList = new ArrayList<PlatformSku2LocationSku>();
				JSONObject jsonObj = new JSONObject(response);
				JSONObject obj = new JSONObject(jsonObj.getString("sku_list_get_response"));
				JSONArray array = obj.getJSONArray("items");
				
				for(int i =0 ; i < array.length(); i++)
				{
					String itemStr = array.get(i).toString();
					JSONObject itemObj = new JSONObject(itemStr);
					String fqlSku = itemObj.getString("sku_id");
					String ourSku = itemObj.getString("goods_id");
					if(StringUtil.isNotBlank(ourSku) && StringUtil.isNotBlank(ourSku))
					{
						//同步sku到本地,单个更新使用, 批量更新 分期暂时只有国内
						PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
						pslSku.setOurSku(ourSku);
						pslSku.setPlatformField("fql_sku");
						pslSku.setPlatformSku(fqlSku);
						pslSku.setType("sh"); 
						fqlSkuList.add(pslSku);
						
						
						sb.append(ourSku);
						sb.append(",");
						sb.append(fqlSku);
						sb.append("\n");
					}
				}
				
				Map fqlSkuMap = new HashMap();
				fqlSkuMap.put("list", fqlSkuList);
				fqlSkuMap.put("platformField", "fql_sku");
 
				
				if(fqlSkuList.size() > 0)
				{
					//更新分期乐sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(fqlSkuMap);
				}
				
				// 跳出条件
				if(!obj.getBoolean("has_next")) break;

				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("fenqile syn sku error " + errorCnt);
				if(errorCnt++ > 5) break;
			}
		}
		
		MiniUiUtil.writeText2File(FQL_SKU_FILE_NAME, sb.toString(), "zj");
	}
	
	/**
	 * 获取签名 签名规则见文档
	 * @param requestMap
	 * @return
	 */
	private String getSign(Map<String,String> requestMap)
	{
		Set<Entry<String, String>> entrySet  =  requestMap.entrySet();
		Iterator<Entry<String,String>> it = entrySet.iterator();
		String needMD5Str = "";
		while(it.hasNext())
		{
			Entry<String,String> en = it.next();
			needMD5Str += en.getKey() +"="+ en.getValue()+"&";
		}
		needMD5Str += "partner_key=" + SH_APP_SECRET;
		return HttpRequest.string2MD5(needMD5Str).toLowerCase();
	}

	
	
	public static void main(String[] args)
	{
		FenQiLePlatFormStocuUpdateImpl fq = new FenQiLePlatFormStocuUpdateImpl();
		
	
	}
}
 
