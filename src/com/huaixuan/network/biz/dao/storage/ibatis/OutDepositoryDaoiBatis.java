package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 
 *
 * @version 3.2.0
 */
@Service("outDepositoryDao")
public class OutDepositoryDaoiBatis implements OutDepositoryDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    /* @model: */
    public Long addOutDepository(OutDepository outDepository)  {
        Long obj = (Long) this.sqlMapClientTemplate.insert("addOutDepository", outDepository);
        return obj;
    }

    /* @model: */
    public void editOutDepository(OutDepository outDepository)  {
        this.sqlMapClientTemplate.update("editOutDepository", outDepository);
    }

    /* @model: */
    public void removeOutDepository(Long outDepositoryId)  {
        this.sqlMapClientTemplate.delete("removeOutDepository", outDepositoryId);
    }

    /* @model: */
    public OutDepository getOutDepository(Long outDepositoryId)  {
        return (OutDepository) this.sqlMapClientTemplate.queryForObject("getOutDepository",
            outDepositoryId);
    }

    /* @model: */
    public List<OutDepository> getOutDepositorys()  {
        return this.sqlMapClientTemplate.queryForList("getOutDepositorys", null);
    }

    public OutDepository getOutDepository(Map map)  {
        return (OutDepository) this.sqlMapClientTemplate.queryForList(
            "getOutDepositoryByParams", map);
    }

    public int getOutDepositoryListsCount(Map<String, String> parMap) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "getOutDepositoryListsCount", parMap);
    }

    @SuppressWarnings("unchecked")
	public QueryPage getOutDepositoryLists(Map parMap, int currentPage, int pageSize, boolean isPage) {
    	QueryPage queryPage = new QueryPage(parMap);
    	queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getOutDepositoryListsCount", parMap);
		queryPage.setTotalItem(count);
		if(isPage){
			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getOutDepositoryLists", parMap));
			}
		}else{
			parMap.put("startRow", "1");
			parMap.put("endRow", String.valueOf(count));
			queryPage.setItems(sqlMapClientTemplate.queryForList("getOutDepositoryLists", parMap));
		}

		return queryPage;
    }

    @SuppressWarnings("unchecked")
	public QueryPage getActualInventoryLists(Map parMap, int currentPage, int pageSize, boolean isPage) {
    	QueryPage queryPage = new QueryPage(parMap);
    	queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getActualInventoryListsCount", parMap);
		queryPage.setTotalItem(count);
		if(isPage){
			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getActualInventoryLists", parMap));
			}
		}else{
			parMap.put("startRow", "1");
			parMap.put("endRow", String.valueOf(count));
			queryPage.setItems(sqlMapClientTemplate.queryForList("getActualInventoryLists", parMap));
		}

		return queryPage;
    }

    @SuppressWarnings("unchecked")
    public int updateOutDepositoryStatusById(Long id, String status, Date gmtOutDep, String modifier) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("status", status);
        map.put("gmtOutDep", gmtOutDep);
        map.put("modifier", modifier);
        return this.sqlMapClientTemplate.update("updateOutDepositoryStatusById", map);
    }

    public String getOutDepositoryStatusByDetailId(Long outDetailId) {
        if (outDetailId == null) {
            return "";
        }
        return (String) this.sqlMapClientTemplate.queryForObject(
            "getOutDepositoryStatusByDetailId", outDetailId);
    }

    @SuppressWarnings("unchecked")
    public int updateExpressCodeById(String expressCode, Long outDepId) {
        if (outDepId == null) {
            return 0;
        }
        Map map = new HashMap();
        map.put("expressCode", expressCode);
        map.put("id", outDepId);
        return this.sqlMapClientTemplate.update("updateExpressCodeById", map);
    }

    public List<OutDepository> getOutDepositorysWithDetail(Map parmap) {
        return this.sqlMapClientTemplate.queryForList("getOutDepositorysWithDetail", parmap);
    }

    public int getOutDepositorysWithDetailCount(Map parmap) {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "getOutDepositorysWithDetailCount", parmap);
    }

    @SuppressWarnings("unchecked")
    public QueryPage gatherOutDepositoryLists(Map parMap, int currentPage, int pageSize, boolean isPage){
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			int count = (Integer) sqlMapClientTemplate.queryForObject("gatherOutDepositoryListsCount", parMap);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClientTemplate.queryForList("gatherOutDepositoryLists", parMap));
			}
		}else{
			queryPage.setItems(sqlMapClientTemplate.queryForList("gatherOutDepositoryLists", parMap));
		}

		return queryPage;
    }

    /* (non-Javadoc)
     * @see com.hundsun.bible.dao.ios.OutDepositoryDao#gatherOutDepositoryListsCount(java.util.Map)
     */
    public int gatherOutDepositoryListsCount(Map parMap)  {
        return (Integer) this.sqlMapClientTemplate.queryForObject(
            "gatherOutDepositoryListsCount", parMap);
    }

    public OutDepository getOutDepositoryByTid(String tid)  {
        List<OutDepository> list = this.sqlMapClientTemplate.queryForList("getOutDepositoryByTid", tid);
        OutDepository outDepository =null;
        if(list!=null && list.size()>0){
            outDepository=list.get(0);
        }
        return outDepository;
    }

    public OutDepository getOutDepositoryByDetailId(Long detailId) {
        try {
            return (OutDepository) this.sqlMapClientTemplate.queryForObject(
                "getOutDepositoryByDetailId", detailId);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
        return null;
    }

    public List<OutDepository> getOutDepositoryByExpressCode(String expressCode) {
        Map parm = new HashMap();
        parm.put("expressCode", expressCode);
        return this.sqlMapClientTemplate.queryForList("getOutDepositoryByExpressCode", parm);
    }

    /**
     * @Title: updateIsOutDepositoryPrintedById
     * @Description: 更新isOutDepositoryPrinted字段
     * @param ids
     * @return int
     * @
      */
     public int updateIsOutDepositoryPrintedById(String[] ids){
    	 Map map = new HashMap();
    	 map.put("ids", ids);
    	 return this.sqlMapClientTemplate.update("updateIsOutDepositoryPrintedById", map);
     }

     /**
     * @Title: updateIsExpressPrintedById
     * @Description: 更新isExpressPrinted字段
     * @param ids
     * @return int
     * @
      */
     public int updateIsExpressPrintedById(String[] ids){
    	 Map map = new HashMap();
    	 map.put("ids", ids);
    	 return this.sqlMapClientTemplate.update("updateIsExpressPrintedById", map);
     }

     /**
     * <p>Title: updateActualWeightById</p>
     * <p>Description: 根据ID更新实际重量</p>
     * @param actualWeight
     * @param id
     * @return
     * @see com.hundsun.bible.dao.ios.OutDepositoryDao#updateActualWeightById(java.lang.Double, java.lang.String)
      */
     public int updateActualWeightById(Double actualWeight,String id){
    	 Map map = new HashMap();
    	 map.put("id", id);
    	 map.put("actualWeight", actualWeight);
    	 return this.updateByMap(map);
     }

     /**
     * <p>Title: updateCastWeightById</p>
     * <p>Description: 根据ID更新计抛重量</p>
     * @param castWeight
     * @param id
     * @return
     * @see com.hundsun.bible.dao.ios.OutDepositoryDao#updateCastWeightById(java.lang.Double, java.lang.String)
      */
     public int updateCastWeightById(Double castWeight,String id){
    	 Map map = new HashMap();
    	 map.put("id", id);
    	 map.put("castWeight", castWeight);
    	 return this.updateByMap(map);
     }


     /**
     * @Title: updateByMap
     * @Description: 根据Map来更新数据库
     * @param @param map
     * @param @return
     * @return int
     * @throws
      */
     public int updateByMap(Map map){
    	 return this.sqlMapClientTemplate.update("updateByMap",map);
     }
     
     public int updateHandleAdminIdByUser(Long handleAdminId, Long outDepositoryId) {
         Map map = new HashMap();
         map.put("handleAdminId", handleAdminId);
         map.put("outDepositoryId", outDepositoryId);
         return this.sqlMapClientTemplate.update("updateHandleAdminIdByUser", map);
     }
}
