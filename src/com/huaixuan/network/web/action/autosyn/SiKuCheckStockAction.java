package com.huaixuan.network.web.action.autosyn;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



/**
 * @author Mr_Yang   2015-11-9 上午11:08:15
 * 司库自动匹配库存
 **/

@Controller
public class SiKuCheckStockAction
{

	/**
	 * 根据司库和本地导出EXCEL处理司库库存
	 * @param model  
	 * @return  
	 */
	@RequestMapping(value = "/timetask/siku/toUploadSiKuStock")
    public String toUploadZouXiuStock(Model model,HttpServletRequest req)
	{
		return "/autosync/uploadSiKuStock";
	}
	
	@RequestMapping(value = "/timetask/siku/uploadSiKuStock")
    public void uploadZouXiuStock(Model model,HttpServletRequest req,HttpServletResponse response)  
	{

    	try
    	{
	    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			MultipartFile zouxiuFile = request.getFile("siku");
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
	        	Integer num = 0;
	        	Cell cell = row.getCell(5);
        		String sku = getCellValue(cell);
        		String numStr = getCellValue(row.getCell(6));
        		num = Integer.parseInt(numStr);
	        	locationMap.put(sku, num);
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
	        	Integer num = 0;
	        	//获取本行的库存数量
	        	if(rowIndex > 0)
	        	{
		        	Cell cell = row.getCell(2);
	        		String huohao = getCellValue(cell);
	        		String[] huoHaoArr = huohao.split("-");
	        		if(huoHaoArr.length == 2)
	        		{
		        		num = locationMap.get(huoHaoArr[1]);//获取本地库存
		        		if(null == num) 
		        		{
		        			num = 0;
		        		}
	        		}
	        		else
	        		{
	        			num = 0;
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
    
    
}
 
