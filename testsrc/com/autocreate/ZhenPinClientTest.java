package com.autocreate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.zp.open.api.pub.constant.IParaNameConstant;
import com.zp.open.api.pub.exception.ZPException;
import com.zp.open.api.pub.response.DefaultResponse;
import com.zp.open.api.pub.util.JsonUtil;
import com.zp.open.api.sdk.client.DefaultZPClient;
import com.zp.open.api.sdk.request.ProductStore;
import com.zp.open.api.sdk.request.QryProdDetailRequest;
import com.zp.open.api.sdk.request.QryProdStoreRequest;
import com.zp.open.api.sdk.request.QryProductRequest;
import com.zp.open.api.sdk.request.SendOrderDetailRequest;
import com.zp.open.api.sdk.request.SendOrderRequest;
import com.zp.open.api.sdk.request.TokenRequest;
import com.zp.open.api.sdk.request.UpdateProdStoreRequest;

/**
 * 
 * description <br/>
 * date: 2015年10月21日 下午1:59:50 <br/>
 * 
 * @author sunshine
 * @version
 * @since JDK 1.7
 */
public class ZhenPinClientTest {
	//private static String serverUrl = "http://zoptest.zhen.com/zpOpenPlatform";
	private static String serverUrl = "http://zop.zhen.com/zpOpenPlatform";
	private static String accessToken = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKm35ovzOKAiFkDOjl1i7ATUYsiz\nO7LSVP3ea11WKVcTMxHvprQ8aMe54/CTvwJWlWXc1qRcA1cCSJef6esheT6ldY6TNG9qwf+iMfhd\njCbDwljHo65NoSBp4fGVgltTN3q6GsG1EXLA4kG2Xy+TUYDLDjrvtHoP9FK5H2ICrVCLAgMBAAEC\ngYBmfakbUNkcUxlvL43ntWpkjEHPg/WESuKKoPvjVLoIXI7VwQzCAU0heA+/4+lQpyr3ZcK1ZN+b\ndsSR1D92ADPv9jzC4xpNjJcd4gVpiwVmYGCnLtiwcxWy3cHA4XV2e9gDHyvSivEuuOf3tBaCByQd\nSS5Ge1WUHRucBhgVQilkYQJBANR0TIxlT3g4UJ4IkuosMiw8AhoEuO0ikpNk9cqTmlpH5HxgpNTB\n1IsZQm5aU9N+zJQcqkhsHnXIajYuifuzF7sCQQDMgTO+yDz6ccKsP2rk7zpeNUKO6OdDq5FnzAEB\nPt8vMIydbWmN33vjX4ubRBYXEWZBaY5dvIv+kBwDzBM+cJVxAkAhexJ1LaIQeA98WC2O6dgeUHak\nUwoxlLk0sTxq6Efy4lGHqFNRlBpCsYBek62SLvFehgXv09oZBbR3RW5JVDfxAkEAvrslyqKwlJfz\nC3keNiSUWHbt+6Fmx6tFMvyGV2YO+9EY5F6+Go672gUrjIUE4UDeENXk7LHEE4jOSBBWRl70UQJA\ncTrU9pZFtYMe7NtOPtwyIhSfeNWT88NZDl0IYqhCiymDifeDd7r65Yg8rS2Kwtg8+K5G/Lngvx/U\n556oG6vWsA==";
	private static String provider = "263";
	private static String providerPwd = "shangshangsp";

	public static void main(String[] args) {
		//testQryToken();
		testOrder();
		//testProductQryList();
		//testUpdateProdStore();
		//testProdDetailQryList();
		//testSendOrderQry();
		//testSendOrderQryDetail();
		//testQryProdStrote();
		
		//Map<String,List<String>>  map = getRepeat("hk");
		//System.out.println(map.get("9642016155674"));
	}

