package com.huaixuan.network.biz.service.autosyn.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.AutoSynDate;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.service.autosyn.AutoSyncZouXiuManager;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.autosyn.Result;

/**
 * 自动更新走秀库存 弃用
 * @author ss9
 *
 */
 
public class AutoSyncZouXiuManagerImple implements AutoSyncZouXiuManager {

	private static final String UID = "3340";  //UID 走秀提供
	private static final String USERNAME = "hk140xiucom"; //用户名 走秀提供
	private static final String PASSWORD = "kpfS1IZA"; //密码 走秀提供
	private static String token;
	private static final String GET_TOKEN_URL="http://open.xiu.com/api/user/token?charset=utf-8&format=json&uid="+UID+"&_method=put";  //获取TOKEN
	private static final String SELECT_GOODS_INFO = "http://open.xiu.com/api/supplier/item/getitem?uid="+UID+"&_method=put"+"&token="; //获取商品URL 需要加token
	private static final String UPDATE_GOODS_STOCK = "http://open.xiu.com/api/supplier/item/stock?uid="+UID+"&_method=put"+"&token="; //更新商品库存URL 需要加token
	private static final String logFileName = "zouxiu"; //记录更新日志名称 zouxiu   shangping  ..等等 用于区别某个平台
	private static final boolean flag = true;//是否记录更新日志
	
	
	@Autowired
	private  AutoSyncDao autoSyncDao;
 

 
	/**
	 * 更新走秀sku到本地
	 * @param updateType  sh-上海账号  hk-香港账号 默认hk
	 * @param isAll isAll=true 更新所有  默认只更新2个小时内sku到本地
	 */
	@Override
	public List<String> updateZouXiuSku(String updateType, String isAll)
	{
		setToken();//设置token值
		
		
		List<String> resultList = new ArrayList<String>(); //保存更新结果
		if(AutoSyncZouXiuManagerImple.token == null) return resultList;
		String selectURL = SELECT_GOODS_INFO + AutoSyncZouXiuManagerImple.token;
		int pageIndex = 0;
		int pageSize = 200;		
		double maxPageIndex = 1;
		int total = 0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -2);
		Date startDate = calendar.getTime();
		Date endDate = new Date();
		
		if(null != isAll && !"".equals(isAll))
		{
			autoSyncDao.clearPlatformSku2Null("sku_zouxiu_hk");//清空sku 在重新生成
		}
		
