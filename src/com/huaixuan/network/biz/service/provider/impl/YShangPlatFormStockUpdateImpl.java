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
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;

//import com.alibaba.fastjson.JSON;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangImgeDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailYShangDao;
import com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsXiYouLogDao;
import com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.provider.OrderItemDto;
import com.huaixuan.network.biz.domain.provider.OrderItemDtoChild;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShangImge;
import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsXiYouLog;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsYShangLog;
import com.huaixuan.network.biz.domain.provider.ProviderYShangPage;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.provider.YShangPlatFormStockUpdate;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author TT
 * 
 */
@Service("YShangPlatFormStockUpdateImpl")
public class YShangPlatFormStockUpdateImpl implements YShangPlatFormStockUpdate {
	                               //http://39.105.230.173:8080/router/rest?
	/*private static final String url="http://39.105.230.173:8080/router/rest?";
	private static final String app_key = "xmW6rKJpRyo=";
	private static final String app_secret = "GSOtqeXxKWgd9kz+LPYb8J92QHNU1d47YzHP4777GhQ=";*/
	
	
	
	private static final String url="https://api.winshine.shop/router/rest?";
	private static final String app_key = "hzAzyPj3/EM=";
	private static final String app_secret = "sJkS76FDhESeesLwz/nxGnR+zKnHQskmPA0uWV+17C4=";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private ProvideGoodsYShangUpdateDao provideGoodsYShangUpdateDao;
	
	@Autowired
	private ProvideGoodsYShangImgeDao provideGoodsYShangImgeDao;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private ProvideOrderDetailDao provideOrderDetailDao;
	
	@Autowired
	private ProvideOrderWaybillDetailDao provideOrderWaybillDetailDao;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	@Autowired
	private ProvideUpdateGoodsXiYouLogDao provideUpdateGoodsXiYouLogDao;
	
	@Autowired
	private ProvideOrderWaybillDetailYShangDao provideOrderWaybillDetailYShangDao;
	
