package com.huaixuan.network.biz.dao.goods.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsAgentDao;
import com.huaixuan.network.biz.domain.goods.GoodsAgent;
import com.huaixuan.network.biz.query.QueryPage;
import com.ibatis.sqlmap.client.SqlMapExecutor;

@Repository("goodsAgentDao")
public class GoodsAgentDaoiBatis implements GoodsAgentDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	protected Log  log = LogFactory.getLog(this.getClass());

//    public List<GoodsAgent> getGoodsAgentByParameterMap(Map parameterMap, Page page) {
//        try {
//            return this.findQueryPage("getGoodsAgentByParameterMap", parameterMap, page);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;
//    }

    public  void insertBatchGoodsAgent(final List<GoodsAgent> agent,final List<Map> agentList){
    	sqlMapClient.execute(new SqlMapClientCallback(){
    		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException{
    			executor.startBatch();
    			for(GoodsAgent goodsAgetn : agent){
    				executor.insert("addGoodsAgent",goodsAgetn);
    			}
    			for(Map agentMap:agentList){
    				executor.update("updateAgentStatusById",agentMap);
    			}
    			executor.executeBatch();
    			return null;
    		}
    	});
    }
    public int countGoodsAgentByUserId(Map paramMap){
    	return (Integer) this.sqlMapClient.queryForObject("countGoodsAgent",paramMap);
    }

    public void updateBatchGoodsAgent(final List<Map> agentList){
    	sqlMapClient.execute(new SqlMapClientCallback(){
    		public Object doInSqlMapClient(SqlMapExecutor executor)throws SQLException{
    			executor.startBatch();
    			for(Map agentMap:agentList){
    				executor.update("updateAgentStatusById",agentMap);
    			}
    			executor.executeBatch();
    			return null;
    		}
    	});
    }

    public int getGoodsAgentCountByParameterMap(Map<String, String> parMap) {
        try {
            return (Integer) this.sqlMapClient.queryForObject(
                "getGoodsAgentCountByParameterMap", parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

//    public List<GoodsAgent> getErrorGoodsAgentByParameterMap(Map parameterMap, Page page) {
//        try {
//            return this.findQueryPage("getErrorGoodsAgentByParameterMap", parameterMap, page);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;
//    }

    public int getErrorGoodsAgentCountByParameterMap(Map<String, String> parMap) {
        try {
            return (Integer) this.sqlMapClient.queryForObject(
                "getErrorGoodsAgentCountByParameterMap", parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public void updateAgentStatus(Map<String, Object> parMap) {
        try {
            this.sqlMapClient.update("updateAgentStatusById", parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void insertGoodsAgent(GoodsAgent goodsAgent) {
        this.sqlMapClient.insert("addGoodsAgent", goodsAgent);

    }

    public GoodsAgent getGoodsAgent(Map<String, Object> parMap) {
        return (GoodsAgent) this.sqlMapClient.queryForObject("getGoodsAgent", parMap);
    }

    @SuppressWarnings("unchecked")
    public List<GoodsAgent> getGoodsAgentList(Map<String, Object> parMap) {
        return this.sqlMapClient.queryForList("getGoodsAgent", parMap);

    }

    @SuppressWarnings("unchecked")
    public List<Long> getPastUserIdForAddGoodsRelation() {
        return this.sqlMapClient.queryForList("getPastUserIdForAddGoodsRelation");
    }

    @SuppressWarnings("unchecked")
    public List<Long> getCurrUserIdForAddGoodsRelation(Long userId) {
        return this.sqlMapClient.queryForList("getCurrUserIdForAddGoodsRelation",
            userId);
    }

    public int editStatusByUserIdGoodsId(GoodsAgent goodsAgent) {
        return this.sqlMapClient.update("editStatusByUserIdGoodsId", goodsAgent);
    }

    public void insertBatchAgentUserForGoodsId(Long goodsId) {
        this.sqlMapClient.insert("insertBatchAgentUserForGoodsId", goodsId);
    }
}
