/**
 * 
 */
package com.huaixuan.network.biz.service.provider.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.dao.platformstock.HxPlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvidePostOrderLogDao;
import com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsXiYouLogDao;
import com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.PlatformSku2LocationSku;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsXiYouLog;


import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.provider.XiYouPlatFormStockUpdate;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;

/**
 * @author TT
 * 
 */
@Service("XiYouPlatFormStockUpdateImpl")
public class XiYouPlatFormStockUpdateImpl implements XiYouPlatFormStockUpdate {

	private static final String app_key = "shangshang";

	private static final String app_secret = "541731163403b2103f0368cf4620d626";
	private static final String api_url = "https://api.shoplinq.cn";

	@Autowired
	private ProviderGoodsUpdateDao providerGoodsUpdateDao;

	@Autowired
	private AutoSyncDao autoSyncDao;
	
	@Autowired
	private HxPlatformStockUpdateDao hxPlatformStockUpdateDao;

	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	@Autowired
	private ProvidePostOrderLogDao providePostOrderLogDao;
	
	@Autowired
	private ProvideOrderWaybillDetailDao provideOrderWaybillDetailDao;

	@Autowired
	private ProvideOrderDetailDao provideOrderDetailDao;
	
	@Autowired
	private ProvideGoodsImgeDao provideGoodsImgeDao;
	
	@Autowired
	private ProvideUpdateGoodsXiYouLogDao provideUpdateGoodsXiYouLogDao;
	
	@Autowired
	private ProvideGoodsYShangUpdateDao provideGoodsYShangUpdateDao;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

