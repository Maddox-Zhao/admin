package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;


import com.huaixuan.network.biz.domain.hx.ConsignmentPro;


public interface ConsignmentProDao
{
	public void addConsignmentPro(ConsignmentPro consignmentPro);
	
	public List<ConsignmentPro> getConsignmentById(String conId);
	
	public void deleteConsignmentPro(String id);
	
	public void updateConsignmentPro(ConsignmentPro consignmentPro);
	
	public ConsignmentPro getOntConsignmentPro(String id);
	
	public void updateConsignmentProPic(Map parMap);
	
	public Integer selectConsignmentProAmount(String id);
	
	public void updatePaidAmount(Map parMap);
	
	public Integer selectConsignmentProPaidAmount(String id);
	
	public void updateProStatusById(Map parMap);

}
