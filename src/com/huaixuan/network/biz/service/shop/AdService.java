package com.huaixuan.network.biz.service.shop;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.AdDetail;
import com.huaixuan.network.biz.query.QueryPage;

public interface AdService {
	/* @interface model: 新增ad */
	public Long addAd(Ad ad, AdDetail adDetail, List<MultipartFile> files);

	/* @interface model:编辑Ad */
	public void editAd(Ad ad);

	/* @interface model:逻辑删除Ad */
	public void removeAd(Long adId);

	/* @interface model:打开Ad */
	public void openAd(Long adId);

	/* @interface model:关闭Ad */
	public void colseAd(Long adId);

	/* @interface model: 根据id获取Ad */
	public Ad getAd(Long adId);

	/* @interface model: 获取Ads */
	public List<Ad> getAds();

	/* @interface model: 修改ad */
	public void updateAd(Ad ad, AdDetail adDetail, List<MultipartFile> files);

	/* @interface model: 获取广告分页列表 */
	// public PageUtil<Ad> getAdsPage(int currentPage, int pageSize,Ad ad);

	Map<String, Object> getAdInfo(Map parMap);

	/**
	 *
	 * @author chenhang 2011-3-3
	 * @description 广告页面显示
	 * @param ad
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public QueryPage getByMapByConditionWithPage(Ad ad, int currPage,
			int pageSize) throws Exception;
}
