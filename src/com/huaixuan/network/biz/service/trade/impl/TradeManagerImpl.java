package com.huaixuan.network.biz.service.trade.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.active.MoveFrameProductDao;
import com.huaixuan.network.biz.dao.agent.InterfaceUserTradeDao;
import com.huaixuan.network.biz.dao.express.ExpressDao;
import com.huaixuan.network.biz.dao.express.RegionDao;
import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.remote.InterfaceApplyDao;
import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.dao.trade.PayPackageDao;
import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.dao.trade.TradePackageDao;
import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.domain.active.MoveFrameLog;
import com.huaixuan.network.biz.domain.active.MoveframeGoods;
import com.huaixuan.network.biz.domain.active.MoveframeInstance;
import com.huaixuan.network.biz.domain.active.MoveframeProduct;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.crm.DoUploadTrade;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;
import com.huaixuan.network.biz.domain.hy.CustomerOrder;
import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.PayPackage;
import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradeResult;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumTradeFrom;
import com.huaixuan.network.biz.enums.EnumTradePayStatus;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumTradeType;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.huaixuan.network.biz.service.account.AccountManager;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.express.ExpressDistManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.GoodsPointManager;
import com.huaixuan.network.biz.service.hy.ProductService;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.biz.service.stock.AvailableStockService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.PayRecordManager;
import com.huaixuan.network.biz.service.trade.TradeAgentManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.user.UserAddressManager;
import com.huaixuan.network.common.util.CsvReader;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.EmallStringUtil;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;
import com.hundsun.itrans.biz.manager.AccountTransManager;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.common.util.Money;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * ������
 */
@Service("tradeManager")
public class TradeManagerImpl implements TradeManager {
    Log                            log                       = LogFactory.getLog(this.getClass());

    // @Autowired
    // private InterfaceUserTradeDao interfaceUserTradeDao;

    @Autowired
    private TradeDao               tradeDao;

    @Autowired
    private TradePackageDao        tradePackageDao;

    // public GoodsDao goodsDao;
    //
    // public OrderDao orderDao;
    //
    // public PackageDao packageDao;
    //
    // private PayPackageDao payPackageDao;
    @Autowired
    private RefundDao              refundDao;
    //
    // private RegionDao regionDao;
    //
    // private ShoppingCartManager shoppingCartManager;
    @Autowired
    private GoodsManager           goodsManager;
    @Autowired
    private OrderManager           orderManager;
    //
    // private PromationManager promationManager;
    //
    // private GoodsAttrManager goodsAttrManager;
    @Autowired
    private GoodsInstanceManager   goodsInstanceManager;
    //
    // private GoodsPointManager goodsPointManager;
    //
    // private GoodsAgentManager goodsAgentManager;
    @Autowired
    private TradeAgentManager      tradeAgentManager;
    //
    // private ResourcesManager resourcesManager;
    //
    // private MailEngine mailEngine;
    // private AdminManager adminManager;
    // private UserAddressManager userAddressManager;
    // private CodeManager codeManager;
    // private ShopInfoManager shopInfoManager;
    // private ExpressDistManager expressDistManager;
    @Autowired
    private PayRecordManager       payRecordManager;
    // private ReturnPointManager returnPointManager;
    @Autowired
    private DepositoryFirstManager depositoryFirstManager;

    @Autowired
    private GoodsInstanceDao       goodsInstanceDao;

    // private GoodsWholsaleManager goodsWholsaleManager;
    // private AccountManager accountManager;
    // private AccountTransManager accountTransManager;
    // private TransactionTemplate transactionTemplate;
    // private String inAccountNo;
    // private UserDao userDao;
    // private InterfaceSyncManager interfaceSyncManager;
    // private TaobaoInterfaceSyncManager taobaoInterfaceSyncManager;

    private static final String    WHOLESALESTATUS_CONFIRMED = "confirmed";

    @Autowired
    private UserAddressManager     userAddressManager;

    @Autowired
    private CodeManager            codeManager;

    @Autowired
    private ShopInfoService        shopInfoService;

    @Autowired
    private AdminService           adminService;

    @Autowired
    private ExpressDistManager     expressDistManager;

    @Autowired
    private UserDao                userDao;

    @Autowired
    private AvailableStockDao      availableStockDao;

    @Autowired
    private ExpressDao             expressDao;

    @Autowired
    private InterfaceApplyDao      interfaceApplyDao;

    /**
     * ����ģ��
     */
    @Autowired
    protected TransactionTemplate  transactionTemplate;
    
    @Autowired
    protected ProductService  productService;
    
    @Autowired
    private AvailableStockService availableStockService;
    
    @Autowired
    private MoveFrameProductDao moveFrameProductDao;
    

    //
    // /**
    // * ���ѷ���
    // *
    // * @return
    // */
    // public boolean warnSendGoods(HttpServletRequest request) {
    //
    // String id = request.getParameter("tradeId");
    // Trade trade = getTrade(new Long(id));
    // if (trade != null) {
    // Map map = new HashMap();
    // map.put("trade", trade);
    // try {
    // // mailEngine.sendMessage("meetyzl@sohu.com", "���ѷ���",
    // // "email-template/warn-send-goods.vm", map);
    // Admin user = adminManager.getAdminUserById(trade.getSellerId());
    // if (user != null) {
    // mailEngine.sendMessage(user.getEmail(), getText("warn.send.goods"),
    // "email-template/warn-send-goods.vm", map);
    // request.setAttribute("message", getText("mail.send.success"));
    // } else {
    // request.setAttribute("errorMessage", getText("seller.error"));
    // }
    // } catch (MessagingException e) {
    // log.error("", e);
    // request.setAttribute("errorMessage", getText("warn.send.goods.failure"));
    // }
    // }
    // return true;
    //
    // }
    //
    //
    /**
     * ��Ч�����ܽ��
     *
     * @return ��Ч�����ܽ��
     */
    public double countAllTradeAmount() {
        return tradeDao.countAllTradeAmount();
    }

    //
    // public double countAllFinishedTradeAmount() {
    // return tradeDao.countAllFinishedTradeAmount();
    // }

    @Override
    public QueryPage getTradesByParameterMap(TradeListQuery query, int currPage, int pageSize) {
        if (log.isInfoEnabled()) {
            log.info("TradeManagerImpl getTradesByParameterMap");
        }
        return tradeDao.getTradesByParameterMap(query, currPage, pageSize);
    }

    public List<Trade> getTradesByParameterMapWhitNote(TradeListQuery query) {
        return tradeDao.getTradesByParameterMap(query);
    }

    /**
     * ��ҳ��ѯ���ϲ�����ParameterMapҪ���Trades�����1??7
     *
     * @param parameterMap
     *            ������1??7
     * @param page
     *            ��ҳ��Ϣ
     * @author fangqing
     * @return ���ϲ�����ParameterMapҪ���Trades�����1??7
     */
    public QueryPage getTradesByParameterMapWhitNote(TradeListQuery query, int currPage,
                                                     int pageSize) {
        if (log.isInfoEnabled()) {
            log.info("TradeManagerImpl getTradesByParameterMap");
        }
        return tradeDao.getTradesByParameterMapWhitNote(query, currPage, pageSize);

    }

    public QueryPage getTradesWithNote(TradeListQuery query, int currPage, int pageSize) {
        if (log.isInfoEnabled()) {
            log.info("TradeManagerImpl getTradesByParameterMap");
        }
        return tradeDao.getTradesWithNote(query, currPage, pageSize);

    }

    //
    // /**
    // * ��ѯ���û�ȫ������
    // *
    // * @param userId
    // * @return
    // */
    // public List<Trade> getTradesByUserId(Long buyUserId) {
    // Map parameterMap = new HashMap();
    // parameterMap.put("buyUserId", buyUserId);
    // return getTradesByParameterMap(parameterMap);
    // }

    /**
     * ��ѯ���ϲ�����ParameterMapҪ���Trades���
     *
     * @param parameterMap
     *            ����
     * @return ���ϲ�����ParameterMapҪ���Trades���
     */
    public List<Trade> getTradesByParameterMap(TradeListQuery query) {
        return tradeDao.getTradesByParameterMap(query);
    }

    //
    // public List<Trade> getTradesAlreadyPaid(Map parameterMap) {
    // return tradeDao.getTradesAlreadyPaid(parameterMap);
    // }
    //
    // public void setTradeDao(TradeDao tradeDao) {
    // this.tradeDao = tradeDao;
    // }
    //
    // public TradeDao getTradeDao() {
    // return this.tradeDao;
    // }
    //
    /* @model: ���Trade��¼ */
    public void addTrade(Trade tradeDao) {
        this.tradeDao.addTrade(tradeDao);
    }

    /* @model: ����Trade��¼ */
    public void editTrade(Trade trade) {
        log.info("TradeManagerImpl.editTrade method");
        this.tradeDao.editTrade(trade);
    }

    //
    // /* @model: ɾ��Trade��¼ */
    // public void removeTrade(Long tradeId) {
    // log.info("TradeManagerImpl.removeTrade method");
    // try {
    // this.tradeDao.removeTrade(tradeId);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // }

    /* @model: ��ѯTrade���,����Trade���� */
    public Trade getTrade(Long tradeId) {
        log.info("TradeManagerImpl.getTrade method");
        return tradeDao.getTrade(tradeId);
    }

