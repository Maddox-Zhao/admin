package com.huaixuan.network.biz.service.platformstock.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.platformstock.YhdPlatformStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;
import com.yhd.YhdClient;
import com.yhd.request.product.GeneralProductsSearchRequest;
import com.yhd.request.product.ProductStockUpdateRequest;
import com.yhd.request.product.SerialProductGetRequest;
import com.yhd.request.product.SerialProductsSearchRequest;
import com.yhd.response.product.GeneralProductsSearchResponse;
import com.yhd.response.product.ProductStockUpdateResponse;
import com.yhd.response.product.SerialProductGetResponse;
import com.yhd.response.product.SerialProductsSearchResponse;



/**
 * @author Mr_Yang   2016-5-19 下午04:40:26
 * 1号点 同步库存
 **/

@Service("yhdPlatformStockUpdate")
public class YhdPlatformStockUpdateImpl implements YhdPlatformStockUpdate
{
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private StockUpdateService stockService;
	
	private static  final String SH_APP_SECRET = "9a6b34199257129b0fa54939bd239683";
	
	private static final String SH_APP_KEY = "10220016050900003995";
	
	private static final String SH_SESSION_KEY = "19e1e97b3b3bb09b3cc55bd8491233ff";
	
	private static final String URL = "http://openapi.yhd.com/app/api/rest/router?";
	
	
	
	
	@Override
	public boolean updateYhdStocku(String platformSku,String ourSku,int num,String type)
	{
		if(StringUtil.isBlank(platformSku) && StringUtil.isBlank(ourSku)) return true;
		boolean  flag = true; //暂时只记录更新成功
		if("sh".equals(type)) //1号店暂时只有国内
		{
			String psku = ourSku;
			YhdClient yhd = new YhdClient(URL,SH_APP_KEY,SH_APP_SECRET);
			ProductStockUpdateRequest request = new ProductStockUpdateRequest();
			request.setVirtualStockNum(num);
			if(StringUtil.isNotBlank(platformSku))
			{
				psku = platformSku;
				request.setProductId (Long.valueOf(platformSku));
			}
			else
			{
				request.setOuterId(ourSku);
			}
			ProductStockUpdateResponse response = yhd.excute(request, SH_SESSION_KEY);
			//记录更新日志
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", psku);
			logMap.put("name", "yhd");
			logMap.put("stock", num+"");
			logMap.put("location", type);
			if(response.getErrorCount() == 0)
			{		
				logMap.put("type", "success");
			}
			else
			{
				logMap.put("type", "error");
				logMap.put("erro", response.getBody());	
			}
			//记录更新日志
			autoSyncDao.addUpdateLog(logMap);
		}
		return flag;
	}
	

	@Override
	public void updateSku2Location()
	{
		//stockService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
		
		//platformStockUpdateDao.updateStockUpdateSku2Null("yhd_sku");//更新平台sku为NULL 
		
		updateGeneralSku2Location();
		
		updateSerialSku2Location();
		
		//stockService.setCanUpdateStockStatus("true"); //解除-暂时不让其他地方做更新
		
	}
	
