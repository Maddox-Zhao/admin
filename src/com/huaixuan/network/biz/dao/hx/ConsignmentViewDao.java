package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.ConsignmentView;

public interface ConsignmentViewDao
{
	public Integer getConsignmentViewByConditionWithPageCount(Map parMap);
		
	public List<ConsignmentView> getConsignmentViewByConditionWithPage(Map parMap);
	
	public Integer addConsignmentView(ConsignmentView consignmentView);
	
	public void updateConsignmentView(ConsignmentView consignmentView);
	
	public void deleteConsignmentViewById(String id);

	public void delteConsignmentView(ConsignmentView consignmentView);
	
	public ConsignmentView getOneConsignment(String id);
	
	public Integer selectAmount(String id);
	
	public Integer selectPaidAmount(String id);
	
	public Integer selectOneConsignmentViewByConCode(String conCode);
	
	public void updateAmount(Map parMap);
	
	public void updatePaidAmount(Map parMap);
		
	public void uploadIdcardImage(Map parMap);
	
	public void uploadConImage(Map parMap);
}
