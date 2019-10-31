package com.huaixuan.network.biz.service.trade.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsAgentDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.dao.trade.RefundOrderDao;
import com.huaixuan.network.biz.dao.trade.TicketRecordDao;
import com.huaixuan.network.biz.dao.trade.TradeAgentDao;
import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.dao.user.UserAgentDao;
import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.agent.AgentTrade;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.RefundOrder;
import com.huaixuan.network.biz.domain.trade.TicketRecord;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.enums.EnumAgentStatus;
import com.huaixuan.network.biz.enums.EnumAgentTradeStatus;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumRefundType;
import com.huaixuan.network.biz.enums.EnumTicketRecordType;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.trade.TradeAgentManager;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("tradeAgentManager")
public class TradeAgentManagerImpl implements TradeAgentManager {
    Log              log = LogFactory.getLog(this.getClass());

    @Autowired
    TradeAgentDao    tradeAgentDao;
    @Autowired
    UserDao          userDao;
    @Autowired
    UserAgentDao     userAgentDao;

    @Autowired
    TradeDao         tradeDao;

    @Autowired
    OrderDao         orderDao;

    @Autowired
    RefundDao        refundDao;

    @Autowired
    RefundOrderDao   refundOrderDao;

    @Autowired
    GoodsInstanceDao goodsInstanceDao;

    @Autowired
    TicketRecordDao  ticketRecordDao;

    @Autowired
    GoodsAgentDao    goodsAgentDao;

    @Autowired
    GoodsDao         goodsDao;

    // public List<AgentTrade> getAgentByParameterMap(Map parameterMap, Page page) {
    // return tradeAgentDao.getAgentByParameterMap(parameterMap, page);
    // }
    //
    // public List<AgentTrade> searchAgentByParameterMap(Map parameterMap, Page page) {
    // return tradeAgentDao.searchAgentByParameterMap(parameterMap, page);
    // }

