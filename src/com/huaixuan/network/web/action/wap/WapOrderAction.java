package com.huaixuan.network.web.action.wap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.MiniUiBase;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.base.MiniuiBaseService;
import com.huaixuan.network.biz.service.base.impl.MiniuiBaseServiceImpl;
import com.huaixuan.network.biz.service.order.OrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;



/**
 * @author Mr_Yang   2015-12-16 下午04:44:03
 **/

@Controller
@RequestMapping("/m/order")
public class WapOrderAction
{
	@Autowired
	private OrderService orderService;
	@Autowired
	private MiniuiBaseService miniuiBaseService;
	
	@RequestMapping("/list")
	public String querWapOrder(AdminAgent adminAgent,HttpServletRequest request,Model model,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int page
			){
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		requestMap.put("userName", adminAgent.getUsername());
		if(MiniUiUtil.hkAllSite.contains(adminAgent.getSiteId()+""))
		{
			//如果是香港账号只查询香港的订单
			requestMap.put("hkOperator", "yes");
		}
		if(MiniUiUtil.hqgAllSite.contains(adminAgent.getSiteId()+"")){
			//如果是环球港账号只查看环球港站点订单
			requestMap.put("hqgOperator", "yes");
		}
		QueryPage queryPage= orderService.getOrderList(requestMap);
		List<Orderdetails> orderList = (List<Orderdetails>)queryPage.getItems();
		List<MiniUiBase> sellchannelList = miniuiBaseService.getAllSellChannel();
		List<MiniUiBase> siteList = miniuiBaseService.getSiteByType("1");
		
		String nextPage = MiniUiUtil.getNextPageParamParams(queryPage);
		String previousPage = MiniUiUtil.getPreviousPageParams(queryPage);
		
		model.addAttribute("idSellChannel", requestMap.get("idSellChannel"));
		model.addAttribute("idSite", requestMap.get("idSite"));
		model.addAttribute("status", requestMap.get("status"));
		model.addAttribute("sellDateEnd", requestMap.get("sellDateEnd"));
		model.addAttribute("page",page);
		model.addAttribute("siteList", siteList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("sellchannelList", sellchannelList);
		model.addAttribute("queryPage",queryPage);
		
		
		model.addAttribute("nextPage",nextPage);
		model.addAttribute("previousPage",previousPage);
		
		return "/web/web_orderlist";
	}
	
	@RequestMapping("/orderlist")
	public @ResponseBody
	Object orderlist(HttpServletRequest request
			){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		return orderService.getOrderList(searchMap);
	}
	
	@RequestMapping("/getOrderByidorder")
	public String getOrderbyidorder(HttpServletRequest request,Model model){
		String idorder = request.getParameter("idorder");
		Orderdetails orderdetails = orderService.getorderlistByid(idorder);
		List<MiniUiBase>  paymentList = miniuiBaseService.getAllPayment();
		List<MiniUiBase>  sellChannelList = miniuiBaseService.getAllSellChannel();
		
		model.addAttribute("orderdetails", orderdetails);
		model.addAttribute("paymentList", paymentList);
		model.addAttribute("sellChannelList", sellChannelList);
		return "/web/web_orderdetails";
	} 
	
	
	@RequestMapping("/getProductByidorder")
	public String getProduct(HttpServletRequest request,Model model){
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		MiniUiGrid gird = orderService.getProductByidorder(requestMap);
		List<Product> productList = gird.getData();
		model.addAttribute("productList", productList);
		return "/web/orderproduct";
	}
}
 
