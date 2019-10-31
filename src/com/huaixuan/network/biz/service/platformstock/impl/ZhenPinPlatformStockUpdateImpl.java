package com.huaixuan.network.biz.service.platformstock.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.platformstock.ZhenPinPlatformStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;
import com.zp.open.api.pub.constant.IParaNameConstant;
import com.zp.open.api.pub.exception.ZPException;
import com.zp.open.api.pub.response.DefaultResponse;
import com.zp.open.api.pub.util.JsonUtil;
import com.zp.open.api.sdk.client.DefaultZPClient;
import com.zp.open.api.sdk.request.ProductStore;
import com.zp.open.api.sdk.request.QryProductRequest;
import com.zp.open.api.sdk.request.SendOrderRequest;
import com.zp.open.api.sdk.request.TokenRequest;
import com.zp.open.api.sdk.request.UpdateProdStoreRequest;



/**
 * @author Mr_Yang   2016-5-11 上午11:26:09
 **/

@Service
public class ZhenPinPlatformStockUpdateImpl implements ZhenPinPlatformStockUpdate
{

	private static String zhenPin_serverUrl = "http://zop.zhen.com/zpOpenPlatform";
	private static String zhenPin_accessToken_sh = null;
	private static String zhenPin_provider_sh = "243";
	private static String zhenPin_providerPwd_sh = "shangshangsp";
	
	private static String zhenPin_accessToken_hk = null;
	private static String zhenPin_provider_hk = "263";
	private static String zhenPin_providerPwd_hk = "shangshangsp";
	


