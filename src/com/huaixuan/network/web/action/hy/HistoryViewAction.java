package com.huaixuan.network.web.action.hy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.huaixuan.network.biz.domain.admin.Role;
import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.RoleAuthorityService;
import com.huaixuan.network.biz.service.admin.RoleService;
import com.huaixuan.network.biz.service.hy.HistoryViewService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.json.JSONArray;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
@Controller
@RequestMapping(value ="/historyView")
public class HistoryViewAction extends BaseAction {

	@Autowired
	private HistoryViewService historyViewService;


	@AdminAccess({EnumAdminPermission.A_ROLE_VIEW_USER})
    @RequestMapping("/historyViewList")
    public String historyViewList(@ModelAttribute("historyView") HistoryView historyView, Model model,
    		@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
    	
		if ("true".equals(isFirst)) {
			Date now = new Date();
			Timestamp nowTs = new Timestamp(now.getTime());
			Date before30 = DateUtil.getDate(now, -30);
			Timestamp beforeTs = new Timestamp(before30.getTime());
			if (StringUtils.isBlank(historyView
					.getDateStart())) {
				historyView.setDateStart(DateUtil
						.getTimestampToString(beforeTs));
			}
			if (StringUtils.isBlank(historyView
					.getDateEnd())) {
				historyView.setDateEnd(DateUtil
						.getTimestampToString(nowTs));
			}
		}
		
		QueryPage page = historyViewService.getHistoryViewByConditionWithPage(historyView, currPage, this.pageSize);
    	if(page != null){
    		model.addAttribute("query", page);
    	}
    	return "/hy/historyViewList";
    }
}
