package com.huaixuan.network.biz.dao.trade.ibatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.domain.hy.CustomerOrder;
import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradeSalesCount;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 *
 * @version 3.2.0
 */
@Repository("tradeDao")
public class TradeDaoiBatis implements TradeDao {

    Log                          log = LogFactory.getLog(this.getClass());

    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public List<Trade> getTradesByParameterMap(TradeListQuery query) {
        return sqlMapClientTemplate.queryForList("getTradesByParameterMap", query);
    }

    @Override
    public QueryPage getTradesByParameterMap(TradeListQuery query, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(query);
        queryPage.setCurrentPage(currPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClientTemplate.queryForObject("getTradesCount", query);
        queryPage.setTotalItem(count);

        if (count > 0) {
            query.setStartRow(queryPage.getPageFristItem());
            query.setEndRow(queryPage.getPageLastItem());

            /* 分页查询操作员记录 */
            queryPage.setItems(sqlMapClientTemplate.queryForList("getTradesByParameterMap", query));
        }

        return queryPage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Trade> getTradesByParameterMapWhitNote(TradeListQuery query) {
        return sqlMapClientTemplate.queryForList("getTradesByParameterMapWhitNote", query);
    }

    @Override
    public QueryPage getTradesByParameterMapWhitNote(TradeListQuery query, int currPage,
                                                     int pageSize) {
        QueryPage queryPage = new QueryPage(new Trade());
        queryPage.setCurrentPage(currPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClientTemplate.queryForObject(
            "getTradesByParameterMapWhitNoteCount", query);
        queryPage.setTotalItem(count);

        if (count > 0) {
            query.setStartRow(queryPage.getPageFristItem());
            query.setEndRow(queryPage.getPageLastItem());

            /* 分页查询操作员记录 */
            queryPage.setItems(sqlMapClientTemplate.queryForList("getTradesByParameterMapWhitNote",
                query));
        }

        return queryPage;
    }

    @Override
    public QueryPage getTradesWithNote(TradeListQuery query, int currPage, int pageSize) {
        QueryPage queryPage = new QueryPage(new Trade());
        queryPage.setCurrentPage(currPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClientTemplate.queryForObject("getTradesWithNoteCount", query);
        queryPage.setTotalItem(count);

        if (count > 0) {
            query.setStartRow(queryPage.getPageFristItem());
            query.setEndRow(queryPage.getPageLastItem());

            /* 分页查询操作员记录 */
            queryPage.setItems(sqlMapClientTemplate.queryForList("getTradesWithNote", query));
        }

        return queryPage;
    }

    @SuppressWarnings("unchecked")
    public List<Trade> getTradesAlreadyPaid(Map parameterMap) {
        return sqlMapClientTemplate.queryForList("getTradesAlreadyPaid", parameterMap);
    }
/* @model: */
    public void addTrade(Trade trade) {
        sqlMapClientTemplate.insert("addTrade", trade);
    }

    public long addTradeGetId(Trade trade) {
        return (Long) this.sqlMapClientTemplate.insert("addTrade", trade);
    }
/* @model: */
    public void editTrade(Trade trade) {
        sqlMapClientTemplate.update("editTrade", trade);
    }

    public void updateTradeExpressInfo(Trade trade) {
        sqlMapClientTemplate.update("updateTradeExpressInfo", trade);
    }

    public void updatePeriodTradeExpressInfo(Trade trade) {
        sqlMapClientTemplate.update("updatePeriodTradeExpressInfo", trade);
    }
/* @model: */
    public void removeTrade(Long tradeId) {
        sqlMapClientTemplate.delete("removeTrade", tradeId);
    }
/* @model: */
    public Trade getTrade(Long tradeId) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTrade", tradeId);
    }
/* @model: */
    public List<Trade> getTrades() {
        return sqlMapClientTemplate.queryForList("getTrades", null);
    }

    public List<Trade> getTradesPartByTimetask(String status, int day) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("day", new Integer(day));
        return sqlMapClientTemplate.queryForList("getTradesPartByTimetask", map);
    }

    public List<Trade> getTradesByIsPoint(String status, String isPoint, Long buyUserId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("isPoint", isPoint);
        map.put("buyUserId", buyUserId);
        return sqlMapClientTemplate.queryForList("getTradesPartByTimetask", map);
    }

    /**
     * 有效订单总金预
     *
     * @return 有效订单总金预
     */
    public double countAllTradeAmount() {
        return (Double) sqlMapClientTemplate.queryForObject("countAllTradeAmount");
    }

    public double countAllFinishedTradeAmount() {
        return (Double) sqlMapClientTemplate.queryForObject("countAllFinishedTradeAmount");
    }

    public Trade getTradeByTid(String tid) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTradeByTid", tid);
    }

    public void updateTradeStatusByTid(Map<String, Object> pramas) {
        sqlMapClientTemplate.update("updateTradeStatusByTid", pramas);
    }

    public int updatetradeBuyerNote(String tid, String buyerNote) {
        Map map = new HashMap();
        map.put("tid", tid);
        map.put("buyerNote", buyerNote);
        return (Integer) sqlMapClientTemplate.update("updateTradeBuyerNote", map);
    }

    public int updateTradeExpressId(String tid, String expressId) {
        Map map = new HashMap();
        map.put("tid", tid);
        map.put("expressId", expressId);
        return (Integer) sqlMapClientTemplate.update("updateTradeExpressId", map);
    }

    public int updateInterfaceExpressCode(String tid, String interfaceExpressCode) {
        Map map = new HashMap();
        map.put("tid", tid);
        map.put("interfaceExpressCode", interfaceExpressCode);
        return (Integer) sqlMapClientTemplate.update("updateInterfaceExpressCode", map);
    }

    public Trade getTradeStatusByTid(Map<String, Object> pramas) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTradeStatusByTid", pramas);
    }