	@Autowired
	private ProvideUpdateGoodsYShangLogDao provideUpdateGoodsYShangLogDao;
	
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	protected Log log = LogFactory.getLog(this.getClass());
	/*  
	 * app_key=xmW6rKJpRyo=&currPage=1&data=json&method=yshang.wdk.trade.products.search&pageSize=10
	 * &sign=058430D924CDD7D2C8205814CE31A6E4&timestamp=2019-06-05 14:19:20&v=1.1
	 * 
	 * 
	 * /* 供应商_skuId 
        * 10833_123456789
		833_123456789
								 
	 */
	@Override
	public void getYShangProducts() {
		int errorCnt = 1;
		String path = "e:/upload/provide/yshang/";
		System.out.println(df.format(new Date()) + " sync getYShangProducts  start");
		long startTime = System.currentTimeMillis();
     TreeMap<String, String> map = new TreeMap<String, String>();
     TreeMap<String, String> mapJson = new TreeMap<String, String>();
     
     TreeMap<String, String> mapDetai = new TreeMap<String, String>();
     
     Map<String,String> mapEntity = new HashMap<String, String>();
//     
     
     List<ProvideGoodsYShang> pgysgoods = provideGoodsYShangUpdateDao.selectYShangEntityByMap(mapEntity);
     List<String> pgs = new ArrayList<String>();
     List<String> pgsOurSku = new ArrayList<String>();
     if(pgysgoods!=null && pgysgoods.size()>0){
    	 for(ProvideGoodsYShang pg:pgysgoods){
    		 String skuid = pg.getSkuId();
    		 String ourSku = pg.getOurSku();
    		 pgs.add(skuid);
    		 pgsOurSku.add(ourSku);
    	 }
     }
//     System.out.println(pgs.size()+"============="+pgsOurSku.size());
     //获取所有的hx_stock_update表中的yshang_sku_id，放进集合
     Map<String,String> searchMap = new  HashMap<String, String>();
     searchMap.put("provideisnotnull", "yshang_sku_id");
     List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(searchMap);
     List<String> pgsYShangSkuId = new ArrayList<String>();
     if(sulist!=null && sulist.size()>0){
    	 for(StockUpdate su:sulist){
    		 String yshshuid = su.getYshangSkuId();
    		 pgsYShangSkuId.add(yshshuid);
    	 }
     }
     
           ProviderYShangPage pysp = provideGoodsYShangUpdateDao.selectYShangPage();
            int page = pysp.getPage();
            int needpage = pysp.getNeedPage();
			String method ="yshang.wdk.trade.products.search";
			String methodDetail ="yshang.wdk.trade.products.detail";
//			System.out.println(pysp.getPage());
			int currPage = pysp.getPage();   
			if(currPage==0){
				currPage=1;
			}
			int pageSize = 100;
			while(true){
				String nowTime = sdf.format(new Date());
				//系统需要参数
				map.put("method", method);
				map.put("app_key", app_key);
				map.put("secret", app_secret);
				map.put("v", "1.0"); 
				map.put("timestamp", nowTime);//调用方法
				//业务参数
				mapJson.put("currPage", currPage+"");
				mapJson.put("pageSize", pageSize+"");
				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(mapJson);
				map.put("data", jsonObject.toString());
				//公共参数和业务参数一起参与签名
				String sign = getSign(map);
				map.put("sign", sign);
				List<ProvideGoodsYShangImge> pgyspList = new ArrayList<ProvideGoodsYShangImge>();
				List<ProvideGoodsYShang> pgysList = new ArrayList<ProvideGoodsYShang>();
				String response = HttpRequest.sendPostNoSecret(url, map);	
//				System.out.println(response);
				String maxPage = "";
				String skuIDs = "";
				try {
					JSONObject jsonObj = new JSONObject(response);
					
					String isSuccess = jsonObj.getString("success");
					String data = jsonObj.getString("data");
					if(isSuccess.equals("0")){
						JSONObject dataObj = new JSONObject(data);
						maxPage = dataObj.getString("maxPage");
						JSONArray dataArray = dataObj.getJSONArray("datas");
						if(dataArray != null && dataArray.length()>0){
							for(int i=0;i<dataArray.length();i++){
								JSONObject dataObject = new JSONObject(dataArray.get(i).toString());
								String skuId = dataObject.getString("skuId");
								skuIDs +=","+skuId;
								String categoryId = dataObject.getString("categoryId"); //商品所属区域：0 大陆
								String source = dataObject.getString("source"); //商品来源 1:寄卖人、2:联营商家、3:自营
								String brandId = dataObject.getString("brandId");
								String areaType = dataObject.getString("areaType");
								
								if(pgs!=null && pgs.size()>=0 && pgs.contains(skuId)){
									continue;
								}
								
								//公共参数
								mapDetai.put("method", methodDetail);
								mapDetai.put("app_key", app_key);
								mapDetai.put("secret", app_secret);
								mapDetai.put("v", "1.0"); 
								mapDetai.put("timestamp", nowTime);
								
								//业务参数
								mapDetai.put("skuId", skuId);
							
								
								//公共参数和业务参数一起参与签名
								String signs = getSign(mapDetai);
								mapDetai.put("sign", signs);
								//獲取商品詳情
								String responseDe = HttpRequest.sendPostNoSecret(url, mapDetai);	
//								System.out.println(responseDe);
								
								
								try {
									JSONObject jsonDetail = new JSONObject(responseDe);
									String success = jsonDetail.getString("success");
									JSONObject dob = jsonDetail.getJSONObject("data");
									
									JSONObject dobf =dob.getJSONObject("productInfo"); //商品详情
									
									
									String brandChName = dobf.getString("brandChName"); //品牌名称
									String level = dobf.getString("level"); //商品等级 N-全新 AB-二手未拆封
									Double marketPrice = Double.parseDouble(dobf.getString("maketPrice"));//市场价
									Double settlePrice = Double.parseDouble(dobf.getString("settlePrice"));//结算价价格//结算价价格
									Double ourSalePrice = (double) Math.round(settlePrice/0.65);
//									System.out.println(skuId);
						/*if(ourSalePrice>marketPrice){
							continue;
						}*/
//									String source = dobf.getString("source"); //商品来源：2 联营
									String categoryName = dobf.getString("categoryName"); //类别名称
//									  String productImgs = dobf.getString("productImgs");  //其他商品图片
									String productName = dobf.getString("productName"); //商品标题
									
									String sellStatus = dobf.getString("sellStatus"); //销售状态 1-可销售 2-不可销售
									
									String orgCode = dobf.getString("orgCode");
//									String areaType = dobf.getString("areaType");
//									String brandId = dobf.getString("brandId");
									String mainImgUrl = dobf.getString("mainImgUrl"); //商品主图
									String model = dobf.getString("model");//商品型号
									String spuId = dobf.getString("spuId");//商品 SpuID
									String isExchange = dobf.getString("isExchange"); //是否支持退换货 ０－不支持 １－支持
//									String categoryId = dobf.getString("categoryId");
//									String skuId = dobf.getString("skuId");
									
									//获取价格和库存
									/*String stockPrice = getYShangStock(skuId);
									String[] sp = stockPrice.split("_");
									String quantity = sp[0]; //库存
									String price = sp[1]; //当前结算价
									Double salePrice = Double.parseDouble(price);*/
									
									
									
									//獲取尺碼和顏色
									String spec = dobf.getString("spec"); //商品规格 尺码: 欧码 35.8 颜色: 浅蓝色
									String color ="";
									String size = "";
									JSONArray arraySpec = new JSONArray(spec);
									for(int t=0;t<arraySpec.length();t++){
										JSONObject jsonString = new JSONObject(arraySpec.getString(t).toString());
										String specIsAffectImg = jsonString.getString("specIsAffectImg"); //0：尺寸，1：顏色
//										String name = jsonString.getString("name"); //顏色或尺寸
										String value = jsonString.getString("value"); //值
										if(specIsAffectImg.equals("1")){
											color = value;
										}else if(specIsAffectImg.equals("0")){
											size = value;
										}
										
									}
									
									//形成尚上的唯一标识和银泰唯一标识一一对应
									String ourSku = createOurSku(skuId);
									
									if(pgsOurSku!=null && pgsOurSku.size()>=0 && pgsOurSku.contains(ourSku)){
										continue;
									}
									//获取主图的地址(mainImgUrl)，并下载图片到本地
									String mainPic = "provide/yshang/"+ourSku+"_0.jpg";
//									if(StringUtils.isNotBlank(mainImgUrl)){
										downloadPicture(mainImgUrl, path, ourSku, "0", 0);
//									}
									
									
									
									//获取图片的地址,转换成本地的地址，存入数据库(所有的图片原地址和转换后的本地地址都存入数据库，图片值只下载xxxxxxxx0.jpg,其他的在页面手动下载)
									String productDetail = dobf.getString("productDetail");//商品详情:type: 0 图片 1 文本
									int num = 0;
									if(!productDetail.equals("null")){
									JSONArray imageArray = new JSONArray(productDetail);
									if(imageArray!=null && imageArray.length()>0){
										for(int t=0;t<imageArray.length();t++){
											JSONObject dataAssJSONT = new JSONObject(imageArray.get(t).toString());
											String type = dataAssJSONT.getString("type");
											String yshangImage = dataAssJSONT.getString("info");
											if(type.equals("1")){
												continue;
											}
											String imagePic = "provide/yshang/"+ourSku+"_"+(t+1)+".jpg"; //本地圖片地址
											ProvideGoodsYShangImge pgysp = new ProvideGoodsYShangImge();
											pgysp.setSkuId(skuId);
								            pgysp.setOurSku(ourSku);
								            pgysp.setType(type);
								            pgysp.setYshangImage(yshangImage);
								            pgysp.setImagePic(imagePic);
								            pgyspList.add(pgysp); 
								            num=t+1;
										}
										
									}
									}
									
									
									String productImgs = dobf.getString("productImgs");  //其他商品图片
									 if(!productImgs.equals("null") && !productImgs.equals("{}")){
										 Map mapTypes = net.sf.json.JSONObject.fromObject(productImgs);
										 for (Object mapi : mapTypes.entrySet()){ 
											 ProvideGoodsYShangImge pgysp = new ProvideGoodsYShangImge();
											    String type = (String) ((Map.Entry)mapi).getKey(); //图片类型数字
									            int nut = Integer.parseInt(type);//productImgs紧接productDetail后的图片，排数字
									            String yshangImage= (String) ((Map.Entry)mapi).getValue();//云尚圖片的地址
									            String imagePic = "provide/yshang/"+ourSku+"_"+(num+nut)+".jpg"; //本地圖片地址
									            
									            pgysp.setSkuId(skuId);
									            pgysp.setOurSku(ourSku);
									            pgysp.setType(type);
									            pgysp.setYshangImage(yshangImage);
									            pgysp.setImagePic(imagePic);
									            pgyspList.add(pgysp);
									     }
										//将mainUrl也插入yshangImage表
										 ProvideGoodsYShangImge pgyspp = new ProvideGoodsYShangImge();
										    pgyspp.setSkuId(skuId);
								            pgyspp.setOurSku(ourSku);
								            pgyspp.setType("0");
								            pgyspp.setYshangImage(mainImgUrl);
								            pgyspp.setImagePic(mainPic);
								            pgyspList.add(pgyspp); 
									 }
									 
//									 System.out.println(skuId);
									 
						
									 ProvideGoodsYShang pgys = new ProvideGoodsYShang();
									 pgys.setSkuId(skuId);
									 pgys.setOurSku(ourSku);
									 pgys.setSpuId(spuId);
						 			
									 pgys.setStock(0);
									 pgys.setColor(color);
									 pgys.setSize(size);
									 pgys.setModel(model);
									 
									 pgys.setProductName(productName);
									 pgys.setBrandChname(brandChName);
									 pgys.setBrandId(brandId);
									 pgys.setCategoryId(categoryId);
									 
									 pgys.setCategoryName(categoryName);
									 pgys.setMarketPrice(marketPrice);
									 pgys.setSettlePrice(settlePrice);
									 pgys.setOurPrice(ourSalePrice);
									 
									 pgys.setSource(Integer.parseInt(source));
									 pgys.setAreaType(Integer.parseInt(areaType));
									 pgys.setSellStatus(Integer.parseInt(sellStatus));
									 pgys.setIsExchange(Integer.parseInt(isExchange));
									 
									 pgys.setMainImg(mainPic);
									 pgys.setLevel(level);
									 pgysList.add(pgys);
									                                                          
								} catch (Exception e) {
									log.error(e.getMessage());
									if(errorCnt++ > 5)
									{
										e.printStackTrace();
										System.out.println("yushang sysc GetYShangProducts error over 5 cnt JSONException");
										break;
									}
								}
							}
							
							
							
							if(pgysList.size()>0){
//								System.out.println(pgysList.size());
								//得到的新数据的sku_id,求得这些skku_id 的库存，待插入新数据后，插入库存
							    skuIDs = skuIDs.substring(1);
//						System.out.println(skuIDs);
								Map updateMap = new HashMap();
								//新数据的库存放入了skuIdLIst中
								List<ProvideGoodsYShang> skuIdLIst = getYShangBatchStockPrice(skuIDs);
								TreeMap<String,Integer> tm = new TreeMap<String, Integer>();
//						        String pp="";
								if(skuIdLIst!=null && skuIdLIst.size()>0){
									for(ProvideGoodsYShang pgSku:skuIdLIst){
										String skuid = pgSku.getSkuId();
										Integer stockS= pgSku.getStock();
										tm.put(skuid+"", stockS);
//										pp +=skuid+","+stockS+";";
									}
//									System.out.println(pp);
//						System.out.println(tm.size());
									
									  //插入的数据是没有库存的
									   provideGoodsYShangUpdateDao.insertProvideGoodsYShang(pgysList);
									   //将刚刚插入的数据，更新上库存
									   if(skuIdLIst!=null && skuIdLIst.size()>0){
											updateMap.put("list", skuIdLIst);
											provideGoodsYShangUpdateDao.updateGoodsYShangByList(updateMap);
										}
									   
										 if(pgyspList!=null && pgyspList.size()>0){
											 provideGoodsYShangImgeDao.insertGoodsYShangImge(pgyspList);
										 }
									 
									  //将新增的商品插入hx_stock_update
								 	  //循环新插入到provide_goods_yshang的数据，拿出skuId查询hx_stock_update没有的这个的，新插入一条
									 for(int i=0;i<pgysList.size();i++){
										   String yshangSkuId = pgysList.get(i).getSkuId();
										   
											String ourSku = pgysList.get(i).getOurSku();
											if(pgsYShangSkuId!=null && pgsYShangSkuId.size()>=0 && pgsYShangSkuId.contains(yshangSkuId)){
												continue;
											}

											int num =0;
											if(tm!=null && tm.size()>0){
												  num = tm.get(yshangSkuId);
											} 
						        			 
						        			StockUpdate su = new StockUpdate();
					        				su.setSku(ourSku);
					        				su.setNowStockNum(num);
					        				su.setLastUpdateStockNum(num);
					        				su.setYshangSkuId(yshangSkuId);
					        				su.setType("sh");
					        				platformStockUpdateDao.insertStockUpdate(su);
//							        		  }
										}
									
									
									
								}else{
									log.error("error=>currPage="+currPage);
								}
								/*else{
									//无任何意义，只是在getYShangBatchStockPrice出错时，可以进入catch，让page+1，让程序执行下去
									int no = tm.get(skuIdLIst.get(0).getStock());
								}*/
								
								
							 }
							
							
						}else{
								System.out.println(dataArray+"=====是null");
								System.out.println(df.format(new Date()) + " sync getYShangProducts  end");
								long endTime = System.currentTimeMillis();
								long a = endTime-startTime;
								long min = a/1000/60;
								System.out.println(df.format(new Date()) + " sync getYShangProducts  total min:" + min + "min");
								break;
							}
						
					}else{
						break;
					}
					
					Map<String,Integer> mapPage = new HashMap<String, Integer>();
					mapPage.put("id", 1);
					mapPage.put("page", currPage);
					//page是此次更新到了多少页
					//needpage是下次要更新的页数
					if(currPage>=needpage+page){
						provideGoodsYShangUpdateDao.updateYShangPage(mapPage);
						System.out.println(df.format(new Date()) + " sync getYShangProducts  end");
						long endTime = System.currentTimeMillis();
						long a = endTime-startTime;
						long minb = a/1000;
						System.out.println(df.format(new Date()) + " sync getYShangProducts  one min:" + minb + "秒");
						System.out.println("第=="+currPage+"==页");
						break;
					}
					
					currPage++;
					
					
					/*System.out.println(df.format(new Date()) + " sync getYShangProducts  end");
					long endTime = System.currentTimeMillis();
					long a = endTime-startTime;
					long min = a/1000/60;
					long minb = a/1000;
					System.out.println(df.format(new Date()) + " sync getYShangProducts  one min:" + minb + "秒");
					System.out.println(df.format(new Date()) + " sync getYShangProducts  one min:" + min + "min");*/
//					System.out.println("第=="+currPage+"==页"); //183
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
					if(errorCnt++ > 6)
					{
						e.printStackTrace();
						System.out.println("yushang sysc GetProducts error over 6 cnt JSONException");
						break;
					}else{
						log.error(e.getMessage()+"error=>currPage"+currPage);
						currPage++;
						continue;
					}
	
				}
				
				
			}
	}
	
	
	
