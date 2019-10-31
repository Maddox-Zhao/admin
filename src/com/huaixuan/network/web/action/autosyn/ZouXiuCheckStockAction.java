package com.huaixuan.network.web.action.autosyn;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.dao.autosyn.AutoSyncDao;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2015-11-9 上午11:08:15
 **/

@Controller
public class ZouXiuCheckStockAction
{

		@Autowired
		private AutoSyncDao autoSyncDao; 
		
	/**
	 * 根据走秀和本地导出EXCEL处理走秀库存
	 * @param model  
	 * @return  
	 */
	@RequestMapping(value = "/timetask/zouxiu/toUploadZouXiuStock")
    public String toUploadZouXiuStock(Model model,HttpServletRequest req)
	{
		return "/autosync/uploadZouXiuStock";
	}
	
	@RequestMapping(value = "/timetask/zouxiu/uploadZouXiuStock")
    public void uploadZouXiuStock(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile zouxiuFile = request.getFile("zouxiu");
			MultipartFile locationFile = request.getFile("location");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			
			Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }  
	        Sheet sheet1 = wb.getSheetAt(0);
	        
	        
	        
	        //处理本地库存excel
	        Map<String,Integer> locationMap = new HashMap<String, Integer>();
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	String   key = "";
	        	Integer num = 0;
	        	for(int colIndex = 0; colIndex < 7;colIndex++)//只需要前7列
	        	{
	        		Cell cell = row.getCell(colIndex);
	        		String value = getCellValue(cell);
	        		if(null != value)
	        		{
	            		value = value.toUpperCase();
	        		}
	            	else
	            	{
	            		value= "";
	            	}
	            	if(colIndex == 1)
	            	{
	            		value	= value.replace("T:",""); //型号剔除T:
	            	}
	            	else if(colIndex == 2)
	            	{
	            		value = value.replace("M:", ""); //材质剔除M:
	            	}
	            	else if(colIndex == 3)
	            	{
	            		value = value.replace("C:", ""); //颜色剔除C:
	            	}
	            	else if(colIndex == 4)
	            	{
	            		value = value.replace("TG.", ""); //尺寸剔除TG.和TU:
	            		value = value.replace("TU", "");
	            	}
	            	if(colIndex >= 1 && colIndex <=4)
	            	{
	            		key+=value; //key由型号材质颜色尺寸组成
	            	}
	            	if(colIndex == 6)
	            	{
	            		if(value.indexOf(".") >=0)
	            			value = value.substring(0,value.indexOf("."));
	            		try
	            		{
	            			num  = Integer.valueOf(value);//本地数量
	            		}
	            		catch (Exception e) {
	            				num = 0;
						}
	            	}
	        	}
	        	locationMap.put(key, num);
	        }
	        
	        
	        
	        
	        //处理走秀excel库存
	        in = zouxiuFile.getInputStream();
			locationFileName = zouxiuFile.getOriginalFilename(); 
			fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }
	        sheet1 = wb.getSheetAt(0);
	        
	        
	        //导出excel
	        HSSFWorkbook exportWb = new HSSFWorkbook();
	        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
	        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
	        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
	        for(int  rowIndex = 0;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	if(row == null) continue;
	        	Integer num = 0;
	        	//获取本行的库存数量
	        	if(rowIndex > 0)
	        	{
		        	String   key = "";
		        	Cell cell = row.getCell(12);
	        		String huohao = getCellValue(cell);
	        		String size = getCellValue(row.getCell(14));
	        		if(null != huohao)
	        		{
	        			huohao = huohao.split("_")[0]; //有些货号后面加了尺码（_35） 剔除掉
	        			huohao=huohao.replaceAll("\\s*", ""); //替换货号中间空格
	        			if(huohao.lastIndexOf(".0") > 0)//有些巴宝莉的获取到的货号加了.0去除
	        			{
	        				huohao = huohao.substring(0,huohao.lastIndexOf(".0"));
	        			}
	        		}
	        		if(size != null)
	        		{
	        			size = size.replace("TU", "");
	        			size = size.replace("F", "");
	        			size = size.replace("IT", "");
	        			size = size.replace("UK", "");
	        			size = size.replace("US", "");
	        			size = size.replace("EU", "");
	        			size = size.replace("领围", "");
	        			if("2XL".equals(size)) size = "XXL";
	        			if("3XL".equals(size)) size = "XXXL";
	        			if("2XS".equals(size)) size = "XXS";
	        			if("3XS".equals(size)) size = "XXXS";
	        			if(size.lastIndexOf(".0") > 0)//有些巴宝莉的获取到的货号加了.0去除
	        			{
	        				size = size.substring(0,size.lastIndexOf(".0"));
	        			}
	        		}
	        		key = huohao+size;
	        		num = locationMap.get(key);//获取本地库存
	        		if(null == num) 
	        		{
	        			num = 0;
	        		}
	        		
	        	}
        		
        		Row exportRow = exportSheet.createRow(rowIndex);
        		/*
        		for(int  colIndex = 0;colIndex < row.getLastCellNum();colIndex++)
        		{
             		 //String oldValue = getCellValue(row.getCell(colIndex));
	    			// exportRow.createCell(colIndex).setCellValue(oldValue);
        		}
        		*/
        		
        		if(rowIndex > 0)
        			exportRow.createCell(0).setCellValue(num);
        		else
        			exportRow.createCell(0).setCellValue("本地库存");
        			
	        }
	        response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");    
	        OutputStream ouputStream = response.getOutputStream();    
	        exportWb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();    
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		
	}
	
	/****1号店***/
	@RequestMapping(value = "/timetask/yhd/toSyncStock")
    public String toSyncStock(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
		return "/autosync/syncYhdLcalStock";
	}
	@RequestMapping(value = "/timetask/yhd/syncStock")
    public void syncStock(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile zouxiuFile = request.getFile("yhd");
 
	        //处理一号店excel库存
			InputStream  in = zouxiuFile.getInputStream();
			String locationFileName = zouxiuFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
	        Workbook wb = null;  
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(in);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(in);  
	        }
	        else
	        {
	        	return;
	        }
	        
	        Sheet sheet1 = wb.getSheetAt(0);
	        

	        
	        List<Long> siteList = new ArrayList<Long>();
	        siteList.add(101L);
	        List<StockData>  list = autoSyncDao.searchStockBySiteList(siteList);
	        Map<String,Integer> localStockMap = new HashMap<String, Integer>();
	        for(StockData s : list)
	        {
	        	localStockMap.put(s.getSku(), s.getNum());
	        }
	        
	        
	        //导出excel
	        HSSFWorkbook exportWb = new HSSFWorkbook();
	        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
	        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
	        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
	        for(int  rowIndex = 0;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);
	        	if(row == null) continue;
	        	Integer num = 0;
	        	if(rowIndex > 0)
	        	{
		        	Cell cell = row.getCell(4);
		        	String sku = getCellValue(cell);
		        	//有sku为空的
		        	if(StringUtil.isBlank(sku)) 
		        	{
		        		num = -1;
		        	}
		        	else
		        	{
			        	num = localStockMap.get(sku);//获取本地库存
			        	if(null == num) 
		        		{
		        			num = 0;
		        		}
		        	}
	        	}
	        	 
        		Row exportRow = exportSheet.createRow(rowIndex);
        		if(rowIndex > 0)
        			exportRow.createCell(0).setCellValue(num);
        		else
        			exportRow.createCell(0).setCellValue("本地库存");
        			
	        }
	        response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");    
	        OutputStream ouputStream = response.getOutputStream();    
	        exportWb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();    
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		
	}
	
	/****结束1号店***/
	
	@RequestMapping(value = "/timetask/zhenpin/uploadZhenPinSku")
    public void uploadZhenPinSku(Model model,HttpServletRequest req,HttpServletResponse response)  
	{
    	try
    	{
    		/*
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile locationFile = request.getFile("location");
			InputStream  in = locationFile.getInputStream();
			String locationFileName = locationFile.getOriginalFilename(); 
			String fileType = locationFileName.substring(locationFileName.lastIndexOf(".")+1);
			*/
    		
    		InputStream  in =  new FileInputStream("d:/1.xls");
			Workbook wb = new HSSFWorkbook(in);  
	        
	        Sheet sheet1 = wb.getSheetAt(0);
	        for(int  rowIndex = 1;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
	        {
	        	Row row  =  sheet1.getRow(rowIndex);	         
        		Cell cellPsku = row.getCell(2);
        		Cell cellBianMa = row.getCell(4);
        		Cell cellChima = row.getCell(12);
        		String zhenpSku = getCellValue(cellPsku);
        		String huohao = getCellValue(cellBianMa);
        		String size = getCellValue(cellChima);
        		if(huohao.startsWith("SH"))
        		{
	        		huohao = huohao.replace("SHHK-", "");
	        		huohao = huohao.replace("SHHK", "");
	        		
	        		huohao = huohao.replace("SS-", "");
	        		huohao = huohao.replace("SS", "");
	        		
	        		huohao = huohao.replace("SH-", "");
	        		huohao = huohao.replace("SH", "");
        		}
        		huohao = huohao.trim();
        		
        		size = size.replace("号", "");
        		size = size.replace("uk", "");
        		size = size.replace("均码", "");
        		size = size.trim();
        		
        		
        		String type = "";
        		String material = "";
        		String color = "";

        		String[] arr = huohao.split(" ");
        		// 获取型号材质颜色
        		if (arr.length == 3)
        		{
        			type = arr[0];
        			material = arr[1];
        			color = arr[2];
        		}
        		else if (arr.length == 1)
        		{
        			type = arr[0];
        		}
        		else if(arr.length == 2)
        		{
        			type = arr[0];
        			color = arr[1];
        		}
        		else
        		{
        			System.out.println("获取材质颜色出错:" + huohao);
        		}

        		if("3XL".equals(size)) size = "XXXL";
        		if("2XL".equals(size)) size = "XXL";
        		Map<String,String> map = new HashMap<String, String>();
        		map.put("type", type);
        		map.put("material", material);
        		map.put("color", color);
        		map.put("size", size);
        	
        		String localSku =  autoSyncDao.getSupplkierSkuByInfo(map);
        		if("".equals(localSku) && arr.length == 2)
        		{
        			map.put("type", type);
        			map.put("material", color);
        			map.put("color", "");
        			map.put("size", size);
        			localSku =  autoSyncDao.getSupplkierSkuByInfo(map);
        		}
        		
        		HttpRequest.fileAddContext("zouxiu.txt", zhenpSku + "," + localSku);
	        }	         
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		
	}
    
    
    private String getCellValue(Cell cell) {
    	if(null == cell) return"";
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }
    
    
}
 
