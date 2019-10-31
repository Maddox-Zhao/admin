package com.huaixuan.network.biz.service.shop;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.AdDetail;
import com.huaixuan.network.biz.query.QueryPage;

public interface AdService {
	/* @interface model: ����ad */
	public Long addAd(Ad ad, AdDetail adDetail, List<MultipartFile> files);

	/* @interface model:�༭Ad */
	public void editAd(Ad ad);

	/* @interface model:�߼�ɾ��Ad */
	public void removeAd(Long adId);

	/* @interface model:��Ad */
	public void openAd(Long adId);

	/* @interface model:�ر�Ad */
	public void colseAd(Long adId);

	/* @interface model: ����id��ȡAd */
	public Ad getAd(Long adId);

	/* @interface model: ��ȡAds */
	public List<Ad> getAds();

	/* @interface model: �޸�ad */
	public void updateAd(Ad ad, AdDetail adDetail, List<MultipartFile> files);

	/* @interface model: ��ȡ����ҳ�б� */
	// public PageUtil<Ad> getAdsPage(int currentPage, int pageSize,Ad ad);

	Map<String, Object> getAdInfo(Map parMap);

	/**
	 *
	 * @author chenhang 2011-3-3
	 * @description ���ҳ����ʾ
	 * @param ad
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public QueryPage getByMapByConditionWithPage(Ad ad, int currPage,
			int pageSize) throws Exception;
}