	//更新普通产品sku到本地
	private void updateGeneralSku2Location()
	{
		int pageNo = 1;
		int pageSize = 100;
		int errorCnt = 1;
		while(true)
		{
			YhdClient yhd = new YhdClient(URL,SH_APP_KEY,SH_APP_SECRET);
			GeneralProductsSearchRequest request = new GeneralProductsSearchRequest();
			request.setPageRows (pageSize);
			request.setCurPage(pageNo);
			request.setCanSale(1);//可售
			GeneralProductsSearchResponse response = yhd.excute(request, SH_SESSION_KEY);
			String responseResult = response.getBody();
			pageNo++;
			try
			{
				List<PlatformSku2LocationSku> yhdSkuList = new ArrayList<PlatformSku2LocationSku>();
				JSONObject resultJsonObj = new JSONObject(responseResult);
				String resoponse  = resultJsonObj.getString("response");
				JSONObject obj = new JSONObject(resoponse);
				int totalCount = obj.getInt("totalCount");
				JSONArray productList = new JSONObject(obj.getString("productList")).getJSONArray("product");
				for(int index = 0; index < productList.length(); index++)
				{
					 JSONObject product = new JSONObject(productList.getString(index));
					 String ourSku = "";
					 String yhdSku = "";
					 try
					 {
						  ourSku = product.getString("outerId");
						  yhdSku = product.getString("productId");
					 }
					 catch (JSONException e)
					 {
						  continue;
					 }
					 if(StringUtil.isBlank(ourSku) || StringUtil.isBlank(yhdSku)) continue; //没有sku 不更新
					 //有些供货商sku填的是货号-sku  有些是直接填sku
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
					 ourSku = ourSku.trim();
					 //同步sku到本地,单个更新使用, 批量更新
				 	 PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
					 pslSku.setOurSku(ourSku);
					 pslSku.setPlatformField("yhd_sku");
					 pslSku.setPlatformSku(yhdSku);
					 pslSku.setType("sh"); 
					 yhdSkuList.add(pslSku);
				}
				
				
				if(yhdSkuList.size() > 0)
				{
					Map yhdSkuMap = new HashMap();
					yhdSkuMap.put("list", yhdSkuList);
					yhdSkuMap.put("platformField", "yhd_sku");
					//更新一号店sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(yhdSkuMap);
				}
				
				int totalPage = (totalCount/pageSize)+1;
				
				if(pageNo > totalPage) break; //没有数据 跳出
				
			}
			catch (JSONException e)
			{
				e.printStackTrace();
				System.out.println("yhd syn sku error " + pageNo);
				if(errorCnt++ > 5) break; //错误5次跳出
			}
		
		}
	}

	
	//更新系列产品sku到本地(有尺寸的 在一号店叫系列产品)
	private void updateSerialSku2Location()
	{
		int pageNo = 1;
		int pageSize = 100;
		while(true)
		{
			
			YhdClient yhd = new YhdClient(URL,SH_APP_KEY,SH_APP_SECRET);
			SerialProductsSearchRequest request = new SerialProductsSearchRequest();
			request.setPageRows (pageSize);
			request.setCurPage(pageNo);
			request.setCanSale(1);//可售
			SerialProductsSearchResponse  response = yhd.excute(request, SH_SESSION_KEY);
			String responseResult = response.getBody();
			pageNo++;
			try
			{
				List<PlatformSku2LocationSku> yhdSkuList = new ArrayList<PlatformSku2LocationSku>();
				JSONObject resultJsonObj = new JSONObject(responseResult);
				String resoponse  = resultJsonObj.getString("response");
				JSONObject obj = new JSONObject(resoponse);
				int totalCount = obj.getInt("totalCount");
				JSONArray productList = new JSONObject(obj.getString("serialProductList")).getJSONArray("serialProduct");
				for(int index = 0; index < productList.length(); index++)
				{
					 JSONObject product = new JSONObject(productList.getString(index));
					 try
					{
						 String shangjiaBianMa = product.getString("outerId"); //系列产品的编码 通过系列产品编码查询子产品
						 updateSerialProductSku(shangjiaBianMa,yhdSkuList);
					}	
					catch (Exception e)
					{
						//防止outerId 不存在报错 有些sku更新不到
					}
					 
				}
				
				if(yhdSkuList.size() > 0)
				{
					Map yhdSkuMap = new HashMap();
					yhdSkuMap.put("list", yhdSkuList);
					yhdSkuMap.put("platformField", "yhd_sku");
					//更新一号店sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(yhdSkuMap);
				}
				
				int totalPage = (totalCount/pageSize)+1;
				
				if(pageNo > totalPage) break; //没有数据 跳出
				
			}
			catch (JSONException e)
			{
				e.printStackTrace();
				System.out.println("yhd syn sku error " + pageNo);
				if(pageNo > 500) break;
			}
		}
	}
	
	private void updateSerialProductSku(String shangjiaBianMa,List<PlatformSku2LocationSku> yhdSkuList)
	{
		YhdClient yhd = new YhdClient(URL,SH_APP_KEY,SH_APP_SECRET);
		SerialProductGetRequest request = new SerialProductGetRequest();
		request.setOuterId (shangjiaBianMa);
		SerialProductGetResponse response = yhd.excute(request, SH_SESSION_KEY);
		try
		{
			JSONObject resultJsonObj = new JSONObject( response.getBody());
			JSONObject obj = new JSONObject(new JSONObject(resultJsonObj.getString("response")).getString("serialChildProdList"));
			JSONArray productList = obj.getJSONArray("serialChildProd");
			for(int i = 0; i < productList.length(); i++)
			{
				 JSONObject product = new JSONObject(productList.get(i).toString());
				 String yhdSku = "";
				 String ourSku = "";
				 try
				 {
					 yhdSku = product.getString("productId");
					 ourSku = product.getString("outerId");
				 }
				 catch (Exception e) {
					continue;
				}
				 if(StringUtil.isBlank(ourSku) || StringUtil.isBlank(yhdSku)) continue; //没有sku 不更新
				 //有些供货商sku填的是货号-sku  有些是直接填sku
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
				 ourSku = ourSku.trim();
				 //同步sku到本地,单个更新使用, 批量更新
			 	 PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
				 pslSku.setOurSku(ourSku);
				 pslSku.setPlatformField("yhd_sku");
				 pslSku.setPlatformSku(yhdSku);
				 pslSku.setType("sh"); 
				 yhdSkuList.add(pslSku);
			}
			
		}
		catch (JSONException e)
		{
			 
		}

	}

	@Override
	public void updateAllStock()
	{
		//1号店暂时只有国内
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", "sh");
		searchMap.put("skuisnotnull", "yhd_sku");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate s : list)
		{
			int num = s.getNowStockNum() - s.getOrderStockNum();
			if(num < 0) num = 0;
			updateYhdStocku(s.getYhdSku(), s.getSku(), num, s.getType());
			//更新一次休息5秒
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		YhdPlatformStockUpdateImpl yhd = new YhdPlatformStockUpdateImpl();
		yhd.updateYhdStocku("55719143", "6663018100236", 22, "sh");
	}
}
 