	protected static void testQryProdStrote() {

		QryProdStoreRequest request = new QryProdStoreRequest();
		request.setSkus("SS-5082016280510,WZ-201213");
		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl,
					accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}

	}

	protected static void testSendOrderQry() {
		SendOrderRequest request = new SendOrderRequest();
		// request.setSendOrderSn("1510292581003");
		// request.setStartDate("20150221143740");
		// request.setEndDate("20151030143740");
		// request.setSendOrderState("2,1,3,4");
		// request.setPage("1");
		// request.setPageSize("60");
		// request.setSortType("1");
		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl,
					accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}
	}

	protected static void testSendOrderQryDetail() {
		SendOrderDetailRequest request = new SendOrderDetailRequest();
		request.setSendOrderCode("");
		request.setSendOrderID("587");
		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl,
					accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}
	}

	protected static void testUpdateProdStore() {
		UpdateProdStoreRequest request = new UpdateProdStoreRequest();
		List<ProductStore> productStoreList = new ArrayList<ProductStore>();
		ProductStore prodStore = new ProductStore();
		//prodStore.setProductSpecID("206");
		//prodStore.setOutSku("4272017109359");
		prodStore.setFreeStock("0");
		prodStore.setOutSku("3082017137518");
		//prodStore.setProductSpecID("127174");
		/*
		productStoreList.add(prodStore);
		prodStore = new ProductStore();
		prodStore.setProductSpecID("207");
		//prodStore.setOutSku("5032016211946");
		prodStore.setFreeStock("25");
		*/
		productStoreList.add(prodStore);
		
		
		request.setProductStoreList(productStoreList);

		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl,
					accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
			JSONArray jsonaArray = new JSONArray(zpResponse.getResult());

			Map<String,Object> map = JsonUtil.json2Map(jsonaArray.get(0).toString());
			System.out.println(map.get("code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取token测试方法<br/>
	 * 
	 * @param client
	 * @author sunshine
	 * @date 2015年10月22日 下午9:14:16
	 */
	protected static void testQryToken() {
		TokenRequest request = new TokenRequest();
		request.setAppKey(provider);
		request.setProvierSecret(providerPwd);
		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl, null,
					provider);
			DefaultResponse zpResponse = client.execute(request);
			HashMap<String, Object> map = JsonUtil.json2Map(zpResponse
					.getResult());
			accessToken = map.get(IParaNameConstant.REQUEST_TOKEN_NAME)
					.toString();
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取产品列表<br/>
	 * 
	 * @param client
	 * @author sunshine
	 * @date 2015年10月22日 下午9:14:37
	 */
	protected static void testProductQryList() {
		DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken,
				provider);
		QryProductRequest request = new QryProductRequest();
		//request.setAddTimeStart("20160229000000");
		//request.setAddTimeEnd("20160229235959");
		request.setPage("500");
		request.setPageSize("10");
		request.setSortType("1");
		request.setStatus("0,1");
		try {
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取产品列表<br/>
	 * 
	 * @param client
	 * @author sunshine
	 * @date 2015年10月22日 下午9:14:37
	 */
	protected static void testProdDetailQryList() {
		DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken,provider);
		QryProdDetailRequest request = new QryProdDetailRequest();
		// request.setProductID("1059791");
		request.setSku("91602252789050");
		//request.setOutSku("9152017275856");
		request.setPageSize("60");
		request.setPage("1");
		request.setSortType("1");
		try {
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private static Map<String,List<String>>getRepeat(String type)
	{
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		File file = new File("d:/log/"+type+"_repeat.txt");
		if(!file.exists()) return map;
		FileReader fReader = null;
		BufferedReader bread = null;
		try
		{
			fReader = new FileReader(file);
			bread = new BufferedReader(fReader);
			String line = "";
			while((line = bread.readLine()) != null)
			{
			      String[] arr = line.split(",");
			      if(arr.length == 2)
			      {
			    	  String zp_sku = arr[0];
			    	  String sku = arr[1];
			    	  if(map.get(sku) != null)
			    	  {	
			    		  List<String> zpSkuList =  map.get(sku);
			    		  zpSkuList.add(zp_sku);
			    	  }
			    	  else
			    	  {
			    		  List<String>  list = new ArrayList<String>();
			    		  list.add(zp_sku);
			    		  map.put(sku, list);
			    	  }
			      }
			      
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
				try
				{
					if(fReader!=null)
						fReader.close();
					if(bread != null)
						bread.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		    
		}
		return map;
	}
	
	
	
	public static void  testOrder()
	{
		SendOrderRequest request = new SendOrderRequest();
		// request.setSendOrderCode("1510131830672");
		request.setStartDate("20160524081802");
		request.setEndDate("20160525103003");		
		request.setSendOrderState("4,1,3");
		request.setPage("1");
		request.setPageSize("100");		
		//request.setSortType("2342");
		try {
			DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken, provider);
			DefaultResponse zpResponse = client.execute(request);
			System.out.println(zpResponse.getResult());
		} catch (ZPException e) {
			e.printStackTrace();
		}

	}
}
