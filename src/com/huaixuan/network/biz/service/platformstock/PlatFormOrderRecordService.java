package com.huaixuan.network.biz.service.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface PlatFormOrderRecordService {
	/*
	 * 查全部用于魅力惠发货通知页面显示和导出
	 */
	public MiniUiGrid searchAllOrderId(Map<String,String> searchMap);
	
	//插入订单号、快递单号、快递公司
	public void update(Map<String,String> map);
	
	//查詢订单号、快递单号、快递公司
	public PlatFormOrderRecord getems(String idorder);
	
	//用于魅力惠获取面单的
	public QueryPage searchWaybillByOrder(PlatFormOrderRecord platFormOrderRecord, int currPage, int pageSize);
}
