package com.huaixuan.network.web.action.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.api.ResponseData;
import com.huaixuan.network.biz.domain.autosyn.ShangPinSku;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.enums.EnumApiResponseCode;
import com.huaixuan.network.biz.service.api.ApiProductService;
import com.huaixuan.network.biz.service.api.UserAuthorityService;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;



/**
 * @author Mr_Yang   2015-12-3 下午01:25:41
 * 用户查询
 **/

@Controller
@RequestMapping("/api/product")
public class ApiSearchAction
{
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	@Autowired
	private ApiProductService apiProductService;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	
	@RequestMapping(value="/searchAll",method=RequestMethod.POST)
	public @ResponseBody Object getAllCanSellProductPost(HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		
		 
		ResponseData responseData = userAuthorityService.validataUser(request); 
		
		if(0 == responseData.getResponseCode()) //验证通过
		{
			//验证成功 查询库存产品信息
			responseData =  validateRequestParam(request); 
			if(0 == responseData.getResponseCode()) //验证通过
			{
				Map<String,String> searchMap = MiniUiUtil.getParameterMap(request); //将请求参数转为map
				
				MiniUiGrid miniuiGrid = apiProductService.getProductListByPages(searchMap);
				responseData.setResponse(miniuiGrid); 
			}
			
		}
		  
		return responseData;
	}
	