    public void updateTradeByRefund(Map<String, Object> pramas) {
        sqlMapClientTemplate.update("updateTradeByRefund", pramas);
    }

    public void updateTradeStatusByTid(String status, String tid) {
        if (StringUtil.isNotBlank(tid)) {
            Map map = new HashMap();
            map.put("status", status);
            map.put("tid", tid);
            sqlMapClientTemplate.update("updateTradeStatusByTid", map);
        }
    }

    public void updateTradeStatusByRefundId(Map map) {
        sqlMapClientTemplate.update("updateTradeStatusByRefundId", map);
    }

    public Trade getTradeByRefundId(String refundId) {
        if (StringUtil.isBlank(refundId)) {
            return null;
        }
        return (Trade) sqlMapClientTemplate.queryForObject("getTradeByRefundId", refundId);
    }

    @SuppressWarnings("unchecked")
    public void updatePayTimeByTid(Long tid) {
        Map map = new HashMap();
        map.put("gmtNow", new Date());
        map.put("tid", tid);
        sqlMapClientTemplate.update("updatePayTimeByTid", map);
    }

    @SuppressWarnings("unchecked")
    public void updateShippingAmountById(Double shippingAmount, Double amount, String memo, Long id) {
        Map map = new HashMap();
        map.put("gmtNow", new Date());
        map.put("shippingAmount", shippingAmount);
        map.put("amount", amount);
        map.put("memo", memo);
        map.put("id", id);
        sqlMapClientTemplate.update("updateShippingAmountById", map);
    }

