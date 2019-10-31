package com.huaixuan.network.biz.service.platformstock.impl;

import java.io.IOException;
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
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.TmallPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Sku;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.request.ItemSkusGetRequest;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.ItemQuantityUpdateResponse;
import com.taobao.api.response.ItemSkusGetResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
 



/**
 * @author Mr_Yang   2016-10-21 下午05:13:34
 **/

@Service("tmallPlatFormStockUpdate")
public class TmallPlatFormStockUpdateImpl implements TmallPlatFormStockUpdate
{
   //https://alipay.open.taobao.com/docs/api.htm?apiId=162
	private static  String APP_KEY = "23482657";  
	private static  String APP_SECRET = "28d5542c5f46276579f562e3f077fbc1";
	                                     //6200d03556dfh255fb50b20e4de68cb5265c9d682ae71371744930853
	private static  String ACCESS_TOKEN = "6202504f140egib82a06469f7ad5156426eded4a6cc2f011744930853";
	private static  String API_URL = "http://gw.api.taobao.com/router/rest?";
	private static  String TMALL_SKU_FILE_NAME= "tmall_sku.txt";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
	protected Log log = LogFactory.getLog(this.getClass());
	
	private Map<String,List<String>> repSkuMap = new HashMap<String, List<String>>(); //记录有重复sku的产品
 
	
	public TmallPlatFormStockUpdateImpl()
	{
		repSkuMap = MiniUiUtil.readRpeatSku(TMALL_SKU_FILE_NAME);
	}

	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	
	@Override
	public int atuoSyncOrder()
	{
		TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, APP_SECRET);
		TradesSoldGetRequest req = new TradesSoldGetRequest();
		req.setFields("seller_nick,buyer_nick,title,type,created,sid,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,orders.title,orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type");
		Date now_24 = new Date(new Date().getTime() - 2*24*60*60*1000); //2天内的订单
		req.setStartCreated(now_24);
		req.setEndCreated(new Date());
		req.setPageSize(100L);//每页100条，查一次足够了(1天内100个订单)  以后订单多了再改
		//req.setStatus("WAIT_BUYER_CONFIRM_GOODS");//等待确认
		req.setStatus("WAIT_SELLER_SEND_GOODS");  //等待卖家发货
		Map<String,List<String>> orderMap = new HashMap<String, List<String>>(); //保存订单信息和对应的sku
		List<String> allOrderList = new ArrayList<String>(); //当前查询出来的订单id
		try
		{
			
			TradesSoldGetResponse response = client.execute(req , ACCESS_TOKEN);
//			System.out.println("天猫："+response);
			if(response.getErrorCode() == null)
			{
				List<Trade>  trades = response.getTrades();
				if(trades != null) 
				{
					for(Trade t : trades)
					{
						List<Order> orders = t.getOrders();
						String orderId = t.getSid();//订单ID
						Date created = t.getCreated();
						Date pay_time = t.getPayTime();
						String createds = sdf.format(created);//下单时间
						String paytime = sdf.format(pay_time);//支付时间
						String receiver_name = t.getReceiverName();//收货人姓名
						String receiver_mobile = t.getReceiverMobile();//收件人电话
						String receiver_state = t.getReceiverState();//省
						String receiver_city = t.getReceiverCity();//市
						String receiver_district = t.getReceiverDistrict();//区
						String receiver_address = t.getReceiverAddress();//详细地址
						String receiver_zip =t.getReceiverZip();//邮政编码
						String total_fee = t.getTotalFee();//订单总价
						String payment = t.getPayment();//实付金额
						String post_fee = t.getPostFee();//邮费
						PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
						platformorderdetails.setIdorder(orderId);


						List<PlatFormOrderDetails> liststatus = platformOrderDetailsDao.selectList(platformorderdetails);

						allOrderList.add(orderId);
						for(Order o : orders)
						{
							String skuId = o.getSkuId();//平台SKU
							String ourSku = o.getOuterSkuId();//我们的SKU
							Long num = o.getNum();//购买数量
							String title = o.getTitle();//商品名称
							String sku_properties_name = o.getSkuPropertiesName();//商品尺寸
							String price = o.getPrice();//商品售价

								platformorderdetails.setIdorder(orderId);
								platformorderdetails.setPalcedTime(createds);
								platformorderdetails.setPayTime(paytime);
								platformorderdetails.setName(receiver_name);
								platformorderdetails.setMobile(receiver_mobile);
								platformorderdetails.setProvince(receiver_state);
								platformorderdetails.setCity(receiver_city);
								platformorderdetails.setDistrict(receiver_district);
								platformorderdetails.setStreetAddress(receiver_address);
								platformorderdetails.setZipCode(receiver_zip);
								platformorderdetails.setTotalPrice(total_fee);
								platformorderdetails.setPayprice(payment);
								platformorderdetails.setFreight(post_fee);
								platformorderdetails.setSkuId(skuId);
								platformorderdetails.setMerchantSkuId(ourSku);
								platformorderdetails.setQuantity(num+"");
								platformorderdetails.setProductname(title);
								platformorderdetails.setSize(sku_properties_name);
								platformorderdetails.setPrice(price);
								platformorderdetails.setPtype("tmall");
								if ((liststatus == null || liststatus.size() == 0)) {
									platformOrderDetailsDao.insertOrder(platformorderdetails);

								} else if (liststatus != null) {
									List<PlatFormOrderDetails> listst = platformOrderDetailsDao.selectList(platformorderdetails);
									if (listst == null || listst.size() == 0) {
										platformOrderDetailsDao.insertOrder(platformorderdetails);
									}
								}
							String taobaoNumiid = o.getNumIid()+"";
							Long number = o.getNum();
							String productAndSaleInfo =  ourSku+"_"+taobaoNumiid+"_"+number;//oursku_tmallsku_number
							if(orderMap.get(orderId) == null)
							{
								List<String> list = new ArrayList<String>();
								list.add(productAndSaleInfo);
								orderMap.put(orderId, list);
							}
							else
							{
								List<String> list = orderMap.get(orderId);
								list.add(productAndSaleInfo);
							}						 
						}
					}
				}
			}
		
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		
		int updateNumber = 0;//用户下单数量
		
		
		//如果有数据
		if(orderMap.size() > 0)
		{
			
			allOrderList.add("-1");//避免没有数据  报错
			List<PlatFormOrderRecord>  orderList = platformStockUpdateDao.getOrdersByOrders(allOrderList);
			//用作对比 如果有记录则说明已处理
			Map<String,String> orderMapRealy = new HashMap<String, String>();
			for(PlatFormOrderRecord k : orderList)
			{
				orderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Entry<String, List<String>>> it = set.iterator();
			
			while(it.hasNext())
			{
				Entry<String, List<String>> entry = it.next();
				String orderId  = entry.getKey();
				 List<String> value = entry.getValue();//oursku_tmallsku_number
				if(orderMapRealy.get(orderId) == null) //没有保存该orderid 没处理  增加orderid数 并插入表
				{
						boolean updateSuccess = false;
						if(value != null && value.size() > 0)
						{
							for(String v : value)
							{
								String[] arr = v.split("_");
								String ourSku = arr[0];
								String tmallSku = arr[1];
								int num = Integer.valueOf(arr[2]);
								Map<String,String> searchMap = new HashMap<String, String>();
								searchMap.put("type", "sh");
								searchMap.put("sku", ourSku);
								List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
								 
								for(StockUpdate su : stockUpdateList)
								{
									Map<String,String> searchMaps = new HashMap<String, String>();
									searchMaps.put("sku", ourSku);
									searchMaps.put("type", "hk");
									boolean updatesh = true;
									List<StockUpdate>  stockUpdateLists = platformStockUpdateDao.selectStockUpdateByMap(searchMaps);
									if(stockUpdateLists != null && stockUpdateLists.size() > 0){
									int  s = su.getNowStockNum()-su.getOrderStockNum();
									if(s <=0 ){
											for(StockUpdate ss : stockUpdateLists){
												ss.setTmallOrderStock(num + ss.getTmallOrderStock());
												ss.setLastOrderTime("yes");
												platformStockUpdateDao.updateStockByNotNull(ss);
												updateSuccess = true;
												updatesh = false;
												updateNumber++;
											}
										}
									}
									if(updatesh){
										su.setTmallOrderStock(num + su.getTmallOrderStock());
										su.setLastOrderTime("yes");
										platformStockUpdateDao.updateStockByNotNull(su);
										updateSuccess = true;
										updateNumber++;
									}
								}
							}
						}
						
						if(updateSuccess)
						{
							//记录该orderid
							PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
							oderRecord.setOrderId(orderId);
							oderRecord.setIdPlartform(5);//tmall
							oderRecord.setIdStatus(1);//已支付的订单
							platformStockUpdateDao.insertOrder(oderRecord);
							/*int num = 0;
							String ourSku = "";
							if(value != null && value.size() > 0)
							{
								for(String v : value)
								{
									String[] arr = v.split("_");
									ourSku = arr[0];
									String tmallSku = arr[1];
									num = Integer.valueOf(arr[2]);
									Map<String,String> searchMap = new HashMap<String, String>();
									searchMap.put("tmallSku", tmallSku);
									searchMap.put("sku", ourSku);
								}
							}*/
							
						}
					
				}
			}
		}
		
		return updateNumber;
	}

	@Override
	public void updateAllStock()
	{
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("skuisnotnull", "tmall_sku");
		List<StockUpdate> list =  platformStockUpdateDao.selectStockUpdateByMap(searchMap);
		for(StockUpdate s : list)
		{
			
			int num = s.getNowStockNum() - s.getOrderStockNum();
			//查询香港库存
//			Map<String,String> map = new HashMap<String, String>();
//			map.put("type", "hk");
//			map.put("sku", s.getSku());
//			List<StockUpdate> hkList =  platformStockUpdateDao.selectStockUpdateByMap(map);
//			int hkNum = 0;
//			for(StockUpdate hks : hkList)
//			{
//				int cnt = hks.getNowStockNum() - hks.getOrderStockNum();
//				if(cnt < 0 ) cnt = 0;
//				hkNum += cnt;
//			}
//			num += hkNum;
			
			if(num < 0) num = 0;
			updateTmallStock(s.getTmallSku(), s.getSku(), num, s.getType());
			//更新一次休息1秒
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				log.error(e.getMessage(), e);
			}
		}

	}

