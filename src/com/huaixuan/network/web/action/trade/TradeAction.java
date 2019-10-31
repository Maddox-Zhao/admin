package com.huaixuan.network.web.action.trade;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.active.MoveFrameProductDao;
import com.huaixuan.network.biz.domain.active.MoveframeInstance;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumDepLocationIsCheck;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumOrderKindType;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumTradeFrom;
import com.huaixuan.network.biz.enums.EnumTradePayStatus;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumTradeWholesaleStatus;
import com.huaixuan.network.biz.enums.hy.EnumBrandType;
import com.huaixuan.network.biz.enums.hy.EnumSeriesType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.huaixuan.network.biz.query.WholesaleQuery;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.agent.InterfaceUserTradeManager;
import com.huaixuan.network.biz.service.express.ExpressDistManager;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.GoodsPointManager;
import com.huaixuan.network.biz.service.goods.GoodsWholsaleManager;
import com.huaixuan.network.biz.service.hy.ProductService;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.InAndOutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailManager;
import com.huaixuan.network.biz.service.storage.ProdRelationOutManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.PackageManager;
import com.huaixuan.network.biz.service.trade.PayRecordManager;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.common.util.Billing_Deal_DetailsUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
public class TradeAction extends BaseAction {
	Log log = LogFactory.getLog(this.getClass());

	private static final String A_WHOLESALE_MANAGE_USER = "A_WHOLESALE_MANAGE_USER"; // 批发订单管理权限

	@Autowired
	private RegionManager regionManager;
	@Autowired
	private GoodsManager goodsManager;
	@Autowired
	private TradeManager tradeManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private PackageManager packageManager;
	@Autowired
	private GoodsPointManager goodsPointManager;
	@Autowired
	private AttributeManager attributeManager;
	@Autowired
	private RefundManager refundManager;
	@Autowired
	private ExpressDistManager expressDistManager;
	@Autowired
	private InterfaceApplyManager interfaceApplyManager;
	@Autowired
	private InterfaceUserTradeManager interfaceUserTradeManager;
	@Autowired
	private PayRecordManager payRecordManager;
	@Autowired
	private OutDepositoryManager outDepositoryManager;
	@Autowired
	private StorageManager storageManager;
	@Autowired
	private InAndOutDepositoryManager inAndOutDepositoryManager;
	@Autowired
	private GoodsBatchManager goodsBatch;
	@Autowired
	private GoodsInstanceManager goodsInstanceManager; // 产品实例
	@Autowired
	private ExpressManager expressManager;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager; // 一级仓库
	@Autowired
	private GoodsWholsaleManager goodsWholsaleManager;
	@Autowired
	private OutDetailManager outDetailManager;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private DepLocationManager depLocationManager;
	@Autowired
	private TaobaoInterfaceApplyManager taobaoInterfaceApplyManager;
	@Autowired
	private ProdRelationOutManager prodRelationOutManager;
	@Autowired
	private ResourcesManager resourcesManager;
	@Autowired
	private DepositoryService depositoryService;
	@Autowired
	private ProductService  productService;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private MoveFrameProductDao moveFrameProductDao;