		while(true)
		{
			pageIndex++;
			if(maxPageIndex < pageIndex) break; 
			String requestJson = "{\"pageSize\":\""+pageSize+"\",\"pageNo\":\""+pageIndex+"\"";
			if("".equals(isAll) || null == isAll)
			{
				requestJson += ",\"startTime\":\"" +  simpleDateFormat.format(startDate) + "\",\"endTime\":\"" + simpleDateFormat.format(endDate)+"\""; 
			}
			requestJson += "}";
			String resultStr = sendPutMethod(requestJson,selectURL);
			//System.out.println(resultStr);
			try
			{
				JSONObject goodsObj = new JSONObject(resultStr);
				int errorCode = goodsObj.getInt("errorCode");
				if(errorCode == 0)
				{
					
					JSONObject data = goodsObj.getJSONObject("data");
					JSONArray listArray = data.getJSONArray("list");
					if(listArray.length() == 0 ) break; //没有数据不更新
					if(listArray.length() > 0)
					{
						if(total == 0)
						{
							total = data.getInt("total");
							maxPageIndex = Math.ceil((float)total/pageSize); //设置最大页面数
						}
						for(int i = 0; i < listArray.length();i++)
						{
							 String str = listArray.get(i).toString();
							 JSONObject good = new JSONObject(str);
							 String itemId = good.getString("itemId");
							 String huoHao =good.getString("productId");
							 String brandNameZhs =good.getString("brandNameZhs");
							 String type = "";
							 String material = "";
							 String color = "";
							 String size = good.getString("itemSize");
							 if(null != huoHao)
							 {
								 String[] tmpArr = huoHao.split("_");
								 if(tmpArr.length == 2)
								 {
									 huoHao =  tmpArr[0];//把尺码给去掉，有些走秀itemid加上了尺码已货号_尺码命名
								 }
								 String[] arr = huoHao.split(" ");
								 if(arr.length == 1)
								 {
									 type  = arr[0]; 
								 }
								 if(arr.length == 2)
								 {
									 type  = arr[0];
									 color = arr[1];
								 }
								 if(arr.length >= 3)
								 {
									 type  = arr[0];
									 material = arr[1];
									 color = arr[2];
								 }
								 
							 }
							 
							 if(null != type) type = type.trim();
							 if(null != material) material = material.trim();
							 if(null != color) color = color.trim();
							 if(null != size) size = size.trim();
							 int cnt = 0;//更新后返回条数
							 if(!"".equals(size))
							 {
								 size = size.toUpperCase();
								 //处理尺寸
								 if("2xl".equals(size) || "2XL".equals(size))  size = "XXL";
								 else if("3xl".equals(size) || "3XL".equals(size))  size = "XXXL";
								 else if("2xs".equals(size) || "2XS".equals(size))  size = "XXS";
								 else if("3xs".equals(size) || "3XS".equals(size))  size = "XXXS";
								 
								 else if("f".equals(size) || "F".equals(size))  size = "";
								 else if(size.contains("TU")) size= size.replace("TU", "");
								 else if(size.contains("UK")) size= size.replace("UK", "");
								 else if(size.contains("US")) size= size.replace("US", "");
								 else if(size.contains("IT")) size= size.replace("IT", "");
								 else if(size.contains("EU")) size= size.replace("EU", "");
								 else if(size.contains("领围")) size= size.replace("领围", "");
								//处理Moncler尺寸不对 走秀存 XS S M这些我们本地有存这些也有存0 1 2 3 这些
								 else if("Moncler".equals(brandNameZhs))
								 {
									 
									 cnt = autoSyncDao.updateZouXiuSku2Location(itemId, type, material, color, size);
									 if("XS".equals(size))  size = "0";
									 else if("S".equals(size))  size = "1";
									 else if("M".equals(size))  size = "2";
									 else if("L".equals(size))  size = "3";
									 else if("XL".equals(size))  size = "4";
								 }
							 }
							 cnt = autoSyncDao.updateZouXiuSku2Location(itemId, type, material, color, size);
							 
							 if(cnt > 0)
							 {
								 //更新成功
								 resultList.add( type + " " + material + " " + color + " " + size + "->" + itemId); 
							 }
							 
						}
					}
				}
				
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
		return resultList;
	}

	/**
	 * 更新走秀库存
	 * @param sku    走秀itemid
	 * @param realyStock  本地真实库存
	 * @param serverStock 走秀库存
	 * @param type  sh-上海  hk-香港
	 * @param huoHao 供货商货号  型号材质颜色加尺寸 记录日志所用
	 * @return
	 */
	@Override
	public Result updateZouXiuStock(String sku, int realyNum,int serverNum, String type,String huoHao) 
	{
		setToken();//设置token值
		String requestJson = "[{\"itemId\":\""+sku+"\",\"stock\":\""+realyNum+"\"}]";
		String url = UPDATE_GOODS_STOCK + AutoSyncZouXiuManagerImple.token;
		String resultStr = sendPutMethod(requestJson, url);
		Result result = new Result();
		try
		{
			JSONObject obj = new JSONObject(resultStr);
			int errorCode = obj.getInt("errorCode");
			 if(0 == errorCode)
			{
				 if(flag == true) //记录日志
				 {
					 LogData logData = new LogData();
					 logData.setFileName(logFileName);
					 logData.setBeforNum(serverNum);
					 logData.setNowNum(realyNum);
					 logData.setPlatformSku(sku);
					 logData.setHuohao(huoHao);
					 HttpRequest.recodeUpdateLog(logData);
				 }
				 result.setOK(true);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	

	/**
	 * 
	 * @param updateType sh-更新上海 hk-更新香港 默认更新香港
	 * @return
	 */
	public List<String> getZouXiuStock(String updateType)
	{
		setToken();//设置token值
		updateZouXiuSku(updateType,null);//先更新走秀sku到本地
		
		List<String> resultList = new ArrayList<String>(); //保存更新结果
		if(AutoSyncZouXiuManagerImple.token == null) return resultList;
		String selectURL = SELECT_GOODS_INFO + AutoSyncZouXiuManagerImple.token;
		int pageIndex = 0;
		int pageSize = 200;
		double maxPageIndex = 1;
		int total = 0;
		while(true)
		{
			pageIndex++;//+1不然死循环
			if(maxPageIndex < pageIndex) break; 
			String requestJson = "{\"pageSize\":\""+pageSize+"\",\"pageNo\":\""+pageIndex+"\"}";
			String resultStr = sendPutMethod(requestJson,selectURL);
			//System.out.println(resultStr);
			Map<String,Goods> zouXiuStockMap = new HashMap<String, Goods>();
			try
			{
				JSONObject goodsObj = new JSONObject(resultStr);
				int errorCode = goodsObj.getInt("errorCode");
				if(errorCode == 0)
				{
					JSONObject data = goodsObj.getJSONObject("data");
					JSONArray listArray = data.getJSONArray("list");
					if(listArray.length() == 0 ) break; //没有数据不更新
					if(listArray.length() > 0)
					{
						if(total == 0)
						{
							total = data.getInt("total");
							maxPageIndex = Math.ceil((float)total/pageSize); //设置最大页面数
						}
						for(int i = 0; i < listArray.length();i++)
						{
							 Goods g = new Goods();
							 String str = listArray.get(i).toString();
							 JSONObject good = new JSONObject(str);
							 String sku = good.getString("itemId");
							 String stockNumStr = good.getString("stock");
							 int stockNum = 0;
							 if(null != stockNumStr && !"".equals(stockNumStr) && !"null".equals(stockNumStr))
							 {
								 stockNum = Integer.valueOf(stockNumStr.trim());
							 }
							 
							 String state = good.getString("state");
							 String productName = good.getString("productId");
							 String brandNameZhs = good.getString("brandNameZhs");
							 String itemSize = good.getString("itemSize");
							 g.setTitle(productName);
							 g.setGoodsItem(sku);
							 g.setGoodsStatus("1".equals(state)?"上架":"下架");
							 g.setGoodsNumber(stockNum);
							 g.setBrandName(brandNameZhs);
							 g.setSize(itemSize);
							 zouXiuStockMap.put(sku, g);
							 //System.out.println(productName + " " + itemSize + " " + stockNum);
						}
					}
				}
				 
				
				
				//通过走秀sku获取本地库存
				//下面是获取本地库存做的处理  通过走秀sku查询本地库存,所以要先更新走秀sku到本地
				Iterator<String> it = zouXiuStockMap.keySet().iterator();
				//拼接sql
				StringBuilder sql = new StringBuilder("'-1'");//防止list没数据报错
				if(zouXiuStockMap.size() > 0) sql.append(",");
				while(it.hasNext())
				{
					String zouXiuSku = it.next();
					sql.append("'" + zouXiuSku + "'");
					if(it.hasNext())
						sql.append(",");
				}
				List<AutoSynDate>  list = autoSyncDao.getZouXiuLocationStock(sql.toString());
				Map<String,Integer> supplierSkuStockMap = new HashMap<String, Integer>(); //供货商本地库存 key-走秀SKU val-数量
				for(int i = 0;i < list.size();i++)
				{
					AutoSynDate data =list.get(i);
					int num= data.getHkNumber(); //香港库存
					supplierSkuStockMap.put(data.getSku(), num);
				}
				
				
				
				//获取本地库存结束  放入map  用作对比
				it = zouXiuStockMap.keySet().iterator();
				while(it.hasNext())
				{
						String zouxiuSku = it.next();					
						Goods g = zouXiuStockMap.get(zouxiuSku); 
						Integer supplierNumInSaler = g.getGoodsNumber(); //在走秀库存
						Integer supplierNumInLocal = supplierSkuStockMap.get(zouxiuSku); //在本地库存
						//System.out.println(g.getTitle() + " " + g.getSize() + " " + supplierNumInSaler + " " +  supplierNumInLocal);
						Result updateR = null;
						if(null == supplierNumInLocal && 0 == supplierNumInSaler) continue;//两边都没库存不做更新
						if(null == supplierNumInLocal && null == supplierNumInSaler) continue;//两边都没库存不做更新
						if(null != supplierNumInLocal && 0 == supplierNumInLocal && null != supplierNumInSaler && 0 == supplierNumInSaler) continue;//两边都没库存不做更新
						
		
						if(null == supplierNumInLocal || "".equals(supplierNumInLocal))  //本地库存为0
						{
							updateR = updateZouXiuStock(zouxiuSku, 0, supplierNumInSaler.intValue(), updateType, g.getTitle()+" " + g.getSize());
						}
						else if(supplierNumInSaler != supplierNumInLocal)  //走秀和本地库存不匹配
						{
							updateR = updateZouXiuStock(zouxiuSku, supplierNumInLocal.intValue(), supplierNumInSaler.intValue(), updateType, g.getTitle()+" " + g.getSize());
						}
						if(updateR != null)
						{
							String log = "location:" + updateType +" huohao: " + g.getTitle() +" size: " + g.getSize() + " zouxiuSku:"+ zouxiuSku + " localNum:"+supplierNumInLocal + " zouxiuNum:"+supplierNumInSaler + " status:" + g.getGoodsStatus();
							if(updateR.getMsg() != null)
								log += " error:" + updateR.getMsg();
							resultList.add(log);
						}
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	
	
	public static String sendPutMethod(String requestJson,String url)
	{
		HttpClient httpClient=new HttpClient();
		PutMethod putMethod=new PutMethod(url);
		String resultStr=""; 
		//System.out.println("url:"+url);
		//System.out.println("json:" + requestJson);
		//System.out.println();
		RequestEntity requestEntity;
		try {
			requestEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
			putMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(putMethod);
			resultStr = putMethod.getResponseBodyAsString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return resultStr;
	}
	
	
	public static String sendPostMethod(String requestJson,String url)
	{
		HttpClient httpClient=new HttpClient();
		PostMethod postMethod=new PostMethod(url);
		String resultStr=""; 
		//System.out.println("url:"+url);
		//System.out.println("json:" + requestJson);
		//System.out.println();
		RequestEntity requestEntity;
		try {
			requestEntity = new StringRequestEntity(requestJson,"application/json","UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			resultStr = postMethod.getResponseBodyAsString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return resultStr;
	}
	
	
	private void setToken()
	{
		if(null == AutoSyncZouXiuManagerImple.token)
		{
			String requestJson = "{\"username\":\""+USERNAME+"\",\"password\":\""+PASSWORD+"\"}";
			String resultJson = sendPutMethod(requestJson,GET_TOKEN_URL);
			try 
			{
				JSONObject obj = new JSONObject(resultJson);
				if(obj.getInt("errorCode") == 0)
				{
					AutoSyncZouXiuManagerImple.token = obj.getString("data");
				}
				
			}
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
	}
	

}
