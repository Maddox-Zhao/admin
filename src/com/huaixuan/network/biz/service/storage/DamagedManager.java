package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Damaged;
import com.huaixuan.network.biz.domain.storage.query.DamagedQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface DamagedManager {

	/**
	 * 增加报残单主表记录
	 *
	 * @param damaged
	 * @return long
	 */
	public long addDamaged(Damaged damaged);

	/**
	 * 修改报残单主表记录
	 *
	 * @param damaged
	 */
	public void editDamaged(Damaged damaged);

	/**
	 * 删除报残单主表记录
	 *
	 * @param damagedId
	 */
	public void removeDamaged(Long damagedId);

	/**
	 * 根据parMap获取报残单主表记录
	 *
	 * @param parMap
	 * @return Damaged
	 */
	public Damaged getDamaged(Map parMap);

	/**
	 * 获取所有的报残单主表记录
	 *
	 * @return
	 */
	public List<Damaged> getDamageds();

	/**
	 * 根据查询条件得到报残单记录集合
	 */
	public QueryPage getDamagedListsByParameterMap(DamagedQuery damagedQuery,
			int currPage, int pageSize);

	/**
	 * 根据查询条件得到报残单记录数量
	 */
	public int getDamagedCountByParameterMap(Map<String, String> parMap);
}
