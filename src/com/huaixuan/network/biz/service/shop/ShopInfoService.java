package com.huaixuan.network.biz.service.shop;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.user.UserAddress;

public interface ShopInfoService {
	/* @interface model: ����������Ϣ */
	public void addShopInfo(ShopInfo shopInfo, UserAddress userAddress,
			List<MultipartFile> files, List<MultipartFile> filesicon);

	/* @interface model: ���µ�����Ϣ */
	public void editShopInfo(ShopInfo shopInfo);

	/* @interface model: ɾ��������Ϣ */
	public void removeShopInfo(Long shopInfoId);

	/* @interface model: ���ݵ���ID��ȡ������Ϣ */
	public ShopInfo getShopInfo(Long shopInfoId);

	/* @interface model: ��ȡ���е�����Ϣ */
	public List<ShopInfo> getShopInfos();

	/* @interface model: ���µ�����Ϣ��ͼƬ����ַ */
	public void updateShopInfo(ShopInfo shopInfo, UserAddress userAddress,
			List<MultipartFile> files, List<MultipartFile> filesicon);

	/* ����userid��ȡ������Ϣ */
	public ShopInfo getShopInfoByUserId(long userId);
}
