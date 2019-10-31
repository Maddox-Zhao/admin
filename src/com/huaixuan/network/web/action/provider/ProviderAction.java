package com.huaixuan.network.web.action.provider;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.base.Site;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao;
import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailYShangDao;
import com.huaixuan.network.biz.dao.provider.ProvidePostOrderLogDao;
import com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.domain.provider.OrderItemDtoChild;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShangImge;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;
import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumGoodsStatus;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.HttpRequest;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService;
import com.huaixuan.network.biz.service.provider.ProvideGoodsYShangImgeService;
import com.huaixuan.network.biz.service.provider.ProvideGoodsYShangLogService;
import com.huaixuan.network.biz.service.provider.ProvideOrderWaybillDetailService;
import com.huaixuan.network.biz.service.provider.ProvideOrderDetailService;
import com.huaixuan.network.biz.service.provider.ProvidePostOrderLogService;
import com.huaixuan.network.biz.service.provider.ProviderService;
import com.huaixuan.network.biz.service.provider.XiYouPlatFormStockUpdate;
import com.huaixuan.network.biz.service.provider.YShangPlatFormStockUpdate;
import com.huaixuan.network.biz.service.provider.impl.YShangPlatFormStockUpdateImpl;
import com.huaixuan.network.common.util.emisZipUtil;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.common.util.json.JSONException;
import com.huaixuan.network.common.util.json.JSONObject;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.biz.service.reserved.HxStockOrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.huaixuan.network.web.action.platformstock.PlatformStockAction;
import com.hundsun.common.lang.StringUtil;

/**
 *2019-1-10 
 */
@Controller
@RequestMapping(value = "/provider")
public class ProviderAction extends BaseAction{
	
	private @Value("${file.upload.dir}")
	String upload;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private StockUpdateService stockUpdateService;
	@Autowired
	private ProvidePostOrderLogService  providePostOrderLog;
	
	@Autowired
	private ProvideOrderWaybillDetailService providerOrderService;
	
	@Autowired
	private PlatFormOrderDetailsDao platformOrderDetailsDao;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
	private ProvideOrderDetailDao provideOrderDetailDao;
	
	@Autowired
	ProvideOrderDetailService provideOrderDetailService;
	@Autowired
	private ProviderGoodsUpdateDao providerGoodsUpdateDao;

	@Autowired
	ProvidePostOrderLogDao providePostOrderLogDao;
	
	@Autowired
	private ProvideOrderWaybillDetailDao provideOrderWaybillDetailDao;
	
	@Autowired
	private ProvideGoodsImgeService provideGoodsImgeService;
	
	@Autowired
	private ProvideGoodsYShangImgeService provideGoodsYShangImgeService;
	
	@Autowired
	private XiYouPlatFormStockUpdate xiYouPlatFormStockUpdate;
	@Autowired
	private ProvideGoodsYShangUpdateDao provideGoodsYShangUpdateDao;

    @Autowired
    private GoodsBatchManager goodsBatch;
	
    @Autowired
	private ProvideOrderWaybillDetailYShangDao provideOrderWaybillDetailYShangDao;
    
    @Autowired
    private ProvideOrderWaybillDetailService provideOrderWaybillDetailService;
    
    @Autowired
    private ProvideGoodsYShangLogService provideGoodsYShangLogService;
    
    
    @Autowired
    private YShangPlatFormStockUpdate yShangPlatFormStockUpdate;
    
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	
	protected Log log = LogFactory.getLog(this.getClass());
	/**
	 * @Description: 供应商信息
	 * @date 2019-1-11
	 */
    @RequestMapping("/searchProvider")
	public String search(@ModelAttribute("provider") ProvideGoodsXiYou provide,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model,ServletRequest request){
		
		QueryPage page = providerService.getProviderListByConditionPage(provide, currPage, this.pageSize);
		if(page != null){
    		model.addAttribute("query", page);
    	}
		model.addAttribute("provider", provide);
		
		return "/provider/providerlist";
	}
	
    
    @RequestMapping("/searchProviderYShang")
   	public String searchYShang(@ModelAttribute("provider") ProvideGoodsYShang provide,
   			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
   			Model model,ServletRequest request){
   		
   		QueryPage page = providerService.getProviderListByConditionPageYShang(provide, currPage, this.pageSize);
   		if(page != null){
       		model.addAttribute("query", page);
       	}
   		model.addAttribute("provider", provide);
   		
   		return "/provider/providerlistYShang";
   	}
    
    
    
    
	/**
	 * @Description: providerLog
	 * @date 2019-1-14
	 */

	@RequestMapping("/providerLog")
	public String providerLog(@ModelAttribute("provider") ProvidePostOrderLog provide,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model,ServletRequest request){
		
		QueryPage page = providePostOrderLog.getProviderLogConditionPage(provide, currPage, this.pageSize);
		if(page != null){
    		model.addAttribute("query", page);
    	}
		model.addAttribute("providerLog", provide);
		
		return "/provider/providerLog";
	}

	
	
	
	//将属于供应商的产品找到，放入ProvideOrderDetail这个表
	@RequestMapping("/selectProvideOrderDetail")
    @AdminAccess
	public String selectProvideOrderDetail(Model model,HttpServletRequest request){
		//获取一段时间的订单，插入到新表 provide_order_detail中
	/*	Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
		String insertTime =df.format(new Date(new Date().getTime() - 3*24*60*60*1000));  //获取一段时间的订单
		System.out.println(insertTime);
		Map<String,String> searchMap = new HashMap<>();
		PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
		platformorderdetails.setInsertTime(insertTime);
	    List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
	    if(orderDetais!=null && orderDetais.size()>0){
	    	 //先循环出，是供应商的商品，将商品详情放入list结合
		    List<ProvideOrderDetail> orderIdProList = getAllProvideOrderDetais(orderDetais); 
		    List<ProvideOrderDetail> orderList = new ArrayList<ProvideOrderDetail>();
		    System.out.println(orderIdProList.size()+"==============");
//		    List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
		     //如果orderIdProList有数据，就循环订单详情
		    if(orderIdProList!=null && orderIdProList.size()>0){
		        for(int i=0;i<orderIdProList.size();i++){
		        	searchmap.put("tradeId", orderIdProList.get(i).getTradeId());
		        	searchmap.put("itemId", orderIdProList.get(i).getItemId());
		        	List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
		        	System.out.println(list.size());
		        	if(list == null || list.size()==0){
		        		orderList.add(orderIdProList.get(i));
		        	}
		      }
		        if(orderList.size()>0){
		        	provideOrderDetailDao.insertOrderDetailList(orderList);
		        }
		    	
		    	System.out.println("-----------");
		    }
	    }*/

		return "/provider/provideAllOrderDetail";

    }
	
	/**
	 * @Description: provider订单
	 * @date 2019-1-16
	 */
	@RequestMapping("/providerOrder")
	public String getList(AdminAgent adminAgent,HttpServletRequest request,Model model) {
		model.addAttribute("site",adminAgent.getSiteId());	
		return "/provider/providerOrderList";
	}
	
	/**
	 * @Description: provider订单
	 * @date 2019-1-16
	 */
	@RequestMapping("/getProviderJsonList")
	@AdminAccess
	public @ResponseBody Object productList2Json(HttpServletRequest request,AdminAgent adminAgent) {
  		
  		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
  		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);//去除空格
//		String orderNO = request.getParameter("orderId"); 
		String orderNO = searchMap.get("orderId");
//		System.out.println(orderNO);
		if(StringUtils.isNotBlank(orderNO)){
			if(orderNO.startsWith("XY")){
			getOrerDetail(orderNO);
			}else if(orderNO.startsWith("YS")){
				getYshangOrderDetail(orderNO);
			}
		}
		
//		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, false, true, true);
		
