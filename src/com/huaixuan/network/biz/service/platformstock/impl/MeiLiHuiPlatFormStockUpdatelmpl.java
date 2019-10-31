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
import com.huaixuan.network.biz.service.platformstock.MeiLiHuiPlatFormStockUpdate;
import com.huaixuan.network.biz.service.platformstock.PlatFormOrderRecordService;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;

@Service("MeiLiHuiPlatFormStockUpdate")
public class MeiLiHuiPlatFormStockUpdatelmpl  implements MeiLiHuiPlatFormStockUpdate{
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	//CB00054
	private static final String vendorCodehk = "690000000041176";//海外正式环境
	private static final String vendorCodesh = "V12594";//国内正式环境
	
	private static final String secrethk = "DAUDU";//海外正式环境
	private static final String secretsh = "TMUOX";//国内正式环境
	
	private static final String signMethod = "MD5";
	private static final String format = "json";
	private static final String v = "1.0";
	
	private static final String api_url = "http://116.62.245.214/inventory/update";//更新库存接口(正式环境)
	private static final String url = "http://116.62.245.214/sku/list";//获取sku接口(正式环境)
	
//	private static final String api_url = "http://121.196.210.172/inventory/update";//测试环境更新库存接口
//	private static final String url ="http://121.196.210.172/sku/list";//测试环境获取SKU接口
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
			sign = secrethk + sign + secrethk;
		}
		else{
			sign = secretsh + sign + secretsh;
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
	    platformStockUpdateDao.updateStockUpdateSku2Null("Mlh_on_sale_status");
	    
		updateSku2LocationByType("hk");
		
		updateSku2LocationByType("sh");
	}

	private  void updateSku2LocationByType(String type){
		TreeMap<String, String> map = new TreeMap<String, String>();
		Integer limit  = 1;
		
		while(true)
		{
			map.remove("sign");
			Integer offset = 50;
			if("hk".equals(type)){
				map.put("vendorCode", vendorCodehk);
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
						pslsku.setPlatformField("Mlh_sku");
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
				mlhSkuMap.put("platformField", "Mlh_sku");
				mlhSkuMap.put("platformstatus", "Mlh_on_sale_status");
				if(mlhskuList.size() > 0){
					platformStockUpdateDao.batchUpdatePlatformSku2Location(mlhSkuMap);
				}
				if(limit >= totalPageSize)break;
				limit++;
			} catch (JSONException e) {
				e.printStackTrace();
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
				searchMap.put("skuisnotnull", "Mlh_sku");
				List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
				for(StockUpdate s : list){
					if(s.getMlhSku() != null){
					int num = s.getNowStockNum() - s.getOrderStockNum();
//					int num = 0;
					if(num < 0) num = 0;
					updateCanSaleProduct(s.getMlhSku(),s.getSku(), num,s.getType());
					try {
						Thread.sleep(500);
					}catch (InterruptedException e) {
					}
					}
				}
			}

	@Override
	public boolean updateCanSaleProduct(String MlhSku,String ourSku,int quantity,String type) {
		if(StringUtil.isBlank(MlhSku) && StringUtil.isBlank(ourSku)){
			return true;
		}
		boolean  flag = true;
		Map<String, String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku)){
			searchMap.put("type", type);
			searchMap.put("MlhSku", MlhSku);
			List<StockUpdate> list= platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0){
				ourSku = list.get(0).getSku();
			}
		}else if(StringUtil.isBlank(MlhSku))
			{
				searchMap.put("sku", ourSku);
				searchMap.put("type", type);
				searchMap.put("field", "Mlh_sku");
				MlhSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
			}
			if(StringUtil.isBlank(MlhSku)){
			return true;
			}
			TreeMap<String, String> map = new TreeMap<String, String>();
			if("hk".equals(type)){
				map.put("vendorCode", vendorCodehk);
			}
			else{
				map.put("vendorCode", vendorCodesh);
			}
			String nowTime = sdf.format(new Date());
			map.put("timestamp",nowTime);
			map.put("v", v);
			map.put("format", format);
			map.put("signMethod", signMethod);
			map.put("skuId", MlhSku);
			map.put("quantity", quantity+"");
			map.put("merchantSkuId", ourSku);
			String sign = getsign(map,type);
			map.put("sign", sign);
			
			//发送POST请求
			String response = HttpRequest.sendPost(api_url, map);
			
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", MlhSku);
			logMap.put("name", "Mlh");
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
	
		updateCanSaleProductByType("hk");
		
		updateCanSaleProductByType("sh");
		
	}
}
