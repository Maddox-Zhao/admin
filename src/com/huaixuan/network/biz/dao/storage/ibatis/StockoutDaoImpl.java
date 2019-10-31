package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.StockoutDao;
import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.domain.storage.query.StockoutQuery;
import com.huaixuan.network.biz.query.QueryPage;

@Service("stockoutDao")
public class StockoutDaoImpl implements StockoutDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

    public long insertStockOut(Stockout stockout) {
        Object obj = this.sqlMapClient.insert("addStockOut", stockout);
        if (null != obj) {
            return (Long) obj;
        } else {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
	public QueryPage getStockoutList(StockoutQuery stockoutQuery, int currentPage, int pageSize, boolean isPage) {
    	QueryPage queryPage = new QueryPage(stockoutQuery);
    	Map pramas = queryPage.getParameters();

		if(isPage){
			int count = (Integer) sqlMapClient.queryForObject("getStockOutCount", pramas);
			if (count > 0) {
				/* 当前页 */
				queryPage.setCurrentPage(currentPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClient.queryForList("getStockOutByMap", pramas));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("getStockOutByMap", pramas));
		}
		return queryPage;
    }

    public void updateNotifyStatus(Map<String, Object> parMap) {
        this.sqlMapClient.update("updateNotifyStatus", parMap);
    }

    public Stockout getStockout(long ids) {
        int id = (int) ids;
        return (Stockout) this.sqlMapClient.queryForObject("getStockoutbyid", id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Stockout> getStockoutList(Map parMap) {
		return sqlMapClient.queryForList("getStockOutByMap", parMap);
	}

}
