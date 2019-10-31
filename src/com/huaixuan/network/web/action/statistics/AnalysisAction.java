/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-14
 */
package com.huaixuan.network.web.action.statistics;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InOutStatReport;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.biz.service.statistics.AnalysisManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.common.util.CsvWriter;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.common.util.Page;
import com.huaixuan.network.common.util.PageUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: AnalysisAction.java,v 0.1 2011-3-14 上午11:26:54 shengyong Exp $
 */
@Controller
public class AnalysisAction extends BaseAction {

    protected final Log           log = LogFactory.getLog(this.getClass());

    @Autowired
    TradeManager                  tradeManager;

    @Autowired
    GoodsManager                  goodsManager;

    @Autowired
    AnalysisManager               analysisManager;

    @Autowired
    CategoryManager               categoryManager;

    @Autowired
    BrandService                  brandService;

    @Autowired
    DepositoryFirstManager        depositoryFirstManager;

    @Autowired
    DepLocationManager            depLocationManager;

    @Autowired
    GoodsBatchManager             goodsBatch;

    private List<Depository>      depositoryList;

    private List<DepositoryFirst> depositoryFirstList;                     // 一级仓库列表

    /**
     * 订单统计
     *
     * @return
     */
    @AdminAccess( { EnumAdminPermission.A_OANALYSIS_VIEW_USER })
    @RequestMapping(value = "/statistics/orderAnalysis", method = RequestMethod.GET)
    public String orderAnalysis(HttpServletRequest request, Model model) {
        double allTradeAmount = 0.0; // 有效订单总金额: ￥28569.50元
        long goodsClickNum = 0;// 总点击数: 516
        double TradeNumPerThousand = 0.0;// 每千点击订单数: 11.63
        double TradeAmountPerThousand = 0.0;// 每千点击购物额: ￥55367.25元
        int allTradeCount = 0;// 有效订单总数
        allTradeAmount = tradeManager.countAllTradeAmount();
        Map parMap = new HashMap();
        parMap.put("notStatus", EnumTradeStatus.TRADE_CLOSE.getKey());
        allTradeCount = tradeManager.getTradesCountByParameterMap(parMap);
        goodsClickNum = goodsManager.countAllGoodsClickNum();
        TradeNumPerThousand = (allTradeCount * 1000) / (double) goodsClickNum;
        TradeAmountPerThousand = (allTradeAmount * 1000) / (double) goodsClickNum;
        model.addAttribute("allTradeAmount", allTradeAmount);
        model.addAttribute("goodsClickNum", goodsClickNum);
        model.addAttribute("TradeNumPerThousand", TradeNumPerThousand);
        model.addAttribute("TradeAmountPerThousand", TradeAmountPerThousand);
        return "/statistics/orderAnalysis";
    }

    // 订单销售统计
    @RequestMapping(value = "/statistics/getSingleOrderAnalysis")
    public @ResponseBody
    String getSingleOrderAnalysis(@RequestParam("param1") String dateStart,
                                  @RequestParam("param2") String dateEnd,
                                  @RequestParam("param3") String flashName,
                                  @RequestParam("param4") String chartWidth,
                                  @RequestParam("param5") String chartHeight,
                                  HttpServletResponse response) {
        String reportHtml = "";
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        parMap.put("flashName", flashName);
        parMap.put("chartWidth", chartWidth);
        parMap.put("chartHeight", chartHeight);
        reportHtml = analysisManager.getSingleOrderAnalysis(parMap);
        return reportHtml;
    }

    // 订单统计加入运费统计 zhangwy
    @RequestMapping(value = "/statistics/getAllTradeMoney")
    public @ResponseBody
    Trade getAllTradeMoney(@RequestParam("param1") String dateStart,
                           @RequestParam("param2") String dateEnd) {
        double goodsAmount = 0.00;
        double shippingAmount = 0.00;
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        goodsAmount = tradeManager.countAllGoodsAmount(parMap);
        shippingAmount = tradeManager.countAllShipAmount(parMap);
        Trade trade = new Trade();
        trade.setGoodsAmount(DoubleUtil.round(goodsAmount, 2));
        trade.setShippingAmount(DoubleUtil.round(shippingAmount, 2));
        return trade;
    }

    @RequestMapping(value = "/statistics/getMultiOrderAnalysis")
    public @ResponseBody
    String getMultiOrderAnalysis(@RequestParam("param1") String yearMonth1,
                                 @RequestParam("param2") String yearMonth2,
                                 @RequestParam("param3") String yearMonth3,
                                 @RequestParam("param4") String yearMonth4,
                                 @RequestParam("param5") String yearMonth5,
                                 @RequestParam("param6") String flashName,
                                 @RequestParam("param7") String chartWidth,
                                 @RequestParam("param8") String chartHeight,
                                 HttpServletResponse response) {
        String returnHtml = "";
        Map parMap = new HashMap();
        parMap.put("yearMonth1", yearMonth1);
        parMap.put("yearMonth2", yearMonth2);
        parMap.put("yearMonth3", yearMonth3);
        parMap.put("yearMonth4", yearMonth4);
        parMap.put("yearMonth5", yearMonth5);
        parMap.put("flashName", flashName);
        parMap.put("chartWidth", chartWidth);
        parMap.put("chartHeight", chartHeight);
        returnHtml = analysisManager.getMultiOrderAnalysis(parMap);
        return returnHtml;
    }
    
    
    @AdminAccess( { EnumAdminPermission.A_HUAIXUAN_STORE })
    @RequestMapping(value = "/statistics/storeAnalysis", method = RequestMethod.GET)
    public String storeAnalysis(HttpServletRequest request, Model model) {
        return "/statistics/storeAnalysis";
    }
    
    
    @RequestMapping(value = "/statistics/getStoreAnalysis")
    public @ResponseBody
    List<String> getStoreAnalysis(@RequestParam("param1") String dateStart,
				            @RequestParam("param2") String dateEnd,
				            @RequestParam("param3") String flashName,
				            @RequestParam("param4") String chartWidth,
				            @RequestParam("param5") String chartHeight,
                                 HttpServletResponse response) {
        String returnHtml = "";
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        parMap.put("flashName", flashName);
        parMap.put("chartWidth", chartWidth);
        parMap.put("chartHeight", chartHeight);
        List<String> returnHtmlList = analysisManager.getStoreAnalysis(parMap);
        return returnHtmlList;
    }
    
