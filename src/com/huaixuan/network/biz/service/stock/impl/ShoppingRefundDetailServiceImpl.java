/**
 * @Title: ShoppingRefundDetailServiceImpl.java
 * @Package com.huaixuan.network.biz.service.stock.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ÏÂÎç07:55:21
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.stock.ShoppingRefundDetailDao;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.service.stock.ShoppingRefundDetailService;

/**
 * @ClassName: ShoppingRefundDetailServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ÏÂÎç07:55:21
 *
 */
@Service("shoppingRefundDetailService")
public class ShoppingRefundDetailServiceImpl
		implements ShoppingRefundDetailService {

	@Autowired
	ShoppingRefundDetailDao shoppingRefundDetailDao;

	public long addShoppingRefundDetail(
			ShoppingRefundDetail shoppingRefundDetailDao) {
//		log.info("ShoppingRefundDetailManagerImpl.addShoppingRefundDetail method");
		try {
			return this.shoppingRefundDetailDao
					.addShoppingRefundDetail(shoppingRefundDetailDao);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return 0;
	}

	/* @model: */
	public void editShoppingRefundDetail(
			ShoppingRefundDetail shoppingRefundDetail) {
//		log.info("ShoppingRefundDetailManagerImpl.editShoppingRefundDetail method");
		try {
			this.shoppingRefundDetailDao
					.editShoppingRefundDetail(shoppingRefundDetail);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void removeShoppingRefundDetail(Long shoppingRefundDetailId) {
//		log.info("ShoppingRefundDetailManagerImpl.removeShoppingRefundDetail method");
		try {
			this.shoppingRefundDetailDao
					.removeShoppingRefundDetail(shoppingRefundDetailId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	/* @model: */
	public ShoppingRefundDetail getShoppingRefundDetail(
			Long shoppingRefundDetailId) {
//		log.info("ShoppingRefundDetailManagerImpl.getShoppingRefundDetail method");
		try {
			return this.shoppingRefundDetailDao
					.getShoppingRefundDetail(shoppingRefundDetailId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/* @model: */
	public List<ShoppingRefundDetail> getShoppingRefundDetails() {
//		log.info("ShoppingRefundDetailManagerImpl.getShoppingRefundDetails method");
		try {
			return this.shoppingRefundDetailDao.getShoppingRefundDetails();
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public List<ShoppingRefundDetail> getShoppingRefundDetailsId(
			Long shoppingRefundDetailId) {
//		log.info("ShoppingRefundDetailManagerImpl.getShoppingRefundDetails method");
		try {
			return this.shoppingRefundDetailDao
					.getShoppingRefundDetails(shoppingRefundDetailId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * £¨·Ç Javadoc£©
	 *
	 * @see com.hundsun.bible.facade.ios.ShoppingRefundDetailManager#
	 * getCountRefundByShoppingId(java.lang.Long, java.lang.String)
	 */
	public int getCountRefundByShoppingIdAndStatus(Map<String, String> parMap) {
		try {
			return shoppingRefundDetailDao
					.getCountRefundByShoppingIdAndStatus(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return 0;
	}

	public List<ShoppingRefundDetail> getRefundDetail(Map<String, Object> parMap) {
		try {
			return this.shoppingRefundDetailDao.getRefundDetial(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public List<ShoppingRefundDetail> getStorageRefundDetails(
			Map<String, Object> parMap) {
		try {
			return this.shoppingRefundDetailDao.getStorageRefundDetails(parMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public int getCountRefundDetail(Map<String, Object> paramMap) {
		try {
			return this.shoppingRefundDetailDao.getCountRefundDetail(paramMap);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return 0;
	}

	public ShoppingRefundDetail sumRefundDetailByShoppingId(
			Map<String, Object> param) {
		try {
			return this.shoppingRefundDetailDao
					.sumRefundDetailByShoppingId(param);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

	public List<ShoppingRefundDetail> getShoppingRefundDetailByShoppingRefundId(
			Long shoppingRefundId) {
//		log.info("ShoppingRefundDetailManagerImpl.getShoppingRefundDetailByShoppingRefundId method");
		try {
			return this.shoppingRefundDetailDao
					.getShoppingRefundDetailByShoppingRefundId(shoppingRefundId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}
}