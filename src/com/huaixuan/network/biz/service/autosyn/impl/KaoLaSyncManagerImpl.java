package com.huaixuan.network.biz.service.autosyn.impl;

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

import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.autosyn.KaoLaSyncManager;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-3-21 下午02:47:19
 * 考拉库存同步 弃用
 **/


public class KaoLaSyncManagerImpl implements KaoLaSyncManager
{
	private static final String FILENAME = "kaola"; //更新成功记录日志名称
	private static final String SH_APP_KEY = "21bbbca21d8bcc81829e26b30de460ae";
	private static final String SH_APP_SECRET = "804a9a5a3a10513c08c8731c89ededb77e28728c";
	private static final String SH_ACCESS_TOKEN = "27eac4d1-edc0-46b1-865e-38b499a96c12";
	private static final String HK_ACCESS_TOKEN = "2a1bcae5-d7ae-45a7-9242-d33a8b521963";
	private static final String BASE_URL = "http://openapi.kaola.com/router?";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private  AutoSyncDao autoSyncDao;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public void updateCanSaleProduct()
	{
		updateCanSaleProductByType("sh","6"); //上海下架
		updateCanSaleProductByType("sh","5"); //上海可售
		
		updateCanSaleProductByType("hk","6"); //香港下架
		updateCanSaleProductByType("hk","5"); //香港可售
	}
	
