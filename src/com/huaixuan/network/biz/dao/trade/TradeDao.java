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
     * �޸�ͬ��������������Ϣ
     *
     * @param trade
     *            @
     */
    void updateTradeExpressInfo(Trade trade);

    /**
     * ���ݳ��ⵥ��Ϣ����ʵ���˷�
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
     * �޸�������ʽ
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
     * @description �޸��Ա�ͬ������
     * @param tid
     * @param interfaceTaobaoCode
     * @return
     */
    int updateInterfaceExpressCode(String tid, String interfaceExpressCode);

    /**
     * ���ݳ��ⵥ��Ϣ����ʵ���˷�
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
     * �޸��������
     *
     * @return int
     * @author chenhang 2010-01-06
     */
    int updatetradeBuyerNote(String tid, String buyerNote);

    /**
     * �޸����ڽ���֧����ʽ�Ķ���������Ϣ
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
     * ��ѯ����Ҫ���Trades���
     *
     * @param query
     *            ��ѯ����
     * @return ����Ҫ���Trades���
     */
    List<Trade> getTradesByParameterMap(TradeListQuery query);

    /**
     * ��ҳ��ѯ����Ҫ���Trades���
     *
     * @param query
     *            ��ѯ����
     * @param currPage
     *            ��ǰҳ
     * @param pageSize
     *            ÿҳ����
     * @author fangqing
     * @return ����Ҫ���Trades���
     */
    QueryPage getTradesByParameterMap(TradeListQuery query, int currPage, int pageSize);

    /**
     * ��ѯ����Ҫ���Trades���
     *
     * @param parameterMap
     *            ����
     * @author fangqing
     * @return ����Ҫ���Trades���
     */
    List<Trade> getTradesByParameterMapWhitNote(TradeListQuery query);

    /**
     * ��ҳ��ѯ����Ҫ���Trades���
     *
     * @param parameterMap
     *            ����
     * @author fangqing
     * @return ����Ҫ���Trades���
     */
    QueryPage getTradesByParameterMapWhitNote(TradeListQuery query, int currPage, int pageSize);

    QueryPage getTradesWithNote(TradeListQuery query, int currPage, int pageSize);

    List<Trade> getTradesAlreadyPaid(Map parameterMap);

    /**
     * ����ʱ��17����������ȡ��17Ҫ������17����Ҫ�ṩ��ʱ��17����17�ã�17�Զ��ջ����Զ�ȡ�����`17
     *
     * @param status
     * @param day
     * @return
     */
    List<Trade> getTradesPartByTimetask(String status, int day);

    /**
     * ��Ч�����ܽ�Ԥ17
     *
     * @return ��Ч�����ܽ�Ԥ17
     */
    double countAllTradeAmount();

    double countAllFinishedTradeAmount();

    /**
     * ���ݶ����ŷ��ض��`17
     *
     * @return ����
     */
    Trade getTradeByTid(String tid);

    void updateTradeStatusByTid(Map<String, Object> pramas);

    Trade getTradeStatusByTid(Map<String, Object> pramas);

    void updateTradeStatusByTid(String status, String tid);

    void updateTradeByRefund(Map<String, Object> pramas);

    /**
     * ����refundId���½���״̬
     *
     * @param map
     *            Map
     * @author chenyan 2009/08/05
     */
    void updateTradeStatusByRefundId(Map map);

    /**
     * ���ݻ���IDȡ�ý��ױ�����
     *
     * @param refundId
     * @return Trade
     * @author chenyan 2009/08/19
     */
    Trade getTradeByRefundId(String refundId);

    /**
     * ����֧��״̬
     *
     * @param tid
     *            Long
     * @author chenyan 2009/08/26
     */
    void updatePayTimeByTid(Long tid);

    /**
     * ����ID�޸������۸��Լ���Ӧ���ܼ۸�
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
     * ����order��IDȡ�ö�����Ϣ
     *
     * @param id
     *            Long
     * @return Trade
     * @author chenyan 2009/09/11
     */
    Trade getTradeByOrderId(Long id);

    /**
     * ���ݶ�����Ʒ��ID���¼۸񣨶����ܼۣ������ܼۣ�
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
     * �����������ܶ����ܶ�
     *
     * @param query
     * @return
     */
    Trade getTradesGoodsAmountSum(TradeListQuery query);

    /**
     * ��ѯ�Ѿ���ɣ�����û���ۼӻ��ֵĶ���
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
     * ��Ʒ�ܽ��
     *
     * @param parMap
     * @return
     */
    double countAllGoodsAmount(Map parMap);

    /**
     * �˷��ܽ��
     *
     * @param parMap
     * @return
     */
    double countAllShipAmount(Map parMap);

    /**
     * ȡ�ô�����Ա�����۶�
     *
     * @author lilei 2010/06/03
     */
    List<TradeSalesCount> listTradeSalesCount();

    /**
     * �޸������������˷� �ܼ� һ���ֿ�
     *
     * @param shippingAmount
     * @param amount
     * @param depFirstId
     * @param tradeId
     * @author lilei 2010/06/03
     */
    void updateWsShippingAndDepositoryById(Map map);

    /**
     * ������ͳ��ÿ���û����ܽ��
     *
     * @return
     */
    List<Trade> getSumAmountGroupByUserId();

    /**
     * ��ȡ��������ڽ��㶩�����
     *
     * @param parMap
     * @return
     */
    double getTodayPeriodMoney(Map parMap);

    /**
     * �����Ա��ɹ���Idȡ�ö���
     *
     * @return
     */
    Trade getTradeByPurchaseId(String pid);

    /**
     *
     * ���ܣ����ݱ��ض���IDȡͬ�����е�Զ�̶���ID<br>
     *
     * @param tid
     *            ���ض���ID
     * @return Զ�̶���ID
     * @author shenzh Nov 3, 2010
     */
    String getRemoteTradeIdByTid(String tid);

    /**
     * �޸�һ���ֿ�id
     *
     * @param trade
     */
    void editTradeWithDepFirstId(Trade trade);

    /**
     * ��ѯ�����������Ľ������
     *
     * @param parMap
     * @return
     */
    public int getTradesCountByParameterMap(Map parMap);

    /**
     * ȡ�ö�������״̬������
     *
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Trade> getTradeCountWithStatus();

    /**
     * ����Excelʹ�õ�sql
     *
     * @param query
     *            TradeListQuery
     * @return List
     * @author chenyan 2011/04/19
     */
    List<Trade> getTradesExcelByParameterMap(TradeListQuery query);

    /**
     * ��ö�����Ӧ�Ŀ�Ʊ��˾����������Ʒ�������Ŀ�Ʊ��˾�������ǰ����һ����
     *
     * @param tids
     *            Ҫ��ѯ�Ķ�����
     * @param rorder
     *            Ҫ��ѯ�Ŀ�Ʊ��˾���
     * @return keyΪ�����ţ�valueΪ��Ӧ�Ŀ�Ʊ��˾ID
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
