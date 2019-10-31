/** 
 * @Title: AvailableStockService.java 
 * @Package com.huaixuan.network.biz.service 
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ÏÂÎç07:08:23 
 * @version V1.0 
 */  
package com.huaixuan.network.biz.service.stock;

import java.util.Map;

import com.huaixuan.network.biz.domain.goods.AvailableStock;

/**
 * @ClassName: AvailableStockService
 * @Description: TODO
 * @author 
 *
 */
public interface AvailableStockService {
	public AvailableStock getAvailableStockByPramas(Map<String,Object> pramas);
	
	public void updateAvaiStoEsNumByPramas(Map<String,Object> pramas);
	
	public void updateAvaiStoEsNumZero();
	
	public void addhxAvaiStoByPramas(AvailableStock availableStock);
}
