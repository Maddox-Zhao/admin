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
 * @author Mr_Yang   2015-9-7 下午02:20:37
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
	            /* 当前页 */
	            queryPage.setCurrentPage(currPage);
	            /* 每页总数 */
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
	
	
	
	
	
	
	
	//上传excel操作
	
	
	//保存上传的excel
	@Override
	public Long insertSupplierFile(SupplierFile supplierFile)
	{
		return supplierFileSourceDao.insertSupplierFile(supplierFile);
	}

	
	 /**
	  * 将excel数据导入supplierGoods表
	  * @param path 路径
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
		 Result result = new Result();//返回结果集
		 Map<String,String> map = new HashMap<String, String>();
		 try
		 {
			 wb = new XSSFWorkbook(is);
			 //获得sheet工作簿
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
            		 if(cellIndex == 3 || cellIndex == 4 || cellIndex == 5) //型号  材质  颜色 不为数字 转化一下
            		 {
            			 if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC)
            			 {
            				 double d = cell.getNumericCellValue();//Excel填的  30254这样的数据 这里提取出来为30254.0  型号材质颜色 去掉.0
            				 int t = (int)d;
            				 content = t+"";
            			 }
            			 else
            			 {
            				 content = cell.getStringCellValue();
            			 }
            		 }
            		 if(!title[cellIndex].equals(content) && rowIndex==0)//  判断上传的excel和模板的excel是否一致
            		 {
            			 result.setOK(false);
            			 result.setMsg("template error");
            			 return result;
            		 }
            		 if(rowIndex == 0)continue; //模板头不要
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
		 
		 
		//读取完成,处理导入 String[] title = new String[]{"brand","series","style","type","material","color","cost","retail","percent","price","currency","size/stock"};
		 for(String[] arr : goodsList)
		 {

			 String sizeAndStock = arr[11];
			 SupplierGoods supplierGoods =  updateOrInsertSupplierGoods(arr,supplerId,fileId,map);  //goods不存在 添加
			 
			 
			 int totalNum = 0;//本次上传的该产品总数量
			 //处理size
			 if(sizeAndStock.indexOf("/") >= 0)  //有size
			 {
				 //size 格式为90/5 91/10 100/4   尺码/库存
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
						 
						 totalNum+=sizeStock; //添加总数量
						 map.put("goodsId", supplierGoods.getId()+"");
						 map.put("size", size);
						 
						 //查询该产品对应的size存在否 不存在则新建  否则更新该size库存
						 SupplierGoodsSize goodsSize = supplierFileSourceDao.getSupplierGoodsSizeByMap(map);
						 
						 if(goodsSize == null)  //该产品的该size不存在  新建
						 {
							 goodsSize = new SupplierGoodsSize();
							 goodsSize.setNum(sizeStock);
							 goodsSize.setGoodsId(supplierGoods.getId());
							 goodsSize.setSize(size);
							 supplierFileSourceDao.insertSupplierGoodsSize(goodsSize);//添加
							 
						 }
						 else //该产品的size已经存在 更新库存
						 {
							 int num = goodsSize.getNum();
							 num+=sizeStock;
							 goodsSize.setNum(num);
							 supplierFileSourceDao.updateSupplierGoodsSize(goodsSize);//更新库存
						 }
						 
					 }
				 }
			 }
			 else //没有size
			 {
				 double stock = Double.parseDouble(sizeAndStock);
				 totalNum = (int)stock;
				 
			 }
			 
			 
			 //更新goods表总库存
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
		 map.put("idFile", fileId);//文件和goods 一对多
		 map.put("isDelete", 0); //未删除
	
		 SupplierGoods supplierGoods  = supplierFileSourceDao.getSupplierGoodsByMap(map);
		 if(supplierGoods == null) //产品不存在
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
			 
			 //如果系统已经存在图片，更新图片
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
 
