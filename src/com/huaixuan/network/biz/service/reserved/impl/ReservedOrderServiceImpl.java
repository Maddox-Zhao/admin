package com.huaixuan.network.biz.service.reserved.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.order.OrderDao;
import com.huaixuan.network.biz.dao.product.ProductDao;
import com.huaixuan.network.biz.dao.reserved.ReservedOrderDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.autosyn.PlatformData;
import com.huaixuan.network.biz.domain.order.ProductShoppingCar;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.reserved.ReservedOrder;
import com.huaixuan.network.biz.domain.reserved.ReservedOrderProduct;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.order.ProductShoppingCarService;
import com.huaixuan.network.biz.service.reserved.ReservedOrderService;
import com.huaixuan.network.common.util.Result;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.hundsun.common.lang.StringUtil;



/**
 * @author Mr_Yang   2016-9-6 下午03:33:17
 **/

@Service("reservedOrderService")
public class ReservedOrderServiceImpl implements ReservedOrderService
{
	@Autowired
	private ReservedOrderDao reservedOrderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductShoppingCarService productShoppingCarService;
	
	@Autowired
	private OrderDao orderDao;
	
	@Override
	public String reservedShoppingCar2SettleAccount(Map<String, String> requestMap,AdminAgent adminAgent)
	{
		String idProducts = requestMap.get("idProducts");
		if("".equals(idProducts) || null == idProducts)
		{
			return "-1";
		}
		Map<String,Double> productsMap = new HashMap<String, Double>();//保存idProduct 和售价
		String[] arr = idProducts.split(";");  //idProduct:price;idProduct:price
		for(String s : arr)
		{
			if(!"".equals(s))
			{
				String[] ids = s.split(":");
				String idProduct = ids[0];
				Double price = Double.valueOf(ids[1]);
				productsMap.put(idProduct, price);
			}
			
		}
		
		//添加预开单订单
		ReservedOrder reservedOrder = new ReservedOrder();
		reservedOrder.setIdCustomer(Long.valueOf(requestMap.get("idCustomer")));
		reservedOrder.setSubTotal(Double.valueOf(requestMap.get("subTotal")));
		reservedOrder.setIdCurrency(Long.valueOf(requestMap.get("idCurrency")));
		reservedOrder.setIdPayment(0L);//未付款
		reservedOrder.setIdChannel(Long.valueOf(requestMap.get("idChannel")));
		reservedOrder.setAmountCard(Double.valueOf(requestMap.get("subTotal")));
		reservedOrder.setStatus(0L);//未处理
		reservedOrder.setCreateUserId(adminAgent.getUsername());
		reservedOrder.setCreateUserName(adminAgent.getName());
		//保存订单信息
		Long orderId = reservedOrderDao.insertReservedOrder(reservedOrder);
		//插入成功,更新产品状态 以及记录历史信息
		if(orderId == null)
		{
			return "-1";
		}
		
		 
		//更新产品状态
		Map<String,String> lifeCylrMap = new HashMap<String, String>();
		
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		
		//更新产品状态为已售 和订单信息
		Set<Entry<String, Double>>  keySet = productsMap.entrySet();
		Iterator<Map.Entry<String,Double>>it = keySet.iterator();
		while(it.hasNext())
		{
			Map.Entry<String,Double> entry = it.next();
			
			Product p = productDao.getproduct(entry.getKey()); //查询当前产品信息(未更新之前)
			if(!"可售".equals(p.getStatus())) continue;

			lifeCylrMap.put("idStatus", 100+""); //更新为预开单
			//lifeCylrMap.put("price", entry.getValue()+"");//售价
			lifeCylrMap.put("idProduct", entry.getKey()); 
			//lifeCylrMap.put("idPriceCurrency", requestMap.get("idCurrency")); //销售币种
			lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
			lifeCylrMap.put("idLifeCycle", p.getIdLifeCycle()+""); //
			productDao.updateLifeCyle(lifeCylrMap);//更新状态
			
			
			
			historyrMap.put("idProduct", entry.getKey());//idProduct
			historyrMap.put("idOperator", requestMap.get("operator"));//operator
			historyrMap.put("idCustomer", requestMap.get("idCustomer"));//operator
			historyrMap.put("idCurStation",p.getCurSiteId());//当前站点
			historyrMap.put("idSupply", p.getIdSupply()+"");//idSupply
			historyrMap.put("idOperation", 15+"");//15为预开单
			historyrMap.put("idStatus",100+"");//预开单
			
			
			productDao.insertHistory(historyrMap); //插入历史表
			
			
			PlatformData platformData = new PlatformData();
			platformData.setIdProduct(entry.getKey());
			
			//添加预开单产品
			
			ReservedOrderProduct reservedOrderProduct = new ReservedOrderProduct();
			reservedOrderProduct.setIdProduct(entry.getKey());
			reservedOrderProduct.setIdReserved(orderId);
			reservedOrderProduct.setSalePrice(entry.getValue());
			reservedOrderProduct.setStatus("1");//已提交 仓库未处理
			reservedOrderProduct.setRealyIdProduct("");
			//添加预开单产品
			reservedOrderDao.insertReservedOrderProduct(reservedOrderProduct);
			
			
			//删除购物车 产品
			ProductShoppingCar productShoppingCar = new ProductShoppingCar();
			productShoppingCar.setIdProduct(entry.getKey());
			productShoppingCar.setUserName(requestMap.get("operator"));
			productShoppingCarService.removeShoppingCar(productShoppingCar);
		
		}
		 
		return "ok";
	}

