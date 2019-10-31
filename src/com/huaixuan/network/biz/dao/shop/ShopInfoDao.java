package com.huaixuan.network.biz.dao.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.ShopInfo;

public interface ShopInfoDao {

	/* @interface model: ShopInfo */
	Long addShopInfo(ShopInfo shopInfo) throws Exception;

	/* @interface model: ShopInfo */
	void editShopInfo(ShopInfo shopInfo) throws Exception;

	/* @interface model: ShopInfo */
	void removeShopInfo(Long shopInfoId) throws Exception;

	/**
	 * @author chenhang 2011-3-2
	 * @description 获得店铺信息
	 */
	ShopInfo getShopInfo(Long shopInfoId) throws Exception;

	/* @interface model: ShopInfo,ShopInfo */
	List<ShopInfo> getShopInfos() throws Exception;

	/* 根据userid获取店铺信息 */
	public ShopInfo getShopInfoByUserId(long userId) throws Exception;
}
