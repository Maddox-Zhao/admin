/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-24
 */
package com.huaixuan.network.web.action.timetask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.storage.InOutStatReportDao;
import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSales;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.domain.remote.InterfaceSync;
import com.huaixuan.network.biz.domain.remote.InterfaceUserGoods;
import com.huaixuan.network.biz.domain.storage.InOutStatReport;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceSync;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceUserGoods;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.MailTask;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserPoints;
import com.huaixuan.network.biz.domain.user.UserSales;
import com.huaixuan.network.biz.enums.EnumInterfaceName;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumPaipaiApi;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.agent.InterfaceUserTradeManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceSalesManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.PromationManager;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.InterfaceSyncManager;
import com.huaixuan.network.biz.service.remote.InterfaceUserGoodsManager;
import com.huaixuan.network.biz.service.remote.PaipaiInterfaceManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceSyncManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceUserGoodsManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.biz.service.trade.TicketRecordManager;
import com.huaixuan.network.biz.service.trade.TradeAgentManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.user.MailEngine;
import com.huaixuan.network.biz.service.user.MailtaskManager;
import com.huaixuan.network.biz.service.user.UserAgentManager;
import com.huaixuan.network.biz.service.user.UserInfoManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.biz.service.user.UserPointsManager;
import com.huaixuan.network.biz.service.user.UserSalesManager;
import com.huaixuan.network.common.util.ApiParameter;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.XmlUtil;
import com.huaixuan.network.common.util.remote.Billing_Deal_DetailsUtil;
import com.huaixuan.network.common.util.remote.TaobaoSyncUtils;
import com.huaixuan.network.common.util.remote.TaobaoUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;
import com.hundsun.network.melody.common.web.url.URLBroker;
import com.sun.net.httpserver.HttpServer;
import com.taobao.api.model.ItemAddRequest;
import com.taobao.api.model.Location;

/**
 * @author shengyong
 * @version $Id: TimetaskAction.java,v 0.1 2011-3-24 ����11:02:10 shengyong Exp $
 */
@Controller
public class TimetaskAction extends BaseAction {
    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    GoodsManager        goodsManager;

    @RequestMapping(value = "/timetask/time_task")
    public String timetaskInit(HttpServletRequest request, Model model) throws Exception {
    	/*
    	System.out.println("X-Forwarded-For: " + request.getHeader("X-Forwarded-For"));
    	System.out.println("Proxy-Client-IP: " + request.getHeader("Proxy-Client-IP"));
    	System.out.println("X-Real-IP: " + request.getHeader("Proxy-Client-IP"));
    	System.out.println("HTTP_X_FORWARDED_FOR: " + request.getHeader("HTTP_X_FORWARDED_FOR"));
    	System.out.println("HTTP_CLIENT_IP: " + request.getHeader("HTTP_CLIENT_IP"));
    	System.out.println("REMOTE_ADDR: " + request.getHeader("REMOTE_ADDR"));
    	*/

        return "/timetask/time_task";
    }

    /**
     * ��Ʒ�Զ��¼�
     */
    @RequestMapping(value = "/timetask/delisting")
    public String delisting(Model model) throws Exception {
        List<Goods> goodsList;
        try {
            goodsList = goodsManager.getNeedDelisting();
        } catch (Exception e) {
            log.error("", e);
            model.addAttribute("result", "fail");
            return "/timetask/time_task_result";
        }
        if (goodsList != null && goodsList.size() > 0) {
            for (Goods goods : goodsList) {
                final Long goodsId = goods.getId();

                goodsManager.deListingGoods(goodsId);

            }
        }

        model.addAttribute("result", "success");
        return "/timetask/time_task_result";
    }

    @Autowired
    TradeManager tradeManager;

