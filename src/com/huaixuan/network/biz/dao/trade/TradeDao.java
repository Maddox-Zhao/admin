package com.huaixuan.network.biz.dao.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hy.CustomerOrder;
import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradeSalesCount;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;

/**
 * 17(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface TradeDao {
    /* @interface model: Trade */
    void addTrade(Trade trade);

    long addTradeGetId(Trade trade);

    /* @interface model: Trade */
    void editTrade(Trade trade);

    /**
     * 修改同步订单的物流信息
     *
     * @param trade
     *            @
     */
    void updateTradeExpressInfo(Trade trade);

    /**
     * 根据出库单信息更新实际运费
     *
     * @param expressCode
     *            String
     * @param id
     *            Long
     * @return int
     * @author chenhang 2010/11/18
     */
    int updateActualInventoryById(Double actualInventory, String reNum);

    /**
     * 修改物流方式
     *
     * @param tid
     * @param expressId
     * @return int
     * @author chenhang 2010/01/10
     */
    int updateTradeExpressId(String tid, String expressId);

    /**
     *
     * @author chenhang 2011-5-5
     * @description 修改淘宝同步物流
     * @param tid
     * @param interfaceTaobaoCode
     * @return
     */
    int updateInterfaceExpressCode(String tid, String interfaceExpressCode);

    /**
     * 根据出库单信息更新实际运费
     *
     * @param expressCode
     *            String
     * @param id
     *            Long
     * @return int
     * @author chenhang 2010/11/18
     */
    int updateActualInventoryByIdRe(Double actualInventory, String reNum);

    /**
     *
     * 修改买家留言
     *
     * @return int
     * @author chenhang 2010-01-06
     */
    int updatetradeBuyerNote(String tid, String buyerNote);

    /**
     * 修改周期结算支付方式的订单物流信息
     *
     * @param trade
     *            @
     */
    void updatePeriodTradeExpressInfo(Trade trade);

    /* @interface model: Trade */
    void removeTrade(Long tradeId);

    /* @interface model: Trade17,Trade */
    Trade getTrade(Long tradeId);

    /* @interface model: Trade17,Trade17 */
    List<Trade> getTrades();

    /**
     * 查询符合要求的Trades结果
     *
     * @param query
     *            查询参数
     * @return 符合要求的Trades结果
     */
    List<Trade> getTradesByParameterMap(TradeListQuery query);

    /**
     * 分页查询符合要求的Trades结果
     *
     * @param query
     *            查询参数
     * @param currPage
     *            当前页
     * @param pageSize
     *            每页条数
     * @author fangqing
     * @return 符合要求的Trades结果
     */
    QueryPage getTradesByParameterMap(TradeListQuery query, int currPage, int pageSize);

    /**
     * 查询符合要求的Trades结果
     *
     * @param parameterMap
     *            参数
     * @author fangqing
     * @return 符合要求的Trades结果
     */
    List<Trade> getTradesByParameterMapWhitNote(TradeListQuery query);

    /**
     * 分页查询符合要求的Trades结果
     *
     * @param parameterMap
     *            参数
     * @author fangqing
     * @return 符合要求的Trades结果
     */
    QueryPage getTradesByParameterMapWhitNote(TradeListQuery query, int currPage, int pageSize);

    QueryPage getTradesWithNote(TradeListQuery query, int currPage, int pageSize);

    List<Trade> getTradesAlreadyPaid(Map parameterMap);

    /**
     * 根据时间17和天数，获取霄17要的数据17，主要提供给时间17程序17用，17自动收货、自动取消订卄17
     *
     * @param status
     * @param day
     * @return
     */
    List<Trade> getTradesPartByTimetask(String status, int day);

    /**
     * 有效订单总金预17
     *
     * @return 有效订单总金预17
     */
    double countAllTradeAmount();

    double countAllFinishedTradeAmount();

    /**
     * 根据订单号返回订卄17
     *
     * @return 订单
     */
    Trade getTradeByTid(String tid);

    void updateTradeStatusByTid(Map<String, Object> pramas);

    Trade getTradeStatusByTid(Map<String, Object> pramas);

    void updateTradeStatusByTid(String status, String tid);

    void updateTradeByRefund(Map<String, Object> pramas);

    /**
     * 根据refundId更新交易状态
     *
     * @param map
     *            Map
     * @author chenyan 2009/08/05
     */
    void updateTradeStatusByRefundId(Map map);

    /**
     * 根据换货ID取得交易表数据
     *
     * @param refundId
     * @return Trade
     * @author chenyan 2009/08/19
     */
    Trade getTradeByRefundId(String refundId);

    /**
     * 更新支付状态
     *
     * @param tid
     *            Long
     * @author chenyan 2009/08/26
     */
    void updatePayTimeByTid(Long tid);

    /**
     * 根据ID修改物流价格以及相应的总价格
     *
     * @param shippingAmount
     *            Double
     * @param amount
     *            Double
     * @param id
     *            Long
     * @param memo
     *            String
     * @author zhangwy 2010/10/09
     */
    void updateShippingAmountById(Double shippingAmount, Double amount, String memo, Long id);

    /**
     * 根据order表ID取得订单信息
     *
     * @param id
     *            Long
     * @return Trade
     * @author chenyan 2009/09/11
     */
    Trade getTradeByOrderId(Long id);

    /**
     * 根据订单商品表ID更新价格（订单总价，交易总价）
     *
     * @param goodsAmount
     *            Double
     * @param amount
     *            Double
     * @param orderId
     *            Long
     * @param memo
     *            String
     * @author zhangwy 2010/10/09
     */
    void updateAmountByGoodsPrice(Double goodsAmount, Double amount, String memo, Long orderId);

    /**
     * 根据条件汇总订单总额
     *
     * @param query
     * @return
     */
    Trade getTradesGoodsAmountSum(TradeListQuery query);

    /**
     * 查询已经完成，并且没有累加积分的订单
     *
     * @param status
     * @param isPoint
     * @return
     */
    List<Trade> getTradesByIsPoint(String status, String isPoint, Long buyUserId);

    void updateMessageByTradeId(Long tradeID, String message);

    void updateInvoiceByTradeId(Long tradeID, String invoice);

    int updateIspurchasedByTradeId(Long tradeID);

    Trade getTradeByMap(Map<String, String> parMap);

    /**
     * 商品总金额
     *
     * @param parMap
     * @return
     */
    double countAllGoodsAmount(Map parMap);

    /**
     * 运费总金额
     *
     * @param parMap
     * @return
     */
    double countAllShipAmount(Map parMap);

    /**
     * 取得代销会员的销售额
     *
     * @author lilei 2010/06/03
     */
    List<TradeSalesCount> listTradeSalesCount();

    /**
     * 修改批发订单的运费 总价 一级仓库
     *
     * @param shippingAmount
     * @param amount
     * @param depFirstId
     * @param tradeId
     * @author lilei 2010/06/03
     */
    void updateWsShippingAndDepositoryById(Map map);

    /**
     * 按条件统计每个用户的总金额
     *
     * @return
     */
    List<Trade> getSumAmountGroupByUserId();

    /**
     * 获取当天的周期结算订单金额
     *
     * @param parMap
     * @return
     */
    double getTodayPeriodMoney(Map parMap);

    /**
     * 根据淘宝采购单Id取得订单
     *
     * @return
     */
    Trade getTradeByPurchaseId(String pid);

    /**
     *
     * 功能：根据本地订单ID取同步表中的远程订单ID<br>
     *
     * @param tid
     *            本地订单ID
     * @return 远程订单ID
     * @author shenzh Nov 3, 2010
     */
    String getRemoteTradeIdByTid(String tid);

    /**
     * 修改一级仓库id
     *
     * @param trade
     */
    void editTradeWithDepFirstId(Trade trade);

    /**
     * 查询符合条件集的结果数量
     *
     * @param parMap
     * @return
     */
    public int getTradesCountByParameterMap(Map parMap);

    /**
     * 取得订单各种状态的数量
     *
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Trade> getTradeCountWithStatus();

    /**
     * 订单Excel使用的sql
     *
     * @param query
     *            TradeListQuery
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Trade> getTradesExcelByParameterMap(TradeListQuery query);

    /**
     * 获得订单对应的开票公司（订单下商品所关联的开票公司中排序最靠前的那一个）
     *
     * @param tids
     *            要查询的订单号
     * @param rorder
     *            要查询的开票公司序号
     * @return key为订单号，value为对应的开票公司ID
     */
    Map<String, Long> getTradeGoodsBill(List<String> tids, Long rorder);
    
    public void updateTradeOrderPrice(Map<String,Object> parMap) throws Exception;
    
    long addCustomerOrderGetId(CustomerOrder customerOrder);
    
    void  updateCustomerOrderStatus(Map<String,Object> pramas);
    
    void updateLifecycleByProductId(Map<String,Object> pramas);
    
    long addHistoryGetId(HistoryView historyView);
    
    void updateTradeCustomerOrderIdOrder(Map<String,Object>  map);
    
    CustomerOrder getCustomerOrderById(Long id);
    
}
