package com.huaixuan.network.biz.service.autosyn.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.AutoSynDate;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.autosyn.ShangPinSku;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.service.autosyn.AutoSyncShangPingManager;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.autosyn.Result;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;

 
/**
 *@author YangJie
 *@date 2015-8-6 下午05:11:34 尚品对接 弃用
 */
 
public class AutoSyncShangPingManagerImpl implements AutoSyncShangPingManager
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
	

	private static final String logFileName = "shangping";//记录日志文件名称
	private static final String path = "d:/stock_log/";//记录库存的日志文件夹

	
	@Autowired
	private  AutoSyncDao autoSyncDao;
	

	/**
	 * 
	 * @param pageIndex 当前页数
	 * @param pageSize   页面大小
	 * @param type         sh 上海 hk香港  默认上海 			
	 * @return map key尚品SKU-  val-尚品sku对象
	 */
	public Map<String,ShangPinSku> getGoodsInfo(Integer pageIndex,Integer pageSize,String type)
	{
		Map<String,ShangPinSku> resultMap = new HashMap<String, ShangPinSku>(); //key尚品SKU    val 尚品SKU对象
		if(pageIndex == null || pageIndex < 0 || pageSize == null || pageSize < 0) return resultMap;
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
		String request = "{\"PageIndex\":\""+pageIndex+"\",\"PageSize\":\""+pageSize+"\"}"; // 组装请求数据
		
		paramMap.put("request", request);//放在签名之前，签名需要request
		
		paramMap.put("sign", getSign(type));//获取签名
		
		String resultStr = HttpRequest.sendPost(selectGoodsInfoURL, paramMap);
		
		try
		{			
			JSONObject jsonObject  = new JSONObject(resultStr);
			Integer responseCode = jsonObject.getInt("responseCode");
			if(responseCode == 0) //获取成功
			{	
				
				JSONObject response = jsonObject.getJSONObject("response");
				JSONArray goods = response.getJSONArray("SopProductSkuIces");
				if(null != goods && goods.length() >0)
				{
					for(int index = 0; index<goods.length(); index++)
					{
						
						Object goodStr = goods.get(index);
						JSONObject good  = new JSONObject(HttpRequest.replaceSpecileStr(goodStr.toString()));
						JSONArray sopSkuIces = good.getJSONArray("SopSkuIces");
						String sopProductName = good.getString("SopProductName");
						String productModel = good.getString("ProductModel");
						
						if(sopSkuIces != null && sopSkuIces.length() > 0)
						{
							for(int i = 0; i < sopSkuIces.length();i++)
							{
								Object skuStr = sopSkuIces.get(i);
								JSONObject skuObj = new JSONObject(skuStr.toString());
								String supplierSKU = skuObj.getString("SupplierSkuNo");
								String shangpSKU = skuObj.getString("SkuNo");
								String productSizeText = skuObj.getString("ProductSizeText");
								int skuStatus = skuObj.getInt("SkuStatus");
								String status = "0";
								if(null == shangpSKU || "".equals(shangpSKU)) continue;
								ShangPinSku p = new ShangPinSku();
								p.setName(sopProductName);
								p.setSku(shangpSKU);
								p.setSupplierSku(supplierSKU);
								p.setSize(productSizeText);
								p.setType(productModel);
								if(1 == skuStatus)
								{
									status = "商品状态待审核";
								}
								else if (2 == skuStatus)
								{
									status = "上架";
								}
								else if(3 == skuStatus)
								{
									status = "待上架";
								}
								else if(4 == skuStatus)
								{
									status = "审核不通过";
								}
								else if (5 == skuStatus)
								{
									status = "已下架 ";
								}
								p.setStatus(status);						
								resultMap.put(shangpSKU,p);
							}
						}
						
					}
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultMap;
		
	}

	
	
	
	
	/**
	 * 通过尚品SKU查询 库存 返回Map  key-尚品SKU  value-数量
	 * @param list  尚品SKU集合
	 * @param type sh 查询上海库存  hk查询香港库存
	 * @return
	 */
	public Map<String,Integer> selectStock(List<String> list,String type)
	{
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if (list == null || list.size() == 0)
			return resultMap;
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
		List<List<String>> lists = HttpRequest.createList(list, 200);//每次最多查询200条数据，分割后 在循环查找库存

		for (int i = 0; i < lists.size(); i++)
		{
			List<String> listTmp = lists.get(i);
			// 组装请求数据
			StringBuilder requestStr = new StringBuilder();
			requestStr.append("[");
			for (int j = 0; j < listTmp.size(); j++)
			{
				requestStr.append("\"" + listTmp.get(j) + "\"");
				if (j < listTmp.size() - 1)
				{
					requestStr.append(",");
				}
			}
			requestStr.append("]");
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
							Integer stockNum = skuObj.getInt("InventoryQuantity");
							resultMap.put(shangpSkU, stockNum);
						}
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		
		//没有查询到该库存设置为0
		for(String shangpSkU :list)
		{
			if(resultMap.get(shangpSkU) == null)
			{
				resultMap.put(shangpSkU, 0);
			}
		}

		return resultMap;
		
	}
	
	/**
	 * 更新库存
	 * @param sku
	 *            尚品SKU
	 * @param num
	 *            现库存
	 * @return
	 */
	public Result updateShangpStock(String paltFormSku, Integer num,String type,String huoHao,Integer beforNum,String ourSku)
	{
		if("sh".equals(type))
		{
			paramMap.put("app_key", sh_app_key);
		}
		else
		{
			paramMap.put("app_key", hk_app_key);
		}
		Result result = new Result();
		if("".equals(paltFormSku) || null == paltFormSku || num < 0 || null == num) 
		{
			result.setMsg("参数错误");
			result.setOK(false);
			return result;
		}
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
		String request = "{\"InventoryQuantity\":" + num + ",\"SkuNo\":\"" + paltFormSku + "\"}"; // 组装请求数据
		
		paramMap.put("request", request);//放在签名之前

		paramMap.put("sign", getSign(type));
		String resultStr = HttpRequest.sendPost(updateStockURL, paramMap);
		System.out.println(resultStr);
		
		try
		{
			JSONObject jsonObj = new JSONObject(resultStr);
			Integer responseCode = jsonObj.getInt("responseCode");
			
			//记录更新日志
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", paltFormSku);
			logMap.put("name", "尚品");
			logMap.put("stock", num+"");
			logMap.put("location", type);
			logMap.put("type", "success");
			if(responseCode == 0)
			{
				 result.setOK(true);
				 LogData logData = new LogData();
				 logData.setFileName(logFileName);
				 logData.setBeforNum(beforNum);
				 logData.setNowNum(num);
				 logData.setPlatformSku(paltFormSku);
				 logData.setSku(ourSku);
				 logData.setType(type);
				 logData.setHuohao(huoHao);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
				
			}
			else
			{
				 result.setOK(false);
				 result.setMsg(jsonObj.getString("responseMsg"));
				 LogData logData = new LogData();
				 logData.setFileName("shangpin_error");
				 logData.setType(type);
				 logData.setError(jsonObj.getString("responseMsg"));
				 logData.setSku(ourSku);
				 logData.setPlatformSku(paltFormSku);
				 logData.setBeforNum(beforNum);
				 logData.setNowNum(num);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的错误日志文件
				 
				 
				 logMap.put("type", "error");
				 logMap.put("error",jsonObj.getString("responseMsg"));
			}
			
			
			//记录日志
			autoSyncDao.addUpdateLog(logMap);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	

	
	 
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
	
	
	
	
	/**
	 * 同步上海库存
	 */
	public List<String>  syncShangPingStockForSh()
	{
		paramMap.put("app_key", sh_app_key);//request 发送请求需要app_key 先放入map
		
		String type = "sh";//sh同步上海库存   hk同步香港库存
		
		return updateStockByType(type);
		
	}
	
	/**
	 * 同步香港库存
	 */
	public List<String>  syncShangPingStockForHk()
	{
		paramMap.put("app_key", hk_app_key);//request 发送请求需要app_key 先放入map
		
		String type = "hk";//sh同步上海库存   hk同步香港库存
		
		return updateStockByType(type);
		
	}

	
	/**
	 * 全量 同步上海库存 
	 * 整个思路 通过商品接口查询尚品产品  在通过尚品产品SKU查询该SKU的库存 
	 * 在和本地的库存做对比 如果不一样则更新本地库存到尚品
	 * @param type  必须  sh  更新上海  hk 更新香港
	 *  
	 */
	private  List<String> updateStockByType(String type)
	{
		int pageIndex = 1; 
		int pageSize = 200; //每页做多200条
		List<String> saveUpdatedList = new ArrayList<String>(); //保存更新过的SKU  页面显示用
		
		while(true)
		{
			Map<String,ShangPinSku> skusMap = getGoodsInfo(pageIndex, pageSize,type); //每页最多查询200条
			if(skusMap.size() == 0) break; //查询不到对应的产品后结束循环
			pageIndex++;//页数加1 不然死循环
			Collection<String> values = skusMap.keySet();
			List<String> shangpSkuList = new ArrayList<String>(values);
			
			//查询尚品库存
			Map<String,Integer> shangpSkuStockMap = selectStock(shangpSkuList,type);
			
			
			
			Set<String>  shangpSkuSet = shangpSkuStockMap.keySet();
			
			
			//下面是获取本地库存做的处理  通过尚品sku查询本地库存
			Iterator<String> it = shangpSkuSet.iterator();
			//拼接sql
			StringBuilder sql = new StringBuilder("'-1'");//防止list没数据报错
			if(shangpSkuSet.size() > 0) sql.append(",");
			while(it.hasNext())
			{
				String supplierSku = it.next();
				sql.append("'" + supplierSku + "'");
				if(it.hasNext())
					sql.append(",");
				
			}
			
			List<AutoSynDate>  list  ;
			if("hk".equals(type))
			{
				list = autoSyncDao.getHkStockBySkus(sql.toString());
			}
			else
			{
				list = autoSyncDao.getShStockBySkus(sql.toString());
			}
			Map<String,Integer> supplierSkuStockMap = new HashMap<String, Integer>(); //供货商本地库存 key-尚品SKU val-数量
			for(int i = 0;i < list.size();i++)
			{
				AutoSynDate data =list.get(i);
				int num = data.getShNumber(); //默认更新上海库存
				if("hk".equals(type))
				{
					num= data.getHkNumber(); //更新香港库存
				}
				supplierSkuStockMap.put(data.getSku(), num);
			}
			//获取本地库存结束  放入map  用作对比
			it = shangpSkuSet.iterator();
			while(it.hasNext())
			{
					String shangpSku = it.next();					
					Integer supplierNumInSaler = shangpSkuStockMap.get(shangpSku); //在尚品库存
					Integer supplierNumInLocal = supplierSkuStockMap.get(shangpSku); //上本地库存
			
					ShangPinSku skuObj = skusMap.get(shangpSku);
					Result updateR = new Result();
					if(null == supplierNumInLocal && 0 == supplierNumInSaler) continue;//两边都没库存不做更新

					if(null == supplierNumInLocal || "".equals(supplierNumInLocal))
					{
						updateR = updateShangpStock(shangpSku, 0,type,skuObj.getType() + " " + skuObj.getSize(),supplierNumInSaler,shangpSku);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else if(supplierNumInSaler != supplierNumInLocal)
					{
						updateR = updateShangpStock(shangpSku, supplierNumInLocal,type,skuObj.getType() + " " + skuObj.getSize(),supplierNumInSaler,shangpSku);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
					
							e.printStackTrace();
						}
					}				
			}
		}
		return saveUpdatedList;
	}
	
	
	
	
	public String getSupplierSkuByNameAndSize(String productModel, String productSizeText)
	{
		
		productModel = productModel.replace("红色", "");
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
		if (null != productSizeText && !"".equals(productSizeText))
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
	
	private static String regEx = "[\u4e00-\u9fa5]";
	private static Pattern pat = Pattern.compile(regEx);
	public static boolean isContainsChinese(String str)
	{
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())    {
		flg = true;
		}
		return flg;
	}
	
 
	/**
	 * 更新尚品sku到本地 默认更新2个小时内的数据
	 */
	public List<String> updateShangpSku(String type,String isAll)
	{

		int pageIndex = 1;
		int pageSize = 50;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if("hk".equals(type))
		{
			//request 发送请求需要app_key 先放入map
			paramMap.put("app_key", hk_app_key);
			
			if(null != isAll && !"".equals(isAll))
			{
					//全量更新 清空尚品香港sku为null
					//autoSyncDao.clearPlatformSku2Null("sku_shangpin_hk");
			}
			
		}
		else
		{
			paramMap.put("app_key", sh_app_key);//request 发送请求需要app_key 先放入map
			if(null != isAll && !"".equals(isAll))
			{
					//全量更新 清空尚品上海sku为null
					//autoSyncDao.clearPlatformSku2Null("sku_shangpin_sh");
			}
		}
		List<String> resultList = new ArrayList<String>();
		Map<String,String> paramsMap = new HashMap<String, String>();

		while(true)
		{
			Calendar rightNow = Calendar.getInstance();
			rightNow.add(Calendar.HOUR, -2);
			Date beforDate = rightNow.getTime();
			Date nowDate = new Date();
			paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
			String request = "{\"PageIndex\":\""+pageIndex+"\",\"PageSize\":\""+pageSize+"\""; // 组装请求数据
			//isAll=true  全量更新  isAll=day  更新2天的    默认更新2个小时内的
			if("".equals(isAll) || null == isAll) //默认2小时
			{
				request += ",\"startTime\":\"" +  format.format(beforDate) + "\",\"endTime\":\"" + format.format(nowDate)+"\""; 
			}
			else if("day".equals(isAll)) //更新2天的
			{
				rightNow.add(Calendar.HOUR, -48);
				beforDate = rightNow.getTime();
				nowDate = new Date();
				request += ",\"startTime\":\"" +  format.format(beforDate) + "\",\"endTime\":\"" + format.format(nowDate)+"\"";
			}
			
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
					if(null != goods && goods.length() >0)
					{
						for(int index = 0; index<goods.length(); index++)
						{
							
							Object goodStr = goods.get(index);
							JSONObject good  = new JSONObject(HttpRequest.replaceSpecileStr(goodStr.toString()));
							JSONArray sopSkuIces = good.getJSONArray("SopSkuIces");
							String sopProductName = good.getString("SopProductName");
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
									
									if(null == shangpSKU || "".equals(shangpSKU) || "1".equals(IsDeleted))
									{
										//System.out.println(productModel + " " + productSizeText);
										continue;
									}
					 
									if(null == supplierSkuNo && "".equals(supplierSkuNo))
										supplierSkuNo = this.getSupplierSkuByNameAndSize(productModel, productSizeText);
									
									if(!"".equals(supplierSkuNo))
									{
										paramsMap.put("suppliersku", supplierSkuNo);
										//两个账号 分别更新不同字段
										if("hk".equals(type))
										{
											paramsMap.put("sku_shangpin_hk", shangpSKU);
										}
										else
										{
											paramsMap.put("sku_shangpin_sh", shangpSKU);
										}
										
										 autoSyncDao.updateShangPinSku(paramsMap);
										
									}
								
								}
							}
							
						}
					}
					
				}
				else
				{
					break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return resultList;
	}
	
	/*
	//更新尚品SKU为空  (用于处理被删除的产品 先把SKU更新为空  然后再更新)
	private void updateShangPinSku2Null(String shangPinSku,String type)
	{
		Map paramsMap = new HashMap<String, String>();
		//两个账号 分别更新不同字段
		if("hk".equals(type))
		{
			paramsMap.put("sku_shangpin_hk", shangPinSku);
		}
		else
		{
			paramsMap.put("sku_shangpin_sh", shangPinSku);
		}
		autoSyncDao.updateShangPinSku2Null(paramsMap);
	}
	*/




    /**
     * 更新尚品库存  只更新有过改变的库存信息  
     * 每次查询当前可售库存  在记录本次查询的信息到txt 用于下次查询出来做对比
     * 对比出有库存变化的产品，在更新有过改变的产品
     */
	@Override
	public List<String> updateStockNew(String type)
	{
		
		List<String> resultList = new ArrayList<String>();
		
		List<Long> siteList = new ArrayList<Long>();
		//上海需要尚上总仓和臻革坊
		if("sh".equals(type))
		{
			siteList.add(101L); //尚上总仓
			siteList.add(202L);//臻革坊
			paramMap.put("app_key", sh_app_key);
		}
		else
		{
			siteList.add(103L);//帝国中心
			paramMap.put("app_key", hk_app_key);
		}
		StringBuffer writedStr = new StringBuffer();
		List<StockData> nowStockList = autoSyncDao.searchStockBySiteList(siteList);
		Map<String,Integer> nowStockMap = new HashMap<String, Integer>();
		for(StockData s : nowStockList)
		{
			String sku = s.getSku();
			int num = s.getNum();
			writedStr.append(sku);
			writedStr.append(",");
			writedStr.append(num);
			writedStr.append("\n");
			nowStockMap.put(sku, num);
		}
		
		//上次的库存信息
		Map<String,Integer> lastStockMap = getLastUpdateStock(type);
		
		 //写当前的库存信息,用于下一次读取,用于比对是否有改变  需要放在读取之后,不然每次数据都和现在一样
		//writeFile(type,writedStr.toString());
		
		//第一次访问没有库存信息 直接返回
		if(lastStockMap.size() == 0)
		{
			return new ArrayList<String>();
		}
		
		
		//循环做对比 如果有不同 则保存到需要更新的map中
		Map<String,Integer> needUpdateMap = new HashMap<String, Integer>();
		for(StockData s : nowStockList)
		{
			int nowStock = s.getNum();
			Integer beforStock = lastStockMap.get(s.getSku());
			if(beforStock == null) needUpdateMap.put(s.getSku(), s.getNum()); //如果没有获取到 说明是新增的产品
			else if(nowStock != beforStock) needUpdateMap.put(s.getSku(), s.getNum());//库存不一样 需要更新
		}
		
		
		//循环上一次的数据 
		Set<Entry<String, Integer>> entry = lastStockMap.entrySet();
		Iterator<Entry<String, Integer>> it = entry.iterator();
		while(it.hasNext())
		{
			Entry<String, Integer> e = it.next();
			String sku = e.getKey();
			Integer nowNum = nowStockMap.get(sku);
			Integer beforNum = lastStockMap.get(sku);
			if(nowNum == null) needUpdateMap.put(sku, 0); // 如果在当前可售库存中没找到说明是卖完了 库存更新为0
			else if(nowNum != beforNum) needUpdateMap.put(sku, nowNum);//库存不一样 需要更新
		}
		
		//查询需要的map
		Map<String,String> searchMap = new HashMap<String, String>();
		
		//有库存改变的数据 查询平台sku 然后更新库存 记录日志
		Set<Entry<String,Integer>> set = needUpdateMap.entrySet();
		Iterator<Entry<String, Integer>> it2  = set.iterator();
		while(it2.hasNext())
		{
			Entry<String, Integer> e = it2.next();
			String sku = e.getKey();
			Integer num = e.getValue();
			if("".equals(sku) || null == sku) continue;
			
			searchMap.put("sku", sku);
			if("sh".equals(type))
			{
				searchMap.put("field", "sku_shangpin_sh");
			}
			else
			{
				searchMap.put("field", "sku_shangpin_hk");
			}
			//查询对应平台的sku,searchMap的field字段为平台sku的字段名
			String paltFormSku = autoSyncDao.getPlatformSkuByOurSku(searchMap);
			
			Integer beforNum = lastStockMap.get(sku);
			if(null == beforNum) beforNum = 0;
			String huoHao = autoSyncDao.getHuoHaoByOurSku(searchMap);
			
 
			//更新尚品库存
			 updateShangpStock(paltFormSku, num, type, huoHao, beforNum,paltFormSku);
			
			//更新后暂停1秒 避免更新过快
			try
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		 
	 
			
		}
		
		
		return resultList;
	}
	
	//获取珍品审核未通过的产品
	private Map<String,String> getZhenPinNoPass(String type)
	{
		Map<String,String> map = new LinkedHashMap<String, String>();
		File file = new File(path+type+"_no_pass.txt");
		if(!file.exists()) return map;
		 
		FileReader fReader = null;
		BufferedReader bread = null;
		try
		{
			fReader = new FileReader(file);
			bread = new BufferedReader(fReader);
			String line = "";
			while((line = bread.readLine()) != null)
			{
				 if("".equals(line)) continue;
			      String[] arr = line.split(",");
			      if(arr.length == 2)
			      {
			    	  String sku = arr[0];
			    	  String time = arr[1];
			    	  map.put(sku, time);
			      }
			      
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
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
		return map;
	}
	
	//获取上一次更新的库存
	private Map<String,Integer> getLastUpdateStock(String type)
	{
		Map<String,Integer> map = new HashMap<String, Integer>();
		File file = new File(path+type+".txt");
		if(!file.exists()) return map;
		 
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
			    	  String sku = arr[0];
			    	  int num = 0;
			    	  try
			    	  {
			    		  num = Integer.valueOf(arr[1]);
			    	  }
			    	  catch (Exception e) 
			    	  {
			    		  	
			    	  }
			    	  map.put(sku, num);
			      }
			      
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
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
		return map;
	}
	
	private void writeFile(String type,String str)
	{

		File f1 = new File(path);
		if(!f1.exists()) f1.mkdir();
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy_MM_dd");
		File f2 = new File(path + type+ "_" + sf1.format(new Date()) + ".txt"); //第一天刚开始


		File f = new File(path + type+".txt");
		
		FileOutputStream fos = null;
		try
		{
			if(!f.exists()) f.createNewFile();
			
			fos = new FileOutputStream(f);
			fos.write(str.getBytes());
			
			if(!f2.exists())
			{
				f2.createNewFile();
				fos = new FileOutputStream(f2);
				fos.write(str.getBytes());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
			try
			{
				if(fos != null) 
					fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}


	
	/**
	 * 获取有过库存改变的信息
	 */
	@Override
	public Map<String, Integer> getChangedStock(String type)
	{
		List<Long> siteList = new ArrayList<Long>();
		//上海需要尚上总仓和臻革坊
		if("sh".equals(type))
		{
			for(String s : MiniUiUtil.shSite)
			{
				siteList.add(Long.valueOf(s));
			}
		}
		else
		{
			for(String s : MiniUiUtil.hkSite)
			{
				siteList.add(Long.valueOf(s));
			}
		}
		StringBuffer writedStr = new StringBuffer();
		List<StockData> nowStockList = autoSyncDao.searchStockBySiteList(siteList);
		Map<String,Integer> nowStockMap = new HashMap<String, Integer>();
		for(StockData s : nowStockList)
		{
			String sku = s.getSku();
			int num = s.getNum();
			writedStr.append(sku);
			writedStr.append(",");
			writedStr.append(num);
			writedStr.append("\n");
			nowStockMap.put(sku, num);
		}
		
		//上次的库存信息
		Map<String,Integer> lastStockMap = getLastUpdateStock(type);
		
		 //写当前的库存信息,用于下一次读取,用于比对是否有改变  需要放在读取之后,不然每次数据都和现在一样
		writeFile(type,writedStr.toString());
		
		
		//循环做对比 如果有不同 则保存到需要更新的map中
		Map<String,Integer> needUpdateMap = new HashMap<String, Integer>();
		
		//第一次访问没有库存信息 直接返回
		if(lastStockMap.size() == 0)
		{
			return needUpdateMap;
		}
		
		for(StockData s : nowStockList)
		{
			int nowStock = s.getNum();
			Integer beforStock = lastStockMap.get(s.getSku());
			if(beforStock == null) needUpdateMap.put(s.getSku(), s.getNum()); //如果没有获取到 说明是新增的产品
			else if(nowStock != beforStock) needUpdateMap.put(s.getSku(), s.getNum());//库存不一样 需要更新
		}
		
		
		//循环上一次的数据 
		Set<Entry<String, Integer>> entry = lastStockMap.entrySet();
		Iterator<Entry<String, Integer>> it = entry.iterator();
		while(it.hasNext())
		{
			Entry<String, Integer> e = it.next();
			String sku = e.getKey();
			Integer nowNum = nowStockMap.get(sku);
			Integer beforNum = lastStockMap.get(sku);
			if(nowNum == null) needUpdateMap.put(sku, 0); // 如果在当前可售库存中没找到说明是卖完了 库存更新为0
			else if(nowNum != beforNum) needUpdateMap.put(sku, nowNum);//库存不一样 需要更新
		}
		return needUpdateMap;
	}
	
	
	
	@Override
	public void updateChangedStock(Map<String,Integer> needUpdateMap,String type)
	{
 
		//上海需要尚上总仓和臻革坊
		if("sh".equals(type))
		{
			paramMap.put("app_key", sh_app_key);
		}
		else
		{
			paramMap.put("app_key", hk_app_key);
		}
		
		  
		
		//查询需要的map
		Map<String,String> searchMap = new HashMap<String, String>();
		
		//有库存改变的数据 查询平台sku 然后更新库存 记录日志
		Set<Entry<String,Integer>> set = needUpdateMap.entrySet();
		Iterator<Entry<String, Integer>> it2  = set.iterator();
		while(it2.hasNext())
		{
			Entry<String, Integer> e = it2.next();
			String sku = e.getKey();
			Integer num = e.getValue();
			if("".equals(sku) || null == sku) continue;
			
			searchMap.put("sku", sku);
			if("sh".equals(type))
			{
				searchMap.put("field", "sku_shangpin_sh");
			}
			else
			{
				searchMap.put("field", "sku_shangpin_hk");
			}
			//查询对应平台的sku,searchMap的field字段为平台sku的字段名
			String paltFormSku = autoSyncDao.getPlatformSkuByOurSku(searchMap);
			int beforNum = -1;
			String huoHao = "";
			//String huoHao = autoSyncDao.getHuoHaoByOurSku(searchMap);

			if(StringUtil.isBlank(paltFormSku)) continue;
			
			//更新尚品库存
			 updateShangpStock(paltFormSku, num, type, huoHao, beforNum,sku);
			
			//更新后暂停0.5秒 避免更新过快
			try
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		 
	 
			
		}
		
		
		//dealZhenPinNoPass(type); //处理珍品未审核通过的产品
		
	}





	@Override
	public boolean updateStockByOurSkuAndPlatFormSku(String type, String platformSku, String ourSku, int nowNum)
	{
 
		if("".equals(platformSku) || null == platformSku || nowNum < 0) 
		{
			return true;
		}
		if("sh".equals(type))
		{
			paramMap.put("app_key", sh_app_key);
		}
		else
		{
			paramMap.put("app_key", hk_app_key);
		}
		
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())); // 每次发起请求都是最新时间
		String request = "{\"InventoryQuantity\":" + nowNum + ",\"SkuNo\":\"" + platformSku  + "\"}"; // 组装请求数据
		
		paramMap.put("request", request);//放在签名之前

		paramMap.put("sign", getSign(type));
		String resultStr = HttpRequest.sendPost(updateStockURL, paramMap);
		
		boolean resultBoolean = false; 
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", platformSku);
		logMap.put("name", "尚品");
		logMap.put("stock", nowNum+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		try
		{
			JSONObject jsonObj = new JSONObject(resultStr);
			Integer responseCode = jsonObj.getInt("responseCode");
			if(responseCode == 0)
			{
				 LogData logData = new LogData();
				 logData.setFileName(logFileName);
				 logData.setBeforNum(-1);
				 logData.setNowNum(nowNum);
				 logData.setPlatformSku(platformSku);
				 logData.setSku(ourSku);
				 logData.setType(type);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
				 resultBoolean = true;
				
			}
			else
			{

				 LogData logData = new LogData();
				 logData.setFileName("shangpin_error");
				 logData.setType(type);
				 logData.setError(jsonObj.getString("responseMsg"));
				 logData.setSku(ourSku);
				 logData.setPlatformSku(platformSku);
				 logData.setBeforNum(-1);
				 logData.setNowNum(nowNum);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的错误日志文件
				 
				 logMap.put("type", "error");
				 logMap.put("error",jsonObj.getString("responseMsg"));
				 resultBoolean = true;
			}

			
		}
		catch (JSONException e)
		{
			 logMap.put("type", "error");
			 logMap.put("error",e.getMessage());
			 resultBoolean = false; 
		}		
		//记录日志
		autoSyncDao.addUpdateLog(logMap);
		
		
		return resultBoolean;
	}
	
	public  void atuoSyncOrder(String startTime,String endTime,String type)
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
		String request = "{\"UpdateTimeBegin\":\"" + startTime + "\",\"UpdateTimeEnd\":\"" + endTime  + "\",\"DetailStatus\":[3],\"PageIndex\":\"1\",\"PageSize\":\"20\"}"; // 组装请求数据
		System.out.println(request);
		paramMap.put("request", request);//放在签名之前

		paramMap.put("sign", getSign(type));
		String resultStr = HttpRequest.sendPost(selectOrder, paramMap);
		System.out.println(resultStr);
		try
		{
			JSONObject jsonObject = new JSONObject(resultStr);
			if("0".equals(jsonObject.getString("responseCode")))
			{
				JSONObject skuListResponse = new JSONObject(jsonObject.getString("response"));
				System.out.println(skuListResponse);
				JSONArray skuList =  skuListResponse.getJSONArray("PurchaseOrderDetails");
				for(int index =0; index < skuList.length(); index++)
				{
					JSONObject skuObj = new JSONObject(skuList.get(index).toString());
					String ourSku = skuObj.getString("SupplierSkuNo");
					int num = 1;
					System.out.println(ourSku + " " + num);
					
				}
			}
			
		}
		catch (JSONException e)
		{
			//e.printStackTrace();
		}
		
	 		 
	}
	
	public static void main(String[] args)
	{
		AutoSyncShangPingManagerImpl as = new AutoSyncShangPingManagerImpl();
		as.updateShangpStock("30117960004", 2, "sh", "", 1, "7242015183573");
	}
	
	
			

}
