/**
 * @Title: ShoppingDetailServiceImpl.java
 * @Package com.huaixuan.network.biz.service.stock.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:47:16
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.stock.ShoppingDetailDao;
import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingMoreDetail;
import com.huaixuan.network.biz.service.stock.ShoppingDetailService;

/**
 * @ClassName: ShoppingDetailServiceImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:47:16
 *
 */
@Service("shoppingDetailService")
public class ShoppingDetailServiceImpl implements ShoppingDetailService {

	@Autowired
	public ShoppingDetailDao shoppingDetailDao;
//
//	public CatAttrRelDao catAttrRelDao;
//
//	public void setShoppingDetailDao(ShoppingDetailDao shoppingDetailDao) {
//		this.shoppingDetailDao = shoppingDetailDao;
//	}
//
//	public ShoppingDetailDao getShoppingDetailDao() {
//		return this.shoppingDetailDao;
//	}
//
//	/**
// * @return catAttrRelDao
// */
//public CatAttrRelDao getCatAttrRelDao() {
//	return catAttrRelDao;
//}
//
///**
// * @param catAttrRelDao 要设置的 catAttrRelDao
// */
//public void setCatAttrRelDao(CatAttrRelDao catAttrRelDao) {
//	this.catAttrRelDao = catAttrRelDao;
//}
//
/* @model: */
	public void addShoppingDetail(ShoppingDetail shoppingDetailDao) {
	//		log.info("ShoppingDetailManagerImpl.addShoppingDetail method");
		try {
			 this.shoppingDetailDao.addShoppingDetail(shoppingDetailDao);
		} catch (Exception e) {
	//			log.error(e.getMessage());
		}
	}
//
	public void editShoppingDetail(ShoppingDetail shoppingDetail) {
//		log.info("ShoppingDetailManagerImpl.editShoppingDetail method");
		try {
			this.shoppingDetailDao.editShoppingDetail(shoppingDetail);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	public void removeShoppingDetail(Long shoppingDetailId) {
//		log.info("ShoppingDetailManagerImpl.removeShoppingDetail method");
		try {
			this.shoppingDetailDao.removeShoppingDetail(shoppingDetailId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
	}

	public ShoppingDetail getShoppingDetail(Long shoppingDetailId) {
//		log.info("ShoppingDetailManagerImpl.getShoppingDetail method");
		try {
			return this.shoppingDetailDao.getShoppingDetail(shoppingDetailId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
	}

public List<ShoppingMoreDetail> getShoppingDetailsByShoppingListId(Long shoppingListId) {
//	log.info("ShoppingDetailManagerImpl.getShoppingDetailsByShoppingListId method");
		try {
			return this.shoppingDetailDao.getShoppingDetailsByShoppingListId(shoppingListId);
		} catch (Exception e) {
//			log.error(e.getMessage());
		}
		return null;
}

/* （非 Javadoc）
 * @see com.hundsun.bible.facade.ios.ShoppingDetailManager#getShopDetailsByShopListId(java.lang.Long)
 */
public List<ShoppingDetail> getShopDetailsByShopListId(Long shoppingListId) {
//	log.info("ShoppingDetailManagerImpl.getShopDetailsByShopListId method");
	try {
		return this.shoppingDetailDao.getShopDetailsByShopListId(shoppingListId);
	} catch (Exception e) {
//		log.error(e.getMessage());
	}
	return null;
}

/* （非 Javadoc）
 * @see com.hundsun.bible.facade.ios.ShoppingDetailManager#getMissingNumByShoppingListId(java.lang.Long)
 */
public int getMissingNumByShoppingListId(Long shoppingListId) {
//	log.info("ShoppingDetailManagerImpl.getMissingNumByShoppingListId method");
	try {
		return (Integer)this.shoppingDetailDao.getMissingNumByShoppingListId(shoppingListId);
	} catch (Exception e) {
//		log.error(e.getMessage());
	}
	return 0;
}
//
/* （非 Javadoc）
 * @see com.hundsun.bible.facade.ios.ShoppingDetailManager#getRejectNumByShoppingListId(java.lang.Long)
 */
public int getRejectNumByShoppingListId(Long shoppingListId) {
//	log.info("ShoppingDetailManagerImpl.getRejectNumByShoppingListId method");
	try {
		return (Integer)this.shoppingDetailDao.getRejectNumByShoppingListId(shoppingListId);
	} catch (Exception e) {
//		log.error(e.getMessage());
	}
	return 0;
}
//
/* （非 Javadoc）
 * @see com.hundsun.bible.facade.ios.ShoppingDetailManager#getCountByShoppingIdAndGoodsInsId(java.util.Map)
 */
public int getCountByShoppingIdAndGoodsInsId(Map<String, String> parMap) {
//	log.info("ShoppingDetailManagerImpl.getCountByShoppingIdAndGoodsInsId method");
	try {
		return (Integer)this.shoppingDetailDao.getCountByShoppingIdAndGoodsInsId(parMap);
	} catch (Exception e) {
//		log.error(e.getMessage());
	}
	return 0;
}
//
///* (non-Javadoc)
// * @see com.hundsun.bible.facade.ios.ShoppingDetailManager#getCountShoppingDetailByCatCode(java.util.Map)
// */
//public int getCountShoppingDetailByCatCode(Map<String, String> parMap) {
////    log.info("ShoppingDetailManagerImpl.getCountShoppingDetailByCatCode method");
//    try {
//        return (Integer)this.shoppingDetailDao.getCountShoppingDetailByCatCode(parMap);
//    } catch (Exception e) {
////        log.error(e.getMessage());
//    }
//    return 0;
//}


}
