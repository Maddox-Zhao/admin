package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.AdPosition;

public interface AdPositionService{
//	public Long addAdPosition(AdPosition adPosition) throws Exception;

//	public void editAdPosition(AdPosition adPosition) throws Exception;

//	public void removeAdPosition(Long adPositionId) throws Exception;

//	public AdPosition getAdPosition(Long adPositionId) throws Exception;
	/**
	 * @author chenhang 2011-3-4
	 * @description 获得广告位
	 */
	public List<AdPosition> getAdPositions() throws Exception;
}
