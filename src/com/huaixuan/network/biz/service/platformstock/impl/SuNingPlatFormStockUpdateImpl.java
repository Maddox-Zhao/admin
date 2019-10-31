/**
 * 
 */
package com.huaixuan.network.biz.service.platformstock.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.platformstock.SuNingPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.hundsun.common.lang.StringUtil;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.custom.itemSaleQueryRequest;
import com.suning.api.entity.custom.itemSaleQueryResponse;
import com.suning.api.entity.custom.orderQueryQueryRequest;
import com.suning.api.entity.custom.orderQueryQueryResponse;

import com.suning.api.entity.inventory.ParallelinventoryModifyRequest;
import com.suning.api.entity.inventory.ParallelinventoryModifyResponse;
import com.suning.api.entity.item.ItemdetailQueryRequest;


import com.suning.api.exception.SuningApiException;
/**
 * @author TT
 * 
 */
@Service("suNingPlatFormStockUpdate")
public class SuNingPlatFormStockUpdateImpl implements SuNingPlatFormStockUpdate {
	
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	private static  String APP_KEY = "31c27d1ebee0447c49b7c0b06a7574ea";   //正式环境
	private static  String APP_SECRET = "efed01a63bc47a1882fa9a90ec8e280d";//正式环境
	private static  String API_URL = "https://open.suning.com/api/http/sopRequest/"; //正式环境
	


   protected Log log = LogFactory.getLog(this.getClass());
	//更新sku到本地

