package com.huaixuan.network.web.action.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gargoylesoftware.htmlunit.javascript.host.Comment;
import com.huaixuan.network.biz.domain.Comments;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CommentsService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value="/comment")
public class CommentsAdminAction extends BaseAction {

	/**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    CommentsService commentsService;
//	private GoodsManager goodsManager;
//
//	private List<Comments> allComments;
//	private Comments commentsParameter;
//	private Comments theComment;
//	private String theReply;
//	private String message;
//    private Page                     page;
//
    @RequestMapping(value="/scomments")
	public String searchAllComments(
			@ModelAttribute("comments")Comments comments,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model
		)throws Exception {

		QueryPage queryPage = commentsService.getCommentsByCondition(comments,currPage,pageSize);

		model.addAttribute("query", queryPage);
		model.addAttribute("queryObject", comments);

		return "/user/commentsSearch";
	}
//
//	private void pageInit(int counts){
//        page = new Page();
//        page.setPageSize(pageSize);
//        page.setTotalRowsAmount(counts);
//        page.setCurrentPage(currentPage);
//	}
//
//	public String searchCommentsByCondition() {
//
//	    if(commentsParameter.getUserNickname()!=null && commentsParameter.getGoodsName()!=null){
//    	    commentsParameter.setUserNickname(commentsParameter.getUserNickname().trim());
//    	    commentsParameter.setGoodsName(commentsParameter.getGoodsName().trim());
//	    }
//		int count = commentsManager.getCommentsCountByCondition(commentsParameter);
//		pageInit(count);
//
//		allComments = commentsManager.getCommentsByCondition(commentsParameter,page);
//		return SUCCESS;
//	}
//
    @RequestMapping("/detailcomm")
	public String detailTheComment(
			@RequestParam("commentId")String cId,
			@RequestParam(value = "message", required = false, defaultValue = "") String message,
			Model model
		)throws Exception{

		Long commentId = Long.valueOf(cId);
		Comments theComment = commentsService.getComments(commentId);
		commentsService.setGoodsNameCommented(theComment);
		model.addAttribute("theComment", theComment);
		model.addAttribute("message", message);
		return "/user/commentsDetail";
	}

    @AdminAccess(EnumAdminPermission.A_REVIEW_REVERT_TO_USER)
    @RequestMapping(value = "/replycomm")
	public String replyTheComment(
			@RequestParam("commentId")String cId,
			@RequestParam("theReply")String theReply,
			Model model
		)throws Exception{



		Long commentId = Long.valueOf(cId);

		Comments comments = commentsService.getComments(commentId);
		if(theReply != null && theReply.length()>0)
		{
			theReply = StringEscapeUtils.escapeHtml(theReply).trim();
		}

		comments.setReplyContent(theReply);

		if(clearTheReply(theReply)){
			comments.setReplyFlag("no");
		}else{
		    comments.setReplyFlag("yes");
		}

		commentsService.editComments(comments);
		Comments theComment=commentsService.getComments(commentId);
		commentsService.setGoodsNameCommented(theComment);

		return "redirect:/comment/detailcomm.html?commentId="+cId;
	}

    @RequestMapping(value = "/showOrHide")
	public @ResponseBody String showOrHide(
			@RequestParam("param1")String commentId_str
		)throws Exception{

    	String message = "";

	    // modified by fangqing 20101126  原先代码无效
	    if(StringUtil.isBlank(commentId_str)){
	        message = "['false','commentId is null or is not number!']";
	        return message;
	    }
	    Long commentId = Long.parseLong(commentId_str);
		Comments theComment = commentsService.getComments(commentId);

		Map<String,Object> status = new HashMap<String,Object>();

		status.put("id", commentId);
		if(theComment.getStatus().equals("0")){
			showTheComment(status);
		}else if(theComment.getStatus().equals("1")){
			hideTheComment(status);
		}
		message = "['true','success!']";
		return message;
	}

	private boolean clearTheReply(String reply){
		if(StringUtil.isEmpty(reply)){
			return true;
		}
		return false;
	}

	private void showTheComment(Map<String,Object> status){
		status.put("status", "1");
		commentsService.changeStatus(status);
	}

	private void hideTheComment(Map<String,Object> status){
		status.put("status", "0");
		commentsService.changeStatus(status);
	}

}