    /**
     * �Զ�ȡ��
     */
    @RequestMapping(value = "/timetask/closeTrade")
    public String closeTrade(Model model) throws Exception {

        model.addAttribute("result", "success");
        try {
            //modified by chenyan 2009/09/09 �޸���15��Ϊ5��
            List<Trade> tradeList = tradeManager.getTradesPartByTimetask(
                EnumTradeStatus.WAIT_BUYER_PAY.getKey(), 5);
            log.info("---------------size=" + tradeList == null ? -1 : tradeList.size());
            if (tradeList != null && tradeList.size() > 0) {
                for (Trade trade : tradeList) {
                    String tradeId = String.valueOf(trade.getId());

                    tradeManager.updateTradeStatus(tradeId, EnumTradeStatus.TRADE_CLOSE.getKey());

                }
            }

        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    /**
     * �Զ�ȷ���ջ�
     */
    @RequestMapping(value = "/timetask/confirmTrade")
    public String confirmTrade(Model model) throws Exception {

        model.addAttribute("result", "success");
        try {
            //modified by chenyan 2009/10/28 �޸���30��Ϊ15��
            //shengyong 2009/11/05 15���Ϊ7��
            List<Trade> tradeList = tradeManager.getTradesPartByTimetask(
                EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey(), 7);
            log.info("---------------size=" + tradeList == null ? -1 : tradeList.size());
            if (tradeList != null && tradeList.size() > 0) {
                for (Trade trade : tradeList) {
                    final String tradeId = String.valueOf(trade.getId());

                    tradeManager.updateTradeStatus(tradeId, EnumTradeStatus.TRADE_FINISH.getKey());

                }
            }
        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    PromationManager promationManager;

    /**
     * �ײ͹����Զ�ȡ���
     */
    @RequestMapping(value = "/timetask/autoCanelFreeze")
    public String autoCanelFreeze(Model model) throws Exception {
        promationManager.autoCanelFreeze();
        model.addAttribute("result", "success");
        return "/timetask/time_task_result";
    }

    /**
     * ��ʱ��֧������������
     */
    @RequestMapping(value = "/timetask/autoAlipayReconciliation")
    public String autoAlipayReconciliation() throws Exception {
        //        log.info("timetask autoAlipayReconciliation start...");
        //        //����ʱ�� ȡ����ǰʱ���24Сʱ��ǰ��ʱ��
        //        Date date = new Date(new Date().getTime() - 1000 * 60 * 60 * 24);
        //        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�����ʽ������ʾ����
        //        String gmt_create_start = df.format(date) + " 00:00:00";
        //        String gmt_create_end = df.format(date) + " 23:59:59";
        //        /*
        //         * ��߶�ʱ����00��10���ߵ�ȡ��ʱ���������00��00��00�������23��59��59���
        //         * ��Ϊ֧����ȡ��ʱ�䲻�ܱ���С��24Сʱ�����Ծͳ���1�����
        //         * ���ڶ����������ȡ���Ѿ����˵���Ϣ �Ͷ�����ƥ�� ���ж��Ƿ�״̬�Ѿ��޸�
        //         * Ҫ��û���޸ľ��޸�״̬�����н��Ҳ�Ƚ�Ҫ�ǽ��Ծ�ע��û��ƥ��
        //         *
        //         * ���ڶ��� ��߻��д����Ĺ���δ����Ŀǰ�����޸�
        //         *
        //         * */
        //
        //        String url = Billing_DetailsUtil.BillingDetailsURL(gmt_create_start, gmt_create_end,
        //            EnumAccountsType.ONLINEPAYMENT.getKey());
        //        Result<List<Csvdate>> result = Billing_DetailsUtil.parseBillingDetailsXml(url);
        //        if (result.getResult() == 0) {
        //            for (Csvdate csvdate : result.getO()) {
        //                csvdate.setPayType("zfb");
        //                csvdate.setIsmatch(0);
        //                if (csvdate.getOutOrderNumber() != null
        //                    && csvdate.getOutOrderNumber().length() != 0) {
        //                    Trade trade = tradeManager.getTradeByTid(csvdate.getOutOrderNumber().trim());
        //                    if (trade != null && trade.getAmount() < csvdate.getIncome()) {
        //                        csvdate.setIsmatch(1);
        //                        csvdate.setMatch("�û�֧�����С�ڸö������");
        //                    } else if (trade != null
        //                               && trade.getStatus().equals(EnumTradeStatus.WAIT_BUYER_PAY.getKey())) {
        //                        trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
        //                        tradeManager.editTrade(trade);
        //                    }
        //                }
        //                csvdateManager.addCsvdate(csvdate);
        //            }
        //        }
        //        try {
        //            String url = Billing_DetailsUtil.BillingDetailsURL(gmt_create_start, gmt_create_end,
        //                EnumAccountsType.ONLINEPAYMENT.getKey());
        //            Result<List<Csvdate>> result = Billing_DetailsUtil.parseBillingDetailsXml(url);
        //            if (result.getResult() == 0) {
        //                for (Csvdate csvdate : result.getO()) {
        //                    csvdate.setPayType("zfb");
        //                    csvdate.setIsmatch(0);
        //                    csvdateManager.addCsvdate(csvdate);
        //                }
        //            }
        //        } catch (Exception e) {
        //            // TODO: handle exception
        //            log.info("timetask autoAlipaySaveDB error...");
        //        }
        //        try {
        //            Map<String, Object> condition = new HashMap<String, Object>();
        //            if (!StringUtil.isEmpty(gmt_create_start)) {
        //                condition.put("gmt_create_start", gmt_create_start);
        //            }
        //            if (!StringUtil.isEmpty(gmt_create_end)) {
        //                condition.put("gmt_create_end", gmt_create_end);
        //            }
        //
        //            List<Csvdate> csvdateList = csvdateManager.getCvsdateByConditionByTime(condition);
        //            if (csvdateList != null && csvdateList.size() != 0) {
        //                for (Csvdate csvdate : csvdateList) {
        //                    if (csvdate.getOutOrderNumber() != null
        //                        && csvdate.getOutOrderNumber().length() != 0) {
        //                        Trade trade = tradeManager
        //                            .getTradeByTid(csvdate.getOutOrderNumber().trim());
        //                        if (trade != null && trade.getAmount() < csvdate.getIncome()) {
        //                            csvdate.setIsmatch(1);
        //                            csvdate.setMatch("�û�֧�����С�ڸö������");
        //                            csvdateManager.editCsvdate(csvdate);
        //                        } else if (trade != null
        //                                   && trade.getStatus().equals(
        //                                       EnumTradeStatus.WAIT_BUYER_PAY.getKey())) {
        //                            trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
        //                            tradeManager.editTrade(trade);
        //                        }
        //                    }
        //
        //                }
        //            }
        //        } catch (Exception e) {
        //            // TODO: handle exception
        //            log.info("timetask autoUpdateTradeManager error...");
        //        }
        //        log.info("timetask autoAlipayReconciliation end...");
        return "/timetask/autoAlipayReconciliation";
    }

    @Autowired
    UserPointsManager userPointsManager;

    @Autowired
    UserManager       userManager;

    @Autowired
    ResourcesManager  resourcesManager;

    /**
     * �Զ��޸��û��ȼ�
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/timetask/autoUpdateUserGrade")
    public String autoUpdateUserGrade(Model model) throws Exception {

        model.addAttribute("result", "success");
        try {
            List<User> userList = userManager.getUsers(null);
            System.out.println("userList size  :  " + userList.size());
            log.info("userList size  :  " + userList.size());
            for (User users : userList) {
                User user = users;
                System.out.println("userList size  :  " + userList.size());
                UserPoints userPoints = userPointsManager.getUserPointsByUserId(user.getId()
                    .toString());
                if (null == userPoints) {
                    continue;
                }
                String values = resourcesManager.getResorcesValue("grade",
                    new Long(userPoints.getUsablePoints()));
                System.out.println("userRank  :  " + user.getId() + "  " + values);
                final Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("userRank", values);
                paramMap.put("id", user.getId());

                try {
                    userManager.updateGrade(paramMap);
                } catch (Exception e) {
                    log.error(e);
                    model.addAttribute("result", "fail");
                }

            }

        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    InOutStatReportDao inOutStatReportDao;

    /**
     * �Զ���ɽ����ͳ�Ʊ���
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/timetask/autoCreatInOutReport")
    public String autoCreatInOutReport(Model model) throws Exception {

        model.addAttribute("result", "success");
        try {
            final InOutStatReport inOutStatReport = new InOutStatReport();

            try {
                inOutStatReportDao.removeInOutStatReport(null);// ��ɾ��
                inOutStatReportDao.addInOutStatReport(inOutStatReport);
            } catch (Exception e) {
                log.error(e);
                model.addAttribute("result", "fail");
            }

        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    //    /**
    //     * �Զ����㷵����
    //     * @return
    //     * @throws Exception
    //     */
    //    @RequestMapping(value = "/timetask/autoUpdateReturnAccount")
    //    public String autoUpdateReturnAccount() throws Exception {
    //        log.info("timetask addReturnPointAccountByTime start..");
    //        try {
    //            this.getTaskExecutor().execute(new Thread() {
    //                public void run() {
    //                    try {
    ////                      returnPointAccountManager.addReturnPointAccountByTime();
    //                    } catch (Exception e) {
    //                        log.error(e);
    //                    }
    //                }
    //            });
    //        } catch (Exception ex) {
    //            log.error(ex);
    //        }
    //        log.info("timetask addReturnPointAccountByTime end..");
    //        return "/timetask/autoUpdateReturnAccount";
    //    }

    @Autowired
    TradeAgentManager   tradeAgentManager;

    @Autowired
    UserInfoManager     userInfoManager;

    @Autowired
    TicketRecordManager ticketRecordManager;

    //    @RequestMapping(value = "/timetask/autoCalculatePoint")
    //    public String autoCalculatePoint() {
    //
    //        final Calendar cal = Calendar.getInstance();
    //        List<AgentTrade> tradeAgentList = tradeAgentManager.countAgentAmount(cal);
    //        for (AgentTrade list : tradeAgentList) {
    //            log.info("---------------size=" + tradeAgentList == null ? -1 : tradeAgentList.size());
    //            final AgentTrade agentList = list;
    //
    //            Double handBackPoint = handBackPointManager.calculatePoint(agentList.getCatCode(),
    //                agentList.getAmt());
    //            Long point = Math.round(handBackPoint);
    //            userInfoManager.updateTicketLeft(agentList.getUserId(), point);
    //
    //            TicketRecord ticketRecord = new TicketRecord();
    //            ticketRecord.setUserId(agentList.getUserId());
    //            ticketRecord.setTicketAmount(point.intValue());//
    //            ticketRecord.setType(EnumTicketRecordType.SALE_ADD.getKey());
    //            ticketRecord.setMemo(EnumTicketRecordType.SALE_ADD.getValue());
    //            ticketRecordManager.addTicketRecord(ticketRecord);
    //
    //            tradeAgentManager.updateAgentIsReceive(cal, agentList.getUserId());
    //
    //        }
    //
    //        return "/timetask/autoCalculatePoint";
    //    }

    @Autowired
    MailtaskManager     mailtaskManager;

    @Autowired
    MailEngine          mailEngine;

    /**
     * �Զ����ʼ�
     * @return
     */
    @RequestMapping(value = "/timetask/autoSendMail")
    public String autoSendMail(Model model) {
        model.addAttribute("result", "success");
        try {

            Map parMap = new HashMap();
            parMap.put("isSend", "n");
            QueryPage query = mailtaskManager.getMailTaskList(parMap, 1, 100);
            List<MailTask> mailTaskList = (List<MailTask>) query.getItems();
            log.info("---------------size=" + mailTaskList == null ? -1 : mailTaskList.size());
            if (mailTaskList != null && mailTaskList.size() > 0) {
                for (MailTask mailtask : mailTaskList) {
                    mailtask.setIsSend("z");
                    mailtaskManager.updateMailTask(mailtask);
                    User user = userManager.getUser(mailtask.getUserId());
                    String context = mailtask.getContext().toString();
                    String[] temp = context.split("\\|");
                    final String email = user.getEmail();
                    final String subject = temp[0];
                    final String templateName = temp[1];
                    final Map map = new HashMap();
                    map.put("user", user);
                    map.put("url", temp[2]);
                    map.put("ctx", temp[3]);

                    try {
                        mailEngine.sendMessage(email, getText(subject), templateName, map);
                    } catch (MessagingException e) {
                        log.error(e.getMessage());
                        model.addAttribute("result", "fail");
                    }

                    mailtask.setIsSend("y");
                    mailtaskManager.updateMailTask(mailtask);
                }
            }

        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    public String getText(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);

        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    @Autowired
    GoodsInstanceManager goodsInstanceManager;

    @Autowired
    AdminService         adminService;

    /**
     * ���Ԥ���ʼ�
     * @param timeTaskGoods
     */
    @RequestMapping(value = "/timetask/autoStorageWarn")
    public String autoStorageWarn(Model model) {
        model.addAttribute("result", "success");
        GoodsInstance search = new GoodsInstance();
        search.setMin("min");
        List<GoodsInstance> goodsInstanceList = goodsInstanceManager
            .searchGoodsInstancesHasStorage(search);
        String idstrOne = "16093"; //�
        String idstrTwo = "10000120"; //֣��
        String idstrThree = "16594"; //���ط�
        String idstrFour = "16159"; //ףƼ
        String idstrFive = "10000080"; //��Ӣ��
        String idstrSix = "16158"; //�����
        String idstrSeven = "10000000"; //�ܱ�
        String idstrEight = "16086"; // ��ޱ
        String idstrNine = "16601"; //��ϼ
        String idstrTen = "16089";  //���÷
        String idstrEleven = "10000240";  //����΢
        String idstrTwelve = "10000841";  //½һ��
        String idstrThirteen = "10000840";  //����
        List<Admin> adminList = new ArrayList<Admin>();
        Admin adminOne = adminService.getAdminById(Long.parseLong(idstrOne));
        if (adminOne != null) {
            adminList.add(adminOne);
        }
        Admin adminTwo = adminService.getAdminById(Long.parseLong(idstrTwo));
        if (adminTwo != null) {
            adminList.add(adminTwo);
        }
        Admin adminThree = adminService.getAdminById(Long.parseLong(idstrThree));
        if (adminThree != null) {
            adminList.add(adminThree);
        }
        Admin adminFour = adminService.getAdminById(Long.parseLong(idstrFour));
        if (adminFour != null) {
            adminList.add(adminFour);
        }
        Admin adminFive = adminService.getAdminById(Long.parseLong(idstrFive));
        if (adminFive != null) {
            adminList.add(adminFive);
        }
        Admin adminSix = adminService.getAdminById(Long.parseLong(idstrSix));
        if (adminSix != null) {
            adminList.add(adminSix);
        }
        Admin adminSeven = adminService.getAdminById(Long.parseLong(idstrSeven));
        if (adminSeven != null) {
            adminList.add(adminSeven);
        }
        Admin adminEight = adminService.getAdminById(Long.parseLong(idstrEight));
        if (adminEight != null) {
            adminList.add(adminEight);
        }
        Admin adminNine = adminService.getAdminById(Long.parseLong(idstrNine));
        if (adminNine != null) {
            adminList.add(adminNine);
        }
        Admin adminTen = adminService.getAdminById(Long.parseLong(idstrTen));
        if(adminTen!=null){
            adminList.add(adminTen);
        }
        Admin adminEleven = adminService.getAdminById(Long.parseLong(idstrEleven));
        if(adminEleven!=null){
            adminList.add(adminEleven);
        }
        Admin adminTwelve = adminService.getAdminById(Long.parseLong(idstrTwelve));
        if(adminTwelve!=null){
            adminList.add(adminTwelve);
        }
        Admin adminThirteen = adminService.getAdminById(Long.parseLong(idstrThirteen));
        if(adminThirteen!=null){
            adminList.add(adminThirteen);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd");
        log.info("---------------size=" + goodsInstanceList == null ? -1 : 1);
        try {
            if (goodsInstanceList != null && goodsInstanceList.size() > 0 && adminList != null
                && adminList.size() > 0) {
                for (Admin temp : adminList) {
                    final String email = temp.getEmail();
                    final String subject = "���Ԥ��";
                    final String templateName = "storage-goods-warn.vm";
                    final Map map = new HashMap();
                    map.put("goodsInstanceList", goodsInstanceList);
                    map.put("admin", temp);
                    map.put("datenow", df.format(new Date()));

                    try {
                        mailEngine.sendMessage(email, subject, createDatasource(map),
                            templateName, map);
                    } catch (MessagingException e) {
                        log.error(e.getMessage());
                        model.addAttribute("result", "fail");
                    }

                }
            }
        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    private DataSource createDatasource(Map model) {
        OutputStream out = new ByteArrayOutputStream();
        createExcel(out, (List<GoodsInstance>) model.get("goodsInstanceList"));
        byte[] buf = ((ByteArrayOutputStream) out).toByteArray();
        InputStream is = new ByteArrayInputStream(buf);
        DataSource dataSource = null;
        try {
            dataSource = new ByteArrayDataSource(is, "application/vnd.ms-excel");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return dataSource;
    }

    private Boolean createExcel(OutputStream os, List<GoodsInstance> listToString) {
        List<String[]> resultList = listToString(listToString);
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(os);
            WritableSheet sheet = wwb.createSheet("new Sheet", 0);
            for (int row = 0; row < resultList.size(); row++) {
                String[] rs = resultList.get(row);
                for (int col = 0; col < rs.length; col++) {
                    if (null != rs[col]) {
                        Label label = new jxl.write.Label(col, row, rs[col]);
                        sheet.addCell(label);
                    }
                }
            }
            wwb.write();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;

        } catch (RowsExceededException e) {
            log.error(e.getMessage());
            return false;
        } catch (WriteException e) {
            log.error(e.getMessage());
            return false;
        } finally {
            if (null != wwb) {
                try {
                    wwb.close();
                } catch (WriteException e) {
                    log.error(e.getMessage());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private List<String[]> listToString(List<GoodsInstance> resultList) {
        List<String[]> goodsExportList = new ArrayList<String[]>();
        String[] title = { "��Ʒ���", "����", "��λ", "���ÿ��", "ʵ�ʿ��", "��С�����" };
        goodsExportList.add(title);
        for (GoodsInstance i : resultList) {
            String[] item = { i.getInstanceName(), i.getCode(), i.getGoodsUnit(),
                    i.getExistNum().toString(), i.getStorageNum().toString(),
                    i.getMinNum().toString() };
            goodsExportList.add(item);
        }
        return goodsExportList;
    }

    @Autowired
    StorageDao storageDao;

    /**
     * �����������
     * @param timeTaskGoods
     */
    @RequestMapping(value = "/timetask/autoStorageAgeExport")
    public String autoStorageAgeExport(Model model) {
        model.addAttribute("result", "success");
        List<Storage> goodsInstanceList = storageDao.getGoodsInstanceList();
        try {
            String today = DateUtil.dtSimpleFormat(new Date());

            if (goodsInstanceList != null && goodsInstanceList.size() > 0) {
                for (Storage gl : goodsInstanceList) {
                    List<StockAge> stockAgeSupplier = goodsInstanceManager
                        .getStockSupplierByInstanceId(gl.getGoodsInstanceId());

                    for (StockAge sa : stockAgeSupplier) {

                        int halfMonth = goodsInstanceManager.getStockNumByInstanceId(
                            gl.getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(),
                            sa.getStorType(), 15, 0);
                        int oneMonth = goodsInstanceManager.getStockNumByInstanceId(
                            gl.getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(),
                            sa.getStorType(), 30, 0);
                        int oneHalfMonth = goodsInstanceManager.getStockNumByInstanceId(
                            gl.getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(),
                            sa.getStorType(), 45, 0);
                        int twoMonth = goodsInstanceManager.getStockNumByInstanceId(
                            gl.getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(),
                            sa.getStorType(), 60, 0);
                        int threeMonth = goodsInstanceManager.getStockNumByInstanceId(
                            gl.getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(),
                            sa.getStorType(), 90, 0);
                        int aboveMonth = goodsInstanceManager.getStockNumByInstanceId(
                            gl.getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(),
                            sa.getStorType(), 0, 0);
                        int afterOnemonth = goodsInstanceManager.getStockNumByInstanceId(gl
                            .getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(), sa
                            .getStorType(), 30, 0);
                        int onemonthThreemonty = goodsInstanceManager.getStockNumByInstanceId(gl
                            .getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(), sa
                            .getStorType(), 90, 30);
                        int threemontySixmonty = goodsInstanceManager.getStockNumByInstanceId(gl
                            .getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(), sa
                            .getStorType(), 180, 90);
                        int sixmontyTwelvemonth = goodsInstanceManager.getStockNumByInstanceId(gl
                            .getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(), sa
                            .getStorType(), 360, 180);
                        int aboveTwelvemonth = goodsInstanceManager.getStockNumByInstanceId(gl
                            .getGoodsInstanceId(), sa.getSupplierId(), sa.getLocId(), sa
                            .getStorType(), 0, 360);
                        aboveMonth = aboveMonth - threeMonth;
                        sa.setAboveMonth(aboveMonth);
                        threeMonth = threeMonth - twoMonth;
                        sa.setThreeMonth(threeMonth);
                        twoMonth = twoMonth - oneHalfMonth;
                        sa.setTwoMonth(twoMonth);
                        oneHalfMonth = oneHalfMonth - oneMonth;
                        sa.setOneHalfMonth(oneHalfMonth);
                        oneMonth = oneMonth - halfMonth;
                        sa.setOneMonth(oneMonth);
                        sa.setHalfMonth(halfMonth);
                        sa.setAfterOnemonth(afterOnemonth);
                        sa.setOnemonthThreemonty(onemonthThreemonty);
                        sa.setThreemontySixmonty(threemontySixmonty);
                        sa.setSixmontyTwelvemonth(sixmontyTwelvemonth);
                        sa.setAboveTwelvemonth(aboveTwelvemonth);
                        sa.setCountDate(today);

                        int returnType = goodsInstanceManager.updateStockAgeBysa(sa);
                        if (returnType == 0) {
                            goodsInstanceManager.insertStockAgeBySa(sa);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    /**
     * ���ڽ��㶩��������
     * @param timeTaskGoods
     */
    @RequestMapping(value = "/timetask/autoCreatPeriod")
    public String autoCreatPeriod(Model model) {
        model.addAttribute("result", "success");
        try {

            try {
                userManager.updateUserPeriodAmount();
            } catch (Exception e) {
                log.error(e);
                model.addAttribute("result", "fail");
            }

        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    UserSalesManager userSalesManager;

    @Autowired
    UserAgentManager userAgentManager;

    /**
     * �ͻ�/������Ա�������б���
     * @return
     */
    @RequestMapping(value = "/timetask/autoCustomerReport")
    public String autoCustomerReport(Model model) {
        model.addAttribute("result", "success");
        try {
            Map parMap = new HashMap();
            Date today = new Date();
            Date yesterday = DateUtil.getDate(today, -1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataStart = df.format(yesterday);
            String dataEnd = df.format(yesterday);
            parMap.put("dataStart", dataStart);
            parMap.put("dataEnd", dataEnd);

            Date userDate = DateUtil.strToDate(dataStart, "yyyy-MM-dd");

            Map newMap = new HashMap();
            newMap.put("searchDay", dataStart);
            int count = userSalesManager.getSearchDayByMap(newMap);
            if (count > 0) {
                userSalesManager.deleteUserSalesByParameterMap(newMap);
            }
            List<User> allUserList = userManager.getUsers(null);
            for (User user : allUserList) {
                double refundAmount = 0;
                UserSales userSales = new UserSales();
                userSales.setUserId(user.getId());
                AgentInfo agentInfo = userAgentManager.getAgentInfoByUserId(user.getId());
                if (agentInfo != null && agentInfo.getAgent_manager_id() != null) {
                    userSales.setAdminId(agentInfo.getAgent_manager_id());
                }
                userSales.setOptTime(userDate);
                parMap.put("userId", user.getId());
                UserSales temp = userSalesManager.getUserSalesInfo(parMap);
                refundAmount = userSalesManager.getUserRefundInfo(parMap);
                userSales.setTradeNum(temp.getTradeNum());
                userSales.setSalesAmount(temp.getSalesAmount());
                userSales.setRefundAmount(refundAmount);
                userSalesManager.addUserSales(userSales);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    GoodsInstanceSalesManager goodsInstanceSalesManager;

    /**
     * ��Ʒ�������б���
     * @return
     */
    @RequestMapping(value = "/timetask/autoGoodsInstanceReport")
    public String autoGoodsInstanceReport(Model model) {
        model.addAttribute("result", "success");
        try {
            Map parMap = new HashMap();
            Date today = new Date();
            Date yesterday = DateUtil.getDate(today, -1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dataStart = df.format(yesterday);
            String dataEnd = df.format(yesterday);
            parMap.put("dataStart", dataStart);
            parMap.put("dataEnd", dataEnd);

            Date userDate = DateUtil.strToDate(dataStart, "yyyy-MM-dd");

            Map newMap = new HashMap();
            newMap.put("searchDay", dataStart);
            int count = goodsInstanceSalesManager.getSearchDayByMap(newMap);
            if (count > 0) {
                goodsInstanceSalesManager.deleteProductByParameterMap(newMap);
            }
            List<GoodsInstance> allgoodsInstanceList = goodsInstanceManager.getGoodsInstances();
            for (GoodsInstance gi : allgoodsInstanceList) {
                double salesAmount = 0;
                double refundAmount = 0;
                GoodsInstanceSales goodsInstanceSales = new GoodsInstanceSales();
                goodsInstanceSales.setGoodsInstanceId(gi.getId());
                goodsInstanceSales
                    .setGoodsInstanceName(StringUtil.isNotBlank(gi.getAttrDesc()) ? gi
                        .getInstanceName() + ";" + gi.getAttrDesc() : gi.getInstanceName());
                goodsInstanceSales.setGoodsInstanceCode(gi.getCode());
                goodsInstanceSales.setOptTime(userDate);
                parMap.put("goodsInstanceId", gi.getId());
                salesAmount = goodsInstanceSalesManager.getSalesAmountInfo(parMap);
                refundAmount = goodsInstanceSalesManager.getRefundAmountInfo(parMap);
                goodsInstanceSales.setSalesAmount(salesAmount);
                goodsInstanceSales.setRefundAmount(refundAmount);
                goodsInstanceSalesManager.addGoodsInstanceSales(goodsInstanceSales);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    InterfaceApplyManager     interfaceApplyManager;
    @Autowired
    InterfaceUserTradeManager interfaceUserTradeManager;

    /**
     * ͬ�����ĵĶ���
     * @return
     */
    @RequestMapping(value = "/timetask/autoSyncPaipaiTrade")
    public String autoSyncPaipaiTrade(Model model) {
        model.addAttribute("result", "success");
        log.info("timetask autoSyncPaipaiTrade start..");
        try {
            Map parMap = new HashMap();
            parMap.put("status", "pass");
            parMap.put("type", "paipai");
            List<InterfaceApply> applyList = interfaceApplyManager.getListByMap(parMap);// ��ȡ��������������б�
            if (applyList != null && applyList.size() > 0) {
                Date nowDate = new Date();
                Resources resources = resourcesManager.getResourcesByTypeAndName("paipai",
                    "depFirstId");
                TreeMap<String, String> signParams = new TreeMap<String, String>();
                for (InterfaceApply apply : applyList) {
                    // ��ȡ�û���Ϣ
                    //User user = userManager.getUser(apply.getUserId());
                    if (StringUtil.isBlank(apply.getParamOne())
                        || StringUtil.isBlank(apply.getParamTwo())
                        || StringUtil.isBlank(apply.getParamThree())
                        || StringUtil.isBlank(apply.getParamFour())) {
                        continue;
                    }
                    signParams.put("uin", apply.getParamOne());
                    signParams.put("token", apply.getParamTwo());
                    signParams.put("spid", apply.getParamThree());
                    signParams.put("seckey", apply.getParamFour());
                    signParams.put("sellerUin", apply.getParamOne());

                    String timeBegin = DateUtil.getFormatDate(apply.getGmtSync(),
                        Billing_Deal_DetailsUtil.DATE_FOMAT_STR);
                    String timeEnd = DateUtil.getFormatDate(nowDate,
                        Billing_Deal_DetailsUtil.DATE_FOMAT_STR);
                    String ItemUrl = Billing_Deal_DetailsUtil.CreateDealListUrl(signParams, "", "",
                        "UPDATE", timeBegin, timeEnd,
                        Billing_Deal_DetailsUtil.DS_WAIT_SELLER_DELIVERY, "", "", "1", "", "", "");

                    log.info("Interface Trade URL:" + ItemUrl);
                    Map resultMap = Billing_Deal_DetailsUtil.parseInterfaceTradeListXml(ItemUrl);//�����б����
                    String errorCode = (String) resultMap.get("errorCode");
                    if (StringUtil.isNotBlank(errorCode) && "0".equals(errorCode)) {
                        //�����б���Ϣ
                        List<Trade> tradeList = (List<Trade>) resultMap.get("tradeList");
                        if (tradeList != null && tradeList.size() > 0) {
                            String reason;
                            Boolean isError;
                            InterfaceUserTrade userTrade = null;
                            for (Trade trade : tradeList) {
                                isError = false;
                                reason = "";
                                // ��ֹ�ظ����붩��
                                InterfaceUserTrade obj = interfaceUserTradeManager
                                    .getInterfaceUserTradeByDealId(trade.getDealCode());
                                if (obj != null) {
                                    continue;
                                }
                                userTrade = new InterfaceUserTrade();
                                List<Order> orderList = trade.getOrderList();
                                if (orderList != null && orderList.size() > 0) {
                                    for (Order order : orderList) {
                                        if (StringUtil.isNotBlank(order.getCode())) {
                                            // �жϹ��������Ƿ���ڿ��ÿ������
                                            GoodsInstance goodsIns = goodsInstanceManager
                                                .getInstanceByCode(order.getCode());
                                            if (goodsIns == null) {//���û��ѯ����Ʒ��Ϣ�����ѯ��Ʒ������Ϣ
                                                Goods goods = goodsManager.getGoodsByCode(order
                                                    .getCode());//��order.getCode()����Goods.goodsSn��ѯGoodsInstance
                                                if (goods != null) {
                                                    List<GoodsInstance> goodsInsList = goodsInstanceManager
                                                        .findGoodsInstances(goods.getId());
                                                    for (GoodsInstance goodsInsTemp : goodsInsList) {//�����������������ֻ����һ��goodsInstance
                                                        order.setCode(goodsInsTemp.getCode());
                                                        goodsIns = goodsInsTemp;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (goodsIns != null) {
                                                Long existNum = storageManager
                                                    .sumStorageByGoodsInstanceId(goodsIns.getId(),
                                                        Long.parseLong(resources.getValue()),
                                                        "exist");
                                                if (existNum < order.getGoodsNumber()) {
                                                    isError = true;
                                                    reason += "��Ʒ����Ϊ��[" + order.getCode()
                                                              + "]��Ʒ���ÿ������������";
                                                }
                                            } else {
                                                isError = true;
                                                reason += "��Ʒ����Ϊ��[" + order.getCode()
                                                          + "]��Ʒ�ڲֿ��в����ڣ�";
                                            }
                                        } else {
                                            isError = true;
                                            reason += "�����е���Ʒ������Ʒ����Ϊ�գ�";
                                        }
                                    }
                                } else {
                                    isError = true;
                                    reason = "�˶�������Ʒ��Ϣ��";
                                }
                                userTrade.setGmtPaipaiCreate(trade.getCreateTime());
                                userTrade.setPaipaiTradeId(trade.getDealCode());
                                userTrade.setGmtPaipaiPaied(trade.getDealPayTime());
                                userTrade.setUserId(apply.getUserId());
                                userTrade.setReason(reason);
                                interfaceUserTradeManager.addInterfaceUserTrade(userTrade);//��ӵ�����ͬ������

                                // �����������Ʒ�Ŀ��ÿ������������������ɹرյĶ���
                                if (isError) {
                                    trade.setStatus(EnumTradeStatus.TRADE_CLOSE.getKey());
                                } else {
                                    trade.setStatus(EnumTradeStatus.WAIT_BUYER_PAY.getKey());
                                }
                                // ����Ƿ����ڽ�����Ƿ���Ҫ��Ʊ 2011-02-11 and by fanyj
                                /*if(user != null){
                                    trade.setInvoice(user.getInvoice());
                                    if("y".equals(user.getIsPeriodPay())){
                                        trade.setExpressPayment(EnumExpressDistPayment.PERIOD_PAY.getKey());
                                        trade.setGmtPeriodPayEnd(user.getGmtPeriodPayEnd());
                                        trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());//���ڽ��㶩��״̬Ϊ�ȴ��̼ҷ���
                                    }else{
                                        trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
                                    }
                                }*/
                                tradeManager.savePaipaiTrade(Long.parseLong(resources.getValue()),
                                    apply.getUserId(), trade, apply.getShopType());
                            }
                        }
                        // �޸����һ��ͬ��ʱ�䣨����ͬ���ӿڲ����������ͬ��ʱ�䣩
                        apply.setGmtSync(nowDate);
                        interfaceApplyManager.editGmtSync(apply);// 
                    }
                }
            }
        } catch (Exception ex) {
            log.info("timetask autoSyncPaipaiTrade Exception:" + ex);
            model.addAttribute("result", "fail");
        }
        log.info("timetask autoSyncPaipaiTrade end..");
        return "/timetask/time_task_result";
    }

    @Autowired
    InterfaceSyncManager      interfaceSyncManager;

    @Autowired
    InterfaceUserGoodsManager interfaceUserGoodsManager;

    @Autowired
    AvailableStockDao         availableStockDao;

    @Autowired
    PaipaiInterfaceManager    paipaiInterfaceManager;

    @Autowired
    private UploadUtil        uploadUtil;

    private @Value("${file.upload.dir}")
    String                    upload;
    
	private @Value("${liangpin99.url}")
	String liangpin99url;

    /**
     * ͬ��������Ʒ����
     * @return
     */
    @RequestMapping(value = "/timetask/autoPaipaiPublicGoods")
    public String autoPaipaiPublicGoods(Model model) {
        model.addAttribute("result", "success");
        try {
            //            Page page = new Page();
            //            page.setPageSize(120);
            Map parMap = new HashMap();
            parMap.put("interfaceType", EnumInterfaceName.PAIPAIPUBLISHGOODS.getKey());
            parMap.put("status", "n");
            QueryPage query = interfaceSyncManager.getInterfaceSyncListQuery(parMap, 1, 120);
            if (query != null) {
                List<InterfaceSync> interfaceSynclist = (List<InterfaceSync>) query.getItems();
                log.info("---------------size=" + interfaceSynclist == null ? -1
                    : interfaceSynclist.size());
                String imageService = liangpin99url + "/imageService";
                String ctx = liangpin99url;
                if (interfaceSynclist != null && interfaceSynclist.size() > 0) {
                    Resources resources = resourcesManager.getResourcesByTypeAndName("paipai",
                        "depFirstId");
                    for (InterfaceSync interfaceSync : interfaceSynclist) {
                        //�ҷ�����������һ����¼
                        Map paramter = new HashMap();
                        paramter.put("userId", interfaceSync.getUserId());
                        paramter.put("goodsId", interfaceSync.getGoodsId());
                        paramter.put("type", EnumInterfaceType.PAIPAI.getKey());
                        paramter.put("status", "z");
                        final InterfaceUserGoods interfaceUserGoods = interfaceUserGoodsManager
                            .getInterfaceUserGoods(paramter);

                        if (interfaceUserGoods == null) {
                            log.error("���ķ�����Ʒ������ϵ������");
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            continue;
                        }

                        InterfaceApply interfaceApply = interfaceApplyManager
                            .getInterfaceApplyByUserId(interfaceSync.getUserId(),
                                EnumInterfaceType.PAIPAI.getKey());
                        if (interfaceApply == null) {
                            log.error("���������û�������");
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            interfaceUserGoods.setStatus("n");
                            interfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                            continue;
                        }
                        Goods goods = goodsManager.getGoods(interfaceSync.getGoodsId());
                        if (goods == null || goods.getIsPaipai().equals("n")) {
                            log.error("����������Ʒ�����ڻ��߲������Ĺ�����Ʒ");
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            interfaceUserGoods.setStatus("n");
                            interfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                            continue;
                        }

                        List<GoodsInstance> goodsInstanceList = goodsInstanceManager
                            .findGoodsInstances(interfaceSync.getGoodsId());

                        if (goodsInstanceList == null || goodsInstanceList.size() == 0) {
                            log.error("���ķ�����Ʒ��Ʒ������");
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            interfaceUserGoods.setStatus("n");
                            interfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                            continue;
                        }

                        Long allNum = 0L;
                        for (GoodsInstance goodsInstance : goodsInstanceList) {
                            AvailableStock availableStock = availableStockDao.getAvailableStock(
                                goodsInstance.getId(), Long.parseLong(resources.getValue()));
                            if (availableStock == null || availableStock.getGoodsNumber() == null
                                || availableStock.getGoodsNumber() == 0) {
                                allNum += 0;
                            } else {
                                allNum += availableStock.getGoodsNumber();
                            }
                        }

                        if (allNum == 0) {
                            log.error("����Ʒȫ����Ʒ����Ϊ0");
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            interfaceUserGoods.setStatus("n");
                            interfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                            continue;
                        }

                        Map<String, String> noSignPram = new HashMap<String, String>();
                        noSignPram.put("uin", interfaceApply.getParamOne());
                        noSignPram.put("itemLocalCode", goods.getGoodsSn());
                        Document doc = paipaiInterfaceManager.paipaiClientByXml(null,
                            EnumPaipaiApi.getItem, false, noSignPram, null, "xml");
                        if (doc != null) {
                            String errorCode = XmlUtil.getElementValueByTagName(doc,
                                EnumPaipaiApi.getItem.name(), "errorCode");
                            if (errorCode.equals("0")) {
                                log.error("��Ʒ�Ѿ������Ĺ���");
                                interfaceSync.setStatus("f");
                                interfaceSyncManager.updateInterfaceSync(interfaceSync);
                                interfaceUserGoods.setStatus("y");
                                interfaceUserGoodsManager
                                    .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                                continue;
                            }
                        }

                        Long userId = interfaceSync.getUserId();
                        EnumPaipaiApi api = EnumPaipaiApi.addItem;
                        ApiParameter parameter = new ApiParameter();
                        ApiParameter parameterTwo = new ApiParameter(); //�ϴ�ͼƬ�ӿ��õĲ���
                        EnumPaipaiApi apipic = EnumPaipaiApi.modifyItemPic; //�ϴ�ͼƬ�ӿ��õĲ���
                        parameter.addStringParam("sellerUin", interfaceApply.getParamOne());
                        parameter.addStringParam("itemName", goods.getTitle());
                        if (goods.getPaipaiAttr() != null) {
                            parameter.addStringParam("attr", goods.getPaipaiAttr());
                        }
                        parameter.addStringParam("classId", goods.getPaipaiClassId());
                        parameter.addStringParam("validDuration", "1209600");
                        parameter.addStringParam("itemState", "IS_IN_STORE");
                        if (StringUtils.isNotBlank(goods.getGoodsDesc())) {
                            String goodsDesc = getPaipaiGoodsDesc(goods, imageService, ctx);
                            parameter.addStringParam("detailInfo", goodsDesc);
                        } else {
                            parameter.addStringParam("detailInfo", goods.getTitle());
                        }
                        parameter.addStringParam("sellerPayFreight", "0");
                        parameter.addStringParam("mailPrice",
                            Math.round(DoubleUtil.mul(interfaceApply.getPostNormal(), 100)));
                        parameter.addStringParam("expressPrice",
                            Math.round(DoubleUtil.mul(interfaceApply.getPostExpress(), 100)));
                        parameter.addStringParam("emsPrice",
                            Math.round(DoubleUtil.mul(interfaceApply.getPostEms(), 100)));
                        parameter.addStringParam("itemLocalCode", goods.getGoodsSn());

                        boolean noPass = false;
                        for (GoodsInstance goodsInstance : goodsInstanceList) {
                            String paipaiInstanceDesc = null;
                            if (goodsInstance.getAttrDesc() != null) {
                                paipaiInstanceDesc = goodsInstance.getAttrDesc().replace("=", ":")
                                    .replace(";", "|")
                                    .substring(0, goodsInstance.getAttrDesc().length() - 1);
                            } else {
                                paipaiInstanceDesc = " : ";
                            }
                            try {
                                AvailableStock availableStock = availableStockDao
                                    .getAvailableStock(goodsInstance.getId(),
                                        Long.parseLong(resources.getValue()));
                                if (availableStock != null
                                    && availableStock.getGoodsNumber() != null
                                    && availableStock.getGoodsNumber() > 0) {
                                    parameter.addStringParam(
                                        "stockInfo",
                                        Math.round(DoubleUtil.mul(goods.getGoodsPrice(), 100))
                                                + "," + availableStock.getGoodsNumber() + ","
                                                + paipaiInstanceDesc + ","
                                                + goodsInstance.getCode());
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                noPass = true;
                                break;
                            }
                        }

                        if (noPass) {
                            log.error("��Ʒ�����Ϣ�쳣");
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            interfaceUserGoods.setStatus("n");
                            interfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                            continue;
                        }

                        if (goods.getImgLarge() != null) {
                                parameterTwo.addStringParam("sellerUin", interfaceApply.getParamOne());
                                parameterTwo.addStringParam("itemLocalCode", goods.getGoodsSn());
                                parameterTwo.addFileParam("pic", upload + Constants.FILE_SEP + goods.getImgLarge());
                        }

                        try {
                            boolean isSuccess = paipaiInterfaceManager.paipaiClientByTimeTask(
                                parameter, api, userId, "json");
                            if (isSuccess) {
                                interfaceUserGoods.setStatus("y");
                                interfaceUserGoodsManager
                                    .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                                interfaceSync.setStatus("y");
                                interfaceSyncManager.updateInterfaceSync(interfaceSync);
                                if (parameterTwo != null) {
                                    boolean isPicSuccess = paipaiInterfaceManager
                                        .paipaiClientByTimeTask(parameterTwo, apipic, userId,
                                            "json");
                                }
                            } else {
                                interfaceUserGoods.setStatus("n");
                                interfaceUserGoodsManager
                                    .updateInterfaceUserGoodsStatus(interfaceUserGoods);
                                interfaceSync.setStatus("f");
                                interfaceSyncManager.updateInterfaceSync(interfaceSync);
                            }
                        } catch (Exception e) {
                            model.addAttribute("result", "fail");
                            log.error(e);
                            continue;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            model.addAttribute("result", "fail");
            log.error(ex);
            return "/timetask/time_task_result";
        }

        return "/timetask/time_task_result";
    }

    public String getPaipaiGoodsDesc(Goods goods, String imageService, String ctx) {

        StringBuilder sb = new StringBuilder();

        StringBuffer s = new StringBuffer();
        if (goods != null && StringUtils.isNotBlank(goods.getAttrDesc())) {
            String[] decs = null;
            if (StringUtil.isBlank(goods.getAttrDesc())) {
                decs = goods.getAttrDesc().split(";");
            } else {
                decs = goods.getAttrDesc().replaceAll("\"", "'").replaceAll(",", "��")
                    .replaceAll("\r", "\t").replaceAll("\n", "").split(";");
            }

            if (decs.length > 0) {
                s.append("<img src='http://v6.freep.cn/3tb_1002211104169vm6287225.jpg' /><br>");
                for (int i = 0; i < decs.length; i++) {
                    s.append(decs[i]).append("<br>");
                }
            }
            s.append("<img src='http://v6.freep.cn/3tb_100221110417vnkq287225.jpg' /><br>");
        }
        sb.append("<p><img src='")
            .append(ctx)
            .append("/remoting/isStockout?goodsSn=")
            .append(goods.getGoodsSn())
            .append("&reqUrl=")
            .append(ctx)
            .append("' /></p>")
            .append(
                goods.getGoodsWeight() != 0.000 ? ("��Ʒ����(ǧ��):") + goods.getGoodsWeight() + "<br>"
                    : "")
            .append(
                s.toString()
                        + goods.getGoodsDesc().replaceAll("/imageService", imageService)
                            .replaceAll("\"", "'").replaceAll(",", "��").replaceAll("\r", "\t")
                            .replaceAll("\n", ""));

        return sb.toString();
    }

    /**
     * ͬ�����Ŀ������
     * @return
     */
    @RequestMapping(value = "/timetask/autoPaipaiSyncNum")
    public String autoPaipaiSyncNum(Model model) {

        model.addAttribute("result", "success");
        try {

            Map parMap = new HashMap();
            parMap.put("interfaceType", EnumInterfaceName.PAIPAISTOCKSYNC.getKey());
            parMap.put("status", "n");
            QueryPage query = this.interfaceSyncManager.getInterfaceSyncListQuery(parMap, 1, 120);
            if (query == null) {
                model.addAttribute("result", "fail");
                return "/timetask/time_task_result";
            }
            List<InterfaceSync> interfaceSynclist = (List<InterfaceSync>) query.getItems();
            log.info("---------------size=" + interfaceSynclist == null ? -1 : interfaceSynclist
                .size());
            if (interfaceSynclist != null && interfaceSynclist.size() > 0) {
                Resources resources = resourcesManager.getResourcesByTypeAndName("paipai",
                    "depFirstId");
                for (InterfaceSync interfaceSync : interfaceSynclist) {
                    InterfaceApply interfaceApply = interfaceApplyManager
                        .getInterfaceApplyByUserId(interfaceSync.getUserId(),
                            EnumInterfaceType.PAIPAI.getKey());
                    if (interfaceApply == null) {
                        log.error("���������û�������");
                        interfaceSync.setStatus("f");
                        interfaceSyncManager.updateInterfaceSync(interfaceSync);
                        continue;
                    }

                    Goods goods = goodsManager.getGoods(interfaceSync.getGoodsId());
                    if (goods == null) {
                        log.error("����������Ʒ������");
                        interfaceSync.setStatus("f");
                        interfaceSyncManager.updateInterfaceSync(interfaceSync);
                        continue;
                    }

                    GoodsInstance goodsInstance = goodsInstanceManager.getInstance(interfaceSync
                        .getGoodsInstanceId());
                    if (goodsInstance == null) {
                        log.error("�������Ĳ�Ʒ������");
                        interfaceSync.setStatus("f");
                        interfaceSyncManager.updateInterfaceSync(interfaceSync);
                        continue;
                    }

                    AvailableStock availableStock = availableStockDao.getAvailableStock(
                        goodsInstance.getId(), Long.parseLong(resources.getValue()));
                    Long userId = interfaceSync.getUserId();
                    EnumPaipaiApi api = EnumPaipaiApi.modifyItemStock;
                    ApiParameter parameter = new ApiParameter();
                    parameter.addStringParam("sellerUin", interfaceApply.getParamOne());
                    parameter.addStringParam("itemLocalCode", goods.getGoodsSn());
                    parameter.addStringParam("stockLocalCode", goodsInstance.getCode());
                    if (availableStock != null && availableStock.getGoodsNumber() != null
                        && availableStock.getGoodsNumber() > 0) {
                        parameter.addStringParam("stockCount", availableStock.getGoodsNumber());
                    } else {
                        parameter.addStringParam("stockCount", 0);
                    }

                    try {
                        boolean isSuccess = paipaiInterfaceManager.paipaiClientByTimeTask(
                            parameter, api, userId, "json");
                        if (isSuccess) {
                            interfaceSync.setStatus("y");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                        } else {
                            interfaceSync.setStatus("f");
                            interfaceSyncManager.updateInterfaceSync(interfaceSync);
                        }
                    } catch (Exception e) {
                        model.addAttribute("result", "fail");
                        log.error(e.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    TaobaoInterfaceApplyManager taobaoInterfaceApplyManager;

    /* begin add by shenzh Oct 26, 2010 ˵������ʱ���ֶ�ִ�� */
    /**
     * ͬ���Ա��Ķ���
     * @return
     */
    @RequestMapping(value = "/timetask/autoSyncTaobaoTrade")
    public String autoSyncTaobaoTrade(Model model) {
        model.addAttribute("result", "success");
        logger.info("timetask autoSyncTaobaoOrders start..");
        Map parMap = new HashMap();
        parMap.put("type", "taobao");
        parMap.put("status", "pass");
        List<TaobaoApply> applyList = taobaoInterfaceApplyManager.getListByMap(parMap); // ��ȡ��������������б�

        //ͬ����ѯ���׼�¼����ֹʱ��
        DateTime dateTime = new DateTime();
        Date startDate = dateTime.minusDays(15).toDate();//��ѯ���15��Ľ��׼�¼
        Date endDate = dateTime.toDate();

        String status = "WAIT_SELLER_SEND_GOODS";//�ȴ����ҷ���,��:����Ѹ���
        try {
            if (CollectionUtils.isNotEmpty(applyList)) {
                for (TaobaoApply apply : applyList) {
                    String app_key = apply.getParamOne();
                    String app_secret = apply.getParamTwo();
                    String nick = apply.getParamThree();
                    //��ҳ����
                    int pageSize = 100;
                    int curPageNo = 1;
                    int total_results = 0;
                    Map<String, Object> retMap = new HashMap<String, Object>();
                    do {
                        retMap = TaobaoUtils.getTradeListFromTaobaoByApply(app_key, app_secret,
                            nick, startDate, endDate, status, pageSize, curPageNo++);
                        if (retMap == null)
                            break;
                        total_results = (Integer) retMap.get("total_results");
                        List<Trade> tradeList = (List<Trade>) retMap.get("tradeListFromTaobao");
                        if (CollectionUtils.isNotEmpty(tradeList)) {
                            saveTrade(tradeList, apply.getUserId(), apply);//���?����Ϣ
                        }
                    } while (total_results > pageSize * (curPageNo - 1)); //ѭ��������û�����
                    // �޸����һ��ͬ��ʱ�䣨����ͬ��ʱ�䣩
                    apply.setGmtSync(endDate);
                    /* begin modify by shenzh Nov 10, 2010 ˵�����쳣���� */
                    try {
                        taobaoInterfaceApplyManager.editGmtSync(apply);//
                    } catch (Exception e) {
                        logger.error("����ͬ����ʱ�������..." + e.getMessage() + "User:"
                                     + apply.getAccount());
                    }
                    /* end by shenzh Nov 10, 2010 */
                }
            }
        } catch (Exception e) {
            logger.error("����ͬ����ʱ�������..." + e.getMessage());
            model.addAttribute("result", "fail");
        } finally {
            logger.info("timetask autoSyncTaobaoOrders end..");
        }
        return "/timetask/time_task_result";
    }

    /* Nov 12, 2010 |3:59:56 PM |shenzh |�޸ĵ����� ���Ӳ��� apply ������Ҫ�����Ա��ӿڲ���ұ�ע*/
    public void saveTrade(List<Trade> tradeList, Long userId, TaobaoApply apply) {
        //ȡ����Դ�������õ�һ���ֿ�ID
        Resources resources = resourcesManager.getResourcesByTypeAndName("taobao", "depFirstId");
        Long depFirstId = Long.parseLong(resources.getValue());
        
        start: //�ڲ�Orders��������쳣ֱ������������������һ��Trade
        for (Trade trade : tradeList) {
            boolean isError = false;
            /* begin add by shenzh Nov 3, 2010 ˵���� �����û�а�ؿ��е���Ʒ�����ڱ������*/
            boolean ignoreTrade = true;
            /* end by shenzh Nov 3, 2010 */
            /* begin add by shenzh Nov 4, 2010 ˵���� ��ʾ�ö����ڹر�״̬���Ƿ�ɻָ�*/
            boolean canResume = false;
            /* end by shenzh Nov 4, 2010 */
            String reason = null;
            InterfaceUserTrade userTrade = null;
            try {
                // �ж϶����Ƿ��Ѿ�����
                userTrade = interfaceUserTradeManager.getInterfaceUserTradeByDealId(trade
                    .getDealCode());
            } catch (Exception e) {
                logger.error("����Ա��ɹ�����ѯ����..." + e.getMessage());
            }

            if (userTrade == null) {
                userTrade = new InterfaceUserTrade();
                List<Order> orderList = trade.getOrderList();
                if (CollectionUtils.isNotEmpty(orderList)) {
                    for (Order order : orderList) {
                        if (StringUtil.isNotBlank(order.getCode())) {
                            try {
                                // �жϹ��������Ƿ���ڿ��ÿ������
                                GoodsInstance goodsIns = goodsInstanceManager
                                    .getInstanceByCode(order.getCode());
                                if (goodsIns == null) {//���û��ѯ����Ʒ��Ϣ�����ѯ��Ʒ������Ϣ
                                    Goods goods = goodsManager.getGoodsByCode(order.getCode());//��order.getCode()����Goods.goodsSn��ѯGoodsInstance
                                    if (goods != null) {
                                        List<GoodsInstance> goodsInsList = goodsInstanceManager
                                            .findGoodsInstances(goods.getId());
                                        for (GoodsInstance goodsInsTemp : goodsInsList) {//�����������������ֻ����һ��goodsInstance
                                            order.setCode(goodsInsTemp.getCode());
                                            goodsIns = goodsInsTemp;
                                            break;
                                        }
                                    }
                                }
                                if (goodsIns != null) {
                                    Long existNum = storageManager.sumStorageByGoodsInstanceId(
                                        goodsIns.getId(), depFirstId, "exist");
                                    if (existNum < order.getGoodsNumber()) {
                                        /* begin modify by shenzh Nov 5, 2010 ˵������Ʒ���ÿ���������㣬�����Ѿ����ִ����Ҳ��ɻָ���״̬����ɻָ�*/
                                        if (!(isError && !canResume))
                                            canResume = true;
                                        isError = true;
                                        /* end by shenzh Nov 5, 2010 */
                                        reason += "��Ʒ����Ϊ��[" + order.getCode() + "]��Ʒ���ÿ������������";
                                    }
                                    /* begin add by shenzh Nov 3, 2010 ˵���� ���ش�����Ʒ������Ҫ��ɱ��ض���,������Ҫ*/
                                    ignoreTrade = false;
                                    /* end by shenzh Nov 3, 2010 */
                                } else {
                                    /* begin add by shenzh Nov 5, 2010 ˵����֮ǰ����Ϊ�ɻָ��������˴������Բ��ɻָ�*/
                                    if (isError && canResume)
                                        canResume = false;
                                    /* end by shenzh Nov 5, 2010 */
                                    isError = true;
                                    reason += "��Ʒ����Ϊ��[" + order.getCode() + "]��Ʒ�ڲֿ��в����ڣ�";
                                }
                            } catch (Exception e) {
                                logger.error("����֤�������ʱ�����쳣..." + e.getMessage() + "���Ա���������ʱ��:"
                                             + trade.getCreateTime() + "���Ա�����ID:"
                                             + trade.getDealCode() + "���Ա���������ʱ��:"
                                             + trade.getDealPayTime() + "��userId:" + userId
                                             + "��reason:" + reason);
                                continue start;
                            }
                        } else {
                            /* begin add by shenzh Nov 5, 2010 ˵����֮ǰ����Ϊ�ɻָ��������˴������Բ��ɻָ�*/
                            if (isError && canResume)
                                canResume = false;
                            /* end by shenzh Nov 5, 2010 */
                            isError = true;
                            reason += "�����е���Ʒ����[stockLocalCode]�����ڣ�";
                        }
                    }
                } else {
                    isError = true;
                    reason = "�˶�������Ʒ��Ϣ��";
                }

                userTrade.setGmtPaipaiCreate(trade.getCreateTime());
                userTrade.setPaipaiTradeId(trade.getDealCode());
                userTrade.setGmtPaipaiPaied(trade.getDealPayTime());
                userTrade.setUserId(userId);
                userTrade.setReason(reason);
                /* begin modify by shenzh Nov 10, 2010 ˵���� ��׽�쳣���ܼ��������ȥ�Ķ���*/
                try {
                    interfaceUserTradeManager.addInterfaceUserTrade(userTrade);//��ӵ�����ͬ������
                } catch (Exception e) {
                    logger.error("��interface_user_trade���в������ʱ�����쳣..." + e.getMessage()
                                 + "���Ա���������ʱ��:" + trade.getCreateTime() + "���Ա�����ID:"
                                 + trade.getDealCode() + "���Ա���������ʱ��:" + trade.getDealPayTime()
                                 + "��userId:" + userId + "��reason:" + reason);
                    continue;
                }
                /* end by shenzh Nov 10, 2010 */
                // �����������Ʒ�Ŀ��ÿ������������������ɹرյĶ���
                if (isError) {
                    trade.setStatus(EnumTradeStatus.TRADE_CLOSE.getKey());
                    /* begin add|modify|delete by shenzh Nov 4, 2010 ˵���� �����ö����Ĺر�״̬���Ƿ���Ϊȱ��*/
                    if (canResume) {
                        trade.setStockoutStatus("y");
                    } else {
                        trade.setStockoutStatus("n");
                    }
                    /* end by shenzh Nov 4, 2010 */
                    logger.error("error message..." + reason);
                } else {
                    /* begin modify by shenzh Oct 29, 2010 ˵�������ȴ���Ϊ�ȴ����״̬ */
                    trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
                    /* end by shenzh Oct 29, 2010 */
                }
                /* begin add by shenzh Nov 4, 2010 ˵���� */
                canResume = false;
                /* end by shenzh Nov 4, 2010 */
                /* begin modify by shenzh Nov 3, 2010 ˵���� �ж��Ƿ���Ҫ�ڱ�����ɶ���*/
                if (!ignoreTrade) {
                    /* begin modify by shenzh Nov 10, 2010 ˵���� ��׽�쳣���ܼ��������ȥ�Ķ���*/
                    try {
                        /* begin add by shenzh Nov 12, 2010 ˵����������ұ�ע */
                        String app_key = apply.getParamOne();
                        String app_secret = apply.getParamTwo();
                        String nick = apply.getParamThree();
                        trade = TaobaoUtils.addBuyerNoteToTrade(trade, userTrade, app_key,
                            app_secret, nick);
                        trade = TaobaoUtils.addInvoiceNameToTrade(trade, userTrade, app_key,
                            app_secret, nick);
                        /* end by shenzh Nov 12, 2010 */
                        tradeManager.saveTaobaoTrade(depFirstId, userId,apply.getOwnExpressId(),
                            apply.getInterfaceExpressCode(), trade);
                    } catch (Exception e) {
                        logger.error("����ɱ��ض���ʱ�����쳣..." + e.getMessage() + "���Ա���������ʱ��:"
                                     + trade.getCreateTime() + "���Ա�����ID:" + trade.getDealCode()
                                     + "���Ա���������ʱ��:" + trade.getDealPayTime() + "��userId:" + userId);
                    }
                    /* end by shenzh Nov 10, 2010 */
                }
                ignoreTrade = true;
                /* end by shenzh Nov 3, 2010 */
            }
        }

    }

    @Autowired
    RegionManager                   regionManager;
    @Autowired
    TaobaoInterfaceUserGoodsManager taobaoInterfaceUserGoodsManager;
    @Autowired
    TaobaoInterfaceSyncManager      taobaoInterfaceSyncManager;
    @Autowired
    TaobaoInterfaceManager          taobaoInterfaceManager;

    /**
     * ͬ���Ա���Ʒ����
     * @return
     */
    @RequestMapping(value = "/timetask/autoTaobaoPublicGoods")
    public String autoTaobaoPublicGoods(Model model) {
        model.addAttribute("result", "success");
        try {

            Map parMap = new HashMap();
            parMap.put("interfaceType", EnumInterfaceName.TAOBAOPUBLISHGOODS.getKey());
            parMap.put("status", "n");
            QueryPage query = taobaoInterfaceSyncManager.getInterfaceSyncListQuery(parMap, 1, 120);
            if (query == null) {
                model.addAttribute("result", "fail");
                return "/timetask/time_task_result";
            }
            List<TaobaoInterfaceSync> interfaceSynclist = (List<TaobaoInterfaceSync>) query
                .getItems();
            log.info("---------------size=" + interfaceSynclist == null ? -1 : interfaceSynclist
                .size());
            String imageService = liangpin99url + "/imageService";
            String ctx = liangpin99url;
            if (interfaceSynclist != null && interfaceSynclist.size() > 0) {
                Resources resources = resourcesManager.getResourcesByTypeAndName("taobao",
                    "depFirstId");
                for (TaobaoInterfaceSync interfaceSync : interfaceSynclist) {
                    //�ҷ�����������һ����¼
                    Map paramter = new HashMap();
                    paramter.put("userId", interfaceSync.getUserId());
                    paramter.put("goodsId", interfaceSync.getGoodsId());
                    paramter.put("type", EnumInterfaceType.TAOBAO.getKey());
                    paramter.put("status", "z");
                    final TaobaoInterfaceUserGoods taobaoInterfaceUserGoods = taobaoInterfaceUserGoodsManager
                        .getInterfaceUserGoods(paramter);

                    if (taobaoInterfaceUserGoods == null) {
                        log.error("�Ա�������Ʒ������ϵ������");
                        interfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        continue;
                    }

                    TaobaoApply taobaoApply = taobaoInterfaceApplyManager
                        .getInterfaceApplyByUserId(interfaceSync.getUserId(),
                            EnumInterfaceType.TAOBAO.getKey());
                    if (taobaoApply == null) {
                        log.error("�����Ա��û�������");
                        interfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        taobaoInterfaceUserGoods.setStatus("n");
                        taobaoInterfaceUserGoodsManager
                            .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                        continue;
                    }
                    Goods goods = goodsManager.getGoods(interfaceSync.getGoodsId());
                    if (goods == null || "n".equals(goods.getIsTaobao())) {
                        log.error("�����Ա���Ʒ�����ڻ��߲����Ա�������Ʒ");
                        interfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        taobaoInterfaceUserGoods.setStatus("n");
                        taobaoInterfaceUserGoodsManager
                            .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                        continue;
                    }

                    List<GoodsInstance> goodsInstanceList = goodsInstanceManager
                        .findGoodsInstances(interfaceSync.getGoodsId());

                    if (goodsInstanceList == null || goodsInstanceList.size() == 0) {
                        log.error("�Ա�������Ʒ��Ʒ������");
                        interfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        taobaoInterfaceUserGoods.setStatus("n");
                        taobaoInterfaceUserGoodsManager
                            .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                        continue;
                    }

                    Long allNum = 0L;
                    // ����鵽������쳣���
                    try {
                        for (GoodsInstance goodsInstance : goodsInstanceList) {
                            AvailableStock availableStock = availableStockDao.getAvailableStock(
                                goodsInstance.getId(), Long.parseLong(resources.getValue()));
                            if (availableStock == null || availableStock.getGoodsNumber() == null
                                || availableStock.getGoodsNumber() == 0) {
                                allNum += 0;
                            } else {
                                allNum += availableStock.getGoodsNumber();
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        continue;
                    }

                    if (allNum == 0) {
                        log.error("����Ʒȫ����Ʒ����Ϊ0");
                        interfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        taobaoInterfaceUserGoods.setStatus("n");
                        taobaoInterfaceUserGoodsManager
                            .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                        continue;
                    }

                    Long userId = interfaceSync.getUserId();
                    boolean isSandBox = TaobaoUtils.IS_SANDBOX;
                    String numIidGet = String.valueOf(taobaoInterfaceUserGoods.getTaobaoItemId());
                    boolean isGet = taobaoInterfaceManager.taobaoItemGet(numIidGet, userId,
                        isSandBox);
                    if (isGet) {
                        log.error("��Ʒ�Ѿ����Ա�����");
                        interfaceSync.setStatus("f");
                        interfaceSync.setReason("��Ʒ�Ѿ����Ա�����");
                        taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        taobaoInterfaceUserGoods.setStatus("y");
                        taobaoInterfaceUserGoodsManager
                            .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                        continue;
                    }

                    ItemAddRequest req = new ItemAddRequest();
                    req.setApproveStatus("instock");
                    if (goods.getTaobaoClassId() != null)
                        req.setCid(goods.getTaobaoClassId().toString());
                    req.setNum(allNum.intValue());
                    req.setPrice(String.valueOf(goods.getGoodsPrice()));
                    req.setType("fixed");
                    req.setStuffStatus("new");
                    req.setTitle(goods.getTitle());

                    if (StringUtils.isNotBlank(goods.getGoodsDesc())) {
                        String goodsDesc = getTaobaoGoodsDesc(goods, imageService, ctx);
                        req.setDesc(goodsDesc);
                    } else {
                        req.setDesc(goods.getTitle());
                    }

                    Location location = new Location();
                    String state = "";
                    String city = "";
                    //ȡһ���ֿ��ʡ��
                    List<Region> regionList = regionManager.getRegionByFirstDepositoryId(resources
                        .getValue());
                    for (Region r : regionList) {
                        if (r.getRegionType() == 2)
                            state = r.getRegionName();
                        if (r.getRegionType() == 3)
                            city = r.getRegionName();
                    }
                    //to do ����
                    //���ʡ����"ʡ����"��β����ȥ��
                    int stateLength = state.length();
                    String stateLast = state.substring(stateLength - 1, stateLength);
                    if ("ʡ".equals(stateLast))
                        state = state.substring(0, stateLength - 1);

                    int cityLength = city.length();
                    String cityLast = city.substring(cityLength - 1, cityLength);
                    if ("��".equals(cityLast))
                        city = city.substring(0, cityLength - 1);

                    location.setState(state);
                    location.setCity(city);
                    req.setLocation(location);

                    //Ĭ����ҳе��˷�
                    req.setFreightPayer("buyer");

                    //�˷�ģ��id���˷ѵ�����
                    Long postId = taobaoApply.getPostId();
                    if (postId != null && !"".equals(postId))
                        req.setPostageId(postId.toString());
                    else {
                        double postNormalDouble = taobaoApply.getPostNormal();
                        String postFee = String.valueOf(postNormalDouble);
                        if (postNormalDouble < 0.01 || postFee == null || "".equals(postFee))
                            postFee = "10";
                        req.setPostFee(postFee);

                        double postExpressDouble = taobaoApply.getPostExpress();
                        String expressFee = String.valueOf(postExpressDouble);
                        if (postExpressDouble < 0.01 || expressFee == null || "".equals(expressFee))
                            expressFee = "10";
                        req.setExpressFee(expressFee);

                        String emsFee = String.valueOf(taobaoApply.getPostEms());
                        req.setEmsFee(emsFee);
                    }

                    // ��Ʊ���ó��з�Ʊ
                    req.setHasInvoice(true);
                    req.setHasWarranty(true);

                    //start - �����Ա���Ʒ����������
                    //���������Ʒ���ж����Ʒ��������������
                    StringBuffer skuOuterIds = new StringBuffer();
                    StringBuffer skuQuantities = new StringBuffer();
                    StringBuffer skuPrices = new StringBuffer();
                    StringBuffer skuProperties = new StringBuffer();
                    int count = 0;
                    //�����ж��ڲ�Ʒ�༭����û�༭�Ա�����
                    boolean isEditSku = false;
                    //������ÿ��鵽��������
                    try {
                        for (GoodsInstance goodsInstance : goodsInstanceList) {
                            AvailableStock availableStock = availableStockDao.getAvailableStock(
                                goodsInstance.getId(), Long.parseLong(resources.getValue()));
                            String InstanceTaobaoSkuProperty = goodsInstance.getTaobaoSkuProperty();
                            //��������Ʒ��sku����û�����ã��Ͳ�����������Ʒ��sku��
                            if (InstanceTaobaoSkuProperty == null
                                || "".equals(InstanceTaobaoSkuProperty))
                                continue;
                            //���������жϣ���Ʒ���sku������ֵ����Ҳ����˵���ڲ�Ʒ�༭���б༭�Ա�������������isEditSkuΪ��
                            isEditSku = true;
                            //������Ʒ�Ŀ��ÿ����ڣ����Ҵ��ڵ���0,���Է����Ա�
                            if (availableStock != null
                                && availableStock.getGoodsNumber() != null
                                && (availableStock.getGoodsNumber() > 0 || availableStock
                                    .getGoodsNumber() == 0)) {
                                // ��Ʒ���ÿ��
                                if (count != 0)
                                    skuQuantities = skuQuantities.append(",");
                                skuQuantities = skuQuantities.append(availableStock
                                    .getGoodsNumber().toString());

                                // ��Ʒ����
                                if (count != 0)
                                    skuOuterIds = skuOuterIds.append(",");
                                skuOuterIds = skuOuterIds.append(goodsInstance.getCode());

                                // ��Ʒ�۸�
                                if (count != 0)
                                    skuPrices = skuPrices.append(",");
                                skuPrices = skuPrices.append(goods.getGoodsPrice());

                                // ��Ʒ��Ӧ���Ա�sku������
                                if (count != 0)
                                    skuProperties = skuProperties.append(",");
                                skuProperties = skuProperties.append(goodsInstance
                                    .getTaobaoSkuProperty());

                                count++;
                            }
                        }
                        //������˵Ĳ�Ʒ�����1������û���ڲ�Ʒ�༭�б༭�Ա���������Ʒ���������
                        if (goodsInstanceList.size() > 1 && isEditSku == false) {
                            interfaceSync.setReason("������Ʒ�����1,�뵽��Ʒ�༭�������Ա���Ʒ");
                            taobaoInterfaceUserGoods.setStatus("n");
                            taobaoInterfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                            interfaceSync.setStatus("f");
                            taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                            continue;
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        continue;
                    }
                    req.setSkuOuterIds(skuOuterIds.toString());
                    req.setSkuQuantities(skuQuantities.toString());
                    req.setSkuPrices(skuPrices.toString());
                    req.setSkuProperties(skuProperties.toString());
                    //end - �����Ա���Ʒ����������

                    if (goods.getTaobaoAttr() != null)
                        req.setProps(goods.getTaobaoAttr());
                    if (goods.getTaobaoInputPids() != null)
                        req.setInputPids(goods.getTaobaoInputPids());
                    if (goods.getTaobaoInputStr() != null)
                        req.setInputStr(goods.getTaobaoInputStr());

                    req.setOuterId(goods.getGoodsSn());
                    //���������ʱ��Ϊ150
                    req.setAuctionPoint("150");

                    // ��Ʒ��ͼ
                    if (goods.getImgLarge() != null) {
                        File file = new File(upload + Constants.FILE_SEP + goods.getImgLarge());
                        if (file.exists())
                            req.setImage(file);
                    }

                    try {
                        String sessionKey = TaobaoUtils.getTestSession(taobaoApply.getParamThree(),
                            taobaoApply.getParamOne());
                        Map<String, String> returnMap = taobaoInterfaceManager.taobaoGoodsAdd(req,
                            userId, sessionKey, isSandBox);
                        String returnStatus = returnMap.get("state");
                        if (returnStatus.equals("true")) {
                            String numIid = returnMap.get("numIid");
                            taobaoInterfaceUserGoods.setStatus("y");
                            taobaoInterfaceUserGoods.setTaobaoItemId(Long.parseLong(numIid));
                            taobaoInterfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                            interfaceSync.setStatus("y");
                            taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        } else {
                            String subMsg = returnMap.get("sub_msg");
                            if (subMsg != "")
                                interfaceSync.setReason(subMsg);
                            taobaoInterfaceUserGoods.setStatus("n");
                            taobaoInterfaceUserGoodsManager
                                .updateInterfaceUserGoodsStatus(taobaoInterfaceUserGoods);
                            interfaceSync.setStatus("f");
                            taobaoInterfaceSyncManager.updateInterfaceSync(interfaceSync);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        model.addAttribute("result", "fail");
                        continue;
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex);
            model.addAttribute("result", "fail");
        }

        return "/timetask/time_task_result";
    }

    public String getTaobaoGoodsDesc(Goods goods, String imageService, String ctx) {

        StringBuilder sb = new StringBuilder();

        StringBuffer s = new StringBuffer();
        if (goods != null && StringUtils.isNotBlank(goods.getAttrDesc())) {
            String[] decs = null;
            if (StringUtil.isBlank(goods.getAttrDesc())) {
                decs = goods.getAttrDesc().split(";");
            } else {
                decs = goods.getAttrDesc().replaceAll("\"", "'").replaceAll(",", "��")
                    .replaceAll("\r", "\t").replaceAll("\n", "").split(";");
            }

            if (decs.length > 0) {
                s.append("<img src='http://v6.freep.cn/3tb_1002211104169vm6287225.jpg' /><br>");
                for (int i = 0; i < decs.length; i++) {
                    s.append(decs[i]).append("<br>");
                }
            }
            s.append("<img src='http://v6.freep.cn/3tb_100221110417vnkq287225.jpg' /><br>");
        }
        sb.append("<p><img src='")
            .append(ctx)
            .append("/remoting/isStockout?goodsSn=")
            .append(goods.getGoodsSn())
            .append("&reqUrl=")
            .append(ctx)
            .append("' /></p>")
            .append(
                goods.getGoodsWeight() != 0.000 ? ("��Ʒ����(ǧ��):") + goods.getGoodsWeight() + "<br>"
                    : "")
            .append(
                s.toString()
                        + goods.getGoodsDesc().replaceAll("/imageService", imageService)
                            .replaceAll("\"", "'").replaceAll(",", "��").replaceAll("\r", "\t")
                            .replaceAll("\n", ""));

        return sb.toString();
    }

    /**
     * ͬ���Ա��������
     * @return
     */
    @RequestMapping(value = "/timetask/autoTaobaoSyncNum")
    public String autoTaobaoSyncNum(Model model) {
        model.addAttribute("result", "success");
        try {

            Map<String, String> parMap = new HashMap<String, String>();
            parMap.put("interfaceType", EnumInterfaceName.TAOBAOSTOCKSYNC.getKey());
            parMap.put("status", "n");
            QueryPage query = this.taobaoInterfaceSyncManager.getInterfaceSyncListQuery(parMap, 1,
                120);
            if (query == null) {
                model.addAttribute("result", "fail");
                return "/timetask/time_task_result";
            }
            List<TaobaoInterfaceSync> taobaoInterfaceSynclist = (List<TaobaoInterfaceSync>) query
                .getItems();
            log.info("---------------size=" + taobaoInterfaceSynclist == null ? -1
                : taobaoInterfaceSynclist.size());
            if (taobaoInterfaceSynclist != null && taobaoInterfaceSynclist.size() > 0) {
                Resources resources = resourcesManager.getResourcesByTypeAndName("taobao",
                    "depFirstId");
                for (TaobaoInterfaceSync taobaoInterfaceSync : taobaoInterfaceSynclist) {
                    TaobaoApply taobaoApply = taobaoInterfaceApplyManager
                        .getInterfaceApplyByUserId(taobaoInterfaceSync.getUserId(),
                            EnumInterfaceType.TAOBAO.getKey());
                    if (taobaoApply == null) {
                        log.error("�����Ա��û�������");
                        taobaoInterfaceSync.setReason("�����Ա��û�������");
                        taobaoInterfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(taobaoInterfaceSync);
                        continue;
                    }

                    Goods goods = goodsManager.getGoods(taobaoInterfaceSync.getGoodsId());
                    if (goods == null) {
                        log.error("�����Ա���Ʒ������");
                        taobaoInterfaceSync.setReason("�����Ա���Ʒ������");
                        taobaoInterfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(taobaoInterfaceSync);
                        continue;
                    }

                    GoodsInstance goodsInstance = goodsInstanceManager
                        .getInstanceTaobaoSkuPropById(taobaoInterfaceSync.getGoodsInstanceId());
                    if (goodsInstance == null) {
                        log.error("�����Ա���Ʒ������");
                        taobaoInterfaceSync.setReason("�����Ա���Ʒ������");
                        taobaoInterfaceSync.setStatus("f");
                        taobaoInterfaceSyncManager.updateInterfaceSync(taobaoInterfaceSync);
                        continue;
                    }

                    //��Ʒ���ÿ����
                    AvailableStock availableStock = new AvailableStock();
                    try {
                        availableStock = availableStockDao.getAvailableStock(goodsInstance.getId(),
                            Long.parseLong(resources.getValue()));
                    } catch (Exception e) {
                        log.error("��Ʒ���ÿ���ѯ�쳣" + e.getMessage());
                        continue;
                    }
                    //�Ա����ԣ�����ȷ��һ�����۵��Ա���Ʒ����������˵�Ǽ���Ʒ��
                    String properties = goodsInstance.getTaobaoSkuProperty();

                    Map<String, Object> parMap2 = new HashMap<String, Object>();
                    parMap2.put("userId", taobaoInterfaceSync.getUserId());
                    parMap2.put("goodsId", taobaoInterfaceSync.getGoodsId());
                    parMap2.put("status", "y");
                    parMap2.put("type", EnumInterfaceType.TAOBAO.getKey());
                    //��Ʒ�����û���Ϣ
                    TaobaoInterfaceUserGoods interfaceUserGoods = taobaoInterfaceUserGoodsManager
                        .getInterfaceUserGoods(parMap2);
                    if (interfaceUserGoods == null)
                        continue;
                    //������Ϣ
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    boolean result = true;

                    if (availableStock != null && availableStock.getGoodsNumber() != null
                        && availableStock.getGoodsNumber() > 0) {
                        resultMap = TaobaoSyncUtils.taobaoClientByTimeTask(availableStock
                            .getGoodsNumber().intValue(), String.valueOf(interfaceUserGoods
                            .getTaobaoItemId()), properties, taobaoApply.getParamOne(), taobaoApply
                            .getParamTwo(), taobaoApply.getParamThree(), TaobaoUtils.IS_SANDBOX);
                    } else {
                        resultMap = TaobaoSyncUtils.taobaoClientByTimeTask(0,
                            String.valueOf(interfaceUserGoods.getTaobaoItemId()), properties,
                            taobaoApply.getParamOne(), taobaoApply.getParamTwo(),
                            taobaoApply.getParamThree(), TaobaoUtils.IS_SANDBOX);
                    }

                    String isSuccess = String.valueOf(resultMap.get("state"));
                    String msg = "";
                    if ("false".equalsIgnoreCase(isSuccess)) {

                        result = false;
                        msg = "�û�[ID��" + taobaoApply.getUserId() + "]���ͬ��ʧ�ܣ�"
                              + String.valueOf(resultMap.get("msg"));
                        log.error("�û�[ID��" + taobaoApply.getUserId() + "]���ͬ��ʧ�ܣ�"
                                  + String.valueOf(resultMap.get("msg")));
                    } else if ("true".equalsIgnoreCase(isSuccess)) {
                        msg = "�û�[ID��" + taobaoApply.getUserId() + "]���ͬ���ɹ�!";
                        log.info("�û�[ID��" + taobaoApply.getUserId() + "]���ͬ���ɹ�!");
                    } else {

                        result = false;
                        msg = "�û�[ID��" + taobaoApply.getUserId() + "]���ͬ��ʱ������Ԥ��֮������!";
                        log.info("�û�[ID��" + taobaoApply.getUserId() + "]���ͬ��ʱ������Ԥ��֮������!");
                    }

                    try {
                        if (result) {
                            taobaoInterfaceSync.setStatus("y");
                            taobaoInterfaceSync.setReason(msg);
                            taobaoInterfaceSyncManager.updateInterfaceSync(taobaoInterfaceSync);
                        } else {
                            taobaoInterfaceSync.setStatus("f");
                            taobaoInterfaceSync.setReason(msg);
                            taobaoInterfaceSyncManager.updateInterfaceSync(taobaoInterfaceSync);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        model.addAttribute("result", "fail");
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    @Autowired
    RefundManager  refundManager;

    @Autowired
    OrderManager   orderManager;

    @Autowired
    StorageManager storageManager;

    /**
     * �Զ��ָ��رյĶ���
     * @return
     */
    @RequestMapping(value = "/timetask/autoResumeTrade")
    public String autoResumeTrade(Model model) {

        model.addAttribute("result", "success");
        String message;
        try {
            //��ѯ�Ķ�����ΧΪ���15����ɵ�
            String gmtCreateStart = DateUtil.getDiffDate(new Date(), -15);
            String gmtCreateEnd = DateUtil.getDateToString(new Date());

            Map<String, String> parMap = new HashMap<String, String>();
            parMap.put("stockoutStatus", "y");
            parMap.put("tradeType", "3");//�Ա�����--Ŀǰֻ�����Ա��Ķ���
            parMap.put("status", "trade_close");
            parMap.put("gmtCreateStart", gmtCreateStart);
            parMap.put("gmtCreateEnd", gmtCreateEnd);
            TradeListQuery query = new TradeListQuery();
            query.setStockoutStatus("y");
            query.setTradeType(new Byte("3"));
            query.setStatus("trade_close");
            query.setGmtCreateStart(gmtCreateStart);
            query.setGmtCreateEnd(gmtCreateEnd);
            List<Trade> tradeList = tradeManager.getTradesByParameterMap(query);
            log.info("---------------size=" + tradeList == null ? -1 : tradeList.size());
            start: //����ǰ������Ϣ�е�Order�б����в����������orderʱ���ڲ�ѭ��ֱ�����������������һ��trade����
            for (Trade trade : tradeList) {
                Refund refund = refundManager.getRefundByOrder(trade.getTid());
                if (refund != null) {
                    if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())
                        || EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())
                        || EnumRefundStatus.Seller_Refuse_Refund.getKey()
                            .equals(refund.getStatus())) {
                        //do nothing
                    } else {
                        message = "[Trade-tid:" + trade.getTid() + "]�Ѿ��˿���ָܻ����ָ����ɹ�";
                        log.info(message);
                        continue;
                    }
                }
                List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
                if (orderList != null) {
                    //�����жϣ�������������Ʒ�Ŀ��ÿ�治��ʱ��������ָ�
                    for (Order temp : orderList) {
                        Long existNum = storageManager.sumStorageByGoodsInstanceId(
                            temp.getGoodsInstanceId(), trade.getDepFirstId(), "exist");
                        if (existNum < temp.getGoodsNumber()) {
                            message = "[Trade-tid:" + trade.getTid() + "]������ ["
                                      + temp.getGoodsTitle() + "] �Ŀ��ÿ�治��, ���ܽ��лָ�!";
                            log.info(message);
                            continue start;//����ǰ�������µĴ��?����ʼ�¸����׵Ĵ���
                        }
                    }
                    for (Order temp : orderList) {
                        // ���¿��ÿ�� Ҫ���ϲֿ�ID
                        goodsInstanceManager.updateAmountForTwo(temp.getGoodsInstanceId(),
                            temp.getGoodsId(), (0 - temp.getGoodsNumber()), trade.getDepFirstId(),
                            true);
                        // ������������
                        //2018-7-26注释（已售库存，在早上恢复库存时统计）
//                        goodsManager.updateSaleNumberById(temp.getGoodsId(), temp.getGoodsNumber());
                    }
                } else {
                    message = "[Trade-tid:" + trade.getTid() + "]��������Ϊ��!";
                    log.info(message);
                }

                try {
                    //���״̬���ȴ��̼ҷ���
                    trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
                    /* begin add shenzh Nov 23, 2010 ˵���� ͬʱ���ȱ��״̬*/
                    trade.setStockoutStatus("n");
                    /* end by shenzh Nov 23, 2010 */
                    trade.setGmtModify(new Date());
                    tradeManager.editTrade(trade);
                    message = "[Trade-tid:" + trade.getTid() + "]�ɹ��ָ�!";
                    log.info(message);
                } catch (Exception e) {
                    log.error("[Trade-tid:" + trade.getTid() + "]the Trade resume fail!", e);
                    message = "�ָ�ʧ��!";
                    log.debug(message);
                    model.addAttribute("result", "fail");
                }
            }
        } catch (Exception ex) {
            log.error("ȱ�������������Ԥ��֮����쳣��" + ex);
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

    /* end by shenzh Nov 9, 2010 */
    /**
     * �޸��������б����
     * @return
     */
    @RequestMapping(value = "/timetask/backSaleReport")
    public String backSaleReport(Model model) {

        model.addAttribute("result", "success");
        log.info("timetask backReport start..");
        try {
            //����������
            userSalesManager.deleteUserSalesByParameterMap(null);

            //��ԭ��� 60��
            Date today = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 1; i < 60; i++) {
                Date getDate = DateUtil.getDate(today, -i);
                String dataStart = df.format(getDate);

                Map parMap = new HashMap();
                parMap.put("dataStart", dataStart);
                parMap.put("dataEnd", dataStart);
                List<User> allUserList = userManager.getUsers(null);
                for (User user : allUserList) {
                    Date userDate = DateUtil.strToDate(dataStart, "yyyy-MM-dd");
                    double refundAmount = 0;
                    UserSales userSales = new UserSales();
                    userSales.setUserId(user.getId());
                    AgentInfo agentInfo = userAgentManager.getAgentInfoByUserId(user.getId());
                    if (agentInfo != null && agentInfo.getAgent_manager_id() != null) {
                        userSales.setAdminId(agentInfo.getAgent_manager_id());
                    }
                    userSales.setOptTime(userDate);
                    parMap.put("userId", user.getId());
                    UserSales temp = userSalesManager.getUserSalesInfo(parMap);
                    refundAmount = userSalesManager.getUserRefundInfo(parMap);
                    userSales.setTradeNum(temp.getTradeNum());
                    userSales.setSalesAmount(temp.getSalesAmount());
                    userSales.setRefundAmount(refundAmount);
                    userSalesManager.addUserSales(userSales);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("result", "fail");
        }
        return "/timetask/time_task_result";
    }

}
