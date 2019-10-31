package com.huaixuan.network.biz.dao.trade.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.RefundApplyDao;
import com.huaixuan.network.biz.domain.trade.RefundApply;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.RefundApplyQuery;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
@Service("refundApplyDao")
public class RefundApplyDaoiBatis implements RefundApplyDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public Long addRefundApply(RefundApply refundApply) {
		return (Long) sqlMapClientTemplate.insert("addRefundApply", refundApply);
	}

	public void editRefundApply(RefundApply refundApply) {
		sqlMapClientTemplate.update("editRefundApply", refundApply);
	}

	public void removeRefundApply(Long refundApplyId) {
		sqlMapClientTemplate.delete("removeRefundApply", refundApplyId);
	}

	public RefundApply getRefundApply(Long refundApplyId) {
		return (RefundApply) sqlMapClientTemplate.queryForObject("getRefundApply", refundApplyId);
	}

	public QueryPage getRefundApplys(RefundApplyQuery query, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(query);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getRefundApplysCountParameterMap", query);
		queryPage.setTotalItem(count);

		if (count > 0) {
			query.setStartRow(queryPage.getPageFristItem());
			query.setEndRow(queryPage.getPageLastItem());

			/* 分页查询操作员记录 */
			queryPage.setItems(sqlMapClientTemplate.queryForList("getRefundApplys", query));
		}

		return queryPage;
	}

	public int getRefundApplysCountParameterMap(RefundApplyQuery query) {
		return (Integer) sqlMapClientTemplate.queryForObject("getRefundApplysCountParameterMap", query);
	}

	public void updateRefundApply(RefundApply refundApply) {
		sqlMapClientTemplate.update("updateRefundApplyStatus", refundApply);
	}
}
