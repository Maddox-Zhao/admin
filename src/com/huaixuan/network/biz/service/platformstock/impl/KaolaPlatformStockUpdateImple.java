package com.huaixuan.network.biz.service.platformstock.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
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
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockNumber;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.KaolaPlatformStockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-5-10 下午05:17:32
 **/

@Service("kaolaPlatformStockUpdate")
public class KaolaPlatformStockUpdateImple implements KaolaPlatformStockUpdate
{

 
	private static final String SH_APP_KEY = "21bbbca21d8bcc81829e26b30de460ae";
	private static final String SH_APP_SECRET = "804a9a5a3a10513c08c8731c89ededb77e28728c";
	private static final String SH_ACCESS_TOKEN = "57b788de-1b67-4f1b-bbf6-42dec5305596";
//	private static final String SH_ACCESS_TOKEN = "67602956-93a4-4bab-b864-528342e0f618";
	
//	private static final String HK_ACCESS_TOKEN = "684eb7cf-9f91-4176-abb2-b150e8f2fd82";
	
	private static final String HK_APP_KEY = "56524139b2912172b541e00c3e4c9dfc";
	private static final String HK_APP_SECRET = "26a80dcb5b1eb585ddae24b07cd52e640847ee436867883deadf76b9806d9ded";
	private static final String HK_ACCESS_TOKEN = "51596162-e6e3-4144-9d87-ca012c31453b";
	
	private static final String BASE_URL = "http://openapi.kaola.com/router?";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String SH_KAOLA_SKU_FILE_NAME= "kaola_sh_sku.txt";
	private static final String HK_KAOLA_SKU_FILE_NAME= "kaola_hk_sku.txt";
	
	private static Map<String,List<String>> shRepSkuMap = new HashMap<String, List<String>>(); //记录有重复sku的产品
	private static Map<String,List<String>> hkRepSkuMap = new HashMap<String, List<String>>(); //记录有重复sku的产品
	
	private static final String path = "d:/stock_log/";//记录库存的日志文件夹
	
	protected Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private  AutoSyncDao autoSyncDao;
	
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	
	public KaolaPlatformStockUpdateImple()
	{
		shRepSkuMap = MiniUiUtil.readRpeatSku(SH_KAOLA_SKU_FILE_NAME);
		hkRepSkuMap = MiniUiUtil.readRpeatSku(HK_KAOLA_SKU_FILE_NAME);
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
	 * 更新卡考拉SKU 和商品KEY到本地 -现在是每天半夜执行  每天同步一次
	 * @param type  sh-上海  hk- 香港
	 * @param productStatus  5-在售  6-下架
	 */
	private int updateSku2LocationByType(String type,String productStatus)
	{
		String method = "kaola.item.batch.status.get";
		int pageNO = 1;	
		int pageSize = 20;
		int totalNum = 0;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		//系统需要参数

 
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HK_APP_SECRET);
			map.put("app_key", HK_APP_KEY);
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			map.put("secret", SH_APP_SECRET);
			map.put("app_key", SH_APP_KEY);
			map.put("access_token", SH_ACCESS_TOKEN);
		}

