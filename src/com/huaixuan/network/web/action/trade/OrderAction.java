package com.huaixuan.network.web.action.trade;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.OrderListQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping("/trade")
public class OrderAction extends BaseAction {

	// private Map<ShoppingCart, Goods> shoppingCartGoodsMap = new HashMap<ShoppingCart, Goods>();
	// private ShoppingCartManager shoppingCartManager;
	// private GoodsManager goodsManager;
	@Autowired
	private OrderManager orderManager;

	// private Page page;
	// private Map<String, String> parMap = new HashMap<String, String>();
	// private Map<String, String> enumTradeStatusMap = EnumTradeStatus.toMap();
	// private AttributeManager attributeManager;
	@Autowired
	private OutDepositoryManager outDepositoryManager;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;

	@Autowired
	private GoodsBatchManager goodsBatch;

	@SuppressWarnings("unchecked")
	@RequestMapping("/orderList")
	public String orderList(@ModelAttribute("query") OrderListQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent) {
		String gmtCreateStart = query.getGmtCreateStart();
		String gmtCreateEnd = query.getGmtCreateEnd();
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = DateUtil.getDiffDate(new Date(), -7);
			gmtCreateEnd = DateUtil.getDateToString(new Date());
			query.setGmtCreateStart(gmtCreateStart);
			query.setGmtCreateEnd(gmtCreateEnd);
		}

		QueryPage page = orderManager.getOrdersByParameterMapQuery(query, currPage, pageSize);
		model.addAttribute("page", page);

		Map<String, Long> param = new HashMap<String, Long>();// 查询商品的仓库所用的MAP
		if (page.getItems() != null) {
			for (Order o : (List<Order>) page.getItems()) {
				o.setTotalPrice(o.getGoodsPrice() * o.getGoodsNumber());// added by chenhang 2011/02/11 订单查询页面显示商品总价
//				if ("wait_distribution".equals(o.getStatus()) || "wait_buyer_confirm_goods".equals(o.getStatus())
//						|| "trade_close".equals(o.getStatus()) || "trade_finish".equals(o.getStatus())) {
//					if (null != outDepositoryManager.getOutDepositoryByTid(o.getTid())) {
//						o.setOutDepId(outDepositoryManager.getOutDepositoryByTid(o.getTid()).getId());
//						o.setDepFirstName(depositoryFirstManager.getDepositoryById(o.getDepFirstId()).getDepFirstName());
//
//						param.put("outDepId", o.getOutDepId());
//						param.put("goodsInstanceId", o.getGoodsInstanceId());
//						o.setDepNames(orderManager.getDepNameByMap(param));
//					}
//				}
			}
		}

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		return "/trade/orderList";
	}

	/***************************************************************************
	 * 订单商品Excel导出
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportOrderGoods")
	public void exportOrderGoods(@ModelAttribute("query") OrderListQuery query, Model model, AdminAgent agent,
			HttpServletResponse res) throws Exception {
		String gmtCreateStart = query.getGmtCreateStart();
		String gmtCreateEnd = query.getGmtCreateEnd();
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = DateUtil.getDiffDate(new Date(), -7);
			gmtCreateEnd = DateUtil.getDateToString(new Date());
			query.setGmtCreateStart(gmtCreateStart);
			query.setGmtCreateEnd(gmtCreateEnd);
		}

		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=trade_order_goods" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<String[]> oraderGoodsExportList = new ArrayList<String[]>();

			String[] title = { "编码", "商品名称", "属性", "数量", "商品价格", "商品总价", "出货仓库", "订单号", "客户名称", "创建时间", "订单状态" };
			oraderGoodsExportList.add(title);

			List<Order> orderList = (List<Order>) orderManager
					.getOrdersByParameterMapQuery(query, 1, Integer.MAX_VALUE).getItems();

			Map<String, Long> parameterMap = new HashMap<String, Long>();// 查询商品的仓库所用的MAP

			if (orderList != null) {
				df.applyPattern("yyyy-MM-dd HH:mm:ss");
			}

			for (Order o : orderList) {
				o.setTotalPrice(o.getGoodsPrice() * o.getGoodsNumber());// added by chenhang 2011/02/11 商品总价
				if ("wait_distribution".equals(o.getStatus()) || "wait_buyer_confirm_goods".equals(o.getStatus())
						|| "trade_close".equals(o.getStatus()) || "trade_finish".equals(o.getStatus())) {
					if (null != outDepositoryManager.getOutDepositoryByTid(o.getTid())) {
						o.setOutDepId(outDepositoryManager.getOutDepositoryByTid(o.getTid()).getId());
						o.setDepFirstName(depositoryFirstManager.getDepositoryById(o.getDepFirstId()).getDepFirstName());
						parameterMap.put("outDepId", o.getOutDepId());
						parameterMap.put("goodsInstanceId", o.getGoodsInstanceId());
						o.setDepNames(orderManager.getDepNameByMap(parameterMap));
					}
				}
				String gmtCreate = df.format(o.getGmtCreate());
				String statusName = EnumTradeStatus.toMap().get(o.getStatus());
				StringBuffer depFirstName = new StringBuffer();
				if (null != o.getDepNames()) {
					for (String name : o.getDepNames()) {
						depFirstName.append(o.getDepFirstName()).append(" > ").append(name).append("  ").append("\r\n");
					}
				}
				String[] data = { o.getGoodsSn(), o.getGoodsTitle(), o.getBuyAttr(), o.getGoodsNumber() + "",
						o.getGoodsPrice() + "", o.getTotalPrice() + "", depFirstName.toString(), o.getTid(),
						o.getBuyNick(), gmtCreate, statusName };
				oraderGoodsExportList.add(data);
			}

			goodsBatch.exportExcel(outwt, oraderGoodsExportList);
			outwt.flush();
		} finally {
			try {
				if (outwt != null) {
					outwt.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

}
