package com.huaixuan.network.web.action.crm;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.crm.ConnectRecord;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumConnectRecordType;
import com.huaixuan.network.biz.enums.EnumInPrestige;
import com.huaixuan.network.biz.enums.EnumInTbYouaPrestige;
import com.huaixuan.network.biz.enums.EnumUserStatus;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.crm.ConnectRecordManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.trade.TradeAgentManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.user.UserAgentManager;
import com.huaixuan.network.biz.service.user.UserInfoManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.biz.service.user.UserService;
import com.huaixuan.network.common.util.ControlUtil;
import com.huaixuan.network.common.util.RequestUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping("/crm")
public class CrmUsersAction extends BaseAction {

	Log log = LogFactory.getLog(getClass());

	// //���������������
	private static final Double cashPercent = 0.01;
	private static final int VIP_MEMO_MAX_LENGTH = 200;
	private static final String USER_TYPE_NOT_VIP = "d";
	private static final String USER_TYPE_VIP = "w";
	private static final String USER_INIT_PASSWORD = "123456";
	// private UserDao userDao;
	// private TradeManager tradeManager;
	@Autowired
	TradeAgentManager tradeAgentManager;
	// private FeedbackManager feedbackManager;
	// private CommentsManager commentsManager;
	// private TicketRecordManager ticketRecordManager;
	// private GoodsManager goodsManager;
	// private UserManager userManager;
	// private Page page;
	// private User user;
	// private UserInfo userInfo;
	// private boolean flag;
	// private List<String> roles;
	// private String userId;
	// private Integer isAdmin;
	// private AgentInfo agentInfo;
	@Autowired
	private UserAgentManager userAgentManager;
	@Autowired
	private UserInfoManager userInfoManager;
	@Autowired
	private RegionManager regionManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private ResourcesManager resourcesManager;
	@Autowired
	private ConnectRecordManager connectRecordManager;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private TradeManager tradeManager;
	@Autowired
	UserService userService;

	// private List<Region> cityListInit;
	// private Map<String, String> presgtige = EnumInPrestige.toMap();
	// private Map<String, String> cashStatusMap = EnumCashStatus.toMap();
	// private AdminService adminService;
	// private Map<String, String> tbYoua = EnumInTbYouaPrestige.toMap();
	// private String cashMemoXilangIn;
	// private String succInfo;
	// private String errorInfo;
	// private GoodsAgentManager goodsAgentManager;
	// private StorageManager storageManager;
	// private Map<String, String> parMap = new HashMap<String, String>();
	// private String message;
	// private List<Resources> resourceses = new ArrayList();
	// private List<Region> selectcitylist = new ArrayList<Region>();
	// private GoodsInstance search;
	// private CategoryManager categoryManager;
	// private GoodsInstanceManager goodsInstanceManager;
	// private List<UserAddress> userAddressList = new ArrayList<UserAddress>();
	// private Map<UserAddress, String> userAddressMap = new
	// HashMap<UserAddress, String>();
	// private UserAddressManager userAddressManager;
	// private UserInfo loginUserInfo;

	// private DepositoryFirstManager depositoryFirstManager; //һ���ֿ�
	// private List<DepositoryFirst> depositoryFirstList;