		int totalCnt = 0;
		int errorCnt = 1;
		StringBuilder sb = new StringBuilder(); //记录sku对应关系
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
			if(StringUtil.isBlank(response) || response.charAt(0) != '{' || response.charAt(response.length()-1) != '}') continue;
			pageNO++;
//			System.out.println(response);
			try
			{
				List<PlatformSku2LocationSku> kaolaSkuList = new ArrayList<PlatformSku2LocationSku>();
				List<PlatformSku2LocationSku> kaolaKeyList = new ArrayList<PlatformSku2LocationSku>();
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
					JSONObject item = new JSONObject(productObj.getString("raw_item_edit"));
					String status ="";
					String itemStaus = item.getString("item_status");
					if(itemStaus.equals("ON_SALE")){
						status ="1";
					}else{
						status="0";
					}
					
					JSONArray skuArray = productObj.getJSONArray("sku_list");
					for(int index = 0; index < skuArray.length();index++)
					{
						JSONObject skuObj = new JSONObject(skuArray.get(index).toString());
						
						
						
						String kaolaSku = skuObj.getString("key"); //考拉sku
//						String kaolaSku = "16573273-404066";
						String supplySku = new JSONObject(skuObj.getString("raw_sku")).getString("bar_code"); //供货商SKU
//						String supplySku = "16573273- 8372013173654";
						if(StringUtil.isBlank(supplySku) || StringUtil.isBlank(kaolaSku)) continue;
						supplySku = supplySku.trim();
						String[] supplySkuArr = supplySku.split("-");
						if(supplySkuArr.length == 2)
						{
							supplySku = supplySkuArr[1].trim();
						}
						else
						{
							supplySku = supplySkuArr[0].trim();
						}
						
						//同步sku到本地,单个更新使用, 批量更新
						PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
						pslSku.setOurSku(supplySku);
						pslSku.setPlatformField("kaola_sku");
						pslSku.setPlatformSku(kaolaSku);
						pslSku.setType(type); 
						kaolaSkuList.add(pslSku);
						
						sb.append(supplySku);
						sb.append(",");
						sb.append(kaolaSku+"");
						sb.append("\n");
						
						Map kaolaSkuMap = new HashMap();
						kaolaSkuMap.put("list", kaolaSkuList);
						kaolaSkuMap.put("platformField", "kaola_sku");
						platformStockUpdateDao.batchUpdatePlatformSku2Location(kaolaSkuMap);
						
						//同步考拉key到本地 一个key对应多个sku 用于更新库时查询考拉当前库存(查询考拉库存必须要考拉key  没有用考拉sku查询的接口)
						PlatformSku2LocationSku pslKey = new PlatformSku2LocationSku();
						pslKey.setOurSku(supplySku);
						pslKey.setPlatformField("kaola_key");
						pslKey.setPlatformstatus("kaola_on_sale_status");
						pslKey.setStatus(status);
						pslKey.setPlatformSku(kaolaKey);
						pslKey.setType(type); 
						kaolaKeyList.add(pslKey);
						
						totalCnt++;
						
					}
				}
				
//				Map kaolaSkuMap = new HashMap();
//				kaolaSkuMap.put("list", kaolaSkuList);
//				kaolaSkuMap.put("platformField", "kaola_sku");
				
				Map kaolaKeyMap = new HashMap();
				kaolaKeyMap.put("list", kaolaKeyList);
				kaolaKeyMap.put("platformField", "kaola_key");
				kaolaKeyMap.put("platformstatus", "kaola_on_sale_status");
				
				if(kaolaSkuList.size() > 0)
				{
					//更新考拉sku到本地
//					platformStockUpdateDao.batchUpdatePlatformSku2Location(kaolaSkuMap);
				}
				
				if(kaolaKeyList.size() > 0)
				{
					//更新考拉key到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(kaolaKeyMap);
				}
				
				//当前页为最后一页 中断
				
				if(pageNO > totalPageSize)break;

				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("kl syn sku error " + pageNO);
				if(errorCnt++ > 5)break; //错误5次挑出
				
			}
		}
		if("sh".equals(type))
			MiniUiUtil.writeText2File(SH_KAOLA_SKU_FILE_NAME, sb.toString(), "zj");
		else if("hk".equals(type))
			MiniUiUtil.writeText2File(HK_KAOLA_SKU_FILE_NAME, sb.toString(), "zj");
		return totalCnt;
	}
	
	@Override
	public int updateWaitFoOnSaleSku2Location()
	{
		int cnt = updateSku2LocationByType("hk","4");
		cnt += updateSku2LocationByType("sh","4");
		
		//更新待上架库存
		updateAllStockByType("hk","4");
		updateAllStockByType("sh","4");
		return cnt;
	}
	
	
	@Override
