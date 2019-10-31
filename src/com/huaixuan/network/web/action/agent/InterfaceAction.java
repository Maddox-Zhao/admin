/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-9
 */
package com.huaixuan.network.web.action.agent;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumInterfaceApplyStatus;
import com.huaixuan.network.biz.enums.EnumInterfaceShopType;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumTaobaoExpress;
import com.huaixuan.network.biz.query.InterfaceApplyQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.remote.Billing_Deal_DetailsUtil;
import com.huaixuan.network.common.util.remote.Contants;
import com.huaixuan.network.common.util.remote.TaobaoUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: InterfaceAction.java,v 0.1 2011-3-9 上午11:32:10 shengyong Exp $
 */
@Controller
public class InterfaceAction extends BaseAction {

    @Autowired
    InterfaceApplyManager       interfaceApplyManager;

    @Autowired
    TaobaoInterfaceApplyManager taobaoInterfaceApplyManager;

    @Autowired
    ExpressManager              expressManager;

    /**
     * 查询拍拍接入申请
     * @return
     */
    @AdminAccess({ EnumAdminPermission.A_INTERFACE_APPLY_USER })
    @RequestMapping(value = "/agent/searchInterfaceApplyList")
    public String searchPaipaiInterfaceApplyList(@ModelAttribute("interfaceApplyQuery") InterfaceApplyQuery interfaceApplyQuery,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                                 Model model) {
        if (StringUtil.isBlank(interfaceApplyQuery.getGmtCreateStart())) {
            interfaceApplyQuery.setGmtCreateStart(DateUtil.getDiffMon(new Date(), -30));
        }
        if (StringUtil.isBlank(interfaceApplyQuery.getGmtCreateEnd())) {
            interfaceApplyQuery.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
        }
        interfaceApplyQuery.setType("paipai");

        QueryPage query = interfaceApplyManager.getListByMap(interfaceApplyQuery, currPage,
            this.pageSize);

        model.addAttribute("enumInterfaceTypeMap", EnumInterfaceType.toMap());
        model.addAttribute("enumInterfaceShopType", EnumInterfaceShopType.toMap());
        model.addAttribute("query", query);
        return "/agent/searchInterfaceApplyList";
    }

    @RequestMapping(value = "/agent/editInterfaceApplyByJQuery")
    public @ResponseBody
    String editInterfaceApplyByJQuery(@RequestParam("param1") String idStr,
                                      @RequestParam("param2") String op,
                                      HttpServletResponse response) {

        String message = "";
        if (StringUtil.isNotBlank(idStr) && StringUtil.isNumeric(idStr)) {
            Long id = Long.parseLong(idStr);
            interfaceApplyManager.editInterfaceApplyStatus(id, op);
            InterfaceApply interfaceApply = interfaceApplyManager.getInterfaceApply(id);
            if (EnumInterfaceApplyStatus.FAIL.getKey().equals(interfaceApply.getStatus())) {
                message = "disconnect success!";
            } else {
                message = "disconnect fail!";
            }
        } else {
            message = "fail!";
        }
        return message;
    }

