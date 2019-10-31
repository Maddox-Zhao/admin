package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DamagedDetailDao;
import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;

/**
 * ÔøΩÔøΩÔøΩÔøΩÔøΩ‘∂ÔøΩÔøΩÔøΩÔø(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Service("damagedDetailDao")
public class DamagedDetailDaoiBatis implements DamagedDetailDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public long addDamagedDetail(DamagedDetail damagedDetail) throws Exception {
		return (Long) this.sqlMapClient.insert("addDamagedDetail",
				damagedDetail);
	}

	@Override
	public void editDamagedDetail(DamagedDetail damagedDetail) throws Exception {
		this.sqlMapClient.update("editDamagedDetail", damagedDetail);
	}

	@Override
	public void removeDamagedDetail(Long damagedDetailId) throws Exception {
		this.sqlMapClient.delete("removeDamagedDetail", damagedDetailId);
	}

	@Override
	public DamagedDetailView getDamagedDetail(Long damagedDetailId)
			throws Exception {
		return (DamagedDetailView) this.sqlMapClient.queryForObject(
				"getDamagedDetail", damagedDetailId);
	}

	/*
	 * ÔºàÈùû JavadocÔº
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDetailDao#getDamagedDetailById(java.
	 * lang.Long)
	 */
	@Override
	public DamagedDetail getDamagedDetailById(Long damagedDetailId)
			throws Exception {
		return (DamagedDetail) this.sqlMapClient.queryForObject(
				"getDamagedDetailById", damagedDetailId);
	}

	/*
	 * ÔºàÈùû JavadocÔº
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDetailDao#getDamagedDetailsByParameterMap
	 * (java.util.Map, com.hundsun.bible.Page)
	 */
	@Override
	public List<DamagedDetailView> getDamagedDetailsByParameterMap(
			Map parameterMap) throws Exception {
		return this.sqlMapClient.queryForList(
				"getDamagedDetailsByParameterMap", parameterMap);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDetailDao#getGroupCountListByMap(java
	 * .util.Map)
	 */
	@Override
	public List<DamagedDetail> getGroupCountListByMap(Map parameterMap)
			throws Exception {
		return this.sqlMapClient.queryForList("getGroupCountListByMap",
				parameterMap);
	}

	/*
	 * ÔºàÈùû JavadocÔº
	 *
	 * @see com.hundsun.bible.dao.ios.DamagedDetailDao#
	 * getDamagedDetailsCountByParameterMap(java.util.Map)
	 */
	@Override
	public int getDamagedDetailsCountByParameterMap(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject(
				"getDamagedDetailsCountByParameterMap", parMap);
	}

	/*
	 * ÔºàÈùû JavadocÔº
	 *
	 * @see
	 * com.hundsun.bible.dao.ios.DamagedDetailDao#getCountByParameterMap(java
	 * .util.Map)
	 */
	@Override
	public int getCountByParameterMap(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject(
				"getCountByParameterMap", parMap);
	}

}