		return providerOrderService.searchAllProviderOrderId(searchMap);
	}
	

	private void getYshangOrderDetail(String orderid) {
		 
		
        String methodOrder = "yshang.wdk.trade.order.get";
		
		
		TreeMap<String,String> map = new TreeMap<String, String>();
		TreeMap<String,Object> mapObj = new TreeMap<String,Object>();
		List<TreeMap<String,Object>> mapList = new  ArrayList<TreeMap<String,Object>>();
		
			String nowTime = df.format(new Date());
		//系统需要参数
			map.put("method", methodOrder);
		map.put("timestamp", nowTime);//调用方法
		map.put("app_key", app_key_ys);
		map.put("v", "1.0");
		map.put("secret", app_secret_ys);
		
		 JSONObject jsonObject = new JSONObject();
		    try {
				jsonObject.put("outOrderId",orderid);
			} catch (JSONException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		
		map.put("data", jsonObject.toString());
		String sign = getSign(map);
		map.put("sign", sign);
		String response = HttpRequest.sendPostNoSecret(ys_url, map);
		
//		System.out.println(response);
		
		JSONObject jsonO;
		try {
			jsonO = new JSONObject(response);
			String jsondata = jsonO.getString("data");
			String statu = jsonO.getString("errorCode");
			String success = jsonO.getString("success");
			if(success.equals("0")){
				JSONObject dataObj = new JSONObject(jsondata);
				String outOrderId = dataObj.getString("outOrderId");
				String parentOrderId = dataObj.getString("parentOrderId");
				String receiverCity = dataObj.getString("receiverCity");
				String receiverArea = dataObj.getString("receiverArea");
				String receiverName = dataObj.getString("receiverName");
				String receiverMobile = dataObj.getString("receiverMobile");
				String receiverProvince = dataObj.getString("receiverProvince");
				String receiverAddress = dataObj.getString("receiverAddress");
				String totalPay = dataObj.getString("totalPay");
				
				String subOrders = dataObj.getString("subOrders");
				JSONArray subOrdersArray = new JSONArray(subOrders);
				if(subOrdersArray!=null && subOrdersArray.length()>0){
				for(int i=0;i<subOrdersArray.length();i++){
					
					JSONObject subOrdersJSON = new JSONObject(subOrdersArray.get(i).toString());
					
					String wmsStatus = subOrdersJSON.getString("wmsStatus");
					String status = getShipStatusYshang(wmsStatus);
					String expressName = subOrdersJSON.getString("expressName");
					String totalPayChild = subOrdersJSON.getString("totalPay");
					String orderId = subOrdersJSON.getString("orderId");
					
					String warehouseId = subOrdersJSON.getString("warehouseId");
					String expressNum = subOrdersJSON.getString("expressNum");
					String orderItems = subOrdersJSON.getString("orderItems");
					
					Map<String,String> detailmap = new HashMap<String, String>();
					Map<String,String> onemap = new HashMap<String, String>();
					String sellPlatform = getPlat(outOrderId); //获取哪个平台卖出的
					
					detailmap.put("orderId", outOrderId);
					detailmap.put("orderstatus", status);
					detailmap.put("buyname", receiverName);
					detailmap.put("phone", receiverMobile);
					
					detailmap.put("address", receiverAddress);
					detailmap.put("amount", totalPay);
					detailmap.put("paidamount", totalPayChild);
					detailmap.put("shipname", expressName);
					
					detailmap.put("waybillcode", expressNum); 
					detailmap.put("sellPlatform", sellPlatform);
					detailmap.put("pushPlatform", "yshang");
					onemap.put("orderId", outOrderId);
					List<ProvideOrderWaybillDetail> provideOrderWaybillDetail = provideOrderWaybillDetailDao.selectWayBill(onemap);
					if(provideOrderWaybillDetail!=null && provideOrderWaybillDetail.size()>0){
						
						try {
							provideOrderWaybillDetailDao.updateOrderWaybillDetail(detailmap);
							detailmap.remove("pushPlatform");
							detailmap.remove("sellPlatform");
							detailmap.put("outOrderId", outOrderId);
							provideOrderWaybillDetailYShangDao.updateOrderWaybillDetailYShang(detailmap);
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
						
					}else{
						try {
							
							provideOrderWaybillDetailDao.insertWayBillDetail(detailmap);
							
							JSONArray orderItemsArray = new JSONArray(orderItems);
							if(orderItemsArray!=null && orderItemsArray.length()>0){
								detailmap.put("outOrderId", outOrderId);
								
								
								detailmap.put("parentOrderId", parentOrderId);
								detailmap.put("receiverCity", receiverCity);
								detailmap.put("receiverArea", receiverArea);
								detailmap.put("receiverProvince", receiverProvince);
								
								detailmap.put("warehouseId", warehouseId);
								detailmap.put("orderChildId", orderId);
								 
								for(int t=0;t<orderItemsArray.length();t++){
									JSONObject orderItemsJSON = new JSONObject(orderItemsArray.get(t).toString());
									String nowPrice = orderItemsJSON.getString("nowPrice");
									String quantity = orderItemsJSON.getString("quantity");
									String productIdAndVendorIdStr = orderItemsJSON.getString("productIdAndVendorIdStr");
									String salePrice = orderItemsJSON.getString("salePrice");
									String productName = orderItemsJSON.getString("productName");
									
									detailmap.put("nowPrice", nowPrice);
									detailmap.put("quantity", quantity);
									detailmap.put("productId", productIdAndVendorIdStr);
									detailmap.put("productName", productName);
									
									detailmap.put("salePrice", salePrice);
									
									provideOrderWaybillDetailYShangDao.insertWayBillDetailYShang(detailmap);
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
						
					}
					
					
					
				}
				
				}
				
				
			}
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	
}


private String getShipStatusYshang(String orderStatus) {
	String status ="";
	if(orderStatus.equals("100")){
		status="商家待发货给云尚互联(非直发)";
	}else if(orderStatus.equals("110")){
		status="商家已发货给云尚互联(非直发)";
	}else if(orderStatus.equals("120")){
		status="云尚互联已经收货(非直发)";
	}else if(orderStatus.equals("130")){
		status="订单已确认(直发待发货)";
	}else if(orderStatus.equals("140")){
		status="已拣货";
	}else if(orderStatus.equals("170")){
		status="已发货";
	}else if(orderStatus.equals("180")){
		status="已签收";
	}else if(orderStatus.equals("190")){
		status="已拒签";
	}else if(orderStatus.equals("200")){
		status="已完成";
	}
	return status;
}
	
	/**
	 * @param orderDetais
	 * @return
	 *  //得到时间段内是哪家供货商
	 */
	private List<ProvideOrderDetail> getAllProvideOrderDetais(List<PlatFormOrderDetails> orderDetais) {
		List<ProvideOrderDetail> lis = new ArrayList<ProvideOrderDetail>();
		Map<String,String> searchMap = new HashMap<String, String>();
		for(int i=0;i<orderDetais.size();i++){
			 String sku = orderDetais.get(i).getMerchantSkuId(); //我们自己的sku
			   String proid = sku.substring(3, 10);
			   searchMap.put("sku", sku);
			   searchMap.put("xiyouProdId", proid);
			   List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap); //查询供应商表是否有这个商品，并确定是哪个商家的
			   //如果能查询出商品，则是供应商的商品，因为xiyouProdId可判断出是西有银泰的
			   if(list !=null && list.size()>0){
				   //把订单id放进集合
				   ProvideOrderDetail pod = new ProvideOrderDetail();
				   pod.setTradeId(orderDetais.get(i).getIdorder());
				   pod.setUserName(orderDetais.get(i).getName());
				   pod.setPhone(orderDetais.get(i).getMobile());
				   pod.setProvince(orderDetais.get(i).getProvince());
				   
				   pod.setCity(orderDetais.get(i).getCity());
				   pod.setRegion(orderDetais.get(i).getDistrict());
				   pod.setLocation(orderDetais.get(i).getStreetAddress());
				   pod.setTotalPrice(orderDetais.get(i).getTotalPrice());
				   pod.setPayPrice(orderDetais.get(i).getPayPrice());
				   
				   
				   pod.setInsertTime(orderDetais.get(i).getInsertTime());
				   pod.setCreateTime(orderDetais.get(i).getPalcedTime());
				   pod.setPayTime(orderDetais.get(i).getPayTime());
				   String prodid = orderDetais.get(i).getMerchantSkuId().substring(3, 10);
				   pod.setProdId(prodid);
				   pod.setItemId(orderDetais.get(i).getMerchantSkuId());
				   
				   pod.setTitle(orderDetais.get(i).getProductname());
				   pod.setSellPrice(orderDetais.get(i).getPrice());
				   pod.setTax("0");
				   pod.setQty(Integer.parseInt(orderDetais.get(i).getQuantity()));
				   pod.setFreight(orderDetais.get(i).getFreight());
				   
				   pod.setSellPlatform(orderDetais.get(i).getPtype());
				   pod.setProvider("xiyou");
				   lis.add(pod);

			   }
			   
			   
		}
		return lis;
	}
	
	
	
	
	/**
	 * @param merchantSkuId
	 * @return
	 */
	
	@RequestMapping("/selectSearchOrderDetalis")
    public @ResponseBody Object selectHxDetalisStockOrder(HttpServletRequest request,
    		HttpServletResponse respons,AdminAgent adminAgent){
		
		Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, true); //去除空格
		MiniUiGrid grid = provideOrderDetailService.getProvideOrderDetail(searchmap);
		return grid;
   	 
    }
	
	/**
	 * @Description: 获取一个订单信息
	 * @date 2019-1-16
	 */
	@RequestMapping("/toProviderEdit")
	public String getDetail(HttpServletRequest request,Model model,AdminAgent adminAgent) {
		//Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		return "/provider/providerOneOrderDetail";
	}
	
	/**
	 * @Description: 获取一个推送后的订单信息
	 * @date 2019-1-16
	 */
	
	@RequestMapping("/toOrderListShow")
	public String getOrderListShow(HttpServletRequest request,Model model,AdminAgent adminAgent) {
		//Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		return "/provider/providerOneOrderList";
	}
	
	
	@RequestMapping("/getOneProviderOrder")
	public @ResponseBody Object getProduct(HttpServletRequest request,Model model) {
		String orderId = request.getParameter("orderId");
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true); //去除空格
		String tradeId = searchMap.get("tradeId");
		MiniUiGrid grid=null;
		if(tradeId!=null){
			grid = provideOrderDetailService.searchOneProviderOrderOrderIdService(searchMap);
		}
		return grid;
	}
	
	
	@RequestMapping("/getOrderListYshang")
	public @ResponseBody Object getOrderListYshang(HttpServletRequest request,Model model) {
		String orderId = request.getParameter("orderId");
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true); //去除空格
		String tradeId = searchMap.get("tradeId");
		MiniUiGrid grid=null;
		if(tradeId!=null){
			if(tradeId.startsWith("YS")){
				grid = provideOrderWaybillDetailService.selectMiniuiOrderListDetailService(searchMap);
			}
			
		}
		return grid;
	}
	
	
	
	
	@RequestMapping("/pushOrder")
    public @ResponseBody Object pushOrder(HttpServletRequest request,
    		HttpServletResponse respons,AdminAgent adminAgent){
		
		Map<String,String> map = new HashMap<String, String>();
   	   Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
   	   
		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
		
		searchmap.put("status", "200");
		 //用于云尚的skuid
		List<ProvidePostOrderLog> orderLog = providePostOrderLogDao.selectPushOrderLog(searchmap);
		if(orderLog!=null && orderLog.size()==0){
			searchmap.remove("prodId");
			searchmap.put("skuId", request.getParameter("prodId"));
            orderLog = providePostOrderLogDao.selectPushOrderLog(searchmap);
		}
		
		if(orderLog!=null && orderLog.size()>0){
			map.put("code", "100");
			map.put("message", "此订单已推送成功,不用重复推送"); //如果想重复提交可修改数据库表provide_post_order_log的字段status=20，即可
			return map;
		}
//		searchmap.put("noStartRowAndEndRow", "yes"); //导出有这个，可以看看
		String tradeId = request.getParameter("orderId");
		
		
//		Map<String,String> xiyouMap = pushXiyouOrder(searchmap,tradeId);
		
		
		
		searchmap.put("tradeId", tradeId);
		
		searchmap.remove("prodId");
		//{tradeId=YS2225215019052, status=200, orderId=YS2225215019052}
		
		List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
		List<ProvideOrderDetail> listXY = new ArrayList<ProvideOrderDetail>();
		List<ProvideOrderDetail> listYShang = new ArrayList<ProvideOrderDetail>();
		
//		System.out.println(list.size()+"==========");
		//查询出东西，必须值不为空，才能发订单
		if(list !=null && list.size()>0){
			//判断必填值是否都填好，并符合要求
			for(int i=0;i<list.size();i++){
				ProvideOrderDetail pod = list.get(i);
				String itemId = pod.getItemId();
				String usernamme = pod.getUserName();
				String phone = pod.getPhone();
				String province = pod.getProvince();
				String city = pod.getCity();
				
				String region = pod.getRegion();
				String location = pod.getLocation();
				if(StringUtils.isBlank(usernamme) || usernamme.contains("*")){
					map.put("code", "300");
					map.put("message", "收件人不能为空,不能包含*");
					return map;
				}else if(StringUtils.isBlank(phone) || phone.contains("*")){
					map.put("code", "300");
					map.put("message", "手机号不能为空,不能包含*");
					return map;
				}else if(StringUtils.isBlank(province) || province.contains("*")){
					map.put("code", "300");
					map.put("message", "省不能为空不能为空,不能包含*");
					return map;
				}else if(StringUtils.isBlank(city) || city.contains("*")){
					map.put("code", "300");
					map.put("message", "市不能为空,不能包含*");
					return map;
				}else if(StringUtils.isBlank(region) || region.contains("*")){
					map.put("code", "300");
					map.put("message", "区不能为空,不能包含*");
					return map;
				}else if(StringUtils.isBlank(location) || location.contains("*")){
					map.put("code", "300");
					map.put("message", "详细地址不能为空,不能包含*");
					return map;
				}
				//如果itemId以222开头则是，云尚的订单，从list中取出放入listYShang集合中，清除掉原来的list中的这个商品
				if(itemId.startsWith("111")){
					listXY.add(list.get(i));
				}else if(itemId.startsWith("222")){
					listYShang.add(list.get(i));
				}
				
				
				
				String totalPrice = pod.getTotalPrice();
				String payPrice = pod.getPayPrice();
				String createTime = pod.getCreateTime();
				String payTime = pod.getPayTime();
				String prodId = pod.getProdId();
				
				
				String title = pod.getTitle();
				String sellPrice = pod.getSellPrice();
				String qty = pod.getQty()+"";
				String freight = pod.getFreight();
				//String sellPlatform = pod.getSellPlatform();
				//String provider = pod.getProvider();
			    if(StringUtils.isBlank(totalPrice)){
					map.put("code", "300");
					map.put("message", "订单总金额不能为空");
					return map;
				}else if(StringUtils.isBlank(payPrice)){
					map.put("code", "300");
					map.put("message", "实付金额不能为空");
					return map;
				}else if(StringUtils.isBlank(createTime)){
					map.put("code", "300");
					map.put("message", "订单创建时间不能为空");
					return map;
				}else if(StringUtils.isBlank(payTime)){
					map.put("code", "300");
					map.put("message", "支付时间不能为空");
					return map;
				}else if(StringUtils.isBlank(prodId)){
					map.put("code", "300");
					map.put("message", "银泰西有的ID不能为空");
					return map;
				}else if(StringUtils.isBlank(itemId)){
					map.put("code", "300");
					map.put("message", "我们的sku不能为空");
					return map;
				}else if(StringUtils.isBlank(title)){
					map.put("code", "300");
					map.put("message", "商品名称不能为空不能为空");
					return map;
				}else if(StringUtils.isBlank(sellPrice)){
					map.put("code", "300");
					map.put("message", "商品售价不能为空");
					return map;
				}else if(StringUtils.isBlank(qty)){
					map.put("code", "300");
					map.put("message", "购买数量不能为空");
					return map;
				}else if(StringUtils.isBlank(freight)){
					map.put("code", "300");
					map.put("message", "运费不能为空");
					return map;
				}
			}
			
			
			Map<String,String> resultmap = new HashMap<String, String>();
			//推送西有的订单
			if(listXY!=null && listXY.size()>0){
				 resultmap = pushOrder(listXY);
			}
			//推送云尚的订单
			if(listYShang!=null && listYShang.size()>0){
				 resultmap = pushYShangOrder(listYShang);
			}
            
			
//			System.out.println("=======");
//			System.out.println(resultmap.get("status"));
			map.put("code", resultmap.get("status"));
			map.put("message", resultmap.get("message"));
			return map;	
		}
		return null;
   	 
    }
	
	
	
	
	
	private static final String ys_url="https://api.winshine.shop/router/rest?";
	private static final String app_key_ys = "hzAzyPj3/EM=";
	private static final String app_secret_ys = "sJkS76FDhESeesLwz/nxGnR+zKnHQskmPA0uWV+17C4=";
	
	public Map<String,String> pushYShangOrder(List<ProvideOrderDetail> list) {
		String method = "yshang.wdk.trade.order.create";
		String sign="";
		String secret = "sJkS76FDhESeesLwz/nxGnR+zKnHQskmPA0uWV+17C4=";
		String v="1.0";

		
		Map<String, String> params = new HashMap<String, String>();
		  
			
			//系统需要参数
		  params.put("method", method);
		 params.put("timestamp", df.format(new Date()));//sdf.format(new Date()));
		 params.put("app_key",app_key_ys);
		 params.put("v", v);
		 params.put("sign", sign);
			
		
		
		 Map<String,String> mapp = new HashMap<String, String>();
		 Map<String,String> resultmap = new HashMap<String, String>();
		 
		 ProvideOrderDetail po = list.get(0);
		  String trade_id = po.getTradeId(); // 订单编号
  	      String username = po.getUserName();    // 用户名称
	   	  String phone = po.getPhone();    // 电话
  	
		  String province = po.getProvince();  // 省
		  String city = po.getCity();            // 市
		  String region = po.getRegion();       // 区
		  String location= po.getLocation();   // 详细地址
 
		  String amount = po.getTotalPrice();   // 订单总金额
		  
		  String cardno = po.getCardNo();
		  if(StringUtils.isBlank(cardno)){
			  cardno = "0";
		  }
		  String platName = po.getSellPlatform();

		 
		  JSONObject jsonObject = new JSONObject();
		  try {
			     jsonObject.put("cardNo",cardno);
				 jsonObject.put("outOrderId",trade_id);
				 jsonObject.put("receiverName",username);
				 jsonObject.put("receiverProvince",province);
				 jsonObject.put("receiverCity",city);
				 jsonObject.put("receiverArea",region);
				 jsonObject.put("receiverMobile",phone);
				 jsonObject.put("receiverAddress",location);
		
				 jsonObject.put("amount",amount); //订单中所有商品的总金额,即所有商品的供货价（cost）和
				 jsonObject.put("orderChannelId","10013");  //id  454725
				 jsonObject.put("orderChannelName","尚上");
			     
			  List<OrderItemDtoChild> listoc = new ArrayList<OrderItemDtoChild>();
			  for(int j=0;j<list.size();j++){
				  String shopCoupon = list.get(j).getShopCoupon();
				  String platformCoupon = list.get(j).getPlatformCoupon();
				  if(StringUtils.isBlank(shopCoupon)){
					  shopCoupon = "0.00";
				  }
				  
				  if(StringUtils.isBlank(platformCoupon)){
					  platformCoupon = "0.00";
				  }
					OrderItemDtoChild dto  = new OrderItemDtoChild();
					dto.setProductId(list.get(j).getProdId());
					dto.setProductNum(list.get(j).getQty());
					dto.setProductPrice(list.get(j).getSellPrice());
					dto.setShopCoupon(shopCoupon);
					dto.setPlatformCoupon(platformCoupon);
					 
					 listoc.add(dto);
			  }
			  
			  jsonObject.put("subOrders", com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(listoc)));
			  params.put("data",jsonObject.toString());
			  
			  List<String> keys = new ArrayList<String>(params.keySet());
				 Collections.sort(keys);

				 String str = "";
				 for (int i = 0; i < keys.size(); i++) {
				     String key = keys.get(i);
				     if (key.equals("sign")) {
				         continue;
				     }
				     String value = params.get(key);
				     str = str + key + value;
				 }
//				 System.out.println("str===>"+str);
//				 System.out.println("secret + str + secret===>"+secret + str + secret);
				 sign = getMD5Str(secret + str + secret).toUpperCase();
				 params.put("sign", sign);
				 
//				 System.out.println(params);
			  
				 String response="";
					try {
						response = post(ys_url, params, "UTF-8");
//						System.out.println(response);
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}

					
					
				
				JSONObject jsonObj = new JSONObject(response);
				
				Map<String, String> logMap = new HashMap<String, String>();
		
				String success = jsonObj.getString("success");
				String message = jsonObj.getString("errorMessage");
				String status ="";
				if(success.equals("0")){
					 status ="200";
					 message ="ok";
				}else{
					status ="100";
				}
				
				logMap.put("status", status);
				logMap.put("message", message);
				resultmap.put("status", status);
				resultmap.put("message", message);
				if (status.equals("200")) {
					
					for(int i=0;i<listoc.size();i++){
						String orderId = trade_id;
						String skuId = listoc.get(i).getProductId();//云尚的sku
						String ourSku = createOurSku(skuId); //我们sku
						Integer qty = listoc.get(i).getProductNum();
						String ptype = platName;
						String pushPlatform = "YShang";
						
						logMap.put("orderId", orderId);
						logMap.put("skuId", skuId);
						logMap.put("sSprodid", ourSku);
						logMap.put("qty", qty+"");
						logMap.put("ptype", ptype);
						logMap.put("pushPlatform", pushPlatform);
						try {
	//insert into provide_post_order_log values(null,#orderId#,#prodId#,#sSprodid#,#qty#,#ptype#,#pushPlatform#,sysdate(),#status#,#message#)
							providePostOrderLogDao.insertPushOrderLog(logMap);
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
						
						Map<String,String> upMap = new HashMap<String, String>();
						upMap.put("skuId", skuId);
						upMap.put("ourSku", ourSku);
						List<ProvideGoodsYShang> pgysgoods = provideGoodsYShangUpdateDao.selectYShangEntityByMap(upMap);
						if(pgysgoods!=null && pgysgoods.size()>0){
							int qtyy = qty; //需要减少的库存数
							int pnum = Integer.parseInt(getYShangStock(skuId)); //此时在查询，平台上的库存 
							upMap.put("stock", pnum+"");
							int t = provideGoodsYShangUpdateDao.updateGoodsYShangMap(upMap);
							if(t>0){
								mapp.put("sku", ourSku);
								mapp.put("type", "sh");
								 List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(mapp);
								 for (StockUpdate nufl : sulist){
			                            //判断是哪个平台的的，以便减去对应的平台的库存 
										
										int orderNum = nufl.getOrderStockNum();
										     orderNum = orderNum-qtyy ;
										if (orderNum < 0)
											orderNum = 0;
										nufl.setOrderStockNum(orderNum);
                                      
										if(platName.equals("siku") || platName.equals("sikunew")){
											int sikuOrderStock = sulist.get(0).getSikuOrderStock();
											sikuOrderStock = sikuOrderStock-qtyy ;
											if (sikuOrderStock < 0)
												sikuOrderStock = 0;
											nufl.setSikuOrderStock(sikuOrderStock);
											
										} else if (platName.equals("kaola")) {
											int kaolaOrderStock = nufl.getKaolaOrderStock();
											kaolaOrderStock = kaolaOrderStock-qtyy;
											if (kaolaOrderStock < 0)
												kaolaOrderStock = 0;
											nufl.setKaolaOrderStock(kaolaOrderStock);
										}else if (platName.equals("tmall")) {
											int tmallOrderStock = nufl.getTmallOrderStock();
											tmallOrderStock = tmallOrderStock-qtyy;
											if (tmallOrderStock < 0)
												tmallOrderStock = 0;
											nufl.setTmallOrderStock(tmallOrderStock);
										} else if (platName.equals("fql")) {
											int fqlOrderStock = nufl.getFqlOrderStock();
											fqlOrderStock = fqlOrderStock-qtyy;
											if (fqlOrderStock < 0)
												fqlOrderStock = 0;
											nufl.setFqlOrderStock(fqlOrderStock);
										}else if (platName.equals("pdd") || platName.equals("pddnew")) {
											int pddOrderStock = nufl.getPddOrderStock();
											pddOrderStock = pddOrderStock-qtyy ;
											if (pddOrderStock < 0)
												pddOrderStock = 0;
											nufl.setPddOrderStock(pddOrderStock);
										} else if (platName.equals("Mlh")) {
											int MlhOrderStock = nufl.getMlhOrderStock();
											MlhOrderStock = MlhOrderStock-qtyy;
											if (MlhOrderStock < 0)
												MlhOrderStock = 0;
											nufl.setMlhOrderStock(MlhOrderStock);
										} else if (platName.equals("xhs")) {
											int xhsOrderStock = nufl.getXhsOrderStock();
											xhsOrderStock=xhsOrderStock-qtyy;
											if (xhsOrderStock < 0)
												xhsOrderStock = 0;
											nufl.setXhsOrderStock(xhsOrderStock);
										} else if (platName.equals("Ofashion")) {
											int ofashionOrderStock = nufl.getOfashionOrderStock();
											ofashionOrderStock = ofashionOrderStock-qtyy;
											if (ofashionOrderStock < 0)
												ofashionOrderStock = 0;
											nufl.setOfashionOrderStock(ofashionOrderStock);
										} else if (platName.equals("yinTai")) {
											int yinTaiOrderStock = nufl.getYinTaiOrderStock();
											yinTaiOrderStock = yinTaiOrderStock-qtyy;
											if (yinTaiOrderStock < 0)
												yinTaiOrderStock = 0;
											nufl.setYinTaiOrderStock(yinTaiOrderStock);
										}else if (platName.equals("shepin")) {
											int shepinOrderStock = nufl.getShepinOrderStock();
											shepinOrderStock = shepinOrderStock-qtyy;
											if (shepinOrderStock < 0)
												shepinOrderStock = 0;
											nufl.setShepinOrderStock(shepinOrderStock);
										}else if (platName.equals("OfashionMicheng")) {
											int ofashionMcOrderStock = nufl.getOfashionMcOrderStock();
											ofashionMcOrderStock =ofashionMcOrderStock-qtyy;
											if (ofashionMcOrderStock < 0)
												ofashionMcOrderStock = 0;
											nufl.setOfashionMcOrderStock(ofashionMcOrderStock);
										}else if (platName.equals("zp")) {
											int zhenpinOrderStock = nufl.getZhenpinOrderStock();
											zhenpinOrderStock = zhenpinOrderStock-qtyy;
											if (zhenpinOrderStock < 0)
												zhenpinOrderStock = 0;
											nufl.setSuningOrderStock(zhenpinOrderStock);
										}
										
										nufl.setNowStockNum(pnum);
										nufl.setSku(skuId);
										nufl.setType("sh");
										platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
				        		  }
								
							
							}
							
						}
						
					}
					
				}else{
					logMap.put("orderId", trade_id);
					/*logMap.put("skuId", skuId);
					logMap.put("sSprodid", ourSku);
					logMap.put("qty", qty+"");
					logMap.put("ptype", ptype);
					logMap.put("pushPlatform", pushPlatform);*/
					logMap.put("status", status);
					logMap.put("message", message);
					providePostOrderLogDao.insertPushOrderLog(logMap);
				}
				
		} catch (JSONException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		}
		  
		return resultmap;
	
	}
	
	
	
	//形成我们自己的sku
		public String createOurSku(String skuId){
			skuId = skuId.replace("_", "");
			String result ="222";
//	       System.out.println(skuId);
			int len = skuId.length();
			if(len>=10){
				result =result + skuId.substring(len-10);
			}else{
				int cha = 10-skuId.length();
				result = result + skuId.substring(len-cha)+skuId;
			}
			return result;
		}
		
	private String getSignList(Map<String,String> map)
	{
		String sign="";
		String secret = map.get("secret");
		List<String> keys = new ArrayList<String>(map.keySet());
	    Collections.sort(keys);

	    String str = "";
	    for (int i = 0; i < keys.size(); i++) {
	        String key = keys.get(i);
	        if (key.equals("secret") || key.equals("sign")) {
	            continue;
	        }
	        String value = map.get(key);
	        str = str + key + value;
	    }
//	    System.out.println("str===>"+str);
//	    System.out.println("secret + str + secret===>"+secret + str + secret);
	    sign = getMD5Str(secret + str + secret).toUpperCase();
//	    System.out.println("mySign===>" + sign);
		return sign;
	}
	
	public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException var5) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();

        for(int i = 0; i < byteArray.length; ++i) {
            if (Integer.toHexString(255 & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(255 & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(255 & byteArray[i]));
            }
        }

        return md5StrBuff.toString().toUpperCase();
    }
	/**
	 * xiyou
	 * @param list
	 * @return
	 */
	private static final String app_key = "shangshang";

	private static final String app_secret = "541731163403b2103f0368cf4620d626";
	private static final String api_url = "https://api.shoplinq.cn";
	public Map<String,String> pushOrder(List<ProvideOrderDetail> list) {
		
		 Map<String,String> map = new HashMap<String, String>();
		   String url = "/shangshang/v0/order/add_order?";
		   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
		   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//		    System.out.println(sign);
//		    System.out.println(api_url+url);
			map.put("app_key", app_key);
			map.put("timestamp", timestamp);
			map.put("sign", sign);
		 Map<String,Object> mapJson = new HashMap<String, Object>();
		 Map<String,String> order = new HashMap<String, String>();
		
		 Map<String,String> mapp = new HashMap<String, String>();
		 Map<String,String> resultmap = new HashMap<String, String>();
		 List<Map<String,Object>> productList = new ArrayList<>();
		 ProvideOrderDetail po = list.get(0);
		 String trade_id = po.getTradeId(); // 订单编号
   	  String username = po.getUserName();    // 用户名称
//	   	  String username = "订单测试";
	   	  String phone = po.getPhone();    // 电话
   	
		  String province = po.getProvince();  // 省
//		  System.out.println(province);
		  String city = po.getCity();            // 市
//		  System.out.println(city);
		  String region = po.getRegion();       // 区
//		  System.out.println(region);
		  String location= po.getLocation();   // 详细地址
//		  System.out.println(location);
		  String pay_price = po.getPayPrice(); // 实付金额
//		  System.out.println(pay_price);
		  String total_price = po.getTotalPrice();   // 订单总金额
		  
		  String seller_memo = po.getSellerMemo();  //卖家备注
//		  System.out.println(total_price);
//		  String createTime = po.getCreateTime();
//		  System.out.println(createTime);
//		  String payTime = po.getPayTime(); // 支付时间
//		  System.out.println(payTime);
		  String create_time = "";
		  String pay_time = "";
		  try {
			   create_time = po.getCreateTime();  // 下单时间
			   long ct = df.parse(create_time).getTime();
			   create_time = String.valueOf(ct);
			   create_time = create_time.substring(0, create_time.length() - 3);
			   
			   pay_time = po.getPayTime(); // 支付时间 ==========================先用插入时间来测试用
			   long pt =df.parse(pay_time).getTime();
			   pay_time = String.valueOf(pt);
			   pay_time = pay_time.substring(0, pay_time.length() - 3);
			} catch (ParseException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage());
			}
				  String platName = po.getSellPlatform();
				  
				  order.put("trade_id",trade_id);
				  order.put("username",username);
				  order.put("phone",phone);
				  order.put("province",province);
				  order.put("city",city);
				  
				  order.put("region",region);
				  order.put("location",location);
				  order.put("pay_price",pay_price);   //订单中所有商品的实付金额,即所有商品的供货价（cost）和
				  order.put("total_price",total_price);//订单中所有商品的总金额,即所有商品的供货价（cost）和
				  order.put("seller_memo", seller_memo);
				  
				  order.put("create_time",create_time);
				  order.put("pay_time",pay_time);
				  mapJson.put("order", order);	
			
//				  System.out.println(list.size()+"=============list.size()==========");
				  //  商品
				  for(int j=0;j<list.size();j++){
					  Map<String,Object> products = new HashMap<String, Object>();
					  long ProdId = Long.parseLong(list.get(j).getProdId()); 
					  String item_id = list.get(j).getItemId();
					  String title = list.get(j).getTitle();
					  double sell_price =Double.parseDouble(list.get(j).getSellPrice());  // 商品售价     
					  String tax = "0"; // 税费      
					  
					  int qty = list.get(j).getQty();                 // 购买数量
					  String freight = list.get(j).getFreight();         // 运费金额
					  
					  products.put("ProdId", ProdId);    //银泰西有的商品sku
					  products.put("item_id", item_id);  //我们的商品 sku
					  products.put("title", title);    
					  products.put("sell_price", sell_price); // 商品售价，即供货价
					  products.put("tax", tax);  // 税费 
					  
					  products.put("qty", qty); // 购买数量	
					  products.put("freight", freight); // 运费金额
					  productList.add(products);
				  }
//			  mapJson.put("products", products);
			   net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(productList);
	    	   mapJson.put("products", json);
	    	   
	    	   Map<String, String> logMap = new HashMap<String, String>();
	    	   String response = HttpRequest.sendPostOrderXiYou(api_url+url, map, mapJson);
//	    	   System.out.println(response);
	    	   JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(response);
				String status = jsonObj.getString("status");
				String message = jsonObj.getString("message");
				logMap.put("status", status);
				logMap.put("message", message);
				
				String jsodata = jsonObj.getString("data");
				if (status.equals("200")) {
					logMap.put("status", status);
					logMap.put("message", message);
					resultmap.put("status", status);
					resultmap.put("message", message);
					JSONObject dataObj = new JSONObject(jsodata);
					String requestData = dataObj.getString("request_data");
					JSONObject requestDataObj = new JSONObject(requestData);
					//获取到发送的商品集合
					String detailCollection = requestDataObj.getString("DetailCollection");
					JSONArray orderArray = new JSONArray(detailCollection);
					for(int k=0;k<orderArray.length();k++){
						JSONObject ob = new JSONObject(orderArray.get(k).toString());
						String orderId = ob.getString("Tid");
						String proId = ob.getString("SkuID");
						String ssProid = ob.getString("OuterItemID");
						String qty = ob.getString("Num");
						String ptype = platName;
						String pushPlatform = "xiYou";
						
						logMap.put("orderId", orderId);
						logMap.put("prodId", proId);
						logMap.put("sSprodid", ssProid);
						logMap.put("qty", qty);
						logMap.put("ptype", ptype);
						logMap.put("pushPlatform", pushPlatform);
						providePostOrderLogDao.insertPushOrderLog(logMap);
						
						//修改provide_goods_xiyou表的库存
						Map<String,String> upMap = new HashMap<String, String>();
						upMap.put("prodId", proId);
						upMap.put("sSprodid", ssProid);
						List<ProvideGoodsXiYou> provideGoods = providerGoodsUpdateDao.getProviderListByConditionWithPage(upMap);
						if(provideGoods!=null && provideGoods.size()>0){

							int num = provideGoods.get(0).getStock();
							int qtyy = Integer.parseInt(qty); //需要减少的库存数
//							upMap.put("stock", qt+"");
							int pnum = Integer.parseInt(getXiYouStock(proId)); //此时在查询，平台上 的库存，查看是否已经改变
							
//							if(pnum != num){ //代表此库存在银泰那里更改的库存，还未更新到本地，不管，只改自己的，改产品表和更新库存的表
								upMap.put("stock", num-qtyy+"");
								int t = providerGoodsUpdateDao.updateGoodsXiYouMap(upMap);
								//改hx_stock_update表的库存，和平台订单数
								if(t>0){
									mapp.put("sku", ssProid);
									mapp.put("type", "sh");
									 List<StockUpdate> sulist = platformStockUpdateDao.selectStockUpdateByMap(mapp);
									 for (StockUpdate nufl : sulist){
				                            //判断是哪个平台的的，以便减去对应的平台的库存 
											
											int orderNum = nufl.getOrderStockNum();
											     orderNum = orderNum-qtyy ;
											if (orderNum < 0)
												orderNum = 0;
											nufl.setOrderStockNum(orderNum);
                                           
											if(platName.equals("siku") || platName.equals("sikunew")){
												int sikuOrderStock = sulist.get(0).getSikuOrderStock();
												sikuOrderStock = sikuOrderStock-qtyy ;
												if (sikuOrderStock < 0)
													sikuOrderStock = 0;
												nufl.setSikuOrderStock(sikuOrderStock);
												
											} else if (platName.equals("kaola")) {
												int kaolaOrderStock = nufl.getKaolaOrderStock();
												kaolaOrderStock = kaolaOrderStock-qtyy;
												if (kaolaOrderStock < 0)
													kaolaOrderStock = 0;
												nufl.setKaolaOrderStock(kaolaOrderStock);
											}else if (platName.equals("tmall")) {
												int tmallOrderStock = nufl.getTmallOrderStock();
												tmallOrderStock = tmallOrderStock-qtyy;
												if (tmallOrderStock < 0)
													tmallOrderStock = 0;
												nufl.setTmallOrderStock(tmallOrderStock);
											} else if (platName.equals("fql")) {
												int fqlOrderStock = nufl.getFqlOrderStock();
												fqlOrderStock = fqlOrderStock-qtyy;
												if (fqlOrderStock < 0)
													fqlOrderStock = 0;
												nufl.setFqlOrderStock(fqlOrderStock);
											}else if (platName.equals("pdd") || platName.equals("pddnew")) {
												int pddOrderStock = nufl.getPddOrderStock();
												pddOrderStock = pddOrderStock-qtyy ;
												if (pddOrderStock < 0)
													pddOrderStock = 0;
												nufl.setPddOrderStock(pddOrderStock);
											} else if (platName.equals("Mlh")) {
												int MlhOrderStock = nufl.getMlhOrderStock();
												MlhOrderStock = MlhOrderStock-qtyy;
												if (MlhOrderStock < 0)
													MlhOrderStock = 0;
												nufl.setMlhOrderStock(MlhOrderStock);
											} else if (platName.equals("xhs")) {
												int xhsOrderStock = nufl.getXhsOrderStock();
												xhsOrderStock=xhsOrderStock-qtyy;
												if (xhsOrderStock < 0)
													xhsOrderStock = 0;
												nufl.setXhsOrderStock(xhsOrderStock);
											} else if (platName.equals("Ofashion")) {
												int ofashionOrderStock = nufl.getOfashionOrderStock();
												ofashionOrderStock = ofashionOrderStock-qtyy;
												if (ofashionOrderStock < 0)
													ofashionOrderStock = 0;
												nufl.setOfashionOrderStock(ofashionOrderStock);
											} else if (platName.equals("yinTai")) {
												int yinTaiOrderStock = nufl.getYinTaiOrderStock();
												yinTaiOrderStock = yinTaiOrderStock-qtyy;
												if (yinTaiOrderStock < 0)
													yinTaiOrderStock = 0;
												nufl.setYinTaiOrderStock(yinTaiOrderStock);
											}else if (platName.equals("shepin")) {
												int shepinOrderStock = nufl.getShepinOrderStock();
												shepinOrderStock = shepinOrderStock-qtyy;
												if (shepinOrderStock < 0)
													shepinOrderStock = 0;
												nufl.setShepinOrderStock(shepinOrderStock);
											}else if (platName.equals("OfashionMicheng")) {
												int ofashionMcOrderStock = nufl.getOfashionMcOrderStock();
												ofashionMcOrderStock =ofashionMcOrderStock-qtyy;
												if (ofashionMcOrderStock < 0)
													ofashionMcOrderStock = 0;
												nufl.setOfashionMcOrderStock(ofashionMcOrderStock);
											}else if (platName.equals("zp")) {
												int zhenpinOrderStock = nufl.getZhenpinOrderStock();
												zhenpinOrderStock = zhenpinOrderStock-qtyy;
												if (zhenpinOrderStock < 0)
													zhenpinOrderStock = 0;
												nufl.setSuningOrderStock(zhenpinOrderStock);
											}
											
											nufl.setNowStockNum(num-qtyy);
											nufl.setSku(ssProid);
											nufl.setType("sh");
											platformStockUpdateDao.updateStockByNotNull(nufl); // 更新库存数
					        		  }
									
								}
//							}
						}
						
					}

				}else{
					logMap.put("status", status);
					logMap.put("message", message);
					JSONObject dataObj = new JSONObject(jsodata);
					String requestData = dataObj.getString("request_data");
					JSONObject requestDataObj = new JSONObject(requestData);
					//获取到发送的商品集合
					String detailCollection = requestDataObj.getString("DetailCollection");
					JSONArray orderArray = new JSONArray(detailCollection);
					for(int k=0;k<orderArray.length();k++){
						JSONObject ob = new JSONObject(orderArray.get(k).toString());
						String orderId = ob.getString("Tid");
						String proId = ob.getString("SkuID");
						String ssProid = ob.getString("OuterItemID");
						String qty = ob.getString("Num");
						String ptype = platName;
						String pushPlatform = "xiYou";
						
						logMap.put("orderId", orderId);
						logMap.put("prodId", proId);
						logMap.put("sSprodid", ssProid);
						logMap.put("qty", qty);
						logMap.put("ptype", ptype);
						logMap.put("pushPlatform", pushPlatform);
						providePostOrderLogDao.insertPushOrderLog(logMap);
					}
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		
		  
		  
		  
		return resultmap;
	}
	@RequestMapping("/editProviderOrderDetial")
	public @ResponseBody Object updateSite(HttpServletRequest request,HttpServletResponse respon){
		
		String idSiteAndName = request.getParameter("provideOrderDetailId");
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		
		if(StringUtil.isBlank("provideOrderDetailId") || StringUtil.isEmpty(idSiteAndName)){
			  return 0;
		}
		String[] str = idSiteAndName.split(";");
		for(String s:str){
			String[] st = idSiteAndName.split("#");
				String tradeId = st[0];
			    String prodId = st[1];
			    String itemId = st[2];
			    String key = st[3];
			    String value = st[4];

			    if(StringUtil.isBlank(tradeId) || StringUtil.isEmpty(tradeId))continue;
			    
			    searchMap.put("tradeId", tradeId);
			    searchMap.put("prodId", prodId);
			    searchMap.put("itemId", itemId);
			    searchMap.put(key, value);
			    Integer upodd = provideOrderDetailService.updateProviderOrderDetaillService(searchMap);
			
		}
		
		return "ok";
	}
	public static String getSignMd5(String url, String appkey,String timestamp, String secret) {
		String sign = "";
		String params = "";
		params = url + "app_key=" + appkey + "&timestamp=" + timestamp + secret;
//		System.out.println(params);

		String MD5 = md5(params);
		sign = MD5;
		return sign;

	}
	public static String md5(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(txt.getBytes("UTF-8")); // 注意这里的编码
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest()) {
				buf.append(String.format("%02x", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
public String getYShangStock(String skuId) {
		
		String method ="yshang.wdk.trade.products.inventory";
		 TreeMap<String, String> map = new TreeMap<String, String>();
		 String stockPrice = "";
		 String nowTime = df.format(new Date());
		//公共参数
		 map.put("method", method);
		 map.put("app_key", app_key_ys);
		 map.put("secret", app_secret_ys);
		 map.put("v", "1.0"); 
		 map.put("timestamp", nowTime);
		
		//业务参数
		map.put("skuId", skuId);
	
		
		//公共参数和业务参数一起参与签名
		String signs = getSign(map);
		map.put("sign", signs);
		//獲取商品價格和庫存
		String response = HttpRequest.sendPostNoSecret(ys_url, map);
		
//		System.out.println(response);
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("success");
			JSONObject jsonbject = jsonObj.getJSONObject("data");
			JSONObject stockAndPrice = jsonbject.getJSONObject( "stockPrice");
			if (status.equals("0")) {
				if(stockAndPrice != null){
//					String skuid = stockAndPrice.getString("skuId");
					String stock = stockAndPrice.getString("quantity");
//					String price = stockAndPrice.getString("salePrice");
					stockPrice = stock;
					return stockPrice;
				}
			}else{
				return "0_0";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return response;
	
	}
	
private String getSign(Map<String,String> map)
{
	String sign = "";
	String needMD5Str = map.get("secret");
	Set<Entry<String, String>> entrySet  =  map.entrySet();
	Iterator<Entry<String,String>> it = entrySet.iterator();
	while(it.hasNext())
	{
		Entry<String,String> en = it.next();
		if(en.getKey().equals("secret") || en.getKey().equals("sign") ) continue; //密码和签名项不加入签名算法
			needMD5Str += en.getKey() + en.getValue();
	}
	needMD5Str+= map.get("secret");
	
//	System.out.println(needMD5Str);
	sign = HttpRequest.string2MD5(needMD5Str).toUpperCase();
	return sign;
}
	
	
	public String getXiYouStock(String prodIds) {
		
		String url = "/shangshang/v0/product/stock?";
		 
		String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
//		System.out.println(timestamp);
		String params = "";
		TreeMap<String,String> map = new TreeMap<>();	
			
		String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	    System.out.println(sign);
//	    System.out.println(api_url+url);
		map.put("app_key", app_key);
		map.put("timestamp", timestamp);
		map.put("sign", sign);
		params="prodIds="+prodIds; //获取一个商品的库存，也可获取多个商品的库存
		String response = HttpRequest.sendPostXiYou(api_url+url, map,params);
//		System.out.println(response);
		try {
			JSONObject jsonObj = new JSONObject(response);
			String status = jsonObj.getString("status");
			JSONObject Jsonbject = jsonObj.getJSONObject("data");
			
			if (status.equals("200")) {
				if(Jsonbject != null && Jsonbject.length()>0){
					String stock1 = Jsonbject.getString(prodIds);
//					System.out.println(stock1);
					return stock1;
				}
			}else{
				return "0";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return response;
	}
	
	//xiyou图片下载
    @RequestMapping(value = "providerDownLoadImg")
    public String downLoadImg(@ModelAttribute("provider") ProvideGoodsXiYou provide,
			Model model,ServletRequest ress,HttpServletResponse res) {
    	String path = "e:/upload/provide/xiyou/";
        emisZipUtil zipUitl = null;
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=picture" + date + ".zip");
            res.setContentType("application/octet-stream;charset=utf-8");
            String ids = provide.getIds();
       
            String[] prodIds = ids.split(",");
            ArrayList<String> prodIdList = new ArrayList<>();
            //根據prodid來查詢provide_goods_imge表，判斷是否，已經將圖片下載到本地，如果沒有下載到本地；下載到本地，直接通過本地下載
            for (int i = 0; i < prodIds.length; i++) {
            	List<ProvideGoodsImge> providerOneImg =  provideGoodsImgeService.selectOneGoodsImage(prodIds[i]);
            	if(providerOneImg !=null && providerOneImg.size()>0){
            		if(providerOneImg.get(0).getStatus().equals("Y")){
            			prodIdList.add(prodIds[i]);
            			continue;
            		}else{
            			for(int j=0;j<providerOneImg.size();j++){
            				ProvideGoodsImge pgi = new ProvideGoodsImge();
            						
            				String proid = providerOneImg.get(j).getProdId();
            				String urlList = providerOneImg.get(j).getXiyouImage();
            				String ssProid = providerOneImg.get(j).getSsProid();
            				String type = providerOneImg.get(j).getType();
            				downloadPicture(urlList,path,ssProid,type,j);
            				pgi.setProdId(proid);
            				pgi.setXiyouImage(urlList);
            				pgi.setImagePic("provide/xiyou/" + ssProid +"_"+j+".jpg");
            				pgi.setStatus("Y");
            				try {
            					provideGoodsImgeService.updateGoodsImageStatus(pgi);
							} catch (Exception e) {
								e.getMessage();
								log.error(e.getMessage());
							}
            				
            			}
            			prodIdList.add(prodIds[i]);
            		}
            		
            	}
            	
            	
			}
    
            QueryPage queryPage = new QueryPage(provide);
    		Map pramas = queryPage.getParameters();
    		pramas.put("prodIdList",prodIdList);
    		List<ProvideGoodsImge> providerImg = provideGoodsImgeService.getProviderImg(pramas);
      
    		
    		zipUitl = new emisZipUtil(outwt);
            String realPathPre = upload;
            for (ProvideGoodsImge goodsTemp : providerImg) {
                if (StringUtil.isNotBlank(goodsTemp.getImagePic())) {
                    zipUitl.put(realPathPre + Constants.FILE_SEP + goodsTemp.getImagePic());
                }
            }
            if(zipUitl.getFileCount()==0){
            	zipUitl.putNextEntry(new ZipEntry(""));
            }
            outwt.flush();
            
        } catch (IOException e) {
        	log.error(e.getMessage());
        } finally {
            if (null != zipUitl)
                ;
            try {
                zipUitl.close();
                close(outwt);
            } catch (IOException e) {
            	e.getStackTrace();
            	log.error(e.getMessage());
            }
        }
        
        return "/provider/providerlist";
    }
	
    
    
    
    //云尚图片下载
  	@RequestMapping(value = "providerDownLoadImgYShang")
      public String downLoadImgYShang(@ModelAttribute("provider") ProvideGoodsYShang provide,
  			Model model,ServletRequest ress,HttpServletResponse res) {
      	String path = "e:/upload/provide/yshang/";
          emisZipUtil zipUitl = null;
          OutputStream outwt = null;
          try {
              Date da = new Date();
              outwt = res.getOutputStream();
              SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
              String date = df.format(da);
              res.setHeader("Content-disposition", "attachment; filename=picture" + date + ".zip");
              res.setContentType("application/octet-stream;charset=utf-8");
              String ids = provide.getIds();
         
              String[] prodIds = ids.split(",");
              ArrayList<String> prodIdList = new ArrayList<>();
              //根據prodid來查詢provide_goods_imge表，判斷是否，已經將圖片下載到本地，如果沒有下載到本地；下載到本地，直接通過本地下載
              for (int i = 0; i < prodIds.length; i++) {
              	List<ProvideGoodsYShangImge> providerOneImg =  provideGoodsYShangImgeService.selectOneGoodsImageYShang(prodIds[i]); 
              	if(providerOneImg !=null && providerOneImg.size()>0){
              		if(providerOneImg.get(0).getStatus().equals("Y")){
              			prodIdList.add(prodIds[i]);
              			continue;
              		}else{
              			for(int j=0;j<providerOneImg.size();j++){
              				ProvideGoodsYShangImge pgi = new ProvideGoodsYShangImge();
              						
              				String proid = providerOneImg.get(j).getSkuId();
              				String urlList = providerOneImg.get(j).getYshangImage();
              				String ssProid = providerOneImg.get(j).getOurSku();
              				String type = providerOneImg.get(j).getType();
              				downloadPicture(urlList,path,ssProid,type,j);
              				pgi.setSkuId(proid);
              				pgi.setYshangImage(urlList);
              				pgi.setImagePic("provide/yshang/" + ssProid +"_"+j+".jpg");
            				pgi.setStatus("Y");
            				try {
            					provideGoodsYShangImgeService.updateGoodsImageStatusYShang(pgi);
							} catch (Exception e) {
								e.getMessage();
								log.error(e.getMessage());
							}
            				
            			}
            			prodIdList.add(prodIds[i]);
            		}
            		
            	}
            	
            	
			}
    
            QueryPage queryPage = new QueryPage(provide);
    		Map pramas = queryPage.getParameters();
    		pramas.put("prodIdList",prodIdList);
    		List<ProvideGoodsYShangImge> providerImg = provideGoodsYShangImgeService.getProviderImgYShang(pramas);
      
    		
    		zipUitl = new emisZipUtil(outwt);
            String realPathPre = upload;
            for (ProvideGoodsYShangImge goodsTemp : providerImg) {
                if (StringUtil.isNotBlank(goodsTemp.getImagePic())) {
                    zipUitl.put(realPathPre + Constants.FILE_SEP + goodsTemp.getImagePic());
                }
            }
            if(zipUitl.getFileCount()==0){
            	zipUitl.putNextEntry(new ZipEntry(""));
            }
            outwt.flush();
            
        } catch (IOException e) {
        	log.error(e.getMessage());
        } finally {
            if (null != zipUitl)
                ;
            try {
                zipUitl.close();
                close(outwt);
            } catch (IOException e) {
            	e.getStackTrace();
            	log.error(e.getMessage());
            }
        }
        
        return "/provider/providerlistYShang";
  	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	//西有图片分文件夹下载
	@RequestMapping(value = "providerDownLoadImgByDir")
	public String downLoadImgByDir(@ModelAttribute("provider") ProvideGoodsXiYou provide,
			Model model,ServletRequest ress,HttpServletResponse res) {
    	String path = "e:/upload/provide/xiyou/";
        emisZipUtil zipUitl = null;
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=picture" + date + ".zip");
            res.setContentType("application/octet-stream;charset=utf-8");
            String ids = provide.getIds();
       
            String[] prodIds = ids.split(",");
            ArrayList<String> prodIdList = new ArrayList<>();
            //根據prodid來查詢provide_goods_imge表，判斷是否，已經將图片下載到本地，如果沒有下載到本地；下載到本地，直接通過本地下載
            for (int i = 0; i < prodIds.length; i++) {
            	List<ProvideGoodsImge> providerOneImg =  provideGoodsImgeService.selectOneGoodsImage(prodIds[i]);
            	if(providerOneImg !=null && providerOneImg.size()>0){
            		if(providerOneImg.get(0).getStatus().equals("Y")){
            			prodIdList.add(prodIds[i]);
            			continue;
            		}else{
            			for(int j=0;j<providerOneImg.size();j++){
            				ProvideGoodsImge pgi = new ProvideGoodsImge();
            						
            				String proid = providerOneImg.get(j).getProdId();
            				String urlList = providerOneImg.get(j).getXiyouImage();
            				String ssProid = providerOneImg.get(j).getSsProid();
            				String type = providerOneImg.get(j).getType();
            				downloadPicture(urlList,path,ssProid,type,j);
            				pgi.setProdId(proid);
            				pgi.setXiyouImage(urlList);
            				pgi.setImagePic("provide/xiyou/" + ssProid +"_"+j+".jpg");
            				pgi.setStatus("Y");
            				try {
            					provideGoodsImgeService.updateGoodsImageStatus(pgi);
							} catch (Exception e) {
								e.getMessage();
								log.error(e.getMessage());
							}
            				
            			}
            			prodIdList.add(prodIds[i]);
            		}
            		
            	}
			}
            zipUitl = new emisZipUtil(outwt);
            String realPathPre = upload;
    		Map<String,List<String>> map = new HashMap<String, List<String>>();
    		List<String> listSspd = new ArrayList<String>();
    		for(String prod:prodIdList){
    			List<String> listImage = new ArrayList<String>();
    			List<ProvideGoodsImge> listImages = provideGoodsImgeService.selectOneGoodsImage(prod);
    			String ssProid = "";
    			for (ProvideGoodsImge goodsTemp : listImages) {
        			 ssProid = goodsTemp.getSsProid();
        			String imagePic = goodsTemp.getImagePic();
        			listImage.add(realPathPre + Constants.FILE_SEP +imagePic);
        		}
    			map.put(ssProid, listImage);
    			listSspd.add(ssProid);
    		}
    		
    		Set<Entry<String, List<String>>> set = map.entrySet();
			Iterator<Entry<String, List<String>>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, List<String>> entry = it.next();
				String ssProd = entry.getKey();
				List<String> imgList = entry.getValue();
				File fileDir = new File("e:/upload/provide/pic/"+ssProd);  //("d:/upload/provide/pic/1111356558108/1111356558108_0.jpg")
				if(!fileDir.exists()){
					fileDir.mkdir();
				}
				for (String img : imgList) {
					String beImg = img; //原文件名（"d:/upload/provide/xiyou/1111356558108_0.jpg"）
					String now = "e:/upload/provide/pic/"+ssProd; //新建文件夹（"d:/upload/provide/pic/1111356558108"）
					String imageName = img.substring(img.lastIndexOf("/")+1); //(图片全名称1111356558108_0.jpg)
					 //在原文件夹中是否有这个图片("d:/upload\provide/xiyou/1111356558108_0.jpg" (系统找不到指定的文件。))
					 File fbeImg = new File(img); 
					 if(!fbeImg.exists()){
					    	continue; 
					    }
					copyFile(beImg,now,imageName);
				}
			}
			for(int j=0;j<listSspd.size();j++){
				zipUitl.put(realPathPre + Constants.FILE_SEP + "provide/pic/"+listSspd.get(j));
			}     
			 /*//删除文件夹的方法
			  * if (file.isFile() && file.exists()) {  
            file.delete();  
                } */

            if(zipUitl.getFileCount()==0){
            	zipUitl.putNextEntry(new ZipEntry(""));
            }
            outwt.flush();
            
        } catch (IOException e) {
        	log.error(e.getMessage());
        } finally {
            if (null != zipUitl)
                ;
            try {
                zipUitl.close();
                close(outwt);
            } catch (IOException e) {
            	e.getStackTrace();
            	log.error(e.getMessage());
            }
        }
        
        return "/provider/providerlist";
    }
	
	
	
	
	
	//云尚图片分文件夹下载
		@RequestMapping(value = "providerDownLoadImgByDirYShang")
		public String downLoadImgByDirYShang(@ModelAttribute("provider") ProvideGoodsYShang provide,
				Model model,ServletRequest ress,HttpServletResponse res) {
	    	String path = "e:/upload/provide/yshang/";
	        emisZipUtil zipUitl = null;
	        OutputStream outwt = null;
	        try {
	            Date da = new Date();
	            outwt = res.getOutputStream();
	            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	            String date = df.format(da);
	            res.setHeader("Content-disposition", "attachment; filename=picture" + date + ".zip");
	            res.setContentType("application/octet-stream;charset=utf-8");
	            String ids = provide.getIds();
	       
	            String[] prodIds = ids.split(",");
	            ArrayList<String> prodIdList = new ArrayList<>();
	            //根據prodid來查詢provide_goods_YShang_imge表，判斷是否，已經將图片下載到本地，如果沒有下載到本地；下載到本地，直接通過本地下載
	            for (int i = 0; i < prodIds.length; i++) {
	            	List<ProvideGoodsYShangImge> providerOneImg =  provideGoodsYShangImgeService.selectOneGoodsImageYShang(prodIds[i]);
	            	if(providerOneImg !=null && providerOneImg.size()>0){
	            		if(providerOneImg.get(0).getStatus().equals("Y")){
	            			prodIdList.add(prodIds[i]);
	            			continue;
	            		}else{
	            			for(int j=0;j<providerOneImg.size();j++){
	            				ProvideGoodsYShangImge pgi = new ProvideGoodsYShangImge();
	            						
	            				String proid = providerOneImg.get(j).getSkuId();
	            				String urlList = providerOneImg.get(j).getYshangImage();
	            				String ssProid = providerOneImg.get(j).getOurSku();
	            				String type = providerOneImg.get(j).getType();
	            				downloadPicture(urlList,path,ssProid,type,j);
	            				pgi.setSkuId(proid);
	            				pgi.setYshangImage(urlList);
	            				pgi.setImagePic("provide/yshang/" + ssProid +"_"+j+".jpg");
	            				pgi.setStatus("Y");
	            				try {
	            					provideGoodsYShangImgeService.updateGoodsImageStatusYShang(pgi);
								} catch (Exception e) {
									e.getMessage();
									log.error(e.getMessage());
								}
	            				
	            			}
	            			prodIdList.add(prodIds[i]);
	            		}
	            		
	            	}
				}
	            zipUitl = new emisZipUtil(outwt);
	            String realPathPre = upload;
	    		Map<String,List<String>> map = new HashMap<String, List<String>>();
	    		List<String> listSspd = new ArrayList<String>();
	    		for(String prod:prodIdList){
	    			List<String> listImage = new ArrayList<String>();
	    			List<ProvideGoodsYShangImge> listImages = provideGoodsYShangImgeService.selectOneGoodsImageYShang(prod);
	    			String ssProid = "";
	    			for (ProvideGoodsYShangImge goodsTemp : listImages) {
	        			 ssProid = goodsTemp.getOurSku();
	        			String imagePic = goodsTemp.getImagePic();
	        			listImage.add(realPathPre + Constants.FILE_SEP +imagePic);
	        		}
	    			map.put(ssProid, listImage);
	    			listSspd.add(ssProid);
	    		}
	    		
	    		Set<Entry<String, List<String>>> set = map.entrySet();
				Iterator<Entry<String, List<String>>> it = set.iterator();
				while (it.hasNext()) {
					Entry<String, List<String>> entry = it.next();
					String ssProd = entry.getKey();
					List<String> imgList = entry.getValue();
					File fileDir = new File("e:/upload/provide/yshangpic/"+ssProd);  //("d:/upload/provide/pic/1111356558108/1111356558108_0.jpg")
					if(!fileDir.exists()){
						fileDir.mkdir();
					}
					for (String img : imgList) {
						String beImg = img; //原文件名（"d:/upload/provide/xiyou/1111356558108_0.jpg"）
						String now = "e:/upload/provide/yshangpic/"+ssProd; //新建文件夹（"d:/upload/provide/pic/1111356558108"）
						String imageName = img.substring(img.lastIndexOf("/")+1); //(图片全名称1111356558108_0.jpg)
						 //在原文件夹中是否有这个图片("d:/upload\provide/xiyou/1111356558108_0.jpg" (系统找不到指定的文件。))
						 File fbeImg = new File(img); 
						 if(!fbeImg.exists()){
						    	continue; 
						    }
						copyFile(beImg,now,imageName);
					}
				}
				for(int j=0;j<listSspd.size();j++){
					zipUitl.put(realPathPre + Constants.FILE_SEP + "provide/yshangpic/"+listSspd.get(j));
				}     
				 /*//删除文件夹的方法
				  * if (file.isFile() && file.exists()) {  
	            file.delete();  
	                } */

	            if(zipUitl.getFileCount()==0){
	            	zipUitl.putNextEntry(new ZipEntry(""));
	            }
	            outwt.flush();
	            
	        } catch (IOException e) {
	        	log.error(e.getMessage());
	        } finally {
	            if (null != zipUitl)
	                ;
	            try {
	                zipUitl.close();
	                close(outwt);
	            } catch (IOException e) {
	            	e.getStackTrace();
	            	log.error(e.getMessage());
	            }
	        }
	        
	        return "/provider/providerlistYShang";
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 public static void copyFile(String srcPath, String destPath,String imageName) throws IOException {
	        
		    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(srcPath));
			 
            FileOutputStream fileOutputStream = new FileOutputStream(new File(destPath+"/"+imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            byte[] buffer = new byte[1024];
            int length;
 
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
		    
		 /* // 打开输入流
		    FileInputStream fis = new FileInputStream(srcPath);
		    FileOutputStream fos = new FileOutputStream(destPath+"/"+imageName);
		    BufferedInputStream bufferedIn=new BufferedInputStream(fis);  
            BufferedOutputStream bufferedOut=new BufferedOutputStream(fos);
	        // 读取和写入信息
            byte[] by=new byte[1];
            while (bufferedIn.read(by)!=-1) {  
                bufferedOut.write(by);  
            }  
            //将缓冲区中的数据全部写出  
            bufferedOut.flush();  
            bufferedIn.close();  
            bufferedOut.close();  */
	        // 关闭流  先开后关  后开先关 
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
    
    
	private void close(OutputStream outwt) {
		try {
            if (outwt != null) {
            	outwt.close();
            }
        } catch (IOException ioe) {
            //ignore
        }
		
	}
	
	
	
	
	//下载图片到本地
		 private  void downloadPicture(String urlList,String path,String ssProid,String type,int t) {
//			 if(type.equals("1")){
//				 path = path + ssProid+"_"+t+urlList.substring(urlList.lastIndexOf("."));
//			 }else{
				 path = path + ssProid +"_"+t+".jpg";
//			 }
		        URL url = null;
		        try {
		            url = new URL(urlList);
		            DataInputStream dataInputStream = new DataInputStream(url.openStream());
		 
		            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
		            ByteArrayOutputStream output = new ByteArrayOutputStream();
		 
		            byte[] buffer = new byte[1024];
		            int length;
		 
		            while ((length = dataInputStream.read(buffer)) > 0) {
		                output.write(buffer, 0, length);
		            }
		            fileOutputStream.write(output.toByteArray());
		            dataInputStream.close();
		            fileOutputStream.close();
		        } catch (MalformedURLException e) {
		            e.printStackTrace();
		            log.error(e.getMessage());
		        } catch (IOException e) {
		            e.printStackTrace();
		            log.error(e.getMessage());
		        }
		    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public void getOrerDetails(){
//		getOrerDetail("P547396177186366765");
//	}
	// ====================通过订单号获取商品订单详情============================
	   public void getOrerDetail(String orderno){
		   Map<String,String> map = new HashMap<String, String>();
		   String url = "/shangshang/v0/order/order_detail?";
		   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
		   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//		    System.out.println(sign);
//		    System.out.println(api_url+url);
			map.put("app_key", app_key);
			map.put("timestamp", timestamp);
			map.put("sign", sign);
			 orderno="orderno="+orderno; //获取二个商品的详细信息
			String response = HttpRequest.sendPostXiYou(api_url+url, map,orderno);
//			System.out.println(response);
			Map<String,String> detailmap = new HashMap<String, String>();
			Map<String,String> onemap = new HashMap<String, String>();
			try {
				JSONObject jsonObject = new JSONObject(response);
				String jsondata = jsonObject.getString("data");
				String statu = jsonObject.getString("status");
				if(statu.equals("200")){
					JSONObject jsonO= new JSONObject(jsondata);
					String channelSonumber = jsonO.getString("ChannelSonumber");  //订单号					
					String orderId = channelSonumber.substring(0, channelSonumber.lastIndexOf("-"));;
					String buyName = jsonO.getString("ShippingContactWith"); // 买家名称 
					String phone = jsonO.getString("ShippingPhone");     // 手机号 
					String address = jsonO.getString("ShippingAddress");  //买家地址
					String amount = jsonO.getString("ProductAmount");  //商品售价
					String paidAmount = jsonO.getString("PaidAmount");  // 支付金额
					
					String shipViaId = jsonO.getString("ShipViaId");  //承运方编号shipname
					String shipname = getShipName(shipViaId); //快递公司
					String wayBillCode = jsonO.getString("WayBillCode");  //运单号
					String orderStatus = jsonO.getString("SOStatus");  //订单状态
					String status = getShipStatus(orderStatus);
					String sellPlatform = getPlat(orderId); //获取哪个平台卖出的
					
					detailmap.put("orderId", orderId);
					detailmap.put("orderstatus", status);
					detailmap.put("buyname", buyName);
					detailmap.put("phone", phone);
					detailmap.put("address", address);
					detailmap.put("amount", amount);
					detailmap.put("paidamount", paidAmount);
					detailmap.put("shipname", shipname);
					detailmap.put("waybillcode", wayBillCode); 
					detailmap.put("sellPlatform", sellPlatform);
					detailmap.put("pushPlatform", "xiyou");
//insert into provide_order_waybill_detail(id,orderId,orderstatus,buyname,phone,address,amount,paidamount,shipname,waybillcode,insert_time,update_time,sellPlatform,pushPlatform) values(null,#orderId#,#orderstatus#,#buyname#,#phone#,#address#,#amount#,#paidamount#,#shipname#,#waybillcode#,sysdate(),sysdate(),#sellPlatform#,#pushPlatform#)
					onemap.put("orderId", orderId);
					List<ProvideOrderWaybillDetail> provideOrderWaybillDetail = provideOrderWaybillDetailDao.selectWayBill(onemap);
					if(provideOrderWaybillDetail!=null && provideOrderWaybillDetail.size()>0){
						provideOrderWaybillDetailDao.updateOrderWaybillDetail(detailmap);
					}else{
						try {
							provideOrderWaybillDetailDao.insertWayBillDetail(detailmap);
						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getMessage());
						}
						
					}
					
				}
				

			} catch (JSONException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			
	   }
	   /**
	    * @param shipViaId
	    * @return
	    * 获取快递公司的名称
	    */
	   private String getShipName(String shipViaId) {
	   	 Map<String,String> map = new HashMap<String, String>();
	   	   String url = "/shangshang/v0/order/shipvia?";
	   	   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
	   	   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//	   	    System.out.println(sign);
//	   	    System.out.println(api_url+url);
	   		map.put("app_key", app_key);
	   		map.put("timestamp", timestamp);
	   		map.put("sign", sign);
	   		String orderno="";
	   		String response = HttpRequest.sendPostXiYou(api_url+url, map,orderno);
//	   		System.out.println(response);
	   		String viadName = "";
	   		try {
	   			JSONObject json = new JSONObject(response);
	   			String status  = json.getString("status");
	   			if(status.equals("200")){
	   			String data = json.getString("data");
	   			JSONArray arrayData = new JSONArray(data);
	   			for(int i=0;i<arrayData.length();i++){
	   				JSONObject jsonString = new JSONObject(arrayData.getString(i).toString());
	   				String viad = jsonString.getString("ShipViaId"); //快递代表号码
	   				String name = jsonString.getString("ShipViaName"); //快递代表公司
	   				
	   				if(viad.equals(shipViaId)){
	   					String[] na = name.split("-");
	   					 viadName =na[1]; 
	   					return viadName;
	   				}
	   				
	   			}
	   			}
	   		} catch (JSONException e) {
	   			e.printStackTrace();
				log.error(e.getMessage());
	   		}
	   		
	   	return "NO";
	   }
		
		/**
		 * @param orderStatus
		 * @return
		 * 获取订单的状态
		 * 订单状态：
		  -100 ：订单取消；
		  -10：订单缺货；
		  0：等待处理；
		  10：全额付款；
		  15：调拨在途；
		  20：审核通过；
		  30：配货中；
		  40：已出库；
		  100：已发货；
		  120：已交接；
		  200：已送达；
		  201：未送达；
		  202：用户拒收；
		  300：交易成功；
		  getShipStatusYshang
		  301：交易失败；
		
		 */
		private String getShipStatus(String orderStatus) {
			String status ="";
			if(orderStatus.equals("-100")){
				status="订单取消";
			}else if(orderStatus.equals("-10")){
				status="订单缺货";
			}else if(orderStatus.equals("0")){
				status="等待处理";
			}else if(orderStatus.equals("10")){
				status="全额付款";
			}else if(orderStatus.equals("15")){
				status="调拨在途";
			}else if(orderStatus.equals("20")){
				status="审核通过";
			}else if(orderStatus.equals("30")){
				status="配货中";
			}else if(orderStatus.equals("40")){
				status="已出库";
			}else if(orderStatus.equals("100")){
				status="已发货";
			}else if(orderStatus.equals("120")){
				status="已交接";
			}else if(orderStatus.equals("200")){
				status="已送达";
			}else if(orderStatus.equals("201")){
				status="未送达";
			}else if(orderStatus.equals("202")){
				status="用户拒收";
			}else if(orderStatus.equals("300")){
				status="交易成功";
			}else if(orderStatus.equals("301")){
				status="交易失败";
			}
			return status;
		}
	/**
	* @param orderId
	* @return
	*/
	private String getPlat(String orderId) {
		String platName = "";
		Map<String,String> onemap = new HashMap<String, String>();
		onemap.put("tradeId", orderId);
		List<ProvideOrderDetail> one = provideOrderDetailDao.selectOrderDetailByMap(onemap);
		String ourOrderId = one.get(0).getOriginalTradeId();
		PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
		platformorderdetails.setIdorder(ourOrderId);
	  List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
	  if(orderDetais!=null && orderDetais.size()>0){
	  	 platName = orderDetais.get(0).getPtype();  	
	  }
		return platName;
	}
//	====================通过订单号获取商品订单详情============================
	
	//取消订单
	@RequestMapping("/cancelOrder")
    public @ResponseBody Object cancelOrder(HttpServletRequest request,HttpServletResponse respons){
		String orderNo = request.getParameter("orderId");
		
		   Map<String,String> map = new HashMap<String, String>();
		   Map<String,String> remap = new HashMap<String, String>();
		   
		   if(orderNo.startsWith("YS")){
			   remap = getCancelResYShang(orderNo);
			   return remap;
		   }
		   
		   
		   
		   String url = "/shangshang/v0/order/refund_order?";
		   String timestamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10); 
		   String sign = getSignMd5(url, app_key, timestamp, app_secret);
//		    System.out.println(sign);
//		    System.out.println(api_url+url);
			map.put("app_key", app_key);
			map.put("timestamp", timestamp);
			map.put("sign", sign);
			String orderno="orderno="+orderNo; //获取二个商品的详细信息
			String response = HttpRequest.sendPostXiYou(api_url+url, map,orderno);
//			System.out.println(response);
//			String response = "{\"status\":200,\"message\":\"推送成功\",\"data\":\"\"}"; 
			try {
				JSONObject jsonObject = new JSONObject(response);
				String status = jsonObject.getString("status");
				String message = jsonObject.getString("message");
				if(status.equals("200")){
					Map<String,String> detailmap = new HashMap<String, String>();
					detailmap.put("cancelstatus", "订单取消-"+message);
					detailmap.put("orderId", orderNo);
					provideOrderWaybillDetailDao.updateOrderWaybillDetail(detailmap);
					remap.put("code", status);
					remap.put("message", message);
					return remap;
				}else{
					remap.put("code", status);
					remap.put("message", message);
					return remap;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return null;
	   }
	
	
	/**
 * @param orderNo
 * @return
 */
			private Map<String, String> getCancelResYShang(String orderNo) {
				
				Map<String,String> resultmap = new HashMap<String, String>();
				
				String method ="yshang.wdk.trade.order.cancel";
				TreeMap<String,String> map = new TreeMap<String, String>();
				
				
				String nowTime = df.format(new Date());
				//系统需要参数
					map.put("method", method);
				map.put("timestamp", nowTime);//调用方法
				map.put("app_key", app_key_ys);
				map.put("v", "1.0");
				map.put("secret", app_secret_ys);
				
				 JSONObject jsonObject = new JSONObject();
				    try {
						jsonObject.put("outOrderId",orderNo);
					} catch (JSONException e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				
				map.put("data", jsonObject.toString());
				String sign = getSign(map);
				map.put("sign", sign);
				String response = HttpRequest.sendPostNoSecret(ys_url, map);
			//{"data":"取消成功","errorCode":"","errorMessage":"","success":0}
				JSONObject jsonO;
				try {
					jsonO = new JSONObject(response);
					String jsondata = jsonO.getString("data");
					String statu = jsonO.getString("errorCode");
					String success = jsonO.getString("success");
					String errorMessage = jsonO.getString("errorMessage");
					
//					remap.put("code", status);
//					remap.put("message", message);
					//resultmap
					List<String> listr = new ArrayList<String>();
					Map<String,String> maps = new HashMap<String, String>();
					Map<String,String> detailmap = new HashMap<String, String>();
					if(success.endsWith("0")){
						
						resultmap.put("code", "200");
						resultmap.put("message", jsondata);
						
						detailmap.put("orderId", orderNo);
						detailmap.put("message", jsondata);
						provideOrderWaybillDetailDao.updateOrderWaybillDetail(resultmap);
						/*String str = "";
						String skuIDs = "";
						List<ProvideOrderDetail> pd = provideOrderDetailDao.selectProviderOrderId(orderNo);
						for(int i=0;i<pd.size();i++){
							String skuID = pd.get(i).getProdId()+"";
							String ourSku = pd.get(i).getItemId();
							str += skuID+","+ourSku;
							skuIDs =","+skuID;
//							listr.add(str);
							maps.put(skuID, ourSku);
						}
						
						skuIDs = skuIDs.substring(1);
						System.out.println(skuIDs);
						Map updateMap = new HashMap();
						YShangPlatFormStockUpdateImpl ys = new YShangPlatFormStockUpdateImpl();
						List<ProvideGoodsYShang> skuIdLIst = ys.getYShangBatchStockPrice(skuIDs);
						TreeMap<String,Integer> tm = new TreeMap<String, Integer>();
						
						if(skuIdLIst!=null && skuIdLIst.size()>0){
							for(ProvideGoodsYShang pgSku:skuIdLIst){
								String skuid = pgSku.getSkuId();
								Integer stockS= pgSku.getStock();
								String ousku= maps.get(skuid);
								 StockUpdate su = new StockUpdate();
			        				su.setSku(ousku);
			        				su.setNowStockNum(stockS);
			        				su.setYshangSkuId(skuid);
			        				su.setType("sh");
								platformStockUpdateDao.updateNowStockBySku(su);
							}
						}
							if(skuIdLIst!=null && skuIdLIst.size()>0){
								updateMap.put("list", skuIdLIst);
								provideGoodsYShangUpdateDao.updateGoodsYShangByList(updateMap);
							}
*/
					}else{
						resultmap.put("code", "200");
						resultmap.put("message", errorMessage);
						detailmap.put("orderId", orderNo);
						detailmap.put("message", errorMessage);
						provideOrderWaybillDetailDao.updateOrderWaybillDetail(resultmap);
					}
	
					
					
				} catch (JSONException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
				
				
				
				
//				System.out.println(response);
				
				return resultmap;
			}
			
     //禁用中
	//定时任务全量获取银泰西有所有商品，固定时间点在每个星期五晚间11点執行，配置在web-main.xml中
	public void getAllProducts(){
		stockUpdateService.setCanUpdateStockStatus("false"); 
		xiYouPlatFormStockUpdate.getProducts();
		xiYouPlatFormStockUpdate.getChangeStockTwo(); //防止在获取商品期间,库存有问题,获取两个小时内的有变化的库存
		stockUpdateService.setCanUpdateStockStatus("true");
	}
	
	//禁用中
	//获取银泰西有cost变化的商品,更新cost、our_sale_price，配置在web-main.xml中
	public void getProductCostChange(){

		xiYouPlatFormStockUpdate.getProductCostChange();

	}
 
	
	//禁用中
	//自动更新云尚的商品和库存
	public void getAllYShangGoods(){
//		stockUpdateService.setCanUpdateStockStatus("false"); 
//		yShangPlatFormStockUpdate.getYShangProducts();
//		yShangPlatFormStockUpdate.updateYShangStockPrice();
//		stockUpdateService.setCanUpdateStockStatus("true");
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
     * 导出银泰西有商品数据
     */
    @RequestMapping(value = "exportProvideExcel")
    public String exportProvideExcel(Model model, HttpServletRequest request, HttpServletResponse res, 
    		@ModelAttribute("provider") ProvideGoodsXiYou provide) {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=provide_goods" + date + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");
            List<ProvideGoodsXiYou> goodsList = new ArrayList<ProvideGoodsXiYou>();
            
            List<Long> list = new ArrayList<Long>();
            String ids = provide.getIds();
            if(StringUtils.isNotBlank(ids)){
            	String[] prodIds = ids.split(",");
                for(int i=0;i<prodIds.length;i++){
                	list.add(Long.valueOf(prodIds[i]));
                }
            }
   
            goodsList = providerService.getOneProviderGoodsList(provide,list);          
            //如果是全量导出
           
            
            List<String[]> goodsExportList = new ArrayList<String[]>();
//            System.out.println(goodsList.size());
            String[] title = { "银泰西有sku","尚上sku","品牌名", "商品名","税率","尚上售价","市场价","供货价","尺码","颜色", "商品分类树","库存","图片" };
            goodsExportList.add(title);
            
//        if(provide.getProdId() !=null || StringUtils.isNotBlank(provide.getSsProdid()+"") || list.size()>0){}
            if(goodsList!=null){
            	for(ProvideGoodsXiYou tmp:goodsList){
//            		if(StringUtils.isNotBlank(tmp.getImageAddresses())){
            		String[] data = {
            				tmp.getProdId().toString()+ "", tmp.getSsProdid()+ "", tmp.getBrandName()+ "",
       				         tmp.getProdName()+ "", tmp.getTaxRate().toString()+ "", tmp.getOurSalePrice().toString()+ "",
       				         tmp.getMarketPrice().toString()+ "", tmp.getCost().toString()+ "", tmp.getSize()+ "", 
       				         tmp.getColor()+ "",tmp.getDetailDescription()+ "",tmp.getStock().toString()+ "", 
			        		 tmp.getImageAddresses()+ ""};
                	goodsExportList.add(data);
//            		}
            	}
            }
        //有条件导出，带图片，全量导出不带图片
//            if(provide.getProdId() !=null || StringUtils.isNotBlank(provide.getSsProdid()) || StringUtils.isNotBlank(provide.getBrandName()) || provide.getStock() !=null || StringUtils.isNotBlank(provide.getIds())){
           if(StringUtils.isNotBlank(provide.getIds())){	
            	goodsBatch.exportExcelWidthPic(outwt, goodsExportList);
            }else{
            	goodsBatch.exportExcelWidthNoPic(outwt, goodsExportList);
            }
            
            outwt.flush();
        } catch (Exception e) {
            model.addAttribute("message", "商品导出失败");
            log.error(e);
            log.error(e.getMessage());
        } finally {
            close(outwt);
        }
        return "/provider/providerlist";
    }
	
    
    
    
    
    
    
    
    
    /**
     * 导出云尚的商品数据
     */
    @RequestMapping(value = "exportProvideExcelYShang")
    public String exportProvideExcelYShang(Model model, HttpServletRequest request, HttpServletResponse res, 
    		@ModelAttribute("provider") ProvideGoodsYShang provide) {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=provide_goods" + date + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");
            List<ProvideGoodsYShang> goodsList = new ArrayList<ProvideGoodsYShang>();
            
            List<String> list = new ArrayList<String>();
            String ids = provide.getIds();
            if(StringUtils.isNotBlank(ids)){
            	String[] skuIds = ids.split(",");
                for(int i=0;i<skuIds.length;i++){
                	String d = skuIds[i];
                	list.add(skuIds[i]);
                }
            }
   
            goodsList = providerService.getOneProviderGoodsListYShang(provide,list);          
            //如果是全量导出
           
            
            List<String[]> goodsExportList = new ArrayList<String[]>();
//            System.out.println(goodsList.size());
            String[] title = { "云尚sku","尚上sku","品牌名", "商品名","市场价","尚上售价","供货价","库存","尺码","颜色", "商品类目","图片" };
            goodsExportList.add(title);
            
//        if(provide.getProdId() !=null || StringUtils.isNotBlank(provide.getSsProdid()+"") || list.size()>0){}
            if(goodsList!=null){
            	for(ProvideGoodsYShang tmp:goodsList){
//            		if(StringUtils.isNotBlank(tmp.getImageAddresses())){
            		String[] data = {
            				 tmp.getSkuId().toString()+ "", tmp.getOurSku()+ "", tmp.getBrandChname()+ "",
       				         tmp.getProductName()+ "", tmp.getMarketPrice().toString()+ "",
       				         tmp.getOurPrice().toString()+ "", tmp.getSettlePrice().toString()+ "", 
       				         tmp.getStock().toString()+ "", tmp.getSize()+ "", 
       				         tmp.getColor()+ "",tmp.getCategoryName()+ "",
			        		 tmp.getMainImg()+ ""};
                	goodsExportList.add(data);
//            		}
            	}
            }
        //有条件导出，带图片，全量导出不带图片
//            if(provide.getProdId() !=null || StringUtils.isNotBlank(provide.getSsProdid()) || StringUtils.isNotBlank(provide.getBrandName()) || provide.getStock() !=null || StringUtils.isNotBlank(provide.getIds())){
           if(StringUtils.isNotBlank(provide.getIds())){	
            	goodsBatch.exportExcelWidthPic(outwt, goodsExportList);
            }else{
            	goodsBatch.exportExcelWidthNoPic(outwt, goodsExportList);
            }
            
            outwt.flush();
        } catch (Exception e) {
            model.addAttribute("message", "商品导出失败");
            log.error(e);
            log.error(e.getMessage());
        } finally {
            close(outwt);
        }
        return "/provider/providerlistYShang";
    }
	
    
    
    
    
    
    
    
    //手动添加一个订单
    @RequestMapping("/toAddOrder")
	public String toAddCustomer(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
		model.addAttribute("admin", adminAgent);
		return "/provider/addOneOrder";
	}
    
    
    @RequestMapping("/addOrderByHand")
   	public @ResponseBody String addOrderByHand(HttpServletRequest request,AdminAgent adminAgent,Model model)
   	{
    	Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
    	
		 MiniUiUtil.trimAndConvSpeSqlStr(requestMap, false, true, true);
		 String orderId = requestMap.get("tradeId");
		 PlatFormOrderDetails platformorderdetails = new PlatFormOrderDetails();
			platformorderdetails.setIdorder(orderId);
		  List<PlatFormOrderDetails> orderDetais = platformOrderDetailsDao.selectList(platformorderdetails);
		  Map<String,String> searchmap = new HashMap<>();
		  if(orderDetais!=null && orderDetais.size()>0){
		    	 //将是这个订单号的商品详情获取到,放入orderIdProList
			    List<ProvideOrderDetail> orderIdProList = getProvideOrderDetaisByOrderId(orderDetais); 
			    List<ProvideOrderDetail> orderList = new ArrayList<ProvideOrderDetail>();
//			    System.out.println(orderIdProList.size()+"==============");
			    if(orderIdProList!=null && orderIdProList.size()>0){ 
			        for(int i=0;i<orderIdProList.size();i++){
			        	searchmap.put("tradeId", orderIdProList.get(i).getTradeId());
			        	searchmap.put("itemId", orderIdProList.get(i).getItemId());
			        	List<ProvideOrderDetail> list = provideOrderDetailDao.selectOrderDetailByMap(searchmap);
//			        	System.out.println(list.size());
			        	//判断是否插入过
			        	if(list == null || list.size()==0){
			        		orderList.add(orderIdProList.get(i));
			        	}else{
			        		return "has";
			        	}
			      }
			        if(orderList.size()>0){ 
//			        	provideOrderDetailDao.insertOrderDetailList(orderList);
 
			        	
			        	provideOrderDetailDao.insertOrderDetailList(orderList);
			        	
                       //修改total_price,和payPrice的价格
			        	List<String> list = new ArrayList<String>();
			        	for(int t=0;t<orderList.size();t++){
			        		list.add(orderList.get(t).getTradeId());
			        	}

			        	HashSet h = new HashSet(list);   
			    	    list.clear();   
			    	    list.addAll(h); 
			    	    for(int j=0;j<list.size();j++){
			    	    	String tradeId = list.get(j);
			    	    	List<ProvideOrderDetail>  orderDetailList  = provideOrderDetailDao.selectProviderOrderId(tradeId);
			    	    	Double totalprice = 0.00;
			    	    	for(int k=0;k<orderDetailList.size();k++){
			    	    		totalprice = totalprice+ Double.valueOf(orderDetailList.get(k).getTotalPrice());
			    	    	}
			    	    	Map<String,String> searchMap = new HashMap<String, String>();
			    	    	searchMap.put("tradeId",  tradeId+"");
			    	    	searchMap.put("totalPrice", totalprice+"");
			    	    	searchMap.put("payPrice", totalprice+"");
			    	    	provideOrderDetailDao.updateProviderOrderDetailDao(searchMap);
			    	    }
			        
			        }
			    	
			    }else{
			    	return "no"; //hx_stock_update_platform_orderdetails中没有我们的sku，或平台的sku，或hx_stock_update中xiyou_prod_id是null,平台sku是null
			    }
		    }else{
		    	return "non"; //hx_stock_update_platform_orderdetails中没有这个订单号的订单
		    }
   		return "ok";
   	}
    //从hx_stock_update_platform_orderdetails表中获取，自动获取详情没有获取到的
    private List<ProvideOrderDetail> getProvideOrderDetaisByOrderId(List<PlatFormOrderDetails> orderDetais) {
		List<ProvideOrderDetail> lis = new ArrayList<ProvideOrderDetail>();
		
		Map<String,String> searchMap = new HashMap<String, String>();
		for(int i=0;i<orderDetais.size();i++){
			 String sku = orderDetais.get(i).getMerchantSkuId(); //我们自己的sku
			 String platSku = orderDetais.get(i).getSkuId();
			 String pty = orderDetais.get(i).getPtype();   //查詢出哪個平台
			 String platName = getPlatName(pty);
			 Map<String,String> mapSku = new HashMap<String, String>();
			 if(StringUtils.isBlank(sku) && StringUtils.isNotBlank(platSku)){
				 if(StringUtils.isNotBlank(platName)){
					 mapSku.put(platName, platSku);
					 mapSku.put("skuisnotnull", "xiyou_prod_id");
					 List<StockUpdate> listSku = platformStockUpdateDao.selectStockUpdateByMap(mapSku);
					 if(listSku!=null && listSku.size()>0){
						 sku = listSku.get(0).getSku();
					 }else{
						 continue;
					 }
				 }else{
					 continue;
				 }
				 
				 
			 }
			 if(StringUtils.isBlank(sku) && StringUtils.isBlank(platSku)){
				 continue;
			 }
			 if(sku.startsWith("111")){
			   String proid = sku.substring(3, 10);
			   searchMap.put("sku", sku);
			   searchMap.put("xiyouProdId", proid);
			   List<StockUpdate> list = platformStockUpdateDao.selectStockUpdateByMap(searchMap); //查询供应商表是否有这个商品，并确定是哪个商家的
			   //如果能查询出商品，则是供应商的商品，因为xiyouProdId可判断出是西有银泰的
			   if(list !=null && list.size()>0){
				   //把订单id放进集合
				   ProvideOrderDetail pod = new ProvideOrderDetail();
				   String originalTradeId = orderDetais.get(i).getIdorder(); //原有的订单编号
				   String newTradeId = getNewTradeId(originalTradeId);//通过原有的订单编号，获取新的订单编号，也是传给西有的订单编号
				   pod.setOriginalTradeId(originalTradeId);
				   pod.setTradeId(newTradeId);
				   pod.setUserName(orderDetais.get(i).getName());
				   pod.setPhone(orderDetais.get(i).getMobile());
				   pod.setProvince(orderDetais.get(i).getProvince());
				   
				   pod.setCity(orderDetais.get(i).getCity());
				   pod.setRegion(orderDetais.get(i).getDistrict());
				   pod.setLocation(orderDetais.get(i).getStreetAddress());
//				   pod.setTotalPrice(orderDetais.get(i).getTotalPrice());
//				   pod.setPayPrice(orderDetais.get(i).getPayPrice());
				   
				   
//				   pod.setInsertTime(orderDetais.get(i).getInsertTime()); //插入當前這個表的時間
				   pod.setCreateTime(orderDetais.get(i).getPalcedTime()); //商品下單時間
				   pod.setPayTime(orderDetais.get(i).getPayTime());    //插入另一張訂單詳情表的時間
				   
				   pod.setProdId(proid);
				   pod.setItemId(sku);
				   
				   pod.setTitle(orderDetais.get(i).getProductname());
				   
				   ProvideGoodsXiYou pg = new ProvideGoodsXiYou();
		   		   pg.setProdId(Long.parseLong(proid));
		   		   List<ProvideGoodsXiYou> pgxy = providerGoodsUpdateDao.selectAllProid(pg);
		   		   if(pgxy!=null && pgxy.size()>0){
		   			   Double d = pgxy.get(0).getCost();
		   			int q = Integer.parseInt(orderDetais.get(i).getQuantity());
		   			pod.setSellPrice(d+"");
		   			pod.setPayPrice(d*q+"");
		   			pod.setTotalPrice(d*q+"");
		   			
		   		   }else{
		   			pod.setSellPrice(0+"");
		   			pod.setPayPrice(0+"");
		   			pod.setTotalPrice(0+"");
		   		   }
		   		   pod.setOurSellPrice(orderDetais.get(i).getPayPrice());
				   pod.setTax("0");
				   pod.setQty(Integer.parseInt(orderDetais.get(i).getQuantity()));
				   pod.setFreight(orderDetais.get(i).getFreight());
				   pod.setSellPlatform(orderDetais.get(i).getPtype());
				   pod.setProvider("xiyou");
				   lis.add(pod);
			   }  
		}
		}
		return lis;
	}
    
    /**
	 * @param originalTradeId
	 * @return
	 */
	private String getNewTradeId(String originalTradeId) {
		Pattern pp = Pattern.compile("[^0-9]");
		Matcher m = pp.matcher(originalTradeId);
		String result = m.replaceAll("");
		String pd="XY111";
//		System.out.println(result.length());
		if(result.length()>8){
			pd = pd+result.substring(3,8)+result.substring(0, 5);
		}else if(result.length()>5 && result.length()<=8){
			
			pd = pd+result.substring(2,7)+result.substring(1, 6);
		}else{
			pd = pd+"08543"+result;
		}
		return pd;
	}
    
    
    /**
	 * @param pty
	 * @return
	 */
	private String getPlatName(String platName) {
		if(platName.equals("siku")){
			return "sikuSku";
		}else if(platName.equals("sikunew")) {
			return "sikunewSku";
		} else if(platName.equals("kaola")) {
			return "kaolaSku";
		}else if (platName.equals("tmall")) {
			return "tmallSku";
		} else if (platName.equals("fql")) {
			return "fqlSku";
		}else if (platName.equals("pdd")) {
			return "pddSku";
		} else if (platName.equals("pddnew")) {
			return "pddnewSku";
		}else if (platName.equals("Mlh")) {
			return "MlhSku";
		} else if (platName.equals("xhs")) {
			return "xhsSku";
		} else if (platName.equals("Ofashion")) {
			return "ofashionsku";
		}else if (platName.equals("yinTai")) {
			return "yinTaiSku";
		}else if (platName.equals("shepin")) {
			return "shepinSku";
		}else if(platName.equals("zp")){
			return "zhenpinSku";
		}
		return null;
	}
	@RequestMapping("/providerSaleInfo")
	public String  getproviderSaleInfo(Model model,
			HttpServletRequest request,HttpServletResponse respons){
		model.addAttribute("id", request.getParameter("id"));
		return "/provider/providexiyoucostlog";
		
	}
	
	   @RequestMapping("/providerSalelogInfolist")
	   public @ResponseBody Object providerSaleInfo (AdminAgent adminAgent,HttpServletRequest request){
		   Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
			MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
			return xiYouPlatFormStockUpdate.providerSaleInfo(searchMap);
			 
	   }
	   
	   
	   
	   //云尚
	   @RequestMapping("/providerYShangInfoLog")
		public String  providerYShangInfoLog(Model model,
				HttpServletRequest request,HttpServletResponse respons){
			model.addAttribute("id", request.getParameter("id"));
			return "/provider/provideYShangUpdateLog";
			
		}
	   
	   
	   @RequestMapping("/providerYShanglogInfolist")
	   public @ResponseBody Object providerYShanglogInfolist (AdminAgent adminAgent,HttpServletRequest request){
		   Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
			MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
			return provideGoodsYShangLogService.providerYShanglogInfolist(searchMap);

	   }
	   
	   
	   
	   
	   
//	  
	    @RequestMapping("/exportProdOrderDetalis")
		public void exportProductyuliu(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent) throws Exception{
			Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
			/*MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);*/
			String[] titleArr = new String[]{"平台订单号","推送订单号","银泰ID","sku","商品供货价","商品售价","数量","插入时间","下单时间","支付时间","商品名称","运费","售卖的平台","供应商平台","收件人","联系电话","省","市","区","详细地址"};
			
			//provideOrderDetailService.getProvideOrderDetail(searchmap);
			searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
			/*String sku = searchMap.get("sku");
			String order_stock = searchMap.get("order_stock");
			if(StringUtil.isBlank(sku)){
				searchMap.remove("sku");
				
			}
			if(StringUtil.isBlank(order_stock)){
				searchMap.remove("order_stock");	
			}*/
			
//			int cnt = platformStockUpdateDao.searchStockUpdateCnt(searchMap);  //{order_stock=, sku=}
			
			int count = provideOrderDetailDao.selectOrderDetailCount(searchMap);
			if(count > 30000)
			{
				return;
			}
			
			/*searchMap.put("userName", adminAgent.getUsername());*/
			
			
			MiniUiGrid miniuiGrid = provideOrderDetailService.getProvideOrderDetail(searchMap);
			int totalCnt = miniuiGrid.getTotal();
			List<ProvideOrderDetail> productList = (List<ProvideOrderDetail>)miniuiGrid.getData();//获取需要导出的数据
	 
			String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
			resultArr[0] = titleArr;
			for(int  i = 0; i < resultArr.length-1; i++)
			{
				String[] rowArr = resultArr[i+1]; //第一行为title  不需要在设置
				ProvideOrderDetail pod = productList.get(i);
//				 "","","","","","插入时间","下单时间",
//				"支付时间","商品名称","运费","售卖的平台","供应商平台","收件人","联系电话","省","市","区","详细地址"};	
				
				rowArr[0] = pod.getOriginalTradeId(); //平台订单号
	
				rowArr[1] = pod.getTradeId();      //推送订单号
				rowArr[2] = pod.getProdId()+""; //银泰ID
				rowArr[3] = pod.getItemId()+"";//sku
				rowArr[4] = pod.getSellPrice()+"";	//商品供货价
				rowArr[5] = pod.getOurSellPrice()+"";	//商品售价
				rowArr[6] = pod.getQty()+"";  //"数量"
				rowArr[7] = pod.getInsertTime(); //,"插入时间"",
				rowArr[8] = pod.getCreateTime()+"";//"下单时间"
				rowArr[9] = pod.getPayTime()+"";//支付时间
				rowArr[10] = pod.getTitle()+"";//商品名称
				rowArr[11] = pod.getFreight()+"";//"运费"
				
				String py = getPlatNameS(pod.getSellPlatform()+"");
				rowArr[12] = py;//"售卖的平台"
				
				if((pod.getProvider()+"").equals("xiyou")){
					rowArr[13] = "银泰西有";//"供应商平台",
				}
				rowArr[14] = pod.getUserName()+"";//"收件人",
				rowArr[15] = pod.getPhone()+""; //"联系电话",
				rowArr[16] = pod.getProvince()+"";//"省",
				rowArr[17] = pod.getCity()+"";//"市",
				rowArr[18] = pod.getRegion()+"";//"区",
				rowArr[19] = pod.getLocation()+"";//"详细地址",
			
		
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
	        response.setHeader("Content-disposition", "attachment;filename=ProdOrderDetail_"+sdf1.format(new Date())+".xls");
	        OutputStream ouputStream = response.getOutputStream();    
	        exportWb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close(); 
				
			}
	    private String getPlatNameS(String platName) {
			if(platName.equals("siku")){
				return "寺库";
			}else if(platName.equals("sikunew")) {
				return "新寺库";
			} else if(platName.equals("kaola")) {
				return "考拉";
			}else if (platName.equals("tmall")) {
				return "天猫";
			}else if (platName.equals("pdd")) {
				return "拼多多";
			} else if (platName.equals("pddnew")) {
				return "新拼多多";
			}else if (platName.equals("Mlh")) {
				return "魅力惠";
			} else if (platName.equals("xhs")) {
				return "小红书";
			} else if (platName.equals("Ofashion")) {
				return "Ofashion";
			}else if (platName.equals("yinTai")) {
				return "银泰";
			}else if (platName.equals("shepin")) {
				return "天猫奢品";
			}else if(platName.equals("zp")){
				return "珍品";
			}
			return null;
		}

	    
	    
	    private static final String CHARSET_UTF8 = "UTF-8";

	    public static String post(String url, Map<String, String> params, String charset,ResponseHandler<String> handler) throws Exception{
	        if (url == null || org.apache.commons.lang.StringUtils.isEmpty(url)) {
	            return null;
	        }
	        // ����HttpClientʵ��
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        UrlEncodedFormEntity formEntity = null;
	        try {
	            if (charset == null || StringUtils.isEmpty(charset)) {
	                formEntity = new UrlEncodedFormEntity(getParamsList(params),CHARSET_UTF8);
	            } else {
	                formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
	            }
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	            throw new UnsupportedEncodingException("��֧�ֵı��뼯");
	        }
	        HttpPost hp = new HttpPost(url);
	        hp.setEntity(formEntity);

	       
	        // �������󣬵õ���Ӧ
	        String responseStr = null;
	        try {
	            responseStr = httpclient.execute(hp, handler);
	        } catch (ClientProtocolException e) {
	            throw new ClientProtocolException("�ͻ�������Э�����", e);
	        } catch (IOException e) {
	            throw new IOException("IO�����쳣", e);
	        } finally {
	            abortConnection(hp, httpclient);
	        }
	        return responseStr;
	    }

	    private static List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
	        if (paramsMap == null || paramsMap.size() == 0) {
	            return Collections.emptyList();
	        }
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        for (Map.Entry<String, String> map : paramsMap.entrySet()) {
	            params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
	        }
	        return params;
	    }

	    private static void abortConnection(final HttpRequestBase hrb, final HttpClient httpclient){
	        if (hrb != null) {
	            hrb.abort();
	        }
	        if (httpclient != null) {
	            httpclient.getConnectionManager().shutdown();
	        }
	    }

	    public static String post(String url,final String params)throws Exception {
	        return post(url, new HashMap<String, String>(){
	            /**
	             *
	             */
	            private static final long serialVersionUID = 1L;

	            {
	                for(String param:params.split("&")){
	                    String[] kv = param.split("=");
	                    if(kv.length>1){
	                        put(kv[0], kv[1]);
	                    }
	                }
	            }
	        }, null);
	    }

	  
	  
	  
	    public static String post(String url, Map<String, String> params, String charset) throws Exception{
	        return post(url, params,charset,responseHandler);
	    }
	    private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	        // ????????????
	        public String handleResponse(HttpResponse response)    throws ClientProtocolException, IOException {
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	                @SuppressWarnings("deprecation")
	                String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8 : EntityUtils.getContentCharSet(entity);
	                return new String(EntityUtils.toByteArray(entity), charset);
	            } else {
	                return null;
	            }
	        }
	    };
}
