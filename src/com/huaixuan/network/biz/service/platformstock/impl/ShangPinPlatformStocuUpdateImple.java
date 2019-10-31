package com.huaixuan.network.biz.service.platformstock.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockNumber;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.autosyn.impl.AutoSyncShangPingManagerImpl;
import com.huaixuan.network.biz.service.platformstock.ShangPinPlatformStocuUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-5-11 下午01:49:48
 **/

@Service("shangPinPlatformStocuUpdate")
public class ShangPinPlatformStocuUpdateImple implements ShangPinPlatformStocuUpdate
{

	private String sh_app_key = "e103f11d703c40ffa1c6723650d77146";
	private String sh_app_secrt = "1d1d46f8465e4c1d8d78680e5b66a83c";
	
	private String hk_app_key = "1dff379e72854c02981291b13a380e78";
	private String hk_app_secrt = "9eefbf26d9c343ab8e6d45b0246539b0";
	private Map<String, String> paramMap = new TreeMap<String, String>(); //key按自然排序
	
	private String updateStockURL ="http://open.shangpin.com:8080/stock/update"; //更新库存地址
	private String selectStockURL ="http://open.shangpin.com:8080/stock/findinfo"; //查询库存地址
	private String selectGoodsInfoURL ="http://open.shangpin.com:8080/commodity/findinfobypage"; //查询商品信息地址
	private String selectOrder ="http://open.shangpin.com:8080/purchase/findporderbypage"; //查询订单信息
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	@Autowired
	private  AutoSyncDao autoSyncDao;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	
	@Autowired
	private StockUpdateService stockService;


	//组装签名  通过appkey reqeust timestamp app_secrt组装
	private String getSign(String type)
	{
		// 组装签名 顺序不能乱 MD5加密 大写 详细规则见文档
		String sign = "app_key=" + paramMap.get("app_key") + "&request="
				+ paramMap.get("request") + "&timestamp="
				+ paramMap.get("timestamp") + "_";
		if("hk".equals(type))
		{
			sign += hk_app_secrt;
		}
		else
		{
			sign +=  sh_app_secrt;
		}
		
		return HttpRequest.string2MD5(sign).toUpperCase();
	}
	@Override
	public void updateSku2Location()
	{
		//stockService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
		
		//platformStockUpdateDao.updateStockUpdateSku2Null("shangpin_sku");//更新平台sku为NULL 
		updateSku2LocationByType("hk");
		updateSku2LocationByType("sh");
		
		//stockService.setCanUpdateStockStatus("true"); //解除-暂时不让其他地方做更新
	}
	
	 
	
