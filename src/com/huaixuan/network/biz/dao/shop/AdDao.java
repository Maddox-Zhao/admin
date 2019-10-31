package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Ad;

public interface AdDao {
	 /* @interface model: */
	 Long addAd(Ad ad) throws Exception;

	 /* @interface model: */
	 void editAd(Ad ad) throws Exception;

	 /* @interface model: */
	 void removeAd(Ad ad) throws Exception;

	 /* @interface model: */
	 Ad getAd(Long adId) throws Exception;

	 /* @interface model: */
	 List <Ad> getAds() throws Exception;

	 /* @interface model: */
	 void updateAd(Ad ad) throws Exception;

	/**
	 * @author chenhang 2011-3-3
	 * @description 广告页面总数
	 *
	 */
	public Integer getAdsCount(Ad ad) throws Exception;

	/**
	 *
	 * @author chenhang 2011-3-3
	 * @description
	 * @param currentPage
	 * @param pageSize
	 * @param totalCount
	 * @param ad
	 * @return
	 */
	public List<Ad> getAdsPage(Map<String, String> pramas)throws Exception;

	List<Ad> getAddInfo(Map parMap) throws Exception;

	Integer getAdMaxSort() throws Exception;

	void updateAdSortUpdtae(int maxid, int maxid2) throws Exception;

	void updateAdSortlow(int maxid, int maxid2) throws Exception;
}
