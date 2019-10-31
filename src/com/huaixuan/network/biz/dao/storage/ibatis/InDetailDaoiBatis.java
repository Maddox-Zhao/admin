package com.huaixuan.network.biz.dao.storage.ibatis;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ????????????????????(bibleUtil auto code generation)
 * @version 3.2.0
 */
@Repository("inDetailDao")
public class InDetailDaoiBatis implements InDetailDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

    /* @model: */
    public long addInDetail(InDetail inDetail) throws Exception {
        return (Long) this.sqlMapClient.insert("addInDetail", inDetail);
    }

    /* @model: */
    public void editInDetail(InDetail inDetail) throws Exception {
        this.sqlMapClient.update("editInDetail", inDetail);
    }

    /* @model: */
    public void removeInDetail(Long inDetailId) throws Exception {
        this.sqlMapClient.delete("removeInDetail", inDetailId);
    }

    /* @model: */
    public InDetail getInDetail(Long inDetailId) throws Exception {
        return (InDetail) this.sqlMapClient.queryForObject("getInDetail", inDetailId);
    }

    /* @model: */
    public List<InDetail> getInDetails() throws Exception {
        return this.sqlMapClient.queryForList("getInDetails", null);
    }

    @SuppressWarnings("unchecked")
    public List<InDetailGoods> getInDetailGoodsLists(Long id) {
        return this.sqlMapClient.queryForList("getInDetailGoodsLists", id);
    }

    @SuppressWarnings("unchecked")
    public List<GoodsForLocation> getLocationForGoods(Long id, Long depFirstId) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("depFirstId", depFirstId);
        return this.sqlMapClient.queryForList("getLocationForGoods", map);
    }

    @SuppressWarnings("unchecked")
    public List<GoodsForLocation> getLocationForGoodsNoMatch(Long id, Long depFirstId) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("depFirstId", depFirstId);
        return this.sqlMapClient.queryForList("getLocationForGoodsNoMatch", map);
    }

    @SuppressWarnings("unchecked")
    public void updateStatusById(String status, Long id, Long depFirstId, String storType) {
        Map map = new HashMap();
        map.put("status", status);
        map.put("id", id);
        map.put("gmtModify", new Date());
        map.put("depFirstId", depFirstId);
        map.put("storType", storType);
        this.sqlMapClient.update("updateStatusById", map);
    }

    @SuppressWarnings("unchecked")
    public List<InDetailGoods> listInDetailNotFinish(Long inDepId, String status) {
        Map map = new HashMap();
        map.put("inDepId", inDepId);
        map.put("status", status);
        return (List<InDetailGoods>) this.sqlMapClient.queryForList(
            "listInDetailNotFinish", map);
    }

    public InDetailBaseInfo getCheckInDetailBaseInfo(Long inDetailId) {
        return (InDetailBaseInfo) this.sqlMapClient.queryForObject(
            "getCheckInDetailBaseInfo", inDetailId);
    }

    @SuppressWarnings("unchecked")
    public InDetailBaseInfo getShoppingOrSalesInDetailBaseInfo(Long inDetailId, String type) {
        Map map = new HashMap();
        map.put("inDetailId", inDetailId);
        map.put("type", type);
        return (InDetailBaseInfo) this.sqlMapClient.queryForObject(
            "getShoppingOrSalesInDetailBaseInfo", map);
    }

    @SuppressWarnings("unchecked")
    public List<GoodsForLocation> getSalesLocationForGoods(Long id) {
        return this.sqlMapClient.queryForList("getSalesLocationForGoods", id);
    }

    @SuppressWarnings("unchecked")
    public List<GoodsForLocation> getSalesLocationForGoodsChange(Long id) {
        return this.sqlMapClient.queryForList("getSalesLocationForGoodsChange", id);
    }

    public void addInDetails(List<InDetail> inDetails) {
        this.batch(inDetails, "add");
    }

    /**
     * 批量操作，主要是添加，更新，删除
     * @param list 要操作的对象集
     * @param executeType 添加（add），修改（edit），删除（remove）
     */
    @SuppressWarnings("unchecked")
    private void batch(final List<InDetail> list, final String executeType) {
        sqlMapClient.execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                if (executeType.equals("add")) {
                    for (int i = 0; i < list.size(); i++) {
                        InDetail o = list.get(i);
                        executor.insert("addInDetail", o);
                    }
                }

                executor.executeBatch();
                return null;
            }
        });
        return;
    }

    @SuppressWarnings("unchecked")
    public List<GoodsForLocation> listInDetailForDisByDetailId(Long inDetailId) {
        if (inDetailId == null) {
            return null;
        }
        return this.sqlMapClient.queryForList("listInDetailForDisByDetailId",
            inDetailId);
    }

    public int getGoodsLocationCountByInDetailId(Long inDetailId) {
        if (inDetailId == null) {
            return 0;
        }
        return (Integer) this.sqlMapClient.queryForObject(
            "getGoodsLocationCountByInDetailId", inDetailId);
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.dao.ios.InDetailDao#gatherInDepositoryLists(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public QueryPage gatherInDepositoryLists(Map parMap, int currentPage, int pageSize, boolean isPage) throws Exception {
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			int count = (Integer) sqlMapClient.queryForObject("gatherInDepositoryListsCount", parMap);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClient.queryForList("gatherInDepositoryLists", parMap));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("gatherInDepositoryLists", parMap));
		}

		return queryPage;
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.dao.ios.InDetailDao#gatherInDepositoryListsCount(java.util.Map)
     */
    public int gatherInDepositoryListsCount(Map parMap) throws Exception {
        if (parMap == null) {
            return 0;
        }
        return (Integer) this.sqlMapClient.queryForObject(
            "gatherInDepositoryListsCount", parMap);
    }

    public void editInDetailGoodsInfo(InDetailGoods inDetailGoodsInfo) throws Exception {
        this.sqlMapClient.update("editInDetailGoodsInfo", inDetailGoodsInfo);
    }

    public GatherInDepository gatherFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)
                                                                                        throws Exception {
        return (GatherInDepository) this.sqlMapClient.queryForObject(
            "gatherFinanceInDepositoryCount", financeDepositoryQuery);
    }

    @SuppressWarnings("unchecked")
	public QueryPage gatherFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize) throws Exception {
    	QueryPage queryPage = new QueryPage(financeDepositoryQuery);
    	Map parMap = queryPage.getParameters();

    	GatherInDepository countList = (GatherInDepository) sqlMapClient.queryForObject("gatherFinanceInDepositoryCount", parMap);

		if (countList.getCount() > 0) {

			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(countList.getCount().intValue());
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("gatherFinanceInDepositoryLists", parMap));
		}
		return queryPage;
    }

    public GatherInDepository estimateFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)
                                                                                          throws Exception {
        return (GatherInDepository) this.sqlMapClient.queryForObject(
            "estimateFinanceInDepositoryCount", financeDepositoryQuery);
    }

    @SuppressWarnings("unchecked")
	public QueryPage estimateFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize) throws Exception {
    	QueryPage queryPage = new QueryPage(financeDepositoryQuery);
    	Map parMap = queryPage.getParameters();
    	GatherInDepository countList = (GatherInDepository) sqlMapClient.queryForObject("estimateFinanceInDepositoryCount", parMap);

		if (countList.getCount() > 0) {

			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(countList.getCount().intValue());
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("estimateFinanceInDepositoryLists", parMap));
		}
		return queryPage;
    }

    public InDetailBaseInfo getInfoByInDetailIdForShoppingList(Long inDetailId) {
        return (InDetailBaseInfo) this.sqlMapClient.queryForObject(
            "getInfoByInDetailIdForShoppingList", inDetailId);
    }

    public List<GoodsForLocation> getLocationForDefect(Long id, Long locId) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("locId", locId);
        return this.sqlMapClient.queryForList("getLocationForDefect", map);
    }

	public List<InDetail> getInDetailListByInDepositoryId(Long InDepositoryId) {
		return this.sqlMapClient.queryForList("getInDetailListByInDepositoryId", InDepositoryId);
	}
}
