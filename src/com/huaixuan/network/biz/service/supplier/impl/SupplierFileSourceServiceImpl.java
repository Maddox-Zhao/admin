package com.huaixuan.network.biz.service.supplier.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.supplier.SupplierFileSourceDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.Result;
import com.huaixuan.network.biz.service.supplier.SupplierFileSourceService;




/**
 * @author Mr_Yang   2015-9-7 ����02:20:37
 **/

@Service("supplierFileSourceService")
public class SupplierFileSourceServiceImpl implements SupplierFileSourceService
{
	@Autowired
	private SupplierFileSourceDao supplierFileSourceDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	public QueryPage searchSupplierFileSourceListWithPage(SupplierFileSource supplierFileSource,
			int currPage, int pageSize)
	{
		    QueryPage queryPage = new QueryPage(supplierFileSource);
	        Map pramas = queryPage.getParameters();
	        int count = supplierFileSourceDao.searchFileSourceCount(pramas);
	        if (count > 0) {
	            /* ��ǰҳ */
	            queryPage.setCurrentPage(currPage);
	            /* ÿҳ���� */
	            queryPage.setPageSize(pageSize);
	            queryPage.setTotalItem(count);

	            pramas.put("startRow", queryPage.getPageFristItem());
	            pramas.put("endRow", queryPage.getPageLastItem());

	            List<SupplierFileSource> supplierList = supplierFileSourceDao.searchFileSource(pramas);
	            if (supplierList != null && supplierList.size() > 0) {
	                queryPage.setItems(supplierList);
	            }
	        }
	        return queryPage;
	}

	@Override
	public SupplierFileSource getSupplierFileSourceById(Long id)
	{
		return supplierFileSourceDao.getSupplierFileSourceByFileId(id);
	}

	
	
	@Override
	public void updateSupplierFileSourceByNotNull(
			SupplierFileSource supplierFileSource)
	{
		supplierFileSourceDao.updateSupplierSourceFile(supplierFileSource);
	}
	
	
	
	
	
	
	
	//�ϴ�excel����
	
	
	//�����ϴ���excel
	@Override
	public Long insertSupplierFile(SupplierFile supplierFile)
	{
		return supplierFileSourceDao.insertSupplierFile(supplierFile);
	}

	
	 /**
	  * ��excel���ݵ���supplierGoods��
	  * @param path ·��
	  * @return
	  */
	@Override
	public Result exportExcel2SupplierGoods(String filePath,Long supplerId,Long fileId)
	{
		File file = new File(filePath);
		InputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		return exportExcel2SupplierGoods(fis,supplerId,fileId);
	}

	@Override
	public Result exportExcel2SupplierGoods(InputStream is,Long supplerId,Long fileId)  
	{
		String[] title = new String[]{"brand","series","style","type","material","color","cost","retail","percent","price","currency","size/stock"};		
		List<String[]> goodsList = new ArrayList<String[]>();
		 XSSFWorkbook wb = null;
		 Result result = new Result();//���ؽ����
		 Map<String,String> map = new HashMap<String, String>();
		 try
		 {
			 wb = new XSSFWorkbook(is);
			 //���sheet������
             XSSFSheet sheet = wb.getSheetAt(0);
             Iterator<Row> it = sheet.rowIterator();
             int rowIndex = 0;
             while(it.hasNext())
             {
            	 Row row = it.next();
            	 Iterator<Cell> cellIt = row.cellIterator();
            	 String[] rowArr = new String[title.length];
            	 int cellIndex = -1;
            	 while(cellIt.hasNext())
            	 {
            		 cellIndex++;
            		 Cell cell = cellIt.next();
            		
            		 String content = cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC?cell.getNumericCellValue()+"":cell.getStringCellValue();
            		 if(cellIndex == 3 || cellIndex == 4 || cellIndex == 5) //�ͺ�  ����  ��ɫ ��Ϊ���� ת��һ��
            		 {
            			 if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC)
            			 {
            				 double d = cell.getNumericCellValue();//Excel���  30254���������� ������ȡ����Ϊ30254.0  �ͺŲ�����ɫ ȥ��.0
            				 int t = (int)d;
            				 content = t+"";
            			 }
            			 else
            			 {
            				 content = cell.getStringCellValue();
            			 }
            		 }
            		 if(!title[cellIndex].equals(content) && rowIndex==0)//  �ж��ϴ���excel��ģ���excel�Ƿ�һ��
            		 {
            			 result.setOK(false);
            			 result.setMsg("template error");
            			 return result;
            		 }
            		 if(rowIndex == 0)continue; //ģ��ͷ��Ҫ
            		 rowArr[cellIndex] = content;
            	 }
            	 if(rowIndex != 0)
            		 goodsList.add(rowArr);
            	 rowIndex++;
             }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 
		//��ȡ���,������ String[] title = new String[]{"brand","series","style","type","material","color","cost","retail","percent","price","currency","size/stock"};
		 for(String[] arr : goodsList)
		 {

			 String sizeAndStock = arr[11];
			 SupplierGoods supplierGoods =  updateOrInsertSupplierGoods(arr,supplerId,fileId,map);  //goods������ ���
			 
			 
			 int totalNum = 0;//�����ϴ��ĸò�Ʒ������
			 //����size
			 if(sizeAndStock.indexOf("/") >= 0)  //��size
			 {
				 //size ��ʽΪ90/5 91/10 100/4   ����/���
				 sizeAndStock = sizeAndStock.trim();
				 String[] sizeArr = sizeAndStock.split(" ");
				 for(int i = 0; i < sizeArr.length; i++)
				 {
					 String sizeStr = sizeArr[i];
					 sizeStr = sizeStr.trim();
					 String[] sizeArrTmp = sizeStr.split("/");
					 
					 if(sizeArrTmp.length == 2)
					 {
						 String size = sizeArrTmp[0];
						 size = size.trim();
						 String sizeStockStr = sizeArrTmp[1];
						 sizeStockStr = sizeStockStr.trim();
						 int sizeStock = Integer.valueOf(sizeStockStr);
						 
						 totalNum+=sizeStock; //���������
						 map.put("goodsId", supplierGoods.getId()+"");
						 map.put("size", size);
						 
						 //��ѯ�ò�Ʒ��Ӧ��size���ڷ� ���������½�  ������¸�size���
						 SupplierGoodsSize goodsSize = supplierFileSourceDao.getSupplierGoodsSizeByMap(map);
						 
						 if(goodsSize == null)  //�ò�Ʒ�ĸ�size������  �½�
						 {
							 goodsSize = new SupplierGoodsSize();
							 goodsSize.setNum(sizeStock);
							 goodsSize.setGoodsId(supplierGoods.getId());
							 goodsSize.setSize(size);
							 supplierFileSourceDao.insertSupplierGoodsSize(goodsSize);//���
							 
						 }
						 else //�ò�Ʒ��size�Ѿ����� ���¿��
						 {
							 int num = goodsSize.getNum();
							 num+=sizeStock;
							 goodsSize.setNum(num);
							 supplierFileSourceDao.updateSupplierGoodsSize(goodsSize);//���¿��
						 }
						 
					 }
				 }
			 }
			 else //û��size
			 {
				 double stock = Double.parseDouble(sizeAndStock);
				 totalNum = (int)stock;
				 
			 }
			 
			 
			 //����goods���ܿ��
			 Integer currNum = supplierGoods.getTotalNum();
			 if(currNum == null) currNum = 0;
			 currNum = (Integer) (currNum + totalNum);
			 supplierGoods.setTotalNum(currNum);
			 supplierFileSourceDao.updateSupplierGoods(supplierGoods);
		 }
		 result.setOK(true);
		 result.setMsg("");
		return result;
	}
	
