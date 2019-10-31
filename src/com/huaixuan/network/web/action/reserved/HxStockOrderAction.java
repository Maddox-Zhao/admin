package com.huaixuan.network.web.action.reserved;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.reserved.HxStockOrderDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.product.GoodsInformation;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.service.platformstock.HxStockUpdateService;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.biz.service.reserved.HxStockOrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;
//import com.jd.open.api.sdk.domain.jzt_zw.FeaturedADJosService.ArrayList;

@Controller
@RequestMapping(value ="/HxStockOrder")
public class HxStockOrderAction extends BaseAction {
	@Autowired
     private HxStockOrderService hxStockOrderService;
	@Autowired
    private HxStockOrderDao hxStockOrderDao;
	
	@Autowired
	private ProductService productService;
	
	
	
     @RequestMapping("/HxStockOdderList")
     @AdminAccess
	public String HxStockOdder(Model model,HttpServletRequest request){
    	 
		return "/reservedOrder/hxStockOrderList";

     }
     @RequestMapping("/HxSearchStockOrder")
     public @ResponseBody Object HxSearchStockOrder(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent){
    	 
    	 Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
 		
 		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
 		
 		
		String type = searchmap.get("type");
		
		if(StringUtil.isNotBlank(type)){
		if(type.equals("1")){
			searchmap.put("type", "hk");
		}else if(type.equals("2")){
			
			searchmap.put("type", "sh");
		}
 		}
		return hxStockOrderService.searchStockOrder(searchmap);
    	 
     }
     
     @RequestMapping("/HxDetailsOdderList")
     @AdminAccess
	public String HxDetailsOdder(Model model,HttpServletRequest request){
    	
 		model.addAttribute("orderId", request.getParameter("orderId"));
		return "/reservedOrder/hxStockOrderListDetails";

     }
     @RequestMapping("/HxSearchDetalisOrder")
     public @ResponseBody Object HxDetalisStockOrder(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent){
    	
    	Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
 		
 		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
		return hxStockOrderService.searchDertailsOrder(searchmap);
    	 
     }
     
     
     
     
     
