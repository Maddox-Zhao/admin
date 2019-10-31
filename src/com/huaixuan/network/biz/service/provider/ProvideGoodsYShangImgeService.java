package com.huaixuan.network.biz.service.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShangImge;

public interface ProvideGoodsYShangImgeService {
	/**
	 * @Description: 下载图片
	 * @date 2019-1-21
	 */
	public List<ProvideGoodsYShangImge> getProviderImgYShang(Map parMap);

	/**
	 * @param string
	 * @return
	 */
	public List<ProvideGoodsYShangImge> selectOneGoodsImageYShang(String string);

	/**
	 * @param pgi
	 */
	public void updateGoodsImageStatusYShang(ProvideGoodsYShangImge pgi);
}