    @RequestMapping(value = "/statistics/getStoreAnalysisday")
    public @ResponseBody
    List<String> getStoreAnalysisday(@RequestParam("param1") String yearMonth1,
				            @RequestParam("param2") String yearMonth2,
				            @RequestParam("param3") String yearMonth3,
				            @RequestParam("param4") String yearMonth4,
				            @RequestParam("param5") String yearMonth5,
				            @RequestParam("param6") String flashName,
				            @RequestParam("param7") String chartWidth,
				            @RequestParam("param8") String chartHeight,
				            @RequestParam("param9") String yearMonth6,
				            HttpServletResponse response) {
				String returnHtml = "";
				Map parMap = new HashMap();
				parMap.put("yearMonth1", yearMonth1);
				parMap.put("yearMonth2", yearMonth2);
				parMap.put("yearMonth3", yearMonth3);
				parMap.put("yearMonth4", yearMonth4);
				parMap.put("yearMonth5", yearMonth5);
				parMap.put("yearMonth6", yearMonth6);
				parMap.put("flashName", flashName);
				parMap.put("chartWidth", chartWidth);
				parMap.put("chartHeight", chartHeight);
				List<String> returnHtmlList = analysisManager.getStoreAnalysisday(parMap);
				return returnHtmlList;
    }

    @AdminAccess( { EnumAdminPermission.A_CATAORDANALYSIS_VIEW_USER })
    @RequestMapping(value = "/statistics/catalogOrderAnalysis")
    public String catalogOrderAnalysis(
                                       HttpServletRequest request,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                       Model model) {
        try {

            List<Category> catList = categoryManager.getCatInfoByDepth(1);
            model.addAttribute("catList", catList);

            String catCode = request.getParameter("catCode");
            String dateStart = request.getParameter("dateStart");
            String dateEnd = request.getParameter("dateEnd");

            if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
                dateStart = DateUtil.getDiffMon(new Date(), -1);
                dateEnd = DateUtil.getDateToString(new Date());
            }

            Map parMap = new HashMap();
            parMap.put("catCode", catCode);
            parMap.put("dateStart", dateStart);
            parMap.put("dateEnd", dateEnd);

            model.addAttribute("parMap", parMap);

            QueryPage query = analysisManager.getCatalogOrderAnalysis(parMap, currPage,
                this.pageSize);
            model.addAttribute("query", query);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "/statistics/catalogOrderAnalysis";
    }

