package com.huaixuan.network.biz.service.shop.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.shop.ShopInfoDao;
import com.huaixuan.network.biz.dao.user.UserAddressDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.common.util.DateUtil;

@Service("shopInfoService")
public class ShopInfoServiceImpl implements ShopInfoService {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	public ShopInfoDao shopInfoDao;
	@Autowired
	public UserAddressDao userAddressDao;
	@Autowired
	private UploadUtil uploadUtil;

	@Override
	public void addShopInfo(ShopInfo shopInfo, UserAddress userAddress,
			List<MultipartFile> files, List<MultipartFile> filesicon) {
		log.info("ShopInfoServiceImpl.addShopInfo method");
		try {
			String shopLogoPicPath = "shopinfo" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			// String realPathPre = uploadUtil.getRealUpload() +
			// Constants.FILE_SEP
			// + uploadUtil.getUploadRootPath();
			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								shopLogoPicPath);
						if (i == 0) {
							shopInfo.setShopLogo(shopLogoPicPath
									+ Constants.FILE_SEP + fileName);

						}
						i++;
					}
				}
			}

			int j = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								shopLogoPicPath);
						if (i == 0) {
							shopInfo.setFaviconLogo(shopLogoPicPath
									+ Constants.FILE_SEP + fileName);

						}
						j++;
					}
				}
			}

			Long addressId = this.userAddressDao.addUserAddress(userAddress);
			shopInfo.setAddressId(addressId);
			this.shopInfoDao.addShopInfo(shopInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void editShopInfo(ShopInfo shopInfo) {
		log.info("ShopInfoServiceImpl.editShopInfo method");
		try {
			this.shopInfoDao.editShopInfo(shopInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void removeShopInfo(Long shopInfoId) {
		log.info("ShopInfoServiceImpl.removeShopInfo method");
		try {
			this.shopInfoDao.removeShopInfo(shopInfoId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	/**
	 * @author chenhang 2011-3-2
	 * @description 获得店铺信息
	 */
	@Override
	public ShopInfo getShopInfo(Long shopInfoId) {
		// log.info("ShopInfoServiceImpl.getShopInfo method");
		try {
			return this.shopInfoDao.getShopInfo(shopInfoId);
		} catch (Exception e) {
			// log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ShopInfo> getShopInfos() {
		log.info("ShopInfoServiceImpl.getShopInfos method");
		try {
			return this.shopInfoDao.getShopInfos();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateShopInfo(ShopInfo shopInfo, UserAddress userAddress,
			List<MultipartFile> files, List<MultipartFile> filesicon) {
		log.info("ShopInfoServiceImpl.updateShopInfo method");
		try {
			String shopLogoPicPath = "shopinfo" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());

			int i = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								shopLogoPicPath);
						if (i == 0) {
							shopInfo.setShopLogo(shopLogoPicPath
									+ Constants.FILE_SEP + fileName);

						}
						i++;
					}
				}
			}

			int j = 0;
			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						String fileName = uploadUtil.newUpload(file,
								shopLogoPicPath);
						if (i == 0) {
							shopInfo.setFaviconLogo(shopLogoPicPath
									+ Constants.FILE_SEP + fileName);

						}
						j++;
					}
				}
			}

			Long addressId = 0L;
			UserAddress userAddressnew = this.userAddressDao
					.getUserAddress(userAddress.getId());

			if (userAddressnew == null) {
				addressId = this.userAddressDao.addUserAddress(userAddress);
				shopInfo.setAddressId(addressId);
			} else {
				shopInfo.setAddressId(userAddress.getId());
				this.userAddressDao.updateShopInfoAddress(userAddress);
			}

			this.shopInfoDao.editShopInfo(shopInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public ShopInfo getShopInfoByUserId(long userId) {
		try {
			return this.shopInfoDao.getShopInfoByUserId(userId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
}