    public Trade getTradeByOrderId(Long id) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTradeByOrderId", id);
    }

    @SuppressWarnings("unchecked")
    public void updateAmountByGoodsPrice(Double goodsAmount, Double amount, String memo,
                                         Long orderId) {
        Map map = new HashMap();
        map.put("goodsAmount", goodsAmount);
        map.put("amount", amount);
        map.put("memo", memo);
        map.put("orderId", orderId);
        sqlMapClientTemplate.update("updateAmountByGoodsPrice", map);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.hundsun.bible.dao.TradeDao#getTradesGoodsAmountSum(java.util.Map)
     */
    public Trade getTradesGoodsAmountSum(TradeListQuery query) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTradesGoodsAmountSum", query);
    }

    public void updateMessageByTradeId(Long tradeID, String message)

    {
        Map map = new HashMap();
        map.put("tradeID", tradeID);
        map.put("message", message);
        sqlMapClientTemplate.update("updateMessageByTradeId", map);
    }

    public void updateInvoiceByTradeId(Long tradeID, String invoice) {
        Map map = new HashMap();
        map.put("tradeID", tradeID);
        map.put("invoice", invoice);
        sqlMapClientTemplate.update("updateInvoiceByTradeId", map);
    }

    public int updateIspurchasedByTradeId(Long tradeID) {
        Map map = new HashMap();
        map.put("tradeID", tradeID);
        return sqlMapClientTemplate.update("updateIspurchasedByTradeId", map);
    }

    public Trade getTradeByMap(Map<String, String> parMap) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTradeByMap", parMap);
    }

    public double countAllGoodsAmount(Map parMap) {
        return (Double) sqlMapClientTemplate.queryForObject("countAllGoodsAmount", parMap);
    }

    public double countAllShipAmount(Map parMap) {
        return (Double) sqlMapClientTemplate.queryForObject("countAllShipAmount", parMap);
    }

    public List<TradeSalesCount> listTradeSalesCount() {
        if (log.isDebugEnabled()) {
            log.debug("TradeDaoiBatis listTradeSalesCount");
        }
        return sqlMapClientTemplate.queryForList("listUsersSalesCount");
    }

    /**
     * <p>
     * Title: updateActualInventoryById
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param actualInventory
     * @param id
     * @author chenhang 2010/11/18
     */
    public int updateActualInventoryById(Double actualInventory, String reNum) {
        if (reNum == null) {
            return 0;
        }
        Map map = new HashMap();
        map.put("actualInventory", actualInventory);
        map.put("relationNum", reNum);
        return sqlMapClientTemplate.update("updateActualInventoryById", map);
    }

    /**
     * <p>
     * Title: updateActualInventoryById
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param actualInventory
     * @param id
     * @author  2010/11/18
     */
    public int updateActualInventoryByIdRe(Double actualInventory, String reNum) {
        if (reNum == null) {
            return 0;
        }
        Map map = new HashMap();
        map.put("actualInventory", actualInventory);
        map.put("relationNum", reNum);
        return sqlMapClientTemplate.update("updateActualInventoryByIdRe", map);
    }

    public void updateWsShippingAndDepositoryById(Map map) {
        if (log.isDebugEnabled()) {
            log.debug("TradeDaoiBatis updateShippingAmountById");
        }
        sqlMapClientTemplate.update("updateShippingAmountById", map);
    }

    public List<Trade> getSumAmountGroupByUserId() {
        return sqlMapClientTemplate.queryForList("getSumAmountGroupByUserId", null);
    }

    public double getTodayPeriodMoney(Map parMap) {
        return (Double) sqlMapClientTemplate.queryForObject("getTodayPeriodMoney", parMap);
    }

    public Trade getTradeByPurchaseId(String pid) {
        return (Trade) sqlMapClientTemplate.queryForObject("getTradeByPurchaseId", pid);
    }

    public String getRemoteTradeIdByTid(String tid) {

        return (String) sqlMapClientTemplate.queryForObject("getRemoteTradeIdByTid", tid);
    }

    public void editTradeWithDepFirstId(Trade trade) {
        sqlMapClientTemplate.update("editTradeWithDepFirstId", trade);
    }

    @Override
    public int getTradesCountByParameterMap(Map parMap) {
        return (Integer) this.sqlMapClientTemplate.queryForObject("getTradesCount", parMap);
    }

    public List<Trade> getTradeCountWithStatus() {
        return this.sqlMapClientTemplate.queryForList("getTradeCountWithStatus");
    }

    public List<Trade> getTradesExcelByParameterMap(TradeListQuery query) {
        return this.sqlMapClientTemplate.queryForList("getTradesExcelByParameterMap", query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Long> getTradeGoodsBill(List<String> tids, Long rorder) {
        Map<String, Long> r = new HashMap<String, Long>();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("rorder", rorder);
        if (tids != null) {
            // in 中最大数目为1000个
            int step = 900;
            for (int i = 0; i * step < tids.size(); i++) {
                int toIndex = (i + 1) * step > tids.size() ? tids.size() : (i + 1) * step;
                param.put("tids", tids.subList(i * step, toIndex));
                Map<String, BigDecimal> tradeGoodsBillIdMap = sqlMapClientTemplate.queryForMap(
                    "getTradeGoodsBill", param, "TID", "BILLID");
                for (Entry<String, BigDecimal> en : tradeGoodsBillIdMap.entrySet()) {
                    r.put(en.getKey(), en.getValue().longValue());
                }
            }
        } else {
            Map<String, BigDecimal> tradeGoodsBillIdMap = sqlMapClientTemplate.queryForMap(
                "getTradeGoodsBill", param, "TID", "BILLID");
            for (Entry<String, BigDecimal> en : tradeGoodsBillIdMap.entrySet()) {
                r.put(en.getKey(), en.getValue().longValue());
            }
        }
        return r;
    }

	@Override
	public void updateTradeOrderPrice(Map<String, Object> parMap)
			throws Exception {
		sqlMapClientTemplate.update("updateTradeOrderPrice", parMap);
	}
	
	public long addCustomerOrderGetId(CustomerOrder customerOrder) {
        return (Long) this.sqlMapClientTemplate.insert("addCustomerOrderGetId", customerOrder);
    }
	
	public void updateCustomerOrderStatus(Map<String,Object> pramas) {
        this.sqlMapClientTemplate.update("updateCustomerOrderStatus", pramas);
    }
	
	public void updateLifecycleByProductId(Map<String,Object> pramas){
		this.sqlMapClientTemplate.update("updateLifecycleByProductId", pramas);
	}
	
	public long addHistoryGetId(HistoryView historyView) {
        return (Long) this.sqlMapClientTemplate.insert("addHistoryGetId", historyView);
    }
	
	public void updateTradeCustomerOrderIdOrder(Map<String,Object> pramas){
		this.sqlMapClientTemplate.update("updateTradeCustomerOrderIdOrder", pramas);
	}
	
	public CustomerOrder getCustomerOrderById(Long id){
		return (CustomerOrder)this.sqlMapClientTemplate.queryForObject("getCustomerOrderById", id);
	}
	
}
