package com.huaixuan.network.biz.service.base;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.MiniUiBase;



/**
 * @author Mr_Yang   2015-11-23 ����04:57:48
 * �����
 **/

public interface MiniuiBaseService
{
	public  List<MiniUiBase> getSiteByType(String type);
	
	public  List<MiniUiBase> getSiteByName(String name);
	
	
	public  List<MiniUiBase> getSiteWhereMenDian(String type);
	
	/**
	 *  
	 * @return
	 */
	public List<MiniUiBase> getAllSellChannel();

	/**
	 * 
	 * @return
	 * 获取所有币种
	 */
	public List<MiniUiBase> getAllCurrency();
	
	
	/**
	 * 
	 * @return
	 * 获取所有付款方式
	 */
	public List<MiniUiBase> getAllPayment();
	
	
	/**
	 *
	 *@return
	 *获取所有省份
	 */
	public List<MiniUiBase> getProvince();
	
	/**
	 * 
	 * @return
	 * 获取销售渠道 根据当前登录用户
	 */
	public List<MiniUiBase> getSellChannelByAccount(AdminAgent adminAgent);
	
	
	
	/**
	 * 
	 * @return
	 * 获取站点 根据当前登录用户
	 */
	public List<MiniUiBase> getSiteByAccount(AdminAgent adminAgent);
	
	
	/**
	 * 查询供货商
	 * @param map
	 * @return
	 */
	public List<MiniUiBase> querySupplier(Map<String,String> map);
}
 
