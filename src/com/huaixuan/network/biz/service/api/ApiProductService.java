package com.huaixuan.network.biz.service.api;

import java.util.Map;

import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2015-12-4 上午11:31:59
 * 产品查询
 **/

public interface ApiProductService
{
	/**
	 * 分页查询所有可售产品
	 * @param searchMap
	 * @return
	 */
	public  MiniUiGrid getProductListByPages(Map<String,String> searchMap);
	
	
	
}
 