	@RequestMapping(value="/order/add",method=RequestMethod.POST)
	public @ResponseBody Object getOrderInfoPost(HttpServletRequest request,HttpServletResponse response)
	{
		
		
		 ResponseData responseData = userAuthorityService.validataUserJsonArray(request);
		 System.out.println("test post");
		 BufferedReader br;
		 String reqBody = "";
		 String vendorCode = "";
		 String palcedTime = "";
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
			String line = null;
	        StringBuilder sb = new StringBuilder();
	        while((line = br.readLine())!=null){
	            sb.append(line);
	         // 将资料解码
	            reqBody = sb.toString();
	        	} 
	        }catch (IOException e1) {
			e1.printStackTrace();
	        }
//		 String order  = request.getParameter("order");
		 Map<String,List<String>> orderMap = new HashMap<String, List<String>>();//保存订单信息和对应的sku
		 List<String> list = new ArrayList<String>();//当前查询出来的订单id
		 if(0 == responseData.getResponseCode()){
			try {
				JSONObject jsonObject = new JSONObject(reqBody);
				JSONObject order = new JSONObject(jsonObject.getString("order"));
				JSONArray orderList =  order.getJSONArray("orderItems");
				for (int i = 0; i < orderList.length(); i++) {
					JSONObject good = new JSONObject(orderList.get(i).toString());
					String Mlhsku = good.getString("skuId");
					int goodNum = good.getInt("quantity");
					String orderId = order.getString("orderId");//订单号

					String tmall = order.getString("tmall");
					Long  time = order.getLong("placedTime");
					System.out.println(time);
					String realName = order.getString("realName");
					String identityNumber = order.getString("identityNumber");
					String name  = order.getString("name");
					String mobile = order.getString("mobile");
					String country = order.getString("country");
					String province = order.getString("province");
					vendorCode = order.getString("vendorCode");
					System.out.println(vendorCode);
					String city = order.getString("city");
					String district = order.getString("district");
					String streetAddress = order.getString("streetAddress");
					String zipCode = order.getString("zipCode");
					String totalPrice = order.getString("totalPrice");
					String discountPrice = order.getString("discountPrice");
					String freight = order.getString("freight");
					
					String skuId = good.getString("skuId");
					String merchantSkuId = good.getString("merchantSkuId");
					String quantity = good.getString("quantity");
					String productname = good.getString("name");
					String size = good.getString("size");
					String currency = good.getString("currency");
					String supplyprice = good.getString("cost");
					SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					palcedTime = format.format(time);
					//保存订单所含商品信息
					PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
					platformorderdetails.setIdorder(orderId);//订单号
					platformorderdetails.setTmall(tmall);
					platformorderdetails.setPalcedTime(palcedTime);
					platformorderdetails.setName(name);
					platformorderdetails.setRealName(realName);
					platformorderdetails.setIdentityNumber(identityNumber);
					platformorderdetails.setMobile(mobile);
					platformorderdetails.setCountry(country);
					platformorderdetails.setProvince(province);
					platformorderdetails.setCity(city);
					platformorderdetails.setDistrict(district);
					platformorderdetails.setStreetAddress(streetAddress);
					platformorderdetails.setZipCode(zipCode);
					platformorderdetails.setTotalPrice(totalPrice);
					platformorderdetails.setDiscountPrice(discountPrice);
					platformorderdetails.setFreight(freight);
					platformorderdetails.setSkuId(skuId);
					platformorderdetails.setMerchantSkuId(merchantSkuId);
					platformorderdetails.setQuantity(quantity);
					platformorderdetails.setProductname(productname);
					platformorderdetails.setSize(size);
					platformorderdetails.setSupplyprice(supplyprice);
					platformorderdetails.setCurrency(currency);
					platformorderdetails.setPtype("Mlh");
					platformOrderDetailsDao.insertOrder(platformorderdetails);
					//保存订单ID
					list.add(orderId);
					String msg = Mlhsku+"_"+goodNum;
					if(orderMap.get(orderId) == null){
						List<String> value = new ArrayList<String>();
						value.add(msg);
						orderMap.put(orderId, value);
					}else{
						orderMap.get(orderId).add(msg);
					}
				}
			} catch (JSONException e) {
			}
		}
		int updateNumber = 0;
		if(orderMap.size() > 0){
			list.add("-1");//避免没有数据  报错
			List<PlatFormOrderRecord>  orderList = platformStockUpdateDao.getOrdersByOrders(list);
			Map<String,String> orderMapRealy = new HashMap<String, String>();
			for(PlatFormOrderRecord k : orderList){
				orderMapRealy.put(k.getOrderId(), "yes");
			}
			Set<Entry<String, List<String>>> set = orderMap.entrySet();
			Iterator<Entry<String,List<String>>> it = set.iterator();
			while(it.hasNext()){
				Entry<String,List<String>> entry = it.next();
				String orderId  = entry.getKey();
				List<String> value = entry.getValue();
				boolean updateSuccess = false;
				if(orderMapRealy.get(orderId) == null){
					for(String v : value){
						String[] arr = v.split("_");
						String Mlhsku = arr[0];
						int num = Integer.valueOf(arr[1]);
						Map<String,String> searchMap = new HashMap<String, String>();
						
						if(vendorCode.equals("V01025") || vendorCode.equals("690000000041176")){
							searchMap.put("MlhSku", Mlhsku);
						}else if(vendorCode.equals("100000000059745")){
							searchMap.put("MlhnewSku", Mlhsku);
						}else if(vendorCode.equals("690000000060758")){
							searchMap.put("MlhnewsecSku", Mlhsku);
						}else{
							searchMap.put("MlhSku", Mlhsku);
						}
						List<StockUpdate>  stockUpdateList = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
						for(StockUpdate su : stockUpdateList){
							su.setMlhOrderStock(num + su.getMlhOrderStock());
							su.setLastOrderTime("yes");
							platformStockUpdateDao.updateStockByNotNull(su);
							updateSuccess = true;
							updateNumber++;
						}
					}
				}
				if(updateSuccess){
					PlatFormOrderRecord oderRecord = new PlatFormOrderRecord();
					oderRecord.setOrderId(orderId);//总订单
					oderRecord.setIdPlartform(9);//魅力惠
					oderRecord.setIdStatus(1);
					oderRecord.setOrderTime(palcedTime);
					if(vendorCode.equals("V01025")){
						oderRecord.setType("sh");
					}
					else if(vendorCode.equals("690000000041176")){
						oderRecord.setType("hk");//5000以下的
					}else if(vendorCode.equals("100000000059745")){
						oderRecord.setType("hknew"); //5000以上的
					}else if(vendorCode.equals("690000000060758")){
						oderRecord.setType("hknewsec");//5000以下的
					}else{
						oderRecord.setType("hk");
					}
					platformStockUpdateDao.insertOrder(oderRecord);
				}
			}
		}
		if(updateNumber > 0){
			platformStockUpdateDao.syncOrderStock();
		}
		return responseData;
	}
	
	
	@RequestMapping(value="/searchAll",method=RequestMethod.GET)
	public @ResponseBody Object getAllCanSellProductGet(HttpServletRequest request,HttpServletResponse response)
	{
 
		ResponseData responseData = userAuthorityService.validataUser(request);
		responseData.setResponseCode(EnumApiResponseCode.ONLY_POST.getKey());
		responseData.setResponseMsg(EnumApiResponseCode.ONLY_POST.getValue());
		return responseData;
	}
	
	

	
	
	
	//验证requestParam是否正确填写
	private ResponseData validateRequestParam(HttpServletRequest request)
	{
		
		ResponseData responseData = new ResponseData();
		String requestParam = request.getParameter("request");
		JSONObject obj = null;
		try
		{
			obj = new JSONObject(requestParam);
		}
		catch (JSONException e)
		{
			//validataUser 已经验证过 不会有错 
		}
		
		//验证请求数据
		int pageSize = 0;
		try
		{
			pageSize = obj.getInt("pageSize"); //必须要有的参数
		}
		catch (JSONException e)
		{
			responseData.setResponseCode(EnumApiResponseCode.PAGESIZE_MUST.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.PAGESIZE_MUST.getValue());
			return responseData;
		}
		//大于0
		if(pageSize <= 0 )
		{
			responseData.setResponseCode(EnumApiResponseCode.PAGESIZE_MUST.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.PAGESIZE_MUST.getValue());
			return responseData;
		}
		
		int pageIndex = 0;
		try
		{
			pageIndex = obj.getInt("pageIndex"); //必须要有的参数
		}
		catch (JSONException e)
		{
			responseData.setResponseCode(EnumApiResponseCode.PAGEINDX_MUST.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.PAGEINDX_MUST.getValue());
			return responseData;
		}
		
		if(pageIndex <= 0)  //大于0
		{
			responseData.setResponseCode(EnumApiResponseCode.PAGEINDX_MUST.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.PAGEINDX_MUST.getValue());
			return responseData;
		}
		
		//0-上海 1-香港
		int type  = 0;
		try
		{
			type  = obj.getInt("type"); //必须要有的参数
		}
		catch (JSONException e)
		{
			responseData.setResponseCode(EnumApiResponseCode.TYPE_MUST.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.TYPE_MUST.getValue());
			return responseData;
		}
		//只能为0或者1
	
		if(type != 0 && type != 1)
		{
			responseData.setResponseCode(EnumApiResponseCode.TYPE_MUST.getKey());
			responseData.setResponseMsg(EnumApiResponseCode.TYPE_MUST.getValue());
			return responseData;
		}
		
		//设置当前查询的是香港还是上海库存 用于服务层查询
		request.setAttribute("type", type);
		
		return responseData;
	}
