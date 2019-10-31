package com.huaixuan.network.web.action.counter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.TransLogApp;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumTransAppStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.counter.AccountTransAdminManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.itrans.domain.base.TransLogDO;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/counter")
public class AccountTransAdminAction extends BaseAction {

	private static final long serialVersionUID = 4069363560145982191L;

	// private Map<String, String> parMap = new HashMap<String, String>();
	//
	// private Page page;
	//
	@Autowired
	private AccountTransAdminManager accountTransAdminManager;

	private static final int PAGE_SIZE = 10;

	//
	// private List<TransLogDO> transLogList;
	//
	// private long accountLogId;
	//
	// private TransLogDO transAjustLog;
	//
	// private String message;
	//
	// private Map<String, String> accTransReq;
	//
	// private List<EnumSubTransCode> transSubCode;
	//
	// private String transType;
	//
	// private List<TransApp> transAppList;
	//
	private Map<String, String> transAppStatusMap = EnumTransAppStatus.toMap();

	// private TransApp transApp;
	//
	// private List<TransLogApp> transLogAppList;
	//
	// private TransLogApp transLogApp;

	/**
	 * 根据账务流水ID取得该笔事务流水账务信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/getTransLogByAccountLogId")
	public String getTransLogByAccountLogId(HttpServletRequest request,
			Model model) {
		String accountLogId = request.getParameter("accountLogId");
		String message = request.getParameter("message");
		if (StringUtil.isNotBlank(accountLogId)) {
			List<TransLogDO> transLogList = accountTransAdminManager
					.getTransLogByAccountLogId(Long.valueOf(accountLogId));
			model.addAttribute("transLogList", transLogList);
		} else {
			return "/counter/counter_error";
		}
		if (StringUtil.isNotBlank(message)) {
			if ("success".equals(message)) {
				message = "申请成功 ";
			} else {
				message = "申请记录已经存在";
			}
		}
		model.addAttribute("message", message);
		model.addAttribute("accountLogId", accountLogId);
		return "/counter/transLogSeach";
	}

	// /**
	// * 进行账务补帐申请
	// *
	// * @return
	// */
	// public String doTransApp() {
	//
	// TransApp transApp = new TransApp();
	// transApp.setStatus("new");
	//
	// String subTransCode = accTransReq.get("subTransCode");
	// transApp.setSubTransCode(subTransCode);
	// transApp.setAmount(Double.valueOf(accTransReq.get("amount")));
	// transApp.setMemo(accTransReq.get("memo"));
	// transApp.setTransDate(accTransReq.get("transDate"));
	//
	// if (StringUtil.isNotBlank(accTransReq.get("bankType"))) {
	// transApp.setBankType(accTransReq.get("bankType"));
	// } else {
	// transApp.setBankType("");
	// }
	//
	// if (StringUtil.isNotBlank(accTransReq.get("innerBizNo"))) {
	// transApp.setInnerBizNo(accTransReq.get("innerBizNo"));
	// } else {
	// transApp.setInnerBizNo("");;
	// }
	//
	// if (StringUtil.isNotBlank(accTransReq.get("outBizNo"))) {
	// transApp.setOutBizNo(accTransReq.get("outBizNo"));
	// } else {
	// transApp.setOutBizNo("");
	// }
	//
	// if (StringUtil.isNotBlank(accTransReq.get("outDate"))) {
	// transApp.setOutDate(accTransReq.get("outDate"));
	// } else {
	// transApp.setOutDate("");
	// }
	//
	// if (StringUtil.equalsIgnoreCase(subTransCode,
	// EnumSubTransCode.TXCODE_DEPOSIT_ONLINE.getCode())) {
	// transApp.setInAccountNo(accTransReq.get("accountNo").trim());
	// transApp.setOutAccountNo("");
	// } else if (StringUtil.equalsIgnoreCase(subTransCode,
	// EnumSubTransCode.TXCODE_WITHDRAW_BATCH_SUCCESS.getCode())
	// || StringUtil.equalsIgnoreCase(subTransCode,
	// EnumSubTransCode.TXCODE_WITHDRAW_SUCCESS.getCode())) {
	// transApp.setOutAccountNo(accTransReq.get("accountNo").trim());
	// transApp.setInAccountNo("");
	// } else if (StringUtil.equalsIgnoreCase(subTransCode,
	// EnumSubTransCode.TXCODE_TRANSFER_RETURN_GOODS.getCode())) {
	// transApp.setOutAccountNo(accTransReq.get("outAccountNo").trim());
	// transApp.setInAccountNo(accTransReq.get("inAccountNo").trim());
	// }
	//
	// message = accountTransAdminManager.doTransApp(transApp);
	// message = this.getText(message);
	//
	// return SUCCESS;
	// }
	//
	// /**
	// * 进行账务补帐申请管理
	// *
	// * @return
	// */
	// public String managerTransApp() {
	// String transDateStart = (String) parMap.get("transDateStart");
	// String transDateEnd = (String) parMap.get("transDateEnd");
	// if (StringUtil.isBlank(transDateStart)
	// && StringUtil.isBlank(transDateEnd)) {
	// Date date = new Date();
	// transDateStart = DateUtil.getDiffDate(date, -30);
	// transDateEnd = DateUtil.getDateToString(date);
	// parMap.put("transDateStart", transDateStart);
	// parMap.put("transDateEnd", transDateEnd);
	// }
	//
	// int count = accountTransAdminManager.getManagerTransAppCount(parMap);
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(count);
	// page.setCurrentPage(currentPage);
	// if (count > 0) {
	// transAppList = accountTransAdminManager.getManagerTransApp(parMap,
	// page);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 单个申请单信息
	// *
	// * @return
	// */
	// public String showTransAppInfo() {
	// transApp = accountTransAdminManager.getTransAppInfo(parMap);
	// return SUCCESS;
	// }
	//
	// /**
	// * 进行帐务补帐操作
	// *
	// * @return
	// */
	// public String doAddAccTrans() {
	// AccountTransReq transReq = new AccountTransReq();
	// accTransReq.put("operator", getLoginAdminUser().getUsername());
	// // 将accTransReq里面的属性拷贝到transReq里面
	// try {
	// if (accTransReq.get("amount") != null) {
	// transReq.setAmount(new Money(accTransReq.get("amount")));
	// }
	// accTransReq.remove("amount");
	// if (accTransReq.get("transDate") != null) {
	// // SimpleDateFormat format = new
	// // SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	// // transReq.setTransDate(format.parse(accTransReq.get("transDate")));
	// Date date = new Date();
	// transReq.setTransDate(date);
	// }
	// accTransReq.remove("transDate");
	// if (accTransReq.get("bankType") != null) {
	// transReq.setBankType(EnumInstitution.getByName(accTransReq
	// .get("bankType")));
	// }
	// accTransReq.remove("bankType");
	// if (accTransReq.get("subTransCode") != null) {
	// transReq.setSubTransCode(EnumSubTransCode.getByCode(accTransReq
	// .get("subTransCode")));
	// }
	// accTransReq.remove("subTransCode");
	// if (accTransReq.get("outDate") != null) {
	// transReq.setOutDate(accTransReq.get("outDate").replace("-", ""));
	// }
	// accTransReq.remove("outDate");
	// BeanUtils.copyProperties(transReq, accTransReq);
	// } catch (Exception e) {
	// log.error("doAddAccTrans occur error", e);
	// message = AccountTransResult.TXN_RESULT_REQ_PARA_NOT_MATCH
	// .getMessage();
	// return this.SUCCESS;
	// }
	// message = accountTransAdminManager.doAddAccTrans(transReq, parMap);
	//
	// transApp = accountTransAdminManager.getTransAppInfo(parMap);
	// // if
	// // (message.equals(AccountTransResult.TXN_RESULT_SUCCESS.getMessage()))
	// // addWorkLog("账务补帐操作", transReq.getSubTransCode().getCode(),
	// // EnumWorkLogType.Acctrans
	// // .getKey(), "账务补帐操作成功");
	// // else
	// // addWorkLog("账务补帐操作", transReq.getSubTransCode().getCode(),
	// // EnumWorkLogType.Acctrans
	// // .getKey(), "账务补帐操作失败");
	// return this.SUCCESS;
	// }
	//
	// /**
	// * 根据补帐类型取得二级枚举列表
	// *
	// * @return
	// */
	// public String listSubTransCodeByType() {
	// transSubCode = EnumSubTransCode.listSubTransCodeByType(transType);
	// return this.SUCCESS;
	// }

