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
import com.huaixuan.network.biz.service.platformstock.SiKuPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-5-11 下午12:07:04
 **/

@Service("siKuPlatFormStockUpdate")
public class SiKuPlatFormStockUpdateImpl implements SiKuPlatFormStockUpdate
{
//	private static final String APPKEY_HK = "3095";
//	private static final String SECRET_HK = "4db0f8b0fc895da263fd77fc8aecabe4";
//	
	private static final String APPKEY_HK = "1767";
	private static final String SECRET_HK = "e8fd4a8a5bab2b3785d794ab51fef55c";
	
	private static final String APPKEY_SH = "1922";
	private static final String SECRET_SH = "333222170ab9edca4785c39f55221fe7";
	private static final String SENDPOSTURL = "http://api.pop.secoo.com/rest?";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	protected Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * @param map  map 根据字典排序 
	 * 签名规则 见考拉文档
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
			if(en.getKey().equals("secret") || en.getKey().equals("sign") ) continue; //密码和签名项不加入签名算法
				needMD5Str += en.getKey() + en.getValue();
		}
		needMD5Str+= map.get("secret");
		
		
		sign = HttpRequest.string2MD5(needMD5Str).toUpperCase();
		return sign;
	}

	@Override
	public void updateSku2Location()
	{
		platformStockUpdateDao.updateStockUpdateSku2Null("siku_on_sale_status");
        
		updateSku2LocationByType("hk");
		
		updateSku2LocationByType("sh");

	}
	private  void updateSku2LocationByType(String type){
		String method = "secoo.goods.list";
		int pageNO = 1;	
		int pageSize = 20;
		/*int pageNO = 200;	
		int pageSize = 40;*/
		
		int errorCnt = 1;
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		if("sh".equals(type))
		{
			map.put("vendorCode", APPKEY_SH);
			map.put("secret", SECRET_SH);
		}
		else
		{
			map.put("vendorCode", APPKEY_HK);
			map.put("secret", SECRET_HK);
		}
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		
		

		while(true)
		{
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("checkStatus", "2"); //审核通过
			
			map.put("pageNumber", pageNO+""); //当前页
			map.put("pageSize", pageSize+""); //每页条数
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);	
			
			pageNO++;
//			System.out.println(pageNO);
//			System.out.println(response);
			try {
				JSONObject jsonObj = new JSONObject(response);
				JSONObject dataResponse = new JSONObject(jsonObj.get("data").toString());
				JSONArray jsonArray = dataResponse.getJSONArray("datas");
				List<PlatformSku2LocationSku> sikuskuList = new ArrayList<PlatformSku2LocationSku>();
				for(int i = 0 ;i < jsonArray.length();i++)
				{

					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					String productId = productObj.getString("id"); //寺库的sku 
					
					if(!jsonArray.get(i).toString().contains("outerSkuId")){
						continue;
					}
					
					String outerSkuId =  productObj.getString("outerSkuId"); //我们的sku 
					String sellStatus =  productObj.getString("sellStatus"); //商品的上下架状态
					if(productId != null){
						productId = productId.trim();
					}else{
						continue;
					}
					if(outerSkuId != null){
						outerSkuId = outerSkuId.trim();
					}else{
						continue;
					}
	
					if(sellStatus != null){
						sellStatus = sellStatus.trim();
						if(!sellStatus.equals("1")){
							sellStatus="0";   //在我們數據庫1是上架，0是下架
						}
					}else{
						continue;
					}
					if(StringUtil.isNotBlank(productId) && StringUtil.isNotBlank(outerSkuId) && StringUtil.isNotBlank(sellStatus)){
						PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
						pslsku.setPlatformSku(productId);
						pslsku.setOurSku(outerSkuId);
						pslsku.setPlatformField("siku_sku");
						pslsku.setPlatformstatus("siku_on_sale_status");
						pslsku.setStatus(sellStatus);
						pslsku.setType(type);
						sikuskuList.add(pslsku);
					}else{
						continue;
					}
					
					
				}
				Map sikuSkuMap = new HashMap();
				sikuSkuMap.put("list", sikuskuList);
				sikuSkuMap.put("platformField", "siku_sku");
				sikuSkuMap.put("platformstatus", "siku_on_sale_status");
				if(sikuskuList.size() > 0){
					platformStockUpdateDao.batchUpdatePlatformSku2Location(sikuSkuMap);
				}				
				if(dataResponse.getBoolean("isLastPage"))break;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("siku syn sku error " + pageNO+" 页");
				if(errorCnt++ > 5) break; //错误5次 跳出
			}
			
		}
	}
		
		
		
		
	//全量更新库存
	private void updateCanSaleProductByType(String type)
	{		
		String method = "secoo.goods.list";
		int pageNO = 1;	
		int pageSize = 20;
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		if("sh".equals(type))
		{
			map.put("vendorCode", APPKEY_SH);
			map.put("secret", SECRET_SH);
		}
		else
		{
			map.put("vendorCode", APPKEY_HK);
			map.put("secret", SECRET_HK);
		}
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		
		//查询当前可售库存
		Map<String,StockUpdate> canSaleStockMap = new HashMap<String, StockUpdate>(); //key为我们sku  value为当前sku可售库存
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("skuisnotnull", "siku_sku");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate su : list)
		{
			if(StringUtil.isNotBlank(su.getSikuSku()))
			{
				canSaleStockMap.put(su.getSikuSku(), su);
			}
		}

		while(true)
		{
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("checkStatus", "2"); //审核通过
			
			map.put("pageNumber", pageNO+""); //当前页
			map.put("pageSize", pageSize+""); //每页条数
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);			
			pageNO++;
			try
			{
				JSONObject jsonObj = new JSONObject(response);
				JSONObject dataResponse = new JSONObject(jsonObj.get("data").toString());
				JSONArray jsonArray = dataResponse.getJSONArray("datas");
				for(int i = 0 ;i < jsonArray.length();i++)
				{

					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					String productId = productObj.getString("id");
					String ourSku = "";
					StockUpdate stock = canSaleStockMap.get(productId);
					int nowNum = 0;
					if(null != stock)
					{
						nowNum = stock.getNowStockNum() - stock.getOrderStockNum();
						if(nowNum < 0 ) nowNum = 0;
						ourSku = stock.getSku();
					}
					if(StringUtil.isBlank(ourSku)) continue;//没有我们的sku 不做更新
			
					/*
					//不能小于冻结库存
					StockNumber sn = getSikuStock(productId, type);
					if(sn.getFreezeNum() > nowNum)
					{
						nowNum = sn.getFreezeNum();
					}
					*/
					
					
					boolean flag = updateSikuStock(productId, ourSku, nowNum, type);
					//更新成功 设置上次更新到平台库存数,防止单个更新的在做更新
					if(flag && StringUtil.isNotBlank(ourSku))
					{
						StockUpdate stockUpdate = new StockUpdate();
						stockUpdate.setType(type);
						stockUpdate.setSku(ourSku);
						stockUpdate.setLastUpdateStockNum(nowNum);
						//platformStockUpdateDao.updateLastUpdateStockBySkuAndTypeForOne(stock);  暂时不做更新 防止这里更新了其他平台更新不到
					}		 
					
				}
				
				//当前页为最后一页 中断
				
				if(dataResponse.getBoolean("isLastPage"))break;

				
			}
			catch (Exception e)
			{
				log.error(e.getMessage());
				if(pageNO > 500) break;
			}
		}
		
		System.out.println(sdf.format(new Date()) + " siku " + type);
	}
	
	
	
	@Override
	public StockNumber getSikuStock(String siKuProductId, String type)
	{
		String method = "secoo.goods.stock.get";
		StockNumber  sn = null;
		if(StringUtil.isBlank(siKuProductId)) 
		{
			return sn;
		}
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		if("sh".equals(type))
		{
			map.put("vendorCode", APPKEY_SH);
			map.put("secret", SECRET_SH);
		}
		else
		{
			map.put("vendorCode", APPKEY_HK);
			map.put("secret", SECRET_HK);
		}
		
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("productId", siKuProductId);  
		
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);
		try
		{
			JSONObject jsonObj = new JSONObject(response);
			JSONObject dataResponse = new JSONObject(jsonObj.get("data").toString());
			int totalQty = dataResponse.getInt("qty");//总库存
			int canSaleQty = dataResponse.getInt("sellQty"); //可售库存
			int freezeQty = dataResponse.getInt("freezeQty"); //冻结库存
			sn = new StockNumber();
			sn.setCanSaleNum(canSaleQty);
			sn.setFreezeNum(freezeQty);
			sn.setTotalNum(totalQty);
			sn.setPlatformSku(siKuProductId);
			return sn;
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
		}
		
		return sn;
	}
	
	
	
	@Override
	public void updateAllStock()
	{
		updateCanSaleProductByType("hk");
		
		
		updateCanSaleProductByType("sh");
	}
	
	
	//库存有变化，更新平台库存
	public boolean updateSikuStock(String sikuProductId,String ourSku,int num,String type)
	{
		if(StringUtil.isBlank(sikuProductId) && StringUtil.isBlank(ourSku)) return true; //没有寺库id 返回
		Map<String,String> searchMap = new HashMap<String, String>();
		
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("sikuSku", sikuProductId);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(sikuProductId))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "siku_sku");
			sikuProductId = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(sikuProductId)) return true;
		/*
		StockNumber  s = getSikuStock(sikuProductId, type);
		if(null != s)
		{
			if(num < s.getFreezeNum()) num = s.getFreezeNum();//不能小于冻结库存
		}
		*/
 
	
		
		String method = "secoo.goods.stock.update";
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		
		if("sh".equals(type))
		{
			map.put("vendorCode", APPKEY_SH);
			map.put("secret", SECRET_SH);
		}
		else
		{
			map.put("vendorCode", APPKEY_HK);
			map.put("secret", SECRET_HK);
		}
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("productId", sikuProductId);  
		map.put("stock", num+""); //当前库存
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);
		
		 
		boolean  flag = true;
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", sikuProductId);
		logMap.put("name", "siku");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		try
		{
			JSONObject jsonObj = new JSONObject(response);
			if("200".equals(jsonObj.getString("code")))
			{
				//更新成功
				flag = true;
			}
			else
			{
				 String codeMsg = jsonObj.getString("subCode");
				 logMap.put("type", "error");
				 logMap.put("error",codeMsg);
				// flag = true;
			}
		}
		catch (Exception e)
		{
			 logMap.put("type", "error");
			 logMap.put("error",response);
			 log.error(e.getMessage());
		}

		//记录更新日志
		autoSyncDao.addUpdateLog(logMap);
		return flag;
	}
	

	@Override
	public void updateSku2LocationByFile(Map<String,String> keyMap,String type)
	{
		 Set<Entry<String,String>> keySet= keyMap.entrySet();
		 Iterator<Entry<String,String>> it = keySet.iterator();
		 while(it.hasNext())
		 {
			 Entry<String,String> entry = it.next();
			 String sikuSku = entry.getKey();
			 String ourSKu = entry.getValue();
			 if(StringUtil.isBlank(sikuSku) || StringUtil.isBlank(ourSKu)) continue;
			 sikuSku = sikuSku.trim();
			 ourSKu = ourSKu.trim();
			 List<PlatformSku2LocationSku> list = new ArrayList<PlatformSku2LocationSku>();
			//同步sku到本地,单个更新使用, 批量更新
			PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
			pslSku.setOurSku(ourSKu);
			pslSku.setPlatformField("siku_sku");
			pslSku.setPlatformSku(sikuSku);
			pslSku.setType(type);  
			list.add(pslSku);
			
			Map shangpinSkuMap = new HashMap();
			shangpinSkuMap.put("list", list);
			shangpinSkuMap.put("platformField", "siku_sku");
			if(list.size() > 0)
			{
				//更新尚品sku到本地
				platformStockUpdateDao.batchUpdatePlatformSku2Location(shangpinSkuMap);
			}
		 }
		
	}
	
	
 
	
	

	@Override
	public  int atuoSyncOrder(String type)
	{
		String method = "secoo.order.reserve";
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		if("sh".equals(type))
		{
			map.put("vendorCode", APPKEY_SH);
			map.put("secret", SECRET_SH);
		}
		else
		{
			map.put("vendorCode", APPKEY_HK);
			map.put("secret", SECRET_HK);
		}
		
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.0"); 
		 
		Map<String,String> orderMap = new HashMap<String, String>(); //保存订单信息和对应的sku
		List<String> list = new ArrayList<String>(); //当前查询出来的订单id
		Date now_24 = new Date(new Date().getTime() - 24*60*60*1000); //1天内的订单
		String startTimeStr = sdf.format(now_24);
		String endTimeStr = sdf.format(new Date());
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("startTime", startTimeStr);  
		map.put("endTime", endTimeStr); 
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项

		//发送POST请求 获取数据
		String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);
		//System.out.println(response);

		Map<String,String> searchMap = new HashMap<String, String>();
		int updateNumber = 0;
		try
		{
			JSONObject jsonObject = new JSONObject(response);
			if("200".equals(jsonObject.getString("code")))
			{
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for(int i = 0; i < jsonArray.length();i++)
				{
					
					JSONObject orderInfo = new JSONObject(jsonArray.get(i).toString());
					JSONArray skuList =  orderInfo.getJSONArray("skuList");
					String orderId = orderInfo.getString("orderId");
					list.add(orderId); 
					orderMap.put(orderId, skuList.toString());
				}
			}
			
			//如果有数据
			if(orderMap.size() > 0)
			{
				list.add("-1");//避免没有数据  报错
				List<PlatFormOrderRecord>  sikuOrderList = platformStockUpdateDao.getOrdersByOrders(list);
				//用作对比 如果有记录则说明已处理
				Map<String,String> sikuOrderMapRealy = new HashMap<String, String>();
				for(PlatFormOrderRecord k : sikuOrderList)
				{
					sikuOrderMapRealy.put(k.getOrderId(), "yes");
				}
				Set<Entry<String, String>> set = orderMap.entrySet();
				Iterator<Entry<String,String>> it = set.iterator();
				while(it.hasNext())
				{
					Entry<String,String> entry = it.next();
					String orderId  = entry.getKey();
					String value = entry.getValue();
					
					PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
					platformorderdetails.setIdorder(orderId);
					List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
					
					if(orderId != null){
						String methods = "secoo.order.get";
						TreeMap<String, String> maps = new TreeMap<String, String>();
						//系统需要参数
						if("sh".equals(type))
						{
							maps.put("vendorCode", APPKEY_SH);
							maps.put("secret", SECRET_SH);
						}
						else
						{
							maps.put("vendorCode", APPKEY_HK);
							maps.put("secret", SECRET_HK);
						}
						String timestamp = sdf.format(new Date());
						maps.put("timestamp", timestamp);//调用时间d
						maps.put("signMethod", "md5");
						maps.put("format", "json");
						maps.put("method", methods);//调用方法
						maps.put("v", "1.1"); 
						maps.put("orderId", orderId);
						String signs = getSign(maps);
						maps.put("sign", signs);//map中放入了签名 第二次调用签名的时候剔除sign项
						String responses = HttpRequest.sendPostNoSecret(SENDPOSTURL, maps);
//						System.out.println(responses);
						try {
							JSONObject jsonObjects = new JSONObject(responses);
							if("200".equals(jsonObjects.getString("code")))
							{
								JSONObject datas = new JSONObject(jsonObjects.getString("data"));
								String id = datas.getString("id");//订单编号
//								String receiverName = datas.getString("receiverName");//接收人姓名
//								String receiverAddress = datas.getString("receiverAddress");//接收人地址
//								String receiverMobile = datas.getString("receiverMobile");//接收人手机
//								String receiverProvince = datas.getString("receiverProvince");//省
//								String receiverCity = datas.getString("receiverCity");//市
//								String receiverArea = datas.getString("receiverArea");//区
								String createDate = datas.getString("createDate");//下单时间
								JSONArray items =  datas.getJSONArray("items");
								for (int  i= 0; i < items.length(); i++) {
									JSONObject goods = new JSONObject(items.get(i).toString());
									String merchantSkuId = goods.getString("outerSkuId");//我们的SKU
									String productName = goods.getString("productName");//商品名称
									String quantity = goods.getString("quantity");//商品数量
//									String totalPrice = goods.getString("secooPrice");//商品结算价
									String price = goods.getString("settlePrice");//售价
									
										platformorderdetails.setIdorder(id);
										platformorderdetails.setMerchantSkuId(merchantSkuId);
										platformorderdetails.setPalcedTime(createDate);
//										platformorderdetails.setName(receiverName);
//										platformorderdetails.setMobile(receiverMobile);
//										platformorderdetails.setProvince(receiverProvince);
//										platformorderdetails.setCity(receiverCity);
//										platformorderdetails.setDistrict(receiverArea);
//										platformorderdetails.setStreetAddress(receiverAddress);
//										platformorderdetails.setTotalPrice(totalPrice);
										platformorderdetails.setMerchantSkuId(merchantSkuId);
										platformorderdetails.setQuantity(quantity);
										platformorderdetails.setProductname(productName);
										platformorderdetails.setPrice(price);
										platformorderdetails.setPtype("siku");
										if ((liststatus == null || liststatus.size() == 0)) {
											platformOrderDetailsDao.insertOrder(platformorderdetails);

										} else if (liststatus != null) {
											List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
											if (listst == null || listst.size() == 0) {
												platformOrderDetailsDao.insertOrder(platformorderdetails);
											}
										}
								
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage(), e);
						}
					}
					if(sikuOrderMapRealy.get(orderId) == null) //没有保存该orderid 没处理  增加orderid数 并插入表
					{
						JSONArray  orderSkus = new JSONArray(value);
						for(int index = 0; index < orderSkus.length(); index++)
						{
							JSONObject skuObj = new JSONObject(orderSkus.get(index).toString());
							String ourSku = skuObj.getString("outerSkuId");
							int num = skuObj.getInt("quantity");
							if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
							ourSku = ourSku.trim();
							if(ourSku.length() != 13) //sku不对 记录  用做修改
							{
								//HttpRequest.fileAddContext("siku_oursku_error.txt",ourSku);
								continue;
							}
							searchMap.put("type", type);
							searchMap.put("sku", ourSku);
							List<StockUpdate>  stockList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
							for(StockUpdate su : stockList)
							{
								su.setSikuOrderStock(num + su.getSikuOrderStock());
								su.setLastOrderTime("yes");
								platformStockUpdateDao.updateStockByNotNull(su);//更新订单数
								updateNumber++;
							}
						}
						//记录该orderid
						PlatFormOrderRecord kaolaOrder = new PlatFormOrderRecord();
						kaolaOrder.setOrderId(orderId);
						kaolaOrder.setIdPlartform(2);
						kaolaOrder.setIdStatus(1);
						platformStockUpdateDao.insertOrder(kaolaOrder);
						int num = 0;
						String ourSku = "";
						for(int index = 0; index < orderSkus.length(); index++)
						{
							JSONObject skuObj = new JSONObject(orderSkus.get(index).toString());
							ourSku = skuObj.getString("outerSkuId");
							num = skuObj.getInt("quantity");
							if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
							ourSku = ourSku.trim();
							if(ourSku.length() != 13) //sku不对 记录  用做修改
							{
								//HttpRequest.fileAddContext("siku_oursku_error.txt",ourSku);
								continue;
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("請求失敗,寺库");
			log.error(e.getMessage());
		}
		return updateNumber;
		
	}


	//测试查询订单
	public  void getOrder(String type,String orderid)
	{
		String method = "secoo.order.get";
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		if("sh".equals(type))
		{
			map.put("vendorCode", APPKEY_SH);
			map.put("secret", SECRET_SH);
		}
		else
		{
			map.put("vendorCode", APPKEY_HK);
			map.put("secret", SECRET_HK);
		}
		
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.0"); 
		 
		 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("orderId", orderid);  
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项

		//发送POST请求 获取数据
		String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);
//		System.out.println(response);

		
		
		
	}
	public static void main(String[] args)
	{
		SiKuPlatFormStockUpdateImpl si = new SiKuPlatFormStockUpdateImpl();
		si.getOrder("sh", "6032270622099");
	}
	 

}
 