	/**
	 * 后台显示订单详情
	 * 
	 * @return
	 */
	@RequestMapping("/trade/detail")
	public String showAdminTradeDetail(@RequestParam(value = "tradeId", required = false) String tid,
			@RequestParam(value = "refundId", required = false) String refundId, Model model, AdminAgent agent) {
		// 如果传入的参数是refund_id,则根据refund_id查询对应的tid
		if (!StringUtil.isBlank(refundId)) {
			Refund refund = refundManager.getRefundByRefundId(refundId);
			if (refund != null) {
				String tradeId = refund.getTid();
				Trade trade = tradeManager.getTradeByTid(tradeId);
				if (trade != null) {
					tid = trade.getTid();
				}
			}
		}

		// 交易信息
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		

		if (trade != null) {
			
			// 物流价格显示为非负整数 zhangwy
//			/* begin add by shenzh Oct 29, 2010 说明：防止物流价格被修改 */
//			double backUpShippingAmount = trade.getShippingAmount();
//			/* end by shenzh Oct 29, 2010 */
//			trade.setShippingAmount(DoubleUtil.round(trade.getShippingAmount(), 1));

			// 收货人地址
			String address = this.regionManager.getAddressInfo(trade.getProvince(), trade.getCity(),
					trade.getDistrict());
			address += trade.getAddress();
			model.addAttribute("address", address);
//			
//			//省份
//			List<Region> provinceList = regionManager.getRegionByType(2);
//			model.addAttribute("provinceList", provinceList);
//			//市
//			if(StringUtil.isNotBlank(trade.getProvince())){
//				List<Region> cityList = regionManager.getRegionChilds(trade.getProvince());
//				model.addAttribute("cityList", cityList);
//			}
//			//地区
//			if(StringUtil.isNotBlank(trade.getCity())){
//				List<Region> districtList = regionManager.getRegionChilds(trade.getCity());
//				model.addAttribute("districtList", districtList);
//			}
//
//
//			// 订单中的普通商品信息 加入重量等信息 zhangwy
//			List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
//			model.addAttribute("orderList", orderList);
//
//			String regionCodeStart = "330782";
//
//			double orderWeight = 0;
//			if (orderList != null) {
//				for (Order o : orderList) {
//					orderWeight += o.getGoodWeight() * o.getGoodsNumber();
//					GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
//					if (g != null) {
//						o.setGoodsSn(g.getCode());
//					}
////					o.setStoragelist(storageManager.getStorageWithTrade(o.getGoodsInstanceId(), false));
//					if(g.getHkExistNum()!=null)
//						o.setInstanceHkExistNum(g.getHkExistNum().longValue());
//				}
//			}
//
//			// 物流信息(首重,续重以及他们的价格)
//			trade.setTotalWeight(DoubleUtil.round(orderWeight, 2));
//			boolean isPeriod = false;
//			if (trade.getExpressPayment() != null && trade.getExpressPayment().equals("period_pay")) {
//				trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
//				isPeriod = true;
//			}
//
//			if (trade.getDistrict() != null && StringUtil.isNotBlank(trade.getDistrict())) {
//				ExpressDist expressDist = expressDistManager.getExpressDistByRegionCodeEnd(regionCodeStart,
//						trade.getDistrict(), trade.getExpressPayment(), trade.getExpressId());
//				if (isPeriod) {
//					trade.setExpressPayment("period_pay");
//				}
//				if (expressDist != null) {
//					trade.setWeightFirst(expressDist.getWeightFirst());
//					trade.setWeightFirstMoney(expressDist.getWeightFirstMoney());
//					trade.setWeightNext(expressDist.getWeightNext());
//					trade.setWeightNextMoney(expressDist.getWeightNextMoney());
//				}
//				/* begin add by shenzh Oct 29, 2010 说明： 淘宝订单，要显示原始价格 */
//				if (3 == trade.getTradeType()) {
//					trade.setShippingAmount(backUpShippingAmount);
//				}
//				/* end by shenzh Oct 29, 2010 */
//			} else {
//				ExpressDist expressDist = expressDistManager.getExpressDistByRegionCodeEnd(regionCodeStart,
//						trade.getCity(), trade.getExpressPayment(), trade.getExpressId());
//				if (isPeriod) {
//					trade.setExpressPayment("period_pay");
//				}
//				if (expressDist != null) {
//					trade.setWeightFirst(expressDist.getWeightFirst());
//					trade.setWeightFirstMoney(expressDist.getWeightFirstMoney());
//					trade.setWeightNext(expressDist.getWeightNext());
//					trade.setWeightNextMoney(expressDist.getWeightNextMoney());
//				}
//				/* begin add by shenzh Oct 29, 2010 说明： 淘宝订单，要显示原始价格 */
//				if (3 == trade.getTradeType()) {
//					trade.setShippingAmount(backUpShippingAmount);
//				}
//				/* end by shenzh Oct 29, 2010 */
//			}
//
//			// 订单中的套餐及套餐商品信息
//			Map<PackageTrade, List<Order>> packageMap = new HashMap<PackageTrade, List<Order>>();
//
//			List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
//
//			Map parameterMap = new HashMap();
//			parameterMap.put("tid", trade.getTid());
//			for (PackageTrade packageinstance : packages) {
//				parameterMap.put("packageId", packageinstance.getId());
//				List<Order> orders = orderManager.getOrdersByParameterMap(parameterMap);
//				for (Order o : orders) {
//					GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
//					if (g != null) {
//						o.setGoodsSn(g.getCode());
//					}
//				}
//				packageMap.put(packageinstance, orders);
//			}
//			model.addAttribute("packageMap", packageMap);
//
//			// 支付方式
//			if (EnumExpressDistPayment.PAYMENT_FIRST.getKey().equals(trade.getExpressPayment())) {
//				PayRecord payRecord = payRecordManager.getPayRecordByTid(trade.getTid());
//				model.addAttribute("payRecord", payRecord);
//			}
		}
		
		
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		trade.setOrderList(orderList);
		model.addAttribute("orderList", orderList);
		
		for (Order o : trade.getOrderList()) {
			
			GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
			List<Product> products = productService.getProductsByOrderId(o.getId());
			for(Product p : products)
			{
				p.setBrandname(EnumBrandType.toMap().get(p.getIdBrand().toString()));
				p.setSeriesname(EnumSeriesType.toMap().get(p.getIdSeries().toString()));
			}
			o.setProducts(products);
			if(g.getHkExistNum()!=null)
				o.setInstanceHkExistNum(g.getHkExistNum().longValue());
			if(trade.getTradeType() != 11) //设置活动库存
			{
				List<MoveframeInstance> ls = moveFrameProductDao.getMoveFrameInstanceByInstanceId(o.getGoodsInstanceId());
				long num = 0;
				for(MoveframeInstance m : ls)
				{
					num += m.getInstanceNum();
				}
				o.setInstanceHkExistNum(num); //活动库存
			}
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", "w");
//		model.addAttribute("depositoryFirstList", depositoryFirstManager.getDepositoryFirstListByParMap(paramMap));

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("enumOrderKindMap", EnumOrderKindType.toMap());
		model.addAttribute("enumTradeWholesaleStatus", EnumTradeWholesaleStatus.toMap());

		return "/trade/tradeDetail";
	}

	/**
	 * 后台显示订单列表
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("/trade/showl")
	public String showAdminTradeList(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "statusOpp", required = false) String statusOpp,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			AdminAgent agent) {
		
		//songfy 20180426注释
//		selectOrderType(query.getOrderType(), query, agent);// 根据权限选择订单类型

		String gmtCreateStart = query.getGmtCreateStart();
		String gmtCreateEnd = query.getGmtCreateEnd();
		String tid = query.getTid();
		if (StringUtil.isBlank(tid)) {
			if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
				// modified 默认原7天修改为2天进行查询
//				gmtCreateStart = DateUtil.convertDateToString(DateUtil.getRelativeDate(new Date(), -1));
				// modified 改为每个月第一天
				Calendar calendar  =   new  GregorianCalendar();
				calendar.set( Calendar.DATE,  1 );
				gmtCreateStart = DateUtil.convertDateToString(calendar.getTime());
				gmtCreateEnd = DateUtil.convertDateToString(new Date());
				query.setGmtCreateStart(gmtCreateStart);
				query.setGmtCreateEnd(gmtCreateEnd);
			}
		}

		List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
		if (depfirstIdList != null && depfirstIdList.size() > 0) {
			query.setDepfirstIds(depfirstIdList);
		}
		
		if (null != depfirstIdList) {
			if (StringUtil.isNotBlank(statusOpp)) {
				if (StringUtil.isNotBlank(query.getStatus())) {
					query.setOpp(query.getStatus());
					query.setStatus(null);
				} else {
					query.setStatus("opp");
				}
			}

			Trade trade = tradeManager.getTradesGoodsAmountSum(query);
			model.addAttribute("trade", trade);

			if(pageSize == null) {
				pageSize = this.pageSize;
			}
			QueryPage page = tradeManager.getTradesByParameterMap(query, currPage, pageSize);
			model.addAttribute("page", page);

			// added by chenhang 对trade的备货属性进行查询添加
//			if (page.getItems() != null) {
//				for (Trade t : (List<Trade>) page.getItems()) {
//					if (t.getDepFirstId() != null) {
//						t.setIsStocked(depositoryFirstManager.getDepositoryById(t.getDepFirstId()).getIsStocked());
//					}
//				}
//			}

			// 还原为原来的设置
			if (StringUtil.isNotBlank(statusOpp)) {
				if (query.getOpp() != null) {
					query.setStatus(query.getOpp());
					query.setOpp(null);
				} else {
					query.setStatus(null);
				}
			}
		}

		model.addAttribute("statusOpp", statusOpp);

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("enumTradePayStatusMap", EnumTradePayStatus.toMap());
		model.addAttribute("enumOrderKindMap", EnumOrderKindType.toMap());
		model.addAttribute("enumTradeFromMap", EnumTradeFrom.toMap());
		model.addAttribute("enumTradeWholesaleStatus", EnumTradeWholesaleStatus.toMap());
		model.addAttribute("enumExpressDistPaymentMap", EnumExpressDistPayment.toMap());
		if ("true".equals(query.getSend())) {
			return "/trade/tradeMergeList";
		} else {

			return "/trade/tradeList";
		}
	}

	/*
	 * 根据权限选择订单类型（批发，代销）
	 */
	private void selectOrderType(String orderType, WholesaleQuery query, AdminAgent adminAgent) {
		if (!this.hasAnyAuthority(A_WHOLESALE_MANAGE_USER, adminAgent)) {
			if ("w".equals(orderType)) {
				orderType = "n";
			}
			query.setIsWholesale(orderType);
		}
	}

	/**
	 * 更新商品热销分数
	 * 
	 * @param goods
	 */
	@SuppressWarnings("unchecked")
	private void updateGoodsPoint(String tid) {
		Map map = new HashMap();
		map.put("tid", tid);
		List<Order> orderList = orderManager.getOrdersByParameterMap(map);
		Map goodsMap = new HashMap<Long, Integer>();
		for (Order order : orderList) {
			Goods goods = goodsManager.getGoods(order.getGoodsId());
			Date date = new Date();
			String pointDate = DateUtil.getDateTime("yyyy-MM-dd", date);
			GoodsPoint gp = goodsPointManager.getGPByGoodsIdAndPointDate(goods.getId(), pointDate);
			if (null == gp) {
				GoodsPoint goodsPoint = new GoodsPoint();
				try {
					goodsPoint.setPointDate(new SimpleDateFormat("yyyy-MM-dd").parse(pointDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				goodsPoint.setClickCount(0);
				goodsPoint.setGoodsCat(goods.getCatCode());
				goodsPoint.setGoodsId(goods.getId());
				goodsPoint.setHighPopularPoint(0);
				goodsPoint.setHotSalePoint((int) order.getGoodsNumber());
				goodsPoint.setSaleCount((int) order.getGoodsNumber());
				goodsPoint.setTradeCount(1);
				goodsPointManager.addGoodsPoint(goodsPoint);
			}
			if (null != gp) {
				if (null == goodsMap.get(goods.getId()) || !goodsMap.get(goods.getId()).equals(1)) {
					gp.setTradeCount(gp.getTradeCount() + 1);
				}
				int oldcount = gp.getSaleCount();
				int newcount = oldcount + (int) order.getGoodsNumber();
				gp.setSaleCount(newcount);
				int hotSalePoint = gp.getHotSalePoint().intValue();
				hotSalePoint = hotSalePoint + (int) order.getGoodsNumber();
				gp.setHotSalePoint(hotSalePoint);
				goodsPointManager.editGoodsPoint(gp);
			}
			goodsMap.put(goods.getId(), 1);
		}
	}

	/**
	 * jason方式修改订单金额
	 * 
	 * @param tradeId
	 *            订单的ID
	 * @param amount
	 *            新的金额
	 * @return 返回操作信息
	 */
	@RequestMapping("/trade/closeJsonTrade")
	@ResponseBody
	public Object closeJsonTrade(@RequestParam(value = "param1", required = false) String tradeId,
			@RequestParam(value = "param2", required = false) String amount, AdminAgent agent) {
		String message;
		if (agent == null) {
			message = "没有登录，操作失败!";
			return message;
		}
		if (StringUtil.isBlank(tradeId)) {
			message = "订单为空，操作失败!";
			return message;
		}
		Trade trade = tradeManager.getTrade(Long.valueOf(tradeId));
		if (trade == null) {
			message = "订单id不存在，操作失败!";
			return message;
		}

		if (EnumTradeStatus.TRADE_CLOSE.getKey().equals(trade.getStatus())) {
			message = "订单已经是关闭状态，不允许操作!";
			return message;
		}

		// 只有等待买家付款状态的订单才可以关
//		if (!trade.getStatus().trim().equals(EnumTradeStatus.WAIT_BUYER_PAY.getKey())) {
//			if (!(EnumExpressDistPayment.GOODS_FIRST.getKey().equals(trade.getExpressPayment()))
//					&& !(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(trade.getStatus()))) {
//				message = "订单状态不正确，操作失败!";
//				return message;
//			}
//		}

		// User user = this.getLoginUser();
		// AdminUser user = getLoginAdminUser();
		// 这里需要判断这个用户是否是商城管理?只有商家,和管理员,以及买家才有权力修改订单金额.
		// if (!user.getId().equals(trade.getBuyId())) {
		// return "['false','only admin or buyer can close it!']";
		// }

		int flag = tradeManager
				.updateTradeStatus(String.valueOf((trade.getId())), EnumTradeStatus.TRADE_CLOSE.getKey());
		if (flag == 1) {
			message = "success";
		} else if (flag == 2) {
			message = "状态不对，不允许修改!";
		} else {
			message = "操作失败!";
		}

		return message;
	}

	/**
	 * json方式发货
	 * 
	 * @param tradeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trade/sendJsonGoods")
	@ResponseBody
	public Object sendJsonGoods(@RequestParam(value = "param1", required = false) String tradeId,
			@RequestParam(value = "param2", required = false) String tradeType, AdminAgent agent) throws Exception {
		String message;

		if (agent == null) {
			message = "没有登录，操作失败!";
			return message;
		}

		if (StringUtil.isBlank(tradeId)) {
			message = "订单为空，操作失败!";
			return message;
		}

		Trade trade = tradeManager.getTrade(Long.valueOf(tradeId));
		String[] tIds = new String[1];
		tIds[0] = trade.getTid();
		String result = checkTrade(trade);// 校验订单方法
		if (result != null) {
			return result;
		}

		//判定有效地实际库存是否足够分配 zhangwy 2011/05/16
		boolean isfull =  inAndOutDepositoryManager.judgeFactStorage(tIds[0]);
		if(!isfull){
			message = "产品实际库存不足,不能配货！";
			return message;
		}
		
		Long outId = new Long(0);
		try {
			outId = inAndOutDepositoryManager.addOutDepository(tIds, agent);
			trade.setStatus(EnumTradeStatus.WAIT_DISTRIBUTION.getKey());
			trade.setGmtModify(new Date());
			tradeManager.editTrade(trade);

			if ("2".equals(tradeType)) {// 调用拍拍接口修改订单状态
				this.updateInterfaceTrade(trade);
			}
			if ("3".equals(tradeType)) {// 调用淘宝接口修改订单状态
				this.updateInterfaceTradeForTaobao(trade);
			}

		} catch (Exception e) {
			log.error("the Trade add outDepository fail!", e);
			message = "配货失败!";
			return message;
		}
		String re = OutDepositoryAuto(outId);
		if ("error".equals(re)) {
			return "error";
		}
		message = "success";
		return message;
	}

	/**
	 * 合并配货
	 * 
	 * @param tradeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trade/mergeSendGoods")
	public String mergeSendGoods(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent, HttpServletRequest req) throws Exception {
		String errorInfo;

		if (agent == null) {
			errorInfo = "没有登录，操作失败!";
			model.addAttribute("errorInfo", errorInfo);
			return showAdminTradeList(query, null, currPage, model, pageSize, agent);
		}

		String[] ids = req.getParameterValues("ids");
		if (ids.length < 2) {
			errorInfo = "请选择两个以上销售订单进行合并配货!";
			model.addAttribute("errorInfo", errorInfo);
			return showAdminTradeList(query, null, currPage, model, pageSize, agent);
		}

		// 循环判断订单
		for (String tId : ids) {
			if (StringUtil.isBlank(tId)) {
				errorInfo = "订单为空，操作失败!";
				model.addAttribute("errorInfo", errorInfo);
				return showAdminTradeList(query, null, currPage, model, pageSize, agent);
			}
			Trade trade = tradeManager.getTradeByTid(tId);
			if (trade == null) {
				errorInfo = "订单id不存在，操作失败!";
				model.addAttribute("errorInfo", errorInfo);
				return showAdminTradeList(query, null, currPage, model, pageSize, agent);
			}

			String result = checkTrade(trade);// 校验订单方法
			if (result != null) {
				errorInfo = "合并配货失败！原因：" + result;
				model.addAttribute("errorInfo", errorInfo);
				return showAdminTradeList(query, null, currPage, model, pageSize, agent);
			}
			//判定有效地实际库存是否足够分配 zhangwy 2011/05/16
			boolean isfull =  inAndOutDepositoryManager.judgeFactStorage(tId);
			if(!isfull){
				errorInfo = "订单号:"+tId+"的产品实际库存不足,不能合并配货！";
				model.addAttribute("errorInfo", errorInfo);
				return showAdminTradeList(query, null, currPage, model, pageSize, agent);
			}
		}
		
		//判定有效地实际库存是否足够分配 zhangwy 2011/05/17(合并判定)
		boolean isfull =  inAndOutDepositoryManager.judgeFactStorageMerge(ids);
		if(!isfull){
			errorInfo = "合并订单后的产品实际库存不足,不能合并配货！";
			model.addAttribute("errorInfo", errorInfo);
			return showAdminTradeList(query, null, currPage, model, pageSize, agent);
		}
		// 修改订单状态
		for (String tId : ids) {
			Trade trade = tradeManager.getTradeByTid(tId);
			try {
				trade.setStatus(EnumTradeStatus.WAIT_DISTRIBUTION.getKey());
				trade.setGmtModify(new Date());
				tradeManager.editTrade(trade);

				if ("2".equals(trade.getTradeType())) {// 调用拍拍接口修改订单状态
					this.updateInterfaceTrade(trade);
				}
				if ("3".equals(trade.getTradeType())) {// 调用淘宝接口修改订单状态
					this.updateInterfaceTradeForTaobao(trade);
				}

			} catch (Exception e) {
				log.error("the Trade add outDepository fail!", e);
				errorInfo = "合并配货失败!";
				model.addAttribute("errorInfo", errorInfo);
				return showAdminTradeList(query, null, currPage, model, pageSize, agent);
			}
		}
		Long outId = inAndOutDepositoryManager.addOutDepository(ids, agent);
		String re = OutDepositoryAuto(outId);// 自动分配库位
		if ("error".equals(re)) {
			errorInfo = "提醒：合并配货成功，自动分配库位出错！";
			model.addAttribute("errorInfo", errorInfo);
		}
		return showAdminTradeList(query, null, currPage, model, pageSize, agent);
	}

	/*
	 * 提取的校验订单方法
	 * 
	 * @param trade
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	private String checkTrade(Trade trade) {
		String message;

		// 只有等待发货状态的订单才可以发
		if (!trade.getStatus().trim().equals(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey())) {
			message = "交易状态不符合，不允许操作。 [" + EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getValue() + "] !";
			return message;
		}

		Refund refund = refundManager.getRefundByOrder(trade.getTid());
		if (refund != null) {
			if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())
					|| EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())
					|| EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(refund.getStatus())) {

			} else {
				message = "已经退款，不能配货，配货不成功";
				return message;
			}
		}
		// 加入判断，若订单中有商品的实际库存不够时，不允许配货 zhangwy
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		if (orderList != null) {
			for (Order temp : orderList) {
				int factStorageNum = 0;
				Map tempMap = new HashMap();
				tempMap.put("goodsInstanceId", temp.getGoodsInstanceId());
				tempMap.put("depfirstId", trade.getDepFirstId());
				factStorageNum = storageManager.getStorageNumBySend(tempMap);
				if (factStorageNum < temp.getGoodsNumber()) {
					message = "订单中 [" + temp.getGoodsTitle() + "] 的实际库存不够, 不能进行配货!";
					return message;
				}
			}
		} else {
			message = "订单内容为空!";
			return message;
		}

		// 加入判断，如果有库位在盘点，则不允许配货 chenhang
		for (Order temp : orderList) {
			Map tempMap = new HashMap();
			tempMap.put("goodsInstanceId", temp.getGoodsInstanceId());
			tempMap.put("depfirstId", trade.getDepFirstId());
			List<Storage> sList = storageManager.getStorageListsByMap(tempMap);
			long compare = 0;
			for (Storage sTemp : sList) {
				DepLocation depLocation = depLocationManager.getDepLocationByLocationId(sTemp.getLocId());
				Depository depository = depositoryService.getDepository(depLocation.getDepId());
				if (depLocationManager.getIsCheckCountById(new Long(sTemp.getLocId()),
						EnumDepLocationIsCheck.Y.getKey()) > 0 || !(EnumDepositoryType.COMMON_DEP.getKey().equals(depository.getType()))) {
					// 盘点中，不记录库存数
				} else {
					compare = compare + sTemp.getStorageNum();
				}
			}
			if (compare < temp.getGoodsNumber()) {
				// 盘点中
				message = "订单中[" + temp.getGoodsTitle() + "]有库位在盘点";
				return message;
			}
		}
		return null;
	}

	/**
	 * 
	 * 功能：将关闭订单改成配货状态<br>
	 * 
	 * @return
	 * @author shenzh Nov 4, 2010
	 */
	@RequestMapping("/trade/onResumeJson")
	@ResponseBody
	public Object onResumeJson(@RequestParam(value = "param1", required = false) String tradeId,
			@RequestParam(value = "param2", required = false) String tradeType, AdminAgent agent) {
		String message;

		if (agent == null) {
			message = "没有登录，操作失败!";
			return message;
		}
		if (StringUtil.isBlank(tradeId)) {
			message = "订单为空，操作失败!";
			return message;
		}
		Trade trade = tradeManager.getTrade(Long.valueOf(tradeId));
		if (trade == null) {
			message = "订单id不存在，操作失败!";
			return message;
		}
		// 只有关闭状态的订单才可以恢复
		if (!trade.getStatus().trim().equals(EnumTradeStatus.TRADE_CLOSE.getKey())) {

			message = "交易状态不符合，不允许操作。 [" + EnumTradeStatus.TRADE_CLOSE.getValue() + "] !";
			return message;
		}
		Refund refund = refundManager.getRefundByOrder(trade.getTid());
		if (refund != null) {
			if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())
					|| EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())
					|| EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(refund.getStatus())) {

			} else {
				message = "已经退款，不能恢复，恢复不成功";
				return message;
			}
		}
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		if (orderList != null) {
			// 加入判断，若订单中有商品的可用库存不够时，不允许恢复
			for (Order temp : orderList) {
				Long existNum = storageManager.sumStorageByGoodsInstanceId(temp.getGoodsInstanceId(),
						trade.getDepFirstId(), "exist");
				if (existNum < temp.getGoodsNumber()) {
					message = "订单中 [" + temp.getGoodsTitle() + "] 的可用库存不够, 不能进行恢复!";
					return message;
				}
			}
			for (Order temp : orderList) {
				// 更新可用库存 要加上仓库ID
				goodsInstanceManager.updateAmountForTwo(temp.getGoodsInstanceId(), temp.getGoodsId(),
						(0 - temp.getGoodsNumber()), trade.getDepFirstId(), true);
				// 更新销售数量
				//2018-7-26注释（已售库存，在早上恢复库存时统计）
//				goodsManager.updateSaleNumberById(temp.getGoodsId(), temp.getGoodsNumber());
			}
		} else {
			message = "订单内容为空!";
			return message;
		}

		try {
			// 更改状态，等待商家发货
			trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
			/* begin add shenzh Nov 23, 2010 说明： 同时更改缺货状态 */
			trade.setStockoutStatus("n");
			/* end by shenzh Nov 23, 2010 */
			trade.setGmtModify(new Date());
			tradeManager.editTrade(trade);

		} catch (Exception e) {
			log.error("the Trade resume fail!", e);
			message = "恢复失败!";
			return message;
		}
		message = "success";
		return message;
	}

	/**
	 * @throws Exception
	 * 
	 * @Title: OutDepositoryAuto
	 * @Description: TODO
	 * @param @param trade
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @author chenhang 2010/11/08 销售订单自动入库
	 * @throws
	 */
	private String OutDepositoryAuto(Long outId) throws Exception {
		// added by chenhang 2010/11/04 入库 销售订单自动分配库位
		if (outId != null && outId != 0) {
			OutDepository outDep = outDepositoryManager.getOutDepository(outId);// 获得出库单
			Long outDepId = outDep.getId();

			String batchNums = null;
			// 取得出库单主表信息
			OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(outDepId);
			// 取得出库单详情信息
			List<OutDetailGoods> outDetailGoodsLists = outDetailManager.getOutDetailGoodsLists(outDepositoryDispaly
					.getId());

			Long otpId;
			if (outDetailGoodsLists != null && outDetailGoodsLists.size() > 0) {
				for (OutDetailGoods outDetailGoodsInfo : outDetailGoodsLists) {
					outDetailGoodsInfo.setCatName(categoryManager.getCatFullNameByCatcode(outDetailGoodsInfo
							.getCatCode()));
					outDetailGoodsInfo.setAttributeName(attributeManager
							.getFullAttributeStringByAttrs(outDetailGoodsInfo.getAttrs()));
					Boolean f = true;// 用于判定是否是第一次进入循环，以利用差值进行计算
					otpId = outDetailGoodsInfo.getId();
					// 取得出库主表信息
					OutDepository outDeposioryTemp = outDepositoryManager.getOutDepositoryByDetailId(otpId);
					// 取得基本信息
					OutDetailBaseInfo outDetailBaseInfo = outDetailManager.getOutDetailBaseInfo(otpId,
							outDeposioryTemp.getType());
					if (outDetailBaseInfo == null) {
						return "error";
					}

					if (StringUtil.isNotBlank(outDetailBaseInfo.getDepFirstId())) {
						DepositoryFirst df = depositoryFirstManager.getDepositoryById(new Long(outDetailBaseInfo
								.getDepFirstId()));
						if (df != null) {
							outDetailBaseInfo.setDepFirstName(df.getDepFirstName());
						}
					}

					List<String> storageIdList = new ArrayList<String>();// 仓库ID
					List<String> disCountList = new ArrayList<String>();// 出库数量
					List<String> locIdList = new ArrayList<String>();// 库位

					// 取得库存数据
					if (StringUtil.isBlank(outDetailBaseInfo.getDepFirstId())) {
						return "success";
					}

					Map mapSearch = new HashMap();
					mapSearch.put("goodsInstanceId", outDetailBaseInfo.getGoodsInstanceId());
					mapSearch.put("supplierId", outDetailBaseInfo.getSupplierId());
					mapSearch.put("batchNums", batchNums);
					mapSearch.put("depFirstId", new Long(outDetailBaseInfo.getDepFirstId()));
					mapSearch.put("isWholesale", outDeposioryTemp.getIsWholesale());
					mapSearch.put("tid", outDeposioryTemp.getTid());
					int i = 0;// 变量 用于循环
					List<OutDepositoryStorage> outStorageList = outDetailManager.getOutStorageList(mapSearch);
					long[] x = new long[outStorageList.size()];// 如果库位库存不足，则保留差值，从下一个库位出货
					for (OutDepositoryStorage outDepositoryStorageInfo : outStorageList) {
						Map getAmountMap = new HashMap();
						getAmountMap.put("outDepId", outDetailBaseInfo.getOutDepositoryId());
						getAmountMap.put("goodsInstanceId", outDetailBaseInfo.getGoodsInstanceId());
						getAmountMap.put("goodsId", outDetailBaseInfo.getGoodsId());
						getAmountMap.put("outDetailId", outDetailBaseInfo.getOutDetailId());
						getAmountMap.put("storageId", outDepositoryStorageInfo.getId());
						Long oriCount = prodRelationOutManager.getDistributedAmount(getAmountMap);
						outDepositoryStorageInfo.setOriCount(oriCount == null ? "" : String.valueOf(oriCount));
						Boolean fl = false;

						if (depLocationManager.getIsCheckCountById(new Long(outDepositoryStorageInfo.getLocId()),
								EnumDepLocationIsCheck.Y.getKey()) > 0) {
							continue;// 判断该库位是否正在盘点，如果是，进入下一次循环
						}

						if (f) {// 如果是第一次进循环，用需要的库存数判断，如果不是用差值判断
							if (outDetailGoodsInfo.getOutNum() <= outDepositoryStorageInfo.getStorageNum()) {
								disCountList.add(outDetailGoodsInfo.getOutNum().toString());
								fl = true;// 如果第一个库位就满足分配，则在最后判断跳出循环
							} else {
								disCountList.add(outDepositoryStorageInfo.getStorageNum().toString());
								x[i] = outDetailGoodsInfo.getOutNum() - outDepositoryStorageInfo.getStorageNum();
							}
						} else {
							if (x[i] <= outDepositoryStorageInfo.getStorageNum()) {
								disCountList.add(String.valueOf(x[i]));
								fl = true;// 如果当前库位就满足分配，则在最后判断跳出循环
							} else {
								disCountList.add(outDepositoryStorageInfo.getStorageNum().toString());
								x[i] = x[i] - outDepositoryStorageInfo.getStorageNum();
							}
						}
						f = false;
						storageIdList.add(outDepositoryStorageInfo.getId().toString());
						locIdList.add(outDepositoryStorageInfo.getLocId().toString());
						if (fl == true) {
							break;// 如果第一个库位就满足分配，则跳出循环
						}

					}
					String allowedInfo = outDepositoryAllowed(outDetailBaseInfo, storageIdList, disCountList, locIdList);

					String errorInfo;
					Boolean succFlag;

					if (StringUtil.isBlank(allowedInfo)) {
						// 商品出库
						Map map = new HashMap();
						map.put("outDetailBaseInfo", outDetailBaseInfo);
						map.put("storageIdList", storageIdList);
						map.put("disCountList", disCountList);
						map.put("locIdList", locIdList);
						map.put("isWholesale", outDeposioryTemp.getIsWholesale());
						map.put("tid", outDeposioryTemp.getTid());
						Boolean optFlag = outDetailManager.outDepositoryOptAuto(map, outDeposioryTemp.getType());
						if (optFlag) {
							errorInfo = null;
							succFlag = Boolean.TRUE;
						} else {
							errorInfo = "库存商品出库分配失败";
							succFlag = Boolean.FALSE;
						}
					} else {
						errorInfo = allowedInfo;
						succFlag = Boolean.FALSE;
					}
					i = i + 1;
				}
			}
			return "";
		} else {
			return "error";
		}
		// ended by chenhang 2010/11/04 入库 销售订单自动分配库位
	}

	/**
	 * 判断是否可以出库
	 * 
	 * @param outDetailBaseInfoForOut
	 *            OutDetailBaseInfo
	 * @param storageIdArray
	 *            String[]
	 * @param disCountArray
	 *            String[]
	 * @param locIdArray
	 *            String[]
	 * @return String
	 * @author chenhang 2010/10/04
	 */
	private String outDepositoryAllowed(OutDetailBaseInfo outDetailBaseInfoForOut, List<String> storageIdList,
			List<String> disCountList, List<String> locIdList) {
		// 出库基本信息丢失则直接返回错误信息
		if (outDetailBaseInfoForOut == null) {
			return "出库基本信息丢失";
		}
		// 输入的出库总数
		Long inputCount = 0L;
		for (int i = 0; i < disCountList.size(); i++) {
			if (StringUtil.isNotBlank(disCountList.get(i))) {
				inputCount = inputCount + new Long(disCountList.get(i));
			}
		}
		return null;
	}

	/*
	 * 通过接口修改订单信息 @param parMap @return
	 */
	private boolean updateInterfaceTrade(Trade trade) {
		InterfaceApply interfaceApply = interfaceApplyManager.getInterfaceApplyByUserId(trade.getBuyId(),
				EnumInterfaceType.PAIPAI.getKey());
		InterfaceUserTrade interfaceUserTrade = interfaceUserTradeManager.getInterfaceUserTradeByTid(trade.getTid());

		if (interfaceApply != null) {
			TreeMap<String, String> signParams = new TreeMap<String, String>();
			signParams.put("uin", interfaceApply.getParamOne());
			signParams.put("token", interfaceApply.getParamTwo());
			signParams.put("spid", interfaceApply.getParamThree());
			signParams.put("seckey", interfaceApply.getParamFour());
			signParams.put("sellerUin", interfaceApply.getParamOne());

			if (interfaceUserTrade != null) {
				String itemUrl = "";
				Map<String, String> resultMap = null;
				// 卖家标记订单配货中
				itemUrl = Billing_Deal_DetailsUtil.CreateDealPreparingUrl(signParams,
						interfaceUserTrade.getPaipaiTradeId());
				resultMap = Billing_Deal_DetailsUtil.parseInterfaceDealStatusXml(itemUrl);
				// 如果修改成功
				if ("0".equals(resultMap.get("errorCode"))) {
					return true;
				} else {
					log.error("修改接口订单[" + interfaceUserTrade.getPaipaiTradeId() + "]失败！",
							new Exception(resultMap.get("errorMessage")));
				}
			}
		}
		return false;
	}

	/**
	 * 通过淘宝接口修改订单信息
	 * 
	 * @param parMap
	 * @return
	 */
	private boolean updateInterfaceTradeForTaobao(Trade trade) {
		TaobaoApply taobaoApply = taobaoInterfaceApplyManager.getInterfaceApplyByUserId(trade.getBuyId(),
				EnumInterfaceType.TAOBAO.getKey());
		InterfaceUserTrade interfaceUserTrade = interfaceUserTradeManager.getInterfaceUserTradeByTid(trade.getTid());

		if (taobaoApply != null) {
			/* 调用淘宝接口 */
			/* 淘宝未提供该接口 */
		}
		return false;
	}

	/**
	 * Json方式确认客户已收到货
	 * 
	 * @param tradeId
	 *            Long
	 * @return String
	 * @author shengyong 2010/01/26
	 */
	@RequestMapping("/trade/confirmJsonPay")
	@ResponseBody
	public Object confirmJsonPay(@RequestParam(value = "param1", required = false) String tradeId, AdminAgent agent) {
		Map<String, String> result = new HashMap<String, String>();

		String message;
		if (agent == null) {
			message = "没有登录，操作失败!";
			result.put("message", message);
			return result;
		}

		if (StringUtil.isBlank(tradeId)) {
			message = "订单为空，操作失败!";
			result.put("message", message);
			return result;
		}

		Trade trade = tradeManager.getTrade(Long.valueOf(tradeId));
		if (trade == null) {
			message = "订单id不存在，操作失败!";
			result.put("message", message);
			return result;
		}

		if (trade != null
				&& StringUtil.isNotBlank(trade.getTid())
				&& (EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey().equals(trade.getStatus()) || EnumTradeStatus.TRADE_FINISH
						.getKey().equals(trade.getStatus())) && "goods_first".equals(trade.getExpressPayment())
				&& !"paid".equals(trade.getPayStatus())) {
			tradeManager.updatePayTimeByTid(new Long(trade.getTid()));
			PayRecord payRecord = new PayRecord();
			payRecord.setPayPlatform("货到付款");
			payRecord.setTid(trade.getTid());
			payRecordManager.addPayRecord(payRecord);
			// 更新商品热销分数
			updateGoodsPoint(tradeId.toString());
			result.put("flag", "success");
			result.put("payDate", DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date()));

			message = "success";
		} else {
			message = "请勿重复刷新页面!";
		}
		result.put("message", message);

		return result;
	}