	private  void updateSku2LocationByType(String type)
	{
		int pageIndex = 1;
		int pageSize = 50;
		if("hk".equals(type))
		{
			//request 发送请求需要app_key 先放入map
			paramMap.put("app_key", hk_app_key);
		}
		else
		{
			paramMap.put("app_key", sh_app_key);//request 发送请求需要app_key 先放入map
		}
		
		while(true)
		{
			paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
			String request = "{\"PageIndex\":\""+pageIndex+"\",\"PageSize\":\""+pageSize+"\""; // 组装请求数据
			request += "}";
			paramMap.put("request", request);//放在签名之前，签名需要request
			paramMap.put("sign", getSign(type));//获取签名
			String resultStr = HttpRequest.sendPost(selectGoodsInfoURL, paramMap);
			pageIndex++;
			
			try
			{			
				JSONObject jsonObject  = new JSONObject(resultStr);
				Integer responseCode = jsonObject.getInt("responseCode");
				if(responseCode == 0) //获取成功
				{	
					
					JSONObject response = jsonObject.getJSONObject("response");
					
					JSONArray goods = response.getJSONArray("SopProductSkuIces");
			 
					if(goods.length() == 0) break;
					
					List<PlatformSku2LocationSku> shangPinSkuList = new ArrayList<PlatformSku2LocationSku>();
					if(null != goods && goods.length() >0)
					{
						for(int index = 0; index<goods.length(); index++)
						{
							
							Object goodStr = goods.get(index);
							JSONObject good  = new JSONObject(HttpRequest.replaceSpecileStr(goodStr.toString()));
							JSONArray sopSkuIces = good.getJSONArray("SopSkuIces");
							String productModel = good.getString("ProductModel");
							if(sopSkuIces != null && sopSkuIces.length() > 0)
							{
								for(int i = 0; i < sopSkuIces.length();i++)
								{
									Object skuStr = sopSkuIces.get(i);
									JSONObject skuObj = new JSONObject(skuStr.toString());
									String shangpSKU = skuObj.getString("SkuNo");
									String productSizeText = skuObj.getString("ProductSizeText");
									String IsDeleted = skuObj.getString("IsDeleted");
									String supplierSkuNo = skuObj.getString("SupplierSkuNo");
									//没有尚品sku或者已删除的不处理
									if(null == shangpSKU || "".equals(shangpSKU) || "1".equals(IsDeleted))
									{
										continue;
									}
					 
									 
									if(null == supplierSkuNo || "".equals(supplierSkuNo))
									{
		
										supplierSkuNo = this.getSupplierSkuByNameAndSize(productModel, productSizeText);
										if(StringUtil.isNotBlank(supplierSkuNo))
										{
											//记录没有供货商sku的数据
											//writeFile(supplierSkuNo,shangpSKU,type+"success");
										}
										else
										{
											//writeFile(supplierSkuNo,shangpSKU,type+"error");
										}
									}
									if(StringUtil.isNotBlank(supplierSkuNo))
									{
										supplierSkuNo = supplierSkuNo.trim();
										//同步sku到本地,单个更新使用, 批量更新
										PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
										pslSku.setOurSku(supplierSkuNo);
										pslSku.setPlatformField("shangpin_sku");
										pslSku.setPlatformSku(shangpSKU);
										pslSku.setType(type); 
										shangPinSkuList.add(pslSku);
									}
								}
							}
						}
						
						if(shangPinSkuList.size() == 0) continue;
						Map shangpinSkuMap = new HashMap();
						shangpinSkuMap.put("list", shangPinSkuList);
						shangpinSkuMap.put("platformField", "shangpin_sku");
			
						if(shangPinSkuList.size() > 0)
						{
							//更新尚品sku到本地
							platformStockUpdateDao.batchUpdatePlatformSku2Location(shangpinSkuMap);
						}
					}
					
				}
				else
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("shangpin syn sku error " + pageIndex);
				if(pageIndex > 500) break;
			}
			
		 
			
		}	
	
	}
	
	public void writeFile(String ourSku,String shangPsku,String type)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String fileName =  type + "_"  + "_shangpin_sku.txt";
		String path = "d:/log";
		File f = new File(path);
		if(!f.exists()){f.mkdirs();}
		path = path + Constants.FILE_SEP  + fileName;
	 
