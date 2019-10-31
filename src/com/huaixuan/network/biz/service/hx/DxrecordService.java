package com.huaixuan.network.biz.service.hx;

import java.util.Map;

import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface DxrecordService {

	public QueryPage queryDxrecordList (Map<String,String> searchMap);
	
	public QueryPage statisticsDxrecordList (Map<String,String> searchMap);
}
