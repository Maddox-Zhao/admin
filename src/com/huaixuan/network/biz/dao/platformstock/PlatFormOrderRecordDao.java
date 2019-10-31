package com.huaixuan.network.biz.dao.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;


public interface PlatFormOrderRecordDao {
	/*
	 * 从hx_stock_update_platform_order表中查找全部的orderid
	 * 1.查总条数为分页做准备
	 * 2.根据站点查找对应的orderid
	 */
	//查询当前总数
	public int searchOrderCount(Map<String,String> searchMap);
	//查全部orderid
	public List<PlatFormOrderDetails> selectAllOrderid(Map<String,String> searchMap);
	//查全部orderid
	public List<PlatFormOrderRecord> selectAllOrderidOne(Map<String,String> searchMap);
	
	public void updateOrderExcel(PlatFormOrderRecord orderExcel);
	

     //查詢快递单号、快递公司
	public PlatFormOrderRecord selectgetems(PlatFormOrderRecord idorder);
	/**
	 * @param pramas
	 * @param platFormOrderRecord
	 * @return
	 */
	public Integer searchOrderCountByParams(Map pramas,
			PlatFormOrderRecord platFormOrderRecord);
	
	/**
	 * @param pramas
	 * @param platFormOrderRecord
	 * @return
	 */
	public List<PlatFormOrderRecord> selectAllOrderidByOrder(Map pramas,
			PlatFormOrderRecord platFormOrderRecord);
	/**
	 * @param pf
	 */
	public void updateOrderToBill(PlatFormOrderRecord pf);
	
}
