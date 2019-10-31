package com.huaixuan.network.biz.service.shop;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.user.UserAddress;

public interface ShopInfoService {
	/* @interface model: 新增店铺信息 */
	public void addShopInfo(ShopInfo shopInfo, UserAddress userAddress,
			List<MultipartFile> files, List<MultipartFile> filesicon);

	/* @interface model: 更新店铺信息 */
	public void editShopInfo(ShopInfo shopInfo);

	/* @interface model: 删除店铺信息 */
	public void removeShopInfo(Long shopInfoId);

	/* @interface model: 根据店铺ID获取店铺信息 */
	public ShopInfo getShopInfo(Long shopInfoId);

	/* @interface model: 获取所有店铺信息 */
	public List<ShopInfo> getShopInfos();

	/* @interface model: 更新店铺信息，图片，地址 */
	public void updateShopInfo(ShopInfo shopInfo, UserAddress userAddress,
			List<MultipartFile> files, List<MultipartFile> filesicon);

	/* 根据userid获取店铺信息 */
	public ShopInfo getShopInfoByUserId(long userId);
}