     //特别导出(魅力惠推送订单页面的导出)
     @RequestMapping("/exportStockOrder")
     public void exportStockOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	 
    	 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
    	 MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false,true,true);
    		String type = searchMap.get("type");
    		
    		if(StringUtil.isNotBlank(type)){
    		if(type.equals("1")){
    			searchMap.put("type", "hk");
    		}else if(type.equals("2")){
    			
    			searchMap.put("type", "sh");
    		}
     		}
    	 String[] titleArr = new String[]{"id","订单号","平台名称","销售状态","渠道","订单创建时间","下单时间"};
        searchMap.put("noStartRowAndEndRow","yes");
        
       MiniUiGrid miniuiGrid = hxStockOrderService.searchStockOrderList(searchMap);
       
       int totalCnt = miniuiGrid.getTotal();
       
     List<PlatFormOrderRecord> orderList = (List<PlatFormOrderRecord>)miniuiGrid.getData();  //获取需要导出的数据
     
     String[][] resultArr = new String[totalCnt+1][titleArr.length];//准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
        resultArr[0] = titleArr;  //标题那一行(第一行)
     
        for(int i=0; i<resultArr.length-1; i++){
        	
        	String[] rowArr = resultArr[i+1]; //准备一个数组，放入第二行的内容，因为第一行是标题
        	PlatFormOrderRecord o = orderList.get(i);
        	
        	rowArr[0]=o.getId()+"";
        	
        	rowArr[1]=o.getOrderId();
        	if("1".equals(o.getIdPlartform()+"")){
        		rowArr[2]="考拉";
        	}
        	else if("2".equals(o.getIdPlartform()+"")){
        		rowArr[2]="寺库";
        	}
        	else if("3".equals(o.getIdPlartform()+"")){
        		rowArr[2]="珍品";
        	}
        	else if("4".equals(o.getIdPlartform()+"")){
        		rowArr[2]="1号店";
        	}
        	else if("5".equals(o.getIdPlartform()+"")){
        		rowArr[2]="天猫";
        	}
        	else if("6".equals(o.getIdPlartform()+"")){
        		rowArr[2]="higo";
        	}
        	
        	else if("7".equals(o.getIdPlartform()+"")){
        		rowArr[2]="分期乐";
        	}
        	else if("8".equals(o.getIdPlartform()+"")){
        		rowArr[2]="拼多多";
        	}
        	else if("9".equals(o.getIdPlartform()+"")){
        		rowArr[2]="魅力惠";
        	}
        
        	if("1".equals(o.getIdStatus()+"")){
        		rowArr[3]="已售";
        	}else if("2".equals(o.getIdStatus()+"")){
        		rowArr[3]="取消";
        	}else if("3".equals(o.getIdStatus()+"")){
        		rowArr[3]="未确定";
        	}
        	rowArr[4]=o.getType();
        	rowArr[5]=o.getCreateDate();
            rowArr[6]=o.getOrderTime();
        	
        }
      //导出excel
      //  创建一个Excel文件
        HSSFWorkbook exportWb = new HSSFWorkbook();
     // 创建一个Excel的Sheet
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
     // 生成一个样式  
        HSSFCellStyle exportStyle = exportWb.createCellStyle(); 
      //水平居中
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
        response.setHeader ("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

        
     }
     
     
     //导出
     @RequestMapping("/exportStockOrderOne")
     public void exportStockOrderOne(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	 
    	 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
    	 MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false,true,true);
    	 String[] titleArr = new String[]{"订单号","平台名称","是否是天猫订单","状态","接收订单时间",
    			 "平台下单时间","收件人姓名","报关人姓名（魅力惠）","报关人身份证号（魅力惠）","收件人电话",
    			 "国家","省份","城市","地区","街道地址","邮政编码","订单总价","优惠总价","运费","魅力惠sku",
    			 "我们的SKU","商品数量","商品名称","商品尺寸","币种","供货价"};
    	 searchMap.put("noStartRowAndEndRow","yes");
        //
       MiniUiGrid miniuiGrid = hxStockOrderService.searchDertailsOrder(searchMap);
       
       int totalCnt = miniuiGrid.getTotal();
       
     List<PlatFormOrderDetails> orderList = (List<PlatFormOrderDetails>)miniuiGrid.getData();  //获取需要导出的数据
     
     String[][] resultArr = new String[totalCnt+1][titleArr.length];//准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
        resultArr[0] = titleArr;  //标题那一行(第一行)
     
        for(int i=0; i<resultArr.length-1; i++){
        	
        	String[] rowArr = resultArr[i+1]; //准备一个数组，放入第二行的内容，因为第一行是标题
        	PlatFormOrderDetails o = orderList.get(i);
        	
        	rowArr[0]=o.getIdorder();

        	if("1".equals(o.getIdPlartform()+"")){
        		rowArr[1]="考拉";
        	}
        	else if("2".equals(o.getIdPlartform()+"")){
        		rowArr[1]="寺库";
        	}
        	else if("3".equals(o.getIdPlartform()+"")){
        		rowArr[1]="珍品";
        	}
        	else if("4".equals(o.getIdPlartform()+"")){
        		rowArr[1]="1号店";
        	}
        	else if("5".equals(o.getIdPlartform()+"")){
        		rowArr[1]="天猫";
        	}
        	else if("6".equals(o.getIdPlartform()+"")){
        		rowArr[1]="higo";
        	}
        	
        	else if("7".equals(o.getIdPlartform()+"")){
        		rowArr[1]="分期乐";
        	}
        	else if("8".equals(o.getIdPlartform()+"")){
        		rowArr[1]="拼多多";
        	}
        	else if("9".equals(o.getIdPlartform()+"")){
        		rowArr[1]="魅力惠";
        	}
        	   //是否天猫订单
        	if("true".equals(o.getTmall())){
        		rowArr[2]="是";
        	}
        	else if("false".equals(o.getTmall())){
        		rowArr[2]="否";
        	}
        	rowArr[3]=o.getIdStatus()+"";
        	rowArr[4]=o.getCreateDate();
        	rowArr[5]=o.getPalcedTime();
        	rowArr[6]=o.getName();
        	rowArr[7]=o.getRealName();
        	rowArr[8]=o.getIdentityNumber();
        	rowArr[9]=o.getMobile();
        	rowArr[10]=o.getCountry();
        	rowArr[11]=o.getProvince();
        	rowArr[12]=o.getCity();
        	rowArr[13]=o.getDistrict();
        	rowArr[14]=o.getStreetAddress();
        	rowArr[15]=o.getZipCode();
        	rowArr[16]=o.getTotalPrice();
        	rowArr[17]=o.getDiscountPrice();
        	rowArr[18]=o.getFreight();
        	rowArr[19]=o.getSkuId();
        	rowArr[20]=o.getMerchantSkuId();
        	rowArr[21]=o.getQuantity();
        	rowArr[22]=o.getProductname();
        	rowArr[23]=o.getSize();
        	rowArr[24]=o.getCurrency();
        	rowArr[25]=o.getSupplyprice();
        	
        	
        	/*
        	rowArr[0] = o.getParentid(); //销售订单号
        	rowArr[1] = o.getIdorder();   //销售子单号
        	
        	   //是否天猫订单
        	if("true".equals(o.getTmall())){
        		rowArr[2]="是";
        	}
        	else if("false".equals(o.getTmall())){
        		rowArr[2]="否";
        	}
        	rowArr[3] = o.getSkuId();    //魅力惠SKU
        	rowArr[4] = o.getProductname(); //"商品名称"
        	rowArr[5] = o.getMerchantSkuId(); //我们的SKU
        	rowArr[6] = o.getQuantity();  //"商品数量"
        	rowArr[7] = o.getSize();      //商品尺寸
        	rowArr[8] = o.getPalcedTime();//销售订单下单日期
        	rowArr[9] = o.getProvince();//,"收货人省"
        	rowArr[10] = o.getCity(); //,"收货人市"
        	rowArr[11] = o.getDistrict(); //,"收货人区"
        	rowArr[12] =o.getZipCode();  //收货人邮编
        	rowArr[13] = o.getName(); //收货人姓名
        	rowArr[14] = o.getCreateDate(); //创建时间
        	rowArr[15] = o.getMobile(); //收货人电话
        	rowArr[16] = o.getStreetAddress(); //收货人地址
        	rowArr[17] = o.getIdentityNumber();//报关身份证号
        	rowArr[18] = o.getRealName(); //报关姓名
        	rowArr[19] = o.getTotalPrice();//订单总价
        	rowArr[20] = o.getSupplyprice();//供货价
        	*/
        	
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
        response.setHeader ("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

        
     }
     
     @RequestMapping("/selectHxDetailsOdderList")
     @AdminAccess
	public String selectDetailsOdder(Model model,HttpServletRequest request){
	
		return "/reservedOrder/hxStockDetailsOrderList";

     }
     @RequestMapping("/selectHxSearchDetalisOrder")
     public @ResponseBody Object selectHxDetalisStockOrder(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent){
    	
    	Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
 		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
		return hxStockOrderService.selectDertails(searchmap);
    	 
     }
     @RequestMapping("/selectcount")
     public @ResponseBody Object selectcount(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent){
    	
    	Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
 		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
 		int num = hxStockOrderDao.selectDerdetails(searchmap);
		return num;
    	 
     }
     
      //魅力惠所有订单详情导出
     @RequestMapping("/exportStockOrderDetail")
     public void exportStockOrderDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	 
    	 
    	 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
    	 MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false,true,true);
    	 String[] titleArr = new String[]{"订单号","平台名称","是否是天猫订单","本地创建时间",
    			 "平台下单时间","收件人姓名","报关人姓名（魅力惠）","报关人身份证号（魅力惠）","收件人电话",
    			 "国家","省份","城市","地区","街道地址","邮政编码","订单总价","优惠总价","运费","魅力惠sku",
    			 "我们的SKU","商品数量","商品名称","商品尺寸","币种","供货价"};
    	
    	 searchMap.put("noStartRowAndEndRow","yes");
       
       MiniUiGrid miniuiGrid = hxStockOrderService.selectDertails(searchMap);
       
       int totalCnt = miniuiGrid.getTotal();
       
     List<PlatFormOrderDetails> orderList = (List<PlatFormOrderDetails>)miniuiGrid.getData();  //获取需要导出的数据
     
     String[][] resultArr = new String[totalCnt+1][titleArr.length];//准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
        resultArr[0] = titleArr;  //标题那一行(第一行)
     
        for(int i=0; i<resultArr.length-1; i++){
        	
        	String[] rowArr = resultArr[i+1]; //准备一个数组，放入第二行的内容，因为第一行是标题
        	PlatFormOrderDetails o = orderList.get(i);
     
        	rowArr[0]=o.getIdorder();                //销售订单号

        	if("Mlh".equals(o.getPtype())){    //平台名称
        		rowArr[1]="魅力惠";
        	}else if("xhs".equals(o.getPtype())){
        		rowArr[1]="小红书";
        		
        	}else{
        		
        		rowArr[1]="null";
        		
        	}
        	                                    //是否天猫订单
        	if("true".equals(o.getTmall())){
        		rowArr[2]="是";
        	}
        	else if("false".equals(o.getTmall())){
        		rowArr[2]="否";
        	}else{
        		rowArr[2]="null";
        	}
        	  
        	rowArr[3]=o.getInsertTime();  //本地创建时间
        	rowArr[4]=o.getPalcedTime();  //平台下单时间
        	rowArr[5]=o.getName();       //收件人姓名
        	rowArr[6]=o.getRealName();    //报关人姓名（魅力惠）
        	rowArr[7]=o.getIdentityNumber(); //报关人身份证号码（魅力惠）
        	rowArr[8]=o.getMobile();      //收件人电话
        	rowArr[9]=o.getCountry();    //国家
        	rowArr[10]=o.getProvince();   //省份
        	rowArr[11]=o.getCity();       //城市
        	rowArr[12]=o.getDistrict();   //区
        	rowArr[13]=o.getStreetAddress(); //街道地址
        	rowArr[14]=o.getZipCode();      //邮政编码
        	rowArr[15]=o.getTotalPrice();   //订单总价
        	rowArr[16]=o.getDiscountPrice();  //优惠总价
        	rowArr[17]=o.getFreight();       //运费
        	rowArr[18]=o.getSkuId();        //平台SKU
        	rowArr[19]=o.getMerchantSkuId();  //我们的SKU
        	rowArr[20]=o.getQuantity();      //商品数量
        	rowArr[21]=o.getProductname();   //商品名称
        	rowArr[22]=o.getSize();         //商品尺寸
        	rowArr[23]=o.getCurrency();    //  币种
        	rowArr[24]=o.getSupplyprice();  //供货价
        	
        	
        	
    	
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
        response.setHeader ("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    

     }
     
     
     //只导出IDproduct
     @RequestMapping("/exportOnlyIdproduct")
     public void exportOnlyIdproduct(HttpServletRequest request,HttpServletResponse response)throws Exception{
//    	 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);  
    	 //String[] titleArr= new String[]{"id","idProduct","yuanzhandian","brandName","seriesName","type","material","color","size","status","curSiteId"};
    	 String[] titleArr= new String[]{"id","idProduct","原站点","品牌","品类","型号","材料","颜色","尺寸","状态","现站点"};
    	 String idProductsu =request.getParameter("idProducts");
    	 String idProducts = null;
    	 String[][] resultArr=null;
    if(StringUtil.isNotBlank(idProductsu)){
    	idProducts = java.net.URLDecoder.decode(idProductsu, "utf-8");//解决乱码问题
    }
    if(StringUtil.isNotBlank(idProducts)){    //导出勾选Idproduct 
    	
    	 String[] idProduct = idProducts.split(";");
    	 int totalCnt = idProduct.length; //得到的总数量,条数
    	  resultArr = new String[totalCnt+1][titleArr.length]; //准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
    	 resultArr[0] =titleArr;  //第一行是id Idproduct
    	 for(int i=0;i<resultArr.length-1;i++){
    			String[] rowArr = resultArr[i+1];
    			String[] id = idProduct[i].split(":");
    			/*rowArr[0] = id[0];
    			rowArr[1] = id[1];
    			rowArr[2] = id[2];
    			*/
    			for(int j=0;j<=titleArr.length-1;j++){
    				if(!id[j].equals(null)){
    					rowArr[j] = id[j];
    				}
    			}
    		}
    	 
    }else{
    	//不勾选全部导出或根据条件查询导出
    	Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
   	    MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false,true,true);
   	    searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
   	   MiniUiGrid miniuiGrid = productService.getDiaoHuoProductList(searchMap);
     	int totalCnt = miniuiGrid.getTotal();
     	List<GoodsInformation> goodsList = (List<GoodsInformation>)miniuiGrid.getData();  //获取需要导出的数据
     	   resultArr = new String[totalCnt+1][titleArr.length];//准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
          resultArr[0] = titleArr;  //标题那一行(第一行)
          for(int i=0; i<resultArr.length-1; i++){
          	
          	String[] rowArr = resultArr[i+1]; //准备一个数组，放入第二行的内容，因为第一行是标题 status+":"+ curSiteId
          	GoodsInformation o = goodsList.get(i);
       
          	rowArr[0]=o.getgId()+"";               
  			rowArr[1] = o.getIdproduct()+"";
  			rowArr[2] = o.getBeforeLocation()+"";
  			rowArr[3] = o.getSize()+"";
  			rowArr[4] = o.getSeriesName()+"";
  			rowArr[5] = o.getType()+"";
  			rowArr[6] = o.getMaterial()+"";
  			rowArr[7] = o.getColor()+"";
  			rowArr[8] = o.getSize()+"";
  			rowArr[9] = o.getStatus()+"";
  			rowArr[10] = o.getCurSiteId();
  		}
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
            response.setHeader ("Content-disposition", "attachment;filename=idProduct_"+sdf1.format(new Date())+".xls");
            OutputStream ouputStream = response.getOutputStream();   
            exportBook.write(ouputStream);    
            ouputStream.flush();    
            ouputStream.close();
     }
    	 
     //商品销售信息
     @RequestMapping("/exportSaleGoodsInfo")
     public void exportOnlySaleGoodsInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	 String[] titleArr= new String[]{"idProduct","sku","品牌","品类","型号","材料","颜色","尺寸","状态","大陆价","欧洲价","代销价",
    			 "尚美价","成本","成本币种","汇率","税率","当前位置","售价","销售币种","入库时间","销售时间","销售单号","销售渠道","客户","瑕疵"};
    	 String idProductsu =request.getParameter("idProducts");
    	 String idProducts = null;
    	 String[][] resultArr=null;
    if(StringUtil.isNotBlank(idProductsu)){
    	idProducts = java.net.URLDecoder.decode(idProductsu, "utf-8");//解决乱码问题
    }
    if(StringUtil.isNotBlank(idProducts)){    //导出勾选Idproduct 
/*    	Map<String,List<String>> searchMap = new HashMap<String,List<String>>();
    	List<String> idProductlist= (List<String>) new ArrayList();*/

    	 String[] idProduct = idProducts.split(";");
    	 int totalCnt = idProduct.length; //得到的总数量,条数
    	  resultArr = new String[totalCnt+1][titleArr.length]; //准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
    	 resultArr[0] =titleArr;  //第一行是id Idproduct
    	 for(int i=0;i<resultArr.length-1;i++){
    			String[] rowArr = resultArr[i+1];
    			String[] id = idProduct[i].split(":");
    			/*rowArr[0] = id[0];
    			rowArr[1] = id[1];
    			rowArr[2] = id[2];*/
/*    			for (String ss:idProduct) {
    				idProductlist.add(ss);
				}*/
    			for(int j=0;j<=titleArr.length-1;j++){
    				if(!id[j].equals(null)){
    					rowArr[j] = id[j];
    				}else {
						rowArr[j]="";
					}
    			}
    		}
    	 //searchMap.put("idproduct", idProductlist);
    }else{
    	//不勾选全部导出或根据条件查询导出
    	Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
   	    MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false,true,true);
   	    searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
   	    MiniUiGrid miniuiGrid = productService.getGoodsSaleInfoList(searchMap);
     	int totalCnt = miniuiGrid.getTotal();
     	List<Product>  goodsList = miniuiGrid.getData();  //获取需要导出的数据
     	   resultArr = new String[totalCnt+1][titleArr.length];//准备的一个放查询结果的数组，查询的结果放入;[totalCnt+1]行数，[titleArr.length]列数
          resultArr[0] = titleArr;  //标题那一行(第一行)
          for(int i=0; i<resultArr.length-1; i++){
          	
          	String[] rowArr = resultArr[i+1]; //准备一个数组，放入第二行的内容，因为第一行是标题 status+":"+ curSiteId
          	Product o =  goodsList.get(i);
          	rowArr[0] = o.getIdProduct()+"";
          	rowArr[1] = o.getSku()+"";
          	rowArr[2] = o.getBrandName();
          	rowArr[3] = o.getSeriesName()+"";
          	rowArr[4] = o.getType()+"";
          	rowArr[5] = o.getMaterial();
          	rowArr[6] = o.getColor()+"";
          	rowArr[7] = o.getSize()+"";
          	rowArr[8] = o.getStatus()+"";
          	rowArr[9] = o.getDlPrice()+"";
          	rowArr[10] = o.getEuPrice()+"";
          	rowArr[11] = o.getDxPrice()+"";
          	rowArr[12] = o.getSmPrice()+"";
          	rowArr[13] = o.getCost()+"";
          	Integer row = o.getIdCostCurrency();
          	if (row == 1){
          		rowArr[14] = "RMB";
          	} else if (row == 2){
          		rowArr[14] = "EU";
          	}
            else if (row == 3){
            	rowArr[14] = "HKD";
            } 
            else if (row == 4){
            	rowArr[14] =  "US";
            }
			else if (row == 5){
				rowArr[14] =  "CHF";
			}else{
				rowArr[14] =  "";}
          	rowArr[15] = o.getExchangeRate()+"";
          	if (o.getTaxesReate()!=null) {
          		rowArr[16] = o.getTaxesReate()+"";
			}else {
				rowArr[16] = "";
			}
          	rowArr[17] = o.getCurSiteName();
          	rowArr[18] = o.getSalePrice()+"";
          	//rowArr[19] = o.getSalePriceCurrency()+"";
          	Integer salePriceCurrency = o.getSalePriceCurrency();
          	if (salePriceCurrency == 1){
          		rowArr[19] = "RMB";
          	} else if (salePriceCurrency == 2){
          		rowArr[19] = "EU";
          	}
            else if (salePriceCurrency == 3){
            	rowArr[19] = "HKD";
            } 
            else if (salePriceCurrency == 4){
            	rowArr[19] =  "US";
            }
			else if (salePriceCurrency == 5){
				rowArr[19] =  "CHF";
			}else{
				rowArr[19] =  "";}
          	//处理时间格式
          	//rowArr[20] = o.getInstock()+"";
          	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
          	rowArr[20] = format.format(o.getInstock().getTime());
          	rowArr[21] = format.format(o.getSellDate().getTime());
          	rowArr[22] = o.getSellIdOrder()+"";
          	rowArr[23] = o.getSellChannel();
          	rowArr[24] = o.getCustomerName();
          	if (o.getIsFlaw()=="1") {
          		rowArr[25] = "是";
			}else {
				rowArr[25] = "否";
			}
          	//rowArr[25] = o.getIsFlaw();
  		}
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
            response.setHeader ("Content-disposition", "attachment;filename=idProduct_"+sdf1.format(new Date())+".xls");
            OutputStream ouputStream = response.getOutputStream();   
            exportBook.write(ouputStream);    
            ouputStream.flush();    
            ouputStream.close();
     }
     
     
}
