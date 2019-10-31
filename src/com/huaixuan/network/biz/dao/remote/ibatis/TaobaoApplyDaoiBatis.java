package com.huaixuan.network.biz.dao.remote.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.remote.TaobaoApplyDao;
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;

/**
 * @version 3.2.0
 */
@Repository("taobaoApplyDao")
public class TaobaoApplyDaoiBatis implements TaobaoApplyDao {

    protected final Log          log = LogFactory.getLog(this.getClass());

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    /* @model: ���һ��InterfaceApply��¼ */
    public void addTaobaoApply(TaobaoApply interfaceApply) throws Exception {
        this.sqlMapClient.insert("addTaobaoApply", interfaceApply);
    }

    /* @model: ����һ��InterfaceApply��¼ */
    public void editTaobaoApply(TaobaoApply interfaceApply) throws Exception {
        this.sqlMapClient.update("editTaobaoApply", interfaceApply);
    }

    public void editGmtSync(TaobaoApply interfaceApply) throws Exception {
        this.sqlMapClient.update("editGmtSyncTaobao", interfaceApply);
    }

    /* @model: ɾ��һ��InterfaceApply��¼ */
    public void removeTaobaoApply(Long interfaceApplyId) throws Exception {
        this.sqlMapClient.delete("removeTaobaoApply", interfaceApplyId);
    }

    public TaobaoApply getTaobaoApply(Long interfaceApplyId) throws Exception {
        return (TaobaoApply) this.sqlMapClient.queryForObject("getTaobaoApply", interfaceApplyId);
    }

    public TaobaoApply getTaobaoApplyByUserId(Long userId, String type) throws Exception {
        try {
            Map parMap = new HashMap();
            parMap.put("userId", userId);
            parMap.put("type", type);
            return (TaobaoApply) this.sqlMapClient.queryForObject("getTaobaoApplyByUserId", parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<TaobaoApply> getTaobaoApplys() throws Exception {
        return this.sqlMapClient.queryForList("getTaobaoApplys", null);
    }

    public int getCountByMap(Map parMap) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject("getCountByMapTaobao", parMap);
    }

    public List<TaobaoApply> getListByMap(Map parMap) throws Exception {

        return this.sqlMapClient.queryForList("getListByMapTaobao", parMap);

    }

    public void editTaobaoApplyStatus(Long id, String status, String memo) throws Exception {
        TaobaoApply interfaceApply = new TaobaoApply();
        interfaceApply.setId(id);
        interfaceApply.setStatus(status);
        interfaceApply.setMemo(memo);
        this.sqlMapClient.update("editTaobaoApplyStatus", interfaceApply);
    }
    
    public void modifyOwnExpressIdById(Long id, Long ownExpressId) {
        Map parMap = new HashMap();
        parMap.put("id", id);
        parMap.put("ownExpressId", ownExpressId);
        this.sqlMapClient.update("modifyOwnExpressIdById",parMap);
    }
    
    public void modifyInterfaceExpressCodeById(Long id, String interfaceExpressCode) {
        Map parMap = new HashMap();
        parMap.put("id", id);
        parMap.put("interfaceExpressCode", interfaceExpressCode);
        this.sqlMapClient.update("modifyInterfaceExpressCodeById",parMap);
    }
}
