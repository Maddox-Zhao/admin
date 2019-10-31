package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.DaiGou;

public interface DaiGouDao
{
	public Integer getDaiGouViewByConditionWithPageCount(Map parMap);

	public List<DaiGou> getDaiGouViewByConditionWithPage(Map parMap);

	public DaiGou getDaiGouById(Long id);
	
	public DaiGou getDaiGouByDaiGouCode(String daigouCode);

	public Long addAcquisition(DaiGou daigou);

	public void updateDaiGouByNotNull(DaiGou daigou);
	
	public void deleteDaiGouById(Long id);
	
	public Integer daiGouCodeIsEixst(String daigouCode);

}
