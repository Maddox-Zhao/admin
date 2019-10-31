package com.huaixuan.network.biz.service.trade;

import java.io.File;

import com.huaixuan.network.biz.domain.trade.RefundApply;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.RefundApplyQuery;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface RefundApplyManager {
	public void addRefundApply(RefundApply refundApply);

	public void editRefundApply(RefundApply refundApply);

	public void removeRefundApply(Long refundApplyId);

	public RefundApply getRefundApply(Long refundApplyId);

	public QueryPage getRefundApplys(RefundApplyQuery query, int currPage, int pageSize);

	public int getRefundApplysCountParameterMap(RefundApplyQuery query);

	public boolean addRefundApply(RefundApply refundApply, File[] pics, String[] imagesFileName);

	public void updateRefundApply(RefundApply refundApply);

}
