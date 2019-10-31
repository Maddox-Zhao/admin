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
 * 代码自动生成(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface TradeManager {
    // /* @interface model: 添加丄1??7条Trade记录 */
    // public void addTrade(Trade trade);

    /* @interface model: 更新丄1??7条Trade记录 */
    public void editTrade(Trade trade);

    //
    // /* @interface model: 删除丄1??7条Trade记录 */
    // public void removeTrade(Long tradeId);

    /* @interface model: 查询丄1??7个Trade结果雄1??7,返回Trade对象 */
    public Trade getTrade(Long tradeId);

    //
    // /* @interface model: 查询扄1??7有Trade结果雄1??7,返回Trade对象的集各1??7 */
    public List<Trade> getTrades();

    //
    // /**
    // * 查询此用户全部订卄1??7
    // * @param userId
    // * @return
    // */
    // public List<Trade> getTradesByUserId(Long userId);
    //
    // /**
    // *
    // * 增加代销会员购买记录
    // */
    // public void insertAgentTrade(AgentTrade agentTrade, User user);

    /**
     * 查询符合参数集ParameterMap要求的Trades结果雄1??7
     *
     * @param parameterMap
     *            参数雄1??7
     * @return 符合参数集ParameterMap要求的Trades结果雄1??7
     */
    public List<Trade> getTradesByParameterMap(TradeListQuery query);

    // /**
    // * 付款成功的订卄1??7
    // * @param parameterMap
    // * @return
    // */
    // public List<Trade> getTradesAlreadyPaid(Map parameterMap);

    /**
     * 分页查询符合要求的Trades结果
     *
     * @param query
     *            {@linkplain com.huaixuan.network.biz.query.TradeListQuery}
     * @param currPage
     * @param pageSize
     * @return
     */
    public QueryPage getTradesByParameterMap(TradeListQuery query, int currPage, int pageSize);

    /**
     * 分页查询符合参数集ParameterMap要求的Trades结果雄1??7
     *
     * @param parameterMap
     *            参数雄1??7
     * @param page
     *            分页信息
     * @author fangqing
     * @return 符合参数集ParameterMap要求的Trades结果雄1??7
     */
    public List<Trade> getTradesByParameterMapWhitNote(TradeListQuery query);

    /**
     * 分页查询符合参数集ParameterMap要求的Trades结果雄1??7
     *
     * @param parameterMap
     *            参数雄1??7
     * @param page
     *            分页信息
     * @author fangqing
     * @return 符合参数集ParameterMap要求的Trades结果雄1??7
     */
    public QueryPage getTradesByParameterMapWhitNote(TradeListQuery query, int currPage,
                                                     int pageSize);

    /**
     * 修改交易状??1??7????1??7??1??7
     *
     * @param id
     * @param status
     * @return
     */
    public int updateTradeStatus(String id, String status);

    /**
     * 修改物流方式
     *
     * @param id
     * @param expressId
     * @return
     */
    public int updateTradeExpressId(String id, String expressId);

    /**
     *
     * @author chenhang 2011-5-5
     * @description 修改淘宝同步物流
     * @param id
     * @param interfaceTaobaoCode
     * @return
     */
    public int updateInterfaceExpressCode(String id, String interfaceExpressCode);

    /**
     * 查询符合条件集的结果数量
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
    // * payment支付回调接口
    // * @param packageId 支付包ID
    // * @param payment
    // * @return
    // */
    public TradeResult updateTradeStatusByPayment(final String packageId, final String payment);

    //
    // /**
    // * 修改交易状??1??7????1??7??1??7
    // * @param tid
    // * @param status
    // * @return
    // */
    // public void updateTradeStatusByTid(String tid, String status);
    //
    // /**
    // * 修改交易状??1??7????1??7??1??7
    // * @param tid
    // * @param status
    // * @return
    // */
    // public Trade getTradeStatusByTid(String tid);
    //
    // /**
    // * 交易支付
    // * @param id
    // * @return
    // */
    // public int tradePay(String id);
    //
    // /**
    // * 根据时间??1??7和天数，获取霄1??7要的数据??1??7，主要提供给时间??1??7程序??1??7用，??1??7自动收货、自动取消订卄1??7
    // *@param status
    // * @param day
    // * @return
    // */
    List<Trade> getTradesPartByTimetask(String status, int day);

    QueryPage getTradesWithNote(TradeListQuery query, int currPage, int pageSize);

    //
    // /**
    // * 有效订单总金预1??7
    // * @return 有效订单总金预1??7
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
     * 根据订单号返回订单信息
     *
     * @return 订单
     */
    Trade getTradeByTid(String tid);

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

    //
    // /**
    // * 根据order表ID取得订单信息
    // * @param id Long
    // * @return Trade
    // * @author chenyan 2009/09/11
    // */
    // Trade getTradeByOrderId(Long id);
    //
    // /**
    // * 根据订单商品表ID更新价格（单个订单商品价格，订单总价，交易总价）
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
     * 根据条件汇总订单总额
     *
     * @param query
     * @return
     */
    public Trade getTradesGoodsAmountSum(TradeListQuery query);

    // /**查询已经完成，并且没有累加积分的订单
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
     * 后台下订单
     *
     * @param goodsInstanceList
     */
    public void backSaveTrade(List<GoodsInstance> goodsInstanceList, DepositoryFirst depfirst,
                              User user, Trade trade, UserAddress userAddress, double weight,
                              double epWeight, double goodsAmount);

    //
    public void savePaipaiTrade(Long depFirstId, Long userId, Trade trade, String shopType);

    /**
     * 总商品金额
     *
     * @return
     */
    public double countAllGoodsAmount(Map parMap);

    /**
     * 总运费金额
     *
     * @param parMap
     * @return
     */
    public double countAllShipAmount(Map parMap);

    //
    // /**
    // * 取得代销会员的销售额
    // * @author lilei 2010/06/03
    // */
    // public List<TradeSalesCount> listTradeSalesCount();
    //
    // /**
    // * 批发下订单
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
     * 修改批发订单的运费 总价 一级仓库
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
     * 获取当天的周期结算订单金额
     *
     * @author zhangwy
     * @param parMap
     * @return
     */
    public double getTodayPeriodMoney(Map parMap);

    //
    // /**
    // * 淘宝分销接口同步生成订单
    // * @param depFirstId
    // * @param userId
    // * @param trade
    // */
    // public void saveFenxiaoTrade(Long depFirstId, Long userId, Trade trade);

    /**
     * 处理淘宝同步过来的订单
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
    // * 根据淘宝采购单Id取得订单
    // * @return
    // */
    // public Trade getTradeByPurchaseId(String pid);
    //
    // /**
    // * 根据淘宝采购单设置金额信息
    // * @return
    // */
    // public List<Trade> setTradeAmount(List<Trade> trade);
    //
    // /**
    // * 修改同步订单的物流信息
    // * @param id
    // * @param shippingAmount
    // * @param expressId
    // * @param expressPayMent
    // * @return 成功返回1，失败返回0
    // */
    // public int updateTradeExpressInfo(Long id, Double shippingAmount, Long
    // expressId, String expressPayment,User
    // user);
    //
    // /**
    // *
    // * 功能：根据本地订单ID取同步表中的远程订单ID<br>
    // *
    // * @param tid 本地订单ID
    // * @return 远程订单ID
    // * @author shenzh
    // * Nov 3, 2010
    // */
    // String getRemoteTradeIdByTid(String tid);

    /**
     * 修改一级仓库id
     *
     * @param trade
     */
    public void editTradeWithDepFirstId(Trade trade);

    //
    // /**
    // * 修改买家留言
    // * @param tid
    // * @param buyNote
    // * @return int
    // * @author chenhang 2010-01-06
    // */
    // int updateTradeBuyerNote(String tid,String buyerNote);

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

    /**
     * 将.csv中的数据读入到一个LIST<MAP>对象中
     * @author chenhang 2011-5-12
     * @description
     * @param filePath
     * @return
     */
    List<Object> readExcel(MultipartFile filePath, Long userId);
    
    /**
     * 给订单添加对应型号的产品
     * @author songfy 2012-12-08
     * @description
     * @param 
     * @return
     */
    public void saveProductToTrade(List<Product> productList,List<Order> orderList,Trade trade);
    
    /**
     * 更新订单价格
     * @author songfy 2012-12-08
     * @description
     * @param 
     * @return
     */
    public void updateTradeOrderPrice(Map<String,Object> parMap);
    
    /**
     * 给订单添加对应型号的产品，并改变订单状态
     * @author songfy 2012-12-08
     * @description
     * @param 
     * @return
     */
    public void saveProductToTradeAndChangeTradeStatus(List<Product> productList,List<Order> orderList,Trade trade);
    
    public int sellerConfirmPay(Trade trade);
    
}
