/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProviderYShangPage;

/**
 * @author TT
 * 
 */
public interface ProvideGoodsYShangUpdateDao {

	
	public List<ProvideGoodsYShang> selectYShangEntityByMap(Map<String,String> map);
	
	public void insertProvideGoodsYShang(List<ProvideGoodsYShang> pgysList);
	
	public Integer updateGoodsYShangMap(Map<String, String> maps);
	public List<ProvideGoodsYShang> getProviderListByPage(Map parMap);

	/**
	 * @param updateMap
	 */
	public void updateGoodsYShangByList(Map updateMap);
	
	public ProviderYShangPage selectYShangPage();
	public void updateYShangPage(Map<String, Integer> map);
}
