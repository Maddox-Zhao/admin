package com.autocreate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-6-2 下午05:36:50
 **/

public class ExcelMainTest
{
	    private  static DecimalFormat df = new DecimalFormat("0");  
	    
		public  static  String getCellValue(Cell cell) {
    	if(null == cell) return"";
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    } 
	public static void main(String[] args) throws Exception
	{
		
		InputStream  in = new FileInputStream("d:\\local.xlsx");
        Workbook wb   = new XSSFWorkbook(in);  
        Map<String,String> localMap = new HashMap<String, String>();
        Sheet sheet1 = wb.getSheetAt(0);

        for(int  rowIndex = 0;rowIndex < sheet1.getPhysicalNumberOfRows();rowIndex++)
        {
        	Row row  =  sheet1.getRow(rowIndex);
        	if(row == null) continue;
 
        	if(rowIndex > 0)
        	{
	        	Cell cell = row.getCell(0);
	        	String zpSku = getCellValue(cell);
	        	
	        	
	        	cell = row.getCell(1);
	        	String sku = df.format(cell.getNumericCellValue());
	        	sku = sku.trim();
	        	localMap.put(zpSku, sku);
        	}
        }

        InputStream  in1 = new FileInputStream("d:\\zp.xlsx");
        Workbook wb1   = new XSSFWorkbook(in1);  
        Sheet sheet2 = wb1.getSheetAt(0);
        Map<String,String> serverMap = new HashMap<String, String>();
        for(int  rowIndex = 0;rowIndex < sheet2.getPhysicalNumberOfRows();rowIndex++)
        {
        	Row row  =  sheet2.getRow(rowIndex);
        	if(row == null) continue;
 
        	if(rowIndex > 0)
        	{
	        	Cell cell = row.getCell(0);
	        	String zpSku = getCellValue(cell);
	        	
	        	
	        	cell = row.getCell(1);
	        	
	        	String sku  = "";
	        	if(StringUtil.isNotEmpty(getCellValue(cell)))
	        	{
	        		try
					{
	        			sku = df.format(cell.getNumericCellValue());
					}
					catch (Exception e)
					{
						 sku = getCellValue(cell);
					}
	        		
	        	}
	        	sku = sku.trim();
	        	serverMap.put(zpSku, sku);
        	}
        }
        Set<Entry<String,String>> set =  localMap.entrySet();
        Iterator<Entry<String,String>> it = set.iterator();
        int index = 1;
        while(it.hasNext())
        {
        	Entry<String,String> en = it.next();
        	String zpSku = en.getKey();
        	String sku = en.getValue();
        	if(!sku.equals(serverMap.get(zpSku)))
        	{
        		System.out.println(index++ + " " +zpSku + "," + sku + " s:" + serverMap.get(zpSku));
        	}
        
        }
        
 
 
	}
}
 
