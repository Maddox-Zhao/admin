package com.huaixuan.network.biz.dao.trade;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.query.QueryPage;

public interface RefundDao {
	Long addRefund(Refund refund);

	void editRefund(Refund refund);

	void removeRefund(Long refundId);

	Refund getRefund(Long refundId);

	/**
	 * 获得所有退换货信息
	 * 
	 * @return
	 */
	List<Refund> getRefunds();

	void updateRefundStatusAndNote(Refund refund);

	List<Refund> getRefundByParameterMap(Map parameterMap);

	QueryPage getRefundByParameterMap(Map<String, Object> parameterMap, int currPage, int pageSize);

	Refund getRefundByOrder(String tid);

	Refund getRefundByRefundId(String refundId);

	/**
	 * 根据RefundId更新退货表状态
	 * 
	 * @param map
	 *            Map
	 * @author chenyan 2009/08/05
	 */
	void updateRefundStatusByRefId(Map map);

	int getRefundCountByStatus(Map parMap);

	// List<Refund> getRefundByStatus(Map parameterMap, Page page);

	Integer getRefundListByStautsesCount(Map<String, Object> pramas);

	/**
	 * 判断交易状态为完成，已经产生积分，退货状态为完成，没有减去积分的退货记录
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	List<Refund> getRefundByTradeStatus(Map<String, Object> paramMap);

	void updateMessageForRefundByTradeId(String refundId, String message);
}