	/**
	 * ע���û�����
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crmRegusers")
	public String crmRegistedUsers(
			@ModelAttribute("user") User user,
			@RequestParam(value = "actionType", required = false, defaultValue = "") String actionType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model) throws Exception {
		List<User> users = new ArrayList<User>();

		if (user.getApply_stauts() == null) {
			user.setApply_stauts("init");
		}
		if(user.getApply_stauts().equals("all")){
		    user.setApply_stauts(null);
		}
		/**
		 * ���û�в鿴ȫ����Ȩ�ޣ�������user���������������usersΪ���û��ܿ�����Ȩ�� �������ӵ�������Χ�����ڡ������û�����ά�����û���
		 *
		 * @author wangdong
		 * @date 2010-10-21
		 */
		// Authz ai = new AuthzImpl();
		if (actionType != null
				&& "track".equals(actionType)
				&& !adminAgent
						.havePermission(EnumAdminPermission.A_CRM_SEE_ALL_USER)) {
			user.setAgentManagerId(adminAgent.getId() + "");
		}
		// ��ѯ��չ��Ա
		List<Admin> adminUsers = adminService.getAdminUserList();
		List<Category> catList = categoryManager.getCatInfoByDepth(1);
		model.addAttribute("catList", catList);
		model.addAttribute("adminUsers", adminUsers);
		QueryPage queryPage = userManager.getUserListByConditionWithPage(user,
				currPage, pageSize);
		if (queryPage.getItems() != null) {
			users = (List<User>) queryPage.getItems();
		}
		// �����û���Ӧ�ƹ���Ա
		User usertemp;
		AgentInfo info = null;
		Admin admin = null;
		for (int i = 0; i < users.size(); i++) {
			usertemp = users.get(i);
			// �������������ƹ���Ա
			if ("d".equals(usertemp.getType())
					|| "w".equals(usertemp.getType())) {
				info = userAgentManager.getUserAgentById(usertemp.getId());
				if (info != null) {
					for (int j = 0; j < adminUsers.size(); j++) {
						admin = adminUsers.get(j);
						// ������Ϣ��ID==�ƹ���ID�������ƹ���ԱUserName
						if (info.getAgent_manager_id() != null
								&& info.getAgent_manager_id().equals(
										admin.getId())) {
							usertemp.setAgentManagerName(admin.getUserName());
						}
					}
				}
				users.set(i, usertemp);
			}
		}
		if (user.getApply_stauts() == null) {
            user.setApply_stauts("all");
        }
		model.addAttribute("user", user);
		model.addAttribute("query", queryPage);
		model.addAttribute("adminUsers", adminUsers);
		model.addAttribute("actionType", actionType);
		model.addAttribute("categoryManager", categoryManager);
		model.addAttribute("controlUtil", new ControlUtil());
		if ("track".equals(actionType)) {
			return "/crm/crmTrackRegistedUsers";
		}
		return "/crm/crmRegistedUsers";
	}

	/**
	 * ϵͳ���붩��
	 *
	 * @author chenhang 2011-5-12
	 * @throws Exception
	 * @throws NumberFormatException
	 * @description
	 */
	@AdminAccess({EnumAdminPermission.A_CRM_IMPORT_ORDER})
	@RequestMapping("/doUploadTrade")
	public String doUploadTrade(Model model, AdminAgent adminAgent,
			MultipartHttpServletRequest request, HttpServletRequest request1)
			throws NumberFormatException, Exception {
		MultipartFile tradeFile = request.getFile("tradeFile");
		String userId = request1.getParameter("userId");
		String ms = "";
		if (0 == tradeFile.getSize()) {
			ms = "���ϴ��ļ���";
		} else {
			List<Object> doUploadTradeList = new ArrayList<Object>();
			doUploadTradeList = tradeManager.readExcel(tradeFile,Long.parseLong(userId));
			if (null != doUploadTradeList) {
				ms = (String) doUploadTradeList
						.get(doUploadTradeList.size() - 1);
			}
		}
		model.addAttribute("message", ms);
		return crmUserDetail(Long.parseLong(userId), model, request1,
				adminAgent);
	}

	/**
	 * 会员详细信息
	 *
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("crmUserDetail")
	public String crmUserDetail(@RequestParam("userId") Long userId,
			Model model, HttpServletRequest req, AdminAgent agent)
			throws Exception {
		model.addAttribute("loginUser", agent);

		User user = userManager.getUserById(userId);
		model.addAttribute("user", user);

		UserInfo userInfo = userInfoManager.getUserInfoByUserId(userId);
		model.addAttribute("userInfo", userInfo);

		AgentInfo agentInfo = userAgentManager.getAgentInfoById(userId);
		model.addAttribute("agentInfo", agentInfo);

		Region province = regionManager
				.getRegionByCode(agentInfo.getProvince());
		model.addAttribute("province", province);

		Region city = regionManager.getRegionByCode(agentInfo.getCity());
		model.addAttribute("city", city);

		// List<Region> provinceList = regionManager.getRegionByType(2);
		// List<Region> cityListInit = regionManager.getRegionByType(3);

		Map resParam = new HashMap();
		resParam.put("type", "howknow");
		List<Resources> resourceses = resourcesManager
				.getResourcesByMap(resParam);
		model.addAttribute("resourceses", resourceses);

		Map connectRecordConditionMap = new HashMap();
		// ȡ�ù�ͨ��¼
		connectRecordConditionMap.put("userId", userId);
		connectRecordConditionMap.put("type",
				EnumConnectRecordType.TYPE_CONNECT.getKey());
		List<ConnectRecord> connectRecordConnectList = connectRecordManager
				.ListConnectRecordByUserId(connectRecordConditionMap);
		Date now = new Date();
		Date before1 = DateUtil.getRelativeDate(now, -1);
		for (ConnectRecord connectRecordConnectTemp : connectRecordConnectList) {
			if (before1.getTime() <= connectRecordConnectTemp.getGmtCreate()
					.getTime()) {
				// �����޸ĵ���Ĺ�ͨ��¼
				connectRecordConnectTemp.setAllowModify("y");
			} else {
				connectRecordConnectTemp.setAllowModify("n");
			}
		}
		model.addAttribute("connectRecordConnectList", connectRecordConnectList);

		// ȡ��ԤԼ��¼
		connectRecordConditionMap.put("type",
				EnumConnectRecordType.TYPE_ORDER.getKey());
		List<ConnectRecord> connectRecordOrderList = connectRecordManager
				.ListConnectRecordByUserId(connectRecordConditionMap);
		for (ConnectRecord connectRecordOrderTemp : connectRecordOrderList) {
			if (before1.getTime() <= connectRecordOrderTemp.getGmtTime()
					.getTime()) {
				// �����޸Ĳ�����ԤԼʱ���ԤԼ��¼
				connectRecordOrderTemp.setAllowModify("y");
			} else {
				connectRecordOrderTemp.setAllowModify("n");
			}
		}
		model.addAttribute("connectRecordOrderList", connectRecordOrderList);

		// added by chenyan 2009/10/22 start�����˵�ȯ��һ����ֽ���
		if (agentInfo != null && agentInfo.getCashTicket() > 0) {
			agentInfo.setCashTotal(agentInfo.getCashTicket()
					/ (1 + cashPercent));
		}
		// added by chenyan 2009/10/22 end
		// List<User> users = new ArrayList<User>();
		// User temp = new User();
		// temp.setIsAdmin(1);
		// users = this.userDao.getUsersByCondition(temp, page);
		// List<Admin> adminUsers = adminService.getAdminUserList();
		// boolean isshow = (agentInfo != null ? true : false);
		//
		// model.addAttribute("users", adminUsers);
		// model.addAttribute("isshow", isshow);

		model.addAttribute("categorys", categoryManager.getCategoryForGuide());

		model.addAttribute("enumUserStatusMap", EnumUserStatus.toMap());
		model.addAttribute("enumUserTypeMap", EnumUserType.toMap());

		model.addAttribute("tbYoua", EnumInTbYouaPrestige.toMap());
		model.addAttribute("presgtige", EnumInPrestige.toMap());
		return "/crm/crmUserDetail";
	}

	/**
	 * ����ظ�
	 *
	 * @throws Exception
	 */
	@RequestMapping("crmIsallowApply")
	public String crmIsallowApply(
			@ModelAttribute("agentInfo") AgentInfo agentInfo,
			@RequestParam("actionType") String actionType,
			@RequestParam("crmUserId") Long crmUserId, Model model,
			HttpServletRequest req, AdminAgent agent) throws Exception {
		model.addAttribute("isAdmin", 0);

		String ctx = RequestUtil.getUrlContext(req);
		this.userAgentManager.isallowApply(agentInfo, ctx);

		if ("crm".equals(actionType)) {
			model.addAttribute("userId", crmUserId);
			return crmUserDetail(crmUserId, model, req, agent);
		}
		return crmRegistedUsers(null, null, 0, agent, model);
	}

	// /**
	// * �����Ա��չ��Ա�޸�
	// */
	// public String crmAgentManagerUpdate() {
	// HttpServletRequest request = getRequest();
	// String newAgentManagerId = request.getParameter("agent_manager_id");
	// String agentId = request.getParameter("agent_id");
	// Map<String, String> param = new HashMap<String, String>();
	// param.put("newAgentManagerId", newAgentManagerId);
	// param.put("agentId", agentId);
	// this.tradeAgentManager.updateAgentTradeById(param);
	// return SUCCESS;
	// }
	//
	// /**
	// * ��˶һ��ֽ����
	// * @return String
	// * @throws Exception
	// * @author chenyan 2009/10/22
	// */
	// @SuppressWarnings("unchecked")
	// public String cashApply() throws Exception {
	// String userId = getRequest().getParameter("userId");
	// Long id = null;
	// if (StringUtils.isNotBlank(userId)) {
	// id = Long.parseLong(userId);
	// }
	// AgentInfo agentInfo = this.userAgentManager.getAgentInfoById(id);
	//
	// cashMemoXilangIn =
	// StringUtil.trim(getRequest().getParameter("cashMemoXilangIn"));
	// String cashApplyFlag = getRequest().getParameter("cashApplyFlag");
	// if (StringUtil.isNotBlank(cashApplyFlag) &&
	// !cashApplyFlag.equals("cash")) {
	// //��������
	// if (StringUtil.isBlank(cashMemoXilangIn)) {
	// errorInfo = "�������������ɡ�";
	// } else if (cashMemoXilangIn.length() > 100) {
	// errorInfo = "��������������������100�����ڣ���";
	// } else if (agentInfo == null
	// || (agentInfo != null && !"init".equals(agentInfo.getCashStatus()))) {
	// errorInfo = "����״̬����ȷ�������½����ҳ�档";
	// } else {
	// //����������
	// if (cashApplyFlag.equals(EnumCashStatus.FAIL.getKey())) {
	// //�˻�����
	// Map map1 = new HashMap();
	// map1.put("cashTicket", 0 - agentInfo.getCashTicket());
	// map1.put("cashStatus", EnumCashStatus.FAIL.getKey());
	// map1.put("cashMemoXilang", cashMemoXilangIn);
	// map1.put("applyFail", "fail");
	// map1.put("id", agentInfo.getId());
	// userAgentManager.editAgentInfoCash(map1);
	// succInfo = "�����˻ز����ɹ���";
	// } else if (cashApplyFlag.equals("success")) {
	// //���ͨ��
	// Map map2 = new HashMap();
	// map2.put("cashStatus", EnumCashStatus.SUCCESS.getKey());
	// map2.put("cashMemoXilang", cashMemoXilangIn);
	// map2.put("applyFail", "succ");
	// map2.put("id", agentInfo.getId());
	// userAgentManager.editAgentInfoCash(map2);
	// succInfo = "����ͨ������ɹ���";
	// } else {
	// errorInfo = "����ɹ��������½����ҳ�档";
	// }
	// }
	// } else {
	// if (agentInfo == null || agentInfo.getGmtCashReceived() != null
	// || !agentInfo.getCashStatus().equals(EnumCashStatus.SUCCESS.getKey())) {
	// errorInfo = "����״̬����ȷ�������½����ҳ�档";
	// } else {
	// //���ֵ����
	// TicketRecord ticketRecord = new TicketRecord();
	// ticketRecord.setUserId(id);
	// ticketRecord.setTicketAmount(0 - agentInfo.getCashTicket());
	// ticketRecord.setType(EnumTicketRecordType.CASH_MINUS.getKey());
	// ticketRecord.setMemo("��ȡ�ֽ�");
	// ticketRecordManager.addTicketRecord(ticketRecord);
	//
	// Map map3 = new HashMap();
	// map3.put("cashStatus", EnumCashStatus.SUCCESS.getKey());
	// //���뷵������Ϊ0
	// map3.put("applyFail", "fail");
	// //��Ҫ��������ʱ��
	// map3.put("gmtCashReceived", new Date());
	// map3.put("gmtCashReceivedFlag", "apply");
	// map3.put("id", agentInfo.getId());
	// userAgentManager.editAgentInfoCash(map3);
	// succInfo = "���ֲ����ɹ���";
	// }
	// }
	//
	// //����ҳ���ʼ������
	// return crmUserDetail();
	// }
	//
	/**
	 * �����Ա��չ��Ա�޸�
	 */
	@RequestMapping("crmAgentManagerUpdateByJson")
	public @ResponseBody
	String crmAgentManagerUpdateByJson(HttpServletRequest request)
			throws Exception {
		String message = "";
		String newAgentManagerId = request.getParameter("param1");
		String userId = request.getParameter("param2");
		User user = userService.getUser(userId);
		if (user != null) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("newAgentManagerId", newAgentManagerId);
			param.put("userId", userId);
			tradeAgentManager.updateAgentTradeById(param);
			message = "success";
		} else {
			message = "failed";
		}
		return message;
	}

	/**
	 * ���ᣬ�ⶳע���û�
	 *
	 * @param id
	 * @param op
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crmFreezeOrRelease")
	public @ResponseBody
	String crmFreezeOrRelease(@RequestParam("param1") String id,
			@RequestParam("param2") String op, AdminAgent adminAgent) {
		String message = "";
		if (StringUtil.isBlank(id)) {
			message = "��ݲ���ȷ������ʧ�ܣ�";
			return message;
		}
		if (!isHasAuthority(id, adminAgent)) {
			message = "û��Ȩ�ޣ�����ʧ��";
			return message;
		}
		User user = userService.getUser(id);
		if (user != null) {
			int status = "freeze".equals(op) ? 2 : 1;
			user.setStauts(status);
			userService.saveUser(user);
			if (status == 2)
				message = "freeze success!";
			else
				message = "release success!";
		} else {
			message = "user not exist!";
		}

		return message;
	}

	/**
	 * ����/ά��ע���û�
	 *
	 * @param id
	 * @param op
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crmScrapOrReassert")
	public @ResponseBody
	String crmScrapOrReassert(@RequestParam("param1") String id,
			@RequestParam("param2") String op, AdminAgent adminAgent)
			throws Exception {
		String message = "";
		if (StringUtil.isBlank(id)) {
			message = "��ݲ���ȷ������ʧ�ܣ�";
			return message;
		}
		if (!isHasAuthority(id, adminAgent)) {
			message = "û��Ȩ�ޣ�����ʧ��";
			return message;
		}
		User user = userService.getUser(id);
		if (user != null) {
			String isScrap = "reassert".equals(op) ? "0" : "1";
			user.setIsScrap(isScrap);
			try {
				userService.updateUserScrap(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (isScrap.equals("0"))
				message = "reassert success!";
			else
				message = "scrap success!";
		} else {
			message = "user not exist!";
		}

		return message;
	}

	//
	// /**
	// * ������ͨ����Աҳ��
	// *
	// * @return
	// */
	// public String newAdmin() {
	//
	// return "success";
	// }
	//
	// /**
	// * ������ͨ����Ա
	// *
	// * @return
	// * @throws Exception
	// */
	// public String addNewAdmin() throws Exception {
	// // boolean flag1 = checkAccount();
	// // boolean flag2 = checkEmail();
	// // if (!flag1)
	// // this.addFieldError("user.account",
	// getText("error.user.account.isExist"));
	// // if (!flag2)
	// // this.addFieldError("user.email", getText("error.user.email.isExist"));
	// // if (!(flag1 && flag2))
	// // return "input";
	// // this.userManager.addNewAdmin(user, userInfo, roles);
	// return "success";
	// // TODO
	// }
	//
	// /**
	// * ��֤account�Ƿ���ע�� true ����ע�� false account��ע���
	// *
	// * @throws Exception
	// */
	// private Boolean checkAccount() throws Exception {
	// Boolean isExist = this.userManager.checkAccount(user.getAccount());
	// if (isExist) {
	// return false;
	// }
	// return true;
	// }
	//
	// /**
	// * ��֤email�Ƿ���ע�� true ����ע�� false email��ע���
	// */
	// private Boolean checkEmail() throws Exception {
	// Boolean isExist = this.userManager.checkEmail(user.getEmail());
	// if (isExist) {
	// return false;
	// }
	// return true;
	// }
	//
	// /**
	// * ��̨������ҳ
	// *
	// * @return
	// */
	// public String indexAdmin() {
	// ActionContext context = ActionContext.getContext();
	//
	// AdminUser admin = getLoginAdminUser();
	//
	// // ������Ϣ
	// List<Trade> tradeList = new ArrayList<Trade>();
	// tradeList = tradeManager.getTrades();
	//
	// int waitBuyerPay = 0;
	// int waitSellerSendGoods = 0;
	// int waitBuyerConfirmGoods = 0;
	// int tradeClose = 0;
	// int tradeFinish = 0;
	//
	// for (Trade trade : tradeList) {
	// if (EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus()))
	// waitBuyerPay++;
	// else if
	// (EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(trade.getStatus()))
	// waitSellerSendGoods++;
	// else if
	// (EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey().equals(trade.getStatus()))
	// waitBuyerConfirmGoods++;
	// else if (EnumTradeStatus.TRADE_CLOSE.getKey().equals(trade.getStatus()))
	// tradeClose++;
	// else
	// tradeFinish++;
	// }
	//
	// // ���Իظ���Ϣ
	// Feedback feedback = new Feedback();
	// feedback.setToUserType("shop");
	// List<Feedback> feedbackList = new ArrayList<Feedback>();
	// feedbackList = feedbackManager.getFeedbacksByCondition(feedback);
	// int notReply = 0;
	// for (Feedback fb : feedbackList) {
	// if ("no".equals(fb.getReplyFlag()))
	// notReply++;
	// }
	//
	// // ������Ϣ
	// Comments comments = new Comments();
	// // comments.setStatus("1");
	// List<Comments> commentsList = new ArrayList<Comments>();
	// commentsList = commentsManager.getCommentsByCondition(comments);
	// int notReplyComments = 0;
	// for (Comments comment : commentsList) {
	// if ("no".equals(comment.getReplyFlag()))
	// notReplyComments++;
	// }
	//
	// //ȥ�������Ϣ��ʾ zhangwy 2010/11/03
	// // // ������Ϣ
	// // parMap.put("stockAge", "fifteen");
	// // int fifteen = storageManager.getStockAgeByCondition(parMap,
	// null).size();
	//
	// context.put("admin", admin);
	//
	// context.put("waitBuyerPay", waitBuyerPay);
	// context.put("waitSellerSendGoods", waitSellerSendGoods);
	// context.put("waitBuyerConfirmGoods", waitBuyerConfirmGoods);
	// context.put("tradeClose", tradeClose);
	// context.put("tradeFinish", tradeFinish);
	// context.put("allTrade", tradeList.size());
	//
	// context.put("allFeedback", feedbackList.size());
	// context.put("notReply", notReply);
	//
	// context.put("allComments", commentsList.size());
	// context.put("notReplyComments", notReplyComments);
	//
	// // context.put("fifteen", fifteen);
	// return SUCCESS;
	// }
	//
	// /**
	// * ���Ӵ�����Ʒ���Ա�Ĺ���
	// * @return
	// * @throws Exception
	// * @author chenyan 2009/11/13 ����˷���chenyan 2010/04/15
	// */
	// // public String agentGoodsRelation() throws Exception {
	// // HttpServletRequest request = getRequest();
	// // userId = request.getParameter("userId");
	// // Long id = null;
	// // if (StringUtils.isNotBlank(userId)) {
	// // id = Long.parseLong(userId);
	// // }
	// // //���ô����Ա�������Ʒ�Ĺ�����ϵ
	// // setRelation(id);
	// //
	// // //����ҳ���ʼ������
	// // return userDetail();
	// // }
	// /**
	// * ���ô����Ա�������Ʒ�Ĺ�����ϵ
	// * @param id Long
	// * @author chenyan 2009/11/13 ����˷���chenyan 2010/04/15
	// */
	// // public void setRelation(Long id) {
	// // //ȡ�ô����Ļ�ԱID
	// // List<Long> currUserIdList =
	// goodsAgentManager.getCurrUserIdForAddGoodsRelation(id);
	// //
	// // if (currUserIdList != null && currUserIdList.size() > 0) {
	// // for (Long userId : currUserIdList) {
	// // //ȡ�����еĴ�����Ʒ
	// // List<Long> agentGoodsIdList = goodsManager.listAgentGoodsId();
	// // if (agentGoodsIdList != null && agentGoodsIdList.size() > 0) {
	// // for (Long goodsId : agentGoodsIdList) {
	// // GoodsAgent goodsAgentTmp = new GoodsAgent();
	// // goodsAgentTmp.setGmtCreate(new Date());
	// // goodsAgentTmp.setGmtModify(new Date());
	// // goodsAgentTmp.setUserId(userId);
	// // goodsAgentTmp.setGoodsId(goodsId);
	// // goodsAgentTmp.setStatus("y");
	// // //���ӹ�����ϵ
	// // if (goodsAgentManager.editStatusByUserIdGoodsId(goodsAgentTmp) <= 0) {
	// // //���û�и��µ���ݣ�����������
	// // goodsAgentManager.insertGoodsAgent(goodsAgentTmp);
	// // }
	// // }
	// // }
	// // }
	// // }
	// // }

	/**
	 * Json��ʽ�޸Ĵ��������޶�
	 *
	 * @return String
	 * @author chenyan 2010/04/15
	 */
	@RequestMapping("crmModifyAgentCountNumberJson")
	@ResponseBody
	public Object crmModifyAgentCountNumberJson(
			@RequestParam("param1") String userId,
			@RequestParam("param2") Long agentCountNumber, AdminAgent agent) {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		if (!isHasAuthority(userId, agent)) {
			return "û��Ȩ�ޣ�����ʧ��";
		}

		// �����޶���������
		userManager.updateAgentCount(Long.parseLong(userId), agentCountNumber);
		return "���������޶���³ɹ���";
	}

	// added by chenhang 2010/12/14 ��Ա�Զ���Ҫ��Ʊ��ѡ started
	@RequestMapping("crmModifyInvoice")
	@ResponseBody
	public Object crmModifyInvoice(@RequestParam("param1") String userId,
			@RequestParam("param2") String invoiceValue, AdminAgent agent) {

		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		if (!isHasAuthority(userId, agent)) {
			return "û��Ȩ�ޣ�����ʧ��";
		}

		// ���·�Ʊѡ�����
		userManager.updateAgentInvoice(Long.parseLong(userId), invoiceValue);
		return "��Ʊѡ���޸ĳɹ���";
	}

	// added by chenhang 2010/12/14 ��Ա�Զ���Ҫ��Ʊ��ѡ ended
	@RequestMapping("crmAddChannelJson")
	@ResponseBody
	public Object crmAddChannelJson(
			@RequestParam("param1") String userId,
			@RequestParam(value = "param2", required = false) String channelMemo,
			AdminAgent agent) {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		if (!isHasAuthority(userId, agent)) {
			return "û��Ȩ�ޣ�����ʧ�ܣ�";
		}
		// ���²���
		userManager.updateChannelMemoByUserId(Long.parseLong(userId),
				channelMemo);
		return "��ݸ��³ɹ���";
	}

	@RequestMapping("crmAddMainJson")
	@ResponseBody
	public Object crmAddMainJson(@RequestParam("param1") String userId,
			@RequestParam(value = "param2", required = false) String mainMemo,
			AdminAgent agent) {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		// ���²���
		userManager.updateMainMemoByUserId(Long.parseLong(userId), mainMemo);
		return "��ݸ��³ɹ���";
	}

	@RequestMapping("crmAddConnectJson")
	@ResponseBody
	public Object crmAddConnectJson(
			@RequestParam("param1") String userId,
			@RequestParam(value = "param2", required = false) String connectMemo,
			AdminAgent agent) {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		if (!isHasAuthority(userId, agent)) {
			return "û��Ȩ�ޣ�����ʧ�ܣ�";
		}

		// ���²���
		userManager.updateConnectMemoByUserId(Long.parseLong(userId),
				connectMemo);
		return "��ݸ��³ɹ���";
	}

	@RequestMapping("crmAddIsFulltimeJson")
	@ResponseBody
	public Object crmAddIsFulltimeJson(
			@RequestParam("param1") String userId,
			@RequestParam(value = "param2", required = false) String isFulltime,
			AdminAgent agent) {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		if (!isHasAuthority(userId, agent)) {
			return "û��Ȩ�ޣ�����ʧ�ܣ�";
		}

		// ���²���
		userManager
				.updateIsFulltimeByUserId(Long.parseLong(userId), isFulltime);
		return "��ݸ��³ɹ���";
	}

	/**
	 * Json��ʽ�޸����ڽ�����Ϣ
	 *
	 * @return String
	 * @author fanyj 2010/07/19
	 * @throws ParseException
	 */
	@RequestMapping("crmModifyPeriodByJson")
	@ResponseBody
	public Object crmModifyPeriodByJson(
			@RequestParam("param1") String userId,
			@RequestParam(value = "param2", required = false) String isPeriodPay,
			@RequestParam(value = "param3", required = false) String gmtPeriodPayStart,
			@RequestParam(value = "param4", required = false) String period,
			@RequestParam(value = "param5", required = false) Double periodAmountMax,
			@RequestParam(value = "param6", required = false) String ycperiod,
			AdminAgent agent) throws ParseException {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}
		if (!isHasAuthority(userId, agent)) {
			return "û��Ȩ�ޣ�����ʧ�ܣ�";
		}

		Long id;
		if (StringUtil.isNumeric(userId)) {
			id = Long.parseLong(userId);
		} else {
			return "�û�ID��ݲ���ȷ������ʧ�ܣ�";
		}

		if (!StringUtil.isNumeric(period)) {
			return "���ڣ��죩��ݲ���ȷ������ʧ�ܣ�";
		}
		if (!StringUtil.isNumeric(ycperiod)) {
			return "�ӳ٣��죩��ݲ���ȷ������ʧ�ܣ�";
		}
		if (periodAmountMax < 0 || periodAmountMax > 100000000) {
			return "�����ڽ�����������ݲ���ȷ������ʧ�ܣ�";
		}

		User user = userManager.getUser(id);
		if (user == null) {
			return "�û������ڣ�����ʧ�ܣ�";
		} else {
			Date periodPayStart = DateUtil
					.convertStringToDate(gmtPeriodPayStart);
			int periodDay = Integer.parseInt(period);
			int ycperiodDay = Integer.parseInt(ycperiod);
			user.setIsPeriodPay(isPeriodPay);
			user.setGmtPeriodPayStart(periodPayStart);
			user.setPeriodAmountMax(periodAmountMax);
			user.setPeriod(periodDay);
			user.setYcperiod(ycperiodDay);
			user.setGmtPeriodPayEnd(DateUtil.getRelativeDate(periodPayStart,
					periodDay));
		}

		// �������ڽ�����Ϣ����
		userManager.updateUserPeriod(user);
		return "���ڽ�����Ϣ���³ɹ���";
	}

	/**
	 * ��̨ע���Ա
	 *
	 * @return
	 */
	@RequestMapping(value = "/crmBackRegister")
	public String backRegister(Model model) {
		List<Region> provinceList = regionManager.getRegionByType(2);
		model.addAttribute("provinceList", provinceList);
		return "/crm/backRegister";
	}

	/**
	 * ��̨ѡ��ʡ��
	 *
	 * @return
	 */
	@RequestMapping(value = "/crmSelectcityback")
	public @ResponseBody
	List<Region> selectcityback(HttpServletRequest request) {
		String provinceName = request.getParameter("param");
		List<Region> selectcitylist = regionManager
				.getRegionChilds(provinceName);
		return selectcitylist;
	}

	/**
	 * ��̨ע���û�
	 *
	 * @return
	 */
	@RequestMapping(value = "/crmSiveBackreg")
	public String siveBackreg(HttpServletRequest request, Model model,
			AdminAgent adminAgent) throws Exception {
		// ��֤�û��������
		String email = request.getParameter("parMap.email");
		Boolean isExist = this.userManager.checkEmail(email);
		if (isExist) {
			model.addAttribute("message", "����ʼ���ַ�ѱ�ע��");
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			return "/crm/backRegister";
		}
		String account = request.getParameter("parMap.account");
		if (this.userManager.checkAccount(account)) {
			model.addAttribute("message", "���û����ѱ�ע��");
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			return "/crm/backRegister";
		}
		String password = "123456";
		String payPassword = "123456";
		String sex = request.getParameter("parMap.sex");
		String phone = request.getParameter("parMap.mobilePhone");
		String province = request.getParameter("parMap.province");
		String city = request.getParameter("parMap.city");
		String zipcode = request.getParameter("parMap.zipcode");
		String address = request.getParameter("parMap.address");

		// ������֤
		String tel_reg = "^[\\d-]{7,13}$";
		String zip = "^[0-9]\\d{5}(?!\\d)$";
		if (StringUtil.isBlank(account) || (account.getBytes().length > 20)
				|| (account.getBytes().length < 4)) {
			model.addAttribute("message", "�û�����Ϊ�գ�����Ϊ4��20���ַ�");
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			return "/crm/backRegister";
		}
		if (StringUtil.isBlank(phone) || !phone.matches(tel_reg)
				|| phone.length() > 20) {
			model.addAttribute("message", "�绰����Ϊ�ջ��߸�ʽ����ȷ,���Ȳ��ܳ���20������");
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			return "/crm/backRegister";
		}
		if (StringUtil.isBlank(address) || StringUtil.isBlank(province)
				|| StringUtil.isBlank(city) || address.length() > 480) {
			model.addAttribute("message", "ʡ�ݡ����С����ص�ַ����Ϊ��,���ܳ���240����");
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			return "/crm/backRegister";
		}
		if (StringUtil.isBlank(zipcode) || !zipcode.matches(zip)
				|| zipcode.length() > 6) {
			model.addAttribute("message", "�ʱ಻��Ϊ�ջ��߸�ʽ����ȷ");
			List<Region> provinceList = regionManager.getRegionByType(2);
			model.addAttribute("provinceList", provinceList);
			return "/crm/backRegister";
		}

		// ��ʼ�����ű�
		User user = new User();
		UserInfo userInfo = new UserInfo();
		UserAddress userAddress = new UserAddress();
		AgentInfo agentInfo = new AgentInfo();

		// ҳ�����Դ������ű�
		// user��
		user.setAccount(account);
		user.setPassword(password);
		user.setPayPassword(payPassword);
		user.setEmail(email);
		user.setSex(Integer.parseInt(sex));
		user.setIpLast(this.getRemortIP(request));
		user.setStauts(1);
		user.setReferenceId(adminAgent.getId());
		user.setType("d");
		// userInfo��
		userInfo.setMobilePhone(phone);
		userInfo.setUserName(account);
		// userAddress��
		userAddress.setProvince(province);
		userAddress.setCity(city);
		userAddress.setAddress(address);
		userAddress.setZipcode(zipcode);
		// agentInfo��
		agentInfo.setGmtApproved(new Date());
		agentInfo.setStatus("success");
		agentInfo.setRealName(account);
		agentInfo.setSex(Integer.parseInt(sex));
		agentInfo.setTelNumber(phone);
		agentInfo.setProvince(province);
		agentInfo.setCity(city);
		agentInfo.setAddress(address);
		agentInfo.setZip(zipcode);
		agentInfo.setAgent_manager_id(adminAgent.getId());

		// ע��
		userManager.registerUser(user, userInfo, userAddress, agentInfo);
		return "redirect:/crm/crmRegusers.html?message=success&actionType=track";
	}

	// // ��̨�¶���
	// public String toBackOrderPage() {
	// provinceList = regionManager.getRegionByType(2);
	// depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
	// String userId = getRequest().getParameter("userId");
	// user = userManager.getUserById(Long.parseLong(userId));
	// loginUserInfo = userInfoManager.getUserInfoByUserId(user.getId());
	// userAddressList = userAddressManager.getTradeAddressByUserId(Long
	// .parseLong(userId));
	// // ȡ���ջ�����Ϣ�еĵ�ַ����
	// for (UserAddress userAddress : userAddressList) {
	// String address = this.regionManager.getAddressInfo(
	// userAddress.getProvince(), userAddress.getCity(),
	// userAddress.getDistrict());
	// address += userAddress.getAddress();
	// userAddressMap.put(userAddress, address);
	// }
	// return SUCCESS;
	// }

	// /**
	// * �û�����Ĭ���ջ���ַ
	// */
	// public String tobeDefaultbackAddress() {
	// String param1 = getRequest().getParameter("param1");
	// String param2 = getRequest().getParameter("param2");
	// Long addressId = Long.parseLong(param1);
	// loginUserInfo =
	// userInfoManager.getUserInfoByUserId(Long.parseLong(param2));
	// if (null != loginUserInfo) {
	// loginUserInfo.setTradeAddressId(addressId);
	// userInfoManager.editUserInfo(loginUserInfo);
	// return SUCCESS;
	// } else {
	// return INPUT;
	// }
	//
	// }
	//
	// public String getWeightAndMoney() {
	// double AllWeight = 0; //������
	// double AllPrice = 0;//�ܼ۸�
	// String[] goodIds = getRequest().getParameterValues("param1");
	// String[] nums = getRequest().getParameterValues("param2");
	// String[] goodsPrices = getRequest().getParameterValues("param3");
	// String[] zpgoodIds = getRequest().getParameterValues("param4");
	// String[] zpnums = getRequest().getParameterValues("param5");
	// String depfirstId = getRequest().getParameter("param6");
	// DepositoryFirst depfirst = depositoryFirstManager.getDepositoryById(Long
	// .parseLong(depfirstId));
	// if (goodIds != null) {
	// for (int i = 0; i < goodIds.length; i++) {
	// Long num = Long.parseLong(nums[i]);
	// Double goodsPrice = Double.parseDouble(goodsPrices[i]);
	// Goods goods = goodsManager.getGoods(Long.parseLong(goodIds[i]));
	// AllWeight += goods.getGoodsWeight() * num;
	// AllPrice += goodsPrice * num;
	// }
	// }
	// if (zpgoodIds != null) {
	// for (int j = 0; j < zpgoodIds.length; j++) {
	// Long zpnum = Long.parseLong(zpnums[j]);
	// Goods goodszp = goodsManager.getGoods(Long.parseLong(zpgoodIds[j]));
	// AllWeight += goodszp.getGoodsWeight() * zpnum;
	// }
	// }
	// search = new GoodsInstance();
	// search.setTotalPrice(DoubleUtil.round(AllPrice, 2));
	// search.setTotalWeight(DoubleUtil.round(AllWeight, 2));
	// search.setDepfirstName(depfirst.getDepFirstName());
	// return SUCCESS;
	// }
	//
	// public String validatenum() {
	// String num = getRequest().getParameter("param1");
	// String goodsInstanceId = getRequest().getParameter("param2");
	// String depFirstId = getRequest().getParameter("param3");
	// AvailableStock availableStock =
	// goodsInstanceManager.getAvailableStock(Long
	// .parseLong(goodsInstanceId), Long.parseLong(depFirstId));
	// if (null == availableStock || availableStock.getGoodsNumber() == null) {
	// message = "nonum";
	// } else if (availableStock.getGoodsNumber() < Long.parseLong(num)) {
	// message = "overnum";
	// } else {
	// message = "success";
	// }
	// return SUCCESS;
	// }
	//
	// public String verifyallnum() {
	// String[] goodInstanceIds = getRequest().getParameterValues("param1");
	// String[] nums = getRequest().getParameterValues("param2");
	// String[] zpgoodInstanceIds = getRequest().getParameterValues("param3");
	// String[] zpnums = getRequest().getParameterValues("param4");
	// String depFirstId = getRequest().getParameter("param5");
	//
	// if (goodInstanceIds != null) {
	// for (int i = 0; i < goodInstanceIds.length; i++) {
	// AvailableStock availableStock =
	// goodsInstanceManager.getAvailableStock(Long
	// .parseLong(goodInstanceIds[i]), Long.parseLong(depFirstId));
	// if (null == availableStock || availableStock.getGoodsNumber() == null) {
	// message = "nonum";
	// return SUCCESS;
	// } else if (availableStock.getGoodsNumber() < Long.parseLong(nums[i])) {
	// message = "overnum";
	// return SUCCESS;
	// }
	// }
	// }
	// if (zpgoodInstanceIds != null) {
	// for (int i = 0; i < zpgoodInstanceIds.length; i++) {
	// AvailableStock availableStock =
	// goodsInstanceManager.getAvailableStock(Long
	// .parseLong(zpgoodInstanceIds[i]), Long.parseLong(depFirstId));
	// if (null == availableStock || availableStock.getGoodsNumber() == null) {
	// message = "zpnonum";
	// return SUCCESS;
	// } else if (availableStock.getGoodsNumber() < Long.parseLong(zpnums[i])) {
	// message = "zpovernum";
	// return SUCCESS;
	// }
	// }
	// }
	// if (goodInstanceIds != null && zpgoodInstanceIds != null) {
	// if (goodInstanceIds.length > 0 && zpgoodInstanceIds.length > 0) {
	// Map<String, String> temp1 = new HashMap<String, String>();
	// Map<String, String> temp2 = new HashMap<String, String>();
	// Map<String, Long> alltemp = new HashMap<String, Long>();//������ظ��Ľ��е���value
	// for (int i = 0; i < goodInstanceIds.length; i++) {
	// temp1.put(goodInstanceIds[i], nums[i]);
	// }
	// for (int j = 0; j < zpgoodInstanceIds.length; j++) {
	// temp2.put(zpgoodInstanceIds[j], zpnums[j]);
	// }
	// for (Map.Entry<String, String> tem1 : temp1.entrySet()) {
	// for (Map.Entry<String, String> tem2 : temp2.entrySet()) {
	// if (tem1.getKey().equals(tem2.getKey())) {
	// alltemp.put(tem1.getKey(), Long.valueOf(tem1.getValue())
	// + Long.valueOf(tem2.getValue()));
	// }
	// }
	// }
	// if (alltemp != null) {
	// for (Map.Entry<String, Long> tem3 : alltemp.entrySet()) {
	// AvailableStock availableStock =
	// goodsInstanceManager.getAvailableStock(Long
	// .parseLong(tem3.getKey()), Long.parseLong(depFirstId));
	// if (availableStock.getGoodsNumber() < tem3.getValue()) {
	// message = "addovernum";
	// return SUCCESS;
	// }
	// }
	// }
	// }
	// }
	// return SUCCESS;
	// }

	/**
	 * Json��ʽ���VIP��Ա
	 *
	 * @param vipUserId
	 *            �û�ID
	 * @param vipMemo
	 *            VIP���˵��
	 * @param vipUserType
	 *            �û�����(Ҫ�޸ĳɵ��û�����)
	 * @return String
	 * @author lilei 2010-06-02
	 */
	@RequestMapping("crmModifyVIPPermission")
	@ResponseBody
	public Object crmModifyVIPPermission(
			@RequestParam("vipuserId") Long vipUserId,
			@RequestParam("vipmemo") String vipMemo,
			@RequestParam("vipusertype") String vipUserType, AdminAgent agent) {
		if (agent == null) {
			return "û�е�¼������ʧ�ܣ�";
		}

		String[] userTypeArray = { "d", "w" };
		List<String> userTypeList = Arrays.asList(userTypeArray);
		// �û�ID
		if (!isHasAuthority(vipUserId.toString(), agent)) {
			return "û��Ȩ�ޣ�����ʧ�ܣ�";
		}

		if (StringUtil.isBlank(vipMemo)) {
			return "����дVIP���˵��������ʧ�ܣ�";
		} else if (vipMemo.length() > VIP_MEMO_MAX_LENGTH) {
			return "VIP���˵�����ܴ���200�ֽڣ�����ʧ�ܣ�";
		}

		if (StringUtil.isBlank(vipUserType)) {
			return "�û�����Ϊ�գ�����ʧ�ܣ�";
		} else if (!userTypeList.contains(StringUtil.trim(vipUserType))) {
			return "��ȷ���û������Ƿ��Ǵ����Ա��VIP��Ա������ʧ�ܣ�";
		}

		String modifyType = "";
		if (USER_TYPE_NOT_VIP.equalsIgnoreCase(vipUserType)) {
			modifyType = USER_TYPE_VIP;
		} else if (USER_TYPE_VIP.equalsIgnoreCase(vipUserType)) {
			modifyType = USER_TYPE_NOT_VIP;
		} else {
			return "��ȷ���û������Ƿ��Ǵ����Ա��VIP��Ա������ʧ�ܣ�";
		}

		vipMemo = StringUtil.trim(vipMemo);
		User oldUser = userManager.getUserById(vipUserId);
		if (StringUtil.isNotBlank(modifyType)
				&& !modifyType.equalsIgnoreCase(oldUser.getType())) {
			// ���»�Ա������
			userManager.updateUserTypeAndRmark(vipUserId.toString(),
					modifyType, vipMemo);
		}

		return "VIP��ԱȨ�޸��³ɹ���";
	}

	/**
	 * ��̨��ʼ����¼����
	 *
	 * @param userId
	 *            �û�ID
	 * @return
	 * @author lilei 2010/06/18
	 */
	@RequestMapping("crmInitUserPassword")
	@ResponseBody
	public Object crmInitUserPassword(@RequestParam("userId") Long userId) {
		try {
			User user = new User();
			user.setId(userId);
			user.setPassword(USER_INIT_PASSWORD);
			if (userManager.editPassword(user)) {
				return "��ʼ����¼����ɹ���";
			} else {
				return "��ʼ����¼����ʧ�ܣ�";
			}
		} catch (Exception e) {
			// �޸��������
			log.error("errors.edit.password", e);
			return "��ʼ����¼����ʧ�ܣ�";
		}
	}

	/**
	 * ��̨��ʼ��֧������
	 *
	 * @param userId
	 *            �û�ID
	 * @return
	 */
	@RequestMapping("crmInitUserPayPassword")
	@ResponseBody
	public Object crmInitUserPayPassword(@RequestParam("userId") Long userId) {
		try {
			User user = new User();
			user.setId(userId);
			user.setPayPassword(USER_INIT_PASSWORD);

			if (userManager.editPayPassword(user)) {
				return "��ʼ��֧������ɹ���";
			} else {
				return "��ʼ��֧������ʧ�ܣ�";
			}
		} catch (Exception e) {
			// �޸��������
			log.error("errors.edit.password", e);
			return "��ʼ��֧������ʧ�ܣ�";
		}
	}

	@RequestMapping("updateAgentDistributAddr")
	@ResponseBody
	public Object updateAgentDistributAddr(
			@RequestParam("userId") String userId,
			@RequestParam("id") Long agentId, AdminAgent agent,
			ServletRequest req) {
		try {
			if (agent == null) {
				return "û�е�¼������ʧ�ܣ�";
			}

			if (!isHasAuthority(userId, agent)) {
				return "û��Ȩ�ޣ�����ʧ�ܣ�";
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();

			String taobaoUser = req.getParameter("taobaoUser");
			String taobaoShop = req.getParameter("taobaoShop");
			String taobaoMainBusiness = req.getParameter("taobaoMainBusiness");
			String taobaoPrestige = req.getParameter("taobaoPrestige");
			if (StringUtil.isNotBlank(taobaoUser)
					&& StringUtil.isNotBlank(taobaoShop)
					&& StringUtil.isNotBlank(taobaoMainBusiness)
					&& StringUtil.isNotBlank(taobaoPrestige)) {
				paramMap.put("taobaoUser", taobaoUser);
				paramMap.put("taobaoShop", taobaoShop);
				paramMap.put("taobaoMainBusiness", taobaoMainBusiness);
				paramMap.put("taobaoPrestige", taobaoPrestige);
			}

			String paipaiUser = req.getParameter("paipaiUser");
			String paipaiShop = req.getParameter("paipaiShop");
			String paipaiMainBusiness = req.getParameter("paipaiMainBusiness");
			String paipaiPrestige = req.getParameter("paipaiPrestige");
			if (StringUtil.isNotBlank(paipaiUser)
					&& StringUtil.isNotBlank(paipaiShop)
					&& StringUtil.isNotBlank(paipaiMainBusiness)
					&& StringUtil.isNotBlank(paipaiPrestige)) {
				paramMap.put("paipaiUser", paipaiUser);
				paramMap.put("paipaiShop", paipaiShop);
				paramMap.put("paipaiMainBusiness", paipaiMainBusiness);
				paramMap.put("paipaiPrestige", paipaiPrestige);
			}

			String ebayUser = req.getParameter("ebayUser");
			String ebayShop = req.getParameter("ebayShop");
			String ebayMainBusiness = req.getParameter("ebayMainBusiness");
			String ebayPrestige = req.getParameter("ebayPrestige");
			if (StringUtil.isNotBlank(ebayUser)
					&& StringUtil.isNotBlank(ebayShop)
					&& StringUtil.isNotBlank(ebayMainBusiness)
					&& StringUtil.isNotBlank(ebayPrestige)) {
				paramMap.put("ebayUser", ebayUser);
				paramMap.put("ebayShop", ebayShop);
				paramMap.put("ebayMainBusiness", ebayMainBusiness);
				paramMap.put("ebayPrestige", ebayPrestige);
			}

			String youaUser = req.getParameter("youaUser");
			String youaShop = req.getParameter("youaShop");
			String youaMainBusiness = req.getParameter("youaMainBusiness");
			String youaPrestige = req.getParameter("youaPrestige");
			if (StringUtil.isNotBlank(youaUser)
					&& StringUtil.isNotBlank(youaShop)
					&& StringUtil.isNotBlank(youaMainBusiness)
					&& StringUtil.isNotBlank(youaPrestige)) {
				paramMap.put("youaUser", youaUser);
				paramMap.put("youaShop", youaShop);
				paramMap.put("youaMainBusiness", youaMainBusiness);
				paramMap.put("youaPrestige", youaPrestige);
			}

			String selfshopName = req.getParameter("selfshopName");
			String selfshopAddress = req.getParameter("selfshopAddress");
			String selfshopMainBusiness = req
					.getParameter("selfshopMainBusiness");
			if (StringUtil.isNotBlank(selfshopName)
					&& StringUtil.isNotBlank(selfshopAddress)
					&& StringUtil.isNotBlank(selfshopMainBusiness)) {
				paramMap.put("selfshopName", selfshopName);
				paramMap.put("selfshopAddress", selfshopAddress);
				paramMap.put("selfshopMainBusiness", selfshopMainBusiness);

			}

			String otherUser = req.getParameter("otherUser");
			String otherShop = req.getParameter("otherShop");
			String otherMainBusiness = req.getParameter("otherMainBusiness");
			if (StringUtil.isNotBlank(otherUser)
					&& StringUtil.isNotBlank(otherShop)
					&& StringUtil.isNotBlank(otherMainBusiness)) {
				paramMap.put("otherUser", otherUser);
				paramMap.put("otherShop", otherShop);
				paramMap.put("otherMainBusiness", otherMainBusiness);
			}

			String entityName = req.getParameter("entityName");
			String entityAddress = req.getParameter("entityAddress");
			String entityMainBusiness = req.getParameter("entityMainBusiness");
			if (StringUtil.isNotBlank(entityName)
					&& StringUtil.isNotBlank(entityAddress)
					&& StringUtil.isNotBlank(entityMainBusiness)) {
				paramMap.put("entityName", entityName);
				paramMap.put("entityAddress", entityAddress);
				paramMap.put("entityMainBusiness", entityMainBusiness);
			}

			paramMap.put("id", agentId);
			int i = userAgentManager.updateAgentDistributAddr(paramMap);
			if (i == 1) {
				return "���³ɹ�!";
			} else {
				return "����ʧ��!";
			}
		} catch (Exception e) {
			// �޸ĳ���
			log.error("errors.edit.distributaddr", e);
			return "����ʧ��!";
		}
	}

	// /**
	// * ��ʾCRM����ҳ
	// * @return
	// */
	// public String crmIndexAdmin() {
	// return SUCCESS;
	// }
	//
	// /**
	// * ��ʾCRM����ҳ
	// * @return
	// */
	// public String crmIndexMenu() {
	// return SUCCESS;
	// }
	/**
	 * ��ֹû�в鿴ȫ���û�Ȩ�޵�ϵͳ����Աͨ��url�޸� ����ά���ͻ�����Ϣ��
	 *
	 * @param userId
	 * @author wangdong
	 * @return
	 */
	private boolean isHasAuthority(String userId, AdminAgent adminAgent) {
		Long id = null;
		if (StringUtil.isNotBlank(userId)) {
			id = Long.parseLong(userId);
		}
		AgentInfo info = userAgentManager.getUserAgentById(id);
		if (info.getAgent_manager_id() == null) {
			return true;
		}
		if (adminAgent.havePermission(EnumAdminPermission.A_CRM_SEE_ALL_USER)) {
			return true;
		} else {
			if (info == null) {
				return false;
			}
			Long agentId = info.getAgent_manager_id();
			Long adminId = adminAgent.getId();
			if (agentId.equals(adminId)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
