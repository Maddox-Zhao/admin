package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.AcquisitionPro;


public interface AcquisitionProDao
{
	public void addAcquisitionPro(AcquisitionPro acquisitionPro);
	
	public List<AcquisitionPro> getAcquisitionByAcqCode(String acqCode);
	
	public void deleteAcquisitionProById(Long id);
	
	public void updateAcquisitionProByNotNull(AcquisitionPro acquisitionPro);
	
	public AcquisitionPro getOneAcquisitionById(Long id);
	
	public void updateAcquisitionProPic(Map parMap);

}