    @RequestMapping(value = "/agent/tryInterfaceByJson")
    public @ResponseBody
    String tryInterfaceByJson(@RequestParam("param") String idStr, HttpServletResponse response) {
        String message = "";
        if (StringUtil.isNotBlank(idStr) && StringUtil.isNumeric(idStr)) {
            Long id = Long.parseLong(idStr);
            InterfaceApply interfaceApply = interfaceApplyManager.getInterfaceApply(id);
            if (interfaceApply != null) {
                String nulMessage = "";
                if (StringUtil.isBlank(interfaceApply.getParamOne())) {
                    nulMessage = "出错：QQ参数为空！";
                    message = "['false','" + nulMessage + "']";
                    return message;
                } else if (StringUtil.isBlank(interfaceApply.getParamTwo())) {
                    nulMessage = "出错：TOKEN参数为空！";
                    message = "['false','" + nulMessage + "']";
                    return message;
                } else if (StringUtil.isBlank(interfaceApply.getParamThree())) {
                    nulMessage = "出错：SPID参数为空！";
                    message = "['false','" + nulMessage + "']";
                    return message;
                } else if (StringUtil.isBlank(interfaceApply.getParamFour())) {
                    nulMessage = "出错：seckey参数为空！";
                    message = "['false','" + nulMessage + "']";
                    return message;
                }

                TreeMap<String, String> signParams = new TreeMap<String, String>();
                signParams.put("uin", interfaceApply.getParamOne());
                signParams.put("token", interfaceApply.getParamTwo());
                signParams.put("spid", interfaceApply.getParamThree());
                signParams.put("seckey", interfaceApply.getParamFour());
                signParams.put("sellerUin", interfaceApply.getParamOne());
                Contants.setPublicParams(signParams);// 共用属性
                signParams.put("pageSize", "1");

                String ItemUrl = Contants.createNewUrl(Billing_Deal_DetailsUtil.DEAL_LIST_URL,
                    signParams);
                Map resultMap = Billing_Deal_DetailsUtil.parseInterfaceTradeListXml(ItemUrl);//结果集合
                String errorCode = (String) resultMap.get("errorCode");
                String errorMessage = (String) resultMap.get("errorMessage");
                if ("0".equals(errorCode)) {
                    interfaceApplyManager.editInterfaceApplyStatus(id,
                        EnumInterfaceApplyStatus.PASS.getKey());
                    message = "['true','try success!']";
                } else {
                    message = "['false','" + errorMessage + "']";
                }
            } else {
                message = "fail";
            }
        } else {
            message = "fail";
        }
        return message;
    }

    /**
     * 查询淘宝接入申请
     * @return
     */
    @AdminAccess({ EnumAdminPermission.A_TAOBAO_INTERFACE_APPLY_USER })
    @RequestMapping(value = "/agent/searchInterfaceApplyTaobaoList")
    public String searchTaobaoInterfaceApplyList(@ModelAttribute("interfaceApplyQuery") InterfaceApplyQuery interfaceApplyQuery,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                                 Model model) {
        //取得所有的1858物流公司
        List<Express> expressList = expressManager.getExpresss();
        model.addAttribute("expressList", expressList);

        if (StringUtil.isBlank(interfaceApplyQuery.getGmtCreateStart())) {
            interfaceApplyQuery.setGmtCreateStart(DateUtil.getDiffMon(new Date(), -30));
        }
        if (StringUtil.isBlank(interfaceApplyQuery.getGmtCreateEnd())) {
            interfaceApplyQuery.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
        }
        interfaceApplyQuery.setType("taobao");

        QueryPage query = interfaceApplyManager.getListByMap(interfaceApplyQuery, currPage,
            this.pageSize);
        model.addAttribute("enumInterfaceApplyStatusMap", EnumInterfaceApplyStatus.toMap());
        model.addAttribute("enumInterfaceTypeMap", EnumInterfaceType.toMap());
        model.addAttribute("enumTaobaoExpressMap", EnumTaobaoExpress.toMap());
        model.addAttribute("query", query);
        return "/agent/searchInterfaceApplyTaobaoList";
    }

    @RequestMapping(value = "/agent/editInterfaceApplyTaobaoByJQuery")
    public @ResponseBody
    String editInterfaceApplyTaobaoByJQuery(@RequestParam("param1") String idStr,
                                            @RequestParam("param2") String op,
                                            @RequestParam("param3") String memo,
                                            HttpServletResponse response) {

        String message = "";

        if (StringUtil.isNotBlank(idStr) && StringUtil.isNumeric(idStr)) {
            Long id = Long.parseLong(idStr);
            if ("pass".equals(op)) {//当为审核通过时，暂时将备注信息设为空
                memo = "";
            }
            this.taobaoInterfaceApplyManager.editInterfaceApplyStatus(id, op, memo);
            if ("pass".equals(op)) {
                message = "pass success!";
            } else {
                message = "fail success!";
            }
        } else {
            message = "failed!";
        }
        return message;
    }