	/**
	 * 
	 * @param type  sh-上海  hk- 香港
	 * @param productStatus  5-在售  6-下架
	 */
	private void updateCanSaleProductByType(String type,String productStatus)
	{
		String method = "kaola.item.batch.status.get";
		int pageNO = 1;	
		int pageSize = 20;
		int totalNum = 0;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		//系统需要参数
		map.put("secret", SH_APP_SECRET);
		map.put("app_key", SH_APP_KEY);
		
		
		List<Long> siteList = new ArrayList<Long>();
		if("hk".equals(type))
		{
			//香港账号
			map.put("access_token", HK_ACCESS_TOKEN);
			//获取可售库存所需站点信息
			for(String idSite : MiniUiUtil.hkSite)
			{
				siteList.add(Long.valueOf(idSite));
			}
		}
		else
		{
			map.put("access_token", SH_ACCESS_TOKEN);
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
			map.put("item_edit_status", productStatus); //产品状态 
			map.put("page_no", pageNO+""); //当前页
			map.put("page_size", pageSize+""); //每页条数
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(BASE_URL, map);
			//System.out.println(response);
			try
			{
				JSONObject jsonObj = new JSONObject(response);
				JSONObject itemsResponse = new JSONObject(jsonObj.get("kaola_item_batch_status_get_response").toString());
				totalNum = itemsResponse.getInt("total_count");
				if(0 == totalNum) break; //没有数据 中断
				int totalPageSize = (totalNum/pageSize)+1; //总页数
				
				JSONArray jsonArray = itemsResponse.getJSONArray("item_edit_list");
				for(int i = 0 ;i < jsonArray.length();i++)
				{
					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					JSONArray skuArray = productObj.getJSONArray("sku_list");
					for(int index = 0; index < skuArray.length();index++)
					{
						JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
						String kaolaSku = skuObj.getString("key"); //考拉sku
						String supplySku = new JSONObject(skuObj.getString("raw_sku")).getString("bar_code"); //供货商SKU
						
						if(StringUtil.isBlank(supplySku) || StringUtil.isBlank(kaolaSku)) continue;
						
						StockData stockData = canSaleStockMap.get(supplySku); //当前库存
						int nowNum = 0;
						if(stockData != null){nowNum = stockData.getNum();}
						
						updateKaoLaStock(type,kaolaSku,supplySku,nowNum);//更新考拉库存 
						
						
					}
				}
				
				
				//当前页为最后一页 中断
				pageNO++;
				if(pageNO > totalPageSize)break;

				
			}
			catch (JSONException e)
			{
				pageNO++;
			}
		
		}
		
		System.out.println(sdf.format(new Date()) + " kaola " + type + " " + productStatus);
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
			if(en.getKey().equals("secret") || en.getKey().equals("sign")) continue; //密码和签名项不加入签名算法
				needMD5Str += en.getKey() + en.getValue();
		}
		needMD5Str+= map.get("secret");
		sign = HttpRequest.string2MD5(needMD5Str).toUpperCase();
		return sign;
	}
	
	
	/**
	 * 更新考拉库存
	 * @param kaolaSku  考拉sku
	 * @param supplySku  供货商sku
	 */
	private void updateKaoLaStock(String type,String kaoLaSku,String supplySku,int nowNum)
	{
		String method = "kaola.item.sku.stock.update";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("secret", SH_APP_SECRET);
		map.put("app_key", SH_APP_KEY);
		map.put("method", method);//调用方法
	
		Map<String,String> sqlMap = new HashMap<String, String>();
		
		if("hk".equals(type))
		{
			//香港账号
			map.put("access_token", HK_ACCESS_TOKEN);
			
		}
		else
		{
			//上海账号
			map.put("access_token", SH_ACCESS_TOKEN);
			
			
		}
		sqlMap.put("sku",supplySku);
	 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("key", kaoLaSku); //考拉sku
		map.put("stock", nowNum+""); //库存
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPost(BASE_URL, map);
		
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", supplySku);
		logMap.put("psku", kaoLaSku);
		logMap.put("name", "考拉");
		logMap.put("stock", nowNum+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		try
		{
			JSONObject jsonObject = new JSONObject(response);
			String result = new JSONObject(jsonObject.getString("kaola_item_sku_stock_update_response")).getString("result");
			if("1".equals(result))
			{
				 LogData logData = new LogData();
				 logData.setBeforNum(-1);
				 logData.setNowNum(nowNum);
				 logData.setPlatformSku(kaoLaSku);
				 logData.setType(type);
				 logData.setFileName(FILENAME);
				 logData.setSku(supplySku);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件 
			}
		}
		catch (JSONException e)
		{
			 logMap.put("type", "error");
			 logMap.put("error",response);
		}
		
		//记录日志
		autoSyncDao.addUpdateLog(logMap);
	}
	
	
	// 暂时没用
	public void updateChangedStock(Map<String,Integer> needUpdateMapSh, String type)
	{
		//有库存改变的数据 查询平台sku 然后更新库存 记录日志
		Set<Entry<String,Integer>> set = needUpdateMapSh.entrySet();
		Iterator<Entry<String, Integer>> it2  = set.iterator();
		Map<String,String> searchMap = new HashMap<String, String>();
		while(it2.hasNext())
		{
			Entry<String, Integer> e = it2.next();
			String sku = e.getKey();
			Integer num = e.getValue();
			if("".equals(sku) || null == sku) continue;
			
			searchMap.put("sku", sku);
			if("sh".equals(type))
			{
				searchMap.put("field", "sku_kaola_sh");
			}
			else
			{
				searchMap.put("field", "sku_kaola_hk");
			}
			//查询对应平台的sku,searchMap的field字段为平台sku的字段名
			String paltFormSku = autoSyncDao.getPlatformSkuByOurSku(searchMap);
			if(StringUtil.isNotBlank(paltFormSku))
			{				 
				//更考拉库存  通过考拉sku更新
				StockData stockData = new StockData();
				stockData.setNum(num);
				updateKaoLaStock(type,paltFormSku,sku,stockData.getNum());
			}
		}
	}

	
	@Override
	public boolean updateStockByOurSkuAndPlatFormSku(String type,String platformSku,String ourSku, int nowNum,String idProduct)
	{
		if(StringUtil.isBlank(platformSku)) return true;
		
		//查询考拉平台库存
		int kaolaServerNum = getKaoLaStockByKaoLaSku(type, idProduct, platformSku); //-1为没查询到数据 更新为我们的库存
		if(kaolaServerNum == 0) return true;//考拉已经为0了不做更新
		
		//如果考拉平台的库存大于我们本地库存 直接更新为我们的库存（比我们多 以我们为主）
		//如果考拉平台的库存小于等于我们的库存 做判断 如果客户不是网易 则在考拉库存的基础上在减少  如果是网易则不做更新 (比我们多 不是开给网易的)
		if(kaolaServerNum <= nowNum && kaolaServerNum !=-1)
		{
			Product product = productService.getproduct(idProduct);
			if(!product.getCustomerName().contains("网易"))//不是开给网易的 其他平台或者线下卖掉了 或者预留的
			{
				//更新考拉库存为考拉当前库存减1
				nowNum = kaolaServerNum--;
			}
			else
			{
				return true;//平台库存比当前库存少  不做更新
			}
		}
		
		if(nowNum < 0) nowNum = 0;
		
		
		String method = "kaola.item.sku.stock.update";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("secret", SH_APP_SECRET);
		map.put("app_key", SH_APP_KEY);
		map.put("method", method);//调用方法
		
		if("hk".equals(type))
		{
			//香港账号
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			//上海账号
			map.put("access_token", SH_ACCESS_TOKEN);
		}

		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("key", platformSku); //考拉sku
		map.put("stock", nowNum+""); //库存
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPost(BASE_URL, map);
		
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", platformSku);
		logMap.put("name", "考拉");
		logMap.put("stock", nowNum+"");
		logMap.put("location", type);
		boolean resultBoolean = false;
		try
		{
			JSONObject jsonObject = new JSONObject(response);
			String result = new JSONObject(jsonObject.getString("kaola_item_sku_stock_update_response")).getString("result");
			
			if("1".equals(result))
			{
				 LogData logData = new LogData();
				 logData.setBeforNum(-1);
				 logData.setNowNum(nowNum);
				 logData.setPlatformSku(platformSku);
				 logData.setType(type);
				 logData.setFileName(FILENAME);
				 logData.setSku(ourSku);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
				 logMap.put("type", "success");
				 resultBoolean = true;
			}
			else
			{
				 logMap.put("type", "error");
				 logMap.put("error",response);
				 resultBoolean = true;
			}
		}
		catch (JSONException e)
		{
			 LogData logData = new LogData();
			 logData.setBeforNum(-1);
			 logData.setNowNum(nowNum);
			 logData.setPlatformSku(platformSku);
			 logData.setType(type);
			 logData.setFileName(FILENAME+"_error");
			 logData.setSku(ourSku);
			 logData.setError(response);
			 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
			 logMap.put("type", "error");
			 logMap.put("error",response);
			 resultBoolean = false; 
		}
		
		//记录更新日志
		autoSyncDao.addUpdateLog(logMap);
		return resultBoolean;
	}

	@Override
	public void updateSku2Location()
	{
		//updateSku2LocationByType("sh","6");
		//updateSku2LocationByType("sh","5");
		
		
		updateSku2LocationByType("hk","6");
		//updateSku2LocationByType("hk","5");
		
		
	}
	
	
	/**
	 * 更新卡考拉SKU 和商品KEY到本地 -现在是每天半夜执行  每天同步一次
	 * @param type  sh-上海  hk- 香港
	 * @param productStatus  5-在售  6-下架
	 */
	private void updateSku2LocationByType(String type,String productStatus)
	{
		String method = "kaola.item.batch.status.get";
		int pageNO = 1;	
		int pageSize = 20;
		int totalNum = 0;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		//系统需要参数
		map.put("secret", SH_APP_SECRET);
		map.put("app_key", SH_APP_KEY);
 
		if("hk".equals(type))
		{
			//香港账号
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			map.put("access_token", SH_ACCESS_TOKEN);
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
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(BASE_URL, map);
			

			try
			{
				List<LogData> logDataList = new ArrayList<LogData>();
				List<LogData> kaolaKeyList = new ArrayList<LogData>();
				JSONObject jsonObj = new JSONObject(response);
				JSONObject itemsResponse = new JSONObject(jsonObj.get("kaola_item_batch_status_get_response").toString());
				totalNum = itemsResponse.getInt("total_count");
				if(0 == totalNum) break; //没有数据 中断
				int totalPageSize = (totalNum/pageSize)+1; //总页数
				
				JSONArray jsonArray = itemsResponse.getJSONArray("item_edit_list");
				for(int i = 0 ;i < jsonArray.length();i++)
				{
					JSONObject productObj = new JSONObject(jsonArray.get(i).toString());
					String kaolaKey = productObj.getString("key");//商品key 一个商品对应多个sku
					JSONArray skuArray = productObj.getJSONArray("sku_list");
					for(int index = 0; index < skuArray.length();index++)
					{
						JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
						String kaolaSku = skuObj.getString("key"); //考拉sku
						String supplySku = new JSONObject(skuObj.getString("raw_sku")).getString("bar_code"); //供货商SKU
						if(StringUtil.isBlank(supplySku) || StringUtil.isBlank(kaolaSku)) continue;
						
						//同步sku到本地,单个更新使用, 批量更新
						LogData logData = new LogData();
						logData.setPlatformSku(kaolaSku);
						logData.setSku(supplySku);
						logDataList.add(logData);
						
						
						//同步考拉key到本地 一个key对应多个sku 用于更新库时查询考拉当前库存(查询考拉库存必须要考拉key  没有用考拉sku查询的接口)
						if(StringUtil.isNotBlank(supplySku) && StringUtil.isNotBlank(kaolaKey))
						{
							LogData kaolaKeyObj = new LogData();
							kaolaKeyObj.setPlatformSku(kaolaKey);
							kaolaKeyObj.setSku(supplySku);
							kaolaKeyList.add(kaolaKeyObj);
						}
					}
				}
				
				
				//更新考拉sku到本地
				autoSyncDao.updateKaoLaSku2Location(logDataList, type);
				
				//更新考拉key到本地
				autoSyncDao.updateKaoLaKey2Location(kaolaKeyList, type);
				
				//当前页为最后一页 中断
				pageNO++;
				if(pageNO > totalPageSize)break;

				
			}
			catch (JSONException e)
			{
				pageNO++;
			}
		
		}
	}
	
	
	
	/**
	 * 获取考拉平台的库存
	 */
	public int getKaoLaStockByKaoLaSku(String type,String idProduct,String kaolaSku)
	{
		String kaolaKey = autoSyncDao.getKaoLaKeyByIdProduct(idProduct, type);
		if("".equals(kaolaKey)) return -1;
		
		String method = "kaola.item.get";

		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		//系统需要参数
		map.put("secret", SH_APP_SECRET);
		map.put("app_key", SH_APP_KEY);
 
		if("hk".equals(type))
		{
			//香港账号
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			map.put("access_token", SH_ACCESS_TOKEN);
		}

	
 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
		map.put("key", kaolaKey); //
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPost(BASE_URL, map);
		
		try
		{

			JSONObject jsonObj = new JSONObject(response);
			JSONObject productObj = new JSONObject(jsonObj.getString("kaola_item_get_response"));
			JSONArray skuArray = productObj.getJSONArray("sku_list");
			for(int index = 0; index < skuArray.length();index++)
			{
				JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
				String kaolaSkuServer = skuObj.getString("key"); //考拉sku
				
				if(kaolaSku.equals(kaolaSkuServer)) //获取该sku当前的库存
				{
					int kaolaCanSaleNum = new JSONObject(skuObj.getString("raw_sku")).getInt("stock_can_sale");
					return kaolaCanSaleNum;
				}
			    
			}
			
				
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			return -1; //没找到产品库存 返回-1
		}
		
		return -1;//没找到产品库存 返回-1
	}
	
	public static void main(String[] args)
	{
		new KaoLaSyncManagerImpl().updateSku2Location();
	}
}
	

 
