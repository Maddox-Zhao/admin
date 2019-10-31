package com.huaixuan.network.biz.service.autosyn.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.LogData;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.autosyn.ZhenPinSyncManager;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;
import com.zp.open.api.pub.constant.IParaNameConstant;
import com.zp.open.api.pub.exception.ZPException;
import com.zp.open.api.pub.response.DefaultResponse;
import com.zp.open.api.pub.util.JsonUtil;
import com.zp.open.api.sdk.client.DefaultZPClient;
import com.zp.open.api.sdk.request.ProductStore;
import com.zp.open.api.sdk.request.QryProductRequest;
import com.zp.open.api.sdk.request.TokenRequest;
import com.zp.open.api.sdk.request.UpdateProdStoreRequest;



/**
 * @author Mr_Yang   2016-3-22 下午03:35:19
 * 珍品库存对接 弃用
 **/


public class ZhenPinSyncManagerImple implements ZhenPinSyncManager
{

	private static String zhenPin_serverUrl = "http://zop.zhen.com/zpOpenPlatform";
	private static String zhenPin_accessToken_sh = null;
	private static String zhenPin_provider_sh = "243";
	private static String zhenPin_providerPwd_sh = "shangshangsp";
	
	private static String zhenPin_accessToken_hk = null;
	private static String zhenPin_provider_hk = "263";
	private static String zhenPin_providerPwd_hk = "shangshangsp";
	
	private static final String path = "d:/stock_log/";//记录库存的日志文件夹
	
	private static final String fileName = "zhenpin";
	
	private  Map<String,List<String>> zp_hk_repeatMap = new HashMap<String, List<String>>();//珍品香港有重复SKU的数据
	private  Map<String,List<String>> zp_sh_repeatMap = new HashMap<String, List<String>>();//珍品香港有重复SKU的数据
	
 
	
	@Autowired
	private AutoSyncDao autoSyncDao;
	
	/**
	 * 全量更新珍品库存  
	 * 1.先查询sku对应的库存
	 * 2.用珍品查询出来的outsku和我们的做对比 如果没有则更新为0 有则更新为我们的库存
	 */
	@Override
	public void updateCanSaleProduct()
	{
		setZhenPinToken(); //设置token
		
		updateCanSaleProductByType("sh");//更新上海库存
		updateCanSaleProductByType("hk");//更新香港库存

	}