    /* @model: ��ѯTrade�����,����Trade����ļ� */
    public List<Trade> getTrades() {
        log.info("TradeManagerImpl.getTrades method");
        try {
            return this.tradeDao.getTrades();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Transactional
    public int updateTradeStatus(String id, String status) {
        log.info("TradeManagerImpl.updateTradeStatus method");
        try {
            Trade trade = this.tradeDao.getTrade(new Long(id));
            if (trade == null) {
                return -1;
            }

            // /��ȡһ���ֿ���Ϣ zhangwy
//            DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(trade
//                .getDepFirstId());

//            if (!EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus())
//                && EnumTradeStatus.TRADE_CLOSE.getKey().equals(status)) {
//                if (!(EnumExpressDistPayment.GOODS_FIRST.getKey().equals(trade.getExpressPayment()))
//                    && !(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(trade.getStatus()))) {
//                    return 2;
//                }
//            }
            if (!EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus())
                && EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(status)) {
                return 2;
            }
            if (!EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(trade.getStatus())
                && EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey().equals(status)) {
                return 2;
            }
            if (!EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey().equals(trade.getStatus())
                && EnumTradeStatus.TRADE_FINISH.getKey().equals(status)) {
                return 2;
            }

            if (EnumTradeStatus.TRADE_FINISH.getKey().equals(status)) {
                Map param = new HashMap();
                param.put("tid", trade.getTid());
                List<Refund> refs = refundDao.getRefundByParameterMap(param);
                for (Refund r : refs) {
                    if (EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(r.getStatus())
                        || EnumRefundStatus.Refund_Close.getKey().equals(r.getStatus())
                        || EnumRefundStatus.Goods_Confirm_Success.getKey().equals(r.getStatus())) {
                        continue;
                    } else {
                        return 3;
                    }
                }
                trade.setFinishTime(new Date());
            }
            trade.setStatus(status);
            this.tradeDao.editTrade(trade);

            // ��̨�¶��� zhangwy
            if (trade.getTradeType() == 1) {
                PayRecord payRecord = payRecordManager.getPayRecordByTid(trade.getTid());
                payRecord.setPayStatus(EnumTradeStatus.TRADE_CLOSE.getKey());
                payRecordManager.editPayRecord(payRecord);
            }

            if (EnumTradeStatus.TRADE_FINISH.getKey().equals(trade.getStatus())
                || EnumTradeStatus.TRADE_CLOSE.getKey().equals(trade.getStatus())) {
               // tradeAgentManager.updateAgentTradeWithAll(trade.getTid(), trade.getStatus(), "save");   //ע�� Emaill_Agent������
            }

            if (EnumTradeStatus.TRADE_CLOSE.getKey().equals(status)) {
                if (null != trade) {
                    String tid = trade.getTid();
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("tid", tid);
                    List<Order> orderList = orderManager.getOrdersByParameterMap(paramMap);
                    for (Order order : orderList) {
//                        if (depositoryFirst != null && (!depositoryFirst.getType().equals("w"))) {
                            goodsInstanceManager.updateAmountForTwo(order.getGoodsInstanceId(),
                                order.getGoodsId(), order.getGoodsNumber(), trade.getDepFirstId(),
                                true);
//                        }
                        // ȡ������ʱ������������
                        goodsManager.updateSaleNumberById(order.getGoodsId(),
                            0 - order.getGoodsNumber());
                    }
                }
            }
            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return -1;
    }

    @Autowired
    PayPackageDao       payPackageDao;

    @Autowired
    OrderDao            orderDao;

    @Autowired
    GoodsPointManager   goodsPointManager;

    @Autowired
    AccountManager      accountManager;

    @Autowired
    AccountTransManager accountTransManager;

    private String      inAccountNo;

    @SuppressWarnings("unchecked")
    @Transactional
    public TradeResult updateTradeStatusByPayment(final String packageId, final String payment) {
        log.info("TradeManagerImpl.updateTradeStatusByPayment method");

        return (TradeResult) transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {
                boolean isSuccess = false;
                TradeResult tradeResult = new TradeResult();

                try {
                    Map<String, Object> pramas = new HashMap<String, Object>();
                    long buyId = 0; // ������ID
                    double sumAmount = 0; // ֧������ܼ�
                    // ����֧����IDȡ�ö�������Ϣ modify by fanyj 2010-12-16
                    // (�����ϲ������)
                    List<PayPackage> payPackgeList = payPackageDao.getPayPackagesByPId(Long
                        .parseLong(packageId));
                    if (payPackgeList != null && payPackgeList.size() > 0) {
                        for (PayPackage obj : payPackgeList) {
                            pramas.put("tid", obj.getTid());
                            Trade trade = tradeDao.getTradeStatusByTid(pramas);
                            if (EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus())) {
                                // �޸Ķ���״̬
                                trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
                                trade.setPayStatus("paid");
                                trade.setPayment(payment);
                                trade.setPayTime(new Date());
                                tradeDao.editTrade(trade);

                                buyId = trade.getBuyId();
                                sumAmount += trade.getAmount();

                                Map parameterMap = new HashMap();
                                parameterMap.put("tid", trade.getTid());
                                List<Order> orderList = orderDao
                                    .getOrdersByParameterMap(parameterMap);
                                Map goodsMap = new HashMap<Long, Integer>();
                                for (Order order : orderList) {

                                    // �޸�������Ʒ����
                                    Goods gd = goodsManager.getGoods(order.getGoodsId());
                                    Date date = new Date();
                                    String pointDate = DateUtil.getDateTime("yyyy-MM-dd", date);
                                    GoodsPoint gp = goodsPointManager.getGPByGoodsIdAndPointDate(
                                        gd.getId(), pointDate);
                                    if (null == gp) {
                                        GoodsPoint goodsPoint = new GoodsPoint();
                                        goodsPoint.setPointDate(new SimpleDateFormat("yyyy-MM-dd")
                                            .parse(pointDate));
                                        goodsPoint.setClickCount(0);
                                        goodsPoint.setGoodsCat(gd.getCatCode());
                                        goodsPoint.setGoodsId(gd.getId());
                                        goodsPoint.setHighPopularPoint(0);
                                        goodsPoint.setHotSalePoint((int) order.getGoodsNumber());
                                        goodsPoint.setSaleCount((int) order.getGoodsNumber());
                                        goodsPoint.setTradeCount(1);
                                        goodsPointManager.addGoodsPoint(goodsPoint);
                                    }
                                    if (null != gp) {
                                        if (null == goodsMap.get(gd.getId())
                                            || !goodsMap.get(gd.getId()).equals(1)) {
                                            gp.setTradeCount(gp.getTradeCount() + 1);
                                        }
                                        int oldcount = gp.getSaleCount();
                                        int newcount = oldcount + (int) order.getGoodsNumber();
                                        gp.setSaleCount(newcount);
                                        int hotSalePoint = gp.getHotSalePoint().intValue();
                                        hotSalePoint = hotSalePoint + (int) order.getGoodsNumber();
                                        gp.setHotSalePoint(hotSalePoint);
                                        goodsPointManager.editGoodsPoint(gp);
                                    }
                                    goodsMap.put(gd.getId(), 1);
                                }
                            }
                            tradeAgentManager.updateAgentTradeWithAll(trade.getTid(),
                                trade.getStatus(), "save");
                        }

                        // �˻�ת��
                        AccountTransReq req = new AccountTransReq();
                        Account transAccount = accountManager.getBuyerTransAccount(buyId);
                        if (transAccount != null) {
                            req.setOutAccountNo(transAccount.getAccountNo());
                        }
                        req.setInAccountNo(inAccountNo);
                        req.setInnerBizNo(packageId);// ����֧����ID modify
                                                     // by fanyj
                                                     // 2010-12-16
                        req.setAmount(new Money(sumAmount));
                        req.setSubTransCode(EnumSubTransCode.TXCODE_FAST_PAY_NO_PWD);

                        AccountTransResult result = accountTransManager.execute(req);
                        if (!result.getCode().equals(
                            AccountTransResult.TXN_RESULT_SUCCESS.getCode())) {
                            log.error("method updateTradeStatusByPayment AccountTransResult fail:"
                                      + result.getCode());
                            throw new Exception("�������ʧ�ܣ�");
                        }
                    }
                    isSuccess = true;
                    tradeResult.setSucess(isSuccess);
                } catch (Exception e) {
                    tradeResult.setSucess(isSuccess);
                    status.setRollbackOnly();
                    log.error(e.getMessage());
                }

                return tradeResult;
            }
        });
    }

    public void updatePayTimeByTid(Long tid) {
        log.info("TradeManagerImpl.updatePayTimeByTid method");
        this.tradeDao.updatePayTimeByTid(tid);
    }

    //
    // public int tradePay(String id) {
    // log.info("TradeManagerImpl.tradePay method");
    // try {
    // Trade trade = this.tradeDao.getTrade(new Long(id));
    // if (!EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus())) {
    // return 2;
    // }
    //
    // if
    // (trade.getPayStatus().equalsIgnoreCase(EnumTradePayStatus.NO_PAY.getKey()))
    // {
    // trade.setStatus(EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey());
    // trade.setPayStatus(EnumTradePayStatus.PAID.getKey());
    // trade.setPayTime(new Date());
    // this.tradeDao.editTrade(trade);
    //
    // Map parameterMap = new HashMap();
    // parameterMap.put("tid", trade.getTid());
    // List<Order> orderList = orderDao.getOrdersByParameterMap(parameterMap);
    // for (Order order : orderList) {
    // Goods goods = goodsDao.getGoods(order.getGoodsId());
    // goods.setSaleNumber(goods.getSaleNumber()
    // + Integer.valueOf(String.valueOf(order.getGoodsNumber())).intValue());
    // goodsDao.editGoods(goods);
    // }
    // return 1;
    // } else {
    // return 3;
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return -1;
    // }
    //
    public List<Trade> getTradesPartByTimetask(String status, int day) {
        log.info("TradeManagerImpl.getTradesPartByTimetask method");
        try {

            return tradeDao.getTradesPartByTimetask(status, day);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;

    }

    //
    // public List<Trade> getTradesByIsPoint(String status, String isPoint, Long
    // buyUserId) {
    // log.info("TradeManagerImpl.getTradesByIsPoint method");
    // try {
    // return tradeDao.getTradesByIsPoint(status, isPoint, buyUserId);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return null;
    // }
    //
    // public void setGoodsDao(GoodsDao goodsDao) {
    // this.goodsDao = goodsDao;
    // }
    //
    // public void setOrderDao(OrderDao orderDao) {
    // this.orderDao = orderDao;
    // }
    //
    // /**
    // * ��������۸�ȥ�����ۿ�
    // *
    // * @param user
    // * User
    // * @param goods
    // * Goods
    // * @return double
    // * @author modified by chenyan 2010/07/02
    // */
    // public double getAgioPrice(User user, Goods goods) {
    // // ResourcesManager resourcesManager = (ResourcesManager)
    // getBean(servletContext,
    // // "resourcesManager");
    // //
    // try {
    // if (null == user) {
    // return goods.getGoodsPrice();
    // } else if (("d".equalsIgnoreCase(user.getType()) ||
    // "w".equalsIgnoreCase(user.getType()))
    // && "y".equalsIgnoreCase(goods.getIsAgent())) {
    // // String userRank = user.getUserRank();
    // // String[] userRanks = userRank.split("-");
    // // double goodsPrice = goods.getAgentPrice();
    // // if (userRanks.length > 0) {
    // // userRank = userRanks[0];
    // // }
    // // String rank = resourcesManager.getResorcesValue("discount", userRank);
    // // double agio = Double.parseDouble(rank);
    // // java.math.BigDecimal f1 = new java.math.BigDecimal(goodsPrice *
    // agio);//小数点后保留2位，4�??5�??
    // // f1 = f1.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
    // // return f1.doubleValue();
    // return goods.getAgentPrice();
    // } else {
    // return goods.getGoodsPrice();
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return goods.getGoodsPrice();
    // }
    //
    // public void saveTrade(
    // Map<ShoppingCart, Long> goodsShoppingTmp// List goodsCartIdList
    // , List promationIdList, PromationVO givePromationVO, User user, Trade
    // trade, String saveType,
    // Map<String, Object> agentSellPriceMap, List<PromationGive>
    // promationGiveAll) {
    // // �������Ʒ�б�
    // // ���ɵĶ����б�
    // List<Order> orderList = new ArrayList<Order>();
    // double totalAgentPrice = 0.0;
    // // ��Ʒ�����б�
    // for (Map.Entry<ShoppingCart, Long> goodsShopping :
    // goodsShoppingTmp.entrySet()) {
    // ShoppingCart shoppingCart = goodsShopping.getKey();
    // Long goodsNumber = goodsShopping.getValue();
    //
    // // if (goodsCartIdList != null && goodsCartIdList.size() > 0) {
    // // List<ShoppingCart> goodsShoppingCartList = shoppingCartManager
    // // .getGoodsShoppingCartByIds(goodsCartIdList);
    // // if (goodsShoppingCartList != null && goodsShoppingCartList.size() > 0)
    // {
    // // for (ShoppingCart shoppingCart : goodsShoppingCartList) {
    // Goods goods = goodsManager.getGoods(shoppingCart.getGoodsId());
    //
    // // �Ƿ���� && �Ƿ������Ʒ
    // if (null != user && ("d".equalsIgnoreCase(user.getType()) ||
    // "w".equalsIgnoreCase(user.getType()))
    // && "y".equalsIgnoreCase(goods.getIsAgent())) {
    // // Map<String, String> agentMap = new HashMap<String, String>();
    // // agentMap.put("agentUserId", user.getId().toString());
    // // agentMap.put("goodCode", goods.getGoodsSn());
    // // int flag =
    // goodsAgentManager.getGoodsAgentCountByParameterMap(agentMap);
    // // if (flag > 0) {
    // // goods.setGoodsPrice(goods.getAgentPrice());
    // // }
    // goods.setMarketPrice(goods.getGoodsPrice());
    // // �����ۿۼ�
    // goods.setGoodsPrice(getAgioPrice(user, goods));
    // }
    // // ��Ʒѡ������
    // String goodsAttrIdsstr = shoppingCart.getGoodsAttrIds();
    // String goodsAttrnameAndValue = "";
    // // Long instanceId=shoppingCart.getGoodsInstanceId();
    // // GoodsInstance
    // // goodsTmpInstance=goodsInstanceManager.getInstance(instanceId);
    // // if(goodsTmpInstance!=null){
    // // goodsAttrnameAndValue=goodsTmpInstance.getAttrDesc();
    // // }
    // if (StringUtil.isNotBlank(goodsAttrIdsstr)) {
    // String[] goodsAttrIds = goodsAttrIdsstr.split(";");
    //
    // for (int x = 0; x < goodsAttrIds.length; x++) {
    // GoodsAttr goodsAttr =
    // goodsAttrManager.getGoodsAttrByGoodsIdAndAttrId(shoppingCart.getGoodsId(),
    // Long.parseLong(goodsAttrIds[x]));
    // if (goodsAttr != null) {
    // goodsAttrnameAndValue = goodsAttrnameAndValue + goodsAttr.getAttrName() +
    // "="
    // + goodsAttr.getAttrValue() + ";";
    // }
    // }
    // }
    // shoppingCart.setGoodsAttrNameAndValues(goodsAttrnameAndValue);
    //
    // // ȡ�ø���Ʒ�Ĺ��ﳵ��Ϣ
    // Order order = getOrderFromGoods(shoppingCart, trade, goods);
    // order.setGoodsNumber(goodsNumber);// �������е������������жϲֿ�
    // // ȡ�ù����û����õ��Ա���
    // String tmp = (String) agentSellPriceMap.get(new
    // Long(goods.getId()).toString());
    // if (StringUtils.isNotBlank(tmp)) {
    // totalAgentPrice = totalAgentPrice + Double.parseDouble(tmp) *
    // goodsNumber;
    // order.setAgentSellPrice(Double.parseDouble(tmp));
    // }
    //
    // orderList.add(order);
    // // ������Ʒ��� Ҫ���ϲֿ�ID
    // goodsInstanceManager.updateAmountForTwo(shoppingCart.getGoodsInstanceId(),
    // shoppingCart.getGoodsId(),
    // (0 - goodsNumber), trade.getDepFirstId(), true);
    // // ������������
    // goodsManager.updateSaleNumberById(shoppingCart.getGoodsId(),
    // goodsNumber);
    //
    // }
    // // }
    // // }
    // // }
    // User userTemp = userDao.getUserById(user.getId());
    // if (trade.getExpressPayment().equals("period_pay")) {
    // trade.setGmtPeriodPayEnd(userTemp.getGmtPeriodPayEnd());
    // // �ӳٽ���ʱ��start
    // Date now = new Date();
    // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // String nowstr = df.format(now);
    // Date nowdate = DateUtil.strToDate(nowstr, "yyyy-MM-dd");
    // Date periodEnd = userTemp.getGmtPeriodPayEnd();
    // if ((nowdate.before(periodEnd) || nowdate.equals(periodEnd))) {
    // trade.setGmtPeriodPayEnd(periodEnd);
    // // �ӳ�ʱ������end
    // } else {
    // Date ycPeriodEnd = DateUtil.string2Date(DateUtil.getDiffDate(periodEnd,
    // user.getPeriod() + 1));
    // trade.setGmtPeriodPayEnd(ycPeriodEnd);
    // }
    //
    // }
    // // �ж��Ƿ�Ϊ�ϲ�����
    // if (StringUtil.isNotBlank(saveType) &&
    // saveType.equalsIgnoreCase("unite")) {
    // // ���� �����û����õ��Ա��ܼ�
    // trade.setAgentSellAmount(0.00);
    // editTrade(trade);
    // } else {
    // // ���� �����û����õ��Ա��ܼ�
    // trade.setAgentSellAmount(0.00);
    // trade.setIsWholesale("n");
    // addTrade(trade);
    // }
    //
    // // ������ײ� zhangwy
    // if (promationGiveAll != null && promationGiveAll.size() > 0) {
    // for (PromationGive temp : promationGiveAll) {
    // Goods goods = goodsManager.getGoods(temp.getGoodsId());
    // GoodsInstance goodsInstance =
    // goodsInstanceManager.getInstance(temp.getGoodsInstanceId());
    // Order order = new Order();
    // order.setGoodsAttr(goods.getAttrDesc());
    // order.setGoodsId(goods.getId());
    // order.setGoodsNumber(temp.getGoodsInstanceNum());
    // order.setGoodsPrice(0);
    // order.setGoodsTitle(goods.getTitle());
    // order.setGoodWeight(goods.getGoodsWeight());
    // order.setMarketPrice(goods.getGoodsPrice());
    // order.setRefundStauts("");
    // order.setTid(trade.getTid());
    // order.setShippingAmount(0);
    // order.setGoodsInstanceId(temp.getGoodsInstanceId());
    // if (goodsInstance.getAttrDesc() != null) {
    // order.setBuyAttr(goodsInstance.getAttrDesc());
    // }
    // order.setOrderType(1); // ��Ʒorder��orderType = 1
    // orderList.add(order);
    // // ������Ʒ��� Ҫ���ϲֿ�ID
    // goodsInstanceManager.updateAmountForTwo(temp.getGoodsInstanceId(),
    // temp.getGoodsId(), (0 - temp
    // .getGoodsInstanceNum().longValue()), trade.getDepFirstId(), true);
    // // ������������
    // goodsManager.updateSaleNumberById(temp.getGoodsId(),
    // temp.getGoodsInstanceNum().longValue());
    // }
    // }
    //
    // // �ײͶ����б�
    // if (promationIdList != null && promationIdList.size() > 0) {
    // for (int m = 0; m < promationIdList.size(); m++) {
    // String[] promationIdTimeTag = ((String)
    // promationIdList.get(m)).split(";");
    // Long promationId = Long.parseLong(promationIdTimeTag[0]);
    // String timeTag = promationIdTimeTag[1];
    //
    // // �ײ�����promationVO����������
    // PromContext context = new PromContext();
    // PromationVO promationVO = promationManager.getPromationInfo(promationId,
    // context);
    //
    // // �ײ�����Ʒlist
    // List<Goods> promationGoodsList = promationVO.getGoodsList();
    // if (promationGoodsList != null && promationGoodsList.size() > 0) {
    // // �ײ���һ����Ʒ�ڹ�����е����й������ݼ�¼
    // List<ShoppingCart> shoppingCartList = this.shoppingCartManager
    // .getShoppingCartListByUserIdAndGoodId(user.getId(),
    // promationGoodsList.get(0).getId(),
    // promationId, timeTag);
    // for (int i = 0; i < shoppingCartList.size(); i++) {
    // // ���ײ���������Ʒ�Ĺ��ﳵ��¼���ݲ����ײ���������Ʒ�Ĺ����¼��
    // List<ShoppingCart> shoppingCartListByPG = this.shoppingCartManager
    // .getShoppingCartListByGoodIdAndTimeTag(shoppingCartList.get(i));
    // if (shoppingCartListByPG != null && shoppingCartListByPG.size() > 0) {
    //
    // PackageTrade packageTrade = new PackageTrade();
    // packageTrade.setName(promationVO.getName());
    // packageTrade.setGoodsAmount(promationVO.getTotalPrice());
    // packageTrade.setPackageAmount(promationVO.getPromationPrice());
    // packageTrade.setTid(trade.getTid());
    // packageTrade.setNumber(shoppingCartListByPG.get(0).getGoodsNumber());
    // Long packageId = this.packageDao.addPackage(packageTrade);
    // String goodsAttrnameAndValue = "";
    // // ȡ����Ʒ��Ӧ�Ĳɹ�����
    // for (int j = 0; j < shoppingCartListByPG.size(); j++) {
    // // String goodsAttrIdsstr = shoppingCartListByPG
    // // .get(j).getGoodsAttrIds();
    // // if (StringUtil.isNotBlank(goodsAttrIdsstr)) {
    // // String[] goodsAttrIds = goodsAttrIdsstr
    // // .split(";");
    // // for (int x = 0; x < goodsAttrIds.length; x++)
    // // {
    // // GoodsAttr goodsAttr = goodsAttrManager
    // // .getGoodsAttrByGoodsIdAndAttrId(
    // // shoppingCartListByPG
    // // .get(j)
    // // .getGoodsId(),
    // // Long
    // // .parseLong(goodsAttrIds[x]));
    // // if (goodsAttr != null) {
    // // goodsAttrnameAndValue = goodsAttr
    // // .getAttrName()
    // // + ":"
    // // + goodsAttr.getAttrValue()
    // // + ";";
    // // }
    // // }
    // // }
    // Long instanceId = shoppingCartListByPG.get(j).getGoodsInstanceId();
    // GoodsInstance goodsTmpInstance =
    // goodsInstanceManager.getInstance(instanceId);
    // if (goodsTmpInstance != null) {
    // goodsAttrnameAndValue = goodsTmpInstance.getAttrDesc();
    // }
    // shoppingCartListByPG.get(j).setGoodsAttrNameAndValues(goodsAttrnameAndValue);
    //
    // // ȡ����Ʒ����
    // for (int y = 0; y < promationGoodsList.size(); y++) {
    // if (shoppingCartListByPG.get(j).getGoodsId() ==
    // promationGoodsList.get(y).getId()) {
    // shoppingCartListByPG.get(j).setGoodsName(promationGoodsList.get(y).getTitle());
    // Order order = getOrderFromGoods(shoppingCartListByPG.get(j), trade,
    // promationGoodsList.get(y));
    // order.setPackageId(packageId);
    // orderList.add(order);
    // goodsInstanceManager.updateAmountForTwo(shoppingCartListByPG.get(j)
    // .getGoodsInstanceId(), shoppingCartListByPG.get(j).getGoodsId(),
    // 0 - new Long(shoppingCartListByPG.get(j).getGoodsNumber()));
    // // ������������
    // goodsManager.updateSaleNumberById(shoppingCartListByPG.get(j).getGoodsId(),
    // new Long(shoppingCartListByPG.get(j).getGoodsNumber()));
    // }
    // }
    //
    // }
    // }
    // }
    // }
    //
    // }
    // }
    //
    // if (givePromationVO != null) {// �ײ�����:ȫ�������������Ʒcode
    // // TODO ��Ҫ�޸�
    // if
    // (Promation.Promation_full_give.equalsIgnoreCase(givePromationVO.getModeCode()))
    // {
    // PackageTrade packageTrade = new PackageTrade();
    // packageTrade.setName(givePromationVO.getName());
    // packageTrade.setGoodsAmount(givePromationVO.getTotalPrice());
    // packageTrade.setPackageAmount(givePromationVO.getPromationPrice());
    // packageTrade.setTid(trade.getTid());
    // packageTrade.setNumber(1);
    // Long packageId = this.packageDao.addPackage(packageTrade);
    // Goods goods = givePromationVO.getGoodsList().get(0);
    // Order order = new Order();
    // order.setGoodsAttr(goods.getAttrDesc());
    // order.setGoodsId(goods.getId());
    // order.setGoodsNumber(1l);
    // order.setGoodsPrice(goods.getGoodsPrice());
    // order.setGoodsTitle(goods.getTitle());
    // order.setGoodWeight(goods.getGoodsWeight());
    // order.setMarketPrice(goods.getMarketPrice());
    // order.setRefundStauts("");
    // order.setTid(trade.getTid());
    // order.setShippingAmount(0);
    // order.setPackageId(packageId);
    // orderList.add(order);
    //
    // }
    // }
    //
    // // �ж��Ƿ�Ϊ�ϲ�����
    // if (StringUtil.isNotBlank(saveType) &&
    // saveType.equalsIgnoreCase("unite")) {
    // List<Order> orderListTmp = orderManager.getOrdersByTid(trade.getTid());
    // List<Order> orderUpdateList = new ArrayList<Order>();
    // List<Order> orderInsertList = new ArrayList<Order>();
    // for (Order ordertmp : orderList) {
    // boolean flag = false;
    // for (Order tmp : orderListTmp) {
    // // �Ƚ��Ѿ����ڵĶ��������ڵĶ�����ƷID�Ƿ���ͬ����ͬ��������ӣ�����
    // if (ordertmp.getGoodsInstanceId().equals(tmp.getGoodsInstanceId())
    // && (ordertmp.getOrderType() == tmp.getOrderType())) { // �Ƿ���ƷҲҪ���� zhangwy
    // long number = tmp.getGoodsNumber() + ordertmp.getGoodsNumber();
    // tmp.setGoodsNumber(number);
    // orderUpdateList.add(tmp);
    // // orderInsertList.remove(ordertmp);//��¼Ҫɾ���Ķ���
    // flag = true;
    // }
    // }
    // if (!flag) {
    // orderInsertList.add(ordertmp);// ��¼Ҫɾ���Ķ���
    // }
    // }
    // orderManager.addOrders(orderInsertList);// ��������
    // orderManager.editOrders(orderUpdateList);// ��������
    // } else {
    // // �ύÿ����Ʒ�Ķ�����Ϣ
    // orderManager.addOrders(orderList);
    // }
    //
    // }
    //
    // /**
    // * ������Ʒ��Ӧ��order����
    // *
    // * @param buyNum
    // * @param goods
    // * @return
    // */
    // public Order getOrderFromGoods(ShoppingCart shoppingCart, Trade trade,
    // Goods goods) {
    // Order order = new Order();
    // order.setGoodsAttr(goods.getAttrDesc());
    // order.setGoodsId(goods.getId());
    // order.setGoodsNumber(shoppingCart.getGoodsNumber());
    // order.setGoodsPrice(this.getAgioPrice(goods));
    // order.setGoodsTitle(goods.getTitle());
    // order.setGoodWeight(goods.getGoodsWeight());
    // order.setMarketPrice(goods.getMarketPrice());
    // order.setRefundStauts("");
    // order.setTid(trade.getTid());
    // order.setShippingAmount(0);
    // order.setGoodsInstanceId(shoppingCart.getGoodsInstanceId());
    // order.setBuyAttr(shoppingCart.getGoodsAttrNameAndValues());
    //
    // return order;
    // }
    //
    // public ShoppingCartManager getShoppingCartManager() {
    // return shoppingCartManager;
    // }
    //
    // public void setShoppingCartManager(ShoppingCartManager
    // shoppingCartManager) {
    // this.shoppingCartManager = shoppingCartManager;
    // }
    //
    // public GoodsManager getGoodsManager() {
    // return goodsManager;
    // }
    //
    // public void setGoodsManager(GoodsManager goodsManager) {
    // this.goodsManager = goodsManager;
    // }
    //
    // public OrderManager getOrderManager() {
    // return orderManager;
    // }
    //
    // public void setOrderManager(OrderManager orderManager) {
    // this.orderManager = orderManager;
    // }
    //
    // public GoodsDao getGoodsDao() {
    // return goodsDao;
    // }
    //
    // public OrderDao getOrderDao() {
    // return orderDao;
    // }
    //
    // public GoodsAttrManager getGoodsAttrManager() {
    // return goodsAttrManager;
    // }
    //
    // public void setGoodsAttrManager(GoodsAttrManager goodsAttrManager) {
    // this.goodsAttrManager = goodsAttrManager;
    // }

    public Trade getTradeByTid(String tid) {
        // �Ƿ��Ǻϲ�����
        if (tid.indexOf("HB") >= 0) {
            List<String> tradeIdStr = tradePackageDao.getTidByMergeTid(tid);
            if (tradeIdStr != null && tradeIdStr.size() > 0) {
                for (String obj : tradeIdStr) {
                    tid = obj;
                    break;
                }
            }
        }
        return tradeDao.getTradeByTid(tid);
    }

    // public Trade getTradeByPurchaseId(String pid) {
    // return tradeDao.getTradeByPurchaseId(pid);
    // }
    //
    // public void updateTradeStatusByTid(String tid, String status) {
    // Map<String, Object> pramas = new HashMap<String, Object>();
    // pramas.put("tid", tid);
    // pramas.put("status", status);
    // this.tradeDao.updateTradeStatusByTid(pramas);
    // }
    //
    // public Trade getTradeStatusByTid(String tid) {
    // Map<String, Object> pramas = new HashMap<String, Object>();
    // pramas.put("tid", tid);
    // return this.tradeDao.getTradeStatusByTid(pramas);
    // }
    //
    // public GoodsInstanceManager getGoodsInstanceManager() {
    // return goodsInstanceManager;
    // }
    //
    // public void setGoodsInstanceManager(GoodsInstanceManager
    // goodsInstanceManager) {
    // this.goodsInstanceManager = goodsInstanceManager;
    // }

    public Trade getTradeByRefundId(String refundId) {
        return this.tradeDao.getTradeByRefundId(refundId);
    }

    public void updateShippingAmountById(Double shippingAmount, Double amount, String memo, Long id) {
        log.info("TradeManagerImpl.updateShippingAmountById method");
        this.tradeDao.updateShippingAmountById(shippingAmount, amount, memo, id);
    }

    //
    // public Trade getTradeByOrderId(Long id) {
    // log.info("TradeManagerImpl.getTradeByOrderId method");
    // try {
    // return this.tradeDao.getTradeByOrderId(id);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return null;
    // }
    //
    // @Transactional
    // public void updateAmountByGoodsPrice(Double goodsPrice, Double
    // goodsAmount, Double amount, String memo, Long
    // orderId) {
    // log.info("TradeManagerImpl.updateAmountByGoodsPrice method");
    // try {
    // // ���¶�����۸�
    // this.tradeDao.updateAmountByGoodsPrice(goodsAmount, amount, memo,
    // orderId);
    // // ���¶�����Ʒ��۸�
    // this.orderDao.updateGoodsPriceById(goodsPrice, orderId);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return;
    // }
    //
    // public void insertAgentTrade(AgentTrade agentTrade, User user) {
    //
    // double goods_amount = goodsDao.getRefund_amount(agentTrade.getTid());
    // double temp = agentTrade.getGoodsAmount() - goods_amount;
    // agentTrade.setUserId(user.getId());
    // agentTrade.setGoodsAmount(temp);
    // boolean flag = true;
    // if ("p".equals(user.getType())) {
    // flag = false;
    // }
    // Map map = new HashMap();
    // map.put("tid", agentTrade.getTid());
    // map.put("user_id", user.getId());
    // if ("n".equals(goodsDao.goodisagent(map))) {
    // flag = false;
    // }
    //
    // if (flag) {
    // this.goodsDao.insertAgentTrade(agentTrade);
    // }
    // }

    public Trade getTradesGoodsAmountSum(TradeListQuery query) {
        log.info("TradeManagerImpl.getTradesGoodsAmountSum method");
        try {
            return tradeDao.getTradesGoodsAmountSum(query);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void updateMessageByTradeId(Long tradeID, String message) {
        log.info("TradeManagerImpl.updateMessageByTradeId method");
        this.tradeDao.updateMessageByTradeId(tradeID, message);
    }

    public void updateInvoiceByTradeId(Long tradeID, String invoice) {
        log.info("TradeManagerImpl.updateInvoiceByTradeId method");
        try {
            this.tradeDao.updateInvoiceByTradeId(tradeID, invoice);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public int updateIspurchasedByTradeId(Long tradeID) {
        log.info("TradeManagerImpl.updateIspurchasedByTradeId method");
        return this.tradeDao.updateIspurchasedByTradeId(tradeID);
    }

    /**
     * ��̨�µ�
     *
     * @param goodsInstanceList
     * @param depfirst
     */
    @SuppressWarnings("unchecked")
    public void backSaveTrade(final List<GoodsInstance> goodsInstanceList,
                              final DepositoryFirst depfirst, final User user, final Trade trade,
                              final UserAddress userAddress, final double weight,
                              final double epWeight, final double goodsAmount) {

        Boolean isSuccess = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            public Boolean doInTransaction(TransactionStatus status) {

                long userAddressId = userAddress.getId();
                if (userAddressId == -1) {
                    trade.setProvince(userAddress.getProvince());
                    trade.setCity(userAddress.getCity());
                    trade.setDistrict(userAddress.getDistrict());
                    /* ���ջ���ַ��ӵ��ҵ��ջ���ַ�б�? */
                    List<UserAddress> tradeAddresses = userAddressManager
                        .getTradeAddressByUserId(user.getId());
                    /* ����ҵ��ջ���ַ����5���򽫴˼�¼��¼���ҵ��ջ���ַ�У������Թ� */
                    if (tradeAddresses.size() < 5) {
                        userAddress.setUserId(user.getId());
                        userAddress.setContextName(trade.getReceiver());
                        if (EmallStringUtil.isMobilePhone(trade.getMobile())) {
                            userAddress.setMobile(trade.getMobile());
                        } else {
                            userAddress.setTel(trade.getMobile());
                        }
                        userAddress.setAddress(trade.getAddress());
                        userAddress.setZipcode(trade.getZipcode());
                        userAddress.setId(0L);
                        userAddressManager.addUserAddress(userAddress);
                    }

                }
                if (userAddressId != -1) {
                    UserAddress userAddress = userAddressManager.getUserAddress(userAddressId);
                    trade.setReceiver(userAddress.getContextName());
                    trade.setCountry(userAddress.getCountry());
                    trade.setProvince(userAddress.getProvince());
                    trade.setCity(userAddress.getCity());
                    trade.setDistrict(userAddress.getDistrict());
                    trade.setAddress(userAddress.getAddress());
                    trade.setZipcode(userAddress.getZipcode());
                    if (!EmallStringUtil.isBlank(userAddress.getMobile())) {
                        trade.setMobile(userAddress.getMobile());
                    } else {
                        trade.setMobile(userAddress.getTel());
                    }
                }

                trade.setTid(codeManager.buildCode(codeManager.TRADE_CODE));
                trade.setBuyId(user.getId());
                trade.setBuyNick(user.getAccount());
                Long shopId = Constants.SHOP_ID;
                trade.setShopId(shopId);
                ShopInfo shopInfo = shopInfoService.getShopInfo(shopId);
                Admin admin_seller = adminService.getAdminById(shopInfo.getUserId());
                trade.setSellerId(admin_seller.getId());
                trade.setSellerNick(admin_seller.getName());
                trade.setPayStatus(EnumTradePayStatus.NO_PAY.getKey());
                trade.setType("2");
                // /������ʼ��ַ��ʼ��������
                String regionCodeStart = "330782";
                if (null != depfirst) {
                    regionCodeStart = depfirst.getRegionCode();
                }
                double shippingAmount = shippingAmount(trade, weight, epWeight, regionCodeStart);
                trade.setShippingAmount(shippingAmount);
                trade.setGoodsAmount(goodsAmount);
                trade.setAmount(goodsAmount + shippingAmount);
                if (trade.getExpressPayment().equals("period_pay")) {
                    trade.setStatus("wait_seller_send_goods");
                } else {
                    trade.setStatus(EnumTradeStatus.WAIT_BUYER_PAY.getKey());
                }
                trade.setTradeType(1);
                trade.setDepFirstId(depfirst.getId());
                User userTemp = userDao.getUserById(user.getId());
                if (trade.getExpressPayment().equals("period_pay")) {
                    trade.setGmtPeriodPayEnd(userTemp.getGmtPeriodPayEnd());
                }

                List<Order> orderList = new ArrayList<Order>();
                if (goodsInstanceList != null && goodsInstanceList.size() > 0) {
                    for (GoodsInstance tmp : goodsInstanceList) {
                        Order order = new Order();
                        Goods goods = goodsManager.getGoods(tmp.getGoodsId());
                        order.setGoodsAttr(goods.getAttrDesc());
                        order.setGoodsId(goods.getId());
                        order.setGoodsNumber(tmp.getBuyNum());
                        if (tmp.getType() != null && tmp.getType().equals("zp")) {
                            order.setGoodsPrice(0);
                            order.setOrderType(1);
                            order.setMarketPrice(goods.getGoodsPrice());
                        } else {
                            order.setGoodsPrice(tmp.getAgentPrice());
                            order.setMarketPrice(goods.getGoodsPrice());
                        }
                        order.setGoodsTitle(goods.getTitle());
                        order.setGoodWeight(goods.getGoodsWeight());
                        order.setRefundStauts("");
                        order.setTid(trade.getTid());
                        order.setShippingAmount(0);
                        order.setGoodsInstanceId(tmp.getId());
                        order.setBuyAttr(tmp.getAttrDesc());
                        orderList.add(order);
                        // ���¿��ÿ�� Ҫ���ϲֿ�ID
                        goodsInstanceManager.updateAmountForTwo(tmp.getId(), tmp.getGoodsId(),
                            (0 - tmp.getBuyNum()), depfirst.getId(), true);
                        // ������������
                        goodsManager.updateSaleNumberById(tmp.getGoodsId(), tmp.getBuyNum());
                    }
                    trade.setIsWholesale("n");
                    addTrade(trade);
                    orderManager.addOrders(orderList);
                    PayRecord payrecord = new PayRecord();
                    payrecord.setPayPlatform("back");
                    payrecord.setTid(trade.getTid());
                    payrecord.setPayAmount(trade.getAmount());
                    payrecord.setBuyer(user.getEmail());
                    payrecord.setSeller(admin_seller.getEmail());
                    payrecord.setPayStatus("TRADE_FINISHED");
                    payRecordManager.addPayRecord(payrecord);
                    try {
                        tradeAgentManager.updateAgentTradeWithAll(trade.getTid(),
                            trade.getStatus(), "golist");
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }

                return true;
            };
        });
    }

    @Autowired
    RegionDao             regionDao;

    @Autowired
    InterfaceUserTradeDao interfaceUserTradeDao;

    /**
     * ���½ӿ�ͬ�����ɶ���
     */
    @Transactional
    public void savePaipaiTrade(Long depFirstId, Long userId, Trade trade, String shopType) {
        try {
            // ������ַ��Ϣ
            String userAddress = trade.getAddress();
            if (StringUtil.isNotBlank(userAddress)) {
                String[] addressArray = userAddress.split(" ");
                Region region = null;
                if (addressArray != null && addressArray.length > 0) {
                    trade.setAddress(addressArray[3]);
                    //address���жϣ�����"��"��"��"��,��¼���� zhangwy 2011/05/23
                    if (StringUtil.isNotBlank(trade.getAddress())
                        && (trade.getAddress().contains("��") || trade.getAddress().contains("��"))) {
                        StringBuffer temp = new StringBuffer();
                        if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                            temp.append(trade.getSeviceNote()).append("(��ַ�а��������)");
                        } else {
                            temp.append("(��ַ�а��������)");
                        }
                        trade.setSeviceNote(temp.toString());
                    }
                    for (int i = 0; i < addressArray.length - 1; i++) {
                        region = regionDao.getRegionByName(addressArray[i]);
                        if (region != null) {
                            if (i == 0) {
                                trade.setProvince(region.getCode());
                            } else if (i == 1) {
                                trade.setCity(region.getCode());
                            } else if (i == 2) {
                                trade.setDistrict(region.getCode());
                            }
                        }
                    }
                }
            }

            // ������Ϣ
            User user = userDao.getUserById(userId);
            String tid = codeManager.buildCode(codeManager.TRADE_CODE);
            // added by chenyan 2011/02/18 ת�����Ĺ���������tid
            tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_PAIPAI.getKey());
            trade.setTid(tid);
            trade.setBuyId(user.getId());
            trade.setBuyNick(user.getAccount());
            Long shopId = Constants.SHOP_ID;
            trade.setShopId(shopId);
            ShopInfo shopInfo = shopInfoService.getShopInfo(shopId);
            Admin admin_seller = adminService.getAdminById(shopInfo.getUserId());
            trade.setSellerId(admin_seller.getId());
            trade.setSellerNick(admin_seller.getName());
            trade.setPayStatus(EnumTradePayStatus.NO_PAY.getKey());
            trade.setType("1");
            // ������ʼ��ַ��ʼ��������
            String regionCodeStart = "330782";
            // ��ȡһ���ֿ���Ϣ
            DepositoryFirst depfirst = depositoryFirstManager.getDepositoryById(depFirstId);
            if (null != depfirst) {
                regionCodeStart = depfirst.getRegionCode();
            }
            // trade.setStatus(EnumTradeStatus.WAIT_BUYER_PAY.getKey());
            trade.setTradeType(2);
            trade.setDepFirstId(depFirstId);
			// ��Ӫ���������Զ���Ҫ��Ʊ2011/05/30 chenyan start
            if (StringUtil.isNotBlank(shopType) && shopType.equals("self") && user != null) {
                trade.setInvoice(user.getInvoice());
            }
            // �������Զ���Ҫ��Ʊ2011/05/30 chenyan end
            List<Order> orderList = trade.getOrderList();
            List<Order> newOrderList = new ArrayList<Order>();
            double goodsAmount = 0;
            double weight = 0;
            boolean isSalesProPrice = false;//�Ƿ��д����۸��ж� zhangwy 2011/05/23
            if (orderList != null && orderList.size() > 0) {
                for (Order order : orderList) {
                    if (StringUtil.isNotBlank(order.getCode())) {
                        GoodsInstance goodsIns = goodsInstanceManager.getInstanceByCode(order
                            .getCode());
                        if (goodsIns != null) {
                            Goods goods = this.goodsManager.getGoods(goodsIns.getGoodsId());
                            order.setGoodsAttr(goods.getAttrDesc());
                            order.setGoodsId(goods.getId());
                            if (goodsIns.getType() != null && goodsIns.getType().equals("zp")) {
                                order.setGoodsPrice(0);
                                order.setOrderType(1);
                                order.setMarketPrice(goods.getGoodsPrice());
                            } else {
                                // ��Ӫ��������ʹ��������Ʒ�۸��� 2011/05/30 chenyan start
	                            if (StringUtil.isNotBlank(shopType) && shopType.equals("self")) {
	                                //ǰ�����и�ֵ����
	                            } else {
	                                order.setGoodsPrice(goods.getAgentPrice());
	                            }
                                order.setMarketPrice(goods.getGoodsPrice());
                            }
                            if (StringUtil.isNotBlank(shopType) && shopType.equals("self")) {
							    goodsAmount += order.getGoodsPrice()
                                * order.getGoodsNumber();// ��Ʒ���׽���ۼ�
							} else {
    							goodsAmount += goods.getAgentPrice()
    									* order.getGoodsNumber();// ��Ʒ���׽���ۼ�
							}
                            weight += goods.getGoodsWeight() * order.getGoodsNumber();// ��Ʒ�����ۼ�
                            order.setGoodsTitle(goods.getTitle());
                            order.setGoodWeight(goods.getGoodsWeight());
                            order.setRefundStauts("");
                            order.setTid(trade.getTid());
                            order.setShippingAmount(0);
                            order.setGoodsInstanceId(goodsIns.getId());
                            order.setBuyAttr(goodsIns.getAttrDesc());
                            if ((!isSalesProPrice)
                                && (order.getGoodsPrice() < goods.getSalesProPrice())) {
                                isSalesProPrice = true;
                            }
                            newOrderList.add(order);
                            // ֻ�ж���״̬�ǵȴ����֧����ʱ��ż����ÿ��
                            if (EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus())) {
                                // ���¿��ÿ�� Ҫ���ϲֿ�ID
                                goodsInstanceManager.updateAmountForTwo(goodsIns.getId(),
                                    goodsIns.getGoodsId(), (0 - order.getGoodsNumber()),
                                    depfirst.getId(), true);
                                // ������������
                                goodsManager.updateSaleNumberById(goodsIns.getGoodsId(),
                                    order.getGoodsNumber());
                            }
                        }
                    }
                }
                if (isSalesProPrice) {
                    StringBuffer temp = new StringBuffer();
                    if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                        temp.append(trade.getSeviceNote()).append("(����������Ʒ�ļ۸�С�ڸ���Ʒ�Ĵ�����)");
                    } else {
                        temp.append("((����������Ʒ�ļ۸�С�ڸ���Ʒ�Ĵ�����)");
                    }
                    trade.setSeviceNote(temp.toString());
                }
                double shippingAmount = this.shippingAmount(trade, weight, 0, regionCodeStart);
                trade.setShippingAmount(shippingAmount);
                trade.setGoodsAmount(goodsAmount);
                trade.setAmount(goodsAmount + shippingAmount);
                trade.setIsWholesale("n");
                addTrade(trade);
                orderManager.addOrders(newOrderList);
                tradeAgentManager.updateAgentTradeWithAll(trade.getTid(), trade.getStatus(),
                    "golist");
                // �����ض����Ÿ��µ�����ͬ������
                interfaceUserTradeDao.editInterfaceUserTradeByDealCode(trade.getDealCode(), tid);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public int updateTradeExpressId(String tid, String expressId) {
        log.info("TradeManagerImpl.updateTradeExpressId method");
        try {
            return this.tradeDao.updateTradeExpressId(tid, expressId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public int updateInterfaceExpressCode(String tid, String interfaceExpressCode) {
        log.info("TradeManagerImpl.updateTradeExpressId method");
        try {
            return this.tradeDao.updateInterfaceExpressCode(tid, interfaceExpressCode);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    /**
     * ��ѯ�����������Ľ������
     *
     * @param parMap
     * @return
     */
    public int getTradesCountByParameterMap(Map parMap) {
        if (log.isInfoEnabled()) {
            log.info("TradeManagerImpl getTradesCountByParameterMap");
        }
        try {
            return tradeDao.getTradesCountByParameterMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    //
    // public int updateTradeBuyerNote(String tid, String buyerNote) {
    // log.info("TradeManagerImpl.updateTradeBuyerNote method");
    // try {
    // return this.tradeDao.updatetradeBuyerNote(tid, buyerNote);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return 0;
    // }
    //
    private double shippingAmount(Trade trade, double weight, double epWeight,
                                  String regionCodeStart) {
        // String regionCodeStart = "330782";
        boolean isPeriod = false;
        if (trade.getExpressPayment() == null
            || EnumExpressDistPayment.PERIOD_PAY.getKey().equals(trade.getExpressPayment())) {
            trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
            isPeriod = true;
        }
        if (trade.getDistrict() != null && StringUtil.isNotBlank(trade.getDistrict())) {
            ExpressDist expressDist = expressDistManager.getExpressDistByRegionCodeEnd(
                regionCodeStart, trade.getDistrict(), trade.getExpressPayment(),
                trade.getExpressId());
            if (isPeriod) {
                trade.setExpressPayment(EnumExpressDistPayment.PERIOD_PAY.getKey());
            }
            if (expressDist != null) {
                if (expressDist.getWeightFirst() < weight) {// ��������ؾ�Ҫ��������������
                    double newWeightFirst = 0;
                    newWeightFirst = jiSuanYouFei(weight, expressDist);
                    // ��������а�����Ͱ��ʻ��Ʒ����������˷�
                    if (epWeight > 0) {
                        double newEpWeightFirst = 0;
                        if (expressDist.getWeightFirst() < epWeight) {// ��������ؾ�Ҫ��������������
                            newEpWeightFirst = jiSuanYouFei(epWeight, expressDist);
                        } else {
                            newEpWeightFirst = DoubleUtil.round(expressDist.getWeightFirstMoney(),
                                1);
                        }
                        newWeightFirst = newWeightFirst - newEpWeightFirst;
                    }
                    return DoubleUtil.round(newWeightFirst > 0 ? newWeightFirst : 0, 1);
                } else {
                    // �Ƿ������Ͱ��ʻ��Ʒ
                    if (epWeight > 0) {
                        return DoubleUtil.round(0, 1);
                    } else {
                        return DoubleUtil.round(expressDist.getWeightFirstMoney(), 1);
                    }
                }
            }
        } else {
            ExpressDist expressDist = expressDistManager.getExpressDistByRegionCodeEnd(
                regionCodeStart, trade.getCity(), trade.getExpressPayment(), trade.getExpressId());
            if (isPeriod) {
                trade.setExpressPayment("period_pay");
            }
            if (expressDist != null) {
                if (expressDist.getWeightFirst() < weight) {// ��������ؾ�Ҫ��������������
                    double newWeightFirst = 0;
                    newWeightFirst = jiSuanYouFei(weight, expressDist);
                    // ��������а�����Ͱ��ʻ��Ʒ����������˷�
                    if (epWeight > 0) {
                        double newEpWeightFirst = 0;
                        if (expressDist.getWeightFirst() < epWeight) {// ��������ؾ�Ҫ��������������
                            newEpWeightFirst = jiSuanYouFei(epWeight, expressDist);
                        } else {
                            newEpWeightFirst = DoubleUtil.round(expressDist.getWeightFirstMoney(),
                                1);
                        }
                        newWeightFirst = newWeightFirst - newEpWeightFirst;
                    }
                    return DoubleUtil.round(newWeightFirst > 0 ? newWeightFirst : 0, 1);
                } else {
                    // �Ƿ������Ͱ��ʻ��Ʒ
                    if (epWeight > 0) {
                        return DoubleUtil.round(0, 1);
                    } else {
                        return DoubleUtil.round(expressDist.getWeightFirstMoney(), 1);
                    }
                }
            }
        }
        return 0;
    }

    private double jiSuanYouFei(double weight, ExpressDist expressDist) {
        double newWeightFirst;
        // �м�������
        double newWeight = (weight - expressDist.getWeightFirst()) / expressDist.getWeightNext();
        // ����С�������أ���+1
        int newWeightInt = (int) newWeight;
        if (newWeight > newWeightInt) {
            newWeightInt = newWeightInt + 1;
        }
        newWeightFirst = expressDist.getWeightFirstMoney() + newWeightInt
                         * expressDist.getWeightNextMoney();
        return newWeightFirst;
    }

    //
    // /**
    // * ��������
    // */
    // public Result<Object> submitToWholeTrade(String loginFlag,
    // List<ShoppingCart> goodsCartIdList, boolean isLoged,
    // UserInfo loginUserInfo, UserAddress userAddress, Trade trade) {
    // Result<Object> result = new Result<Object>();
    // User user = CurrentUser.get();
    // if (null == user) {
    // result.setMessage(getText("user.session.invalid"));
    // result.setResult(1);
    // return result;
    // }
    // if (goodsCartIdList == null || goodsCartIdList.size() == 0) {
    // result.setMessage(getText("nopopedom.ordersubmit.goods.required"));
    // result.setResult(1);
    // return result;
    // }
    //
    // // ����������֯�Ĺ��ﳵ
    // List<ShoppingCart> goodsCartFinalList = new ArrayList<ShoppingCart>();
    //
    // // ȡ������Ʒ�ȼ��۸񣬲������¼��Լ�������������֤
    // List<Long> goodsIdList = new ArrayList<Long>();
    // for (ShoppingCart shoppingCart : goodsCartIdList) {
    // goodsIdList.add(shoppingCart.getGoodsId());
    // }
    // List<Goods> goodsList = goodsManager.getGoodsByIds(goodsIdList);
    // for (Goods goods : goodsList) {
    // List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
    // Map parMap = new HashMap();
    // parMap.put("goodsId", goods.getId());
    // parMap.put("userId", user.getId());
    // parMap.put("isWholesale", "w");
    // shoppingCartList =
    // shoppingCartManager.getShoppingCartsByParameterMap(parMap);
    // int allNum = 0;
    // for (ShoppingCart shoppingCart : shoppingCartList) {
    // int tempNum = shoppingCart.getGoodsNumber();
    // allNum += tempNum;
    // }
    // List<GoodsWholsale> goodsWholsaleList =
    // goodsWholsaleManager.getGoodsWholsalelistByGoodsId(goods.getId());
    // if (goodsWholsaleList != null) {
    // if (goodsWholsaleList.size() == 1) {
    // Long oneS = goodsWholsaleList.get(0).getStartNum();
    // double wholeOnePrice = goodsWholsaleList.get(0).getWholesalePrice();
    // if (allNum < oneS) {
    // result.setMessage(getText("goods.numwrong.required"));
    // result.setResult(1);
    // return result;
    // } else {
    // goods.setWholesalePrice(wholeOnePrice);
    // }
    // } else if (goodsWholsaleList.size() == 2) {
    // Long oneS = goodsWholsaleList.get(0).getStartNum();
    // Long oneE = goodsWholsaleList.get(0).getEndNum();
    // double wholeOnePrice = goodsWholsaleList.get(0).getWholesalePrice();
    // double wholeTwoPrice = goodsWholsaleList.get(1).getWholesalePrice();
    // if (allNum < oneS) {
    // result.setMessage(getText("goods.numwrong.required"));
    // result.setResult(1);
    // return result;
    // } else if ((oneS <= allNum) && (allNum <= oneE)) {
    // goods.setWholesalePrice(wholeOnePrice);
    // } else {
    // goods.setWholesalePrice(wholeTwoPrice);
    // }
    // } else if (goodsWholsaleList.size() == 3) {
    // Long oneS = goodsWholsaleList.get(0).getStartNum();
    // Long oneE = goodsWholsaleList.get(0).getEndNum();
    // Long TwoS = goodsWholsaleList.get(1).getStartNum();
    // Long TwoE = goodsWholsaleList.get(1).getEndNum();
    // double wholeOnePrice = goodsWholsaleList.get(0).getWholesalePrice();
    // double wholeTwoPrice = goodsWholsaleList.get(1).getWholesalePrice();
    // double wholeThreePrice = goodsWholsaleList.get(2).getWholesalePrice();
    // if (allNum < oneS) {
    // result.setMessage(getText("goods.numwrong.required"));
    // result.setResult(1);
    // return result;
    // } else if ((oneS <= allNum) && (allNum <= oneE)) {
    // goods.setWholesalePrice(wholeOnePrice);
    // } else if ((TwoS <= allNum) && (allNum <= TwoE)) {
    // goods.setWholesalePrice(wholeTwoPrice);
    // } else {
    // goods.setWholesalePrice(wholeThreePrice);
    // }
    // }
    // }
    // // ���뵽������֯��List��
    // for (ShoppingCart shoppingCart : shoppingCartList) {
    // shoppingCart.setWholesalePrice(goods.getWholesalePrice());
    // if (shoppingCart.getGoodsNumber() > 0) {
    // goodsCartFinalList.add(shoppingCart);
    // }
    // }
    // }
    //
    // // /��������
    // if (userAddress.getId() == -1) {
    // trade.setProvince(userAddress.getProvince());
    // trade.setCity(userAddress.getCity());
    // trade.setDistrict(userAddress.getDistrict());
    // /* ���ջ���ַ��ӵ��ҵ��ջ���ַ�б�? */
    // List<UserAddress> tradeAddresses =
    // userAddressManager.getTradeAddressByUserId(user.getId());
    // /* ����ҵ��ջ���ַ����5���򽫴˼�¼��¼���ҵ��ջ���ַ�У������Թ� */
    // if (tradeAddresses.size() < 5) {
    // userAddress.setUserId(user.getId());
    // userAddress.setContextName(trade.getReceiver());
    // if (EmallStringUtil.isMobilePhone(trade.getMobile())) {
    // userAddress.setMobile(trade.getMobile());
    // } else {
    // userAddress.setTel(trade.getMobile());
    // }
    // userAddress.setAddress(trade.getAddress());
    // userAddress.setZipcode(trade.getZipcode());
    // userAddress.setId(0L);
    // userAddressManager.addUserAddress(userAddress);
    // }
    //
    // }
    // if (userAddress.getId() != -1) {
    // userAddress = userAddressManager.getUserAddress(userAddress.getId());
    // trade.setReceiver(userAddress.getContextName());
    // trade.setCountry(userAddress.getCountry());
    // trade.setProvince(userAddress.getProvince());
    // trade.setCity(userAddress.getCity());
    // trade.setDistrict(userAddress.getDistrict());
    // trade.setAddress(userAddress.getAddress());
    // trade.setZipcode(userAddress.getZipcode());
    // if (!EmallStringUtil.isBlank(userAddress.getMobile())) {
    // trade.setMobile(userAddress.getMobile());
    // } else {
    // trade.setMobile(userAddress.getTel());
    // }
    // }
    //
    // trade.setTid(codeManager.buildCode(codeManager.TRADE_CODE));
    // trade.setBuyId(user.getId());
    // trade.setBuyNick(user.getAccount());
    // Long shopId = Constants.SHOP_ID;
    // trade.setShopId(shopId);
    // ShopInfo shopInfo = shopInfoManager.getShopInfo(shopId);
    // Admin admin_seller = adminManager.getAdminUserById(shopInfo.getUserId());
    // trade.setSellerId(admin_seller.getId());
    // trade.setSellerNick(admin_seller.getName());
    // trade.setPayStatus(EnumTradePayStatus.NO_PAY.getKey());
    // trade.setType("3");
    // trade.setStatus(EnumWholesaleListStatus.WAIT_BUYER_PAY.getKey());
    // trade.setWholesaleStatus(EnumWholesaleListStatus.WAIT_CONFIRM.getKey());
    // trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST.getKey());
    //
    // List<Order> orderList = new ArrayList<Order>();
    // double goodsAmount = 0.00;
    // for (ShoppingCart tmp : goodsCartFinalList) {
    // Order order = new Order();
    // Goods goods = goodsManager.getGoods(tmp.getGoodsId());
    // GoodsInstance goodsInstance =
    // goodsInstanceManager.getInstance(tmp.getGoodsInstanceId());
    // order.setGoodsAttr(goods.getAttrDesc());
    // order.setGoodsId(goods.getId());
    // order.setGoodsNumber(tmp.getGoodsNumber());
    // order.setGoodsPrice(tmp.getWholesalePrice());
    // order.setMarketPrice(goods.getGoodsPrice());
    // order.setGoodsTitle(goods.getTitle());
    // order.setGoodWeight(goods.getGoodsWeight());
    // order.setRefundStauts("");
    // order.setTid(trade.getTid());
    // order.setShippingAmount(0);
    // order.setGoodsInstanceId(tmp.getGoodsInstanceId());
    // order.setBuyAttr(goodsInstance.getAttrDesc());
    // orderList.add(order);
    // double orderGoodsAllPrice = tmp.getWholesalePrice() *
    // tmp.getGoodsNumber();
    // goodsAmount += orderGoodsAllPrice;
    // goodsManager.updateSaleNumberById(tmp.getGoodsId(), new
    // Long(tmp.getGoodsNumber()));
    // }
    // trade.setGoodsAmount(DoubleUtil.round(goodsAmount, 2));
    // trade.setAmount(DoubleUtil.round(goodsAmount, 2));
    // trade.setIsWholesale("w");
    // addTrade(trade);
    // orderManager.addOrders(orderList);
    //
    // // ����ύ�Ĺ��ﳵ��Ϣ
    // for (ShoppingCart tmp : goodsCartFinalList) {
    // shoppingCartManager.removeShoppingCart(tmp.getId());
    // }
    // result.setResult(0);
    // return result;
    // }
    //
    public double countAllGoodsAmount(Map parMap) {
        return tradeDao.countAllGoodsAmount(parMap);
    }

    public double countAllShipAmount(Map parMap) {
        return tradeDao.countAllShipAmount(parMap);
    }

    //
    // public List<TradeSalesCount> listTradeSalesCount() {
    // if (log.isInfoEnabled()) {
    // log.info("TradeManagerImpl listTradeSalesCount");
    // }
    // return tradeDao.listTradeSalesCount();
    // }

    @SuppressWarnings("unchecked")
    public void updateWsShippingAndDepositoryById(Double shippingAmount, Double amount,
                                                  String memo, Long depFirstId, Long id) {
        Map map = new HashMap();
        map.put("gmtNow", new Date());
        map.put("shippingAmount", shippingAmount);
        map.put("amount", amount);
        map.put("depFirstId", depFirstId);
        map.put("id", id);
        map.put("wholesaleStatus", WHOLESALESTATUS_CONFIRMED);
        map.put("memo", memo);
        tradeDao.updateWsShippingAndDepositoryById(map);
    }

    public double getTodayPeriodMoney(Map parMap) {
        try {
            return this.tradeDao.getTodayPeriodMoney(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    //
    // public UserDao getUserDao() {
    // return userDao;
    // }
    //
    // public void setUserDao(UserDao userDao) {
    // this.userDao = userDao;
    // }
    //
    // /**
    // * �����Ա��������� add by wangkun 2010-09-28
    // */
    // @Transactional
    // public void saveFenxiaoTrade(Long depFirstId, Long userId, Trade trade) {
    // try {
    //
    // Region region1 = regionDao.getRegionByName(trade.getProvince());
    // Region region2 = regionDao.getRegionByName(trade.getCity());
    // Region region3 = regionDao.getRegionByName(trade.getDistrict());
    //
    // trade.setProvince(region1.getCode());
    // trade.setCity(region2.getCode());
    // trade.setDistrict(region3.getCode());
    //
    // // ������Ϣ
    // User user = userDao.getUserById(userId);
    // String tid = codeManager.buildCode(codeManager.TRADE_CODE);
    // // added by chenyan 2011/02/18 ת���Ա�����������tid
    // tid = convertTidByTradeFrom(tid,
    // EnumTradeFrom.TRADE_FROM_FENXIAO.getKey());
    // trade.setTid(tid);
    // trade.setBuyId(user.getId());
    // trade.setBuyNick(user.getAccount());
    // Long shopId = Constants.SHOP_ID;
    // trade.setShopId(shopId);
    // ShopInfo shopInfo = shopInfoManager.getShopInfo(shopId);
    // Admin admin_seller = adminManager.getAdminUserById(shopInfo.getUserId());
    // trade.setSellerId(admin_seller.getId());
    // trade.setSellerNick(admin_seller.getName());
    // trade.setPayStatus(EnumTradePayStatus.PAID.getKey());
    // trade.setType("1");
    // // ������ʼ��ַ��ʼ��������
    // String regionCodeStart = "330782";
    // // ��ȡһ���ֿ���Ϣ
    // DepositoryFirst depfirst =
    // depositoryFirstManager.getDepositoryById(depFirstId);
    // if (null != depfirst) {
    // regionCodeStart = depfirst.getRegionCode();
    // }
    // trade.setDepFirstId(depFirstId);
    //
    // List<Order> orderList = trade.getOrderList();
    // List<Order> newOrderList = new ArrayList<Order>();
    // double goodsAmount = 0;
    // double weight = 0;
    // if (orderList != null && orderList.size() > 0) {
    // for (Order order : orderList) {
    // if (StringUtils.isNotBlank(order.getCode())) {
    // GoodsInstance goodsIns =
    // goodsInstanceManager.getInstance(NumberUtils.toLong(order.getCode())); //
    // .getInstanceByCode();
    // if (goodsIns != null) {
    // Goods goods = this.goodsManager.getGoods(goodsIns.getGoodsId());
    // order.setGoodsAttr(goods.getAttrDesc());
    // order.setGoodsId(goods.getId());
    // if (goodsIns.getType() != null && goodsIns.getType().equals("zp")) {
    // order.setGoodsPrice(0);
    // order.setOrderType(1);
    // order.setMarketPrice(goods.getGoodsPrice());
    // } else {
    // order.setGoodsPrice(goods.getAgentPrice());
    // order.setMarketPrice(goods.getGoodsPrice());
    // }
    // goodsAmount += goods.getAgentPrice() * order.getGoodsNumber();// ��Ʒ���׽���ۼ�
    // weight += goods.getGoodsWeight() * order.getGoodsNumber();// ��Ʒ�����ۼ�
    // order.setGoodsTitle(goods.getTitle());
    // order.setGoodWeight(goods.getGoodsWeight());
    // order.setRefundStauts("");
    // order.setTid(trade.getTid());
    // order.setShippingAmount(0);
    // order.setGoodsInstanceId(goodsIns.getId());
    // order.setBuyAttr(goodsIns.getAttrDesc());
    // newOrderList.add(order);
    // // ֻ�ж���״̬������Ѹ����ʱ��ż����ÿ��
    // if (StringUtils.equals(trade.getStatus(),
    // EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey())) {
    // // ���¿��ÿ�� Ҫ���ϲֿ�ID
    // boolean flag = goodsInstanceManager.updateAmountForTwo(goodsIns.getId(),
    // goodsIns.getGoodsId(), (0 - order.getGoodsNumber()), depfirst.getId(),
    // true); // ,
    // // new
    // // String[]{"fenxiao",
    // // order.getItemId()}
    // // ������������
    // goodsManager.updateSaleNumberById(goodsIns.getGoodsId(),
    // order.getGoodsNumber());
    // // �����Ա�����ƽ̨��Ʒ����
    // if (flag) {
    // Resources resources =
    // resourcesManager.getResourcesByTypeAndName("taobao",
    // "depFirstId");
    // if (depFirstId != null && Long.parseLong(resources.getValue()) ==
    // depFirstId) {
    // if (goodsIns.getGoodsId() != null) {
    // TaobaoInterfaceSync interfaceSync = new TaobaoInterfaceSync();
    // interfaceSync
    // .setInterfaceType(EnumInterfaceName.TAOBAOFENXIAOSYNC.getKey());
    // interfaceSync.setItemId(order.getItemId());
    // interfaceSync.setGoodsId(goodsIns.getGoodsId());
    // interfaceSync.setGoodsInstanceId(goodsIns.getId());
    // interfaceSync.setUserId(-2l);
    // interfaceSync.setStatus("n");
    // taobaoInterfaceSyncManager.addInterfaceSync(interfaceSync);
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // double shippingAmount = this.shippingAmount(trade, weight, 0,
    // regionCodeStart);
    // trade.setShippingAmount(shippingAmount);
    // trade.setGoodsAmount(goodsAmount);
    // trade.setAmount(goodsAmount + shippingAmount);
    // trade.setIsWholesale("n");
    // addTrade(trade);
    // orderManager.addOrders(newOrderList);
    // tradeAgentManager.updateAgentTradeWithAll(trade.getTid(),
    // trade.getStatus(), "golist");
    // // �����ض����Ÿ��µ�����ͬ������
    // //
    // interfaceUserTradeDao.editInterfaceUserTradeByDealCode(trade.getDealCode(),tid);
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // }
    //
    @Autowired
    ResourcesManager resourcesManager;

    /**
     * �Ա��ӿ�ͬ�����ɶ���
     */
    @Transactional
    public void saveTaobaoTrade(Long depFirstId, Long userId, Long ownExpressId,
                                String interfaceExpressCode, Trade trade) {

        // Ĭ��������ʼ������Ϊ������
        String regionCodeStart = "330782";
        DepositoryFirst depfirst = depositoryFirstManager.getDepositoryById(depFirstId);
        if (null != depfirst) {
            regionCodeStart = depfirst.getRegionCode();
        }
        // Ĭ�����ڽ���ʹ�ÿ����
        String expressPayment = "payment_first";

        try {// �������������쳣
            try {
                // ������ַ��Ϣ
                Region region1 = regionDao.getRegionByName(trade.getProvince());
                Region region2 = regionDao.getRegionByName(trade.getCity());
                Region region3 = regionDao.getRegionByName(trade.getDistrict());

                if (region1 != null)
                    trade.setProvince(region1.getCode());
                if (region2 != null)
                    trade.setCity(region2.getCode());
                if (region3 != null)
                    trade.setDistrict(region3.getCode());
            } catch (Exception e) {
                log.error("ת��������ַ��������������ʱ����" + "�Ա�����ID:" + trade.getDealCode() + "Province:"
                          + trade.getProvince() + "City:" + trade.getCity() + "District:"
                          + trade.getDistrict() + ";" + e.getMessage());
                return;
            }

            //address���жϣ�����"��"��"��"��,��¼���� zhangwy 2011/05/23
            if (StringUtil.isNotBlank(trade.getAddress())
                && (trade.getAddress().contains("��") || trade.getAddress().contains("��"))) {
                StringBuffer temp = new StringBuffer();
                if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                    temp.append(trade.getSeviceNote()).append("(��ַ�а��������)");
                } else {
                    temp.append("(��ַ�а��������)");
                }
                trade.setSeviceNote(temp.toString());
            }

            /* begin add by shenzh Oct 29, 2010 ˵���� ���������˾ID */
            try {
                // modified by chenyan 2011/04/11 �����ж������Ƿ�ɴ���ɴ����õڶ�����
                // modified by chenyan 2011/03/11 start ���ݵ���ָ��������˾
                Long expressIdTemp = ownExpressId;
                if (ownExpressId == null) {
                    Resources resources = resourcesManager.getResourcesByTypeAndName("taobao",
                        "expressId");
                    expressIdTemp = Long.parseLong(resources.getValue());
                }
                // �жϵ�ǰ��ַ������˾�Ƿ�ɴ�
                List<ExpressDist> expressDistListTemp = expressDistManager
                    .listExpressDistByRegionCodeEnd(regionCodeStart, trade.getDistrict(),
                        expressPayment, expressIdTemp);
                if (expressDistListTemp != null && expressDistListTemp.size() > 0) {
                    // ��ǰ�����ɴ�����
                    trade.setExpressId(expressIdTemp);
                    trade.setInterfaceExpressCode(interfaceExpressCode);
                } else {
                    // ��ǰ�������ɴ�������ֱ��ʹ����Դ��ĵڶ�����
                    Resources resourcesLocal = resourcesManager.getResourcesByTypeAndName("taobao",
                        "secondLocalExpress");
                    Resources resourcesTaobao = resourcesManager.getResourcesByTypeAndName(
                        "taobao", "secondTaobaoExpress");
                    trade.setExpressId(Long.parseLong(resourcesLocal.getValue()));
                    trade.setInterfaceExpressCode(resourcesTaobao.getValue());
                }
                // modified by chenyan 2011/03/11 end
                // modified by chenyan 2011/04/11 end
            } catch (Exception e) {
                log.error("ȡ������˾IDʱ����" + "�Ա�����ID:" + trade.getDealCode() + ";" + e.getMessage());
                return;
            }
            /* begin modify by shenzh Nov 12, 2010 ˵���� */
            trade.setExpressPayment(EnumExpressDistPayment.PERIOD_PAY.getKey());// ֧����ʽ
                                                                                // Ϊ���ڽ���
            /* end by shenzh Nov 12, 2010 */
            /* end by shenzh Oct 29, 2010 */
            // ������Ϣ
            User user = new User();
            try {
                user = userDao.getUserById(userId);
            } catch (Exception e) {
                log.error("ȡ�û���Ϣʱ����" + "�Ա�����ID:" + trade.getDealCode() + "userId:" + userId + ";"
                          + e.getMessage());
                return;
            }
            String tid = "";
            try {
                tid = codeManager.buildCode(codeManager.TRADE_CODE);
                // added by chenyan 2011/02/18 ת���Ա�����������tid
                tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_TAOBAO.getKey());
            } catch (Exception e) {
                log.error("����TIDʱ����[codeManager.buildCode]��" + "�Ա�����ID:" + trade.getDealCode()
                          + "userId:" + userId + "type:" + CodeManager.TRADE_CODE + ";"
                          + e.getMessage());
                return;
            }
            trade.setTid(tid);
            trade.setBuyId(user.getId());

            // added by chenhang 2010/12/15
            if ("y".equals(user.getInvoice())) {
                trade.setInvoice("y");
            }

            trade.setBuyNick(user.getAccount());
            Long shopId = Constants.SHOP_ID;
            trade.setShopId(shopId);
            ShopInfo shopInfo = shopInfoService.getShopInfo(shopId);
            Admin admin_seller = adminService.getAdminById(shopInfo.getUserId());
            trade.setSellerId(admin_seller.getId());
            trade.setSellerNick(admin_seller.getName());
            /* begin modify by shenzh Nov 12, 2010 ˵���� ��Ϊδ���� */
            trade.setPayStatus(EnumTradePayStatus.NO_PAY.getKey());
            /* end by shenzh Nov 12, 2010 */
            trade.setType("1");
            // trade.setStatus(EnumTradeStatus.WAIT_BUYER_PAY.getKey());

            // ���ö�������TRADE_TYPE(0��ǰ̨�µ���1����̨�µ���2�����Ķ��� 3:�Ա����� 4���Ա�����ƽ̨)
            trade.setTradeType(3);

            trade.setDepFirstId(depFirstId);

            List<Order> orderList = trade.getOrderList();
            List<Order> newOrderList = new ArrayList<Order>();
            double goodsAmount = 0;
            double weight = 0;
            boolean isSalesProPrice = false;//�Ƿ��д����۸��ж� zhangwy 2011/05/23
            if (orderList != null && orderList.size() > 0) {
                for (Order order : orderList) {
                    if (StringUtil.isNotBlank(order.getCode())) {
                        GoodsInstance goodsIns = goodsInstanceManager.getInstanceByCode(order
                            .getCode());
                        if (goodsIns != null) {
                            Goods goods = this.goodsManager.getGoods(goodsIns.getGoodsId());
                            order.setGoodsAttr(goods.getAttrDesc());
                            order.setGoodsId(goods.getId());
                            if (goodsIns.getType() != null && goodsIns.getType().equals("zp")) {
                                order.setGoodsPrice(0);
                                order.setOrderType(1);
                                order.setMarketPrice(goods.getGoodsPrice());
                            } else {
                                /*
                                 * begin delete by shenzh Oct 29, 2010 ˵����
                                 * �۸�����Ա��ϵ��ۼ� �ڶ���ת��ʱ������
                                 */
                                // order.setGoodsPrice(goods.getAgentPrice());
                                /* end by shenzh Oct 29, 2010 */
                                order.setMarketPrice(goods.getGoodsPrice());
                            }
                            if ((!isSalesProPrice)
                                && (order.getGoodsPrice() < goods.getSalesProPrice())) {
                                isSalesProPrice = true;
                            }
                            /* begin modify by shenzh Oct 29, 2010 ˵���� ��Ʒ�۸�ȡ�Ա��ۼ� */
                            // goodsAmount += goods.getAgentPrice() *
                            // order.getGoodsNumber();// ��Ʒ���׽���ۼ�
                            goodsAmount += order.getGoodsPrice() * order.getGoodsNumber();// ��Ʒ���׽���ۼ�
                            /* end by shenzh Oct 29, 2010 */
                            weight += goods.getGoodsWeight() * order.getGoodsNumber();// ��Ʒ�����ۼ�
                            order.setGoodsTitle(goods.getTitle());
                            order.setGoodWeight(goods.getGoodsWeight());
                            order.setRefundStauts("");
                            order.setTid(trade.getTid());
                            order.setShippingAmount(0);
                            order.setGoodsInstanceId(goodsIns.getId());
                            order.setBuyAttr(goodsIns.getAttrDesc());
                            newOrderList.add(order);
                            // ֻ�ж���״̬�ǵȴ����֧����ʱ��ż����ÿ��
                            /* modify by shenzh Oct 29, 2010 ˵�������ȴ������Ϊ�ȴ����״̬ */
                            if (EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(
                                trade.getStatus())) {
                                try {
                                    // ���¿��ÿ�� Ҫ���ϲֿ�ID
                                    goodsInstanceManager.updateAmountForTwo(goodsIns.getId(),
                                        goodsIns.getGoodsId(), (0 - order.getGoodsNumber()),
                                        depfirst.getId(), true);
                                } catch (Exception e) {
                                    log.error("����TIDʱ����[goodsInstanceManager.updateAmountForTwo]��"
                                              + "�Ա�����ID:" + trade.getDealCode() + "userId:"
                                              + userId + "goodsInsId:" + goodsIns.getId()
                                              + "goodsId:" + goodsIns.getGoodsId() + "depfirstId:"
                                              + depfirst.getId() + ";" + e.getMessage());
                                    return;
                                }
                                try {
                                    // ������������
                                    goodsManager.updateSaleNumberById(goodsIns.getGoodsId(),
                                        order.getGoodsNumber());
                                } catch (Exception e) {
                                    log.error("����TIDʱ����[goodsManager.updateSaleNumberById]��"
                                              + "�Ա�����ID:" + trade.getDealCode() + "goodsInsId:"
                                              + goodsIns.getId() + "userId:" + userId + ";"
                                              + e.getMessage());
                                    return;
                                }
                            }
                        }
                    }
                }
                /* begin modify by shenzh Oct 29, 2010 ˵���� �����¼����ʷ� */
                // double shippingAmount = this.shippingAmount(trade, weight,
                // regionCodeStart);
                // double shippingAmount = trade.getShippingAmount();
                /* end by shenzh Oct 29, 2010 */
                /* begin delete by shenzh Nov 12, 2010 ˵���� ȫ�����Ա�������Ϣ��ȡ�� */
                // trade.setShippingAmount(shippingAmount);
                // trade.setGoodsAmount(goodsAmount);
                // trade.setAmount(goodsAmount + shippingAmount);
                /* end by shenzh Nov 12, 2010 */
                if (isSalesProPrice) {
                    StringBuffer temp = new StringBuffer();
                    if (StringUtil.isNotBlank(trade.getSeviceNote())) {
                        temp.append(trade.getSeviceNote()).append("(����������Ʒ�ļ۸�С�ڸ���Ʒ�Ĵ�����)");
                    } else {
                        temp.append("(����������Ʒ�ļ۸�С�ڸ���Ʒ�Ĵ�����)");
                    }
                    trade.setSeviceNote(temp.toString());
                }

                trade.setIsWholesale("n");
                addTrade(trade);
                orderManager.addOrders(newOrderList);
                try {
                    /*
                     * begin delete by shenzh Dec 23, 2010 ˵����
                     * ���ø÷�����ʱ���׳�һ��returned too many results���쳣������ԭ����
                     * ����Ŀǰ��ȯ�����Ѳ���Ҫ������ע�ʹ����
                     */
                    // tradeAgentManager.updateAgentTradeWithAll(trade.getTid(),
                    // trade.getStatus(), "golist");
                    /* end by shenzh Dec 23, 2010 */
                } catch (Exception e) {
                    log.error("��ȯ�������[tradeAgentManager.updateAgentTradeWithAll]��" + "�Ա�����ID:"
                              + trade.getDealCode() + "userId:" + userId + "���ݵĲ�������Ҫ���ɵı��ض���ID:"
                              + trade.getTid() + "��Ҫ���ɵĶ���״̬��:" + trade.getStatus() + "saveType:"
                              + "golist��" + ";" + e.getMessage());
                    return;
                }
                try {
                    // �����ض����Ÿ��µ�����ͬ������
                    interfaceUserTradeDao
                        .editInterfaceUserTradeByDealCode(trade.getDealCode(), tid);
                } catch (Exception e) {
                    log.error("�����ض����Ÿ��µ�����ͬ����ʱ����" + "�Ա�����ID:" + trade.getDealCode() + "userId:"
                              + userId + "���ض���ID:" + trade.getTid() + ";" + e.getMessage());
                    return;
                }
            }
        } catch (Exception e) {
            log.error("��������δ������쳣��");
            e.printStackTrace();
        }
    }

    //
    // public List<Trade> setTradeAmount(List<Trade> tradeList) {
    // if (CollectionUtils.isNotEmpty(tradeList)) {
    // for (Trade trade : tradeList) {
    // List<Order> orderList = trade.getOrderList();
    // double goodsAmount = 0;
    // double weight = 0;
    // for (Order order : orderList) {
    // GoodsInstance goodsIns =
    // goodsInstanceManager.getInstance(NumberUtils.toLong(order.getCode())); //
    // .getInstanceByCode(order.getCode());
    // Goods goods = goodsManager.getGoods(goodsIns.getGoodsId());
    // goodsAmount += goods.getAgentPrice() * order.getGoodsNumber();// ��Ʒ���׽���ۼ�
    // weight += goods.getGoodsWeight() * order.getGoodsNumber();// ��Ʒ�����ۼ�
    // }
    // // ������ʼ��ַ��ʼ��������
    // String regionCodeStart = "330782";
    // // ��ȡһ���ֿ���Ϣ
    // DepositoryFirst depfirst = depositoryFirstManager.getDepositoryById(62l);
    // if (null != depfirst) {
    // regionCodeStart = depfirst.getRegionCode();
    // }
    // trade.setDepFirstId(62l);
    // double shippingAmount = this.shippingAmount(trade, weight, 0,
    // regionCodeStart);
    // trade.setShippingAmount(shippingAmount);
    // trade.setGoodsAmount(goodsAmount);
    // trade.setAmount(goodsAmount + shippingAmount);
    // }
    // }
    //
    // return tradeList;
    // }
    //
    // /**
    // * ��������ۿۼ۸�
    // *
    // * @param user
    // * User
    // * @param goods
    // * Goods
    // * @return double
    // * @author modified by chenyan 2010/07/02
    // */
    // public double getAgioPrice(Goods goods) {
    // try {
    // // �ſ����۴����ײ��ۿ�
    // double agio = promationManager.getGoodsDiscount(goods.getId());
    // if (agio > 0) {
    // double goodsPrice = goods.getAgentPrice();
    // java.math.BigDecimal f1 = new java.math.BigDecimal(goodsPrice * agio);//
    // С�������2λ��4��5��
    // f1 = f1.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
    // return f1.doubleValue();
    // } else {
    // return goods.getAgentPrice();
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // return goods.getGoodsPrice();
    // }

    public void editTradeWithDepFirstId(Trade trade) {
        tradeDao.editTradeWithDepFirstId(trade);
    }

    //
    // public InterfaceUserTradeDao getInterfaceUserTradeDao() {
    // return interfaceUserTradeDao;
    // }
    //
    // public void setInterfaceUserTradeDao(InterfaceUserTradeDao
    // interfaceUserTradeDao) {
    // this.interfaceUserTradeDao = interfaceUserTradeDao;
    // }
    //
    // public RefundDao getRefundDao() {
    // return refundDao;
    // }
    //
    // public GoodsPointManager getGoodsPointManager() {
    // return goodsPointManager;
    // }
    //
    // public AccountManager getAccountManager() {
    // return accountManager;
    // }
    //
    // public AccountTransManager getAccountTransManager() {
    // return accountTransManager;
    // }
    //
    // public String getRemoteTradeIdByTid(String tid) {
    // return tradeDao.getRemoteTradeIdByTid(tid);
    // }
    //
    /**
     * �����������ɲ�ͬ��ʶ��tid
     *
     * @param tid
     *            String
     * @return String
     * @author chenyan 2011/02/18
     */
    private String convertTidByTradeFrom(String tid, String tradeFrom) {
        String convertTid = tid;
        if (StringUtil.isNotBlank(tid) && tid.length() > 8) {
            convertTid = tid.substring(0, 8) + (StringUtil.isBlank(tradeFrom) ? "" : tradeFrom)
                         + tid.substring(8);
        }
        return convertTid;
    }

    public List<Trade> getTradeCountWithStatus() {
        return tradeDao.getTradeCountWithStatus();
    }

    public List<Trade> getTradesExcelByParameterMap(TradeListQuery query) {
        if (log.isInfoEnabled()) {
            log.info("TradeManagerImpl getTradesExcelByParameterMap");
        }
        return tradeDao.getTradesExcelByParameterMap(query);
    }

    @Override
    public Map<String, Long> getTradeGoodsBill(List<String> tids, Long rorder) {
        return tradeDao.getTradeGoodsBill(tids, rorder);
    }

    @Override
    public List<Object> readExcel(MultipartFile filePath, Long userId) {
        InputStream is = null;

        List<Object> doUploadTradeList = new ArrayList<Object>();
        String message = "";
        StringBuffer sbMsg = new StringBuffer();
        int success = 0;
        int failure = 0;
        try {
            // ����Workbook����, ֻ��Workbook����
            // ֱ�Ӵӱ����ļ�����Workbook
            // ������������Workbook
            is = filePath.getInputStream();
            // ���workbook ������
            // rwb = Workbook.getWorkbook(is);
            // // Workbook rwb = Workbook.getWorkbook(new File(sourcefile));
            // // ��ȡSheet ������
            InputStreamReader inputReader = new InputStreamReader(is, "gbk");
            CsvReader reader = new CsvReader(inputReader, ',');
            reader.readHeaders();
            int count = 0;
            while (reader.readRecord()) {
                DoUploadTrade doUploadTrade = new DoUploadTrade();
                boolean flag = rowToGoods(doUploadTrade, reader, count, sbMsg, userId);
                if (!flag) {// ���������֤��ͨ������ֱ�ӷ��ش�����Ϣ
                    message = sbMsg.toString();
                    failure++;
                } else {
                    GoodsInstance goodsInstanceTemp = goodsInstanceDao
                        .getInstanceByCode(doUploadTrade.getGoodsSn());
                    doUploadTrade.setMarketPrice(goodsInstanceTemp.getMarketPrice());
                    doUploadTrade.setGoodsId(goodsInstanceTemp.getGoodsId());
                    doUploadTrade.setGoodsInstanceId(goodsInstanceTemp.getId());
                    doUploadTrade.setId(goodsInstanceTemp.getId());
                    doUploadTrade.setBuyAttr(goodsInstanceTemp.getAttrDesc());
                    success++;
                    doUploadTradeList.add(doUploadTrade);
                }
                count++;
            }
            if (count <= 2000) {
                List<DoUploadTrade> doUploadTradeList1 = new ArrayList<DoUploadTrade>();
                for (Object p : doUploadTradeList) {
                    doUploadTradeList1.add((DoUploadTrade) p);
                }
                Collections.sort(doUploadTradeList1);

                List<Trade> tradeList = new ArrayList<Trade>();
                Trade trade = new Trade();
                List<Order> orderList = new ArrayList<Order>();
                int i = 0;
                while (i < doUploadTradeList1.size()) {
                    if (i == 0) {
                        trade = doUploadTradeList1.get(i).getTradeObject();
                        orderList.add(doUploadTradeList1.get(i).getOrderObject());
                        if (1 == doUploadTradeList1.size()) {
                            tradeList.add(trade);
                            trade.setOrderList(orderList);
                        }
                    } else {
                        if (doUploadTradeList1.get(i).getPaipaiTradeId()
                            .equals(doUploadTradeList1.get(i - 1).getPaipaiTradeId())) {
                            orderList.add(doUploadTradeList1.get(i).getOrderObject());
                            if (i == (doUploadTradeList1.size() - 1)) {
                                trade.setOrderList(orderList);
                                tradeList.add(trade);
                            }
                        } else {
                            trade.setOrderList(orderList);
                            tradeList.add(trade);
                            orderList = new ArrayList<Order>();
                            trade = new Trade();
                            trade = doUploadTradeList1.get(i).getTradeObject();
                            orderList.add(doUploadTradeList1.get(i).getOrderObject());
                            if (i == (doUploadTradeList1.size() - 1)) {
                                trade.setOrderList(orderList);
                                tradeList.add(trade);
                            }
                        }
                    }
                    i++;
                }

                // Map<Long,String> depositoryNameMapTemp = new
                // HashMap<Long,String>();
                // List<DepositoryFirst> depositoryFirstList =
                // depositoryFirstManager
                // .getDepositoryFirstList();
                // for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                // depositoryNameMapTemp.put(depositoryFirst.getId(),depositoryFirst.getDepFirstName()
                // );
                // }

                for (Trade tmp : tradeList) {
                    // Boolean fl = true;// �ж��Ƿ�ȱ����־
                    double tempFee = 0.00;
                    tmp.setStatus("wait_seller_send_goods");
                    tmp.setExpressPayment("period_pay");
                    tmp.setPayStatus("no_pay");
                    //					tid = convertTidByTradeFrom(tid,
                    //							EnumTradeFrom.TRADE_FROM_PAIPAI.getKey());
                    String tid = codeManager.buildCode(codeManager.TRADE_CODE);
                    if (EnumTradeType.trade_type_4.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_FENXIAO.getKey());
                    } else if (EnumTradeType.trade_type_2.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_PAIPAI.getKey());
                    } else if (EnumTradeType.trade_type_3.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_TAOBAO.getKey());
                    } else if (EnumTradeType.trade_type_5.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_YYG.getKey());
                    } else if (EnumTradeType.trade_type_6.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_LKT.getKey());
                    } else if (EnumTradeType.trade_type_7.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_ZYKG.getKey());
                    } else if (EnumTradeType.trade_type_8.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_TG.getKey());
                    } else if (EnumTradeType.trade_type_9.getValue().equals(tmp.getTradeType())) {
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_XYDG.getKey());
                    } else{
                        tid = convertTidByTradeFrom(tid, EnumTradeFrom.TRADE_FROM_OTHERS.getKey());
                    }
                    tmp.setTid(tid);
                    //address���жϣ�����"��"��"��"��,��¼���� zhangwy 2011/05/23
                    if (StringUtil.isNotBlank(tmp.getAddress())
                        && (tmp.getAddress().contains("��") || tmp.getAddress().contains("��"))) {
                        StringBuffer temp = new StringBuffer();
                        if (StringUtil.isNotBlank(tmp.getSeviceNote())) {
                            temp.append(tmp.getSeviceNote()).append("(��ַ�а��������)");
                        } else {
                            temp.append("(��ַ�а��������)");
                        }
                        tmp.setSeviceNote(temp.toString());
                    }
                    boolean isSalesProPrice = false;//�Ƿ��д����۸��ж� zhangwy 2011/05/23
                    for (Order t : tmp.getOrderList()) {
                        t.setTid(tmp.getTid());
                        tempFee = tempFee + t.getGoodsPrice() * t.getGoodsNumber();
                        Goods goods = goodsManager.getGoods(t.getGoodsId());
                        t.setGoodWeight(goods.getGoodsWeight());
                        AvailableStock availableStock = availableStockDao.getAvailableStock(
                            t.getGoodsInstanceId(), tmp.getDepFirstId());
                        if (null == availableStock) {
                            tmp.setStockoutStatus("y");
                            tmp.setStatus("trade_close");
                        } else {
                            Long availableGoodsNumber = availableStock.getGoodsNumber();
                            if (null != availableGoodsNumber
                                && availableGoodsNumber >= t.getGoodsNumber()) {
                                goodsInstanceManager.updateAmountForTwo(t.getGoodsInstanceId(),
                                    t.getGoodsId(), -t.getGoodsNumber(), tmp.getDepFirstId(), true);
                            } else {
                                tmp.setStockoutStatus("y");
                                tmp.setStatus("trade_close");
                            }
                        }
                        if ((!isSalesProPrice) && (t.getGoodsPrice() < goods.getSalesProPrice())) {
                            isSalesProPrice = true;
                        }
                    }
                    if (isSalesProPrice) {
                        StringBuffer temp = new StringBuffer();
                        if (StringUtil.isNotBlank(tmp.getSeviceNote())) {
                            temp.append(tmp.getSeviceNote()).append("(����������Ʒ�ļ۸�С�ڸ���Ʒ�Ĵ�����)");
                        } else {
                            temp.append("(����������Ʒ�ļ۸�С�ڸ���Ʒ�Ĵ�����)");
                        }
                        tmp.setSeviceNote(temp.toString());
                    }
                    User u = userDao.getUserById(userId);
                    tmp.setBuyNick(u.getAccount());
                    tmp.setShopId(1);
                    tmp.setBuyId(userId);
                    tmp.setSellerId(-2);
                    Admin admin = adminService.getAdminById(-2L);
                    if (null != admin) {
                        tmp.setSellerNick(admin.getName());
                    }
                    tmp.setGoodsAmount(tempFee);
                    tmp.setShippingAmount(0.00);
                    tmp.setAmount(tmp.getGoodsAmount() + tmp.getShippingAmount());
                    long id = tradeDao.addTradeGetId(tmp);
                    Trade temp = tradeDao.getTrade(id);
                    InterfaceUserTrade interfaceUserTrade = new InterfaceUserTrade();
                    interfaceUserTrade.setUserId(userId);
                    interfaceUserTrade.setTradeId(temp.getTid());
                    interfaceUserTrade.setPaipaiTradeId(tmp.getPaipaiTradeId());
                    interfaceUserTradeDao.addInterfaceUserTrade(interfaceUserTrade);
                    for (Order t : tmp.getOrderList()) {
                        orderDao.addOrder(t);
                    }
                }
                doUploadTradeList.clear();
                for (DoUploadTrade d : doUploadTradeList1) {
                    doUploadTradeList.add(d);
                }
                sbMsg.append("---�ɹ�������" + success + "---ʧ��������" + failure);
                doUploadTradeList.add(sbMsg.toString());
            } else {
                sbMsg.append("����2000������");
                doUploadTradeList.add(sbMsg.toString());
            }
            inputReader.close();
            is.close();
            return doUploadTradeList;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

    }

    /**
     * �ж��Ƿ���������������
     *
     * @param data
     * @param type
     * @return
     */
    public boolean isNumber(String data, String type) {
        String patternString = "^(0|[1-9][0-9]*)$"; // ����
        String parttern = "^(0|[1-9][0-9]*)\\.[0-9]+$"; // ������
        Pattern patternInt = Pattern.compile(patternString);
        Pattern patternNum = Pattern.compile(parttern);
        if (null != type && type.equals("int")) {
            Matcher matcher = patternInt.matcher(data.trim());
            return matcher.matches();
        } else {// �Ƿ��������ͣ����ж��Ƿ�������Ȼ���ж��Ƿ񸡵���
            Matcher matcher = patternInt.matcher(data.trim());
            if (!matcher.matches()) {
                Matcher matcherNum = patternNum.matcher(data.trim());
                return matcherNum.matches();
            } else {
                return true;
            }
        }
    }

    // public static String getFormatString(String strMoney, String formatStr) {
    // Long longMoney=0;
    // if (strMoney == null || strMoney.trim().equals("")) {
    // strMoney = "0";
    // }
    // try {
    // longMoney = Long.valueOf(longMoney);
    // } catch (Exception e) {
    // return strMoney;
    // }
    // return getFormatString(longMoney, formatStr);
    // }
    //
    // public static String getFormatString(Long longMoney, String formatStr,
    // Locale locale) {
    // if (longMoney == null) {
    // longMoney = Long.valueOf(0);
    // }
    // if (null == formatStr || formatStr.trim().equals("")) {
    // formatStr = "###";
    // }
    // DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();//
    // ����ʹ���������ָ�ʽ(ǧ��λ)
    // df.applyPattern(formatStr);// ����Ӧ�ý���ʽ
    // return df.format(longMoney);
    // }

    /**
     * ��ÿ������ת���ɶ���
     *
     * @param sheet
     * @param row
     *            getBrandsByName
     * @param sbMsg
     */
    public boolean rowToGoods(DoUploadTrade doUploadTrade, CsvReader reader, int count,
                              StringBuffer sbMsg, Long userId) {
        StringBuilder choose = new StringBuilder();
        String excelHead = "excel�е�";
        try {
            if (null != reader && StringUtil.isNotBlank(reader.get(0))) { // �ⲿ������
                InterfaceUserTrade interfaceUserTrade = interfaceUserTradeDao
                    .getInterfaceUserTradeByPaipaiTradeId(reader.get(0).trim());
                if (null != interfaceUserTrade) {
                    sbMsg.append(excelHead + (count + 1) + "�е��ⲿ�������Ѿ����ڼ�¼;");
                    return false;
                } else {
                    doUploadTrade.setPaipaiTradeId(reader.get(0).trim());
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е��ⲿ�����Ų���Ϊ��;");
                return false;
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(1))
                && EnumTradeType.findPermission(reader.get(1))) { // ������Դ
                doUploadTrade.setTradeType(EnumTradeType.toMap().get(reader.get(1).trim()));
            } else {
                doUploadTrade.setTradeType(10);
            }
            GoodsInstance goodsInstance = goodsInstanceDao.getInstanceByCode(reader.get(2).trim());
            if (null != reader && StringUtil.isNotBlank(reader.get(2)) && null == goodsInstance) {// ��Ʒ����
                sbMsg.append(excelHead + (count + 1) + "�еĲ�Ʒ���벻���ڶ�Ӧ��Ʒ;");
                return false;
            } else if (null != reader && StringUtil.isNotBlank(reader.get(2))) {
                doUploadTrade.setGoodsSn(reader.get(2).trim());
            } else {
                sbMsg.append(excelHead + (count + 1) + "�еĲ�Ʒ���벻��Ϊ��;");
                return false;
            }
            doUploadTrade.setGoodsName(goodsInstance.getInstanceName());
            // if (null != reader && StringUtil.isNotBlank(reader.get(3))) {//
            // ��Ʒ����
            // doUploadTrade.setGoodsName(reader.get(3).trim());
            // } else {
            // sbMsg.append(excelHead + (count + 1) + "�еĲ�Ʒ���Ʋ���Ϊ��}");
            // return false;
            // }
            if (null != reader && StringUtil.isNotBlank(reader.get(4))) { // ����
                boolean flag = isNumber(reader.get(4).trim(), "int");
                if (flag)
                    doUploadTrade.setGoodsNumber(Integer.valueOf(reader.get(4).trim()));
                else {
                    sbMsg.append(excelHead + (count + 1) + "�е��������Ͳ���ȷ;");
                    return false;
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е���������Ϊ��;");
                return false;
            }
            // else{
            // sbMsg.append("excel�е�"+row+"�еĵ�Ʒ�����Ʋ���Ϊ��");
            // return false;
            // }
            if (null != reader && StringUtil.isNotBlank(reader.get(5))) { // ����
                boolean flag = isNumber(reader.get(5).trim(), "double");
                if (flag)
                    doUploadTrade.setGoodsPrice(Double.valueOf(reader.get(5).trim()));
                else {
                    sbMsg.append(excelHead + (count + 1) + "�еĵ������Ͳ���ȷ;");
                    return false;
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�еĵ��۲���Ϊ��;");
                return false;
            }
            Map<String, Long> depositoryNameMap = new HashMap<String, Long>();
            List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
                .getDepositoryFirstList();
            for (DepositoryFirst depositoryFirst : depositoryFirstList) {
                depositoryNameMap.put(depositoryFirst.getDepFirstName(), depositoryFirst.getId());
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(6))) {// һ���ֿ�
                if (null != depositoryNameMap.get(reader.get(6).trim())) {
                    doUploadTrade.setDepFirstId(depositoryNameMap.get(reader.get(6).trim()));
                } else {
                    sbMsg.append(excelHead + (count + 1) + "�е�һ���ֿⲻ����;");
                    return false;
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е�һ���ֿⲻ��Ϊ��;");
                return false;
            }
            String tmp = null;
            if (null != reader && StringUtil.isNotBlank(reader.get(7))) {// ʡ
                Region r1 = regionDao.getRegionByNameAndType(reader.get(7).trim(), 2);
                if (null != r1) {
                    doUploadTrade.setProvince(r1.getCode());
                    tmp = r1.getCode();
                } else {
                    sbMsg.append(excelHead + (count + 1) + "�е�ʡ������;");
                    return false;
                }

            } else {
                sbMsg.append(excelHead + (count + 1) + "�е�ʡ����Ϊ��;");
                return false;
            }

            if (null != reader && StringUtil.isNotBlank(reader.get(8))) {// ��
                Region r2 = regionDao.getRegionByNameAndTypeAndCode(reader.get(8).trim(), 3, tmp);
                if (null != r2) {
                    doUploadTrade.setCity(r2.getCode());
                    tmp = r2.getCode();
                } else {
                    sbMsg.append(excelHead + (count + 1) + "�е��в�����;");
                    return false;
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е��в���Ϊ��;");
                return false;
            }

            if (null != reader && StringUtil.isNotBlank(reader.get(9))) {// ��
                Region r3 = regionDao.getRegionByNameAndTypeAndCode(reader.get(9).trim(), 4, tmp);
                if (null != r3) {
                    doUploadTrade.setDistrict(r3.getCode());
                } else {
                    doUploadTrade.setDistrict(null);
                }
            } else {
                doUploadTrade.setDistrict(null);
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(10))) {// ��������
                doUploadTrade.setZipcode(reader.get(10).trim());
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е��������벻��Ϊ��;");
                return false;
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(11))) {// �ջ���
                doUploadTrade.setReceiver(reader.get(11).trim());
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е��ջ��˲���Ϊ��;");
                return false;
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(12))
                && StringUtil.isNotBlank(reader.get(13))) {
                doUploadTrade.setMobile(reader.get(12).trim());
            } else if (null != reader && StringUtil.isNotBlank(reader.get(12))) { // �ֻ�
                doUploadTrade.setMobile(reader.get(12).trim());
            } else if (null != reader && StringUtil.isNotBlank(reader.get(13))) {// �绰
                doUploadTrade.setMobile(reader.get(13).trim());
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е��ֻ��͵绰������һ��;");
                return false;
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(14))) { // ��ϸ��ַ
                doUploadTrade.setAddress(reader.get(14).trim());
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е���ϸ��ַ����Ϊ��;");
                return false;
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(15))) { // ������˾
                InterfaceApply interfaceApply = interfaceApplyDao.getInterfaceApplyByUserId(userId,
                    EnumInterfaceType.TAOBAO.getKey());
                DepositoryFirst depositoryFirst = depositoryFirstManager
                    .getDepositoryById(doUploadTrade.getDepFirstId());
                //���������Դ���Ա���������ͬ�����е�express_id��Ϊ�յ�ʱ�����ж�
                if (reader.get(1).equals(EnumTradeType.trade_type_3.getKey())
                    && interfaceApply != null && interfaceApply.getOwnExpressId() != null) {
                    ExpressDist expressdist = expressDistManager.getExpressDistByRegionCodeEnd(
                        depositoryFirst.getRegionCode(),
                        doUploadTrade.getDistrict() != null ? doUploadTrade.getDistrict()
                            : doUploadTrade.getCity(), EnumExpressDistPayment.PAYMENT_FIRST
                            .getKey(), interfaceApply.getOwnExpressId());
                    if (expressdist != null) {
                        Express express = expressDao.getExpress(interfaceApply.getOwnExpressId());
                        doUploadTrade.setInterfaceExpressCode(express.getExpressCode());
                        doUploadTrade.setExpressId(interfaceApply.getOwnExpressId());
                    } else {
                        Resources resourcesLocal = resourcesManager.getResourcesByTypeAndName(
                            "taobao", "secondLocalExpress");
                        Resources resourcesTaobao = resourcesManager.getResourcesByTypeAndName(
                            "taobao", "secondTaobaoExpress");
                        doUploadTrade.setInterfaceExpressCode(resourcesTaobao.getValue());
                        doUploadTrade.setExpressId(Long.parseLong(resourcesLocal.getValue()));
                    }
                } else {
                    Express express = expressDao.getExpressIdByExpressCode(reader.get(15).trim());
                    if (express == null) {
                        sbMsg.append(excelHead + (count + 1) + "�е�������˾������;");
                        return false;
                    }
                    ExpressDist expressdist = expressDistManager.getExpressDistByRegionCodeEnd(
                        depositoryFirst.getRegionCode(),
                        doUploadTrade.getDistrict() != null ? doUploadTrade.getDistrict()
                            : doUploadTrade.getCity(), EnumExpressDistPayment.PAYMENT_FIRST
                            .getKey(), express.getId());
                    if (expressdist != null) {
                        doUploadTrade.setExpressId(express.getId());
                    } else {
                        sbMsg.append(excelHead + (count + 1) + "�е�������˾���ܵ���;");
                        return false;
                    }
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е�������˾����Ϊ��;");
                return false;
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(16))) { // ��ұ�ע
                doUploadTrade.setBuyerNote(reader.get(16).trim());
            }
            if (null != reader && StringUtil.isNotBlank(reader.get(17))
                && ("��".equals(reader.get(17)) || "��".equals(reader.get(17)))) { // �Ƿ���Ҫ��Ʊ
                if ("��".equals(reader.get(17))) {
                    doUploadTrade.setInvoice("y");
                } else {
                    doUploadTrade.setInvoice("n");
                }
            } else {
                sbMsg.append(excelHead + (count + 1) + "�е��Ƿ���Ҫ��Ʊ����Ϊ��;");
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            sbMsg.append(e);
            return false;
        }

    }
    
    
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void saveProductToTrade(List<Product> productList,List<Order> orderList,Trade trade) {
		
		//���ɿͻ��˶���
		CustomerOrder customerOrder = tradeDao.getCustomerOrderById((long)trade.getCustomerorderIdOrder());
		if(customerOrder==null){
			customerOrder = new CustomerOrder();
			customerOrder.setIdChannel(4);
			customerOrder.setIdCurrency(3);
			customerOrder.setOperator(trade.getIdlastoperator());
			customerOrder.setOperator2(0L);
			customerOrder.setSubTotal(trade.getCustomerorderAmount());
			customerOrder.setRemark("���綩��,������ţ�"+trade.getTid());
			customerOrder.setIdSite(102L);
			customerOrder.setIdCustomer(trade.getCustomerId());
			customerOrder.setStatus(1);
			customerOrder.setIdPayment(0);
			
			
			long customerOrderId = tradeDao.addCustomerOrderGetId(customerOrder);
			
			Map<String,Object> tradePramas = new HashMap<String,Object>();
			tradePramas.put("customerorder_idOrder", customerOrderId);
			tradePramas.put("tid", trade.getTid());
			tradeDao.updateTradeCustomerOrderIdOrder(tradePramas);
		}
		
		
		for(Product product:productList){
			//����lifecycle ��ΪԤ��״̬ 
			Map<String,Object> pramas = new HashMap<String,Object>();
			pramas.put("idProduct", product.getIdProduct());
			pramas.put("price", product.getPrice());
			pramas.put("idPriceCurrency", trade.getIdPriceCurrency());
			pramas.put("idOrder", customerOrder.getIdOrder());
			pramas.put("idStatus", 3);
			pramas.put("idLastOperator", trade.getIdlastoperator());
			pramas.put("remark", customerOrder.getRemark());
			
			tradeDao.updateLifecycleByProductId(pramas);  //��ӵ�ʱ���Ѿ�����ΪԤ��״̬��
			
			//����Ԥ����ʷ
			HistoryView history = new HistoryView();
			history.setIdOperation(5);
			history.setIdproduct(product.getIdProduct());
//			history.setIdSupply(idSupply);
			history.setIdCustomer(customerOrder.getIdCustomer());
			history.setIdOperator(trade.getIdlastoperator());
			history.setIdCurStation((int)customerOrder.getIdSite().longValue());
			history.setIdStatus(3);
			
			tradeDao.addHistoryGetId(history);

		}
		
		//order����
		for(Order order:orderList){
			Map<String,Object> pramas = new HashMap<String,Object>();
			pramas.put("idOrder", customerOrder.getIdOrder());
			pramas.put("instanceId", order.getGoodsInstanceId());
			
			Long num = productService.getProductNumByPramas(pramas);
			order.setProductNumber(num);
		}
		
		
		
		
	}
	
	
	@Transactional
	public void saveProductToTradeAndChangeTradeStatus(List<Product> productList,List<Order> orderList,Trade trade) {
		
		this.saveProductToTrade(productList,orderList,trade);
		
		//�жϲ�Ʒ�����ǲ�����ȷ�ˣ���ȷ�˸ı䶩��״̬
		
		boolean tag =false;
		/*
		for(Order order:orderList){
			if(order.getProductNumber()!=order.getGoodsNumber()){
				tag = true;
			}
		}
		*/
		if(!tag){
			this.updateTradeStatus(""+trade.getId(), EnumTradeStatus.WAIT_BUYER_PAY.getKey());
		}
	}

	@Override
	public Trade getTradeByMap(Map<String, String> parMap) {
		log.info("TradeManagerImpl.getTradeByMap method");
        try {
            return this.tradeDao.getTradeByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
	}

	@Override
	public void updateTradeOrderPrice(Map<String, Object> parMap) {
		log.info("TradeManagerImpl.updateTradeOrderPrice method");
        try {
            this.tradeDao.updateTradeOrderPrice(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException();
        }
	}

	@Override
	@Transactional
	public int sellerConfirmPay(Trade trade) {
		
		log.info("TradeManagerImpl.sellerConfirmPay method");
        try {
//            Trade trade = this.tradeDao.getTrade(new Long(id));
            if (!EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus())) {
                return 2;
            }

            if (trade.getPayStatus().equalsIgnoreCase(EnumTradePayStatus.NO_PAY.getKey())) {
        		trade.setStatus(EnumTradeStatus.TRADE_FINISH.getKey());
        		trade.setPayStatus(EnumTradePayStatus.PAID.getKey());
        		trade.setPayTime(new Date());
                this.tradeDao.editTrade(trade);

                Map parameterMap = new HashMap();
                parameterMap.put("tid", trade.getTid());
                List<Order> orderList = orderDao.getOrdersByParameterMap(parameterMap);
                for (Order order : orderList) {
                    Goods goods = goodsManager.getGoods(order.getGoodsId());
                    
                    //����emall_goods���ioss_goods_instance����
                    goodsInstanceManager.updateAmountForTwo(order.getGoodsInstanceId(),
                    		order.getGoodsId(),(0-order.getGoodsNumber()));
                    
                    //������������
                    goods.setSaleNumber(goods.getSaleNumber()
                                        + Integer.valueOf(String.valueOf(order.getGoodsNumber()))
                                            .intValue());
                    goodsManager.editGoods(goods);
                }
                //����C�ͻ��˶����Ͳ�Ʒ״̬�͸�վ����
                CustomerOrder customerOrder = tradeDao.getCustomerOrderById((long)trade.getCustomerorderIdOrder());
                
                Map<String,Object> pramas = new HashMap<String,Object>();
                pramas.put("idPayment", trade.getPayment());
                pramas.put("status", 0);
                pramas.put("idOrder", trade.getCustomerorderIdOrder());
                pramas.put("cash", trade.getCash());
                tradeDao.updateCustomerOrderStatus(pramas);
                
                List<Product> productList= productService.getProductByCustomerOrderId((long)trade.getCustomerorderIdOrder());
                for(Product product:productList){
        			//����lifecycle ��Ϊ����״̬
        			Map<String,Object> pramasin = new HashMap<String,Object>();
        			pramasin.put("idProduct", product.getIdProduct());
        			//4Ϊ����״̬
        			pramasin.put("idStatus", 4);
        			
        			tradeDao.updateLifecycleByProductId(pramasin);
        			
        			//����������ʷ
        			HistoryView history = new HistoryView();
        			history.setIdOperation(4);
        			history.setIdproduct(product.getIdProduct());
        			//history.setIdSupply(idSupply);
        			history.setIdCustomer(customerOrder.getIdCustomer());
        			history.setIdOperator(trade.getIdlastoperator());
        			history.setIdCurStation((int)customerOrder.getIdSite().longValue());
        			history.setIdStatus(4);
        			
        			tradeDao.addHistoryGetId(history);
        			//������ۿ��
        			if(product.getIdLocation() !=null && product.getIdLocation().longValue()==102){
	        			goodsManager.updateGoodsHKGoodsNumberById(product.getGoodsId(), -1l,false);
	        			goodsInstanceManager.updateGoodsInstanceHKExistNumById(product.getInstanceId(), -1l);
        			}
        			
        			//���²ֿ���
    				Map<String,Object> aspra = new HashMap<String,Object>();
    				aspra.put("siteId", product.getIdLocation());
    				aspra.put("goodsInstanceId", product.getInstanceId());
    				aspra.put("goodsNumber", -1);
    				availableStockService.updateAvaiStoEsNumByPramas(aspra);

    				//���»���
    				List<MoveframeProduct> list = moveFrameProductDao.getMoveFrameProductsByPorductId(product.getIdProduct());
    				for(MoveframeProduct  m : list)
    				{
    					Map parMap = new HashMap();
    					parMap.put("goodsId", m.getGoodsId());
    					parMap.put("moveFrameId", m.getMoveframeId());
    					List<MoveframeGoods> moveFramesList = moveFrameProductDao.getMoveFrameGoodsByGoodsIdAndMfId(parMap); 
    					//����log��
    					MoveFrameLog moveFrameLog = new MoveFrameLog();
    					moveFrameLog.setNote(product.getIdProduct()+"");
    					moveFrameLog.setMoveframeId(m.getMoveframeId());
    					moveFrameProductDao.insertMoveFrameLog(moveFrameLog); 
    					for(MoveframeGoods moveframeGoods : moveFramesList)
    					{
    							Long number = moveframeGoods.getGoodsNum();
    							Long soldNum = moveframeGoods.getSoldNum();//���ۼ�һ
    							if(number > 0) 
    							{
    								number -=1; //������һ
    								soldNum += 1; //���ۼ���
    								moveframeGoods.setGoodsNum(number);
    								moveframeGoods.setSoldNum(soldNum);
    								moveframeGoods.setGmtModify(new java.sql.Date(new Date().getTime()));
    								moveFrameProductDao.updateMoveFrameGoods(moveframeGoods); //����hx_moveframe_goods����
    							}
    								
    					}
    					parMap.put("instanceId", m.getInstanceId());
    					List<MoveframeInstance> moveFrameInsList = moveFrameProductDao.getMoveFrameInstanceByGoodsIdAndInstanceIdAndMfId(parMap);
    					for(MoveframeInstance instance : moveFrameInsList)
    					{
    							Long number = instance.getInstanceNum();
    							Long soldNum = instance.getSoldNum();
    							if(number > 0)
    							{
    								number -= 1;
    								soldNum += 1;
    								instance.setInstanceNum(number);
    								instance.setSoldNum(soldNum);
    								instance.setGmtModify(new java.sql.Date(new Date().getTime()));
    								moveFrameProductDao.updateMoveFrameInstance(instance); //����hx_moveframe_instance����
    							}
    							
    					}
    				}
        			
        		}
                
                return 1;
            } else {
                return 3;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ManagerException();
        }
	}
}