package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao;
import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangImgeDao;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShangImge;
import com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService;
import com.huaixuan.network.biz.service.provider.ProvideGoodsYShangImgeService;

@Service
public class ProvideGoodsYShangImgeServiceImpl implements ProvideGoodsYShangImgeService{
	
	@Autowired
	private ProvideGoodsImgeDao  provideGoodsImgeDao;

	@Autowired
	private ProvideGoodsYShangImgeDao  provideGoodsYShangImgeDao;
	
	@Override
	public List<ProvideGoodsYShangImge> getProviderImgYShang(Map parMap) {
		List<ProvideGoodsYShangImge> provideImgDao = provideGoodsYShangImgeDao.getProvideYShangImgDao(parMap);
		return provideImgDao;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService#selectOneGoodsImage(java.lang.String)
	 */
	@Override
	public List<ProvideGoodsYShangImge> selectOneGoodsImageYShang(String prodid) {
		List<ProvideGoodsYShangImge> provideoneGoodsImg = provideGoodsYShangImgeDao.getProvideOneGoodsYShangImage(prodid);
		return provideoneGoodsImg;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProvideGoodsImgeService#updateGoodsImageStatus(com.huaixuan.network.biz.domain.provider.ProvideGoodsImge)
	 */
	@Override
	public void updateGoodsImageStatusYShang(ProvideGoodsYShangImge pgi) {
		provideGoodsYShangImgeDao.updatGoodsYShangImage(pgi);
		
	}
	
	
	
}
