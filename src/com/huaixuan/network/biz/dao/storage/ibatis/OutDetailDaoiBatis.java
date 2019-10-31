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
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ????????????????????1??7(bibleUtil auto code generation)
 * @version 3.2.0
 */
@Repository("outDetailDao")
public class OutDetailDaoiBatis implements OutDetailDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

    /* @model: */
    public Long addOutDetail(OutDetail outDetail) throws Exception {
        return (Long) this.sqlMapClient.insert("addOutDetail", outDetail);
    }

    /* @model: */
    public void editOutDetail(OutDetail outDetail) throws Exception {
        this.sqlMapClient.update("editOutDetail", outDetail);
    }

    /* @model: */
    public void removeOutDetail(Long outDetailId) throws Exception {
        this.sqlMapClient.delete("removeOutDetail", outDetailId);
    }

    /* @model: */
    public OutDetail getOutDetail(Long outDetailId) throws Exception {
        return (OutDetail) this.sqlMapClient.queryForObject("getOutDetail",
            outDetailId);
    }

    /* @model: */
    public List<OutDetail> getOutDetails() throws Exception {
        return this.sqlMapClient.queryForList("getOutDetails", null);
    }

    public void addOutDetails(List<OutDetail> outDetails) {
        this.batch(outDetails, "add");
    }

    /**
     * 批量操作，主要是添加，更新，删除
     * @param list 要操作的对象雄1??7
     * @param executeType 添加（add），修改（edit），删除（remove＄1??7
     */
    @SuppressWarnings("unchecked")
    private void batch(final List<OutDetail> list, final String executeType) {
        sqlMapClient.execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                if (executeType.equals("add")) {
                    for (int i = 0; i < list.size(); i++) {
                        OutDetail o = list.get(i);
                        executor.insert("addOutDetail", o);
                    }
                }

                executor.executeBatch();
                return null;
            }
        });
        return;
    }

    @SuppressWarnings("unchecked")
    public List<OutDetailGoods> getOutDetailGoodsLists(Long id) {
        return this.sqlMapClient.queryForList("getOutDetailGoodsLists", id);
    }

    public OutDetailBaseInfo getOutDetailSalesChangeBaseInfo(Long outDetailId) {
        return (OutDetailBaseInfo) this.sqlMapClient.queryForObject(
            "getOutDetailSalesChangeBaseInfo", outDetailId);
    }

    @SuppressWarnings("unchecked")
    public OutDetailBaseInfo getOutDetailShoppingBaseInfo(Long outDetailId, String type) {
        Map map = new HashMap();
        map.put("outDetailId", outDetailId);
        map.put("type", type);
        return (OutDetailBaseInfo) this.sqlMapClient.queryForObject(
            "getOutDetailShoppingBaseInfo", map);
    }

    @SuppressWarnings("unchecked")
    public void updateOutDetailStatusById(Long outDetailId, String status) {
        Map map = new HashMap();
        map.put("outDetailId", outDetailId);
        map.put("status", status);
        map.put("gmtModify", new Date());
        this.sqlMapClient.update("updateOutDetailStatusById", map);
    }

    @SuppressWarnings("unchecked")
    public void updateOutDetailStorTypeById(Long outDetailId, String storType) {
        Map map = new HashMap();
        map.put("outDetailId", outDetailId);
        map.put("storType", storType);
        this.sqlMapClient.update("updateOutDetailStorTypeById", map);
    }

    @SuppressWarnings("unchecked")
    public List<OutDetailGoods> listOutDetailNotFinish(Long outDepId, String status) {
        Map map = new HashMap();
        map.put("outDepId", outDepId);
        map.put("status", status);
        return (List<OutDetailGoods>) this.sqlMapClient.queryForList(
            "listOutDetailNotFinish", map);
    }

    public void editOutOutDetailGoods(OutDetailGoods outDetailGoodsInfo) throws Exception {
        this.sqlMapClient.update("editOutOutDetailGoods", outDetailGoodsInfo);
    }

    public GatherOutDepository gatherFinanceOutDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)
                                                                                          throws Exception {
        return (GatherOutDepository) this.sqlMapClient.queryForObject(
            "gatherFinanceOutDepositoryCount", financeDepositoryQuery);
    }

    @SuppressWarnings("unchecked")
	public QueryPage gatherFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize) throws Exception {
    	QueryPage queryPage = new QueryPage(financeDepositoryQuery);
    	Map parMap = new HashMap();
    	parMap = queryPage.getParameters();
    	GatherOutDepository countList = (GatherOutDepository) sqlMapClient.queryForObject("gatherFinanceOutDepositoryCount", parMap);

		if (countList.getCount() > 0) {

			queryPage.setTotalItem(countList.getCount().intValue());
			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("gatherFinanceOutDepositoryLists", parMap));
		}
		return queryPage;
    }

    public GatherOutDepository estimateFinanceOutDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)
                                                                                            throws Exception {
        return (GatherOutDepository) this.sqlMapClient.queryForObject(
            "estimateFinanceOutDepositoryCount", financeDepositoryQuery);
    }

    @SuppressWarnings("unchecked")
	public QueryPage estimateFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize) throws Exception {

    	QueryPage queryPage = new QueryPage(financeDepositoryQuery);

    	Map parMap = queryPage.getParameters();

    	GatherOutDepository countList = (GatherOutDepository) sqlMapClient.queryForObject("estimateFinanceOutDepositoryCount", parMap);
		if (countList.getCount() > 0) {

			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(countList.getCount().intValue());
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow",queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("estimateFinanceOutDepositoryLists", parMap));
		}
		return queryPage;
    }

    public List<OutDetail> getOutDetailListByOutDepositoryId(Long outDepositoryId) throws Exception {
        return this.sqlMapClient.queryForList("getOutDetailListByOutDepositoryId",
            outDepositoryId);
    }
}