   protected Log log = LogFactory.getLog(this.getClass());
   
   
  // 订单号获取商品订单详情
   public void getOrerDetail(){
	   Map<String,String> map = new HashMap<String, String>();
	   String url = "/shangshang/v0/order/order_detail?";
	   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
	   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		String orderno="orderno=P547396177186366765"; //获取二个商品的详细信息
		String response = HttpRequest.sendPostXiYou(api_url+url, map,orderno);
//		System.out.println(response);
		Map<String,String> detailmap = new HashMap<String, String>();
		Map<String,String> onemap = new HashMap<String, String>();
		try {
			JSONObject jsonObject = new JSONObject(response);
			String jsondata = jsonObject.getString("data");
			String statu = jsonObject.getString("status");
			if(statu.equals("200")){
				JSONObject jsonO= new JSONObject(jsondata);
				String channelSonumber = jsonO.getString("ChannelSonumber");  //订单号
				String[] ch = channelSonumber.split("-");
				String orderId = ch[0];
				String buyName = jsonO.getString("ShippingContactWith"); // 买家名称 
				String phone = jsonO.getString("ShippingPhone");     // 手机号 
				String address = jsonO.getString("ShippingAddress");  //买家地址
				String amount = jsonO.getString("ProductAmount");  //商品售价
				String paidAmount = jsonO.getString("PaidAmount");  // 支付金额
				
				String shipViaId = jsonO.getString("ShipViaId");  //承运方编号shipname
				String shipname = getShipName(shipViaId); //快递公司
				String wayBillCode = jsonO.getString("WayBillCode");  //运单号
				String orderStatus = jsonO.getString("SOStatus");  //订单状态
				String status = getShipStatus(orderStatus);
				String sellPlatform = getPlat(orderId); //获取哪个平台卖出的
				
				detailmap.put("orderId", orderId);
				detailmap.put("orderstatus", status);
				detailmap.put("buyname", buyName);
				detailmap.put("phone", phone);
				detailmap.put("address", address);
				detailmap.put("amount", amount);
				detailmap.put("paidamount", paidAmount);
				detailmap.put("shipname", shipname);
				detailmap.put("waybillcode", wayBillCode); 
				detailmap.put("sellPlatform", sellPlatform);
				detailmap.put("pushPlatform", "xiyou");
				// 
				onemap.put("orderId", orderId);
				List<ProvideOrderWaybillDetail> provideOrderWaybillDetail = provideOrderWaybillDetailDao.selectWayBill(onemap);
				if(provideOrderWaybillDetail!=null && provideOrderWaybillDetail.size()>0){
					provideOrderWaybillDetailDao.updateOrderWaybillDetail(detailmap);
				}else{
					provideOrderWaybillDetailDao.insertWayBillDetail(detailmap);
				}
				
			}
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
   }
   
   
   /**
 * @param orderId
 * @return
 */
private String getPlat(String orderId) {
	String platName = "";
	PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
	platformorderdetails.setIdorder(orderId);
    List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
    if(orderDetais!=null && orderDetais.size()>0){
    	 platName = orderDetais.get(0).getPtype();  	
    }
	return platName;
}


/**
 * @param orderStatus
 * @return
 * 获取订单的状态
 * 订单状态：
  -100 ：订单取消；
  -10：订单缺货；
  0：等待处理；
  10：全额付款；
  15：调拨在途；
  20：审核通过；
  30：配货中；
  40：已出库；
  100：已发货；
  120：已交接；
  200：已送达；
  201：未送达；
  202：用户拒收；
  300：交易成功；
  
  301：交易失败；

 */
private String getShipStatus(String orderStatus) {
	String status ="";
	if(orderStatus.equals("-100")){
		status="订单取消";
	}else if(orderStatus.equals("-10")){
		status="订单缺货";
	}else if(orderStatus.equals("0")){
		status="等待处理";
	}else if(orderStatus.equals("10")){
		status="全额付款";
	}else if(orderStatus.equals("15")){
		status="调拨在途";
	}else if(orderStatus.equals("20")){
		status="审核通过";
	}else if(orderStatus.equals("30")){
		status="配货中";
	}else if(orderStatus.equals("40")){
		status="已出库";
	}else if(orderStatus.equals("100")){
		status="已发货";
	}else if(orderStatus.equals("120")){
		status="已交接";
	}else if(orderStatus.equals("200")){
		status="已送达";
	}else if(orderStatus.equals("201")){
		status="未送达";
	}else if(orderStatus.equals("202")){
		status="用户拒收";
	}else if(orderStatus.equals("300")){
		status="交易成功";
	}else if(orderStatus.equals("301")){
		status="交易失败";
	}
	return status;
}


/**
 * @param shipViaId
 * @return
 * 获取快递公司的名称
 */
private String getShipName(String shipViaId) {
	 Map<String,String> map = new HashMap<String, String>();
	   String url = "/shangshang/v0/order/shipvia?";
	   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
	   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		String orderno="";
		String response = HttpRequest.sendPostXiYou(api_url+url, map,orderno);
//		System.out.println(response);
		String viadName = "";
		try {
			JSONObject json = new JSONObject(response);
			String status  = json.getString("status");
			if(status.equals("200")){
			String data = json.getString("data");
			JSONArray arrayData = new JSONArray(data);
			for(int i=0;i<arrayData.length();i++){
				JSONObject jsonString = new JSONObject(arrayData.getString(i).toString());
				String viad = jsonString.getString("ShipViaId"); //快递代表号码
				String name = jsonString.getString("ShipViaName"); //快递代表公司
				
				if(viad.equals(shipViaId)){
					String[] na = name.split("-");
					 viadName =na[1]; 
					return viadName;
				}
				
			}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	return "NO";
}


//取消订单
   public void cancelOrder(){
	   Map<String,String> map = new HashMap<String, String>();
	   String url = "/shangshang/v0/order/refund_order?";
	   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
	   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		String orderno="orderno=190129-300179784330897"; //获取二个商品的详细信息
		String response = HttpRequest.sendPostXiYou(api_url+url, map,orderno);
//		System.out.println(response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			String status = jsonObject.getString("status");
			String message = jsonObject.getString("message");
			if(status.equals("200")){
				Map<String,String> detailmap = new HashMap<String, String>();
				detailmap.put("cancelstatus", "订单取消-"+message);
				detailmap.put("orderId", "P547396177186366765");
				provideOrderWaybillDetailDao.updateOrderWaybillDetail(detailmap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
   }
  
   
 
   
   /*
    * 推送订单给供应商,推送成功后，减商品表provide_goods_xiyou的库存,减hx_stock_update下单平台的订单数为0，减now_stock和order_stock字段,
    */
	@Override
	public void pushOrderToPlat() {
		String times = String.valueOf(System.currentTimeMillis());
		String timestamps = times.substring(0, times.length() - 3);
		   Map<String,String> map = new HashMap<String, String>();
		   String url = "/shangshang/v0/order/add_order?";
		   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
		   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//		    System.out.println(sign);
//		    System.out.println(api_url+url);
			map.put("app_key", app_key);
			map.put("timestamp", timestamp);
			map.put("sign", sign);
				   
			/*  Map<String,Object> mapJson = new HashMap<String, Object>();
			  Map<String,String> order = new HashMap<String, String>();
			  Map<String,Object> products = new HashMap<String, Object>();
			  String trade_id = "01014443356009"; // 订单编号 0012343356789   112312343356009  01014443356009
			  String username = "测试订单";            // 用户名称
			  String phone ="12345678908";         // 电话
			  String province ="北京";         // 省
			  
              String city = "北京";            // 市
			  
			  String region = "通州";       // 区
			  String location= "万达广场";   // 详细地址
			  String postcode= "100010";  // 邮编（可为空）
			  String pay_price = "2000"; // 实付金额
			  String total_price = "2000";   // 订单总金额
			  
			  String create_time = timestamps;  // 订单创建时间
			  String pay_time = timestamps; // 支付时间
			  String buyer_message = "测试";  // 买家留言 （可为空）
			  String buyer_memo ="测试";     // 买家留言 （可为空）
			  String seller_memo ="测试";    // 卖家备注（可为空）
			  
			  String invoice_name ="个人发票";  // 发票名称（可为空）
			  String invoice_type = "123333344444555666";// 发票税号（可为空）
			  order.put("trade_id",trade_id);
			  order.put("username",username);
			  order.put("phone",phone);
			  order.put("province",province);
			  order.put("city",city);
			  
			  order.put("region",region);
			  order.put("location",location);
			  order.put("postcode",postcode);
			  order.put("pay_price",pay_price);
			  order.put("total_price",total_price);
			  
			  order.put("create_time",create_time);
			  order.put("pay_time",pay_time);
			  order.put("buyer_message",buyer_message);
			  order.put("buyer_memo",buyer_memo);
			  order.put("seller_memo",seller_memo);
			  
			  order.put("invoice_name",invoice_name);
			  order.put("invoice_type",invoice_type);
			  
			  mapJson.put("order", order);
			  
			  
			//  商品
			  long ProdId = 1351889;
			  String item_id ="1111351889822";
			  String title = "【自营】BURBERRY巴宝莉蓝色/黑色PVC/牛皮材质格纹样式两面可用男士腰带, MAN FW2017 3996179 NAVY/BLACK BELT TU";
			  double sell_price = 2240.00;
			  String tax = "0";
			  
			  String qty = "1";
			  String freight = "50";
			  
			  List productList = new ArrayList<>();
			  products.put("ProdId", ProdId);
			  products.put("item_id", item_id);
			  products.put("title", title);
			  products.put("sell_price", sell_price);
			  products.put("tax", tax);
			  
			  products.put("qty", qty);
			  products.put("ProdId", ProdId);
			  products.put("freight", freight);
			  productList.add(products);
			  net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(productList);
			  mapJson.put("products", json);
			  
			  
			  String response = HttpRequest.sendPostOrderXiYou(api_url+url, map, mapJson);
			  
			  
			  
			  System.out.println(response);
			  
			  
			  
			  Map<String,String> maplog = new HashMap<String, String>();
			 List<ProvidePostOrderLog> orderLog = providePostOrderLogDao.selectPushOrderLog(maplog);
			 */
//			int i = providePostOrderLogDao.insertPushOrderLog(maplog);
		
			  
			  
			  
			  
			  
			  
			  
			  
		
		
		
		
//		String startDatetime =df.format(new Date(new Date().getTime() - 5*60*1000)); //5分钟前
		//获取5分钟内的订单详情
		String insertTime =df.format(new Date(new Date().getTime() - 6*24*60*60*1000));
//		System.out.println(insertTime);
		Map<String,String> searchMap = new HashMap<>();
		PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
		platformorderdetails.setInsertTime(insertTime);
	    List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
	    Map<String,String> mapp = new HashMap<String, String>();
	    //在此时间段内有订单详情
	    if(orderDetais!=null && orderDetais.size()>0){
	    	 //先循环出，是供应商的商品，将商品详情放入orderIdProList结合
		    List<PlatFormOrderDetails> orderIdProList = getAllProvideOrderDetais(orderDetais); 
//		    System.out.println(orderIdProList.size()+"==============");
		     //如果orderIdProList有数据，就循环订单详情
		    if(orderIdProList!=null && orderIdProList.size()>0){
		     for(int i=0;i<orderIdProList.size();i++){
		    	  String trade_id = orderIdProList.get(i).getIdorder(); // 订单编号
//		    	  String username = orderIdProList.get(i).getName();    // 用户名称
		    	  String username = "订单测试";
		    	  String phone = orderIdProList.get(i).getMobile();    // 电话
		    	
				  String province = orderIdProList.get(i).getProvince();  // 省
//				  System.out.println(province);
				  String city = orderIdProList.get(i).getCity();            // 市
//				  System.out.println(city);
				  String region = orderIdProList.get(i).getDistrict();       // 区
//				  System.out.println(region);
				  String location= orderIdProList.get(i).getStreetAddress();   // 详细地址
//				  System.out.println(location);
				  String postcode= "";  // 邮编（可为空）
				  String pay_price = orderIdProList.get(i).getDiscountPrice(); // 实付金额
//				  System.out.println(pay_price);
				  String total_price = orderIdProList.get(i).getTotalPrice();   // 订单总金额
//				  System.out.println(total_price);

				  String create_time = "";
				  String pay_time = "";
				  try {
    				   create_time = orderIdProList.get(i).getPalcedTime();  // 订单创建时间
    				   long ct = df.parse(create_time).getTime();
    				   create_time = String.valueOf(ct);
    				   create_time = create_time.substring(0, create_time.length() - 3);
    				   
    				   pay_time = orderIdProList.get(i).getInsertTime(); // 支付时间 ==========================先用插入时间来测试用
 					   long pt =df.parse(pay_time).getTime();
 					   pay_time = String.valueOf(pt);
 					   pay_time = pay_time.substring(0, pay_time.length() - 3);
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}
				  
				  
				  //获取是我们哪个平台卖出的商品
				  String platName = orderIdProList.get(i).getPtype();
//				  System.out.println(platName);
				  String buyer_message = "测试";  // 买家留言 （可为空）
				  String buyer_memo ="测试";     // 买家留言 （可为空）
				  String seller_memo ="测试";    // 卖家备注（可为空）
				  
				  String invoice_name ="个人发票";  // 发票名称（可为空）
				  String invoice_type = "123333344444555666";// 发票税号（可为空）
				  Map<String,Object> mapJson = new HashMap<String, Object>();
				  Map<String,String> order = new HashMap<String, String>();
				  
				  order.put("trade_id",trade_id);
				  order.put("username",username);
				  order.put("phone",phone);
				  order.put("province",province);
				  order.put("city",city);
				  
				  order.put("region",region);
				  order.put("location",location);
				  order.put("postcode",postcode);
				  order.put("pay_price",pay_price);
				  order.put("total_price",total_price);
				  
				  order.put("create_time",create_time);
				  order.put("pay_time",pay_time);
				  order.put("buyer_message",buyer_message);
				  order.put("buyer_memo",buyer_memo);
				  order.put("seller_memo",seller_memo);
				  
				  order.put("invoice_name",invoice_name);
				  order.put("invoice_type",invoice_type);
				  
				  mapJson.put("order", order);		
				  //查询出这个订单号有几个订单详情
		    	  platformorderdetails.setIdorder(trade_id);
		    	  List<PlatFormOrderDetails> orderDetaisTwo = platformOrderDetailsDao.selectList(platformorderdetails);
		    	  
		    
		    	  List<Map<String,Object>> productList = new ArrayList<>();
		    	  List<String> orderidList = new ArrayList<String>();
		    	  //循环有几种商品放进productList集合（即一个订单号，几个商品详情）
		    	  for(int k=0;k<orderDetaisTwo.size();k++){
		    		
		    		//建一个发送订单成功的表格，进来前先查询，是否已经发送过，如果发送过不在发送(用订单号和sku作为条件)
		    		  Map<String,Object> products= new HashMap<String, Object>();
		    		  String sku = orderDetaisTwo.get(k).getMerchantSkuId(); //我们自己的sku
		    		  String proid = sku.substring(3, 10);

		    		  long prodId= Long.parseLong(proid);
		    		  searchMap.put("prodId", proid); //proid
		    		  searchMap.put("orderId", trade_id);//trade_id
		    		  List<ProvidePostOrderLog> providePostOrderLog = providerGoodsUpdateDao.getProviderLogConditionWithPage(searchMap);
		    		  //如果存在这个log，表示已经推送过了
		    		  if(providePostOrderLog!=null && providePostOrderLog.size()>0){
		    			  continue;
		    		  }
		    		  
		    		   ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
		   		      pg.setProdId(prodId);
		   		      List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(pg);
		   		      if(pgxy == null || pgxy.size()==0){
		   		    	continue;
		   		      }
		    		 //  商品
					  long ProdId = prodId; 
					  String item_id = sku;
					  String title = orderDetaisTwo.get(k).getProductname();
					  double sell_price = pgxy.get(0).getPrice();  // 商品售价     
					  String tax = "0"; // 税费      
					  
					  String qty = orderDetaisTwo.get(k).getQuantity();                 // 购买数量
					  String freight = orderDetaisTwo.get(k).getFreight();         // 运费金额
					  
					  products.put("ProdId", ProdId);
					  products.put("item_id", item_id);
					  products.put("title", title);
					  products.put("sell_price", sell_price); // 商品售价
					  products.put("tax", tax);  // 税费 
					  
					  products.put("qty", qty); // 购买数量	
					  products.put("freight", freight); // 运费金额
					  productList.add(products);
		    	 }
		    	  
		    	  if(productList == null || productList.size() <=0 ){
		    		  continue;
		    	  }
				   net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(productList);
		    	   mapJson.put("products", json);
		    	   
		    	   Map<String, String> logMap = new HashMap<String, String>();
		    	   String response = HttpRequest.sendPostOrderXiYou(api_url+url, map, mapJson);
//		    	  System.out.println(response);
		    	   JSONObject jsonObj;
				try {
					jsonObj = new JSONObject(response);
					String status = jsonObj.getString("status");
					String message = jsonObj.getString("message");
					logMap.put("status", status);
					logMap.put("message", message);
					
					String jsodata = jsonObj.getString("data");
					if (status.equals("200")) {
						logMap.put("status", status);
						logMap.put("message", message);
						JSONObject dataObj = new JSONObject(jsodata);
						String requestData = dataObj.getString("request_data");
						JSONObject requestDataObj = new JSONObject(requestData);
						//获取到发送的商品集合
						String detailCollection = requestDataObj.getString("DetailCollection");
						JSONArray orderArray = new JSONArray(detailCollection);
						for(int k=0;k<orderArray.length();k++){
							JSONObject ob = new JSONObject(orderArray.get(k).toString());
							String orderId = ob.getString("Tid");
							String proId = ob.getString("SkuID");
							String ssProid = ob.getString("OuterItemID");
							String qty = ob.getString("Num");
							String ptype = platName;
							String pushPlatform = "xiYou";
							
							logMap.put("orderId", orderId);
							logMap.put("prodId", proId);
							logMap.put("sSprodid", ssProid);
							logMap.put("qty", qty);
							logMap.put("ptype", ptype);
							logMap.put("pushPlatform", pushPlatform);
							providePostOrderLogDao.insertPushOrderLog(logMap);
							
							//修改provide_goods_xiyou表的库存
							Map<String,String> upMap = new HashMap<String, String>();
							upMap.put("prodId", proId);
							upMap.put("sSprodid", ssProid);
							List<ProvideGoodsXiYou> provideGoods = providerGoodsUpdateDao.getProviderListByConditionWithPage(upMap);
							if(provideGoods!=null && provideGoods.size()>0){

								int num = provideGoods.get(0).getStock();
//								int qt = num - Integer.parseInt(qty);
								
								int pnum = Integer.parseInt(getXiYouStock(proId)); //此时在查询，平台上 的库存，查看是否已经改变
								if(pnum != num){ //代表此库存在银泰那里更改的库存，还未更新到本地，不管，只改自己的，改产品表和更新库存的表
									upMap.put("stock", pnum+"");
									int t = providerGoodsUpdateDao.updateGoodsXiYouMap(upMap);
									//改hx_stock_update表的库存，和平台订单数
									if(t>0){
										mapp.put("sku", ssProid);
										mapp.put("type", "sh");
										 List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(mapp);
										 for (StockUpdate nufl : sulist){
					                            //判断是哪个平台的的，以便减去对应的平台的库存 
												int qtyy = Integer.parseInt(qty);
												int orderNum = nufl.getOrderStockNum();
												     orderNum = orderNum-qtyy ;
												if (orderNum < 0)
													orderNum = 0;
												nufl.setOrderStockNum(orderNum);
	                                           
												if(platName.equals("siku") || platName.equals("sikunew")){
													int sikuOrderStock = sulist.get(0).getSikuOrderStock();
													sikuOrderStock = sikuOrderStock-qtyy ;
													if (sikuOrderStock < 0)
														sikuOrderStock = 0;
													nufl.setSikuOrderStock(sikuOrderStock);
													
												} else if (platName.equals("kaola")) {
													int kaolaOrderStock = nufl.getKaolaOrderStock();
													kaolaOrderStock = kaolaOrderStock-qtyy;
													if (kaolaOrderStock < 0)
														kaolaOrderStock = 0;
													nufl.setKaolaOrderStock(kaolaOrderStock);
												}else if (platName.equals("tmall")) {
													int tmallOrderStock = nufl.getTmallOrderStock();
													tmallOrderStock = tmallOrderStock-qtyy;
													if (tmallOrderStock < 0)
														tmallOrderStock = 0;
													nufl.setTmallOrderStock(tmallOrderStock);
												} else if (platName.equals("fql")) {
													int fqlOrderStock = nufl.getFqlOrderStock();
													fqlOrderStock = fqlOrderStock-qtyy;
													if (fqlOrderStock < 0)
														fqlOrderStock = 0;
													nufl.setFqlOrderStock(fqlOrderStock);
												}else if (platName.equals("pdd") || platName.equals("pddnew")) {
													int pddOrderStock = nufl.getPddOrderStock();
													pddOrderStock = pddOrderStock-qtyy ;
													if (pddOrderStock < 0)
														pddOrderStock = 0;
													nufl.setPddOrderStock(pddOrderStock);
												} else if (platName.equals("Mlh")) {
													int MlhOrderStock = nufl.getMlhOrderStock();
													MlhOrderStock = MlhOrderStock-qtyy;
													if (MlhOrderStock < 0)
														MlhOrderStock = 0;
													nufl.setMlhOrderStock(MlhOrderStock);
												} else if (platName.equals("xhs")) {
													int xhsOrderStock = nufl.getXhsOrderStock();
													xhsOrderStock=xhsOrderStock-qtyy;
													if (xhsOrderStock < 0)
														xhsOrderStock = 0;
													nufl.setXhsOrderStock(xhsOrderStock);
												} else if (platName.equals("Ofashion")) {
													int ofashionOrderStock = nufl.getOfashionOrderStock();
													ofashionOrderStock = ofashionOrderStock-qtyy;
													if (ofashionOrderStock < 0)
														ofashionOrderStock = 0;
													nufl.setOfashionOrderStock(ofashionOrderStock);
												} else if (platName.equals("weimob")) {
													int weiMobOrderStock = nufl.getWeiMobOrderStock();
													weiMobOrderStock = weiMobOrderStock-qtyy;
													if (weiMobOrderStock < 0)
														weiMobOrderStock = 0;

													nufl.setWeiMobOrderStock(weiMobOrderStock);

												} else if (platName.equals("yinTai")) {
													int yinTaiOrderStock = nufl.getYinTaiOrderStock();
													yinTaiOrderStock = yinTaiOrderStock-qtyy;
													if (yinTaiOrderStock < 0)
														yinTaiOrderStock = 0;
													nufl.setYinTaiOrderStock(yinTaiOrderStock);
												}else if (platName.equals("shepin")) {
													int shepinOrderStock = nufl.getShepinOrderStock();
													shepinOrderStock = shepinOrderStock-qtyy;
													if (shepinOrderStock < 0)
														shepinOrderStock = 0;
													nufl.setShepinOrderStock(shepinOrderStock);
												}else if (platName.equals("OfashionMicheng")) {
													int ofashionMcOrderStock = nufl.getOfashionMcOrderStock();
													ofashionMcOrderStock =ofashionMcOrderStock-qtyy;
													if (ofashionMcOrderStock < 0)
														ofashionMcOrderStock = 0;
													nufl.setOfashionMcOrderStock(ofashionMcOrderStock);
												}else if (platName.equals("suning")) {
													int suningOrderStock = nufl.getSuningOrderStock();
													suningOrderStock = suningOrderStock-qtyy;
													if (suningOrderStock < 0)
														suningOrderStock = 0;
													nufl.setSuningOrderStock(suningOrderStock);
												}
												
												nufl.setNowStockNum(pnum);
												nufl.setSku(ssProid);
												nufl.setType("sh");
												platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
						        		  }
										
									}
								}else{
									upMap.put("stock", pnum-Integer.parseInt(qty)+""); //使用从平台得到的库存
									int t = providerGoodsUpdateDao.updateGoodsXiYouMap(upMap);
									if(t>0){
										mapp.put("sku", ssProid);
										mapp.put("type", "sh");
										 List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(mapp);
										 for (StockUpdate nufl : sulist){
					                            //判断是哪个平台的的，以便减去对应的平台的库存 
												int qtyy = Integer.parseInt(qty);
												int orderNum = nufl.getOrderStockNum();
												     orderNum = orderNum-qtyy ;
												if (orderNum < 0)
													orderNum = 0;
												nufl.setOrderStockNum(orderNum);
	                                           
												if(platName.equals("siku") || platName.equals("sikunew")){
													int sikuOrderStock = sulist.get(0).getSikuOrderStock();
													sikuOrderStock = sikuOrderStock-qtyy ;
													if (sikuOrderStock < 0)
														sikuOrderStock = 0;
													nufl.setSikuOrderStock(sikuOrderStock);
													
												} else if (platName.equals("kaola")) {
													int kaolaOrderStock = nufl.getKaolaOrderStock();
													kaolaOrderStock = kaolaOrderStock-qtyy;
													if (kaolaOrderStock < 0)
														kaolaOrderStock = 0;
													nufl.setKaolaOrderStock(kaolaOrderStock);
												}else if (platName.equals("tmall")) {
													int tmallOrderStock = nufl.getTmallOrderStock();
													tmallOrderStock = tmallOrderStock-qtyy;
													if (tmallOrderStock < 0)
														tmallOrderStock = 0;
													nufl.setTmallOrderStock(tmallOrderStock);
												} else if (platName.equals("fql")) {
													int fqlOrderStock = nufl.getFqlOrderStock();
													fqlOrderStock = fqlOrderStock-qtyy;
													if (fqlOrderStock < 0)
														fqlOrderStock = 0;
													nufl.setFqlOrderStock(fqlOrderStock);
												}else if (platName.equals("pdd") || platName.equals("pddnew")) {
													int pddOrderStock = nufl.getPddOrderStock();
													pddOrderStock = pddOrderStock-qtyy ;
													if (pddOrderStock < 0)
														pddOrderStock = 0;
													nufl.setPddOrderStock(pddOrderStock);
												} else if (platName.equals("Mlh")) {
													int MlhOrderStock = nufl.getMlhOrderStock();
													MlhOrderStock = MlhOrderStock-qtyy;
													if (MlhOrderStock < 0)
														MlhOrderStock = 0;
													nufl.setMlhOrderStock(MlhOrderStock);
												} else if (platName.equals("xhs")) {
													int xhsOrderStock = nufl.getXhsOrderStock();
													xhsOrderStock=xhsOrderStock-qtyy;
													if (xhsOrderStock < 0)
														xhsOrderStock = 0;
													nufl.setXhsOrderStock(xhsOrderStock);
												} else if (platName.equals("Ofashion")) {
													int ofashionOrderStock = nufl.getOfashionOrderStock();
													ofashionOrderStock = ofashionOrderStock-qtyy;
													if (ofashionOrderStock < 0)
														ofashionOrderStock = 0;
													nufl.setOfashionOrderStock(ofashionOrderStock);
												} else if (platName.equals("weimob")) {
													int weiMobOrderStock = nufl.getWeiMobOrderStock();
													weiMobOrderStock = weiMobOrderStock-qtyy;
													if (weiMobOrderStock < 0)
														weiMobOrderStock = 0;

													nufl.setWeiMobOrderStock(weiMobOrderStock);

												} else if (platName.equals("yinTai")) {
													int yinTaiOrderStock = nufl.getYinTaiOrderStock();
													yinTaiOrderStock = yinTaiOrderStock-qtyy;
													if (yinTaiOrderStock < 0)
														yinTaiOrderStock = 0;
													nufl.setYinTaiOrderStock(yinTaiOrderStock);
												}else if (platName.equals("shepin")) {
													int shepinOrderStock = nufl.getShepinOrderStock();
													shepinOrderStock = shepinOrderStock-qtyy;
													if (shepinOrderStock < 0)
														shepinOrderStock = 0;
													nufl.setShepinOrderStock(shepinOrderStock);
												}else if (platName.equals("OfashionMicheng")) {
													int ofashionMcOrderStock = nufl.getOfashionMcOrderStock();
													ofashionMcOrderStock =ofashionMcOrderStock-qtyy;
													if (ofashionMcOrderStock < 0)
														ofashionMcOrderStock = 0;
													nufl.setOfashionMcOrderStock(ofashionMcOrderStock);
												}else if (platName.equals("suning")) {
													int suningOrderStock = nufl.getSuningOrderStock();
													suningOrderStock = suningOrderStock-qtyy;
													if (suningOrderStock < 0)
														suningOrderStock = 0;
													nufl.setSuningOrderStock(suningOrderStock);
												}
												
												nufl.setNowStockNum(pnum-Integer.parseInt(qty));
												nufl.setSku(ssProid);
												nufl.setType("sh");
												platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
						        		  }
										
									}
								}
							}
							
						}

					}else{
						logMap.put("status", status);
						logMap.put("message", message);
						JSONObject dataObj = new JSONObject(jsodata);
						String requestData = dataObj.getString("request_data");
						JSONObject requestDataObj = new JSONObject(requestData);
						//获取到发送的商品集合
						String detailCollection = requestDataObj.getString("DetailCollection");
						JSONArray orderArray = new JSONArray(detailCollection);
						for(int k=0;k<orderArray.length();k++){
							JSONObject ob = new JSONObject(orderArray.get(k).toString());
							String orderId = ob.getString("Tid");
							String proId = ob.getString("SkuID");
							String ssProid = ob.getString("OuterItemID");
							String qty = ob.getString("Num");
							String ptype = platName;
							String pushPlatform = "xiYou";
							
							logMap.put("orderId", orderId);
							logMap.put("sSprodid", ssProid);
							logMap.put("qty", qty);
							logMap.put("ptype", ptype);
							logMap.put("pushPlatform", pushPlatform);
							providePostOrderLogDao.insertPushOrderLog(logMap);
						}
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
		     }
		    
		    }
		     //然后通过订单号和sku确定这个订单详情，查询出商品的数量，推送到银泰西有，成功后，循环这个数量，减库存
		     //在减proid_goods 的商品数量和hx_stock_update的数量
	    }

	}
   
   
   
  
   

	

	/**
 * @param orderDetais
 * @return
 *  //得到时间段内是哪家供货商
 */
private List<PlatFormOrderDetails> getAllProvideOrderDetais(List<PlatFormOrderDetails> orderDetais) {
	List<PlatFormOrderDetails> lis = new ArrayList<PlatFormOrderDetails>();
	Map<String,String> searchMap = new HashMap<String, String>();
	for(int i=0;i<orderDetais.size();i++){
		 String sku = orderDetais.get(i).getMerchantSkuId(); //我们自己的sku
		 if(StringUtils.isBlank(sku)){
			 continue;
		 }
		if(sku.startsWith("111")){
		   String proid = sku.substring(3, 10);
		   searchMap.put("sku", sku);
		   searchMap.put("xiyouProdId", proid);
		   List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap); //查询供应商表是否有这个商品，并确定是哪个商家的
		   //如果能查询出商品，则是供应商的商品，因为xiyouProdId可判断出是西有银泰的
		   if(list !=null && list.size()>0){
			   //把订单id放进集合
			   lis.add(orderDetais.get(i));
		   }
	}
	}
	return lis;
}





	/*
	 *一、 获取西有在库的商品id列表
	 *   将新增的商品导入provide_goods_xiyou和hx_stock_updte表
	 */
	@Override
	@Transactional
	public void getProducts() {  //377   383
		int errorCnt = 1;
		String path = "e:/upload/provide/xiyou/";
		System.out.println(df.format(new Date()) + " sync getProducts  start");
		long startTime = System.currentTimeMillis();
		
		String url = "/shangshang/v0/product/get_products?";
		 
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
		int page = 1;   //共179页 、、8905条数据,测试数据8912;第二次获取：9260条数据，测试数据9203条，
		int size = 50;
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();
		Map<String,String> mapp = new HashMap<String, String>();
		List<Long> proidAll = new ArrayList<Long>();
		//先获取已有的商品的provid，放入list集合
		List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(new ProvideGoodsXiYou());
		if(pgxy.size()>0){
		for(ProvideGoodsXiYou p:pgxy){
			proidAll.add(p.getProdId());
		}
		}
//		int conut =10;
    while(true){
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		params="page"+"="+page+"&"+"size"+"="+size;
		
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//		System.out.println(response);
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			JSONArray dataArray = jsonObj.getJSONArray("data");
			List<ProvideGoodsXiYou> pgList = new ArrayList<ProvideGoodsXiYou>();
			List<ProvideGoodsImge> pgiList = new ArrayList<ProvideGoodsImge>();
			if (status.equals("200")) {
				if(dataArray != null && dataArray.length()>0){
//					System.out.println(dataArray.length());
					for(int i=0;i<dataArray.length();i++){
						ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
						JSONObject dataObject = new JSONObject(dataArray.get(i).toString());
						String ProdId = dataObject.getString("ProdId");
						Long prodId = Long.parseLong(ProdId); //银泰商品唯一标识
                     //如果存放prodid的集合中，有刚获取的prodid,这个prodid跳过，继续下一个商品；如果没有将这个商品放入商品表
						if(proidAll.size()>=0 && proidAll.contains(prodId)){
							continue;
						}
						String brandName = dataObject.getString("BrandName"); //品牌
						String prodName = dataObject.getString("ProdName"); 
						
//						int taxRate = Integer.parseInt(dataObject.getString("TaxRate"));
						Double taxRate = Double.parseDouble(dataObject.getString("TaxRate"));
						int providerId = Integer.parseInt(dataObject.getString("ProviderId")); 
						
						String providerName = dataObject.getString("ProviderName");
						
						Double price = Double.parseDouble(dataObject.getString("Price"));
						Double cost = Double.parseDouble(dataObject.getString("Cost"));
						Double marketPrice = Double.parseDouble(dataObject.getString("MarketPrice"));
						
						String Size= dataObject.getString("Size");
						
						String color= dataObject.getString("Color");
						String provideSkuNumbr= dataObject.getString("ProviderSkuNumbr");
						
						String detailDescription= dataObject.getString("DetailDescription");
						
						//获取当前ProdId的库存
						String stock = getXiYouStock(ProdId);
						
					   //形成尚上的唯一标识和银泰唯一标识一一对应
						String ssProdid = getSsProdId(ProdId);
//						System.out.println(ssProdid);
						
						String imageAddresses= dataObject.getString("ImageAddresses");
						String imageAddressess ="";
						if(!imageAddresses.equals("null")){
							JSONArray imageArray = new JSONArray(imageAddresses);
//							JSONObject dataAssJSON = new JSONObject(imageArray.get(0).toString());
							if(imageArray!=null && imageArray.length()>0){
								for(int t=0;t<imageArray.length();t++){
									JSONObject dataAssJSONT = new JSONObject(imageArray.get(t).toString());
									String type = dataAssJSONT.getString("ImageType");
									String imagepic = dataAssJSONT.getString("ImageName");
									ProvideGoodsImge  pgim = new ProvideGoodsImge();
									pgim.setProdId(ProdId);
									pgim.setSsProid(ssProdid);
									pgim.setType(type);
									
//									if(type.equals("1")){
//										imageAddressess = "provide/xiyou/"+ssProdid+"_"+t+imagepic.substring(imagepic.lastIndexOf("."));
//										pgim.setImagePic(imageAddressess);
//										pgiList.add(pgim);
//										downloadPicture(imagepic,path,ssProdid,type,t);
//									}else{
									if(type.equals("1")){
//										if(imagepic.endsWith(".JPG") || imagepic.endsWith(".jpg") || imagepic.endsWith(".png")){
											String pic = "provide/xiyou/"+ssProdid+"_"+t+".jpg";
											imageAddressess = pic;
											downloadPicture(imagepic,path,ssProdid,type,t);
//										}
										
									}
//										pgim.setImagePic(pic);
										pgim.setXiyouImage(imagepic);
										pgiList.add(pgim);
//										downloadPicture(imagepic,path,ssProdid,type,t);
//									}
									
								}
							}
						}
//						System.out.println(imageAddressess);
						Double ourSalePrice = (double) Math.round(cost/0.65);
						
						pg.setProdId(prodId);
						pg.setSsProdid(ssProdid);
						pg.setBrandName(brandName);
						pg.setProdName(prodName);
						pg.setTaxRate(taxRate);
						pg.setProviderId(providerId);
						pg.setProviderName(providerName);
						pg.setPrice(price);
						pg.setCost(cost);
						pg.setMarketPrice(marketPrice);
						pg.setOurSalePrice(ourSalePrice);
						pg.setSize(Size);
						pg.setColor(color);
						pg.setProvideSkuNumbr(provideSkuNumbr);
						pg.setDetailDescription(detailDescription);
						pg.setImageAddresses(imageAddressess);
						pg.setStock(Integer.parseInt(stock));
						pgList.add(pg);
					}					
//					System.out.println("000000000000000");
					if(pgList.size()>0 && pgiList!=null && pgiList.size()>0){
//						conut = conut +1;
						//把新增的商品插入provide_goods_xiyou表
						providerGoodsUpdateDao.insertXiYouProducts(pgList);
						provideGoodsImgeDao.insertGoodsImge(pgiList);
						//将新增的商品插入hx_stock_update
						//循环新插入到provide_goods_xiyou的数据，拿出ssProdid查询hx_stock_update没有的这个的，新插入一条
						for(int i=0;i<pgList.size();i++){
							String ssProdid = pgList.get(i).getSsProdid();
							Long prodid = pgList.get(i).getProdId();
							mapp.put("xiyouProdId", prodid+"");
			        		  List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(mapp);
			        		  if(sulist==null || sulist.size()==0){
			        			  int num = pgList.get(i).getStock();
			        			  StockUpdate su = new StockUpdate();
			        				su.setSku(ssProdid);
			        				su.setNowStockNum(num);
			        				su.setLastUpdateStockNum(num);
			        				su.setXiyouProdId(prodid+"");
			        				su.setType("sh");
			        				platformStockUpdateDao.insertStockUpdate(su);
			        		  }
						}
						
					}
					
				}else{
					System.out.println(dataArray+"=====是null");
					System.out.println(df.format(new Date()) + " sync getProducts  end");
					long endTime = System.currentTimeMillis();
					long a = endTime-startTime;
					long min = a/1000/60;
					System.out.println(df.format(new Date()) + " sync getProducts  total min:" + min + "min");
					break;
				}
				
			}else{
				break;
			}
			page++;
			System.out.println("第=="+page+"==页");
//			conut = conut-1;
//			if(conut<=0){
//				break;
//			}
			} catch (JSONException e) {

					log.error(e.getMessage());
					if(errorCnt++ > 5)
					{
						e.printStackTrace();
						System.out.println("xiyou sysc GetProducts error over 5 cnt JSONException");
						break;
					}
		   } catch (Exception e) {
					log.error(e.getMessage());
					if(errorCnt++ > 5)
					{
						e.printStackTrace();
						System.out.println("xiyou sysc GetProducts error over 5 cnt Exception");
						break;
					}
		}
	}
	}   
	
   
	//下载图片到本地
	 private void downloadPicture(String urlList,String path,String ssProid,String type,int t) {
//		 if(type.equals("1")){
//			 path = path + ssProid+"_"+t+urlList.substring(urlList.lastIndexOf("."));
//		 }else{
			 path = path + ssProid +"_"+t+".jpg";
//		 }
	        URL url = null;
	        try {
	            url = new URL(urlList);
	            DataInputStream dataInputStream = new DataInputStream(url.openStream());
	 
	            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	 
	            byte[] buffer = new byte[1024];
	            int length;
	 
	            while ((length = dataInputStream.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            fileOutputStream.write(output.toByteArray());
	            dataInputStream.close();
	            fileOutputStream.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	            log.error(e.getMessage());
	        } catch (IOException e) {
	            e.printStackTrace();
	            log.error(e.getMessage());
	        }
	    }
	
	/*
	 *  获取时间段内库存变化的商品库存
	 *  并更新provide_goods_xiyou的stock，和hx_stock_update的库存
	 */
	@Override
	public void getChangeStock() {
		
//		String startDatetime =df.format(new Date(new Date().getTime() - 24*24*60*60*1000-6*24*60*60*1000));
//		String startDatetime =df.format(new Date(new Date().getTime() - 24*60*60*1000)); //开始时间
		String startDatetime =df.format(new Date(new Date().getTime() - 5*60*1000)); //5分鐘內的庫存變化
		String endDatetime = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		
		String url = "/shangshang/v0/product/change_stock?";
		 
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
//		System.out.println(timestamp);
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();	
		Map<String,String> maps =new HashMap<String, String>();
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		params="start_datetime="+startDatetime+"&end_datetime="+endDatetime; 
//		System.out.println(params);
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//		System.out.println(response);
		try {
			//{"status":200,"message":"ok","data":[]}
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			String datas = jsonObj.getString("data");
//			System.out.println(datas);
			if(datas.equals("[]")){
				return;
			}
			JSONObject jsonObject = jsonObj.getJSONObject("data");
//           JSONArray jsonObject = jsonObj.getJSONArray("data");
//           JSONObject dataObject = new JSONObject(dataArray.get(i).toString());
			if (status.equals("200")) {
				if(jsonObject != null && jsonObject.length()>0){
										
					List<Long> proidList = getAllProid();
					if(proidList!=null && proidList.size()>0){
						Iterator<String> it = jsonObject.keys(); 
						List<StockUpdate> list = new ArrayList<StockUpdate>();
						Map updateMap = new HashMap();
						while(it.hasNext()){
						// 获得key
						String key = it.next(); //proid
						String value = jsonObject.getString(key); //库存
						
						Long proid = Long.parseLong(key);
						int numChang = Integer.parseInt(value); 
						ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
						pg.setProdId(proid);
						//如果原来有这个商品，查询这个商品的库存，不一样，就改变这个商品的库存
						if(proidList.contains(proid)){
							pg = getProidNum(pg); 
							int prenum = pg.getStock();//原来的库存
							String ssProid = pg.getSsProdid();
							if(numChang != prenum){
								maps.put("prodId", proid+"");
								maps.put("stock", numChang+"");
								maps.put("timeChangeUpdateStock", "yes"); //代表是时间段内库存变化，插入此时的时间
								Integer i = providerGoodsUpdateDao.updateGoodsXiYouMap(maps); //更新provide_goods_xiyou表的库存
								//如果更新provide_goods_xiyou成功,更新hx_stock_update的库存
								if(i>0){
									StockUpdate su = new StockUpdate();
									su.setSku(ssProid);
									su.setNowStockNum(numChang);
									su.setType("sh");
									list.add(su);
								}
							}
						}
						}		
						updateMap.put("list", list);
						if(list!=null && list.size()>0){
							platformStockUpdateDao.updateBatchNowStock(updateMap);
						}
					}
					
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	
		
	}
	@Override
	public void getChangeStockTwo() {
			
			String startDatetime =df.format(new Date(new Date().getTime() - 2*60*60*1000)); //开始时间
	//		String startDatetime =df.format(new Date(new Date().getTime() - 3*60*1000)); //5分鐘內的庫存變化
			String endDatetime = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
			
			String url = "/shangshang/v0/product/change_stock?";
			 
			String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
	//		System.out.println(timestamp);
			String params = "";
			TreeMap<String,String> map = new TreeMap<>();	
			Map<String,String> maps =new HashMap<String, String>();
			String sign = getSignMd5(url, app_key, timestamp, app_secret);
	//	    System.out.println(sign);
	//	    System.out.println(api_url+url);
			map.put("app_key", app_key);
			map.put("timestamp", timestamp);
			map.put("sign", sign);
			params="start_datetime="+startDatetime+"&end_datetime="+endDatetime; 
	//		System.out.println(params);
			String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
	//		System.out.println(response);
			try {
				//{"status":200,"message":"ok","data":[]}
				JSONObject jsonObj = new JSONObject(response);
				String status = jsonObj.getString("status");
				String datas = jsonObj.getString("data");
	//			System.out.println(datas);
				if(datas.equals("[]")){
					return;
				}
				JSONObject jsonObject = jsonObj.getJSONObject("data");
	//           JSONArray jsonObject = jsonObj.getJSONArray("data");
	//           JSONObject dataObject = new JSONObject(dataArray.get(i).toString());
				if (status.equals("200")) {
					if(jsonObject != null && jsonObject.length()>0){
											
						List<Long> proidList = getAllProid();
						if(proidList!=null && proidList.size()>0){
							Iterator<String> it = jsonObject.keys(); 
							List<StockUpdate> list = new ArrayList<StockUpdate>();
							Map updateMap = new HashMap();
							while(it.hasNext()){
							// 获得key
							String key = it.next(); //proid
							String value = jsonObject.getString(key); //库存
							
							Long proid = Long.parseLong(key);
							int numChang = Integer.parseInt(value); 
							ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
							pg.setProdId(proid);
							//如果原来有这个商品，查询这个商品的库存，不一样，就改变这个商品的库存
							if(proidList.contains(proid)){
								pg = getProidNum(pg); 
								int prenum = pg.getStock();//原来的库存
								String ssProid = pg.getSsProdid();
								if(numChang != prenum){
									maps.put("prodId", proid+"");
									maps.put("stock", numChang+"");
									maps.put("timeChangeUpdateStock", "yes"); //代表是时间段内库存变化，插入此时的时间
									Integer i = providerGoodsUpdateDao.updateGoodsXiYouMap(maps); //更新provide_goods_xiyou表的库存
									//如果更新provide_goods_xiyou成功,更新hx_stock_update的库存
									if(i>0){
										StockUpdate su = new StockUpdate();
										su.setSku(ssProid);
										su.setNowStockNum(numChang);
										su.setType("sh");
										list.add(su);
									}
								}
							}
							}		
							updateMap.put("list", list);
							if(list!=null && list.size()>0){
								platformStockUpdateDao.updateBatchNowStock(updateMap);
							}
						}
						
						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		
			
		}
	   @Override
        public void getChangeStockThree(String startTime,String endTime) {
		
//		String startDatetime =df.format(new Date(new Date().getTime() - 24*24*60*60*1000-6*24*60*60*1000));
//		String startDatetime =df.format(new Date(new Date().getTime() - 24*60*60*1000)); //开始时间
		String startDatetime =startTime;
//		String endDatetime = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		String endDatetime = endTime;
		String url = "/shangshang/v0/product/change_stock?";
		 
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
//		System.out.println(timestamp);
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();	
		Map<String,String> maps =new HashMap<String, String>();
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		params="start_datetime="+startDatetime+"&end_datetime="+endDatetime; 
//		System.out.println(params);
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//		System.out.println(response);
		try {
			//{"status":200,"message":"ok","data":[]}
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			String datas = jsonObj.getString("data");
//			System.out.println(datas);
			if(datas.equals("[]")){
				return;
			}
			JSONObject jsonObject = jsonObj.getJSONObject("data");
//           JSONArray jsonObject = jsonObj.getJSONArray("data");
//           JSONObject dataObject = new JSONObject(dataArray.get(i).toString());
			if (status.equals("200")) {
				if(jsonObject != null && jsonObject.length()>0){
										
					List<Long> proidList = getAllProid();
					if(proidList!=null && proidList.size()>0){
						Iterator<String> it = jsonObject.keys(); 
						List<StockUpdate> list = new ArrayList<StockUpdate>();
						Map updateMap = new HashMap();
						while(it.hasNext()){
						// 获得key
						String key = it.next(); //proid
						String value = jsonObject.getString(key); //库存
						
						Long proid = Long.parseLong(key);
						int numChang = Integer.parseInt(value); 
						ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
						pg.setProdId(proid);
						//如果原来有这个商品，查询这个商品的库存，不一样，就改变这个商品的库存
						if(proidList.contains(proid)){
							pg = getProidNum(pg); 
							int prenum = pg.getStock();//原来的库存
							String ssProid = pg.getSsProdid();
							if(numChang != prenum){
								maps.put("prodId", proid+"");
								maps.put("stock", numChang+"");
								maps.put("timeChangeUpdateStock", "yes"); //代表是时间段内库存变化，插入此时的时间
								Integer i = providerGoodsUpdateDao.updateGoodsXiYouMap(maps); //更新provide_goods_xiyou表的库存
								//如果更新provide_goods_xiyou成功,更新hx_stock_update的库存
								if(i>0){
									StockUpdate su = new StockUpdate();
									su.setSku(ssProid);
									su.setNowStockNum(numChang);
									su.setType("sh");
									list.add(su);
								}
							}
						}
						}		
						updateMap.put("list", list);
						if(list!=null && list.size()>0){
							platformStockUpdateDao.updateBatchNowStock(updateMap);
						}
					}
					
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	
		
	}
	
	/**
	 * @param prodId
	 * @return
	 * 生成我们自己的sku
	 */
	private String getSsProdId(String prodId) {
		String pd="111"+prodId;
		
		int p06 = 1;
 		for(int i=0;i<prodId.length();i++){
 			 p06 =p06+p06*Integer.parseInt(prodId.charAt(i)+""); 
 		}
 		String p = p06+"";
 		if(p.length()>3){
 			p = p.substring(0,3);
 			pd = pd+p;
 		}else if(p.length()<3){
 			pd = pd+prodId.charAt(4)+""+prodId.charAt(5)+""+prodId.charAt(6)+"";
 		}else{
 			pd = pd+p;
 		}
		return pd;
	}





	/* 
	 *  二、//获取商品详细信息,可获取一个 或多个商品的详情，更根据prodId
	 */
	@Override
	public void getDetailProducts() {
		String url = "/shangshang/v0/product/products?";
		String path = "e:/upload/provide/xiyou/";
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
	  //银泰商品唯一标识
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();	
	   
		List<Long> proidAll = new ArrayList<Long>();
		Map<String,String> mapp = new HashMap<String, String>();
		//先获取已有的商品的provid，放入list集合
		/*List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(new ProvideGoodsXiYou());
		if(pgxy.size()>0){
		for(ProvideGoodsXiYou p:pgxy){
			proidAll.add(p.getProdId());
		}
		}*/
		int errorCnt =1;
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
//		params="prodid=1374855"; //获取一个商品的详细信息
		params="prodid=1378725,1378742,1378733,1378789,1378759";
//		params="prodid=1378729,1378782,1378764,1378774,1378803";
//		params="prodid=1374786,1374542,1374533,1374470,1374468,1374466,1374465,1374464,1374463,1374455"; //获取二个商品的详细信息
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
		System.out.println(response);
//		while(true){
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			JSONArray dataArray = jsonObj.getJSONArray("data");
			List<ProvideGoodsXiYou> pgList = new ArrayList<ProvideGoodsXiYou>();
			List<ProvideGoodsImge> pgiList = new ArrayList<ProvideGoodsImge>();
			if (status.equals("200")) {
				if(dataArray != null && dataArray.length()>0){
//					System.out.println(dataArray.length());
					for(int i=0;i<dataArray.length();i++){
						ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
						JSONObject dataObject = new JSONObject(dataArray.get(i).toString());
						String ProdId = dataObject.getString("ProdId");
						Long prodId = Long.parseLong(ProdId); //银泰商品唯一标识
                     //如果存放prodid的集合中，有刚获取的prodid,这个prodid跳过，继续下一个商品；如果没有将这个商品放入商品表
						if(proidAll.size()>=0 && proidAll.contains(prodId)){
							continue;
						}
						String brandName = dataObject.getString("BrandName"); //品牌
						String prodName = dataObject.getString("ProdName"); 
						
//						int taxRate = Integer.parseInt(dataObject.getString("TaxRate"));
						Double taxRate = Double.parseDouble(dataObject.getString("TaxRate"));
						int providerId = Integer.parseInt(dataObject.getString("ProviderId")); 
						
						String providerName = dataObject.getString("ProviderName");
						
						Double price = Double.parseDouble(dataObject.getString("Price"));
						Double cost = Double.parseDouble(dataObject.getString("Cost"));
						Double marketPrice = Double.parseDouble(dataObject.getString("MarketPrice"));
						
						String Size= dataObject.getString("Size");
						
						String color= dataObject.getString("Color");
						String provideSkuNumbr= dataObject.getString("ProviderSkuNumbr");
						
						String detailDescription= dataObject.getString("DetailDescription");
						
						//获取当前ProdId的库存
						String stock = getXiYouStock(ProdId);
						
					   //形成尚上的唯一标识和银泰唯一标识一一对应
						String ssProdid = getSsProdId(ProdId);
//						System.out.println(ssProdid);
						
						String imageAddresses= dataObject.getString("ImageAddresses");
						String imageAddressess ="";
						if(!imageAddresses.equals("null")){
							JSONArray imageArray = new JSONArray(imageAddresses);
//							JSONObject dataAssJSON = new JSONObject(imageArray.get(0).toString());
							if(imageArray!=null && imageArray.length()>0){
								for(int t=0;t<imageArray.length();t++){
									JSONObject dataAssJSONT = new JSONObject(imageArray.get(t).toString());
									String type = dataAssJSONT.getString("ImageType");
									String imagepic = dataAssJSONT.getString("ImageName");
									ProvideGoodsImge  pgim = new ProvideGoodsImge();
									pgim.setProdId(ProdId);
									pgim.setSsProid(ssProdid);
									pgim.setType(type);
									
//									if(type.equals("1")){
//										imageAddressess = "provide/xiyou/"+ssProdid+"_"+t+imagepic.substring(imagepic.lastIndexOf("."));
//										pgim.setImagePic(imageAddressess);
//										pgiList.add(pgim);
//										downloadPicture(imagepic,path,ssProdid,type,t);
//									}else{
									if(type.equals("1")){
//										if(imagepic.endsWith(".JPG") || imagepic.endsWith(".jpg") || imagepic.endsWith(".png")){
											String pic = "provide/xiyou/"+ssProdid+"_"+t+".jpg";
											imageAddressess = pic;
											downloadPicture(imagepic,path,ssProdid,type,t);
//										}
										
									}
//										pgim.setImagePic(pic);
										pgim.setXiyouImage(imagepic);
										pgiList.add(pgim);
//										downloadPicture(imagepic,path,ssProdid,type,t);
//									}
									
								}
							}
						}
//						System.out.println(imageAddressess);
						Double ourSalePrice = (double) Math.round(cost/0.65);
						
						pg.setProdId(prodId);
						pg.setSsProdid(ssProdid);
						pg.setBrandName(brandName);
						pg.setProdName(prodName);
						pg.setTaxRate(taxRate);
						pg.setProviderId(providerId);
						pg.setProviderName(providerName);
						pg.setPrice(price);
						pg.setCost(cost);
						pg.setMarketPrice(marketPrice);
						pg.setOurSalePrice(ourSalePrice);
						pg.setSize(Size);
						pg.setColor(color);
						pg.setProvideSkuNumbr(provideSkuNumbr);
						pg.setDetailDescription(detailDescription);
						pg.setImageAddresses(imageAddressess);
						pg.setStock(Integer.parseInt(stock));
						pgList.add(pg);
					}					
//					System.out.println("000000000000000");
					if(pgList.size()>0){
//						conut = conut +1;
						//把新增的商品插入provide_goods_xiyou表
						providerGoodsUpdateDao.insertXiYouProducts(pgList);
						provideGoodsImgeDao.insertGoodsImge(pgiList);
						//将新增的商品插入hx_stock_update
						//循环新插入到provide_goods_xiyou的数据，拿出ssProdid查询hx_stock_update没有的这个的，新插入一条
						for(int i=0;i<pgList.size();i++){
							String ssProdid = pgList.get(i).getSsProdid();
							Long prodid = pgList.get(i).getProdId();
							mapp.put("xiyouProdId", prodid+"");
			        		  List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(mapp);
			        		  if(sulist==null || sulist.size()==0){
			        			  int num = pgList.get(i).getStock();
			        			  StockUpdate su = new StockUpdate();
			        				su.setSku(ssProdid);
			        				su.setNowStockNum(num);
			        				su.setLastUpdateStockNum(num);
			        				su.setXiyouProdId(prodid+"");
			        				su.setType("sh");
			        				platformStockUpdateDao.insertStockUpdate(su);
			        		  }
						}
						
					}
					
				}else{
					System.out.println(dataArray+"=====是null");
					
					
				}
				
			}else{
				
			}
//			conut = conut-1;
//			if(conut<=0){
//				break;
//			}
			}catch (JSONException e) {

				log.error(e.getMessage());
				if(errorCnt++ > 5)
				{
					e.printStackTrace();
					System.out.println("xiyou sysc GetProducts error over 5 cnt");
//					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				if(errorCnt++ > 5)
				{
					e.printStackTrace();
					System.out.println("xiyou sysc GetProducts error over 5 cnt");
//					break;
				}
			}
//		}
	}
	 
	
	/* 
	 * //获取一个商品或多个商品库存，根据prodIds
	 */
	@Override
	public String getXiYouStock(String prodIds) {
		
		String url = "/shangshang/v0/product/stock?";
		 
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
//		System.out.println(timestamp);
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();	
			
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		params="prodIds="+prodIds; //获取一个商品的库存，也可获取多个商品的库存
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//		System.out.println(response);
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			JSONObject Jsonbject = jsonObj.getJSONObject("data");
			
			if (status.equals("200")) {
				if(Jsonbject != null && Jsonbject.length()>0){
					String stock1 = Jsonbject.getString(prodIds);
//					System.out.println(stock1);
					return stock1;
				}
			}else{
				return "0";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return response;
	
	}
	
	@Override
	public void getXiYouStock() {
		String url = "/shangshang/v0/product/stock?";
		 
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
//		System.out.println(timestamp);
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();	
			
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		params="prodIds=1355645"; //获取一个商品的库存，也可获取多个商品的库存
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//		System.out.println(response);
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			JSONObject Jsonbject = jsonObj.getJSONObject("data");
			if (status.equals("200")) {
				if(Jsonbject != null && Jsonbject.length()>0){
					String stock1 = Jsonbject.getString("1355645");		
//					System.out.println(stock1);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	//获取proid和对应的库存
	public ProvideGoodsXiYou getProidNum(ProvideGoodsXiYou pg){
		List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(pg);
		if(pgxy.size()>0){
			pg = pgxy.get(0);
		}
		return pg;
	}
	
	
	
	
	public List<Long> getAllProid(){
		List<Long> proidAll = new ArrayList<Long>();
		List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(new ProvideGoodsXiYou());
		if(pgxy.size()>0){
		for(ProvideGoodsXiYou p:pgxy){
			proidAll.add(p.getProdId());
		}
		}
		return proidAll;
	}
	
	
	 /*
	    *  //将获取的商品库存导入hx_stock_update，使其更新到各个平台
		*/
		@Override
		public void insertProvideToStockUpdate() {
			Map<String, String> searchMap  = new HashMap<String, String>();
			Map<String, String> searchMaps  = new HashMap<String, String>();
			List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(new ProvideGoodsXiYou());
	          if(pgxy.size()>0){      	  
	        	  for(int i=0;i<pgxy.size();i++){
	        		  String ssProdid = pgxy.get(i).getSsProdid();
	        		  searchMaps.put("sku", ssProdid);
	        		  List<StockUpdate> sulist = hxPlatformStockUpdateDao.HxSelectStockUpdate(searchMaps);
	        		  if(sulist==null || sulist.size()==0){
	        			  ProvideGoodsXiYou pro = new ProvideGoodsXiYou();
	        			  pro.setSsProdid(ssProdid);
	        			  pro = providerGoodsUpdateDao.selectOneProidProduct(pro);
	        			  int num = pro.getStock();
	        			  StockUpdate su = new StockUpdate();
	        				su.setSku(ssProdid);
	        				su.setNowStockNum(num);
	        				su.setLastUpdateStockNum(num);
	        				su.setType("sh");
	        				platformStockUpdateDao.insertStockUpdate(su);
	        		  }
	        	  }
	          }
		}
	
	
   
	public static String getSignMd5(String url, String appkey,String timestamp, String secret) {
		String sign = "";
		String params = "";
		params = url + "app_key=" + appkey + "&timestamp=" + timestamp + secret;
		String MD5 = md5(params);
		sign = MD5;
		return sign;
	}

	public static String md5(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(txt.getBytes("UTF-8")); // 注意这里的编码
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest()) {
				buf.append(String.format("%02x", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
    		return null;
		}
	}
	
	
	public static String getRandomString(int length){
	    //定义一个字符串（A-Z，a-z，0-9）即62位；
	    String str="1234567890";
	    //由Random生成随机数
	        Random random=new Random();  
	        StringBuffer sb=new StringBuffer();
	        //长度为几就循环几次
	        for(int i=0; i<length; ++i){
	          //产生0-10的数字
	          int number=random.nextInt(10);
	          //将产生的数字通过length次承载到sb中
	          sb.append(str.charAt(number));
	        }
	        //将承载的字符转换成字符串
	        return sb.toString();
	  }
   
   
   
	//======================将属于供应商的产品找到，放入ProvideOrderDetail这个表==========================================
	@Override
	public void selectProvideOrderDetail(){
		//获取一段时间的订单，插入到新表 provide_order_detail中
		try {
			Map<String,String> searchmap = new HashMap<String, String>();
			MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
			String insertTime =df.format(new Date(new Date().getTime() - 10*60*1000));  //获取10分鐘內的订单
//			System.out.println(insertTime);
			PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
			platformorderdetails.setInsertTime(insertTime);
		    List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
//		    System.out.println(orderDetais.size()+"===--=-==-");
		    if(orderDetais!=null && orderDetais.size()>0){
		    	 //先循环出，是供应商的商品，将商品详情放入orderIdProList结合
			    List<ProvideOrderDetail> orderIdProList = getProvideOrderDetais(orderDetais); 
			    List<ProvideOrderDetail> orderList = new ArrayList<ProvideOrderDetail>();
//			    System.out.println(orderIdProList.size()+"==============");
			    if(orderIdProList!=null && orderIdProList.size()>0){
			        for(int i=0;i<orderIdProList.size();i++){
			        	searchmap.put("tradeId", orderIdProList.get(i).getTradeId());
			        	searchmap.put("itemId", orderIdProList.get(i).getItemId());
			        	List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
//			        	System.out.println(list.size());
			        	//判断是否插入过
			        	if(list == null || list.size()==0){
			        		orderList.add(orderIdProList.get(i));
			        	}
			      }
			        if(orderList.size()>0){ 
			        	
			        	provideOrderDetailDao.insertOrderDetailList(orderList);
			        	
                       //修改total_price,和payPrice的价格
			        	List<String> list = new ArrayList<String>();
			        	for(int t=0;t<orderList.size();t++){
			        		list.add(orderList.get(t).getTradeId());
			        	}

			        	HashSet h = new HashSet(list);   
			    	    list.clear();   
			    	    list.addAll(h); 
			    	    for(int j=0;j<list.size();j++){
			    	    	String tradeId = list.get(j);
			    	    	List<ProvideOrderDetail>  orderDetailList  = provideOrderDetailDao.selectProviderOrderId(tradeId);
			    	    	Double totalprice = 0.00;
			    	    	for(int k=0;k<orderDetailList.size();k++){
			    	    		totalprice = totalprice+ Double.valueOf(orderDetailList.get(k).getTotalPrice());
			    	    	}
			    	    	Map<String,String> searchMap = new HashMap<String, String>();
			    	    	searchMap.put("tradeId",  tradeId+"");
			    	    	searchMap.put("totalPrice", totalprice+"");
			    	    	searchMap.put("payPrice", totalprice+"");
			    	    	provideOrderDetailDao.updateProviderOrderDetailDao(searchMap);
			    	    }
			        }
			    }
		    }
		} catch (Exception e) {
		e.printStackTrace();
		log.error(e.getMessage());
		}
	

    }
   
	/**
	 * @param orderDetais
	 * @return
	 *  //得到时间段内是哪家供货商
	 */
	private List<ProvideOrderDetail> getProvideOrderDetais(List<PlatFormOrderDetails> orderDetais) {
		List<ProvideOrderDetail> lis = new ArrayList<ProvideOrderDetail>();
		
		Map<String,String> searchMap = new HashMap<String, String>();
		for(int i=0;i<orderDetais.size();i++){
			 String sku = orderDetais.get(i).getMerchantSkuId(); //我们自己的sku
			 String platSku = orderDetais.get(i).getSkuId();
			 String pty = orderDetais.get(i).getPtype();   //查詢出哪個平台
			 String platName = getPlatName(pty);
			 Map<String,String> mapSku = new HashMap<String, String>();
			 if(StringUtils.isBlank(sku) && StringUtils.isNotBlank(platSku)){
				 if(StringUtils.isNotBlank(platName)){
					 mapSku.put(platName, platSku);
					 mapSku.put("skuisnotnull", "xiyou_prod_id");
					 List<StockUpdate> listSku = platformStockUpdateDao.selectStockUpdateByMap(mapSku);
					 if(listSku!=null && listSku.size()>0){
						 sku = listSku.get(0).getSku();
					 }else{
						 continue;
					 }
				 }else if(StringUtils.isNotBlank(platName)){
					 mapSku.put(platName, platSku);
					 mapSku.put("skuisnotnull", "yshang_sku_id");
					 List<StockUpdate> listSku = platformStockUpdateDao.selectStockUpdateByMap(mapSku);
					 if(listSku!=null && listSku.size()>0){
						 sku = listSku.get(0).getSku();
					 }else{
						 continue;
					 }
				 }else{
					 continue;
				 }
				 
				 
			 }
			 if(StringUtils.isBlank(sku) && StringUtils.isBlank(platSku)){
				 continue;
			 }
			 if(sku.startsWith("111")){ //获取银泰西有的商品以111开头
			   String proid = sku.substring(3, 10);
			   searchMap.put("sku", sku);
			   searchMap.put("xiyouProdId", proid);
			   List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap); //查询供应商表是否有这个商品，并确定是哪个商家的
			   //如果能查询出商品，则是供应商的商品，因为xiyouProdId可判断出是西有银泰的
			   if(list !=null && list.size()>0){
				   //把订单id放进集合
				   ProvideOrderDetail pod = new ProvideOrderDetail();
				   String originalTradeId = orderDetais.get(i).getIdorder(); //原有的订单编号
				   String newTradeId = getNewTradeId(originalTradeId);//通过原有的订单编号，获取新的订单编号，也是传给西有的订单编号
				   pod.setOriginalTradeId(originalTradeId);
				   pod.setTradeId(newTradeId);
				   pod.setUserName(orderDetais.get(i).getName());
				   pod.setPhone(orderDetais.get(i).getMobile());
				   pod.setProvince(orderDetais.get(i).getProvince());
				   
				   pod.setCity(orderDetais.get(i).getCity());
				   pod.setRegion(orderDetais.get(i).getDistrict());
				   pod.setLocation(orderDetais.get(i).getStreetAddress());
//				   pod.setTotalPrice(orderDetais.get(i).getTotalPrice());
//				   pod.setPayPrice(orderDetais.get(i).getPayPrice());
				   
				   
//				   pod.setInsertTime(orderDetais.get(i).getInsertTime()); //插入當前這個表的時間
				   pod.setCreateTime(orderDetais.get(i).getPalcedTime()); //商品下單時間
				   pod.setPayTime(orderDetais.get(i).getPayTime());    //插入另一張訂單詳情表的時間
				   
				   pod.setProdId(proid);
				   pod.setItemId(sku);
				   
				   pod.setTitle(orderDetais.get(i).getProductname());
				   
				   ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
		   		   pg.setProdId(Long.parseLong(proid));
		   		   List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(pg);
		   		   if(pgxy!=null && pgxy.size()>0){
		   			   Double d = pgxy.get(0).getCost();
		   			int q = Integer.parseInt(orderDetais.get(i).getQuantity());
		   			pod.setSellPrice(d+"");
		   			pod.setPayPrice(d*q+"");
		   			pod.setTotalPrice(d*q+"");
		   			
		   		   }else{
		   			pod.setSellPrice(0+"");
		   			pod.setPayPrice(0+"");
		   			pod.setTotalPrice(0+"");
		   		   }
		   		   pod.setOurSellPrice(orderDetais.get(i).getPayPrice());
				   pod.setTax("0");
				   pod.setQty(Integer.parseInt(orderDetais.get(i).getQuantity()));
				   pod.setFreight(orderDetais.get(i).getFreight());
				   pod.setSellPlatform(orderDetais.get(i).getPtype());
				   pod.setProvider("xiyou");
				   lis.add(pod);
			   }
			   
			   
		}else if(sku.startsWith("222")){//获取云尚的商品以222开头
			 Map<String,String> mapEntity = new HashMap<String, String>();
			 mapEntity.put("ourSku", sku);
		     List<ProvideGoodsYShang> pgysgoods = provideGoodsYShangUpdateDao.selectYShangEntityByMap(mapEntity);
		     String proid = "";
		     if(pgysgoods!=null && pgysgoods.size()>0){
		    	 proid = pgysgoods.get(0).getSkuId();

			   searchMap.put("sku", sku);
			   searchMap.put("yshangSkuId", proid);
			   List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap); //查询供应商表是否有这个商品，并确定是哪个商家的
			   //如果能查询出商品，则是供应商的商品，因为xiyouProdId可判断出是西有银泰的
			   if(list !=null && list.size()>0){
				   //把订单id放进集合
				   ProvideOrderDetail pod = new ProvideOrderDetail();
				   String originalTradeId = orderDetais.get(i).getIdorder(); //原有的订单编号
				   String newTradeId = getNewTradeIdYShang(originalTradeId);//通过原有的订单编号，获取新的订单编号，也是传给西有的订单编号
				   pod.setOriginalTradeId(originalTradeId);
				   pod.setTradeId(newTradeId);
				   pod.setUserName(orderDetais.get(i).getName());
				   pod.setPhone(orderDetais.get(i).getMobile());
				   pod.setProvince(orderDetais.get(i).getProvince());
				   
				   pod.setCity(orderDetais.get(i).getCity());
				   pod.setRegion(orderDetais.get(i).getDistrict());
				   pod.setLocation(orderDetais.get(i).getStreetAddress());

				   pod.setCreateTime(orderDetais.get(i).getPalcedTime()); //商品下單時間
				   pod.setPayTime(orderDetais.get(i).getPayTime());    //插入另一張訂單詳情表的時間
				   
				   pod.setProdId(proid);
				   pod.setItemId(sku);
				   
				   pod.setTitle(orderDetais.get(i).getProductname());
				   
				   
				   Double dp = pgysgoods.get(0).getSettlePrice();
				   
				   Integer stock = pgysgoods.get(0).getStock();
				   Double t = getYShangUpdate(sku,proid,dp+"",stock+"");
				   if(!t.equals(0.00)){
					   dp = t;
				   }
				   
				   int q = Integer.parseInt(orderDetais.get(i).getQuantity());
				   pod.setSellPrice(dp+"");
		   		   pod.setPayPrice(dp*q+"");
		   		   pod.setTotalPrice(dp*q+"");
		   		   pod.setOurSellPrice(orderDetais.get(i).getPayPrice());
				   pod.setTax("0");
				   pod.setQty(Integer.parseInt(orderDetais.get(i).getQuantity()));
				   pod.setFreight(orderDetais.get(i).getFreight());
				   pod.setSellPlatform(orderDetais.get(i).getPtype());
				   
				   pod.setCardNo(orderDetais.get(i).getIdentityNumber());
//				   pod.setOrderChannelId("454725");  //订单渠道商用户 ID（云尚分配）
//				   pod.setOrderChannelName("ABC测试");//渠道商用户名称（云尚分配）
				   
				   
				   pod.setProvider("yshang");
				   lis.add(pod);
			   }
		}
			   
		
		}
			
		}
		return lis;
	}
   
   
	//======================将属于供应商的产品找到，放入ProvideOrderDetail这个表==========================================  
   
   
   
   
   
   
   
   
   
   
	/**
	 * @param proid
	 * @param dp
	 * @param stock
	 * @return
	 */
	private Double getYShangUpdate(String sku,String skuId, String setprice, String stock) {
		
		String stockPrice = getYShangStock(skuId);
		String[] sp = stockPrice.split("_");
		String quantity = sp[0]; //库存
		String price = sp[1]; //当前结算价
		Map<String,String> maps = new TreeMap<String, String>();
		if(price.equals(setprice) && quantity.equals(stock)){
			return 0.00;
		}
		/*
		if(!price.equals(setprice) && !quantity.equals(stock)){
			maps.put("timeChangeUpdateStock", "yes");
			maps.put("costChangeTime", "yes");
		}  */
		
		if(!quantity.equals(stock)){
			maps.put("timeChangeUpdateStock", "yes");
		}
		if(!price.equals(setprice)){
			maps.put("costChangeTime", "yes");
		}
		
		Double settlePrice = Double.parseDouble(price);
		Double ourSalePrice = (double) Math.round(settlePrice/0.65);
		if(!price.equals(setprice) || !quantity.equals(stock)){
			//更新provide_goods_yshang的库存、供货价、我们的销售价
			
			maps.put("skuId", skuId);
			maps.put("stock", quantity);
			maps.put("settlePrice", price);
			maps.put("ourPrice", ourSalePrice+"");
			provideGoodsYShangUpdateDao.updateGoodsYShangMap(maps);
			
			//更新hx_stock_update的库存
			StockUpdate su = new StockUpdate();
			su.setSku(sku);
			su.setNowStockNum(Integer.parseInt(quantity));
			su.setType("sh");
			platformStockUpdateDao.updateNowStockBySku(su);
		}
		
		
		return settlePrice;
	}
public String getYShangStock(String skuId) {
		
		String method ="yshang.wdk.trade.products.inventory";
		 TreeMap<String, String> map = new TreeMap<String, String>();
		 String stockPrice = "";
		 String nowTime = df.format(new Date());
		//公共参数
		 map.put("method", method);
		 map.put("app_key", "hzAzyPj3/EM=");
		 map.put("secret", "sJkS76FDhESeesLwz/nxGnR+zKnHQskmPA0uWV+17C4=");
		 map.put("v", "1.0"); 
		 map.put("timestamp", nowTime);
		
		//业务参数
		map.put("skuId", skuId);
	
		
		//公共参数和业务参数一起参与签名
		String signs = getSign(map);
		map.put("sign", signs);
		//獲取商品價格和庫存
		String response = HttpRequest.sendPostNoSecret("https://api.winshine.shop/router/rest?", map);
		
		System.out.println(response);
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("success");
			JSONObject jsonbject = jsonObj.getJSONObject("data");
			JSONObject stockAndPrice = jsonbject.getJSONObject( "stockPrice");
			if (status.equals("0")) {
				if(stockAndPrice != null){
					String skuid = stockAndPrice.getString("skuId");
					String stock = stockAndPrice.getString("quantity");
					String price = stockAndPrice.getString("salePrice");
					stockPrice = stock+"_"+price;
					return stockPrice;
				}
			}else{
				return "0_0";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return response;
	
	}
private String getSign(Map<String,String> map)
{
	String sign = "";
	String needMD5Str = map.get("secret");
	Set<Entry<String, String>> entrySet  =  map.entrySet();
	Iterator<Entry<String,String>> it = entrySet.iterator();
	while(it.hasNext())
	{
		Entry<String,String> en = it.next();
		if(en.getKey().equals("secret") || en.getKey().equals("sign") ) continue; //密码和签名项不加入签名算法
			needMD5Str += en.getKey() + en.getValue();
	}
	needMD5Str+= map.get("secret");
	
	System.out.println(needMD5Str);
	sign = HttpRequest.string2MD5(needMD5Str).toUpperCase();
	return sign;
}
	/**
	 * @param originalTradeId
	 * @return
	 */
	private String getNewTradeId(String originalTradeId) {
		Pattern pp = Pattern.compile("[^0-9]");
		Matcher m = pp.matcher(originalTradeId);
		String result = m.replaceAll("");
		String pd="XY111";
//		System.out.println(result.length());
		if(result.length()>8){
			pd = pd+result.substring(3,8)+result.substring(0, 5);
		}else if(result.length()>5 && result.length()<=8){
			
			pd = pd+result.substring(2,7)+result.substring(1, 6);
		}else{
			pd = pd+"08543"+result;
		}
		return pd;
	}
	
	private String getNewTradeIdYShang(String originalTradeId) {
		Pattern pp = Pattern.compile("[^0-9]");
		Matcher m = pp.matcher(originalTradeId);
		String result = m.replaceAll("");
		String pd="YS222";
//		System.out.println(result.length());
		if(result.length()>8){
			pd = pd+result.substring(3,8)+result.substring(0, 5);
		}else if(result.length()>5 && result.length()<=8){
			
			pd = pd+result.substring(2,7)+result.substring(1, 6);
		}else{
			pd = pd+"08543"+result;
		}
		return pd;
	}
	
	


	/**
	 * @param pty
	 * @return
	 */
	private String getPlatName(String platName) {
		if(platName.equals("siku")){
			return "sikuSku";
		}else if(platName.equals("sikunew")) {
			return "sikunewSku";
		} else if(platName.equals("kaola")) {
			return "kaolaSku";
		}else if (platName.equals("tmall")) {
			return "tmallSku";
		} else if (platName.equals("fql")) {
			return "fqlSku";
		}else if (platName.equals("pdd")) {
			return "pddSku";
		} else if (platName.equals("pddnew")) {
			return "pddnewSku";
		}else if (platName.equals("Mlh")) {
			return "MlhSku";
		} else if (platName.equals("xhs")) {
			return "xhsSku";
		} else if (platName.equals("Ofashion")) {
			return "ofashionsku";
		}else if (platName.equals("yinTai")) {
			return "yinTaiSku";
		}else if (platName.equals("shepin")) {
			return "shepinSku";
		}else if(platName.equals("zp")){
			return "zhenpinSku";
		}
		return null;
	}


	/*
	 * 银泰的sku更新到本地
	 */
	@Override
	public void updateSku2Location() {
		
	}

	// 更新银泰平台上的stock
	public void updateAllStock() {
		
		
	}

	
	
	/* 
	 * 
	 */
	@Override
	public boolean updateYinTaiStock(String oursku, String weimobsku,int quantity, String type) {
		return false;
	}

	/* 
	 * 
	 */
	@Override
	public int atuoSyncOrder() {
		return 0;
	}

	 public void getjson(){
		  Map<String,Object> orderJson = new HashMap<String, Object>();
		  Map<String,Object> maps = new HashMap<String, Object>();
		  Map<String,String> order = new HashMap<String, String>();
		  Map<String,Object> products = new HashMap<String, Object>();
		  String trade_id = "123456789012345"; // 订单编号
		  String username = "测试";            // 用户名称
		  String phone ="12345678908";         // 电话
		  String province = "北京";          // 省
		  String city = "北京";            // 市
		  
		  String region = "通州";       // 区
		  String location= "万达广场";   // 详细地址
		  String postcode= "100010";  // 邮编（可为空）
		  String pay_price = "pay_price"; // 实付金额
		  String total_price = "30000";   // 订单总金额
		  
		  String create_time = "1539141893";  // 订单创建时间
		  String pay_time = "1539141893"; // 支付时间
		  String buyer_message = "测试";  // 买家留言 （可为空）
		  String buyer_memo ="测试";     // 买家留言 （可为空）
		  String seller_memo ="测试";    // 卖家备注（可为空）
		  
		  String invoice_name ="个人发票";  // 发票名称（可为空）
		  String invoice_type = "123333344444555666";// 发票税号（可为空）
		  order.put("trade_id",trade_id);
		  order.put("username",username);
		  order.put("phone",phone);
		  order.put("province",province);
		  order.put("city",city);
		  
		  order.put("region",region);
		  order.put("location",location);
		  order.put("postcode",postcode);
		  order.put("pay_price",pay_price);
		  order.put("total_price",total_price);
		  
		  order.put("create_time",create_time);
		  order.put("pay_time",pay_time);
		  order.put("buyer_message",buyer_message);
		  order.put("buyer_memo",buyer_memo);
		  order.put("seller_memo",seller_memo);
		  
		  order.put("invoice_name",invoice_name);
		  order.put("invoice_type",invoice_type);
		  
		  maps.put("order", order);
		  
		  
		  /*商品*/
		  long ProdId = 1373053;
		  String item_id ="19876";
		  String title = "EMPORIO ARMANI 修身 小LOGO丝绒拼接91%白鸭绒连帽羽绒服";
		  double sell_price = 14700;
		  double tax = 50;
		  
		  int qty = 1;
		  double freight = 50;
		  
		  products.put("ProdId", ProdId);
		  products.put("item_id", item_id);
		  products.put("title", title);
		  products.put("sell_price", sell_price);
		  products.put("tax", tax);
		  
		  products.put("qty", qty);
		  products.put("ProdId", ProdId);
		  products.put("freight", freight);
		  maps.put("products", products);
		  
		  JSONObject json=new JSONObject(maps);
//		  System.out.println(json);
	  }

	 
	 //将image_addresses为空或null的修改成有图片名称的
	 public  void updateProdIdImage(){
		 Map<String,String> parMap = new HashMap<String, String>();
		 parMap.put("imageIsNull", "image_addresses");
		List<ProvideGoodsXiYou> prod = providerGoodsUpdateDao.getProviderListByConditionWithPage(parMap);
		System.out.println(prod.size());
		for(int i=0;i<prod.size();i++){
			//imageAddresses
			System.out.println(prod.get(i).getProdId());
			Map<String,String> maps = new HashMap<String, String>();
			maps.put("imageAddresses", "provide/xiyou/"+prod.get(i).getSsProdid()+"_0.jpg");
			maps.put("prodId", prod.get(i).getProdId()+"");
			providerGoodsUpdateDao.updateGoodsXiYouMap(maps);
		}
		 
	 }
	 
	 
	 
	    /* getDetailProducts
		 * 获取银泰cost变化的商品,更新cost、our_sale_price
		 */
		@Override
		public void getProductCostChange() {
			System.out.println(df.format(new Date()) + "xiyou product Cost Change  start....");
			long startTime = System.currentTimeMillis();

//					String startDatetime =df.format(new Date(new Date().getTime() - 24*24*60*60*1000 - 24*24*60*60*1000 - 24*24*60*60*1000 - 24*24*60*60*1000 - 10*24*60*60*1000)); //开始时间 72
					String startDatetime =df.format(new Date(new Date().getTime() - 1*24*60*60*1000)); //1天内的cost价格的变化
					String endDatetime = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
					
					String url = "/shangshang/v0/product/change_cost?";
					List<Long> proidList = getAllProid();
					 
					String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
					String params = "";
					TreeMap<String,String> map = new TreeMap<>();	
					String sign = getSignMd5(url, app_key, timestamp, app_secret);
		
					map.put("app_key", app_key);
					map.put("timestamp", timestamp);
					map.put("sign", sign);
					params="start_datetime="+startDatetime+"&end_datetime="+endDatetime; 
			//		System.out.println(params);
					String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//					System.out.println(response);
					try {
						//{"status":200,"message":"ok","data":[]}
						JSONObject jsonObj = new JSONObject(response);
						String status = jsonObj.getString("status");
						String datas = jsonObj.getString("data");
			//			System.out.println(datas);
						if(datas.equals("[]")){
							System.out.println("datas为[]");
							return;
						}
						

			           JSONArray jsonArrayObject = jsonObj.getJSONArray("data");
//			           System.out.println(jsonArrayObject.length()+"============总条数");
			           if(jsonArrayObject!=null && jsonArrayObject.length()>0){
			        	   for(int i=0;i<jsonArrayObject.length();i++){
			        		   JSONObject dataObject = new JSONObject(jsonArrayObject.get(i).toString());
			        		   String prodId = dataObject.getString("ProdId");
//			        		   String cost = dataObject.getString("Cost");
			        		   Double costdouble = Double.parseDouble(dataObject.getString("Cost"));
			        		   Double salePrice = (double) Math.round(costdouble/0.65);
			        		   
			        		   String editDate = dataObject.getString("EditDate");
			        		   String[] datime = editDate.split("T");
			        		   String year = datime[0];
			        		   String tm = datime[1].split("\\+")[0];
			        		   editDate = year+" "+tm;
			        		   String cost = costdouble+"";
			        		   String ourSalePrice = salePrice+"";
//			        		   String ourSalePrice = Math.round(Integer.parseInt(cost)/0.65)+"";
			        		   if(!proidList.contains(Long.parseLong(prodId))){
			        			   continue;
			        		   }

			        		   
			        		   ProvideGoodsXiYou pdgxy = new ProvideGoodsXiYou();
			        		   pdgxy.setProdId(Long.parseLong(prodId));
			        		   pdgxy = providerGoodsUpdateDao.selectOneProidProduct(pdgxy);

			        		   Map<String,String> mapCost = new HashMap<String, String>();
			        		   mapCost.put("prodId", prodId);
			        		   mapCost.put("cost", cost);
			        		   mapCost.put("costChangeTime", editDate);
			        		   mapCost.put("ourSalePrice", ourSalePrice);
			        		   Integer t = providerGoodsUpdateDao.updateGoodsXiYouMap(mapCost); 
			        		   
			        		   ProvideUpdateGoodsXiYouLog pugxyl = new ProvideUpdateGoodsXiYouLog();
			        		   pugxyl.setProdid(Long.parseLong(prodId));
			        		   pugxyl.setStock(pdgxy.getStock());
			        		   pugxyl.setBrandname(pdgxy.getBrandName());
			        		   pugxyl.setProdname(pdgxy.getProdName());
			        		   pugxyl.setCost(pdgxy.getCost());
			        		   pugxyl.setOursaleprice(pdgxy.getOurSalePrice());
			        		   pugxyl.setNewcost(costdouble);
			        		   pugxyl.setNewoursaleprice(salePrice);
			        		   pugxyl.setCostchangetime(editDate);
			        		   provideUpdateGoodsXiYouLogDao.insertProvideXiYouLog(pugxyl);
			        		   
//			 System.out.println(i+"=============================");
			        	   }
			           }
			           
			        long endTime = System.currentTimeMillis();
			   		long a = endTime-startTime;
			   		long min = a/1000/60;
			   		System.out.println(df.format(new Date()) + "getProductCostChange  total min:" + min + "min");
					} catch (JSONException e) {
						
						log.error(e.getMessage()+"=====xiyou getProductCostChange error JSONException",e);
						e.printStackTrace();
					}catch (Exception e) {
						log.error(e.getMessage()+"=====xiyou getProductCostChange error Exception",e);
						e.printStackTrace();
					}
	
			
			
		}
	 
	 
	 
	 
	 
	 
	 

	 
	  public static void main(String[] args) {
//		  String pd="111"+"1355908";
//			pd=pd+XiYouPlatFormStockUpdateImpl.getRandomString(3);
//			System.out.println(pd);
//			System.out.println(XiYouPlatFormStockUpdateImpl.getRandomString(3));
	  XiYouPlatFormStockUpdateImpl xy = new XiYouPlatFormStockUpdateImpl();
//	  xy.getProductCostChange();
	  
//	   xy.getDetailProducts();
	  
//	   xy.getProducts();
//	   xy.getXiYouStock();
// 	  xy.getXiYouStock("1374542"); //75 1111364755806
//	  xy.getChangeStock();
//	  xy.getjson();
//	  xy.cancelOrder();
//	  xy.getOrerDetail();
//	  String name = xy.getShipName("1046");
//	  System.out.println(name);
//	   xy.getDetailProducts();
//	   xy.getChangeStockThree();
	   System.out.println("===================================");
	   String times = String.valueOf(System.currentTimeMillis());
	   System.out.println(times);
	  }

//	  @PostConstruct
	  public void ss(){
		System.out.println("================开始===================");
//		getProductCostChange();
		
		this.getProducts(); //有bug 圖片有問題的頁數73 74頁 75 76 123 157 159 170
//		this.insertProvideToStockUpdate();

//		getChangeStock();  //有bug
//		getChangeStockThree();
//		pushOrderToPlat();
//		getOrerDetail();
//		cancelOrder();
//		getOrerDetail();
//		getXiYouStock("1364755");
		
//		selectProvideOrderDetail();
//		getDetailProducts();
//		updateProdIdImage();'
		System.out.println("================结束===================");
	  }


	@Override
	public MiniUiGrid providerSaleInfo(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = provideUpdateGoodsXiYouLogDao.getproviderSaleInfolistCount(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		if(count >0){
			String userName = searchMap.get("userName");
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count);
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow"))){
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<ProvideUpdateGoodsXiYouLog> list =	provideUpdateGoodsXiYouLogDao.getproviderSaleInfoList(searchMap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;
	}


	







	
	  

}
