package com.huaixuan.network.common.util.miniui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.query.QueryBase;
import com.hundsun.network.melody.common.util.StringUtil;





/**
 * @author Mr_Yang   2015-11-20 下午12:01:15
 * MiniUi 工具类  
 * 前台页面显示的产品站点
      国内的站点：101 、209、212、214、215、216、217
      国外的站点：103、104
 **/

public class MiniUiUtil
{
	public  static final List<String> shSite = new ArrayList<String>();//上海站点
	public  static final String shSiteStr;//上海站点（101,102,103）
	public static final List<String> hkSite = new ArrayList<String>();//香港站点
	public  static final String hkSiteStr;//香港站点（101,102,103）
	public static final List<String> hkAllSite = new ArrayList<String>();//香港所有站点
	public static final List<String> hqgAllSite = new ArrayList<String>();
	public static final String IMG_ROOT = "http://140.207.52.210:88";  //图片显示-使用192.168.1.198 的9090端口
	public static final String UPLOAD_IMG_URL = IMG_ROOT + "/upload/create.php"; 
	
	private static final String path = "d:/stock_log/";//记录日志
	
	static
	{
			shSite.add("101");
			shSite.add("209");//紫安仓
			shSite.add("204");//吴伟明
			shSite.add("212");//线下活动仓
			shSite.add("215");//环球港备用仓
			shSite.add("216");//环球港	
			shSite.add("214");//福州特卖
			shSiteStr = "101,209,204,212,215,216";
			hkSite.add("102"); //观塘仓
			hkSite.add("103"); //帝国中心408
			hkSite.add("104"); //善美大厦
			hkSiteStr = "102,103,104";
			hkAllSite.add("102");
			hkAllSite.add("103");
			hkAllSite.add("104");
			hqgAllSite.add("216");//环球港
	}
	/**
	 * 查询条件map 去除value的空格以及转换SQL中的特殊字符串
	 * @param map
	 * @param turnUp 是否转为大写 
	 */
	public static void trimAndConvSpeSqlStr(Map<String,String> map,boolean turnUp)
	{
		 
		trimAndConvSpeSqlStr(map,turnUp,true,true);
	}
	
	
	/**
	 * 查询条件map 去除value的空格以及转换SQL中的特殊字符串
	 * @param map
	 * @param toUp 是否转为大写 
	 * @param trim 是否去除空格 
	 * @param sql 是否去除空间转换'为'' 
	 */
	public static void trimAndConvSpeSqlStr(Map<String,String> map,boolean toUp,boolean trim,boolean sql)
	{
		Set<String> keySet = map.keySet();
		Iterator<String> it =  keySet.iterator();
		while(it.hasNext())
		{
			String key = it.next();
			String value = map.get(key);
			if(trim)
				value = value.trim(); //去除空格
			if(sql)
				value = value.replace("'", "''"); //'为SQL中特殊字符串转为''
			if(toUp)
				value = value.toUpperCase(); //转为大写
			map.put(key, value);
		}
	}
	   /**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String,String> returnMap = new HashMap<String,String>();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
	
	/**
	 * map 转对象 
	 * @param map 需要转换的只
	 * @param cla 需要转换的class对象
	 * @return
	 */
	public static Object Map2Object(Map<String,String> map,Class<?> cla)
	{
		Field[] fields = cla.getDeclaredFields();
		Object obj = null;
		try
		{
			obj = cla.newInstance();
		}
		catch (InstantiationException e1)
		{
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1)
		{
			e1.printStackTrace();
		}
		for(Field field : fields)
		{
			String fieldName = field.getName();
			String mapValue = map.get(fieldName); //前端传过来的值  如果有
			if(null != mapValue) //有值匹配类型 转化
			{
				setFiledValue(field,mapValue,obj);
			}
		}
		 
		return obj;
	}

	
	/**
	 * 设置产品的图片
	 * @param p
	 * @return
	 */
	public static String setProductPicture(Product p)
	{
		if(p == null) return "";
		String brandName = p.getBrandName();
		String type = p.getType();
		String material = p.getMaterial();
		String color = p.getColor();
		if(brandName== null || "".equals(brandName)) return "";
		if(null == type || "".equals(type))return "";
		
		brandName = brandName.replace(" ", "").replace("&", "");
		material = material.toUpperCase();
		color = color.toUpperCase();
		String picName = brandName+ "_" + type + "_" + material + "_" + color + ".jpg"; 
		String picUrl = IMG_ROOT + "/upload/"+ picName;
		p.setPicture(picUrl);
		return picName;	
	}
	