    public int getCountAgentTradeByParMap(Map parameterMap) {
        try {
            return tradeAgentDao.getCountAgentTradeByParMap(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // public List<AgentTrade> getAgentTradeByParMap(Map parameterMap, Page page) {
    // try {
    // return tradeAgentDao.getAgentTradeByParMap(parameterMap, page);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    public List<AgentTrade> getGroupAgentTrade() {
        try {
            return tradeAgentDao.getGroupAgentTrade();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int searchAgentCountByParameterMap(Map<String, String> parMap) {
        return tradeAgentDao.searchAgentCountByParameterMap(parMap);
    }

    public int getAgentCountByParameterMap(Map<String, String> parMap) {
        return tradeAgentDao.getAgentCountByParameterMap(parMap);
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setRefundDao(RefundDao refundDao) {
        this.refundDao = refundDao;
    }

    public void setRefundOrderDao(RefundOrderDao refundOrderDao) {
        this.refundOrderDao = refundOrderDao;
    }

    public void setGoodsInstanceDao(GoodsInstanceDao goodsInstanceDao) {
        this.goodsInstanceDao = goodsInstanceDao;
    }

    public void setTicketRecordDao(TicketRecordDao ticketRecordDao) {
        this.ticketRecordDao = ticketRecordDao;
    }

    public void setGoodsAgentDao(GoodsAgentDao goodsAgentDao) {
        this.goodsAgentDao = goodsAgentDao;
    }

    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public void updateAgentTradeWithAll(String tid, String status, String saveType)
                                                                                   throws Exception {
        Boolean isTradeSuccess = false;

        if (StringUtil.isBlank(tid)) {
            log.error("交易tid不能为空！");
            return;
        }
        if (StringUtil.isBlank(status)) {
            log.error("交易状态不能为空！");
            return;
        }
        Trade t = tradeDao.getTradeByTid(tid);
        if (t == null) {
            log.error("订单信息不存在！");
            return;
        }
        // 针对交易成功后退货的用户，不更新agent_trade的状态
        if (EnumTradeStatus.TRADE_FINISH.getKey().equals(t.getStatus())) {
            isTradeSuccess = true;
        }
        User s = userDao.getUserById(t.getBuyId());
        if (s == null) {
            log.error("用户不存在！");
            return;
        }
//        if (!(s.getType().equals(EnumUserType.AGENT_USER.getKey()) || s.getType().equals(
//            EnumUserType.WHOLESALE_USER.getKey()))) {
//            return;
//        }
        AgentInfo a = userAgentDao.getAgentInfoById(s.getId());
        if (a == null) {
            log.error("代销用户信息不存在！");
            return;
        }
        // 首先往agent_trade插入数据
        try {

            Map param = new HashMap();
            param.put("tid", tid);
            List<Refund> refs = refundDao.getRefundByParameterMap(param);

            // Refund refund = refundDao.getRefundByOrder(tid);
            List<Order> orders = orderDao.getOrdersByTid(tid);
            for (Order o : orders) {
                // 判断是否是用户的代销商品
                // Map parMap = new HashMap();
                // parMap.put("userId", a.getUserId());
                // parMap.put("goodsId", o.getGoodsId());
                // parMap.put("status", EnumAgentStatus.Is_agent.getKey());

                // 套餐商品，不进入agent trade
                if (null != o.getPackageId()) {
                    continue;
                }
                // 注释代销会员关联表 2010-2-26 shengyong
                // GoodsAgent ga = goodsAgentDao.getGoodsAgent(parMap);
                // if (ga == null) {
                // AgentTrade agentTrade = tradeAgentDao.getAgentTradeByTidOrderId(t.getTid(), o
                // .getId());
                // if (agentTrade == null) {
                // continue;
                // }
                // }
                Goods goods = goodsDao.getGoods(o.getGoodsId());
                if (goods == null || goods.getIsAgent().equals(EnumAgentStatus.No_agent.getKey())) {
                    AgentTrade agentTrade = tradeAgentDao.getAgentTradeByTidOrderId(t.getTid(),
                        o.getId());
                    if (agentTrade == null) {
                        continue;
                    }
                }

                AgentTrade agentTrade = tradeAgentDao.getAgentTradeByTidOrderId(t.getTid(),
                    o.getId());
                if (agentTrade == null) {
                    AgentTrade at = new AgentTrade();
                    at.setTid(o.getTid());
                    at.setOrderId(o.getId());
                    at.setUserId(t.getBuyId());
                    at.setPrice(o.getGoodsPrice());
                    at.setAmount(o.getGoodsNumber());
                    at.setGoodsInstanceId(o.getGoodsInstanceId());
                    GoodsInstance g = goodsInstanceDao.getInstance(o.getGoodsInstanceId());
                    if (g != null) {
                        at.setCatCode(g.getCatCode());
                    }
                    at.setGoodsId(o.getGoodsId());

                    // for (Refund r : refs) {
                    // List<RefundOrder> listRefundOrder = refundOrderDao
                    // .getRefundOrderByRefundId(r.getRefundId());
                    // for (RefundOrder ro : listRefundOrder) {
                    // at.setRefundPrice(add(ro.getFactFee(), at.getRefundPrice()));
                    // at.setRefundAmount(ro.getRefAmount() + at.getRefundAmount());
                    // at.setRefundType(r.getType());
                    // }
                    //
                    // }

                    at.setStatus(EnumAgentTradeStatus.AGENT_TRADE_INIT.getKey());
                    //					// 加入规则id zhangwy
                    //					ReturnPoint returnPoint = returnPointManager.getReturnPointWithTrade(t.getBuyId(), o.getGoodsId());
                    //					if (returnPoint != null) {
                    //						at.setReturnId(returnPoint.getId());
                    //					}
                    tradeAgentDao.addAgentTrade(at);
                } else {
                    agentTrade.setPrice(o.getGoodsPrice());
                    for (Refund r : refs) {
                        if (EnumRefundType.REFUND_GOODS.getKey().equals(r.getType())
                            || EnumRefundType.REFUND_ALL_GOODS.getKey().equals(r.getType())) {
                            if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(
                                r.getStatus())
                                && !"3".equals(r.getIsGoodsUntread())) {

                                List<RefundOrder> listRefundOrder = refundOrderDao
                                    .getRefundOrderByRefundId(r.getRefundId());
                                for (RefundOrder ro : listRefundOrder) {
                                    if (ro.getGoodsInstanceId() == agentTrade.getGoodsInstanceId()
                                        .longValue()) {
                                        agentTrade.setRefundPrice(add(ro.getFactFee(),
                                            agentTrade.getRefundPrice()));
                                        agentTrade.setRefundAmount(ro.getRefAmount()
                                                                   + agentTrade.getRefundAmount());
                                        agentTrade.setRefundType(r.getType());
                                    }
                                }
                            }
                        }
                    }
                    if (!isTradeSuccess || "save".equals(saveType)) {
                        if (status.equals(EnumTradeStatus.TRADE_CLOSE.getKey())) {
                            agentTrade.setStatus(EnumAgentTradeStatus.AGENT_TRADE_CLOSE.getKey());
                        } else if (status.equals(EnumTradeStatus.TRADE_FINISH.getKey())) {
                            if (agentTrade.getRefundAmount() == o.getGoodsNumber()) {
                                if (EnumRefundType.REFUND_GOODS.getKey().equals(
                                    agentTrade.getRefundType())) {
                                    agentTrade.setStatus(EnumAgentTradeStatus.AGENT_TRADE_CLOSE
                                        .getKey());
                                } else {
                                    agentTrade.setStatus(EnumAgentTradeStatus.AGENT_TRADE_SUCCESS
                                        .getKey());
                                    agentTrade.setGmtTradeFinish(t.getFinishTime());
                                }
                            } else {
                                agentTrade.setStatus(EnumAgentTradeStatus.AGENT_TRADE_SUCCESS
                                    .getKey());
                                agentTrade.setGmtTradeFinish(t.getFinishTime());
                            }
                        }

                    }
                    agentTrade.setId(agentTrade.getId());
                    tradeAgentDao.updateAgentTrade(agentTrade);
                }
            }
            // 然后对agent_info进行扣除点数的操作
            if (status.equals(EnumTradeStatus.WAIT_BUYER_PAY.getKey()) && !"unite".equals(saveType)) {
                a.setTicketUsed(a.getTicketUsed() + t.getTicketAmount());
                a.setTicketLeft(a.getTicketLeft() - t.getTicketAmount());
                userAgentDao.updateAgentTickets(a);
                // 点券记录,为0的时候不插ticket记录表
                if (t.getTicketAmount() > 0) {
                    TicketRecord ticketRecord = new TicketRecord();
                    ticketRecord.setUserId(a.getUserId());
                    ticketRecord.setTicketAmount(0 - t.getTicketAmount());
                    ticketRecord.setType(EnumTicketRecordType.AGENT_TICKET_TRADE.getKey());
                    ticketRecord.setMemo("订单" + tid + "创建，扣除点券");
                    ticketRecordDao.addTicketRecord(ticketRecord);
                }
            } else if (status.equals(EnumTradeStatus.TRADE_FINISH.getKey())) {
                for (Refund r : refs) {

                    if (r.getStatus().equals(EnumRefundStatus.Goods_Confirm_Success.getKey())
                        && !"3".equals(r.getIsGoodsUntread())) {
                        a.setTicketUsed(a.getTicketUsed() - r.getTicketAmount());
                        a.setTicketLeft(a.getTicketLeft() + r.getTicketAmount());
                        userAgentDao.updateAgentTickets(a);
                        // 点券记录,为0的时候不插ticket记录表
                        if (r.getTicketAmount() > 0) {
                            TicketRecord ticketRecord = new TicketRecord();
                            ticketRecord.setUserId(a.getUserId());
                            ticketRecord.setTicketAmount(r.getTicketAmount());
                            ticketRecord.setType(EnumTicketRecordType.AGENT_TICKET_REFUND.getKey());
                            ticketRecord.setMemo("订单" + tid + "返还点券");
                            ticketRecordDao.addTicketRecord(ticketRecord);
                        }
                        r.setIsGoodsUntread("3");
                        refundDao.editRefund(r);
                    }

                }
            } else if (status.equals(EnumTradeStatus.TRADE_CLOSE.getKey())) {
                for (Refund r : refs) {
                    // if (r.getStatus().equals(EnumRefundStatus.Refund_Close.getKey())) {
                    // a.setTicketUsed(a.getTicketUsed() - t.getTicketAmount());
                    // a.setTicketLeft(a.getTicketLeft() + t.getTicketAmount());
                    // userAgentDao.updateAgentTickets(a);
                    // // 点券记录,为0的时候不插ticket记录表
                    // if (t.getTicketAmount() > 0) {
                    // TicketRecord ticketRecord = new TicketRecord();
                    // ticketRecord.setUserId(a.getUserId());
                    // ticketRecord.setTicketAmount(t.getTicketAmount());
                    // ticketRecord.setType(EnumTicketRecordType.AGENT_TICKET_REFUND.getKey());
                    // ticketRecord.setMemo("订单" + tid + ",退款完成，返还点券");
                    // ticketRecordDao.addTicketRecord(ticketRecord);
                    // }
                    // } else
                    if (r.getStatus().equals(EnumRefundStatus.Goods_Confirm_Success.getKey())
                        && !"3".equals(r.getIsGoodsUntread())) {
                        a.setTicketUsed(a.getTicketUsed() - r.getTicketAmount());
                        a.setTicketLeft(a.getTicketLeft() + r.getTicketAmount());
                        userAgentDao.updateAgentTickets(a);
                        // 点券记录,为0的时候不插ticket记录表
                        if (r.getTicketAmount() > 0) {
                            TicketRecord ticketRecord = new TicketRecord();
                            ticketRecord.setUserId(a.getUserId());
                            ticketRecord.setTicketAmount(r.getTicketAmount());
                            ticketRecord.setType(EnumTicketRecordType.AGENT_TICKET_REFUND.getKey());
                            ticketRecord.setMemo("订单" + tid + "，退款成功，返还点券");
                            ticketRecordDao.addTicketRecord(ticketRecord);
                        }
                        r.setIsGoodsUntread("3");
                        refundDao.editRefund(r);
                    }
                }
                if (refs.size() == 0) {
                    a.setTicketUsed(a.getTicketUsed() - t.getTicketAmount());
                    a.setTicketLeft(a.getTicketLeft() + t.getTicketAmount());
                    userAgentDao.updateAgentTickets(a);
                    // 点券记录,为0的时候不插记录表
                    if (t.getTicketAmount() > 0) {
                        TicketRecord ticketRecord = new TicketRecord();
                        ticketRecord.setUserId(a.getUserId());
                        ticketRecord.setTicketAmount(t.getTicketAmount());
                        ticketRecord.setType(EnumTicketRecordType.AGENT_TICKET_REFUND.getKey());
                        ticketRecord.setMemo("订单" + tid + "关闭，返还点券");
                        ticketRecordDao.addTicketRecord(ticketRecord);
                    }
                }
            }
        } catch (Exception e) {
            log.error("点券处理错误！" + e.getMessage());
        }
        return;
    }

    public List<AgentTrade> countAgentAmount(Calendar cal) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "success");
        map.put("isReceive", "n");// 设置当前朋的第一天，和最后一天
        Calendar calTemp = Calendar.getInstance();
        calTemp.set(Calendar.MONTH, calTemp.get(Calendar.MONTH) - 1);
        calTemp.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        map.put("startTradeFinish", calTemp.getTime());
        calTemp.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        map.put("endTradeFinish", calTemp.getTime());
        return this.tradeAgentDao.countAgentAmount(map);
    }

    public void updateAgentIsReceive(Calendar cal, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "success");
        map.put("isReceive", "y");// 设置当前朋的第一天，和最后一天
        Calendar calTemp = Calendar.getInstance();
        calTemp.set(Calendar.MONTH, calTemp.get(Calendar.MONTH) - 1);
        calTemp.set(Calendar.DAY_OF_MONTH, calTemp.getActualMinimum(Calendar.DAY_OF_MONTH));
        map.put("startTradeFinish", calTemp.getTime());
        calTemp.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        map.put("endTradeFinish", calTemp.getTime());
        map.put("userId", userId);
        this.tradeAgentDao.updateAgentIsReceive(map);

    }

    /**
     * 加法运算。
     * 
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public List<Admin> getAdminInfoById(Long newAgentManagerId) {
        return this.tradeAgentDao.getAdminInfoById(newAgentManagerId);
    }

    public void updateAgentTradeById(Map<String, String> param) {
        this.tradeAgentDao.updateAgentTradeById(param);
    }

    public void resetAgentManager(Map<String, String> paramap) {
        this.tradeAgentDao.resetAgentManager(paramap);
    }

    public List<Admin> getAdminInfo() {
        return this.tradeAgentDao.getAdminInfo();
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage searchAgentByParameterMap(AgentTrade trade, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(trade);
        Map pramas = queryPage.getParameters();

        int count = tradeAgentDao.searchAgentCountByParameterMap(pramas);

        if (count > 0) {

            /* 当前页 */
            queryPage.setCurrentPage(currPage);
            /* 每页总数 */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);

            pramas.put("startRow", queryPage.getPageFristItem());
            pramas.put("endRow", queryPage.getPageLastItem());

            List<AgentTrade> agentTradeList = tradeAgentDao.searchAgentByParameterMap(pramas);
            if (agentTradeList != null && agentTradeList.size() > 0) {
                queryPage.setItems(agentTradeList);
            }
        }
        return queryPage;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Double searchAgentCountPriceByParameterMap(AgentTrade trade) {
        QueryPage queryPage = new QueryPage(trade);
        Map pramas = queryPage.getParameters();
        Double totalAmount = tradeAgentDao.searchAgentCountPriceByParameterMap(pramas);
        return totalAmount;
    }
}
