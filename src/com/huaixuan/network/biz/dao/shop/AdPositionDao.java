package com.huaixuan.network.biz.dao.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.AdPosition;

public interface AdPositionDao {
	Long addAdPosition(AdPosition adPosition) throws Exception;

	void editAdPosition(AdPosition adPosition) throws Exception;

	void removeAdPosition(Long adPositionId) throws Exception;

	AdPosition getAdPosition(Long adPositionId) throws Exception;

	/**
	 * @author chenhang 2011-3-4
	 * @description
	 */
	List<AdPosition> getAdPositions() throws Exception;

	public Integer getAdPositionsCount() throws Exception;

	// PageUtil<AdPosition> getAdPositionsPage(Map<String,Object> conditions,
	// int currentPage, int pageSize, int totalCount);
}
