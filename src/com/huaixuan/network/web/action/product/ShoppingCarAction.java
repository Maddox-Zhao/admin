
package com.huaixuan.network.web.action.product;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.order.ProductShoppingCar;
import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.domain.product.Product;

import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.Series;



import com.huaixuan.network.biz.domain.product.Transfer;

import com.huaixuan.network.biz.service.base.MiniuiBaseService;
import com.huaixuan.network.biz.service.order.ProductShoppingCarService;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;




/**
 * @author Mr_Yang   2015-11-27 下午02:21:09
 * 购物车
 **/

@Controller
@RequestMapping("/shoppingCar")
public class ShoppingCarAction
{
	private static final String cookieKey =  "idProducts";
	
	protected Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductShoppingCarService productShoppingCarService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private MiniuiBaseService miniuiBaseService;
	
	@Autowired
	private ProductDao productDao;
	
	
	@RequestMapping("/add2ShoppingCar")
	public @ResponseBody Object  addProductToCookies(HttpServletRequest request,HttpServletResponse respons,
			AdminAgent adminAgent)
	{
		
		 String idProducts = request.getParameter(cookieKey); //1652015312136,1652015312137,1652015312138
		 //新添加的产品
		 List<String> newiIdProducts = getProductIds(idProducts);
		 List<Product> existsProduct = productService.showShoppingCar(adminAgent.getUsername());
		 Map<String,String> productStatusMap = new HashMap<String, String>();
		 for(Product p : existsProduct)
		 {
			 productStatusMap.put(p.getStatus(), p.getStatus());   //map的键重复，后面的值会覆盖前面的值，这个map里只有一对，键值对
		 }
		 
		 if(productStatusMap.size() > 1)    //productStatusMap.size()=1,productStatusMap中只有{可售：可售}一个键值对
		 {
			 return "exists_status_error"; //购物车产品只能有一个状态
		 }
		 
		 for(String idProduct : newiIdProducts)
		 {
			 Product product = productService.getproduct(idProduct);
			 if(null == productStatusMap.get(product.getStatus()) && productStatusMap.size() == 0)
			 {
				 productStatusMap.put(product.getStatus(), product.getStatus());
			 }
			 if(!product.getStatus().equals(productStatusMap.get(product.getStatus())))
			 {
				 return "only_one_status"; //加入购物车的产品状态必须一致
			 }
			 ProductShoppingCar productShoppingCar = new ProductShoppingCar();
			 productShoppingCar.setIdProduct(idProduct);
			 productShoppingCar.setUserName(adminAgent.getUsername());
			 productShoppingCarService.add2ShoppingCar(productShoppingCar);
		 }
		 
		
		return "ok";
	}
//根据sku自动加入购物车
	@RequestMapping("/addShoppingCarTwo")
	public @ResponseBody Object addShoppingCarTwo(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent){
		
		 Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
		 String status = searchmap.get("status");
		   if(StringUtil.isNotBlank(status)){
			   if(status.equals("1")){
				   status="可售";
			   }else if(status.equals("2")){
				   status="运输在途";
			   }else if(status.equals("3")){
				   status="预订";
			   }else if(status.equals("4")){
				   status="已售";
			   }else if(status.equals("5")){
				   status="寄卖已售未结算";
			   }else if(status.equals("6")){
				   status="准入库";
			   }else if(status.equals("8")){
				  status = "锁库";
			   }
			   
		   }else if(status.equals("")){
			   return "unchecked_status_error"; //请先选中加入购物车的状态
		   }
		   String carStatus = "";
		 List<Product> prots = productService.selectCarProdcutStatus(adminAgent.getUsername());
		   
		   for(Product po:prots){
			   
			   carStatus=po.getStatus();
		   }
		   //页面传过来的商品状态(status)和购物车中商品的状态(carStatus)
		   if(!status.equals(carStatus) && carStatus!=null && carStatus.length()>0){
			   return "only_one_status"; //购物车产品只能有一个状态;
		   }
		   
		   //查询符合sku和传过来的状态的所有产品的idProduct
		   searchmap.put("status",status);
		   
		   if(StringUtil.isNotBlank(searchmap.get("sku"))){
		   List<Product> products = productService.selectProductAllBySku(searchmap);
		   if(products!=null && products.size()>0){
		     for(Product idp:products){
		    	 
		    	String idProduct = idp.getIdProduct();
		    	
		    	 ProductShoppingCar productShoppingCar = new ProductShoppingCar();
		    	 productShoppingCar.setIdProduct(idProduct);
		    	 productShoppingCar.setUserName(adminAgent.getUsername());
		    	 productShoppingCarService.add2ShoppingCar(productShoppingCar);
		     }
		   }else{
			   
			   return "no_status_error";   //这个idProduct没有这个状态
		   }
		   }else{
			   return "no_sku_error"; //请先填写要加入购物车产品的sku
		   }
		return "ok";
	}
	
	
	
	
	
