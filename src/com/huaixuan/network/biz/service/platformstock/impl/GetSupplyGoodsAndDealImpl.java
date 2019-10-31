/**
 * 
 */
package com.huaixuan.network.biz.service.platformstock.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.json.JsonArray;

import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.biz.domain.product.SupplyGood;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.GetSupplyGoodsAndDeal;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;

/**
 * @author TT
 * 
 */
public class GetSupplyGoodsAndDealImpl implements GetSupplyGoodsAndDeal {

	/*
	 * KEY ACCESSO : EpFF87Ibl9
http://185.58.119.177/velashopapi/Myapi/Productslist/GetProducts?DBContext=Default&CategoryId=&BrandId=&SeasonCode=021&StartIndex=1&EndIndex=100&key=EpFF87Ibl9

http://185.58.119.177/velashopapi/Myapi/Productslist/GetAllSeasonCode?DBContext=default&key =
String getSeasonCode ="Myapi/Productslist/GetAllSeasonCode?";
		getSeasonCode=url+getSeasonCode;
		System.out.println(getSeasonCode+"=================");
	 * 
	 */
	private static final String url="http://185.58.119.177/velashopapi/";
	private static final String KEY_ACCESSO ="EpFF87Ibl9";
	private static final String DBContext ="Default";
	private static final String CategoryId ="";
	private static final String BrandId ="";
	private static final String SeasonCode ="";
	
	@Override
	public void getSupplyGoodsToProject() {
		String getGoodsUrl = "Myapi/Productslist/GetProducts?";
		       getGoodsUrl = url+getGoodsUrl;
			
      Integer startIndex=1850;
      Integer EndIndex =1851;
     
      
//      while(true){
    	  TreeMap<String, String> map = new TreeMap<String, String>();
          map.put("DBContext", DBContext);
          map.put("key", KEY_ACCESSO);
     
          map.put("CategoryId", CategoryId);
          map.put("BrandId", BrandId);
          map.put("SeasonCode", SeasonCode);
          map.put("StartIndex", startIndex+"");
          map.put("EndIndex", EndIndex+"");
    	  String responseGoods = HttpRequest.sendGetMJson(getGoodsUrl, map);
    	  System.out.println(responseGoods);
    	  List<SupplyGood> lsg = new ArrayList<SupplyGood>();
    	  try {
    		 
			JSONObject jsons = new JSONObject(responseGoods);
			String num = jsons.getString("number");
			int totalCnt =0;
		System.out.println(num);
		
			JSONArray jsonArrays = jsons.getJSONArray("product");
			for(int i = 0; i < jsonArrays.length(); i++){
				
				JSONObject dataObject = new JSONObject(jsonArrays.get(i).toString());
				String productId = dataObject.getString("product_id");
		System.out.println(productId);
				String productName= dataObject.getString("product_name");
		System.out.println(productName);
				String items = dataObject.getString("items");
				JSONObject jsonsItems = new JSONObject(items);
				JSONArray jsonArrayItem = jsonsItems.getJSONArray("item");
		System.out.println(jsonArrayItem.length());
				for(int t=0;t<jsonArrayItem.length();t++){
					SupplyGood sg = new SupplyGood();
					sg.setProduct_id(productId);
					sg.setItem_id(productName);
					JSONObject dataItem = new JSONObject(jsonArrayItem.get(t).toString());		
					String itemId = dataItem.getString("item_id");
			System.out.println(itemId);
			sg.setItem_id(itemId);
					String barcode = dataItem.getString("barcode");
			sg.setBarcode(barcode);
			System.out.println(barcode);
			
			lsg.add(sg);
				}
				
			}
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
    	
    	  System.out.println(lsg.size());
    	  int totalCnt = lsg.size();
    	  String[] titleArr = new String[]{"product_id","product_name","item_id","barcode"}; 
    	  String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
  		  resultArr[0] = titleArr;
  		  for(int j=0;j<lsg.size();j++){
  			String[] rowArr = resultArr[j+1];   //第一行为title  不需要在设置
  			SupplyGood p = lsg.get(j);
  			rowArr[0] = p.getProduct_id();
			rowArr[1] = p.getProduct_name();
			rowArr[2] = p.getItem_id();
			rowArr[3] = p.getBarcode();
  		  }
  		  
//      }
     
      
	}

	public static void main(String[] args) {
		GetSupplyGoodsAndDealImpl gd = new  GetSupplyGoodsAndDealImpl();
		gd.getSupplyGoodsToProject();
	}
}
