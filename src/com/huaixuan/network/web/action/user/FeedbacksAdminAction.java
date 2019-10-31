package com.huaixuan.network.web.action.user;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Insertcontent;
import com.huaixuan.network.biz.domain.hx.Leaveword;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.shop.FeedbackService;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbacksAdminAction extends BaseAction {

	// private static final long serialVersionUID = -8386798766351295345L;

	@Autowired
	FeedbackService feedbackService;

	@Autowired
	private UserManager userManager;

	@Autowired
	private AdminService adminService;

	// private List<Feedback> allFeedbacks;
	// private Feedback theFeedback;
	// private Feedback feedbackParameter;
	//
	// private String theReply;
	// private String toUserType;
	// private Page page;
	// private AdminManager adminManager;
	// private String message;
	//
	//
	@RequestMapping(value = "/showfb")
	public String searchAllFeedbacks(@ModelAttribute("feedback") Feedback newFeedback,
			@ModelAttribute("leaveword") Leaveword leaveword,
			@RequestParam(value = "toUserType", required = false, defaultValue = "") String toUserType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {

//		if (StringUtil.isEmpty(toUserType)) {
//			toUserType = "shop";
//		}
//		newFeedback.setToUserType(toUserType);
		QueryPage queryPage = feedbackService.getFeedbacksByCondition(leaveword, currPage, pageSize);
		
		model.addAttribute("query", queryPage);
		model.addAttribute("toUserType", toUserType);
		model.addAttribute("queryObject", leaveword);

		return "/user/feedbacksSearch";
	}

	//
	// public String searchFeedbacksByCondition() {
	//
	// toUserType = feedbackParameter.getToUserType();
	// if (feedbackParameter.getUserNickname() != null) {
	// feedbackParameter.setUserNickname(feedbackParameter.getUserNickname().trim());
	// }
	// int count = feedbackManager.getFeedbackCountByCondition(feedbackParameter);
	// pageInit(count);
	// allFeedbacks = feedbackManager.getFeedbacksByCondition(feedbackParameter, page);
	// // ..
	// for (Feedback feedback : allFeedbacks) {
	// feedbackManager.changeTheTypeNames(feedback);
	// }
	// return SUCCESS;
	// }
	//
	// private void pageInit(int counts) {
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(counts);
	// page.setCurrentPage(currentPage);
	// }
	//
	@RequestMapping(value = "/fbdetail")
	public String detailTheFeedback(
			@RequestParam("feedbackId") String fbId,
			@RequestParam(value = "message", required = false, defaultValue = "") String message,
			Model model) {

		Long feedbackId = Long.valueOf(fbId);
		Leaveword leaveword = feedbackService.getFeedback(feedbackId);
//		feedbackService.changeTheTypeNames(theFeedback);

		model.addAttribute("leaveword", leaveword);
		model.addAttribute("message", message);
		return "/user/feedbacksDetail";
	}

	@AdminAccess(EnumAdminPermission.A_FB_REVERT_TO_USER)
	@RequestMapping(value = "/replyfb")
	public String replyTheFeedback(
			@ModelAttribute("leaveword") Leaveword leaveword,
			@RequestParam("theReply") String theReply,
			Model model
			)throws Exception {
//		Long feedbackId = Long.valueOf(fbId);
		String message = "";
//		Leaveword leaveword = feedbackService.getFeedback(feedbackId);
		if(theReply != null && theReply.length() > 0)
		{
			theReply = theReply.trim();
		}
		if (StringUtil.isNotBlank(theReply) && theReply.length() > 1000) {
			message = "回复超长，不能保存！";
			model.addAttribute("message", message);
			return "redirect:/feedback/fbdetail.html?feedbackId=" + leaveword.getId();
		}
		
		Insertcontent insertcontent = new Insertcontent();
    	insertcontent.setContent(theReply);
    	insertcontent.setLeavewordId(leaveword.getId());
    	insertcontent.setIdCustomer(leaveword.getIdCustomer());
    	
    	feedbackService.addInsertcontent(insertcontent);
		
		return "redirect:/feedback/fbdetail.html?feedbackId=" + leaveword.getId();
	}

	private boolean clearTheReply(String reply) {
		if (StringUtil.isEmpty(reply)) {
			return true;
		}
		return false;
	}

	@RequestMapping("fdedit")
	public String editFeedback(@RequestParam("toUserId") Long toUserId, Model model) {
		Feedback feedback;
		if (toUserId != null) {
			feedback = new Feedback();
			feedback.setToUserId(toUserId);

		} else {
			feedback = (Feedback) model.asMap().get("feedback");
		}

		String toUserNick = userManager.getUser(feedback.getToUserId()).getAccount();
		feedback.setToUserNick(toUserNick);

		model.addAttribute("feedback", feedback);

		return "/user/editFeedback";
	}

	@RequestMapping("sendfb")
	public String addFeedback(@ModelAttribute("feedback") Feedback feedback, Model model, AdminAgent agent) {
		feedback.setUserId(agent.getId());
		feedback.setUserNickname(agent.getUsername());
		feedback.setMsgType("leaveWord");
		feedback.setToUserType("user");

		Admin admin = adminService.getAdminById(agent.getId());
		feedback.setUserEmail(admin.getEmail());

		feedbackService.addFeedback(feedback);

		return "/user/feedbackSendS";
	}

}
