package com.huaixuan.network.biz.service.platformstock;

import java.util.Map;

import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface HxStockUpdateService {
//  查询当前库存
	
	public MiniUiGrid searchStockUpdateHx(Map<String,String> searchmap);
}
