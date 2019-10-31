package com.huaixuan.network.web.action.purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.purchase.Purchase;
import com.huaixuan.network.biz.domain.purchase.PurchaseProduct;
import com.huaixuan.network.biz.domain.purchase.Purchaselifecycle;
import com.huaixuan.network.biz.domain.shop.Payaccount;
import com.huaixuan.network.biz.service.purchase.BaseDataService;
import com.huaixuan.network.biz.service.purchase.PurchaseLifecyleService;
import com.huaixuan.network.biz.service.purchase.PurchaseOrderService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.biz.service.shop.PayaccountService;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;


/**

 *2012-7-5  05:02:55

 *2012-7-5 下午05:02:55

 *Mr_Yang
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseAction extends BaseAction
{
	@Autowired
	private BaseDataService baseDataService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private PayaccountService payaccountService;
	@Autowired
	private PurchaseLifecyleService purchaseLifecyleService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	/*
	//分页查询单款产品基本数据和综合数据

	@RequestMapping("/find")
	public String find(@ModelAttribute("baseData")BaseData baseData,@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,Model model)
	{
		QueryPage query = baseDataService.getBaseDataByMap(baseData, currPage, pageSize);
		model.addAttribute("query", query);
		model.addAttribute("brandMap", EnumBrandType.toMap());
		model.addAttribute("seriesMap", EnumSeriesType.toMap());
		return "/purchase/find";
	}
	



	//转跳到查询页面

	@RequestMapping("/findInput")
	public String findInput(Model model)
	{
		BaseData bs = new BaseData();
		
		List<Brand> brandList = brandService.getBrands();
		Map<String, String> seriesMap = EnumSeriesType.toMap();
		model.addAttribute("brandList", brandList);
		model.addAttribute("baseData", bs);
		model.addAttribute("seriesMap", seriesMap);
		return "/purchase/find";
	}
	

 

	//尚上价有过改变的入库记录

	@RequestMapping("/hkPriceChange")
	public String hkPriceChange(Model model,@RequestParam("goodsId")Long goodsId)
	{
		Map map = baseDataService.getNotSameHKPrice(goodsId);
		model.addAttribute("changeMap", map);
		return "/purchase/goodsPriceChange";
	}
	
 

	//欧洲价有过改变的入库记录

	@RequestMapping("/euPriceChange")
	public String euPriceChange(Model model,@RequestParam("goodsId")Long goodsId)
	{
		Map map = baseDataService.getNotSameEUPrice(goodsId);
		model.addAttribute("changeMap", map);
		return "/purchase/goodsPriceChange";
	}

	@RequestMapping("/size/getAllSize")
	public @ResponseBody Object getAllSize(HttpServletRequest request)
	{
		return brandService.getAllSize();
	}

	*/
	
	
	//分页查询单款产品基本数据和综合数据
	@RequestMapping("/tolist")
	public String toList()
	{
		return "/purchase/list";
	}
	@RequestMapping("/list")
	public @ResponseBody Object list(HttpServletRequest request)
	{
		 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		 return purchaseOrderService.queryPurchaseOrder(searchMap);
	}
	
	
	@RequestMapping("/toEditPurchaseOrder")
	public String  toEditPurchaseOrder(HttpServletRequest request)
	{
		 
		 return  "/purchase/purchaseOrderDetail";
	}
	/**
	 * 获取付款银行
	 */
	@RequestMapping("/bank")
	public @ResponseBody Object getBankbyBankName(HttpServletRequest request){
		return payaccountService.getBankbyBankName();
		
	}
	/**
	 * 获取货代公司信息
	 */
	@RequestMapping("/company")
	public @ResponseBody Object getCompany(HttpServletRequest request){
		return payaccountService.getCompany();
		
	}
	/**
	 * 产品入库
	 * @param request
	 * @param adminAgent
	 * @return
	 */
	@RequestMapping("/purchaseProductInStock")
	@AdminAccess
	public @ResponseBody Object  purchaseProductInStock(HttpServletRequest request,AdminAgent adminAgent)
	{
		 Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		 //采购单号 和 站点必填
		 if(StringUtil.isBlank(requestMap.get("idPurchaseLifeCycle")) ||  StringUtil.isBlank(requestMap.get("idLocation"))) return "error";
		 
		 requestMap.put("idLastOperator", adminAgent.getUsername());
		 int totalNum  = purchaseOrderService.purchaseProductInStock(requestMap);
		 return totalNum-1;
		 
	}

	
	/**
	 * 查询采购单对应的产品
	 * @param request
	 * @param adminAgent
	 * @return
	 */
	@RequestMapping("/getPurchaseProductByIdPurchse")
	@AdminAccess
	public @ResponseBody Object  getPurchaseProductByIdPurchse(HttpServletRequest request,AdminAgent adminAgent)
	{
		 Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		 //采购单号 和 站点必填
		 if(StringUtil.isBlank(searchMap.get("idPurchaseLifeCycle"))) return "error";
		 
		 return  purchaseOrderService.getPurchaseProduct(searchMap);
		 
		 
	}
	
	/**
	 * 更新采购订单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePurchaseLifecyle")
	public @ResponseBody Object updatePurchaseLifecyle(HttpServletRequest request)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		Purchaselifecycle purchaselifecycle = (Purchaselifecycle)MiniUiUtil.Map2Object(requestMap, Purchaselifecycle.class);
		purchaseLifecyleService.updatePurchaseLifecyleByNotNull(purchaselifecycle);
		return "ok";
	}
	/**
	 * 确认采购订单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmPurchaseLifecyle")
	public @ResponseBody Object confirmPurchaseLifecyle(HttpServletRequest request){
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		Purchaselifecycle purchaselifecycle = (Purchaselifecycle) MiniUiUtil.Map2Object(requestMap, Purchaselifecycle.class);
		purchaseLifecyleService.updatePurchaseLifecyleByNotNull(purchaselifecycle);
		return "ok";
		
	}
	
	@RequestMapping("/updatesubTotal")
	@AdminAccess
	public @ResponseBody
	String PurchaseProductLists(HttpServletRequest request,
			@ModelAttribute("purchase")Purchase purchase){
		Double subtotal =Double.valueOf(request.getParameter("subTotal"));
		int idPurchaseLifeCycle =Integer.parseInt(request.getParameter("idPurchaseLifeCycle"));
		int idPurchase = (int) purchaseLifecyleService.getidPurchase(idPurchaseLifeCycle);
		purchase.setSubtotal(subtotal);
		purchase.setIdpurchase((long) idPurchase);
		purchaseLifecyleService.updatePurchase(purchase);
		return "OK";
	}

}
