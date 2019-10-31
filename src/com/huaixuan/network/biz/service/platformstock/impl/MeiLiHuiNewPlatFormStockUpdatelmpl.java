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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.MeiLiHuiNewPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.MeiLiHuiPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.PlatFormOrderRecordService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;

@Service("MeiLiHuinewPlatFormStockUpdate")
public class MeiLiHuiNewPlatFormStockUpdatelmpl  implements MeiLiHuiNewPlatFormStockUpdate{
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	//CB00054	
	private static final String vendorCodehknew = "100000000059745";//海外正式环境,5000以上的
	private static final String vendorCodehknews = "690000000060758";//海外正式环境,5000以下的
	private static final String vendorCodesh = "V12594";//国内正式环境
	
	private static final String secrethknew = "YAZHF";//海外正式环境 5000以上的
	private static final String secrethknews = "INXLZ";//海外正式环境 5000以下的
	private static final String secretsh = "TMUOX";//国内正式环境
	
	private static final String signMethod = "MD5";
	private static final String format = "json";
	private static final String v = "1.0";
	
	private static final String api_url = "http://116.62.245.214/inventory/update";//更新库存接口(正式环境)
	private static final String url = "http://116.62.245.214/sku/list";//获取sku接口(正式环境)
	
//	private static final String api_url = "http://121.196.210.172/inventory/update";//测试环境更新库存接口
//	private static final String url ="http://121.196.210.172/sku/list";//测试环境获取SKU接口
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	
	private String getsign(Map<String, String> map,String type){
		String sign = "";
		Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String,String> en = it.next();
			sign += en.getKey() + en.getValue();
		}
//		sign = secret + sign + secret;
		if("hk".equals(type)){
			sign = secrethknew + sign + secrethknew;
		}
		else{
			sign = secrethknews + sign + secrethknews;
		}
		
		sign  = HttpRequest.string2MD5(sign).toUpperCase();
		return sign;
	}
	
	/**
	 * 更新魅力惠SKU到本地
	 * 魅力惠商品接口未开放，使用EXCEL手动更新已上架SKU，自动更新SKU弃用，开放后再进行开发。
	 */
    @Override
	public void updateSku2Location() {
    	////5000以上的sku更新
		updateSku2LocationByType("hk");
		//5000以下的sku更新
		updateSku2LocationByTypeSec("hks");
	}
   /**
	 * @param string
	 */
    //5000以下的库存更新
	private void updateSku2LocationByTypeSec(String str) {
		String type ="";
		if(str.equals("hks")){
			type = "hk";
		}
		TreeMap<String, String> map = new TreeMap<String, String>();
		Integer limit  = 1;
		int errorCnt = 0;
		while(true)
		{
			map.remove("sign");
			Integer offset = 50;
			if("hk".equals(type)){
				map.put("vendorCode", vendorCodehknews);
			}
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);
			map.put("v", v);
			map.put("format", format);
			map.put("signMethod", signMethod);
			map.put("limit", limit+"");
			map.put("offset", offset+"");
			String sign = getsign(map,str);
			map.put("sign", sign);
			String response = HttpRequest.sendPost(url, map);
//			System.out.println(response);

			try {
				JSONObject jsonObj = new JSONObject(response);
				String data = jsonObj.getString("data");
				JSONObject jsondata = new JSONObject(data);
				int totalCount = jsondata.getInt("totalCount");//商品总数
				String totalPage = jsondata.getString("totalPage");//页数
				int totalPageSize = (int) Math.floor((totalCount/offset))+1;
				JSONArray products = jsondata.getJSONArray("products");
				
				List<PlatformSku2LocationSku> mlhskuList = new ArrayList<PlatformSku2LocationSku>();
				for (int i = 0; i < products.length(); i++) {
					JSONObject item = new JSONObject(products.get(i).toString());
					String mlhSku = item.getString("skuId");
					String ourSku = item.getString("merchantSkuId");
					String status = item.getString("status");
					if(ourSku != null){
						ourSku = ourSku.trim();
					}
					if(status != null){
						status = status.trim();
					}
					if(StringUtil.isNotBlank(mlhSku) && StringUtil.isNotBlank(ourSku) && StringUtil.isNotBlank(status)){
						PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
						pslsku.setOurSku(ourSku);
						pslsku.setPlatformField("Mlhnewsec_sku");
						pslsku.setPlatformSku(mlhSku);
						pslsku.setPlatformstatus("Mlh_on_sale_status");
						pslsku.setStatus(status);
						pslsku.setType(type);
						mlhskuList.add(pslsku);
					}else{
						continue;
					}
				}
				Map mlhSkuMap = new HashMap();
				mlhSkuMap.put("list", mlhskuList);
				mlhSkuMap.put("platformField", "Mlhnewsec_sku");
				mlhSkuMap.put("platformstatus", "Mlh_on_sale_status");
				if(mlhskuList.size() > 0){
					platformStockUpdateDao.batchUpdatePlatformSku2Location(mlhSkuMap);
				}
				if(limit >= totalPageSize)break;
				limit++;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Mlhnewsec_sku syn sku error " + errorCnt);
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}
		}
		
	}

