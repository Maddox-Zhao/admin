package com.huaixuan.network.biz.service.customer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.customer.CustomerDao;
import com.huaixuan.network.biz.dao.order.OrderDao;
import com.huaixuan.network.biz.domain.customer.Customer;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.customer.CustomerService;
import com.huaixuan.network.biz.service.user.UserService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2015-12-8 下午12:11:55
 **/

@Service
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserService userService;

	
	@Override
	public MiniUiGrid searchCustomer(Map<String, String> searchMap)
	{
	 		
		QueryPage queryPage = new QueryPage(new Object());
		
		String userName = searchMap.get("userName");
		boolean flag = orderDao.canSearchAllOrder(userName);//能否查询所有的客户
		if(flag)
		{
			searchMap.put("notSearchAll", "true");
		}
		int count = customerDao.searchCustomerCount(searchMap);
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItem(count);
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageLastItem()+"");
			List<Customer> list =  customerDao.searchCustomer(searchMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}
	
 

	@Override
	public Customer getCustomerById(Long idCustomer)
	{
		return customerDao.getCustomerById(idCustomer);
	}

	@Override
	@Transactional
	public int inserCustomer(Customer customer)
	{
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("phone", customer.getPhone());
		int cnt = customerDao.searchCustomerCount(searchMap);
		if(cnt > 0)
		{
			return 2;
		}
		if(StringUtil.isNotBlank(customer.getEmail()))
		{
			Map<String,String> searchMap1 = new HashMap<String, String>();
			searchMap1.put("email", customer.getEmail());
			cnt = customerDao.searchCustomerCount(searchMap1);
			if(cnt > 0)
				return 3;
		}

		Long idCustomer = customerDao.inserCustomer(customer);
		User user = new User();
		user.setAccount(customer.getPhone());
		user.setPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");
		if(StringUtil.isNotBlank(customer.getEmail()))
		{
			user.setEmail(customer.getEmail());
		}
		if(customer.getType() == 1)
		{
			user.setType("p");
		}
		else if(customer.getType() == 2)
		{
			user.setType("p");
		}
		else if(customer.getType() == 3)
		{
			user.setType("d");
		}
		user.setStauts(1);
		user.setIsValidated(1);
		user.setIdCustomer(idCustomer);
		userService.saveUser(user);
		return 1;

		
		
	}

	@Override
	public void updateCustomer(Customer customer)
	{
		 customerDao.updateCustomer(customer);
	}

 
	@Override
	public MiniUiGrid getOrderByidCustomer(Map<String, String> map) {
		MiniUiGrid gird = new MiniUiGrid();
		
		List<Orderdetails> list = customerDao.getOrderByidCustomer(map);
		if(list != null && list.size() >0)
		{	
			gird.setData(list);
			gird.setTotal(list.size());
		}
	return gird;
	}

 
}
 