		String str = shangPsku + "," + ourSku + "\n";;
		RandomAccessFile accessFile = null;
		try
		{
			accessFile = new RandomAccessFile(path,"rw");
			long fileLength = accessFile.length();
			accessFile.seek(fileLength);
			accessFile.write(str.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
						e.printStackTrace();
					}
				}
		}
		
	}
	public String getSupplierSkuByNameAndSize(String productModel, String productSizeText)
	{
		
		String type = "";
		String material = "";
		String color = "";
		String size = "";
		String[] arr = productModel.split(" ");
		// 获取型号材质颜色
		if (arr.length >= 3)
		{
			type = arr[0];
			material = arr[1];
			color = arr[2];
		}
		else if (arr.length == 1)
		{
			type = arr[0];
		}
		else if(arr.length == 2)
		{
			type = arr[0];
			color = arr[1];
		}
		else
		{
			System.out.println("获取材质颜色出错:" + productModel);
		}
		

		// 获取大小
		if(null != productSizeText && !"".equals(productSizeText))
		{
		
				String[] sizeArr = productSizeText.split(":");
				if(sizeArr.length == 1)
					sizeArr = productSizeText.split("：");
				if (sizeArr.length == 2)
				{
					size = sizeArr[1];
				}
				else if (sizeArr.length == 1)
				{
					size = sizeArr[0];
				}
				else
				{
					System.out.println("获取size出错:" + productSizeText);
				}
			
		}
		if("均码".equals(size) || "f".endsWith(size) || "F".equals(size))
			size = "";
		
		if(!"".equals(type)) type = type.trim();
		//型号去掉点
		if(!"".equals(type)) type = type.replace(".", "");
		if(!"".equals(type)) type = type.replace("。", "");
		
		//颜色去点
		if(!"".equals(color)) color = color.trim();
		if(!"".equals(color)) color = color.replace(".", "");
		if(!"".equals(color)) color = color.replace("。", "");
		
		//材质取点
		if(!"".equals(material)) material = material.trim();
		if(!"".equals(material)) material = material.replace(".", "");
		if(!"".equals(material)) material = material.replace("。", "");
		
		if(!"".equals(size)) size = size.trim();
		if("3XL".equals(size)) size = "XXXL";
		if("2XL".equals(size)) size = "XXL";
		Map<String,String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("material", material);
		map.put("color", color);
		map.put("size", size);
		//型号有误  包含中文
		if(AutoSyncShangPingManagerImpl.isContainsChinese(type))
		{
			return "";
		}
		String localSku =  autoSyncDao.getSupplkierSkuByInfo(map);
		if("".equals(localSku) && arr.length == 2)
		{
			map.put("type", type);
			map.put("material", color);
			map.put("color", "");
			map.put("size", size);
			return autoSyncDao.getSupplkierSkuByInfo(map);
		}
		else
		{
			return localSku;
		}
		

	}
	@Override
	public void updateAllStock()
	{
		
		updateAllStockByType("hk");
		updateAllStockByType("sh");
	}
	
	
	private  void updateAllStockByType(String type)
	{
		int pageIndex = 1;
		int pageSize = 50;
		if("hk".equals(type))
		{
			//request 发送请求需要app_key 先放入map
			paramMap.put("app_key", hk_app_key);
		}
		else
		{
			paramMap.put("app_key", sh_app_key);//request 发送请求需要app_key 先放入map
		}
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("searchCanSale", "yes");// 查询可售库存 searchCanSale 参数必须要
		searchMap.put("type", type);//type 参数必须要
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		
		//key为尚品sku value为当前可售数量
		Map<String,StockUpdate> platformSkuMap = new HashMap<String, StockUpdate>();

		for(StockUpdate s : list)
		{
			platformSkuMap.put(s.getSku(), s);	
		}
		
		while(true)
		{
			paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
			String request = "{\"PageIndex\":\""+pageIndex+"\",\"PageSize\":\""+pageSize+"\""; // 组装请求数据
			request += "}";
			paramMap.put("request", request);//放在签名之前，签名需要request
			paramMap.put("sign", getSign(type));//获取签名
			String resultStr = HttpRequest.sendPost(selectGoodsInfoURL, paramMap);
			pageIndex++;
			
			try
			{			
				JSONObject jsonObject  = new JSONObject(resultStr);
				Integer responseCode = jsonObject.getInt("responseCode");
				if(responseCode == 0) //获取成功
				{	
					
					JSONObject response = jsonObject.getJSONObject("response");
					
					JSONArray goods = response.getJSONArray("SopProductSkuIces");
			 
					if(goods.length() == 0) break;//没有数据 跳出循环
					
					if(null != goods && goods.length() >0)
					{
						for(int index = 0; index<goods.length(); index++)
						{
							
							Object goodStr = goods.get(index);
							JSONObject good  = new JSONObject(HttpRequest.replaceSpecileStr(goodStr.toString()));
							JSONArray sopSkuIces = good.getJSONArray("SopSkuIces");
							String productModel = good.getString("ProductModel");
							if(sopSkuIces != null && sopSkuIces.length() > 0)
							{
								for(int i = 0; i < sopSkuIces.length();i++)
								{
									Object skuStr = sopSkuIces.get(i);
									JSONObject skuObj = new JSONObject(skuStr.toString());
									String shangpSKU = skuObj.getString("SkuNo");
									String productSizeText = skuObj.getString("ProductSizeText");
									String IsDeleted = skuObj.getString("IsDeleted");
									String supplierSkuNo = skuObj.getString("SupplierSkuNo");
									
									//没有尚品sku或者已删除的不处理
									if(null == shangpSKU || "".equals(shangpSKU) || "1".equals(IsDeleted))
									{
										continue;
									}
									//尚品有些没有填SKU  通过型号 材质  颜色来匹配
									if(StringUtil.isBlank(supplierSkuNo))
									{
										supplierSkuNo = getSupplierSkuByNameAndSize(productModel, productSizeText);
									}
									if(StringUtil.isBlank(supplierSkuNo)) continue;
									
									StockUpdate su =  platformSkuMap.get(supplierSkuNo);
									int nowNum = 0;
									if(null != su)
									{
										nowNum = su.getNowStockNum()-su.getOrderStockNum();
									}
									if(nowNum < 0) nowNum = 0;//更新库存数不能小于0
									
									//更新库存
									boolean flag = updateShangpinStocku(shangpSKU,supplierSkuNo,nowNum,type);
									
									//更新成功 设置上次更新到平台库存数,防止单个更新的在做更新
									if(flag && StringUtil.isNotBlank(supplierSkuNo))
									{
										StockUpdate stockUpdate = new StockUpdate();
										stockUpdate.setType(type);
										stockUpdate.setSku(supplierSkuNo);
										stockUpdate.setLastUpdateStockNum(nowNum);
										if(su != null) 
										{
											stockUpdate.setLastUpdateStockNum(nowNum-su.getOrderStockNum());
										}
										//platformStockUpdateDao.updateLastUpdateStockBySkuAndTypeForOne(stockUpdate); 不更新上次更新数 避免其他平台更新不到
									}
									try
									{
										Thread.sleep(500);
									}
									catch (InterruptedException e)
									{
										e.printStackTrace();
									}
								}
							}
						}
					}
					
				}
				else
				{
					try
					{
						Thread.sleep(1000); //获取出错 休息1秒
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
			}
			catch (Exception e)
			{
				if(pageIndex > 500) break;
			}
			
			
			
		}
	}
	
	@Override
	public boolean updateShangpinStockByOurSku(String shangpinSku, int num,String type)
	{
		return updateShangpinStocku(shangpinSku,"",num,type);
	}
	
	@Override
	public boolean  updateShangpinStocku(String shangpinSku,String ourSku,int num,String type)
	{
		if("hk".equals(type))
		{
			//request 发送请求需要app_key 先放入map
			paramMap.put("app_key", hk_app_key);
			
		}
		else
		{
			paramMap.put("app_key", sh_app_key);//request 发送请求需要app_key 先放入map
		}
		
		if(StringUtil.isBlank(shangpinSku) && StringUtil.isBlank(ourSku)) return true;
		
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("shangpinSku", shangpinSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(shangpinSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "shangpin_sku");
			shangpinSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(shangpinSku)) return true;
		
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
		String request = "{\"InventoryQuantity\":" + num + ",\"SkuNo\":\"" + shangpinSku + "\"}"; // 组装请求数据
		
		paramMap.put("request", request);//放在签名之前

		paramMap.put("sign", getSign(type));
		String resultStr = HttpRequest.sendPost(updateStockURL, paramMap);
		boolean flag = true;
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", shangpinSku);
		logMap.put("name", "shangpin");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		try
		{
			JSONObject jsonObj = new JSONObject(resultStr);
			Integer responseCode = jsonObj.getInt("responseCode");
			if(responseCode == 0)
			{
				flag = true;
			}
			else
			{
				 logMap.put("type", "error");
				 logMap.put("error",jsonObj.getString("responseMsg"));
				 try
				{
					Thread.sleep(30*1000); //更新出错 休息30秒 避免更新过快
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (JSONException e)
		{
			 logMap.put("type", "error");
			 logMap.put("error",resultStr);
			 try
			{
				Thread.sleep(30*1000); //更新出错 休息30秒 避免更新过快
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
		//记录日志
		autoSyncDao.addUpdateLog(logMap);
		return flag;
	}
	
	
	
	@Override
	public StockNumber getShangpinStockByOurSku(String ourSku,String type)
	{
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("sku", ourSku);
		searchMap.put("type", type);
		searchMap.put("field", "shangpin_sku");
		String shangpinSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		return getShangpinStockByShangPinSku(shangpinSku,type);
	}
	
	/**
	 * 尚品只有可售库存  没有冻结库存
	 */
	@Override
	public StockNumber getShangpinStockByShangPinSku(String shangpinSku,String type)
	{
		StockNumber sn = null;
		if(StringUtil.isBlank(shangpinSku)) return sn;
		if("hk".equals(type))
		{
			//request 发送请求需要app_key 先放入map
			paramMap.put("app_key", hk_app_key);
		}
		else
		{
			paramMap.put("app_key", sh_app_key);//request 发送请求需要app_key 先放入map
		}		
		paramMap.put("timestamp", sdf.format(new Date())); // 每次发起请求都是最新时间
		 
		String requestStr = "[\"" + shangpinSku + "\"]";
		
		paramMap.put("request", requestStr.toString()); // 放在签名之前，签名要用到request
		paramMap.put("sign", getSign(type));

		String resultStr = HttpRequest.sendPost(selectStockURL, paramMap);
		try
		{
			JSONObject jsonObj = new JSONObject(resultStr);
			Integer responseCode = jsonObj.getInt("responseCode");
			if (responseCode == 0)
			{
				JSONArray goods = jsonObj.getJSONArray("response");
				if (goods != null && goods.length() > 0)
				{
					for (int index = 0; index < goods.length(); index++)
					{
						Object skuObjStr = goods.get(index);
						JSONObject skuObj = new JSONObject(HttpRequest.replaceSpecileStr(skuObjStr.toString()));
						String shangpSkU = skuObj.getString("SkuNo");
						String supplierSKU = skuObj.getString("SupplierSkuNo");
						int stockNum = skuObj.getInt("InventoryQuantity");
						sn = new StockNumber();
						sn.setOurSku(supplierSKU);
						sn.setPlatformSku(shangpSkU);
						sn.setCanSaleNum(stockNum);
					}
				}
			}
		}
		catch (JSONException e)
		{
		}
		return sn;
	}
	
	
	@Override
	public  int atuoSyncOrder(String startTime,String endTime,String type)
	{
		if("sh".equals(type))
		{
			paramMap.put("app_key", sh_app_key);
		}
		else
		{
			paramMap.put("app_key", hk_app_key);
		}
		
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
		String request = "{\"UpdateTimeBegin\":\"" + startTime + "\",\"UpdateTimeEnd\":\"" + 
			endTime  + "\",\"DetailStatus\":[1,5],\"PageIndex\":\"1\",\"PageSize\":\"20\"}"; // 组装请求数据 DetailStatus[1]  为刚推送uolai订单
		
		paramMap.put("request", request);//放在签名之前
		paramMap.put("sign", getSign(type));
		String resultStr = HttpRequest.sendPost(selectOrder, paramMap);
		Map<String,String> searchMap = new HashMap<String, String>();
		int updateNumber = 0;
		try
		{
			JSONObject jsonObject = new JSONObject(resultStr);
			if("0".equals(jsonObject.getString("responseCode")))
			{
				JSONObject skuListResponse = new JSONObject(jsonObject.getString("response"));
				JSONArray skuList =  skuListResponse.getJSONArray("PurchaseOrderDetails");
				for(int index =0; index < skuList.length(); index++)
				{
					JSONObject skuObj = new JSONObject(skuList.get(index).toString());
					String ourSku = skuObj.getString("SupplierSkuNo");
					int detailStatus = skuObj.getInt("DetailStatus");//状态  1.待处理 5.取消  取消减 1增加
					int num = 1;
					if(StringUtil.isNotBlank(ourSku) && StringUtil.isNotBlank(type))
					{
						ourSku = ourSku.trim();
						searchMap.put("type", type);
						searchMap.put("sku", ourSku);
						List<StockUpdate>  list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						for(StockUpdate su : list)
						{
							if(1 == detailStatus)
							{
								su.setShangpinOrderStock(num + su.getShangpinOrderStock());
							}
							else if(5 == detailStatus)
							{
								int shangpinOrderStock = su.getShangpinOrderStock();
								shangpinOrderStock = shangpinOrderStock - num;
								if(shangpinOrderStock < 0 )shangpinOrderStock = 0;
								su.setShangpinOrderStock(shangpinOrderStock); //已取消的订单 未下单
							}
							su.setLastOrderTime("yes");
							platformStockUpdateDao.updateStockByNotNull(su);//更新订单数
							updateNumber++;
						}
				 
					}
					
				}
			}
			
		}
		catch (JSONException e)
		{
			//e.printStackTrace();
			System.out.println("請求失敗,尚品");
		}
		
		return updateNumber;	 
	}

	public static void main(String[] args)
	{
		ShangPinPlatformStocuUpdateImple sp = new ShangPinPlatformStocuUpdateImple();
		sp.atuoSyncOrder("2016-5-24 17:09:22", "2016-5-24 17:09:23", "sh");
	}
 
}
 
