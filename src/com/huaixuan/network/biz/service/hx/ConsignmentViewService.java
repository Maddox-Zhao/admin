package com.huaixuan.network.biz.service.hx;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.ConsignmentView;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.query.QueryPage;

public interface ConsignmentViewService
{
	public QueryPage getConsignmentViewByConditionWithPage(ConsignmentView consignmentview,int currPage,int pageSize);
	
	public Integer addConsignmentView(ConsignmentView consignmentView,Customer customer,AdminAgent aminAgent);
	
	public void updateConsignmentView(ConsignmentView consignmentView);
	
	public ConsignmentView getOneConsignmentView(String id);
	
	public void deleteOneConsignmentView(String id);
	
	public Integer selectAmount(String id);
	
	public Integer seletePaidAmount(String id);
	
	public boolean conCodeIsEixsts(String conCode);
	
	public void updateAmount(Map parMap);
	
	public void updatePaidAmount(Map parMap);
	
	public String uploadImage(MultipartFile mutilPartFile,String type,String id);

}
