package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.MyCollectionDao;
import com.huaixuan.network.biz.domain.goods.MyCollection;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("myCollectionDao")
public class MyCollectionDaoiBatis implements MyCollectionDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addMyCollection(MyCollection myCollection) throws Exception {
        this.sqlMapClient.insert("addMyCollection", myCollection);
    }

    @Override
    public void editMyCollection(MyCollection myCollection) throws Exception {
        this.sqlMapClient.update("editMyCollection", myCollection);
    }

    @Override
    public void removeMyCollection(Long myCollectionId) throws Exception {
        this.sqlMapClient.delete("removeMyCollection", myCollectionId);
    }

    @Override
    public MyCollection getMyCollection(Long myCollectionId) throws Exception {
        return (MyCollection) this.sqlMapClient.queryForObject("getMyCollection", myCollectionId);
    }

    @Override
    public List<MyCollection> getMyCollections() throws Exception {
        return this.sqlMapClient.queryForList("getMyCollections", null);
    }

    @Override
    public List<MyCollection> getMyCollectionByCondition(MyCollection condition) throws Exception {
        return this.sqlMapClient.queryForList("getMyCollectionByCondition", condition);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getMyCollectionsByUserId(Long userId, String isWholesale, int currentPage,
                                              int pageSize) {
        Map parMap = new HashMap();
        parMap.put("userId", userId);
        parMap.put("isWholesale", isWholesale);

        QueryPage queryPage = new QueryPage(new MyCollection());
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient.queryForObject("getMyCollectionsCountByUserId", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("getMyCollectionsByUserId", parMap));
        }
        return queryPage;
    }

    @Override
    public void clearAllCollectionByUserId(Long userId) {
        this.sqlMapClient.delete("clearAllCollectionByUserId", userId);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getMyCollectionWithParmap(Map parMap, int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(new MyCollection());
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient.queryForObject("getMyCollectionCountWithParmap", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("getMyCollectionWithParmap", parMap));
        }
        return queryPage;
    }

	@Override
	public int getMyCollectionListByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getMyCollectionCountWithParmap", parMap);
	}

	@Override
	public List<MyCollection> getMyCollectionListByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getMyCollectionWithParmap", parMap);
	}
    
}