	@RequestMapping("/updateShoppingCarSalePrice")
	public @ResponseBody Object  updateShoppingCarSalePrice(HttpServletRequest request,HttpServletResponse respons,
			AdminAgent adminAgent)
	{
		String idProductAndSalePrice = request.getParameter("idProductAndSalePrice"); //1652015312136:1655.5;1652015312137:3500
		productShoppingCarService.updateShoppingCarSalePrice(idProductAndSalePrice, adminAgent.getUsername());
		return "ok";
	}
	
	@RequestMapping("/showShoppingCar")
	public @ResponseBody List<Product> showShoppingCar(HttpServletRequest request,HttpServletResponse respons,
			AdminAgent adminAgent)
	{ 
		Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
		searchmap.put("userName", adminAgent.getUsername());
	
		return productService.showShoppingCarMap(searchmap);
	}
	
	
	@RequestMapping("/removeAllProduct")
	public @ResponseBody Object  removeAllProduct(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		productShoppingCarService.removeAllShopingCar(adminAgent.getUsername());
		return "ok";
	}
	
	
	@RequestMapping("/removeProduct")
	public @ResponseBody Object  removeProduct(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		String idProducts = request.getParameter(cookieKey);
		
		
		List<String> removedList = getProductIds(idProducts);
		
		for(String idProduct : removedList)
		{
			ProductShoppingCar productShoppingCar = new ProductShoppingCar();
			productShoppingCar.setUserName(adminAgent.getUsername());
			productShoppingCar.setIdProduct(idProduct);
			productShoppingCarService.removeShoppingCar(productShoppingCar);
		}
		
		return "ok";
	}
	
	
	
	@RequestMapping("/showShoppingCarCount")
	public @ResponseBody Object  showShoppingCarCount(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
	
		 ProductShoppingCar productShoppingCar = new ProductShoppingCar();
		 productShoppingCar.setUserName(adminAgent.getUsername());
		 return  productShoppingCarService.getShoppingCarCnt(productShoppingCar);
	}
	
	@RequestMapping("/toShowShoppingCar")
	public String  toShowShoppingCar(HttpServletRequest request,HttpServletResponse respons)
	{
		return "/product/shoppingCar";
	}
	
	
	@RequestMapping("/toSuoKu")
	public String  toSuoKu(HttpServletRequest request,HttpServletResponse respons)
	{
		return "/product/suoku";
	}
	
	@RequestMapping("/toShowSite")
	public String  toShowSite(AdminAgent adminAgent,HttpServletRequest request,HttpServletResponse respons,Model model)
	{
		model.addAttribute("idLocation", adminAgent.getSiteId());
		return "/product/site";
	}
	