	@Override
	public void updateSku2Location()
	{
//		stockService.setCanUpdateStockStatus("false"); //暂时不让其他地方做更新
//		platformStockUpdateDao.updateStockUpdateSku2Null("tmall_sku");//更新平台sku为NULL
		
		platformStockUpdateDao.updateStockUpdateSku2Null("tmall_on_sale_status"); //更新平台tmall_on_sale_status为NULL
		//清空文件
		MiniUiUtil.writeText2File(TMALL_SKU_FILE_NAME, "", "cx");
		
		updateCanSellSku2Location();
		
		updateStocklSku2Location();

		repSkuMap = MiniUiUtil.readRpeatSku(TMALL_SKU_FILE_NAME);
		
//		stockService.setCanUpdateStockStatus("true"); //解除-暂时不让其他地方做更新
	}
	
	//在售
	private void updateCanSellSku2Location()
	{
		TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, APP_SECRET);
		Long pageNo = 1L;
		Long pageSize = 50L;
		StringBuilder sb = new StringBuilder(); //记录sku对应关系
		while(true)
		{
			int errorCnt = 0;
			ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
			req.setPageNo(pageNo);
			req.setPageSize(pageSize);
			req.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
			try
			{
				List<PlatformSku2LocationSku> tmallSkuList = new ArrayList<PlatformSku2LocationSku>();
				ItemsOnsaleGetResponse response = client.execute(req , ACCESS_TOKEN);
				//当前页为最后一页 退出
				Long totalNumber = response.getTotalResults();
				Long totalPageSize = (totalNumber/pageSize)+1; //总页数
				if(pageNo > totalPageSize)break;
				pageNo++;
				List<Item> items = response.getItems();
				for(Item item : items)
				{
					Long tmallNumiid = item.getNumIid();
					String approve_status = item.getApproveStatus();
					if(approve_status.equals("onsale")){
						approve_status = "1";
					}else{
						approve_status = "0";
					}
					List<String> ourSkuList = getOurSkuByTmallNumiid(tmallNumiid+"");
					for(String ourSku : ourSkuList)
					{
						//同步sku到本地,  批量更新
						PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
						pslSku.setOurSku(ourSku);
						pslSku.setPlatformField("tmall_sku");
						pslSku.setPlatformSku(tmallNumiid+"");
						pslSku.setPlatformstatus("tmall_on_sale_status");
						pslSku.setStatus(approve_status);
						pslSku.setType("sh"); 
						tmallSkuList.add(pslSku);
						
						
						sb.append(ourSku);
						sb.append(",");
						sb.append(tmallNumiid+"");
						sb.append("\n");
					}
					
				}
				Map tmallSkuMap = new HashMap();
				tmallSkuMap.put("list", tmallSkuList);
				tmallSkuMap.put("platformField", "tmall_sku");
				tmallSkuMap.put("platformstatus", "tmall_on_sale_status");
				if(tmallSkuList.size() > 0)
				{
					//更新tmall sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(tmallSkuMap);
				}
				
				
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
				log.error(e.getMessage(), e);
				System.out.println("tmall syn sku error " + errorCnt);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				errorCnt++;
			}
		}
		
