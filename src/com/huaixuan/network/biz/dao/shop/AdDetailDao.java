package com.huaixuan.network.biz.dao.shop;

 import java.util.List;

import com.huaixuan.network.biz.domain.shop.AdDetail;

 public interface AdDetailDao {
 	/* @interface model:  */
 	Long addAdDetail(AdDetail adDetail) throws Exception;

 	/* @interface model:  */
 	void editAdDetail(AdDetail adDetail) throws Exception;

 	/* @interface model:  */
 	void removeAdDetail(Long adDetailId) throws Exception;

 	/* @interface model:  */
 	AdDetail getAdDetail(Long adDetailId) throws Exception;

 	/* @interface model: */
 	List <AdDetail> getAdDetails() throws Exception;

 	void updateAdDetail(AdDetail adDetail) throws Exception;

 	public AdDetail getAdDetailByAdId(Long adId) throws Exception;
 }
