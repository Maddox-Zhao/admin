package com.huaixuan.network.biz.dao.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.query.TradeListQuery;

public interface PayRecordDao {

    void addPayRecord(PayRecord payRecord);

    void editPayRecord(PayRecord payRecord);

    void removePayRecord(Long payRecordId);

    PayRecord getPayRecord(Long payRecordId);

    List<PayRecord> getPayRecords();

    void updatePayRecordByNotify(PayRecord payRecord);

    PayRecord getPayRecordByTid(String tid);

    List<PayRecord> getPayRecordLists(Map parMap);

    int getPayRecordListsCount(Map parMap);

    PayRecord getPayAmountByPlatform(TradeListQuery tradeListQuery);

    //	/**
    //	 * 货到付款收款列表
    //	 *
    //	 * @param parMap
    //	 * @return
    //	 */
    //	List<PayRecord> getPayRecordListsByCod(Map parMap, int currPage, int pageSize);

    /**
     * 货到付款收款数量
     *
     * @return
     */
    int getCodPayRecordListsCount(Map parMap);

    /**
     * 货到付款收款统计
     *
     * @param parMap
     * @return
     */
    PayRecord getPayAmountByCod(Map parMap);
}
