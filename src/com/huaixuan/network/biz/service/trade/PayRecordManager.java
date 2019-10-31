package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;

public interface PayRecordManager {

    void addPayRecord(PayRecord payRecord);

    void editPayRecord(PayRecord payRecord);

    void removePayRecord(Long payRecordId);

    PayRecord getPayRecord(Long payRecordId);

    List<PayRecord> getPayRecords();

    void updatePayRecordByNotify(PayRecord payRecord);

    PayRecord getPayRecordByTid(String tid);

//    int getPayRecordListsCount(Map parMap);

    QueryPage getPayRecordLists(TradeListQuery tradeListQuery, int currentPage, int pageSize) throws Exception;

    PayRecord getPayAmountByPlatform(TradeListQuery tradeListQuery);

    //	 /**
    //	 * ���������տ��б�
    //	 *
    //	 * @param parMap
    //	 * @return
    //	 */
    //	  List<PayRecord> getPayRecordListsByCod(Map parMap, Page page);

    /**
    * ���������տ�����
    *
    * @return
    */
    int getCodPayRecordListsCount(Map parMap);

    /**
    * ���������տ�ͳ��
    *
    * @param parMap
    * @return
    */
    PayRecord getPayAmountByCod(Map parMap);
}