//	88e1be7c8d153b7345078541e700c487  key
	//3ae8e918825f1572f2dbb69deb5603e0 secret
	//http://140.207.52.210:8099/api/product/searchAll.html;外网访问赵瞳电脑的地址
	//http://192.168.1.93:8077/api/product/searchAll.html; 内网访问赵瞳电脑的地址
//	private static String selectGoodsInfoURL = "http://192.168.1.93:8077/api/product/searchAll.html";//先启动Tomcat(可用)内网访问赵瞳电脑的地址
//	private static String selectGoodsInfoURL = "http://localhost:8078/api/product/searchAll.html"; //先启动Tomcat(可用)
	/*private static String selectGoodsInfoURL = "http://192.168.1.93:8077/api/product/searchAll.html";//先启动Tomcat(可用)内网访问赵瞳电脑的地址
	private static String sh_app_key = "88e1be7c8d153b7345078541e700c487"; // key(任意位数的英文，MD5加密后，取32位 小写)
	private static String sh_app_secrt = "3ae8e918825f1572f2dbb69deb5603e0"; //secret(任意位数的英文，MD5加密后，取32位 小写)
	private static Map<String,String> paramMap =null;
	public static void main(String[] args) throws ParseException{
		
		paramMap = new HashMap<String, String>(); //
		Calendar calendar = Calendar.getInstance();
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime())); // 每次发起请求都是最新时间
		paramMap.put("app_key", sh_app_key);//放在签名之前，签名需要request
		String request = "{\"pageIndex\":\""+1+"\",\"pageSize\":\""+10+"\",\"type\":\""+0+"\"}"; // 组装请求数据
		paramMap.put("request", request);//放在签名之前，签名需要request
		paramMap.put("sign", getSign());//获取签名
		
		String resultStr = HttpRequest.sendPost(selectGoodsInfoURL, paramMap);
		
		System.out.println(resultStr);
		
		
		
	}
	private static String getSign()
	{
		// 组装签名 顺序不能乱 MD5加密 大写 详细规则见文档
		String sign = "app_key=" + sh_app_key + "&request="
				+ paramMap.get("request") + "&timestamp="
				+ paramMap.get("timestamp") + "_";
		sign +=  sh_app_secrt;
		//System.out.println(sign);
		String resultMd5 =  HttpRequest.string2MD5(sign).toUpperCase();
		
		return resultMd5;
	}*/
}
 
