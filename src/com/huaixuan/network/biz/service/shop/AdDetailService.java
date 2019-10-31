package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.AdDetail;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface AdDetailService {
	/* @interface model: 新增adDetail */
	public Long addAdDetail(AdDetail adDetail) throws Exception;

	/* @interface model: 更新adDetail */
	public void editAdDetail(AdDetail adDetail) throws Exception;

	/* @interface model: 删除adDetail */
	public void removeAdDetail(Long adDetailId) throws Exception;

	/* @interface model: 根据ID获取adDetail */
	public AdDetail getAdDetail(Long adDetailId) throws Exception;

	/* @interface model: 获取�有adDetail */
	public List<AdDetail> getAdDetails() throws Exception;

	/* @interface model: 根据adid得到addetail */
	public AdDetail getAdDetailByAdId(Long adId) throws Exception;
}
