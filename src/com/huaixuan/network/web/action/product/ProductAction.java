package com.huaixuan.network.web.action.product;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.Site;
import com.huaixuan.network.biz.domain.order.ProductShoppingCar;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.product.ProductSuoKuProduct;
import com.huaixuan.network.biz.domain.shop.Series;
import com.huaixuan.network.biz.service.base.SiteService;
import com.huaixuan.network.biz.service.order.OrderService;
import com.huaixuan.network.biz.service.order.ProductShoppingCarService;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang 2015-11-20 12:07:01
 **/

@Controller
@RequestMapping("/product")
public class ProductAction {
	@Autowired
	private ProductService productService;
	
	private static final String cookieKey =  "idProducts";
	
	@Autowired
	private ProductShoppingCarService productShoppingCarService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private StockUpdateService stockUpdateService;

	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/list")
	@AdminAccess
	public String getList(AdminAgent adminAgent,HttpServletRequest request,Model model) {
		model.addAttribute("site",adminAgent.getSiteId());	
		model.addAttribute("dutyids", adminAgent.getDutyIds());
//		seriesInit(model);
		return "/product/list";
	}
	
	
	
	//方法二
	@RequestMapping("/getOneSeries")
	public @ResponseBody Object getAllSeries(HttpServletRequest request)
	{
		Series ser = new Series();
		ser.setParentIdSeries(-1);
		List<Series> oneSeries = brandService.getSeriesBySeries(ser);
		return oneSeries;
	}
	@RequestMapping(value = "/getTwoSeries")
	@ResponseBody
	public Object getTwoSeries(HttpServletRequest request) {
		// 初始化城市资
		int idSeries = Integer.parseInt(request.getParameter("idSeries"));
		Series series = new Series();
		series.setParentIdSeries(idSeries);
		List<Series> twoSeries = brandService.getSeriesBySeries(series);
		return twoSeries;
	}
	@RequestMapping(value = "/getThreeSeries")
	@ResponseBody
	public Object getTreeSeries(HttpServletRequest request) {
		// 初始化城市资
		int idSeries = Integer.parseInt(request.getParameter("idSeries"));
		Series series = new Series();
		series.setParentIdSeries(idSeries);
		List<Series> threeSeries = brandService.getSeriesBySeries(series);
		return threeSeries;
	}
	
	@RequestMapping("/toDxList")
	@AdminAccess
	public String toDxList(AdminAgent adminAgent,HttpServletRequest request,Model model) {
		model.addAttribute("site",adminAgent.getSiteId());	
		model.addAttribute("idLocation", adminAgent.getSearchIdSites());
		return "/product/dx_product_list";
	}
	@RequestMapping("/toShowImg")
	public String toShowImg(AdminAgent adminAgent,HttpServletRequest request,Model model) {
		return "/product/showImg";
	}
	 
	@RequestMapping("/toEdit")
	@AdminAccess
	public String getDetail(HttpServletRequest request,Model model,AdminAgent adminAgent) {
		model.addAttribute("curSite", adminAgent.getSiteId());
		return "/product/productDetail";
	}
	
	
 