	/**
	 * 批量调货出库
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping("/diaohuoChuku")
	public @ResponseBody Object diaohuoChuku(AdminAgent adminAgent,HttpServletRequest request,HttpServletResponse respons)
	{		
		Map<String,String> map =MiniUiUtil.getParameterMap(request);
		
		String adminidSite = adminAgent.getSiteId()+"";
		String idSite = map.get("idSite");
		String requestStr = map.get("idProductAndSite");
		String idCustomer = map.get("customerShow");
		String remark = map.get("remark");
		if(StringUtil.isNotBlank(requestStr))
		{
			String[] arr = requestStr.split(";");
			//循环判断批量商品的原站点是否和目的地站点相同，相同站点不允许调货。
			for(String s : arr){
				String[] idProductAndSiteArr = s.split(":");
				String beforelocation = idProductAndSiteArr[2];//商品原站点名
				List<MiniUiBase> alllist = miniuiBaseService.getSiteByName(beforelocation);
				int iDsite = alllist.get(0).getId();//商品原站点ID
				if(iDsite == Integer.parseInt(idSite)){
					return "no";
				}
			}
		}
		
		if(StringUtil.isNotBlank(requestStr))
		{
			String[] arr = requestStr.split(";");
			for(String s : arr)
			{
				
				String[] idProductAndSiteArr = s.split(":");
				String idProduct = idProductAndSiteArr[0];
				
				String idLocation = idProductAndSiteArr[1];
				
				Product p = new Product();
				p.setIdProduct(idProduct);
				p.setIdLocation(idLocation);
				p.setIdCustomer(idCustomer);
				p.setIdLastOperator(adminAgent.getUsername());
				
				productService.transferChuku(p);//调货出库
				
				
			}
		}	
		
			//调货出库记录		
		if(StringUtil.isNotBlank(requestStr))
		{
			String[] arr = requestStr.split(";");
			
			
				String[] idProductAndSiteArr = arr[0].split(":");
				
				String idProduct = idProductAndSiteArr[0];
				Product products = productService.getproduct(idProduct);
				
				String idStatus =  products.getStatusID();//
				
				String idLocation = idProductAndSiteArr[1];//调货目的地
				
				String beforelocation = idProductAndSiteArr[2];//商品原站点名
				
				List<MiniUiBase> alllist = miniuiBaseService.getSiteByName(beforelocation);
				int iDsite = alllist.get(0).getId();//商品原站点ID
			
				Transfer transfer = new Transfer();
				transfer.setCreateUser(adminAgent.getUsername());//操作人
				transfer.setAfterLocation(Integer.parseInt(idLocation));//调货目的地
				transfer.setBeforelocation(iDsite);//原站点
				transfer.setIdStatus(Integer.parseInt(idStatus));
				transfer.setIdCustomer(idCustomer);
				transfer.setRemark(remark);
			
				productService.transferDiaoHuo(transfer);//调货出库
			
//				String[] arr = requestStr.split(":");
//				
//				String idlocation = arr[1];
//				
//				String[] location = idlocation.split(";");
//				String idLocation = location[0];
//				
		}
		
		
		
		
		//调货出库产品
		if(StringUtil.isNotBlank(requestStr))
		{
			String[] arrs = requestStr.split(";");
			for(String s : arrs)
			{
				String[] idProductAndSiteArr = s.split(":");
				String idProduct = idProductAndSiteArr[0];
				
				String idLocations = idProductAndSiteArr[1];
				
				String curSiteName = idProductAndSiteArr[2];
			
				GoodsInformation goods = new GoodsInformation();
				
							
				//添加对应的主外键对应关系，transfer的最大id
				int maxId=productService.selectTransferMaxId();
				goods.setgId(maxId);
				//产品的idproduct	
				goods.setIdproduct(idProduct);
				//添加产品调动前的站点
				goods.setBeforeLocation(curSiteName);
				goods.setIdStatus("2");
				productService.GoodSInformationDiaoHuo(goods);//调货产品详细
			}
		}
		
		
		productShoppingCarService.removeAllShopingCar(adminAgent.getUsername());//清空购物车
		
		return "ok";
		
		
	}
	
	/**
	 * 批量调货入库
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping("/diaohuoRuku")
	public @ResponseBody Object diaohuoRuku(AdminAgent adminAgent,HttpServletRequest request,HttpServletResponse respons)
	{
		Map<String,String> map =MiniUiUtil.getParameterMap(request);
		String gidqw = "";
		String requestStr = map.get("idProducts");
		String gids = map.get("gidd");
		
		if(StringUtil.isNotBlank(gids)){
				String[] arrss = gids.split(",");
				gidqw = arrss[0];
		}
		
		if(StringUtil.isNotBlank(requestStr))
		{
			String[] arr = requestStr.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for(String s : arr)
			{
			 
				 Product p = new Product();
				 p.setIdProduct(s);
				 p.setIdLastOperator(adminAgent.getUsername());
				 productService.transferRuku(p);//调货入库
				 
				 GoodsInformation goods = new GoodsInformation();
				 goods.setIdStatus(1+"");      //给GoodsInformation表改变状态为可售
				 goods.setIdproduct(s);        //条件idproduct和条件gidqw来确定是表中的哪条记录
				 if(StringUtil.isNotBlank(gids)){
					 goods.setgId(Integer.parseInt(gidqw)); 
					 list.add(Integer.parseInt(gidqw));
				 }else{
					 //没有gid,通过idproduct,求出gid
					 Map<String,String> searchMapg = new HashMap<String, String>();
					 searchMapg.put("idproduct", s);
					 MiniUiGrid miniuiGrid = productService.getDiaoHuoProductList(searchMapg);
					 List<GoodsInformation>	orderlsit =  (List<GoodsInformation>) miniuiGrid.getData();
					if(orderlsit != null && orderlsit.size() > 0){
					int sta = orderlsit.get(orderlsit.size()-1).getgId();//取list集合中的最后一个gid//hx_goodsinformation表中GID (关联调货明细表)
					goods.setgId(sta); 
					list.add(sta);	
					 }

				 }
		 
				 productDao.updateGoodSInformation(goods);
			}
				List<Integer> newList = new ArrayList<Integer>();
				 for(Integer gId:list){
					 if(!newList.contains(gId)){
						 newList.add(gId);
					 }
				 }
				
				Map<String,String> searchMap = new HashMap<String, String>();
				Map<String,String> searchMapto = new HashMap<String, String>();
				Map<String,String> searchMaptoo = new HashMap<String, String>();
				boolean flag = false;
				
				boolean flg =false;
				
					for(Integer sta:newList){
						
						   //获取当前gid下所有商品在GoodsInformation表中的状态
							searchMapto.put("gid", sta+"");
							MiniUiGrid miniuiGridto = productService.getDiaoHuoProductList(searchMapto);				
							List<GoodsInformation> goodsInfo = (List<GoodsInformation>)miniuiGridto.getData(); 

							//根据hx_goodsinformation表中的GID查询hx_transfer表中GID的状态
							searchMaptoo.put("id", sta+"");
							MiniUiGrid miniuiGridtoo = productService.getTransferList(searchMaptoo);
							List<Transfer> transFer =  (List<Transfer>)miniuiGridtoo.getData();
							String statu = transFer.get(0).getStatu();//调货明细中的状态
							String st= "";
							if(statu.equals("2")){
								st = "运输在途";
							}else{
								st = "可售";
							}	
							Map<String,String> maps = new HashMap<String, String>();
							
						if(flag == false){
							for (int i = 0; i < goodsInfo.size(); i++) {
								//获取gid=30的所有产品状态
								String stau = goodsInfo.get(i).getStatus();//
								//调货明细(transFer)中的状态st和hx_goodsinformation表中的状态stau,如果相同，则返回页面
								if(!st.equals(stau)){
									flag = true;
								}else{
									maps.put("是的", "yes");
									break;
								}
							 }
							}
						if(!"yes".equalsIgnoreCase(maps.get("是的"))){
							if(flag == true){
								Transfer tran = new Transfer();
								tran.setId(sta);
								productService.updatebyid(tran);
							}
						}
				}
			}else{
				return "ok";
			}
//		productShoppingCarService.removeAllShopingCar(adminAgent.getUsername());//清空购物车
		return "ok";
	}
	
	
	
	//购物车结算
	@RequestMapping("/toSettleAccount")
	public String  toSettleAccount(AdminAgent adminAgent,HttpServletRequest request,Model model)
	{
		 String currencyId = "1"; //当前结算币种
		 if(MiniUiUtil.hkAllSite.contains(adminAgent.getSiteId()+""))
		 {
			 currencyId = "3";
		 }
		 model.addAttribute("currencyId",currencyId);
		 return "/product/settleAccount";
	}
	
	

	
	
	/**
	 * 购物车结算
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping("/settleAccount")
	public @ResponseBody Object  shoppingCarSettleAccount(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		requestMap.put("operator", adminAgent.getUsername()); 
		if("1002011".equals(requestMap.get("operator"))){
			if(StringUtil.isBlank(requestMap.get("operator2"))){
				return "needName";
			}
		}
		boolean success = productService.shoppingCar2SettleAccount(requestMap);
		if(true == success)//成功  清空购物车
		{
			productShoppingCarService.removeAllShopingCar(adminAgent.getUsername());//清空购物车
	        return "ok";
		}
		else
		{
			return "error";
		}
		
	}
	
	
	/**
	 * 批量锁库
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping("/suoku")
	public @ResponseBody Object  shoppingCarSuoKu(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		requestMap.put("operator", adminAgent.getUsername()); 
		
		List<String> idProductList = getProductIds(requestMap.get("idProducts"));
		
		boolean success = productService.batchSuKu(requestMap,idProductList);
		
		if(true == success)//成功  清空购物车
		{
			productShoppingCarService.removeAllShopingCar(adminAgent.getUsername());//清空购物车
	        return "ok";
		}
		else
		{
			return "error";
		}
		
	}
	
	
	/**
	 * 批量解锁
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping("/jiesuo")
	public @ResponseBody Object  shoppingCarJieSuo(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		requestMap.put("operator", adminAgent.getUsername()); 
		List<String> idProductList = getProductIds(requestMap.get("idProducts"));
		
	    productService.batchJieSuo(requestMap,idProductList);
	    return "ok";

	}
	
	

	
	
	//获取cookies中存在的产品
	private List<String> getExistsProducts(HttpServletRequest request,AdminAgent adminAgent)
	{
		List<String> list  = new ArrayList<String>();
		Cookie[] cookie = request.getCookies();  
        for (int i = 0; cookie != null && i < cookie.length; i++) 
        {  
        	if((cookieKey+adminAgent.getUsername()).equals(cookie[i].getName()))
        	{
        		String idProductStr = cookie[i].getValue();
        		list = getProductIds(idProductStr);
        	}
        } 
        return list;
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
	
	/**
	 * 批量导出商品
	 * @param request
	 * @param respons
	 */
	@RequestMapping("/exportProduct")
	@AdminAccess
	public void exportProduct(HttpServletResponse response,AdminAgent adminAgent) throws Exception{
		String[] titleArr = new String[]{"idProduct","品牌","品类","型号","材质","颜色","size","状态","尚美价","活动价","售价","位置"};
		List<Product> productList = productService.showShoppingCar(adminAgent.getUsername());
		int totalCnt = productList.size();
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; 
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++){
			String[] rowArr = resultArr[i+1];
			Product p = productList.get(i);
			rowArr[0] = p.getIdProduct();
			rowArr[1] = p.getBrandName();
			rowArr[2] = p.getSeriesName();
			rowArr[3] = p.getType();
			rowArr[4] = p.getMaterial();
			rowArr[5] = p.getColor();
			rowArr[6] = p.getSize();
			rowArr[7] = p.getStatus();
			rowArr[8] = p.getSmPrice()+"";
			rowArr[9] = p.getActivePrice()+"";
			rowArr[10] = p.getSalePrice()+"";
			rowArr[11] = p.getCurSiteName();
		}
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
	//修改商品名
	