	/***************************************************************************
	 * 订单Excel导出
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trade/exportOrder")
	public void exportOrder(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "statusOpp", required = false) String statusOpp, AdminAgent agent,
			HttpServletResponse res, Model model) throws Exception {
		// int count = tradeManager.getTradesCountByParameterMap(parMap);
		// page = new Page();
		// page.setPageSize(pageSize);
		// page.setTotalRowsAmount(count);
		// page.setCurrentPage(currentPage);
		// tradeList = tradeManager.getTradesByParameterMap(parMap, null);

		OutputStream outwt = null;
		selectOrderType(query.getOrderType(), query, agent);// 根据权限选择订单类型
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=trade" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
			if (depfirstIdList != null && depfirstIdList.size() > 0) {
				query.setDepfirstIds(depfirstIdList);
			}

			if (depfirstIdList != null) {
				if (StringUtil.isNotBlank(statusOpp)) {
					if (StringUtil.isNotBlank(query.getStatus())) {
						query.setOpp(query.getStatus());
						query.setStatus(null);
					} else {
						query.setStatus("opp");
					}
				}
			}

			List<String[]> tradeExportList = new ArrayList<String[]>();
			String[] title = { "订单号", "下单时间", "买家昵称", "总金额(元)", "付款时间", "物流方式", "订单状态", "是否发票", "物流公司", "物流单号" };
			tradeExportList.add(title);
			List<Trade> tradeListAll = tradeManager.getTradesExcelByParameterMap(query);
			if (tradeListAll != null) {
				df.applyPattern("yyyy-MM-dd HH:mm:ss");

				for (Trade t : tradeListAll) {
					String createTime = "";
					String payTime = "";
					String invoice = "";
					if (t.getGmtCreate() != null) {
						createTime = df.format(t.getGmtCreate());
					}
					if (t.getPayTime() != null) {

						payTime = df.format(t.getPayTime());
					}
					if ("y".equals(t.getInvoice())) {
						invoice = "是";
					} else {
						invoice = "否";
					}
					String[] data = { t.getTid(), createTime, t.getBuyNick(),
							DoubleUtil.add(t.getGoodsAmount(), t.getShippingAmount()) + "", payTime,
							EnumExpressDistPayment.toMap().get(t.getExpressPayment()),
							EnumTradeStatus.toMap().get(t.getStatus()), invoice, t.getExpressName(), t.getExpressCode() };
					tradeExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, tradeExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "导出失败！");
			log.error("", e);
		} finally {
			close(outwt);
		}
	}

	/***************************************************************************
	 * 订单商品Excel导出
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trade/exportOrderTwo")
	public void exportOrderTwo(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "statusOpp", required = false) String statusOpp, AdminAgent agent,
			HttpServletResponse res, Model model) throws Exception {
		OutputStream outwt = null;
		selectOrderType(query.getOrderType(), query, agent);// 根据权限选择订单类型

		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=trade" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
			if (depfirstIdList != null && depfirstIdList.size() > 0) {
				query.setDepfirstIds(depfirstIdList);
			}

			if (depfirstIdList != null) {
				if (StringUtil.isNotBlank(statusOpp)) {
					if (StringUtil.isNotBlank(query.getStatus())) {
						query.setOpp(query.getStatus());
						query.setStatus(null);
					} else {
						query.setStatus("opp");
					}
				}
			}

			List<String[]> tradeExportList = new ArrayList<String[]>();
			String[] title = { "订单行", "订单号", "订单状态", "买家昵称", "商品编码", "产品名称", "产品属性", "计量单位", "数量", "采购单价（元）", "采购金额",
					"需求日期", "单价（元）", "总额（元）", "运费", "收货人", "联系电话", "收货地址", "物流" };
			tradeExportList.add(title);
			List<Trade> tradeListAll = tradeManager.getTradesByParameterMap(query);
			if (tradeListAll != null) {
				df.applyPattern("yyyy-MM-dd HH:mm:ss");
				int orderNum = 1;
				for (Trade tmp : tradeListAll) {
					List<Order> orderList = orderManager.getOrdersNotInPackage(tmp.getTid());
					// 取得详细的收货地址
					String address = this.regionManager.getAddressInfo(tmp.getProvince(), tmp.getCity(),
							tmp.getDistrict());
					address += tmp.getAddress();
					// 取得物流方式
					String expressName = "";
					if (tmp.getExpressId() != null) {
						Express expressTemp = expressManager.getExpress(tmp.getExpressId());
						if (expressTemp != null) {
							expressName = expressTemp.getExpressName();
						}
					}

					if (orderList != null)
						for (Order order : orderList) {

							Goods good = goodsManager.getGoods(order.getGoodsId());
							GoodsInstance g = goodsInstanceManager.getInstance(order.getGoodsInstanceId());

							String[] data = {
									orderNum + "",
									tmp.getTid(),
									EnumTradeStatus.toMap().get(tmp.getStatus()),
									tmp.getBuyNick(),
									(good != null && good.getGoodsSn() != null) ? good.getGoodsSn() : "",
									order.getGoodsTitle(),
									(g != null && g.getAttrs() != null) ? attributeManager
											.getFullAttributeStringByAttrs(g.getAttrs()) : "",
									(g != null && g.getGoodsUnit() != null) ? g.getGoodsUnit() : "",
									order.getGoodsNumber() + "", "", "", "", order.getGoodsPrice() + "",
									getFormatNumber(order.getGoodsNumber() * order.getGoodsPrice()),
									tmp.getShippingAmount() + "", tmp.getReceiver(), tmp.getMobile(), address,
									expressName };
							tradeExportList.add(data);
							orderNum++;
						}
				}
			}
			goodsBatch.exportExcel(outwt, tradeExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "导出失败！");
			log.error(e);
		} finally {
			close(outwt);
		}
	}

	/***************************************************************************
	 * 开票Excel导出
	 * 
	 * @author fangqing
	 * @since 20101216
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/trade/exportOrderThree")
	public void exportOrderThree(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "statusOpp", required = false) String statusOpp, AdminAgent agent,
			HttpServletResponse res, Model model) throws Exception {
		OutputStream outwt = null;
		selectOrderType(query.getOrderType(), query, agent);// 根据权限选择订单类型

		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=invoice" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
			if (depfirstIdList != null && depfirstIdList.size() > 0) {
				query.setDepfirstIds(depfirstIdList);
			}

			if (depfirstIdList != null) {
				if (StringUtil.isNotBlank(statusOpp)) {
					if (StringUtil.isNotBlank(query.getStatus())) {
						query.setOpp(query.getStatus());
						query.setStatus(null);
					} else {
						query.setStatus("opp");
					}
				}
			}

			List<String[]> tradeExportList = new ArrayList<String[]>();
			String[] title = { "单据编号", "购方名称", "购方税号", "地址电话", "银行帐号", "商品名称", "规格型号", "计量单位", "数量", "单价", "金额",
					"单据日期", "收款人", "复核人", "折扣", "备注", "买家备注", "客服备注", "发票抬头", "开票公司" };
			tradeExportList.add(title);

			List<Trade> tradeListAll = null;

			// 若指定了开票公司，则加入对订单号的限定
			if (query.getRorder() != null) {
				Set<String> tids = tradeManager.getTradeGoodsBill(null, query.getRorder()).keySet();

				if (!tids.isEmpty()) {
					List<Trade> _tradeListAll = tradeManager.getTradesByParameterMapWhitNote(query);
					// 过滤
					tradeListAll = new ArrayList<Trade>();
					if (_tradeListAll != null) {
						for (Trade t : _tradeListAll) {
							if (tids.contains(t.getTid())) {
								tradeListAll.add(t);
							}
						}
					}
				}
			} else {
				tradeListAll = tradeManager.getTradesByParameterMapWhitNote(query);
			}

			if (tradeListAll != null) {
				List<String> tids = new ArrayList<String>();
				for (Trade t : tradeListAll) {
					tids.add(t.getTid());
				}

				Map<String, Long> tradeBillIdMap = tradeManager.getTradeGoodsBill(tids, null);

				Map<String, Resources> tradeBillMap = new HashMap<String, Resources>();
				for (Entry<String, Long> en : tradeBillIdMap.entrySet()) {
					Resources bill = resourcesManager.getResources(en.getValue());
					tradeBillMap.put(en.getKey(), bill);
				}

				df.applyPattern("yyyy-MM-dd HH:mm:ss");
				for (Trade tmp : tradeListAll) {
					List<Order> orderList = orderManager.getOrdersNotInPackage(tmp.getTid());
					// 取得详细的收货地址
					String address = this.regionManager.getAddressInfo(tmp.getProvince(), tmp.getCity(),
							tmp.getDistrict());
					address += tmp.getAddress();
					// 取得出库单主表信息
					OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepositoryByTid(tmp.getTid());

					if (orderList != null)
						for (Order order : orderList) {
							/*
							 * Goods good = goodsManager.getGoods(order .getGoodsId());
							 */
							GoodsInstance g = goodsInstanceManager.getInstance(order.getGoodsInstanceId());
							String allAmount = getFormatNumber(order.getGoodsNumber() * order.getGoodsPrice());
							String[] data = {
									tmp.getTid(),
									"个人", // tmp.getReceiver() 暂时使用个人代替 modified by chenyan 2011/03/16
									"",
									address + " " + (tmp.getMobile() != null ? tmp.getMobile() : ""),
									"",
									order.getGoodsTitle(),
									"",
									(g != null && g.getGoodsUnit() != null) ? g.getGoodsUnit() : "",
									order.getGoodsNumber() + "",
									order.getGoodsPrice() + "",
									allAmount + "",
									DateUtil.getDate(new Date()),
									"",
									"",
									"",
									(outDepositoryDispaly != null ? outDepositoryDispaly.getBillNum() : ""),
									tmp.getBuyerNote(),
									tmp.getSeviceNote(),
									tmp.getInvoiceName(),
									tradeBillMap.get(tmp.getTid()) != null ? tradeBillMap.get(tmp.getTid()).getValue()
											: "" };
							tradeExportList.add(data);
						}
				}
			}
			goodsBatch.exportExcel(outwt, tradeExportList);
			outwt.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			close(outwt);
		}
	}

	/**
	 * 格式化金额保留小数后两位 fangqing 20101218
	 * 
	 * @param numberStr
	 * @return
	 */
	private String getFormatNumber(double numberStr) {
		BigDecimal bigDecimal = new BigDecimal(numberStr);
		String formateNumer = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		return formateNumer;
	}

	private static void close(OutputStream out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
	
	/**
	 * 输入产品Id，查找产品
	 * 
	 * @param productId
	 *            Long
	 * @author songfy 2012-12-10
	 */
	@RequestMapping("/trade/findProductJson")
	@ResponseBody
	public Product findProductJson(@RequestParam("productId") Long productId,@RequestParam("orderId")Long orderId,@RequestParam("goodsInstanceId")Long goodsInstanceId) {
		//参数为空返回
		if(productId==null)
		{
			return null;
		}

		Order order =  orderManager.getOrder(orderId);

		Map<String,Object> parMap = new HashMap<String,Object> ();
		parMap.put("productId", productId);
		Product product = productService.getProductByMap(parMap);
		boolean type = order.getGoodsInstanceId().longValue() != product.getInstanceId().longValue(); //添加的产品型号和该产品的型号是否相同
		boolean status = product.getIdStatus().longValue()!=1; //状态是否为可售
		if(type)
		{
				product.setType("false");//产品型号出错
		}
		if(status)
		{
			product.setIdStatus(100L); //产品状态不可售
		}
		//型号和状态都对应后才可以加入关联表并更新productId的状态为预订
		if(product != null && product.getInstanceId().equals(goodsInstanceId) && !type && !status)
		{
			parMap.put("status", 3);
			productService.updateProductStatusByProductId(parMap);//更新状态为预订
			productService.insertProductVSOrderId(orderId, productId);
			
		}
		product.setBrandname(EnumBrandType.toMap().get(product.getIdBrand().toString()));
		product.setSeriesname(EnumSeriesType.toMap().get(product.getIdSeries().toString()));
		
		return product;
	}
	
	/**
	 * 修改订单产品价格初始化
	 * 
	 * @param productId
	 *            Long
	 * @author songfy 2012-12-10
	 */
	@RequestMapping("/trade/udtppinit")
	public String updateTradeProductPriceInit(@RequestParam(value = "tradeId", required = false) String tid,
			@RequestParam(value = "refundId", required = false) String refundId, Model model, AdminAgent agent) {
		// 如果传入的参数是refund_id,则根据refund_id查询对应的tid
		if (!StringUtil.isBlank(refundId)) {
			Refund refund = refundManager.getRefundByRefundId(refundId);
			if (refund != null) {
				String tradeId = refund.getTid();
				Trade trade = tradeManager.getTradeByTid(tradeId);
				if (trade != null) {
					tid = trade.getTid();
				}
			}
		}

		// 交易信息
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		if (trade != null) {


			// 订单中的普通商品信息 加入重量等信息 zhangwy
			List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
			model.addAttribute("orderList", orderList);
			
			if (orderList != null) {
				for (Order o : orderList) {
					GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
					if(g.getHkExistNum()!=null)
						o.setInstanceHkExistNum(g.getHkExistNum().longValue());
				}
			}

		}

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());

		return "/trade/updateTradePrice";
	}
	
	
	/**
	 * 修改订单产品价格
	 * 
	 * @param productId
	 *            Long
	 * @author songfy 2012-12-10
	 */
	@RequestMapping("/trade/udtpp")
	@ResponseBody
	public Order updateTradeProductPrice(@RequestParam("orderId") Long orderId,
			@RequestParam("tid") String tid,
			@RequestParam("price") Double price,AdminAgent agent) {
		//参数为空返回
//		if(StringUtil.isBlank(orderIdsPrice)){
//			return "['false','edit failed!']";
//		}
		
		Order order = orderManager.getOrder(orderId);
		if(order==null){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		Trade trade = tradeManager.getTradeByTid(tid);
		if(trade==null){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		
		if(!order.getTid().equals(tid)){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		//价差
		double com = price - order.getGoodsPrice();
		
		//修改order价格
		order.setGoodsPrice(price);
		orderManager.editOrder(order);
		
		//修改trade价格
		Map<String, Object> parMap = new HashMap<String,Object>();
		double goodsAmount = trade.getGoodsAmount()+com*order.getGoodsNumber();
		double amount = trade.getAmount()+com*order.getGoodsNumber();
		parMap.put("goodsAmount", goodsAmount);
		parMap.put("amount", amount);
		parMap.put("tid", tid);
		tradeManager.updateTradeOrderPrice(parMap);
		
		order.setJsonMsgType("true");
		order.setJsonMessage("价格修改保存成功！");
		order.setJsonTradeAmount(amount);
		order.setJsonOrderPriceAll(order.getGoodsPrice()*order.getGoodsNumber());
		return order;
	}
	
	// 后台批发订单修改商品数量 
	@RequestMapping("/trade/checkGoodsNumber")
	@ResponseBody
	public Order checkGoodsNumber(@RequestParam("orderId") Long orderId,
			@RequestParam("tid") String tid,
			@RequestParam("goodsNumber") Long goodsNumber,AdminAgent agent) {
		Order order = orderManager.getOrder(orderId);
		if(order==null){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		Trade trade = tradeManager.getTradeByTid(tid);
		if(trade==null){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		
		if(!order.getTid().equals(tid)){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}

		//修改Order数量
		order.setGoodsNumber(goodsNumber);
		orderManager.editOrder(order);
		
		
		//价差
		double com = order.getGoodsPrice()*(goodsNumber - order.getGoodsNumber());
		double comSc = order.getGoodsPriceSc()*(goodsNumber - order.getGoodsNumber());
		
		//修改trade价格
		Map<String, Object> parMap = new HashMap<String,Object>();
		double goodsAmount = trade.getGoodsAmount()+com;
		double amount = trade.getAmount()+com;
		parMap.put("goodsAmount", goodsAmount);
		parMap.put("amount", amount);
		parMap.put("tid", tid);
		tradeManager.updateTradeOrderPrice(parMap);

		order.setJsonMsgType("true");
		order.setJsonMessage("数量修改保存成功！");
		order.setJsonComSc(comSc);
		order.setJsonTradeAmount(amount);
		order.setJsonOrderPriceAll(order.getGoodsPrice()*order.getGoodsNumber());
		return order;
	}
	
	

	// 删除单某个订单的产品
	@RequestMapping("/trade/deleteGoodsNumberToZero")
	@ResponseBody
	public Order deleteGoodsNumberToZero(@RequestParam("orderId") Long orderId,
			@RequestParam("tid") String tid,AdminAgent agent) {
		Order order = orderManager.getOrder(orderId);
		if(order==null){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		Trade trade = tradeManager.getTradeByTid(tid);
		if(trade==null){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}
		
		if(!order.getTid().equals(tid)){
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return order;
		}

		//删除Order数量
		orderManager.removeOrder(orderId);
		
		//删除订单关联的Product
		orderManager.deleteOrderByOrderId(orderId);
		
		
		//价差
		double com = order.getGoodsPrice()*(0 - order.getGoodsNumber());
		double comSc = order.getGoodsPriceSc()* order.getGoodsNumber();
		
		//修改trade价格
		Map<String, Object> parMap = new HashMap<String,Object>();
		double goodsAmount = trade.getGoodsAmount()+com;
		double amount = trade.getAmount()+com;
		parMap.put("goodsAmount", goodsAmount);
		parMap.put("amount", amount);
		parMap.put("tid", tid);
		tradeManager.updateTradeOrderPrice(parMap);

		order.setJsonMsgType("true");
		order.setJsonMessage("删除成功！");
		order.setJsonComSc(comSc);
		order.setJsonTradeAmount(amount);
		order.setJsonOrderPriceAll(order.getGoodsPrice()*order.getGoodsNumber());
		return order;
	}

	

	// 删除单某个订单的产品
	@RequestMapping("/trade/deleteOrderProduct")
	@ResponseBody
	public String deleteOrderProduct(@RequestParam("productId") Long productId,
			@RequestParam("orderId") Long orderId,AdminAgent agent) {
		Order order = orderManager.getOrder(orderId);
		if(order==null){
			order = new Order();
			order.setJsonMsgType("false");
			order.setJsonMessage("参数出错，修改失败！");
			return "false";
		}
		
		//删除Order下某个prouct
		orderManager.deleteOrderByIdProduct(productId, orderId);
		//更新ProductId的状态为可售
		Map parMap = new HashMap();
		parMap.put("status", 1);
		parMap.put("productId", productId);
		productService.updateProductStatusByProductId(parMap);
		order.setJsonMsgType("true");
		order.setJsonMessage("删除成功！");
		return "true";
	}

	

	@RequestMapping("/trade/saveProductIds")
	@ResponseBody
	public Object saveProductIdsJson(AdminAgent agent,@RequestParam("productIds") String productIds) {
		//参数为空返回
		if(StringUtil.isBlank(productIds)){
			return "['false','请先添加产品!']";
		}
		
		String[] instanceArr = productIds.split(";");
		
		List<Product> productList = new ArrayList<Product>();
		List<Order> orderList = new ArrayList<Order>();
		Trade trade = null;
		double productAmount = 0;
		long buyId = 0L;
		for(String inObj:instanceArr){
			String[] inObjArr = inObj.split(":");
			Long orderId = Long.parseLong(inObjArr[0]);
			Long productId = Long.parseLong(inObjArr[1]);
			Order order = null;
			boolean tag= false;
			for(Order orderItem:orderList){
				if(orderItem.getId()==orderId.longValue()){
					order = orderItem;
					tag= true;
				}
			}
			if(!tag){
				order = orderManager.getOrder(orderId);
				orderList.add(order);
			}
			if(trade==null){
				trade = tradeManager.getTradeByTid(order.getTid());
			}
			buyId = trade.getBuyId(); //下单客户ID
			Map<String,Object> parMap = new HashMap<String,Object> ();
			parMap.put("productId", productId);
			Product product = productService.getProductByMap(parMap);
			
			if(product == null){
				return "['false','"+"产品Id:"+productId+"参数没有对应的产品!']";
			}
			
			if(order.getGoodsInstanceId().longValue()
					!=product.getInstanceId().longValue()){
				return "['false','"+order.getGoodsTitle()+"产品型号出错!']";
			}
			//不用检查状态了,在添加产品的时候必须添加可售状态的产品
			/*
			if(product.getIdStatus().longValue()!=1){
				return "['false','"+order.getGoodsTitle()+"产品Id"+product.getIdProduct()+"不是可售状态!']";
			}
			*/
			// list传进去，建一个customerorder  把customerorderid 保存在trade表中
			product.setPrice(order.getGoodsPrice());
			productAmount = productAmount+order.getGoodsPrice();
			productList.add(product);
//			order.setProductNumber(order.getProductNumber()+1);
		}
		
		
		if("admin".equalsIgnoreCase(agent.getUsername())){
			trade.setIdlastoperator(1200001L);
		}else{
			trade.setIdlastoperator(Long.parseLong(agent.getUsername()));
		}
		
		Long customerId = userManager.getCustomerIdByUserId(buyId); //通过UserId得到C端客户ID
		trade.setCustomerId(customerId); 
		trade.setIdPriceCurrency(3);
		trade.setCustomerorderAmount(productAmount);
		
		
		tradeManager.saveProductToTradeAndChangeTradeStatus(productList, orderList,trade);
		/*
		int tag = 0;
		for(Order order:orderList){
			if(order.getProductNumber()<order.getGoodsNumber()){
				tag = 1;
			}
			if(order.getProductNumber()>order.getGoodsNumber()){
				tag = 2;
			}
		}
		if(tag==1){
			return "['false','添加产品数量还未到客户采购数量 !']";
		}
		if(tag==2){
			return "['false','添加产品数量大于客户采购数量 !']";
		}
		*/
		return "['true','edit success!']";
	}
	
	
	
	
	
	
	
	/**
	 * 修改物流方式
	 * 
	 * @param afterModifyAmount
	 * @param id
	 *            Long
	 * @author chenhang 2010-01-10
	 */
	@RequestMapping("/trade/modifyExpressJson")
	@ResponseBody
	public Object modifyExpressJson(@RequestParam("param1") String afterModifyAmount, @RequestParam("param2") String tid) {
		int result = -1;
		result = tradeManager.updateTradeExpressId(tid, afterModifyAmount);
		if (result > 0) {
			String interfaceExpressCode = expressManager.getInterfaceExpressCodeByExpressId(Long
					.valueOf(afterModifyAmount));
			tradeManager.updateInterfaceExpressCode(tid, interfaceExpressCode);
			return "['true','edit success!']";
		} else {
			return "['false','edit failed!']";
		}
	}

	/**
	 * 修改物流费用
	 * 
	 * @param afterModifyAmount
	 *            String
	 * @param id
	 *            Long
	 * @return String 需?['true','amount:100;goodsPriceAcount:98']"格式返回(其中true,100 ,98为变?
	 * @author chenyan 2009/09/10
	 */
	@RequestMapping("/trade/modifyShippingAmountJson")
	@ResponseBody
	public Object modifyShippingAmountJson(@RequestParam("param1") String afterModifyAmount,
			@RequestParam("param2") String id, AdminAgent agent) {
		// 物流费用
		Double shippingAmount = 0.00;
		// 交易总价
		Double amount = 0.00;
		// 商品总价
		String goodsPriceAcount = "0.00";
		String agentPrice = "0.00";

		try {
			if (StringUtil.isBlank(id)) {
				return "['false','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (!StringUtil.isNumeric(StringUtil.replaceOnce(afterModifyAmount.trim(), ".", ""))) {
				return "['numberError','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (StringUtil.isNotBlank(afterModifyAmount)) {
				shippingAmount = Double.valueOf(afterModifyAmount);
			}
			// 取得订单信息用于回显价格
			Trade tradeInfoForAmount = this.tradeManager.getTrade(new Long(id));
			if (tradeInfoForAmount == null) {
				return "['false','amount:;goodsPriceAcount:;agentPrice:']";
			} else {
				amount = DoubleUtil.sub(tradeInfoForAmount.getAmount(), tradeInfoForAmount.getShippingAmount());
				amount = DoubleUtil.add(amount, shippingAmount);
				if (amount < 0) {
					return "['priceError','amount:;goodsPriceAcount:;agentPrice:']";
				}
				// 更新物流价格和交易总价,更新时记录下来 zhangwy
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = df.format(now);
				String memo = agent.getUsername() + "在" + dateStr + "修改物流价格为："
						+ MoneyUtil.getFormatMoney(shippingAmount, "0.00") + "元, 订单总价变为："
						+ MoneyUtil.getFormatMoney(amount, "0.00") + "元。";

				tradeManager.updateShippingAmountById(shippingAmount, amount, memo, new Long(id));

				// 成功修改的情
				goodsPriceAcount = MoneyUtil.getFormatMoney(tradeInfoForAmount.getGoodsAmount() + shippingAmount,
						"0.00");
				if (tradeInfoForAmount.getAgentSellAmount() != null) {
					agentPrice = MoneyUtil.getFormatMoney(tradeInfoForAmount.getAgentSellAmount() + shippingAmount,
							"0.00");
				}

				this.log.error(agent.getUsername() + " modify amount : " + amount + " tid="
						+ tradeInfoForAmount.getTid());

				// 修改payrecord表 zhangwy
				if (tradeInfoForAmount.getTradeType() == 1) {
					PayRecord payRecord = payRecordManager.getPayRecordByTid(tradeInfoForAmount.getTid());
					if (payRecord != null) {
						payRecord.setPayAmount(amount);
						payRecordManager.editPayRecord(payRecord);
					}
				}
				return "['true','amount:" + MoneyUtil.getFormatMoney(amount, "0.00") + ";goodsPriceAcount:"
						+ MoneyUtil.getFormatMoney(goodsPriceAcount, "0.00") + ";agentPrice:" + agentPrice + "']";
			}
		} catch (Exception e) {
			return "['false','amount:;goodsPriceAcount:;agentPrice:']";
		}
	}

	/**
	 * 收款管理
	 * 
	 * @return
	 */
	@RequestMapping("/trade/receive")
	public String showAdminReceive(@ModelAttribute("query") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "message", required = false, defaultValue = "") String message, Model model)
			throws Exception {
		String gmtCreateStart = tradeListQuery.getPlatformTimeStart();
		String gmtCreateEnd = tradeListQuery.getPlatformTimeEnd();
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = com.huaixuan.network.common.util.DateUtil.getDiffDate(new Date(), -30);
			gmtCreateEnd = com.huaixuan.network.common.util.DateUtil.getDateToString(new Date());
			tradeListQuery.setPlatformTimeStart(gmtCreateStart);
			tradeListQuery.setPlatformTimeEnd(gmtCreateEnd);
		}

		PayRecord payRecord = payRecordManager.getPayAmountByPlatform(tradeListQuery);

		QueryPage query = payRecordManager.getPayRecordLists(tradeListQuery, currPage, pageSize);

		model.addAttribute("query", query);
		model.addAttribute("payRecord", payRecord);
		model.addAttribute("queryObject", tradeListQuery);
		model.addAttribute("message", message);
		return "/trade/tradeReceive";
	}

	/**
	 * 收款管理EXCEL导出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/trade/doExportReceive")
	public void doExportReceive(@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, HttpServletResponse res)
			throws Exception {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=receive" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<String[]> receiveExportList = new ArrayList<String[]>();
			String[] title = { "支付平台", "订单号", "支付金额", "买家帐户名", "交易批次号", "完成时间", "财务状态" };
			receiveExportList.add(title);
			// tradeListQuery.setPayStatus(EnumTradeStatus.WAIT_BUYER_PAY.getKey());
			QueryPage queryPage = payRecordManager.getPayRecordLists(tradeListQuery, 1, Integer.MAX_VALUE);
			List<PayRecord> payRecordList = (List<PayRecord>) queryPage.getItems();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (payRecordList != null) {
				for (PayRecord tmp : payRecordList) {
					if (tmp.getPayPlatform().equals("zfb") || tmp.getPayPlatform().equals("ZFB")) {
						tmp.setPayPlatform("支付宝");
					} else if (tmp.getPayPlatform().equals("CHB")) {
						tmp.setPayPlatform("网银在线");
					} else if (tmp.getPayPlatform().equals("CFT")) {
						tmp.setPayPlatform("财付通");
					} else if (tmp.getPayPlatform().equals("SXY")) {
						tmp.setPayPlatform("首信易");
					} else if (tmp.getPayPlatform().equals("WHT")) {
						tmp.setPayPlatform("网汇通");
					} else if (tmp.getPayPlatform().equals("back")) {
						tmp.setPayPlatform("后台下单");
					}
					if (StringUtil.isBlank(tmp.getFinanceStatus()) || tmp.getFinanceStatus().equals("n")) {
						tmp.setFinanceStatus("未确认");
					} else if (tmp.getFinanceStatus().equals("y")) {
						tmp.setFinanceStatus("已确认");
					}
					String[] data = { tmp.getPayPlatform() + "", tmp.getTid() + "", tmp.getPayAmount() + "",
							tmp.getBuyer() + "", (tmp.getBatchNo() == null ? "" : tmp.getBatchNo()) + "",
							df2.format(tmp.getGmtModify()) + "", tmp.getFinanceStatus() + "" };
					receiveExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, receiveExportList);
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "导出失败！");
			log.error(e);
		} finally {
			close(outwt);
		}
	}

	/**
	 * 周期结算收款管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/trade/searchPeriodList")
	public String searchPeriodList(@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "message", required = false, defaultValue = "") String message, Model model)
			throws Exception {
		tradeListQuery.setExpressPayment(EnumExpressDistPayment.PERIOD_PAY.getKey());// 查询周期结算记录

		String gmtCreateStart = tradeListQuery.getGmtCreateStart();
		String gmtCreateEnd = tradeListQuery.getGmtCreateEnd();
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = com.huaixuan.network.common.util.DateUtil.getDiffDate(new Date(), -30);
			gmtCreateEnd = com.huaixuan.network.common.util.DateUtil.getDateToString(new Date());
			tradeListQuery.setGmtCreateStart(gmtCreateStart);
			tradeListQuery.setGmtCreateEnd(gmtCreateEnd);
		}
		Trade trade = tradeManager.getTradesGoodsAmountSum(tradeListQuery);
		QueryPage query = tradeManager.getTradesByParameterMap(tradeListQuery, currPage, pageSize);

		if (message != null) {
			model.addAttribute("message", message);
		}
		model.addAttribute("queryObject", tradeListQuery);
		model.addAttribute("query", query);
		model.addAttribute("trade", trade);

		return "/trade/tradePeriodReceive";
	}

	/**
	 * 货到付款收款管理
	 * 
	 * @return
	 */
	@RequestMapping("/trade/codReceive")
	public String showAdminCodReceive(@ModelAttribute("query") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "message", required = false, defaultValue = "") String message, Model model) {
		tradeListQuery.setExpressPayment(EnumExpressDistPayment.GOODS_FIRST.getKey());// 货到付款记录
		// getTradeListByExpressPayMent(tradeListQuery,currPage, pageSize);

		String gmtCreateStart = tradeListQuery.getGmtCreateStart();
		String gmtCreateEnd = tradeListQuery.getGmtCreateEnd();
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = com.huaixuan.network.common.util.DateUtil.getDiffDate(new Date(), -30);
			gmtCreateEnd = com.huaixuan.network.common.util.DateUtil.getDateToString(new Date());
			tradeListQuery.setGmtCreateStart(gmtCreateStart);
			tradeListQuery.setGmtCreateEnd(gmtCreateEnd);
		}
		Trade trade = tradeManager.getTradesGoodsAmountSum(tradeListQuery);
		QueryPage query = tradeManager.getTradesByParameterMap(tradeListQuery, currPage, pageSize);

		if (message != null) {
			model.addAttribute("message", message);
		}
		model.addAttribute("trade", trade);
		model.addAttribute("query", query);
		model.addAttribute("queryObject", tradeListQuery);

		return "/trade/tradeCodReceive";
	}

	private void getTradeListByExpressPayMent(TradeListQuery tradeListQuery, int currPage, int pageSize) {
		String gmtCreateStart = tradeListQuery.getGmtCreateStart();
		String gmtCreateEnd = tradeListQuery.getGmtCreateEnd();
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = com.huaixuan.network.common.util.DateUtil.getDiffDate(new Date(), -30);
			gmtCreateEnd = com.huaixuan.network.common.util.DateUtil.getDateToString(new Date());
			tradeListQuery.setGmtCreateStart(gmtCreateStart);
			tradeListQuery.setGmtCreateEnd(gmtCreateEnd);
		}
		Trade trade = tradeManager.getTradesGoodsAmountSum(tradeListQuery);
		QueryPage query = tradeManager.getTradesByParameterMap(tradeListQuery, currPage, pageSize);

	}

	/**
	 * 货到付款收款管理EXCEL导出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/trade/doExportCodReceive")
	public void doExportCodReceive(@ModelAttribute("query") TradeListQuery tradeListQuery, HttpServletResponse res) {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=cod_receive" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<String[]> receiveExportList = new ArrayList<String[]>();
			String[] title = { "订单号", "支付金额", "代收金额", "买家名称", "完成时间", "财务状态" };
			receiveExportList.add(title);
			tradeListQuery.setExpressPayment(EnumExpressDistPayment.GOODS_FIRST.getKey());
			QueryPage queryPage = tradeManager.getTradesByParameterMap(tradeListQuery, 1, Integer.MAX_VALUE);
			List<Trade> tradeList = (List<Trade>) queryPage.getItems();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (tradeList != null) {
				for (Trade tmp : tradeList) {
					String agentAmount = "";
					if (tmp.getPayStatus().equals(EnumTradePayStatus.NO_PAY.getKey())) {
						tmp.setPayStatus("未确认");
					} else if (tmp.getPayStatus().equals(EnumTradePayStatus.PAID.getKey())) {
						tmp.setPayStatus("已确认");
					}
					if (tmp.getType().equals("2")) {
						if (tmp.getAgentSellAmount() != null) {
							agentAmount = String.valueOf(tmp.getAgentSellAmount());
						}

					}
					String[] data = { tmp.getTid() + "", tmp.getAmount() + "", agentAmount + "", tmp.getBuyNick() + "",
							tmp.getPayTime() == null ? "" : df2.format(tmp.getPayTime()) + "", tmp.getPayStatus() };
					receiveExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, receiveExportList);
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "导出失败！");
			log.error(e);
		} finally {
			close(outwt);
		}
	}

	/**
	 * 周期结算收款管理EXCEL导出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/trade/doExportPeriodReceive")
	public void doExportPeriodReceive(@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			HttpServletResponse res) {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=period_receive" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<String[]> receiveExportList = new ArrayList<String[]>();
			String[] title = { "订单号", "支付金额", "买家名称", "财务确认时间", "财务状态", "结算截止日" };
			receiveExportList.add(title);
			tradeListQuery.setExpressPayment(EnumExpressDistPayment.PERIOD_PAY.getKey());
			QueryPage queryPage = tradeManager.getTradesByParameterMap(tradeListQuery, 1, Integer.MAX_VALUE);
			List<Trade> tradeList = (List<Trade>) queryPage.getItems();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (tradeList != null) {
				for (Trade tmp : tradeList) {
					if (tmp.getPayStatus().equals(EnumTradePayStatus.NO_PAY.getKey())) {
						tmp.setPayStatus("未确认");
					} else if (tmp.getPayStatus().equals(EnumTradePayStatus.PAID.getKey())) {
						tmp.setPayStatus("已确认");
					}
					String[] data = { tmp.getTid() + "", tmp.getAmount() + "", tmp.getBuyNick() + "",
							tmp.getPayTime() == null ? "" : df2.format(tmp.getPayTime()) + "", tmp.getPayStatus(),
							tmp.getGmtPeriodPayEnd() == null ? "" : df2.format(tmp.getGmtPeriodPayEnd()) + "" };
					receiveExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, receiveExportList);
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "导出失败！");
			log.error(e);
		} finally {
			close(outwt);
		}
		// return SUCCESS;
	}

	//
	/**
	 * 收款管理/周期结算财务确认状态
	 * 
	 * @param payRecordId
	 * @return
	 */
	@RequestMapping(value = "/trade/confirmStatus")
	public String confirmStatus(@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			HttpServletRequest request, Model model) throws Exception {
		String message = "";
		String id = request.getParameter("id");
		String actionType = request.getParameter("actionType");
		try {
			Trade trade = tradeManager.getTrade(Long.parseLong(id));
			if (trade != null
					&& StringUtil.isNotBlank(trade.getTid())
					&& (EnumExpressDistPayment.GOODS_FIRST.getKey().equals(trade.getExpressPayment()) || EnumExpressDistPayment.PERIOD_PAY
							.getKey().equals(trade.getExpressPayment())) && !"paid".equals(trade.getPayStatus())) {
				// 更新付款状态
				tradeManager.updatePayTimeByTid(new Long(trade.getTid()));
				// 更新商品热销分数
				updateGoodsPoint(trade.getTid());
				message = "财务确认成功！";
			} else {
				message = "财务确认失败！";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			message = "财务确认失败！";
		}

		tradeListQuery.setId(null);

		if ("period".equals(actionType)) {
			return searchPeriodList(tradeListQuery, currPage, message, model);
		}
		return showAdminCodReceive(tradeListQuery, currPage, message, model);
	}
	
	/**
	 * 周期结算批量财务确认状态
	 * 
	 * @param payRecordId
	 * @return
	 */
	@RequestMapping(value = "/trade/batchConfirmStatus")
	public String batchConfirmStatus(@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			HttpServletRequest request, Model model)throws Exception{
		String message = "";
		StringBuffer errormessage = new StringBuffer();
		String ids[] = request.getParameterValues("ids");
		String actionType = request.getParameter("actionType");
		String curpage = request.getParameter("curpage");
		boolean allsuccess = true;
		try {
			for(String id: ids){
				Trade trade = tradeManager.getTrade(Long.parseLong(id));
				if (trade != null
						&& StringUtil.isNotBlank(trade.getTid())
						&& (EnumExpressDistPayment.GOODS_FIRST.getKey().equals(trade.getExpressPayment()) || EnumExpressDistPayment.PERIOD_PAY
								.getKey().equals(trade.getExpressPayment())) && !"paid".equals(trade.getPayStatus())) {
					// 更新付款状态
					tradeManager.updatePayTimeByTid(new Long(trade.getTid()));
					// 更新商品热销分数
					updateGoodsPoint(trade.getTid());
				} else {
					errormessage.append( trade.getTid()+"财务确认失败！");
					allsuccess = false;
				}	
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			allsuccess = false;
			errormessage.append("系统错误,财务确认失败！");
		}
		tradeListQuery.setId(null);
		
		if(allsuccess){
			message = "财务确认成功！";
		}
		if ("period".equals(actionType)) {
			return searchPeriodList(tradeListQuery, Integer.parseInt(curpage), (allsuccess)?message:errormessage.toString(), model);
		}
		return showAdminCodReceive(tradeListQuery, Integer.parseInt(curpage), message, model);
	}

	@RequestMapping(value = "/trade/confirmTradeStatus")
	public String confirmTradeStatus(@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam("id") String id, Model model) throws Exception {
		model.addAttribute("query", tradeListQuery);
		model.addAttribute("page", currPage);
		String message = "";
		try {
			PayRecord payRecord = payRecordManager.getPayRecord(Long.parseLong(id));
			// 后台下单确认
			if (payRecord.getPayPlatform().equals("back")) {
				Trade backTrade = tradeManager.getTradeByTid(payRecord.getTid());
				if (EnumTradeStatus.TRADE_CLOSE.getKey().equals(backTrade.getStatus())) {
					message = "close"; // 此订单已经关闭！

					return showAdminReceive(tradeListQuery, currPage, message, model);
					// return "redirect:/trade/receive.html";
				}
				backTrade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
				backTrade.setPayStatus(EnumTradePayStatus.PAID.getKey());
				backTrade.setPayTime(new Date());
				tradeManager.editTrade(backTrade);
			}
			payRecord.setFinanceStatus("y");
			payRecordManager.editPayRecord(payRecord);
			message = "success";// 财务确认成功！
		} catch (Exception e) {
			log.error(e.getMessage());
			message = "faile";// 财务确认失败！
		}

		return showAdminReceive(tradeListQuery, currPage, message, model);
		// return "redirect:/trade/receive.html";
	}

	/**
	 * 修改发票选项
	 * 
	 * @return
	 */
	@RequestMapping("/trade/modifyInvoice")
	public String modifyInvoice(@RequestParam("invoice") String invoice, @RequestParam("tid") String tid, Model model,
			AdminAgent agent) {
		try {
			tradeManager.updateInvoiceByTradeId(Long.parseLong(tid), invoice);
			model.addAttribute("success", true);
			model.addAttribute("message", "修改成功");
		} catch (Exception ex) {
			log.error("", ex);
			model.addAttribute("success", false);
			model.addAttribute("message", "修改失败");
		}

		showAdminTradeDetail(tid, null, model, agent);
		return "/trade/tradeDetail";
	}

	/**
	 * 确认已经采购
	 * 
	 * @author chenhang 2011/01/18
	 */
	@RequestMapping("/trade/modifyIspurchased")
	@ResponseBody
	public Object modifyIspurchased(@RequestParam("time") String time, @RequestParam("param1") String tid) {
		String message;

		int result = -1;
		try {
			result = tradeManager.updateIspurchasedByTradeId(Long.parseLong(tid));
		} catch (Exception e) {
			log.error("", e);
		}
		if (result > 0) {
			message = "success";
		} else {
			message = "修改失败";
		}
		return message;
	}

	/**
	 * 客服订单留言
	 * 
	 * @return
	 */
	@RequestMapping("/trade/messageConserve")
	public String csMessage(@RequestParam(value = "csMessage", required = false) String smessage,
			@RequestParam("tid") String tid, Model model, AdminAgent agent) throws Exception {
		try {
			tradeManager.updateMessageByTradeId(Long.parseLong(tid), smessage);
			model.addAttribute("success", true);
			model.addAttribute("message", "保存成功");
		} catch (Exception ex) {
			model.addAttribute("success", false);
			model.addAttribute("message", "保存失败");
			throw ex;
		}

		showAdminTradeDetail(tid, null, model, agent);
		return "/trade/tradeDetail";
	}

	/**
	 * @param afterModifyAmount
	 *            修改后的运费
	 * @param id
	 *            订单ID
	 * @param depFirstIdStr
	 *            一级仓库id
	 * @param model
	 * @param agent
	 * @return
	 */
	@RequestMapping("/trade/modifyWsShippingAmountJson")
	@ResponseBody
	public Object modifyWsShippingAmountJson(@RequestParam("modifyAmount") String afterModifyAmount,
			@RequestParam("tradeId") String id, @RequestParam("depFirstId") String depFirstIdStr, Model model,
			AdminAgent agent) {
		// 物流费用
		Double shippingAmount = 0.00;
		// 交易总价
		Double amount = 0.00;
		// 商品总价
		String goodsPriceAcount = "0.00";
		String agentPrice = "0.00";
		try {
			if (StringUtil.isBlank(id)) {// 订单ID为空
				return "['false','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (!StringUtil.isNumeric(StringUtil.replaceOnce(afterModifyAmount.trim(), ".", ""))) {// 运费非数字
				return "['numberError','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (StringUtil.isNotBlank(afterModifyAmount)) {
				shippingAmount = Double.valueOf(afterModifyAmount);
			}
			if (!StringUtil.isNumeric(id)) {// 订单ID不是数字
				return "['tradeIdNotNum','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (StringUtil.isBlank(depFirstIdStr)) {// 一级仓库ID为空
				return "['depFirstIdIsNull','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (!StringUtil.isNumeric(depFirstIdStr)) {// 一级仓库ID非数字
				return "['depFirstIdNotNum','amount:;goodsPriceAcount:;agentPrice:']";
			}

			Long depFirstId = Long.parseLong(depFirstIdStr);
			// 查询一级仓库信息
			DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(depFirstId);
			if (null == depositoryFirst) {// 一级仓库信息不存在
				return "['depositoryIsNull','amount:;goodsPriceAcount:;agentPrice:']";
			}
			if (null != depositoryFirst && !"w".equals(depositoryFirst.getType())) {// 一级仓库不是批发类型
				// type=w
				return "['depositoryNotW','amount:;goodsPriceAcount:;agentPrice:']";
			}

			// 取得订单信息用于回显价格
			Trade tradeInfoForAmount = this.tradeManager.getTrade(Long.parseLong(id));
			if (tradeInfoForAmount == null) {
				return "['false','amount:;goodsPriceAcount:;agentPrice:']";
			} else {
				amount = DoubleUtil.sub(tradeInfoForAmount.getAmount(), tradeInfoForAmount.getShippingAmount());
				amount = DoubleUtil.add(amount, shippingAmount);
				if (amount < 0) {
					return "['priceError','amount:;goodsPriceAcount:;agentPrice:']";
				}

				// 更新物流价格和交易总价,更新时记录下来 zhangwy
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = df.format(now);
				String memo = agent.getUsername() + "在" + dateStr + "修改物流价格为："
						+ MoneyUtil.getFormatMoney(shippingAmount, "0.00") + "元, 订单总价变为："
						+ MoneyUtil.getFormatMoney(amount, "0.00") + "元。";

				this.tradeManager.updateWsShippingAndDepositoryById(shippingAmount, amount, memo, depFirstId,
						Long.parseLong(id));

				// 成功修改的情况
				goodsPriceAcount = MoneyUtil.getFormatMoney(tradeInfoForAmount.getGoodsAmount() + shippingAmount,
						"0.00");
				if (tradeInfoForAmount.getAgentSellAmount() != null) {
					agentPrice = MoneyUtil.getFormatMoney(tradeInfoForAmount.getAgentSellAmount() + shippingAmount,
							"0.00");
				}
				this.log.error(agent.getUsername() + " modify amount : " + amount + " tid="
						+ tradeInfoForAmount.getTid());

				return "['true','amount:" + MoneyUtil.getFormatMoney(amount, "0.00") + ";goodsPriceAcount:"
						+ MoneyUtil.getFormatMoney(goodsPriceAcount, "0.00") + ";agentPrice:" + agentPrice + "']";
			}
		} catch (Exception e) {
			return "['false','amount:;goodsPriceAcount:;agentPrice:']";
		}
	}

	/**
	 * 修改一级仓库 
	 * 
	 * @return
	 */
	@RequestMapping("/trade/updateTradeDepfirstId")
	@ResponseBody
	public Object updateTradeDepfirstId(@RequestParam(value = "param1", required = false) String tradeId,
			@RequestParam(value = "param2", required = false) String depfirstId) {
		String message;

		if (StringUtil.isBlank(tradeId)) {
			message = "notradeId";
			return message;
		} else if (StringUtil.isBlank(depfirstId)) {
			message = "nodepfirstId";
			return message;
		}

		Trade trade = tradeManager.getTrade(Long.parseLong(tradeId));
		if (trade == null) {
			message = "notradeId";
			return message;
		}

		if (!(trade.getStatus().equals(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey()) || trade.getStatus().equals(
				EnumTradeStatus.WAIT_BUYER_PAY.getKey()))) {
			if (trade.getStatus().equals(EnumTradeStatus.TRADE_CLOSE.getKey()) && trade.getStockoutStatus() != null) {
				if (trade.getStockoutStatus().equals("n")) {
					message = "statusWrong";
					return message;
				}
			} else {
				message = "statusWrong";
				return message;
			}
		}

		trade.setDepFirstId(Long.parseLong(depfirstId));
		tradeManager.editTradeWithDepFirstId(trade);
		message = "success";
		return message;
	}

	/**
	 * 
	 * 功能：缺货订单列表<br>
	 * 
	 * @return
	 * @author shenzh Nov 4, 2010
	 */
	@RequestMapping("/trade/showStockoutList")
	public String showStockoutList(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent) {
		query.setStockoutStatus("y");
		query.setTradeType((byte) 3);
		query.setStatus("trade_close");
		showAdminTradeList(query, null, currPage, model, pageSize, agent);

		return "/trade/stockoutTradeList";
	}

	@RequestMapping("trade/closeStockoutTrade")
	public String closeStockoutTrade(@ModelAttribute("query") TradeListQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent, HttpServletRequest req) {
		int count = 0;

		String[] ids = req.getParameterValues("ids");
		if (ids != null) {
			for (String tid : ids) {
				Trade t = tradeManager.getTradeByTid(tid);
				if (t != null && EnumTradeStatus.TRADE_CLOSE.getKey().equals(t.getStatus())
						&& "y".equals(t.getStockoutStatus())) {
					t.setStockoutStatus("n");
					t.setGmtModify(new Date());
					tradeManager.editTrade(t);
					count++;
				}
			}
		}
		String message = count + "个订单被成功关闭!";
		model.addAttribute("message", message);
		return showStockoutList(query, currPage, model, agent);
	}

	@RequestMapping("/trade/stockoutDetail")
	public String stockoutDetail(@RequestParam(value = "tradeId", required = false) String tid,
			@RequestParam(value = "refundId", required = false) String refundId, Model model, AdminAgent agent) {
		showAdminTradeDetail(tid, refundId, model, agent);
		return "trade/stockoutTradeDetail";
	}

	@AdminAccess({EnumAdminPermission.A_ORDER_RECEIVE_MODIFY})
	@RequestMapping("/trade/modifyReceiveAddress")
	public String modifyReceiveAddress(AdminAgent agent,Model model,@ModelAttribute("trade") Trade trade){
		Trade tradeTemp = tradeManager.getTrade(trade.getId());
		tradeTemp.setProvince(trade.getProvince());
		tradeTemp.setCity(trade.getCity());
		if(StringUtil.isNotBlank(trade.getDistrict())){
			tradeTemp.setDistrict(trade.getDistrict());
		}else{
			tradeTemp.setDistrict(null);
		}
		tradeTemp.setZipcode(trade.getZipcode());
		tradeTemp.setReceiver(trade.getReceiver());
		tradeTemp.setMobile(trade.getMobileRewrite());
		tradeTemp.setAddress(trade.getAddress());
		tradeManager.editTrade(tradeTemp);
		model.addAttribute("receivemessage","success");
		return showAdminTradeDetail(trade.getTid() , null, model,agent);
	}
	
	
	@RequestMapping("/trade/sellerConfirmTrade")
	public String sellerConfirmTrade(AdminAgent agent,Model model,@ModelAttribute("trade") Trade trade){
		String message = "";
		int flag = tradeManager.updateTradeStatus(String.valueOf((trade.getId())), EnumTradeStatus.WAIT_BUYER_CONFIRM.getKey());
			if (flag == 1) {
			message = "success";
			} else if (flag == 2) {
			message = "状态不对，不允许修改!";
			} else {
			message = "操作失败!";
			}
		model.addAttribute("message",message);
		return showAdminTradeDetail(trade.getTid() , null, model,agent);
	}
	
	/**
	 * 后台订单备货初始化
	 * 
	 * @return
	 */
	@RequestMapping("/trade/distdin")
	public String distributeTradeInit(@RequestParam(value = "tradeId", required = false) String tid,
			@RequestParam(value = "refundId", required = false) String refundId, Model model, AdminAgent agent) {
		// 如果传入的参数是refund_id,则根据refund_id查询对应的tid
		if (!StringUtil.isBlank(refundId)) {
			Refund refund = refundManager.getRefundByRefundId(refundId);
			if (refund != null) {
				String tradeId = refund.getTid();
				Trade trade = tradeManager.getTradeByTid(tradeId);
				if (trade != null) {
					tid = trade.getTid();
				}
			}
		}

		// 交易信息
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		if (trade != null) {
			
			// 收货人地址
			String address = this.regionManager.getAddressInfo(trade.getProvince(), trade.getCity(),
					trade.getDistrict());
			address += trade.getAddress();
			model.addAttribute("address", address);
			
			//省份
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			//市
			if(StringUtil.isNotBlank(trade.getProvince())){
				List<Region> cityList = regionManager.getRegionChilds(trade.getProvince());
				model.addAttribute("cityList", cityList);
			}
			//地区
			if(StringUtil.isNotBlank(trade.getCity())){
				List<Region> districtList = regionManager.getRegionChilds(trade.getCity());
				model.addAttribute("districtList", districtList);
			}

			// 订单中的普通商品信息 加入重量等信息 zhangwy
			List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
			model.addAttribute("orderList", orderList);
			
			if (orderList != null) {
				for (Order o : orderList) {
					GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
					List<Product> products = productService.getProductsByOrderId(o.getId());
					for(Product p : products)
					{
						p.setBrandname(EnumBrandType.toMap().get(p.getIdBrand().toString()));
						p.setSeriesname(EnumSeriesType.toMap().get(p.getIdSeries().toString()));
					}
					o.setProducts(products);
					if(g.getHkExistNum()!=null)
						o.setInstanceHkExistNum(g.getHkExistNum().longValue());
				}
			}

		}
		
		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("brandMap", EnumBrandType.toMap());
		model.addAttribute("seriesMap", EnumSeriesType.toMap());

		return "/trade/distributeTradeInit";
	}
	
	/***
	 * 
	 * 确认报价 
	 * 
	 * */
	@RequestMapping("/trade/adminbucont")
	@ResponseBody
	public String tradeBuyerConfirm(@RequestParam(value = "param1", required = false) String tradeId,
			@RequestParam(value = "param2", required = false) String amount, AdminAgent agent)
	{
		int flag = 0;
		flag = tradeManager.updateTradeStatus(tradeId,
				EnumTradeStatus.WAIT_DISTRIBUTION.getKey());
		String message = "";
		if (flag == 0)
		{
			message = "状态已经修改，不能确认!";
		}
		else if (flag == 1)
		{
			message = "success";
		}
		else if (flag == 2)
		{
			message = "状态不对，不允许修改!";
		}
		else if (flag == 3)
		{
			message = "有未完成的退货/退款记录，不允许修改!";
		}
		else
		{
			message = "操作失败!";
		}
		return message;
	}
	
	/**
	 * 确认付款初始化
	 * 
	 * @param productId
	 *            Long
	 * @author songfy 2012-12-10
	 */
	@RequestMapping("/trade/confirmpay")
	public String confirmpayInit(@RequestParam(value = "tradeId", required = false) String tid,
			@RequestParam(value = "refundId", required = false) String refundId, Model model, AdminAgent agent) {
		// 如果传入的参数是refund_id,则根据refund_id查询对应的tid
		if (!StringUtil.isBlank(refundId)) {
			Refund refund = refundManager.getRefundByRefundId(refundId);
			if (refund != null) {
				String tradeId = refund.getTid();
				Trade trade = tradeManager.getTradeByTid(tradeId);
				if (trade != null) {
					tid = trade.getTid();
				}
			}
		}

		// 交易信息
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		if (trade != null) {
			// 订单中的普通商品信息 加入重量等信息 zhangwy
			List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
			model.addAttribute("orderList", orderList);
			
			if (orderList != null) {
				for (Order o : orderList) {
					GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());

					List<Product> products = productService.getProductsByOrderId(o.getId());
					for(Product p : products)
					{
						p.setBrandname(EnumBrandType.toMap().get(p.getIdBrand().toString()));
						p.setSeriesname(EnumSeriesType.toMap().get(p.getIdSeries().toString()));
					}
					o.setProducts(products);

					if(g.getHkExistNum()!=null)
						o.setInstanceHkExistNum(g.getHkExistNum().longValue());
				}
			}

		}

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());

		return "/trade/confirmpay";
	}
	
	@RequestMapping("/trade/confirmTradePay")
	public String confirmTradePay(AdminAgent agent,Model model,@ModelAttribute("trade") Trade trade){
		String message = "";
		String payment = trade.getPayment();
		Integer cash = trade.getCash();
		trade = tradeManager.getTrade(trade.getId());
		trade.setPayment(payment);
		trade.setCash(cash);
		if("admin".equalsIgnoreCase(agent.getUsername())){
			trade.setIdlastoperator(1200001L);
		}else{
			trade.setIdlastoperator(Long.parseLong(agent.getUsername()));
		}
		
		int flag = this.tradeManager.sellerConfirmPay(trade);
		
		if (flag == 1) {
			message = "success";
		} else if (flag == 2) {
			message = "状态不对，不允许修改!";
		} else {
			message = "操作失败!";
		}
		model.addAttribute("message",message);
		return showAdminTradeDetail(trade.getTid() , null, model,agent);
	}
	
}
