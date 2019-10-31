package com.huaixuan.network.biz.service.autosyn.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.print.resources.serviceui;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.autosyn.SiKuSyncManager;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-4-13 下午12:06:33
 * 同步寺库库存 弃用
 **/


public class SiKuSyncManagerImpl implements SiKuSyncManager
{

	private static final String APPKEY = "1434";
	private static final String SECRET = "123456";
	private static final String SENDPOSTURL = "http://testapi.pop.secoo.com:8888/rest?";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Map<String,String> sh_sikuSku2OurSkuMap = new HashMap<String, String>();//key为寺库sku value为我们sku
	private static Map<String,String> sh_ourSku2sikuSkuMap = new HashMap<String, String>();//key为我们sku value为寺库sku
	
	
	private static Map<String,String> hk_sikuSku2OurSkuMap = new HashMap<String, String>();//key为寺库sku value为我们sku
	private static Map<String,String> hk_ourSku2sikuSkuMap = new HashMap<String, String>();//key为我们sku value为寺库sku
	
	private static final String path = "d:/stock_log/";//记录库存的日志文件夹
 
	
	@Autowired
	private AutoSyncDao autoSyncDao; 
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDao productDao;
	
	public SiKuSyncManagerImpl()
	{
		//getOrderInfoForSuoku();
	}
	
	@Override
	public Map<Integer,Integer> getSiKuStockBySiKuSku(String type, String siKuProductId)
	{
		String method = "secoo.goods.stock.get";
		Map<Integer,Integer> resultMap = new HashMap<Integer, Integer>();
		if(StringUtil.isBlank(siKuProductId)) 
		{
			resultMap.put(0, 0);
			return resultMap;
		}
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		map.put("vendorCode", APPKEY);
		map.put("secret", SECRET);
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
		System.out.println(response);

		try
		{
			JSONObject jsonObj = new JSONObject(response);
			JSONObject dataResponse = new JSONObject(jsonObj.get("data").toString());
			int totalQty = dataResponse.getInt("qty");//总库存
			//int canSaleQty = dataResponse.getInt("sellQty"); //可售库存
			int freezeQty = dataResponse.getInt("freezeQty"); //冻结库存
			resultMap.put(totalQty, freezeQty);
		}
		catch (JSONException e)
		{
			resultMap.put(0, 0);
		}
		
		return resultMap;
	}

	@Override
	public boolean updateStockByOurSkuAndPlatFormSku(String type, 
			String platformSku, String ourSku, int nowNum, String idProduct)
	{
		if("sh".equals(type))
		{
			platformSku = sh_ourSku2sikuSkuMap.get(ourSku);
		}
		else
		{
			platformSku = hk_ourSku2sikuSkuMap.get(ourSku);
		}
 
		if(StringUtil.isBlank(platformSku)) return true;
		
		Map<Integer,Integer> sikuStockMap = getSiKuStockBySiKuSku(type, platformSku);
		Set<Integer> set = sikuStockMap.keySet();
		Iterator<Integer> it  = set.iterator();
		Integer totalQty = it.next();
		Integer freezeQty = sikuStockMap.get(totalQty);//冻结库存
 
		//if(0 == totalQty) return true; //寺库已经为0了 不做更新
	 
		//如果寺库平台的库存大于我们本地库存 直接更新为我们的库存（比我们多 以我们为主）
		//如果寺库平台的库存小于等于我们的库存 做判断 如果客户不是寺库 则在寺库库存的基础上在减锁定库存在减少1  如果是开给寺库的则不做更新
		if(totalQty < nowNum)
		{
			Product product = productService.getproduct(idProduct);
			if(!product.getCustomerName().contains("寺库"))//不是开给司库的 其他平台或者线下卖掉了 或者预留的 则在寺库库存的基础上减锁定库存在减少1
			{
				//更新库存为寺库当前总库存减1
				nowNum = totalQty-1;
			}
			else
			{
				//nowNum = nowNum;
				//return true;//是开给寺库的,不做更新1
			}
		}

		
		if(nowNum < freezeQty) nowNum = freezeQty; //当前可售不能小于冻结数
		if(nowNum < 0) nowNum = 0; //库存数不能小于0
		
		
		return updateSiKuStock(type,platformSku,ourSku,nowNum);
	}
	
	
	@Override
	public void updateCanSaleProduct()
	{
		updateCanSaleProductByType("hk");
	}
	

