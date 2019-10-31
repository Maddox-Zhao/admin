package com.huaixuan.network.biz.service.autosyn.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.AutoSynDate;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.service.autosyn.AutoSyncWangDianTongManager;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.common.util.Md5Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;



/**
 * @author Mr_Yang   2015-10-12 下午02:57:03 弃用
 **/

 
public class AutoSyncWangDianTongManagerImpl implements AutoSyncWangDianTongManager
{
	//由旺店通提供
	private static final String 	sellerId = "oupai"; 
	private static final String		interfaceID = "oupai-gw";
	private static final String 	key = "b203ec7f8638993361486fab59ab61f4";
	private static final String 	url = "http://api.wangdian.cn/stockapi/interface.php";
	private static final String warehouseNO = "10025";
	
	
	private static final String logFileName = "wangdiantong"; //记录更新日志名称 zouxiu   shangping  ..等等 用于区别某个平台
	private static final boolean flag = true;//是否记录更新日志
	
	@Autowired
	private  AutoSyncDao autoSyncDao;

	/**
	 * 更新旺店通的SKU到本地来
	 */
	@Override
	public void updateWDTSku2Loacation()
	{
		long startTime = System.currentTimeMillis();
		int pageNO = 0;	
		int pageSize = 50;
		int totalNum = 0;
		int maxPageNo = 999999;//最大页数

		//全量更新 清空旺店通sku为null 在重新更新下来
		autoSyncDao.clearPlatformSku2Null("sku_wangdiantong_sh");
		
		while(true)
		{
			pageNO++;
			if(maxPageNo < pageNO) break; //当前页面大于最大页 说明到最后一页了 跳出
			
			//组装需要查询的数据  详情见文档
			String 	json = "{\"PageNO\":\""+pageNO+"\",\"PageSize\":\""+pageSize+"\"}";
			//String 	json = "{\"PageNO\":\"1\",\"PageSize\":\"50\",\"GoodsNO\":\"XXW0VN0N260SHAU807\"}";
			String 	context = json+key;
			String md5 = Md5Util.makeMd5Sum(context.getBytes());
			String base64 = Base64.encode(md5.getBytes());
			String sign = "";
			try
			{
				sign = URLEncoder.encode(base64,"UTF-8");
				LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();//放进去和取数来顺序一致
				map.put("Method", "QueryGoodsInfo");
				map.put("SellerID", sellerId);
				map.put("InterfaceID", interfaceID);
				map.put("Sign", sign);
				map.put("Content", json);
				String response = HttpRequest.sendPost(url, map);
				response = convert(response);//转Unicode编码
				//System.out.println(response);
				JSONObject goodsObj = new JSONObject(response);
				
				//第一次循坏处理最大页
				totalNum = goodsObj.getInt("TotalCount");
				if(maxPageNo == 999999) 
				{
					double t = (double)(totalNum/pageSize);
					maxPageNo = (int)Math.floor(t)+1;
					//maxPageNo = 5;
				}
				//System.out.println("pageNO:" + pageNO);
				//获取成功,处理json数据 对应的处理查看返回的JSON数据是如何嵌套的
				if(goodsObj.getInt("ResultCode") == 0)
				{
					String goodsObjStr =  goodsObj.getString("GoodsList");
					JSONObject goodsList = new JSONObject(goodsObjStr);
					JSONArray goods = goodsList.getJSONArray("Goods");
					List<Goods> list = new ArrayList<Goods>();
					
					for(int i = 0; i < goods.length(); i++)
					{
						JSONObject good = new JSONObject(goods.getString(i));
						String bBlockUp = good.getString("bBlockUp");
						if("1".equals(bBlockUp)) continue; //已删除数据
						
						String huoHao  = good.getString("GoodsNO");
						if(null != huoHao) huoHao = huoHao.trim();
						String brandName  = good.getString("Brand");

						JSONObject skuList = new JSONObject(good.getString("SkuList"));
						JSONArray skus = skuList.getJSONArray("Sku");
						for(int j = 0; j < skus.length(); j++)
						{
							JSONObject sku = new JSONObject(skus.getString(j));
							String size = sku.getString("SpecName");
							String skuCode = sku.getString("SkuCode");
							String skubBlockUp = sku.getString("bBlockUp");
							if(null != size) 
							{
								size = size.trim();
								size = size.replace("码", "");//去掉size里面的码字
							}
							//skus.length()>1代表有尺码   而尺码为空说明尺码有问题 不做更新
							if(skus.length() > 1 && "".equals(size))
							{
								continue;
							}
							//尺码包含中文 或者sku不存在  或者已删除的数据 不做处理 
							if("".equals(skuCode) || isContainsChinese(size) || "1".equals(skubBlockUp))
							{
								continue;
							}
							Goods g = new Goods();
							String type = "";
							String material = "";
							String color =  "";
							if(null != huoHao)
							{
								String[] huoHaoArr = huoHao.split(" ");
								if(huoHaoArr.length == 1)
								{
									type = huoHaoArr[0];
									type = type.trim();
								}
								else if(huoHaoArr.length == 2)
								{
									type = huoHaoArr[0];
									type = type.trim();
									
									color = huoHaoArr[1];
									color = color.trim();
								}
								else if(huoHaoArr.length >= 3)
								{
									type = huoHaoArr[0];
									type = type.trim();
									
									material = huoHaoArr[1];
									material = material.trim();
									
									color = huoHaoArr[2];
									color = color.trim();
								}
								if(!"".equals(type)) //至少有型号才能做更新
								{
									
									if(null != size)
									{
										size = size.replace("'", "");
									}
									if(null != type)
									{
										type = type.replace("'", "");
									}
									if(null != material)
									{
										material = material.replace("'", "");
									}
									if(null != color)
									{
										color = color.replace("'", "");
									}
									g.setType(type);
									g.setMaterial(material);
									g.setColor(color);
									g.setSize(size);
									g.setTaobaoSkuProp(skuCode);
									
									list.add(g);
									
									//处理TOD'S不正确的货号 eg XXW0VN0N260SHAU807 分割为XXW0VN0N260SHA U807
									if(null != brandName)
									{
										if(brandName.indexOf("TOD'S") != -1)
										{
												Goods tg = new Goods();
												type = huoHao.substring(0,huoHao.length()-4);
												color = huoHao.substring(huoHao.length()-4);
												tg.setType(type);
												tg.setMaterial("");
												tg.setColor(color);
												tg.setSize(size);
												tg.setTaobaoSkuProp(skuCode);
												list.add(tg);
										}
									}
								}
							}
						}
						
					}
					if(list.size() > 0)
						autoSyncDao.updateWangDianTongSku2Location(list);

				}	
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		long endTime = System.currentTimeMillis(); 
		//System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
	}
	
	public String convert(String utfString){
	    StringBuilder sb = new StringBuilder();
	    int i = -1;
	    int pos = 0;
	     
	    while((i=utfString.indexOf("\\u", pos)) != -1){
	        sb.append(utfString.substring(pos, i));
	        if(i+5 < utfString.length()){
	            pos = i+6;
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
	        }
	    }
	    sb.append(utfString.substring(pos));
	    return sb.toString();
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
	

	@Override
	public AutoSynDate getLocationStockBySku(String sku)
	{
		return  autoSyncDao.getWangDianTongStockBySku(sku);
	}

	//查询该sku的库存
	public int getWangDianTongStockBySku(String sku)
	{
		//组装需要查询的数据  详情见文档
		String 	json = "{\"WarehouseNO\":\""+warehouseNO+"\",\"Sku_Code\":\""+sku+"\"}";
		String 	context = json+key;
		String md5 = Md5Util.makeMd5Sum(context.getBytes());
		String base64 = Base64.encode(md5.getBytes());
		String sign = "";
		try
		{
			sign = URLEncoder.encode(base64,"UTF-8");
			LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();//放进去和取数来顺序一致
			map.put("Method", "QueryStorage");
			map.put("SellerID", sellerId);
			map.put("InterfaceID", interfaceID);
			map.put("Sign", sign);
			map.put("Content", json);
			String response = HttpRequest.sendPost(url, map);
			response = convert(response);//转Unicode编码
			JSONObject goodsObj = new JSONObject(response);
			if(goodsObj.getInt("ResultCode") == 0)
			{
				if(goodsObj.getInt("TotalCount") > 0)
				{
					JSONObject itemList = new JSONObject(goodsObj.getString("ItemList"));
					JSONArray items = itemList.getJSONArray("Item");
					String skuObjStr = items.getString(0);
					JSONObject skuObj = new JSONObject(skuObjStr);
					double qty = skuObj.getDouble("Qty");
					return (int)qty;
				}
			}
			else
			{
				return -1;//没查询到该sku
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	//更新该sku的库存
	private boolean updateWangDianTongStockBySku(String sku,int num)
	{
		
		//组装需要查询的数据  详情见文档
		String 	json = "{\"WarehouseNO\":\""+warehouseNO+"\",\"Sku_Code\":\""+sku+"\",\"Outer_Code\":\"\",\"Qty\":\""+num+"\"}";
		String 	context = json+key;
		String md5 = Md5Util.makeMd5Sum(context.getBytes());
		String base64 = Base64.encode(md5.getBytes());
		String sign = "";
		try
		{
			sign = URLEncoder.encode(base64,"UTF-8");
			LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();//放进去和取数来顺序一致
			map.put("Method", "SyncStorage");
			map.put("SellerID", sellerId);
			map.put("InterfaceID", interfaceID);
			map.put("Sign", sign);
			map.put("Content", json);
			String response = HttpRequest.sendPost(url, map);
			response = convert(response);//转Unicode编码
			JSONObject goodsObj = new JSONObject(response);
			if(goodsObj.getInt("ResultCode") == 0)
			{
				return true;
			}
			return false;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateWangDianTongStock()
	{
		List<String> wdtSkus = autoSyncDao.getWangDianTongSkus();
		for(String sku:wdtSkus)
		{
			int num = 0;
			AutoSynDate obj = getLocationStockBySku(sku);
			if(obj != null)
			{
				num = obj.getShNumber();
			}
			int wdtNum = getWangDianTongStockBySku(sku);
			
			//该sku在该仓库不存在
			if(wdtNum == -1)
			{
				continue;
			}
			if(num != wdtNum) //两边库存不一致 //更新库存
			{
				boolean isUpdate = updateWangDianTongStockBySku(sku, num);
				//boolean isUpdate=true;
				if(flag == true && isUpdate) //记录日志
				 {
					 String huoHao = autoSyncDao.getHuoHaoByWangDianTongSku(sku);
					 LogData logData = new LogData();
					 logData.setFileName(logFileName);
					 logData.setBeforNum(wdtNum);
					 logData.setNowNum(num);
					 logData.setPlatformSku(sku);
					 logData.setHuohao(huoHao);
					 HttpRequest.recodeUpdateLog(logData);
				 }
			}
			
			
		}
		
		
	}
	
	
	
	public static void main(String[] args)
	{
		new AutoSyncWangDianTongManagerImpl().updateWDTSku2Loacation();
	}


	
}
 
