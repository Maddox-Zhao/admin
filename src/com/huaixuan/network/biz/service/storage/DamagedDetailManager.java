package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;
import com.huaixuan.network.biz.query.QueryPage;

public interface DamagedDetailManager {

	/**
	 * 增加报残单明细记录
	 *
	 * @param damagedDetail
	 * @return long
	 */
	public long addDamagedDetail(DamagedDetail damagedDetail);

	public void editDamagedDetail(DamagedDetail damagedDetail);

	/*
	 * 删除报残明细记录
	 *
	 * @param damagedDetailId
	 */

	public void removeDamagedDetail(Long damagedDetailId);

	/*
	 * 根据id获取商品信息
	 *
	 * @param damagedDetailId
	 *
	 * @return DamagedDetailView
	 */

	// public DamagedDetailView getDamagedDetail(Long damagedDetailId);

	/**
	 * 根据id获取报残单明细记录
	 *
	 * @param damagedDetailId
	 *
	 * @return DamagedDetail
	 */

	public DamagedDetail getDamagedDetailById(Long damagedDetailId);

	/**
	 * 根据查询条件得到报残商品记录集合
	 *
	 * @param parameterMap
	 * @param page
	 * @return QueryPage
	 */

	public QueryPage getDamagedDetailsByParameterMap(
			Map parameterMap, int currPage, int pageSize);

	/**
	 * 根据查询条件获得LIST类型的报残商品记录集合
	 *
	 * @author chenhang 2011-3-28
	 * @description
	 * @param par
	 * @return
	 */
	public List<DamagedDetailView> getDamagedDetails(Map par);

	/**
	 * 根据条件得到分组后的记录集合
	 *
	 * @param parameterMap
	 * @return
	 */
	public List<DamagedDetail> getGroupCountListByMap(Map parameterMap);

	// /**
	// * 根据查询条件得到报残商品记录数量
	// * @param parMap
	// * @return int
	// */
	// public int getDamagedDetailsCountByParameterMap(Map<String, String>
	// parMap);

	/**
	 * 同一个报残单是否有相同的报残单商品distinct
	 *
	 * @param parMap
	 *
	 * @return int
	 */
	public int getCountByParameterMap(Map<String, String> parMap);

	/**
	 * 完成报残（生成出库单、修改库存、修改产品实例表和商品表中的库存
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public Boolean finishedDamaged(Map parMap) throws Exception;
}
