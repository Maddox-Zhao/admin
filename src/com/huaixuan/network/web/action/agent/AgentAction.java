/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-8
 */
package com.huaixuan.network.web.action.agent;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.agent.AgentTrade;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.GoodsAgentManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.trade.TradeAgentManager;
import com.huaixuan.network.biz.service.user.UserAgentManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: AgentAction.java,v 0.1 2011-3-8 下午03:16:42 shengyong Exp $
 */
@Controller
public class AgentAction extends BaseAction {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	TradeAgentManager tradeAgentManager;
	@Autowired
	GoodsAgentManager goodsAgentManager;
	@Autowired
	UserAgentManager userAgentManager;
	@Autowired
	RegionManager regionManager;
	@Autowired
	AdminService adminService;
	@Autowired
	GoodsBatchManager goodsBatch;

	@AdminAccess({ EnumAdminPermission.A_AGENT_VIEW_USER })
	@RequestMapping(value = "/agent/searchAgentTrade")
	public String searchAgentTrade(@ModelAttribute("agentTrade") AgentTrade agentTrade,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model) {

		if (StringUtil.isBlank(agentTrade.getGmtCreateStart()) && StringUtil.isBlank(agentTrade.getGmtCreateEnd())) {
			agentTrade.setGmtCreateStart(DateUtil.getDiffMon(new Date(), -1));
			agentTrade.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
		}

		// 查询拓展人员
		List<Admin> adminUsers = adminService.getAdminUserList();
		model.addAttribute("users", adminUsers);

		QueryPage query = tradeAgentManager.searchAgentByParameterMap(agentTrade, currPage, this.pageSize);

		Double totalAmount = tradeAgentManager.searchAgentCountPriceByParameterMap(agentTrade);
		String totalAmountPage = "";

		// 根据条件查询交易记录
		List<AgentTrade> agentTradeList = (List<AgentTrade>) tradeAgentManager.searchAgentByParameterMap(agentTrade, 1,
				Integer.MAX_VALUE).getItems();
		double amount = 0.00;
		if (agentTradeList != null) {
			for (int j = 0; j < agentTradeList.size(); j++) {
				amount = amount + agentTradeList.get(j).getTotalPrice();
			}

			// 销售总价精确到小数点两位
			DecimalFormat df = new DecimalFormat("0.##");
			totalAmountPage = df.format(amount);
			ArrayList list = new ArrayList();
			// 排序（降序）：默认根据订单号排序
			if ("1".equals(agentTrade.getSortType())) {
				Collections.sort(agentTradeList, new AgentTradeGoodsNameComparator());
			} else {
				Collections.sort(agentTradeList, new AgentTradeTidComparator());
			}
			// 设置列表显示数据
			for (int i = 0; i < agentTradeList.size(); i++) {
				if (currPage == 1 && i < pageSize) {
					list.add(agentTradeList.get(i));
				} else {
					if (i > (pageSize * (currPage - 1) - 1) && i < (pageSize * currPage)) {
						list.add(agentTradeList.get(i));
					}
				}
			}
			agentTradeList.clear();
			agentTradeList = list;
			list = null;
		}
		query.setItems(agentTradeList);
		model.addAttribute("totalAmountPage", totalAmountPage);
		model.addAttribute("query", query);
		return "/agent/searchAgentTrade";
	}

	private String exportContentType = "application/oct-stream";

	private String defaultCsvName = "agentTradeExport.csv";

	@RequestMapping(value = "/agent/agentExportOrder")
	public void searchAgentTradeExport(@ModelAttribute("agentTrade") AgentTrade agentTrade, AdminAgent adminAgent,
			HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {
		OutputStream outwt = null;
		Map parMap = new HashMap();
		String userNick = request.getParameter("userNick");
		String gmtCreateStart = (String) parMap.get("gmtCreateStart");
		String gmtCreateEnd = (String) parMap.get("gmtCreateEnd");
		if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
			gmtCreateStart = DateUtil.getDiffMon(new Date(), -1);
			gmtCreateEnd = DateUtil.getDateToString(new Date());
			parMap.put("gmtCreateStart", gmtCreateStart);
			parMap.put("gmtCreateEnd", gmtCreateEnd);
		}
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition", "attachment; filename=trade" + date + ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> tradeExportList = new ArrayList<String[]>();
			String[] title = { "会员昵称", "订单编号", "商品编码", "商品名称", "拓展人员", "销售总数", "销售总价", "创建时间", "完成时间", "订单状态" };
			tradeExportList.add(title);
			// 根据条件查询交易记录
			QueryPage agentTradeList = tradeAgentManager.searchAgentByParameterMap(agentTrade, 1, Integer.MAX_VALUE);

			if (agentTradeList != null) {
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (AgentTrade tmp : (List<AgentTrade>) agentTradeList.getItems()) {
					String createTime = "";
					String finishTime = "";
					String status = "";
					if (tmp.getGmtCreate() != null) {
						createTime = df1.format(tmp.getGmtCreate());
					}
					if (tmp.getGmtTradeFinish() != null) {

						finishTime = df1.format(tmp.getGmtTradeFinish());
					}
					if ("init".equals(tmp.getStatus())) {
						status = "新建";
					} else if ("success".equals(tmp.getStatus())) {
						status = "完成";
					} else if ("close".equals(tmp.getStatus())) {
						status = "关闭";
					}
					String[] data = { tmp.getUserNick(), tmp.getTid(), tmp.getGoodsSn(), tmp.getGoodsName(),
							tmp.getAgentManagerName(), String.valueOf(tmp.getTotalAmount()),
							MoneyUtil.getFormatMoney(tmp.getTotalPrice(), "0.00"), createTime, finishTime, status };
					tradeExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, tradeExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "导出失败！");
			log.error(e);
		} finally {
			outwt.close();
		}

	}

	class AgentTradeTidComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			AgentTrade a = (AgentTrade) o1;
			AgentTrade b = (AgentTrade) o2;
			return -(a.getTid()).compareTo(b.getTid());
		}
	}

	class AgentTradeGoodsNameComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			AgentTrade a = (AgentTrade) o1;
			AgentTrade b = (AgentTrade) o2;
			return -a.getGoodsName().compareTo(b.getGoodsName());
		}
	}
}
