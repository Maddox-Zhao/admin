package com.huaixuan.network.biz.service.hx;



import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Acquisition;
import com.huaixuan.network.biz.domain.hx.Customer;
import com.huaixuan.network.biz.query.QueryPage;

public interface AcquisitionService
{
	public  void addAcquisitionView(Acquisition acquisition,Customer customer,AdminAgent adminAgent);
	
	public QueryPage getAcquisitionViewByConditionWithPage(Acquisition acquisition,int currPage,int pageSize);
	
	public void deleteAcquisitionViewById(Long id);
	
	public void deleteAcquisitionView(Acquisition acquisition);
	
	public Acquisition getAcquisitionById(Long id);
	
	public Acquisition selectAcquisitionViewByAcpCode(String acpCode);
	
	public boolean theAcpCodeIsExist(String acpCode);
	
	public void updateAcquisitionViewByNotNull(Acquisition acquisition);
	
	public void updateAcquisitionViewByPrimaryKey(Acquisition acquisition);
	
	
	
}	
