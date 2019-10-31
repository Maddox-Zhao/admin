package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.AdPositionDao;
import com.huaixuan.network.biz.domain.shop.AdPosition;
import com.huaixuan.network.biz.service.shop.AdPositionService;

@Service("adPositionService")
public class AdPositionServiceImpl implements AdPositionService {
	@Autowired
	public AdPositionDao adPositionDao;

	//
	// public void setAdPositionDao(AdPositionDao adPositionDao) {
	// this.adPositionDao = adPositionDao;
	// }
	//
	//
	/* @model: */
	// public Long addAdPosition(AdPosition adPositionDao) throws Exception{
	// log.info("AdPositionManagerImpl.addAdPosition method");
	// Long id =null;
	//
	// id = this.adPositionDao.addAdPosition(adPositionDao);
	//
	// return id;
	// }
	//
	/* @model: */
	// public void editAdPosition(AdPosition adPosition) throws Exception{
	// log.info("AdPositionManagerImpl.editAdPosition method");
	//
	// this.adPositionDao.editAdPosition(adPosition);
	//
	// }
	//
	/* @model: */
	// public void removeAdPosition(Long adPositionId) throws Exception{
	// log.info("AdPositionManagerImpl.removeAdPosition method");
	//
	// this.adPositionDao.removeAdPosition(adPositionId);
	//
	// }
	//
	/* @model: */
	// public AdPosition getAdPosition(Long adPositionId) throws Exception{
	// log.info("AdPositionManagerImpl.getAdPosition method");
	//
	// return this.adPositionDao.getAdPosition(adPositionId);
	//
	// }
	// @Override
	// public AdPosition getAdPosition(Long adPositionId) throws Exception {
	// // log.info("AdPositionManagerImpl.getAdPosition method");
	// return this.adPositionDao.getAdPosition(adPositionId);
	//
	// }

	@Override
	public List<AdPosition> getAdPositions() throws Exception {
		// log.info("AdPositionManagerImpl.getAdPositions method");
		return this.adPositionDao.getAdPositions();
	}
	//
	// public PageUtil<AdPosition> getAdPositionsPage(int currentPage, int
	// pageSize)
	// throws Exception {
	// Integer count = this.adPositionDao.getAdPositionsCount();
	// return this.adPositionDao.getAdPositionsPage(null, currentPage,
	// pageSize, count.intValue());
	//
	// }
}
