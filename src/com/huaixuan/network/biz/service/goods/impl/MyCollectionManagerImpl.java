package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.MyCollectionDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.MyCollection;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.goods.MyCollectionManager;

@Service("myCollectionManager")
public class MyCollectionManagerImpl implements MyCollectionManager {
    protected Log           log = LogFactory.getLog(this.getClass());
    @Autowired
    private MyCollectionDao myCollectionDao;
    @Autowired
    private GoodsManager    goodsManager;

    @Override
    public void addMyCollection(MyCollection myCollectionDao) {
        log.info("MyCollectionManagerImpl.addMyCollection method");
        try {
            this.myCollectionDao.addMyCollection(myCollectionDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editMyCollection(MyCollection myCollection) {
        log.info("MyCollectionManagerImpl.editMyCollection method");
        try {
            this.myCollectionDao.editMyCollection(myCollection);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void removeMyCollection(Long myCollectionId) {
        log.info("MyCollectionManagerImpl.removeMyCollection method");
        try {
            this.myCollectionDao.removeMyCollection(myCollectionId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public MyCollection getMyCollection(Long myCollectionId) {
        log.info("MyCollectionManagerImpl.getMyCollection method");
        try {
            return this.myCollectionDao.getMyCollection(myCollectionId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MyCollection> getMyCollections() {
        log.info("MyCollectionManagerImpl.getMyCollections method");
        try {
            return this.myCollectionDao.getMyCollections();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<MyCollection> getMyCollectionByCondtion(MyCollection query) {
        log.info("MyCollectionManagerImpl.getMyCollectionByCondtion method");
        try {
            return this.myCollectionDao.getMyCollectionByCondition(query);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public QueryPage getMyCollectionsByUserId(Long userId, String isWholesale, int currentPage,
                                              int pageSize) {
        log.info("MyCollectionManagerImpl.getMyCollectionsByUserId method");
        try {
            return this.myCollectionDao.getMyCollectionsByUserId(userId, isWholesale, currentPage,
                pageSize);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void clearAllCollectionByUserId(Long userId) {
        log.info("MyCollectionManagerImpl.clearAllCollectionByUserId method");
        try {
            this.myCollectionDao.clearAllCollectionByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //    public void clearExpiredCollection(Long userId, String isWholesale) {
    //        log.info("MyCollectionManagerImpl.clearExpiredCollection method");
    //        try {
    //            QueryPage all = this.getMyCollectionsByUserId(userId, null, isWholesale);
    //            if (all != null && all.size() > 0) {
    //                for (MyCollection tmp : all) {
    //                    Goods goods = goodsManager.getGoods(tmp.getGoodsId());
    //                    if (goods == null || !"on_sale".equals(goods.getGoodsStatus())) {
    //                        this.removeMyCollection(tmp.getId());
    //                    }
    //                }
    //            }
    //        } catch (Exception e) {
    //            log.error(e.getMessage());
    //        }
    //    }

    @Override
    public QueryPage getMyCollectionWithParmap(Map<String, String> parMap, int currentPage,
                                               int pageSize) {
        log.info("MyCollectionManagerImpl.getMyCollectionWithParmap method");
        try {
            return myCollectionDao.getMyCollectionWithParmap(parMap, currentPage, pageSize);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getMyCollectionListByConditionWithPage(
			MyCollection myCollection, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(myCollection);
		Map pramas = queryPage.getParameters();

		int count = myCollectionDao.getMyCollectionListByConditionWithPageCount(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<MyCollection> myCollectionList = myCollectionDao.getMyCollectionListByConditionWithPage(pramas);
			if (myCollectionList != null && myCollectionList.size() > 0) {
				queryPage.setItems(myCollectionList);
			}
		}
		return queryPage;
	}

}
