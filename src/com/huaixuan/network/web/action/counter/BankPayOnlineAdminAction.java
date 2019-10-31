package com.huaixuan.network.web.action.counter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.counter.query.PayOnlineSearchQuery;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.account.BankPayOnlineAdminManager;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/counter")
public class BankPayOnlineAdminAction extends BaseAction {

	private static final long serialVersionUID = 4069363560144992191L;
	@Autowired
	private BankPayOnlineAdminManager bankPayOnlineAdminManager;
	private static final int PAGE_SIZE = 10;

	// private Map<String, String> payOnlineSearch;

	// private List<BaseBankPayOnline> payOnlineSearchResult;

	// private Page page;

	/**
	 * 跳转到支付流水搜索页面
	 *
	 * @return
	 */
	@AdminAccess({EnumAdminPermission.A_PAYONLINE_ADMIN_SEARCH})
	@RequestMapping(value = "/showBankPayOnlineAdminSearch")
	public String showBankPayOnlineAdminSearch(
			@ModelAttribute("payOnlineSearchQuery") PayOnlineSearchQuery payOnlineSearchQuery,
			Model model) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String todayDate = sdf.format(date);
		payOnlineSearchQuery.setPayDateStart(todayDate);
		payOnlineSearchQuery.setPayDateEnd(todayDate);
		model.addAttribute("payOnlineSearchQuery", payOnlineSearchQuery);
		return "/counter/bankPayOnlineSearch";
	}

	/**
	 * 支付流水查看功能
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchBankPayOnlineList")
	public String searchBankPayOnlineList(
			@ModelAttribute("payOnlineSearchQuery") PayOnlineSearchQuery payOnlineSearchQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model) throws Exception {

		if (payOnlineSearchQuery != null) {
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getBankType())) {
				payOnlineSearchQuery.setBankType(payOnlineSearchQuery
						.getBankType().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getIsSucceed())) {
				payOnlineSearchQuery.setIsSucceed(payOnlineSearchQuery
						.getIsSucceed().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getPaymentType())) {
				payOnlineSearchQuery.setPaymentType(payOnlineSearchQuery
						.getPaymentType().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getFlagCompare())) {
				payOnlineSearchQuery.setFlagCompare(payOnlineSearchQuery
						.getFlagCompare().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getPayDest())) {
				payOnlineSearchQuery.setPayDest(payOnlineSearchQuery
						.getPayDest().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getPayDateStart())) {
				payOnlineSearchQuery.setPayDateStart(payOnlineSearchQuery
						.getPayDateStart().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getPayDateEnd())) {
				payOnlineSearchQuery.setPayDateEnd(payOnlineSearchQuery
						.getPayDateEnd().split("\\,")[0].trim());
			}
			if (StringUtil.isNotBlank(payOnlineSearchQuery.getBankBillNo())) {
				payOnlineSearchQuery.setBankBillNo(payOnlineSearchQuery
						.getBankBillNo().split("\\,")[0].trim());
			}
		}

		QueryPage payOnlineListPage = bankPayOnlineAdminManager
				.searchBankPayOnlineListByCondition(payOnlineSearchQuery,
						currPage, PAGE_SIZE);
		if (payOnlineListPage != null) {
			model.addAttribute("payOnlineListPage", payOnlineListPage);
		}
		return "/counter/bankPayOnlineSearch";
	}

	// public void setBankPayOnlineAdminManager(
	// BankPayOnlineAdminManager bankPayOnlineAdminManager) {
	// this.bankPayOnlineAdminManager = bankPayOnlineAdminManager;
	// }
	//
	// public Map<String, String> getPayOnlineSearch() {
	// return payOnlineSearch;
	// }
	//
	// public void setPayOnlineSearch(Map<String, String> payOnlineSearch) {
	// this.payOnlineSearch = payOnlineSearch;
	// }

	// public List<BaseBankPayOnline> getPayOnlineSearchResult() {
	// return payOnlineSearchResult;
	// }
	//
	// public void setPayOnlineSearchResult(
	// List<BaseBankPayOnline> payOnlineSearchResult) {
	// this.payOnlineSearchResult = payOnlineSearchResult;
	// }
	//
	// public Page getPage() {
	// this.page = (page == null) ? new Page() : page;
	// return page;
	// }
	//
	// public void setPage(Page page) {
	// this.page = page;
	// }

}
