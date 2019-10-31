package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.crm.DoUploadTrade;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradeResult;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;

/**
 * �����Զ�����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface TradeManager {
    // /* @interface model: ��ӁA1??7��Trade��¼ */
    // public void addTrade(Trade trade);

    /* @interface model: ���A1??7��Trade��¼ */
    public void editTrade(Trade trade);

    //
    // /* @interface model: ɾ���A1??7��Trade��¼ */
    // public void removeTrade(Long tradeId);

    /* @interface model: ��ѯ�A1??7��Trade�����1??7,����Trade���� */
    public Trade getTrade(Long tradeId);

    //
    // /* @interface model: ��ѯ��1??7��Trade�����1??7,����Trade����ļ���1??7 */
    public List<Trade> getTrades();

    //
    // /**
    // * ��ѯ���û�ȫ�����`1??7
    // * @param userId
    // * @return
    // */
    // public List<Trade> getTradesByUserId(Long userId);
    //
    // /**
    // *
    // * ���Ӵ�����Ա�����¼
    // */
    // public void insertAgentTrade(AgentTrade agentTrade, User user);

    /**
     * ��ѯ���ϲ�����ParameterMapҪ���Trades�����1??7
     *
     * @param parameterMap
     *            ������1??7
     * @return ���ϲ�����ParameterMapҪ���Trades�����1??7
     */
    public List<Trade> getTradesByParameterMap(TradeListQuery query);

    // /**
    // * ����ɹ��Ķ��`1??7
    // * @param parameterMap
    // * @return
    // */
    // public List<Trade> getTradesAlreadyPaid(Map parameterMap);

    /**
     * ��ҳ��ѯ����Ҫ���Trades���
     *
     * @param query
     *            {@linkplain com.huaixuan.network.biz.query.TradeListQuery}
     * @param currPage
     * @param pageSize
     * @return
     */
    public QueryPage getTradesByParameterMap(TradeListQuery query, int currPage, int pageSize);

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
    public List<Trade> getTradesByParameterMapWhitNote(TradeListQuery query);

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
                                                     int pageSize);

    /**
     * �޸Ľ���״??1??7????1??7??1??7
     *
     * @param id
     * @param status
     * @return
     */
    public int updateTradeStatus(String id, String status);

    /**
     * �޸�������ʽ
     *
     * @param id
     * @param expressId
     * @return
     */
    public int updateTradeExpressId(String id, String expressId);

    /**
     *
     * @author chenhang 2011-5-5
     * @description �޸��Ա�ͬ������
     * @param id
     * @param interfaceTaobaoCode
     * @return
     */
    public int updateInterfaceExpressCode(String id, String interfaceExpressCode);

    /**
     * ��ѯ�����������Ľ������
     *
     * @param parMap
     * @return
     */
    public int getTradesCountByParameterMap(Map parMap);

    //
    // public void updateTradeStatusByNotify(String tid, String status, String
    // payment);
    //
    // /**
    // * payment֧���ص��ӿ�
    // * @param packageId ֧����ID
    // * @param payment
    // * @return
    // */
    public TradeResult updateTradeStatusByPayment(final String packageId, final String payment);

    //
    // /**
    // * �޸Ľ���״??1??7????1??7??1??7
    // * @param tid
    // * @param status
    // * @return
    // */
    // public void updateTradeStatusByTid(String tid, String status);
    //
    // /**
    // * �޸Ľ���״??1??7????1??7??1??7
    // * @param tid
    // * @param status
    // * @return
    // */
    // public Trade getTradeStatusByTid(String tid);
    //
    // /**
    // * ����֧��
    // * @param id
    // * @return
    // */
    // public int tradePay(String id);
    //
    // /**
    // * ����ʱ��??1??7����������ȡ��1??7Ҫ������??1??7����Ҫ�ṩ��ʱ��??1??7����??1??7�ã�??1??7�Զ��ջ����Զ�ȡ�����`1??7
    // *@param status
    // * @param day
    // * @return
    // */
    List<Trade> getTradesPartByTimetask(String status, int day);

    QueryPage getTradesWithNote(TradeListQuery query, int currPage, int pageSize);

    //
    // /**
    // * ��Ч�����ܽ�Ԥ1??7
    // * @return ��Ч�����ܽ�Ԥ1??7
    // */
    public double countAllTradeAmount();

    //
    // public double countAllFinishedTradeAmount();
    //
    // public void saveTrade( Map<ShoppingCart,Long> goodsShoppingTmp,//List
    // goodsCartIdList,
    // List promationIdList, PromationVO givePromationVO,
    // User user, Trade trade, String saveType,
    // Map<String, Object> agentSellPriceMap, List<PromationGive>
    // promationGiveAll);

    /**
     * ���ݶ����ŷ��ض�����Ϣ
     *
     * @return ����
     */
    Trade getTradeByTid(String tid);

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

    //
    // /**
    // * ����order��IDȡ�ö�����Ϣ
    // * @param id Long
    // * @return Trade
    // * @author chenyan 2009/09/11
    // */
    // Trade getTradeByOrderId(Long id);
    //
    // /**
    // * ���ݶ�����Ʒ��ID���¼۸񣨵���������Ʒ�۸񣬶����ܼۣ������ܼۣ�
    // * @param goodsPrice Double
    // * @param goodsAmount Double
    // * @param amount Double
    // * @param orderId Long
    // * @param memo String
    // * @author zhangwy 2010/10/09
    // */
    // void updateAmountByGoodsPrice(Double goodsPrice, Double goodsAmount,
    // Double amount, String memo, Long orderId);

    /**
     * �����������ܶ����ܶ�
     *
     * @param query
     * @return
     */
    public Trade getTradesGoodsAmountSum(TradeListQuery query);

    // /**��ѯ�Ѿ���ɣ�����û���ۼӻ��ֵĶ���
    // * @param status
    // * @param isPoint
    // * @return
    // */
    // List<Trade> getTradesByIsPoint(String status, String isPoint, Long
    // buyUserId);

    void updateMessageByTradeId(Long tradeID, String message);

    void updateInvoiceByTradeId(Long tradeID, String invoice);

    int updateIspurchasedByTradeId(Long tradeID);

    //
    public Trade getTradeByMap(Map<String, String> parMap);

    //
    // public boolean warnSendGoods(HttpServletRequest request);
    //
    /**
     * ��̨�¶���
     *
     * @param goodsInstanceList
     */
    public void backSaveTrade(List<GoodsInstance> goodsInstanceList, DepositoryFirst depfirst,
                              User user, Trade trade, UserAddress userAddress, double weight,
                              double epWeight, double goodsAmount);

    //
    public void savePaipaiTrade(Long depFirstId, Long userId, Trade trade, String shopType);

    /**
     * ����Ʒ���
     *
     * @return
     */
    public double countAllGoodsAmount(Map parMap);

    /**
     * ���˷ѽ��
     *
     * @param parMap
     * @return
     */
    public double countAllShipAmount(Map parMap);

    //
    // /**
    // * ȡ�ô�����Ա�����۶�
    // * @author lilei 2010/06/03
    // */
    // public List<TradeSalesCount> listTradeSalesCount();
    //
    // /**
    // * �����¶���
    // * @param loginFlag
    // * @param goodsCartIdList
    // * @param isLoged
    // * @param loginUserInfo
    // * @param userAddressList
    // * @param userAddressMap
    // * @return
    // */
    // public Result<Object> submitToWholeTrade(String loginFlag,
    // List<ShoppingCart> goodsCartIdList, boolean isLoged ,
    // UserInfo loginUserInfo,UserAddress userAddress,Trade trade);

    /**
     * �޸������������˷� �ܼ� һ���ֿ�
     *
     * @param shippingAmount
     * @param amount
     * @param depFirstId
     * @param tradeId
     * @param meno
     * @author zhangwy 2010/10/09
     */
    public void updateWsShippingAndDepositoryById(Double shippingAmount, Double amount,
                                                  String memo, Long depFirstId, Long tradeId);

    /**
     * ��ȡ��������ڽ��㶩�����
     *
     * @author zhangwy
     * @param parMap
     * @return
     */
    public double getTodayPeriodMoney(Map parMap);

    //
    // /**
    // * �Ա������ӿ�ͬ�����ɶ���
    // * @param depFirstId
    // * @param userId
    // * @param trade
    // */
    // public void saveFenxiaoTrade(Long depFirstId, Long userId, Trade trade);

    /**
     * �����Ա�ͬ�������Ķ���
     *
     * @param depFirstId
     *            Long
     * @param userId
     *            Long
     * @param ownExpressId
     *            Long
     * @param interfaceExpressCode
     *            String
     * @param trade
     *            Trade
     * @modified by chenyan 2011/03/11
     */
    public void saveTaobaoTrade(Long depFirstId, Long userId, Long ownExpressId,
                                String interfaceExpressCode, Trade trade);

    //
    // /**
    // * �����Ա��ɹ���Idȡ�ö���
    // * @return
    // */
    // public Trade getTradeByPurchaseId(String pid);
    //
    // /**
    // * �����Ա��ɹ������ý����Ϣ
    // * @return
    // */
    // public List<Trade> setTradeAmount(List<Trade> trade);
    //
    // /**
    // * �޸�ͬ��������������Ϣ
    // * @param id
    // * @param shippingAmount
    // * @param expressId
    // * @param expressPayMent
    // * @return �ɹ�����1��ʧ�ܷ���0
    // */
    // public int updateTradeExpressInfo(Long id, Double shippingAmount, Long
    // expressId, String expressPayment,User
    // user);
    //
    // /**
    // *
    // * ���ܣ����ݱ��ض���IDȡͬ�����е�Զ�̶���ID<br>
    // *
    // * @param tid ���ض���ID
    // * @return Զ�̶���ID
    // * @author shenzh
    // * Nov 3, 2010
    // */
    // String getRemoteTradeIdByTid(String tid);

    /**
     * �޸�һ���ֿ�id
     *
     * @param trade
     */
    public void editTradeWithDepFirstId(Trade trade);

    //
    // /**
    // * �޸��������
    // * @param tid
    // * @param buyNote
    // * @return int
    // * @author chenhang 2010-01-06
    // */
    // int updateTradeBuyerNote(String tid,String buyerNote);

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

    /**
     * ��.csv�е����ݶ��뵽һ��LIST<MAP>������
     * @author chenhang 2011-5-12
     * @description
     * @param filePath
     * @return
     */
    List<Object> readExcel(MultipartFile filePath, Long userId);
    
    /**
     * ��������Ӷ�Ӧ�ͺŵĲ�Ʒ
     * @author songfy 2012-12-08
     * @description
     * @param 
     * @return
     */
    public void saveProductToTrade(List<Product> productList,List<Order> orderList,Trade trade);
    
    /**
     * ���¶����۸�
     * @author songfy 2012-12-08
     * @description
     * @param 
     * @return
     */
    public void updateTradeOrderPrice(Map<String,Object> parMap);
    
    /**
     * ��������Ӷ�Ӧ�ͺŵĲ�Ʒ�����ı䶩��״̬
     * @author songfy 2012-12-08
     * @description
     * @param 
     * @return
     */
    public void saveProductToTradeAndChangeTradeStatus(List<Product> productList,List<Order> orderList,Trade trade);
    
    public int sellerConfirmPay(Trade trade);
    
}
