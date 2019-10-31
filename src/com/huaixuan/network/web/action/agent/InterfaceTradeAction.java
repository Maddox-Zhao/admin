/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-7
 */
package com.huaixuan.network.web.action.agent;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.SearchInterfaceTradeQuery;
import com.huaixuan.network.biz.service.agent.InterfaceUserTradeManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: InterfaceTradeAction.java,v 0.1 2011-3-7 ÏÂÎç04:51:48 shengyong Exp $
 */
@Controller
public class InterfaceTradeAction extends BaseAction {

    @Autowired
    InterfaceUserTradeManager interfaceUserTradeManager;

    @AdminAccess({EnumAdminPermission.A_INTERFACE_DATA_USER})
    @RequestMapping(value = "/agent/searchInterfaceTrade")
    public String searchInterfaceTrade(@ModelAttribute("searchInterfaceTradeQuery") SearchInterfaceTradeQuery searchInterfaceTradeQuery,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                       Model model, HttpServletRequest request) {

    	String initFlag = request.getParameter("init");        
        if(StringUtil.isNotBlank(initFlag) && initFlag.equals("true")){
        	searchInterfaceTradeQuery.setGmtCreateStart(DateUtil.getDiffMon(new Date(), -1));
        	searchInterfaceTradeQuery.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
        }
        
        QueryPage page = interfaceUserTradeManager.searchInterfaceByParameterMap(
            searchInterfaceTradeQuery, currPage, this.pageSize);
        model.addAttribute("query", page);
        return "/agent/searchInterfaceTrade";
    }

    @RequestMapping(value = "/agent/deleteInterfaceTrade")
    public String searchInterfaceTrade(@RequestParam(value = "htradeId", required = false) String htradeId,
                                       Model model,@ModelAttribute("searchInterfaceTradeQuery") SearchInterfaceTradeQuery searchInterfaceTradeQuery,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                       HttpServletRequest request) {
        if (StringUtil.isNotBlank(htradeId)) {
            interfaceUserTradeManager.removeInterfaceUserTrade(Long.valueOf(htradeId));
        }
        model.addAttribute("message", "É¾³ý³É¹¦£¡");
        return searchInterfaceTrade(searchInterfaceTradeQuery,currPage,model,request);

    }

}
