package com.huaixuan.network.web.action.order;


import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.Site;
import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.order.OrderPrint;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.order.ProductShoppingCar;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.base.SiteService;
import com.huaixuan.network.biz.service.customer.CustomerService;
import com.huaixuan.network.biz.service.order.OrderService;
import com.huaixuan.network.biz.service.order.ProductShoppingCarService;
import com.huaixuan.network.biz.service.product.ProductService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;


@Controller
@RequestMapping("/order1")
public class OrderAction1 {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private ProductShoppingCarService productShoppingCarService;
	
	
	
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping("orderlist")
	public String getList() {
		return "/order/orderlist";
	}

	//只能通过订单ID查询
	@RequestMapping("orderlisOnlyOrderid")
	public String orderlisOnlyOrderid() {
		return "/order/orderlisOnlyOrderid";
	}

	
	/**
	 * 根据查询条件 返回订单信息 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getorderlist")
	public @ResponseBody
	Object orderlist(AdminAgent adminAgent,HttpServletRequest request){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		searchMap.put("userName", adminAgent.getUsername());
		if(MiniUiUtil.hkAllSite.contains(adminAgent.getSiteId()+""))
		{
			//如果是香港账号只查询香港的订单
			searchMap.put("hkOperator", "yes");
		}
		if(MiniUiUtil.hqgAllSite.contains(adminAgent.getSiteId()+"")){
			//如果是环球港账号只查看环球港站点订单
			searchMap.put("hqgOperator", "yes");
		}
		QueryPage  queryPage = orderService.getOrderList(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setData(queryPage.getItems());
		gird.setTotal(queryPage.getTotalItem());
		return gird;
	}
	
	
	/**
	 * 根据查询条件  获取订单总金额  返回  订单总额,现金总额  eg：123,456 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getorderlistTotlPrice")
	public @ResponseBody
	Object getorderlistTotlPrice(AdminAgent adminAgent,HttpServletRequest request){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		if(MiniUiUtil.hkAllSite.contains(adminAgent.getSiteId()+""))
		{
			//如果是香港账号只查询香港的订单
			searchMap.put("hkOperator", "yes");
		}
		if(MiniUiUtil.hqgAllSite.contains(adminAgent.getSiteId()+"")){
			//如果是环球港账号只查看环球港站点订单
			searchMap.put("hqgOperator", "yes");
		}
		searchMap.put("userName", adminAgent.getUsername());
		return orderService.getOrderListTotalPrice(searchMap);
	}
	
	/**
	 * 订单完成付款
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderPayMent")
	public @ResponseBody
	Object orderPayMent(HttpServletRequest request,AdminAgent adminAgent){
			Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
			
			requestMap.put("operator", adminAgent.getUsername());
			orderService.customerOrderPayMent(requestMap);
			return "ok";
	}
	
	

	/**
	 * 订单取消
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancelOrder")
	public @ResponseBody
	Object cancelOrder(HttpServletRequest request,AdminAgent adminAgent){
			Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
			requestMap.put("idLastOperator", adminAgent.getUsername());
			orderService.cancelOrderById(requestMap);
			return "ok";
	}
	
	
	
	
	/**
	 * 订单更新
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatCustomerOrder")
	public @ResponseBody
	Object updateOrder(HttpServletRequest request,AdminAgent adminAgent)
	{
			Map<String,String> map = MiniUiUtil.getParameterMap(request);
			Orderdetails o = (Orderdetails)MiniUiUtil.Map2Object(map, Orderdetails.class);
			o.setIdEmployee(adminAgent.getUsername());
			orderService.updateCustomerOrder(o);
			return "ok";
	}

	
	
	
	
	@RequestMapping("/customerordeexport")
	@AdminAccess
	public void exportOrder(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent)throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		searchMap.put("userName", adminAgent.getUsername());
		if(MiniUiUtil.hqgAllSite.contains(adminAgent.getSiteId()+"")){
			//如果是环球港账号只查看环球港站点订单
			searchMap.put("hqgOperator", "yes");
		}
		String[] titleArr = new String[]{"订单号","日期","总价","币种","付款方式","现金","客户","客户经理","渠道","出售地点","最后经手人","直接开单人","付款时间","发货","发货时间","订单状态"};
		searchMap.put("noStartRowAndEndRow", "yes");
		MiniUiGrid miniuiGrid = orderService.getOrdersList(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<Orderdetails>	orderlsit =  (List<Orderdetails>) miniuiGrid.getData();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String[][] resultArr = new String[totalCnt+1][titleArr.length];
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1];
			Orderdetails o = orderlsit.get(i);
			rowArr[0] = o.getIdorder();         //订单号
			rowArr[1] = sdf.format(o.getDate());//日期
			rowArr[2] = o.getSubTotal()+"";     //总价    
			rowArr[3] = o.getCurrencyname();    //币种
			rowArr[4] = o.getPaymentname();    //付款方式
			rowArr[5] = o.getAmountCash()+"";   //现金
			rowArr[6] = o.getCustomername();   //客户
			rowArr[7] = o.getCustomerManager(); //客户经理
			rowArr[8] = o.getSellchannelname(); //渠道
			rowArr[9] = o.getSitename();        //出售地点
			rowArr[10] = o.getEmployeename();  //最后经手人
			rowArr[11] = o.getEmpName();       //直接开单人
			rowArr[12] = o.getGmtpay()+"";    //付款时间
			rowArr[13] = o.getDeliverystatus(); //发货
			rowArr[14] = o.getDeliverydate()+""; //发货时间
			String Status = "";                //订单状态
			if(o.getStatus() == 0){
				Status = "已付款";
			}else if(o.getStatus() == 1){
				Status = "未付款";
			}else if(o.getStatus() == 2){
				Status = "取消/部分订单取消";
			}
			rowArr[15] = Status;
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
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=order_"+System.currentTimeMillis()+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();
	}
	
	
	
	/**
	 * 订单打印
	 * @param request
	 * @return
	 */
	@RequestMapping("/print")
	public   String  printOrder(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
			Map<String,String> map = MiniUiUtil.getParameterMap(request);
			
			
			//通过idorder获取产品
			MiniUiGrid gird = orderService.getProductByidorder(map);
			List<Product> productList = (List<Product>)gird.getData();
			Map<String,OrderPrint> keyMap = new HashMap<String, OrderPrint>();
			int totalNum = 0;
			for(Product p : productList)
			{
				totalNum++;
				String sku = p.getSku();
				if(keyMap.get(sku) == null) //第一次
				{
					OrderPrint op = new OrderPrint();
					op.setName(p.getBrandName()+" "+p.getSeriesName()+" " + p.getType() + " " + 
							p.getMaterial() + " " + p.getColor() + " "+ p.getSize());
					op.setQty(1);
					op.setDealPrice(p.getSalePrice());
					op.setAmountPrice(1*op.getDealPrice());
					op.setUnitPrice(p.getSmPrice());
					keyMap.put(sku, op);
				}
				else
				{
					OrderPrint  print =  keyMap.get(p.getSku());
					int nowQty = print.getQty() + 1;
					print.setAmountPrice(nowQty*print.getDealPrice());
					print.setQty(nowQty);
				}
				
			}
			List<OrderPrint> list = new ArrayList<OrderPrint>();
			Set<Entry<String, OrderPrint>> entryeSet =  keyMap.entrySet();
			Iterator<Entry<String, OrderPrint>> it =  entryeSet.iterator();
			while(it.hasNext())
			{
				list.add(it.next().getValue());
			}
			Orderdetails o = orderService.getorderlistByid(map.get("idorder"));
			Customer  customer = customerService.getCustomerById(Long.valueOf(o.getIdCustomer()));
			model.addAttribute("customer",customer);
			model.addAttribute("order",o);
			model.addAttribute("list", list);
			model.addAttribute("totalNum", totalNum);
			return "/order/print";
	}

	
	