	/**
	 * 显示帐务冲正搜索页面
	 *
	 * @return
	 */
	@AdminAccess({EnumAdminPermission.A_TRANS_LOG_CANCEL_SEARCH})
	@RequestMapping(value = "/showTransLogFlagCancelSeach")
	public String showTransLogFlagCancelSeach() {
		return "/counter/transLogSeach";
	}

	/**
	 * 帐务充值备注输入页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/showTransAjustMemo")
	public String showTransAjustMemo() {
		return "/counter/accountProcessMemo";
	}

	// /**
	// * 帐务补帐页面
	// *
	// * @return
	// */
	// public String showAddAccTrans() {
	// return this.SUCCESS;
	// }

	/**
	 * 冲正申请
	 *
	 * @return
	 */
	@RequestMapping(value = "/doAccountTransAjustApp")
	public String doAccountTransAjustApp(HttpServletRequest request, Model model) {
		String transAjustLogId = request.getParameter("transAjustLogId");
		String transMemo = request.getParameter("transMemo");
		TransLogDO transAjustLog = new TransLogDO();
		transAjustLog.setId(Long.valueOf(transAjustLogId));
		transAjustLog.setTransMemo(transMemo);
		String message = accountTransAdminManager
				.doAccountTransAjustApp(transAjustLog);

		String accountLogId = request.getParameter("accountLogId");
		return "redirect:/counter/getTransLogByAccountLogId.html?accountLogId="
				+ accountLogId + "&message=" + message;
	}