	@RequestMapping("/updateBrandName")
	
	public @ResponseBody Object updateBrandName(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent){
		
		String idProductAndBrandName = request.getParameter("idProductAndBrandName");
		if(idProductAndBrandName==null) return "";
		String[] brandNames = idProductAndBrandName.split(":");
		
		String idProduct = brandNames[0];
		String brandName = brandNames[1];
		Brand brand = new Brand();
		  brand.setBrandName(brandName);
		 Brand brands = brandService.selectBrandByName(brand);
		 if(brands==null){
			 return "no";
		 }
		 String brandId = String.valueOf(brands.getId()); //或String brandId =brands.getId()+"";
		
		 Product oldProduct = productDao.getproduct(idProduct);	 //查询出为修改brand之前的product属性
		 Product product = new Product();
		 product.setIdProduct(idProduct);
		//当型号、材质、颜色、尺寸、品牌之中任意一个有变化，将product中的goods_id,instace_id,gmt_export设置为null
		 if(!oldProduct.getBrandID().equals(brandId)){				
			 productDao.updateProductGoddsIdIsNull(product); 
		}
		 
		 product.setBrandID(brandId);		 
		 productService.updateProductById(product);
		   return "ok";
		
	}
	
@RequestMapping("/updateSeriesName")
	
