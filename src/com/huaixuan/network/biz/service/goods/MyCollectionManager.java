package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.MyCollection;
import com.huaixuan.network.biz.query.QueryPage;

public interface MyCollectionManager {

    public void addMyCollection(MyCollection myCollection);

    public void editMyCollection(MyCollection myCollection);

    public void removeMyCollection(Long myCollectionId);

    public MyCollection getMyCollection(Long myCollectionId);

    public List<MyCollection> getMyCollections();

    public QueryPage getMyCollectionsByUserId(Long userId, String isWholesale, int currentPage,
                                              int pageSize);

    public List<MyCollection> getMyCollectionByCondtion(MyCollection query);

    public void clearAllCollectionByUserId(Long userId);

//    public void clearExpiredCollection(Long userId, String isWholesale);

    /**
     * 根据parMap查List
     * @param page
     * @param parMap
     * @return List<MyCollection>
     */
    public QueryPage getMyCollectionWithParmap(Map<String, String> parMap, int currentPage,
                                               int pageSize);
    
    /**
     * 找收藏列表
     * @param myCollection
     * @param currPage
     * @param pageSize
     * @return
     */
    public QueryPage getMyCollectionListByConditionWithPage(MyCollection myCollection, int currPage, int pageSize);
}
