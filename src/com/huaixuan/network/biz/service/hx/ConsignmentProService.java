package com.huaixuan.network.biz.service.hx;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.ConsignmentPro;

public interface ConsignmentProService
{
	public List<ConsignmentPro> getConsignmentPro(String gmId);
	
	public void deleteConsignmentPro(String id,String conId);
	
	public void addConsignmentPro(ConsignmentPro consignmentPro);
	
	public void updateConsignmentPro(ConsignmentPro consignmentPro);
	
	public ConsignmentPro getOneConsignmentPro(String id);
	
	//public String updateConsignmentProPic(MultipartFile img,String id);
	
	public Integer selectPaidAmount(String id);
	
	public void updatePaidAmount(String id,Integer paidMoney,String conId,AdminAgent adminAgent);
	
	
	public void updateProStatusById(Long id,String type);

}
