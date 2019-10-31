package com.huaixuan.network.web.action.crm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumInPrestige;
import com.huaixuan.network.biz.enums.EnumInTbYouaPrestige;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSalesManager;
import com.huaixuan.network.biz.service.user.UserSalesManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
public class CrmRankAction extends BaseAction {

    @Autowired
    private UserSalesManager          userSalesManager;
    @Autowired
    private GoodsInstanceSalesManager goodsInstanceSalesManager;

    /**
     * 客户销售排行页面
     * @return String
     * @author chenyan 2011/03/09
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/crm/crmCustomerRank")
    public String searchCustomerRankInit(
                                         @ModelAttribute("crmCustomerRankQuery") CrmRankQuery crmCustomerRankQuery,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                         Model model) {
        if (StringUtil.isBlank(crmCustomerRankQuery.getGmtOptTimeStart())) {
            crmCustomerRankQuery.setGmtOptTimeStart(DateUtil.getDiffMon(new Date(), -1));
        }
        if (StringUtil.isBlank(crmCustomerRankQuery.getGmtOptTimeEnd())) {
            crmCustomerRankQuery.setGmtOptTimeEnd(DateUtil.getDiffMon(new Date(), 0));
        }
        QueryPage page = userSalesManager.getUserSalesListsByParameterMap(crmCustomerRankQuery,
            currPage, pageSize);
        model.addAttribute("query", page);
        model.addAttribute("presgtige", EnumInPrestige.toMap());
        model.addAttribute("tbYoua", EnumInTbYouaPrestige.toMap());
        model.addAttribute("enumUserType", EnumUserType.toMap());
        return "/crm/crmCustomerRank";
    }

    /**
     * 客户订单详细列表
     * @return String
     * @author chenyan 2011/03/17
     */
    @RequestMapping(value = "/crm/crmUserTradeDetail")
    public String getUserTradeDetail(
                                     @RequestParam(value = "userId", required = false, defaultValue = "0") Long userId,
                                     @RequestParam(value = "dataStart", required = false, defaultValue = "") String dataStart,
                                     @RequestParam(value = "dataEnd", required = false, defaultValue = "") String dataEnd,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                     Model model) {
        Map newMap = new HashMap();
        newMap.put("userId", userId);
        newMap.put("dataStart", dataStart);
        newMap.put("dataEnd", dataEnd);
        QueryPage page = userSalesManager.getUserTradeDetail(newMap, currPage, pageSize);

        model.addAttribute("query", page);
        model.addAttribute("enumExpressDistPaymentMap", EnumExpressDistPayment.toMap());
        model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());

        model.addAttribute("userId", userId);
        model.addAttribute("dataStart", dataStart);
        model.addAttribute("dataEnd", dataEnd);
        return "/crm/crmUserTradeDetail";
    }

    /**
     * 产品销售排行
     * @return
     */
    @RequestMapping(value = "/crm/crmProductRank")
    public String searchProductRankInit(
                                        @ModelAttribute("crmProductRankQuery") CrmRankQuery crmProductRankQuery,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                        Model model) {
        if (StringUtil.isBlank(crmProductRankQuery.getGmtOptTimeStart())) {
            crmProductRankQuery.setGmtOptTimeStart(DateUtil.getDiffMon(new Date(), -1));
        }
        if (StringUtil.isBlank(crmProductRankQuery.getGmtOptTimeEnd())) {
            crmProductRankQuery.setGmtOptTimeEnd(DateUtil.getDiffMon(new Date(), 0));
        }
        QueryPage page = goodsInstanceSalesManager.getProductListsByParameterMap(
            crmProductRankQuery, currPage, pageSize);
        model.addAttribute("query", page);
        return "/crm/crmProductRank";
    }

    /**
     * 销售人员销售排行
     * @return
     */
    @RequestMapping(value = "/crm/crmSalesmanRank")
    public String searchSalesmanRank(
                                     @ModelAttribute("crmSalesmanRankQuery") CrmRankQuery crmSalesmanRankQuery,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                     Model model) {
        if (StringUtil.isBlank(crmSalesmanRankQuery.getGmtOptTimeStart())) {
            crmSalesmanRankQuery.setGmtOptTimeStart(DateUtil.getDiffMon(new Date(), -1));
        }
        if (StringUtil.isBlank(crmSalesmanRankQuery.getGmtOptTimeEnd())) {
            crmSalesmanRankQuery.setGmtOptTimeEnd(DateUtil.getDiffMon(new Date(), 0));
        }
        QueryPage page = userSalesManager.getSalesManListsByParameterMap(crmSalesmanRankQuery,
            currPage, pageSize);
        model.addAttribute("query", page);
        return "/crm/crmSalesmanRank";
    }
}
