package com.huaixuan.network.web.action.trade;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.trade.RefundApply;
import com.huaixuan.network.biz.enums.EnumRefundApplyStatus;
import com.huaixuan.network.biz.query.RefundApplyQuery;
import com.huaixuan.network.biz.service.trade.RefundApplyManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * 
 */
@Controller
public class RefundApplyAction extends BaseAction {
	@Autowired
	private RefundApplyManager refundApplyManager;

	private File[] images;
	private String[] imagesFileName;

	@RequestMapping("/igc/refundApplySearch")
	public String refundApplySearchAdmin(@ModelAttribute("query") RefundApplyQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent) {
		if (StringUtil.isBlank(query.getDateStart()) && StringUtil.isBlank(query.getDateEnd())) {
			query.setDateStart(DateUtil.getDiffDate(new Date(), -30));
			query.setDateEnd(DateUtil.getDateToString(new Date()));
		}

		model.addAttribute("page", refundApplyManager.getRefundApplys(query, currPage, pageSize));

		model.addAttribute("enumRefundApplyStatusMap", EnumRefundApplyStatus.toMap());

		return "/trade/refundApplySearch";
	}

	@RequestMapping("/igc/refundApplyDetail")
	public String refundApplyDetailAdmin(@RequestParam("applyId") String id,
			@RequestParam(value = "isApply", required = false) String isApply,
			@RequestParam(value = "agreeTag", required = false) String agreeTag,
			@RequestParam(value = "serviceNote", required = false) String serviceNote, Model model, AdminAgent agent) {

		RefundApply refundApply = refundApplyManager.getRefundApply(new Long(id));
		// 是否进行审批操作
		if (!StringUtil.isBlank(isApply)) {
			String message;
			if (EnumRefundApplyStatus.APPLY_NEW.getKey().equals(refundApply.getStatus())) {
				if (EnumRefundApplyStatus.APPLY_PASS.getKey().equals(agreeTag)
						|| EnumRefundApplyStatus.APPLY_NOT_PASS.getKey().equals(agreeTag)) {
					refundApply.setStatus(agreeTag);
					refundApply.setServiceNote(serviceNote);
					refundApply.setCreater(agent.getUsername());
					refundApply.setCreaterId(agent.getId());
					refundApplyManager.updateRefundApply(refundApply);

					message = "申请审批成功！";
					refundApply = refundApplyManager.getRefundApply(new Long(id));
				} else {
					message = "审批状态不正确，不允许审批！";
				}
			} else {
				message = "申请已经审批，不允许重复操作！";

			}
			model.addAttribute("message", message);
		}

		model.addAttribute("refundApply", refundApply);
		model.addAttribute("enumRefundApplyStatusMap", EnumRefundApplyStatus.toMap());

		return "/trade/refundApplyDetail";
	}
}
