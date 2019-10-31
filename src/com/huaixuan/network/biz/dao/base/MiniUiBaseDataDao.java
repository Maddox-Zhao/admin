package com.huaixuan.network.biz.dao.base;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.base.MiniUiBase;



/**
 * @author Mr_Yang   2015-11-23 04:18:52
 * 
 **/

public interface MiniUiBaseDataDao
{
	/**
	 * ͨ
	 * @param type
	 * @return
	 */
	public  List<MiniUiBase>querySiteByType(String type);
	
	public  List<MiniUiBase>getSiteWhereMenDian(String type);
	
	public  List<MiniUiBase> getSiteByName(String name);
	
	public  List<MiniUiBase>querySellChannel();
	
	public  List<MiniUiBase>queryAllCurrency();
	
	
	public  List<MiniUiBase> queryAllPayMent();
	
	public  List<MiniUiBase> queryProvince();
	

	
	/**
	 * 根据账号查询该账号的销售渠道
	 * @param accountId
	 * @return
	 */
	public List<MiniUiBase> querySellChannelByAccount(String accountId);
	
	/**
	 * 根据账号查询该账号的站点
	 * @param accountId
	 * @return
	 */
	public List<MiniUiBase> querySiteByAccount(String accountId);
	
	
	/**
	 * map  id name
	 * @param map
	 * @return
	 */
	public List<MiniUiBase> querySupplier(Map<String,String> map);
	
 

}
 
