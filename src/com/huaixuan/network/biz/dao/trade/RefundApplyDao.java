package com.huaixuan.network.biz.dao.trade;

import com.huaixuan.network.biz.domain.trade.RefundApply;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.RefundApplyQuery;

/**
 * RefundApplyDao(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RefundApplyDao {
	Long addRefundApply(RefundApply refundApply);

	void editRefundApply(RefundApply refundApply);

	void removeRefundApply(Long refundApplyId);

	RefundApply getRefundApply(Long refundApplyId);

	QueryPage getRefundApplys(RefundApplyQuery query, int currPage, int pageSize);

	int getRefundApplysCountParameterMap(RefundApplyQuery query);

	void updateRefundApply(RefundApply refundApply);
}
