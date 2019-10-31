package com.huaixuan.network.biz.service.platformstock.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderRecordDao;


import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.platformstock.PlatFormOrderRecordService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.hundsun.common.lang.StringUtil;
@Service("PlatFormOrderRecordService")
public class PlatFormOrderRecordServiceImpl implements PlatFormOrderRecordService {

	@Autowired      
    private  PlatFormOrderRecordDao platFormOrderRecordDao;
	@Override
	public MiniUiGrid searchAllOrderId(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = platFormOrderRecordDao.searchOrderCount(searchMap);
		MiniUiGrid grid = new MiniUiGrid();
		grid.setTotal(count);
		if(count>0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理.
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow")))
			{
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<PlatFormOrderRecord> list = platFormOrderRecordDao.selectAllOrderidOne(searchMap);
			if(list!=null && list.size()>0){
				grid.setData(list);
			}
		}
	
		return grid;
	}
	
	//导入xls
	@Override
	public void update(Map<String, String> keyMap) {
		Set<Entry<String,String>> keySet = keyMap.entrySet();
		Iterator<Entry<String, String>> it = keySet.iterator();
		while(it.hasNext()){
			Entry<String,String> entry = it.next();
			String orderId = entry.getKey();
			String ems = entry.getValue();
			if(StringUtil.isBlank(orderId) || StringUtil.isBlank(orderId)) continue;
			String[] es =ems.split("_");
			String   emsCode = es[0];  //快递单号
			String   emsCompany=es[1]; //快递公司
			
			PlatFormOrderRecord orderExcel = new PlatFormOrderRecord();
			orderExcel.setOrderId(orderId);
			orderExcel.setEmsCode(emsCode);
			orderExcel.setEmsCompany(emsCompany);
			
			platFormOrderRecordDao.updateOrderExcel(orderExcel);
		}
		
		
	}

	

    
	@Override
	public PlatFormOrderRecord getems(String orderId) {
		PlatFormOrderRecord platFormDetail = new PlatFormOrderRecord();
		platFormDetail.setOrderId(orderId);
		return platFormOrderRecordDao.selectgetems(platFormDetail);
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.platformstock.PlatFormOrderRecordService#searchWaybillByOrder(java.util.Map)
	 * 用于显示面单页面的数据
	 */
	@Override
	public QueryPage searchWaybillByOrder(PlatFormOrderRecord platFormOrderRecord, int currPage, int pageSize) {
		
		QueryPage queryPage = new QueryPage(platFormOrderRecord);
		Map pramas = queryPage.getParameters();
		
		int count = platFormOrderRecordDao.searchOrderCountByParams(pramas,platFormOrderRecord);
		if(count>0){
			 queryPage.setCurrentPage(currPage);
		
			 queryPage.setPageSize(pageSize);
			 queryPage.setTotalItem(count);
			 pramas.put("startRow", queryPage.getPageFristItem());
	         pramas.put("endRow", queryPage.getPageLastItem());
//			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow")))
//			{
//			searchMap.put("startRow", queryPage.getPageFristItem()+"");
//			searchMap.put("endRow", queryPage.getPageSize()+"");
//			}
			 
			List<PlatFormOrderRecord> list = platFormOrderRecordDao.selectAllOrderidByOrder(pramas,platFormOrderRecord);
			if(list!=null && list.size()>0){
				  queryPage.setItems(list);
			}
		}
	
		return queryPage;
	}
}