	public void updateSku2LocationByStatus(String status)
	{	
//		suning.custom.itemsale.query 商品销售情况查询(只会获取到苏宁的主商品编码)
//		suning.custom.itemdetail.query 获取我的商品详情信息(通过苏宁的主商品编码获得，苏宁的子商品编码和我们的sku)
		itemSaleQueryRequest request = new itemSaleQueryRequest();
		int pageNo = 1;
		int pageSize = 20;
		int pageTotal = 0;
		int errorCnt = 1;
		while(true){
//			System.out.println("pageNo的值===="+pageNo);
			request.setPageNo(pageNo);
			request.setPageSize(pageSize);
			request.setSaleStatus("1"); //商品销售状态。0-停售； 1-在售； 2-待售；3-强制下架；4-违规下架；5-从未上架。
			//api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
//			request.setCheckParam(true);
			
//			 System.out.println("我的页数"+pageNo);
			try {
			 DefaultSuningClient client = new DefaultSuningClient(API_URL, APP_KEY,APP_SECRET, "json");
			 itemSaleQueryResponse response = client.excute(request);
//			 System.out.println("返回json/xml格式数据 :" + response.getBody());
			 
			 
			 JSONObject jsons = new JSONObject(response.getBody());
//			 System.out.println("我的少时诵诗书所所"+response);
			 
			 JSONObject jsonContent = jsons.getJSONObject("sn_responseContent"); //String类型的对象转为JSONObject
			 //当返回错误时,跳出循环
			 if(jsonContent.has("sn_error")){
				 break;
			 }
			 
			 JSONObject jsonHead = jsonContent.getJSONObject("sn_head");
			 String page = jsonHead.getString("pageTotal");
			 pageTotal = Integer.parseInt(page); //当前页数
			 
			
             String result = jsonHead.getString("returnMessage");
             //
             if(result.equals("biz.handler.data-get:success")){
            	 String sn_body  = jsonContent.getString("sn_body");
            	 JSONObject jsonBody = new JSONObject(sn_body);
            	 
            	 JSONArray dataArrays = jsonBody.getJSONArray("itemSale");  //数组类型的对象转为JSONArray
            	 List<PlatformSku2LocationSku> sunNingSkuList = new ArrayList<PlatformSku2LocationSku>();
            	 for(int i=0;i<dataArrays.length();i++){
            		 JSONObject dataArrayObject = new JSONObject(dataArrays.get(i).toString());          		
            		 String productCode = dataArrayObject.getString("productCode"); //苏宁主商品编码
            		 ItemdetailQueryRequest reqquestDetail = new ItemdetailQueryRequest(); //通过苏宁主商品编码查询，这个主商品编码下所有的苏宁子商品编码和我们的商品编码
            		 reqquestDetail.setProductCode(productCode);
            		 DefaultSuningClient clientDetail = new DefaultSuningClient(API_URL, APP_KEY,APP_SECRET, "json");
            		 
            		 try {
            			 com.suning.api.entity.item.ItemdetailQueryResponse responseDetail = clientDetail.excute(reqquestDetail);
            			JSONObject jsonsBody = new JSONObject(responseDetail.getBody());
//            			  System.out.println("返回json/xml格式数据 :"+responseDetail.getBody());
            			JSONObject responseContent = jsonsBody.getJSONObject("sn_responseContent");
            			 if(responseContent.has("sn_error")){
            				 break;
            			 }
            			JSONObject bodyDetail = responseContent.getJSONObject("sn_body");
            			JSONObject jsobjItemDetail = bodyDetail.getJSONObject("itemDetail");
            			 //当有子商品时，数据库保存的是苏宁子编码和我们的sku，两者一一对应
               		 if(jsobjItemDetail.has("childItem") && jsobjItemDetail.getString("childItem")!=null){
               			 JSONArray dataArray =jsobjItemDetail.getJSONArray("childItem");
               			 for(int y=0;y<dataArray.length();y++){          				 
               				 
               				 JSONObject dataObject = new JSONObject(dataArray.get(y).toString());
               				 String ourSku="";
               				 String itemCode= dataObject.getString("itemCode"); //我们的skku
               				 String suingSku= dataObject.getString("productCode");//苏宁sku
               				 //防止itemCode和suingSku两个字段没有值
//               				 if(StringUtil.isNotBlank(itemCode) && StringUtil.isNotBlank(suingSku)){
               					if(itemCode.indexOf("-")>0){
                					   ourSku= itemCode.split("-")[1];
                				 }else{
                					 ourSku=itemCode;
                				 }
//               				 }else{
//               					 itemCode= dataArrayObject.getString("itemCode"); 		        		 
//           		        		 suingSku = dataArrayObject.getString("productCode"); //苏宁平台生成的sku
//               				 }
               				 
               				 
               				 PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
               				 pslSku.setOurSku(ourSku);
        						 pslSku.setPlatformField("suning_sku");
        						 pslSku.setPlatformSku(suingSku);
        						 pslSku.setType("hk");
        						 sunNingSkuList.add(pslSku);       				 
               			 }
               		 }else{//当只有一个主商品时，数据库保存的是苏宁主编码和我们的sku，两者一一对应(即一个主编码对应我们的sku)
               			 
   		                 String itemCode= dataArrayObject.getString("itemCode"); //我们的货号和sku(22D200 0684099-9820005835555)		        		 
   		        		 String suingSku = dataArrayObject.getString("productCode"); //苏宁平台生成的sku
   		        		 String ourSku="";
   		        		 
   		        		 //要存入数据库 的我们的sku 
   		        		 if(itemCode.indexOf("-")>0){
         					   ourSku= itemCode.split("-")[1];
   	      				 }else{
   	      					 ourSku=itemCode;
   	      				 }
   		        		 
   		        		 PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
    						 pslSku.setOurSku(ourSku);
    						 pslSku.setPlatformField("suning_sku");
    						 pslSku.setPlatformSku(suingSku);
    						 pslSku.setType("hk"); 
    						 sunNingSkuList.add(pslSku);
               		 } 
            			} catch (SuningApiException e) {
            			 e.printStackTrace();
            			 log.error(e.getMessage(), e);
            			}      		
            	 }
            	 Map snSkuMap = new HashMap();
            	 snSkuMap.put("list", sunNingSkuList);
 				 snSkuMap.put("platformField", "suning_sku");				
 				if(sunNingSkuList.size() > 0)
 				{
 					//更新苏宁的sku到本地(数据库保存的都是苏宁子编码和我们的sku，两者一一对应)
 					platformStockUpdateDao.batchUpdatePlatformSku2Location(snSkuMap);
 				}
            	 
             }
             if(pageNo>=pageTotal){
				 break;
			 }
			 pageNo++;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				System.out.println("suning syn sku error " + errorCnt);
				if(errorCnt++ > 5) break;
			}
			
		}
		
	}	
	
	
	