    @RequestMapping(value = "/agent/tryInterfaceTaobaoByJson")
    public @ResponseBody
    String tryInterfaceTaobaoByJson(@RequestParam("param1") String paramOne,
                                    @RequestParam("param2") String paramTwo,
                                    @RequestParam("param3") String paramThree,
                                    HttpServletResponse response) {

        String message = "";

        if (StringUtil.isBlank(paramOne) || StringUtil.isBlank(paramTwo)
            || StringUtil.isBlank(paramThree)) {
            message = "['false','用户提供的参数不完整!']";
            return message;
        }

        message = TaobaoUtils.testApply(paramOne, paramTwo, paramThree);

        return message;
    }

    /**
     * Json方式修改同步过来生成的订单使用的物流公司
     * param1 申请单的ID
     * param2 修改后的物流公司ID
     * @return String
     * @author chenyan 2011/03/11
     */
    @RequestMapping(value = "/agent/modifyOwnExpressIdByJson")
    public @ResponseBody
    void modifyOwnExpressIdByJson(@RequestParam("param1") String paramOne,
                                  @RequestParam("param2") String paramTwo,
                                  HttpServletResponse response) {
        taobaoInterfaceApplyManager.modifyOwnExpressIdById(Long.parseLong(paramOne),
            Long.parseLong(paramTwo));
    }

    /**
     * Json方式修改同步发货到淘宝订单的物流公司
     * param1 申请单的ID
     * param2 修改后的物流公司CODE
     * @return String
     * @author chenyan 2011/03/11
     */
    @RequestMapping(value = "/agent/modifyInterfaceExpressCodeByJson")
    public @ResponseBody
    void modifyInterfaceExpressCodeByJson(@RequestParam("param1") String paramOne,
                                          @RequestParam("param2") String paramTwo,
                                          HttpServletResponse response) {
        taobaoInterfaceApplyManager.modifyInterfaceExpressCodeById(Long.parseLong(paramOne),
            paramTwo);
    }

    /**
     * Json方式修改拍拍店铺类型
     * param1 申请单的ID
     * param2 修改后的物流公司CODE
     * @return String
     * @author shengyong 2011/05/26
     */
    @RequestMapping(value = "/agent/modifyInterfaceShopTypeByJson")
    public @ResponseBody
    void modifyInterfaceShopTypeByJson(@RequestParam("param1") String paramOne,
                                       @RequestParam("param2") String paramTwo,
                                       HttpServletResponse response) {
        InterfaceApply interfaceApply = interfaceApplyManager.getInterfaceApply(new Long(paramOne));
        interfaceApply.setShopType(paramTwo);
        interfaceApplyManager.editInterfaceApply(interfaceApply);
    }

    //    /**
    //     * 查询淘宝接入申请
    //     * @return
    //     */
    //    public String searchTaobaoInterfaceApplyList() {
    //        String gmtCreateStart = (String) parMap.get("gmtCreateStart");
    //        String gmtCreateEnd = (String) parMap.get("gmtCreateEnd");
    //        if (StringUtil.isBlank(gmtCreateStart) && StringUtil.isBlank(gmtCreateEnd)) {
    //            gmtCreateStart = DateUtil.getDiffMon(new Date(), -30);
    //            gmtCreateEnd = DateUtil.getDateToString(new Date());
    //            parMap.put("gmtCreateStart", gmtCreateStart);
    //            parMap.put("gmtCreateEnd", gmtCreateEnd);
    //        }
    //        //只查询淘宝的接入申请
    //        parMap.put("type", "taobao");
    //        //设置分页
    //        int count = taobaoInterfaceApplyManager.getCountByMap(parMap);
    //        page = new Page();
    //        page.setPageSize(pageSize);
    //        page.setTotalRowsAmount(count);
    //        page.setCurrentPage(currentPage);
    //        if (count > 0) {
    //            taobaoApplyList = this.taobaoInterfaceApplyManager.getListByMap(parMap, page);
    //        }
    //        return SUCCESS;
    //    }

}