	private void updateCanSaleProductByType(String type)
	{
		setZhenPinToken(); //设置token
		String serverUrl = zhenPin_serverUrl;
		String accessToken = zhenPin_accessToken_sh;
		String provider = zhenPin_provider_sh;
		
		List<Long> siteList = new ArrayList<Long>();
		if("hk".equals(type))
		{
			accessToken = zhenPin_accessToken_hk;
			provider = zhenPin_provider_hk;
			
			//获取可售库存所需站点信息
			for(String idSite : MiniUiUtil.hkSite)
			{
				siteList.add(Long.valueOf(idSite));
			}
		}
		else
		{
			//获取可售库存所需站点信息
			for(String idSite : MiniUiUtil.shSite)
			{
				siteList.add(Long.valueOf(idSite));
			}
		}
		
		Map<String,Integer> canSaleStockMap = new HashMap<String, Integer>(); //key为我们sku  value为当前sku可售库存
		/*
		List<StockData> stockDataList = autoSyncDao.searchStockBySiteList(siteList);
		Map<String,Integer> canSaleStockMap = new HashMap<String, Integer>(); //key为我们sku  value为当前sku可售库存
		for(StockData s : stockDataList)
		{
			canSaleStockMap.put(s.getSku(), s.getNum());
		}
		*/
		
		//记录出现多次, 我们填写的sku在珍品有重复的数据(我们的1一个sku对应珍品的多个sku)
		Map<String,String> zhenpinMap = new HashMap<String, String>(); //用于记录该sku出现次数
		Map<String,List<String>> zhenpinListMap = new HashMap<String, List<String>>(); //如果出现次数大于1了 记录有重复sku的珍品sku和我们的sku对应关系
		
		DefaultZPClient client = new DefaultZPClient(serverUrl, accessToken, provider);
		int pageNo = 1;
		int pageSize = 100;
		
		while(true)
		{
			QryProductRequest request = new QryProductRequest();
			request.setPage(pageNo+"");
			request.setPageSize(pageSize+"");
			request.setSortType("1");
			request.setStatus("0,1");//可售和下架产品0 下架  1可售
			try 
			{
				DefaultResponse zpResponse = client.execute(request);
				JSONObject jsonObj = new JSONObject(zpResponse.getResult());
				
				int totalCnt = jsonObj.getInt("totalCount");
				if(totalCnt == 0) break;
				JSONArray productInfoList = new JSONArray(jsonObj.getString("productInfo"));
				for(int index = 0; index < productInfoList.length(); index++)
				{
					JSONObject product = new JSONObject(productInfoList.getString(index));
					String outSku = product.getString("outSku");
					String zhenpinSku = product.getString("sku");
					if(StringUtil.isBlank(outSku)) continue; //没有设置sku 不更新
					String productSpecId = product.getString("productSpecId");
					Integer nowNum = canSaleStockMap.get(outSku);
					if(null == nowNum) nowNum = 0; //没有获取到库存 库存为0
					
					//更新库存
					//updateProdStore(client,productSpecId,nowNum,outSku,type);

					
					//下面是处理珍品sku和我们sku有重复项
					//记录在zhenpinListMap中 最后写入文件  在做单个更新的时候通过珍品sku来更新 不通过outsku来更新
					if(zhenpinMap.get(outSku) != null) //该sku已经出现过  记录在map中 最后记录在文件
					{
						String firstZhenPintSku = zhenpinMap.get(outSku); //第一次出现的珍品的sku
						if(zhenpinListMap.get(outSku) == null) //没有记录过
						{
							List<String> outList = new ArrayList<String>();
							if(!zhenpinSku.equalsIgnoreCase(firstZhenPintSku)) //当前珍品sku和第一出现的珍品sku不一样
							{
								outList.add(firstZhenPintSku); //第一次的珍品sku
								outList.add(zhenpinSku); //这次出现的珍品sku
							}
							zhenpinListMap.put(outSku, outList);
						}
						else
						{
							zhenpinListMap.get(outSku).add(zhenpinSku);
						}
						
					}
					else
					{
						zhenpinMap.put(outSku, zhenpinSku);//记录该sku第一次出现
						
					}
					

				}

				int totalPage = (totalCnt/pageSize)+1;
				pageNo++;
				if(pageNo > totalPage) break; //没有数据 跳出
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		
		
	   //写入到文件
	   writeRepeatData2File(type,zhenpinListMap);
	   
	   System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " zhnepin " + type);
	   
	}
	
	
	//珍品token
	private  void setZhenPinToken() 
	{
		TokenRequest request = new TokenRequest(); 
		request.setAppKey(zhenPin_provider_sh);
		request.setProvierSecret(zhenPin_providerPwd_sh);
		try 
		{
			DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl, null,zhenPin_provider_sh);
			DefaultResponse zpResponse = client.execute(request);
			HashMap<String, Object> map = JsonUtil.json2Map(zpResponse.getResult());
			zhenPin_accessToken_sh = map.get(IParaNameConstant.REQUEST_TOKEN_NAME).toString();
		} 
		catch (ZPException e) 
		{
			e.printStackTrace();
		}
		
		
		request = new TokenRequest();
		request.setAppKey(zhenPin_provider_hk);
		request.setProvierSecret(zhenPin_providerPwd_hk);
		try 
		{
			DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl, null,zhenPin_provider_hk);
			DefaultResponse zpResponse = client.execute(request);
			HashMap<String, Object> map = JsonUtil.json2Map(zpResponse.getResult());
			zhenPin_accessToken_hk = map.get(IParaNameConstant.REQUEST_TOKEN_NAME).toString();
		} 
		catch (ZPException e) 
		{
			e.printStackTrace();
		}

	}
	
	
	
  //更新库存
  private void updateProdStore(DefaultZPClient client,String productSpecID,int nowNum,String ourSku,String type)
  {
		UpdateProdStoreRequest request = new UpdateProdStoreRequest();
		List<ProductStore> productStoreList = new ArrayList<ProductStore>();
		ProductStore prodStore = new ProductStore();
		prodStore.setFreeStock(nowNum+"");
		prodStore.setProductSpecID(productSpecID);
		productStoreList.add(prodStore);
		request.setProductStoreList(productStoreList);
		try
		{
			DefaultResponse zpResponse = client.execute(request);
			JSONArray jsonaArray = new JSONArray(zpResponse.getResult());
			Map<String,Object> map = JsonUtil.json2Map(jsonaArray.get(0).toString());
			//记录更新日志
			Map<String, String> logMap = new HashMap<String, String>();
			logMap.put("sku", ourSku);
			logMap.put("psku", ourSku);
			logMap.put("name", "珍品");
			logMap.put("stock", nowNum+"");
			logMap.put("location", type);
			logMap.put("type", "success");
			if(map.get("code").toString().equals("200"))
			{
				 LogData logData = new LogData();
				 logData.setFileName(fileName);
				 logData.setType(type);
				 logData.setSku(ourSku);
				 logData.setNowNum(nowNum);
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
				 
			}
			else
			{
				 LogData logData = new LogData();
				 logData.setFileName(fileName+"_error");
				 logData.setType(type);
				 logData.setSku(ourSku);
				 logData.setError(map.get("codeMsg").toString());
				 HttpRequest.recodeUpdateLog(logData); //记录更新库存的错误日志文件
				 
				 logMap.put("type", "error");
				 logMap.put("error",map.get("codeMsg").toString());
			}
			autoSyncDao.addUpdateLog(logMap);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
}
	
	//将有重复sku的数据记录在文件
  private void writeRepeatData2File(String type,Map<String,List<String>> zhenpinListMap)
  {
	    Set<Entry<String,List<String>>> entry = zhenpinListMap.entrySet();
		Iterator<Entry<String,List<String>>> it =  entry.iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext())
		{
			Entry<String,List<String>> next = it.next();
			String ourSku = next.getKey();
			List<String> zhenPinSkuList = next.getValue();
			for(String s : zhenPinSkuList)
			{
				sb.append(s);
				sb.append(",");
				sb.append(ourSku);
				sb.append("\n");
			}
		}
		String repeatPath = path+type+"_repeat.txt";
		RandomAccessFile accessFile = null;
		try
		{
			accessFile = new RandomAccessFile(repeatPath,"rw");
			accessFile.seek(0);
			accessFile.write(sb.toString().getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
				if(accessFile != null)
				{
					try
					{
						accessFile.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
		}
		
  }

  
  /**
   * 更新有过库存改变的数据  暂时未用
   */
@Override
public void updateChangedStock(Map<String, Integer> needUpdateMap,String type)
{
	
	Map<String,List<String>> rePeatMap = getRepeat(type); //重复sku的
	if("sh".equals(type))
	{
		zp_sh_repeatMap = rePeatMap;
	}
	else
	{
		zp_hk_repeatMap = rePeatMap;
	}
	setZhenPinToken();//设置token
	
	
	
	
	//有库存改变的数据 查询平台sku 然后更新库存 记录日志
	Set<Entry<String,Integer>> set = needUpdateMap.entrySet();
	Iterator<Entry<String, Integer>> it2  = set.iterator();
	while(it2.hasNext())
	{
		Entry<String, Integer> e = it2.next();
		String sku = e.getKey();
		Integer num = e.getValue();
		if("".equals(sku) || null == sku) continue;
		
		//查询需要的map
		//Map<String,String> searchMap = new HashMap<String, String>();
		//searchMap.put("sku", sku);		
		//String huoHao = autoSyncDao.getHuoHaoByOurSku(searchMap);
		String huoHao = "";
		int beforNum = -1;
		//更新珍品库存  通过我们的sku更新
		updateZhenPinStock(sku,num,type,huoHao,beforNum);
	}
	
	
}


public void updateZhenPinStock(String sku ,Integer num,String type,String huoHao,Integer beforNum)
{

	UpdateProdStoreRequest request = new UpdateProdStoreRequest();
	List<ProductStore> productStoreList = new ArrayList<ProductStore>();
	
	 List<String> zpSkuList = null;
	//如果是有重复SKU 的  通过珍品的sku来更新
	 if("sh".equals(type))
	 {
		 zpSkuList = zp_sh_repeatMap.get(sku);
	 }
	 else
	 {
		 zpSkuList = zp_hk_repeatMap.get(sku);
	 }
	 if(zpSkuList != null)
	 {
		 for(String zpSku : zpSkuList)
		 {
			ProductStore prodStore = new ProductStore();
			prodStore.setSku(zpSku); //设置珍品sku
			prodStore.setFreeStock(num+"");
			productStoreList.add(prodStore);
		 }
	 }
	 else//没有重复sku 通过供货商SKU来更新
	 {
		ProductStore prodStore = new ProductStore();
		prodStore.setOutSku(sku);
		prodStore.setFreeStock(num+"");
		productStoreList.add(prodStore);
	 }
	request.setProductStoreList(productStoreList);
	
	
	
	
	//默认为上海
	String accessToken = zhenPin_accessToken_sh;
	String provider = zhenPin_provider_sh;
	
	if("hk".equals(type))
	{
		accessToken = zhenPin_accessToken_hk;
		provider = zhenPin_provider_hk;
	}
	
	try 
	{
		DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl,accessToken, provider);
		DefaultResponse zpResponse = client.execute(request);
		JSONArray jsonaArray = new JSONArray(zpResponse.getResult());
		if(jsonaArray.length() <=  0) return;
		Map<String,Object> map = JsonUtil.json2Map(jsonaArray.get(0).toString());
		if(map.get("code").toString().equals("200")) //成功
		{
			 LogData logData = new LogData();
			 logData.setFileName(fileName);
			 logData.setBeforNum(beforNum);
			 logData.setNowNum(num);
			 logData.setSku(sku);
			 logData.setType(type);
			 logData.setHuohao(huoHao);
			 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
		}
		else
		{
			 //更新失败,审核未通过 没转到珍品正式库
			 LogData logData = new LogData();
			 logData.setFileName(fileName + "_error");
			 logData.setHuohao(huoHao+ " " +map.get("codeMsg").toString());
			 logData.setSku(sku);
			 logData.setType(type);
			 logData.setBeforNum(beforNum);
			 logData.setNowNum(num);
			 HttpRequest.recodeUpdateLog(logData); //记录更新库存的错误日志文件
 
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

}


//获取珍品有重复sku的数据
private static Map<String,List<String>> getRepeat(String type)
{
	Map<String,List<String>> map = new HashMap<String, List<String>>();
	File file = new File(path+type+"_repeat.txt");
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


/**
 * 单个产品更新库存
 */
@Override
public boolean updateStockByOurSkuAndPlatFormSku(String type, String platformSku, String ourSku, int nowNum)
{
 
	UpdateProdStoreRequest request = new UpdateProdStoreRequest();
	List<ProductStore> productStoreList = new ArrayList<ProductStore>();
	if(StringUtil.isBlank(ourSku)) return true;
	
	 List<String> zpSkuList = null;
	//如果是有重复SKU 的  通过珍品的sku来更新
	 if("sh".equals(type))
	 {
		 zpSkuList = zp_sh_repeatMap.get(ourSku);
	 }
	 else
	 {
		 zpSkuList = zp_hk_repeatMap.get(ourSku);
	 }
	 if(zpSkuList != null)
	 {
		 for(String zpSku : zpSkuList)
		 {
			ProductStore prodStore = new ProductStore();
			prodStore.setSku(zpSku); //设置珍品sku
			prodStore.setFreeStock(nowNum+"");
			productStoreList.add(prodStore);
		 }
	 }
	 else//没有重复sku 通过供货商SKU来更新
	 {
		ProductStore prodStore = new ProductStore();
		prodStore.setOutSku(ourSku);
		prodStore.setFreeStock(nowNum+"");
		productStoreList.add(prodStore);
	 }
	request.setProductStoreList(productStoreList);
	
	
	if(zhenPin_accessToken_hk == null || zhenPin_accessToken_sh == null)
	{
		setZhenPinToken();
	}
	
	//默认为上海
	String accessToken = zhenPin_accessToken_sh;
	String provider = zhenPin_provider_sh;
	
	if("hk".equals(type))
	{
		accessToken = zhenPin_accessToken_hk;
		provider = zhenPin_provider_hk;
	}
	boolean resultBoolean = false;
	//记录更新日志
	Map<String, String> logMap = new HashMap<String, String>();
	logMap.put("sku", ourSku);
	logMap.put("psku", ourSku);
	logMap.put("name", "珍品");
	logMap.put("stock", nowNum+"");
	logMap.put("location", type);
	logMap.put("type", "success");
	try 
	{
		DefaultZPClient client = new DefaultZPClient(zhenPin_serverUrl,accessToken, provider);
		DefaultResponse zpResponse = client.execute(request);
		JSONArray jsonaArray = new JSONArray(zpResponse.getResult());
		if(jsonaArray.length() <=  0) return true;
		Map<String,Object> map = JsonUtil.json2Map(jsonaArray.get(0).toString());
		
		if(map.get("code").toString().equals("200")) //成功
		{
			 LogData logData = new LogData();
			 logData.setFileName(fileName);
			 logData.setBeforNum(-1);
			 logData.setNowNum(nowNum);
			 logData.setSku(ourSku);
			 logData.setType(type);
			 HttpRequest.recodeUpdateLog(logData); //记录更新库存的日志文件
			 resultBoolean = true;
		}
		else
		{
			 //更新失败,审核未通过 没转到珍品正式库
			 LogData logData = new LogData();
			 logData.setFileName(fileName + "_error");
			 logData.setHuohao(map.get("codeMsg").toString());
			 logData.setSku(ourSku);
			 logData.setType(type);
			 logData.setBeforNum(-1);
			 logData.setNowNum(nowNum);
			 HttpRequest.recodeUpdateLog(logData); //记录更新库存的错误日志文件
			 logMap.put("type", "error");
			 logMap.put("error",map.get("codeMsg").toString());
			 resultBoolean = true;
		}
		
		
	}
	catch (Exception e)
	{
		 logMap.put("type", "error");
		 logMap.put("error",e.getMessage());
		 resultBoolean = false; 
	}
	//记录日志
	autoSyncDao.addUpdateLog(logMap);
	return resultBoolean;
}
	
	 public static void main(String[] args)
	{
		ZhenPinSyncManagerImple zp = new ZhenPinSyncManagerImple();
		zp.updateCanSaleProductByType("hk");
	}
}
 
