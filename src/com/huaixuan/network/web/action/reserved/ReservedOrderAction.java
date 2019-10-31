package com.huaixuan.network.web.action.reserved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.reserved.ReservedOrderProduct;
import com.huaixuan.network.biz.service.reserved.ReservedOrderService;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.interceptor.AdminAccess;



/**
 * @author Mr_Yang   2016-9-6 下午01:23:18
 * 预开单
 **/

@Controller
@RequestMapping(value ="/reservedOrder")
public class ReservedOrderAction
{

	@Autowired
	private ReservedOrderService reservedOrderService;
	
	//查询可售产品
	@RequestMapping("/list")
	@AdminAccess
	public String getList(AdminAgent adminAgent,HttpServletRequest request,Model model) {
		model.addAttribute("site",adminAgent.getSiteId());	
		model.addAttribute("dutyids", adminAgent.getDutyIds());
		return "/reservedOrder/list";
	}
	
	//显示购物车
	@RequestMapping("/toShowShoppingCar")
	@AdminAccess
	public String  toShowShoppingCar(HttpServletRequest request,HttpServletResponse respons)
	{
		return "/reservedOrder/shoppingCar";
	}
	
	//结算
	@RequestMapping("/settleAccount")
	@AdminAccess
	@ResponseBody
	public String  settleAccount(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		requestMap.put("operator", adminAgent.getUsername());

		return reservedOrderService.reservedShoppingCar2SettleAccount(requestMap,adminAgent)+"";
	}

	
	
	//显示订单
	@RequestMapping("/toShowOrderList")
	@AdminAccess
	public String  toShowOrderList(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		return "/reservedOrder/orderList";
	}
	
	//显示订单json
	@RequestMapping("/showOrderList")
	@AdminAccess
	@ResponseBody
	public Object  showOrderList(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		searchMap.put("createUserId", adminAgent.getUsername());
		return reservedOrderService.searchReservedOrder(searchMap);
	}
	
	//显示订单总金额
	@RequestMapping("/showOrderListPrice")
	@AdminAccess
	@ResponseBody
	public Object  showOrderListPrice(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		searchMap.put("createUserId", adminAgent.getUsername());
		return reservedOrderService.searchReservedListPrice(searchMap);
	}
	
	
	
	
	/******预开单产品信息*/
	
	//显示订单详情
	@RequestMapping("/toShowOrderDetail")
	@AdminAccess
	public String  toShowOrderDetail(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		return "/reservedOrder/orderDetail";
	}
	
	
	@RequestMapping("/showOrderDetail")
	@AdminAccess
	@ResponseBody
	public Object  showOrderDetail(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent)
	{
		Map<String,String> searchMap =  MiniUiUtil.getParameterMap(request);
		
		return  reservedOrderService.searchReservedOrderProduct(searchMap);
	}
	
	/**
	 * 打印订单产品 发货需要
	 * @param request
	 * @param respons
	 * @param adminAgent
	 * @param model
	 * @return
	 */
	@RequestMapping("/orderPrint")
	@AdminAccess
	public String  orderPrint(HttpServletRequest request,HttpServletResponse respons,AdminAgent adminAgent, Model model)
	{
		Map<String,String> searchMap =  MiniUiUtil.getParameterMap(request);
		List<ReservedOrderProduct> listResult = new ArrayList<ReservedOrderProduct>();
		Map<String,ReservedOrderProduct> map = new HashMap<String, ReservedOrderProduct>();
		List<ReservedOrderProduct> list = reservedOrderService.searchReservedOrderProduct(searchMap);
		for(ReservedOrderProduct r : list)
		{
			ReservedOrderProduct g = map.get(r.getSku());
			if(g == null)
			{
				r.setSsPrice(1D);
				listResult.add(r);
				map.put(r.getSku(), r);
			}
			else
			{
				g.setSsPrice(g.getSsPrice()+1D);
			}
		}
		
		
		model.addAttribute("list", listResult);
		return "/reservedorder/orderProductPrint";
	}
	
	/**
	 * 仓库通过sku拿到的产品和预开单的订单做匹配
	 * @param resuest
	 * @param respnose
	 * @return
	 */
	@RequestMapping("/setRealyIdProduct")
	@ResponseBody
	@AdminAccess
	public Object setRealyIdProduct(HttpServletRequest request,HttpServletResponse respnose)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		return reservedOrderService.setRealyIdProduct(requestMap);
	}
	
	
	/**
	 * 修改真实idProduct
	 * @param resuest
	 * @param respnose
	 * @return
	 */
	@RequestMapping("/updateRealyIdProduct")
	@ResponseBody
	@AdminAccess
	public Object updateRealyIdProduct(HttpServletRequest request,HttpServletResponse respnose)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		return reservedOrderService.updateRealyIdProduct(requestMap);
	}
	
	/**
	 * 真实idProduct设置完毕,生成真实订单
	 * @param resuest
	 * @param respnose
	 * @return
	 */
	@RequestMapping("/preOrdertoRealyOrder")
	@ResponseBody
	@AdminAccess
	public Object preOrdertoRealyOrder(HttpServletRequest request,HttpServletResponse respnose,AdminAgent adminAgent)
	{
		Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		requestMap.put("operator", adminAgent.getUsername());//操作人
		
		return reservedOrderService.preOrdertoRealyOrder(requestMap,adminAgent);
	}

}
 
