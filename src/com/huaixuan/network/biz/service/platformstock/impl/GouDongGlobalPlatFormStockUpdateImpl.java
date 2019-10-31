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
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.platformstock.GouDongGlobalPlatFormStockUpdate;
import com.hundsun.common.lang.StringUtil;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.SkuReadService.Page;
import com.jd.open.api.sdk.domain.ware.SkuReadService.Sku;
import com.jd.open.api.sdk.request.ware.SkuReadSearchSkuListRequest;
import com.jd.open.api.sdk.request.ware.StockWriteUpdateSkuStockRequest;
import com.jd.open.api.sdk.request.ware.WareSkuStockUpdateRequest;
import com.jd.open.api.sdk.response.ware.SkuReadSearchSkuListResponse;
import com.jd.open.api.sdk.response.ware.StockWriteUpdateSkuStockResponse;
import com.jd.open.api.sdk.response.ware.WareSkuStockUpdateResponse;


@Service("gouDongGlobalPlatFormStockUpdate")
public class GouDongGlobalPlatFormStockUpdateImpl implements GouDongGlobalPlatFormStockUpdate{
	
	private static final String APP_KEY = "AF540D8CF56B19C94883808A7F1546D8"; //0EDF5C042F17037A1A95660FB2701AA3
	private static final String APP_SECRET = "2341618569084b42bb1c170a8627ab52"; //13bb88d5e95744a6bbf64b240d729603
	private static final String BASE_URL = "https://api.jd.com/routerjson";  //https://api.jd.com/routerjson
	private static final String ACCESS_TOKEN = "ad0aa80e-08d8-4b91-bbf7-ed089907321c"; //68b326cd-049a-4935-b2d8-99d1115fd979
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private JdClient client = new DefaultJdClient(BASE_URL,ACCESS_TOKEN,APP_KEY,APP_SECRET);
	protected Log log = LogFactory.getLog(this.getClass()); 

	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private  AutoSyncDao autoSyncDao;
	

	@Override
	public void updateAllStock() {
		updateAllStockByType("hk");
	}
	
	public void updateAllStockByType(String type){
		//同步所有库存
		Map<String,String> searchMap = new HashMap<String, String>();
		//查询香港库存
		searchMap.put("type", "hk");
		searchMap.put("skuisnotnull", "jd_sku");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate s : list){
			int num = s.getNowStockNum() - s.getOrderStockNum();
			
			if(num < 0) num=0;
			updateGouDongStock(s.getJdSku(), s.getSku(), num, s.getType());
			//更新一次休息0.5秒
			try{
				Thread.sleep(500);
			}catch (InterruptedException e){
				
			}
		}
	}

	@Override
	public boolean updateGouDongStock(String jdSku, String ourSku, int num,
			String type) {
		//更新JD库存
		if(StringUtil.isBlank(jdSku) && StringUtil.isBlank(ourSku)) return true;
		
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku)){
			searchMap.put("type", type);
			searchMap.put("jdSku", jdSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0){
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(jdSku)){
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "jd_sku");
			jdSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(jdSku)) return true;
		
		
		//记录更新日志
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("sku", ourSku);
		logMap.put("psku", jdSku);
		logMap.put("name", "jd");
		logMap.put("stock", num+"");
		logMap.put("location", type);
		logMap.put("type", "success");
		
		StockWriteUpdateSkuStockRequest request=new StockWriteUpdateSkuStockRequest();
		Long jdskuLong = Long.parseLong(jdSku);
		Long jdnumLong = (long) num;
		request.setSkuId(jdskuLong);
		request.setStockNum(jdnumLong);

		boolean updateFlag = false;
		try
		{
			StockWriteUpdateSkuStockResponse response=client.execute(request); 
			
			if(response.getSuccess())
			{
				updateFlag = true;
			}
			else
			{
				logMap.put("type", "error");
				logMap.put("error", response.getMsg());
			}
		}
		catch (JdException e)
		{
			logMap.put("type", "error");
			logMap.put("error", e.getErrMsg());
		}
		
		autoSyncDao.addUpdateLog(logMap);
		return updateFlag;
	}

	
	
	@Override
	public void updateSku2Location() {
		//更新SKU到本地
		//2-下架 1-上架 4-删除
		updateSku2LocationByStatus("2");//2-下架 
		updateSku2LocationByStatus("1");//1-上架 

	}
	private void updateSku2LocationByStatus(String status){
		int pageNo = 1;
		int errorCnt = 1;
		while(true){
			SkuReadSearchSkuListRequest request=new SkuReadSearchSkuListRequest();
			request.setSkuStatuValue(status);
			request.setPageNo(pageNo++);
			try{
				SkuReadSearchSkuListResponse response= client.execute(request);
				Page page = response.getPage();
				Long totalNum = page.getTotalItem();
				int pageSize = page.getPageSize();
				if(totalNum <= 0) break;
				
				
				List<Sku> list = page.getData();
				if(list != null && list.size() > 0){
				List<PlatformSku2LocationSku> gouDongSkuList = new ArrayList<PlatformSku2LocationSku>();
				for(Sku s : list){
					String ourSku = s.getOuterId(); 
					String gouDongSku = s.getSkuId()+"";
					if(StringUtil.isNotBlank(ourSku)){
						ourSku = ourSku.trim();
						if(ourSku.length() == 13){
							//同步sku到本地,单个更新使用, 批量更新 分期暂时只有国内
							PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
							pslSku.setOurSku(ourSku);
							pslSku.setPlatformField("jd_sku");
							pslSku.setPlatformSku(gouDongSku);
							pslSku.setType("hk"); 
							gouDongSkuList.add(pslSku);
						}
					}
				}
				
				Map jdSkuMap = new HashMap();
				jdSkuMap.put("list", gouDongSkuList);
				jdSkuMap.put("platformField", "jd_sku");
 
				
				if(gouDongSkuList.size() > 0){
					//更新京东sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(jdSkuMap);
				}
				int totalPageNo = (int)((totalNum/pageSize) + 1);
				if(pageNo > totalPageNo) {
					break;
				}
			}else{
				break;
			}
			}
			catch (JdException e)
			{
				log.error(e.getMessage(), e);
				if(errorCnt++ > 5)
				{
					e.printStackTrace();
					System.out.println("gouDong sync sku error over 5 cnt");
					break;
				}
			}
			
		}
	}
	@Override
	public int atuoSyncOrder() {
		//同步订单
		return 0;
	}
}
