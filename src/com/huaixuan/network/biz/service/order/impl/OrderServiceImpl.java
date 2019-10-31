package com.huaixuan.network.biz.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.order.OrderDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.order.OrderService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.hundsun.common.lang.StringUtil;


@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Override
	public QueryPage getOrderList(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(searchMap);
		String userName = searchMap.get("userName");
		boolean flag = orderDao.canSearchAllOrder(userName);//能否查询所有的订单
		if(flag)
		{
			searchMap.put("searchAll", "true");
		}
		int count = orderDao.getOrderListCount(searchMap);
		MiniUiGrid grid = new MiniUiGrid();
		grid.setTotal(count);
		queryPage.setTotalItem(count);
		if(count >0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItem(count);
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageLastItem()+"");
			List<Orderdetails> list = orderDao.getorderList(searchMap);
			if(list != null && list.size() >0){
				queryPage.setItems(list);
			}
		}
		return queryPage;
	}
	
	@Override
	public MiniUiGrid getOrdersList(Map<String,String> searchMap){
		QueryPage queryPage = new QueryPage(new Object());
		String userName = searchMap.get("userName");
		boolean flag = orderDao.canSearchAllOrder(userName);//能否查询所有的订单
		if(flag)
		{
			searchMap.put("searchAll", "true");
		}
		int count = orderDao.getOrderListCount(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		if(count >0)
		{
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count);
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow")))
			{
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<Orderdetails> list = orderDao.getorderList(searchMap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;
		
	}
	@Override
	public String getOrderListTotalPrice(Map<String, String> searchMap)
	{
		String userName = searchMap.get("userName");
		boolean flag = orderDao.canSearchAllOrder(userName);//能否查询所有的订单
		if(flag)
		{
			searchMap.put("searchAll", "true");
		}
		return orderDao.getOrderListTotalPrice(searchMap);
	}
	
	
	@Override
	public Orderdetails getorderlistByid(String idorder) {
		return orderDao.getorderlistByid(idorder);
	}
	@Override
	public MiniUiGrid getProductByidorder(Map<String, String> searchMap) {
		MiniUiGrid gird = new MiniUiGrid();
		
			List<Product> list = orderDao.getProductListByidorder(searchMap);
			if(list != null && list.size() >0)
			{	
				gird.setData(list);
				gird.setTotal(list.size());
			}
		return gird;
	}
	@Override
	public void updateCustomerOrder(Orderdetails orderdetails)
	{
		 if(orderdetails.getIdorder() == null || "".equals(orderdetails.getIdorder())) return;
		 orderDao.updateCustomerOrder(orderdetails);
	}
	
	/**
	 * 订单完成付款
	 */
	@Override
	@Transactional
	public void customerOrderPayMent(Map<String, String> requestMap)
	{
			Orderdetails orderdetails = new Orderdetails();
			String idOrder = requestMap.get("idorder");
			if(StringUtil.isBlank(idOrder))return;
			
			Orderdetails o =  orderDao.getorderlistByid(idOrder);
			if(o.getStatus() == 0 || o.getStatus() == 2)
			{
				return;
			}
			
			String idPayment = requestMap.get("idPayment");
			String idSellChannel = requestMap.get("idSellChannel");
			String amountCash = requestMap.get("amountCash");
			String subTotal = requestMap.get("subTotal");
			
			
			if(!StringUtil.isBlank(idPayment))
			{
				orderdetails.setIdPayment(Integer.valueOf(idPayment));
			}
			if(!StringUtil.isBlank(idSellChannel))
			{
				orderdetails.setIdSellChannel(Integer.valueOf(idSellChannel));
			}
			if(!StringUtil.isBlank(amountCash) && !"0".equals(amountCash))
			{
				orderdetails.setAmountCash(Double.valueOf(amountCash));//现金
				
				orderdetails.setAmountCard(Double.valueOf(subTotal) - Double.valueOf(amountCash)); //刷卡
			}
			else
			{
				orderdetails.setAmountCard(Double.valueOf(subTotal));//刷卡
			}
			//根据订单号查询该订单关联的产品
			Map<String,String> searchProductMap = new HashMap<String, String>();
			searchProductMap.put("idOrder", idOrder);
			List<Product> productList = productDao.getProductList(searchProductMap);
			
			orderdetails.setIdorder(idOrder);
			orderdetails.setStatus(0); //更新为已付款
			orderdetails.setGmtpay(new Date()); //付款时间
			orderdetails.setIdEmployee(requestMap.get("operator"));//最后经手人
			orderdetails.setRemark(requestMap.get("remark"));//备注
			orderdetails.setDeliverymeno(requestMap.get("deliverymeno"));//发货信息
			orderDao.updateCustomerOrder(orderdetails);//更新订单状态
			
			//更新产品状态
			Map<String,String> lifeCylrMap = new HashMap<String, String>();
			//插入历史表
			Map<String,String> historyrMap = new HashMap<String, String>();
			
			for(Product p : productList)
			{
				lifeCylrMap.put("idStatus", 4+""); //更新为已售
				lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+""); 
				lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
				productDao.updateLifeCyle(lifeCylrMap);//更新状态
				
				//插入历史记录
				historyrMap.put("idProduct", p.getIdProduct());//idProduct
				historyrMap.put("idOperator", requestMap.get("operator"));//operator
				historyrMap.put("idCurStation",p.getCurSiteId());//当前站点
				historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
				 
				historyrMap.put("idOperation", 4+"");//4为销售出库
				historyrMap.put("idCustomer", p.getCustomerId());//idCustomer
				historyrMap.put("idStatus",4+"");//已售
				productDao.insertHistory(historyrMap); //插入历史表
				
			}
			
			
			 
		
	}

	@Override
	@Transactional
	public void cancelOrderById(Map<String, String> requestMap)
	{
		 String idOrder = requestMap.get("idorder");
		 String idLastOperator = requestMap.get("idLastOperator");
		//更新产品状态
		Map<String,String> lifeCylrMap = new HashMap<String, String>();
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		
		//根据订单号查询该订单关联的产品
		Map<String,String> searchProductMap = new HashMap<String, String>();
		searchProductMap.put("idOrder", idOrder);
		List<Product> productList = productDao.getProductList(searchProductMap);
		
		//更新产品状态，且复制一条数据
		for(Product p : productList)
		{
			lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+"");
			lifeCylrMap.put("idStatus", "7");
			lifeCylrMap.put("idOrder2", "-3");
			lifeCylrMap.put("idLastOperator", idLastOperator);
			productDao.updateLifeCyle(lifeCylrMap); //更新产品为退回状态
			productDao.copyLifeCyleByIdLiceCyle(p.getIdLifeCycle());//复制一条数据为可售
			
			
			//插入历史记录
			historyrMap.put("idProduct", p.getIdProduct());//idProduct
			historyrMap.put("idOperator", idLastOperator);//operator
			historyrMap.put("idCurStation",p.getCurSiteId());//当前站点
			historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
			 
			historyrMap.put("idOperation", 10+"");//10-销售入库
			historyrMap.put("idCustomer", "-1");//idCustomer
			historyrMap.put("idStatus",1+"");//可售
			 
			productDao.insertHistory(historyrMap); //插入历史表

		}
		
		//更新订单金额为0 状态为取消订单
		Orderdetails orderdetails = new Orderdetails();
		orderdetails.setIdorder(idOrder);
		orderdetails.setAmountCard(0D);
		orderdetails.setAmountCash(0D);
		orderdetails.setSubTotal(0D);
		orderdetails.setStatus(2);
		orderdetails.setIdPayment(8);
		orderdetails.setEmployeename(idLastOperator);
		orderDao.updateCustomerOrder(orderdetails);
		
	}
	
	
	
	@Override
	@Transactional
	public void cancelOrderByIdProduct(String idProduct,AdminAgent adminAgent)
	{
		
		Product product = productDao.getproduct(idProduct);
		if(!"预订".equals(product.getStatus())) return; //产品状态必须为预定
		
		
		//没有关联订单的预留  ,直接更新为可售
		if(product.getIdOrder().equals("-1"))
		{
			Map<String,String> map = new HashMap<String, String>();
			map.put("idStatus", "1"); //可售
			map.put("idLastOperator", adminAgent.getUsername()); //操作人
			map.put("idLifeCycle", product.getIdLifeCycle()+""); //更新产品信息为可售
			productDao.updateLifeCyle(map);
		}
		else
		{
		
			//通过idorder获取产品
			Map<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("idOrder", product.getIdOrder());
			List<Product> productList = productDao.getProductList(searchMap);
	
			Orderdetails order = orderDao.getorderlistByid(product.getIdOrder());
			
			
			Orderdetails updateOrder = new Orderdetails(); //更新订单总金额 和状态
			
			
			//该订单有多个产品       新建一个退货订单关联该idproduct   减少该订单总价
			if(productList.size() > 1)
			{
				Map<String,String> customerOrderMap = new HashMap<String, String>();
				customerOrderMap.put("site", order.getIdSite());
				customerOrderMap.put("idCustomer", order.getIdCustomer()+"");
				customerOrderMap.put("idChannel", order.getIdSellChannel()+"");
				customerOrderMap.put("subTotal", 0+"");
				customerOrderMap.put("idCurrency", order.getIdCurrency());
				customerOrderMap.put("operator", adminAgent.getUsername());
				customerOrderMap.put("operator2", 0+"");
				customerOrderMap.put("idPayment", "9"); //部分取消
				customerOrderMap.put("amountCard",  "0");
				customerOrderMap.put("amountCash",  "0");
				customerOrderMap.put("status", "2");
				customerOrderMap.put("remark", "取消部分订单：" + order.getIdorder());
				customerOrderMap.put("gmtPay", "null"); //未付款
				Integer custoerOrderId = orderDao.insertIntoCustomerOrder(customerOrderMap);  //保存订单信息
				Map<String,String> map = new HashMap<String, String>();
				map.put("idStatus", "7"); //退货
				map.put("idOrder2", "-3");//退货
				map.put("idOrder", custoerOrderId+""); //关联新的订单号
				map.put("idLastOperator", adminAgent.getUsername()); //操作人
				map.put("idLifeCycle", product.getIdLifeCycle()+""); //更新
				productDao.updateLifeCyle(map);
			}
			else  //该订单只有一个产品  更新该订单状态  
			{
				
				updateOrder.setIdPayment(8); //取消订单
				updateOrder.setStatus(2);
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("idStatus", "7"); //退货
				map.put("idOrder2", "-3");//退货
				map.put("idLastOperator", adminAgent.getUsername()); //操作人
				map.put("idLifeCycle", product.getIdLifeCycle()+""); //更新产品信息为退货
				productDao.updateLifeCyle(map);
			}
			
			
			
			//更新订单总金额 和最后操作人
			Double amountCard = order.getAmountCard();
			Double amountCash = order.getAmountCash();
			Double orderTotalPrice = order.getSubTotal();
			orderTotalPrice = orderTotalPrice - product.getSalePrice();
			if(orderTotalPrice < 0) orderTotalPrice=0d; 
			if(amountCard > 0)
			{
				amountCard = amountCard - product.getSalePrice();
				if(amountCard < 0) amountCard=0d;
			}
			else if(amountCash > 0)
			{
				amountCash = amountCash - product.getSalePrice();
				if(amountCash < 0)amountCash=0d;
			}
			updateOrder.setSubTotal(orderTotalPrice);
			updateOrder.setAmountCard(amountCard);
			updateOrder.setAmountCash(amountCash);
			updateOrder.setIdorder(product.getIdOrder());
			updateOrder.setIdEmployee(adminAgent.getUsername());
			orderDao.updateCustomerOrder(updateOrder);
			
			
			productDao.copyLifeCyleByIdLiceCyle(product.getIdLifeCycle());//之前的数据已更新为退货  复制一条数据为可售
		
		}
		Map<String,String> historyrMap = new HashMap<String, String>();
		//插入历史记录
		historyrMap.put("idProduct", product.getIdProduct());//idProduct
		historyrMap.put("idOperator", adminAgent.getUsername());//operator
		historyrMap.put("idCurStation",product.getCurSiteId());//当前站点
		historyrMap.put("idSupply", product.getIdSupply()+"");//idSupply
		 
		historyrMap.put("idOperation", 6+"");//6-取消预留
		historyrMap.put("idCustomer", "-1");//idCustomer
		historyrMap.put("idStatus",1+"");//可售
		 
		productDao.insertHistory(historyrMap); //插入历史表

	}
	
	
	
	@Override
	@Transactional
	public void saleInStockByIdProduct(String idProduct,String idPayment,AdminAgent adminAgent)
	{
		
		
		Product product = productDao.getproduct(idProduct);
		if("已售".equals(product.getStatus()))
		{
			//新生成一个负订单 关联产品信息
			Orderdetails order = orderDao.getorderlistByid(product.getIdOrder());
			if(StringUtil.isBlank(idPayment)) idPayment = order.getIdPayment()+"";//默认为原付款方式
			Map<String,String> customerOrderMap = new HashMap<String, String>();
			customerOrderMap.put("site", order.getIdSite());
			customerOrderMap.put("idCustomer", order.getIdCustomer()+"");
			customerOrderMap.put("idChannel", order.getIdSellChannel()+"");
			customerOrderMap.put("subTotal", product.getSalePrice()*-1+"");
			customerOrderMap.put("idCurrency", order.getIdCurrency());
			customerOrderMap.put("operator", adminAgent.getUsername());
			customerOrderMap.put("operator2", 0+"");
			customerOrderMap.put("idPayment", idPayment); //退款方式
			customerOrderMap.put("amountCard",  product.getSalePrice()*-1+"");
			customerOrderMap.put("amountCash",  "0");
			customerOrderMap.put("status", "0");//销售入库
			customerOrderMap.put("remark", "");
			customerOrderMap.put("gmtPay", "sysdate()"); //未付款
			Integer custoerOrderId = orderDao.insertIntoCustomerOrder(customerOrderMap);  //保存订单信息
			
			Map<String,String> map = new HashMap<String, String>();
			map.put("idStatus", "7"); //退货
			map.put("idOrder2", custoerOrderId+"");//退货
			map.put("idLastOperator", adminAgent.getUsername()); //操作人
			map.put("idLifeCycle", product.getIdLifeCycle()+""); //更新产品信息为退货
			productDao.updateLifeCyle(map);
	
			productDao.copyLifeCyleByIdLiceCyle(product.getIdLifeCycle());//之前的数据已更新为退货  复制一条数据为可售
			
		 
			Map<String,String> historyrMap = new HashMap<String, String>();
			//插入历史记录
			historyrMap.put("idProduct", product.getIdProduct());//idProduct
			historyrMap.put("idOperator", adminAgent.getUsername());//operator
			historyrMap.put("idCurStation",product.getCurSiteId());//当前站点
			historyrMap.put("idSupply", product.getIdSupply()+"");//idSupply
			 
			historyrMap.put("idOperation", 10+"");//10-销售入库
			historyrMap.put("idCustomer", "-1");//idCustomer
			historyrMap.put("idStatus",1+"");//可售
			 
			productDao.insertHistory(historyrMap); //插入历史表
		}
		
		
	}
	
	


}