	private SupplierGoods updateOrInsertSupplierGoods(String[] arr,Long supplerId,Long fileId,Map map)
	{
		 String brandName = arr[0];
		 String series = arr[1];
		 String style = arr[2];
		 String type = arr[3];
		 String material = arr[4];
		 String color = arr[5];
		 String cost = arr[6];
		 String retail = arr[7];
		 String percent = arr[8];
		 String price = arr[9];
		 String currency = arr[10];
		 String sizeAndStock = arr[11];
		 map.put("type", type);
		 map.put("material", material);
		 map.put("color", color);
		 map.put("idSupply", supplerId+"");
		 map.put("idFile", fileId);//�ļ���goods һ�Զ�
		 map.put("isDelete", 0); //δɾ��
	
		 SupplierGoods supplierGoods  = supplierFileSourceDao.getSupplierGoodsByMap(map);
		 if(supplierGoods == null) //��Ʒ������
		 {
			 supplierGoods = new SupplierGoods();
			 if(null != brandName)  brandName = brandName.trim();
			 if(null != series)  series = series.trim();
			 if(null != style)  style = style.trim();
			 if(null != type)  type = type.trim();
			 if(null != material)  material = material.trim();
			 if(null != color)  color = color.trim();
			 if(null != color)  color = color.trim();
			 
			 supplierGoods.setBrandName(brandName);
			 supplierGoods.setSeries(series);
			 supplierGoods.setStyle(style);
			 supplierGoods.setType(type);
			 supplierGoods.setMaterial(material);
			 supplierGoods.setColor(color);
			 
			 supplierGoods.setCost(new BigDecimal(cost));
			 supplierGoods.setPercent(new BigDecimal(percent));
			 supplierGoods.setRetail(new BigDecimal(retail));
			 supplierGoods.setPrice(new BigDecimal(price));
			 supplierGoods.setCurrency(currency);
			 supplierGoods.setIdSupply(supplerId);
			 supplierGoods.setIdFile(fileId);
			 supplierGoods.setTotalNum(0);
			 supplierGoods.setTitle(brandName + " " + series + " " + type + " " + material + " " + color);
			 
			 //���ϵͳ�Ѿ�����ͼƬ������ͼƬ
			 Goods g = new Goods();
			 g.setType(type);
			 g.setMaterial(material);
			 g.setColor(color);
			 g = goodsDao.getGooodsByTypeMaterialColor(map);
			 if(g != null)
			 {
				 supplierGoods.setImgOriginal(g.getOriginalImg());
				 supplierGoods.setImgLarge(g.getImgLarge());
				 supplierGoods.setImgMiddle(g.getImgMiddle());
				 supplierGoods.setImgSmall(g.getImgSmall());
			 }

			 supplierFileSourceDao.insertSupplierGoods(supplierGoods);
		 }
		
		 return supplierGoods;
	}
	
 
}
 