	/**
	 * 冲正申请列表
	 *
	 * @return
	 */
	@AdminAccess({EnumAdminPermission.A_MANAGER_TRANS_LOG_APP})
	@RequestMapping(value = "/managerTransLogApp")
	public String managerTransLogApp(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("status", request.getParameter("status"));
		parMap.put("transDateStart", request.getParameter("transDateStart"));
		parMap.put("transDateEnd", request.getParameter("transDateEnd"));
		String transDateStart = (String) parMap.get("transDateStart");
		String transDateEnd = (String) parMap.get("transDateEnd");
		if (StringUtil.isBlank(transDateStart)
				&& StringUtil.isBlank(transDateEnd)) {
			Date date = new Date();
			transDateStart = DateUtil.getDiffDate(date, -30);
			transDateEnd = DateUtil.getDateToString(date);
			parMap.put("transDateStart", transDateStart);
			parMap.put("transDateEnd", transDateEnd);
		}

		QueryPage transLogAppList = accountTransAdminManager
				.getManagerTransLogApp(parMap, currPage, PAGE_SIZE);
		if (transLogAppList != null) {
			model.addAttribute("transLogAppList", transLogAppList);
		}
		model.addAttribute("transAppStatusMap", transAppStatusMap);
		model.addAttribute("status", request.getParameter("status"));
		model.addAttribute("transDateStart",transDateStart
				);
		model.addAttribute("transDateEnd", transDateEnd);
		model.addAttribute("transAppStatusMap", transAppStatusMap);
		return "/counter/managerTransLogApp";
	}

	/**
	 * 账务冲正信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/showTransLogAppInfo")
	public String showTransLogAppInfo(HttpServletRequest request, Model model) {
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("transLogAppId", request.getParameter("transLogAppId"));
		TransLogApp transLogApp = accountTransAdminManager
				.getTransLogAppInfo(parMap);
		if (transLogApp != null) {
			TransLogDO transAjustLog = this.accountTransAdminManager
					.getTransLogByLogId(transLogApp.getTransLogId());
			List<TransLogDO> transLogList = new ArrayList<TransLogDO>();
			transLogList.add(transAjustLog);
			TransLogDO transAjustLog2 = null;
			if (transAjustLog != null && transAjustLog.getFlagCancel() != null) {
				transAjustLog2 = this.accountTransAdminManager
						.getTransLogByLogId(transAjustLog.getRelatedTransId());
			} else {
				try {
					transAjustLog2 = new TransLogDO();
					BeanUtils.copyProperties(transAjustLog2, transAjustLog);
					transAjustLog2.setId(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			transLogList.add(transAjustLog2);
			model.addAttribute("transLogList", transLogList);
		}
		model.addAttribute("transLogApp", transLogApp);
		model.addAttribute("message", request.getParameter("message"));
		return "/counter/transLogAppInfo";
	}

	/**
	 * 进行账务充正操作
	 *
	 * @return
	 */
	@RequestMapping(value = "/doAccountTransAjust")
	public String doAccountTransAjust(HttpServletRequest request,
			AdminAgent adminAgent, Model model) {
		TransLogDO transAjustLog = new TransLogDO();
		transAjustLog.setTransOperator(adminAgent.getUsername());
		transAjustLog.setId(Long.valueOf(request.getParameter("id")));
		transAjustLog.setTransMemo(request.getParameter("transMemo"));
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("status", request.getParameter("status"));
		parMap.put("transLogAppId", request.getParameter("transLogAppId"));
		String message = accountTransAdminManager.doAccountTransAjust(
				transAjustLog, parMap);
		// if
		// (message.equals(AccountTransResult.TXN_RESULT_SUCCESS.getMessage()))
		// addWorkLog("账务冲正操作", transAjustLog.getId() + "",
		// EnumWorkLogType.Acctrans.getKey(),
		// "账务充正操作成功");
		// else
		// addWorkLog("账务冲正操作", transAjustLog.getId() + "",
		// EnumWorkLogType.Acctrans.getKey(),
		// "账务充正操作失败");
		if (((String) parMap.get("status")).equals("fail")) {
			message = "fail";
		} else {
			message = "success";
		}
		return "redirect:/counter/showTransLogAppInfo.html?transLogAppId="
				+ request.getParameter("transLogAppId") + "&message=" + message;
	}

}
