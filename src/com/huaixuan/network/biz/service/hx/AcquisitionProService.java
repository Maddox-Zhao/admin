package com.huaixuan.network.biz.service.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.AcquisitionPro;

public interface AcquisitionProService
{
	public void addAcquisitionPro(AcquisitionPro acquisitionPro,AdminAgent adminAgent);
	
	public List<AcquisitionPro> getAcquisitionByAcqCode(String acqCode);
	
	public void deleteAcquisitionProById(Long id);
	
	public void updateAcquisitionProByNotNull(AcquisitionPro acquisitionPro);
	
	public AcquisitionPro getOneAcquisitionById(Long id);
	
	public void draw(Long proId,Long acqId,AdminAgent adminAgent,Double money);

}