	private void updateCanSaleProductByType(String type)
	{
		setSiku2OurSkuMatch(type); //先设置sku对应关系
		
		String method = "secoo.goods.list";
		int pageNO = 1;	
		int pageSize = 20;
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		map.put("vendorCode", APPKEY);
		map.put("secret", SECRET);
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		
 
		List<Long> siteList = new ArrayList<Long>();
		if("hk".equals(type))
		{
			//香港账号
			//获取可售库存所需站点信息
			for(String idSite : MiniUiUtil.hkSite)
			{
				siteList.add(Long.valueOf(idSite));
			}
		}
		else
		{
			//获取可售库存所需站点信息
			for(String idSite : MiniUiUtil.shSite)
			{
				siteList.add(Long.valueOf(idSite));
			}
		}

		List<StockData> stockDataList = autoSyncDao.searchStockBySiteList(siteList);
		Map<String,StockData> canSaleStockMap = new HashMap<String, StockData>(); //key为我们sku  value为当前sku可售库存
		for(StockData s : stockDataList)
		{
			canSaleStockMap.put(s.getSku(), s);
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

			try
			{
				JSONObject jsonObj = new JSONObject(response);
				JSONObject dataResponse = new JSONObject(jsonObj.get("data").toString());
				JSONArray jsonArray = dataResponse.getJSONArray("datas");
				for(int i = 0 ;i < jsonArray.length();i++)
				{

					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					String productId = productObj.getString("id");
					
					//通过sikuProductid获取我们的sku  
					String ourSku = "";
					if("sh".equals(type))
					{
						ourSku = sh_sikuSku2OurSkuMap.get(productId);
					}
					else
					{
						ourSku = hk_sikuSku2OurSkuMap.get(productId);
					}
					
					//如果没有获取到我们的sku  不做更新
					if(StringUtil.isBlank(ourSku)) continue;
					
					Map<Integer,Integer> stockMap = getSiKuStockBySiKuSku(type, productId);
					
					Set<Integer> set = stockMap.keySet();
					Iterator<Integer> it  = set.iterator();
					Integer totalQty = it.next();
					Integer freezeQty = stockMap.get(totalQty);//冻结库存
					Integer ourStockNum = 0 ;
					StockData sd  = canSaleStockMap.get(ourSku);
					if(sd != null)
					{
						ourStockNum = sd.getNum();
					}
					ourStockNum = ourStockNum - freezeQty;//当前库存-寺库冻结库存
					
					if(ourStockNum < freezeQty) ourStockNum = freezeQty;
					
					updateSiKuStock(type,productId,ourSku,ourStockNum);
					
				}
				
				//当前页为最后一页 中断
				pageNO++;
				if(dataResponse.getBoolean("isLastPage"))break;

				
			}
			catch (JSONException e)
			{
				pageNO++;
			}
		}

	}
	
	
	
	private boolean updateSiKuStock(String type,String sikuProductId,String ourSku,int stock)
	{
 
		if(StringUtil.isBlank(sikuProductId)) return true; //没有寺库id 返回
		
		String method = "secoo.goods.stock.update";
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		map.put("vendorCode", APPKEY);
		map.put("secret", SECRET);
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("productId", sikuProductId);  
		map.put("stock", stock+""); //当前库存
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);		
 
		
		//如果有我们的sku 不需要做处理  如果没有则需要查询
		if(StringUtil.isBlank(ourSku))
		{
			if("sh".equals(type))
			{
				ourSku = sh_sikuSku2OurSkuMap.get(sikuProductId);
			}
			else
			{
				ourSku = hk_sikuSku2OurSkuMap.get(sikuProductId);
			}
		}
		