//5000以上的
	private  void updateSku2LocationByType(String type){
		TreeMap<String, String> map = new TreeMap<String, String>();
		Integer limit  = 1;
		int errorCnt = 0;
		while(true)
		{
			map.remove("sign");
			Integer offset = 50;
			if("hk".equals(type)){
				map.put("vendorCode", vendorCodehknew);
			}
			else{
				map.put("vendorCode", vendorCodesh);
			}
			String nowTime = sdf.format(new Date());
			map.put("timestamp", nowTime);
			map.put("v", v);
			map.put("format", format);
			map.put("signMethod", signMethod);
			map.put("limit", limit+"");
			map.put("offset", offset+"");
			String sign = getsign(map,type);
			map.put("sign", sign);
			String response = HttpRequest.sendPost(url, map);
//			System.out.println(response);

			try {
				JSONObject jsonObj = new JSONObject(response);
				String data = jsonObj.getString("data");
				JSONObject jsondata = new JSONObject(data);
				int totalCount = jsondata.getInt("totalCount");//商品总数
				String totalPage = jsondata.getString("totalPage");//页数
				int totalPageSize = (int) Math.floor((totalCount/offset))+1;
				JSONArray products = jsondata.getJSONArray("products");
				
				List<PlatformSku2LocationSku> mlhskuList = new ArrayList<PlatformSku2LocationSku>();
				for (int i = 0; i < products.length(); i++) {
					JSONObject item = new JSONObject(products.get(i).toString());
					String mlhSku = item.getString("skuId");
					String ourSku = item.getString("merchantSkuId");
					String status = item.getString("status");
					if(ourSku != null){
						ourSku = ourSku.trim();
					}
					if(status != null){
						status = status.trim();
					}
					if(StringUtil.isNotBlank(mlhSku) && StringUtil.isNotBlank(ourSku) && StringUtil.isNotBlank(status)){
						PlatformSku2LocationSku pslsku = new PlatformSku2LocationSku();
						pslsku.setOurSku(ourSku);
						pslsku.setPlatformField("Mlhnew_sku");
						pslsku.setPlatformSku(mlhSku);
						pslsku.setPlatformstatus("Mlh_on_sale_status");
						pslsku.setStatus(status);
						pslsku.setType(type);
						mlhskuList.add(pslsku);
					}else{
						continue;
					}
				}
				Map mlhSkuMap = new HashMap();
				mlhSkuMap.put("list", mlhskuList);
				mlhSkuMap.put("platformField", "Mlhnew_sku");
				mlhSkuMap.put("platformstatus", "Mlh_on_sale_status");
				if(mlhskuList.size() > 0){
					platformStockUpdateDao.batchUpdatePlatformSku2Location(mlhSkuMap);
				}
				if(limit >= totalPageSize)break;
				limit++;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("MLHNew syn sku error " + errorCnt);
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}
		}
	}


	@Override
	public void updateSku2LocationByFileMlh(Map<String, String> keyMap,
			String type) {
		Set<Entry<String,String>> keySet= keyMap.entrySet();
		Iterator<Entry<String,String>> it = keySet.iterator();
		 while(it.hasNext()){
			 Entry<String,String> entry = it.next();
			 String MlhSku = entry.getKey();
			 String ourSKu = entry.getValue();
			 if(StringUtil.isBlank(MlhSku) || StringUtil.isBlank(ourSKu)) continue;
			 MlhSku = MlhSku.trim();
			 ourSKu = ourSKu.trim();
			 List<PlatformSku2LocationSku> list = new ArrayList<PlatformSku2LocationSku>();
			 PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
			 pslSku.setOurSku(ourSKu);
			 pslSku.setPlatformField("Mlh_sku");
			 pslSku.setPlatformSku(MlhSku);
			 pslSku.setType(type);
			 list.add(pslSku);
			 
			 Map meilihuiSkuMap = new HashMap();
			 meilihuiSkuMap.put("list", list);
			 meilihuiSkuMap.put("platformField", "Mlh_sku");
			 if(list.size() > 0)
				{
					platformStockUpdateDao.batchUpdatePlatformSku2Location(meilihuiSkuMap);
				}
		 }
	}
	
		private void updateCanSaleProductByType(String type){
				Map<String,String> searchMap = new HashMap<String, String>();
				searchMap.put("type", type);
				searchMap.put("skuisnotnull", "Mlhnew_sku");
				List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
				for(StockUpdate s : list){
					if(s.getMlhnewSku() != null){
					int num = s.getNowStockNum() - s.getOrderStockNum();
//					int num = 0;
					if(num < 0) num = 0;
					updateCanSaleProduct(s.getMlhnewSku(),s.getSku(), num,s.getType());
					try {
						Thread.sleep(500);
					}catch (InterruptedException e) {
					}
					}
				}
			}
		
		
	@Override
	public boolean updateCanSaleProduct(String MlhnewSku,String ourSku,int quantity,String type) {
		if(StringUtil.isBlank(MlhnewSku) && StringUtil.isBlank(ourSku)){
			return true;
		}
		boolean  flag = true;
		Map<String, String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku)){
			searchMap.put("type", type);
			searchMap.put("MlhnewSku", MlhnewSku);
			List<StockUpdate> list= platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0){
				ourSku = list.get(0).getSku();
			}
		}else if(StringUtil.isBlank(MlhnewSku))
			{
				searchMap.put("sku", ourSku);
				searchMap.put("type", type);
				searchMap.put("field", "Mlhnew_sku");
				MlhnewSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
			}
			if(StringUtil.isBlank(MlhnewSku)){
			return true;
			}
			TreeMap<String, String> map = new TreeMap<String, String>();
			if("hk".equals(type)){
				map.put("vendorCode", vendorCodehknew);
			}
			else{
				map.put("vendorCode", vendorCodesh);
			}
			String nowTime = sdf.format(new Date());
			map.put("timestamp",nowTime);
			map.put("v", v);
			map.put("format", format);
			map.put("signMethod", signMethod);
			map.put("skuId", MlhnewSku);
			map.put("quantity", quantity+"");
			map.put("merchantSkuId", ourSku);
			String sign = getsign(map,type);
			map.put("sign", sign);
			
			//发送POST请求
			String response = HttpRequest.sendPost(api_url, map);
			
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", MlhnewSku);
			logMap.put("name", "Mlhnew");
			logMap.put("stock", quantity+"");
			logMap.put("location", type);
			logMap.put("type", "success");
			try {
				JSONObject jsonObject = new JSONObject(response);
				if("200".equals(jsonObject.getString("code")))
				{
					//更新成功
					flag = true;
				}else{
					 String codeMsg = jsonObject.getString("codeMsg");
					 logMap.put("type", "error");
					 logMap.put("error",codeMsg);
				}
				
			}
			catch (JSONException e) 
			{
				logMap.put("type", "error");
				logMap.put("error",response);
			}
			autoSyncDao.addUpdateLog(logMap);
			return flag;
	}
	
	
	
	
	
	//5000以下的库存更新
	private void updateCanSaleProductByTypes(String str){
		String type="";
		if(str.equals("hks")){
			type = "hk";
		}
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("type", type);
		searchMap.put("skuisnotnull", "Mlhnewsec_sku");
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate s : list){
			if(s.getMlhnewsecSku() != null){
			int num = s.getNowStockNum() - s.getOrderStockNum();
//			int num = 0;
			if(num < 0) num = 0;
			updateCanSaleProducts(s.getMlhnewsecSku(),s.getSku(), num,type);
			try {
				Thread.sleep(500);
			}catch (InterruptedException e) {
			}
			}
		}
	}

	/* (non-Javadoc)5000以下的库存更新
	 * @see com.huaixuan.network.biz.service.platformstock.MeiLiHuiNewPlatFormStockUpdate#updateCanSaleProducts(java.lang.String, java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean updateCanSaleProducts(String MlhnewsecSku, String ourSku,int quantity, String type) {
//		String type="";
//		if(str.equals("hks")){
//			type = "hk";
//		}
		if(StringUtil.isBlank(MlhnewsecSku) && StringUtil.isBlank(ourSku)){
			return true;
		}
		boolean  flag = true;
		Map<String, String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku)){
			searchMap.put("type", type);
			searchMap.put("MlhnewsecSku", MlhnewsecSku);
			List<StockUpdate> list= platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0){
				ourSku = list.get(0).getSku();
			}
		}else if(StringUtil.isBlank(MlhnewsecSku))
			{
				searchMap.put("sku", ourSku);
				searchMap.put("type", type);
				searchMap.put("field", "Mlhnewsec_sku");
				MlhnewsecSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
			}
			if(StringUtil.isBlank(MlhnewsecSku)){
			return true;
			}
			TreeMap<String, String> map = new TreeMap<String, String>();
 
			if("hk".equals(type)){
				map.put("vendorCode", vendorCodehknews);
			}
			else{
				return true;
			}
			
			String nowTime = sdf.format(new Date());
			map.put("timestamp",nowTime);
			map.put("v", v);
			map.put("format", format);
			map.put("signMethod", signMethod);
			map.put("skuId", MlhnewsecSku);
			map.put("quantity", quantity+"");
			map.put("merchantSkuId", ourSku);
			String sign = getsign(map,"hks");
			map.put("sign", sign);
			
			//发送POST请求
			String response = HttpRequest.sendPost(api_url, map);
//			System.out.println(response);
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", MlhnewsecSku);
			logMap.put("name", "Mlhnewsec");
			logMap.put("stock", quantity+"");
			logMap.put("location", type);
			logMap.put("type", "success");
			try {
				JSONObject jsonObject = new JSONObject(response);
				if("200".equals(jsonObject.getString("code")))
				{
					//更新成功
					flag = true;
				}else{
					 String codeMsg = jsonObject.getString("codeMsg");
					 logMap.put("type", "error");
					 logMap.put("error",codeMsg);
				}
				
			}
			catch (JSONException e) 
			{
				logMap.put("type", "error");
				logMap.put("error",response);
			}
			autoSyncDao.addUpdateLog(logMap);
			return flag;
	}
	
	
	/**
	 * 同步所有可售库存
	 */
	@Override
	public void updateAllStock() {
	
		//5000以上的库存更新
		updateCanSaleProductByType("hk");
		
		//5000以下的库存更新
		updateCanSaleProductByTypes("hks");
		
	}

	
}
