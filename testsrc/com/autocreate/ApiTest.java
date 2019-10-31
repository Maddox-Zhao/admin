package com.autocreate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.crypto.Data;

import org.joda.time.DateTime;

import com.huaixuan.network.biz.domain.autosyn.OrderItem;
import com.huaixuan.network.biz.domain.autosyn.ShangPinSku;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;



/**
 * @author Mr_Yang   2015-9-17 上午10:48:34
 **/

public class ApiTest
{
	private String sh_app_key = "7068a39b704b6832b1ee90d9d2f013e2";
	private String sh_app_secrt = "3368b12faf2b59d96f01fe3dc664c9b5";
 
	private Map<String, String> paramMap = new TreeMap<String, String>(); //key按自然排序
	
	//测试地址
	private String selectGoodsInfoURLs = "http://140.207.52.210:8099/api/product/searchAll.html"; //查询商品信息地址
	private String getOrderInfoURL    ="http://140.207.52.210:8099/api/product/order/add.html"; //接收订单信息地址;
	//正式地址测试成功后提供
	private String selectGoodsInfoURL = "http://140.207.52.210:8080/api/product/searchAll.html"; 
	//private String selectGoodsInfoURL ="http://127.0.0.1:8073/api/product/searchAll.html"; //查询商品信息地址

	private int index  = 1;
	
	 
	 
	public Map<String,ShangPinSku> getGoodsInfo(Integer pageIndex,Integer pageSize,String type)
	{
		Map<String,ShangPinSku> resultMap = new HashMap<String, ShangPinSku>(); //
		Calendar calendar = Calendar.getInstance();
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime())); // 每次发起请求都是最新时间
		paramMap.put("app_key", sh_app_key);//放在签名之前，签名需要request
		String request = "{\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"type\":\""+type+"\"}"; // 组装请求数据
		paramMap.put("request", request);//放在签名之前，签名需要request
		paramMap.put("sign", getSign());//获取签名
		
		String resultStr = HttpRequest.sendPost(selectGoodsInfoURL, paramMap);
		System.out.println(index++ + " "  + resultStr);
		return resultMap;
	}
	
	
	
	
	public Map<String,ShangPinSku> getOrderInfo(String orderId,String status, String name, String mobile, String country,
			String province,String city, String district, String streetAddress,String zipCode,
			List<String> orderList ,Double totalPrice,
			Double discountPrice,Double freight,Date placedTime)
	{
		Map<String,ShangPinSku> resultMap = new HashMap<String, ShangPinSku>(); //
		Calendar calendar = Calendar.getInstance();
		paramMap.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime())); // 每次发起请求都是最新时间
		paramMap.put("app_key", sh_app_key);//放在签名之前，签名需要request
		String request = "[{\"skuId\":\""+"19044967"+"\",\"merchantSkuId\":\""+"4462013189688"+"\",\"quantity\":\""+"1"+"\",\"name\":\""+"潘多拉"+"\"}]"; // 组装请求数据
//		for(OrderItem orderItem:orderItemlist){
//			request= request+"{\"skuId\":\""+orderItem.getSkuId()+"\"}";
//		}
//		request=request+"]";
		paramMap.put("request", request);//放在签名之前，签名需要request
		paramMap.put("sign", getSign());//获取签名
		paramMap.put("orderId", orderId);
		paramMap.put("status", status);
		paramMap.put("name", name);
		paramMap.put("mobile",mobile);
		paramMap.put("country", country);
		paramMap.put("province", province);
		paramMap.put("city", city);
		paramMap.put("district", district);
		paramMap.put("streetAddress", streetAddress);
		paramMap.put("zipCode", zipCode);
		paramMap.put("totalPrice", totalPrice+"");
		paramMap.put("discountPrice", discountPrice+"");
		paramMap.put("freight", freight+"");
		paramMap.put("placedTime", placedTime+"");
		String resultStr = HttpRequest.sendPost(getOrderInfoURL, paramMap);
		try {
			JSONObject good = new JSONObject(resultStr);
//			JSONObject gooditem = good.getJSONObject("response");
			String  responseMsg = good.getString("responseMsg");
			System.out.println(good);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//System.out.println(index++ + " "  + resultStr);
		return resultMap;
	}
	
	
	
	//组装签名  通过appkey reqeust timestamp app_secrt组装
	private String getSign()
	{
		// 组装签名 顺序不能乱 MD5加密 大写 详细规则见文档
		String sign = "app_key=" + sh_app_key + "&request="
				+ paramMap.get("request") + "&timestamp="
				+ paramMap.get("timestamp") + "_";
		sign +=  sh_app_secrt;
		//System.out.println(sign);
		String resultMd5 =  HttpRequest.string2MD5(sign).toUpperCase();
		
		return resultMd5;
	}
	
	public static void main(String[] args)
	{
		//ApiTest apiTest = new ApiTest();
		//apiTest.getGoodsInfo(1, 50, "0");
//		List<String> list = new ArrayList<String>();
//		String skuid ="19044967";
//		String merchantSkuId = "4462013189688";
//		String quantity = "1";
//		String name = "panduola";
//		list.add(skuid);
//		list.add(merchantSkuId);
//		list.add(quantity);
//		list.add(name);
//		apiTest.getOrderInfo("123", "1", "chengbi", "", "", "", "", "", "", "", list, 0.0, 0.0, 0.0, new Date());
//		
	}
	 
	
}
 