	@RequestMapping("/getJsonList")
	@AdminAccess
	public @ResponseBody
	Object productList2Json(HttpServletRequest request,AdminAgent adminAgent) {
  		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		Long idSite = adminAgent.getSiteId();
		
		Site site = siteService.getSiteByIdSite(idSite+"");
		searchMap.put("userName", adminAgent.getUsername());
		//如果是代销客户 只能查 101 103 104可售
		if(site.getType() == 4) 
		{
			searchMap.put("status", "1");
			if(StringUtil.isBlank(searchMap.get("idLocation")))
			searchMap.put("idLocation", "101,209,103,104");
		}
		//如果是门店只查看 216所有状态
		if(searchMap.get("userName").equals("30001")) 
		{
			if(StringUtil.isBlank(searchMap.get("idLocation")))
			searchMap.put("idLocation", "216");
		}
		
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		if(searchMap.get("tongkuan")!=null){
		if(searchMap.get("tongkuan").equals("TRUE")){
			
			 List<Product> po = productService.selectProductByType(searchMap);
			 searchMap.put("type", po.get(0).getType());
		     searchMap.remove("tongkuan");
		     searchMap.remove("sku");
		     searchMap.remove("idProduct");
		     
		     return productService.getProductList(searchMap);
		
	   }
		}
		return productService.getProductList(searchMap);

	}
	
	
	@RequestMapping("/exportProduct")
	@AdminAccess
	public void exportProduct(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		Long idSite = adminAgent.getSiteId();
		Site site = siteService.getSiteByIdSite(idSite+"");
		
		//如果是代销客户
		if(site.getType() == 4) 
		{
			searchMap.put("status", "1");
			if(StringUtil.isBlank(searchMap.get("idLocation")))
			searchMap.put("idLocation", "101,209,103,104");
		}
		
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		String[] titleArr = new String[]{"idProduct","sku","品牌","品类","型号","材质","颜色","大小","市场价","尚上价","尚美价","欧洲价","当前位置","状态","入库时间","销售时间","销售单号","售价","销售渠道","客户","活动价","瑕疵"};
		//是否需要显示成本
		if("yes".equalsIgnoreCase(searchMap.get("showCost")))
		{
			titleArr = (String[])MiniUiUtil.arrayAddLength(titleArr,5);//添加数组长度
			titleArr[titleArr.length-5] = "成本";
			titleArr[titleArr.length-4] = "成本币种";
			titleArr[titleArr.length-3] = "汇率";
			titleArr[titleArr.length-2] = "税率";
			titleArr[titleArr.length-1] = "销售币种";
		}
		//"安全技术类别","执行标准","商品名称","材质描述","颜色描述","产地"
		titleArr = (String[])MiniUiUtil.arrayAddLength(titleArr,6);//添加数组长度
		titleArr[titleArr.length-6] = "安全技术类别";
		titleArr[titleArr.length-5] = "执行标准";
		titleArr[titleArr.length-4] = "商品名称";
		titleArr[titleArr.length-3] = "材质描述";
		titleArr[titleArr.length-2] = "颜色描述";
		titleArr[titleArr.length-1] = "产地";
		
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		int cnt = productService.getProductListCount(searchMap);
		if(cnt > 30000)
		{
			return;
		}
		
		searchMap.put("userName", adminAgent.getUsername());
		MiniUiGrid miniuiGrid = productService.getProductList(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<Product> productList = (List<Product>)miniuiGrid.getData();//获取需要导出的数据
 
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1]; //第一行为title  不需要在设置
			Product p = productList.get(i);
			rowArr[0] = p.getIdProduct();
			rowArr[1] = p.getSku();
			rowArr[2] = p.getBrandName();
			rowArr[3] = p.getSeriesName();
			rowArr[4] = p.getType();
			rowArr[5] = p.getMaterial();
			rowArr[6] = p.getColor();
			rowArr[7] = p.getSize();
			rowArr[8] = p.getDlPrice()+"";
			rowArr[9] = p.getSsPrice()+"";
			rowArr[10] = p.getSmPrice()+"";
			rowArr[11] = p.getEuPrice()+"";
			rowArr[12] = p.getCurSiteName();
			rowArr[13] = p.getStatus();
			rowArr[14] = sdf.format(p.getInstock());
			rowArr[15] = p.getSellDate()!=null?sdf.format(p.getSellDate()):"";
			rowArr[16] = p.getSellIdOrder()+"";
			if(p.getSalePrice()!=null && p.getSalePrice()==1.1){
				rowArr[17]="****";
			}else{
				rowArr[17] = p.getSalePrice()==null?"":p.getSalePrice()+"";
			}
			
			rowArr[18] = p.getSellChannel();
			rowArr[19] = p.getCustomerName();
			rowArr[20] = p.getActivePrice()==null?"":(p.getActivePrice()+"");
			if("1".equals(p.getIsFlaw()))
			{
				rowArr[21] = "是";
			}
			else
			{
				rowArr[21] = "否";
			}
		    
			
					
			if("yes".equalsIgnoreCase(searchMap.get("showCost")))//需要显示成本
			{
				rowArr[22] =  p.getCost()+"";
				String costCurrency = "";
				if(p.getIdCostCurrency() == 1)
				{
					costCurrency = "RMB";
				}
				else if(p.getIdCostCurrency() == 2)
				{
					costCurrency = "EU";
				}
				else if (p.getIdCostCurrency() == 3)
				{
					costCurrency = "HKD";
				}
				else if (p.getIdCostCurrency() == 4)
				{
					costCurrency = "US";
				}
				else if (p.getIdCostCurrency() == 5)
				{
					costCurrency = "CHF";
				}
				rowArr[23] = costCurrency;          //成本币种
				rowArr[24] = p.getExchangeRate()+"";   //汇率
				rowArr[25] = (p.getTaxesReate()==null)?"":(p.getTaxesReate()+"");//税率
				
				rowArr[26] = p.getSalePriceCurrency()+"";  //销售币种
				if(p.getSalePriceCurrency() == 1)
				{
					rowArr[26] = "RMB";
				}
				else if(p.getSalePriceCurrency() == 2)
				{
					rowArr[26] = "EU";
				}
				else if (p.getSalePriceCurrency() == 3)
				{
					rowArr[26] = "HKD";
				}
				else if (p.getSalePriceCurrency() == 4)
				{
					rowArr[26] = "US";
				}
				else if (p.getSalePriceCurrency() == 5)
				{
					rowArr[26] = "CHF";
				}else if(p.getSalePriceCurrency() == 0){
					
					rowArr[26]="NULL";
				}
				//"安全技术类别","执行标准","商品名称","材质描述","颜色描述","产地"
				rowArr[27] = p.getSecurityTC();   //安全技术类别
				rowArr[28] = p.getImplementationS();  //执行标准
				rowArr[29] = p.getName();       //商品名称
				if(StringUtil.isBlank(p.getMaterialdes())){
					rowArr[30] = p.getMaterialdes();
				}else{
					rowArr[30] = p.getMaterialdes().replaceAll("\n", " ");   //材质描述
				}
				
				rowArr[31] = p.getColordes();      //颜色描述
				rowArr[32] = p.getOrigin();       //产地
			}else{
				//"安全技术类别","执行标准","商品名称","材质描述","颜色描述","产地"
				rowArr[22] = p.getSecurityTC();   //安全技术类别
				rowArr[23] = p.getImplementationS();  //执行标准
				rowArr[24] = p.getName();       //商品名称
				if(StringUtil.isBlank(p.getMaterialdes())){
					rowArr[25] = p.getMaterialdes();
				}else{
					rowArr[25] = p.getMaterialdes().replaceAll("\n", " ");   //材质描述
				}
				rowArr[26] = p.getColordes();      //颜色描述
				rowArr[27] = p.getOrigin();       //产地
				
			}
			
		}
		  //导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

	}
	
	
	/**
	 * 统计sku-数量
	 * @param request
	 * @param response
	 * @param adminAgent
	 * @throws Exception
	 */
	@RequestMapping("/exportProductForSku")
	@AdminAccess
	public void exportProductForSku(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		Long idSite = adminAgent.getSiteId();
		Site site = siteService.getSiteByIdSite(idSite+"");
		
		//如果是代销客户
		if(site.getType() == 4) 
		{
			searchMap.put("status", "1");
			if(StringUtil.isBlank(searchMap.get("idLocation")))
			searchMap.put("idLocation", "101,209,103,104");
		}
		
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		String[] titleArr = new String[]{"sku","品牌","品类","型号","材质","颜色","大小","市场价","尚上价","尚美价","欧洲价","活动价","数量"};
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		int cnt = productService.getProductListCount(searchMap);
		if(cnt > 30000)
		{
			return;
		}
		Map<String,Integer> contMap = new HashMap<String, Integer>();
		Map<String,Product> productMap = new LinkedHashMap<String, Product>();
		MiniUiGrid miniuiGrid = productService.getProductList(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<Product> productList = (List<Product>)miniuiGrid.getData();//获取需要导出的数据
		int size = productList.size();
		for(int index = size-1; index >= 0; index--)
		{
			Product p = productList.get(index);
			if(productMap.get(p.getSku()) == null)
			{
				productMap.put(p.getSku(), p);
				contMap.put(p.getSku(), 1);
			}
			else
			{
				int cnt1 = contMap.get(p.getSku());
				cnt1++;
				contMap.put(p.getSku(), cnt1);
			}
		}
 
		String[][] resultArr = new String[productMap.size()+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		
		Set<Entry<String, Product>> en = productMap.entrySet();
		Iterator<Entry<String, Product>> it = en.iterator();
		int index = 1;
		while(it.hasNext())
		{
			Entry<String, Product> en1 = it.next();
			Product p = en1.getValue();
			String sku = en1.getKey();
			String[] rowArr = resultArr[index]; //第一行为title  不需要在设置
			rowArr[0] = p.getSku();
			rowArr[1] = p.getBrandName();
			rowArr[2] = p.getSeriesName();
			rowArr[3] = p.getType();
			rowArr[4] = p.getMaterial();
			rowArr[5] = p.getColor();
			rowArr[6] = p.getSize();
			rowArr[7] = p.getDlPrice()+"";
			rowArr[8] = p.getSsPrice()+"";
			rowArr[9] = p.getSmPrice()+"";
			rowArr[10] = p.getEuPrice()+"";
			rowArr[11] = p.getActivePrice()==null?"":p.getActivePrice()+"";
			rowArr[12] = contMap.get(sku)+"";
			index++;
		}

		//导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

	}
	
	
	@RequestMapping("/dxExportProduct")
	@AdminAccess
	public void dxExportProduct(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		Long idSite = adminAgent.getSiteId();
		Site site = siteService.getSiteByIdSite(idSite+"");
		
		//如果是代销客户
		if(site.getType() == 4) 
		{
			searchMap.put("status", "1");
			if(StringUtil.isBlank(searchMap.get("idLocation")))
			searchMap.put("idLocation", "101,209,103,104");
		}
		
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		String[] titleArr = new String[]{"sku","品牌","品类","型号","材质","颜色","大小","尚美价","欧洲价","状态","位置"};
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		MiniUiGrid miniuiGrid = productService.getProductList(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<Product> productList = (List<Product>)miniuiGrid.getData();//获取需要导出的数据
 
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1]; //第一行为title  不需要在设置
			Product p = productList.get(i);
			rowArr[0] = p.getSku();
			rowArr[1] = p.getBrandName();
			rowArr[2] = p.getSeriesName();
			rowArr[3] = p.getType();
			rowArr[4] = p.getMaterial();
			rowArr[5] = p.getColor();
			rowArr[6] = p.getSize();
			rowArr[7] = p.getSmPrice()+"";
			rowArr[8] = p.getEuPrice()+"";
			rowArr[9] = p.getStatus();
			if(MiniUiUtil.shSite.contains(p.getCurSiteId()))
			{
				rowArr[10] = "国内";
			}
			else
			{
				rowArr[10] = "海外";
			}
			  
		}
		  //导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

	}
	
	
	
	
	

	@RequestMapping("/getProduct")
	public @ResponseBody
	Product getProduct(HttpServletRequest request) {
		String idProduct = request.getParameter("idproduct");
		Product product = productService.getproduct(idProduct);
		return product;
	}
 

	/*
	 * 产品入库  准入库转可售
	 */
	@RequestMapping("/productInStock")
	@AdminAccess
	public @ResponseBody Object productInStock(AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(map, false, true, false); //去除空格
		Product product =  (Product)MiniUiUtil.Map2Object(map, Product.class); //map转对象
		product.setIdLastOperator(adminAgent.getUsername()); //最后操作人
		productService.productInStock(product);
		
		return product;
		
	}
	
	
	/*
	 * 产品入库  准入库转可售 -批量入库
	 */
	@RequestMapping("/productBatchInStock")
	@AdminAccess
	public @ResponseBody Object productBatchInStock(AdminAgent adminAgent,HttpServletRequest request)
	{
		String idProductAndSizeStr = request.getParameter("idProductAndSize");//获取产品 idProduct:type:material:color:size;idProduct:type:material:color:size 
		
		if(StringUtil.isNotBlank(idProductAndSizeStr))
		{
			String[] idProductAndSizeArr = idProductAndSizeStr.split(";");
			for(String idProductAndSize : idProductAndSizeArr)
			{
				String[] resultArr = idProductAndSize.split(":");
				String idProduct = resultArr[0];
				String size = "";
				String type = "";
				String material = "";
				String color = "";
				if(resultArr.length >= 5)
				{
					type= resultArr[1];
					if(StringUtil.isNotEmpty(type))
					{
						type = type.trim().toUpperCase();
					}

					material= resultArr[2];
					if(StringUtil.isNotEmpty(material))
					{
						material = material.trim().toUpperCase();
					}
					color= resultArr[3];
					if(StringUtil.isNotEmpty(size))
					{
						color = color.trim().toUpperCase();
					}
					size= resultArr[4];
					if(StringUtil.isNotEmpty(size))
					{
						size = size.trim().toUpperCase();
					}	
				}
				Product product = new Product();
				product.setIdProduct(idProduct);
				product.setType(type);
				product.setMaterial(material);
				product.setColor(color);
				product.setSize(size);
				product.setIdLastOperator(adminAgent.getUsername()); //最后操作人
				product.setSecondHand(-1);//采购产品

				productService.productInStock(product);
				
				ProductShoppingCar productShoppingCar = new ProductShoppingCar();
				productShoppingCar.setUserName(adminAgent.getUsername());
				productShoppingCar.setIdProduct(idProduct);
				productShoppingCarService.removeShoppingCar(productShoppingCar);
				
			}
		}
		return "ok";
		
	}
	
	
	/*
	 * 产品更新
	 */
	@RequestMapping("/updateProduct")
	@AdminAccess
	public @ResponseBody Object updateProduct(AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(map, false, true, false); //去除空格
		Product product =  (Product)MiniUiUtil.Map2Object(map, Product.class); //map转对象
		product.setStatus("");//更新的时候 状态不做更新  防止前端传过来的值有误
		product.setIdLastOperator(adminAgent.getUsername()); //最后操作人
		String oldSku = product.getSku();
		productService.updateProductByNotNull(product);	
		if(!product.getSku().equals(oldSku))
		{
			stockUpdateService.updateStockUpdateProductNum(product.getIdProduct(), oldSku);
		}
		String idProduct = product.getIdProduct();
		Product products = productService.getproduct(idProduct);
		int operator = 15;
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		historyrMap.put("idProduct", products.getIdProduct());//idProduct
		historyrMap.put("idOperator", adminAgent.getUsername());//operator
		historyrMap.put("idCurStation",products.getCurSiteId());//当前站点
		historyrMap.put("idSupply",products.getIdSupply()+"");//idSupply
		 
		historyrMap.put("idOperation", operator+"");//15为产品修改
		historyrMap.put("idCustomer", -1+"");//idCustomer
		historyrMap.put("idStatus",products.getStatusID());
		 
		productDao.insertHistory(historyrMap); //插入历史表
		
		return product;
		
	}
	
	//更新活动价
	@RequestMapping("/updateProductBaseInfo")
	@AdminAccess
	public @ResponseBody Object updateProductBaseInfo(AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(map, false, true, false); //去除空格
		Product product =  (Product)MiniUiUtil.Map2Object(map, Product.class); //map转对象
		product.setStatus("");//更新的时候 状态不做更新  防止前端传过来的值有误
		product.setIdLastOperator(adminAgent.getUsername());
		
		boolean flag = productService.updateProductActivePrice(product);
		//在购物车删除更新成功的产品
		if(flag)
		{
			ProductShoppingCar productShoppingCar = new ProductShoppingCar();
			productShoppingCar.setIdProduct(product.getIdProduct());
			productShoppingCar.setUserName(adminAgent.getUsername());
			productShoppingCarService.removeShoppingCar(productShoppingCar);
		}
		return flag;
	}
	
	@RequestMapping("/updateToXiaCiPin")
	@AdminAccess
	public @ResponseBody Object updateToXiaCiPin(AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(map, false, true, false); //去除空格
		Product product =  (Product)MiniUiUtil.Map2Object(map, Product.class); //map转对象
		//product.setIsFlaw("1");//瑕疵
		productService.updateLifeCyleByNotNull(product);
		stockUpdateService.updateStockUpdateProductNum(product.getIdProduct(),null);
		return "ok";
	}
	
	
	@RequestMapping("/yuding2Cansale")
	@AdminAccess
	public @ResponseBody Object productYuDing2Cansale(AdminAgent adminAgent,HttpServletRequest request)
	{
		String idProducts = request.getParameter("idProducts");
		List<String> productList = getProductIds(idProducts);
		
		for(String idProduct : productList)
		{
			orderService.cancelOrderByIdProduct(idProduct,adminAgent);
			
			//删除购物车 产品
			ProductShoppingCar productShoppingCar = new ProductShoppingCar();
			productShoppingCar.setIdProduct(idProduct);
			productShoppingCar.setUserName(adminAgent.getUsername());
			productShoppingCarService.removeShoppingCar(productShoppingCar);
		}
		return true;
	}
	
	/*
	 *修改订单实际售价
	 */
	@RequestMapping("/updatesalePrice")
	@AdminAccess
	public @ResponseBody Object updatesalePrice(AdminAgent adminAgent,HttpServletRequest request){
		Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(map, false, true, false); //去除空格
		Product product =  (Product)MiniUiUtil.Map2Object(map, Product.class); //map转对象		
		productService.updatesalePrice(product);
		return product;
	}
	
	@RequestMapping("/getProductHistory")
	public @ResponseBody Object selectProductHistoryByIdProduct(AdminAgent adminAgent,HttpServletRequest request)
	{
		String idProduct = request.getParameter("idProduct");
		return productService.selectProductHistoryByIdProduct(idProduct);
	}
	
	
	@RequestMapping("/toGetProductHistory")
	public String toSelectProductHistoryByIdProduct(AdminAgent adminAgent,HttpServletRequest request)
	{
	    return "/product/history";
	}

	
	@RequestMapping("/toGetSuoKuProductList")
	public String toGetSuoKuProductList(Model model,AdminAgent adminAgent,HttpServletRequest request)
	{
		model.addAttribute("site", adminAgent.getSiteId());		
		model.addAttribute("dutyids", adminAgent.getDutyIds());		
		model.addAttribute("suokuId", request.getParameter("suokuId"));	
		return "/product/suokuList";
	}
	
	
	
	@RequestMapping("/getSuoKuProductList")
	public @ResponseBody Object getSuoKuProductList(AdminAgent adminAgent,HttpServletRequest request)
	{
		
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);//request请求转map
		

		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		return productService.getSuoKuProductList(searchMap);
	}
	
	@RequestMapping("/exportSuoKuProduct")
	@AdminAccess
	public void exportSuoKuProduct(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);	
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		String[] titleArr = new String[]{"idProduct","sku","品牌","品类","型号","材质","颜色","大小","尚上价","尚美价","当前位置","状态","客户","客户经理","入库时间","创建时间","创建人"};
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		MiniUiGrid miniuiGrid =  productService.getSuoKuProductList(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<ProductSuoKuProduct> productList = (List<ProductSuoKuProduct>)miniuiGrid.getData();//获取需要导出的数据
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1];   //第一行为title  不需要在设置
			ProductSuoKuProduct p = productList.get(i);
			rowArr[0] = p.getIdProduct();
			rowArr[1] = p.getSku();
			rowArr[2] = p.getBrandName();
			rowArr[3] = p.getSeriesName();
			rowArr[4] = p.getType();
			rowArr[5] = p.getMaterial();
			rowArr[6] = p.getColor();
			rowArr[7] = p.getSize();
			rowArr[8] = p.getSsPrice()+"";
			rowArr[9] = p.getSmPrice()+"";
			rowArr[10] = p.getLocation();
			rowArr[11] = p.getStatus();
			rowArr[12] = p.getCustomerName();
			rowArr[13] = p.getCustomerManager();
			rowArr[14] = p.getInStockDate();
			rowArr[15] = p.getCreateDate();
			rowArr[16] = p.getCreateUserName();
		}
		  //导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=suoku_product_"+System.currentTimeMillis()+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

	}
	
	
	@RequestMapping("/toGetSuoKuList")
	public String toGetSuoKuList(Model model,AdminAgent adminAgent,HttpServletRequest request)
	{
		model.addAttribute("site", adminAgent.getSiteId());
		
		return "/product/suokuOrderList";
	}
	
	@RequestMapping("/getSuoKuList")
	public @ResponseBody Object getSuoKuList(Model model,AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		return productService.getSuoKuList(searchMap);
	}
	
	//查询产品更新记录
	@RequestMapping("/toGetProductUpdateLog")
	public String toGetProductUpdateLog(HttpServletRequest request,AdminAgent adminAgent,Model model){
		return "/product/productUpdateLog";
	}
	
	@RequestMapping("/getProductUpdateLog")
	@ResponseBody
	public Object getProductUpdateLog(HttpServletRequest request,AdminAgent adminAgent,Model model){
		Map<String,String> map = MiniUiUtil.getParameterMap(request);
		MiniUiGrid gird = productService.searchProductUpdateLog(map);
	    return gird;
	}
	
	
	//1652015312136,1652015312137,1652015312138 将ID字符串转为list
	private  List<String> getProductIds(String idsStr)
	{
		List<String> list  = new ArrayList<String>();
		if(null != idsStr)
		{
			String[] arr = idsStr.split(",");
			for(int i = 0; i < arr.length;i++)
			{
				String idProduct = arr[i];
				if(!"".equals(idProduct))
					list.add(idProduct);
			}
		}
		return list;
	}
	
	@RequestMapping("/productprint")
	public String printProduct(HttpServletRequest request,AdminAgent adminAgent,Model model){
		Map<String,String> map = MiniUiUtil.getParameterMap(request);
		map.put("noStartRowAndEndRow", "yes");
		MiniUiGrid gird = productService.getProductList(map);
		List<Product> productList =	(List<Product>) gird.getData();
		model.addAttribute("list",productList);
		return "/product/productprint";
	}
	
	@RequestMapping("/updates")
	public @ResponseBody Object updates(HttpServletRequest request,HttpServletResponse respons,
			AdminAgent adminAgent){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		
		String id = searchMap.get("idProducts");
		String idProduct[] = id.split(",");
		List<String> list  = new ArrayList<String>();
		for(int i = 0; i < idProduct.length;i++){
			list.add(idProduct[i]);
		}
		MiniUiGrid gird = new MiniUiGrid();
		List<Product> productList = productService.getProductsByIdList(list);
		gird.setTotal(productList.size());
		gird.setData(productList);
		return gird;

	}
	@RequestMapping("/batchupdateProduct")
	public @ResponseBody Object batchupdateProduct(AdminAgent adminAgent,HttpServletRequest request){
		
		return request;
	}
	
	
	
	@RequestMapping("/updateProducts")
	public @ResponseBody Object updateProducts(HttpServletRequest request,HttpServletResponse respons,
			AdminAgent adminAgent){
		
		
		/*Map<String,String> map = MiniUiUtil.getParameterMap(request);*/
		int j=0;
		String productAndUpdte = request.getParameter("productAndUpdte"); //获取产品 idProduct:type:material:color:size;idProduct:type:material:color:size
		
		if(StringUtil.isNotBlank(productAndUpdte)){
		
	   	String[] productUpdate= productAndUpdte.split(";"); //[idProduct:type:material:color:size,idProduct:type:material:color:size]
	   	
	   	for(String products:productUpdate){   //product="idProduct:type:material:color:size"
	   		String size="";
	   		String type="";
	   		String material="";
	   		String color="";
	   		
	   		String[] productt = products.split(":");  //[idProduct,type,material,color,size]
	   		//System.out.println(productt.length+"字符串长度");
	   		String idProduct = productt[0];
	   		
	   		  try {
 		        	
	   		    	type = productt[1];
	   		        	
					} catch (Exception e) {
						
						if(StringUtil.isNotBlank(type)){
							type = productt[1];
				   		}else{
				   			
				   			type="";
				   		}
				   		    
					}
	   		
	   		     
	   		  try {
 		        	
	   		    	material = productt[2];
	   		        	
					} catch (Exception e) {
						
						if(StringUtil.isNotBlank(material)){
							material = productt[2];
				   		}else{
				   			
				   			material="";
				   		}
				   		    
					}
	   		 try {
		        	
	   			     color = productt[3];
	   		        	
					} catch (Exception e) {
						
						if(StringUtil.isNotBlank(color)){
							color = productt[3];
				   		}else{
				   			
				   			color="";
				   		}
				   		    
					}
	   		 
	   		 
	   		 try {
		        	
		        	size = productt[4];
		        	
				} catch (Exception e) {
					
					if(StringUtil.isNotBlank(size)){
			   			size = productt[4];
			   		}else{
			   			
			   			size="";
			   		}
			   		    
				}
	   		 Product oldProduct = productDao.getproduct(idProduct);			 
			 //当型号、材质、颜色、尺寸、品牌之中任意一个有变化，将product中的goods_id,instace_id,gmt_export设置为null
	   		Product product = new Product();
	   		    product.setIdProduct(idProduct);
				if(!oldProduct.getType().equals(type) || !oldProduct.getMaterial().equals(material) || !oldProduct.getColor().equals(color) || !oldProduct.getSize().equals(size)){				
					 productDao.updateProductGoddsIdIsNull(product);
				}

	   		  product.setType(type);
	   		  product.setMaterial(material);
	   		  product.setColor(color);
	   		  product.setSize(size);
	   		int i = productService.updateProductsByIdList(product);
	   		j=j+i;   		
	   	}
		}

				return j+"";
		
	}
	
	
	@RequestMapping("/toGetTransferList")
	public String toTransferList(Model model,AdminAgent adminAgent,HttpServletRequest request)
	{
		model.addAttribute("site", adminAgent.getSiteId());
		return "/product/changeDetailed";
	}
	
	@RequestMapping("/getTransferList")
	public @ResponseBody Object getTransferList(Model model,AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);//request请求转map
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		return productService.getTransferList(searchMap);
	}
	
	//跳转调货产品信息 
	@RequestMapping("/toGetDiaoHuoList")
	public String toGetDiaohuoList(Model model,AdminAgent adminAgent,HttpServletRequest request)
	{
		model.addAttribute("site", adminAgent.getSiteId());
		model.addAttribute("id", request.getParameter("id"));
		return "/product/diaoHuoList";
	}
	//查找调货产品信息
	@RequestMapping("/getDiaoHuoProductList")
	public @ResponseBody Object getDiaoHuoProductList(AdminAgent adminAgent,HttpServletRequest request)
	{	
		
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);//request请求转ma
	
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		return productService.getDiaoHuoProductList(searchMap);
	}
	
	
	
	
	/*
	//特别导出 ,导出安全类别、执行标准、商品名称、材质描述、颜色描述、产地
	@RequestMapping("/exportProductSecurityTC")
	@AdminAccess
	public void exportProductSecurityTC(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);	
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true); //去除空格
		String[] titleArr = new String[]{"sku","安全技术类别","执行标准","商品名称","材质描述","颜色描述","产地"};
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		MiniUiGrid miniuiGrid = productService.getProductList(searchMap);
		MiniUiGrid miniuiGrid =  productService.getSuoKuProductList(searchMap);
		int totalCnt = miniuiGrid.getTotal();
System.out.println(totalCnt+"_________>totalCnt");
		List<Product> productList = (List<Product>)miniuiGrid.getData();//获取需要导出的数据
		
		//将查处的数据，用Map集合过滤，sku相同的只保留一条数据(Map规则，当键相同时，后面的值覆盖前面的值，键不变)
		Map<String,Object> so = new HashMap<String, Object>();
		for(int  i = 0; i < productList.size(); i++){
			so.put(productList.get(i).getSku(), productList.get(i));			
		}
		
System.out.println(productList.size()+"_________>proList");	
        //定义一个list集合，将Map集合转为List集合
		List<Product> proList = new ArrayList<Product>();
		//取得键的集合
		Set<String> s = so.keySet();  
		Iterator<String> it = s.iterator();  
		 while(it.hasNext()){  
	            String key = it.next();  
	            Product value = (Product) so.get(key);  
	            proList.add(value);
	        }  
System.out.println(proList.size()+"_________>proList");	
		 
		String[][] resultArr = new String[proList.size()+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1];   //第一行为title  不需要在设置
			Product p = proList.get(i);
			
			rowArr[0] = p.getSku();         //sku			
			rowArr[1] = p.getSecurityTC();   //安全技术类别
			rowArr[2] = p.getImplementationS();  //执行标准
			rowArr[3] = p.getName();       //商品名称
			rowArr[4] = p.getMaterialdes();   //材质描述
			rowArr[5] = p.getColordes();      //颜色描述
			rowArr[6] = p.getOrigin();       //产地
		
		}
		  //导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=suoku_product_"+System.currentTimeMillis()+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

	}
	*/
	@RequestMapping("/exportProductyuliu")
	@AdminAccess
	public void exportProductyuliu(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		/*MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);*/
		String[] titleArr = new String[]{"sku","渠道","当前库存","平台订单数","更新到平台数","型号","材质","颜色","尚品订单数","小红书订单数","Ofashion订单数","微盟订单数","银泰订单数","拼多多订单数","珍品订单数","考拉订单数","寺库订单数","higo订单数","tmall订单数","1号店订单数","魅力惠订单数","天猫奢品订单数","新寺库订单数","OFashionB2B订单数","平台最后下单时间"};
		
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		String sku = searchMap.get("sku");
		String order_stock = searchMap.get("order_stock");
		if(StringUtil.isBlank(sku)){
			searchMap.remove("sku");
			
		}
		if(StringUtil.isBlank(order_stock)){
			searchMap.remove("order_stock");	
		}
		
		int cnt = platformStockUpdateDao.searchStockUpdateCnt(searchMap);  //{order_stock=, sku=}
		if(cnt > 30000)
		{
			return;
		}
		
		/*searchMap.put("userName", adminAgent.getUsername());*/
		
		
		MiniUiGrid miniuiGrid = stockUpdateService.searchStockUpdate(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<StockUpdate> productList = (List<StockUpdate>)miniuiGrid.getData();//获取需要导出的数据
 
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1]; //第一行为title  不需要在设置
			StockUpdate su = productList.get(i);
			rowArr[0] = su.getSku(); //sku
			rowArr[1] = su.getType();      //渠道 hk/sh
			rowArr[2] = su.getNowStockNum()+""; //当前库存
			rowArr[3] = su.getOrderStockNum()+"";//平台订单数
			rowArr[4] = su.getLastUpdateStockNum()+"";	//更新到平台数
//			rowArr[4] = (su.getNowStockNum()-su.getOrderStockNum())+"";	//更新到平台数于頁面相同
			rowArr[5] = su.getpType();//型号
			rowArr[6] = su.getMaterial();  //"材质"
			rowArr[7] = su.getColor(); //,"颜色",
			rowArr[8] = su.getShangpinOrderStock()+"";//尚品订单数
			rowArr[9] = su.getXhsOrderStock()+"";//小红书订单数
			rowArr[10] = su.getOfashionOrderStock()+"";//Ofashion订单数
			rowArr[11] = su.getWeiMobOrderStock()+"";//"微盟订单数"
			rowArr[12] = su.getYinTaiOrderStock()+"";//"银泰订单数"
			rowArr[13] = su.getPddOrderStock()+"";//,"拼多多订单数",
			rowArr[14] = su.getZhenpinOrderStock()+"";//"珍品订单数",
			rowArr[15] = su.getKaolaOrderStock()+""; //"考拉订单数",
			rowArr[16] = su.getSikuOrderStock()+"";//"寺库订单数",
			rowArr[17] = su.getHigoOrderStock()+"";//"higo订单数",
			rowArr[18] = su.getTmallOrderStock()+"";//"tmall订单数",
			rowArr[19] = su.getYhdOrderStock()+"";//"1号店订单数",
			rowArr[20] = su.getMlhOrderStock()+"";//"魅力惠订单数",
			rowArr[21] = su.getShepinOrderStock()+"";//"天猫奢品订单数",
			rowArr[22] = su.getSikunewOrderStock()+"";//"新寺库订单数",
			rowArr[23] = su.getOfashionMcOrderStock()+"";//"OFashionB2B订单数",
			rowArr[24] = su.getLastOrderTime();//"平台最后下单时间"
	
			}
		 //导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close(); 
			
		}
	    

    /**
     * 
     * @Description: 商品销售信息
     * @author 
     * @date 2018-8-1
     */
 /*   @RequestMapping("/goodsSaleInfo")
	public String  getGoodsSaleInfo(@RequestParam("gid") String goodsId,Model model,
			HttpServletRequest request){
    	model.addAttribute("id", goodsId);
		return "/goods/goodsSaleInfoList";
	}*/
	@RequestMapping("/goodsSaleInfo")
	public String  getGoodsSaleInfo(Model model,
			HttpServletRequest request,HttpServletResponse respons){
		model.addAttribute("id", request.getParameter("id"));
		return "/goods/goodsSaleInfoList";
	}	
    @RequestMapping("/goodsSaleInfoList")
	public @ResponseBody Object 
	getGoodsSaleInfoList(AdminAgent adminAgent,HttpServletRequest request){
    	Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		return productService.getGoodsSaleInfoList(searchMap);
	}
    
    //三级联动方法一，缺点是无法将联动后的值传给后台
