package com.huaixuan.network.biz.service.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;

public interface ProvideGoodsImgeService {
	/**
	 * @Description: 下载图片
	 * @date 2019-1-21
	 */
	public List<ProvideGoodsImge> getProviderImg(Map parMap);

	/**
	 * @param string
	 * @return
	 */
	public List<ProvideGoodsImge> selectOneGoodsImage(String string);

	/**
	 * @param pgi
	 */
	public void updateGoodsImageStatus(ProvideGoodsImge pgi);
}
