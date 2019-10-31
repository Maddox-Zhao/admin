package com.huaixuan.network.biz.dao.hx;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hx.Dxrecord;

public interface DxrecordDao {

	Long addDxrecord(Dxrecord dx);
	
	public int getDxrecordLsitCount(Map<String, String> searchMap);
	
	public int getstatisticsDxrecordListCount(Map<String, String> searchMap);
	
	public List<Dxrecord> queryDxrecordList(Map<String,String> searchMap);
	
	public List<Dxrecord> statisticsDxrecordList(Map<String,String> searchMap);
}