	public @ResponseBody Object updateSeriesName(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent){
		
		String idProductAndSeriesName = request.getParameter("idProductAndSeriesName");
		if(idProductAndSeriesName==null) return "";
		String[] seriesNames = idProductAndSeriesName.split(":");
		
		String idProduct = seriesNames[0];
		String seriesName = seriesNames[1];
		Series series = new Series();
		series.setName(seriesName);
		Series serieses = brandService.selectSeriesByName(series);
		 String seriesId = String.valueOf(serieses.getId()); //或String brandId =brands.getId()+"";
		 Product product = new Product();
		 product.setSeriesId(seriesId);
		 product.setIdProduct(idProduct);
		 productService.updateProductById(product);
		   return "ok";
		
	}

public static final  String pic_root = "http://140.207.52.210:88/upload/";
public static final  String basePath ="d:\\upic"; //应用所在服务器的d盘
@RequestMapping(value = "/timetask/SecurityHttpByFile")
public @ResponseBody Object SecurityHttpByFile(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam(value="img",required=false) MultipartFile file)  
{	
	  
	Map<String,String> map = MiniUiUtil.getParameterMap(request);//request请求转map
	    Map<String,String> maps = new HashMap<String, String>();
		maps.put("result", "non");			
	try
	{ 
		String pic = request.getParameter("pic");
		int i = pic.lastIndexOf("/");
		int t = pic.lastIndexOf("?");
		//页面上得到的图片名
		String pictue = pic.substring(i+1, t);

//		String brandName = pictue.split("_")[0];
//		System.out.println(pictue);		
//		System.out.println(brandName);
//		String img =brandName+"_"+file.getOriginalFilename();	
//		String imgPath = basePath+"\\"+img;
		String imgPath = basePath+"\\"+pictue;
		File destFile = new File(imgPath);
		//从客户端将文件读入到指定字节数组中
		InputStream is = file.getInputStream();
		byte[] data = new byte[(int)file.getSize()];
		is.read(data);
		//再将此字节数组写入到指定文件中
		FileUtils.writeByteArrayToFile(destFile, data);
//		System.out.println("完成");		
	
		//上传的图片名要和产品的产品、型号、材质、颜色一致
//		if(!pictue.equals(img)){
//			maps.put("result", "no");
//			return maps;
//		}
		String result=uploadImg(imgPath);		
		if(result.equals("upload success")){
			result=uploadImg(imgPath);
			if(result.equals("upload success")){
			maps.put("imgUrl", pic_root+pictue);
			maps.put("result", "success");
			return maps;
			}
		}else if(result.equals("upload error")){
			maps.put("result", "error");
			return maps;
		}else if(result.equals("error")){
			maps.put("result", "error");
			return maps;
		}
//		 System.out.println(result);
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		log.error(e.getMessage(), e);
	}
	return maps;
	
	
	
}
public static final String API="http://140.207.52.210:88/upload/test.php";
 private String uploadImg(String imgUrl) {
	 OutputStream os=null;
	 InputStream input=null;
	 String reString ="";
	 try {
		 File imgFile=new File(imgUrl);
	        URL url=new URL(API);
	        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(10000);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----123456789");
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        
	        os=new DataOutputStream(conn.getOutputStream());
	        StringBuilder body=new StringBuilder();
	        body.append("------123456789\r\n");
	        body.append("Content-Disposition: form-data; name='img'; filename='"+imgFile.getName()+"'\r\n");
	        body.append("Content-Type: image/jpeg\r\n\r\n");
	        os.write(body.toString().getBytes());
	        
	        InputStream is=new FileInputStream(imgFile);
	        byte[] b=new byte[1024];
	        int len=0;
	        while((len=is.read(b))!=-1){
	            os.write(b,0,len); //将指定 byte数组中从偏移量 off 开始的 len个字节写入此输出流。write(byte[] b, int off, int len)
	        }
	        String end="\r\n------123456789--";
	        os.write(end.getBytes());
	        os.flush();
	        //输出返回结果
	         input=conn.getInputStream();
	        byte[] res=new byte[1024];
	        int resLen=input.read(res);
	        reString = new String(res,0,resLen);
	     
	} catch (Exception e) {
		e.printStackTrace();
		log.error(e.getMessage(), e);
		return "error";
	}finally{

		if (os != null)
		{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
		if (input != null)
		{
			try
			{
				input.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	
	}
	return reString;
        
    }
}
 
