package com.huaixuan.network.web.action.order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.service.order.OrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;

@Controller
@RequestMapping("/orderinformation")
public class OrderinformationAction {
	@Autowired
	private OrderService orderService;

 

	@RequestMapping("/getorderlistByid")
	public @ResponseBody
	Orderdetails getOrderlist(HttpServletRequest request) {
		String idorder = request.getParameter("idorder");
		Orderdetails orderdetails = orderService.getorderlistByid(idorder);
		return orderdetails;
	}
	@RequestMapping("/getProductByidorder")
	public @ResponseBody
	Object getProductByidorder(HttpServletRequest request){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		MiniUiGrid m= orderService.getProductByidorder(searchMap);
		return m;
	}
 
	 
	
}