    protected Log log = LogFactory.getLog(this.getClass());
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private StockUpdateService stockService;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	
	public ZhenPinPlatformStockUpdateImpl()
	{
		//初始化 就设置token
		setZhenPinToken();
	}
	//先珍品token
	private  void setZhenPinToken() 
	{
		TokenRequest request = new TokenRequest(); 
		request.setAppKey(zhenPin_provider_sh);
		request.setProvierSecret(zhenPin_providerPwd_sh);
		try 
		{
			DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl, null,zhenPin_provider_sh);
			DefaultResponse zpResponse = client.execute(request);
			HashMap<String, Object> map = JsonUtil.json2Map(zpResponse.getResult());
			zhenPin_accessToken_sh = map.get(IParaNameConstant.REQUEST_TOKEN_NAME).toString();
		} 
		catch (ZPException e) 
		{
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		
		
		request = new TokenRequest();
		request.setAppKey(zhenPin_provider_hk);
		request.setProvierSecret(zhenPin_providerPwd_hk);
		try 
		{
			DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl, null,zhenPin_provider_hk);
			DefaultResponse zpResponse = client.execute(request);
			HashMap<String, Object> map = JsonUtil.json2Map(zpResponse.getResult());
			zhenPin_accessToken_hk = map.get(IParaNameConstant.REQUEST_TOKEN_NAME).toString();
		} 
		catch (ZPException e) 
		{
			log.error(e.getMessage(), e);
		}

	}
	public void updateAllStock()
	{
		updateAllStockByType("hk");
		
		
		updateAllStockByType("sh");
	}
	
	
	public void updateAllStockByType(String type)
	{
		String serverUrl = zhenPin_serverUrl;
		String accessToken = zhenPin_accessToken_sh;
		String provider = zhenPin_provider_sh;
		
		if("hk".equals(type))
		{
			accessToken = zhenPin_accessToken_hk;
			provider = zhenPin_provider_hk;
			 
		}
		

		//查询当前可售库存
		Map<String,StockUpdate> canSaleStockMap = new HashMap<String, StockUpdate>(); //key为我们sku  value为当前sku可售库存
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("searchCanSale", "yes");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate su : list)
		{
			canSaleStockMap.put(su.getSku(), su);
		}
		
		DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken, provider);
		int pageNo = 1;
		int pageSize = 100;
		
		while(true)
		{
			QryProductRequest request = new QryProductRequest();
			request.setPage(pageNo+"");
			request.setPageSize(pageSize+"");
			request.setSortType("1");
			request.setStatus("0,1");//可售和下架产品
			pageNo++;
			try 
			{
				DefaultResponse zpResponse = client.execute(request);
				JSONObject jsonObj = new JSONObject(zpResponse.getResult());
				int totalCnt = jsonObj.getInt("totalCount");
				if(totalCnt == 0) break;
				JSONArray productInfoList = new JSONArray(jsonObj.getString("productInfo"));
				for(int index = 0; index < productInfoList.length(); index++)
				{
					JSONObject product = new JSONObject(productInfoList.getString(index));
					String outSku = product.getString("outSku");
					if(StringUtil.isBlank(outSku)) continue; //没有设置sku 不更新
					//String productSpecId = product.getString("productSpecId");
					String productSpecId = product.getString("sku");
					StockUpdate su = canSaleStockMap.get(outSku);
					int nowNum = 0;
					if(su != null) nowNum = su.getNowStockNum()-su.getOrderStockNum();
					if(nowNum < 0) nowNum = 0;//库存数不能小于0
			 
					
					//更新库存
					boolean flag = updateZhenpinStock(productSpecId, outSku, nowNum, type);
					//更新成功 设置上次更新到平台库存数,防止单个更新的在做更新
					if(flag && StringUtil.isNotBlank(outSku))
					{
						StockUpdate stockUpdate = new StockUpdate();
						stockUpdate.setType(type);
						stockUpdate.setSku(outSku);
						stockUpdate.setLastUpdateStockNum(nowNum);
						if(su != null) 
						{
							stockUpdate.setLastUpdateStockNum(nowNum-su.getOrderStockNum());
						}
						//platformStockUpdateDao.updateLastUpdateStockBySkuAndTypeForOne(stockUpdate); 暂时不做更新 防止其他平台更新不到
					}
					
				}

				int totalPage = (totalCnt/pageSize)+1;
				
				if(pageNo > totalPage || pageNo > 500) break; //没有数据 跳出
			} 
			catch (Exception e) 
			{
				log.error(e.getMessage(), e);
				if(pageNo > 500) break; //页面太多 跳出
			}
		}
		
		System.out.println(sdf.format(new Date()) + " zhenpin " + type);
	}
	
	@Override
	public void updateSku2Location()
	{
		
		//stockService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
		
		//platformStockUpdateDao.updateStockUpdateSku2Null("zhenpin_sku");//更新平台sku为NULL 
		
		//先更新下架 在更新上架 0-下架  1-上架
		platformStockUpdateDao.updateStockUpdateSku2Null("zhenpin_on_sale_status");
		updateSku2LocationByTypeAndStatus("hk","0");
		updateSku2LocationByTypeAndStatus("hk","1");
		
		
		updateSku2LocationByTypeAndStatus("sh","0");
		updateSku2LocationByTypeAndStatus("sh","1");
		
		//stockService.setCanUpdateStockStatus("true"); //解除-暂时不让其他地方做更新
	}
	
	private void updateSku2LocationByTypeAndStatus(String type,String status)
	{
		String serverUrl = zhenPin_serverUrl;
		String accessToken = zhenPin_accessToken_sh;
		String provider = zhenPin_provider_sh;
		if("hk".equals(type))
		{
			accessToken = zhenPin_accessToken_hk;
			provider = zhenPin_provider_hk;
		}
	 
		DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken, provider);
		
		int pageNo = 1;
		int pageSize = 100;
		int errorCnt = 1;
		while(true)
		{
			QryProductRequest request = new QryProductRequest();
			request.setPage(pageNo+"");
			request.setPageSize(pageSize+"");
			request.setSortType("1");
			request.setStatus(status);//可售或者下架产品
			pageNo++;
			try 
			{
				List<PlatformSku2LocationSku> zehnPinSkuList = new ArrayList<PlatformSku2LocationSku>();
				DefaultResponse zpResponse = client.execute(request);
				JSONObject jsonObj = new JSONObject(zpResponse.getResult());
//				System.out.println(jsonObj);
				int totalCnt = jsonObj.getInt("totalCount");
				if(totalCnt == 0) break;
				JSONArray productInfoList = new JSONArray(jsonObj.getString("productInfo"));

				for(int index = 0; index < productInfoList.length(); index++)
				{
					JSONObject product = new JSONObject(productInfoList.getString(index));
					String ourSku = product.getString("outSku");
					String productSpecId = product.getString("productSpecId");
					String zhenpinSku = product.getString("sku");
					String statuss = product.getString("status");
					if(StringUtil.isBlank(ourSku) || StringUtil.isBlank(zhenpinSku)) continue; //没有sku 不更新
					
					if(statuss != null){
						statuss = statuss.trim();
						if(!statuss.equals("1")){
							statuss="0";   //在我們數據庫1是上架，0是下架
						}
					}
					//同步sku到本地,单个更新使用, 批量更新
					PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
					pslSku.setOurSku(ourSku);
					pslSku.setPlatformField("zhenpin_sku");
					pslSku.setPlatformSku(zhenpinSku);
					pslSku.setPlatformstatus("zhenpin_on_sale_status");
					pslSku.setStatus(statuss);
					pslSku.setType(type); 
					zehnPinSkuList.add(pslSku);


				}
				Map zhenpinSkuMap = new HashMap();
				zhenpinSkuMap.put("list", zehnPinSkuList);
				zhenpinSkuMap.put("platformField", "zhenpin_sku");
				zhenpinSkuMap.put("platformstatus", "zhenpin_on_sale_status");
	
				if(zehnPinSkuList.size() > 0)
				{
					//更新珍品sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(zhenpinSkuMap);
				}

				int totalPage = (totalCnt/pageSize)+1;
				
				if(pageNo > totalPage) break; //没有数据 跳出
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("zp syn sku error " + pageNo);
				if(errorCnt++ > 5) break; //错误5次 跳出
			}
		}
		
		
	
	}
	@Override
	public boolean updateZhenpinStock(String productSpecID, String ourSku,int num, String type)
	{
		if(StringUtil.isBlank(productSpecID) && StringUtil.isBlank(ourSku)) return true;
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("zhenpinSku", productSpecID);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(productSpecID))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "zhenpin_sku");
			productSpecID = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(productSpecID)) return true;
		UpdateProdStoreRequest request = new UpdateProdStoreRequest();
		List<ProductStore> productStoreList = new ArrayList<ProductStore>();
		ProductStore prodStore = new ProductStore();
		prodStore.setFreeStock(num+"");
		//prodStore.setProductSpecID(productSpecID); 通过sku来更新  不用productSpecID
		prodStore.setSku(productSpecID);
		productStoreList.add(prodStore);
		request.setProductStoreList(productStoreList);
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", productSpecID);
		logMap.put("name", "zhenpin");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		boolean flag = true;
		String accessToken = zhenPin_accessToken_sh;
		String provider = zhenPin_provider_sh;
		if("hk".equals(type))
		{
			accessToken = zhenPin_accessToken_hk;
			provider = zhenPin_provider_hk;
		}
		try
		{
			DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl, accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			JSONArray jsonaArray = new JSONArray(zpResponse.getResult());
			Map<String,Object> map = JsonUtil.json2Map(jsonaArray.get(0).toString());
			if(map.get("code").toString().equals("200"))
			{
				flag = true;
			}
			else
			{
				 logMap.put("type", "error");
				 logMap.put("error",map.get("codeMsg").toString());
				 
			}
			
		} 
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			//e.printStackTrace();
			//flag = true;
			setZhenPinToken();
		}
		autoSyncDao.addUpdateLog(logMap);
		return flag;
	}

	
	
	@Override
	public  int atuoSyncOrder(String startTime,String endTime,String type)
	{
		String serverUrl = zhenPin_serverUrl;
		String accessToken = zhenPin_accessToken_sh;
		String provider = zhenPin_provider_sh;
		if("hk".equals(type))
		{
			accessToken = zhenPin_accessToken_hk;
			provider = zhenPin_provider_hk;	
		}
		
		SendOrderRequest request = new SendOrderRequest();
		request.setStartDate(startTime);
		request.setEndDate(endTime);		
		request.setSendOrderState("1");
		request.setPage("1");
		request.setPageSize("100");		
		Map<String,String> searchMap = new HashMap<String, String>();
		int updateNumber = 0;
		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			JSONObject jsonObj = new JSONObject(zpResponse.getResult());
//			System.out.println(jsonObj);
			JSONArray orderObjList =jsonObj.getJSONArray("sendOrderInfos");
			String totalCount = jsonObj.getString("totalCount");//订单数量
			for(int index = 0; index < orderObjList.length(); index++)
			{
				JSONObject jsonObje = new JSONObject(orderObjList.get(index).toString());
				String sendOrderSn = jsonObje.getString("sendOrderSn");//订单号
//				String cTime = jsonObje.getString("createTime");
				Long cTime = jsonObje.getLong("createTime");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = format.format(cTime  * 1000L);//下单时间
				String consignee = jsonObje.getString("consignee");//收件人姓名
				String consigneeMobile = jsonObje.getString("consigneeMobile");//收件人手机
				String address = jsonObje.getString("address");//收货地址
				String zipcode = jsonObje.getString("zipcode");//邮编
				String totalAmount = jsonObje.getString("totalAmount");//订单总额
				String shippingPrice = jsonObje.getString("shippingPrice");//邮费
				JSONArray skuList = new JSONObject(orderObjList.get(index).toString()).getJSONArray("subSendOrderList");
				for(int i = 0; i < skuList.length(); i++)
				{
					JSONObject skuObj = new JSONObject(skuList.get(i).toString());
					String productSpecId = skuObj.getString("sku");
					int num = skuObj.getInt("quantity"); //数量
					String productName = skuObj.getString("productName");//商品名称
					String specValue= skuObj.getString("specValue");//商品规格
					String money = skuObj.getString("money");//商品售价
					searchMap.put("type", type);
					searchMap.put("zhenpinSku", productSpecId);
					List<StockUpdate>  list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
					for(StockUpdate su : list)
					{
						su.setZhenpinOrderStock(num + su.getZhenpinOrderStock());
						su.setLastOrderTime("yes");
						platformStockUpdateDao.updateStockByNotNull(su);//更新订单数
						updateNumber++;
						PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
						platformorderdetails.setIdorder(sendOrderSn);
						List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);
							platformorderdetails.setIdorder(sendOrderSn);
							platformorderdetails.setPalcedTime(createTime);
							platformorderdetails.setName(consignee);
							platformorderdetails.setMobile(consigneeMobile);
							platformorderdetails.setStreetAddress(address);
							platformorderdetails.setZipCode(zipcode);
							platformorderdetails.setTotalPrice(totalAmount);
							platformorderdetails.setFreight(shippingPrice);
							platformorderdetails.setSkuId(productSpecId);
							platformorderdetails.setQuantity(num+"");
							platformorderdetails.setProductname(productName);
							platformorderdetails.setSize(specValue);
							platformorderdetails.setPrice(money);
							platformorderdetails.setPtype("zp");
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
				
			}
			
			
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			setZhenPinToken();
			
		}
		return updateNumber;
	}
	
	public static void main(String[] args)
	{
		ZhenPinPlatformStockUpdateImpl zp = new ZhenPinPlatformStockUpdateImpl();
		zp.atuoSyncOrder("20160522222401", "20160522222402", "hk");
	}

}
 
