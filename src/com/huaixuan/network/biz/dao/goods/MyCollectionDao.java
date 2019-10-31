package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.MyCollection;
import com.huaixuan.network.biz.query.QueryPage;

public interface MyCollectionDao {

    void addMyCollection(MyCollection myCollection) throws Exception;

    void editMyCollection(MyCollection myCollection) throws Exception;

    void removeMyCollection(Long myCollectionId) throws Exception;

    MyCollection getMyCollection(Long myCollectionId) throws Exception;

    List<MyCollection> getMyCollectionByCondition(MyCollection condition) throws Exception;

    List<MyCollection> getMyCollections() throws Exception;

    QueryPage getMyCollectionsByUserId(Long userId, String isWholesale, int currentPage,
                                       int pageSize);

    void clearAllCollectionByUserId(Long userId);

    /**
     * ”√parMap≤ÈList
     * @param page
     * @param parMap
     * @return QueryPage
     */
    QueryPage getMyCollectionWithParmap(Map parMap, int currentPage, int pageSize);
    
    int getMyCollectionListByConditionWithPageCount(Map parMap);
    
    List<MyCollection> getMyCollectionListByConditionWithPage(Map parMap);
}