	//更新苏宁的sku到本地
	@Override
	public void updateSku2Location()
	{
		//商品销售状态。0-停售； 1-在售； 2-待售；3-强制下架；4-违规下架；5-从未上架。		
		updateSku2LocationByStatus("1");
		updateSku2LocationByStatus("2");
	
	}
	
	
	

	/* 更新出售中的产品库存
	 * suning.custom.parallelinventory.modify （平行仓模式）修改单个仓库单个商品库存
	 */
	@Override
	public void updateAllStock() {
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("type", "hk");
		searchMap.put("skuisnotnull", "suning_sku");
		List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			for (StockUpdate s : list) {
				if (s.getSuningSku() != null) {
					int num = s.getNowStockNum() - s.getOrderStockNum();
					if (num < 0) num = 0;
					updateSuNingStock(s.getSku(), s.getSuningSku(), num, s.getType());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
					}
				}
			}		
	}

	@Override
	public boolean updateSuNingStock(String ourSku,String suningSku, int num,String type) {
		if(StringUtil.isBlank(suningSku) && StringUtil.isBlank(ourSku)) return true;
		Map<String,String> searchMap = new HashMap<String, String>();
		if(StringUtil.isBlank(ourSku))
		{
			searchMap.put("type", type);
			searchMap.put("suningSku", suningSku);
			List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
			if(list.size() > 0)
			{
				ourSku = list.get(0).getSku();
			}
		}
		else if(StringUtil.isBlank(suningSku))
		{
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "suning_sku");
			suningSku = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		if(StringUtil.isBlank(suningSku)) return true;
		if(StringUtil.isNotBlank(suningSku) && StringUtil.isNotBlank(ourSku) && "hk".equals(type))
		{   
			ParallelinventoryModifyRequest request = new ParallelinventoryModifyRequest();
			
			request.setInvCode("001");  //商家平行仓仓库编码是001
			request.setProductCode(suningSku);  //苏宁商品编码。
			request.setItemCode(ourSku);  //商家商品编码，即我们的sku
			request.setDestInvNum(num+"");            //需要维护的库存值
			//api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
//			request.setCheckParam(true);
		
			Map<String, String> logMap =null;
			
			try {
				DefaultSuningClient client = new DefaultSuningClient(API_URL, APP_KEY,APP_SECRET, "json");
				ParallelinventoryModifyResponse response = client.excute(request);
//			    {"sn_responseContent":{"sn_head":{},"sn_body":{"parallelInventory":{"result":"Y"}}}} 正确返回
//				{"sn_responseContent":{"sn_error":{"error_code":"biz.custom.inventory.invalid-biz:productCode"}}} 错误返回
				 logMap = new HashMap<String, String>();
				logMap.put("sku", ourSku);
				logMap.put("psku", suningSku);
				logMap.put("name", "suning");
				logMap.put("stock", num + "");
				logMap.put("location", type);
				logMap.put("type", "success");
				try {
					JSONObject responseBody = new JSONObject(response.getBody());
					JSONObject responseContent = responseBody.getJSONObject("sn_responseContent");
					 if(responseContent.has("sn_body")){
						    JSONObject sn_body = responseContent.getJSONObject("sn_body");
							JSONObject inventory = sn_body.getJSONObject("parallelInventory");
							 String result = inventory.getString("result");
							 if(result.equals("Y")){
								logMap.put("type", "success_"+result);
							 }
							 
					 }else if(responseContent.has("sn_error")){					   
						 JSONObject sn_error = responseContent.getJSONObject("sn_error");
						 String error = sn_error.getString("error_code");
					     logMap.put("type", "error");
						 logMap.put("error", error);
					 }else{
						 logMap.put("type", "error");
						 logMap.put("error", "error");
					 }
			
				} catch (Exception e) {
					logMap.put("type", "error");
					logMap.put("error", "catch-error");
					log.error(e.getMessage(), e);
					e.printStackTrace();
				}
			} catch (Exception e) {
			 e.printStackTrace();
			 log.error(e.getMessage(), e);
			}
			autoSyncDao.addUpdateLog(logMap);
		}
		

		return true;
	}
	
	
	

