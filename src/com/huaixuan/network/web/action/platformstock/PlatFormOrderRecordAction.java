package com.huaixuan.network.web.action.platformstock;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
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

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.pattern.IntegerPatternConverter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderRecordDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.platformstock.PlatFormOrderRecordService;
import com.huaixuan.network.biz.service.reserved.HxStockOrderService;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.base.MiniuiBaseAction;
import com.hundsun.common.lang.StringUtil;

@Controller
@RequestMapping("/plartFromOrderRecord")
public class PlatFormOrderRecordAction {
	         //690000000041176
//			private static final String vendorCodehk = "CB00054";//海外正式环境
	        private static final String vendorCodehk = "690000000041176";//海外正式环境
			private static final String vendorCodesh = "V12594";//国内正式环境
			private static final String signMethod = "MD5";
			private static final String format = "json";
			private static final String v = "1.0";
//			private static final String secrethk = "DQVFZ";//海外正式环境
			private static final String secrethk = "DAUDU";//海外正式环境
			private static final String secretsh = "TMUOX";//国内正式环境
			private static final String api_urls = "http://116.62.245.214/order/shipped";//发货通知接口
			private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			private static final String cookieKey =  "orderId";
	@Autowired
	private PlatFormOrderRecordService plartFromOrderRecordService;
	
	@Autowired      
    private  PlatFormOrderRecordDao platFormOrderRecordDao;
	
	@RequestMapping("/toSearchOrderId")
	public String toSearchStock(Model model,PlatFormOrderRecord platFormOrderRecord,HttpServletRequest request)
	{
		model.addAttribute("idPlartform",platFormOrderRecord.getIdPlartform());
		return "/notice/charismaGoodsNotify";
	}
	
//	@RequestMapping("/toSearchWaybill")
//	public String toSearchWaybill(Model model,PlatFormOrderRecord platFormOrderRecord,HttpServletRequest request)
//	{
////		model.addAttribute("idPlartform",platFormOrderRecord.getIdPlartform());
//		
//		return "/notice/mlhWaybill";
//	}
	protected int              pageSize           = 25;
	@RequestMapping("/toSearchWaybill")
	public String toSearchWaybill(Model model,@ModelAttribute("platFormOrderRecord") PlatFormOrderRecord platFormOrderRecord,HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		List<PlatFormOrderRecord> platFormOrderR = new ArrayList<PlatFormOrderRecord>();
		QueryPage page = plartFromOrderRecordService.searchWaybillByOrder(platFormOrderRecord,currPage,this.pageSize);
		platFormOrderR = (List<PlatFormOrderRecord>) page.getItems();
		if (page != null)
		{
			model.addAttribute("query", page);
		}
		return "/notice/mlhWaybill";
	}
	
	
	@RequestMapping("/searchOrderId")
	@ResponseBody
	public Object searchStock(Model model,HttpServletRequest request){
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		return plartFromOrderRecordService.searchAllOrderId(searchMap);
	}
	
