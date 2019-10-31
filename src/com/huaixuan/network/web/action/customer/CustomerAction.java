package com.huaixuan.network.web.action.customer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.service.customer.CustomerService;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2015-12-8 下午12:16:04
 **/

@RequestMapping("/customer")
@Controller
public class CustomerAction
{
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RegionManager regionManager;
	
	@RequestMapping("/searchCustomer")
	@ResponseBody
	public MiniUiGrid searchCustomer(AdminAgent adminAgent,HttpServletRequest request)
	{
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		searchMap.put("userName", adminAgent.getUsername());
		MiniUiGrid gird = customerService.searchCustomer(searchMap);
		return gird;
	}
	
	@RequestMapping("/toSearchCustomer")
	public String toSearchCustomer(HttpServletRequest request)
	{
		return "/customer/search";
	}
	
	
	@RequestMapping("/toSearchCustomerMain")
	public String toSearchCustomerMain(HttpServletRequest request)
	{
		return "/customer/search_main";
	}
	
	
	@RequestMapping("/toUpdateetCustomer")
	public String getCustomer(HttpServletRequest request)
	{
		return "/customer/search_main";
	}
	@RequestMapping("/getCustomerByidCustomer")
	public @ResponseBody
	Customer getCustomerByidCustomer(HttpServletRequest request){
		Long id;
		String idCustomer = request.getParameter("idCustomer");
		id = Long.parseLong(idCustomer);
		Customer customer = customerService.getCustomerById(id);
		return customer;
	}
	
	@RequestMapping("/getOrderbyidCustomer")
	public @ResponseBody
	Object getOrderByidCustomer(HttpServletRequest request){
		String idCustomer = request.getParameter("idCustomer");
		Map<String,String> map = new HashMap<String, String>();
		map.put("idCustomer", idCustomer+"");
		MiniUiGrid m = customerService.getOrderByidCustomer(map);
		return "/customer/Customerdetails";
		
	}
	
	
	@RequestMapping("/toAddCustomer")
	public String toAddCustomer(HttpServletRequest request,AdminAgent adminAgent,Model model)
	{
		model.addAttribute("admin", adminAgent);
		return "/customer/insertCustomer";
	}
	
	@RequestMapping("/addCustomer")
	public @ResponseBody String addCustomer(HttpServletRequest request,AdminAgent adminAgent)
	{
		 Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		 MiniUiUtil.trimAndConvSpeSqlStr(requestMap, false, true, true);
		 requestMap.put("idCustomer", "-1");
		 Customer customer = (Customer)MiniUiUtil.Map2Object(requestMap, Customer.class);
		 customer.setPassword("123456");
		 if(StringUtil.isBlank(customer.getManagerId()))
		 {
			 customer.setManagerId(adminAgent.getUsername());
		 }
		 int cnt = customerService.inserCustomer(customer);
		 if(cnt == 1)
		 {
			 return "ok";
		 }
		 else  if(cnt == 2)
		 {
			 return "phone_exists";
		 }
		 else  if(cnt == 3)
		 {
			 return "email_exists";
		 }
		 
		 else return "error";
	}
	
	
	@RequestMapping("/updateCustomer")
	public @ResponseBody String updateCustomer(HttpServletRequest request,AdminAgent adminAgent)
	{
		 Map<String,String> requestMap = MiniUiUtil.getParameterMap(request);
		 MiniUiUtil.trimAndConvSpeSqlStr(requestMap, false, true, true);
		 Customer customer = (Customer)MiniUiUtil.Map2Object(requestMap, Customer.class);
		 customerService.updateCustomer(customer);
		 return "ok";
	}
	
	
	
	@RequestMapping("/getRegion")
	public @ResponseBody Object getRegion()
	{
		List<Region> list = regionManager.getRegionByType(2);
		return list;
	}
	
	
	@RequestMapping("/toShowCustomerOrder")
	public String toShowCustomerOrder(HttpServletRequest request)
	{
		return "/customer/customerOrder";
	}
	
}
 