    /**
     * 查询显示销售统计报表
     *
     * @return
     */
    @AdminAccess( { EnumAdminPermission.A_SANALYSIS_VIEW_USER })
    @RequestMapping(value = "/statistics/saleAnalysis")
    public String saleAnalysisResult(
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                     @ModelAttribute("saleAnalysis") SaleAnalysisTwo saleAnalysis,
                                     Model model, HttpServletRequest request) {
        try {
            List<Category> catList = categoryManager.getCatInfoByDepth(1);
            model.addAttribute("catList", catList);

            // 添加产品销售统计内容（add fanyj 2010-12-2）
            String actionType = request.getParameter("actionType");
            if (StringUtils.isBlank(saleAnalysis.getActionType()) && StringUtil.isBlank(actionType)) {
                actionType = "goods";// 默认是商品销售统计
            }

            saleAnalysis.setActionType(actionType);

            if (StringUtil.isBlank(saleAnalysis.getDateStart())
                && StringUtil.isBlank(saleAnalysis.getDateEnd())) {
                saleAnalysis.setDateStart(DateUtil.getDiffMon(new Date(), -1));
                saleAnalysis.setDateEnd(DateUtil.getDateToString(new Date()));

            }

            Long interval = changStringToDate(saleAnalysis.getDateStart(), saleAnalysis
                .getDateEnd());// 查询的时间间隔

            Map parMap = new HashMap();
            parMap.put("title", saleAnalysis.getTitle());
            parMap.put("catId", saleAnalysis.getCatId());
            parMap.put("dateStart", saleAnalysis.getDateStart());
            parMap.put("dateEnd", saleAnalysis.getDateEnd());
            parMap.put("goodsSn", saleAnalysis.getGoodsSn());

            // 添加产品销售统计内容（add fanyj 2010-12-2）
            parMap.put("actionType", saleAnalysis.getActionType());

            List<SaleAnalysisTwo> SaleAnalysisList = new ArrayList<SaleAnalysisTwo>();

            int countOne = analysisManager.getSaleAnalysisCount(parMap);
            int countTwo = analysisManager.getRefundAnalysisCount(parMap);

            PageUtils pageUtils = new PageUtils();
            Page page = new Page();
            page.setCurrentPage(currPage);
            page.setPageSize(this.pageSize);

            if (countOne > 0) {
                if (page.getCurrentPage() == 1) {
                    model.addAttribute("sumMap", analysisManager
                        .getSaleAnalysisTradeOutPriceSum(parMap));
                }
                List<SaleAnalysisTwo> saleAnalysistempOne = analysisManager.getSaleAnalysis(parMap);
                if (saleAnalysistempOne != null && saleAnalysistempOne.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempOne) {
                        temp.setStorageLasts((double) Math
                            .round((temp.getGoodsNumber() / (double) (temp.getSaleNum()))
                                   * interval * 100) / 100);
                        SaleAnalysisList.add(temp);
                    }
                }
            }

            if (countTwo > 0) {
                if (page.getCurrentPage() == 1) {
                    model.addAttribute("refundMap", analysisManager
                        .getRefundAnalysisTradeOutPriceSum(parMap));
                }
                List<SaleAnalysisTwo> saleAnalysistempTwo = analysisManager
                    .getRefundAnalysis(parMap);
                if (saleAnalysistempTwo != null && saleAnalysistempTwo.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempTwo) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            Collections.sort(SaleAnalysisList, Collections.reverseOrder());

            page.setTotalRowsAmount(SaleAnalysisList.size());
            SaleAnalysisList = pageUtils.doPage(SaleAnalysisList, page);

            QueryPage query = new QueryPage(saleAnalysis);
            query.setCurrentPage(page.getCurrentPage());
            query.setPageSize(page.getPageSize());
            query.setItems(SaleAnalysisList);
            query.setTotalItem(page.getTotalRowsAmount());
            model.addAttribute("query", query);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 添加产品销售统计内容（add fanyj 2010-12-2）
        if ("products".equals(saleAnalysis.getActionType())) {
            return "/statistics/saleAnalysisProducts";
        }
        return "/statistics/saleAnalysis";
    }

    @RequestMapping(value = "/statistics/exportSaleAnal")
    public String exportSaleAnal(Model model, HttpServletRequest request, HttpServletResponse res,
                                 @ModelAttribute("saleAnalysis") SaleAnalysisTwo saleAnalysis) {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=saleAnalysis" + date
                                                 + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");

            String actionType = request.getParameter("actionType");
            if (StringUtils.isBlank(saleAnalysis.getActionType()) && StringUtil.isBlank(actionType)) {
                actionType = "goods";// 默认是商品销售统计
            }

            saleAnalysis.setActionType(actionType);

            Map parMap = new HashMap();

            parMap.put("title", saleAnalysis.getTitle());
            parMap.put("catId", saleAnalysis.getCatId());
            parMap.put("dateStart", saleAnalysis.getDateStart());
            parMap.put("dateEnd", saleAnalysis.getDateEnd());
            parMap.put("goodsSn", saleAnalysis.getGoodsSn());
            parMap.put("actionType", saleAnalysis.getActionType());

            List<SaleAnalysisTwo> SaleAnalysisList = new ArrayList<SaleAnalysisTwo>();

            int countOne = analysisManager.getSaleAnalysisCount(parMap);
            int countTwo = analysisManager.getRefundAnalysisCount(parMap);

            Long interval = changStringToDate(saleAnalysis.getDateStart(), saleAnalysis
                .getDateEnd());// 查询的时间间隔
            if (countOne > 0) {
                List<SaleAnalysisTwo> saleAnalysistempOne = analysisManager.getSaleAnalysis(parMap);
                if (saleAnalysistempOne != null && saleAnalysistempOne.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempOne) {
                        temp.setStorageLasts((double) Math
                            .round(temp.getGoodsNumber() / (double) (temp.getSaleNum() * interval)
                                   * 100) / 100);
                        SaleAnalysisList.add(temp);
                    }
                }
            }

            if (countTwo > 0) {
                List<SaleAnalysisTwo> saleAnalysistempTwo = analysisManager
                    .getRefundAnalysis(parMap);
                if (saleAnalysistempTwo != null && saleAnalysistempTwo.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempTwo) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            Collections.sort(SaleAnalysisList, Collections.reverseOrder());

            // 导出到 excel
            List<Object[]> goodsCatList = new ArrayList<Object[]>();
            String name = "";
            if (actionType.equals("products")) {
                name = "产品名称";
            } else {
                name = "商品名称";
            }
            String[] title = { "编码", name, "类目", "属性", "单位", "销售数量", "销售成本", "销售金额", "毛利", "毛利率",
                    "可用库存", "库存消化天数" };
            goodsCatList.add(title);
            for (SaleAnalysisTwo sa : SaleAnalysisList) {
                Object[] good = {
                        sa.getGoodsSn(),
                        sa.getTitle(),
                        sa.getCatName(),
                        sa.getAttrs(),
                        sa.getUnit(),
                        sa.getSaleNum(),
                        DoubleUtil.round(sa.getGoodsInPrice(), 2),
                        DoubleUtil.round(sa.getSalePrice(), 2),
                        Double.valueOf(sa.getSaleProfit()),
                        String.valueOf(sa.getSaleProfitPer()) + "%",
                        sa.getGoodsNumber(),
                        (double) Math.round((sa.getGoodsNumber() == null ? 0 : sa.getGoodsNumber()
                                                                               / (double) (sa
                                                                                   .getSaleNum())
                                                                               * interval) * 100) / 100 };
                goodsCatList.add(good);
            }
            goodsBatch.exportExcelByObject(outwt, goodsCatList);
            outwt.flush();
        } catch (Exception e) {
            request.setAttribute("errorMessage", "导出失败");
            log.error(e);
        } finally {
            close(outwt);
        }
        // 添加产品销售统计内容（add fanyj 2010-12-2）
        if ("products".equals(saleAnalysis.getActionType())) {
            return "/statistics/saleAnalysisProducts";
        }
        return "/statistics/saleAnalysis";
    }