	//形成我们自己的sku
	public String createOurSku(String skuId){
		skuId = skuId.replace("_", "");
		String result ="222";
//       System.out.println(skuId);
		int len = skuId.length();
		if(len>=10){
			result =result + skuId.substring(len-10);
		}else{
			int cha = 10-skuId.length();
			result = result + skuId.substring(len-cha)+skuId;
		}
		return result;
	}
	
	
	
	
	//獲取一個skuid的詳情
	public void getYShangDetail(String skuId){
		String path = "e:/upload/provide/yshang/";
		TreeMap<String, String> mapDetai = new TreeMap<String, String>();
		String methodDetail ="yshang.wdk.trade.products.detail";
		 String nowTime = sdf.format(new Date());
		mapDetai.put("method", methodDetail);
		mapDetai.put("app_key", app_key);
		mapDetai.put("secret", app_secret);
		mapDetai.put("v", "1.0"); 
		mapDetai.put("timestamp", nowTime);
		
		//业务参数
		mapDetai.put("skuId", skuId);
	
		
		//公共参数和业务参数一起参与签名
		String signs = getSign(mapDetai);
		mapDetai.put("sign", signs);
		//獲取商品詳情
		String responseDe = HttpRequest.sendPostNoSecret(url, mapDetai);	
//		System.out.println(responseDe);
		
		List<ProvideGoodsYShangImge> pgyspList = new ArrayList<ProvideGoodsYShangImge>();
		List<ProvideGoodsYShang> pgysList = new ArrayList<ProvideGoodsYShang>();
		try {
			JSONObject jsonDetail = new JSONObject(responseDe);
			String success = jsonDetail.getString("success");
			JSONObject dob = jsonDetail.getJSONObject("data");
			
			JSONObject dobf =dob.getJSONObject("productInfo"); //商品详情
			
			
			String brandChName = dobf.getString("brandChName"); //品牌名称
			String level = dobf.getString("level"); //商品等级 N-全新 AB-二手未拆封
			Double settlePrice = Double.parseDouble(dobf.getString("settlePrice"));//结算价价格
	String source = dobf.getString("source"); //商品来源：2 联营
			String categoryName = dobf.getString("categoryName"); //类别名称
			 
			String productName = dobf.getString("productName"); //商品标题
			
			
			
			//sellStatus和 salePrice应相同
			String sellStatus = dobf.getString("sellStatus"); //销售状态 1-可销售 2-不可销售  
			Double marketPrice = Double.parseDouble(dobf.getString("maketPrice"));//市场价
			String orgCode = dobf.getString("orgCode");
	String areaType = dobf.getString("areaType");
	String brandId = dobf.getString("brandId");
			String mainImgUrl = dobf.getString("mainImgUrl"); //商品主图
			String model = dobf.getString("model");//商品型号
			String spuId = dobf.getString("spuId");//商品 SpuID
			String isExchange = dobf.getString("isExchange"); //是否支持退换货 ０－不支持 １－支持
	String categoryId = dobf.getString("categoryId");
//	String skuId = dobf.getString("skuId");
			
			//获取价格和库存
			String stockPrice = getYShangStock(skuId);
			String[] sp = stockPrice.split("_");
			String quantity = sp[0]; //库存
			String price = sp[1]; //当前结算价
			Double salePrice = Double.parseDouble(price);
			//獲取尺碼和顏色
			String spec = dobf.getString("spec"); //商品规格 尺码: 欧码 35.8 颜色: 浅蓝色
			String color ="";
			String size = "";
			JSONArray arraySpec = new JSONArray(spec);
			for(int i=0;i<arraySpec.length();i++){
				JSONObject jsonString = new JSONObject(arraySpec.getString(i).toString());
				String specIsAffectImg = jsonString.getString("specIsAffectImg"); //0：尺寸，1：顏色
				String name = jsonString.getString("name"); //顏色或尺寸
				String value = jsonString.getString("value"); //值
				if(specIsAffectImg.equals("1")){
					color = value;
				}else if(specIsAffectImg.equals("0")){
					size = value;
				}
				
			}
			
			//形成尚上的唯一标识和银泰唯一标识一一对应
			String ourSku = createOurSku(skuId);
			
			//获取主图的地址(mainImgUrl)，并下载图片到本地
			String mainPic = "provide/yshang/"+ourSku+"_0.jpg";
			downloadPicture(mainImgUrl, path, ourSku, "0", 0);
			
			
			//获取图片的地址,转换成本地的地址，存入数据库
			
			String productDetail = dobf.getString("productDetail"); 
			int num = 0;
			if(!productDetail.equals("null")){
			JSONArray imageArray = new JSONArray(productDetail);
			if(imageArray!=null && imageArray.length()>0){
				for(int t=0;t<imageArray.length();t++){
					JSONObject dataAssJSONT = new JSONObject(imageArray.get(t).toString());
					String type = dataAssJSONT.getString("type");
					String yshangImage = dataAssJSONT.getString("info");
					if(type.equals("1")){
						continue;
					}
					String imagePic = "provide/yshang/"+ourSku+"_"+(t+1)+".jpg"; //本地圖片地址
					ProvideGoodsYShangImge pgysp = new ProvideGoodsYShangImge();
					pgysp.setSkuId(skuId);
		            pgysp.setOurSku(ourSku);
		            pgysp.setType(type);
		            pgysp.setYshangImage(yshangImage);
		            pgysp.setImagePic(imagePic);
		            pgyspList.add(pgysp); 
		            num=t+1;
				}
				
			}
			}
//			System.out.println(num+"==============num");
			
			
			
			 String productImgs = dobf.getString("productImgs");  //其他商品图片
//			System.out.println(productImgs);
			 if(!productImgs.equals("null") && !productImgs.equals("{}")){
				 Map mapTypes = net.sf.json.JSONObject.fromObject(productImgs);
				 for (Object map : mapTypes.entrySet()){ 
					 ProvideGoodsYShangImge pgysp = new ProvideGoodsYShangImge();
			            String type = (String) ((Map.Entry)map).getKey(); //图片类型数字
			            int nut = Integer.parseInt(type);//productImgs紧接productDetail后的图片，排数字
			            String yshangImage= (String) ((Map.Entry)map).getValue();//云尚圖片的地址
			            String imagePic = "provide/yshang/"+ourSku+"_"+(num+nut)+".jpg"; //本地圖片地址
			            if(StringUtils.isBlank(yshangImage) || StringUtils.isBlank(type)){
							continue;
						}
			            pgysp.setSkuId(skuId);
			            pgysp.setOurSku(ourSku);
			            pgysp.setType(type);
			            pgysp.setYshangImage(yshangImage);
			            pgysp.setImagePic(imagePic);
			            pgyspList.add(pgysp); 
			     }
				 //将mainUrl也插入yshangImage表
				 ProvideGoodsYShangImge pgyspp = new ProvideGoodsYShangImge();
				    pgyspp.setSkuId(skuId);
		            pgyspp.setOurSku(ourSku);
		            pgyspp.setType("0");
		            pgyspp.setYshangImage(mainImgUrl);
		            pgyspp.setImagePic(mainPic);
		            pgyspList.add(pgyspp); 
			 }
			 
Double ourSalePrice = (double) Math.round(settlePrice/0.65);
			 ProvideGoodsYShang pgys = new ProvideGoodsYShang();
			 pgys.setSkuId(skuId);
			 pgys.setOurSku(ourSku);
			 pgys.setSpuId(spuId);
 			
			 pgys.setStock(Integer.parseInt(quantity));
			 pgys.setColor(color);
			 pgys.setSize(size);
			 pgys.setModel(model);
			 
			 pgys.setProductName(productName);
			 pgys.setBrandChname(brandChName);
			 pgys.setBrandId(brandId);
			 pgys.setCategoryId(categoryId);
			 
			 pgys.setCategoryName(categoryName);
			 pgys.setMarketPrice(marketPrice);
			 pgys.setSettlePrice(settlePrice);
			 pgys.setOurPrice(ourSalePrice);
			 
			 pgys.setSource(Integer.parseInt(source));
			 pgys.setAreaType(Integer.parseInt(areaType));
			 pgys.setSellStatus(Integer.parseInt(sellStatus));
			 pgys.setIsExchange(Integer.parseInt(isExchange));
			 
			 pgys.setMainImg(mainPic);
			 pgys.setLevel(level);
			 pgysList.add(pgys);
			 
			 if(pgysList.size()>0){
				 provideGoodsYShangUpdateDao.insertProvideGoodsYShang(pgysList);
				 
				 if(pgyspList!=null && pgyspList.size()>0){
					 provideGoodsYShangImgeDao.insertGoodsYShangImge(pgyspList);
				 }
				 
				  //将新增的商品插入hx_stock_update
			 	  //循环新插入到provide_goods_xiyou的数据，拿出ssProdid查询hx_stock_update没有的这个的，新插入一条
			 }
			 
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	
	
	//下载图片到本地
		 private void downloadPicture(String urlList,String path,String ourSku,String type,int t) {
 
				 path = path + ourSku +"_"+type+".jpg";
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
	
	
	
	
	public void ss(){
		String str = "{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}";  
		Map mapTypes = net.sf.json.JSONObject.fromObject(str);
		 for (Object map : mapTypes.entrySet()){  
			 String type = (String) ((Map.Entry)map).getKey();
	            String imagPic= (String) ((Map.Entry)map).getValue();
//	            System.out.println(type);
	            
//	            System.out.println(imagPic);
	        } 
	}
	
   //獲取一个云尚编码的庫存和價格
	public String getYShangStock(String skuId) {
		
		String method ="yshang.wdk.trade.products.inventory";
		 TreeMap<String, String> map = new TreeMap<String, String>();
		 String stockPrice = "";
		 String nowTime = sdf.format(new Date());
		//公共参数
		 map.put("method", method);
		 map.put("app_key", app_key);
		 map.put("secret", app_secret);
		 map.put("v", "1.0"); 
		 map.put("timestamp", nowTime);
		
		//业务参数
		map.put("skuId", skuId);
	
		
		//公共参数和业务参数一起参与签名
		String signs = getSign(map);
		map.put("sign", signs);
		//獲取商品價格和庫存
		String response = HttpRequest.sendPostNoSecret(url, map);
		
//		System.out.println(response);
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
	
	
	//獲取多个云尚编码的庫存和價格
		public List<ProvideGoodsYShang> getYShangBatchStockPrice(String skuIds) {
			
			String method ="yshang.wdk.trade.products.batchInventory";
			 TreeMap<String, String> map = new TreeMap<String, String>();
//			 List<String> stockPriceList = null;
			 List<ProvideGoodsYShang> pgysListps = new ArrayList<ProvideGoodsYShang>();
			 String nowTime = sdf.format(new Date());
			//公共参数
			 map.put("method", method);
			 map.put("app_key", app_key);
			 map.put("secret", app_secret);
			 map.put("v", "1.0"); 
			 map.put("timestamp", nowTime);
			
			//业务参数
			map.put("skuIds", skuIds);
		
			
			//公共参数和业务参数一起参与签名
			String signs = getSign(map);
			map.put("sign", signs);
			//獲取商品價格和庫存
			String response = HttpRequest.sendPostNoSecret(url, map);
			
//			System.out.println(response);
			
			String[] skuids = skuIds.split(",");
			
			try {
				JSONObject jsonObj = new JSONObject(response);
				String success = jsonObj.getString("success");
				if (success.equals("0")) {
				JSONObject jsonbject = jsonObj.getJSONObject("data");
				for(int i=0;i<skuids.length;i++){
					ProvideGoodsYShang ph = new ProvideGoodsYShang();
					    JSONObject skui = jsonbject.getJSONObject(skuids[i]+"");
						JSONObject stockAndPrice = skui.getJSONObject("stockPrice");
						String skuid = stockAndPrice.getString("skuId");
						String stock = stockAndPrice.getString("quantity");
						String price = stockAndPrice.getString("salePrice");
						Double settlePrice = Double.parseDouble(price);
						Double ourSalePrice = (double) Math.round(settlePrice/0.65);
						ph.setSkuId(skuid); //云尚sku
						ph.setStock(Integer.parseInt(stock)); //当前库存
						ph.setSettlePrice(settlePrice);
						ph.setOurPrice(ourSalePrice);
						pgysListps.add(ph);
//						System.out.println(skuids.length+"=============skuid的个数");
//					    String stockPrice = skuid+"_"+stock+"_"+price;
//					    stockPriceList.add(stockPrice);
				}
				return pgysListps;
				}else{
					return pgysListps;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return pgysListps;
		
		}
		//更新价格和库存
		@Override
		public void updateYShangStockPrice(){
			
			System.out.println(df.format(new Date()) + " sync UpdateYShangGoodsStockAndPrice  start");
			long startTime = System.currentTimeMillis();
			 Map<String,String> pramas = new HashMap<String, String>();
			 List<String> listSkuId = new ArrayList<String>();
		     List<ProvideGoodsYShang> pgysgoods = provideGoodsYShangUpdateDao.getProviderListByPage(pramas);
//		     System.out.println(pgysgoods);
//		     System.out.println(pgysgoods.size());
		     TreeMap<String,String> tmap =new TreeMap<String,String>();
		     if(pgysgoods!=null && pgysgoods.size()>0){
		    	 for(int i=0;i<pgysgoods.size();i++){
			    	  ProvideGoodsYShang pg = pgysgoods.get(i);
			    	  String skuId = pg.getSkuId();
			    	  String ourSku = pg.getOurSku();
			    	  String stock = pg.getStock()+"";
			    	  String price = pg.getSettlePrice()+"";
			    	  String ourPrice = pg.getOurPrice()+"";
			    	  String productName = pg.getProductName();
			    	  if(productName.contains(",") || productName.contains("，")){
			    		  productName = productName.replaceAll(",","");
			    		  productName = productName.replaceAll("，","");
			    	  }
			    	  String stockPrice = stock+","+price+","+ourSku+","+ourPrice+","+productName;
			    	 
			    	  tmap.put(skuId, stockPrice);
					 
			    	  listSkuId.add(skuId);
			      }
		    	 
//			      System.out.println(listSkuId.size()+"===============成年古代");
			      int t=0;
		          int j=0;
		    	 while(true){
		    		 TreeMap<String,String> tmapBatch =new TreeMap<String,String>();
		    		 String skuIds = "";
		    		 
//		    		 System.out.println(tmapBatch.size()+"================="+skuIds.length());
			         
		    		 for(t=j;t<100+j;t++){
		    			 //当t==list集合中的商品个数时，跳出for循环
		    			 if(t==listSkuId.size()){
		    				 break;
		    			 } 
		    			 String skuId = listSkuId.get(t);
		    			 skuIds += ","+skuId;
//		    			 System.out.println(skuIds);
		    		 }
		    		 
//		    		 System.out.println(skuIds);
//				      System.out.println(skuIds.substring(1));
				      skuIds = skuIds.substring(1);
			      String method ="yshang.wdk.trade.products.batchInventory";
					 TreeMap<String, String> map = new TreeMap<String, String>();
					 String nowTime = sdf.format(new Date());
					//公共参数
					 map.put("method", method);
					 map.put("app_key", app_key);
					 map.put("secret", app_secret);
					 map.put("v", "1.0"); 
					 map.put("timestamp", nowTime);
					
					//业务参数
					map.put("skuIds", skuIds);
				
					
					//公共参数和业务参数一起参与签名
					String signs = getSign(map);
					map.put("sign", signs);
					//獲取商品價格和庫存
					String response = HttpRequest.sendPostNoSecret(url, map);
					
//					System.out.println(response);
					
					String[] skuids = skuIds.split(",");
		 
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("success");
						if (success.equals("0")) {
						JSONObject jsonbject = jsonObj.getJSONObject("data");
							if(jsonbject!=null){
								for(int i=0;i<skuids.length;i++){
									    JSONObject skui = jsonbject.getJSONObject(skuids[i]+"");
										JSONObject stockAndPrice = skui.getJSONObject("stockPrice");
										String skuid = stockAndPrice.getString("skuId");
										String stock = stockAndPrice.getString("quantity");
										String price = stockAndPrice.getString("salePrice");
									    String stockPrice = stock+","+price;
									    tmapBatch.put(skuid+"", stockPrice);
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
						log.error(e.getMessage()+"t==="+t+";");
						continue;
					}
			      
					Set<String> keySet = tmapBatch.keySet();
					Iterator<String> it = keySet.iterator();
					List<StockUpdate> list = new ArrayList<StockUpdate>();
					List<ProvideGoodsYShang> listp = new ArrayList<ProvideGoodsYShang>();
					List<ProvideUpdateGoodsYShangLog> pugsList = new ArrayList<ProvideUpdateGoodsYShangLog>();
					Map updateMapp = new HashMap();
					Map updateMap = new HashMap();
					while(it.hasNext()){
						Map<String,String> maps = new TreeMap<String, String>();
						String key = it.next();
//						System.out.println(key);
					/*	if(key.equals("1054_3119719")){
							System.out.println("======productname中有，号=======");
						}*/
						
						String value = tmap.get(key);
						String valueBatch = tmapBatch.get(key);
						//原来的库存和价格、我们的sku、商品标题
						String[] valueL = value.split(",");
						String stockL = valueL[0];   //旧库存
						String priceL = valueL[1];   //旧价格
						String ourSkuL = valueL[2];
						String ourPriceL = valueL[3]; //旧销售价
						String productNameL = valueL[4];
						//新的库存和价格
						String[] valueB = valueBatch.split(",");
						String stockB = valueB[0];    //新库存
						String priceB = valueB[1];    //新价格
						
						//旧价等于新价，旧库存等于新库存，则继续下一个
						if(priceL.equals(priceB) && stockL.equals(stockB)){
							continue;
						}
						
						ProvideUpdateGoodsYShangLog pugys = new ProvideUpdateGoodsYShangLog();	
						if(!stockL.equals(stockB)){
							maps.put("timeChangeUpdateStock", "yes");
							pugys.setCostChangeTime(nowTime); //库存变化，时间更新
						}
						
						if(!priceL.equals(priceB)){
							maps.put("costChangeTime", "yes");
							pugys.setPriceChangeTime(nowTime);//价格变化，时间更新
						}
						           
						
						/*maps.put("skuId", key);
						maps.put("stock", stockB);
						maps.put("settlePrice", priceB);
						Double settlePrice = Double.parseDouble(priceB);
						Double ourSalePrice = (double) Math.round(settlePrice/0.65);
						maps.put("ourPrice", ourSalePrice+"");*/
						//存入ProvideGoodsYShang
					
						Double oldPriceL = Double.parseDouble(priceL);  //旧价格
						Double newPriceB = Double.parseDouble(priceB); //新价格
						Double oldOurPriceL = Double.parseDouble(ourPriceL);//我们的旧售价
						Double newOurPriceB = (double) Math.round(newPriceB/0.65);//我们的新售价
						
						 int intStockB = Integer.parseInt(stockB);
						
						ProvideGoodsYShang pp = new ProvideGoodsYShang();
						pp.setSkuId(key);
						pp.setStock(intStockB);
						pp.setSettlePrice(newPriceB);
						pp.setOurPrice(newOurPriceB);
						listp.add(pp);
						
//						Integer i = provideGoodsYShangUpdateDao.updateGoodsYShangMap(maps);
//						updateMap.put("list", skuIdLIst);
//						provideGoodsYShangUpdateDao.updateGoodsYShangByList(updateMap);
						//如果更新provide_goods_xiyou成功,更新hx_stock_update的库存
						//存入StockUpdate
							StockUpdate su = new StockUpdate();
							su.setSku(ourSkuL);
							su.setNowStockNum(intStockB);
							su.setType("sh");
							list.add(su);
							
							
					  //当价格或库存变化，更新记录log
							pugys.setSkuId(key);
							pugys.setOurSku(ourSkuL);
							pugys.setProductName(productNameL);
							pugys.setStock(intStockB);
							pugys.setOldCost(oldPriceL);
							pugys.setNewCost(newPriceB);
							pugys.setOldOurPrice(oldOurPriceL);
							pugys.setNewOurPrice(newOurPriceB);
							pugsList.add(pugys);
							
							
					}
					updateMapp.put("list", listp);
					updateMap.put("list", list);
					if(list!=null && list.size()>0 && listp!=null && listp.size()>0){
						try {
							provideGoodsYShangUpdateDao.updateGoodsYShangByList(updateMapp);
							platformStockUpdateDao.updateBatchNowStock(updateMap);
							provideUpdateGoodsYShangLogDao.insertYShangLog(pugsList);
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
						
					}

//					System.out.println((skuIds.split(",")).length+"=======长度");
					//当t==list集合中的商品个数时，跳出while循环
					if(t==listSkuId.size()){
						  System.out.println(df.format(new Date()) + " sync getYShangProducts  end");
							long endTime = System.currentTimeMillis();
							long a = endTime-startTime;
							long min = a/1000/60;
							System.out.println(df.format(new Date()) + " sync getYShangProducts  total min:" + min + "min");
	    				 break;
	    			 } 
			        j = j+100;
//					System.out.println("第=="+t+"==个");
				}
		    	 
		     }else{
					System.out.println(df.format(new Date()) + " sync getYShangProducts  end");
					long endTime = System.currentTimeMillis();
					long a = endTime-startTime;
					long min = a/1000/60;
					System.out.println(df.format(new Date()) + " sync getYShangProducts  total min:" + min + "min");
				 
		     }
		      
		     
		      
		}
		
		
		
		
		
		
	//=====================================以上是商品=============================
	
		
		
	//=====================================下面是订单=============================
		
		public void createYShangOrder(){
			String method = "yshang.wdk.trade.order.create";
			String sign="";
			String secret = "GSOtqeXxKWgd9kz+LPYb8J92QHNU1d47YzHP4777GhQ=";
			String v="1.0";
			try {
			
			Map<String, String> params = new HashMap<String, String>();
			 params.put("method", method);
			 params.put("timestamp", sdf.format(new Date()));//sdf.format(new Date()));
			 params.put("app_key",app_key);
			 params.put("v", v);
			 params.put("sign", sign);
			 JSONObject jsonObject = new JSONObject();
			 jsonObject.put("cardNo","0");
			 jsonObject.put("outOrderId","YS789456122010");
			 jsonObject.put("receiverName","好哦");
			 jsonObject.put("receiverProvince","dd");
			 jsonObject.put("receiverCity","fff");
			 jsonObject.put("receiverArea","gggg");
			 jsonObject.put("receiverMobile","13301945521");
			 jsonObject.put("receiverAddress","hhhhhhhhhhh");
	
			 jsonObject.put("amount",1157.2); //订单中所有商品的总金额,即所有商品的供货价（cost）和
			 jsonObject.put("orderChannelId","454725");  //id  454725
			 jsonObject.put("orderChannelName","ABCtest");

			 List<OrderItemDtoChild> list=new ArrayList<OrderItemDtoChild>();
			 OrderItemDtoChild dto =new OrderItemDtoChild();
			    dto.setProductId("13_1473501");
			    dto.setProductNum(1);
			    dto.setProductPrice(5801.78+"");
			    dto.setShopCoupon(0.00+"");
			    dto.setPlatformCoupon(0.00+"");
		
			    list.add(dto);
			 
			 
				jsonObject.put("subOrders", com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list)));
				
				params.put("data",jsonObject.toString());
				
				 List<String> keys = new ArrayList<String>(params.keySet());
				 Collections.sort(keys);

				 String str = "";
				 for (int i = 0; i < keys.size(); i++) {
				     String key = keys.get(i);
				     if (key.equals("sign")) {
				         continue;
				     }
				     String value = params.get(key);
				     str = str + key + value;
				 }
//				 System.out.println("str===>"+str);
//				 System.out.println("secret + str + secret===>"+secret + str + secret);
				 sign = getMD5Str(secret + str + secret).toUpperCase();
				 params.put("sign", sign);
				 
//				 System.out.println(params);
				  String response="";
					try {
						response = post(url, params, "UTF-8");
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}

//					System.out.println(response);
				
			} catch (JSONException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage());
			}
			
			
 
			/*String methodOrder = "yshang.wdk.trade.order.create";
			String nowTime = sdf.format(new Date());
			
			Map<String, String> map = new HashMap<String, String>();
			//系统需要参数
			map.put("method", methodOrder);
			
			//将时间固定，测试，看看，main的签名和tomcat的签名是否相同
			
			//试试正式环境
			map.put("timestamp", nowTime);//调用方法
			map.put("app_key", app_key);
			map.put("v", "1.0");
			map.put("secret", app_secret);
			 
			
			 com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
			
			try {
				
				 jsonObject.put("cardNo","000");
				 jsonObject.put("outOrderId","YS789456122008");
				 jsonObject.put("receiverName","好哦");
				 jsonObject.put("receiverProvince","dd");
				 jsonObject.put("receiverCity","fff");
				 jsonObject.put("receiverArea","gggg");
				 jsonObject.put("receiverMobile","13301945521");
				 jsonObject.put("receiverAddress","hhhhhhhhhhh");
		
				 jsonObject.put("amount",1157.2); //订单中所有商品的总金额,即所有商品的供货价（cost）和
				 jsonObject.put("orderChannelId","4547ee");  //id  454725
				 jsonObject.put("orderChannelName","ABCtest");
				
//				 OrderItemDto od = new OrderItemDto();
//				 od.setCardNo("0000");
//				 od.setOutOrderId("YS789456122007");
//				 od.setReceiverName("测试");
//				 od.setReceiverProvince("dddd");
//				 od.setReceiverCity("cccc");
//				 od.setReceiverArea("eeee");
//				 od.setReceiverMobile("13301945521");
//				 od.setReceiverAddress("hhhhhhhhhhhhhhhhhhh");
//				 
//				 od.setAmount(BigDecimal.valueOf(1157.20));
//				 od.setOrderChannelId("454725");
//				 od.setOrderChannelName("ABCtest");
//				 
//				 net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(od);
//				 com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(json);

				 
				List<OrderItemDtoChild> list = new ArrayList<OrderItemDtoChild>();
				OrderItemDtoChild oid  = new OrderItemDtoChild();
				oid.setProductId("7_1348250");
				oid.setProductNum(1);
				oid.setProductPrice(BigDecimal.valueOf(5801.78));
				oid.setShopCoupon(BigDecimal.valueOf(100));
				oid.setPlatformCoupon(BigDecimal.valueOf(100));
//				oid.setProductPrice(2000+"");
//				oid.setShopCoupon(0.00+"");
//				oid.setPlatformCoupon(0.00+"");
				 list.add(oid);
				 net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(list);
				   jsonObject.put("subOrders",json);
//					jsonObject.put("subOrders", com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list)));

				    map.put("data",jsonObject.toString());
					String sign = getSignListToEn(map);
//					String sign = getSign(map);
//				    String sign = getSignList(map);
					map.put("sign", sign);
					
					
//					String response = HttpRequest.sendPostNoSecret(url, map);
					String response = HttpRequest.sendPostOrderYs(url, map);
					
//					String response = HttpRequest.sendPostWeimob(url, map);
					System.out.println(response);
					
					
					
					
					String response2="";
					try {
						response2 = post(url, map, "");
						System.out.println(response2);
					} catch (Exception e) {
						e.printStackTrace();
					}

					
					 Map<String,String> tmp = new HashMap<String, String>();
						tmp.put("method", methodOrder);
						
						//将时间固定，测试，看看，main的签名和tomcat的签名是否相同
						
						//试试正式环境
						tmp.put("timestamp", nowTime);//调用方法
						tmp.put("app_key", app_key);
						tmp.put("v", "1.0");
						tmp.put("secret", app_secret);
						tmp.put("data",jsonObject.toString());
//						String signs = getSignListToEn(tmp);
						String signs = getSignList(tmp);

						tmp.put("sign", sign);
					String response3 = HttpRequest.sendPostNoSecret(url, tmp);
					
					System.out.println(response3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
			
			
			/*String methodOrder = "yshang.wdk.trade.order.create";
			String nowTime = sdf.format(new Date());
			
			Map<String, String> map = new HashMap<String, String>();
			//系统需要参数
			map.put("method", methodOrder);
			
			//将时间固定，测试，看看，main的签名和tomcat的签名是否相同
			
			//试试正式环境
			map.put("timestamp", nowTime);//调用方法
			map.put("app_key", app_key);
			map.put("v", "1.0");
			map.put("secret", app_secret);
			 
			
			JSONObject jsonObject = new JSONObject();
			
			try {
				
				 jsonObject.put("cardNo","000");
				 jsonObject.put("outOrderId","YS789456122006");
				 jsonObject.put("receiverName","咋说的");
				 jsonObject.put("receiverProvince","dd");
				 jsonObject.put("receiverCity","ddd");
				 jsonObject.put("receiverArea","ee");
				 jsonObject.put("receiverMobile","13301945521");
				 jsonObject.put("receiverAddress","fffffff");
		
				 jsonObject.put("amount",1157.2); //订单中所有商品的总金额,即所有商品的供货价（cost）和
				 jsonObject.put("orderChannelId","454725");  //id  454725
				 jsonObject.put("orderChannelName","ABCtest");
				 
				List<OrderItemDtoChild> list = new ArrayList<OrderItemDtoChild>();
				OrderItemDtoChild oid  = new OrderItemDtoChild();
				oid.setProductId("7_1338947");
				oid.setProductNum(1);
				oid.setProductPrice(BigDecimal.valueOf(5801.78));
				oid.setShopCoupon(BigDecimal.valueOf(100));
				oid.setPlatformCoupon(BigDecimal.valueOf(100));
				oid.setProductPrice(2000+"");
				oid.setShopCoupon(0.00+"");
				oid.setPlatformCoupon(0.00+"");
				 list.add(oid);
				 net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(list);
				   jsonObject.put("subOrders",json);
				   
				    map.put("data",jsonObject.toString());
					String sign = getSignListToEn(map);
//				    String sign = getSignList(map);
					map.put("sign", sign);
					String response = HttpRequest.sendPostNoSecret(url, map);
					System.out.println(response);
					
					TreeMap<String,String> tmp = new TreeMap<String, String>();
					tmp.put("app_key", app_key);
					tmp.put("data",jsonObject.toString());
					tmp.put("method", methodOrder);
					tmp.put("sign", sign);
					tmp.put("timestamp", nowTime);//调用方法
					tmp.put("data",jsonObject.toString());
					tmp.put("v", "1.0");
					String response = HttpRequest.sendPostNoSecret(url, tmp);
					
					System.out.println(response);
			} catch (JSONException e) {
				e.printStackTrace();
			}*/
			
			
			
			
			
			/*String method = "yshang.wdk.trade.order.create";
			String sign="";
			String secret = "GSOtqeXxKWgd9kz+LPYb8J92QHNU1d47YzHP4777GhQ=";
			String v="1.0";
			try {
			
			Map<String, String> params = new HashMap<String, String>();
			 params.put("method", method);
			 params.put("timestamp", sdf.format(new Date()));//sdf.format(new Date()));
			 params.put("app_key",app_key);
			 params.put("v", v);
			 params.put("sign", sign);
			 JSONObject jsonObject = new JSONObject();
			 jsonObject.put("cardNo","130927199112084210");
			 jsonObject.put("orderId","6012412845070");
			 jsonObject.put("receiverName","咋说的");
			 jsonObject.put("receiverProvince","北京");
			 jsonObject.put("receiverCity","北京");
			 jsonObject.put("receiverArea","朝阳区");
			 jsonObject.put("receiverPhone","18301542277");
			 jsonObject.put("receiverMobile","18301542277");
			 jsonObject.put("receiverAddress","北京");
			 jsonObject.put("outOrderId","35152565767");
			 jsonObject.put("amount","2800");
			 jsonObject.put("orderChannelId","454725");  //id  454725
		     jsonObject.put("orderChannelName","ABC测试"); //name    ABC测试

			 List<OrderItemDtoChild> list=new ArrayList<OrderItemDtoChild>();
			 OrderItemDtoChild dto =new OrderItemDtoChild();
			 dto.setProductNum(1);
			 dto.setProductPrice(BigDecimal.valueOf(5801.78));
			 dto.setProductId("7_1340144");
			 dto.setShopCoupon(BigDecimal.valueOf(100));
			 dto.setPlatformCoupon(BigDecimal.valueOf(100));
		
			 list.add(dto);
			 
			 
				jsonObject.put("subOrders", com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(list)));
				
				params.put("data",jsonObject.toString());
				
				 List<String> keys = new ArrayList<String>(params.keySet());
				 Collections.sort(keys);

				 String str = "";
				 for (int i = 0; i < keys.size(); i++) {
				     String key = keys.get(i);
				     if (key.equals("sign")) {
				         continue;
				     }
				     String value = params.get(key);
				     str = str + key + value;
				 }
				 System.out.println("str===>"+str);
				 System.out.println("secret + str + secret===>"+secret + str + secret);
				 sign = getMD5Str(secret + str + secret).toUpperCase();
				 params.put("sign", sign);
				 
				 System.out.println(params);
				 
				 String response="";
					try {
						response = post(url, params, "UTF-8");
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println(response);
				
			} catch (JSONException e1) {
				e1.printStackTrace();
			}*/
			
			
			
			
			
			
		
			
			
			 
			 
			 
			 
		/*	
			String methodOrder = "yshang.wdk.trade.order.create";
			
			
			TreeMap<String,String> map = new TreeMap<String, String>();
			TreeMap<String,Object> mapObj = new TreeMap<String,Object>();
			List<TreeMap<String,Object>> mapList = new  ArrayList<TreeMap<String,Object>>();
			
 			String nowTime = sdf.format(new Date());
			//系统需要参数
           map.put("method", methodOrder);
			
			//将时间固定，测试，看看，main的签名和tomcat的签名是否相同
			
			//试试正式环境
			map.put("timestamp", nowTime);//调用方法
			map.put("app_key", app_key);
			map.put("v", "1.0");
			map.put("secret", app_secret);
 
			
			
			mapObj.put("cardNo", "32013");
			mapObj.put("outOrderId", "123456");
			mapObj.put("receiverCity", "上海");
			mapObj.put("receiverArea", "静安区");
			mapObj.put("receiverMobile", "12345678989");
			mapObj.put("receiverProvince", "上海");
			mapObj.put("receiverAddress", "你猜");
			mapObj.put("receiverName", "测试");
			mapObj.put("amount", 1);
			mapObj.put("orderChannelId", "454725");
			mapObj.put("orderChannelName","ABC测试");
			
			TreeMap<String,Object> map1 = new TreeMap<String,Object>();
			map1.put("productNum", 1);
			map1.put("productPrice", 200);
			map1.put("productId", "7_1358960");
			map1.put("shopCoupon", 10);
			map1.put("platformCoupon", 20);
			mapList.add(map1);
			
			mapObj.put("subOrders", mapList);
			
			
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(mapObj);
			
			
			map.put("data", jsonObject.toString());
			String sign = getSignList(map);
			map.put("sign", sign);
			String response = HttpRequest.sendPostNoSecret(url, map);
			
			System.out.println(response);
			
			
			String response2="";
			try {
				response2 = post(url, map, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(response2);*/
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * @param string
		 */
		private void getYshangOrderDetail(String orderid) {
			 
			
	            String methodOrder = "yshang.wdk.trade.order.get";
				
				
				TreeMap<String,String> map = new TreeMap<String, String>();
				
				
	 			String nowTime = sdf.format(new Date());
				//系统需要参数
	 			map.put("method", methodOrder);
				map.put("timestamp", nowTime);//调用方法
				map.put("app_key", app_key);
				map.put("v", "1.0");
				map.put("secret", app_secret);
				
				 JSONObject jsonObject = new JSONObject();
				    try {
						jsonObject.put("outOrderId",orderid);
					} catch (JSONException e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				
				map.put("data", jsonObject.toString());
				String sign = getSign(map);
				map.put("sign", sign);
				String response = HttpRequest.sendPostNoSecret(url, map);
				
//				System.out.println(response);
				
				JSONObject jsonO;
				try {
					jsonO = new JSONObject(response);
					String jsondata = jsonO.getString("data");
					String statu = jsonO.getString("errorCode");
					String success = jsonO.getString("success");
					if(success.equals("0")){
						JSONObject dataObj = new JSONObject(jsondata);
						String outOrderId = dataObj.getString("outOrderId");
						String parentOrderId = dataObj.getString("parentOrderId");
						String receiverCity = dataObj.getString("receiverCity");
						String receiverArea = dataObj.getString("receiverArea");
						String receiverName = dataObj.getString("receiverName");
						String receiverMobile = dataObj.getString("receiverMobile");
						String receiverProvince = dataObj.getString("receiverProvince");
						String receiverAddress = dataObj.getString("receiverAddress");
						String totalPay = dataObj.getString("totalPay");
						
						String subOrders = dataObj.getString("subOrders");
						JSONArray subOrdersArray = new JSONArray(subOrders);
						if(subOrdersArray!=null && subOrdersArray.length()>0){
						for(int i=0;i<subOrdersArray.length();i++){
							
							JSONObject subOrdersJSON = new JSONObject(subOrdersArray.get(i).toString());
							
							String wmsStatus = subOrdersJSON.getString("wmsStatus");
							String status = getShipStatus(wmsStatus);
							String expressName = subOrdersJSON.getString("expressName");
							String totalPayChild = subOrdersJSON.getString("totalPay");
							String orderId = subOrdersJSON.getString("orderId");
							
							String warehouseId = subOrdersJSON.getString("warehouseId");
							String expressNum = subOrdersJSON.getString("expressNum");
							String orderItems = subOrdersJSON.getString("orderItems");
							
							Map<String,String> detailmap = new HashMap<String, String>();
							Map<String,String> onemap = new HashMap<String, String>();
							String sellPlatform = getPlat(orderId); //获取哪个平台卖出的
							
							detailmap.put("orderId", outOrderId);
							detailmap.put("orderstatus", status);
							detailmap.put("buyname", receiverName);
							detailmap.put("phone", receiverMobile);
							
							detailmap.put("address", receiverAddress);
							detailmap.put("amount", totalPay);
							detailmap.put("paidamount", totalPayChild);
							detailmap.put("shipname", expressName);
							
							detailmap.put("waybillcode", expressNum); 
							detailmap.put("sellPlatform", sellPlatform);
							detailmap.put("pushPlatform", "yshang");
							onemap.put("orderId", orderId);
							List<ProvideOrderWaybillDetail> provideOrderWaybillDetail = provideOrderWaybillDetailDao.selectWayBill(onemap);
							if(provideOrderWaybillDetail!=null && provideOrderWaybillDetail.size()>0){
								provideOrderWaybillDetailDao.updateOrderWaybillDetail(detailmap);
								detailmap.remove("pushPlatform");
								detailmap.remove("sellPlatform");
								provideOrderWaybillDetailYShangDao.updateOrderWaybillDetailYShang(detailmap);
							}else{
								try {
									detailmap.remove("pushPlatform");
									provideOrderWaybillDetailDao.insertWayBillDetail(detailmap);
									
									JSONArray orderItemsArray = new JSONArray(orderItems);
									if(orderItemsArray!=null && orderItemsArray.length()>0){
										
										
										detailmap.put("parentOrderId", parentOrderId);
										detailmap.put("receiverCity", receiverCity);
										detailmap.put("receiverArea", receiverArea);
										detailmap.put("receiverProvince", receiverProvince);
										
										detailmap.put("warehouseId", warehouseId);
										detailmap.put("orderId", orderId);
										 
										for(int t=0;t<orderItemsArray.length();t++){
											JSONObject orderItemsJSON = new JSONObject(orderItemsArray.get(t).toString());
											String nowPrice = orderItemsJSON.getString("nowPrice");
											String quantity = orderItemsJSON.getString("quantity");
											String productIdAndVendorIdStr = orderItemsJSON.getString("productIdAndVendorIdStr");
											String salePrice = orderItemsJSON.getString("salePrice");
											String productName = orderItemsJSON.getString("productName");
											
											detailmap.put("nowPrice", nowPrice);
											detailmap.put("quantity", quantity);
											detailmap.put("productId", productIdAndVendorIdStr);
											detailmap.put("productName", productName);
											
											detailmap.put("salePrice", salePrice);
											
											provideOrderWaybillDetailYShangDao.insertWayBillDetailYShang(detailmap);
										}
									}
									
								} catch (Exception e) {
									e.printStackTrace();
									log.error(e.getMessage());
								}
								
							}
							
							
							
						}
						
						}
						
						
					}
					
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					log.error(e.getMessage());
				}
				
				
				
				
			
		}
		
		
		private String getShipStatus(String orderStatus) {
			String status ="";
			if(orderStatus.equals("100")){
				status="商家待发货给云尚互联(非直发)";
			}else if(orderStatus.equals("110")){
				status="商家已发货给云尚互联(非直发)";
			}else if(orderStatus.equals("120")){
				status="云尚互联已经收货(非直发)";
			}else if(orderStatus.equals("130")){
				status="订单已确认(直发待发货)";
			}else if(orderStatus.equals("140")){
				status="已拣货";
			}else if(orderStatus.equals("170")){
				status="已发货";
			}else if(orderStatus.equals("180")){
				status="已签收";
			}else if(orderStatus.equals("190")){
				status="已拒签";
			}else if(orderStatus.equals("200")){
				status="已完成";
			}
			return status;
		}
		
		
		private String getPlat(String orderId) {
			String platName = "";
			Map<String,String> onemap = new HashMap<String, String>();
			onemap.put("tradeId", orderId);
			List<ProvideOrderDetail> one = provideOrderDetailDao.selectOrderDetailByMap(onemap);
			String ourOrderId = one.get(0).getOriginalTradeId();
			PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
			platformorderdetails.setIdorder(ourOrderId);
		  List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
		  if(orderDetais!=null && orderDetais.size()>0){
		  	 platName = orderDetais.get(0).getPtype();  	
		  }
			return platName;
		}
		
	
	//=====================================上面是订单=============================
	
	
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
		
//		System.out.println(needMD5Str);
		sign = HttpRequest.string2MD5(needMD5Str).toUpperCase();
		return sign;
	}
	private String getSignList(Map<String,String> map)
	{
		String sign="";
		String secret = map.get("secret");
		List<String> keys = new ArrayList<String>(map.keySet());
	    Collections.sort(keys);

	    String str = "";
	    for (int i = 0; i < keys.size(); i++) {
	        String key = keys.get(i);
	        if (key.equals("secret") || key.equals("sign")) {
	            continue;
	        }
	        String value = map.get(key);
	        str = str + key + value;
	    }
//	    System.out.println("str===>"+str);
//	    System.out.println("secret + str + secret===>"+secret + str + secret);
	    sign = getMD5Str(secret + str + secret).toUpperCase(); 

//	    sign = HttpRequest.string2MD5(secret + str + secret).toUpperCase();
//	   String md = getMd5(secret + str + secret);
//	    System.out.println("mySign===>" + sign);
//	    System.out.println("mySignssss===>" + signs);
//	    System.out.println("md===>" + md.toUpperCase());
		return sign;
	}
	public static String getMd5(String plainText) {  
		          try {  

             MessageDigest md = MessageDigest.getInstance("MD5");  
		              md.update(plainText.getBytes());  
		              byte b[] = md.digest();  
		    
		              int i;  
		    
		              StringBuffer buf = new StringBuffer("");  
		              for (int offset = 0; offset < b.length; offset++) {  
		                  i = b[offset];  
		                  if (i < 0)  
		                      i += 256;  
		                  if (i < 16)  
		                      buf.append("0");  
		                  buf.append(Integer.toHexString(i));  
		              }  
		              //32位加密  
		              return buf.toString().toUpperCase();  
		              // 16位的加密  
		              //return buf.toString().substring(8, 24);  
		          } catch (NoSuchAlgorithmException e) {  
		              e.printStackTrace();  
		              return null;  
		          }  
		    
		      }  
	
	  
	  
	  
	  
	  
	  
	  
	  private static final String CHARSET_UTF8 = "UTF-8";

	    public static String post(String url, Map<String, String> params, String charset,ResponseHandler<String> handler) throws Exception{
	        if (url == null || org.apache.commons.lang.StringUtils.isEmpty(url)) {
	            return null;
	        }
	        // ����HttpClientʵ��
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        UrlEncodedFormEntity formEntity = null;
	        try {
	            if (charset == null || StringUtils.isEmpty(charset)) {
	                formEntity = new UrlEncodedFormEntity(getParamsList(params),CHARSET_UTF8);
	            } else {
	                formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
	            }
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	            throw new UnsupportedEncodingException("��֧�ֵı��뼯");
	        }
	        HttpPost hp = new HttpPost(url);
	        hp.setEntity(formEntity);

	       
	        // �������󣬵õ���Ӧ
	        String responseStr = null;
	        try {
	            responseStr = httpclient.execute(hp, handler);
	        } catch (ClientProtocolException e) {
	            throw new ClientProtocolException("�ͻ�������Э�����", e);
	        } catch (IOException e) {
	            throw new IOException("IO�����쳣", e);
	        } finally {
	            abortConnection(hp, httpclient);
	        }
	        return responseStr;
	    }

	    private static List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
	        if (paramsMap == null || paramsMap.size() == 0) {
	            return Collections.emptyList();
	        }
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        for (Map.Entry<String, String> map : paramsMap.entrySet()) {
	            params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
	        }
	        return params;
	    }

	    private static void abortConnection(final HttpRequestBase hrb, final HttpClient httpclient){
	        if (hrb != null) {
	            hrb.abort();
	        }
	        if (httpclient != null) {
	            httpclient.getConnectionManager().shutdown();
	        }
	    }

	    public static String post(String url,final String params)throws Exception {
	        return post(url, new HashMap<String, String>(){
	            /**
	             *
	             */
	            private static final long serialVersionUID = 1L;

	            {
	                for(String param:params.split("&")){
	                    String[] kv = param.split("=");
	                    if(kv.length>1){
	                        put(kv[0], kv[1]);
	                    }
	                }
	            }
	        }, null);
	    }

	  
	  
	  
	    public static String post(String url, Map<String, String> params, String charset) throws Exception{
	        return post(url, params,charset,responseHandler);
	    }
	    private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	        // ????????????
	        public String handleResponse(HttpResponse response)    throws ClientProtocolException, IOException {
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	                @SuppressWarnings("deprecation")
	                String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8 : EntityUtils.getContentCharSet(entity);
	                return new String(EntityUtils.toByteArray(entity), charset);
	            } else {
	                return null;
	            }
	        }
	    };
	  
	  
	    public static String getMD5Str(String str) {
	        MessageDigest messageDigest = null;

	        try {
	            messageDigest = MessageDigest.getInstance("MD5");
	            messageDigest.reset();
	            messageDigest.update(str.getBytes("UTF-8"));
	        } catch (NoSuchAlgorithmException var5) {
	            System.out.println("NoSuchAlgorithmException caught!");
	            System.exit(-1);
	        } catch (UnsupportedEncodingException var6) {
	            var6.printStackTrace();
	        }

	        byte[] byteArray = messageDigest.digest();
	        StringBuffer md5StrBuff = new StringBuffer();

	        for(int i = 0; i < byteArray.length; ++i) {
	            if (Integer.toHexString(255 & byteArray[i]).length() == 1) {
	                md5StrBuff.append("0").append(Integer.toHexString(255 & byteArray[i]));
	            } else {
	                md5StrBuff.append(Integer.toHexString(255 & byteArray[i]));
	            }
	        }

	        return md5StrBuff.toString().toUpperCase();
	    }
	  
	
	    
	    
	    private static String getSignListToEn(Map<String,String> map)
		{
			String sign="";
			String secret = map.get("secret");
			List<String> keys = new ArrayList<String>(map.keySet());
		    Collections.sort(keys);

		    String str = "";
		    for (int i = 0; i < keys.size(); i++) {
		        String key = keys.get(i);
		        if (key.equals("secret") || key.equals("sign")) {
		            continue;
		        }
		        
		        if (key.equals("data")) {
		        	TreeMap<String, String> tmp = new TreeMap<String, String>();
		        	tmp.put("da", map.get(key));
		        	net.sf.json.JSONObject jsonOb = net.sf.json.JSONObject.fromObject(tmp);
			        String jsondata = jsonOb.getString("da");
			        JSONObject dataO;
					try {
						dataO = new JSONObject(jsondata);
						 String receiverName = dataO.getString("receiverName");
					       String receiverProvince = dataO.getString("receiverProvince");
					       String receiverCity = dataO.getString("receiverCity");
					       String receiverArea = dataO.getString("receiverArea");
					       String receiverAddress = dataO.getString("receiverAddress");

					       String orderChannelId = dataO.getString("orderChannelId");  //id  454725
					       String orderChannelName = dataO.getString("orderChannelName");
					        //如果包含中文，清除，然后在加上
					       if(isContainChinese(receiverName)){
					    	   dataO.remove("receiverName");
					    	   
					    	 try {
								dataO.put("receiverName", URLDecoder.decode(receiverName, "UTF-8"));
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								
							}
					    	/*   
					    	 try {
								dataO.put("receiverName", new String(receiverName.toString().getBytes("ISO8859-1"),"UTF-8"));
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}*/
					    	   
					       }
					       if(isContainChinese(receiverProvince)){
					    	   dataO.remove("receiverProvince");
					    	   dataO.put("receiverProvince", receiverProvince);
					        }
					       if(isContainChinese(receiverCity)){
					    	   dataO.remove("receiverCity");
					    	   dataO.put("receiverCity", receiverCity);
					        }
					       if(isContainChinese(receiverArea)){
					    	   dataO.remove("receiverArea");
					    	   dataO.put("receiverArea", receiverArea);
					        }
					       if(isContainChinese(receiverAddress)){
					    	   dataO.remove("receiverAddress");
					    	   dataO.put("receiverAddress", receiverAddress);
					        }
					       if(isContainChinese(orderChannelId)){
					    	   dataO.remove("orderChannelId");
					    	   dataO.put("orderChannelId", orderChannelId);
					        }
					       if(isContainChinese(orderChannelName)){
					    	   dataO.remove("orderChannelName");
					    	   dataO.put("orderChannelName", orderChannelName);
					        }
					       
					       map.put("data", dataO.toString());
					} catch (JSONException e) {
						e.printStackTrace();
						
					}
		        }
		        
		        String value = map.get(key);
		        str = str + key + value;
		    }
//		    System.out.println("str===>"+str);
//		    System.out.println("secret + str + secret===>"+secret + str + secret);
		    sign = getMD5Str(secret + str + secret).toUpperCase(); 
//		    sign = MD5Encode(secret + str + secret,"UTF-8").toUpperCase(); 
//		   String signs = HttpRequest.string2MD5(secret + str + secret).toUpperCase();
//		   
//		   String md = getMd5(secret + str + secret);
//		    System.out.println("mySign===>" + sign);
//		    System.out.println("mySignssss===>" + signs);
//		    System.out.println("md===>" + md.toUpperCase());
			return sign;
		}
	    
	    
	    
	    private static String byteArrayToHexString(byte b[]) {
			StringBuffer resultSb = new StringBuffer();
			for (int i = 0; i < b.length; i++)
				resultSb.append(byteToHexString(b[i]));
	 
			return resultSb.toString();
		}
	 
		private static String byteToHexString(byte b) {
			int n = b;
			if (n < 0)
				n += 256;
			int d1 = n / 16;
			int d2 = n % 16;
			return hexDigits[d1] + hexDigits[d2];
		}
	 
		public static String MD5Encode(String origin, String charsetname) {
			String resultString = null;
			try {
				resultString = new String(origin);
				MessageDigest md = MessageDigest.getInstance("MD5");
				if (charsetname == null || "".equals(charsetname))
					resultString = byteArrayToHexString(md.digest(resultString
							.getBytes()));
				else
					resultString = byteArrayToHexString(md.digest(resultString
							.getBytes(charsetname)));
			} catch (Exception exception) {
			}
			return resultString.toString().toUpperCase();
		}
	 
		private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "a", "b", "c", "d", "e", "f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

	    
	    
	    
	    public static String unicodeToString(String str) {

	    	Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
	    	Matcher matcher = pattern.matcher(str);
	    	char ch;
	    	while (matcher.find()) {
	    		//group 6728
	    		String group = matcher.group(2);
	    		//ch:'木' 26408
	    		ch = (char) Integer.parseInt(group, 16);
	    		//group1 \u6728
	    		String group1 = matcher.group(1);
	    		str = str.replace(group1, ch + "");
	    	}
	    	return str;
	    }
	
		 public static boolean isContainChinese(String str) {
		        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		        Matcher m = p.matcher(str);
		        if (m.find()) {
		            return true;
		        }
		        return false;
		    }
		

	      //中文转英文
		 public static String getEname(String name)
	       {
		          HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
		          pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		          pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		          pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		  
		          try {
					return PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
				return name;
		      }
		 
	    
	    public void cancelYShangOrder(String orderId){
	    	String method ="yshang.wdk.trade.order.cancel";

				
				
				TreeMap<String,String> map = new TreeMap<String, String>();
				
				
	 			String nowTime = sdf.format(new Date());
				//系统需要参数
	 			map.put("method", method);
				map.put("timestamp", nowTime);//调用方法
				map.put("app_key", app_key);
				map.put("v", "1.0");
				map.put("secret", app_secret);
				
				 JSONObject jsonObject = new JSONObject();
				    try {
						jsonObject.put("outOrderId",orderId);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
				map.put("data", jsonObject.toString());
				String sign = getSign(map);
				map.put("sign", sign);
				String response = HttpRequest.sendPostNoSecret(url, map);
	    	
//				System.out.println(response);
	    	
	    	
	    }
	    
	    
	    
	    
	    
	    

		public static void main(String[] args) {

			YShangPlatFormStockUpdateImpl ys = new YShangPlatFormStockUpdateImpl();
//			ys.getYShangDetail("1008_1508921");
//		     ys.getYShangProducts();
			//7_1340816 库存3
			//7_1358995 库存4
			//7_1358967 库存2
			//{"data":{"stockPrice":{"quantity":4,"salePrice":1538.90,"skuId":"7_1359093","status":1}}
			String stock = ys.getYShangStock("1013_2796599"); //13_1473501
			System.out.println(stock);
//	     ys.ss();SO0119061900955502 SO0119061900955503
//			ys.getYShangBatchStockPrice("7_1335370,7_1335454,7_1335461,7_1335468,7_1335475,7_1335573,7_1335580,7_1335587,7_1335594,7_1335629,7_1335664,7_1335832,7_1335839,7_1335846,7_1335853,7_1335860,7_1335895,7_1335902,7_1335909,7_1335916");
			//YS789456122001
//			ys.createYShangOrder();
			//YS8885522002255
//			ys.getYshangOrderDetail("YS2226271745777"); //7_1359093
		   System.out.println("===================================");
		   String times = String.valueOf(System.currentTimeMillis());
//		   System.out.println(times);
		  }
		
		




//		  @PostConstruct
		  public void ys(){
			System.out.println("================开始===================");
//			getYShangProducts();
//			getYShangDetail("7_1359170");
			updateYShangStockPrice();

//			getYshangOrderDetail("YS8885522002255");
//			createYShangOrder();
//			cancelYShangOrder("YS8885522002255");
			System.out.println("================结束===================");
		  }
		
}
