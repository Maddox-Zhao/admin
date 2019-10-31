package com.huaixuan.network.web.action.crm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.crm.ConnectRecord;
import com.huaixuan.network.biz.enums.EnumConnectRecordStatus;
import com.huaixuan.network.biz.enums.EnumConnectRecordType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.crm.ConnectRecordManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * 维护记录管理
 * 
 * @author chenyan 2010/08/05
 * 
 */
@Controller
@RequestMapping("crm")
public class CrmConnectRecordAction extends BaseAction {

	private static final long serialVersionUID = 6172691236100742123L;
	@Autowired
	private ConnectRecordManager connectRecordManager;

	// private ConnectRecord connectRecord;
	// private Map<String, String> connectRecordStatusMap = EnumConnectRecordStatus.toMap();
	// private Map<String, String> connectRecordTypeMap = EnumConnectRecordType.toMap();
	// private Map<String, String> parMap = new HashMap<String, String>();
	// private Page page;
	// private String message;
	// private boolean modifyFlag;
	// private String idTmp;

	/**
	 * 维护记录查询
	 * 
	 * @return String
	 * @author chenyan 2010/08/05
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/crmConnectManage")
	public String crmConnectManage(
			@RequestParam(value = "initFlag", required = false, defaultValue = "false") String initFlag,
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "gmtTimeStart", required = false, defaultValue = "") String gmtTimeStart,
			@RequestParam(value = "gmtTimeEnd", required = false, defaultValue = "") String gmtTimeEnd,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		// if (StringUtil.isBlank(initFlag)) {
		// //删除记录后跳转过来的情况
		// initFlag = context.get("init") == null ? "" : (String) context.get("init");
		// }

		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
			// 初始化时候，需要设置默认时间
			gmtTimeStart = DateUtil.getDateToString(new Date());
		}

		Map parMap = new HashMap();
		if (gmtTimeStart != null && gmtTimeStart.length() > 0) {
			parMap.put("gmtTimeStart", gmtTimeStart);
		}
		if (gmtTimeStart != null && gmtTimeStart.length() > 0) {
			parMap.put("gmtTimeEnd", gmtTimeEnd);
		}
		if (userName != null && userName.length() > 0) {
			parMap.put("userName", userName);
		}
		if (status != null && status.length() > 0) {
			parMap.put("status", status);
		}

		// 此功能修改为只查询预约记录chenyan 2010/09/10 start
		parMap.put("type", "order");
		// chenyan 2010/09/10 end

		// 取得符合条件的数据总数
		// int count = connectRecordManager.listConnectRecordByParameterCount(parMap);
		// //进行分页的设置
		// page = new Page();
		// page.setPageSize(pageSize);
		// page.setTotalRowsAmount(count);
		// page.setCurrentPage(currentPage);
		QueryPage query = connectRecordManager.listConnectRecordByParameter(parMap, currPage, pageSize);

		model.addAttribute("connectRecordStatusMap", EnumConnectRecordStatus.toMap());
		model.addAttribute("connectRecordTypeMap", EnumConnectRecordType.toMap());

		model.addAttribute("query", query);

		model.addAttribute("userName", userName);
		model.addAttribute("status", status);
		model.addAttribute("gmtTimeStart", gmtTimeStart);
		model.addAttribute("gmtTimeEnd", gmtTimeEnd);

		return "/crm/crmConnectManage";
	}

	/**
	 * 添加预约沟通记录
	 * 
	 * @return String
	 * @author chenyan 2010/08/05
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("crmAddOrderRecord")
	public String crmAddOrderRecord(@ModelAttribute("connectRecord") ConnectRecord connectRecord, Model model,
			AdminAgent agent) throws ServletException, IOException {
		// 组装预约沟通记录
		ConnectRecord connectRecordTmp = new ConnectRecord();
		connectRecordTmp.setAdminUser(agent.getUsername());
		connectRecordTmp.setContent(connectRecord.getOrderContent());
		connectRecordTmp.setGmtTime(DateUtil.strToDate(connectRecord.getGmtOrderTimeStr(), "yyyy-MM-dd"));
		connectRecordTmp.setUserId(connectRecord.getUserId());
		connectRecordTmp.setType("order");
		// 预约沟通记录默认为未完成
		connectRecordTmp.setStatus(EnumConnectRecordStatus.STATUS_NEW.getKey());
		// 插入数据操作
		connectRecordManager.addConnectRecord(connectRecordTmp);

		// 初始化用户ID，用于显示用户详情页面

		return "redirect:/crm/crmUserDetail.html?userId=" + connectRecord.getUserId();
	}

	/**
	 * 添加沟通记录
	 * 
	 * @return String
	 * @author chenyan 2010/08/05
	 */
	@RequestMapping("crmAddConnectRecord")
	public String crmAddConnectRecord(@ModelAttribute("connectRecord") ConnectRecord connectRecord, Model model,
			AdminAgent agent) {
		// 组装沟通记录
		ConnectRecord connectRecordTmp = new ConnectRecord();
		connectRecordTmp.setAdminUser(agent.getUsername());
		connectRecordTmp.setContent(connectRecord.getConnectContent());
		connectRecordTmp.setGmtTime(new Date());
		connectRecordTmp.setUserId(connectRecord.getUserId());
		connectRecordTmp.setType("connect");
		// 沟通记录默认为已完成
		connectRecordTmp.setStatus(EnumConnectRecordStatus.STATUS_FINISHED.getKey());
		// 插入数据操作
		connectRecordManager.addConnectRecord(connectRecordTmp);

		// 初始化用户ID，用于显示用户详情页面
		return "redirect:/crm/crmUserDetail.html?userId=" + connectRecord.getUserId();
	}