    public static void close(OutputStream out) {

        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    /**
     * 计算时间间隔 用于库存消耗天数
     *
     * @author chenhang 2010/11/09
     */
    public long changStringToDate(String dateStart, String dateEnd) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long l = 0;
        try {
            Date d1 = df.parse(dateStart);
            Date d2 = df.parse(dateEnd);
            long i = d2.getTime() - d1.getTime();
            l = i / (1000 * 60 * 60 * 24) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 查询显示销售统计报表
     *
     * @return
     */
    @AdminAccess( { EnumAdminPermission.A_SANALYSIS_DETAIL_USER })
    @RequestMapping(value = "/statistics/saleAnalysisDetail")
    public String saleAnalysisResultDetail(
                                           @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                           @ModelAttribute("saleAnalysis") SaleAnalysis saleAnalysis,
                                           Model model, HttpServletRequest request) {
        try {
            List<Category> catList = categoryManager.getCatInfoByDepth(1);
            model.addAttribute("catList", catList);

            if (StringUtil.isBlank(saleAnalysis.getDateStart())
                && StringUtil.isBlank(saleAnalysis.getDateEnd())) {
                saleAnalysis.setDateStart(DateUtil.getDiffMon(new Date(), -1));
                saleAnalysis.setDateEnd(DateUtil.getDateToString(new Date()));
            }

            Map parMap = new HashMap();
            parMap.put("buyNick", saleAnalysis.getBuyNick());
            parMap.put("title", saleAnalysis.getTitle());
            parMap.put("catId", saleAnalysis.getCatId());
            parMap.put("dateStart", saleAnalysis.getDateStart());
            parMap.put("dateEnd", saleAnalysis.getDateEnd());
            parMap.put("goodsSn", saleAnalysis.getGoodsSn());

            List<SaleAnalysis> SaleAnalysisList = new ArrayList<SaleAnalysis>();

            int count = analysisManager.getSaleAnalysisDetailCount(parMap);
            int countTwo = analysisManager.getRefundAnalysisDetailCount(parMap);

            PageUtils pageUtils = new PageUtils();
            Page page = new Page();
            page.setCurrentPage(currPage);
            page.setPageSize(this.pageSize);

            if (count > 0) {
                if (page.getCurrentPage() == 1) {
                    model.addAttribute("sumMap", analysisManager.getSaleAnalysisDetailSum(parMap));
                }
                List<SaleAnalysis> saleAnalysistempOne = analysisManager
                    .getSaleAnalysisDetail(parMap);
                if (saleAnalysistempOne != null && saleAnalysistempOne.size() > 0) {
                    for (SaleAnalysis temp : saleAnalysistempOne) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            if (countTwo > 0) {
                if (page.getCurrentPage() == 1) {
                    model.addAttribute("refundMap", analysisManager
                        .getRefundAnalysisDetailSum(parMap));
                }
                List<SaleAnalysis> saleAnalysistempTwo = analysisManager
                    .getRefundAnalysisDetail(parMap);
                if (saleAnalysistempTwo != null && saleAnalysistempTwo.size() > 0) {
                    for (SaleAnalysis temp : saleAnalysistempTwo) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            Collections.sort(SaleAnalysisList);
            page.setTotalRowsAmount(SaleAnalysisList.size());
            SaleAnalysisList = pageUtils.doPage(SaleAnalysisList, page);

            QueryPage query = new QueryPage(saleAnalysis);
            query.setCurrentPage(page.getCurrentPage());
            query.setPageSize(page.getPageSize());
            query.setItems(SaleAnalysisList);
            query.setTotalItem(page.getTotalRowsAmount());
            model.addAttribute("query", query);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "/statistics/saleAnalysisDetail";
    }

    @RequestMapping(value = "/statistics/saleAnalysisDetailExport")
    public String exportSaleAnalDetail(Model model, HttpServletRequest request,
                                       HttpServletResponse res,
                                       @ModelAttribute("saleAnalysis") SaleAnalysis saleAnalysis) {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=saleAnalysis" + date
                                                 + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");

            Map parMap = new HashMap();
            parMap.put("buyNick", saleAnalysis.getBuyNick());
            parMap.put("title", saleAnalysis.getTitle());
            parMap.put("catId", saleAnalysis.getCatId());
            parMap.put("dateStart", saleAnalysis.getDateStart());
            parMap.put("dateEnd", saleAnalysis.getDateEnd());
            parMap.put("goodsSn", saleAnalysis.getGoodsSn());
            List<SaleAnalysis> SaleAnalysisList = new ArrayList<SaleAnalysis>();

            int count = analysisManager.getSaleAnalysisDetailCount(parMap);
            int countTwo = analysisManager.getRefundAnalysisDetailCount(parMap);

            if (count > 0) {
                List<SaleAnalysis> saleAnalysistempOne = analysisManager
                    .getSaleAnalysisDetail(parMap);
                if (saleAnalysistempOne != null && saleAnalysistempOne.size() > 0) {
                    for (SaleAnalysis temp : saleAnalysistempOne) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            if (countTwo > 0) {
                List<SaleAnalysis> saleAnalysistempTwo = analysisManager
                    .getRefundAnalysisDetail(parMap);
                if (saleAnalysistempTwo != null && saleAnalysistempTwo.size() > 0) {
                    for (SaleAnalysis temp : saleAnalysistempTwo) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            Collections.sort(SaleAnalysisList);
            // 导出到 excel
            List<Object[]> goodsCatList = new ArrayList<Object[]>();
            String[] title = { "编码", "用户名", "商品名称", "类目", "属性", "单位", "销售数量", "销售成本", "销售金额", "毛利",
                    "毛利率" };
            goodsCatList.add(title);
            for (SaleAnalysis sa : SaleAnalysisList) {
                Object[] good = { sa.getGoodsSn(), sa.getBuyNick(), sa.getTitle(), sa.getCatName(),
                        sa.getAttrs(), sa.getUnit(), sa.getSaleNum(),
                        DoubleUtil.round(sa.getGoodsInPrice(), 2),
                        DoubleUtil.round(sa.getSalePrice(), 2), Double.valueOf(sa.getSaleProfit()),
                        String.valueOf(sa.getSaleProfitPer()) + "%" };
                goodsCatList.add(good);
            }
            goodsBatch.exportExcelByObject(outwt, goodsCatList);
            outwt.flush();
        } catch (Exception e) {
            request.setAttribute("errorMessage", "导出失败");
            log.error(e);
        } finally {
            close(outwt);
        }
        return "/statistics/saleAnalysisDetail";
    }

    /**
     * 热销商品
     *
     * @return
     * @throws Exception
     */
    @AdminAccess( { EnumAdminPermission.A_HOTGOODS_VIEW_USER })
    @RequestMapping(value = "/statistics/searchHotGoods")
    public String searchHotGoods(
                                 HttpServletRequest request,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                 Model model) {

        // 增加品牌检索条件 zhangwy
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute("brandList", brandList);

        List<Category> categorys;
        categorys = categoryManager.getCategoryForGuide();
        model.addAttribute("categorys", categorys);

        int count = 0;

        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        String goodsNum = request.getParameter("goodsNum");
        String dosearch = request.getParameter("dosearch");

        if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
            dateStart = DateUtil.getDiffMon(new Date(), -1);
            dateEnd = DateUtil.getDateToString(new Date());

        }

        if (StringUtil.isNotBlank(goodsNum) && StringUtil.isNumeric(goodsNum)) {
            count = (new Integer(goodsNum)).intValue();
        } else {
            goodsNum = "20";
            count = 20;
        }
        String catCode = request.getParameter("catCode");
        String brandId = request.getParameter("brandId");
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        parMap.put("catCode", catCode);
        parMap.put("goodsNum", Integer.valueOf(goodsNum));
        parMap.put("brandId", brandId);
        model.addAttribute("parMap", parMap);
        if (count > 0) {
            // int total = analysisManager.getHotSalesGoodsCount(parMap);

            if (currPage == 1) {
                model.addAttribute("sumMap", analysisManager.getHotSalesGoodsSum(parMap));
            }

            model.addAttribute("query", analysisManager.getHotSalesGoods(parMap, currPage,
                this.pageSize));

        }
        return "/statistics/searchHotGoods";
    }

    /**
     * 滞销商品
     *
     * @return
     * @throws Exception
     */
    @AdminAccess( { EnumAdminPermission.A_SLOWGOODS_VIEW_USER })
    @RequestMapping(value = "/statistics/searchSlowGoods")
    public String searchSlowGoods(
                                  HttpServletRequest request,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                  Model model) {
        try {
            List<Category> catList = categoryManager.getCatInfoByDepth(1);
            model.addAttribute("catList", catList);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        int count = 0;

        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        String goodsNum = request.getParameter("goodsNum");
        String dosearch = request.getParameter("dosearch");
        // zhangwy
        String goodsCode = request.getParameter("goodsCode");

        if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
            dateStart = DateUtil.getDiffMon(new Date(), -1);
            dateEnd = DateUtil.getDateToString(new Date());

        }

        if (StringUtil.isNotBlank(goodsNum) && StringUtil.isNumeric(goodsNum)) {
            count = (new Integer(goodsNum)).intValue();
        } else {
            goodsNum = "20";
            count = 20;
        }
        String catCode = request.getParameter("catCode");
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        parMap.put("catCode", catCode);
        parMap.put("goodsNum", goodsNum);
        // zhangwy
        parMap.put("goodsCode", goodsCode);
        model.addAttribute("parMap", parMap);
        if (count >= 0) {

            if (currPage == 1) {
                model.addAttribute("sumMap", analysisManager.getSlowSalesGoodsSum(parMap));
            }

            model.addAttribute("query", analysisManager.getSlowSalesGoods(parMap, currPage,
                this.pageSize));

        }
        return "/statistics/searchSlowGoods";
    }

    /**
     * 类目销售汇总
     *
     * @return
     * @throws Exception
     */
    @AdminAccess( { EnumAdminPermission.A_CATSUM_VIEW_USER })
    @RequestMapping(value = "/statistics/searchCatSum")
    public String searchCatSum(
                               HttpServletRequest request,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                               Model model) {

        List<Category> categorys = categoryManager.getCategoryForGuide();
        model.addAttribute("categorys", categorys);

        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");

        String catCode = request.getParameter("catCode");
        String dosearch = request.getParameter("dosearch");

        if (StringUtil.isBlank(dateStart) && StringUtil.isBlank(dateEnd)) {
            dateStart = DateUtil.getDiffMon(new Date(), -1);
            dateEnd = DateUtil.getDateToString(new Date());

        }
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        parMap.put("catCode", catCode);
        model.addAttribute("parMap", parMap);

        if (currPage == 1) {
            model.addAttribute("sumMap", analysisManager.getCatSalesGoodsSum(parMap));
        }

        model.addAttribute("query", analysisManager.getCatSalesGoods(parMap, currPage,
            this.pageSize));

        return "/statistics/searchCatSum";
    }

    @AdminAccess( { EnumAdminPermission.A_CANALYSIS_VIEW_USER })
    @RequestMapping(value = "/statistics/catalogAnalysis")
    public String catalogAnalysis() {
        return "/statistics/catalogAnalysis";
    }

    /**
     * @Title: getCatalogAnalysis
     * @Description: 一级目录商品销售金额统计
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/statistics/getCatalogAnalysis")
    public @ResponseBody
    String getCatalogAnalysis(@RequestParam("param1") String dateStart,
                              @RequestParam("param2") String dateEnd,
                              @RequestParam("param3") String flashName,
                              @RequestParam("param4") String chartWidth,
                              @RequestParam("param5") String chartHeight,
                              HttpServletResponse response) {
        String reportHtml = "";
        Map parMap = new HashMap();
        parMap.put("dateStart", dateStart);
        parMap.put("dateEnd", dateEnd);
        parMap.put("flashName", flashName);
        parMap.put("chartWidth", chartWidth);
        parMap.put("chartHeight", chartHeight);

        reportHtml = analysisManager.getCatalogAnalysis(parMap);
        return reportHtml;
    }

    @Autowired
    DepositoryService depositoryService;

    @Autowired
    StorageManager    storageManager;

    /**
     * 类目库存成本汇总
     *
     * @return
     * @throws Exception
     */
    @AdminAccess( { EnumAdminPermission.A_STORAGECOST_VIEW_USER })
    @RequestMapping(value = "/statistics/searchStorageCost")
    public String searchStorageCost(
                                    HttpServletRequest request,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                    Model model) throws Exception {
        Map parMap = new HashMap();
        parMap.put("status", "v");
        DepositoryQuery dquery = new DepositoryQuery();
        dquery.setStatus("v");
        QueryPage query = depositoryService.getDepositorysByParMap(dquery, currPage, this.pageSize,
            false);
        request.setAttribute("query", query);

        String depId = request.getParameter("depId");
        parMap.put("depId", depId);
        if (StringUtil.isBlank(depId)) {
            return "/statistics/searchStorageCost";
        }
        model.addAttribute("parMap", parMap);
        request.setAttribute("storages", storageManager.sumStorageCostByDepid(new Long(depId)));

        return "/statistics/searchStorageCost";
    }

    @AdminAccess( { EnumAdminPermission.A_INSTORAGE_VIEW_USER })
    @RequestMapping(value = "/statistics/searchInStorage")
    public String searchInStorage(
                                  HttpServletRequest request,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                  Model model,
                                  @ModelAttribute("storageQuery") StorageQuery storageQuery,
                                  AdminAgent adminAgent) throws Exception {

        // 加入全部的一级仓库ID
        this.initDepository(storageQuery, adminAgent, model);

        List<Category> catList = categoryManager.getCatInfoByDepth(1);
        model.addAttribute("catList", catList);

        Map parMap = new HashMap();
        parMap.put("depfirstIds", getDepfirstIdForQuery(adminAgent));
        parMap.put("instanceName", request.getParameter("instanceName"));
        parMap.put("code", request.getParameter("code"));
        parMap.put("catCode", request.getParameter("catCode"));
        parMap.put("storType", request.getParameter("storType"));
        parMap.put("depfirstId", request.getParameter("depfirstId"));
        parMap.put("depId", request.getParameter("depId"));
        parMap.put("locId", request.getParameter("locId"));
        QueryPage query = analysisManager.getGoodsInStorage(parMap, currPage, this.pageSize);
        model.addAttribute("parMap", parMap);
        model.addAttribute("query", query);
        return "/statistics/searchInStorage";
    }

    /**
     * 月进出货统计报表
     *
     * @return
     * @throws Exception
     */
    @AdminAccess( { EnumAdminPermission.A_IN_OUT_STAT_REPORT_USER })
    @RequestMapping(value = "/statistics/searchInOutStatReport")
    public String searchInOutStatReport(
                                        HttpServletRequest request,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                        Model model,
                                        @ModelAttribute("storageQuery") StorageQuery storageQuery,
                                        AdminAgent adminAgent) throws Exception {
        // 加入全部的一级仓库ID
        this.initDepository(storageQuery, adminAgent, model);
        Map parMap = new HashMap();
        parMap.put("year", request.getParameter("year"));
        parMap.put("month", request.getParameter("month"));
        parMap.put("depfirstIds", getDepfirstIdForQuery(adminAgent));
        parMap.put("goodsName", request.getParameter("goodsName"));
        parMap.put("goodsCode", request.getParameter("goodsCode"));
        parMap.put("groupByCode", "true");
        parMap.put("depfirstId", request.getParameter("depfirstId"));
        parMap.put("depId", request.getParameter("depId"));
        parMap.put("locId", request.getParameter("locId"));
        if (StringUtil.isNotBlank(storageQuery.getDepId())) {
            QueryPage query = analysisManager.getInOutStatReportListByMap(parMap, currPage,
                this.pageSize);
            model.addAttribute("query", query);
        }
        model.addAttribute("parMap", parMap);
        return "/statistics/searchInOutStatReport";
    }

    /**
     * 月进出货统计报表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/statistics/exportInOutStatReport")
    public String exportInOutStatReport(
                                        HttpServletRequest request,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                        Model model,
                                        @ModelAttribute("storageQuery") StorageQuery storageQuery,
                                        AdminAgent adminAgent, HttpServletResponse response)
                                                                                            throws Exception {
        Map parMap = new HashMap();
        parMap.put("year", request.getParameter("year"));
        parMap.put("month", request.getParameter("month"));
        parMap.put("depfirstIds", getDepfirstIdForQuery(adminAgent));
        parMap.put("goodsName", request.getParameter("goodsName"));
        parMap.put("goodsCode", request.getParameter("goodsCode"));
        parMap.put("groupByCode", "true");
        parMap.put("depFirstId", request.getParameter("depFirstId"));
        parMap.put("depId", request.getParameter("depId"));
        parMap.put("locId", request.getParameter("locId"));
        if (StringUtil.isNotBlank(storageQuery.getDepId())) {
            QueryPage query = analysisManager.getInOutStatReportListByMap(parMap, currPage,
                this.pageSize);
            model.addAttribute("query", query);
        }

        try {
            response.resetBuffer();

            response.getOutputStream().flush();
            response.setCharacterEncoding("GBK");
            response.setContentType("application/oct-stream");
            Date da = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            response.setHeader("Content-Disposition", "attachment;Filename=" + date
                                                      + "InOutStatReport.csv");

            CsvWriter writer = new CsvWriter(response.getOutputStream(), ',', Charset
                .forName("GBK"));

            long sum_lastRemainAmount = 0;
            double sum_lastRemainMoney = 0;
            long sum_thisInAmount = 0;
            double sum_thisInMoney = 0;
            long sum_thisOutAmount = 0;
            double sum_thisOutMoney = 0;
            long sum_thisRemainAmount = 0;
            double sum_thisRemainMoney = 0;

            QueryPage query = analysisManager.getInOutStatReportListByMap(parMap, currPage,
                this.pageSize);
            List<InOutStatReport> inOutStatReportList = (List<InOutStatReport>) query.getItems();

            String[] titleName = { "商品名称", "商品编码", "上月结余数量", "上月结余金额(￥)", "本月进货数量", "本月进货金额(￥)",
                    "本月出货数量", "本月出货金额(￥)", "本月结余数量", "本月结余金额(￥)" };
            writer.writeRecord(titleName);
            if (inOutStatReportList != null) {
                for (InOutStatReport report : inOutStatReportList) {
                    String[] data = { report.getGoodsName(), report.getGoodsCode(),
                            report.getLastRemainAmount() + "", report.getLastRemainMoney() + "",
                            report.getThisInAmount() + "", report.getThisInMoney() + "",
                            report.getThisOutAmount() + "", report.getThisOutMoney() + "",
                            report.getThisRemainAmount() + "", report.getThisRemainMoney() + "" };
                    writer.writeRecord(data);
                    sum_lastRemainAmount = MoneyUtil.addNumber(sum_lastRemainAmount, report
                        .getLastRemainAmount());
                    sum_lastRemainMoney = MoneyUtil.add(sum_lastRemainMoney, report
                        .getLastRemainMoney());
                    sum_thisInAmount = MoneyUtil.addNumber(sum_thisInAmount, report
                        .getThisInAmount());
                    sum_thisInMoney = MoneyUtil.add(sum_thisInMoney, report.getThisInMoney());
                    sum_thisOutAmount = MoneyUtil.addNumber(sum_thisOutAmount, report
                        .getThisOutAmount());
                    sum_thisOutMoney = MoneyUtil.add(sum_thisOutMoney, report.getThisOutMoney());
                    sum_thisRemainAmount = MoneyUtil.addNumber(sum_thisRemainAmount, report
                        .getThisRemainAmount());
                    sum_thisRemainMoney = MoneyUtil.add(sum_thisRemainMoney, report
                        .getThisRemainMoney());
                }
            }
            String[] bottom = { "合计", "", sum_lastRemainAmount + "",
                    "￥" + MoneyUtil.getFormatMoney(sum_lastRemainMoney, "0.00"),
                    sum_thisInAmount + "", "￥" + MoneyUtil.getFormatMoney(sum_thisInMoney, "0.00"),
                    sum_thisOutAmount + "",
                    "￥" + MoneyUtil.getFormatMoney(sum_thisOutMoney, "0.00"),
                    sum_thisRemainAmount + "",
                    "￥" + MoneyUtil.getFormatMoney(sum_thisRemainMoney, "0.00") };
            writer.writeRecord(bottom);
            writer.flush();
            writer.close();
            response.getOutputStream().flush();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "导出失败！");
            log.error(e);
        }

        return "";
    }

    /*
     * 初始化仓库信息
     *
     * @param storageQuery
     *
     * @param adminAgent
     *
     * @param model
     */
    private void initDepository(StorageQuery storageQuery, AdminAgent adminAgent, Model model) {
        // 加入全部的一级仓库ID
        depositoryFirstList = depositoryFirstManager
            .getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
        if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
            model.addAttribute("message", "一级仓库记录为空！");
        } else {
            model.addAttribute("depositoryFirstList", depositoryFirstList);
            if (StringUtils.isNotBlank(storageQuery.getDepfirstId())
                && StringUtils.isNumeric(storageQuery.getDepfirstId())) {
                model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long
                    .valueOf(storageQuery.getDepfirstId())));
            }
            if (StringUtils.isNotBlank(storageQuery.getDepId())
                && StringUtils.isNumeric(storageQuery.getDepId())) {
                model.addAttribute("depLocationLists", depLocationManager
                    .getRightLocationsByDepositoryId(Long.valueOf(storageQuery.getDepId())));
            }
        }
    }

    @RequestMapping(value = "/statistics/sanalysisDepository")
    public String saleAnalysisDepository(
                                         @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                         @ModelAttribute("saleAnalysis") SaleAnalysisTwo saleAnalysis,
                                         AdminAgent adminAgent, Model model,
                                         HttpServletRequest request) {
        try {
            //初始化仓库信息
            // 加入全部的一级仓库ID
            depositoryFirstList = depositoryFirstManager
                .getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
            if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
                model.addAttribute("message", "一级仓库记录为空！");
            } else {
                model.addAttribute("depositoryFirstList", depositoryFirstList);
                if (saleAnalysis.getDepFirstId() != null) {
                    model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long
                        .valueOf(saleAnalysis.getDepFirstId())));
                }
                if (saleAnalysis.getDepId() != null) {
                    model.addAttribute("depLocationLists", depLocationManager
                        .getRightLocationsByDepositoryId(Long.valueOf(saleAnalysis.getDepId())));
                }
            }

            List<Category> catList = categoryManager.getCatInfoByDepth(1);
            model.addAttribute("catList", catList);

            if (StringUtil.isBlank(saleAnalysis.getDateStart())
                && StringUtil.isBlank(saleAnalysis.getDateEnd())) {
                saleAnalysis.setDateStart(DateUtil.getDiffMon(new Date(), -1));
                saleAnalysis.setDateEnd(DateUtil.getDateToString(new Date()));

            }

            Long interval = changStringToDate(saleAnalysis.getDateStart(), saleAnalysis
                .getDateEnd());// 查询的时间间隔

            Map parMap = new HashMap();
            parMap.put("title", saleAnalysis.getTitle());
            parMap.put("catId", saleAnalysis.getCatId());
            parMap.put("dateStart", saleAnalysis.getDateStart());
            parMap.put("dateEnd", saleAnalysis.getDateEnd());
            parMap.put("goodsSn", saleAnalysis.getGoodsSn());
            parMap.put("depfirstId", saleAnalysis.getDepFirstId());
            parMap.put("depId", saleAnalysis.getDepId());
            parMap.put("locId", saleAnalysis.getLocId());
            List<SaleAnalysisTwo> SaleAnalysisList = new ArrayList<SaleAnalysisTwo>();

            int countOne = analysisManager.getSaleAnalysisDepositoryCount(parMap);
            int countTwo = analysisManager.getRefundAnalysisDepositoryCount(parMap);

            PageUtils pageUtils = new PageUtils();
            Page page = new Page();
            page.setCurrentPage(currPage);
            page.setPageSize(this.pageSize);

            if (countOne > 0) {
                if (page.getCurrentPage() == 1) {
                    model.addAttribute("sumMap", analysisManager
                        .getSaleAnalysisDepositoryTradeOutPriceSum(parMap));
                }
                List<SaleAnalysisTwo> saleAnalysistempOne = analysisManager
                    .getSaleAnalysisDepository(parMap);
                if (saleAnalysistempOne != null && saleAnalysistempOne.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempOne) {
                        temp.setStorageLasts((double) Math.round((temp.getGoodsNumber() == null ? 0
                            : temp.getGoodsNumber() / (double) (temp.getSaleNum()))
                                                                 * interval * 100) / 100);
                        SaleAnalysisList.add(temp);
                    }
                }
            }

            if (countTwo > 0) {
                if (page.getCurrentPage() == 1) {
                    model.addAttribute("refundMap", analysisManager
                        .getRefundAnalysisDepositoryTradeOutPriceSum(parMap));
                }
                List<SaleAnalysisTwo> saleAnalysistempTwo = analysisManager
                    .getRefundAnalysisDepository(parMap);
                if (saleAnalysistempTwo != null && saleAnalysistempTwo.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempTwo) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            Collections.sort(SaleAnalysisList, Collections.reverseOrder());

            page.setTotalRowsAmount(SaleAnalysisList.size());
            SaleAnalysisList = pageUtils.doPage(SaleAnalysisList, page);

            QueryPage query = new QueryPage(saleAnalysis);
            query.setCurrentPage(page.getCurrentPage());
            query.setPageSize(page.getPageSize());
            query.setItems(SaleAnalysisList);
            query.setTotalItem(page.getTotalRowsAmount());
            model.addAttribute("query", query);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "/statistics/saleAnalysisDepository";
    }

    /**
     * 导出销售统计（商品分仓）的数据
     * @param model
     * @param request
     * @param res
     * @param saleAnalysis
     * @return
     */
    @RequestMapping(value = "/statistics/exportSaleAnalDepository")
    public String exportSaleAnalDepository(
                                           Model model,
                                           HttpServletRequest request,
                                           HttpServletResponse res,
                                           @ModelAttribute("saleAnalysis") SaleAnalysisTwo saleAnalysis) {
        OutputStream outwt = null;
        try {
            Date da = new Date();
            outwt = res.getOutputStream();
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
            String date = df.format(da);
            res.setHeader("Content-disposition", "attachment; filename=saleAnalysis" + date
                                                 + ".xls");
            res.setContentType("application/octet-stream;charset=utf-8");

            Map parMap = new HashMap();

            parMap.put("title", saleAnalysis.getTitle());
            parMap.put("catId", saleAnalysis.getCatId());
            parMap.put("dateStart", saleAnalysis.getDateStart());
            parMap.put("dateEnd", saleAnalysis.getDateEnd());
            parMap.put("goodsSn", saleAnalysis.getGoodsSn());
            parMap.put("depfirstId", saleAnalysis.getDepFirstId());
            parMap.put("depId", saleAnalysis.getDepId());
            parMap.put("locId", saleAnalysis.getLocId());

            List<SaleAnalysisTwo> SaleAnalysisList = new ArrayList<SaleAnalysisTwo>();

            int countOne = analysisManager.getSaleAnalysisDepositoryCount(parMap);
            int countTwo = analysisManager.getRefundAnalysisDepositoryCount(parMap);

            Long interval = changStringToDate(saleAnalysis.getDateStart(), saleAnalysis
                .getDateEnd());// 查询的时间间隔
            if (countOne > 0) {
                List<SaleAnalysisTwo> saleAnalysistempOne = analysisManager
                    .getSaleAnalysisDepository(parMap);
                if (saleAnalysistempOne != null && saleAnalysistempOne.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempOne) {
                        temp.setStorageLasts((double) Math
                            .round(temp.getGoodsNumber() == null ? 0
                                : temp.getGoodsNumber() / (double) (temp.getSaleNum() * interval)
                                  * 100) / 100);
                        SaleAnalysisList.add(temp);
                    }
                }
            }

            if (countTwo > 0) {
                List<SaleAnalysisTwo> saleAnalysistempTwo = analysisManager
                    .getRefundAnalysisDepository(parMap);
                if (saleAnalysistempTwo != null && saleAnalysistempTwo.size() > 0) {
                    for (SaleAnalysisTwo temp : saleAnalysistempTwo) {
                        SaleAnalysisList.add(temp);
                    }
                }
            }
            Collections.sort(SaleAnalysisList, Collections.reverseOrder());

            // 导出到 excel
            List<Object[]> goodsCatList = new ArrayList<Object[]>();
            String[] title = { "编码", "商品名称", "类目", "一级仓库", "仓库", "库位", "属性", "单位", "销售数量", "销售成本",
                    "销售金额", "毛利", "毛利率", "可用库存", "库存消化天数" };
            goodsCatList.add(title);
            for (SaleAnalysisTwo sa : SaleAnalysisList) {
                Object[] good = {
                        sa.getGoodsSn(),
                        sa.getTitle(),
                        sa.getCatName(),
                        sa.getDepFirstName(),
                        sa.getDepositoryName(),
                        sa.getLocName(),
                        sa.getAttrs(),
                        sa.getUnit(),
                        sa.getSaleNum(),
                        DoubleUtil.round(sa.getGoodsInPrice(), 2),
                        DoubleUtil.round(sa.getSalePrice(), 2),
                        Double.valueOf(sa.getSaleProfit()),
                        String.valueOf(sa.getSaleProfitPer()) + "%",
                        sa.getGoodsNumber(),
                        (double) Math.round((sa.getGoodsNumber() == null ? 0 : sa.getGoodsNumber()
                                                                               / (double) (sa
                                                                                   .getSaleNum()))
                                            * interval * 100) / 100 };
                goodsCatList.add(good);
            }
            goodsBatch.exportExcelByObject(outwt, goodsCatList);
            outwt.flush();
        } catch (Exception e) {
            request.setAttribute("errorMessage", "导出失败");
            log.error(e);
        } finally {
            close(outwt);
        }
        return "/statistics/saleAnalysisDepository";
    }

    /*
     * 初始化仓库信息
     *
     * @param moveStorageQuery
     * @param adminAgent
     * @param model
     */
    private List<DepositoryFirst> initDepository(MoveStorageQuery moveStorageQuery,
                                                 AdminAgent adminAgent, Model model) {
        //加入全部的一级仓库ID
        List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
            .getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
        moveStorageQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
        if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
            model.addAttribute("message", "一级仓库记录为空！");
        } else {
            model.addAttribute("depositoryFirstList", depositoryFirstList);
            if (StringUtils.isNotBlank(moveStorageQuery.getDepfirstId())
                && StringUtils.isNumeric(moveStorageQuery.getDepfirstId())) {
                model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long
                    .valueOf(moveStorageQuery.getDepfirstId())));
            }
            if (StringUtils.isNotBlank(moveStorageQuery.getDepId())
                && StringUtils.isNumeric(moveStorageQuery.getDepId())) {
                model.addAttribute("depLocationLists", depLocationManager
                    .getLocationsByDepositoryId(Long.valueOf(moveStorageQuery.getDepId())));
            }
        }
        return depositoryFirstList;
    }
}
