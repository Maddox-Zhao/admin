package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;
import com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService;

@Service
public class ProvideGoodsImgeServiceImpl implements ProvideGoodsImgeService{
	
	@Autowired
	private ProvideGoodsImgeDao  provideGoodsImgeDao;

	@Override
	public List<ProvideGoodsImge> getProviderImg(Map parMap) {
		List<ProvideGoodsImge> provideImgDao = provideGoodsImgeDao.getProvideImgDao(parMap);
		return provideImgDao;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService#selectOneGoodsImage(java.lang.String)
	 */
	@Override
	public List<ProvideGoodsImge> selectOneGoodsImage(String prodid) {
		List<ProvideGoodsImge> provideoneGoodsImg = provideGoodsImgeDao.getProvideOneGoodsImage(prodid);
		return provideoneGoodsImg;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService#updateGoodsImageStatus(com.huaixuan.network.biz.domain.provider.ProvideGoodsImge)
	 */
	@Override
	public void updateGoodsImageStatus(ProvideGoodsImge pgi) {
		provideGoodsImgeDao.updatGoodsImage(pgi);
		
	}
	
	
	
}