	/**
	 * 修改沟通纪录
	 * 
	 * @return String
	 * @author chenyan 2010/09/09
	 */
	@RequestMapping({ "crmModifyConnectRecordSelf", "crmModifyConnectRecord" })
	public String crmModifyConnectRecord(@RequestParam("modifyConnectId") Long modifyConnectId,
			@RequestParam("modifyConnectContent") String modifyConnectContent,
			@RequestParam("modifyConnectUserId") Long modifyConnectUserId) {
		// 组装沟通记录
		ConnectRecord connectRecordTmp = new ConnectRecord();
		connectRecordTmp.setId(modifyConnectId);
		connectRecordTmp.setContent(modifyConnectContent);

		// 插入数据操作
		connectRecordManager.updateConnectRecordById(connectRecordTmp);

		// 初始化用户ID，用于显示用户详情页面
		return "redirect:/crm/crmUserDetail.html?userId=" + modifyConnectUserId;
	}

	/**
	 * 修改预约纪录
	 * 
	 * @return String
	 * @author chenyan 2010/09/09
	 */
	@RequestMapping({ "crmModifyOrderRecordSelf", "crmModifyOrderRecord" })
	public String crmModifyOrderRecord(@RequestParam("modifyOrderId") Long modifyOrderId,
			@RequestParam("modifyOrderTime") Date modifyOrderTime,
			@RequestParam("modifyOrderContent") String modifyOrderContent,
			@RequestParam("modifyOrderUserId") Long modifyOrderUserId) {
		// 组装沟通记录
		ConnectRecord connectRecordTmp = new ConnectRecord();
		connectRecordTmp.setId(modifyOrderId);
		connectRecordTmp.setGmtTime(modifyOrderTime);
		connectRecordTmp.setContent(modifyOrderContent);

		// 插入数据操作
		connectRecordManager.updateConnectRecordById(connectRecordTmp);

		// 初始化用户ID，用于显示用户详情页面
		return "redirect:/crm/crmUserDetail.html?userId=" + modifyOrderUserId;
	}

	/**
	 * 逻辑删除预约记录
	 * 
	 * @return String
	 * @author chenyan 2010/08/05
	 */
	// public String crmDeleteOrderRecordById() {
	// String id = getRequest().getParameter("id");
	// //验证数据完整性
	// ConnectRecord connectRecordOri = connectRecordManager.getConnectRecordById(Long
	// .parseLong(id));
	// if (connectRecordOri != null && connectRecordOri.getStatus().equals("new")
	// && connectRecordOri.getType().equals("order")) {
	// ConnectRecord connectRecordTmp = new ConnectRecord();
	// connectRecordTmp.setId(Long.parseLong(id));
	// connectRecordTmp.setStatus("deleted");
	// connectRecordManager.updateConnectRecordStatusById(connectRecordTmp);
	// message = "预约数据删除成功！";
	// }
	// ActionContext.getContext().put("init", "true");
	// return this.crmConnectManage();
	// }

	/**
	 * 查看详细记录信息
	 * 
	 * @return String
	 * @author chenyan 2010/08/05
	 */
	@RequestMapping("crmShowConnectRecordDetail")
	public String crmShowConnectRecordDetail(@RequestParam("id") Long id, Model model) {
		ConnectRecord connectRecord = connectRecordManager.getConnectRecordById(id);
		model.addAttribute("connectRecord", connectRecord);

		Date now = new Date();
		Date before1 = DateUtil.getDate(now, -1);
		if (before1.getTime() <= connectRecord.getGmtCreate().getTime()
				|| connectRecord.getType().equals(EnumConnectRecordType.TYPE_ORDER.getKey())) {
			// 可以修改当天的沟通记录或者修改预约记录
			model.addAttribute("modifyFlag", true);
		} else {
			model.addAttribute("modifyFlag", false);
		}

		model.addAttribute("connectRecordTypeMap", EnumConnectRecordType.toMap());
		model.addAttribute("connectRecordStatusMap", EnumConnectRecordStatus.toMap());

		return "/crm/crmShowConnectRecordDetail";
	}

	/**
	 * 修改沟通记录
	 * 
	 * @return String
	 * @author chenyan 2010/08/05
	 */
	@RequestMapping("crmUpdateConnectRecord")
	public String crmUpdateConnectRecord(@ModelAttribute("connectRecord") ConnectRecord connectRecord, Model model) {
		// 更新数据
		connectRecord.setGmtTime(DateUtil.strToDate(connectRecord.getGmtConnectTimeStr(), "yyyy-MM-dd"));
		connectRecordManager.updateConnectRecordById(connectRecord);
		model.addAttribute("message", "数据更新成功");
		return crmShowConnectRecordDetail(connectRecord.getId(), model);
	}
}
