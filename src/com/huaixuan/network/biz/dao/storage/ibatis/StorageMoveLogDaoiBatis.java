package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.StorageMoveLogDao;
import com.huaixuan.network.biz.domain.storage.StorageMoveLog;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Service("StorageMoveLogDao")
public class StorageMoveLogDaoiBatis implements StorageMoveLogDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
    public void addStorageMoveLog(StorageMoveLog storageMoveLog) throws Exception {
        this.sqlMapClient.insert("addStorageMoveLog", storageMoveLog);
    }

	@Override
    public void editStorageMoveLog(StorageMoveLog storageMoveLog) throws Exception {
        this.sqlMapClient.update("editStorageMoveLog", storageMoveLog);
    }

	@Override
    public void removeStorageMoveLog(Long storageMoveLogId) throws Exception {
        this.sqlMapClient.delete("removeStorageMoveLog", storageMoveLogId);
    }

	@Override
    public StorageMoveLog getStorageMoveLog(Long storageMoveLogId) throws Exception {
        return (StorageMoveLog) this.sqlMapClient.queryForObject("getStorageMoveLog",
            storageMoveLogId);
    }

	@SuppressWarnings("unchecked")
	@Override
    public QueryPage getStorageMoveLogsByMap(Map parMap, int currentPage, int pageSize) {
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getStorageMoveLogsCountByMap", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("getStorageMoveLogsByMap", parMap));
		}
		return queryPage;
    }

	@Override
    public int getStorageMoveLogsCountByMap(Map<String, String> parMap) {
        return (Integer) this.sqlMapClient.queryForObject(
            "getStorageMoveLogsCountByMap", parMap);
    }

	@Override
    public int getBorrowedMoveLogsCountByMap(Map<String, String> parMap) {
        return (Integer) this.sqlMapClient.queryForObject(
            "getBorrowedMoveLogsCountByMap", parMap);
    }

	@Override
    public int sumMoveNumByMap(Map<String, String> parMap) {
        return (Integer) this.sqlMapClient.queryForObject("sumMoveNumByMap", parMap);
    }

	@Override
    public QueryPage getMoreDetailByMap(Map<String, String> parMap, int currentPage, int pageSize, boolean isPage) throws Exception {
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

//		if(isPage){
//			int count = (Integer) sqlMapClient.queryForObject("",parMap); 
//			queryPage.setTotalItem(count);
//
//			if (count > 0) {
//				parMap.put("startRow", String.valueOf(queryPage.getPageFristItem()));
//				parMap.put("endRow", String.valueOf(queryPage.getPageLastItem()));
//				/* 分页查询  */
//				queryPage.setItems(sqlMapClient.queryForList("getMoreDetailByMap", parMap));
//			}
//		}else{
//			// 导出使用的方法，无记录总数
//			queryPage.setItems(sqlMapClient.queryForList("getMoreDetailByMap", parMap));
//		}

		// 导出使用的方法，无记录总数
		queryPage.setItems(sqlMapClient.queryForList("getMoreDetailByMap", parMap));

		return queryPage;
    }

	@Override
    public QueryPage getStorageMoveLogsByMoveCode(Map parMap, int currentPage, int pageSize)
                                                                                                   throws Exception {
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getStorageMoveLogsCountByMoveCode", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("getStorageMoveLogsByMoveCode", parMap));
		}
		return queryPage;
    }

	@Override
    public int getStorageMoveLogsCountByMoveCode(Map<String, String> parMap) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getStorageMoveLogsCountByMoveCode", parMap);
    }
    public int getMoveOrdersCountByMap(Map<String, String> parMap) throws Exception
    {
        return (Integer) this.sqlMapClient.queryForObject(
            "getMoveOrdersCountByMap",parMap);
    }

    @Override
    public QueryPage getMoveOrdersDetailByMap(Map parMap, int currentPage, int pageSize) throws Exception {
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getMoveOrdersCountByMap", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询  */
			queryPage.setItems(sqlMapClient.queryForList("getMoveOrdersDetailByMap", parMap));
		}
		return queryPage;
	}

	public StorageMoveLog getSumNumByMap(Map<String, String> parMap) throws Exception {
		return (StorageMoveLog) this.sqlMapClient.queryForObject("getSumNumByMap",parMap);
	}

}
