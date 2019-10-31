package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface DamagedDetailDao {
	/* @interface model: DamagedDetail */
	long addDamagedDetail(DamagedDetail damagedDetail) throws Exception;

	/* @interface model: DamagedDetail */
	void editDamagedDetail(DamagedDetail damagedDetail) throws Exception;

	/* @interface model: DamagedDetail */
	void removeDamagedDetail(Long damagedDetailId) throws Exception;

	/* @interface model: DamagedDetail,DamagedDetail */
	DamagedDetailView getDamagedDetail(Long damagedDetailId) throws Exception;

	/*
	 * 根据ID得到对象
	 */
	public DamagedDetail getDamagedDetailById(Long damagedDetailId)
			throws Exception;

	/*
	 * 根据查询条件得到报残商品记录集合
	 */
	public List<DamagedDetailView> getDamagedDetailsByParameterMap(
			Map parameterMap) throws Exception;

	/*
	 * 根据条件得到分组后的记录集合
	 */
	List<DamagedDetail> getGroupCountListByMap(Map parameterMap)
			throws Exception;

	/*
	 * 根据查询条件得到报残商品记录数量
	 */
	public int getDamagedDetailsCountByParameterMap(Map<String, String> parMap)
			throws Exception;

	/*
	 * 根据查询条件得到报残产品记录数
	 */
	int getCountByParameterMap(Map<String, String> parMap) throws Exception;
}