	/**
	 * 取消预留的某个产品
	 * @param request map-idorder
	 * @return
	 */
	@RequestMapping("/cancelOrderByIdProduct")
	public  @ResponseBody Object  cancelOrderByIdProduct(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
			Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
			
			if(StringUtil.isBlank(searchMap.get("idProduct"))) return "error";
			/*
			List<ProductHistory> productHistoryList = productService.selectProductHistoryByIdProduct(searchMap.get("idProduct"));
			String idOperator = "";
			for(ProductHistory ph : productHistoryList)
			{
				if(5 == ph.getIdOperation().intValue())
				{
					idOperator = ph.getIdOperator(); //最后预留的人
				}
			}
			*/
			//不用预留的人才能取消  同一个站点的即可
			Product product = productService.getproduct(searchMap.get("idProduct"));
			
			
			//上海站点的用户可以取消任何站点的产品
			if(MiniUiUtil.shSite.contains(product.getCurSiteId()))
			{
				orderService.cancelOrderByIdProduct(searchMap.get("idProduct"),adminAgent);
				return "ok";
			}
			else if((adminAgent.getSiteId()+"").equals(product.getCurSiteId()))
			{
				orderService.cancelOrderByIdProduct(searchMap.get("idProduct"),adminAgent);
				return "ok";
			}
			else
			{
				return "no_sample_operator";
			}
			
			
			
			
	}
	
	
	
	
	/**
	 * 销售入库某个产品
	 * @param request map-idorder
	 * @return
	 */
	@RequestMapping("/saleInStockByIdProduct")
	public  @ResponseBody Object  saleInStockByIdProduct(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
			Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
			
			if(StringUtil.isBlank(searchMap.get("idProduct"))) return "error";
			String idPayment = searchMap.get("idPayment");
			orderService.saleInStockByIdProduct(searchMap.get("idProduct"), idPayment, adminAgent);
			return "ok";
	}
	
	
	/**
	 * 批量销售入库产品
	 * @param request map-idorder
	 * @return
	 */
	@RequestMapping("/batchSaleInStockByIdProduct")
	public  @ResponseBody Object  batchSaleInStockByIdProduct(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
			Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
			if(StringUtil.isBlank(searchMap.get("idProducts"))) return "error";
			String idProducts = searchMap.get("idProducts"); //2662013225795,9862013263312
			if(StringUtil.isNotBlank(idProducts))
			{
				String[] products = idProducts.split(",");
				for(String idProduct : products)
				{
					orderService.saleInStockByIdProduct(idProduct, null, adminAgent);
					//删除购物车 产品
					ProductShoppingCar productShoppingCar = new ProductShoppingCar();
					productShoppingCar.setIdProduct(idProduct);
					productShoppingCar.setUserName(adminAgent.getUsername());
					productShoppingCarService.removeShoppingCar(productShoppingCar);
				}
			}
			
			return "ok";
	}
	
	 
	@RequestMapping("/toSaleInStockByIdProduct")
	public  String  toSaleInStockByIdProduct(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
		 
			return "/product/saleInStock";
	}

}