		boolean  flag = false;
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", sikuProductId);
		logMap.put("name", "寺库");
		logMap.put("stock", stock+"");
		logMap.put("location", type);
		try
		{
			JSONObject jsonObj = new JSONObject(response);
			if("200".equals(jsonObj.getString("code")))
			{
				//更新成功
				flag = true;
				logMap.put("type", "success");
			}
			else
			{
				String codeMsg = jsonObj.getString("subCode");
				 logMap.put("type", "error");
				 logMap.put("error",codeMsg);
			}
		}
		catch (JSONException e)
		{
			 logMap.put("type", "error");
			 logMap.put("error",response);
		}

		//记录更新日志
		//autoSyncDao.addUpdateLog(logMap);
		return flag;
	}
	
	
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
 
	

	/**
	 * 暂时未用
	 */
	@Override
	public void updateSiKuSku2Location()
	{
		String method = "secoo.goods.list";
		int pageNO = 1;	
		int pageSize = 20;
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		map.put("vendorCode", APPKEY);
		map.put("secret", SECRET);
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.1"); 
		while(true)
		{
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			//map.put("sellStatus", "1"); //产品状态
			
			map.put("pageNumber", pageNO+""); //当前页
			map.put("pageSize", pageSize+""); //每页条数
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);			

			try
			{
				JSONObject jsonObj = new JSONObject(response);
				System.out.println(jsonObj);
				JSONObject dataResponse = new JSONObject(jsonObj.get("data").toString());
				JSONArray jsonArray = dataResponse.getJSONArray("datas");
				for(int i = 0 ;i < jsonArray.length();i++)
				{
					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					String productId = productObj.getString("id");

				}
				
				//当前页为最后一页 中断
				pageNO++;
				if(dataResponse.getBoolean("isLastPage"))break;

				
			}
			catch (JSONException e)
			{
				pageNO++;
			}
		}
		
	}
	
	/**
	 * 设置寺库和我们sku的对应关系
	 * 
	 * @param type
	 */
	public void setSiku2OurSkuMatch(String type)
	{
 
		File file = new File(path+type+"_siku.txt");
		if(!file.exists()) return;
		FileReader fReader = null;
		BufferedReader bread = null;
		try
		{
			fReader = new FileReader(file);
			bread = new BufferedReader(fReader);
			String line = "";
			while((line = bread.readLine()) != null)
			{
			      String[] arr = line.split(",");
			      if(arr.length == 2)
			      {
			    	  String siku_sku = arr[0];
			    	  String our_sku = arr[1];
			    	  siku_sku = siku_sku.trim();
			    	  our_sku = our_sku.trim();
			    	  if("sh".equals(type))
			    	  {
			    		  sh_ourSku2sikuSkuMap.put(our_sku, siku_sku);
			    		  sh_sikuSku2OurSkuMap.put(siku_sku, our_sku);
			    	  }
			    	  else
			    	  {
			    		  hk_ourSku2sikuSkuMap.put(our_sku, siku_sku);
			    		  hk_sikuSku2OurSkuMap.put(siku_sku, our_sku);
			    	  }
			 
			      }
			      
			}
		}
		catch (Exception e1)
		{
			 
		}
		finally
		{
				try
				{
					if(fReader!=null)
						fReader.close();
					if(bread != null)
						bread.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		    
		}
	}
	
	
	/**
	 * 每隔10分钟获取订单信息
	 * 判断是否需要锁库产品
	 */
	public void getOrderInfoForSuoku()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				String method = "secoo.order.reserve";
				TreeMap<String, String> map = new TreeMap<String, String>();
				
				//系统需要参数
				map.put("vendorCode", APPKEY);
				map.put("secret", SECRET);
				map.put("signMethod", "md5");
				map.put("format", "json");
				map.put("method", method);//调用方法
				map.put("v", "1.0"); 
				Date nowDate = new Date();
				Date now_10 = new Date(nowDate.getTime() - 10*60*1000); //10分钟前时间
				Map<String,String> searchMap = new HashMap<String, String>();
				String shSite =  "";
				for(String s : MiniUiUtil.shSite)
				{
					shSite += s + ",";
				}
				shSite += "-1";
				searchMap.put("location", shSite);
				searchMap.put("sku", "8962015211746");
				//int nowNum = autoSyncDao.getStockByOurSku(searchMap);
				//System.out.println(nowNum);
				
				
				String startTime = sdf.format(now_10);
				String endTime = sdf.format(nowDate);
				//startTime =  "2016-05-05 16:20:00";
				//endTime  =  "2016-05-05 16:30:00";
				 
				//应用参数
				String nowTime = sdf.format(new Date());
				map.put("timestamp", nowTime);//调用时间
				map.put("startTime", startTime);  
				map.put("endTime", endTime); 
				String sign = getSign(map);
				map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
				
				//发送POST请求 获取数据
				String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);
				System.out.println(response);
				
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
							for(int j = 0; j < skuList.length();j++)
							{
								JSONObject skuObj = new JSONObject(skuList.get(j).toString());
								String ourSku = skuObj.getString("outerSkuId");
								int num = skuObj.getInt("quantity");
								System.out.println(ourSku + " " + num);
								System.out.println("------------------");
								Map<String,String> searchMap2 = new HashMap<String, String>();
								searchMap2.put("sku", ourSku);
								searchMap2.put("status", "1");
								//searchMap2.put("idLocation", "103,104");
								
								List list = productService.getProductList(searchMap2).getData();
								if(list != null &&  list.size() >0)
								{
									List<Product> l = (List<Product>)list;
									for(int index = 0; index < num; index++)
									{
										Product p = l.get(index);
										String idProduct = p.getIdProduct();
										Map<String,String> lifeCylrMap = new HashMap<String, String>();
										lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+""); //
										lifeCylrMap.put("idStatus", 4+""); //更新为已售
										productService.updateLifeCyle(lifeCylrMap);//更新状态
										
									}
								}
							}
							
						}
					}
				}
				catch (JSONException e)
				{
					 
				}
				
				
			}
		}, 1000,1000*60*10);
	}
	
	
	public void getOrderInfoForSuoku1()
	{
 
		String method = "secoo.order.get";
		TreeMap<String, String> map = new TreeMap<String, String>();
		
		//系统需要参数
		map.put("vendorCode", APPKEY);
		map.put("secret", SECRET);
		map.put("signMethod", "md5");
		map.put("format", "json");
		map.put("method", method);//调用方法
		map.put("v", "1.0"); 
		Date nowDate = new Date();
		Date now_10 = new Date(nowDate.getTime() - 18*60*60*1000); //10分钟前时间
		 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("orderId", "1010294844023");  
		 
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPostNoSecret(SENDPOSTURL, map);		
		System.out.println(response);
				/*
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
							for(int j = 0; j < skuList.length();j++)
							{
								JSONObject skuObj = new JSONObject(skuList.get(j).toString());
								String ourSku = skuObj.getString("outerSkuId");
								int num = skuObj.getInt("quantity");
								System.out.println(ourSku + " " + num);
								System.out.println("------------------");
							}
							
						}
					}
				}
				catch (JSONException e)
				{
					 
				}
				
				
			}
		}, 1000,1000*60*1);
		*/
	}
	

	public static void main(String[] args)
	{
		//new SiKuSyncManagerImpl().updateCanSaleProductByType("sh");
		SiKuSyncManagerImpl siKuSyncManager= new SiKuSyncManagerImpl();
		/*
		boolean a = siKuSyncManager.updateStockByOurSkuAndPlatFormSku("sh", "20155566", "", 10, "9582016157707");
		Map<Integer, Integer>  map = siKuSyncManager.getSiKuStockBySiKuSku("sh", "20155566", "9582016157707");
		System.out.println(map);
		*/
		
		//siKuSyncManager.updateCanSaleProductByType("sh");
		//System.out.println(a);
 
		//siKuSyncManager.setSiku2OurSkuMatch("hk");
		siKuSyncManager.updateSiKuStock("hk","20158233","",5);
 
		
		//siKuSyncManager.getSiKuStockBySiKuSku("hk","20158233");
		
		//siKuSyncManager.updateStockByOurSkuAndPlatFormSku("sh", "10758087", "", 10, "9582016157707");
		//siKuSyncManager.getOrderInfoForSuoku();
	}

}
 