	@Override
	public MiniUiGrid searchReservedOrder(Map<String, String> searchMap)
	{
		QueryPage queryPage = new QueryPage(new Object());
		int count = reservedOrderDao.searchReservedListCnt(searchMap);
		
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		
		if(count >0)
		{
			//int currPage,int pageSize
	
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);

			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理

			List<ReservedOrder> list =  reservedOrderDao.searchReservedList(searchMap);
			if(list != null && list.size() >0)
			{
				gird.setData(list);
			}
		}
		return gird;
	}

	@Override
	public String searchReservedListPrice(Map<String, String> searchMap)
	{
		return  reservedOrderDao.searchReservedListPrice(searchMap);
	}

	@Override
	public List<ReservedOrderProduct> searchReservedOrderProduct(Map<String, String> searchMap)
	{
		 
		return reservedOrderDao.searchReservedOrderProduct(searchMap);
	}

	/**
	 * 返回预开单ID和找到的idProduct前端设置用
	 */
	@Override
	public Result setRealyIdProduct(Map<String, String> requestMap)
	{
		String idProduct = requestMap.get("idProduct");
		String idReserved = requestMap.get("idReserved");
		Result result = new Result();
		if(StringUtil.isBlank(idProduct)) 
		{
			result.setMessage("没有idProduct");
			result.setResult(0);
			return result;
		}
		if(StringUtil.isBlank(idReserved)) 
		{
			result.setMessage("没有订单id");
			result.setResult(0);
			return result;
		}
		idProduct = idProduct.trim();
		Product scanProduct = productDao.getproduct(idProduct);
		if(scanProduct == null) 
		{
			result.setMessage("没有查询到该idProduct");
			result.setResult(0);
			return result;
		}
		if("可售".equals(scanProduct.getStatus()) || "预开单".equals(scanProduct.getStatus()))
		{
			//判断该idProduct是否赋值给其他行
			Map<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("idReserved", idReserved);
			searchMap.put("realyIdProduct", " != '' "); //有设置真实idProduct
			List<ReservedOrderProduct> list = reservedOrderDao.searchReservedOrderProduct(searchMap);
			for(ReservedOrderProduct p : list)
			{
				 if(p.getRealyIdProduct().equals(idProduct))
				 {
						result.setMessage(idProduct + ",已经赋值给其他行");
						result.setResult(0);
						return result;
				 }
			}
			
			
			
			searchMap.put("realyIdProduct", " = '' "); //没有设置真实idProduct
		    list = reservedOrderDao.searchReservedOrderProduct(searchMap);
		    boolean notsameSite = false;
			for(ReservedOrderProduct p : list)
			{
				//只处理已提交的数据
				if(p.getSku().equals(scanProduct.getSku()) && p.getStatus().equals("1"))
				{
					if(p.getIdSite().equals(scanProduct.getCurSiteId()))
					{
						ReservedOrderProduct updateProduct = new ReservedOrderProduct();
						updateProduct.setId(p.getId());
						updateProduct.setRealyIdProduct(idProduct);
						reservedOrderDao.updateReservedProudctByNotNull(updateProduct);
						String msg = p.getIdReserved() + "," + p.getIdProduct(); //返回预开单ID和找到的idProduct前端设置用
						result.setMessage(msg);
						result.setResult(1);
						return result;
					}
					else
					{
						notsameSite = true;
					}
				}
			}
			if(notsameSite == true)
			{
				result.setMessage("站点不匹配");
				result.setResult(0);
				return result;
			}
			else
			{
				result.setMessage("没有找到匹配项");
				result.setResult(0);
				return result;
			}
			
		}
		else
		{
			result.setMessage("该idProduct当前状态为:" + scanProduct.getStatus());
			result.setResult(0);
			return result;
		}
	}
	
	
	
	
	/**
	 * 修改预开单订单的产品对应的真实idProduct
	 */
	@Override
	public Result updateRealyIdProduct(Map<String, String> requestMap)
	{
		String idProducts = requestMap.get("idProducts"); //eg: 1001:1643018140604:1643018140604,1002:1613018318396:   id:订单idProduct:真实idProduct(可空)
		String idReserved = requestMap.get("idReserved");
		
		Result result = new Result();
		if(StringUtil.isBlank(idProducts)) 
		{
			result.setMessage("没有idProduct");
			result.setResult(0);
			return result;
		}
	

		String[] idProductArr = idProducts.split(",");
		if(idProductArr.length <= 0)
		{
			result.setMessage("字符串数据有误");
			result.setResult(0);
			return result;
		}
		for(String s : idProductArr)
		{
			String[] arr = s.split(":");
			String id = arr[0];
			String idProduct = arr[1];
			String realyIdProduct = arr[2];
			if("-1".equals(realyIdProduct)) realyIdProduct = "";
			if(StringUtil.isBlank(id))
			{
				result.setMessage("没有id");
				result.setResult(0);
				return result;
			}
			if(StringUtil.isNotBlank(realyIdProduct) && realyIdProduct.length() != 13)
			{
				result.setMessage("填写的idProduct错误");
				result.setResult(0);
				return result;
			}
			
			//realyIdProduct 制空
			if(StringUtil.isBlank(realyIdProduct) && StringUtil.isNotBlank(idProduct))
			{
				ReservedOrderProduct updateProduct = new ReservedOrderProduct();
				updateProduct.setId(Long.valueOf(id));
				updateProduct.setRealyIdProduct(""); //-1  设置为NULL
				reservedOrderDao.updateReservedProudctByNotNull(updateProduct);
				continue;
			}
			
			//修改为其他idProduct才需要订单ID
			if(StringUtil.isBlank(idReserved)) 
			{
				result.setMessage("没有订单ID");
				result.setResult(0);
				return result;
			}
			//判断该idProduct是否赋值给其他行
			Map<String,String> searchMap = new HashMap<String, String>();
			searchMap.put("idReserved", idReserved);
			searchMap.put("realyIdProduct", " != '' "); //有设置真实idProduct
			List<ReservedOrderProduct> list = reservedOrderDao.searchReservedOrderProduct(searchMap);
			for(ReservedOrderProduct p : list)
			{
				 if(p.getRealyIdProduct().equals(realyIdProduct))
				 {
						result.setMessage(realyIdProduct + ",已经赋值给其他行");
						result.setResult(0);
						return result;
				 }
			}
			
			//更新realyIdproduct
			Product scanProduct = productDao.getproduct(realyIdProduct);
			if(scanProduct == null) 
			{
				result.setMessage("没有查询到该idProduct");
				result.setResult(0);
				return result;
			}
			if("可售".equals(scanProduct.getStatus()) || "预开单".equals(scanProduct.getStatus()))
			{
				Product orderProduct = productDao.getproduct(idProduct);
				if(orderProduct.getSku().equals(scanProduct.getSku()))
				{
					if(orderProduct.getCurSiteId().equals(scanProduct.getCurSiteId()))
					{
						ReservedOrderProduct updateProduct = new ReservedOrderProduct();
						updateProduct.setId(Long.valueOf(id));
						updateProduct.setRealyIdProduct(realyIdProduct);
						reservedOrderDao.updateReservedProudctByNotNull(updateProduct);
						result.setMessage(realyIdProduct);
						result.setResult(1);
						return result;
					}
					else
					{
						result.setMessage(realyIdProduct +"站点为:"+scanProduct.getCurSiteName() + "<br/>"+  orderProduct.getIdProduct()+ "站点为:" + orderProduct.getCurSiteName());
						result.setResult(0);
						return result;
					}
				}
			}
			else
			{
				result.setMessage(scanProduct.getIdProduct() + " 状态为：" + scanProduct.getStatus() + ",不为可售或预开单状态");
				result.setResult(0);
				return result;
			}
		}
		result.setMessage("处理完成");
		result.setResult(1);
		return result;
		
	}
	
 
	/**
	 *生成真实订单
	 *0.先判断提交的订单客户是否一致
	 *1 在判断真实idProduct是否为可售或者预开单状态
	 *2 如果idProduct不一致 更新原idProduct和真实idProduct状态和历史记录
	 *3 生成订单信息
	 */
	@Override
	public Result preOrdertoRealyOrder(Map<String, String> requestMap,AdminAgent adminAgent)
	{
		
		String idReserveds = requestMap.get("idReserveds"); //提交的订单
		Result result = new Result();
		//查询订单信息
		Map<String,String> searchMap  = new HashMap<String, String>();
		searchMap.put("id", idReserveds);
		List<ReservedOrder> orderList =  reservedOrderDao.searchReservedList(searchMap);
		if(orderList.size() == 0)
		{
			result.setResult(0);
			result.setMessage("没有订单信息");
			return result;
		}
		Long  idCustomer = null;
		for(ReservedOrder r : orderList)
		{
			if(r.getStatus().intValue() != 0)
			{
					result.setResult(1);
					result.setMessage("订单号 " + r.getId() + " 不是未处理状态");
					return result;
			}
			if(idCustomer == null)
				idCustomer = r.getIdCustomer();
			else
			{
				if(idCustomer.intValue() != r.getIdCustomer().intValue()) 
				{
					result.setResult(1);
					result.setMessage("订单客户不一致");
					return result;
				}
				idCustomer = r.getIdCustomer();
			}
			
		}
		
		
		//
		Map<String,String> idProductMap  = new HashMap<String, String>(); //key是预开单idProduct value是真实idProduct
		Map<String,String> realyProductMap = new HashMap<String, String>();//key是真实idProduct value是预开单idProduct
		
		//查询订单产品判断是否可售或者预开单状态
		searchMap.put("idReserved", idReserveds);
		Double realyOrderSubTotal = 0D; //订单总金额 (不包含没设置realyIdProduct的产品)
		List<ReservedOrderProduct> list = reservedOrderDao.searchReservedOrderProduct(searchMap);
		int notRealyIdProductCnt = 0;//没有记录真实idProduct的
		for(ReservedOrderProduct r : list)
		{
			//真实idProduct存在  且是已提交-未处理状态
			if(StringUtil.isNotBlank(r.getRealyIdProduct()))
			{
				if(!(r.getRealyIdStatus().intValue() == 1 || r.getRealyIdStatus().intValue() == 100))
				{
					result.setResult(2);
					result.setMessage(r.getRealyIdProduct() + " 不是可售或者预开单状态");
					return result;
				}
				/*
				if(!(r.getIdStatus().intValue() == 1 || r.getIdStatus().intValue() == 100))
				{
					result.setResult(2);
					result.setMessage(r.getIdProduct() + " 不是可售或者预开单状态");
					return result;
				}
				*/
				realyOrderSubTotal += r.getSalePrice();
				
				//用于判断是否需要设置回可售状态
				realyProductMap.put(r.getRealyIdProduct(), r.getRealyIdProduct());
			}
			else
			{
				notRealyIdProductCnt++;
			}
		}
		
		
		
		//预开单转为正式订单
	
		//1.先生成订单 
		ReservedOrder  preOrderInfo = orderList.get(0); //预开单信息  付款方式 客户 总金额等
		Map<String,String> customerOrderMap = new HashMap<String, String>();
		customerOrderMap.put("idCustomer", preOrderInfo.getIdCustomer()+"");
		customerOrderMap.put("idChannel", preOrderInfo.getIdChannel()+"");
		customerOrderMap.put("subTotal", realyOrderSubTotal+"");
		customerOrderMap.put("idCurrency", preOrderInfo.getIdCurrency()+"");
		customerOrderMap.put("operator", requestMap.get("operator"));
		customerOrderMap.put("operator2", 0+"");
		customerOrderMap.put("idPayment",preOrderInfo.getIdPayment()+"");
		customerOrderMap.put("amountCard", realyOrderSubTotal+"");
		customerOrderMap.put("amountCash", "0");//现金付款
		customerOrderMap.put("status", "1");//未付款 0-已付款 1-未付款
		customerOrderMap.put("remark", "预开单-"+idReserveds);
		customerOrderMap.put("gmtPay", "null"); //未付款
		customerOrderMap.put("site", adminAgent.getSiteId()+""); //
		Integer custoerOrderId = orderDao.insertIntoCustomerOrder(customerOrderMap);  //保存订单信息
		if(custoerOrderId == null) 
		{
			result.setResult(3);
			result.setMessage("生成真实订单出错");
			return result;
		}
		
		Map<Long,Integer> preOrderStatusMap = new HashMap<Long, Integer>(); //更新订单信息 key预开单订单号  value 状态编码
 
		//插入历史表
		Map<String,String> historyrMap = new HashMap<String, String>();
		//更新产品状态 并且关联真实订单
		//如果真实idProduct和预开单的idProduct不一致   需先更新预开单idProduct状态并记录历史记录
		//在更新真实idProduct的状态和历史记录
		for(ReservedOrderProduct r : list)
		{
			Product preProduct = productDao.getproduct(r.getIdProduct());
			if(r.getStatus().equals("1") && StringUtil.isNotBlank(r.getRealyIdProduct()))//1-已提交-还没处理 只处理未处理的产品 且 真实idProduct存在
			{
					//未处理的才需要更新为已处理
					if(preOrderStatusMap.get(r.getIdReserved()) == null)
						preOrderStatusMap.put(r.getIdReserved(), 1);//key-预预开单id  value-订单状态  已处理	
					r.setStatus("2");//已开单
					
					//如果开单的idProduct和真实idProduct不一致
					//先更新开单idProduct状态和历史记录
					//在更新真实idProduct的状态和历史记录
					if(realyProductMap.get(r.getIdProduct()) == null)
					{
						//插入历史记录
						historyrMap.put("idProduct", preProduct.getIdProduct());//idProduct
						historyrMap.put("idOperator", requestMap.get("operator"));//operator
						historyrMap.put("idCurStation",preProduct.getCurSiteId());//当前站点
						historyrMap.put("idSupply", preProduct.getIdSupply()+"");//idSupply		 
						historyrMap.put("idOperation", 16+"");//16为解除预开单
						historyrMap.put("idCustomer", preOrderInfo.getIdCustomer()+"");//idCustomer
						historyrMap.put("idStatus",1+"");//可售
						productDao.insertHistory(historyrMap); //插入历史表
						
						//更新产品状态
						Map<String,String> lifeCylrMap = new HashMap<String, String>();
						//更新预开单产品状态
						lifeCylrMap.put("idStatus", 1+""); //更新为可售
						lifeCylrMap.put("idLifeCycle", preProduct.getIdLifeCycle()+""); 
						lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
						productDao.updateLifeCyle(lifeCylrMap);//更新状态
					}
					
					Product realyProduct = productDao.getproduct(r.getRealyIdProduct());
					//插入历史记录
					historyrMap.put("idProduct", realyProduct.getIdProduct());//idProduct
					historyrMap.put("idOperator", requestMap.get("operator"));//operator
					historyrMap.put("idCurStation",realyProduct.getCurSiteId());//当前站点
					historyrMap.put("idSupply", realyProduct.getIdSupply()+"");//idSupply		 
					historyrMap.put("idCustomer", preOrderInfo.getIdCustomer()+"");//idCustomer
					historyrMap.put("idOperation", 4+"");//4为销售出库
					historyrMap.put("idStatus",4+"");//已售
					productDao.insertHistory(historyrMap); //插入历史表
					
					Map<String,String> lifeCylrMap = new HashMap<String, String>();
					//更新预开单真实产品状态
					lifeCylrMap.put("idStatus", 4+""); //4-已售
					lifeCylrMap.put("idLifeCycle", realyProduct.getIdLifeCycle()+""); 
					lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
					lifeCylrMap.put("idPriceCurrency", preOrderInfo.getIdCurrency()+""); //币种
					
					lifeCylrMap.put("idOrder",custoerOrderId+""); //关联订单号
					lifeCylrMap.put("price", r.getSalePrice()+"");
					
					
					productDao.updateLifeCyle(lifeCylrMap);//更新状态
				}
				else if(r.getStatus().equals("1") && StringUtil.isBlank(r.getRealyIdProduct()) )
				{
					//真实idProduct不存在  说明缺货 更新产品和订单状态
					preOrderStatusMap.put(r.getIdReserved(), 2);//key-预预开单id  value-预开单订单状态  已处理 部分缺
					r.setStatus("3");//已取消
					
					
					if(realyProductMap.get(r.getIdProduct()) == null)
					{
						//插入历史记录
						historyrMap.put("idProduct", preProduct.getIdProduct());//idProduct
						historyrMap.put("idOperator", requestMap.get("operator"));//operator
						historyrMap.put("idCurStation",preProduct.getCurSiteId());//当前站点
						historyrMap.put("idSupply", preProduct.getIdSupply()+"");//idSupply		 
						historyrMap.put("idOperation", 16+"");//16为解除预开单
						historyrMap.put("idCustomer", preOrderInfo.getIdCustomer()+"");//idCustomer
						historyrMap.put("idStatus",1+"");//可售
						productDao.insertHistory(historyrMap); //插入历史表
						//更新预开单产品状态
						Map<String,String> lifeCylrMap = new HashMap<String, String>();
						lifeCylrMap.put("idStatus", 1+""); //更新为可售
						lifeCylrMap.put("idLifeCycle", preProduct.getIdLifeCycle()+""); 
						lifeCylrMap.put("idLastOperator", requestMap.get("operator")); //最后操作人
						productDao.updateLifeCyle(lifeCylrMap);//更新状态
					}
				}
			ReservedOrderProduct reservedOrderProduct = new ReservedOrderProduct();
			reservedOrderProduct.setStatus(r.getStatus());
			reservedOrderProduct.setId(r.getId());
			reservedOrderDao.updateReservedProudctByNotNull(reservedOrderProduct);

		}
		
		//更新订单状态
		Set<Entry<Long, Integer>> set = preOrderStatusMap.entrySet();
		Iterator<Entry<Long, Integer>> it = set.iterator();
		while(it.hasNext())
		{
			Entry<Long, Integer> en = it.next();
			Long idOrder = en.getKey();
			Integer orderStatus = en.getValue();
			ReservedOrder reservedOrder = new ReservedOrder();
			reservedOrder.setId(idOrder);
			reservedOrder.setStatus(Long.valueOf(orderStatus));
			reservedOrder.setIdOrder(Long.valueOf(custoerOrderId));
			reservedOrderDao.updateReservedOrderByNotNull(reservedOrder);
		}
		
		
		result.setResult(4);
		result.setMessage("生成订单成功!总金额:" + realyOrderSubTotal);
		return result;
	}
	
}
 