//	
	/* 
	 * 获取订单
	 * 
	 */
	@Override
	public int atuoSyncOrder() {
		int updateNumber = 0;
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String nowTime = sdf.format(new Date());
	   String preTime = sdf.format(new Date().getTime() - 14*24*60*60*1000); //两天内的订单
//	   System.out.println(nowTime+"==========nowTime");
//	   System.out.println(preTime+"==========preTime");
	   Map<String, List<String>> orderMap = new HashMap<String, List<String>>();
	   List<String> list = new ArrayList<String>();
		
	    orderQueryQueryRequest request = new orderQueryQueryRequest();
		request.setEndTime(nowTime);
//	     订单头状态（10：买家已付款，20：卖家已发货，21：部分发货，30：交易成功，40：交易关闭）
//		request.setOrderStatus("10");
		request.setPageNo(1);
		request.setPageSize(50);
		request.setStartTime(preTime);
		//api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
		request.setCheckParam(true);
		DefaultSuningClient client = new DefaultSuningClient(API_URL,APP_KEY,APP_SECRET,"json");
		String createtime="";
		try {
		 orderQueryQueryResponse response = client.excute(request);
//		 System.out.println("返回json/xml格式数据 :" + response.getBody());

//		 {"sn_responseContent":{"sn_error":{"error_code":"sys.check.pagesize-format:error"}}}
		 
		 JSONObject jsonBody = new JSONObject(response.getBody());
		 JSONObject responseContent = jsonBody.getJSONObject("sn_responseContent");
		 if(responseContent.has("sn_error")){
			 return 0;
		 }
		JSONObject sn_body = responseContent.getJSONObject("sn_body");
		JSONArray orderQuery = sn_body.getJSONArray("orderQuery");
		
		for(int i=0;i<orderQuery.length();i++){
			
			JSONObject jsonObj = new JSONObject(orderQuery.get(i).toString());
			String order_no = jsonObj.getString("orderCode"); //订单号  
			 createtime = jsonObj.getString("orderSaleTime"); //订单创建时间	
			String receviername = jsonObj.getString("customerName");// 收件人姓名
			String mobile = jsonObj.getString("mobNum"); // 收件人电话号码
			String province = jsonObj.getString("provinceName"); // 省
			String city = jsonObj.getString("cityName"); // 市
			String district = jsonObj.getString("districtName"); // 区
			String address = jsonObj.getString("customerAddress"); // 街道地址
			String postalcode = jsonObj.getString("districtCode"); // 邮编编码 
			 
			JSONArray orderDetail = jsonObj.getJSONArray("orderDetail");
			for(int t=0;t<orderDetail.length();t++){
				JSONObject orderdetail = new JSONObject(orderDetail.get(t).toString());
				
				String ourSkuCode = "";
				String spucode = orderdetail.getString("productCode"); // 平台的sku
				String skucode = orderdetail.getString("itemCode"); // 我们的sku				
				if(skucode.indexOf("-")>0){
					ourSkuCode=skucode.split("-")[1];
				}else{
					ourSkuCode=skucode;
				}
				
				String saleNum = orderdetail.getString("saleNum");// 此sku商品的数量
				String qty = saleNum.substring(0,saleNum.indexOf("."));
				String productName = orderdetail.getString("productName"); // 商品的名字
				
				PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
				platformorderdetails.setIdorder(order_no);
				List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);

				platformorderdetails.setIdorder(order_no); // 订单号
				platformorderdetails.setPalcedTime(createtime); // 下单时间
				platformorderdetails.setName(receviername); // 收件人姓名
				platformorderdetails.setMobile(mobile); // 收件人电话
				platformorderdetails.setProvince(province); // 省份
				platformorderdetails.setCity(city); // 城市
				platformorderdetails.setDistrict(district); // 区
				platformorderdetails.setStreetAddress(address);// 街道地址
				platformorderdetails.setZipCode(postalcode); // 邮政编码
//				platformorderdetails.setTotalPrice(saleprice); // 订单总价
//				platformorderdetails.setDiscountPrice(price); // 优惠总价
//				platformorderdetails.setFreight(deliveryAmount); // 运费
				platformorderdetails.setSkuId(spucode); // 平台SKU
				platformorderdetails.setMerchantSkuId(ourSkuCode); // 我们的SKU
				platformorderdetails.setQuantity(qty); // 商品数量
				platformorderdetails.setProductname(productName); // 商品名称
				platformorderdetails.setPtype("suning"); // 平台订单
				if ((liststatus == null || liststatus.size() == 0)) {
					platformOrderDetailsDao.insertOrder(platformorderdetails);

				} else if (liststatus != null) {
					List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
					if (listst == null || listst.size() == 0) {
						platformOrderDetailsDao.insertOrder(platformorderdetails);
					}
				}
				
				list.add(order_no); // 将订单号放进list
				String msg = ourSkuCode + "_" + qty; // 我们的sku和购买的商品数量

				if (orderMap.get(order_no) == null) {
					List<String> value = new ArrayList<String>();
					value.add(msg);
					orderMap.put(order_no, value);

				} else {
					orderMap.get(order_no).add(msg);
				}
			}					
		}
		} catch (Exception e) {
		 e.printStackTrace();
		 log.error(e.getMessage(), e);
		}
		
		if(orderMap.size()>0){
			list.add("-1"); // 避免没有数据 报错
			//查询数据库是否已有此条数据
		List<PlatFormOrderRecord> orderList =	platformStockUpdateDao.getOrdersByOrders(list);
		Map<String, String> orderMapRealy = new HashMap<String, String>();
		//orderList已有数据，进入循环，没有跳过此循环
			for(PlatFormOrderRecord order:orderList){
				orderMapRealy.put(order.getOrderId(), "yes");
			}
			Set<Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Entry<String, List<String>>> it = set.iterator();
			while(it.hasNext()){
				Entry<String, List<String>> entry = it.next();
				String orderId = entry.getKey(); //订单id
				List<String> value = entry.getValue(); //此订单下的所有商品，即所有的sku(一个订单，对应多个商品)
				boolean updateSuccess = false;
				//null代表此orderId未成插入订单表
				if(orderMapRealy.get(orderId)==null){
					 for(String v:value){
						 String[] arr = v.split("_");
						 String sku = arr[0];
						 int num = Integer.parseInt(arr[1]);
						 Map<String,String> searchMap = new HashMap<String, String>();
						 searchMap.put("sku", sku);
						 searchMap.put("type","hk");
						 List<StockUpdate> stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						 for(StockUpdate su:stockUpdateList){
							    su.setSuningOrderStock(num + su.getSuningOrderStock());
								su.setLastOrderTime("yes");
								platformStockUpdateDao.updateStockByNotNull(su);
								updateSuccess = true;
								updateNumber++;
						 }
					 }				
				}			
				if (updateSuccess) {
					PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
					oderRecord.setOrderId(orderId);
					oderRecord.setOrderTime(createtime);
					oderRecord.setIdPlartform(16);//苏宁
					oderRecord.setIdStatus(1); // 已支付的订单
					oderRecord.setType("hk");
					platformStockUpdateDao.insertOrder(oderRecord);
				}
			}
			
		}
		return updateNumber;
	}

}
