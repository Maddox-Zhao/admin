package com.huaixuan.network.biz.dao.remote.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.remote.TaobaoInterfaceSyncDao;
import com.huaixuan.network.biz.domain.taobao.TaobaoInterfaceSync;

@Repository("taobaoInterfaceSyncDao")
public class TaobaoInterfaceSyncDaoiBatis implements TaobaoInterfaceSyncDao {
    protected final Log          log = LogFactory.getLog(this.getClass());

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public void addInterfaceSync(TaobaoInterfaceSync interfaceSync) {
        this.sqlMapClient.insert("addTaobaoInterfaceSync", interfaceSync);
    }

    public List<TaobaoInterfaceSync> getInterfaceSyncList(Map parMap) {
        return this.sqlMapClient.queryForList("getTaobaoInterfaceSyncList", parMap);
    }

    public int getInterfaceSyncListCount(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getTaobaoInterfaceSyncCount", parMap);
    }

    public void updateInterfaceSync(TaobaoInterfaceSync interfaceSync) {
        this.sqlMapClient.update("updateTaobaoInterfaceSync", interfaceSync);
    }

    public void insertBatchSyncList(List<TaobaoInterfaceSync> interfaceList) {
        if (interfaceList != null && interfaceList.size() > 0) {
            for (TaobaoInterfaceSync interfaceSync : interfaceList) {
                this.sqlMapClient.insert("addTaobaoInterfaceSync", interfaceSync);
            }
        }
    }

    public int getTaobaoInterfaceSyncSearchCount(Map<String, String> parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getTaobaoInterfaceSyncSearchCount",
            parMap);
    }

    public List<TaobaoInterfaceSync> getTaobaoInterfaceSyncListSearch(Map parMap) {

        return this.sqlMapClient.queryForList("getTaobaoInterfaceSyncListSearch", parMap);

    }

    public int getInterfaceSyncListForCheck(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getInterfaceSyncListForCheck", parMap);
    }

    public void updateSync(TaobaoInterfaceSync interfaceSync) {
        this.sqlMapClient.update("updateSync", interfaceSync);
    }
}