//	@Transactional
	public void updateSku2Location()
	{
		//stockService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
		
		//platformStockUpdateDao.updateStockUpdateSku2Null("kaola_sku");//更新平台sku为NULL 
		//platformStockUpdateDao.updateStockUpdateSku2Null("kaola_key");//更新平台sku为NULL 
		
		platformStockUpdateDao.updateStockUpdateSku2Null("kaola_on_sale_status"); //更新平台kaola_on_sale_status为NULL 
		//清空文件
		MiniUiUtil.writeText2File(SH_KAOLA_SKU_FILE_NAME, "", "cx");
		MiniUiUtil.writeText2File(HK_KAOLA_SKU_FILE_NAME, "", "cx");
		
		 //5-在售  6-下架  先更新下架 在更新在售  防止下架的产品和在售的sku重复  已在售的sku为准
		updateSku2LocationByType("sh","6");
		updateSku2LocationByType("sh","5");
		
		updateSku2LocationByType("hk","6");
		updateSku2LocationByType("hk","5");
		
		shRepSkuMap = MiniUiUtil.readRpeatSku(SH_KAOLA_SKU_FILE_NAME);
		hkRepSkuMap = MiniUiUtil.readRpeatSku(HK_KAOLA_SKU_FILE_NAME);
		
		//stockService.setCanUpdateStockStatus("true"); //解除-暂时不让其他地方做更新
	}


	@Override
	public void updateAllStock()
	{
		updateAllStockByType("hk","6");  //6, 下架,
		updateAllStockByType("hk","5"); //5是在售
		
		
		updateAllStockByType("sh","6");
		updateAllStockByType("sh","5");
		
	}
	
	private void updateAllStockByType(String type,String productStatus)
	{
		String method = "kaola.item.batch.status.get";
		int pageNO = 1;	
		int pageSize = 20;
		int totalNum = 0;
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		

		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HK_APP_SECRET);
			map.put("app_key", HK_APP_KEY);
			map.put("access_token", HK_ACCESS_TOKEN);	 
		}
		else
		{
			//系统需要参数
			map.put("secret", SH_APP_SECRET);
			map.put("app_key", SH_APP_KEY);
			map.put("access_token", SH_ACCESS_TOKEN);
		}


		//查询当前可售库存
		Map<String,StockUpdate> canSaleStockMap = new HashMap<String, StockUpdate>(); //key为我们sku  value为当前sku可售库存
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("searchCanSale", "yes");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate su : list)
		{
			if(StringUtil.isNotBlank(su.getKaolaSku()))
			{
				canSaleStockMap.put(su.getSku(), su);
			}
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
			pageNO++;
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(BASE_URL, map);

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
						
						if(StringUtil.isBlank(supplySku)) continue;
						supplySku = supplySku.trim();
						String[] supplySkuArr = supplySku.split("-");
						if(supplySkuArr.length == 2)
						{
							supplySku = supplySkuArr[1];
						}
						else 
						{
							supplySku = supplySkuArr[0];
						}
						
						
						StockUpdate su = canSaleStockMap.get(supplySku); //当前库存
						int nowNum = 0;
						if(su != null){nowNum = su.getNowStockNum()-su.getOrderStockNum();}
						if(nowNum < 0 ) nowNum = 0;
						boolean flag = updateKaoLaStocku(kaolaSku, supplySku, nowNum, type); //更新成功
						//更新成功 设置上次更新到平台库存数,防止单个更新的在做更新
						if(flag)
						{
							StockUpdate stockUpdate = new StockUpdate();
							stockUpdate.setType(type);
							stockUpdate.setSku(supplySku);
							stockUpdate.setLastUpdateStockNum(nowNum);
							//platformStockUpdateDao.updateLastUpdateStockBySkuAndTypeForOne(stockUpdate); 暂时不做更新防止其他平台更新不到
						}
					}
				}
				
				
				//当前页为最后一页 中断
				
				if(pageNO > totalPageSize)break;

				
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
				if(pageNO > 500)break;
				
			}
		
		}
		
		System.out.println(sdf.format(new Date()) + " kaola " + type + " " + productStatus);
	}


	@Override
	public boolean updateKaoLaStocku(String kaolaSku, String ourSku, int num, String type)
	{
		if(StringUtil.isBlank(kaolaSku) && StringUtil.isBlank(ourSku)) return true;
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("kaolaSku", kaolaSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(kaolaSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "kaola_sku");
			kaolaSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(kaolaSku)) return true;
		String method = "kaola.item.sku.stock.update";
		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		
		map.put("method", method);//调用方法
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HK_APP_SECRET);
			map.put("app_key", HK_APP_KEY);
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			//上海账号
			map.put("secret", SH_APP_SECRET);
			map.put("app_key", SH_APP_KEY);
			map.put("access_token", SH_ACCESS_TOKEN);
		}
		
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", kaolaSku);
		logMap.put("name", "kaola");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		List<String> kaolaSkusList = null;
		if("sh".equals(type))
		{
			kaolaSkusList = shRepSkuMap.get(ourSku);
		} 
		else if("hk".equals(type))
		{
			kaolaSkusList = hkRepSkuMap.get(ourSku);
		}
			
		
		if(kaolaSkusList != null)
		{
			boolean flag = false;
			//List<String> kaolaSkusList = repSkuMap.get(ourSku);
			for(String kaolaSku1 : kaolaSkusList)
			{
				//应用参数
				String nowTime = sdf.format(new Date());
				map.put("timestamp", nowTime);//调用时间
				map.put("key", kaolaSku1); //考拉sku
				map.put("stock", num+""); //库存
				String sign = getSign(map);
				map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
				//发送POST请求 获取数据
				String response = HttpRequest.sendPost(BASE_URL, map);
				logMap.put("psku", kaolaSku1);
				try
				{
					JSONObject jsonObject = new JSONObject(response);
					String result = new JSONObject(jsonObject.getString("kaola_item_sku_stock_update_response")).getString("result");
					if("1".equals(result))
					{
						flag = true;
						logMap.put("type", "success");
					}
					else
					{
						logMap.put("type", "error");
					    logMap.put("error",response);
					}
				}
				catch (Exception e)
				{
					 log.error(e.getMessage(), e);
					 logMap.put("type", "error");
					 logMap.put("error",response);
				}
				//记录日志
				autoSyncDao.addUpdateLog(logMap);
			}
			return flag;
		}
		else
		{
			//应用参数
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);//调用时间
			map.put("key", kaolaSku); //考拉sku
			map.put("stock", num+""); //库存
			String sign = getSign(map);
			map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
			
			
			logMap.put("psku", kaolaSku);
			
			//发送POST请求 获取数据
			String response = HttpRequest.sendPost(BASE_URL, map);
			boolean flag = true;
			try
			{
				JSONObject jsonObject = new JSONObject(response);
				String result = new JSONObject(jsonObject.getString("kaola_item_sku_stock_update_response")).getString("result");
				if("1".equals(result))
				{
					logMap.put("type", "success");
					flag = true;
				}
				else
				{
					logMap.put("type", "error");
				    logMap.put("error",response);
				    flag = true;
				}
			}
			catch (Exception e)
			{
				 logMap.put("type", "error");
				 logMap.put("error",response);
				 log.error(e.getMessage(), e);
			}
			
			//记录日志
			autoSyncDao.addUpdateLog(logMap);
			return flag;
		}
		
	}


	@Override
	public StockNumber getKaolaStock(String kaolaSku, String kaolaKey,String type)
	{
		StockNumber sn = null;
		if(StringUtil.isBlank(kaolaSku)) return sn; 
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("kaolaSku", kaolaSku);
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		if(list.size() > 0)
		{
			 kaolaKey= list.get(0).getKaolaKey();
		}
		if(StringUtil.isBlank(kaolaKey)) return sn; 
		String method = "kaola.item.get";

		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("method", method);//调用方法
		//系统需要参数
		
 
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HK_APP_SECRET);
			map.put("app_key", HK_APP_KEY);
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			map.put("secret", SH_APP_SECRET);
			map.put("app_key", SH_APP_KEY);
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
					JSONObject obj = new JSONObject(skuObj.getString("raw_sku"));
					int kaolaCanSaleNum = obj.getInt("stock_can_sale");
					int kaolaFreezeSaleNum = obj.getInt("stock_freeze");
					sn = new StockNumber();
					sn.setPlatformSku(kaolaSku);
					sn.setCanSaleNum(kaolaCanSaleNum);
					sn.setFreezeNum(kaolaFreezeSaleNum);
					return sn;
				}
			    
			}	
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
		return  sn;
	}
	 public String getOrSetLastUpdateTime(String lastTime)
	 {
		    String result = "";
		    //设置
		    if(StringUtil.isNotBlank(lastTime))
		    {
		    	FileWriter fw = null;
				try
				{
					File file =new File(path+"last_update_time_not_delete_kaola.txt");
					fw = new FileWriter(file);
					fw.write(lastTime);
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
				}
				finally
				{
						if(fw != null)
						{
							try
							{
								fw.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
								log.error(e.getMessage(), e);
							}
						}
				}
		    }
		    else //获取
		    {
				RandomAccessFile accessFile = null;
				try
				{
					accessFile = new RandomAccessFile(path+"last_update_time_not_delete_kaola.txt","rw");
					int length = (int)accessFile.length();
					byte[] c = new byte[length];
					accessFile.seek(0);
					accessFile.read(c, 0, length);
					result =  new String(c);
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
				}
				finally
				{
						if(accessFile != null)
						{
							try
							{
								accessFile.close();
							}
							catch (IOException e)
							{								
								log.error(e.getMessage(), e);
							}
						}
				}
		    }
			return result;
		}
	
	  /**
	   * 考拉推送延迟 查询当前时间24小时内的下订单信息(24小时内推送过来)
	   * 如果本地有记录该order说明已处理过  没有记录则 订单id+1 并记录
	   * 
	   * 
	   */
	@Override
	public  int atuoSyncOrder(String type)
	{
 		Date now_24 = new Date(new Date().getTime() - 3*24*60*60*1000); //3天内的订单
		String startTimeStr = sdf.format(now_24);
		String endTimeStr = sdf.format(new Date());
		 
		String method = "kaola.order.search";
//		String method = "kaola.order.customsclearance.get"; //清关接口

		TreeMap<String, String> map = new TreeMap<String, String>();
		//系统需要参数
		map.put("method", method);//调用方法
//		map.put("v", "2.0");
		if("hk".equals(type))
		{
			//香港账号
			map.put("secret", HK_APP_SECRET);
			map.put("app_key", HK_APP_KEY);
			map.put("access_token", HK_ACCESS_TOKEN);
		}
		else
		{
			//上海账号
			map.put("secret", SH_APP_SECRET);
			map.put("app_key", SH_APP_KEY);
			map.put("access_token", SH_ACCESS_TOKEN);
		}
	 
		//应用参数
		String nowTime = sdf.format(new Date());
		map.put("timestamp", nowTime);//调用时间
//		map.put("biz_content", "{\"order_id\":\"2019011716511709404190066\"}");
		map.put("order_status", "1"); //已付款
		map.put("page_size", "100"); //
		map.put("start_time", startTimeStr); //
		map.put("end_time", endTimeStr); //
		map.put("date_type", "1"); //支付时间
		String sign = getSign(map);
		map.put("sign", sign);//map中放入了签名 第二次调用签名的时候剔除sign项
		
		//发送POST请求 获取数据
		String response = HttpRequest.sendPost(BASE_URL, map);
//		System.out.println(response);
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		int updateNumber = 0;
		Map<String,String> orderMap = new HashMap<String, String>(); //保存订单信息和对应的sku
		List<String> list = new ArrayList<String>(); //当前查询出来的订单id
		try
		{
			JSONObject objList = new JSONObject(response);
			JSONObject orderObj = new JSONObject(objList.getString("kaola_order_search_response"));
			
			JSONArray orderList = orderObj.getJSONArray("orders");
			for(int i = 0; i < orderList.length();i++)
			{
				JSONObject skuList = new JSONObject(orderList.get(i).toString());
				String orderId = skuList.getString("order_id");//订单号
				PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
				platformorderdetails.setIdorder(orderId);
				List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
				String receiver_name = skuList.getString("receiver_name");//收件人姓名
				String receiver_phone = skuList.getString("receiver_phone");//收件人手机
				String receiver_province_name = skuList.getString("receiver_province_name");//省
				String receiver_city_name = skuList.getString("receiver_city_name");//市
				String receiver_district_name = skuList.getString("receiver_district_name");//区
				String receiver_address_detail = skuList.getString("receiver_address_detail");//详细地址
				String receiver_post_code = skuList.getString("receiver_post_code");//邮政编码
				String pay_success_time = skuList.getString("pay_success_time");//支付时间
				String order_real_price = skuList.getString("order_real_price");//订单实际价格
				String order_origin_price = skuList.getString("order_origin_price");//订单原始价格
				String express_fee = skuList.getString("express_fee");//邮费
				String order_time  = skuList.getString("order_time");//订单下单时间
				JSONArray orderSkus = skuList.getJSONArray("order_skus");
				for (int  j= 0; j < orderSkus.length();j++) {
					JSONObject goods = new JSONObject(orderSkus.get(j).toString());
					String skuId = goods.getString("sku_key");//平台SKU
					String product_name = goods.getString("product_name");//商品名称
					String origin_price = goods.getString("origin_price");//原始单价
					String count = goods.getString("count");//商品数量
					String ourSku = goods.getString("barcode");//我们的SKU
					if(!ourSku.equals("")){
						ourSku = ourSku.substring(ourSku.indexOf("-")+1);
					}
						platformorderdetails.setIdorder(orderId);
						platformorderdetails.setQuantity(count);
						platformorderdetails.setSkuId(skuId);
						platformorderdetails.setName(receiver_name);
						platformorderdetails.setMobile(receiver_phone);
						platformorderdetails.setProvince(receiver_province_name);
						platformorderdetails.setCity(receiver_city_name);
						platformorderdetails.setDistrict(receiver_district_name);
						platformorderdetails.setStreetAddress(receiver_address_detail);
						platformorderdetails.setZipCode(receiver_post_code);
						platformorderdetails.setPayTime(pay_success_time);
						platformorderdetails.setPayprice(order_real_price);
						platformorderdetails.setTotalPrice(order_origin_price);
						platformorderdetails.setFreight(express_fee);
						platformorderdetails.setPalcedTime(order_time);
						platformorderdetails.setProductname(product_name);
						platformorderdetails.setPrice(origin_price);
						platformorderdetails.setQuantity(count);
						platformorderdetails.setMerchantSkuId(ourSku);
						platformorderdetails.setPtype("kaola");
						if ((liststatus == null || liststatus.size() == 0)) {
							platformOrderDetailsDao.insertOrder(platformorderdetails);

						} else if (liststatus != null) {
							List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
							if (listst == null || listst.size() == 0) {
								platformOrderDetailsDao.insertOrder(platformorderdetails);
							}
						}
				}
//				String orderId = skuList.getString("order_id");
				list.add(orderId); 
				orderMap.put(orderId, orderSkus.toString());
			}
			
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		//如果有数据
		if(orderMap.size() > 0)
		{
			list.add("-1");//避免没有数据  报错
			List<PlatFormOrderRecord>  kaolaOrderList = platformStockUpdateDao.getOrdersByOrders(list);
			//用作对比 如果有记录则说明已处理
			Map<String,String> kaolaOrderMapRealy = new HashMap<String, String>();
			for(PlatFormOrderRecord k : kaolaOrderList)
			{
				kaolaOrderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Entry<String, String>> set = orderMap.entrySet();
			Iterator<Entry<String,String>> it = set.iterator();
			while(it.hasNext())
			{
				Entry<String,String> entry = it.next();
				String orderId  = entry.getKey();
				String value = entry.getValue();
				if(kaolaOrderMapRealy.get(orderId) == null) //没有保存该orderid 没处理  增加orderid数 并插入表
				{
					try
					{
						boolean updateSucces = false;
						JSONArray  orderSkus = new JSONArray(value);
						for(int index = 0; index < orderSkus.length(); index++)
						{
							JSONObject skuObj = new JSONObject(orderSkus.get(index).toString());
							String ourSku = skuObj.getString("barcode");
							if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
							ourSku = ourSku.trim();
							String[] supplySkuArr = ourSku.split("-");
							if(supplySkuArr.length == 2)
							{
								ourSku = supplySkuArr[1];
							}
							else
							{
								ourSku = supplySkuArr[0];
							}
							int num = skuObj.getInt("count");
							searchMap.put("sku", ourSku);
							searchMap.put("type", type);
							if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
							List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
							for(StockUpdate su : stockUpdateList)
							{
								su.setKaolaOrderStock(num + su.getKaolaOrderStock());
								su.setLastOrderTime("yes");
								platformStockUpdateDao.updateStockByNotNull(su);
								updateNumber++;
								updateSucces = true;
							}
						}
						
						if(updateSucces)
						{
							//记录该orderid
							PlatFormOrderRecord kaolaOrder = new PlatFormOrderRecord();
							kaolaOrder.setOrderId(orderId);
							kaolaOrder.setIdPlartform(1);
							kaolaOrder.setIdStatus(1);
							platformStockUpdateDao.insertOrder(kaolaOrder);
							
						  /*PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
							platformorderdetails.setIdorder(orderId);
							List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
							if (liststatus == null || liststatus.size() == 0) {
								int num = 0;
								String ourSku = "";
								JSONArray  orderSkuss = new JSONArray(value);
								for(int index = 0; index < orderSkuss.length(); index++)
								{
									JSONObject skuObj = new JSONObject(orderSkuss.get(index).toString());
									ourSku = skuObj.getString("barcode");
									if(null == ourSku || "".equals(ourSku) || StringUtil.isBlank(ourSku)) continue;
									ourSku = ourSku.trim();
									String[] supplySkuArr = ourSku.split("-");
									if(supplySkuArr.length == 2)
									{
										ourSku = supplySkuArr[1];
									}
									else
									{
										ourSku = supplySkuArr[0];
									}
									 num = skuObj.getInt("count");
								}
						}*/
					}
				}
					catch (Exception e)
					{
						e.printStackTrace();
						log.error(e.getMessage(), e);
						System.out.println("請求失敗,考拉");
					}
					
				}
			}
		}
		
		
	 
		return updateNumber;
	}

	
	public static void main(String[] args) throws Exception
	{
//		KaolaPlatformStockUpdateImple k = new KaolaPlatformStockUpdateImple();
//		Map<String,String> map = new LinkedHashMap<String, String>();
//		map.put("mobile", "13094818146");
//		map.put("source", "register");
//		map.put("client_id", "1");
//		map.put("cver", "6.2.0");
//		map.put("qudaoid", "10011");
//		map.put("via", "android");
//		map.put("app", "higo");
//		map.put("uuid", "B3D7155FF5B3DBFD82E7403F50A179FC");
//		map.put("imei", "133524116498006");
//		map.put("mac", "08:00:27:6d:c4:f3");
//		map.put("ver", "0.8");
//		map.put("from", "3");
//		map.put("device_token", "e678551d359615314ed296876f4329ee2d9b5e2d");
//		map.put("account_id", "0");
//		map.put("device_id", "h_3804da4cd079d33da0d30099b0262275");
//		map.put("device_version", "4.2.2");
//		map.put("h", "");
//		map.put("s_st", "1478570859");
//		map.put("s_nonse", "93693");
//		
//		
//		
//		
//		
//		String sign = "";
//		String needMD5Str = "";
//		Set<Entry<String, String>> entrySet  =  map.entrySet();
//		Iterator<Entry<String,String>> it = entrySet.iterator();
//		while(it.hasNext())
//		{
//			Entry<String,String> en = it.next();
//			needMD5Str += en.getKey() + en.getValue();
//		}
////		System.out.println(needMD5Str);
//		//System.out.println("24cd860ab4ce7df2a0a5".toUpperCase());
//		 byte[] arrayOfByte = needMD5Str.getBytes("UTF-8");
//		 byte[] paramArrayOfByte = MessageDigest.getInstance("SHA1").digest(arrayOfByte);
//		 
//		 	int i = 0;
//		    Object localObject2 = "";
//		    if (i < paramArrayOfByte.length)
//		    {
//		      String str1 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
//		      if (str1.length() == 1) {}
//		      for (String str2 = (String)localObject2 + "0" + str1;; str2 = (String)localObject2 + str1)
//		      {
//		        i++;
//		        localObject2 = str2;
//		        break;
//		      }
//
//		    }
//		    System.out.println(localObject2.toString());
		StringBuilder sb = new StringBuilder(); //记录sku对应关系
		sb.append("111111111111");
		sb.append(",");
		sb.append("222222222222");
		sb.append("\n");
		
		MiniUiUtil.writeText2File(SH_KAOLA_SKU_FILE_NAME, "", "cx");

		
		    
		MiniUiUtil.writeText2File(SH_KAOLA_SKU_FILE_NAME, sb.toString(), "zj");
		
	}
	
	
	
	
}
 
