package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.AdDetailDao;
import com.huaixuan.network.biz.domain.shop.AdDetail;
import com.huaixuan.network.biz.service.shop.AdDetailService;

@Service("adDetailService")
public class AdDetailServiceImpl implements AdDetailService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public AdDetailDao adDetailDao;

	@Override
	public Long addAdDetail(AdDetail adDetailDao) throws Exception {
		log.info("AdDetailManagerImpl.addAdDetail method");
		Long id = null;

		id = this.adDetailDao.addAdDetail(adDetailDao);

		return id;
	}

	@Override
	public void editAdDetail(AdDetail adDetail) throws Exception {
		log.info("AdDetailManagerImpl.editAdDetail method");

		this.adDetailDao.editAdDetail(adDetail);

	}

	@Override
	public void removeAdDetail(Long adDetailId) throws Exception {
		log.info("AdDetailManagerImpl.removeAdDetail method");

		this.adDetailDao.removeAdDetail(adDetailId);

	}

	@Override
	public AdDetail getAdDetail(Long adDetailId) throws Exception {
		log.info("AdDetailManagerImpl.getAdDetail method");

		return this.adDetailDao.getAdDetail(adDetailId);

	}

	@Override
	public List<AdDetail> getAdDetails() throws Exception {
		log.info("AdDetailManagerImpl.getAdDetails method");

		return this.adDetailDao.getAdDetails();

	}

	@Override
	public AdDetail getAdDetailByAdId(Long adId) throws Exception {
		log.info("AdDetailManagerImpl.getAdDetail method");
		return this.adDetailDao.getAdDetailByAdId(adId);
	}
}