		MiniUiUtil.writeText2File(TMALL_SKU_FILE_NAME, sb.toString(), "zj");
	}
	
	//仓库中
	private void updateStocklSku2Location()
	{
		TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, APP_SECRET);
		int errorCnt = 0;
		Long pageNo = 1L;
		Long pageSize = 50L;
		StringBuilder sb = new StringBuilder(); //记录sku对应关系
		while(true)
		{
			
			ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
			req.setPageNo(pageNo);
			req.setPageSize(pageSize);
//			req.setStartModified(StringUtils.parseDateTime("2019-03-20 09:53:00"));
//			req.setEndModified(StringUtils.parseDateTime("2019-03-22 10:45:00"));
			req.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
			try
			{
				List<PlatformSku2LocationSku> tmallSkuList = new ArrayList<PlatformSku2LocationSku>();
				ItemsInventoryGetResponse response = client.execute(req , ACCESS_TOKEN);
				//当前页为最后一页 退出
				Long totalNumber = response.getTotalResults();
				
				Long totalPageSize = (totalNumber/pageSize)+1; //总页数
				if(pageNo > totalPageSize)break;
				pageNo++;
				List<Item> items = response.getItems();
//				System.out.println(response.getTotalResults());
//				System.out.println(response.getItems().get(0));
				if(items!= null && items.size() > 0){
				for(Item item : items)
				{
					Long tmallNumiid = item.getNumIid();
					String approve_status = item.getApproveStatus();
					if(approve_status.equals("onsale")){
						approve_status = "1";
					}else{
						approve_status = "0";
					}
					List<String> ourSkuList = getOurSkuByTmallNumiid(tmallNumiid+"");
					
					for(String ourSku : ourSkuList)
					{
						//同步sku到本地,  批量更新
						PlatformSku2LocationSku pslSku = new PlatformSku2LocationSku();
						pslSku.setOurSku(ourSku);
						pslSku.setPlatformField("tmall_sku");
						pslSku.setPlatformSku(tmallNumiid+"");
						pslSku.setPlatformstatus("tmall_on_sale_status");
						pslSku.setStatus(approve_status);
						pslSku.setType("sh"); 
						tmallSkuList.add(pslSku);
						
						sb.append(ourSku);
						sb.append(",");
						sb.append(tmallNumiid+"");
						sb.append("\n");
					}
					
				}
				Map tmallSkuMap = new HashMap();
				tmallSkuMap.put("list", tmallSkuList);
				tmallSkuMap.put("platformField", "tmall_sku");
				tmallSkuMap.put("platformstatus", "tmall_on_sale_status");
				if(tmallSkuList.size() > 0)
				{
					//更新tmall sku到本地
					platformStockUpdateDao.batchUpdatePlatformSku2Location(tmallSkuMap);
				}
			}
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
				//错误10次跳出
				if(errorCnt > 10)
				{
					break;
				}
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				errorCnt++;
				
			}
			
		}
	
		MiniUiUtil.writeText2File(TMALL_SKU_FILE_NAME, sb.toString(), "zj");
		
		
	}
	
	//一个天猫id对应我们多个sku,此方法获取所有的我们的sku
	 private List<String> getOurSkuByTmallNumiid(String numIID)
	 {
		 
		 List<String> ourSkuList = new ArrayList<String>();
		 if(StringUtil.isBlank(numIID)) return ourSkuList;
		 TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, APP_SECRET);
		 ItemSkusGetRequest req = new ItemSkusGetRequest();
		 req.setFields("sku_id,num_iid,properties,quantity,price,outer_id,created,modified");
		 req.setNumIids(numIID);
		 try
		{
			ItemSkusGetResponse response = client.execute(req , ACCESS_TOKEN);
			if(response == null) return ourSkuList;
			List<Sku> list = response.getSkus();
			if(list == null)
			{
				return ourSkuList;
			}
			for(Sku sku : list)
			{
				String status = sku.getStatus();
				if("delete".equals(status)) continue;
				String ourSku = sku.getOuterId();
				if(StringUtil.isNotBlank(ourSku))
				{
					ourSku = ourSku.trim();
					if(ourSku.length() ==13 && ourSku.indexOf("9600000") == -1)
					{
						ourSkuList.add(ourSku);
					}

				}
				
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			 return ourSkuList;
			 
		}
		 return ourSkuList;
	 }

	@Override
	public boolean updateTmallStock(String tmallNumIid, String ourSku, int num,String type)
	{
		boolean flag = false;
		 if(StringUtil.isBlank(tmallNumIid))
		{
			Map<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("sku", ourSku);
			searchMap.put("type", type);
			searchMap.put("field", "tmall_sku");
			tmallNumIid = platformStockUpdateDao.getPlatformSkuByOurSku(searchMap);
		}
		//天猫更新 必须要我们的sku和天猫产品id  天猫产品id对应多个我们的sku  暂时只对接国内
		if(StringUtil.isNotBlank(tmallNumIid) && StringUtil.isNotBlank(ourSku) && "sh".equals(type))
		{
			//记录更新日志
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", tmallNumIid);
			logMap.put("name", "tmall");
			logMap.put("stock", num+"");
			logMap.put("location", type);
			logMap.put("type", "success");
			
			TaobaoClient client = new DefaultTaobaoClient(API_URL, APP_KEY, APP_SECRET);
			ItemQuantityUpdateRequest req = new ItemQuantityUpdateRequest();
			//如果平台sku有填重复的 都更新
			if(repSkuMap.get(ourSku) != null)
			{
				List<String> tmallSkusList = repSkuMap.get(ourSku);
				for(String tmallSku : tmallSkusList)
				{
					req.setNumIid(Long.valueOf(tmallSku));
					req.setOuterId(ourSku);
					req.setQuantity(Long.valueOf(num));
					logMap.put("psku", tmallSku);
					try
					{
						ItemQuantityUpdateResponse response = client.execute(req , ACCESS_TOKEN);
						String errorCode = response.getErrorCode();
						if(errorCode == null)
						{
							flag =  true;
						}
						else
						{
							 logMap.put("type", "error");
							 logMap.put("error",response.getMsg() + response.getSubMsg());
						}
					}
					catch (ApiException e)
					{
						logMap.put("type", "error");
						logMap.put("error","apiexception");
						log.error(e.getMessage(), e);
					}
					//记录日志
					autoSyncDao.addUpdateLog(logMap);
				}
			}
			else
			{
				req.setNumIid(Long.valueOf(tmallNumIid));
				req.setOuterId(ourSku);
				req.setQuantity(Long.valueOf(num));
				try
				{
					ItemQuantityUpdateResponse response = client.execute(req , ACCESS_TOKEN);
					String errorCode = response.getErrorCode();
					if(errorCode == null)
					{
						flag =  true;
					}
					else
					{
						 logMap.put("type", "error");
						 logMap.put("error",response.getMsg() + response.getSubMsg());
					}
				}
				catch (ApiException e)
				{
					logMap.put("type", "error");
					logMap.put("error","apiexception");
					log.error(e.getMessage(), e);
				}
				//记录日志
				autoSyncDao.addUpdateLog(logMap);
			}
			
		}
		return flag;
	}

	public static void main(String[] args)
	{
//		new TmallPlatFormStockUpdateImpl();

	}
	
	
}
 