//    private void seriesInit(Model model) {
//		Series ser = new Series();
//		ser.setParentIdSeries(-1);
//		List<Series> oneSeries = brandService.getSeriesBySeries(ser);		
//    	model.addAttribute("oneSeries", oneSeries);
//    }
//	
//	
//	
//	//@RequestMapping(value = "/getTwoSeries")
//	@ResponseBody
//	public Series[] getRightCitys(HttpServletRequest request) {
//		// 初始化城市资
//		int idSeries = Integer.parseInt(request.getParameter("idSeries"));
//		Series series = new Series();
//		series.setParentIdSeries(idSeries);
//		Series[] serArray = null;
//		List<Series> twoSeries = brandService.getSeriesBySeries(series);
//		int length = twoSeries.size();
//		serArray = new Series[length];
//		for (int i = 0; i < length; i++) {
//			serArray[i] = (Series) twoSeries.get(i);
//		}
//	
//		return serArray;
//	}
//	
//	@RequestMapping(value = "/getThreeSeries")
//	@ResponseBody
//	public Series[] getThreeSeries(HttpServletRequest request) {
//		int idSeries = Integer.parseInt(request.getParameter("idSeries"));
//		Series series = new Series();
//		series.setParentIdSeries(idSeries);
//		Series[] serArray = null;
//		List<Series> threeSeries = brandService.getSeriesBySeries(series);
//		int length = threeSeries.size();
//		serArray = new Series[length];
//		for (int i = 0; i < length; i++) {
//			serArray[i] = (Series) threeSeries.get(i);
//		}
//	
//		return serArray;
//	}
}