	//导入Excel
	/*String   MlhKey = "";
	String   emsKey = "";
	String   emsCompany="";
	String   ems="";*/
	@RequestMapping("/orderExecl")
	public void orderExecl(Model model,HttpServletRequest req,HttpServletResponse response){
		try {
			 // 转型为MultipartHttpRequest
			MultipartHttpServletRequest request= (MultipartHttpServletRequest)req;
			
			 // 获得文件：   
			MultipartFile  locationFile = request.getFile("Mlh");
			  
		  	// 获得输入流
	    	   InputStream in = locationFile.getInputStream();
	    	   
			// 获得文件名：
			String locationFileName = locationFile.getOriginalFilename();
			
			//截取文件名xxxxxx.xls中的xls，在.後面,所以fileName=xls
			String fileName = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			//创建一个webbook，对应一个Excel文件
			Workbook wb=null;
    	   if(fileName.equals("xls")){
    		 //把一张xls的数据表读到wb里
    		   wb = new HSSFWorkbook(in);
    	   }
    	   else if(fileName.equals("xlsx")){
    		 //把一张xlsx的数据表读到wb里
    		   wb = new XSSFWorkbook(in); 
    	   }
    	   
    	 //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
    	   Sheet sheet1 = wb.getSheetAt(0);
    	   
    	   Map<String,String> locationmap = new HashMap<String, String>();
    	   //循环遍历表sheet.getPhysicalNumberOfRows()是获取sheet下标的总行数
    	   for(int rowIndex=0;rowIndex<sheet1.getPhysicalNumberOfRows();rowIndex++){
    		 //创建一个行对象
    		   Row row = sheet1.getRow(rowIndex);
    		    String   orderId = "";  //订单号
    			String   emsCode = "";  //快递单号
    			String   emsCompany=""; //快递公司
    			String   ems="";   //快递单号和快递公司
    			//把一行里的每一个字段遍历出来
    			for(int colIndex=0;colIndex<3;colIndex++){
    				//创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
    				Cell cell = row.getCell(colIndex);
    				String value = getCellValue(cell);
    				if(StringUtil.isBlank(value))continue;
    				value = value.trim();
    				if(colIndex==0){
    					orderId = value;
    				}
    				if(colIndex==1){
    					emsCode = value;
    				}
    				if(colIndex==2){
    					emsCompany = value;
    				}
    				ems = emsCode+"_"+emsCompany;
    			}
    			locationmap.put(orderId, ems);
    	   }
    	   plartFromOrderRecordService.update(locationmap);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	//导出Excel
	@RequestMapping("/orderOutExecl")
	public void orderOutExecl(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		 MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false,true,true);
		 String[] titleArr= new String[]{"订单号","快递单号","快递公司"};
		 searchMap.put("noStartRowAndEndRow", "yes");
		 
		 MiniUiGrid miniuigrid = plartFromOrderRecordService.searchAllOrderId(searchMap);
		 
		 int totalCnt = miniuigrid.getTotal(); //得到的总数量,条数
		 
		 List<PlatFormOrderRecord> orderList =(List<PlatFormOrderRecord>)miniuigrid.getData();
		//准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
		 String[][] resultArr = new String[totalCnt+1][titleArr.length]; 
		 resultArr[0] = titleArr; //titleArr= new String[]{"订单号","快递单号","快递公司"};标题那一行(第一行)
		 for(int i=0;i<resultArr.length-1;i++){
			 String[] rowArr = resultArr[i+1];
			 PlatFormOrderRecord pd = orderList.get(i);
			 rowArr[0] = pd.getOrderId();
			 rowArr[1] = pd.getEmsCode();
			 rowArr[2] = pd.getEmsCompany();
		 }
		 
		 HSSFWorkbook exportBook =new HSSFWorkbook();
		 HSSFSheet exportSheet = exportBook.createSheet("sheet0");
		 HSSFCellStyle exportStyle = exportBook.createCellStyle();
		 exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
		 for(int rowIndex=0;rowIndex<resultArr.length;rowIndex++){
			 String[] row =  resultArr[rowIndex];
			 
	    		Row exportRow = exportSheet.createRow(rowIndex);

	    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
	    		{
	         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
	    		}
		
		 }
		 
	    	 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
	        response.setContentType("application/vnd.ms-excel");    
	        response.setHeader ("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
	        OutputStream ouputStream = response.getOutputStream();    
	        exportBook.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();    

	};


	//选中导出Excel
	@RequestMapping("/orderOutCheckExecl")
	public void orderOutCheckExecl(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String[] titleArr= new String[]{"订单号","快递单号","快递公司"};
		String orderIdAndEms =request.getParameter("orderIdAndEms"); // 2002942474:EK224660462HK:ems: ;2002957360:EK224660051HK:ems: ;200294244302:EK224660476HK:ems
		String[] idAndEms = orderIdAndEms.split(";");
		int totalCnt = idAndEms.length; //得到的总数量,条数
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; //准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
		resultArr[0] =titleArr;
		
	for(int i=0;i<resultArr.length-1;i++){
		String[] rowArr = resultArr[i+1];
		String[] orderEms = idAndEms[i].split(":");
		rowArr[0] = orderEms[0];
		rowArr[1] = orderEms[1];
		rowArr[2] = orderEms[2];

	}
	
	 HSSFWorkbook exportBook =new HSSFWorkbook();
	 HSSFSheet exportSheet = exportBook.createSheet("sheet0");
	 HSSFCellStyle exportStyle = exportBook.createCellStyle();
	 exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
	 for(int rowIndex=0;rowIndex<resultArr.length;rowIndex++){
		 String[] row =  resultArr[rowIndex];
		 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
	
	 }
	 
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
    	
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader ("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();   
        exportBook.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();
		 
	
	}
	
	
	
	
	
	 private String getCellValue(Cell cell) {
	    	if(null == cell) return"";
	        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(cell.getBooleanCellValue());
	        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        	DecimalFormat df = new DecimalFormat("0");  
	            return df.format(cell.getNumericCellValue());
	        } else {
	            return String.valueOf(cell.getStringCellValue());
	        }
	    }
		/**
		 * 订单发货通知
		 */
		@RequestMapping("/order/shipped")
		public @ResponseBody Object  addProductToCookies(HttpServletRequest request,HttpServletResponse respons){
				String orderIds = request.getParameter(cookieKey);
				 List<String> newiorderIds = getorderIds(orderIds);
				 boolean  flag =true;
				 for(String idorder : newiorderIds){
					 if(flag==true){
					 TreeMap<String, String> map = new TreeMap<String, String>();
					 
					 PlatFormOrderRecord platFormOrderDetail =  plartFromOrderRecordService.getems(idorder);
		 
					 String orderId = platFormOrderDetail.getOrderId();
					 String emsCode = platFormOrderDetail.getEmsCode();
					 String emsCompany = platFormOrderDetail.getEmsCompany();
					 String type = platFormOrderDetail.getType();	
						if("hk".equals(type)){
							map.put("vendorCode", vendorCodehk);
						}
						else{
							map.put("vendorCode", vendorCodesh);
						}
					 String nowTime = sdf.format(new Date());
					 map.put("timestamp",nowTime);
					 map.put("v",v);
					 map.put("format",format);
					 map.put("signMethod",signMethod);
					 map.put("orderId",orderId);
					 map.put("stageOneWaybill",emsCode);
					 map.put("stageOneWaybillCompany",emsCompany);
					 map.put("shippedTime",nowTime);
					 map.remove("sign");
					 String sign = getsign(map,type);
					 map.put("sign",sign);
					 String response = HttpRequest.sendPost(api_urls,map); 
					 
					 try {
						JSONObject jsonObject = new JSONObject(response);
						if("200".equals(jsonObject.getString("code"))){
							//更新成功
							flag = true;							
						}else{
							//更新失败
							flag = false;
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					 }
				 }
			return flag;
		}
		/*
		 * 6.12	供应商调取本接口获取面单(跨境供应商) 
		 */
		private static final String api_url = "http://116.62.245.214/order/getWaybill?";//测试环境更新库存接口
		@RequestMapping("/getWaybill")
		public @ResponseBody Object  getWaybill(HttpServletRequest request,HttpServletResponse respons){
			String orderCode = request.getParameter("orderCode");
			TreeMap<String, String> map = new TreeMap<String, String>();
			
			String type = "hk";
			String nowTime = sdf.format(new Date());
			 map.put("timestamp",nowTime);
			 map.put("v",v);
			 map.put("format",format);
			 map.put("signMethod",signMethod);
			 map.put("vendorCode", vendorCodehk);
			 map.put("orderCode",orderCode);
			 map.remove("sign");
			 String sign = getsign(map,type);
			 map.put("sign",sign);
			//2005219310 
			 //2005210160
			 String response = HttpRequest.sendGetMLH(api_url, map);
//			 System.out.println(response);
			 //{"code":200,"data":{"waybillUrl":"http://cgtrade.cainiao.com/tfscom/TB1zuAtzpzqK1RjSZSgXXcpAVXa.tfsprivate.pdf"},"codeMsg":"Success"}
			 JSONObject jsonObject;
			 Map<String,String> maps = new HashMap<String, String>();
			try { 
				jsonObject = new JSONObject(response);
				if("200".equals(jsonObject.getString("code"))){
					String data = jsonObject.getString("data");
					String codeMsg = jsonObject.getString("codeMsg");
					JSONObject jsonObjectData = new JSONObject(data);
					String waybillUrl = jsonObjectData.getString("waybillUrl");
					maps.put("code", "200");
					maps.put("waybillUrl", waybillUrl);
					maps.put("codeMsg", codeMsg);
					PlatFormOrderRecord pf = new PlatFormOrderRecord();
					pf.setOrderId(orderCode);
					pf.setBillHref(waybillUrl); //面单的链接
					platFormOrderRecordDao.updateOrderToBill(pf);//第一次获取的面单链接放入表
//					String sta = getStatus(orderCode,waybillUrl);
//					maps.put("status", sta);
				}else if("300".equals(jsonObject.getString("code"))){
					String codeMsg = jsonObject.getString("codeMsg");
					maps.put("code", "300");
					maps.put("codeMsg", codeMsg);
				}else{
					String codeMsg = jsonObject.getString("codeMsg");
					maps.put("codeMsg", codeMsg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
				
			return maps;
		}

//		//将面单的连接放入表中和获取面单的次数（弃用）
//		public String getStatus(String orderCode,String waybillUrl){
//			Map<String,String> map = new HashMap<String, String>();
//			PlatFormOrderRecord pf = new PlatFormOrderRecord();
//			map.put("orderId", orderCode);
//			List<PlatFormOrderRecord> pList = platFormOrderRecordDao.selectAllOrderidOne(map);
//			String status = pList.get(0).getStatus();
//			if(StringUtil.isBlank(status)){
//				status = "0";
//			}
//			int countStatus = Integer.parseInt(status);
//			countStatus = countStatus +1;
//			pf.setStatus(countStatus+"");
//			pf.setOrderId(orderCode);
//			pf.setBillHref(waybillUrl); //回传面单的连接
//			platFormOrderRecordDao.updateOrderToBill(pf);
//			List<PlatFormOrderRecord> pLists = platFormOrderRecordDao.selectAllOrderidOne(map);
//			status = pLists.get(0).getStatus();
//			return status;
//		}
		
		//获取面单的打印次数
		@RequestMapping("/getPrintCount")
		public @ResponseBody Object getPrintCount(HttpServletRequest request,HttpServletResponse respons){
			String orderCode = request.getParameter("orderCode");
			Map<String,String> maps = new HashMap<String, String>();
			Map<String,String> map = new HashMap<String, String>();
			PlatFormOrderRecord pf = new PlatFormOrderRecord();
			map.put("orderId", orderCode);
			List<PlatFormOrderRecord> pList = platFormOrderRecordDao.selectAllOrderidOne(map);
			String status = pList.get(0).getStatus();
			String billHref = pList.get(0).getBillHref();
			if(StringUtil.isBlank(billHref)){
				status = "0";
				maps.put("status", status);
				return maps;
			}
			if(StringUtil.isBlank(status)){
				status = "0";
			}
			int countStatus = Integer.parseInt(status);
			countStatus = countStatus +1;
			pf.setStatus(countStatus+"");
			pf.setOrderId(orderCode);
			platFormOrderRecordDao.updateOrderToBill(pf);
			List<PlatFormOrderRecord> pLists = platFormOrderRecordDao.selectAllOrderidOne(map);
			status = pLists.get(0).getStatus();
			maps.put("status", status);
			return maps;
		}
		

		private static final String api_url_pac = "http://116.62.245.214/order/packing?";//测试环境更新库存接口
		@RequestMapping("/getOrderPack")
		public @ResponseBody Object  getOrderPack(HttpServletRequest request,HttpServletResponse respons){
			String orderCode = request.getParameter("orderCode");
			PlatFormOrderRecord pf = new PlatFormOrderRecord();
			Map<String,String> maps = new HashMap<String, String>();
			pf.setOrderId(orderCode);
			PlatFormOrderRecord pl = platFormOrderRecordDao.selectgetems(pf);
			if(StringUtils.isNotBlank(pl.getBackPackStatus())){
				maps.put("code", "000");
				maps.put("codeMsg", "已回传，只能回传一次");
				return maps;
			}
			
			
			TreeMap<String, String> map = new TreeMap<String, String>();
			String type = "hk";
			String nowTime = sdf.format(new Date());
			 map.put("timestamp",nowTime);
			 map.put("v",v);
			 map.put("format",format);
			 map.put("signMethod",signMethod);
			 map.put("vendorCode", vendorCodehk);
			 map.put("orderCode",orderCode);
			 map.remove("sign");
			 String sign = getsign(map,type);
			 map.put("sign",sign);
			 String response = HttpRequest.sendGetMLH(api_url_pac, map);
//			 System.out.println(response);
			 //"code":500,"subCode":500,"codeMsg":"getWayBill order[orderId=2005237259] error"}
			 //{"code":200,"codeMsg":"Success"}
			 JSONObject jsonObject;
			 
			try { 
				jsonObject = new JSONObject(response);
				if("200".equals(jsonObject.getString("code"))){
					pf.setBackPackStatus("已回传");					
					platFormOrderRecordDao.updateOrderToBill(pf);
					maps.put("code", "200");
					maps.put("codeMsg", "已回传");
				}else if("300".equals(jsonObject.getString("code"))){
					String codeMsg = jsonObject.getString("codeMsg");
					maps.put("code", "300");
					maps.put("codeMsg", codeMsg);
				}else{
					String codeMsg = jsonObject.getString("codeMsg");
					maps.put("codeMsg", codeMsg);
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
				
			return maps;
		}
		
	
		
		
		private  List<String> getorderIds(String idsStr){
			List<String> list  = new ArrayList<String>();
			if(null != idsStr)
			{
				String[] arr = idsStr.split(",");
				for(int i = 0; i < arr.length;i++)
				{
					String orderId = arr[i];
					if(!"".equals(orderId))
						list.add(orderId);
				}
			}
			return list;
		}
		
		
		private String getsign(Map<String, String> map,String type){
			String sign = "";
			Set<Entry<String, String>> entrySet = map.entrySet();
			Iterator<Entry<String, String>> it = entrySet.iterator();
			while (it.hasNext()) {
				Entry<String,String> en = it.next();
				sign += en.getKey() + en.getValue();
			}
//			sign = secret + sign + secret;
			if("hk".equals(type)){
				sign = secrethk + sign + secrethk;
			}
			else{
				sign = secretsh + sign + secretsh;
			}
			sign  = HttpRequest.string2MD5(sign).toUpperCase();
			return sign;
		}
}
