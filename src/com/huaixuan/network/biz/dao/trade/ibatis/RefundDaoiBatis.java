package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.query.QueryPage;

@Service("refundDao")
public class RefundDaoiBatis implements RefundDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public Long addRefund(Refund refund) {
		return (Long) sqlMapClientTemplate.insert("addRefund", refund);
	}

	public void editRefund(Refund refund) {
		sqlMapClientTemplate.update("editRefund", refund);
	}

	public void removeRefund(Long refundId) {
		sqlMapClientTemplate.delete("removeRefund", refundId);
	}

	public Refund getRefund(Long refundId) {
		return (Refund) sqlMapClientTemplate.queryForObject("getRefund", refundId);
	}

	@SuppressWarnings("unchecked")
	public List<Refund> getRefunds() {
		return sqlMapClientTemplate.queryForList("getRefunds");
	}

	public void updateRefundStatusAndNote(Refund refund) {
		sqlMapClientTemplate.update("updateRefundStatusAndNote", refund);
	}

	@SuppressWarnings("unchecked")
	public List<Refund> getRefundByParameterMap(Map parameterMap) {
		return sqlMapClientTemplate.queryForList("getRefundByParameterMap", parameterMap);
	}

	public QueryPage getRefundByParameterMap(Map<String, Object> parameterMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parameterMap);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getRefundCountByParameterMap", parameterMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parameterMap.put("startRow", queryPage.getPageFristItem());
			parameterMap.put("endRow", queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			queryPage.setItems(sqlMapClientTemplate.queryForList("getRefundByParameterMap", parameterMap));
		}

		return queryPage;
	}

	public Refund getRefundByOrder(String tid) {
		return (Refund) sqlMapClientTemplate.queryForObject("getRefundByOrder", tid);
	}

	public Refund getRefundByRefundId(String refundId) {
		return (Refund) sqlMapClientTemplate.queryForObject("getRefundByRefundId", refundId);
	}

	public void updateRefundStatusByRefId(Map map) {
		sqlMapClientTemplate.update("updateRefundStatusByRefId", map);
	}

	// public List<Refund> getRefundByStatus(Map parameterMap, Page page) {
	// return this.findQueryPage("getRefundByStatus", parameterMap, page);
	// }

	public List<Refund> getRefundByTradeStatus(Map<String, Object> paramMap) {
		return sqlMapClientTemplate.queryForList("getRefundByTradeStatus", paramMap);
	}

	public int getRefundCountByStatus(Map parMap) {
		return (Integer) sqlMapClientTemplate.queryForObject("getRefundCountByStatus", parMap);
	}

	public Integer getRefundListByStautsesCount(Map<String, Object> pramas) {
		return (Integer) sqlMapClientTemplate.queryForObject("getRefundListByStautsesCount", pramas);
	}

	public void updateMessageForRefundByTradeId(String refundId, String message) {
		Map map = new HashMap();
		map.put("refundId", refundId);
		map.put("message", message);
		sqlMapClientTemplate.update("updateMessageForRefundByTradeId", map);
	}

}
