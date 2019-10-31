package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.Acquisition;

public interface AcquisitionDao
{
	public Integer getConsignmentViewByConditionWithPageCount(Map parMap);

	public List<Acquisition> getAcquisitionViewByConditionWithPage(Map parMap);

	public Acquisition getAcquisitionById(Long id);

	public Long addAcquisitionView(Acquisition acquisition);

	public void updateAcquisitionViewByPrimaryKey(Acquisition acquisition);

	public void updateAcquisitionViewByNotNull(Acquisition acquisition);

	public void uploadIdcardImage(Map parMap);

	public void uploadAcqImage(Map parMap);

	public void deleteAcquisitionViewById(Long id);

	public void delteAcquisitionView(Acquisition acquisition);

	public Acquisition selectAcquisitionViewByAcpCode(String acpCode);

	public Integer theAcpCodeIsEixst(String acpCode);

}