	private static void setFiledValue(Field field,String value,Object obj)
	{

		field.setAccessible(true);
		String fileType = field.getType().getName();
		if("java.lang.Long".equals(fileType) || "long".equals(fileType))
		{
			try
			{
				Long v = Long.valueOf(value);
				field.set(obj, v);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		else if("java.lang.Double".equals(fileType) || "double".equals(fileType))
		{
			try
			{
				if(StringUtil.isNotBlank(value))
				{
					Double v = Double.valueOf(value);
					field.set(obj, v);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if("java.lang.Integer".equals(fileType) || "int".equals(fileType))
		{
			try
			{
				Integer v = Integer.valueOf(value);
				field.set(obj, v);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if("java.util.Date".equals(fileType))
		{
			try
			{
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date v= sf.parse(value);
				field.set(obj, v);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				field.set(obj, value.toString());
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		 
	}
	
	

	/**
	     * post方式请求http服务
	     * @param urlStr
	     * @param params   name=yxd&age=25
	     * @return
	     * @throws Exception
	     */
	    public static String getURLByPost(String urlStr,String params)throws Exception{
	        URL url=new URL(urlStr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
	        printWriter.write(params);
	        printWriter.flush();        
	        BufferedReader in = null; 
	        StringBuilder sb = new StringBuilder(); 
	        try{   
	            in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8") ); 
	            String str = null;  
	            while((str = in.readLine()) != null) {  
	                sb.append( str );   
	            }   
	         } catch (Exception ex) { 
	            throw ex; 
	         } finally{  
	          try{ 
	              conn.disconnect();
	              if(in!=null){
	                  in.close();
	              }
	              if(printWriter!=null){
	                  printWriter.close();
	              }
	          }catch(IOException ex) {   
	              throw ex; 
	          }   
	         }   
	         return sb.toString(); 
	    }

	    
	    /**
	     * 发送get请求
	     * @param urlStr
	     * @return
	     */
	    public static String getURLContent(String urlStr) {             
	        /** 网络的url地址 */      
	     URL url = null;            
	        /** http连接 */  
	     HttpURLConnection httpConn = null;          
	         /**//** 输入流 */ 
	     BufferedReader in = null; 
	     StringBuffer sb = new StringBuffer(); 
	     try{   
	      url = new URL(urlStr);   
	      in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") ); 
	      String str = null;  
	      while((str = in.readLine()) != null) {  
	       sb.append( str );   
	             }   
	         } catch (Exception ex) { 
	           
	         } finally{  
	          try{           
	           if(in!=null) {
	            in.close();   
	                 }   
	             }catch(IOException ex) {    
	             }   
	         }   
	         String result =sb.toString();   
	         //System.out.println(result);   
	         return result;  
	         }  

	
	
	 public static String getNextPageParamParams(QueryBase queryBase)
	 {
		 String result = "";
		 Map<String,String> map = queryBase.getParameters();
		 if(!queryBase.isLastPage())
		 {
			 map.put("pageIndex", (queryBase.getNextPage()-1)+""); //pageIndex以0开始
		 }

		 Set<String> keys = map.keySet();
		 Iterator<String> it = keys.iterator();
		 while (it.hasNext()) {
			 String key = it.next();
			 if(key.equals("startRow") || key.equals("endRow")) continue;
			 String value = map.get(key);
			 result += key + "="+value+"&";
		}
		 return result;
	 }
	 public static String getPreviousPageParams(QueryBase queryBase)
	 {
		 String result = "";
		 Map<String,String> map = queryBase.getParameters();
		 if(!queryBase.isFirstPage())
		 {
			 map.put("pageIndex", (queryBase.getPreviousPage()-1)+""); //pageIndex以0开始
		 }
		 Set<String> keys = map.keySet();
		 Iterator<String> it = keys.iterator();
		 while (it.hasNext()) {
			 String key = it.next();
			 if(key.equals("startRow") || key.equals("endRow")) continue;
			 String value = map.get(key);
			 result += key + "="+value+"&";
		}
		 return result;
	 }
	 
	 
	 //添加数组长度
	 public static Object arrayAddLength(Object oldArray,int addLength) {  
		 Class c = oldArray.getClass();  
		 if(!c.isArray())return null;  
		 Class componentType = c.getComponentType();  
		 int length = Array.getLength(oldArray);  
		 int newLength = length + addLength;  
		 Object newArray = Array.newInstance(componentType,newLength);  
		 System.arraycopy(oldArray,0,newArray,0,length);  
		 return newArray;  
		 }
	 
	 
	 /**
	  * 获取idProduct 
	  * 采购ID(3位或者4位) + 5位（00001-00009） +5位或者4位随机数
	  * @param idPurchaseLifeCyle
	  * @return
	  */
	 public static String getIdProduct(String idPurchaseLifeCyle,int number)
	 {
		 String idProduct = idPurchaseLifeCyle; //采购单号
		 String numberStr  = String.format("%05d", number); //序号 5位 补0
		 idProduct+=numberStr;
		 int cha = 13-idProduct.length(); //总共13位 还差剩下的位数  随机生成 差的位数
		 
		 Random random = new Random(); 
	     String result="";
	     for(int i=0;i<cha;i++)
	     {
	         result+=random.nextInt(10);    
	      }
		 
	     idProduct+=result;
	     return idProduct;
	 }
	 
	 
	/**
	 * 
	 * @param fileName 文件名
	 * @param str 内容
	 * @param type zj-追加 cx-重写
	 */
	public static void writeText2File(String fileName,String str,String type)
	{
		RandomAccessFile accessFile = null;
		try
		{
			accessFile = new RandomAccessFile(path + fileName,"rw");
			if("zj".equals(type))
			{
				accessFile.seek(accessFile.length()); //追加
			}
			else if("cx".equals(type))
			{   
				//清空text文件
				FileChannel fc = accessFile.getChannel();
				fc.truncate(0);
//				accessFile.seek(0);//重写
			}
			else
			{
				return;
			}
			accessFile.write(str.getBytes());
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
	 * 读取有重复sku的数据
	 * 文件内容必须为 ourSku,平台sku
	 * 返回map key为我们sku value为平台sku的list
	 */
	public static Map<String,List<String>> readRpeatSku(String fileName)
	{
		RandomAccessFile accessFile = null;
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		Map<String,List<String>> mapTmp = new HashMap<String, List<String>>();
		try
		{
			accessFile = new RandomAccessFile(path + fileName,"r");
			 
			accessFile.seek(0);//重写
			String str = "";
			while((str = accessFile.readLine())!= null)
			{
				 if(StringUtil.isNotBlank(str))
				 {
					 String[] arr = str.split(",");
					 if(arr.length == 2)
					 {
						 String ourSku = arr[0];
						 String plartformSku = arr[1];
						 ourSku = ourSku.trim();
						 plartformSku = plartformSku.trim();
						 if(mapTmp.get(ourSku) == null)
						 {
							 List<String> list= new ArrayList<String>();
							 list.add(plartformSku);
							 mapTmp.put(ourSku, list);
						 }
						 else
						 {
							 mapTmp.get(ourSku).add(plartformSku);
						 }
						 
					 }
				 }
			}
			
			//在把重复的值放入map
			Set<Entry<String, List<String>>> set = mapTmp.entrySet();
			Iterator<Entry<String,List<String>>> it = set.iterator();
			while(it.hasNext())
			{
				Entry<String, List<String>> mp = it.next();
				String ourSku = mp.getKey();
				List<String> l = mp.getValue();
				if(l.size() > 1)
				{
					map.put(ourSku, l);
				}
			}
			
			 
		}
		catch (Exception e)
		{
			//e.printStackTrace();
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
		return map;
	}
	
	
	//读取需要更新到sku平台的sku
	public static List<String> readOfanshSku(String fileName)
	{
		RandomAccessFile accessFile = null;
		List<String> list = new ArrayList<String>();
		try
		{
			accessFile = new RandomAccessFile(path + fileName,"r");
			String str = "";
			while((str = accessFile.readLine())!= null)
			{
				 if(StringUtil.isNotBlank(str))
				 {
					 String[] arr = str.split(",");
					 if(arr.length == 1)
					 {
						 String ourSku = arr[0];
						 ourSku = ourSku.trim();	                 
						 list.add(ourSku);					 
					 }
				 }
			}

		}
		catch (Exception e)
		{
			//e.printStackTrace();
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
		return list;
	}
	private static final String SHEPIN_SKU_FILE_NAME= "higo_sku.txt";
	private static Map<String,List<String>> repSkuMap = MiniUiUtil.readRpeatSku(SHEPIN_SKU_FILE_NAME);
	public static void main(String[] args)
	{
		
//		String ourSku="9300032072049";
//		
//		List<String> HigoSkusList = repSkuMap.get(ourSku);
//		for(String higoSku : HigoSkusList)
//		{
//			System.out.println(higoSku);
//		}
		
		
		
//		Map<String,String> map = new HashMap<String, String>();
		//map.put("instock", "2015-12-30 00:00:00");
		//Product p = (Product)Map2Object(map,Product.class);
		//System.out.println(p.getInstock());
//		readRpeatSku("last_update_time_not_delete.txt");
		
		
//       String filname="ofashion_sku.txt";
//		
//	    List<String> ls = readOfanshSku(filname);
//		for(String s:ls){
//			System.out.println(s);
//		}
		
		
	}
}
 
